package it.algos.simple.pratical.ch04.interaction;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

import java.time.*;

@Route("button")
public class ButtonView extends Composite<Component> {

    @Override
    protected Component initContent() {
        Button button = new Button("Time in the server", event -> {
            Notification.show("Sure: " + LocalTime.now());
        });
        button.setDisableOnClick(true);

        return new VerticalLayout(button);
    }

}
