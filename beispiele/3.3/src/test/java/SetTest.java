import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

class SetTest {

    @Test
    void hashSetErkenntInhaltsgleichePersonen() throws FirmaException {
        HashSet<Person> set = new HashSet<>();
        set.add(new Freelancer("Sepp Gruber", 1981, 3, 300));
        set.add(new Freelancer("Sepp Gruber", 1981, 5, 900));   // equals -> Duplikat
        set.add(new Freelancer("Sepp Gruber", 1982, 3, 300));
        assertEquals(2, set.size());
    }

    @Test
    void equalsUndHashCodeKonsistent() throws FirmaException {
        Person a = new Angestellter("Anna Novak", 2001, 14, 20, true);
        Person b = new Angestellter("Anna Novak", 2001, 30, 40, false);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void treeSetIstSortiert() throws FirmaException {
        TreeSet<Person> set = new TreeSet<>();
        set.add(new Freelancer("Sepp Gruber", 1981, 3, 300));
        set.add(new Arbeiter("Karl Maier", 1989, 15, 40, false));
        set.add(new Angestellter("Anna Novak", 2001, 14, 20, true));
        assertEquals("Anna Novak", set.first().getName());
        assertEquals("Sepp Gruber", set.last().getName());
    }

    @Test
    void firmaVerhindertDuplikate() throws FirmaException {
        Firma firma = new Firma("Spengergasse OG");
        firma.einstellen(new Arbeiter("Karl Maier", 1989, 15, 40, false));
        assertThrows(FirmaException.class,
                     () -> firma.einstellen(new Arbeiter("Karl Maier", 1989, 20, 38, true)));
        assertEquals(1, firma.anzahlMitarbeiter());
    }

    @Test
    void nachnamen() throws FirmaException {
        Firma firma = new Firma("Spengergasse OG");
        firma.einstellen(new Arbeiter("Karl Maier", 1989, 15, 40, false));
        firma.einstellen(new Angestellter("Eva Maier", 1995, 18, 38, true));
        firma.einstellen(new Freelancer("Sepp Gruber", 1981, 3, 300));
        assertEquals(2, firma.nachnamen().size());
        assertTrue(firma.nachnamen().contains("Gruber"));
    }
}
