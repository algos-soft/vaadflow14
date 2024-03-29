package it.algos.simple.pratical.ch04.input;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;

@Route("password-field")
public class PasswordFieldView extends Composite<Component> {

    @Override
    public Component getContent() {
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setRevealButtonVisible(true);

        passwordField.addValueChangeListener(event -> {
            String password = event.getValue();
            System.out.println("Password: " + password);
        });

        return new VerticalLayout(passwordField);
    }
}
