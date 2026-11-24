package at.spengergasse.company;

import at.spengergasse.company.model.Company;
import at.spengergasse.company.model.CompanyException;
import at.spengergasse.company.model.Employee;

import java.time.LocalDate;

/**
 * Testprogramm für die Model-Klassen (noch ohne Oberfläche).
 */
public class Main {

    public static void main(String[] args) {
        try {
            Company company = Company.getInstance();   // Singleton mit Testdaten
            company.hire(new Employee("Grace", "Hopper", "hopper@spengergasse.at",
                    "+43 1 54615 130", LocalDate.of(1906, 12, 9),
                    Employee.Role.ANALYST, 9500, false));
            company.print();

            System.out.println("Managers: " + company.numberOfManagers());
            System.out.println("Sorted by last name:");
            company.employeesSortedLastName()
                   .forEach(e -> System.out.println("  " + e.getLastName()));
            System.out.println("Salary > 10000:");
            company.employeesSortedSalaryDecreasing(10000)
                   .forEach(e -> System.out.println("  " + e.getSalary() + " " + e.getLastName()));

            company.writeToFile("company-neu.csv");
            Company.loadFromFile("company-neu.csv");
            System.out.println("Reloaded: " + Company.getInstance().numberOfEmployees()
                               + " employees");
        } catch (CompanyException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
    }
}
