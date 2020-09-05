package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 29-ago-2020
 * Time: 17:08
 * Layer around TimePicker <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ATimeField extends CustomField<LocalTime> implements AIField {

    private final TimePicker timePicker;

    private final Duration STEP = Duration.ofMinutes(15);

    String fieldKey;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ATimeField.class) <br>
     */
    public ATimeField() {
        this(VUOTA, VUOTA);
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ATimeField.class, caption) <br>
     *
     * @param caption label visibile del field
     */
    public ATimeField(String caption) {
        this(VUOTA, caption);
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ATimeField.class, fieldKey, caption) <br>
     *
     * @param fieldKey nome interno del field
     * @param caption  label visibile del field
     */
    public ATimeField(String fieldKey, String caption) {
        this.fieldKey = fieldKey;
        //        super.caption = caption.equals(VUOTA) ? FlowCost.TIME_PICKER_FIELD : caption;
        timePicker = new TimePicker(caption);
        timePicker.setStep(STEP);
        add(timePicker);
    } // end of SpringBoot constructor


    @Override
    protected LocalTime generateModelValue() {
        return timePicker.getValue();
    }


    @Override
    protected void setPresentationValue(LocalTime newPresentationValue) {
        timePicker.setValue(newPresentationValue);
    }


    public void setStep(Duration step) {
        timePicker.setStep(step);
    }


    @Override
    public void setItem(Collection collection) {

    }


    @Override
    public void setText(String caption) {

    }


    @Override
    public AbstractSinglePropertyField getBinder() {
        return timePicker;
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
        return fieldKey;
    }
    @Override
    public void setWidth(String width) {
        timePicker.setWidth(width);
    }

}
