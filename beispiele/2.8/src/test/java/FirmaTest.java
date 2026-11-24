import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirmaTest {

    private Firma firma;
    private Mitarbeiter karl;
    private Mitarbeiter susi;
    private Mitarbeiter anna;

    @BeforeEach
    void setUp() {
        firma = new Firma("Spengergasse OG");
        karl = new Mitarbeiter("Karl Maier", 1989, 15, 40);
        susi = new Mitarbeiter("Susanne Huber", 1991, 20, 30);
        anna = new Mitarbeiter("Anna Novak", 2001, 14, 20);
    }

    @Test
    void beliebigVieleMitarbeiter() {
        for (int i = 0; i < 25; i++) {
            assertTrue(firma.einstellen(new Mitarbeiter("MA " + i, 1990)));
        }
        assertEquals(25, firma.anzahlMitarbeiter());
    }

    @Test
    void keineNullWerte() {
        assertFalse(firma.einstellen(null));
        assertFalse(firma.einfuegen(0, null));
        assertEquals(0, firma.anzahlMitarbeiter());
    }

    @Test
    void einfuegenAnPosition() {
        firma.einstellen(karl);
        firma.einstellen(susi);
        firma.einfuegen(1, anna);
        assertSame(anna, firma.getMitarbeiter(1));
        assertSame(susi, firma.getMitarbeiter(2));
    }

    @Test
    void kuendigen() {
        firma.einstellen(karl);
        firma.einstellen(susi);
        assertSame(susi, firma.kuendigen("Susanne Huber"));
        assertNull(susi.getFirma());
        assertTrue(firma.kuendigen(karl));
        assertEquals(0, firma.anzahlMitarbeiter());
    }

    @Test
    void kostenUndSortieren() {
        firma.einstellen(susi);
        firma.einstellen(karl);
        firma.einstellen(anna);
        assertEquals(5920, firma.summeKosten());
        firma.sortierenNachName();
        assertSame(anna, firma.getMitarbeiter(0));
        assertSame(susi, firma.getMitarbeiter(2));
    }
}
