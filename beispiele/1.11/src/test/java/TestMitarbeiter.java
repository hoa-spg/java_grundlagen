/*
 * Ausprobieren mit main: erzeugt Mitarbeiter, ruft Methoden auf und
 * gibt die Ergebnisse aus. Die erwarteten Werte stehen im Kommentar -
 * ob sie stimmen, wird "mit dem Auge" kontrolliert.
 * Starten: grüner Pfeil neben main.
 */
void main() {
    System.out.println("--- Karl, Konstruktor mit 4 Parametern:");
    Mitarbeiter m1 = new Mitarbeiter("Karl Maier", 1989, 15, 40);
    m1.print();   // ... 15 Euro/h, 40 h/Woche (Vollzeit)
    System.out.println("Kosten pro Monat: " + m1.getKostenProMonat());     // 2400
    System.out.println("Kosten fuer 5 Wochen: " + m1.getKostenProMonat(5)); // 3000
    System.out.println("Alter im Jahr 2026: " + m1.alterBerechnen(2026));   // 37

    System.out.println("--- Susanne, Standardwerte und Set-Methoden:");
    Mitarbeiter m2 = new Mitarbeiter("Susanne Huber", 1991);
    m2.print();   // ... 10 Euro/h, 40 h/Woche (Standardwerte)
    m2.setStundenlohn(20);
    m2.setAnzahlWochenStunden(30);
    m2.print();   // ... 20 Euro/h, 30 h/Woche
    System.out.println("Kosten pro Monat: " + m2.getKostenProMonat());     // 2400
    System.out.println("Teilzeit? " + m2.isTeilzeit());                    // true
    System.out.println("Stunden pro Tag: " + m2.stundenProTag());          // 6.0

    System.out.println("--- Ungueltige Werte:");
    Mitarbeiter m3 = new Mitarbeiter(null, 1789, 5, 70);   // 4 Fehlermeldungen
    m3.print();   // Mitarbeiter: n/a (Geburtsjahr: 1970), 10 Euro/h, 40 h/Woche (Vollzeit)
}
