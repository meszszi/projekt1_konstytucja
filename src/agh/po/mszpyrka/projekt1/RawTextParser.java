package agh.po.mszpyrka.projekt1;

import java.util.LinkedList;
import java.util.regex.Pattern;


/**
 * class used for operating on single lines from source file
 */
public class RawTextParser {

    /**
     * determins a line type of a single line String
     * @param line - single line from input file
     * @return LineType that matches given String contents
     */
    public static LineType getLineType(String line) {

        if(line == null || line.trim().length() == 0)
            return null;

        line = line.trim();

        if(line.length() < 2 || line.startsWith("©") || isDate(line))
            return LineType.Trash;

        if(line.startsWith("DZIAŁ "))
            return LineType.Section;

        if(line.startsWith("Rozdział "))
            return LineType.Chapter;

        if(Character.isUpperCase(line.charAt(0)) && Character.isUpperCase(line.charAt(1)))  // not Section yet capitalised, so must be Title
            return LineType.Title;

        if(line.startsWith("Art. "))
            return LineType.Article;

        if(Pattern.matches("^\\d+[a-z]?\\..*", line))   // pattern: (one or more digits) + (one or zero lowerCase letter) + ". "
            return LineType.NumberDotPoint;

        if(Pattern.matches("^\\d+[a-z]?\\).*", line))   // pattern: (one or more digits) + (one or zero lowerCase letter) + ". "
            return LineType.NumberParenthPoint;

        if(Pattern.matches("^[a-z]\\).*", line)) // pattern: (one lowerCase letter) + ") "
            return LineType.LetterParenthPoint;

        return LineType.RegularText;
    }


    /**
     * connects word parts from two consecutive lines when there is a word break
     * @param first - line that contains the first part of the word and hyphen at the end
     * @param second - line that contains the second part of the word
     */
    public static void deleteWordBreak(DocLine first, DocLine second) {

        int split = getWhitespacePosition(second.getContents(), 1);

        first.setContents(first.getContents().substring(0, first.getContents().length() - 1)
                    + second.getContents().substring(0, split));

        second.setContents(second.getContents().substring(split, second.getContents().length()));
    }


    /**
     * splits given String into array of Strings, each containing only one line type
     * @param s - String from source file
     * @return - array of Strings
     */
    public static String[] splitToDifferentLineTypes(String s) {

        LinkedList<String> ans = new LinkedList<>();

        LineType type;

        while((type = getLineType(s = s.trim())) != null) {

            int split;

            // RegularText, Title and Trash lines don't need to be split at all
            if(type == LineType.RegularText || type == LineType.Title || type == LineType.Trash)
                split = s.length();

            // Section, Chapter and Article lines need to be split at the second whitespace
            else if(type == LineType.Section || type == LineType.Chapter || type == LineType.Article)
                split = getWhitespacePosition(s, 2);

            // all *Point types should be split at the first whitespace
            else
                split = getWhitespacePosition(s, 1);

            ans.add(s.substring(0, split).trim());
            s = s.substring(split, s.length());
        }

        return ans.toArray(new String[ans.size()]);
    }


    /**
     * tests if a string has date-like format (dddd-dd-dd), d -> digit
     * @param line - string to check
     * @return true only if date pattern is matched
     */
    private static boolean isDate(String line) {
        return Pattern.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}.*", line);
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
