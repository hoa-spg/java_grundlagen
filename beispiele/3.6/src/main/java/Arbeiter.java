/*
  Personalverwaltung – ein Arbeiter ist ein Mitarbeiter (extends),
  Facharbeiter erhalten einen Zuschlag.
  Stand: Lektion 3.3 (CSV)
*/
public class Arbeiter extends Mitarbeiter {

    private boolean facharbeiter;

    public Arbeiter() throws FirmaException {
        super("n/a", 1970, 10, 40);
        setFacharbeiter(false);
    }

    public Arbeiter(String name, int geburtsjahr, int stundenlohn,
                    int anzahlWochenStunden, boolean facharbeiter)
                        throws FirmaException
    {
        super(name, geburtsjahr, stundenlohn, anzahlWochenStunden);
        setFacharbeiter(facharbeiter);
    }

    // CSV-Zeile: Arbeiter;Karl Maier;1989;15;40;Facharbeiter
    public Arbeiter(String csv) throws FirmaException {
        String[] tokens = csv.split(";");
        if (tokens.length != 6) {
            throw new FirmaException("Ungueltige Zeile: " + csv + ". 6 Spalten erwartet.");
        }
        setName(tokens[1]);
        setGeburtsjahr(zahlAusCsv(tokens[2], "Geburtsjahr (3. Spalte)", csv));
        setStundenlohn(zahlAusCsv(tokens[3], "Stundenlohn (4. Spalte)", csv));
        setAnzahlWochenStunden(zahlAusCsv(tokens[4], "Wochenstunden (5. Spalte)", csv));
        if (tokens[5].equals("Facharbeiter")) {
            setFacharbeiter(true);
        } else if (tokens[5].equals("Hilfsarbeiter")) {
            setFacharbeiter(false);
        } else {
            throw new FirmaException("Ungueltige Zeile: " + csv
                        + ". Facharbeiter/Hilfsarbeiter in 6. Spalte erwartet.");
        }
    }

    public boolean isFacharbeiter() {
        return facharbeiter;
    }

    public void setFacharbeiter(boolean facharbeiter) {
        this.facharbeiter = facharbeiter;
    }

    @Override
    public int getKostenProMonat() {
        int kosten = super.getKostenProMonat();
        if (isFacharbeiter()) {
            kosten += 200; // Facharbeiterzuschlag
        }
        return kosten;
    }

    @Override
    public String toCSVString() {
        return super.toCSVString() + ";"
               + (isFacharbeiter() ? "Facharbeiter" : "Hilfsarbeiter");
    }

    @Override
    public String toString() {
        String str = super.toString();
        str += isFacharbeiter() ? ", Facharbeiter" : ", Hilfsarbeiter";
        return str;
    }
}
