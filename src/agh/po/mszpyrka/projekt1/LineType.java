package agh.po.mszpyrka.projekt1;

public enum LineType {
    RegularText,            // R - regular contents not including any headings
    Section,                // S - DZIAŁ XI
    Chapter,                // C - Rozdzial 4.
    Title,                  // T - RZECZPOSPOLITA
    Article,                // A - Art. 123.
    NumberDotPoint,         // D - 4.
    MixedDotPoint,          // P - 3b.
    NumberParenthPoint,     // N - 4)
    LetterParenthPoint,     // L - b)
    MixedParenthPoint,      // M - 16c)
    Trash,                  // X - unimportant lines
    MainHeader;             // H - KONSTYTUCJA RZECZYPOSPOLITEJ POLSKIEJ

    /*
     * depth levels in konstytucja.txt:
     *  1 -> Chapter
     *  2 -> Title
     *  3 -> Article
     *  4 -> NumberDotPoint
     *  5 -> LetterParenthPoint
     *
     *  *first lines need to be parsed separately
     *
     *  depth levels in uokik.txt:
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
     *  1 -> Section
     *  2 -> Chapter
     *  3 -> Title
     *  4 -> Article
     *  5 -> NumberDotPoint
     *  6 -> MixedDotPoint
     *  7 -> NumberParenthPoint
     *  8 -> MixedParenthPoint
     *  9 -> LetterParenthPoint
     */

    @Override
    public String toString() {
        switch (this) {

            case RegularText:
                return "R";

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
                return "N";

            case LetterParenthPoint:
                return "L";

            case MixedDotPoint:
                return "P";

            case MixedParenthPoint:
                return "M";

            case Trash:
                return "X";

            case MainHeader:
                return "H";

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
     * gets depth level of a LineType represented by given signature
     * @param signature - character representing LineType
     * @return - depth level of a LineType
     */

    public int getDepthLevelFromSignature (char signature) {
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
