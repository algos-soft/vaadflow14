package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowCost;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 29-ago-2020
 * Time: 17:07
 * Layer around DatePicker <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ADateField extends AField<LocalDate> {

    private final DatePicker innerField;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ADateField.class) <br>
     */
    public ADateField() {
        this(VUOTA, VUOTA);
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ADateField.class, caption) <br>
     *
     * @param caption label visibile del field
     */
    public ADateField(String caption) {
        this(VUOTA, caption);
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ADateField.class, fieldKey, caption) <br>
     *
     * @param fieldKey nome interno del field
     * @param caption  label visibile del field
     */
    public ADateField(String fieldKey, String caption) {
        super.fieldKey = fieldKey;
        super.caption = caption.equals(VUOTA) ? FlowCost.DATE_PICKER_FIELD : caption;
        innerField = new DatePicker(super.caption);
        add(innerField);
    } // end of SpringBoot constructor


    @Override
    protected LocalDate generateModelValue() {
        return innerField.getValue();
    }


    @Override
    protected void setPresentationValue(LocalDate value) {
        innerField.setValue(value);
    }


    @Override
    public void setWidth(String width) {
        innerField.setWidth(width);
    }


    @Override
    public AbstractSinglePropertyField getBinder() {
        return innerField;
    }


}
