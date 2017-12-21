package agh.po.mszpyrka.projekt1;

import java.util.LinkedList;

import static java.lang.Math.max;


/**
 * class used for printing document contents in proper format
 */
public class DocFormatter {

    private static final String defaultIndent = "  ";

    /**
     * converts text from list of Contents nodes to readable String format
     * @param sourceList - input list of nodes
     * @return - properly formatted String
     */
    public static String showContents(LinkedList<Contents> sourceList, boolean tableOfContentsMode) {

        int maxDepth = 0;

        // max depth is set to two the greatest depth level from sourceList
        for(Contents c : sourceList)
            maxDepth = max(maxDepth, c.getHeading().getType().getDepthLevel());

        if(maxDepth == 0)
            maxDepth = 3;

        else if(maxDepth <= 3)
            maxDepth = 4;

        else
            maxDepth = 7;

        LinkedList<String> resultList = new LinkedList<>();

        boolean startIndent = true;
        if(sourceList.getFirst().getParent() == sourceList.getFirst()) // start indent is not necessary if whole document is printed
            startIndent = false;

        resultList.add(getPathToRoot(sourceList.getFirst()));
        resultList.add("");
        resultList.addAll(formatContents(sourceList, startIndent, tableOfContentsMode, maxDepth));
        deleteAdditionalEmptyLines(resultList);

        String resultStr = "";
        for(String s : resultList)
            resultStr += s + "\n";

        return resultStr;
    }


    /**
     * converts contents list to table of contents with proper indents
     * @param sourceList - list of Contents nodes
     * @param startIndent - if true, additional indent is set to formatted node's contents
     * @return - properly formatted table of contents
     */
    private static LinkedList<String> formatContents(LinkedList<Contents> sourceList, boolean startIndent, boolean tableOfContentsMode, int maxDepth) {

        LinkedList<String> result = new LinkedList<>();

        for(int i = 0; i < sourceList.size(); i++) {

            Contents c = sourceList.get(i);

            if(tableOfContentsMode && c.getHeading().getType().getDepthLevel() > maxDepth)
                continue;

            boolean needsIndent = false;

            // if the depth level of node is greater then the previous one from source list, it should be formatted with bigger indent
            if(i > 0 && c.getHeading().getType().getDepthLevel() > sourceList.get(i - 1).getHeading().getType().getDepthLevel()) {

                result.add(getPathFromLevel(c, sourceList.get(i - 1).getHeading().getType().getDepthLevel()));
                needsIndent = true;
            }

            String properIndent = "";
            if(needsIndent || startIndent)
                properIndent = "  ";

            if(tableOfContentsMode) {

                String highlights = properIndent + ContentsFormatter.getHighlights(c, maxDepth);
                result.add(highlights);
            }

            else {

                LinkedList<String> mainContents = (ContentsFormatter.mainContentsToStringList(c));

                for(int j = 0; j < mainContents.size(); j++)
                    mainContents.set(j, properIndent + mainContents.get(j));

                result.addAll(mainContents);
            }


            if(c.getHeading().getType().getDepthLevel() <= 3)
                result.add("");

            LinkedList<String> childrenContents = formatContents(c.getSubcontents(), false, tableOfContentsMode, maxDepth);

            // all contents from children nodes should have greater indent
            for(int j = 0; j < childrenContents.size(); j++)
                childrenContents.set(j, properIndent + defaultIndent + childrenContents.get(j));

            result.addAll(childrenContents);

            // empty line for better readability is added for all Sections, Chapters and Titles, as well as for nodes with many children
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
    private static String getPathToRoot(Contents c) {
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
    private static String getPathFromLevel(Contents c, int level) {
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
     * processes list of Strings in a way that no two consecutive empty lines are left
     * @param list - list of Strings to process
     */
    private static void deleteAdditionalEmptyLines(LinkedList<String> list) {

        while(list.getFirst().trim().length() == 0)
            list.removeFirst();

        while(list.getLast().trim().length() == 0)
            list.removeLast();

        for(int i = 1; i < list.size(); i++)
            while(list.get(i).trim().length() == 0 && list.get(i - 1).trim().length() == 0)
                list.remove(i);
    }
}
