public class Programm {

    public static void main(String[] args) {
        try {
            // CSV-Datei im Projektordner einlesen (Arbeitsverzeichnis!)
            Firma firma = Firma.readFromFile("firma.csv");
            firma.print();

            firma.einstellen(new Arbeiter("Ali Yilmaz", 2000, 16, 40, false));
            firma.writeToFile("firma-neu.csv");
            firma.serialize("firma.ser");

            Firma kopie = Firma.deserialize("firma.ser");
            System.out.println();
            System.out.println("Aus firma.ser gelesen:");
            kopie.print();
        } catch (FirmaException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
    }
}
