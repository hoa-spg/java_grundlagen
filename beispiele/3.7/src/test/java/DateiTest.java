import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DateiTest {

    @TempDir
    Path tempDir;            // JUnit legt einen temporären Ordner an

    private Firma firma;

    @BeforeEach
    void setUp() throws FirmaException {
        firma = new Firma("Spengergasse OG");
        firma.einstellen(new Angestellter("Susanne Huber", 1991, 20, 30, false));
        firma.einstellen(new Arbeiter("Karl Maier", 1989, 15, 40, true));
        firma.einstellen(new Freelancer("Sepp Gruber", 1981, 3, 300));
    }

    @Test
    void csvZeilen() throws FirmaException {
        assertEquals("Arbeiter;Karl Maier;1989;15;40;Facharbeiter",
                     firma.getPerson(1).toCSVString());
        assertEquals("Freelancer;Sepp Gruber;1981;3;300",
                     firma.getPerson(2).toCSVString());
    }

    @Test
    void csvSchreibenUndLesen() throws FirmaException {
        String datei = tempDir.resolve("firma.csv").toString();
        firma.writeToFile(datei);
        Firma gelesen = Firma.readFromFile(datei);
        assertEquals("Spengergasse OG", gelesen.getName());
        assertEquals(3, gelesen.anzahlMitarbeiter());
        assertEquals(firma.summeKosten(), gelesen.summeKosten());
        assertEquals(firma.getPerson(0), gelesen.getPerson(0));   // equals
    }

    @Test
    void serialisierenUndDeserialisieren() throws FirmaException {
        String datei = tempDir.resolve("firma.ser").toString();
        firma.serialize(datei);
        assertTrue(new File(datei).exists());
        Firma kopie = Firma.deserialize(datei);
        assertEquals(3, kopie.anzahlMitarbeiter());
        assertSame(kopie, kopie.getPerson(0).getFirma());         // Assoziation bleibt
    }

    @Test
    void fehlerhafteZeilen() {
        assertThrows(FirmaException.class,
                     () -> new Arbeiter("Arbeiter;Karl;1989;15;40"));          // 5 Spalten
        assertThrows(FirmaException.class,
                     () -> new Arbeiter("Arbeiter;Karl;neunzehn;15;40;Facharbeiter"));
        assertThrows(FirmaException.class,
                     () -> Firma.readFromFile(tempDir.resolve("gibtsnicht.csv").toString()));
    }
}
