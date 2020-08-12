package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AETypeBool;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VIRGOLA;
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

    //    private AbstractSinglePropertyField innerField;
    private boolean usaCheckBox;

    private Checkbox checkBox;

    private RadioButtonGroup<String> radioGroup;

    private AETypeBool typeBool;

    private String captionRadio = VUOTA;

    private String firstItem;

    private String secondItem;


    public ABooleanField() {
        this(VUOTA, AETypeBool.checkBox);
    }


    public ABooleanField(String label, AETypeBool typeBool) {
        this(label, typeBool, VUOTA);
    }


    public ABooleanField(String label, AETypeBool typeBool, String captionRadio) {
        this.typeBool = typeBool;
        this.captionRadio = captionRadio;
        initView(label);
    }


    protected void initView(String label) {
        usaCheckBox = typeBool == AETypeBool.checkBox;

        if (usaCheckBox) {
            checkBox = new Checkbox();
        } else {
            radioGroup = new RadioButtonGroup<String>();
        }

        if (typeBool != null) {
            switch (typeBool) {
                case checkBox:
                    checkBox.setLabelAsHtml(label);
                    break;
                case radioTrueFalse:
                    radioGroup.setItems("Vero", "Falso");
                    radioGroup.setLabel(label);
                    break;
                case radioSiNo:
                    radioGroup.setItems("Si", "No");
                    radioGroup.setLabel(label);
                    break;
                case radioCustomHoriz:
                    radioGroup.setItems(getItems());
                    break;
                case radioCustomVert:
                    radioGroup.setItems(getItems());
                    radioGroup.setLabel(label);
                    radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
                    break;
                default:
                    //                    logger.warn("Switch - caso non definito", this.getClass(), "nomeDelMetodo");
                    break;
            }
        }

        if (usaCheckBox) {
            add(checkBox);
        } else {
            add(radioGroup);
        }
    }


    protected List<String> getItems() {
        List<String> items = new ArrayList<>();
        String[] parti;

        if (captionRadio != null && captionRadio.length() > 0 && captionRadio.contains(VIRGOLA)) {
            parti = captionRadio.split(VIRGOLA);
            if (parti != null && parti.length == 2) {
                firstItem = parti[0].trim();
                secondItem = parti[1].trim();
                items.add(firstItem);
                items.add(secondItem);
            }
        } else {
            items.add("Vero");
            items.add("Falso");
        }

        return items;
    }


    @Override
    protected void setModelValue(Boolean newModelValue, boolean fromClient) {
        super.setModelValue(newModelValue, fromClient);
    }


    @Override
    public Boolean getValue() {
        return super.getValue();
    }


    @Override
    public void setValue(Boolean value) {
        super.setValue(value);
    }


    //    @Override
    public void xsetValue(Boolean value) {
        //        String firstItem = getItems().get(0);
        //        String secondItem = getItems().get(1);
        //
        //        if (typeBool == AETypeBool.checkBox) {
        //            super.setValue(value);
        //        } else {
        //            if (((RadioButtonGroup) innerField) != null) {
        //                if (value) {
        //                    ((RadioButtonGroup) innerField).setValue(firstItem);
        //                } else {
        //                    ((RadioButtonGroup) innerField).setValue(secondItem);
        //                }
        //            }
        //            super.setValue(value);
        //        }
    }


    @Override
    protected void updateValue() {
        super.updateValue();
    }


    @Override
    protected Boolean generateModelValue() {
        return super.generateModelValue();
    }


    @Override
    protected void setPresentationValue(Boolean value) {
        if (!usaCheckBox) {
            if (value) {
                radioGroup.setValue(firstItem);
            } else {
                radioGroup.setValue(secondItem);
            }
        }
    }


    @Override
    public AbstractSinglePropertyField getBinder() {
        if (usaCheckBox) {
            return checkBox;
        } else {
            return radioGroup;
        }
    }


}
