import java.util.Random;

/*
  Personalverwaltung – gemeinsame Basisklasse aller Personen der Firma.
  abstract: es gibt keine Objekte vom Typ Person, nur von Unterklassen.
  Stand: Lektion 2.9 (abstrakte Klassen, Interfaces)
*/
public abstract class Person implements Comparable<Person> {

    private String name;
    private int geburtsjahr;
    private int personalNummer;
    private Firma firma;

    public Person() {
        setName("n/a");
        setGeburtsjahr(1970);
        personalNummerVergeben();
    }

    public Person(String name, int geburtsjahr) {
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

    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name.trim();
        } else {
            System.out.println("Fehler: ungueltiger Name: " + name);
            this.name = "n/a";
        }
    }

    public int getGeburtsjahr() {
        return geburtsjahr;
    }

    public void setGeburtsjahr(int geburtsjahr) {
        if (istGueltigesGeburtsjahr(geburtsjahr)) {
            this.geburtsjahr = geburtsjahr;
        } else {
            System.out.println("Fehler: ungueltiges Geburtsjahr: " + geburtsjahr);
            this.geburtsjahr = 1970;
        }
    }

    public int getPersonalNummer() {
        return personalNummer;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        if (firma != null) {
            if (firma.enthaelt(this)) {
                this.firma = firma;
            } else {
                System.out.println("Fehler: " + name + " ist nicht in Firma "
                                   + firma.getName() + " eingetragen.");
            }
        } else {
            if (this.firma == null || !this.firma.enthaelt(this)) {
                this.firma = null;
            } else {
                System.out.println("Fehler: " + name + " arbeitet noch in Firma "
                                   + this.firma.getName() + ".");
            }
        }
    }

    public int alterBerechnen(int aktuellesJahr) {
        return aktuellesJahr - geburtsjahr;
    }

    public String initialen() {
        int leerzeichen = name.indexOf(" ");
        if (leerzeichen > 0 && leerzeichen < name.length() - 1) {
            return name.charAt(0) + ". " + name.charAt(leerzeichen + 1) + ".";
        } else {
            return name.charAt(0) + ".";
        }
    }

    public String kuerzel() {
        if (name.length() >= 3) {
            return name.substring(0, 3).toUpperCase();
        } else {
            return name.toUpperCase();
        }
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
        if (other != null) {
            int c = this.getName().compareTo(other.getName());
            if (c == 0) {
                return this.getGeburtsjahr() - other.getGeburtsjahr();
            } else {
                return c;
            }
        } else {
            return -1; // spaeter loesen wir das mit Exceptions
        }
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
