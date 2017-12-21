package agh.po.mszpyrka.projekt1;


import org.apache.commons.cli.*;

import java.io.IOException;

/**
 * class used for parsing and managing program arguments
 */
public class ArgsManager {

    private final CommandLine commandLine;
    private final Options options;

    public ArgsManager(String[] args) throws ParseException {

        this.options = new Options();

        // source file
        Option file = new Option("f", "file", true, "path to source file");
        file.setArgName("path");

        // table of contents mode
        Option tableMode = new Option("t", false, "show only table of contents");

        // search expression
        Option show = new Option("s", "show", true, "show only specified part of document,\n" +
                "e.g. -s \"rozdział II, art. 32., 2.\";\n" +
                "~> use \":\" to specify range,\n" +
                "e.g. -s \"art. 23. : art. 48.\"");
        show.setArgName("expression");

        // help
        Option help = new Option("h", "help", false, "print this help message");

        this.options.addOption(file);
        this.options.addOption(tableMode);
        this.options.addOption(show);
        this.options.addOption(help);

        CommandLineParser parser = new DefaultParser();
        this.commandLine = parser.parse(options, args);


    }


    public boolean needsHelp() {
        return this.commandLine.hasOption("h");
    }

    public boolean tableOfContentsMode() {
        return this.commandLine.hasOption("t");
    }

    public String getFilePath() {
        return this.commandLine.getOptionValue("f");
    }

    public boolean hasSearchExpression() {
        return this.commandLine.hasOption("s");
    }

    public void printHelp() {

        String header = "search through act file\n\n";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("appname", header, this.options, "", true);

    }

    /**
     * divides search expression String into array of String-paths
     * @return - array of path-arrays
     * @throws IOException - thrown when there is more than one colon in search expression
     */
    public DocumentPath[] getSearchPaths () throws Exception {

        String[] paths = this.commandLine.getOptionValue("s").split(":");


        if(paths.length == 0 || paths.length > 2)
            throw new Exception("error: invalid search expression format");

        DocumentPath[] result = new DocumentPath[paths.length];
        for(int i = 0; i < result.length; i++)
            result[i] = new DocumentPath(paths[i]);

        return result;
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
