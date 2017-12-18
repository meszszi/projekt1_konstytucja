package agh.po.mszpyrka.projekt1;

import java.util.LinkedList;

public class Contents {
    private final Heading heading;
    private final Contents parent;
    private LinkedList<String> mainContents;
    private LinkedList<Contents> subcontents;

    public Contents(DocLine line) {
        mainContents = new LinkedList<>();
        subcontents = new LinkedList<>();
        this.heading = new Heading(line);
        this.parent = this;
    }

    public Contents(Contents parent, DocLine line) {
        mainContents = new LinkedList<>();
        subcontents = new LinkedList<>();
        this.heading = new Heading(line);
        this.parent = parent;
    }


    /**
     * gets subcontents list
     * @return - subcontents
     */
    public LinkedList<Contents> getSubcontents() {
        return this.subcontents;
    }


    /**
     * gets heading
     * @return - heading
     */
    public Heading getHeading() {
        return this.heading;
    }


    /**
     * gets parent
     * @return - parent
     */
    public Contents getParent() {
        return this.parent;
    }


    /**
     * parses sourceList into Contents type object hierarchy
     * @param sourceList - list of Strings to parse
     * @param begin - index of the element in sourceList to start parsing with
     * @return - index of the sourceList element that the parsing ended on (points to first line not parsed yet)
     */
    public int parse(LinkedList<DocLine> sourceList, int begin) {

        int iterator = begin + 1;

        while(iterator < sourceList.size() && sourceList.get(iterator).getType() == LineType.RegularText) {
            this.extendMainContents(sourceList.get(iterator).getContents());
            iterator++;
        }

        while(iterator < sourceList.size() && this.heading.getType().getDepthLevel() < sourceList.get(iterator).getType().getDepthLevel()) {
            Contents subc = new Contents(this, sourceList.get(iterator));
            iterator = subc.parse(sourceList, iterator);
            this.extendSubcontents(subc);
        }

        return iterator;
    }


    /**
     * checks if given node lays on path from 'this' to root node in Contents tree
     * @param c
     * @return
     */
    public boolean isChildOf(Contents c) {
        if(this.parent == c || c == this)
            return true;

        if(this.parent == this)
            return false;

        return this.parent.isChildOf(c);
    }


    /**
     * adds next line to mainContents
     * @param s - line to add to mainContents list
     */
    private void extendMainContents(String s) {
        this.mainContents.add(s);
    }


    /**
     * adds Contents object to subcontents list
     * @param c - node to add to subcontents list
     */
    private void extendSubcontents(Contents c) {
        this.subcontents.add(c);
    }


    /**
     * returns properly formatted text for single Contents node (doesn't include any subContents)
     * @return String array, each element containing single line
     */
    public LinkedList<String> mainContentsToStringList() {

        LinkedList<String> result = new LinkedList<>();

        if(this.heading.getType() == LineType.MainHeader || this.mainContents.size() == 0) {
            result.add(this.heading.toString());
            result.addAll(this.mainContents);

            return result;
        }

        String tmp = "";

        if(this.heading.getType() == LineType.Chapter)
            tmp = ":";

        else if(this.heading.getType() == LineType.Section)
            tmp = ")";

        result.add(this.heading.toString() + tmp + " " + this.mainContents.getFirst());

        for(int i = 1; i < this.mainContents.size(); i++)
            result.add(" " + this.mainContents.get(i));

        return result;
    }


    /**
     * formats node's contents for table of contents mode (heading + first line of mainContents is selected)
     * @return
     */
    public String getHighlights() {
        String ans = this.heading.toString();

        if(this.heading.getType() == LineType.MainHeader) // doesn't insclude any text after heading in case of MainHeader
            return ans;

        if(this.mainContents.size() > 0)                  // if there are any mainContents -> includes the first line
            ans += " ~ " + this.mainContents.getFirst();

        if(this.mainContents.size() > 1)                  // if there is more -> adds "(...)"
            ans += "(...)";

        return ans;
    }
}
