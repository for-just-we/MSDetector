package generator;

import org.junit.Test;
import java.io.IOException;

public class ScriptParserTest {

    @Test
    public void parseTest() throws IOException {
        String testfile = "src/test/files/normal/mark11.php";
        ScriptParser scriptParser = new ScriptParser();
        scriptParser.parse(testfile);
        System.out.println();
    }
}