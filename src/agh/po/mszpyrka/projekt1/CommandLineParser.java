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
     * #1 arg: file path
     *
     * #1 option:
     *      -t -> table of contents mode
     *
     * #2 arg: document path in single quotes, elements separated with comma ( e.g. 'DZIAŁ II, Rozdział 3, Art. 12., 5., 2)' )
     *
     * if -t option was selected, only the beginning part of the first line contents for each point is shown
     */

    public int getMode() throws Exception {

        Exception invProgArgs = new Exception("Error: invalid program arguments.");

        // program should be executed with at least 1 argument

        if(this.commandLineArgs.length == 0)
            throw new Exception(invProgArgs);

        if(this.commandLineArgs.length == 1)
            return 0;

        switch(this.commandLineArgs[1]) {
            case "-t":
                return 1;

            default:
                return 0;
        }
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
            throw new IOException("error: invalid search expression");

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

//    public static LinkedList<String> getDocSearchPath(String[] commandLineArgs, int mode) {

//    }

}
