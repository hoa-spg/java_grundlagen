import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Programm {

    public static void main(String[] args) {
        try {
            Firma firma = Firma.readFromFile("firma.csv");
            firma.print();

            System.out.println("\nNach Kosten (absteigend):");
            for (Person p : firma.sortiertNachKosten()) {
                System.out.println("  " + p.getKostenProMonat() + " Euro  " + p.getName());
            }

            // forEach mit Lambda
            System.out.println("\nNach Geburtsjahr:");
            firma.sortiertNachGeburtsjahr().forEach(p -> System.out.println("  " + p.getName()));
        } catch (FirmaException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // Sortieren von Zahlen mit Lambdas
        ArrayList<Integer> values = new ArrayList<>(
                Arrays.asList(3, 1, 5, 6, 12, 3, 5, -3, 44, 0, 1, 5));
        Collections.sort(values, (a, b) -> (a - b)); // aufsteigend
        System.out.println("Values: " + values);
        Collections.sort(values, (a, b) -> (b - a)); // absteigend
        System.out.println("Values: " + values);
    }
}
