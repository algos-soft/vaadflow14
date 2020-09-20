package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
import it.algos.vaadflow14.backend.enumeration.AETypePref;
import it.algos.vaadflow14.backend.packages.preferenza.Preferenza;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 27-ago-2020
 * Time: 18:05
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class APreferenzaField extends AField<byte[]> {


    private AField valueField;


    private AETypePref type;

    private Preferenza entityBean;

    private AEOperation operationForm;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(APreferenzaField.class, entityBean) <br>
     *
     * @param entityBean    preferenza di riferimento
     * @param operationForm tipologia di Form in uso
     */
    public APreferenzaField(Preferenza entityBean, AEOperation operationForm) {
        this.entityBean = entityBean;
        this.operationForm = operationForm;
        this.type = entityBean.type;
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
        sincro(type);
    }


    @Override
    protected byte[] generateModelValue() {
        //        return entityBean.getType().objectToBytes("mariolino");
        return null;
    }


    @Override
    protected void setPresentationValue(byte[] o) {
    }


    @Override
    public void setWidth(String width) {

    }


    /**
     * Cambia il valueField sincronizzandolo col comboBox
     * Senza valori, perché è attivo SOLO in modalità AddNew (new record)
     */
    public AField sincro(AETypePref type) {
        String tag = "Valore ";
        List<String> items;
        //        String enumValue = getString();

        if (valueField != null) {
            this.remove(valueField);
        }
        valueField = null;

        type = type != null ? type : AETypePref.string;
        switch (type) {
            case string:
                valueField = appContext.getBean(ATextField.class);
                valueField.setLabel(tag + "(string)");
                String message = "L' indirizzo pippoz non è valido";
                ((ATextField) valueField).setErrorMessage(message);
                break;
            case email:
                valueField = appContext.getBean(AEmailField.class);
                valueField.setLabel(tag + "(email)");
                 message = "L' indirizzo eMail non è valido";
                ((AEmailField) valueField).setErrorMessage(message);
                break;
            case integer:
                caption += "(solo interi)";
                valueField = appContext.getBean(AIntegerField.class, caption);
                break;
            case bool:
                valueField = appContext.getBean(ABooleanField.class, AETypeBoolField.radioTrueFalse);
                valueField.setLabel(tag + "(booleano)");
                break;
            case localdate:
                valueField = appContext.getBean(ADateField.class);
                break;
            case localdatetime:
                valueField = appContext.getBean(ADateTimeField.class);
                break;
            case localtime:
                valueField = appContext.getBean(ATimeField.class);
                //                ((ATimePicker) valueField).setStep(Duration.ofMinutes(15));
                break;
            case enumeration:
                //                if (operationForm == AEOperation.addNew) {
                //                    valueField = appContext.getBean(ATextField.class, ENUM_FIELD_NEW);
                //                } else {
                //                    if (enumValue.contains(PUNTO_VIRGOLA)) {
                //                        enumValue = text.levaCodaDa(enumValue, PUNTO_VIRGOLA);
                //                    }
                //                    items = enumerationService.getList(enumValue);
                //                    if (array.isValid(items)) {
                //                        valueField = appContext.getBean(AComboField.class, ENUM_FIELD_SHOW, items);
                //                    }
                //                }
                break;
            default:
                logger.warn("Switch - caso non definito");
                break;
        }

        if (valueField != null) {
            this.add(valueField);
        }

        return valueField;
    }


}
