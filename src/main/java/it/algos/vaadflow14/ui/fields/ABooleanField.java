package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: gio, 11-giu-2020
 * Time: 21:54
 * Simple layer around boolean value <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 * Normalmente i fields vengono creati con new xxxField() <br>
 * Se necessitano di injection, occorre usare appContext.getBean(xxxField.class) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ABooleanField extends AField<Boolean> {

    private final Checkbox innerField;


    public ABooleanField() {
        this(VUOTA);
    }


    public ABooleanField(String label) {
        this(label, VUOTA);
    }


    public ABooleanField(String label, String placeholder) {
        innerField = new Checkbox();
        innerField.setLabelAsHtml(label);
        add(innerField);
    }


    @Override
    protected Boolean generateModelValue() {
        return super.generateModelValue();
    }


    @Override
    protected void setPresentationValue(Boolean value) {
        super.setPresentationValue(value);
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
