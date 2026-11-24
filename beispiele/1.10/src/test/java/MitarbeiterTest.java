import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit-Testklasse zu Mitarbeiter.
 * Ausführen: grüner Pfeil neben der Klasse bzw. einer Testmethode.
 */
public class MitarbeiterTest {

    private Mitarbeiter m;

    @BeforeEach
    public void setUp() {
        m = new Mitarbeiter("Karl Maier", 1989, 15, 40);
    }

    @Test
    public void testKostenProMonat() {
        assertEquals(2400, m.getKostenProMonat());
        assertEquals(3000, m.getKostenProMonat(5));
    }

    @Test
    public void testAlter() {
        assertEquals(37, m.alterBerechnen(2026));
    }

    @Test
    public void testUngueltigeWerte() {
        m.setStundenlohn(5);
        assertEquals(10, m.getStundenlohn());   // Standardwert
        m.setGeburtsjahr(1789);
        assertEquals(1970, m.getGeburtsjahr());
    }

    @Test
    public void testStrings() {
        assertEquals("K. M.", m.initialen());
        assertEquals("KAR", m.kuerzel());
        m.setName("   ");                 // nur Leerzeichen -> ungueltig
        assertEquals("n/a", m.getName());
    }

    @Test
    public void testKostenBalken() {
        assertEquals("#####", m.kostenBalken());        // 2400 Euro
        m.setAnzahlWochenStunden(25);                   // 1500 Euro
        assertEquals("###", m.kostenBalken());
    }

    @Test
    public void testTeilzeit() {
        assertFalse(m.isTeilzeit());
        m.setAnzahlWochenStunden(20);
        assertTrue(m.isTeilzeit());
        assertEquals(4.0, m.stundenProTag(), 0.001);
    }
}
