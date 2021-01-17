package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import it.algos.vaadflow14.ui.fields.ACheckBox;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEPackage;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadwiki14
 * Created by Algos
 * User: gac
 * Date: lun, 28-dic-2020
 * Time: 18:59
 * Layer per presentare un checkBox eventualmente seguito da un TextField <br>
 */
public class WizBox extends HorizontalLayout {

    private ACheckBox checkbox;

    private TextField textField;

    private AECheck check;

    public WizBox(AEPackage pack) {
        checkbox = new ACheckBox(pack.getDescrizione());
        this.setValue(pack.isAccesoInizialmente());
        this.add(checkbox);
    }


    public WizBox(AECheck check) {
        checkbox = new ACheckBox(check.getCaption(), check.getCaption());
        this.setValue(check.isAccesoInizialmente());
        this.add(checkbox);
        this.check = check;

        if (check.isFieldAssociato()) {
            textField = new TextField();
            if (check.is()) {
                textField.setValue(check.getFieldName());
            }
            textField.setAutoselect(true);
            checkbox.getBinder().addValueChangeListener(event -> sincroText());

            this.add(textField);
        }
    }

    private void sincroText() {
        if (checkbox.getBinder().getValue()) {
            textField.setValue(check.getFieldName());
        }
        else {
            textField.setValue(VUOTA);
        }
    }

    public Checkbox getBox() {
        return checkbox.getBinder();
    }

    public boolean is() {
        return getBox().getValue();
    }

    public String getValue() {
        return textField != null ? textField.getValue() : VUOTA;
    }

    public void setValue(boolean value) {
        getBox().setValue(value);
    }

}
