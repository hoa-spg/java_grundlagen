import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZeiterfassungTest {

    private Zeiterfassung z;

    @BeforeEach
    void setUp() {
        z = new Zeiterfassung(4);
        z.hinzufuegen(8);
        z.hinzufuegen(6);
        z.hinzufuegen(10);
    }

    @Test
    void hinzufuegenBisVoll() {
        z.hinzufuegen(5);
        z.hinzufuegen(7);              // Array voll -> wird ignoriert
        assertEquals(4, z.getAnzahl());
        assertEquals("Buchungen: 8 h, 6 h, 10 h, 5 h (Summe: 29 h)", z.toString());
    }

    @Test
    void einfuegenVerschiebtNachHinten() {
        z.einfuegen(1, 4);
        assertEquals("Buchungen: 8 h, 4 h, 6 h, 10 h (Summe: 28 h)", z.toString());
    }

    @Test
    void entfernenZiehtNachVorne() {
        z.entfernen(0);
        assertEquals(2, z.getAnzahl());
        assertEquals(6, z.wert(0));
        assertEquals(-1, z.wert(2));   // nicht mehr gültig
    }

    @Test
    void berechnungen() {
        assertEquals(24, z.summe());
        assertEquals(10, z.maximum());
        assertEquals(8.0, z.durchschnitt(), 0.001);
    }
}
