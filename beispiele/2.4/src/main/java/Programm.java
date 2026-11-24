public class Programm {

    public static void main(String[] args) {
        Firma firma = new Firma("Spengergasse OG");
        firma.einstellen(new Mitarbeiter("Karl Maier", 1989, 15, 40));
        firma.einstellen(new Mitarbeiter("Susanne Huber", 1991, 20, 30));
        firma.einstellen(new Mitarbeiter("Sepp Gruber", 1981, 18, 38));
        firma.print();
        System.out.println();

        // Zeiterfassung: Buchungen hinzufügen, einfügen, entfernen
        Zeiterfassung zeiten = new Zeiterfassung(20);
        zeiten.hinzufuegen(8);
        zeiten.hinzufuegen(9);
        zeiten.hinzufuegen(7);
        zeiten.einfuegen(1, 4);
        zeiten.entfernen(3);
        zeiten.print();
        System.out.println("Median: " + zeiten.median() + " h");
    }
}
