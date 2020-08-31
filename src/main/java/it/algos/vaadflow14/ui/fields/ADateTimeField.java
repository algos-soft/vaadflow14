package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 29-ago-2020
 * Time: 17:07
 * Layer around DatePicker <br>
 * Layer around TimePicker <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ADateTimeField extends AField<LocalDateTime> {

    private final DatePicker datePicker;

    private final TimePicker timePicker;

    private final Duration STEP = Duration.ofMinutes(30);

    private final String giorno = "(giorno)";

    private final String orario = "(orario)";

    private HorizontalLayout innerField;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ADateTimeField.class) <br>
     */
    public ADateTimeField() {
        this(VUOTA);
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ADateTimeField.class, fieldKey) <br>
     *
     * @param fieldKey nome interno del field
     */
    public ADateTimeField(String fieldKey) {
        super.fieldKey = fieldKey;
        datePicker = new DatePicker(DATE_PICKER_FIELD);
        timePicker = new TimePicker(TIME_PICKER_FIELD);
        timePicker.setStep(STEP);
        innerField = new HorizontalLayout(datePicker, timePicker);
        add(innerField);
    } // end of SpringBoot constructor


    @Override
    protected LocalDateTime generateModelValue() {
        final LocalDate date = datePicker.getValue();
        final LocalTime time = timePicker.getValue();

        return date != null && time != null ? LocalDateTime.of(date, time) : null;
    }


    @Override
    protected void setPresentationValue(LocalDateTime newPresentationValue) {
        datePicker.setValue(newPresentationValue != null ? newPresentationValue.toLocalDate() : null);
        timePicker.setValue(newPresentationValue != null ? newPresentationValue.toLocalTime() : null);
    }


    public void setStep(Duration step) {
        timePicker.setStep(step);
    }


    @Override
    public AbstractSinglePropertyField getBinder() {
        return null;
    }


}

