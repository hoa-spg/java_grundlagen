/*
  Personalverwaltung – ein Freelancer ist eine Person, aber kein
  Mitarbeiter: er wird pro Projekt bezahlt.
  Stand: Lektion 3.1 (Exceptions)
*/
public class Freelancer extends Person {

    private int anzahlProjekte;
    private int projektPauschale;

    public Freelancer() throws FirmaException {
        super();
        setAnzahlProjekte(1);
        setProjektPauschale(500);
    }

    public Freelancer(String name, int geburtsjahr, int anzahlProjekte,
                      int projektPauschale) throws FirmaException
    {
        super(name, geburtsjahr);
        setAnzahlProjekte(anzahlProjekte);
        setProjektPauschale(projektPauschale);
    }

    public int getAnzahlProjekte() {
        return anzahlProjekte;
    }

    public void setAnzahlProjekte(int anzahlProjekte) throws FirmaException {
        if (anzahlProjekte < 0) {
            throw new FirmaException("Fehler: ungueltige Anzahl an Projekten: "
                                     + anzahlProjekte);
        }
        this.anzahlProjekte = anzahlProjekte;
    }

    public int getProjektPauschale() {
        return projektPauschale;
    }

    public void setProjektPauschale(int projektPauschale) throws FirmaException {
        if (projektPauschale <= 200) {
            throw new FirmaException("Fehler: ungueltige Projektpauschale: "
                                     + projektPauschale);
        }
        this.projektPauschale = projektPauschale;
    }

    // abstrakte Methode aus Person muss hier implementiert werden!
    @Override
    public int getKostenProMonat() {
        return getAnzahlProjekte() * getProjektPauschale();
    }

    @Override
    public String toString() {
        return super.toString() + ", Freelancer: " + anzahlProjekte
               + " Projekte (Projektpauschale: " + projektPauschale + ")";
    }
}
