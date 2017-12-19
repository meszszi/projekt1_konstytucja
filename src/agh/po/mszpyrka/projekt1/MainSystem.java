package agh.po.mszpyrka.projekt1;

import java.io.*;
import java.util.LinkedList;

public class MainSystem {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(
                        new FileReader(
                                new File(args[0] + "uokik.txt")))){

            SourceParser sourceParser = new SourceParser();
            LinkedList<DocLine> list = sourceParser.parseFromReader(reader);

            Contents document = new Contents(list.getFirst());
            document.parse(list, 0);

            DocSearcher searcher = new DocSearcher(document);
            CommandLineParser cliParser = new CommandLineParser(args);
            String[][] paths = cliParser.getAllPaths();

            LinkedList<Contents> contents;
            if(paths.length == 1)
                contents = searcher.getRange(paths[0], paths[0]);

            else
                contents = searcher.getRange(paths[0], paths[1]);

            boolean tableMode = false;
            if(cliParser.getMode() == 1)
                tableMode = true;

            System.out.println(DocPrinter.showContents(contents, tableMode));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage()); //TODO
        }





    }

}
