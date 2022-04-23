package generator;

import baseListener.PhpLexer;
import baseListener.PhpParser;
import model.NodeTag;
import model.SimpleNode;
import model.StringType;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Vocabulary;
import util.ConversionUtil;
import util.StringParser;
import util.Words;

import java.io.*;
import java.util.*;

//There are some errors when parsing Heredoc and Nowdoc

public class ScriptParser {
    private List<String> tokenSequence;
    private List<String> stringLiterals;
    private List<String> tags;

    private Map<String, String> varNameTable; //store user-defined varnames
    private Map<String, String> funcNameTable; //store user-defined funcnames
    private Map<String, String> classNameTable; //store user-defined funcnames

    private final Set<String> stringTypes = new HashSet<>(Arrays.asList("HereDocText", "stringConstant", "StringPart", "SingleQuoteString", "BackQuoteString"));
    private final Set<String> jumpTypes = new HashSet<>(Arrays.asList("StartNowDoc", "StartHereDoc", "DoubleQuote", "attributes", "useDeclaration", "namespaceDeclaration",
            "SuppressWarnings"));
    private final Set<String> classRelatedTypes = new HashSet<>(Arrays.asList("Class", "Trait", "Interface"));
    private final Set<String> modifiers = new HashSet<>(Arrays.asList("Abstract", "Final", "Private", "Public", "Partial", "Protected"));
    private final Set<String> quoteSymbols = new HashSet<>(Arrays.asList("functionDeclaration", "foreachStatement", "formalParameter",
            "classStatement", "lambdaFunctionExpr", "lambdaFunctionUseVar", "actualArgument"));


    private final Map<String, String> constantTypes = new HashMap<>(){
        {
            put("Real", "floatConstant");
            put("BooleanConstant", "boolConstant");
            put("Octal", "integerConstant");
            put("Decimal", "integerConstant");
            put("Hex", "integerConstant");
            put("Binary", "integerConstant");
        }
    };

    public ScriptParser() {
        tokenSequence = new ArrayList<>();
        stringLiterals = new ArrayList<>();
        tags = new ArrayList<>();

        varNameTable = new HashMap<>();
        funcNameTable = new HashMap<>();
        classNameTable = new HashMap<>();
    }

    public void parse(String fileName) throws IOException {
        InputStream content = new FileInputStream(new File(fileName));

        PhpLexer lexer = new PhpLexer(CharStreams.fromStream(content));
        lexer.removeErrorListeners();
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        PhpParser parser = new PhpParser(tokens);
        parser.removeErrorListeners();
        PhpParser.PhpBlockContext tree = parser.phpBlock();

        SimpleNode simpleTree = ConversionUtil.convertAntlrTree(tree);
        simpleTree = ConversionUtil.compressTree(simpleTree);
        parseSimpleTree(simpleTree);
    }

    // flag indicate whether parent node is in function call context and root is its first child
    private void parseSimpleTree(SimpleNode root){
        String curType = root.getTypeLabel();

        if (jumpTypes.contains(curType))
            return;

        // constants
        if (constantTypes.containsKey(curType)) {
            tokenSequence.add(constantTypes.get(curType));
            tags.add(constantTypes.get(curType));
        }
        // parse strings
        else if (stringTypes.contains(curType))
            parseString(root, curType);
        // parse variable, in this case, varName could not be a function call
        else if (curType.equals("VarName"))
            parseVarName(root, false);

        // parse FunctionDecl
        else if (curType.equals("functionDeclaration"))
            parseFunctionDeclaration(root);

        // parse ClassDecl
        else if (curType.equals("classDeclaration"))
            parseClassDeclaration(root);

        // parse ClassStatement
        else if (curType.equals("classStatement"))
            parseClassStatement(root);

        // parse fuction call
        else if (curType.equals("functionCall"))
            parseFuncCall(root);

        // parse member access
        else if (curType.equals("memberAccess"))
            parseMemberAccess(root);

        // parse NewExpression
        else if (curType.equals("newExpr"))
            parseNewExpr(root);

        // parse classConstant
        else if (curType.equals("classConstant"))
            parseClassConstant(root, false);

        else if (curType.equals("Ampersand")){
            tokenSequence.add(root.getToken());
            if (quoteSymbols.contains(root.getParent().getTypeLabel()))
                tags.add(NodeTag.Quote.getTag());
            else
                tags.add(curType);
        }

        else if (curType.equals("string"))
            parseString(root);

        else{
            if (root.isLeaf()){
                tokenSequence.add(root.getToken());
                tags.add(curType);
            }
            else
                root.getChildren().forEach(this::parseSimpleTree);
        }

    }


    public void clear(){
        tokenSequence.clear();
        tags.clear();
        stringLiterals.clear();

        varNameTable.clear();
        funcNameTable.clear();
        classNameTable.clear();
    }

    private void parseString(SimpleNode root, String curType){
        String content = root.getToken();
        if (curType.equals("SingleQuoteString"))
            content = content.substring(1, content.length() - 1);
        else if (curType.equals("BackQuoteString")){
            tokenSequence.add("shell_exec");
            tags.add(NodeTag.FuncCall.getTag());
            tokenSequence.add("(");
            tags.add("OpenRoundBracket");
            content = content.substring(1, content.length() - 1);
        }

        StringType type = StringParser.classify(content);
        tokenSequence.add(type.getName());
        tags.add(NodeTag.StringLiteral.getTag());

        if (curType.equals("BackQuoteString")){
            tokenSequence.add(")");
            tags.add("CloseRoundBracket");
        }

        if (!Words.emptyWords.contains(content)){
            switch (type){
                case CodeString:
                    stringLiterals.add(content);
                    break;
                case NormalString:
                    stringLiterals.add(content);
                    break;
                default:
                    break;
            }
        }
    }

    private void parseVarName(SimpleNode root, boolean flag){
        String varName = root.getToken();

        if (Words.systemVariables.contains(varName))
            tokenSequence.add(varName);
            // it has been symbolized
        else if (varNameTable.containsKey(varName))
            tokenSequence.add(varNameTable.get(varName));
            // if has bot been symbolized yet
        else {
            int index = varNameTable.size() + 1;
            String newSymbolizedName = Words.varName + index;
            varNameTable.put(varName, newSymbolizedName);
            tokenSequence.add(newSymbolizedName);
        }
        tags.add(flag? NodeTag.FuncCall.getTag() :NodeTag.VarName.getTag());
    }

    private void parseFunctionDeclaration(SimpleNode root){
        root.getChildren().forEach(it -> {
            if (!it.isLeaf())
                parseSimpleTree(it);
            else{
                switch (it.getTypeLabel()) {
                    case "Function":
                        tokenSequence.add(it.getToken());
                        tags.add(it.getTypeLabel());
                        break;
                    // FuncName
                    case "Label":
                        String symbolizedFuncName;
                        if (funcNameTable.containsKey(it.getToken()))
                            symbolizedFuncName = funcNameTable.get(it.getToken());
                        else {
                            int index = funcNameTable.size() + 1;
                            symbolizedFuncName = Words.FuncName + index;
                            funcNameTable.put(it.getToken(), symbolizedFuncName);
                        }
                        tokenSequence.add(symbolizedFuncName);
                        tags.add(NodeTag.FuncName.getTag());
                        break;

                    default:
                        parseSimpleTree(it);
                        break;
                }
            }
        });
    }

    private void parseClassDeclaration(SimpleNode root){
        root.getChildren().forEach(it -> {
            if (!it.isLeaf())
                parseSimpleTree(it);
            else{
                if (classRelatedTypes.contains(it.getTypeLabel())){
                    tokenSequence.add(it.getToken());
                    tags.add(it.getTypeLabel());
                }
                else if (modifiers.contains(it.getTypeLabel())){
                    tokenSequence.add(it.getToken());
                    tags.add(NodeTag.Modifier.getTag());
                }
                // class name
                else if (it.getTypeLabel().equals("Label")){
                    String symbolizedClassName;
                    if (classNameTable.containsKey(it.getToken()))
                        symbolizedClassName = classNameTable.get(it.getToken());
                    else {
                        symbolizedClassName = Words.className + (classNameTable.size() + 1);
                        classNameTable.put(it.getToken(), symbolizedClassName);
                    }
                    tokenSequence.add(symbolizedClassName);
                    tags.add(NodeTag.ClassName.getTag());
                }

                else
                    parseSimpleTree(it);
            }
        });
    }

    private void parseClassStatement(SimpleNode root){
        root.getChildren().forEach(it -> {
            if (!it.isLeaf())
                parseSimpleTree(it);
            else{
                if (modifiers.contains(it.getTypeLabel())){
                    tokenSequence.add(it.getToken());
                    tags.add(NodeTag.Modifier.getTag());
                }
                // FuncName
                else if (it.getTypeLabel().equals("Label")){
                    String symbolizedFuncName;
                    if (it.getToken().startsWith("__"))// magic call like __tostring
                        symbolizedFuncName = it.getToken();
                    else{ // normal inner function declaration
                        if (funcNameTable.containsKey(it.getToken()))
                            symbolizedFuncName = funcNameTable.get(it.getToken());
                        else {
                            int index = funcNameTable.size() + 1;
                            symbolizedFuncName = Words.FuncName + index;
                            funcNameTable.put(it.getToken(), symbolizedFuncName);
                        }
                    }
                    tokenSequence.add(symbolizedFuncName);
                    tags.add(NodeTag.FuncName.getTag());
                }
                //
                else if (it.getTypeLabel().equals("VarName"))
                    parseVarName(it, false);
                else {
                    tokenSequence.add(it.getToken());
                    tags.add(it.getTypeLabel());
                }
            }
        });
    }

    private void parseFuncCall(SimpleNode root){
        SimpleNode funcNameNode = root.getChildren().get(0);

        if (funcNameNode.getTypeLabel().equals("VarName"))
            parseVarName(funcNameNode, true);
        else if (funcNameNode.getTypeLabel().equals("classConstant"))
            parseClassConstant(funcNameNode, true);
        else {
            if (funcNameNode.isLeaf()){
                String funcName = funcNameTable.getOrDefault(funcNameNode.getToken(), funcNameNode.getToken());
                tokenSequence.add(funcName);
                tags.add(NodeTag.FuncCall.getTag());
            }
            else
                parseSimpleTree(funcNameNode);
        }
        root.getChildren().subList(1, root.getChildren().size()).forEach(this::parseSimpleTree);
    }

    private void parseMemberAccess(SimpleNode root){
        int size = root.getChildren().size();
        tokenSequence.add(root.getChildren().get(0).getToken());
        tags.add(root.getChildren().get(0).getTypeLabel());
        if (size < 3){ // < 3, get variable
            SimpleNode varNameNode = root.getChildren().get(1);
            parseVarName(varNameNode, false);
        }
        else{ // function call
            String funcName = root.getChildren().get(1).getToken();
            String symbolizedFuncName = funcNameTable.getOrDefault(funcName, funcName);
            tokenSequence.add(symbolizedFuncName);
            tags.add(NodeTag.FuncCall.getTag());
        }

        root.getChildren().subList(2, root.getChildren().size()).forEach(this::parseSimpleTree);
    }

    private void parseNewExpr(SimpleNode root){
        tokenSequence.add(root.getChildren().get(0).getToken());
        tags.add(root.getChildren().get(0).getTypeLabel());

        SimpleNode classNode = root.getChildren().get(1);

        if (classNode.getTypeLabel().equals("Label")){
            String originClassName = classNode.getToken();
            String symbolizedClassName = classNameTable.getOrDefault(originClassName, originClassName);
            tokenSequence.add(symbolizedClassName);
            tags.add(NodeTag.ClassName.getTag());
        }
        else if (classNode.getTypeLabel().equals("anoymousClass")) //  anoymousClass
            parseClassDeclaration(classNode);


        root.getChildren().subList(2, root.getChildren().size()).forEach(this::parseSimpleTree);
    }

    // flag indicate whether this is a function call
    private void parseClassConstant(SimpleNode root, boolean flag){
        String originClassName = root.getChildren().get(0).getToken();
        String symbolizedClassName = classNameTable.getOrDefault(originClassName, originClassName);
        tokenSequence.add(symbolizedClassName);
        tags.add(NodeTag.ClassName.getTag());

        tokenSequence.add(root.getChildren().get(1).getToken());
        tags.add(root.getChildren().get(1).getTypeLabel());

        if (flag){
            String originFuncName = root.getChildren().get(2).getToken();
            String symbolizedFuncName = funcNameTable.getOrDefault(originFuncName, originFuncName);
            tokenSequence.add(symbolizedFuncName);
            tags.add(NodeTag.FuncName.getTag());
        }
        else { // varName
            String originVarName = root.getChildren().get(2).getToken();
            String symbolizedVarName = funcNameTable.getOrDefault(originVarName, originVarName);
            tokenSequence.add(symbolizedVarName);
            tags.add(NodeTag.VarName.getTag());
        }
    }

    private void parseString(SimpleNode root){
        // concat StringPart inside it, there are some errors that ANTLR might occur, this
        // function is to mitigate the influence of these errors
        // for example: if root.childnode == {StringPart:xx, StringPart: xx1, ...}
        // we need to simplify it to {StringPart: xxxx1..}
        List<SimpleNode> newChildrens = new ArrayList<>();
        newChildrens.add(root.getChildren().get(0));
        root.getChildren().subList(1, root.getChildren().size()).forEach(it -> {
            SimpleNode lastChild = newChildrens.get(newChildrens.size() - 1);
            if (it.getTypeLabel().equals("StringPart") && lastChild.getTypeLabel().equals("StringPart"))
                // concat the content
                lastChild.setToken(lastChild.getToken() + it.getToken());
            else
                newChildrens.add(it);
        });

        root.replaceChildren(newChildrens);
        root.getChildren().forEach(this::parseSimpleTree);
    }

    //getter

    public List<String> getTokenSequence() {
        return tokenSequence;
    }

    public List<String> getStringLiterals() {
        return stringLiterals;
    }

    public List<String> getTags() {
        return tags;
    }
}
