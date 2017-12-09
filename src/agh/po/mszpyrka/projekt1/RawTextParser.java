package agh.po.mszpyrka.projekt1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Pattern;


/**
 * Class used for parsing source text and creating further usable list of lines,
 * each line is marked with proper LineType sign at the beginning.
 */
public class RawTextParser {
    /**
     * converts raw text from input file to list of ready-to-use lines for further document composing
     * @param reader - BufferedReader reading from input file
     * @return - awesome list of text lines divided into different categories (LineType values)
     */
    public LinkedList<DocLine> parseFromReader(BufferedReader reader) {
        LinkedList<DocLine> list = initialParse(reader);
        deleteWordBreaks(list);
        connectTitlesWithChapters(list);
        setMainHeader(list);
        deleteEmptyLines(list);

        return list;
    }


    /**
     * converts input file to list of strings, each containing max. 1 heading, gets rid of trash lines
     * @param reader - BufferedReader reading from input txt file
     * @return  LinkedList of strings, each one containing lineType signature at the beginning
     */
    private LinkedList<DocLine> initialParse(BufferedReader reader) {

        LinkedList<DocLine> result = new LinkedList<>();

        String nextLine;
        try {
            while ((nextLine = reader.readLine()) != null) {

                LineType lineType;

                while ((lineType = this.getLineType(nextLine)) != null) {

                    nextLine = nextLine.trim();

                    // RegularText and Title lines don't need to be split
                    if(lineType == LineType.RegularText || lineType == LineType.Title) {
                        result.add(new DocLine(lineType, nextLine));
                        nextLine = null;
                    }

                    // Section, Chapter and Article contain heading name that ends at second white space and may be followed by another lineType
                    else if(lineType == LineType.Section || lineType == LineType.Chapter || lineType == LineType.Article) {
                        int split = getWhitespacePosition(nextLine, 2);
                        result.add(new DocLine(lineType, nextLine.substring(0, split)));
                        nextLine = nextLine.substring(split, nextLine.length()).trim();
                    }

                    // Trash lines are not included in output list
                    else if(lineType == LineType.Trash) {
                        nextLine = null;
                    }

                    // other types (all point types) end their name after first whitespace and may be followed by another lineType
                    else {
                        int split = getWhitespacePosition(nextLine, 1);
                        result.add(new DocLine(lineType, nextLine.substring(0, split).trim()));
                        nextLine = nextLine.substring(split, nextLine.length()).trim();
                    }

                }
            }
        }
        catch (IOException ex) {
            System.out.println(ex + " XDD"); //TODO
        }

        return result;
    }


    /**
     * deletes word-breaking hyphens and connects words from consecutive RegularText type lines
     * @param sourceList - list of lines created with initialParse method
     */
    private void deleteWordBreaks (LinkedList<DocLine> sourceList) {

        for(int i = 0; i < sourceList.size() - 1; i++) {

            DocLine line = sourceList.get(i);
            if(line.getType() == LineType.RegularText && Pattern.matches(".*\\p{IsAlphabetic}-$", line.getContents())) {
                DocLine nextLine = sourceList.get(i + 1);
                int split = getWhitespacePosition(nextLine.getContents(), 1);

                line.setContents(line.getContents().substring(0, line.getContents().length() - 1)
                        + nextLine.getContents().substring(0, split));

                nextLine.setContents(nextLine.getContents().substring(split, nextLine.getContents().length()));
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
     * deletes empty lines from sourceList
     * @param sourceList
     */
    private void deleteEmptyLines (LinkedList<DocLine> sourceList) {
        for(int i = 0; i < sourceList.size(); i++)
            while(i < sourceList.size() && sourceList.get(i).isEmpty()) // lines that contain only type signature
                sourceList.remove(i);
    }


    /**
     * tests whether a line is a potential heading beginning
     * @param line - single line from input file
     * @return heading type that matches the beggining of the line (thou the line may not be heading starter, e.g. "1997)."
     */
    private LineType getLineType (String line) {

        if(line == null)
            return null;

        line = line.trim();

        if(line.length() == 0)
            return null;

        if(line.length() < 2 || line.startsWith("©") || isDate(line))
            return LineType.Trash;

        if(line.startsWith("DZIAŁ "))
            return LineType.Section;

        if(line.startsWith("Rozdział "))
            return LineType.Chapter;

        if(Character.isUpperCase(line.charAt(0)) && Character.isUpperCase(line.charAt(1)))  // not Section yet capitalised, so must be Title
            return LineType.Title;

        if(line.startsWith("Art. "))        // ...
            return LineType.Article;

        if(Pattern.matches("^\\d+[a-z]?\\. .*", line))   // pattern: (one or more digits) + (one or zero lowerCase letter) + ". "
            return LineType.NumberDotPoint;

        if(Pattern.matches("^\\d+[a-z]?\\) .*", line))   // pattern: (one or more digits) + (one or zero lowerCase letter) + ". "
            return LineType.NumberParenthPoint;

        if(Pattern.matches("^[a-z]\\) .*", line)) // pattern: (one lowerCase letter) + ") "
            return LineType.LetterParenthPoint;

        return LineType.RegularText;
    }


    /**
     * tests if a string has date-like format (dddd-dd-dd), d -> digit
     * @param line - string to check
     * @return true only if date pattern is matched
     */
    private boolean isDate(String line) {
        return Pattern.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}.*", line);
    }


    /**
     * finds nth string's whitespace's position
     * @param string - source string
     * @param n - number of whitespaces
     * @return - index of nth white space position
     */
    private int getWhitespacePosition(String string, int n) {

        int i;
        for(i = 0; i < string.length(); i++) {
            if (Character.isWhitespace(string.charAt(i))) n--;
            if (n == 0) break;
        }
        return i;
    }


    //private static LinkedList<String> ()
}
