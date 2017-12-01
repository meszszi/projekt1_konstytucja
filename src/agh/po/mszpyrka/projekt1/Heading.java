package agh.po.mszpyrka.projekt1;

public class Heading {
    public final LineType lineType;
    private final String title;

    public Heading(LineType lineType, String title) {
        this.lineType = lineType;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
