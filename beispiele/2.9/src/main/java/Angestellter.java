/*
  Personalverwaltung – ein Angestellter ist ein Mitarbeiter (extends),
  der eine Überstundenpauschale haben kann.
  Stand: Lektion 2.8 (Vererbung)
*/
public class Angestellter extends Mitarbeiter {

    private boolean ueberstundenPauschale;

    public Angestellter() {
        super("n/a", 1970, 10, 40);
        setUeberstundenPauschale(false);
    }

    public Angestellter(String name, int geburtsjahr, int stundenlohn,
                        int anzahlWochenStunden, boolean ueberstundenPauschale)
    {
        super(name, geburtsjahr, stundenlohn, anzahlWochenStunden);
        setUeberstundenPauschale(ueberstundenPauschale);
    }

    public boolean hasUeberstundenPauschale() {
        return ueberstundenPauschale;
    }

    public void setUeberstundenPauschale(boolean ueberstundenPauschale) {
        this.ueberstundenPauschale = ueberstundenPauschale;
    }

    @Override
    public int getKostenProMonat() {
        int kosten = super.getKostenProMonat();
        if (!hasUeberstundenPauschale()) {
            // Überstunden müssen extra bezahlt werden
            kosten += 5 * getStundenlohn();
        }
        return kosten;
    }

    @Override
    public String toString() {
        String str = super.toString() + ", Angestellter";
        str += hasUeberstundenPauschale() ? " (Ueberstundenpauschale)"
                                          : " (keine Uestd.-Pauschale)";
        return str;
    }
}
