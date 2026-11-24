import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VererbungTest {

    @Test
    void kostenWerdenUeberschrieben() {
        Angestellter susi = new Angestellter("Susanne Huber", 1991, 20, 30, false);
        Arbeiter karl = new Arbeiter("Karl Maier", 1989, 15, 40, true);
        Freelancer sepp = new Freelancer("Sepp Gruber", 1981, 3, 300);
        assertEquals(2400 + 5 * 20, susi.getKostenProMonat());
        assertEquals(2400 + 200, karl.getKostenProMonat());
        assertEquals(900, sepp.getKostenProMonat());
    }

    @Test
    void abstrakteMethodeFuerAllePersonen() {
        Person[] personen = {
            new Angestellter("Susanne Huber", 1991, 20, 30, true),
            new Freelancer("Sepp Gruber", 1981, 2, 1000)
        };
        int summe = 0;
        for (Person p : personen) {
            summe += p.getKostenProMonat();   // dynamische Bindung
        }
        assertEquals(2400 + 2000, summe);
    }
}
