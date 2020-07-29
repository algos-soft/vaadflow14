package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Collection;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: gio, 11-giu-2020
 * Time: 22:00
 * Simple layer around ComboBox value <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 * Normalmente i fields vengono creati con new xxxField() <br>
 * Se necessitano di injection, occorre usare appContext.getBean(xxxField.class) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AComboField<T> extends AField<Object> {

    private final ComboBox innerField;


    public AComboField() {
        this(VUOTA);
    }


    public AComboField(String label) {
        this(label, VUOTA);
    }


    public AComboField(String label, String placeholder) {
        innerField = new ComboBox(label);
        add(innerField);
    }

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

}
