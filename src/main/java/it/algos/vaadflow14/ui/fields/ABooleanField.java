package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
import it.algos.vaadflow14.backend.service.ATextService;
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

    private RadioButtonGroup<Boolean> radioGroup;

    private AETypeBoolField typeBool;

    private String boolEnum = VUOTA;

    private String firstItem;

    private String secondItem;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(AEmailField.class, caption) <br>
     *
     * @param typeBool per la tipologia di visualizzazione
     * @param caption  label visibile del field
     */
    public ABooleanField(String caption, AETypeBoolField typeBool) {
        this(VUOTA, typeBool, caption, VUOTA);
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(ABooleanField.class, fieldKey, typeBool, caption) <br>
     *
     * @param fieldKey nome interno del field
     * @param typeBool per la tipologia di visualizzazione
     * @param caption  label visibile del field
     * @param boolEnum valori custom della scelta booleana
     */
    public ABooleanField(String fieldKey, AETypeBoolField typeBool, String caption, String boolEnum) {
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
        //@todo Funzionalità ancora da implementare
        //@todo Per adesso funziona solo il checkbox
        typeBool = AETypeBoolField.checkBox;
        //@todo Funzionalità ancora da implementare

        initView();
    }


    protected void initView() {
        usaCheckBox = typeBool == AETypeBoolField.checkBox;
        List<Boolean> items = new ArrayList<>();

        if (usaCheckBox) {
            checkBox = new Checkbox();
        } else {
            radioGroup = new RadioButtonGroup<>();
        }

        items.add(true);
        items.add(false);

        if (typeBool != null) {
            switch (typeBool) {
                case checkBox:
                    checkBox.setLabelAsHtml(caption);
                    break;
                case radioTrueFalse:
                    //                    radioGroup.setItems("Vero", "Falso");
                    radioGroup.setItems(items);
                    radioGroup.setLabel(caption);
                    radioGroup.setRenderer(new TextRenderer<>(e -> "mario"));
                    break;
                case radioSiNo:
                    //                    radioGroup.setItems("Si", "No");
                    radioGroup.setItems(items);
                    radioGroup.setLabel(caption);
                    radioGroup.setRenderer(new TextRenderer<>(e -> "aldo"));
                    break;
                case radioCustomHoriz:
                    radioGroup.setItems(items);
                    radioGroup.setRenderer(new TextRenderer<>(e -> "marco"));
                    break;
                case radioCustomVert:
                    radioGroup.setItems(items);
                    radioGroup.setLabel(caption);
                    radioGroup.setRenderer(new TextRenderer<>(e -> "franco"));
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
        if (usaCheckBox) {
            //            return checkBox.getValue();
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void setValue(Boolean value) {
        super.setValue(value);
    }


    @Override
    protected Boolean generateModelValue() {
        if (usaCheckBox) {
            return checkBox.getValue();
        } else {
            return true;
        }
    }


    @Override
    protected void setPresentationValue(Boolean value) {
        if (!usaCheckBox) {
            if (value) {
                radioGroup.setValue(value);
            } else {
                radioGroup.setValue(value);
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
