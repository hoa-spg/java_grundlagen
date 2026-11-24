/*
  Personalverwaltung – Arbeitsstunden eines Monats.
  Stand: Lektion 2.2 (Arrays): ein Array mit einem Platz pro Tag.
*/
public class Zeiterfassung {

    private int[] stunden;   // stunden[0] = 1. Tag, stunden[1] = 2. Tag, ...

    public Zeiterfassung() {
        stunden = new int[31];
    }

    public Zeiterfassung(int anzahlTage) {
        if (anzahlTage >= 28 && anzahlTage <= 31) {
            stunden = new int[anzahlTage];
        } else {
            System.out.println("Fehler: ungueltige Anzahl an Tagen: " + anzahlTage);
            stunden = new int[31];
        }
    }

    public int anzahlTage() {
        return stunden.length;
    }

    // tag: 1 .. anzahlTage (wie im Kalender), Index im Array: tag - 1
    public void eintragen(int tag, int anzahlStunden) {
        if (tag >= 1 && tag <= stunden.length) {
            if (anzahlStunden >= 0 && anzahlStunden <= 12) {
                stunden[tag - 1] = anzahlStunden;
            } else {
                System.out.println("Fehler: ungueltige Stundenanzahl: " + anzahlStunden);
            }
        } else {
            System.out.println("Fehler: ungueltiger Tag: " + tag);
        }
    }

    public int stunden(int tag) {
        if (tag >= 1 && tag <= stunden.length) {
            return stunden[tag - 1];
        } else {
            System.out.println("Fehler: ungueltiger Tag: " + tag);
            return -1;
        }
    }

    public int summe() {
        int summe = 0;
        for (int i = 0; i < stunden.length; i++) {
            summe += stunden[i];
        }
        return summe;
    }

    public int maximum() {
        int max = stunden[0];
        for (int i = 1; i < stunden.length; i++) {
            if (stunden[i] > max) {
                max = stunden[i];
            }
        }
        return max;
    }

    public int anzahlArbeitstage() {
        int anzahl = 0;
        for (int i = 0; i < stunden.length; i++) {
            if (stunden[i] > 0) {
                anzahl++;
            }
        }
        return anzahl;
    }

    // Durchschnitt nur über Tage, an denen gearbeitet wurde
    public double durchschnitt() {
        int tage = anzahlArbeitstage();
        if (tage > 0) {
            return (double) summe() / tage;
        } else {
            return 0;
        }
    }

    public void print() {
        System.out.println("Zeiterfassung (" + stunden.length + " Tage)");
        for (int i = 0; i < stunden.length; i++) {
            if (stunden[i] > 0) {
                System.out.println("  Tag " + (i + 1) + ": " + stunden[i] + " h");
            }
        }
        System.out.println("Summe: " + summe() + " h, Maximum: " + maximum()
                           + " h, Durchschnitt: " + durchschnitt() + " h");
    }
}
