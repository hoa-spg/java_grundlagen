/*
  Personalverwaltung – die Klasse Mitarbeiter modelliert eine Person,
  die in einer Firma arbeitet.
  Stand: Lektion 1.8 (Strings)
*/
public class Mitarbeiter {

    // Objekteigenschaften (Attribute)
    private String name;
    private int geburtsjahr;
    private int stundenlohn;          // in Euro
    private int anzahlWochenStunden;

    // Konstruktoren
    public Mitarbeiter() {
        setName("n/a");
        setGeburtsjahr(1970);
        setStundenlohn(10);
        setAnzahlWochenStunden(40);
    }

    public Mitarbeiter(String name, int geburtsjahr) {
        setName(name);
        setGeburtsjahr(geburtsjahr);
        setStundenlohn(10);
        setAnzahlWochenStunden(40);
    }

    public Mitarbeiter(String name, int geburtsjahr, int stundenlohn,
                       int anzahlWochenStunden)
    {
        setName(name);
        setGeburtsjahr(geburtsjahr);
        setStundenlohn(stundenlohn);
        setAnzahlWochenStunden(anzahlWochenStunden);
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
        if (geburtsjahr >= 1900 && geburtsjahr <= 2050) {
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

    public void print() {
        String beschaeftigung;
        if (isTeilzeit()) {
            beschaeftigung = "Teilzeit";
        } else {
            beschaeftigung = "Vollzeit";
        }
        System.out.println(kuerzel() + " " + name + " (Geburtsjahr: " + geburtsjahr
                           + "), " + stundenlohn + " Euro/h, "
                           + anzahlWochenStunden + " h/Woche (" + beschaeftigung + ")");
    }
}
