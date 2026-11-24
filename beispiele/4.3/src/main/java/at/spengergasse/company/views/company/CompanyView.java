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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Formular für Name und Adresse der Firma.
 * Stand 4.3: Layouts und Komponenten; Daten werden noch "von Hand"
 * zwischen Formular und Company-Objekt kopiert (ab 4.4: Binder).
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

    public CompanyView() {
        createLayout();
        addHandlers();
        readData();
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
        cancel.addClickListener(e -> readData());
        cancel.addClickShortcut(Key.ESCAPE);
        saveToFile.addClickListener(e -> saveCompanyToFile());
        loadFromFile.addClickListener(e -> loadCompanyFromFile());
    }

    // Model -> Formular
    private void readData() {
        Company company = Company.getInstance();
        companyName.setValue(company.getCompanyName());
        companyAddress.setValue(company.getCompanyAddress());
    }

    // Formular -> Model (die Set-Methoden prüfen die Werte!)
    private void storeData() {
        try {
            Company.getInstance().setCompanyName(companyName.getValue());
            Company.getInstance().setCompanyAddress(companyAddress.getValue());
            Notification.show("Company name and address saved.");
        } catch (CompanyException e) {
            Notification.show(e.getMessage());
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
            readData();
            Notification.show("Company successfully loaded from " + filename);
        } catch (CompanyException e) {
            Notification.show(e.getMessage());
        }
    }
}
