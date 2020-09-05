package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

import static it.algos.vaadflow14.backend.application.FlowCost.DATE_TIME_PICKER_FIELD;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

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
public class ADateTimeField extends CustomField<LocalDateTime> implements AIField {

    private final DateTimePicker dateTimePicker;

    private final Duration STEP = Duration.ofMinutes(30);
    //    private final DatePicker datePicker;
    //
    //    private final TimePicker timePicker;

    private final String giorno = "(giorno)";

    private final String orario = "(orario)";

    String fieldKey;

    private HorizontalLayout innerField;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ADateTimeField.class) <br>
     */
    public ADateTimeField() {
        this(VUOTA, VUOTA);
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ADateTimeField.class, fieldKey) <br>
     *
     * @param fieldKey nome interno del field
     */
    public ADateTimeField(String fieldKey, String caption) {
        this.fieldKey = fieldKey;
        //        datePicker = new DatePicker(DATE_PICKER_FIELD);
        //        timePicker = new TimePicker(TIME_PICKER_FIELD);
        dateTimePicker = new DateTimePicker(DATE_TIME_PICKER_FIELD);
        dateTimePicker.setStep(STEP);
        innerField = new HorizontalLayout(dateTimePicker);
        add(innerField);
    } // end of SpringBoot constructor


    @Override
    protected LocalDateTime generateModelValue() {
        //        final LocalDate date = datePicker.getValue();
        //        final LocalTime time = timePicker.getValue();
        //
        //        return date != null && time != null ? LocalDateTime.of(date, time) : null;
        return dateTimePicker.getValue();
    }


    @Override
    protected void setPresentationValue(LocalDateTime newPresentationValue) {
        //        datePicker.setValue(newPresentationValue != null ? newPresentationValue.toLocalDate() : null);
        //        timePicker.setValue(newPresentationValue != null ? newPresentationValue.toLocalTime() : null);
        dateTimePicker.setValue(newPresentationValue);
    }


    public void setStep(Duration step) {
        dateTimePicker.setStep(step);
    }


    //    @Override
    //    public AbstractSinglePropertyField getBinder() {
    //        return null;
    //    }


    @Override
    public void setItem(Collection collection) {

    }


    @Override
    public void setText(String caption) {

    }


    @Override
    public AbstractSinglePropertyField getBinder() {
        return null;
    }


    @Override
    public Component get() {
        return null;
    }


    @Override
    public void setAutofocus() {

    }


    @Override
    public String getKey() {
        return fieldKey;
    }


    @Override
    public void setWidth(String width) {
        dateTimePicker.setWidth(width);
    }

}

