package agh.po.mszpyrka.projekt1;

import java.util.LinkedList;

/**
 * class used for finding particular nodes in Contents tree
 */
public class DocSearcher {

    private final Contents document;

    public DocSearcher(Contents document) {
        this.document = document;
    }


    /**
     *
     * @param path - String array, each element is matched with nodes' headings
     * @return - node that matches given path
     * @throws Exception - if no or more than one nodes are found
     */
    public Contents getNode(String[] path) throws Exception{

        LinkedList<Contents> list = findMatchingNodes(path, document, 0);

        if(list.size() == 0)
            throw new Exception("invalid path: no matches were found");

        if(list.size() > 1)
            throw new Exception("insufficient path: multiple matches were found");

        return list.getFirst();
    }


    /**
     *
     * @param path1 - path for the first node
     * @param path2 - path for the second node
     * @return - list of nodes between first and second one
     * @throws Exception - if paths are invalid or given bounds are not in order in document
     */
    public LinkedList<Contents> getRange(String[] path1, String[] path2) throws Exception{

        Contents first = this.getNode(path1);
        Contents second = this.getNode(path2);

        if(!this.areInOrder(first, second))
            throw new Exception("invalid path: bounds reversed");

        LinkedList<Contents> list = new LinkedList<>();

        while(first != second) {
            while(second.isChildOf(first))
                first = first.getSubcontents().getFirst();

            list.add(first);
            first = this.getNext(first);
        }

        list.add(first);

        return list;
    }


    /**
     * checks if two nodes are in proper order in Contents tree
     * @param first - first node
     * @param second - second node
     * @return - true only if the second node represents further part of the document than the first one
     */
    private boolean areInOrder(Contents first, Contents second) {

        if(second.isChildOf(first) || first == second)
            return true;

        if(first.isChildOf(second))
            return false;

        Contents commonParent = first;
        while(!second.isChildOf(commonParent))
            commonParent = commonParent.getParent();

        for(Contents c : commonParent.getSubcontents()) {
            if(first.isChildOf(c))
                return true;

            if(second.isChildOf(c))
                return false;
        }

        return false;
    }


    /**
     * takes in a Contents node and searches for its first 'brother' in Contents order (goes to parent's subcontents list and gets the first node after given one)
     * @param c - node
     * @return - next node (null if node is last in its parent subcontents list)
     */
    private Contents getBrother(Contents c) {
        LinkedList<Contents> list = c.getParent().getSubcontents();
        for(int i = 0; i < list.size() - 1; i++)
            if(list.get(i) == c)
                return list.get(i + 1);

        return null;
    }


    /**
     * takes in a node and searches for its successor in document
     * @param c - node
     * @return - c's successor
     */
    private Contents getNext(Contents c) {
        while(getBrother(c) == null)
            c = c.getParent();

        return getBrother(c);
    }


    /**
     * searches for document nodes specified by given path
     * @param path - String array, each element is matched with nodes' headings
     * @param startNode - node to begin searching with
     * @return - list of Contents nodes that match specified path
     */
    private LinkedList<Contents> findMatchingNodes (String[] path, Contents startNode, int depthIndex) {

        LinkedList<Contents> list = new LinkedList<>();

        if(depthIndex >= path.length) {
            list.add(startNode);
            return list;
        }

        if(startNode.getHeading().matches(path[depthIndex]))
            return findMatchingNodes(path, startNode, depthIndex + 1);

        for(Contents c : startNode.getSubcontents()) {
            LinkedList<Contents> tmp = findMatchingNodes(path, c, depthIndex);
            list.addAll(tmp);
        }

        return list;
    }

}
