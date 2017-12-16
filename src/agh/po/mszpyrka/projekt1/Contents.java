package agh.po.mszpyrka.projekt1;


import sun.awt.image.ImageWatched;

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
     * @param indent
     * @return
     */
    private String toString(int indent) {

        String strIndent = getIndent(indent);

        String ans = strIndent + this.heading.toString();

        if(this.heading.getType() == LineType.MainHeader) {
            for(String s : mainContents)
                ans += "\n" + strIndent + s;

            return ans + "\n";
        }

        if(this.mainContents.size() > 0) {

            if(this.heading.getType() == LineType.Chapter)
                ans += ":";

            else if(this.heading.getType() == LineType.Section)
                ans += ")";

            ans += " " + this.mainContents.getFirst() + "\n";
        }

        for(int i = 1; i < this.mainContents.size(); i++)
            ans += strIndent + "  " + this.mainContents.get(i) + "\n";

        if(this.mainContents.size() == 0)
            ans += "\n";

        return ans;
    }


    /**
     * returns a string that shows complete node's heading-based path from the main document node
     * @return - path in String format
     */
    public String getPathString() {
        if(this.parent == this)
            return "";

        String ans = this.parent.getPathString();

        if(ans.length() > 0)
            ans += ", ";

        return ans + this.parent.heading.toString();
    }


    /**
     * returns only the first line of contents
     * @param indent - number of white spaces that line indent be followed by
     * @return - properly formatted line
     */
    private String getHighlights(int indent) {
        String ans = getIndent(indent) + this.heading.toString();

        if(this.heading.getType() == LineType.MainHeader) // doesn't insclude any text after heading in case of MainHeader
            return ans;

        if(this.mainContents.size() > 0)                  // if there are any mainContents -> includes the first line
            ans += " : " + this.mainContents.getFirst();

        if(this.mainContents.size() > 1)                  // if there is more -> adds "(...)"
            ans += "(...)";

        return ans;
    }


    /**
     * gets whole node's table of contents (each heading is presented in highlight style)
     * @param indent
     * @param maxDepth
     * @return
     */
    public String getTableOfContents(int indent, int maxDepth) {

        if(maxDepth < this.heading.getType().getDepthLevel()) return "";

        String tableOfContents = this.getHighlights(indent) + "\n";

        for(Contents c : this.subcontents)
            tableOfContents += c.getTableOfContents(indent + 1, maxDepth);

        return tableOfContents;
    }


    /**
     * gets node's full contents (including all subConents)
     * @param indent
     * @return
     */
    public String getFullContents(int indent) {

        String fullContents = this.toString(indent);

        if(this.heading.getType() == LineType.Chapter
                || this.heading.getType() == LineType.Section
                || this.heading.getType() == LineType.MainHeader
                || this.heading.getType() == LineType.Title)
            fullContents += "\n";

        for(Contents c : this.subcontents)
            fullContents += c.getFullContents(indent + 1);

        if(this.heading.getType() == LineType.Article)
            fullContents += "\n";

        return fullContents;
    }


    /**
     * returns indent String (spaces only)
     * @param indent
     * @return
     */
    private String getIndent(int indent) {
        String strIndent = "";
        while(indent-- > 0) strIndent += "  ";

        return strIndent;
    }
}
