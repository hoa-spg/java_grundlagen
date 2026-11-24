/*
  Personalverwaltung – ein Mitarbeiter ist eine Person mit Stundenlohn
  und fixer Wochenstundenanzahl. Ebenfalls abstrakt: konkret sind
  Angestellter und Arbeiter.
  Stand: Lektion 3.1 (Exceptions)
*/
public abstract class Mitarbeiter extends Person {

    private int stundenlohn;          // in Euro
    private int anzahlWochenStunden;

    public Mitarbeiter() throws FirmaException {
        super();
        setStundenlohn(10);
        setAnzahlWochenStunden(40);
    }

    public Mitarbeiter(String name, int geburtsjahr, int stundenlohn,
                       int anzahlWochenStunden) throws FirmaException
    {
        super(name, geburtsjahr);
        setStundenlohn(stundenlohn);
        setAnzahlWochenStunden(anzahlWochenStunden);
    }

    public int getStundenlohn() {
        return stundenlohn;
    }

    public void setStundenlohn(int stundenlohn) throws FirmaException {
        if (stundenlohn <= 7 || stundenlohn >= 1000) {
            throw new FirmaException("Fehler: ungueltiger Stundenlohn: " + stundenlohn);
        }
        this.stundenlohn = stundenlohn;
    }

    public int getAnzahlWochenStunden() {
        return anzahlWochenStunden;
    }

    public void setAnzahlWochenStunden(int anzahlWochenStunden) throws FirmaException {
        if (anzahlWochenStunden <= 0 || anzahlWochenStunden > 50) {
            throw new FirmaException("Fehler: ungueltige Wochenstundenanzahl: "
                                     + anzahlWochenStunden);
        }
        this.anzahlWochenStunden = anzahlWochenStunden;
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
