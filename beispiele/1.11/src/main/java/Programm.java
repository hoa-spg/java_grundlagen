/**
 * Einstiegspunkt der Personalverwaltung.
 * Starten: grüner Pfeil neben main (oder Shift+F10).
 */
public class Programm {

    public static void main(String[] args) {
        Firma firma = new Firma("Spengergasse OG");
        firma.einstellen(new Mitarbeiter("Karl Maier", 1989, 15, 40));
        firma.einstellen(new Mitarbeiter("Susanne Huber", 1991, 20, 30));
        firma.einstellen(new Mitarbeiter("Sepp Gruber", 1981, 18, 38));

        firma.print();
        System.out.println();
        firma.printKostenBalken();

        // statische Methode: Aufruf über den Klassennamen
        System.out.println("1789 gueltig? " + Mitarbeiter.istGueltigesGeburtsjahr(1789));
    }
}
