package agh.po.mszpyrka.projekt1;

import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentPathTest {
    @Test
    public void isSubsequenceOf() throws Exception {
        DocumentPath path1 = new DocumentPath("dział II, art. 12., 2)");
        DocumentPath path2 = new DocumentPath("art 12, 2)");

        assertTrue(path2.isSubsequenceOf(path1));

        path2 = new DocumentPath("art 12, 2");
        assertFalse(path2.isSubsequenceOf(path1));
    }

}