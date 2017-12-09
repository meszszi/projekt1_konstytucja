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
}
