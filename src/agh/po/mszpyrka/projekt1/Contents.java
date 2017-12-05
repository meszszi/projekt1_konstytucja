package agh.po.mszpyrka.projekt1;

import java.util.LinkedList;

public class Contents {
    private Heading heading;
    private LinkedList<String> mainContents;
    private LinkedList<Contents> subcontents;

    public Contents() {
        mainContents = new LinkedList<>();
        subcontents = new LinkedList<>();
    }

    /**
     * parses sourceList into Contents type object hierarchy
     * @param sourceList - list of Strings to parse
     * @param begin - index of the element in sourceList to start parsing with
     * @return - index of the sourceList element that the parsing ended on (points to first line not parsed yet)
     */
    public int parse(LinkedList<String> sourceList, int begin) {
        int iterator = begin + 1;

        this.heading = new Heading(sourceList.get(begin));

        while(iterator < sourceList.size() && LineType.getType(sourceList.get(iterator)) == LineType.RegularText) {
            this.extendMainContents(sourceList.get(iterator));
            iterator++;
        }

        while(iterator < sourceList.size() && this.heading.getType().getDepthLevel() < LineType.getDepthLevel(sourceList.get(iterator))) {
            Contents subc = new Contents();
            iterator = subc.parse(sourceList, iterator);
            this.extendSubcontents(subc);
        }

        return iterator;
    }

    /**
     * adds next line to mainContents
     * @param s
     */
    public void extendMainContents(String s) {
        this.mainContents.add(s.substring(1, s.length()));
    }

    /**
     * adds Contents object to subcontents list
     * @param c
     */
    public void extendSubcontents(Contents c) {
        this.subcontents.add(c);
    }


    /**
     * gets subcontents list
     * @return
     */
    public LinkedList<Contents> getSubcontents() {
        return subcontents;
    }

    public Heading getHeading() {
        return heading;
    }

    @Override
    public String toString() {

        String indent = "";
        for(int i = 0; i < this.heading.getType().getDepthLevel(); i++)
            indent = indent + " ";

        String str = indent + this.heading.toString()+" ";

        for(String s : mainContents)
            str = str + s + "\n" + indent;

        if(!str.endsWith("\n"))
            str = str + "\n";


        for(Contents c : subcontents)
            str = str + c.toString();

        return str;

    }
}
