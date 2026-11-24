/*
  Personalverwaltung – eine Firma mit einem Array an Mitarbeitern.
  Stand: Lektion 2.5 (Objekt-Arrays)
*/
public class Firma {

    private String name;
    private Mitarbeiter[] personal;
    private int anzahl;              // gültige Einträge, keine Lücken

    public Firma() {
        setName("n/a");
        personal = new Mitarbeiter[10];
        anzahl = 0;
    }

    public Firma(String name) {
        setName(name);
        personal = new Mitarbeiter[10];
        anzahl = 0;
    }

    public Firma(String name, int maxMitarbeiter) {
        setName(name);
        if (maxMitarbeiter > 0) {
            personal = new Mitarbeiter[maxMitarbeiter];
        } else {
            System.out.println("Fehler: ungueltige Groesse: " + maxMitarbeiter);
            personal = new Mitarbeiter[10];
        }
        anzahl = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            System.out.println("Fehler: ungueltiger Name: " + name);
            this.name = "n/a";
        }
    }

    public int anzahlMitarbeiter() {
        return anzahl;
    }

    public int anzahlFreiePlaetze() {
        return personal.length - anzahl;
    }

    public Mitarbeiter getMitarbeiter(int index) {
        if (index >= 0 && index < anzahl) {
            return personal[index];
        } else {
            return null;
        }
    }

    public boolean einstellen(Mitarbeiter m) {
        return einfuegen(anzahl, m);     // Spezialfall von einfuegen
    }

    public boolean einfuegen(int position, Mitarbeiter m) {
        if (m == null) {
            System.out.println("Fehler: Mitarbeiter ist null.");
            return false;
        }
        if (position < 0 || position > anzahl) {
            System.out.println("Fehler: ungueltige Position " + position);
            return false;
        }
        if (anzahlFreiePlaetze() == 0) {
            System.out.println("Fehler: kein freier Platz vorhanden.");
            return false;
        }
        for (int i = anzahl; i > position; i--) {
            personal[i] = personal[i - 1];
        }
        personal[position] = m;
        anzahl++;
        return true;
    }

    public Mitarbeiter kuendigen(int position) {
        if (position >= 0 && position < anzahl) {
            Mitarbeiter m = personal[position];
            for (int i = position; i < anzahl - 1; i++) {
                personal[i] = personal[i + 1];
            }
            anzahl--;
            personal[anzahl] = null;     // letzten Platz freigeben
            return m;
        } else {
            System.out.println("Fehler: ungueltige Position " + position);
            return null;
        }
    }

    // Code-Wiederverwendung: suchen und dann kuendigen(int) aufrufen
    public Mitarbeiter kuendigen(String name) {
        if (name != null) {
            for (int i = 0; i < anzahl; i++) {
                if (name.equals(personal[i].getName())) {
                    return kuendigen(i);
                }
            }
            System.out.println("Fehler: kein Mitarbeiter mit Namen " + name);
        } else {
            System.out.println("Fehler: name ist null.");
        }
        return null;
    }

    public boolean kuendigen(Mitarbeiter m) {
        for (int i = 0; i < anzahl; i++) {
            if (personal[i] == m) {
                kuendigen(i);
                return true;
            }
        }
        return false;
    }

    public int summeKosten() {
        int summe = 0;
        for (int i = 0; i < anzahl; i++) {
            summe += personal[i].getKostenProMonat();
        }
        return summe;
    }

    public double durchschnittsKosten() {
        if (anzahl > 0) {
            return (double) summeKosten() / anzahl;
        } else {
            return 0;
        }
    }

    // Bubble-Sort nach Namen (String.compareTo)
    public void sortierenNachName() {
        boolean getauscht = true;
        while (getauscht) {
            getauscht = false;
            for (int i = 0; i < anzahl - 1; i++) {
                if (personal[i].getName().compareTo(personal[i + 1].getName()) > 0) {
                    vertausche(i, i + 1);
                    getauscht = true;
                }
            }
        }
    }

    // private: nur für sortierenNachName gedacht
    private void vertausche(int i, int j) {
        Mitarbeiter tmp = personal[i];
        personal[i] = personal[j];
        personal[j] = tmp;
    }

    private String linie(char zeichen, int laenge) {
        String s = "";
        for (int i = 0; i < laenge; i++) {
            s += zeichen;
        }
        return s;
    }

    private String auffuellen(String text, int breite) {
        String s = text;
        while (s.length() < breite) {
            s += " ";
        }
        return s;
    }

    public void printKostenBalken() {
        System.out.println("Kosten pro Monat (# = 500 Euro)");
        System.out.println(linie('=', 47));
        for (int i = 0; i < anzahl; i++) {
            System.out.println(auffuellen(personal[i].getName(), 16)
                               + personal[i].kostenBalken());
        }
        System.out.println(linie('=', 47));
    }

    @Override
    public String toString() {
        String str = "Firma: " + name + "\n" + linie('-', 47) + "\n";
        for (int i = 0; i < anzahl; i++) {
            str += (i + 1) + ": " + personal[i] + "\n";
        }
        str += linie('-', 47) + "\n";
        str += "Kosten pro Monat: " + summeKosten() + " Euro (" + anzahl + " Mitarbeiter, "
               + anzahlFreiePlaetze() + " Plaetze frei)";
        return str;
    }

    public void print() {
        System.out.println(this);
    }
}
