import java.util.Random;

/*
  Personalverwaltung – die Klasse Mitarbeiter modelliert eine Person,
  die in einer Firma arbeitet.
  Stand: Lektion 2.6 (beidseitige Assoziation, equals)
*/
public class Mitarbeiter {

    // Objekteigenschaften (Attribute)
    private String name;
    private int geburtsjahr;
    private int stundenlohn;          // in Euro
    private int anzahlWochenStunden;
    private int personalNummer;       // zufaellig, 4-stellig
    private Firma firma;              // Firma, in der der Mitarbeiter arbeitet (oder null)

    // Konstruktoren
    public Mitarbeiter() {
        setName("n/a");
        setGeburtsjahr(1970);
        setStundenlohn(10);
        setAnzahlWochenStunden(40);
        personalNummerVergeben();
    }

    public Mitarbeiter(String name, int geburtsjahr) {
        setName(name);
        setGeburtsjahr(geburtsjahr);
        setStundenlohn(10);
        setAnzahlWochenStunden(40);
        personalNummerVergeben();
    }

    public Mitarbeiter(String name, int geburtsjahr, int stundenlohn,
                       int anzahlWochenStunden)
    {
        setName(name);
        setGeburtsjahr(geburtsjahr);
        setStundenlohn(stundenlohn);
        setAnzahlWochenStunden(anzahlWochenStunden);
        personalNummerVergeben();
    }

    private void personalNummerVergeben() {
        Random rnd = new Random();
        personalNummer = 1000 + rnd.nextInt(9000);   // 1000 .. 9999
    }

    // statische Methode: braucht kein Objekt, verwendet keine Attribute
    public static boolean istGueltigesGeburtsjahr(int jahr) {
        return jahr >= 1900 && jahr <= 2050;
    }

    public int getPersonalNummer() {
        return personalNummer;
    }

    public Firma getFirma() {
        return firma;
    }

    // Wird von Firma.einfuegen/kuendigen aufgerufen und hält beide Seiten konsistent
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

    // Get-/Set-Methoden mit Parameterprüfungen
    public String getName() {
        return name;
    }

    public void setName(String name) {
        // isBlank() liefert true bei "" und bei reinen Leerzeichen
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

    public int getStundenlohn() {
        return stundenlohn;
    }

    public void setStundenlohn(int stundenlohn) {
        if (stundenlohn > 7 && stundenlohn < 1000) {
            this.stundenlohn = stundenlohn;
        } else {
            System.out.println("Fehler: ungueltiger Stundenlohn: " + stundenlohn);
            this.stundenlohn = 10;
        }
    }

    public int getAnzahlWochenStunden() {
        return anzahlWochenStunden;
    }

    public void setAnzahlWochenStunden(int anzahlWochenStunden) {
        if (anzahlWochenStunden > 0 && anzahlWochenStunden <= 50) {
            this.anzahlWochenStunden = anzahlWochenStunden;
        } else {
            System.out.println("Fehler: ungueltige Wochenstundenanzahl: "
                               + anzahlWochenStunden);
            this.anzahlWochenStunden = 40;
        }
    }

    // Weitere Methoden
    public int alterBerechnen(int aktuellesJahr) {
        return aktuellesJahr - getGeburtsjahr();
    }

    public int getKostenProMonat(int anzahlWochen) {
        return anzahlWochen * stundenlohn * anzahlWochenStunden;
    }

    public int getKostenProMonat() {
        return getKostenProMonat(4);
    }

    public boolean isTeilzeit() {
        return anzahlWochenStunden < 38;
    }

    public double stundenProTag() {
        // Typkonvertierung: sonst ganzzahlige Division (37 / 5 = 7)
        double stunden = (double) anzahlWochenStunden / 5;
        return stunden;
    }

    // "Karl Maier" -> "K. M."
    public String initialen() {
        int leerzeichen = name.indexOf(" ");
        if (leerzeichen > 0 && leerzeichen < name.length() - 1) {
            return name.charAt(0) + ". " + name.charAt(leerzeichen + 1) + ".";
        } else {
            return name.charAt(0) + ".";
        }
    }

    // "Karl Maier" -> "KAR"
    public String kuerzel() {
        if (name.length() >= 3) {
            return name.substring(0, 3).toUpperCase();
        } else {
            return name.toUpperCase();
        }
    }

    // ein '#' pro angefangenen 500 Euro Kosten, z. B. 2400 Euro -> "#####"
    public String kostenBalken() {
        String balken = "";
        int kosten = getKostenProMonat();
        int i = 0;
        while (i < kosten) {
            balken += "#";
            i += 500;
        }
        return balken;
    }

    // Inhaltsgleichheit: gleicher Name und gleiches Geburtsjahr
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (this == other) return true;
        if (this.getClass() != other.getClass()) return false;

        Mitarbeiter m = (Mitarbeiter) other;
        return this.name.equals(m.name) && this.geburtsjahr == m.geburtsjahr;
    }

    @Override
    public String toString() {
        String beschaeftigung = "Vollzeit";
        if (isTeilzeit()) {
            beschaeftigung = "Teilzeit";
        }
        String str = "#" + personalNummer + " " + kuerzel() + " " + name + " (Geburtsjahr: "
                     + geburtsjahr + "), " + stundenlohn + " Euro/h, "
                     + anzahlWochenStunden + " h/Woche (" + beschaeftigung + ")";
        if (firma != null) {
            str += ", Firma: " + firma.getName();
        }
        return str;
    }

    public void print() {
        System.out.println(this);
    }
}
