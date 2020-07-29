package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 10-giu-2020
 * Time: 17:30
 * Simple layer around TextField <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 * Normalmente i fields vengono creati con new xxxField() <br>
 * Se necessitano di injection, occorre usare appContext.getBean(xxxField.class) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ATextField extends AField<String> {

    private final TextField innerField;


    public ATextField() {
        this(VUOTA);
    }


    public ATextField(String label) {
        this(label,VUOTA);
    }


    public ATextField(String label, String placeholder) {
        innerField = new TextField(label, placeholder);
        add(innerField);
    }

    @Override
    protected String generateModelValue() {
        return innerField.getValue();
    }

    @Override
    protected void setPresentationValue(String value) {
        innerField.setValue(value);
    }

}
