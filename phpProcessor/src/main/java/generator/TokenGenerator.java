package generator;

import baseListener.PhpLexer;
import baseListener.PhpParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TokenGenerator {
    private final Vocabulary vocabulary = PhpParser.VOCABULARY;
    private final List<String> globalVariables = Arrays.asList("$_GET", "$GLOBALS", "$_SERVER", "$_REQUEST","$_POST",
            "$_FILES","$_ENV","$_COOKIE","$_SESSION"); //全局变量表
    private final List<String> emptyWords = Arrays.asList("", "\\n", "\\t", "\\r", " ");

    private final String stringToken = "<string>";
    private final String integerToken = "<integer>";
    private final String floatToken = "<float>";

    private List<String> tokenSequence;
    private List<String> stringLiterals;

    private Map<String, String> varNameTable; //变量表
    private int varNameCount; //计算有多少个不同的变量

    public TokenGenerator() {
        tokenSequence = new ArrayList<>();
        stringLiterals = new ArrayList<>();

        varNameCount = 0;
        varNameTable = new HashMap<>();
    }

    public void clear() {
        tokenSequence.clear();
        stringLiterals.clear();
        varNameTable.clear();
        varNameCount = 0;
    }

    private List<String> parser(String str){
        List<String> strList = new ArrayList<>();
        String[] strings = str.split(" ");
        for (String s: strings){
            if (s.length() >= 50)
                strList.add("long_word");
            else if (s.length() > 0)
                strList.add(s);
        }
        return strList;
    }

    public List<String> getTokenSequence() {
        return tokenSequence;
    }

    public List<String> getStringLiterals() {
        return stringLiterals;
    }

    public void parseToken(InputStream content) throws IOException {
        PhpLexer lexer = new PhpLexer(CharStreams.fromStream(content));
        lexer.removeErrorListeners();
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        PhpParser parser = new PhpParser(tokens);
        parser.removeErrorListeners();
        PhpParser.PhpBlockContext tree = parser.phpBlock();

        generateTokens(tree);
    }

    @SuppressWarnings("all")
    private void generateTokens(ParserRuleContext tree){
        if (tree.children == null)
            return;

        tree.children.forEach(
                it->{
                    if (it == null)
                        return;
                    if (it instanceof TerminalNode){
                        TerminalNode terminalNode = (TerminalNode)it;
                        String token = terminalNode.getSymbol().getText();
                        String type = vocabulary.getSymbolicName(terminalNode.getSymbol().getType());
                        switch (type) {

                            case "VarName":  //变量名
                                if (globalVariables.contains(token)){
                                    tokenSequence.add(token);
                                    break;
                                }
                                String varToken;
                                if (varNameTable.containsKey(token)) {
                                    varToken = varNameTable.get(token);
                                } else {
                                    ++varNameCount;
                                    varToken = "$var" + varNameCount;
                                    varNameTable.put(token, varToken);
                                }
                                tokenSequence.add(varToken);
                                break;

                            case "SingleQuoteString":// 字符串值
                                if (!emptyWords.contains(token.substring(1, token.length() - 1))){
                                    stringLiterals.addAll(parser(token.substring(1, token.length() - 1)));
                                    tokenSequence.add(stringToken);
                                }
                                break;

                            case "StringPart":// 字符串值
                                if (!emptyWords.contains(token)){
                                    stringLiterals.addAll(parser(token));
                                    tokenSequence.add(stringToken);
                                }
                                break;

                            case "Real":// float型
                                tokenSequence.add(floatToken);
                                break;

                            case "Decimal":  // int
                                tokenSequence.add(integerToken);
                                break;


                            default:
                                if (token != null)
                                    tokenSequence.add(token);
                                break;
                        }


                    }else if (it instanceof ErrorNode){

                    }else
                    if (it instanceof PhpParser.StringConstantContext){ //字符串值
                        PhpParser.StringConstantContext stringConstantContext = (PhpParser.StringConstantContext)it;
                        String token = stringConstantContext.Label().getSymbol().getText();
                        if (!emptyWords.contains(token)){
                            stringLiterals.add(token);
                            tokenSequence.add(stringToken);
                        }
                    }
                    else
                        generateTokens((ParserRuleContext)it);
                }
        );
    }
}
