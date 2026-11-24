/*
  Personalverwaltung – Arbeitseinsätze (Buchungen) in Stunden.
  Stand: Lektion 2.3: statt fester Tage eine "Liste" von Buchungen.
  Das Array hat eine Maximalgröße, "anzahl" zählt die gültigen Einträge.
*/
public class Zeiterfassung {

    private int[] buchungen;
    private int anzahl;      // Anzahl gültiger Werte = Index des nächsten freien Platzes

    public Zeiterfassung() {
        buchungen = new int[50];
        anzahl = 0;
    }

    public Zeiterfassung(int maxBuchungen) {
        if (maxBuchungen > 0) {
            buchungen = new int[maxBuchungen];
        } else {
            System.out.println("Fehler: ungueltige Groesse: " + maxBuchungen);
            buchungen = new int[50];
        }
        anzahl = 0;
    }

    public int getAnzahl() {
        return anzahl;
    }

    // ACHTUNG: keine Methode getBuchungen(), die das Array herausgibt!
    // Sonst könnte man von außen ungültige Werte hineinschreiben.

    public int wert(int index) {
        if (index >= 0 && index < anzahl) {
            return buchungen[index];
        } else {
            System.out.println("Fehler: ungueltiger Index: " + index);
            return -1;
        }
    }

    public void hinzufuegen(int stunden) {
        if (stunden >= 1 && stunden <= 12) {
            if (anzahl < buchungen.length) {
                buchungen[anzahl++] = stunden;
            } else {
                System.out.println("Fehler: Zeiterfassung voll!");
            }
        } else {
            System.out.println("Fehler: ungueltige Stundenanzahl: " + stunden);
        }
    }

    public void einfuegen(int index, int stunden) {
        if (stunden >= 1 && stunden <= 12) {
            if (index >= 0 && index <= anzahl) {
                if (anzahl < buchungen.length) {
                    // von hinten nach vorne um einen Platz nach hinten schieben
                    for (int i = anzahl; i > index; i--) {
                        buchungen[i] = buchungen[i - 1];
                    }
                    buchungen[index] = stunden;
                    anzahl++;
                } else {
                    System.out.println("Fehler: Zeiterfassung voll!");
                }
            } else {
                System.out.println("Fehler: ungueltiger Index: " + index);
            }
        } else {
            System.out.println("Fehler: ungueltige Stundenanzahl: " + stunden);
        }
    }

    public void entfernen(int index) {
        if (index >= 0 && index < anzahl) {
            // nachfolgende Werte um einen Platz nach vorne ziehen
            for (int i = index; i < anzahl - 1; i++) {
                buchungen[i] = buchungen[i + 1];
            }
            anzahl--;
        } else {
            System.out.println("Fehler: ungueltiger Index: " + index);
        }
    }

    public int summe() {
        int summe = 0;
        for (int i = 0; i < anzahl; i++) {
            summe += buchungen[i];
        }
        return summe;
    }

    public int maximum() {
        if (anzahl == 0) {
            return 0;
        }
        int max = buchungen[0];
        for (int i = 1; i < anzahl; i++) {
            if (buchungen[i] > max) {
                max = buchungen[i];
            }
        }
        return max;
    }

    public double durchschnitt() {
        if (anzahl > 0) {
            return (double) summe() / anzahl;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        String str = "Buchungen: ";
        if (anzahl > 0) {
            for (int i = 0; i < anzahl; i++) {
                if (i > 0) {
                    str += ", ";
                }
                str += buchungen[i] + " h";
            }
            str += " (Summe: " + summe() + " h)";
        } else {
            str += "keine Buchungen vorhanden.";
        }
        return str;
    }

    public void print() {
        System.out.println(this);   // ruft toString() auf
    }
}
