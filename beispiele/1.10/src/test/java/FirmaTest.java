import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FirmaTest {

    private Firma f;
    private Mitarbeiter karl;
    private Mitarbeiter susi;

    @BeforeEach
    public void setUp() {
        f = new Firma("Spengergasse OG");
        karl = new Mitarbeiter("Karl Maier", 1989, 15, 40);
        susi = new Mitarbeiter("Susanne Huber", 1991, 20, 30);
    }

    @Test
    public void testEinstellenUndKosten() {
        f.einstellen(karl);
        f.einstellen(susi);
        assertEquals(2, f.anzahlMitarbeiter());
        assertEquals(2400 + 2400, f.summeKosten());
    }

    @Test
    public void testMaximalDrei() {
        f.einstellen(new Mitarbeiter("Anna", 1990));
        f.einstellen(new Mitarbeiter("Bert", 1991));
        f.einstellen(new Mitarbeiter("Cleo", 1992));
        f.einstellen(new Mitarbeiter("Dora", 1993));   // kein Platz mehr
        assertEquals(3, f.anzahlMitarbeiter());
    }

    @Test
    public void testKuendigen() {
        f.einstellen(karl);
        f.kuendigen(susi);           // nicht in der Firma -> keine Aenderung
        assertEquals(1, f.anzahlMitarbeiter());
        f.kuendigen(karl);
        assertEquals(0, f.anzahlMitarbeiter());
    }

    @Test
    public void testKuendigenNachName() {
        f.einstellen(karl);
        f.einstellen(susi);
        // neuer String mit gleichem Inhalt -> equals findet ihn trotzdem
        String name = new String("Karl Maier");
        f.kuendigen(name);
        assertEquals(1, f.anzahlMitarbeiter());
    }
}
