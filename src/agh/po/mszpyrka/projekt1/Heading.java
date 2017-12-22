package agh.po.mszpyrka.projekt1;

import java.util.regex.Pattern;

public class Heading {
    private final LineType lineType;
    private final String title;

    public Heading(DocLine line) {
        this.lineType = line.getType();
        this.title = line.getContents();
    }

    public LineType getType() {
        return this.lineType;
    }

    @Override
    public String toString() {
        return this.title;
    }

    /**
     * checks if heading title represents a range of headings (e.g. "Art. 123-156.")
     * @return true if title contains [digits]-[digits] pattern
     */
    public boolean isRange () {
        return Pattern.matches("\\D*[0-9]+–[0-9]+\\D*", this.title);
    }
}
