package agh.po.mszpyrka.projekt1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Pattern;


/**
 * class used for parsing source text and creating further usable list of DocLines
 */
public class SourceParser {

    /**
     * converts raw text from input file to list of ready-to-use lines for further document composing
     * @param reader - BufferedReader reading from input file
     * @return - awesome list of text lines divided into different categories (LineType values)
     */
    public LinkedList<DocLine> parseFromReader(BufferedReader reader) throws IOException{
        LinkedList<DocLine> list = initialParse(reader);
        deleteTrash(list);
        deleteWordBreaks(list);
        connectTitlesWithChapters(list);
        setMainHeader(list);

        return list;
    }


    /**
     * converts input file to list of properly set DocLines
     * @param reader - BufferedReader reading from input txt file
     * @return  LinkedList of DocLines
     */
    private LinkedList<DocLine> initialParse(BufferedReader reader) throws IOException{

        LinkedList<DocLine> result = new LinkedList<>();

        String nextLine;

        while ((nextLine = reader.readLine()) != null) {

            String[] tab = RawTextParser.splitToDifferentLineTypes(nextLine);

            for (String s : tab)
                result.add(new DocLine(RawTextParser.getLineType(s), s));
        }

        return result;
    }


    /**
     * connects words from consecutive RegularText type lines if there is a hyphen at the end of the first line
     * @param sourceList - list of lines created with initialParse method
     */
    private void deleteWordBreaks (LinkedList<DocLine> sourceList) {

        for(int i = 0; i < sourceList.size() - 1; i++) {

            DocLine line = sourceList.get(i);
            if(line.getType() == LineType.RegularText && Pattern.matches(".*\\p{IsAlphabetic}-$", line.getContents())) {
                RawTextParser.deleteWordBreak(line, sourceList.get(i + 1));

                if(sourceList.get(i + 1).isEmpty())
                    sourceList.remove(i + 1);
            }
        }
    }


    /**
     * searches for Title type lines after Chapter type lines, if one is found it's type is changed to RegularText
     * @param sourceList - list of lines parsed with initialParse method
     */
    private void connectTitlesWithChapters (LinkedList<DocLine> sourceList) {
        for(int i = 1; i < sourceList.size(); i++) {

            if(sourceList.get(i).getType() == LineType.Title && sourceList.get(i - 1).getType() == LineType.Chapter)
                sourceList.get(i).setType(LineType.RegularText);
        }
    }


    /**
     * creates MainHeader from document opening title-type lines
     * @param sourceList - list of DocLines parsed with initialParse method
     */
    private void setMainHeader (LinkedList<DocLine> sourceList) {
        while(sourceList.size() > 0 && !(sourceList.getFirst().getType() == LineType.Title))  // firstly removes all contents above the first Title line
            sourceList.removeFirst();

        DocLine header = new DocLine(LineType.MainHeader, "");
        while(sourceList.size() > 0 && sourceList.getFirst().getType() == LineType.Title) { // removes all Title type lines and merges them into single Header line
            DocLine tmp = sourceList.removeFirst();
            header.setContents(header.getContents() + " " + tmp.getContents());
        }

        sourceList.addFirst(header);
    }


    /**
     * deletes Trash type lines from sourceList
     * @param sourceList - list of DocLines parsed with initialParse method
     */
    private void deleteTrash (LinkedList<DocLine> sourceList) {
        for(int i = 0; i < sourceList.size(); i++)
            while(i < sourceList.size() && (sourceList.get(i).getType() == LineType.Trash))
                sourceList.remove(i);
    }
}
