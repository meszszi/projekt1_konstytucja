package agh.po.mszpyrka.projekt1;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;

import java.io.*;
import java.util.LinkedList;


/**
 * class used to execute program
 */
public class ExecSystem {

    public static void exec(String[] cmdArgs) throws ParseException, InvalidFileContentsException, InvalidSearchExpressionException, IOException {

        ArgsManager manager = new ArgsManager(cmdArgs);

        // print help message
        if(manager.needsHelp())
            manager.printHelp();

        else {
            if(!manager.hasFilePath())
                throw new MissingArgumentException("Source file path must be specified with -f option");

            Contents document;

            // create reader from source file path
            try ( BufferedReader reader = new BufferedReader(
                    new FileReader(
                            new File(manager.getFilePath())))) {

                // create list of DocLines
                SourceParser sourceParser = new SourceParser();
                LinkedList<DocLine> lineList = sourceParser.parseFromReader(reader);

                if(lineList.size() == 0)
                    throw new InvalidFileContentsException("Could not parse input file properly");

                // create Contents tree
                document = new Contents(lineList.getFirst());
                document.parse(lineList, 0);
            }

            if(manager.hasSearchExpression()) {

                DocSearcher searcher = new DocSearcher(document);
                DocumentPath[] paths = manager.getSearchPaths();

                LinkedList<Contents> sourceList;

                if(paths.length == 1)
                    sourceList = searcher.getRange(paths[0], paths[0]);

                else
                    sourceList = searcher.getRange(paths[0], paths[1]);

                System.out.println(DocFormatter.showContents(sourceList, manager.tableOfContentsMode()));
            }
            else {
                LinkedList<Contents> tmp = new LinkedList<>();
                tmp.add(document);
                System.out.println(DocFormatter.showContents(tmp, manager.tableOfContentsMode()));
            }
        }
    }
}
