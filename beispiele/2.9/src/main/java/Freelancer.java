/*
  Personalverwaltung – ein Freelancer ist eine Person, aber kein
  Mitarbeiter: er wird pro Projekt bezahlt.
  Stand: Lektion 2.9 (Klassenhierarchie)
*/
public class Freelancer extends Person {

    private int anzahlProjekte;
    private int projektPauschale;

    public Freelancer() {
        super();
        setAnzahlProjekte(1);
        setProjektPauschale(500);
    }

    public Freelancer(String name, int geburtsjahr, int anzahlProjekte,
                      int projektPauschale)
    {
        super(name, geburtsjahr);
        setAnzahlProjekte(anzahlProjekte);
        setProjektPauschale(projektPauschale);
    }

    public int getAnzahlProjekte() {
        return anzahlProjekte;
    }

    public void setAnzahlProjekte(int anzahlProjekte) {
        if (anzahlProjekte >= 0) {
            this.anzahlProjekte = anzahlProjekte;
        } else {
            System.out.println("Fehler: ungueltige Anzahl an Projekten: "
                               + anzahlProjekte);
        }
    }

    public int getProjektPauschale() {
        return projektPauschale;
    }

    public void setProjektPauschale(int projektPauschale) {
        if (projektPauschale > 200) {
            this.projektPauschale = projektPauschale;
        } else {
            System.out.println("Fehler: ungueltige Projektpauschale: "
                               + projektPauschale);
            this.projektPauschale = 500;
        }
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
