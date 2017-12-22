package agh.po.mszpyrka.projekt1;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeadingTest {
    @Test
    public void isRange() throws Exception {

        DocLine line1 = new DocLine(LineType.ARTICLE, "Art. 156.");
        Heading heading1 = new Heading(line1);
        assertFalse(heading1.isRange());

        DocLine line2 = new DocLine(LineType.ARTICLE, "Art. 90–123.");
        Heading heading2 = new Heading(line2);
        assertTrue(heading2.isRange());
    }

}