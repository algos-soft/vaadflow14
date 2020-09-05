package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Collection;
import java.util.List;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: gio, 11-giu-2020
 * Time: 22:00
 * Simple layer around ComboBox value <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AComboField<T> extends AField<Object> {

    private final ComboBox innerField;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(AComboField.class, fieldKey, caption) <br>
     *
     * @param fieldKey nome interno del field
     * @param caption  label visibile del field
     */
    public AComboField(String fieldKey, String caption) {
        super.fieldKey = fieldKey;
        super.caption = caption;
        innerField = new ComboBox(caption);
        add(innerField);
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(AComboField.class, fieldKey, caption, items) <br>
     *
     * @param caption  label visibile del field
     * @param items    collezione dei valori previsti
     */
    public AComboField( String caption, List<String> items) {
        innerField = new ComboBox(caption);
        super.fieldKey = fieldKey;
        this.setItem(items);
        add(innerField);
    } // end of SpringBoot constructor

    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(AComboField.class, fieldKey, caption, items) <br>
     *
     * @param fieldKey nome interno del field
     * @param caption  label visibile del field
     * @param items    collezione dei valori previsti
     */
    public AComboField(String fieldKey, String caption, List<String> items) {
        super.fieldKey = fieldKey;
        innerField = new ComboBox(caption);
        super.fieldKey = fieldKey;
        this.setItem(items);
        add(innerField);
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(AComboField.class, fieldKey, caption, items, value) <br>
     *
     * @param caption label visibile del field
     * @param items   collezione dei valori previsti
     * @param value   selezionato
     */
    public AComboField(String caption, List<String> items, String value) {
        innerField = new ComboBox(caption);
        super.fieldKey = fieldKey;
        this.setItem(items);
        this.setValue(value);
        add(innerField);
    } // end of SpringBoot constructor


    @Override
    public void setValue(Object value) {
        innerField.setValue(value);
    }


    @Override
    public void setItem(Collection collection) {
        try {
            innerField.setItems(collection);
        } catch (Exception unErrore) {
            System.out.println("Items nulli in AComboField.setItems()");
        }
    }


    @Override
    protected Object generateModelValue() {
        Object alfa = innerField.getValue(); //@todo Linea di codice DA RIMETTERE (sistemare su una sola riga)
        return innerField.getValue();
    }


    @Override
    protected void setPresentationValue(Object value) {
        innerField.setValue(value);
    }


    @Override
    public ComboBox getBinder() {
        return innerField;
    }


}
