package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 10-giu-2020
 * Time: 18:36
 * Simple layer around IntegerField <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 * Normalmente i fields vengono creati con new xxxField() <br>
 * Se necessitano di injection, occorre usare appContext.getBean(xxxField.class) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AIntegerField extends AField<Integer> {

    private final IntegerField innerField;


    public AIntegerField() {
        this(VUOTA);
    }


    public AIntegerField(String label) {
        this(label,VUOTA);
    }


    public AIntegerField(String label, String placeholder) {
        innerField = new IntegerField(label, placeholder);
        innerField.setAutoselect(true);
        innerField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        add(innerField);
    }

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
    public IntegerField getBinder() {
        return innerField;
    }
    @Override
    public void setPlaceholder(String placeholder) {
        innerField.setPlaceholder(placeholder);
    }

    @Override
    public void setAutofocus() {
        innerField.setAutofocus(true);
    }

}
