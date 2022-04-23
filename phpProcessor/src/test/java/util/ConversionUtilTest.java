package util;

import baseListener.PhpLexer;
import baseListener.PhpParser;
import model.SimpleNode;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ConversionUtilTest {

    @Test
    public void convertAntlrTreeTest() throws IOException {
        String testfile = "src/test/files/normal/Strings.php";
        InputStream content = new FileInputStream(new File(testfile));

        PhpLexer lexer = new PhpLexer(CharStreams.fromStream(content));
        lexer.removeErrorListeners();
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        PhpParser parser = new PhpParser(tokens);
        parser.removeErrorListeners();
        PhpParser.PhpBlockContext tree = parser.phpBlock();

        SimpleNode simpleTree = ConversionUtil.convertAntlrTree(tree);
        simpleTree.prettyPrint(0, "--");
        simpleTree = ConversionUtil.compressTree(simpleTree);
        System.out.println("=====================================================");
        simpleTree.prettyPrint(0, "--");
    }
}