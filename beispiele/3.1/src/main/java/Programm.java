public class Programm {

    public static void main(String[] args) {
        try {
            Firma firma = new Firma("Spengergasse OG");
            firma.einstellen(new Angestellter("Susanne Huber", 1991, 20, 30, false));
            firma.einstellen(new Arbeiter("Karl Maier", 1989, 15, 40, true));
            firma.einstellen(new Freelancer("Sepp Gruber", 1981, 3, 300));
            firma.sortieren();
            firma.print();

            // Hier wird eine Exception geworfen (Geburtsjahr ungueltig) ...
            Arbeiter fehler = new Arbeiter("Karl Maier", 1789, 15, 40, false);

            // ... diese Zeilen werden daher nicht mehr ausgeführt
            firma.einstellen(fehler);
            firma.print();
        } catch (FirmaException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
    }
}
