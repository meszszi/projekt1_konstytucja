package agh.po.mszpyrka.projekt1;

import java.io.*;
import java.util.LinkedList;

public class MainSystem {
    public static void main(String[] args) {

        try {
            ArgsManager manager = new ArgsManager(args);

            // print help message
            if(manager.needsHelp())
                manager.printHelp();

            else {
                // create reader from source file path
                BufferedReader reader = new BufferedReader(
                        new FileReader(
                                new File(manager.getFilePath())));

                // create list of DocLines
                SourceParser sourceParser = new SourceParser();
                LinkedList<DocLine> lineList = sourceParser.parseFromReader(reader);

                // create Contents tree
                Contents document = new Contents(lineList.getFirst());
                document.parse(lineList, 0);

                if(manager.hasSearchExpression()) {

                    DocSearcher searcher = new DocSearcher(document);
                    DocumentPath[] paths = manager.getSearchPaths();

                    for(DocumentPath d : paths)
                        System.out.println(d.toString());

                    LinkedList<Contents> sourceList;

                    if(paths.length == 1)
                        sourceList = searcher.getRange(paths[0], paths[0]);

                    else
                        sourceList = searcher.getRange(paths[0], paths[1]);

                    System.out.println(DocFormatter.showContents(sourceList, manager.tableOfContentsMode()));
                }
                else {
                    LinkedList<Contents> ll = new LinkedList<>();
                    ll.add(document);
                    System.out.println(DocFormatter.showContents(ll, manager.tableOfContentsMode()));
                }
            }
        }
        catch (Exception ex) {
            System.out.println("cos sie zwaliło XD");
            System.out.println(ex.getMessage()); //TODO
        }
    }
}
