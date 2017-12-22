package agh.po.mszpyrka.projekt1;

import org.junit.Test;

import static org.junit.Assert.*;

public class RawTextParserTest {

    @Test
    public void getLineType() throws Exception {

        assertEquals(RawTextParser.getLineType("©Kancelaria Sejmu"), LineType.TRASH);
        assertEquals(RawTextParser.getLineType("2009-11-16"), LineType.TRASH);
        assertEquals(RawTextParser.getLineType("r"), LineType.TRASH);

        assertEquals(RawTextParser.getLineType("RZECZYPOSPOLITEJ POLSKIEJ"), LineType.TITLE);
        assertEquals(RawTextParser.getLineType("RZECZPOSPOLITA"), LineType.TITLE);

        assertEquals(RawTextParser.getLineType("Rozdział I"), LineType.CHAPTER);

        assertEquals(RawTextParser.getLineType("DZIAŁ VIII"), LineType.SECTION);

        assertEquals(RawTextParser.getLineType("Art. 1."), LineType.ARTICLE);
        assertEquals(RawTextParser.getLineType("Art. 131. 1. Do postępowań wszczętych na podstawie ustawy z dnia 15 grudnia"), LineType.ARTICLE);
        assertEquals(RawTextParser.getLineType("Art. 113j. 1. Wniosek przedsiębiorcy zgodny z wymogami określonymi"), LineType.ARTICLE);
        assertEquals(RawTextParser.getLineType("Art. 111. 1. Prezes Urzędu, ustalając wysokość nakładanej kary pieniężnej,"), LineType.ARTICLE);
        assertEquals(RawTextParser.getLineType("Art. 115–129. (pominięte)"), LineType.ARTICLE);

        assertEquals(RawTextParser.getLineType("2. Finansowanie partii politycznych jest jawne."), LineType.NUMBER_DOT_POINT);

        assertEquals(RawTextParser.getLineType("5) czasu trwania porozumienia;"), LineType.NUMBER_PARENTH_POINT);
        assertEquals(RawTextParser.getLineType("16a) roku obrotowym – rozumie się przez to rok obrotowy w rozumieniu art. 3 ust. 1"), LineType.NUMBER_PARENTH_POINT);

        assertEquals(RawTextParser.getLineType("c) podjęcie z własnej inicjatywy działań w celu zaprzestania naruszenia lub"), LineType.LETTER_PARENTH_POINT);

        assertEquals(RawTextParser.getLineType("Rzeczpospolita Polska jest dobrem wspólnym wszystkich obywateli."), LineType.REGULAR_TEXT);
        assertEquals(RawTextParser.getLineType("2000 r. o ochronie konkurencji i konsumentów i niezakończonych do dnia wejścia"), LineType.REGULAR_TEXT);
        assertEquals(RawTextParser.getLineType("art. 57 ust. 6, art. 77 ust. 6, art. 94 ust. 5, art. 103a ust. 5 ustawy z dnia 15 grudnia 2000"), LineType.REGULAR_TEXT);
        assertEquals(RawTextParser.getLineType("z dnia 2 kwietnia 1997 r."), LineType.REGULAR_TEXT);
        assertEquals(RawTextParser.getLineType("108, stanowią dochód budżetu państwa."), LineType.REGULAR_TEXT);
        assertEquals(RawTextParser.getLineType("w art. 101 ust. 1 lit. a–e TFUE:"), LineType.REGULAR_TEXT);

    }

    @Test
    public void deleteWordBreak() throws Exception {

        DocLine line1 = new DocLine(LineType.REGULAR_TEXT, ""), line2 = new DocLine(LineType.REGULAR_TEXT, "");

        line1.setContents("niepodzielności jego terytorium oraz zapewnieniu bezpieczeństwa i nienaruszal-");
        line2.setContents("ności jego granic.");

        RawTextParser.deleteWordBreak(line1, line2);

        assertEquals(line1.getContents(), "niepodzielności jego terytorium oraz zapewnieniu bezpieczeństwa i nienaruszalności");
        assertEquals(line2.getContents(), "jego granic.");



        line1.setContents("Przepisy Konstytucji stosuje się bezpośrednio, chyba że Konstytucja stanowi in-");
        line2.setContents("aczej.");

        RawTextParser.deleteWordBreak(line1, line2);

        assertEquals(line1.getContents(), "Przepisy Konstytucji stosuje się bezpośrednio, chyba że Konstytucja stanowi inaczej.");
        assertEquals(line2.getContents(), "");

    }

    @Test
    public void splitToDifferentLineTypes() throws Exception {

        String tab[] = new String[] {"Art. 105j.", "1.", "Przebieg przeprowadzonej kontroli kontrolujący przedstawia"};
        assertArrayEquals(RawTextParser.splitToDifferentLineTypes("Art. 105j. 1. Przebieg przeprowadzonej kontroli kontrolujący przedstawia"), tab);

    }

}