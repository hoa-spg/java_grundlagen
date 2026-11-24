/*
  Personalverwaltung – ein Arbeiter ist ein Mitarbeiter (extends),
  Facharbeiter erhalten einen Zuschlag.
  Stand: Lektion 2.8 (Vererbung)
*/
public class Arbeiter extends Mitarbeiter {

    private boolean facharbeiter;

    public Arbeiter() {
        super("n/a", 1970, 10, 40);
        setFacharbeiter(false);
    }

    public Arbeiter(String name, int geburtsjahr, int stundenlohn,
                    int anzahlWochenStunden, boolean facharbeiter)
    {
        super(name, geburtsjahr, stundenlohn, anzahlWochenStunden);
        setFacharbeiter(facharbeiter);
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
    public String toString() {
        String str = super.toString();
        str += isFacharbeiter() ? ", Facharbeiter" : ", Hilfsarbeiter";
        return str;
    }
}
