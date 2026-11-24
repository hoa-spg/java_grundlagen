package at.spengergasse.company.views.company;

import at.spengergasse.company.model.Company;
import at.spengergasse.company.model.CompanyException;
import at.spengergasse.company.model.Employee;
import at.spengergasse.company.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Tabelle aller Mitarbeiter mit Bearbeiten- und Löschen-Button.
 */
@PageTitle("Employees")
@Route(value = "employees", layout = MainLayout.class)
public class EmployeesListView extends Div {

    private final Grid<Employee> grid = new Grid<>(Employee.class, false);

    public EmployeesListView() {
        setSizeFull();

        // Spalten über den Namen der Get-Methode (getFirstName -> "firstName")
        grid.addColumn("firstName").setAutoWidth(true);
        grid.addColumn("lastName").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("phone").setAutoWidth(true);
        grid.addColumn("dateOfBirth").setAutoWidth(true);
        grid.addColumn("role").setAutoWidth(true);
        grid.addColumn("salary").setAutoWidth(true);
        grid.addColumn("subcontractor").setAutoWidth(true);

        // eigene Spalte mit Komponenten (Buttons) pro Zeile
        grid.addComponentColumn(employee -> {
                    Button edit = new Button(new Icon(VaadinIcon.EDIT));
                    edit.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_SUCCESS);
                    edit.addClickListener(e -> edit(employee));
                    Button delete = new Button(new Icon(VaadinIcon.TRASH));
                    delete.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);
                    delete.addClickListener(e -> delete(employee));
                    return new HorizontalLayout(edit, delete);
                }).setHeader("Actions")
                .setTextAlign(ColumnTextAlign.END)
                .setFrozenToEnd(true)
                .setAutoWidth(true)
                .setFlexGrow(0);

        refreshGrid();
        add(grid);
    }

    // Navigation mit Parameter: /employee/1002
    private void edit(Employee employee) {
        getUI().ifPresent(ui -> ui.navigate(EmployeeView.class, employee.getId()));
    }

    private void delete(Employee employee) {
        try {
            Company.getInstance().fire(employee.getId());
            refreshGrid();
            Notification.show("Employee " + employee.getLastName() + " removed.");
        } catch (CompanyException e) {
            Notification.show("Error: " + e.getMessage());
        }
    }

    private void refreshGrid() {
        grid.setItems(Company.getInstance().getStaff());
    }
}
