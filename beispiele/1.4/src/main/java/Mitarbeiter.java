/*
  Personalverwaltung – die Klasse Mitarbeiter modelliert eine Person,
  die in einer Firma arbeitet.
  Stand: Lektion 1.4 (Datentypen)
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

    // Get-/Set-Methoden
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGeburtsjahr() {
        return geburtsjahr;
    }

    public void setGeburtsjahr(int geburtsjahr) {
        this.geburtsjahr = geburtsjahr;
    }

    public int getStundenlohn() {
        return stundenlohn;
    }

    public void setStundenlohn(int stundenlohn) {
        this.stundenlohn = stundenlohn;
    }

    public int getAnzahlWochenStunden() {
        return anzahlWochenStunden;
    }

    public void setAnzahlWochenStunden(int anzahlWochenStunden) {
        this.anzahlWochenStunden = anzahlWochenStunden;
    }

    // Weitere Methoden
    public int alterBerechnen(int aktuellesJahr) {
        return aktuellesJahr - getGeburtsjahr();
    }

    public void print() {
        System.out.println("Mitarbeiter: " + name + " (Geburtsjahr: " + geburtsjahr
                           + "), " + stundenlohn + " Euro/h, "
                           + anzahlWochenStunden + " h/Woche");
    }
}
