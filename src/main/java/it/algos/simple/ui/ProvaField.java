package it.algos.simple.ui;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.ui.fields.AField;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 04-set-2020
 * Time: 11:15
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProvaField extends AField<LocalDateTime> {

    private final Duration STEP = Duration.ofMinutes(15);

    private DatePicker datePicker = null;

    private TimePicker timePicker = null;


    public ProvaField() {
        super();
        datePicker = new DatePicker();
        timePicker = new TimePicker();
        timePicker.setStep(STEP);
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


    //    @Override
    //    public String getErrorMessage() {
    //        return null;
    //    }
    //
    //
    //    @Override
    //    public void setErrorMessage(String s) {
    //
    //    }
    //
    //
    //    @Override
    //    public boolean isInvalid() {
    //        return false;
    //    }
    //
    //
    //    @Override
    //    public void setInvalid(boolean b) {
    //
    //    }
    //
    //
    //    @Override
    //    public void setItem(Collection collection) {
    //
    //    }
    //
    //
    //    @Override
    //    public void setText(String caption) {
    //
    //    }
    //
    //
    //    @Override
    //    public void setWidth(String width) {
    //        datePicker.setWidth(width);
    //    }
    //
    //
    //    @Override
    //    public DatePicker getBinder() {
    //        return datePicker;
    //    }
    //
    //
    //    @Override
    //    public Component get() {
    //        return this;
    //    }
    //
    //
    //    @Override
    //    public void setAutofocus() {
    //
    //    }
    //
    //
    //    @Override
    //    public String getKey() {
    //        return null;
    //    }

}
