package util;

import java.util.Arrays;
import java.util.List;

public class Words {
    public static final List<String> systemVariables = Arrays.asList("$_GET", "$GLOBALS", "$_SERVER", "$_REQUEST","$_POST",
            "$_FILES","$_ENV","$_COOKIE","$_SESSION", "$this"); //全局变量表
    public static final List<String> emptyWords = Arrays.asList("", "\n", "\t", "\r" , "\\n" , "\\t", "\\r", " ");


    public static final String varName = "$Var";
    public static final String FuncName = "Func";
    public static final String className = "Class";
}
