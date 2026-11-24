import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirmaTest {

    private Firma firma;
    private Mitarbeiter karl;
    private Mitarbeiter susi;

    @BeforeEach
    void setUp() {
        firma = new Firma("Spengergasse OG");
        karl = new Mitarbeiter("Karl Maier", 1989, 15, 40);
        susi = new Mitarbeiter("Susanne Huber", 1991, 20, 30);
    }

    @Test
    void einstellenUndKosten() {
        firma.einstellen(karl);
        firma.einstellen(susi);
        assertEquals(2, firma.anzahlMitarbeiter());
        assertEquals(4800, firma.summeKosten());
    }

    @Test
    void kuendigenNachReferenzUndName() {
        firma.einstellen(karl);
        firma.einstellen(susi);
        firma.kuendigen(karl);
        assertEquals(1, firma.anzahlMitarbeiter());
        firma.kuendigen("Susanne Huber");
        assertEquals(0, firma.anzahlMitarbeiter());
    }
}
