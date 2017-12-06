package agh.po.mszpyrka.projekt1;

import java.io.IOException;
import java.util.LinkedList;

public class CommandLineParser {

    /**
     * Command line user manual:
     *
     * #1 arg: file path
     *
     * #1 option:
     *      -t -> table of contents mode
     *
     * #2 arg: document path in single quotes, elements separated with comma ( e.g. 'DZIAŁ II, Rozdział 3, Art. 12., 5., 2)' )
     *
     * if -t option was selected, only the beginning part of the first line contents for each point is shown
     */

    public static int getMode(String[] commandLineArgs) throws IOException {

        if(commandLineArgs.length < 2) {
            throw new IOException("brakuje argumentow");
        }

        switch(commandLineArgs[1]) {
            case "-t":
                return 1;

            default:
                return 0;
        }
    }

//    public static LinkedList<String> getDocSearchPath(String[] commandLineArgs, int mode) {

//    }

}
