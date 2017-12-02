package agh.po.mszpyrka.projekt1;

import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class MainSystem {
    public static void main(String[] args) throws IOException {

        try (BufferedReader reader = new BufferedReader(
                        new FileReader(
                                new File(args[0] + "uokik.txt")))){

            LinkedList<String> list = RawTextParser.parseRawText(reader);

            Contents document = new Contents();
            document.parse(list, 0);

            System.out.println(document.toString());
        }
        catch (IOException ex) {
            System.out.println(ex + "lol");
        }




    }

}
