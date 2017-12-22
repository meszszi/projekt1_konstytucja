package agh.po.mszpyrka.projekt1;


import org.apache.commons.cli.*;

/**
 * Class used for parsing and managing program arguments.
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
        Option show = new Option("s", "show", true, "show only specified part of document (use ':' to specify range)");
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

    public boolean hasFilePath() {
        return this.commandLine.hasOption("f");
    }

    public String getFilePath() {
        return this.commandLine.getOptionValue("f");
    }

    public boolean hasSearchExpression() {
        return this.commandLine.hasOption("s");
    }


    /**
     * Divides search expression String into array of String-paths.
     * @return - array of path-arrays
     * @throws InvalidSearchExpressionException - thrown when there is more than one colon in search expression or the expression is empty
     */
    public DocumentPath[] getSearchPaths () throws InvalidSearchExpressionException {

        String[] paths = this.commandLine.getOptionValue("s").split(":");


        if(paths.length == 0 || paths.length > 2)
            throw new InvalidSearchExpressionException("Invalid search expression format");

        DocumentPath[] result = new DocumentPath[paths.length];
        for(int i = 0; i < result.length; i++)
            result[i] = new DocumentPath(paths[i]);

        return result;
    }


    /**
     * Prints usage guide using provided in commons-cli HelpFormatter.
     */
    public void printHelp() {

        String header = "search through act file\n\n";

        String footer = "\nusage examples:\n" +
                "appname -f uokik.txt -s \"art 31, 15)\"   <- this command will show only contents of article 31, subsection 15)\n" +
                "appname -f kontytucja.txt -s \"rozdział ii : rozdział iii, art 90\"   <- this command will show contents from chapter II and everything from chapter III up to article 90\n" +
                "appname -f uokik.txt -s \"dział v\" -t   <- this command will show table of contents for section V\n" +
                "appname -f konstytucja.txt -t   <- this command will show table of contents for whole document";

        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(200);
        formatter.printHelp("appname", header, this.options, footer, true);

    }
}
