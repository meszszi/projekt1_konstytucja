package agh.po.mszpyrka.projekt1;

import java.io.*;
import java.util.LinkedList;

public class MainSystem {
    public static void main(String[] args) throws IOException {

        try (BufferedReader reader = new BufferedReader(
                        new FileReader(
                                new File(args[0] + "uokik.txt")))){

            LinkedList<String> list = RawTextParser.parseRawText(reader);

            Contents document = new Contents();
            document.parse(list, 0);

            System.out.println(document.toString());

            String[] path = {"DZIAŁ II", "Rozdział 3", "Art. 12.", "5.", "2)"};
            DocViewer d = new DocViewer();
            LinkedList<Contents> xd = d.findContentsNode(path, document, 0);

            if(xd.size() == 0) System.out.println("nie ma takiego");
            else
                for (Contents c : xd) {
                    System.out.println("\n"+c.toString());
                }

        }
        catch (IOException ex) {
            System.out.println(ex + "lol"); //TODO
        }




    }

}
