import com.alibaba.fastjson.JSON;
import generator.ScriptParser;
import org.apache.commons.cli.*;
import generator.TokenGenerator;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        CommandLineParser parser = new BasicParser();
        Options options = new Options( );
        options.addOption("h", "help", false, "Print this usage information");
//        options.addOption("d", "dir", false, "parse all php files in a dir" );
        options.addOption("f", "file", true, "parse s single php file");
        // Parse the program arguments
        CommandLine commandLine = parser.parse( options, args );

        boolean verbose = false;

        ScriptParser scriptParser = new ScriptParser();

        if( commandLine.hasOption('h') ) {
            System.out.println( "Help Message");
            System.exit(0);
        }
//        if( commandLine.hasOption('d') ) {
//            verbose = true;
//        }
        if( commandLine.hasOption('f') ) {
            String phpFileName = commandLine.getOptionValue('f');
            scriptParser.clear();
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
}
