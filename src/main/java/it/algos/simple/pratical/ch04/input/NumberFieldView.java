package it.algos.simple.pratical.ch04.input;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;

@Route("number-field")
public class NumberFieldView extends Composite<Component> {

    @Override
    protected Component initContent() {
        NumberField numberField = new NumberField("Rating");
        numberField.setHasControls(true);
        numberField.setMin(0.0);
        numberField.setMax(5.0);
        numberField.setStep(0.5);
        numberField.setClearButtonVisible(true);
        numberField.setHelperText("From 0.0 to 5.0");

        numberField.addValueChangeListener(event -> {
            Double value = event.getValue();
            Notification.show("Your rating: " + value);
        });

        return new VerticalLayout(numberField);
    }
}
