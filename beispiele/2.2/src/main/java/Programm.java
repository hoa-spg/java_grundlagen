public class Programm {

    public static void main(String[] args) {
        Firma firma = new Firma("Spengergasse OG");
        firma.einstellen(new Mitarbeiter("Karl Maier", 1989, 15, 40));
        firma.einstellen(new Mitarbeiter("Susanne Huber", 1991, 20, 30));
        firma.einstellen(new Mitarbeiter("Sepp Gruber", 1981, 18, 38));
        firma.print();
        System.out.println();

        // neu in 2.2: Zeiterfassung mit einem Array
        Zeiterfassung november = new Zeiterfassung(30);
        november.eintragen(3, 8);
        november.eintragen(4, 9);
        november.eintragen(5, 7);
        november.print();
    }
}
