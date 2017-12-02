package agh.po.mszpyrka.projekt1;

import javax.sound.sampled.Line;

public enum LineType {
    MainHeader,             // H - KONSTYTUCJA RZECZYPOSPOLITEJ POLSKIEJ
    Section,                // S - DZIAŁ XI
    Chapter,                // C - Rozdzial 4.
    Title,                  // T - RZECZPOSPOLITA
    Article,                // A - Art. 123.
    NumberDotPoint,         // D - 4.
    MixedDotPoint,          // P - 3b.
    NumberParenthPoint,     // N - 4)
    MixedParenthPoint,      // M - 16c)
    LetterParenthPoint,     // L - b)
    RegularText,            // R - regular contents not including any headings
    Trash;                  // X - unimportant lines


    /*
     * depth levels in konstytucja.txt:
     *  0 -> MainHeader
     *  1 -> Chapter
     *  2 -> Title
     *  3 -> Article
     *  4 -> NumberDotPoint
     *  5 -> LetterParenthPoint
     *
     *  *first lines need to be parsed separately
     *
     *  depth levels in uokik.txt:
     *  0 -> MainHeader
     *  1 -> Section
     *  2 -> Chapter
     *  3 -> Article
     *  4 -> NumberDotPoint
     *  5 -> MixedDotPoint
     *  6 -> NumberParenthPoint
     *  7 -> MixedParenthPoint
     *  8 -> LetterParenthPoint
     *
     *  *title "USTAWA" at the beginning shouldn't be treated as proper LineType.Title
     *
     *
     *  combined depth levels:
     *  0 -> MainHeader
     *  1 -> Section
     *  2 -> Chapter
     *  3 -> Title
     *  4 -> Article
     *  5 -> NumberDotPoint
     *  6 -> MixedDotPoint
     *  7 -> NumberParenthPoint
     *  8 -> MixedParenthPoint
     *  9 -> LetterParenthPoint
     *
     *  + RegularText
     */

    @Override
    public String toString() {
        switch (this) {
            case MainHeader:
                return "H";

            case Section:
                return "S";

            case Chapter:
                return "C";

            case Title:
                return "T";

            case Article:
                return "A";

            case NumberDotPoint:
                return "D";

            case MixedDotPoint:
                return "P";

            case NumberParenthPoint:
                return "N";

            case MixedParenthPoint:
                return "M";

            case LetterParenthPoint:
                return "L";

            case RegularText:
                return "R";

            case Trash:
                return "X";

            default:
                return null;
        }
    }

    /**
     * gets string's LineType according to its first letter (LineType signature)
     * @param s - string
     * @return - string's LineType
     */

    public static LineType getType (String s) {
        if(s == null)
            return null;

        return getTypeFromSignature(s.charAt(0));
    }

    private static LineType getTypeFromSignature (char signature) {
        switch (signature) {
            case 'H':
                return MainHeader;

            case 'S':
                return Section;

            case 'C':
                return Chapter;

            case 'T':
                return Title;

            case 'A':
                return Article;

            case 'D':
                return NumberDotPoint;

            case 'P':
                return MixedDotPoint;

            case 'N':
                return NumberParenthPoint;

            case 'M':
                return MixedParenthPoint;

            case 'L':
                return LetterParenthPoint;

            case 'R':
                return RegularText;

            default:
                return null;
        }
    }

    /**
     * gets depth level of a lineType according to the hierarchy presented in the comment section above
     * @return depthLevel
     */

    public int getDepthLevel () {
        switch (this) {
            case MainHeader:
                return 0;

            case Section:
                return 1;

            case Chapter:
                return 2;

            case Title:
                return 3;

            case Article:
                return 4;

            case NumberDotPoint:
                return 5;

            case MixedDotPoint:
                return 6;

            case NumberParenthPoint:
                return 7;

            case MixedParenthPoint:
                return 8;

            case LetterParenthPoint:
                return 9;

            case RegularText:
                return 10;

            default:
                return 20;
        }
    }

    /**
     * gets depth level of a String
     * @param s - line to get depth level of
     * @return - depth level
     */

    public static int getDepthLevel(String s) {
        return getDepthLevelFromSignature(s.charAt(0));
    }

    /**
     * gets depth level of a LineType represented by given signature
     * @param signature - character representing LineType
     * @return - depth level of a LineType
     */

    public static int getDepthLevelFromSignature (char signature) {
        switch (signature) {
            case 'H':
                return 0;

            case 'S':
                return 1;

            case 'C':
                return 2;

            case 'T':
                return 3;

            case 'A':
                return 4;

            case 'D':
                return 5;

            case 'P':
                return 6;

            case 'N':
                return 7;

            case 'M':
                return 8;

            case 'L':
                return 9;

            case 'R':
                return 10;

            default:
                return 20;
        }
    }
}
