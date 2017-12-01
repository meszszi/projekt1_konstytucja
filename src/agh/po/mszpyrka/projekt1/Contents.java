package agh.po.mszpyrka.projekt1;

import java.util.LinkedList;

public class Contents {
    private final Heading heading;
    private String mainContents;
    private LinkedList<Contents> subcontents;

    public Contents(Heading heading) {
        this.heading = heading;
    }
}
