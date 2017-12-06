package agh.po.mszpyrka.projekt1;


import java.util.LinkedList;

public class DocViewer {

    private final Contents document;

    public DocViewer(Contents document) {
        this.document = document;
    }


    /**
     * searches for document node specified by given path
     * @param path - String array - each element points to single level node
     * @param startNode - Contents node to begin searching with
     * @return - Contents node that matches specified path, null if no such is found
     */
    public LinkedList<Contents> findMatchingNode(String[] path, Contents startNode, int depthIndex) {

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
