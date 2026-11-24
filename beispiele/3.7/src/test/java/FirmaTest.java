import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirmaTest {

    private Firma firma;
    private Angestellter susi;
    private Arbeiter karl;
    private Freelancer sepp;

    @BeforeEach
    void setUp() throws FirmaException {
        firma = new Firma("Spengergasse OG");
        susi = new Angestellter("Susanne Huber", 1991, 20, 30, false);
        karl = new Arbeiter("Karl Maier", 1989, 15, 40, false);
        sepp = new Freelancer("Sepp Gruber", 1981, 3, 300);
    }

    @Test
    void allePersonenInEinerListe() throws FirmaException {
        firma.einstellen(susi);
        firma.einstellen(karl);
        firma.einstellen(sepp);
        assertEquals(3, firma.anzahlMitarbeiter());
        assertEquals(2500 + 2400 + 900, firma.summeKosten());
    }

    @Test
    void keineNullWerte() throws FirmaException {
        assertThrows(FirmaException.class, () -> firma.einstellen(null));
        assertThrows(FirmaException.class, () -> firma.kuendigen((Person) null));
        assertEquals(0, firma.anzahlMitarbeiter());
    }

    @Test
    void ungueltigerIndexUndName() throws FirmaException {
        firma.einstellen(susi);
        assertThrows(FirmaException.class, () -> firma.getPerson(1));
        assertThrows(FirmaException.class, () -> firma.kuendigen(5));
        assertThrows(FirmaException.class, () -> firma.kuendigen("Niemand"));
        assertThrows(FirmaException.class, () -> new Firma(""));
    }

    @Test
    void sortierenMitComparable() throws FirmaException {
        firma.einstellen(susi);
        firma.einstellen(sepp);
        firma.einstellen(karl);
        firma.sortieren();
        assertSame(karl, firma.getPerson(0));
        assertSame(sepp, firma.getPerson(1));
        assertSame(susi, firma.getPerson(2));
    }

    @Test
    void kuendigen() throws FirmaException {
        firma.einstellen(susi);
        firma.einstellen(karl);
        assertSame(susi, firma.kuendigen("Susanne Huber"));
        assertNull(susi.getFirma());
        assertTrue(firma.kuendigen(karl));
        assertEquals(0, firma.anzahlMitarbeiter());
    }
}
