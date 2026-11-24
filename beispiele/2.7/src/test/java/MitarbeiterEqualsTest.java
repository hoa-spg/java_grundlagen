import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MitarbeiterEqualsTest {

    @Test
    void inhaltsgleichheitVsReferenzgleichheit() {
        Mitarbeiter m1 = new Mitarbeiter("Peter Berger", 2001);
        Mitarbeiter m2 = m1;
        Mitarbeiter m3 = new Mitarbeiter("Peter Berger", 2001);
        Mitarbeiter m4 = new Mitarbeiter("Lisa Berger", 2003);

        assertTrue(m1 == m2);
        assertFalse(m1 == m3);
        assertTrue(m1.equals(m3));
        assertTrue(m3.equals(m1));           // symmetrisch
        assertFalse(m1.equals(m4));
        assertFalse(m1.equals(null));
    }
}
