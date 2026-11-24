import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/*
  Personalverwaltung – eine Firma mit beliebig vielen Personen.
  Stand: Lektion 3.6: Streams.
  Seit 3.5: Lambdas; 3.3: Serialisierung/CSV; 3.2: keine Duplikate; 3.1: FirmaException.
*/
public class Firma implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private ArrayList<Person> personal;

    public Firma() throws FirmaException {
        personal = new ArrayList<>();
        setName("Default GmbH");
    }

    public Firma(String name) throws FirmaException {
        personal = new ArrayList<>();
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws FirmaException {
        if (name == null || name.isBlank()) {
            throw new FirmaException("Fehler: ungueltiger Name: " + name);
        }
        this.name = name;
    }

    public int anzahlMitarbeiter() {
        return personal.size();
    }

    public Person getPerson(int index) throws FirmaException {
        if (index < 0 || index >= personal.size()) {
            throw new FirmaException("Fehler: ungueltiger Index: " + index);
        }
        return personal.get(index);
    }

    public void einstellen(Person p) throws FirmaException {
        einfuegen(personal.size(), p);
    }

    public void einfuegen(int position, Person p) throws FirmaException {
        if (p == null) {
            throw new FirmaException("Fehler: ungueltiger Mitarbeiter: null");
        }
        if (position < 0 || position > personal.size()) {
            throw new FirmaException("Fehler: ungueltige Position " + position);
        }
        if (personal.contains(p)) {          // verwendet equals!
            throw new FirmaException("Fehler: " + p.getName() + " (" + p.getGeburtsjahr()
                                     + ") ist bereits in der Firma.");
        }
        if (p.getFirma() != null) {
            throw new FirmaException("Fehler: " + p.getName() + " arbeitet schon bei "
                                     + p.getFirma().getName());
        }
        personal.add(position, p);
        p.setFirma(this);
    }

    public Person kuendigen(int position) throws FirmaException {
        if (position < 0 || position >= personal.size()) {
            throw new FirmaException("Fehler: ungueltige Position " + position);
        }
        Person p = personal.remove(position);
        p.setFirma(null);
        return p;
    }

    public Person kuendigen(String name) throws FirmaException {
        if (name == null) {
            throw new FirmaException("Fehler: name ist null.");
        }
        for (int i = 0; i < personal.size(); i++) {
            if (name.equals(personal.get(i).getName())) {
                return kuendigen(i);
            }
        }
        throw new FirmaException("Fehler: kein Mitarbeiter mit Namen " + name);
    }

    public boolean kuendigen(Person p) throws FirmaException {
        if (p == null) {
            throw new FirmaException("Fehler: ungueltiger Mitarbeiter: null");
        }
        if (personal.remove(p)) {
            p.setFirma(null);
            return true;
        }
        return false;    // nicht in der Firma: kein Fehler, nur "nichts passiert"
    }

    public boolean enthaelt(Person p) {
        return personal.contains(p);
    }

    public int summeKosten() {
        // Stream statt Schleife: jede Person -> Kosten (int), dann aufsummieren
        return personal.stream()
                       .mapToInt(p -> p.getKostenProMonat())
                       .sum();
    }

    public double durchschnittsKosten() {
        if (personal.isEmpty()) {
            return 0;
        }
        return (double) summeKosten() / personal.size();
    }

    // TreeSet: sortiert (compareTo), ohne Duplikate – eine Kopie für die Ausgabe
    public TreeSet<Person> personalSortiert() {
        return new TreeSet<>(personal);
    }

    // HashSet: jeder Nachname nur einmal (Reihenfolge nicht festgelegt)
    public HashSet<String> nachnamen() {
        HashSet<String> namen = new HashSet<>();
        for (Person p : personal) {
            String n = p.getName();
            namen.add(n.substring(n.lastIndexOf(" ") + 1));
        }
        return namen;
    }

    // ---- Lambdas (3.5) und Streams (3.6) ----------------------------------

    // Kopie, absteigend nach Kosten sortiert (Lambda als Comparator)
    public List<Person> sortiertNachKosten() {
        ArrayList<Person> sortiert = new ArrayList<>(personal);
        sortiert.sort((a, b) -> b.getKostenProMonat() - a.getKostenProMonat());
        return sortiert;
    }

    // Kopie, nach Geburtsjahr sortiert (Methodenreferenz)
    public List<Person> sortiertNachGeburtsjahr() {
        ArrayList<Person> sortiert = new ArrayList<>(personal);
        sortiert.sort(Comparator.comparing(Person::getGeburtsjahr));
        return sortiert;
    }

    public List<Person> personalMitKostenUeber(int minKosten) {
        return personal.stream()
                       .filter(p -> p.getKostenProMonat() > minKosten)
                       .collect(Collectors.toList());
    }

    public long anzahlFreelancer() {
        return personal.stream()
                       .filter(p -> p instanceof Freelancer)
                       .count();
    }

    public List<String> namen() {
        return personal.stream()
                       .map(Person::getName)
                       .sorted()
                       .collect(Collectors.toList());
    }

    public void sortieren() {
        Collections.sort(personal);
    }

    // ---- Serialisierung (Binärformat) ------------------------------------

    public void serialize(String filename) throws FirmaException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new FirmaException("I/O Error: " + e.getMessage(), e);
        }
    }

    public static Firma deserialize(String filename) throws FirmaException {
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            return (Firma) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new FirmaException("Datei nicht gefunden: " + filename, e);
        } catch (IOException e) {
            throw new FirmaException("I/O Error: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new FirmaException("Fehler bei der Konvertierung. " + e.getMessage(), e);
        }
    }

    // ---- CSV-Textdateien ---------------------------------------------------

    public String toCSVString() {
        String s = getName() + "\n";
        for (Person p : personal) {
            s += p.toCSVString() + "\n";
        }
        return s;
    }

    public void writeToFile(String filename) throws FirmaException {
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw))
        {
            bw.write(this.toCSVString());
        } catch (IOException e) {
            throw new FirmaException("I/O Error: " + e.getMessage(), e);
        }
    }

    public static Firma readFromFile(String filename) throws FirmaException {
        Firma firma;
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr))
        {
            String line = br.readLine();
            if (line == null || line.isBlank()) {
                throw new FirmaException("Ungueltige Eingabedatei: "
                                         + "ungueltiger Firmenname in erster Zeile");
            }
            firma = new Firma(line);
            line = br.readLine();
            while (line != null) {
                if (!line.isBlank()) {
                    Person p;
                    if (line.startsWith("Arbeiter;")) {
                        p = new Arbeiter(line);
                    } else if (line.startsWith("Angestellter;")) {
                        p = new Angestellter(line);
                    } else if (line.startsWith("Freelancer;")) {
                        p = new Freelancer(line);
                    } else {
                        throw new FirmaException("Ungueltiger Mitarbeitertyp in Zeile "
                                                 + line);
                    }
                    firma.einstellen(p);
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new FirmaException("Datei " + filename + " nicht gefunden.", e);
        } catch (IOException e) {
            throw new FirmaException("I/O Error: " + e.getMessage(), e);
        }
        return firma;
    }

    // ---- Ausgabe -------------------------------------------------------------

    private String linie(char zeichen, int laenge) {
        String s = "";
        for (int i = 0; i < laenge; i++) {
            s += zeichen;
        }
        return s;
    }

    private String auffuellen(String text, int breite) {
        String s = text;
        while (s.length() < breite) {
            s += " ";
        }
        return s;
    }

    public void printKostenBalken() {
        System.out.println("Kosten pro Monat (# = 500 Euro)");
        System.out.println(linie('=', 47));
        for (Person p : personal) {
            System.out.println(auffuellen(p.getName(), 16) + p.kostenBalken());
        }
        System.out.println(linie('=', 47));
    }

    @Override
    public String toString() {
        String str = "Firma: " + name + "\n" + linie('-', 47) + "\n";
        if (personal.isEmpty()) {
            str += "Keine Mitarbeiter vorhanden.\n";
        }
        int nr = 1;
        for (Person p : personal) {
            str += nr + ": " + p + "\n";
            nr++;
        }
        str += linie('-', 47) + "\n";
        str += "Kosten pro Monat: " + summeKosten() + " Euro (" + personal.size()
               + " Personen)";
        return str;
    }

    public void print() {
        System.out.println(this);
    }
}
