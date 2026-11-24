import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Mitarbeiter ist ab 2.9 abstrakt – getestet wird über Angestellter. */
class MitarbeiterTest {

    private Mitarbeiter m;

    @BeforeEach
    void setUp() throws FirmaException {
        m = new Angestellter("Karl Maier", 1989, 15, 40, true);
    }

    @Test
    void kostenProMonat() throws FirmaException {
        assertEquals(2400, m.getKostenProMonat());
        assertEquals(3000, m.getKostenProMonat(5));
    }

    @Test
    void ungueltigeWerteWerfenException() throws FirmaException {
        assertThrows(FirmaException.class, () -> m.setStundenlohn(5));
        assertEquals(15, m.getStundenlohn());          // unverändert
        FirmaException e = assertThrows(FirmaException.class,
                                        () -> m.setGeburtsjahr(1789));
        assertTrue(e.getMessage().contains("1789"));
        assertThrows(FirmaException.class, () -> m.setName("   "));
        assertEquals("Karl Maier", m.getName());
    }

    @Test
    void konstruktorMitUngueltigenWerten() {
        assertThrows(FirmaException.class,
                     () -> new Arbeiter("Karl Maier", 1789, 15, 40, false));
        assertThrows(FirmaException.class,
                     () -> new Freelancer("Sepp", 1981, 3, 100));
    }

    @Test
    void strings() throws FirmaException {
        assertEquals("K. M.", m.initialen());
        assertEquals("KAR", m.kuerzel());
        assertEquals("#####", m.kostenBalken());
    }

    @Test
    void teilzeit() throws FirmaException {
        assertFalse(m.isTeilzeit());
        m.setAnzahlWochenStunden(20);
        assertTrue(m.isTeilzeit());
        assertEquals(4.0, m.stundenProTag(), 0.001);
    }
}
