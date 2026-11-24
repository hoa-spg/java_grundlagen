package at.spengergasse.company.views.company;

import at.spengergasse.company.model.Company;
import at.spengergasse.company.model.CompanyException;
import at.spengergasse.company.model.Employee;
import at.spengergasse.company.util.UIFactory;
import at.spengergasse.company.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
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
 * Stand 4.5: eigene Telefon-Komponente, Bestätigung, Fehlermeldungen.
 */
@PageTitle("Employee")
@Route(value = "employee", layout = MainLayout.class)
public class EmployeeView extends VerticalLayout implements HasUrlParameter<Long> {

    // Feldnamen = Attributnamen von Employee (für bindInstanceFields)
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("Email address");
    private final PhoneNumberField phone = new PhoneNumberField("Phone number");
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
        cancel.addClickListener(e -> onCancel());

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
            UIFactory.error("No employee found with id " + id, e);
            employee = new Employee();
        }
        binder.readBean(employee);
    }

    private void onCancel() {
        UIFactory.showConfirm("Discard changes?",
                "Are you sure you want to discard potential changes in this object?",
                e -> getUI().ifPresent(ui -> ui.navigate(EmployeesListView.class)));
    }

    private void onSave() {
        try {
            binder.writeBean(employee);              // Formular -> Objekt
            if (employee.getId() == null) {
                Company.getInstance().hire(employee); // INSERT: neu einstellen
            }                                         // sonst UPDATE: schon geändert
            UIFactory.showNotification("Employee " + employee.getLastName() + " saved.");
            getUI().ifPresent(ui -> ui.navigate(EmployeesListView.class));
        } catch (Exception e) {
            UIFactory.error("Error on saving employee", e);
        }
    }
}
