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
}
