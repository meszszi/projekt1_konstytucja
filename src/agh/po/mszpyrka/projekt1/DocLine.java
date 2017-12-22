package agh.po.mszpyrka.projekt1;

/**
 * Class that represents single line from source file.
 */
public class DocLine {
    private LineType type;
    private String contents;

    public DocLine(LineType type, String contents) {
        this.type = type;
        this.contents = contents.trim();
    }

    public LineType getType() {
        return this.type;
    }

    public String getContents() {
        return this.contents;
    }

    public void setType(LineType type) {
        this.type = type;
    }

    public void setContents(String contents) {
        this.contents = contents.trim();
    }

    public boolean isEmpty() {
        return this.contents.length() == 0;
    }

    @Override
    public String toString() {
        return this.getContents();
    }
}
