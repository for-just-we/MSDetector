package util;

import baseListener.PhpParser;
import model.SimpleNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

/*
kotlin version is much simple: https://github.com/JetBrains-Research/astminer/blob/master/src/main/kotlin/astminer/parse/antlr/conversionUtil.kt
 */

public class ConversionUtil {

    private static final Vocabulary vocabulary = PhpParser.VOCABULARY;
    private static final String[] ruleNames = PhpParser.ruleNames;

    public static SimpleNode convertAntlrTree(ParserRuleContext tree){
        return convertRuleContext(tree, null);
    }

    // recursively convert Antlr AST to simplified AST
    private static SimpleNode convertRuleContext(ParserRuleContext ruleContext,
                                                SimpleNode parent){
        String typeLabel = ruleNames[ruleContext.getRuleIndex()];
        SimpleNode currentNode = new SimpleNode(typeLabel, parent, null);
        List<SimpleNode> children = new ArrayList<>();

        if (ruleContext.children == null)
            return currentNode;
        ruleContext.children.forEach(it -> {
            if (it instanceof TerminalNode)
                children.add(convertTerminalNode((TerminalNode)it, currentNode));
            else if (it instanceof ErrorNode)
                children.add(convertErrorNode((ErrorNode)it, currentNode));
            else
                children.add(convertRuleContext((ParserRuleContext)it, currentNode));
        });

        currentNode.replaceChildren(children);
        return currentNode;
    }

    // convert error node
    private static SimpleNode convertErrorNode(ErrorNode errorNode,
                                              SimpleNode parent){
        return new SimpleNode(
                            "Error",
                            parent,
                            errorNode.getText()
        );
    }

    // convert terminal node
    private static SimpleNode convertTerminalNode(TerminalNode terminalNode,
                                              SimpleNode parent){
        String curType = vocabulary.getSymbolicName(terminalNode.getSymbol().getType());
        String parentType = parent.getTypeLabel();

        if (parentType.equals("stringConstant") && curType.equals("Label"))
            curType = "stringConstant";

        return new SimpleNode(
                curType,
                parent,
                terminalNode.getSymbol().getText()
        );
    }


    // compute range
//    private static NodeRange getRange(ParserRuleContext ruleContext){
//        return new NodeRange(new Position(ruleContext.getStart().getLine(),
//                                ruleContext.getStart().getCharPositionInLine()),
//                new Position(ruleContext.getStop().getLine(),
//                        ruleContext.getStop().getCharPositionInLine() + ruleContext.getStop().getStopIndex() - ruleContext.getStop().getStartIndex()));
//    }
//
//    private static NodeRange getRange(TerminalNode terminalNode){
//        return new NodeRange(new Position(terminalNode.getSymbol().getLine(),
//                                          terminalNode.getSymbol().getCharPositionInLine()),
//                             new Position(terminalNode.getSymbol().getLine(),
//                                      terminalNode.getSymbol().getCharPositionInLine() +
//                                              terminalNode.getSymbol().getStopIndex()
//                                              - terminalNode.getSymbol().getStartIndex()));
//    }
//
//    private static NodeRange getRange(ErrorNode errorNode){
//        return new NodeRange(new Position(errorNode.getSymbol().getLine(),
//                errorNode.getSymbol().getCharPositionInLine()),
//                new Position(errorNode.getSymbol().getLine(),
//                        errorNode.getSymbol().getCharPositionInLine() +
//                                errorNode.getSymbol().getStopIndex()
//                                - errorNode.getSymbol().getStartIndex()));
//    }


    // simplify AST by PreOrderVisit
    public static SimpleNode compressTree(SimpleNode root){
        // ignore leaf node
        if (root.isLeaf())
            return root;

        while (root.getChildren().size() == 1){
            SimpleNode preParent = root.getParent();
            root = root.getChildren().get(0);
            root.setParent(preParent);
        }

        List<SimpleNode> newChildren = new ArrayList<>();
        root.getChildren().forEach(child -> {newChildren.add(compressTree(child));});
        root.replaceChildren(newChildren);
        return root;
    }
}
