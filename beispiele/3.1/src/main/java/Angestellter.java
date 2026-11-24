/*
  Personalverwaltung – ein Angestellter ist ein Mitarbeiter (extends),
  der eine Überstundenpauschale haben kann.
  Stand: Lektion 3.1 (Exceptions)
*/
public class Angestellter extends Mitarbeiter {

    private boolean ueberstundenPauschale;

    public Angestellter() throws FirmaException {
        super("n/a", 1970, 10, 40);
        setUeberstundenPauschale(false);
    }

    public Angestellter(String name, int geburtsjahr, int stundenlohn,
                        int anzahlWochenStunden, boolean ueberstundenPauschale)
                            throws FirmaException
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
