package agh.po.mszpyrka.projekt1;

import org.junit.Test;

import static org.junit.Assert.*;

public class RawTextParserTest {

    @Test
    public void getLineType() throws Exception {

        assertEquals(RawTextParser.getLineType("©Kancelaria Sejmu"), LineType.Trash);
        assertEquals(RawTextParser.getLineType("2009-11-16"), LineType.Trash);
        assertEquals(RawTextParser.getLineType("r"), LineType.Trash);

        assertEquals(RawTextParser.getLineType("RZECZYPOSPOLITEJ POLSKIEJ"), LineType.Title);
        assertEquals(RawTextParser.getLineType("RZECZPOSPOLITA"), LineType.Title);

        assertEquals(RawTextParser.getLineType("Rozdział I"), LineType.Chapter);

        assertEquals(RawTextParser.getLineType("DZIAŁ VIII"), LineType.Section);

        assertEquals(RawTextParser.getLineType("Art. 1."), LineType.Article);
        assertEquals(RawTextParser.getLineType("Art. 131. 1. Do postępowań wszczętych na podstawie ustawy z dnia 15 grudnia"), LineType.Article);
        assertEquals(RawTextParser.getLineType("Art. 113j. 1. Wniosek przedsiębiorcy zgodny z wymogami określonymi"), LineType.Article);
        assertEquals(RawTextParser.getLineType("Art. 111. 1. Prezes Urzędu, ustalając wysokość nakładanej kary pieniężnej,"), LineType.Article);
        assertEquals(RawTextParser.getLineType("Art. 115–129. (pominięte)"), LineType.Article);

        assertEquals(RawTextParser.getLineType("2. Finansowanie partii politycznych jest jawne."), LineType.NumberDotPoint);

        assertEquals(RawTextParser.getLineType("5) czasu trwania porozumienia;"), LineType.NumberParenthPoint);
        assertEquals(RawTextParser.getLineType("16a) roku obrotowym – rozumie się przez to rok obrotowy w rozumieniu art. 3 ust. 1"), LineType.NumberParenthPoint);

        assertEquals(RawTextParser.getLineType("c) podjęcie z własnej inicjatywy działań w celu zaprzestania naruszenia lub"), LineType.LetterParenthPoint);

        assertEquals(RawTextParser.getLineType("Rzeczpospolita Polska jest dobrem wspólnym wszystkich obywateli."), LineType.RegularText);
        assertEquals(RawTextParser.getLineType("2000 r. o ochronie konkurencji i konsumentów i niezakończonych do dnia wejścia"), LineType.RegularText);
        assertEquals(RawTextParser.getLineType("art. 57 ust. 6, art. 77 ust. 6, art. 94 ust. 5, art. 103a ust. 5 ustawy z dnia 15 grudnia 2000"), LineType.RegularText);
        assertEquals(RawTextParser.getLineType("z dnia 2 kwietnia 1997 r."), LineType.RegularText);
        assertEquals(RawTextParser.getLineType("108, stanowią dochód budżetu państwa."), LineType.RegularText);
        assertEquals(RawTextParser.getLineType("w art. 101 ust. 1 lit. a–e TFUE:"), LineType.RegularText);

    }

    @Test
    public void deleteWordBreak() throws Exception {

        DocLine line1 = new DocLine(LineType.RegularText, ""), line2 = new DocLine(LineType.RegularText, "");

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