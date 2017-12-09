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
        return h.toUpperCase().equals(this.title.trim().toUpperCase());
    }


    /**
     * checks if heading title represents a range of headings (e.g. "Art. 123-156.")
     * @return true if title contains [digits]-[digits] pattern
     */
    public boolean isRange () {
        return Pattern.matches(".*[0-9]+–[0-9]+.*", this.title);
    }


    //public int getFirstInRange
}
