package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Collection;

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

    private String placeholder;


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
     * L' istanza viene costruita con appContext.getBean(AComboField.class, fieldKey, caption, placeholder) <br>
     *
     * @param fieldKey    nome interno del field
     * @param caption     label visibile del field
     * @param placeholder iniziale
     */
    public AComboField(String fieldKey, String caption, String placeholder) {
        innerField = new ComboBox(caption);
        super.fieldKey = fieldKey;
        this.placeholder = placeholder;
        add(innerField);
    } // end of SpringBoot constructor


    @Override
    public void setItem(Collection collection) {
        innerField.setItems(collection);
    }


    @Override
    protected Object generateModelValue() {
        return innerField.getValue();
    }


    @Override
    protected void setPresentationValue(Object value) {
        innerField.setValue(value);
    }


    @Override
    public AbstractSinglePropertyField getBinder() {
        return innerField;
    }


}
