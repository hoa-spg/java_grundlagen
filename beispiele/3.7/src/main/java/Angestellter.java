/*
  Personalverwaltung – ein Angestellter ist ein Mitarbeiter (extends),
  der eine Überstundenpauschale haben kann.
  Stand: Lektion 3.3 (CSV)
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

    // CSV-Zeile: Angestellter;Susanne Huber;1991;20;30;keine Ueberstundenpauschale
    public Angestellter(String csv) throws FirmaException {
        String[] tokens = csv.split(";");
        if (tokens.length != 6) {
            throw new FirmaException("Ungueltige Zeile: " + csv + ". 6 Spalten erwartet.");
        }
        setName(tokens[1]);
        setGeburtsjahr(zahlAusCsv(tokens[2], "Geburtsjahr (3. Spalte)", csv));
        setStundenlohn(zahlAusCsv(tokens[3], "Stundenlohn (4. Spalte)", csv));
        setAnzahlWochenStunden(zahlAusCsv(tokens[4], "Wochenstunden (5. Spalte)", csv));
        if (tokens[5].equals("Ueberstundenpauschale")) {
            setUeberstundenPauschale(true);
        } else if (tokens[5].equals("keine Ueberstundenpauschale")) {
            setUeberstundenPauschale(false);
        } else {
            throw new FirmaException("Ungueltige Zeile: " + csv
                        + ". Info zu Ueberstundenpauschale in 6. Spalte erwartet.");
        }
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
    public String toCSVString() {
        return super.toCSVString() + ";"
               + (hasUeberstundenPauschale() ? "Ueberstundenpauschale"
                                             : "keine Ueberstundenpauschale");
    }

    @Override
    public String toString() {
        String str = super.toString() + ", Angestellter";
        str += hasUeberstundenPauschale() ? " (Ueberstundenpauschale)"
                                          : " (keine Uestd.-Pauschale)";
        return str;
    }
}
