/*
 * Testklasse zur Firma: Einstellen, Kündigen und Kosten.
 */
void main() {
    testEinstellen();
}

void testEinstellen() {
    Firma f = new Firma("Spengergasse OG");
    Mitarbeiter karl = new Mitarbeiter("Karl Maier", 1989, 15, 40);
    Mitarbeiter susi = new Mitarbeiter("Susanne Huber", 1991, 20, 30);
    Mitarbeiter sepp = new Mitarbeiter("Sepp Gruber", 1981, 18, 38);
    Mitarbeiter anna = new Mitarbeiter("Anna Novak", 2001, 14, 20);

    f.einstellen(karl);
    f.einstellen(susi);
    f.einstellen(sepp);
    f.einstellen(anna);      // erwartet: Fehler, kein Platz mehr
    f.print();               // erwartet: 3 Mitarbeiter, Kosten 7536

    f.kuendigen(susi);
    f.einstellen(anna);      // anna bekommt Platz 2
    f.print();
    System.out.println("Anzahl: " + f.anzahlMitarbeiter()); // 3
}
