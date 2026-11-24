import java.util.ArrayList;
import java.util.Collections;

/*
  Personalverwaltung – eine Firma mit beliebig vielen Personen.
  Stand: Lektion 3.1: Fehler werden als FirmaException geworfen.
*/
public class Firma {

    private String name;
    private ArrayList<Person> personal;

    public Firma() throws FirmaException {
        personal = new ArrayList<>();
        setName("Default GmbH");
    }

    public Firma(String name) throws FirmaException {
        personal = new ArrayList<>();
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws FirmaException {
        if (name == null || name.isBlank()) {
            throw new FirmaException("Fehler: ungueltiger Name: " + name);
        }
        this.name = name;
    }

    public int anzahlMitarbeiter() {
        return personal.size();
    }

    public Person getPerson(int index) throws FirmaException {
        if (index < 0 || index >= personal.size()) {
            throw new FirmaException("Fehler: ungueltiger Index: " + index);
        }
        return personal.get(index);
    }

    public void einstellen(Person p) throws FirmaException {
        einfuegen(personal.size(), p);
    }

    public void einfuegen(int position, Person p) throws FirmaException {
        if (p == null) {
            throw new FirmaException("Fehler: ungueltiger Mitarbeiter: null");
        }
        if (position < 0 || position > personal.size()) {
            throw new FirmaException("Fehler: ungueltige Position " + position);
        }
        if (p.getFirma() != null) {
            throw new FirmaException("Fehler: " + p.getName() + " arbeitet schon bei "
                                     + p.getFirma().getName());
        }
        personal.add(position, p);
        p.setFirma(this);
    }

    public Person kuendigen(int position) throws FirmaException {
        if (position < 0 || position >= personal.size()) {
            throw new FirmaException("Fehler: ungueltige Position " + position);
        }
        Person p = personal.remove(position);
        p.setFirma(null);
        return p;
    }

    public Person kuendigen(String name) throws FirmaException {
        if (name == null) {
            throw new FirmaException("Fehler: name ist null.");
        }
        for (int i = 0; i < personal.size(); i++) {
            if (name.equals(personal.get(i).getName())) {
                return kuendigen(i);
            }
        }
        throw new FirmaException("Fehler: kein Mitarbeiter mit Namen " + name);
    }

    public boolean kuendigen(Person p) throws FirmaException {
        if (p == null) {
            throw new FirmaException("Fehler: ungueltiger Mitarbeiter: null");
        }
        if (personal.remove(p)) {
            p.setFirma(null);
            return true;
        }
        return false;    // nicht in der Firma: kein Fehler, nur "nichts passiert"
    }

    public boolean enthaelt(Person p) {
        return personal.contains(p);
    }

    public int summeKosten() {
        int summe = 0;
        for (Person p : personal) {
            summe += p.getKostenProMonat();
        }
        return summe;
    }

    public double durchschnittsKosten() {
        if (personal.isEmpty()) {
            return 0;
        }
        return (double) summeKosten() / personal.size();
    }

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
        for (Person p : personal) {
            System.out.println(auffuellen(p.getName(), 16) + p.kostenBalken());
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
        for (Person p : personal) {
            str += nr + ": " + p + "\n";
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
