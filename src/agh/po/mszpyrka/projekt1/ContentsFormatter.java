package agh.po.mszpyrka.projekt1;

import java.util.LinkedList;

/**
 * Class used to format Contents nodes.
 */
public class ContentsFormatter {

    /**
     * Returns properly formatted text for single Contents node (doesn't include any subContents).
     * @return - String array, each element containing single line
     */
    public static LinkedList<String> mainContentsToStringList(Contents node) {

        LinkedList<String> result = new LinkedList<>();

        if(node.getHeading().getType() == LineType.MainHeader || node.getMainContents().size() == 0) {
            result.add(node.getHeading().toString());
            result.addAll(node.getMainContents());

            return result;
        }

        String tmp = "";

        if(node.getHeading().getType() == LineType.Chapter)
            tmp = ":";

        else if(node.getHeading().getType() == LineType.Section)
            tmp = ")";

        result.add(node.getHeading().toString() + tmp + " " + node.getMainContents().getFirst());

        for(int i = 1; i < node.getMainContents().size(); i++)
            result.add(" " + node.getMainContents().get(i));

        return result;
    }


    /**
     * Formats node's contents for table of contents mode (heading + first line of mainContents is selected).
     * @param minDepth - recursive subtontents' highlights are included if this level is reached
     * @return - formatted highlights for Contents node
     */
    public static String getHighlights(Contents node, int minDepth) {
        String ans = node.getHeading().toString();

        if(node.getHeading().getType() == LineType.MainHeader) // doesn't insclude any text after heading in case of MainHeader
            return ans;

        if(node.getMainContents().size() > 0) {                // if there are any mainContents -> includes the first line
            ans += " ~ " + node.getMainContents().getFirst();

            if(node.getMainContents().size() > 1)                  // if there is more -> adds "(...)"
                ans += " (...)";
        }

        else if(node.getSubcontents().size() > 0 && node.getSubcontents().getFirst().getHeading().getType().getDepthLevel() > minDepth)
            ans += " | " + getHighlights(node.getSubcontents().getFirst(), minDepth);

        return ans;
    }
}
