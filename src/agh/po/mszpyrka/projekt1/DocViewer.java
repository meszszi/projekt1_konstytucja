package agh.po.mszpyrka.projekt1;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

import java.util.LinkedList;

public class DocViewer {

    /**
     * searches for document node specified by given path
     * @param path - String array - each element points to single level node
     * @param startNode - Contents node to begin searching with
     * @return - Contents node that matches specified path, null if no such is found
     */

    public LinkedList<Contents> findContentsNode(String[] path, Contents startNode, int depthIndex) {

        LinkedList<Contents> list = new LinkedList<Contents>();

        if(depthIndex >= path.length) {
            list.add(startNode);
            return list;
        }

        if(startNode.getHeading().matches(path[depthIndex]))
            return findContentsNode(path, startNode, depthIndex + 1);

        for(Contents c : startNode.getSubcontents()) {
            LinkedList<Contents> tmp = findContentsNode(path, c, depthIndex);
            list.addAll(tmp);
        }

        return list;
    }

}
