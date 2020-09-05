package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 04-set-2020
 * Time: 11:15
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProvaField extends CustomField<LocalDateTime> implements AIField {

    private final DatePicker datePicker = new DatePicker();

    private final TimePicker timePicker = new TimePicker();


    public ProvaField(String caption) {
        super();
        add(datePicker, timePicker);
    }


    @Override
    protected void setPresentationValue(LocalDateTime newPresentationValue) {
        datePicker.setValue(newPresentationValue != null ? newPresentationValue.toLocalDate() : null);
        timePicker.setValue(newPresentationValue != null ? newPresentationValue.toLocalTime() : null);
    }


    @Override
    protected LocalDateTime generateModelValue() {
        final LocalDate date = datePicker.getValue();
        final LocalTime time = timePicker.getValue();

        return date != null && time != null ? LocalDateTime.of(date, time) : null;
    }// end of method


    @Override
    public String getErrorMessage() {
        return null;
    }


    @Override
    public void setErrorMessage(String s) {

    }


    @Override
    public boolean isInvalid() {
        return false;
    }


    @Override
    public void setInvalid(boolean b) {

    }


    @Override
    public void setItem(Collection collection) {

    }


    @Override
    public void setText(String caption) {

    }


    @Override
    public void setWidth(String width) {
        datePicker.setWidth(width);
    }


    @Override
    public DatePicker getBinder() {
        return datePicker;
    }


    @Override
    public Component get() {
        return this;
    }


    @Override
    public void setAutofocus() {

    }


    @Override
    public String getKey() {
        return null;
    }

}
