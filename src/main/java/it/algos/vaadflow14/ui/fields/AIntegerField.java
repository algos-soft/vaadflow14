package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 10-giu-2020
 * Time: 18:36
 * Simple layer around IntegerField <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AIntegerField extends AField<Integer> {

    private final IntegerField innerField;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(AIntegerField.class, fieldKey, caption) <br>
     *
     * @param fieldKey nome interno del field
     * @param caption  label visibile del field
     */
    public AIntegerField(String fieldKey, String caption) {
        super.fieldKey = fieldKey;
        super.caption = caption;
        innerField = new IntegerField(caption);
        innerField.setAutoselect(true);
        innerField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        add(innerField);
    } // end of SpringBoot constructor



    @Override
    protected Integer generateModelValue() {
        return innerField.getValue();
    }


    @Override
    protected void setPresentationValue(Integer value) {
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


    @Override
    public void setAutofocus() {
        innerField.setAutofocus(true);
    }

}
