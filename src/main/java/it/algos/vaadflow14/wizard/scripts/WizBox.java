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

    public WizBox(AEPackage pack) {
        checkbox = new ACheckBox(pack.getDescrizione());
        this.setValue(pack.isAccesoInizialmente());
        this.add(checkbox);
    }


    public WizBox(AECheck check) {
        checkbox = new ACheckBox(check.getCaption(), check.getCaption());
        this.setValue(check.isAccesoInizialmente());
        this.add(checkbox);

        if (check.isFieldAssociato()) {
            textField = new TextField();
            textField.setValue(check.getFieldName());
            this.add(textField);
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
