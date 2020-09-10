package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 29-ago-2020
 * Time: 17:07
 * Simple layer around DatePicker <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ADateField extends AField<LocalDate> {

    private  DatePicker datePicker;


    /**
     * Costruttore senza parametri <br>
     * L' istanza viene costruita con appContext.getBean(ADateField.class) <br>
     */
    public ADateField() {
        datePicker = new DatePicker();
        add(datePicker);
    } // end of SpringBoot constructor


    @Override
    protected LocalDate generateModelValue() {
        return datePicker.getValue();
    }


    @Override
    protected void setPresentationValue(LocalDate value) {
        datePicker.setValue(value);
    }


}
