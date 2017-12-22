package agh.po.mszpyrka.projekt1;

/**
 * Class used to represent Content node paths in document.
 */
public class DocumentPath {
    private final String[] path;

    public DocumentPath(String str) {
        String[] path = str.split(",");

        for(int i = 0; i < path.length; i++)
            path[i] = path[i].trim();

        this.path = path;
    }

    public DocumentPath(String[] path) {
        for(int i = 0; i < path.length; i++)
            path[i] = path[i].trim();

        this.path = path;
    }

    // Gets path.
    public String[] getPath() {
        return this.path;
    }


    /**
     * Creates new DocumentPath from existing one by adding given String to path array.
     * @param s - String to add
     * @return - extended DocumentPath
     */
    public DocumentPath extend(String s) {
        String[] path = new String[this.path.length + 1];
        for(int i = 0; i < this.path.length; i++)
            path[i] = this.path[i];

        path[path.length - 1] = s;

        return new DocumentPath(path);
    }

    /**
     * Checks if this isSubsequenceOf given DocumentPath.
     * @param docPath - another docPath
     * @return - true if this.path is a subsequence of docPath.path
     * (e.g. {"art. 123", "c)"} isSubsequenceOf {"rozdział II", "art 123", "4.", "c)"} but not the other way around)
     */
    public boolean isSubsequenceOf(DocumentPath docPath) {

        String[] path2 = docPath.getPath();

        int j = 0;
        for(String s : path2)
            if(j < this.path.length && pathElemMatch(s, this.path[j]))
                j++;

        return j >= this.path.length;
    }


    /**
     * Checks if two strings represent the same Headings, matching function is not sensitive to letter cases and missing dots
     * (e.g. "art 123" isSubsequenceOf "art. 123", "Art. 123" isSubsequenceOf "aRT.. 123.", "art 123a" doesn't match "art 123", "4" matches "4.", but not "4)").
     * @param a - first String
     * @param b - second String
     * @return - true only if given strings match each other
     */
    private static boolean pathElemMatch(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();

        String[] tabA = a.split("\\s+");
        String[] tabB = b.split("\\s+");

        if(tabA.length != tabB.length)
            return false;

        for(int i = 0; i < tabA.length; i++)
            if(!(tabA[i].equals(tabB[i]) || tabA[i].equals(tabB[i] + ".") || tabB[i].equals(tabA[i] + ".")))
                return false;

        return true;
    }
}
