import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Mitarbeiter ist ab 2.9 abstrakt – getestet wird über Angestellter. */
class MitarbeiterTest {

    private Mitarbeiter m;

    @BeforeEach
    void setUp() {
        m = new Angestellter("Karl Maier", 1989, 15, 40, true);
    }

    @Test
    void kostenProMonat() {
        assertEquals(2400, m.getKostenProMonat());
        assertEquals(3000, m.getKostenProMonat(5));
    }

    @Test
    void ungueltigeWerteSetzenStandardwerte() {
        m.setStundenlohn(5);
        assertEquals(10, m.getStundenlohn());
        m.setGeburtsjahr(1789);
        assertEquals(1970, m.getGeburtsjahr());
        m.setName("   ");
        assertEquals("n/a", m.getName());
    }

    @Test
    void strings() {
        assertEquals("K. M.", m.initialen());
        assertEquals("KAR", m.kuerzel());
        assertEquals("#####", m.kostenBalken());
    }

    @Test
    void teilzeit() {
        assertFalse(m.isTeilzeit());
        m.setAnzahlWochenStunden(20);
        assertTrue(m.isTeilzeit());
        assertEquals(4.0, m.stundenProTag(), 0.001);
    }
}
