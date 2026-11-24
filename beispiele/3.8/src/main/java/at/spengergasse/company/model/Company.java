package at.spengergasse.company.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Die Firma als Singleton: in der ganzen Anwendung gibt es genau eine
 * Instanz, die über Company.getInstance() erreichbar ist.
 */
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CSV_DELIMITER = ";";

    private String companyName;
    private String companyAddress;
    private ArrayList<Employee> staff;

    // ---- Singleton ---------------------------------------------------------

    private static Company companyInstance = null;

    private Company() {
        staff = new ArrayList<>();
        companyName = "n/a";
        companyAddress = "n/a";
    }

    public static Company getInstance() {
        if (companyInstance == null) {
            companyInstance = new Company();
            try {
                companyInstance.fillDebugData();
            } catch (CompanyException e) {
                e.printStackTrace();
            }
        }
        return companyInstance;
    }

    public static void loadFromFile(InputStream is) throws CompanyException {
        companyInstance = Company.readFromFile(is);
    }

    public static void loadFromFile(String filename) throws CompanyException {
        companyInstance = Company.readFromFile(filename);
    }

    public static void clearInstance() {
        companyInstance = null;
    }

    // ---- Get-/Set-Methoden ---------------------------------------------------

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) throws CompanyException {
        if (companyName == null || companyName.isBlank()) {
            throw new CompanyException("Invalid company name: null or empty. ");
        }
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) throws CompanyException {
        if (companyAddress == null || companyAddress.isBlank()) {
            throw new CompanyException("Invalid company address: null or empty. ");
        }
        this.companyAddress = companyAddress;
    }

    // ---- Personal ------------------------------------------------------------

    public void hire(Employee emp) throws CompanyException {
        if (emp == null) {
            throw new CompanyException("Error: invalid employee: null. ");
        }
        if (emp.getId() == null) {
            emp.setId(Employee.idCounter++);
        }
        if (staff.contains(emp)) {
            throw new CompanyException("Error: employee already exists in company. ");
        }
        staff.add(emp);
    }

    public boolean fire(Employee emp) throws CompanyException {
        if (emp == null) {
            throw new CompanyException("Error: employee is null. ");
        }
        return staff.remove(emp);   // nicht vorhanden ist kein Fehler
    }

    /**
     * Alle (!) Mitarbeiter mit diesem Namen werden entfernt. Beim Entfernen
     * während des Durchlaufens muss ein Iterator verwendet werden (keine
     * for-each-Schleife -> ConcurrentModificationException).
     */
    public boolean fire(String firstName, String lastName) throws CompanyException {
        if (firstName == null || lastName == null) {
            throw new CompanyException("Error: invalid parameters "
                                       + "(null values are not allowed). ");
        }
        boolean removed = false;
        Iterator<Employee> it = staff.iterator();
        while (it.hasNext()) {
            Employee e = it.next();
            if (e.getLastName().equals(lastName) && e.getFirstName().equals(firstName)) {
                it.remove();
                removed = true;
            }
        }
        return removed;
    }

    public boolean fire(Long id) throws CompanyException {
        if (id == null) {
            throw new CompanyException("Error: provided id is null. ");
        }
        Iterator<Employee> it = staff.iterator();
        while (it.hasNext()) {
            if (id.equals(it.next().getId())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public int numberOfEmployees() {
        return staff.size();
    }

    public Employee getEmployee(int index) throws CompanyException {
        if (index < 0 || index >= staff.size()) {
            throw new CompanyException("Error: employee with index " + index
                                       + " does not exist. ");
        }
        return staff.get(index);
    }

    public Employee getEmployee(Long id) throws CompanyException {
        return staff.stream()
                    .filter(e -> e.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new CompanyException("Error: no employee with id "
                                                            + id + " exists. "));
    }

    // Kopie der Liste: von außen kann die interne Liste nicht verändert werden
    public List<Employee> getStaff() {
        return new ArrayList<>(staff);
    }

    // ---- Streams -------------------------------------------------------------

    public int numberOfManagers() {
        return (int) staff.stream()
                          .filter(e -> e.getRole().equals(Employee.Role.MANAGER))
                          .count();
    }

    public List<Employee> employeesSortedLastName() {
        ArrayList<Employee> sorted = new ArrayList<>(staff);
        sorted.sort(Comparator.comparing(Employee::getLastName));
        return sorted;
    }

    public List<Employee> employeesSortedSalaryDecreasing() {
        ArrayList<Employee> sorted = new ArrayList<>(staff);
        sorted.sort((Employee a, Employee b) -> (b.getSalary() - a.getSalary()));
        return sorted;
    }

    public List<Employee> employeesSortedSalaryDecreasing(int minSalary) {
        return staff.stream()
                    .filter(e -> (e.getSalary() > minSalary))
                    .sorted((Employee a, Employee b) -> (b.getSalary() - a.getSalary()))
                    .collect(Collectors.toList());
    }

    public void increaseSalaryByPercent(int percent) throws CompanyException {
        if (percent <= 0 || percent > 100) {
            throw new CompanyException("Error: invalid percentage provided. ");
        }
        // Im Lambda sind keine checked Exceptions erlaubt -> umverpacken
        staff.forEach(e -> {
            try {
                e.setSalary((int) (e.getSalary() * (100 + percent) / 100.0));
            } catch (CompanyException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // ---- Dateien -------------------------------------------------------------

    public void serialize(String filename) throws CompanyException {
        if (filename == null) {
            throw new CompanyException("Error: filename is null. ");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new CompanyException("Error: IO Exception. ", e);
        }
    }

    public static Company deserialize(String filename) throws CompanyException {
        if (filename == null) {
            throw new CompanyException("Error: filename is null. ");
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Company) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new CompanyException("Error: file not found: " + filename, e);
        } catch (IOException e) {
            throw new CompanyException("Error: IO Exception. ", e);
        } catch (ClassNotFoundException e) {
            throw new CompanyException("Error reading object from file. ", e);
        }
    }

    public void writeToFile(String filename) throws CompanyException {
        if (filename == null) {
            throw new CompanyException("Error: filename is null. ");
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(this.toCsvString());
        } catch (IOException e) {
            throw new CompanyException("Error: could not write " + filename, e);
        }
    }

    public static Company readFromFile(String filename) throws CompanyException {
        if (filename == null) {
            throw new CompanyException("Error: filename is null. ");
        }
        // Code-Wiederverwendung: Datei öffnen und an die InputStream-Variante übergeben
        try (InputStream is = new FileInputStream(filename)) {
            return readFromFile(is);
        } catch (FileNotFoundException e) {
            throw new CompanyException("Error: file not found: " + filename, e);
        } catch (IOException e) {
            throw new CompanyException("Error: could not read " + filename, e);
        }
    }

    // InputStream: funktioniert für Dateien UND für Uploads im Browser (Teil 4)
    public static Company readFromFile(InputStream is) throws CompanyException {
        if (is == null) {
            throw new CompanyException("Error: input stream is null. ");
        }
        Company company = new Company();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            if (line == null || line.contains(CSV_DELIMITER)) {
                throw new CompanyException("Invalid first line. "
                                           + "Single column with company name expected. ");
            }
            company.setCompanyName(line);
            line = br.readLine();
            if (line == null || line.contains(CSV_DELIMITER)) {
                throw new CompanyException("Invalid second line. "
                                           + "Single column with company address expected. ");
            }
            company.setCompanyAddress(line);
            line = br.readLine();
            while (line != null) {
                if (!line.isBlank()) {
                    company.hire(new Employee(line));
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new CompanyException("Error: could not read input. ", e);
        }
        return company;
    }

    public String toCsvString() {
        String csv = companyName + "\n" + companyAddress + "\n";
        for (Employee e : staff) {
            csv += e.toCsvString() + "\n";
        }
        return csv;
    }

    @Override
    public String toString() {
        final String line = "------------------------------------------------";
        String str = "Company Name: " + companyName + "\n";
        str += "Address: " + companyAddress + "\n";
        str += line + "\n";
        str += "Staff: " + "\n";
        for (Employee emp : staff) {
            str += emp + "\n";
        }
        str += line + "\n";
        return str;
    }

    public void print() {
        System.out.println(this);
    }

    // Testdaten, damit die Oberfläche nicht leer ist
    protected void fillDebugData() throws CompanyException {
        if (staff.isEmpty()) {
            setCompanyName("Spenger Inc.");
            setCompanyAddress("Spengergasse 20, 1050 Wien");
            hire(new Employee(1001L, "Donald", "Knuth", "knuth@spengergasse.at",
                    "+43 1 54615 101", LocalDate.of(1938, 1, 10),
                    Employee.Role.DEVELOPER, 12000, false));
            hire(new Employee(1002L, "Bjarne", "Stroustrup", "stroustrup@spengergasse.at",
                    "+43 1 54615 102", LocalDate.of(1950, 12, 30),
                    Employee.Role.MANAGER, 13000, false));
            hire(new Employee(1003L, "Dennis", "Ritchie", "ritchie@spengergasse.at",
                    "+43 1 54615 113", LocalDate.of(1941, 9, 9),
                    Employee.Role.DEVELOPER, 9000, true));
            hire(new Employee(1004L, "Linus", "Torvalds", "torvalds@spengergasse.at",
                    "+43 1 54615 121", LocalDate.of(1969, 12, 28),
                    Employee.Role.MANAGER, 7000, false));
            hire(new Employee(1005L, "Martin", "Fowler", "fowler@spengergasse.at",
                    "+43 1 54615 127", LocalDate.of(1963, 12, 18),
                    Employee.Role.ANALYST, 11000, false));
        }
    }
}
