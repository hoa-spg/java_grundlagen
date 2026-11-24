public class Programm {

    public static void main(String[] args) {
        Firma firma = new Firma("Spengergasse OG");
        // Polymorphismus: Angestellte und Arbeiter in EINER Firma
        firma.einstellen(new Angestellter("Susanne Huber", 1991, 20, 30, false));
        firma.einstellen(new Arbeiter("Karl Maier", 1989, 15, 40, true));
        firma.einstellen(new Arbeiter("Sepp Gruber", 1981, 18, 38, false));
        firma.einstellen(new Angestellter("Anna Novak", 2001, 14, 20, true));
        firma.sortierenNachName();
        firma.print();
        System.out.println();
        firma.printKostenBalken();
    }
}
