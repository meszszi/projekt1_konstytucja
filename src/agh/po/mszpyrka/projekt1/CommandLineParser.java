package agh.po.mszpyrka.projekt1;

import java.io.IOException;
import java.util.LinkedList;

public class CommandLineParser {

    private final String[] commandLineArgs;

    public CommandLineParser(String[] args) {
        this.commandLineArgs = args;
    }

    /**
     * Command line user manual:
     *
     * #1 arg: file path | -h -> help mode
     *
     * (*) option:
     *      -t -> table of contents mode
     *
     * #2 arg: document path in single quotes, elements separated with comma ( e.g. 'DZIAŁ II, Rozdział 3, Art. 12., 5., 2)' )
     *
     * if -t option was selected, only the beginning part of the first line contents for each point is shown
     */

    public int getMode() throws Exception {

        Exception invProgArgs = new Exception("error: invalid program arguments");

        // program should be executed with at least 1 argument

        if(this.commandLineArgs.length == 0)
            throw new Exception("error: invalid program arguments");

        if(this.commandLineArgs.length == 1) {
            if (this.commandLineArgs[0].equals("-h"))   // help mode
                return 2;

            return 0;                                   // default fullContents mode
        }

        if(this.commandLineArgs[1].equals("-t"))        // tableOfContents mode
            return 1;

        if(this.commandLineArgs.length > 4)
            throw invProgArgs;

        return 0;
    }


    /**
     * divides search expression String into array of String-paths
     * @return - array of path-arrays
     * @throws IOException - thrown when there is
     */
    public String[][] getAllPaths () throws Exception {
        int mode = this.getMode();
        if(this.commandLineArgs.length < mode + 2)
            return new String[0][];

        String pathsStr = this.commandLineArgs[mode + 1];
        String[] paths = pathsStr.split(":");
        if(paths.length == 0 || paths.length > 2)
            throw new Exception("error: invalid search expression format");

        String[][] ans = new String[paths.length][];
        for(int i = 0; i < paths.length; i++) {
            ans[i] = getPath(paths[i]);
        }

        return ans;
    }


    /**
     * get String array containing single nodes' headings
     * @param pathStr - String containing headings separated with ','
     * @return - array containing single heading Strings
     */
    private String[] getPath(String pathStr) {

        String[] ans = pathStr.split(",");

        for(int i = 0; i < ans.length; i++)
            ans[i] = ans[i].trim();

        return ans;
    }
}
