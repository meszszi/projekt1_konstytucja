package agh.po.mszpyrka.projekt1;

import java.io.*;
import java.util.LinkedList;

public class MainSystem {
    public static void main(String[] args) throws IOException {

        try {

            BufferedReader reader = new BufferedReader(
                    new FileReader(
                            new File(args[0])
                    )
            );

            LinkedList<String> lista = RawTextParser.parseRawText(reader);
            for(String s : lista) {
                if(s.startsWith("C") || s.startsWith("T"))
                    System.out.println(s);
            }

        }
        catch (IOException ex) {
            System.out.println(ex + "lol");
        }

    }
}
