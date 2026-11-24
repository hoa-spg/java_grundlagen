import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit-Tests zu Mitarbeiter (JUnit 5).
 * IntelliJ: in der Klasse Mitarbeiter Alt+Einfg -> Test...
 * Ausführen: Rechtsklick -> Run 'MitarbeiterTest' oder "mvn test".
 */
class MitarbeiterTest {

    private Mitarbeiter m;

    @BeforeEach
    void setUp() {
        m = new Mitarbeiter("Karl Maier", 1989, 15, 40);
    }

    @Test
    void kostenProMonat() {
        assertEquals(2400, m.getKostenProMonat());
        assertEquals(3000, m.getKostenProMonat(5));
    }

    @Test
    void alterBerechnen() {
        assertEquals(37, m.alterBerechnen(2026));
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
    }

    @Test
    void kostenBalken() {
        assertEquals("#####", m.kostenBalken());
    }

    @Test
    void teilzeit() {
        assertFalse(m.isTeilzeit());
        m.setAnzahlWochenStunden(20);
        assertTrue(m.isTeilzeit());
        assertEquals(4.0, m.stundenProTag(), 0.001);
    }

    @Test
    void personalNummerIstVierstellig() {
        int nr = m.getPersonalNummer();
        assertTrue(nr >= 1000 && nr <= 9999, "Personalnummer: " + nr);
    }
}
