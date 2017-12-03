package agh.po.mszpyrka.projekt1;

public enum LineType {

    MainHeader,             // H - KONSTYTUCJA RZECZYPOSPOLITEJ POLSKIEJ
    Section,                // S - DZIAŁ XI
    Chapter,                // C - Rozdzial 4.
    Title,                  // T - RZECZPOSPOLITA
    Article,                // A - Art. 123.
    NumberDotPoint,         // D - 4. lub 4a.
    NumberParenthPoint,     // P - 4) lub 4a)
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
     *
     *  depth levels in uokik.txt:
     *  0 -> MainHeader
     *  1 -> Section
     *  2 -> Chapter
     *  3 -> Article
     *  4 -> NumberDotPoint
     *  5 -> NumberParenthPoint
     *  6 -> LetterParenthPoint
     *
     *
     *  combined depth levels:
     *  0 -> MainHeader
     *  1 -> Section
     *  2 -> Chapter
     *  3 -> Title
     *  4 -> Article
     *  5 -> NumberDotPoint
     *  6 -> NumberParenthPoint
     *  7 -> LetterParenthPoint
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

            case NumberParenthPoint:
                return "P";

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
                return NumberParenthPoint;

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

            case NumberParenthPoint:
                return 6;

            case LetterParenthPoint:
                return 7;

            case RegularText:
                return 8;

            default:
                return 9;
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
        return getTypeFromSignature(signature).getDepthLevel();
    }
}
