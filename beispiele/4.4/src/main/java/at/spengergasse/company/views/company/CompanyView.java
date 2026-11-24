package at.spengergasse.company.views.company;

import at.spengergasse.company.model.Company;
import at.spengergasse.company.model.CompanyException;
import at.spengergasse.company.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Formular für Name und Adresse der Firma.
 * Stand 4.4: Data Binding mit Binder statt readData/storeData von Hand.
 */
@PageTitle("Company")
@Route(value = "company", layout = MainLayout.class)
public class CompanyView extends VerticalLayout {

    private final TextField companyName = new TextField("Company Name");
    private final TextField companyAddress = new TextField("Company Address");

    private final Button save = new Button("Save");
    private final Button cancel = new Button("Cancel");

    private final Button saveToFile = new Button("Save Company To File");
    private final Button loadFromFile = new Button("Load Company From File");

    // verbindet die Felder companyName/companyAddress mit den gleichnamigen
    // Get-/Set-Methoden von Company
    private final Binder<Company> binder = new Binder<>(Company.class);

    public CompanyView() {
        createLayout();
        addHandlers();
        binder.bindInstanceFields(this);           // Felder nach Namen binden
        binder.readBean(Company.getInstance());    // Model -> Formular
    }

    private void createLayout() {
        setWidth("80%");

        FormLayout form = new FormLayout();
        form.add(companyName, companyAddress);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        buttons.setJustifyContentMode(JustifyContentMode.END);
        buttons.setWidthFull();

        // aufklappbarer Bereich für die Datei-Funktionen
        Details fileHandling = new Details("Save/Load Company to/from File",
                                           new HorizontalLayout(saveToFile, loadFromFile));
        fileHandling.setOpened(false);

        add(form, buttons, fileHandling);
    }

    private void addHandlers() {
        save.addClickListener(e -> storeData());
        save.addClickShortcut(Key.ENTER);
        cancel.addClickListener(e -> binder.readBean(Company.getInstance()));
        cancel.addClickShortcut(Key.ESCAPE);
        saveToFile.addClickListener(e -> saveCompanyToFile());
        loadFromFile.addClickListener(e -> loadCompanyFromFile());
    }

    // Formular -> Model; die Set-Methoden prüfen die Werte
    private void storeData() {
        try {
            binder.writeBean(Company.getInstance());
            Notification.show("Company name and address saved.");
        } catch (Exception e) {
            // Exceptions der Set-Methoden kommen "verpackt" an
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            Notification.show("Error: " + cause.getMessage());
        }
    }

    private void saveCompanyToFile() {
        final String filename = "company.csv";   // TODO Dateiname wählbar machen
        try {
            Company.getInstance().writeToFile(filename);
            Notification.show("Company saved to " + filename);
        } catch (CompanyException e) {
            Notification.show(e.getMessage());
        }
    }

    private void loadCompanyFromFile() {
        final String filename = "company.csv";
        try {
            Company.loadFromFile(filename);
            binder.readBean(Company.getInstance());
            Notification.show("Company successfully loaded from " + filename);
        } catch (CompanyException e) {
            Notification.show(e.getMessage());
        }
    }
}
