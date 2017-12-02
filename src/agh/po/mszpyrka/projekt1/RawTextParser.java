package agh.po.mszpyrka.projekt1;

import sun.awt.image.ImageWatched;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Class used for parsing source text and creating further usable list of lines,
 * each line is marked with proper LineType sign at the beginning.
 */

class RawTextParser {

    /**
     * converts raw text from input file to list of ready-to-use lines for further document composing
     * @param reader - BufferedReader reading from input file
     * @return - awesome list of text lines divided into different categories (LineType values)
     */

    static LinkedList<String> parseRawText(BufferedReader reader) {
        LinkedList<String> list = initialParse(reader);
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

    private static LinkedList<String> initialParse(BufferedReader reader) {

        LinkedList<String> result = new LinkedList<>();

        String nextLine;
        try {
            while ((nextLine = reader.readLine()) != null) {

                LineType lineType;

                while ((lineType = RawTextParser.getLineType(nextLine)) != null) {

                    String signature = lineType.toString();

                    nextLine = nextLine.trim();

                    // RegularText and Title lines needn't being splitted
                    if(lineType == LineType.RegularText || lineType == LineType.Title) {
                        result.add((signature + nextLine).trim());
                        nextLine = null;
                    }

                    // Section, Chapter and Article contain heading name that ends at second white space and may be followed by another lineType
                    else if(lineType == LineType.Section || lineType == LineType.Chapter || lineType == LineType.Article) {
                        int split = getWhitespacePosition(nextLine, 2);
                        result.add((signature + nextLine.substring(0, split)).trim());
                        nextLine = nextLine.substring(split, nextLine.length()).trim();
                    }

                    // Trash lines are not included in output list
                    else if(lineType == LineType.Trash) {
                        nextLine = null;
                    }

                    // other types (all point types) end their name after first whitespace and may be followed by another lineType
                    else {
                        int split = getWhitespacePosition(nextLine, 1);
                        result.add((signature + nextLine.substring(0, split)).trim());
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

    private static void deleteWordBreaks (LinkedList<String> sourceList) {

        for(int i = 0; i < sourceList.size() - 1; i++) {

            String str = sourceList.get(i);
            if(LineType.getType(str) == LineType.RegularText && str.charAt(str.length() - 1) == '-' && Character.isLetter(str.charAt(str.length() - 2))) {

                String next = sourceList.get(i + 1);
                int split = getWhitespacePosition(next, 1);
                str = str.substring(0, str.length()-1) + next.substring(1, split).trim();
                next = next.charAt(0) + (next.substring(split, next.length())).trim();

                sourceList.set(i, str);
                sourceList.set(i + 1, next);
            }
        }
    }


    /**
     * searches for Title type lines after Chapter type lines, if one is found it's type is changed to RegularText to connect it with Chapter
     * @param sourceList - list of lines parsed with initialParse method
     */

    private static void connectTitlesWithChapters (LinkedList<String> sourceList) {
        for(int i = 1; i < sourceList.size(); i++) {

            String str = sourceList.get(i);
            if(str.charAt(0) == 'T') {

                String previous = sourceList.get(i - 1);
                if(previous.charAt(0) == 'C') {
                    str = "R" + str.substring(1, str.length());
                    sourceList.set(i, str);
                }
            }
        }
    }

    /**
     * sets firts capitalised lines a MainHeader lineType
     * @param sourceList - list of String parsed with initialParse method
     */

    private static void setMainHeader (LinkedList<String> sourceList) {
        while(sourceList.size() > 0 && !sourceList.getFirst().startsWith("T"))  // firstly removes all contents above the mainHeader
            sourceList.removeFirst();

        String header = "H";
        while(sourceList.size() > 0 && sourceList.getFirst().startsWith("T")) { // removes all Title type lines and merges them into single Header line
            String tmp = sourceList.removeFirst();
            header = header + tmp.substring(1, tmp.length()) + " ";
        }

        sourceList.addFirst(header.trim());
    }

    /**
     * deletes empty lines from sourceList
     * @param sourceList
     */

    private static void deleteEmptyLines (LinkedList<String> sourceList) {
        for(int i = 0; i < sourceList.size(); i++)
            while(i < sourceList.size() && sourceList.get(i).length() == 1) // lines that contain only type signature
                sourceList.remove(i);
    }

    /**
     * tests whether a line is a potential heading beginning
     * @param line - single line from input file
     * @return heading type that matches the beggining of the line (thou the line may not be heading starter, e.g. "1997)."
     */

    private static LineType getLineType (String line) {

        if(line == null)
            return null;

        line = line.trim();

        if(line.length() == 0)
            return null;

        if(line.length() < 2 || line.startsWith("©") || isDate(line))  // line too short or containing unimportant text
            return LineType.Trash;       // also at least two characters will be needed for some following statements

        if(line.startsWith("DZIAŁ "))       // quite obvious
            return LineType.Section;

        if(line.startsWith("Rozdział "))    // not surprising at all
            return LineType.Chapter;

        if(Character.isUpperCase(line.charAt(0)) && Character.isUpperCase(line.charAt(1)))  // not Section yet capitalised, so must be Title
            return LineType.Title;

        if(line.startsWith("Art. "))        // ...
            return LineType.Article;


        // only possible headings not covered yet match the pattern: {digit}, {letter}, "." | ")", " "

        // sets pointer to index after last digit
        int numEnd = 0;
        while(line.length() > numEnd && Character.isDigit(line.charAt(numEnd)))
            numEnd++;

        // sets pointer to index after last letter
        int letterEnd = numEnd;
        while(line.length() > letterEnd && Character.isLowerCase(line.charAt(letterEnd)))
            letterEnd++;


        // checks if line starts with some (more than zero) digits + letters (zero or more) and ends with ". "
        if((numEnd > 0) && (line.length() > (letterEnd + 1))
                && (line.charAt(letterEnd) == '.') && (line.charAt(letterEnd + 1) == ' ')) {

            if(letterEnd == numEnd)
                return LineType.NumberDotPoint;

            return LineType.MixedDotPoint;
        }



        // checks only lines that start with some (maybe zero) digits followed by some (or none) letters, followed by ") "

        if(line.length() > (letterEnd + 1) && line.charAt(letterEnd) == ')' && line.charAt(letterEnd + 1) == ' ') {
            if(numEnd > 0 && letterEnd == numEnd)
                return LineType.NumberParenthPoint;

            if(numEnd == 0 && letterEnd > numEnd)
                return LineType.LetterParenthPoint;

            if(letterEnd > 0)
                return LineType.MixedParenthPoint;
        }

        return LineType.RegularText;
    }


    /**
     * tests if a string has date-like format (dddd-dd-dd), d -> digit
     * @param line - string to check
     * @return true only if date pattern is matched
     */

    private static boolean isDate(String line) {

        String dateString = "dddd-dd-dd";

        if(line.length() != dateString.length())
            return false;

        for(int i = 0; i < dateString.length(); i++) {
            switch (dateString.charAt(i)) {
                case 'd':
                    if(!Character.isDigit(line.charAt(i))) return false;
                    break;
                default:
                    if(line.charAt(i) != '-') return false;
            }
        }

        return true;
    }


    /**
     * finds nth string's whitespace's position
     * @param string - source string
     * @param n - number of whitespaces
     * @return - index of nth white space position
     */

    private static int getWhitespacePosition(String string, int n) {

        int i;
        for(i = 0; i < string.length(); i++) {
            if (Character.isWhitespace(string.charAt(i))) n--;
            if (n == 0) break;
        }
        return i;
    }
}
