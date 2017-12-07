package agh.po.mszpyrka.projekt1;

import java.io.*;
import java.util.LinkedList;

public class MainSystem {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(
                        new FileReader(
                                new File(args[0] + "konstytucja.txt")))){

            int mode = CommandLineParser.getMode(args);

            LinkedList<String> list = RawTextParser.parseRawText(reader);

            Contents document = new Contents(list.getFirst());
            document.parse(list, 0);

            //System.out.println(document.getTableOfContents(0, 3));
            System.out.println(document.getFullContents(0));
            String[] path1 = {"art. 10."};
            String[] path2 = {"art. 50."};
            DocViewer d = new DocViewer(document);
            //System.out.println(d.showContetns(path1, mode));
            //System.out.println(d.showRange(path1, path2, mode));

        }
        catch (Exception ex) {
            System.out.println(ex.getMessage()); //TODO
        }





    }

}
