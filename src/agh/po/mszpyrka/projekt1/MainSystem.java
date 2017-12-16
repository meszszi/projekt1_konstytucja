package agh.po.mszpyrka.projekt1;

import javax.print.Doc;
import java.io.*;
import java.util.LinkedList;

public class MainSystem {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(
                        new FileReader(
                                new File(args[0] + "konstytucja.txt")))){


            //System.out.println(Pattern.matches("\\p{IsAlphabetic}", "5"));

            SourceParser sourceParser = new SourceParser();
            LinkedList<DocLine> list = sourceParser.parseFromReader(reader);

            //for(DocLine d : list)
            //    System.out.println(d.getType().toString() + ": " + d.getContents());

            Contents document = new Contents(list.getFirst());
            document.parse(list, 0);

            //System.out.println(document.getTableOfContents(0, 3));
            //System.out.println(document.getFullContents(0));
            //String[] path1 = {"dział ix", "art. 120."};
            //String[] path2 = {"art. 114."};
            DocViewer d = new DocViewer(document);
            CommandLineParser cliParser = new CommandLineParser(args);
            String[][] paths = cliParser.getAllPaths();
            System.out.println(d.showContetns(paths[0], cliParser.getMode()));
            //System.out.println(d.showRange(path1, path2, mode));

        }
        catch (Exception ex) {
            System.out.println(ex.getMessage()); //TODO
        }





    }

}
