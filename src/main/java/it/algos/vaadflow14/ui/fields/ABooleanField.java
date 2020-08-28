package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AETypeBool;
import it.algos.vaadflow14.backend.service.ATextService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
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
 * Layer around boolean value <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ABooleanField extends AField<Boolean> {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ATextService text;

    private boolean usaCheckBox;

    private Checkbox checkBox;

    private RadioButtonGroup<String> radioGroup;

    private AETypeBool typeBool;

    private String boolEnum = VUOTA;

    private String firstItem;

    private String secondItem;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ABooleanField.class, fieldKey, typeBool, caption) <br>
     *
     * @param fieldKey nome interno del field
     * @param typeBool per la tipologia di visualizzazione
     * @param caption  label visibile del field
     * @param boolEnum valori custom della scelta booleana
     */
    public ABooleanField(String fieldKey, AETypeBool typeBool, String caption, String boolEnum) {
        this.typeBool = typeBool;
        this.caption = caption;
        this.boolEnum = boolEnum;
    } // end of SpringBoot constructor


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le (eventuali) istanze @Autowired <br>
     * Questo metodo viene chiamato subito dopo che il framework ha terminato l' init() implicito <br>
     * del costruttore e PRIMA di qualsiasi altro metodo <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l' ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        initView();
    }


    protected void initView() {
        usaCheckBox = typeBool == AETypeBool.checkBox;

        if (usaCheckBox) {
            checkBox = new Checkbox();
        } else {
            radioGroup = new RadioButtonGroup<String>();
        }

        if (typeBool != null) {
            switch (typeBool) {
                case checkBox:
                    checkBox.setLabelAsHtml(caption);
                    break;
                case radioTrueFalse:
                    radioGroup.setItems("Vero", "Falso");
                    radioGroup.setLabel(caption);
                    break;
                case radioSiNo:
                    radioGroup.setItems("Si", "No");
                    radioGroup.setLabel(caption);
                    break;
                case radioCustomHoriz:
                    radioGroup.setItems(getItems());
                    break;
                case radioCustomVert:
                    radioGroup.setItems(getItems());
                    radioGroup.setLabel(caption);
                    //                    radioGroup.getElement().setAttribute("style", "spacing:0em; margin:0em; padding:0em;");
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

        if (text.isValid(boolEnum) && boolEnum.contains(VIRGOLA)) {
            parti = boolEnum.split(VIRGOLA);
            if (parti != null && parti.length == 2) {
                firstItem = parti[0].trim();
                secondItem = parti[1].trim();
                firstItem = text.primaMaiuscola(firstItem);
                secondItem = text.primaMaiuscola(secondItem);
                items.add(firstItem);
                items.add(secondItem);
            }
        } else {
            items.add("Si");
            items.add("No");
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
        checkBox.setValue(value);
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
