package agh.po.mszpyrka.projekt1;

import java.io.*;
import java.util.LinkedList;

public class MainSystem {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(
                        new FileReader(
                                new File(args[0] + "uokik.txt")))){

            int mode = CommandLineParser.getMode(args);

            LinkedList<String> list = RawTextParser.parseRawText(reader);

            Contents document = new Contents(list.getFirst());
            document.parse(list, 0);

            //System.out.println(document.getTableOfContents(0, 3));
            System.out.println(document.getFullContents(0));
            String[] path = {"DZIAŁ II", "Art. 10.", "4.", "3)"};
            DocViewer d = new DocViewer(document);
            LinkedList<Contents> xd = d.findMatchingNode(path, document, 0);

            if(xd.size() == 0) System.out.println("nie ma takiego");
            else
                for (Contents c : xd) {
                    System.out.println("\n" + c.getPathString());
                    System.out.println("\n"+c.getFullContents(0));
                }

        }
        catch (IOException ex) {
            System.out.println("lol"); //TODO
            //throw ex;
        }




    }

}
