import java.util.Random;

/*
  Personalverwaltung – gemeinsame Basisklasse aller Personen der Firma.
  abstract: es gibt keine Objekte vom Typ Person, nur von Unterklassen.
  Stand: Lektion 3.1 (Exceptions statt Konsolenausgaben)
*/
public abstract class Person implements Comparable<Person> {

    private String name;
    private int geburtsjahr;
    private int personalNummer;
    private Firma firma;

    public Person() throws FirmaException {
        setName("n/a");
        setGeburtsjahr(1970);
        personalNummerVergeben();
    }

    public Person(String name, int geburtsjahr) throws FirmaException {
        setName(name);
        setGeburtsjahr(geburtsjahr);
        personalNummerVergeben();
    }

    private void personalNummerVergeben() {
        Random rnd = new Random();
        personalNummer = 1000 + rnd.nextInt(9000);
    }

    public static boolean istGueltigesGeburtsjahr(int jahr) {
        return jahr >= 1900 && jahr <= 2050;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws FirmaException {
        if (name == null || name.isBlank()) {
            throw new FirmaException("Fehler: ungueltiger Name: " + name);
        }
        this.name = name.trim();
    }

    public int getGeburtsjahr() {
        return geburtsjahr;
    }

    public void setGeburtsjahr(int geburtsjahr) throws FirmaException {
        if (!istGueltigesGeburtsjahr(geburtsjahr)) {
            throw new FirmaException("Fehler: ungueltiges Geburtsjahr: " + geburtsjahr);
        }
        this.geburtsjahr = geburtsjahr;
    }

    public int getPersonalNummer() {
        return personalNummer;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) throws FirmaException {
        if (firma == null) {                      // Zuordnung lösen
            if (this.firma != null && this.firma.enthaelt(this)) {
                throw new FirmaException("Fehler: " + name + " arbeitet noch in Firma "
                                         + this.firma.getName() + ".");
            }
            this.firma = null;
            return;
        }
        if (!firma.enthaelt(this)) {              // Zuordnung setzen
            throw new FirmaException("Fehler: " + name + " ist nicht in Firma "
                                     + firma.getName() + " eingetragen.");
        }
        this.firma = firma;
    }

    public int alterBerechnen(int aktuellesJahr) {
        return aktuellesJahr - geburtsjahr;
    }

    public String initialen() {
        int leerzeichen = name.indexOf(" ");
        if (leerzeichen <= 0 || leerzeichen >= name.length() - 1) {
            return name.charAt(0) + ".";
        }
        return name.charAt(0) + ". " + name.charAt(leerzeichen + 1) + ".";
    }

    public String kuerzel() {
        if (name.length() < 3) {
            return name.toUpperCase();
        }
        return name.substring(0, 3).toUpperCase();
    }

    // abstrakt: jede konkrete Unterklasse MUSS die Kosten berechnen können
    public abstract int getKostenProMonat();

    // verwendet die abstrakte Methode – funktioniert für alle Unterklassen
    public String kostenBalken() {
        String balken = "";
        for (int i = 0; i < getKostenProMonat(); i += 500) {
            balken += "#";
        }
        return balken;
    }

    // Comparable: zuerst nach Name, bei gleichem Namen nach Geburtsjahr
    @Override
    public int compareTo(Person other) {
        if (other == null) {
            // compareTo darf keine checked Exception werfen (Interface!),
            // daher eine unchecked Exception
            throw new NullPointerException("Vergleich mit null nicht moeglich");
        }
        int c = this.getName().compareTo(other.getName());
        if (c != 0) {
            return c;
        }
        return this.getGeburtsjahr() - other.getGeburtsjahr();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (this == other) return true;
        if (this.getClass() != other.getClass()) return false;

        Person p = (Person) other;
        return this.name.equals(p.name) && this.geburtsjahr == p.geburtsjahr;
    }

    @Override
    public String toString() {
        String str = "#" + personalNummer + " " + kuerzel() + " " + name
                     + " (Geb.Jahr: " + geburtsjahr + ")";
        if (firma != null) {
            str += ", Firma: " + firma.getName();
        }
        return str;
    }

    public void print() {
        System.out.println(this);
    }
}
