package agh.po.mszpyrka.projekt1;

import java.util.LinkedList;

/**
 * class used for printing document contents in proper format
 */
public class DocPrinter {

    private final String defaultIndent = "  ";


    /**
     *
     * @param sourceList
     * @return
     */
    public LinkedList<String> showFullContents(LinkedList<Contents> sourceList) {

        LinkedList<String> result = new LinkedList<>();

        boolean startIndent = true;
        if(sourceList.getFirst().getParent() == sourceList.getFirst())
            startIndent = false;

        result.add(this.getPathToRoot(sourceList.getFirst()));
        result.add("");
        result.addAll(this.formatFullContents(sourceList, startIndent));

        return result;
    }


    /**
     * converts Contents list to String list with proper indents
     * @param sourceList - list of Contents nodes
     * @param startIndent - true only for outer function calls, makes indent for few first nodes from sourceList
     * @return - list of Strings with proper text format
     */
    private LinkedList<String> formatFullContents(LinkedList<Contents> sourceList, boolean startIndent) {

        LinkedList<String> result = new LinkedList<>();

        for(int i = 0; i < sourceList.size(); i++) {

            Contents c = sourceList.get(i);

            boolean needsIndent = false;

            if(i > 0 && c.getHeading().getType().getDepthLevel() > sourceList.get(i - 1).getHeading().getType().getDepthLevel()) {

                result.add(this.getPathFromLevel(c, sourceList.get(i - 1).getHeading().getType().getDepthLevel()));
                needsIndent = true;
            }

            if(i > 0 && c.getHeading().getType().getDepthLevel() != sourceList.get(i - 1).getHeading().getType().getDepthLevel())
                startIndent = false;


            LinkedList<String> mainContents = (c.mainContentsToStringList());

            String properIndent = "";
            if(needsIndent || startIndent)
                properIndent = "  ";

            for(int j = 0; j < mainContents.size(); j++)
                mainContents.set(j, properIndent + mainContents.get(j));

            result.addAll(mainContents);

            if(c.getHeading().getType().getDepthLevel() <= 3)
                result.add("");

            LinkedList<String> childrenContents = this.formatFullContents(c.getSubcontents(), false);
            for(int j = 0; j < childrenContents.size(); j++)
                childrenContents.set(j, properIndent + defaultIndent + childrenContents.get(j));

            result.addAll(childrenContents);

            if(c.getHeading().getType().getDepthLevel() <= 4 || c.getSubcontents().size() > 0)
                result.add("");
        }

        return result;
    }


    /**
     * gets path from root node of Contents tree to given node
     * @param c - node
     * @return - path in String format
     */
    private String getPathToRoot(Contents c) {
        String ans = "";
        while(c != c.getParent()) {
            ans = c.getParent().getHeading().toString() + ", " + ans;
            c = c.getParent();
        }

        if(ans.length() == 0)
            return ans;

        return ans.substring(0, ans.length() - 2) + ":";
    }


    /**
     * gets file path from given level to given node
     * @param c - node specified by the path
     * @param level - depth level from which the path starts
     * @return - properly formatted String representing the path
     */
    private String getPathFromLevel(Contents c, int level) {
        String ans = "";
        while(c.getParent().getHeading().getType().getDepthLevel() >= level) {
            ans = c.getParent().getHeading().toString() + ", " + ans;
            c = c.getParent();
        }

        if(ans.length() == 0)
            return ans;

        return ans.substring(0, ans.length() - 2) + ":";
    }


    /**
     *
     * @param path
     * @param mode
     * @return
     * @throws IOException
     /
    public String showContetns(String[] path, int mode) throws Exception{
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
     /
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
     * @param node
     * @param mode
     * @return
     /
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

    */
}
