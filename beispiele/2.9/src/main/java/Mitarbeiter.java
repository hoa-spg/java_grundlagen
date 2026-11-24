/*
  Personalverwaltung – ein Mitarbeiter ist eine Person mit Stundenlohn
  und fixer Wochenstundenanzahl. Ebenfalls abstrakt: konkret sind
  Angestellter und Arbeiter.
  Stand: Lektion 2.9 (abstrakte Klassen, Klassenhierarchie)
*/
public abstract class Mitarbeiter extends Person {

    private int stundenlohn;          // in Euro
    private int anzahlWochenStunden;

    public Mitarbeiter() {
        super();
        setStundenlohn(10);
        setAnzahlWochenStunden(40);
    }

    public Mitarbeiter(String name, int geburtsjahr, int stundenlohn,
                       int anzahlWochenStunden)
    {
        super(name, geburtsjahr);
        setStundenlohn(stundenlohn);
        setAnzahlWochenStunden(anzahlWochenStunden);
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

    public int getKostenProMonat(int anzahlWochen) {
        return anzahlWochen * stundenlohn * anzahlWochenStunden;
    }

    // implementiert die abstrakte Methode aus Person
    @Override
    public int getKostenProMonat() {
        return getKostenProMonat(4);
    }

    public boolean isTeilzeit() {
        return anzahlWochenStunden < 38;
    }

    public double stundenProTag() {
        return (double) anzahlWochenStunden / 5;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + stundenlohn + " Euro/h, "
               + anzahlWochenStunden + " h/Woche ("
               + (isTeilzeit() ? "Teilzeit" : "Vollzeit") + ")";
    }
}
