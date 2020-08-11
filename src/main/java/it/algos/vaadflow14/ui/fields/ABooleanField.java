package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AETypeBool;
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

    private AbstractSinglePropertyField innerField;

    private Checkbox checkBox;

    private RadioButtonGroup<String> radioGroup;

    private AETypeBool typeBool;


    public ABooleanField() {
        this(VUOTA, AETypeBool.checkBox);
    }


    public ABooleanField(String label, AETypeBool typeBool) {
        this(label, typeBool, VUOTA);
    }


    public ABooleanField(String label, AETypeBool typeBool, String placeholder) {
        this.typeBool = typeBool;
        initView(label);
    }


    protected void initView(String label) {
        if (typeBool != null) {
            switch (typeBool) {
                case checkBox:
                    innerField = new Checkbox();
                    ((Checkbox) innerField).setLabelAsHtml(label);
                    break;
                case radioTrueFalse:
                    innerField = new RadioButtonGroup<>();
                    ((RadioButtonGroup) innerField).setItems("Vero", "Falso");
                    break;
                case radioSiNo:
                    innerField = new RadioButtonGroup<>();
                    ((RadioButtonGroup) innerField).setItems("Si", "No");
                    break;
                case radioCustomHoriz:

                    break;
                case radioCustomVert:

                    break;
                default:
                    //                    logger.warn("Switch - caso non definito", this.getClass(), "nomeDelMetodo");
                    break;
            }

        }
        if (typeBool != AETypeBool.checkBox) {
            Label comp= new Label();
            comp.setText(label);
            add(comp);
        }

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
    public AbstractSinglePropertyField getBinder() {
        return innerField;
    }


}
