import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreamTest {

    private Firma firma;

    @BeforeEach
    void setUp() throws FirmaException {
        firma = new Firma("Spengergasse OG");
        firma.einstellen(new Angestellter("Susanne Huber", 1991, 20, 30, false)); // 2500
        firma.einstellen(new Arbeiter("Karl Maier", 1989, 15, 40, true));         // 2600
        firma.einstellen(new Freelancer("Sepp Gruber", 1981, 3, 300));            //  900
    }

    @Test
    void summeMitStream() {
        assertEquals(6000, firma.summeKosten());
    }

    @Test
    void filternZaehlenAbbilden() {
        assertEquals(2, firma.personalMitKostenUeber(1000).size());
        assertEquals(1, firma.anzahlFreelancer());
        assertEquals(List.of("Karl Maier", "Sepp Gruber", "Susanne Huber"), firma.namen());
    }
}
