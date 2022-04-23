import com.alibaba.fastjson.JSON;
import generator.ScriptParser;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest {
    @Test
    public void check() throws IOException {
        String phpFileName = "src/test/files/normal/mark11.php";
        ScriptParser scriptParser = new ScriptParser();
        scriptParser.parse(phpFileName);

        Map<String, List<String>> sequenceData = new HashMap<>(){
            {
                put("tokenSequence", scriptParser.getTokenSequence());
                put("stringSequence", scriptParser.getStringLiterals());
                put("tags", scriptParser.getTags());
            }
        };
        String jsonStr = JSON.toJSONString(sequenceData);
        System.out.println(jsonStr);
    }
}