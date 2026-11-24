import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssoziationTest {

    private Firma spg;
    private Firma andere;
    private Person karl;

    @BeforeEach
    void setUp() throws FirmaException {
        spg = new Firma("Spengergasse OG");
        andere = new Firma("Andere GmbH");
        karl = new Arbeiter("Karl Maier", 1989, 15, 40, false);
    }

    @Test
    void einstellenSetztBeideSeiten() throws FirmaException {
        spg.einstellen(karl);
        assertTrue(spg.enthaelt(karl));
        assertSame(spg, karl.getFirma());
    }

    @Test
    void keineZweiFirmen() throws FirmaException {
        spg.einstellen(karl);
        assertThrows(FirmaException.class, () -> andere.einstellen(karl));
        assertSame(spg, karl.getFirma());
    }

    @Test
    void kuendigenLoestBeideSeiten() throws FirmaException {
        spg.einstellen(karl);
        spg.kuendigen(karl);
        assertNull(karl.getFirma());
        andere.einstellen(karl);
        assertSame(andere, karl.getFirma());
    }

    @Test
    void setFirmaVonAussenWirftException() {
        assertThrows(FirmaException.class, () -> karl.setFirma(spg));
    }
}
