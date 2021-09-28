package it.algos.simple.pratical.ch04.input;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.value.*;
import com.vaadin.flow.router.*;

@Route("text-field")
public class TextFieldView extends Composite<Component> {

    @Override
    protected Component initContent() {
        TextField textField = new TextField();
        textField.setLabel("Name");
        textField.setPlaceholder("enter your full name");

        textField.setAutoselect(true);
        textField.setAutofocus(true);
        textField.setClearButtonVisible(true);
        textField.setValue("John Doe");

        textField.setRequired(true);
        textField.setMinLength(2);
        textField.setMaxLength(10);
        textField.setPattern("^[a-zA-Z\\s]+");
        textField.setErrorMessage("Letters only. Min 2 chars");

        textField.addValueChangeListener(event -> {
            if (textField.isInvalid()) {
                Notification.show("Invalid name");
            }
        });

        textField.setValueChangeMode(ValueChangeMode.EAGER);

        return new VerticalLayout(textField);
    }

}
