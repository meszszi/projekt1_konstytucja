package agh.po.mszpyrka.projekt1;

import java.util.regex.Pattern;

public class Heading {
    private final LineType lineType;
    private final String title;

    /**
     * creates new Heading object according to given String
     * @param line
     */
    public Heading(DocLine line) {
        this.lineType = line.getType();
        this.title = line.getContents();
    }


    /**
     *
     * @return
     */
    public LineType getType() {
        return this.lineType;
    }

    @Override
    public String toString() {
        return this.title;
    }


    /**
     * checks if given string matches the heading
     * @param h
     * @return
     */
    public boolean matches(String h) {
        if(!this.isRange())
            return h.toUpperCase().equals(this.title.trim().toUpperCase());

        return this.matchesRange(h);
    }


    /**
     * checks if heading title represents a range of headings (e.g. "Art. 123-156.")
     * @return true if title contains [digits]-[digits] pattern
     */
    private boolean isRange () {
        return Pattern.matches("\\D*[0-9]+–[0-9]+\\D*", this.title);
    }


    /**
     * checks if given string matches this.heading in case of heading being a range of contents nodes
     * @param h - String that is checked for matching this.heading
     * @return - true only if beginning parts of both h and this.heading are the same and the rest part of h is a number contained in this.heading range
     */
    private boolean matchesRange(String h) {
        if(h.length() < 1 || h.charAt(h.length() - 1) != '.')
            return false;

        String lowerTitle = this.title.toLowerCase();
        h = h.toLowerCase();
        int numPos;
        for(numPos = 0; numPos < h.length() && numPos < lowerTitle.length() && !Character.isDigit(lowerTitle.charAt(numPos)); numPos++)
            if(h.charAt(numPos) != lowerTitle.charAt(numPos))
                break;

        if(numPos >= h.length() || numPos >= lowerTitle.length())
            return false;

        try {
            int checkedNum = Integer.parseInt(h.substring(numPos, h.length() - 1));
            int hyphenPos = numPos;
            while(hyphenPos < lowerTitle.length() && lowerTitle.charAt(hyphenPos) != '–')
                hyphenPos++;

            int beginNum = Integer.parseInt(lowerTitle.substring(numPos, hyphenPos));
            int endNum = Integer.parseInt(lowerTitle.substring(hyphenPos + 1, lowerTitle.length() - 1));

            return beginNum <= checkedNum && checkedNum <= endNum;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
