package at.spengergasse.company.views.company;

import at.spengergasse.company.model.Company;
import at.spengergasse.company.model.CompanyException;
import at.spengergasse.company.model.Employee;
import at.spengergasse.company.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Formular für einen Mitarbeiter.
 * /employee        -> neuer Mitarbeiter
 * /employee/1002   -> Mitarbeiter mit Id 1002 bearbeiten
 */
@PageTitle("Employee")
@Route(value = "employee", layout = MainLayout.class)
public class EmployeeView extends VerticalLayout implements HasUrlParameter<Long> {

    // Feldnamen = Attributnamen von Employee (für bindInstanceFields)
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("Email address");
    private final TextField phone = new TextField("Phone number");
    private final DatePicker dateOfBirth = new DatePicker("Birthday");
    private final ComboBox<Employee.Role> role = new ComboBox<>("Role");
    private final IntegerField salary = new IntegerField("Salary");
    private final Checkbox subcontractor = new Checkbox("Subcontractor");

    private final Button save = new Button("Save");
    private final Button cancel = new Button("Cancel");

    private final Binder<Employee> binder = new Binder<>(Employee.class);
    private Employee employee = new Employee();

    public EmployeeView() {
        role.setItems(Employee.Role.values());
        email.setErrorMessage("Please enter a valid email address");

        FormLayout form = new FormLayout();
        form.add(firstName, lastName, dateOfBirth, phone, email, role, salary, subcontractor);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);

        add(new H3("Personal information"), form, buttons);

        save.addClickListener(e -> onSave());
        cancel.addClickListener(e -> binder.readBean(employee));

        binder.bindInstanceFields(this);
    }

    // wird beim Navigieren aufgerufen – vor dem Anzeigen der View
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long id) {
        if (id == null) {                         // kein Parameter: neuer Mitarbeiter
            employee = new Employee();
            save.setText("Hire Employee");
            binder.readBean(employee);
            return;
        }
        try {
            employee = Company.getInstance().getEmployee(id);
            save.setText("Update");
        } catch (CompanyException e) {
            Notification.show(e.getMessage());
            employee = new Employee();
        }
        binder.readBean(employee);
    }

    private void onSave() {
        try {
            binder.writeBean(employee);              // Formular -> Objekt
            if (!Company.getInstance().getStaff().contains(employee)) {
                Company.getInstance().hire(employee); // neu: einstellen
            }
            Notification.show("Employee " + employee.getLastName() + " saved.");
            getUI().ifPresent(ui -> ui.navigate(EmployeesListView.class));
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            Notification.show("Error: " + cause.getMessage());
        }
    }
}
