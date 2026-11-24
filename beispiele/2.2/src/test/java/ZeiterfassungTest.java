import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZeiterfassungTest {

    private Zeiterfassung z;

    @BeforeEach
    void setUp() {
        z = new Zeiterfassung(30);
        z.eintragen(1, 8);
        z.eintragen(2, 6);
        z.eintragen(5, 10);
    }

    @Test
    void summeMaximumDurchschnitt() {
        assertEquals(24, z.summe());
        assertEquals(10, z.maximum());
        assertEquals(3, z.anzahlArbeitstage());
        assertEquals(8.0, z.durchschnitt(), 0.001);
    }

    @Test
    void ungueltigerTagWirdIgnoriert() {
        z.eintragen(31, 8);            // Monat hat nur 30 Tage
        z.eintragen(0, 8);
        assertEquals(24, z.summe());
        assertEquals(-1, z.stunden(31));
    }

    @Test
    void ungueltigeStundenWerdenIgnoriert() {
        z.eintragen(3, 13);
        assertEquals(0, z.stunden(3));
    }
}
