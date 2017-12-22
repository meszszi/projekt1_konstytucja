package agh.po.mszpyrka.projekt1;

public enum LineType {

    MainHeader,             // KONSTYTUCJA RZECZYPOSPOLITEJ POLSKIEJ
    Section,                // DZIAŁ XI
    Chapter,                // Rozdzial 4.
    Title,                  // RZECZPOSPOLITA
    Article,                // Art. 123.
    NumberDotPoint,         // 4. lub 4a.
    NumberParenthPoint,     // 4) or 4a)
    LetterParenthPoint,     // b)
    RegularText,            // regular contents not including any headings
    Trash;                  // unimportant lines


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
     * Gets depth level of a lineType according to the hierarchy presented in the comment section above.
     * @return depthLevel
     */
    public int getDepthLevel () {
        return this.ordinal();
    }
}
