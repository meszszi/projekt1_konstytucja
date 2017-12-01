package agh.po.mszpyrka.projekt1;

public class Heading {
    private final LineType lineType;
    private final String title;

    public Heading(LineType lineType, String title) {
        this.lineType = lineType;
        this.title = title;
    }

    public LineType getType() {
        return this.lineType;
    }

    @Override
    public String toString() {
        return title;
    }
}
