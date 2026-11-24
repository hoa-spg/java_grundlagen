import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VererbungTest {

    @Test
    void kostenWerdenUeberschrieben() {
        Angestellter susi = new Angestellter("Susanne Huber", 1991, 20, 30, false);
        Arbeiter karl = new Arbeiter("Karl Maier", 1989, 15, 40, true);
        assertEquals(2400 + 5 * 20, susi.getKostenProMonat());
        assertEquals(2400 + 200, karl.getKostenProMonat());
    }

    @Test
    void geerbteMethodenSindVorhanden() {
        Arbeiter karl = new Arbeiter("Karl Maier", 1989, 15, 40, false);
        assertEquals("K. M.", karl.initialen());       // aus Mitarbeiter
        assertEquals(37, karl.alterBerechnen(2026));
    }

    @Test
    void polymorphismusInDerFirma() {
        Firma firma = new Firma("Spengergasse OG");
        Mitarbeiter m1 = new Angestellter("Susanne Huber", 1991, 20, 30, true);
        Mitarbeiter m2 = new Arbeiter("Karl Maier", 1989, 15, 40, true);
        firma.einstellen(m1);
        firma.einstellen(m2);
        // dynamische Bindung: es wird die Methode der Unterklasse aufgerufen
        assertEquals(2400 + 2600, firma.summeKosten());
        assertTrue(firma.getMitarbeiter(1).toString().endsWith("Facharbeiter"));
    }
}
