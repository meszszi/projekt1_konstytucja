package agh.po.mszpyrka.projekt1;


import java.io.IOException;
import java.util.LinkedList;

public class DocViewer {

    private final Contents document;

    public DocViewer(Contents document) {
        this.document = document;
    }


    /**
     *
     * @param path
     * @param mode
     * @return
     * @throws IOException
     */
    public String showContetns(String[] path, int mode) throws IOException{
        Contents node = getNode(path);
        String pathString = node.getPathString();
        if(pathString.length() > 0)
            pathString += ":\n";

        return pathString + getNodeContents(node, mode);
    }


    /**
     *
     * @param pathBegin
     * @param pathEnd
     * @param mode
     * @return
     * @throws Exception
     */
    public String showRange(String[] pathBegin, String[] pathEnd, int mode) throws Exception{
        Contents startNode = getNode(pathBegin);
        Contents endNode = getNode(pathEnd);
        if(!areInOrder(startNode, endNode))
            throw new Exception("range bounds reversed");

        String ans = startNode.getPathString();

        if(ans.length() > 0)
            ans += ":\n";

        while(startNode != endNode) {
            if(endNode.isChildOf(startNode))
                startNode = startNode.getSubcontents().getFirst();

            else {
                ans += getNodeContents(startNode, mode);
                startNode = getUpperNext(startNode);
            }
        }

        ans += getNodeContents(startNode, mode);

        return ans;
    }


    /**
     *
     * @param a
     * @param b
     * @return
     */
    private boolean areInOrder(Contents a, Contents b) {
        if(a.isChildOf(b))
            return false;

        if(b.isChildOf(a))
            return true;

        Contents commonParent = a;
        while(!b.isChildOf(commonParent))
            commonParent = commonParent.getParent();

        for(Contents c : commonParent.getSubcontents()) {
            if(a.isChildOf(c))
                return true;

            if(b.isChildOf(c))
                return false;
        }

        return false;
    }


    /**
     * takes in a Contents node and searches for its ancestor in the same tree level (goes to parent and gets following node in subcontents list)
     * @param c - node
     * @return - next node (null if node is last in its parent subcontents list)
     */
    private Contents getNext(Contents c) {
        LinkedList<Contents> list = c.getParent().getSubcontents();
        for(int i = 0; i < list.size() - 1; i++)
            if(list.get(i) == c)
                return list.get(i + 1);

        return null;
    }


    /**
     *
     * @param c
     * @return
     */
    private Contents getUpperNext(Contents c) {
        while(getNext(c) == null)
            c = c.getParent();

        return getNext(c);
    }


    /**
     *
     * @param path
     * @return
     * @throws IOException
     */
    private Contents getNode(String[] path) throws IOException{
        LinkedList<Contents> list = findMatchingNode(path, document, 0);
        if(list.size() == 0)
            throw new IOException("no matches were found");

        if(list.size() > 1)
            throw new IOException("multiple matches were found");

        return list.getFirst();
    }


    /**
     *
     * @param node
     * @param mode
     * @return
     */
    private String getNodeContents(Contents node, int mode) {

        if(mode == 0)
            return node.getFullContents(0);

        int maxDepthLevel;

        LineType line = node.getHeading().getType();

        if(line == LineType.MainHeader)
            maxDepthLevel = 3;

        else if(line == LineType.Chapter || line == LineType.Section || line == LineType.Title)
            maxDepthLevel = 4;

        else
            maxDepthLevel = 10;

        return node.getTableOfContents(0, maxDepthLevel);
    }


    /**
     * searches for document node specified by given path
     * @param path - String array - each element points to single level node
     * @param startNode - Contents node to begin searching with
     * @return - Contents node that matches specified path, null if no such is found
     */
    private LinkedList<Contents> findMatchingNode(String[] path, Contents startNode, int depthIndex) {

        LinkedList<Contents> list = new LinkedList<>();

        if(depthIndex >= path.length) {
            list.add(startNode);
            return list;
        }

        if(startNode.getHeading().matches(path[depthIndex]))
            return findMatchingNode(path, startNode, depthIndex + 1);

        for(Contents c : startNode.getSubcontents()) {
            LinkedList<Contents> tmp = findMatchingNode(path, c, depthIndex);
            list.addAll(tmp);
        }

        return list;
    }

}
