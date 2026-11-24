package at.spengergasse.company.views.company;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Eigene Komponente (Vererbung!): Vorwahl + Nummer, nach außen ein String
 * wie "+43 15461501". Kann wie ein TextField an "phone" gebunden werden.
 */
public class PhoneNumberField extends CustomField<String> {

    private final ComboBox<String> countryCode = new ComboBox<>();
    private final TextField number = new TextField();

    public PhoneNumberField(String label) {
        setLabel(label);
        countryCode.setWidth("120px");
        countryCode.setPlaceholder("Country");
        countryCode.setAllowedCharPattern("[\\+\\d]");
        countryCode.setItems("+43", "+49", "+41", "+91", "+62", "+98", "+964",
                             "+353", "+44", "+972", "+39", "+225");
        countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
        number.setAllowedCharPattern("[\\d ]");
        HorizontalLayout layout = new HorizontalLayout(countryCode, number);
        layout.setFlexGrow(1.0, number);
        add(layout);
    }

    // Komponenten -> Wert
    @Override
    protected String generateModelValue() {
        if (countryCode.getValue() == null || number.getValue() == null) {
            return "";
        }
        return countryCode.getValue() + " " + number.getValue();
    }

    // Wert -> Komponenten
    @Override
    protected void setPresentationValue(String phoneNumber) {
        String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
        if (parts.length == 1) {
            countryCode.clear();
            number.setValue(parts[0]);
        } else if (parts.length == 2) {
            countryCode.setValue(parts[0]);
            number.setValue(parts[1]);
        } else {
            countryCode.clear();
            number.clear();
        }
    }
}
