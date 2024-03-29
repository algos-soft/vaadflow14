package it.algos.simple.pratical.ch04.input;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.datepicker.*;
import com.vaadin.flow.component.datetimepicker.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.timepicker.*;
import com.vaadin.flow.router.*;

import java.time.*;
import java.util.*;

@Route("datepicker")
public class DatePickerView extends Composite<Component> {

    @Override
    protected Component initContent() {
        DatePicker datePicker = new DatePicker(
                "Enter a memorable date",
                // LocalDate.now(),
                event -> showMessage(event.getValue())
        );

        // shows the calendar only when clicking on the calendar icon
        // not when clicking the field
        datePicker.setAutoOpen(false);

        // shows an X button to clear the value
        datePicker.setClearButtonVisible(true);

        // sets the date that's visible when the calendar is opened
        // * works only when no date value is set
        datePicker.setInitialPosition(LocalDate.now().minusMonths(1));

        // sets the minimum and maximum dates
        datePicker.setMin(LocalDate.now().minusMonths(3));
        datePicker.setMax(LocalDate.now().plusMonths(3));

        // sets the locale for date formatting
        datePicker.setLocale(Locale.CANADA);

        TimePicker timePicker = new TimePicker("Pick a time");
        timePicker.addValueChangeListener(event -> {
            LocalTime value = event.getValue();
            Notification.show("Time: " + value);
        });

        DateTimePicker dateTimePicker = new DateTimePicker("When?");

        return new VerticalLayout(datePicker, timePicker, dateTimePicker);
    }

    private void showMessage(LocalDate date) {
        Notification.show(
                date + " is great!"
        );
    }
}
