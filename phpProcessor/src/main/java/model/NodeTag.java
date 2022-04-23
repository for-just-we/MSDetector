package model;

public enum NodeTag {
    FuncCall("FuncCall"),
    StringLiteral("String"),
    FuncName("FuncName"),
    ClassName("ClassName"),
    VarName("VarName"),
    Modifier("modifier"),
    Quote("Quote");


    private String tag;

    NodeTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
