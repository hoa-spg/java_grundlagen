import java.util.ArrayList;
import java.util.Collections;

/*
  Personalverwaltung – eine Firma mit beliebig vielen Mitarbeitern.
  Stand: Lektion 2.9: Personal vom Typ Person (Polymorphismus), sortieren()
*/
public class Firma {

    private String name;
    private ArrayList<Person> personal;

    public Firma() {
        setName("n/a");
        personal = new ArrayList<>();
    }

    public Firma(String name) {
        setName(name);
        personal = new ArrayList<>();
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
        return personal.size();
    }

    public Person getPerson(int index) {
        if (index >= 0 && index < personal.size()) {
            return personal.get(index);
        } else {
            return null;
        }
    }

    public boolean einstellen(Person m) {
        return einfuegen(personal.size(), m);
    }

    public boolean einfuegen(int position, Person m) {
        if (m == null) {
            System.out.println("Fehler: Person ist null.");
            return false;
        }
        if (position < 0 || position > personal.size()) {
            System.out.println("Fehler: ungueltige Position " + position);
            return false;
        }
        if (m.getFirma() != null) {
            System.out.println("Fehler: " + m.getName() + " arbeitet schon bei "
                               + m.getFirma().getName());
            return false;
        }
        personal.add(position, m);       // ArrayList verschiebt selbst
        m.setFirma(this);
        return true;
    }

    public Person kuendigen(int position) {
        if (position >= 0 && position < personal.size()) {
            Person m = personal.remove(position);
            m.setFirma(null);
            return m;
        } else {
            System.out.println("Fehler: ungueltige Position " + position);
            return null;
        }
    }

    public Person kuendigen(String name) {
        if (name != null) {
            for (int i = 0; i < personal.size(); i++) {
                if (name.equals(personal.get(i).getName())) {
                    return kuendigen(i);
                }
            }
            System.out.println("Fehler: kein Mitarbeiter mit Namen " + name);
        } else {
            System.out.println("Fehler: name ist null.");
        }
        return null;
    }

    public boolean kuendigen(Person m) {
        if (m != null && personal.remove(m)) {
            m.setFirma(null);
            return true;
        }
        return false;
    }

    // Achtung: contains vergleicht mit equals (Inhaltsgleichheit)
    public boolean enthaelt(Person m) {
        return personal.contains(m);
    }

    public int summeKosten() {
        int summe = 0;
        for (Person m : personal) {         // for-each-Schleife
            summe += m.getKostenProMonat();
        }
        return summe;
    }

    public double durchschnittsKosten() {
        if (!personal.isEmpty()) {
            return (double) summeKosten() / personal.size();
        } else {
            return 0;
        }
    }

    // sortiert mit compareTo aus Person (Interface Comparable)
    public void sortieren() {
        Collections.sort(personal);
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
        for (Person m : personal) {
            System.out.println(auffuellen(m.getName(), 16) + m.kostenBalken());
        }
        System.out.println(linie('=', 47));
    }

    @Override
    public String toString() {
        String str = "Firma: " + name + "\n" + linie('-', 47) + "\n";
        if (personal.isEmpty()) {
            str += "Keine Mitarbeiter vorhanden.\n";
        }
        int nr = 1;
        for (Person m : personal) {
            str += nr + ": " + m + "\n";
            nr++;
        }
        str += linie('-', 47) + "\n";
        str += "Kosten pro Monat: " + summeKosten() + " Euro (" + personal.size()
               + " Personen)";
        return str;
    }

    public void print() {
        System.out.println(this);
    }
}
