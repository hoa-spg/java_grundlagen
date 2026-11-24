package at.spengergasse.company.model;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CompanyTest {

    @TempDir
    Path tempDir;

    private Company company;

    @BeforeEach
    void setUp() {
        Company.clearInstance();
        company = Company.getInstance();       // 5 Testdatensätze
    }

    @Test
    void singleton() {
        assertSame(company, Company.getInstance());
        assertEquals(5, company.numberOfEmployees());
    }

    @Test
    void hireVergibtIdUndVerhindertDuplikate() throws CompanyException {
        Employee e = new Employee();
        e.setFirstName("Grace");
        e.setLastName("Hopper");
        company.hire(e);
        assertNotNull(e.getId());
        assertThrows(CompanyException.class, () -> company.hire(e));
        assertSame(e, company.getEmployee(e.getId()));
    }

    @Test
    void fireNachIdUndName() throws CompanyException {
        assertTrue(company.fire(1001L));
        assertFalse(company.fire(1001L));
        assertTrue(company.fire("Linus", "Torvalds"));
        assertEquals(3, company.numberOfEmployees());
        assertThrows(CompanyException.class, () -> company.getEmployee(1001L));
    }

    @Test
    void getStaffLiefertKopie() {
        List<Employee> staff = company.getStaff();
        staff.clear();
        assertEquals(5, company.numberOfEmployees());
    }

    @Test
    void streams() throws CompanyException {
        assertEquals(2, company.numberOfManagers());
        assertEquals("Fowler", company.employeesSortedLastName().get(0).getLastName());
        assertEquals(13000, company.employeesSortedSalaryDecreasing().get(0).getSalary());
        assertEquals(3, company.employeesSortedSalaryDecreasing(10000).size());
        company.increaseSalaryByPercent(10);
        assertEquals(14300, company.getEmployee(1002L).getSalary());
    }

    @Test
    void csvSchreibenUndLesen() throws CompanyException {
        String file = tempDir.resolve("company.csv").toString();
        company.writeToFile(file);
        Company read = Company.readFromFile(file);
        assertEquals("Spenger Inc.", read.getCompanyName());
        assertEquals(5, read.numberOfEmployees());
        assertEquals(Employee.Role.MANAGER, read.getEmployee(1002L).getRole());
    }

    @Test
    void ungueltigeEmployeeDaten() {
        assertThrows(CompanyException.class, () -> new Employee("1;Al;Turing;x;y;z;A;1;E"));
        assertThrows(CompanyException.class,
                () -> new Employee(7L, "Alan", "Turing", "turing-at-spg", "1",
                                   LocalDate.of(1912, 6, 23), Employee.Role.ADMIN, 5000, false));
    }

    @Test
    void equalsUeberId() throws CompanyException {
        Employee a = new Employee(2000L, "Ada", "Lovelace", "ada@spg.at", "1",
                                  LocalDate.of(1815, 12, 10), Employee.Role.ANALYST, 5000, false);
        Employee b = new Employee(2000L, "Ada", "Byron", "byron@spg.at", "2",
                                  LocalDate.of(1815, 12, 10), Employee.Role.ADMIN, 6000, true);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, new Employee());
    }
}
