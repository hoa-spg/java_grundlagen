import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactoryTest {

    @Test
    void erzeugtPassendeUnterklasse() throws FirmaException {
        Person a = Person.ausCsv("Arbeiter;Karl Maier;1989;15;40;Facharbeiter");
        Person b = Person.ausCsv("Angestellter;Susanne Huber;1991;20;30;keine Ueberstundenpauschale");
        Person c = Person.ausCsv("Freelancer;Sepp Gruber;1981;3;300");
        assertInstanceOf(Arbeiter.class, a);
        assertInstanceOf(Angestellter.class, b);
        assertInstanceOf(Freelancer.class, c);
        assertEquals("Karl Maier", a.getName());
    }

    @Test
    void unbekannterTypWirftException() {
        assertThrows(FirmaException.class, () -> Person.ausCsv("Praktikant;Max;2005;1;1"));
        assertThrows(FirmaException.class, () -> Person.ausCsv(""));
    }
}
