public class Programm {

    public static void main(String[] args) {
        try {
            Firma firma = Firma.readFromFile("firma.csv");
            firma.print();

            // Auswertungen mit Streams
            System.out.println("Summe Kosten: " + firma.summeKosten() + " Euro");
            System.out.println("Kosten > 2500 Euro: " + firma.personalMitKostenUeber(2500).size());
            System.out.println("Freelancer: " + firma.anzahlFreelancer());
            System.out.println("Namen: " + firma.namen());

            System.out.println("\nNach Kosten (absteigend):");
            firma.sortiertNachKosten().stream()
                 .map(p -> p.getKostenProMonat() + " Euro  " + p.getName())
                 .forEach(System.out::println);
        } catch (FirmaException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
    }
}
