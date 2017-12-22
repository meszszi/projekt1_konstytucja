package agh.po.mszpyrka.projekt1;

import java.util.LinkedList;

public class Contents {
    private final Heading heading;
    private final Contents parent;
    private final DocumentPath documentPath;
    private LinkedList<String> mainContents;
    private LinkedList<Contents> subcontents;

    public Contents(DocLine line) {
        mainContents = new LinkedList<>();
        subcontents = new LinkedList<>();
        this.heading = new Heading(line);
        this.parent = this;
        this.documentPath = new DocumentPath(this.heading.toString());
    }

    public Contents(Contents parent, DocLine line) {
        mainContents = new LinkedList<>();
        subcontents = new LinkedList<>();
        this.heading = new Heading(line);
        this.parent = parent;
        this.documentPath = this.parent.getDocumentPath().extend(this.heading.toString());
    }


    /**
     * gets subcontents list
     * @return - subcontents
     */
    public LinkedList<Contents> getSubcontents() {
        return this.subcontents;
    }


    /**
     * gets mainContents
     * @return - mainContents
     */
    public LinkedList<String> getMainContents() { return this.mainContents; }


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
     * gets documentPath
     * @return - documentPath
     */
    public DocumentPath getDocumentPath() {
        return this.documentPath;
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
            if(!subc.getHeading().isRange())    // ignores range-type nodes
                this.extendSubcontents(subc);
        }

        return iterator;
    }


    /**
     * checks if given node lays on path from 'this' to root node in Contents tree
     * @param c - node that may be potential father of 'this'
     * @return - true only 'this' is a child of c (not necessarily direct child)
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
}
