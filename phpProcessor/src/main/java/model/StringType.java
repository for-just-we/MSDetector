package model;

public enum StringType {
    HtmlString("html"),
    CodeString("code"),
    EncryptedString("encrypt"),
    NormalString("normal")
    ;
    private String name;

    StringType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}