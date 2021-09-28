package it.algos.simple.pratical.ch04.input;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

@Route("checkbox")
public class CheckboxView extends Composite<Component> {

    @Override
    protected Component initContent() {
        Checkbox checkbox = new Checkbox();
        checkbox.setLabelAsHtml("I'm <b>learning</b> Vaadin!");

        checkbox.setValue(true);
        checkbox.setIndeterminate(true);
        Boolean initialValue = checkbox.getValue();
        Notification.show("Initial value: " + initialValue);

        checkbox.addValueChangeListener(event -> {
            Boolean value = event.getValue();
            Notification.show("Value: " + value);
        });

        return new VerticalLayout(checkbox);
    }
}
