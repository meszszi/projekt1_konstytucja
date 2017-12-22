package agh.po.mszpyrka.projekt1;

public enum LineType {

    MAIN_HEADER,             // KONSTYTUCJA RZECZYPOSPOLITEJ POLSKIEJ
    SECTION,                // DZIAŁ XI
    CHAPTER,                // Rozdzial 4.
    TITLE,                  // RZECZPOSPOLITA
    ARTICLE,                // Art. 123.
    NUMBER_DOT_POINT,         // 4. lub 4a.
    NUMBER_PARENTH_POINT,     // 4) or 4a)
    LETTER_PARENTH_POINT,     // b)
    REGULAR_TEXT,            // regular contents not including any headings
    TRASH;                  // unimportant lines


    /*
     * depth levels in konstytucja.txt:
     *  0 -> MAIN_HEADER
     *  1 -> CHAPTER
     *  2 -> TITLE
     *  3 -> ARTICLE
     *  4 -> NUMBER_DOT_POINT
     *  5 -> LETTER_PARENTH_POINT
     *
     *
     *  depth levels in uokik.txt:
     *  0 -> MAIN_HEADER
     *  1 -> SECTION
     *  2 -> CHAPTER
     *  3 -> ARTICLE
     *  4 -> NUMBER_DOT_POINT
     *  5 -> NUMBER_PARENTH_POINT
     *  6 -> LETTER_PARENTH_POINT
     *
     *
     *  combined depth levels:
     *  0 -> MAIN_HEADER
     *  1 -> SECTION
     *  2 -> CHAPTER
     *  3 -> TITLE
     *  4 -> ARTICLE
     *  5 -> NUMBER_DOT_POINT
     *  6 -> NUMBER_PARENTH_POINT
     *  7 -> LETTER_PARENTH_POINT
     *
     *  + REGULAR_TEXT
     */


    /**
     * Gets depth level of a lineType according to the hierarchy presented in the comment section above.
     * @return depthLevel
     */
    public int getDepthLevel () {
        return this.ordinal();
    }
}
