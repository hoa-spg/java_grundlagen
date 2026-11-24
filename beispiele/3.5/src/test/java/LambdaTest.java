import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LambdaTest {

    private Firma firma;

    @BeforeEach
    void setUp() throws FirmaException {
        firma = new Firma("Spengergasse OG");
        firma.einstellen(new Angestellter("Susanne Huber", 1991, 20, 30, false)); // 2500
        firma.einstellen(new Arbeiter("Karl Maier", 1989, 15, 40, true));         // 2600
        firma.einstellen(new Freelancer("Sepp Gruber", 1981, 3, 300));            //  900
    }

    @Test
    void sortierenMitLambda() {
        List<Person> sortiert = firma.sortiertNachKosten();
        assertEquals("Karl Maier", sortiert.get(0).getName());
        assertEquals("Sepp Gruber", sortiert.get(2).getName());
    }

    @Test
    void sortierenMitMethodenreferenz() {
        List<Person> sortiert = firma.sortiertNachGeburtsjahr();
        assertEquals(1981, sortiert.get(0).getGeburtsjahr());
        assertEquals(1991, sortiert.get(2).getGeburtsjahr());
    }

    @Test
    void originalBleibtUnveraendert() {
        firma.sortiertNachKosten();
        assertEquals(6000, firma.summeKosten());
        assertEquals(3, firma.sortiertNachGeburtsjahr().size());
    }
}
