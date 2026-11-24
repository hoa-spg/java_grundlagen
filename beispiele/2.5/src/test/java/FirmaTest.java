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
        firma = new Firma("Spengergasse OG", 3);
        karl = new Mitarbeiter("Karl Maier", 1989, 15, 40);
        susi = new Mitarbeiter("Susanne Huber", 1991, 20, 30);
        anna = new Mitarbeiter("Anna Novak", 2001, 14, 20);
    }

    @Test
    void einstellenBisVoll() {
        assertTrue(firma.einstellen(karl));
        assertTrue(firma.einstellen(susi));
        assertTrue(firma.einstellen(anna));
        assertFalse(firma.einstellen(new Mitarbeiter("Zu Viel", 1990)));
        assertEquals(0, firma.anzahlFreiePlaetze());
    }

    @Test
    void einfuegenAnPosition() {
        firma.einstellen(karl);
        firma.einstellen(susi);
        firma.einfuegen(0, anna);
        assertSame(anna, firma.getMitarbeiter(0));
        assertSame(karl, firma.getMitarbeiter(1));
        assertFalse(firma.einfuegen(5, new Mitarbeiter("X", 1990)));
    }

    @Test
    void kuendigenOhneLuecken() {
        firma.einstellen(karl);
        firma.einstellen(susi);
        firma.einstellen(anna);
        assertSame(susi, firma.kuendigen("Susanne Huber"));
        assertEquals(2, firma.anzahlMitarbeiter());
        assertSame(anna, firma.getMitarbeiter(1));
        assertNull(firma.getMitarbeiter(2));
        assertTrue(firma.kuendigen(karl));
        assertFalse(firma.kuendigen(karl));
    }

    @Test
    void kostenUndSortieren() {
        firma.einstellen(susi);
        firma.einstellen(karl);
        firma.einstellen(anna);
        assertEquals(2400 + 2400 + 1120, firma.summeKosten());
        firma.sortierenNachName();
        assertSame(anna, firma.getMitarbeiter(0));
        assertSame(karl, firma.getMitarbeiter(1));
        assertSame(susi, firma.getMitarbeiter(2));
    }
}
