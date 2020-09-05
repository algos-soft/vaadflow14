package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
import it.algos.vaadflow14.backend.service.ATextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VIRGOLA;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 31-ago-2020
 * Time: 12:29
 * Layer around boolean value <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ARadioField extends AField<String> {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ATextService text;

    private AETypeBoolField typeBool;

    private String boolEnum = VUOTA;

    private RadioButtonGroup<String> radioGroup;

    private List<String> items;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(AEmailField.class, caption) <br>
     *
     * @param typeBool per la tipologia di visualizzazione
     * @param caption  label visibile del field
     */
    public ARadioField(String caption, AETypeBoolField typeBool) {
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
    public ARadioField(String fieldKey, AETypeBoolField typeBool, String caption, String boolEnum) {
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
        radioGroup = new RadioButtonGroup<>();
        items=getItems();

        if (typeBool != null) {
            switch (typeBool) {
                case checkBox:
                    break;
                case radioTrueFalse:
                    radioGroup.setItems(Arrays.asList("Vero", "Falso"));
                    radioGroup.setLabel(caption);
                    break;
                case radioSiNo:
                    radioGroup.setItems(items);
                    radioGroup.setLabel(caption);
                    break;
                case radioCustomHoriz:
                    radioGroup.setItems(items);
                    break;
                case radioCustomVert:
                    radioGroup.setItems(items);
                    radioGroup.setLabel(caption);
                    //                    radioGroup.getElement().setAttribute("style", "spacing:0em; margin:0em; padding:0em;");
                    radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
                    break;
                default:
                    //                    logger.warn("Switch - caso non definito", this.getClass(), "nomeDelMetodo");
                    break;
            }
        }

        add(radioGroup);
    }


    protected List<String> getItems() {
        List<String> items;

        if (text.isValid(boolEnum) && boolEnum.contains(VIRGOLA)) {
            items = text.getArray(boolEnum);
        } else {
            items = Arrays.asList("Si", "No");
        }

        return items;
    }


    @Override
    protected void setModelValue(String newModelValue, boolean fromClient) {
        super.setModelValue(newModelValue, fromClient);
    }


    @Override
    public String getValue() {
        return radioGroup.getValue();
    }


    @Override
    public void setValue(String value) {
        radioGroup.setValue(value);
    }


    @Override
    protected String generateModelValue() {
        return radioGroup.getValue();
    }


    @Override
    protected void setPresentationValue(String value) {
        radioGroup.setValue(value);
    }


    @Override
    public RadioButtonGroup getBinder() {
        return radioGroup;
    }

}
