package at.spengergasse.company.views.about;

import at.spengergasse.company.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    public AboutView() {
        setSpacing(false);

        add(new H2("Company – Personalverwaltung"));
        add(new Paragraph("Eine Webanwendung, komplett in Java geschrieben."));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

        TextField tf1 = new TextField();                       // Eingabefeld
        Button b1 = new Button("Press Button");                // Button
        b1.addClickListener(e -> showInfo(tf1.getValue()));    // Event als Lambda

        FormLayout formLayout = new FormLayout();
        formLayout.add(tf1, b1);                               // Elemente ins Layout

        add(formLayout);                                       // Layout zur Seite
    }

    private void showInfo(String text) {
        Notification.show("Hello, " + text);                   // kurze Meldung

        Dialog dialog = new Dialog();                          // Dialogfenster
        dialog.setHeaderTitle("Hello");

        TextField tf = new TextField();
        tf.setValue(text);
        tf.setEnabled(false);
        dialog.add(tf);

        dialog.open();
    }
}
