import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MitarbeiterEqualsTest {

    @Test
    void inhaltsgleichheitVsReferenzgleichheit() throws FirmaException {
        Person p1 = new Angestellter("Peter Berger", 2001, 15, 40, false);
        Person p2 = p1;
        Person p3 = new Angestellter("Peter Berger", 2001, 15, 40, false);
        Person p4 = new Arbeiter("Peter Berger", 2001, 15, 40, false);

        assertTrue(p1 == p2);
        assertFalse(p1 == p3);
        assertTrue(p1.equals(p3));
        assertFalse(p1.equals(p4));          // andere Klasse
    }

    @Test
    void compareTo() throws FirmaException {
        Person a = new Freelancer("Anna", 1990, 1, 500);
        Person b = new Freelancer("Bert", 1990, 1, 500);
        Person a2 = new Freelancer("Anna", 1995, 1, 500);
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);
        assertTrue(a.compareTo(a2) < 0);     // gleicher Name -> Geburtsjahr
    }
}
