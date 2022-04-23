package model;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

public class SimpleNode {

    private String typeLabel;

    private SimpleNode parent; // parent node

    private String token;

    private List<SimpleNode> children; // child node

    private NodeRange range; // range of this token corresponding to the node

    public void replaceChildren(List<SimpleNode> newChildren) {
        children.clear();
        newChildren.forEach(it->{it.parent = this;});
        children.addAll(newChildren);
    }

    @Override
    public String toString() {
        return String.format("%s : %s", typeLabel, token);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }


    public SimpleNode(String typeLabel,
                      SimpleNode parent,
                      String token) {
        this.children = new ArrayList<>();
        this.typeLabel = typeLabel;
        this.parent = parent;
        this.token = token;
    }

    // dump ast
    public void prettyPrint(int indent, String indentSymbol){
        System.out.print(indentSymbol.repeat(indent));
        System.out.println(this);
        children.forEach(it -> {it.prettyPrint(indent + 1, indentSymbol);});
    }


    // getter and setter
    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public SimpleNode getParent() {
        return parent;
    }

    public void setParent(SimpleNode parent) {
        this.parent = parent;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<SimpleNode> getChildren() {
        return children;
    }

    public void setChildren(List<SimpleNode> children) {
        this.children = children;
    }

    public NodeRange getRange() {
        return range;
    }

    public void setRange(NodeRange range) {
        this.range = range;
    }
}
