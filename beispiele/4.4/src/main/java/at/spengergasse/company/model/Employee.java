package at.spengergasse.company.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Model-Klasse für einen Mitarbeiter – Basis für die Weboberfläche (Teil 4).
 * Die Get-/Set-Methoden folgen der JavaBeans-Konvention, damit der
 * Vaadin-Binder die Attribute automatisch an Formularfelder binden kann.
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int MAX_SALARY = 100000;
    private static final int MIN_SALARY = 1500;
    public static Long idCounter = 1000L;

    // konsistent halten mit dem CSV-Konstruktor
    public enum Role { ADMIN, ASSISTANT, DEVELOPER, ANALYST, MANAGER }

    private Long id;               // null = noch nicht eingestellt
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private Role role;
    private int salary;            // Bruttogehalt pro Monat
    private boolean subcontractor;

    public Employee() {
        id = null;
        firstName = "[first name not set]";
        lastName = "[last name not set]";
        email = "office@spengergasse.at";
        phone = "+43 1 54615 0";
        dateOfBirth = LocalDate.of(1970, 1, 1);
        role = Role.ADMIN;
        salary = 2000;
        subcontractor = false;
    }

    public Employee(String firstName, String lastName, String email,
                    String phone, LocalDate dateOfBirth,
                    Role role, int salary, boolean subcontractor)
            throws CompanyException
    {
        this(Employee.idCounter, firstName, lastName, email, phone, dateOfBirth,
             role, salary, subcontractor);
    }

    public Employee(Long id, String firstName, String lastName, String email,
                    String phone, LocalDate dateOfBirth,
                    Role role, int salary, boolean subcontractor)
            throws CompanyException
    {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(phone);
        setDateOfBirth(dateOfBirth);
        setRole(role);
        setSalary(salary);
        setSubcontractor(subcontractor);
    }

    // CSV: 1001;Donald;Knuth;knuth@spengergasse.at;+43 1 54615 101;1938-01-10;DEVELOPER;12000;Employee
    public Employee(String csvString) throws CompanyException {
        if (csvString == null) {
            throw new CompanyException("Error: csvString is null. ");
        }
        String[] tokens = csvString.split(Company.CSV_DELIMITER);
        if (tokens.length < 9) {
            throw new CompanyException("Error: invalid csv line: " + csvString
                                       + ". Not enough values (columns). ");
        }
        try {
            setId(Long.parseLong(tokens[0]));
        } catch (NumberFormatException e) {
            throw new CompanyException("Error: invalid id in line " + csvString + ". ", e);
        }
        setFirstName(tokens[1]);
        setLastName(tokens[2]);
        setEmail(tokens[3]);
        setPhone(tokens[4]);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            setDateOfBirth(LocalDate.parse(tokens[5], formatter));
        } catch (DateTimeParseException e) {
            throw new CompanyException("Error: could not read date in line "
                                       + csvString + ". ", e);
        }
        switch (tokens[6]) {
            case "ADMIN": setRole(Role.ADMIN); break;
            case "ASSISTANT": setRole(Role.ASSISTANT); break;
            case "DEVELOPER": setRole(Role.DEVELOPER); break;
            case "ANALYST": setRole(Role.ANALYST); break;
            case "MANAGER": setRole(Role.MANAGER); break;
            default: throw new CompanyException("Error: invalid role value in line "
                                                + csvString + ". ");
        }
        try {
            setSalary(Integer.parseInt(tokens[7]));
        } catch (NumberFormatException e) {
            throw new CompanyException("Error: invalid salary in line " + csvString + ". ", e);
        }
        if ("Employee".equals(tokens[8])) {
            setSubcontractor(false);
        } else if ("Subcontractor".equals(tokens[8])) {
            setSubcontractor(true);
        } else {
            throw new CompanyException("Error: invalid employment status in line "
                                       + csvString
                                       + ". Expected: 'Employee' or 'Subcontractor'.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws CompanyException {
        if (id == null || id < 0) {
            throw new CompanyException("Error: invalid id: " + id);
        }
        if (id >= Employee.idCounter) {
            Employee.idCounter = id + 1;   // nächste freie Id
        }
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws CompanyException {
        if (firstName == null || firstName.length() <= 2 || firstName.isBlank()) {
            throw new CompanyException("Error: invalid first name: " + firstName);
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws CompanyException {
        if (lastName == null || lastName.length() <= 2 || lastName.isBlank()) {
            throw new CompanyException("Error: invalid last name: " + lastName);
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws CompanyException {
        if (email == null || email.length() <= 5 || !email.contains("@")
            || !email.contains("."))
        {
            throw new CompanyException("Error: invalid email address: " + email);
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws CompanyException {
        if (phone == null) {
            throw new CompanyException("Error: invalid phone (null). ");
        }
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) throws CompanyException {
        if (role == null) {
            throw new CompanyException("Error: invalid role " + role);
        }
        this.role = role;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) throws CompanyException {
        if (salary <= MIN_SALARY || salary >= MAX_SALARY) {
            throw new CompanyException("Error: invalid salary: " + salary);
        }
        this.salary = salary;
    }

    public boolean isSubcontractor() {
        return subcontractor;
    }

    public void setSubcontractor(boolean subcontractor) {
        this.subcontractor = subcontractor;
    }

    // Zwei Employees sind gleich, wenn sie dieselbe Id haben
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id != null && id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toCsvString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String d = Company.CSV_DELIMITER;
        return id + d + firstName + d + lastName + d + email + d + phone + d
               + formatter.format(dateOfBirth) + d + role + d + salary + d
               + (subcontractor ? "Subcontractor" : "Employee");
    }

    @Override
    public String toString() {
        return firstName + " " + lastName
               + " (E: " + email + ", P: " + phone + "), "
               + "date of birth: " + dateOfBirth
               + ", role: " + role
               + ", gross salary: " + salary + " per month, "
               + (subcontractor ? "Subcontractor" : "Employee")
               + ", id=" + id;
    }
}
