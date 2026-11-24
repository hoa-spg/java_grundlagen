public class Programm {

    public static void main(String[] args) {
        Firma firma = new Firma("Spengergasse OG");
        firma.einstellen(new Mitarbeiter("Susanne Huber", 1991, 20, 30));
        firma.einstellen(new Mitarbeiter("Karl Maier", 1989, 15, 40));
        firma.einstellen(new Mitarbeiter("Sepp Gruber", 1981, 18, 38));
        firma.einfuegen(0, new Mitarbeiter("Anna Novak", 2001, 14, 20));
        firma.print();

        firma.sortierenNachName();
        firma.kuendigen("Sepp Gruber");
        System.out.println();
        firma.print();
        System.out.println();
        firma.printKostenBalken();

        Zeiterfassung zeiten = new Zeiterfassung(20);
        zeiten.hinzufuegen(8);
        zeiten.hinzufuegen(9);
        zeiten.hinzufuegen(7);
        zeiten.print();
        System.out.println("Median: " + zeiten.median() + " h");
    }
}
