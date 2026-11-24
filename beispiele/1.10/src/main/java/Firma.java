/*
  Personalverwaltung – eine Firma mit bis zu drei Mitarbeitern.
  Stand: Lektion 1.10 (Schleifen)
*/
public class Firma {

    private String name;

    // Objektreferenzen auf Mitarbeiter; null bedeutet: Platz frei
    private Mitarbeiter ma1;
    private Mitarbeiter ma2;
    private Mitarbeiter ma3;

    public Firma() {
        setName("n/a");
    }

    public Firma(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        } else {
            System.out.println("Fehler: name darf nicht null sein");
            this.name = "n/a";
        }
    }

    public void einstellen(Mitarbeiter m) {
        if (m != null) {
            if (ma1 == null) {
                ma1 = m;
            } else if (ma2 == null) {
                ma2 = m;
            } else if (ma3 == null) {
                ma3 = m;
            } else {
                System.out.println("Fehler: kein freier Platz in der Firma!");
            }
        } else {
            System.out.println("Fehler: Parameter m ist null!");
        }
    }

    public void kuendigen(Mitarbeiter m) {
        if (m != null) {
            // Vergleich der Objektreferenzen: ist es DERSELBE Mitarbeiter?
            if (ma1 == m) {
                ma1 = null;
            } else if (ma2 == m) {
                ma2 = null;
            } else if (ma3 == m) {
                ma3 = null;
            } else {
                System.out.println("Fehler: Mitarbeiter arbeitet nicht in der Firma.");
            }
        } else {
            System.out.println("Fehler: Parameter m ist null!");
        }
    }

    public void kuendigen(String name) {
        if (name != null) {
            // Achtung: Strings immer mit equals vergleichen, nie mit ==
            if (ma1 != null && name.equals(ma1.getName())) {
                ma1 = null;
            } else if (ma2 != null && name.equals(ma2.getName())) {
                ma2 = null;
            } else if (ma3 != null && name.equals(ma3.getName())) {
                ma3 = null;
            } else {
                System.out.println("Fehler: kein Mitarbeiter mit Namen " + name);
            }
        } else {
            System.out.println("Fehler: name ist null!");
        }
    }

    public int anzahlMitarbeiter() {
        int anzahl = 0;
        if (ma1 != null) {
            anzahl++;
        }
        if (ma2 != null) {
            anzahl++;
        }
        if (ma3 != null) {
            anzahl++;
        }
        return anzahl;
    }

    public int summeKosten() {
        int summe = 0;
        if (ma1 != null) {
            summe = summe + ma1.getKostenProMonat();
        }
        if (ma2 != null) {
            summe = summe + ma2.getKostenProMonat();
        }
        if (ma3 != null) {
            summe = summe + ma3.getKostenProMonat();
        }
        return summe;
    }

    // Hilfsmethode: liefert eine Linie aus "laenge" gleichen Zeichen
    private String linie(char zeichen, int laenge) {
        String s = "";
        for (int i = 0; i < laenge; i++) {
            s += zeichen;
        }
        return s;
    }

    // Name auf eine fixe Breite mit Leerzeichen auffuellen (fuer Tabellen)
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
        if (ma1 != null) {
            System.out.println(auffuellen(ma1.getName(), 16) + ma1.kostenBalken());
        }
        if (ma2 != null) {
            System.out.println(auffuellen(ma2.getName(), 16) + ma2.kostenBalken());
        }
        if (ma3 != null) {
            System.out.println(auffuellen(ma3.getName(), 16) + ma3.kostenBalken());
        }
        System.out.println(linie('=', 47));
    }

    public void print() {
        System.out.println("Firma: " + name);
        System.out.println(linie('-', 47));
        System.out.print("Platz 1: ");
        if (ma1 != null) {
            ma1.print();
        } else {
            System.out.println("--frei--");
        }
        System.out.print("Platz 2: ");
        if (ma2 != null) {
            ma2.print();
        } else {
            System.out.println("--frei--");
        }
        System.out.print("Platz 3: ");
        if (ma3 != null) {
            ma3.print();
        } else {
            System.out.println("--frei--");
        }
        System.out.println(linie('-', 47));
        System.out.println("Kosten pro Monat: " + summeKosten() + " Euro");
    }
}
