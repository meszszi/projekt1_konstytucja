package agh.po.mszpyrka.projekt1;

public class Heading {
    private final LineType lineType;
    private final String title;

    /**
     * creates new Heading object according to given String
     * @param title - String with LineType signature at the beginning
     */

    public Heading(String title) {
        this.lineType = LineType.getType(title);
        this.title = title.substring(1, title.length());
    }

    public LineType getType() {
        return this.lineType;
    }

    @Override
    public String toString() {
        return title;
    }


    /**
     * checks if given string matches the heading
     * @param h
     * @return
     */

    public boolean matches(String h) {
        return h.startsWith(this.title.trim());
    }

    /**
     * takes in a string with document contents path and deletes beginning part
     * (it is assumed that input string matches this.heading.title)
     * @param h
     * @return
     */

    public String cutHeadingOff(String h) {

        String[] tmp = this.title.split(" ");
        String compressed = "";
        for (String s : tmp) {
            compressed = compressed + s;
        }

        return h.substring(compressed.length(), h.length());
    }
}
