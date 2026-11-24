public class Programm {

    public static void main(String[] args) {
        try {
            Firma firma = new Firma("Spengergasse OG");
            firma.einstellen(new Angestellter("Susanne Huber", 1991, 20, 30, false));
            firma.einstellen(new Arbeiter("Karl Maier", 1989, 15, 40, true));
            firma.einstellen(new Freelancer("Sepp Gruber", 1981, 3, 300));
            firma.einstellen(new Angestellter("Eva Maier", 1995, 18, 38, true));
            firma.print();

            System.out.println("Sortiert (TreeSet):");
            for (Person p : firma.personalSortiert()) {
                System.out.println("  " + p.getName());
            }
            System.out.println("Nachnamen (HashSet): " + firma.nachnamen());

            // Duplikat: gleicher Name und gleiches Geburtsjahr -> Exception
            firma.einstellen(new Arbeiter("Karl Maier", 1989, 20, 38, false));

            System.out.println("Diese Zeile wird nicht mehr erreicht.");
        } catch (FirmaException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
    }
}
