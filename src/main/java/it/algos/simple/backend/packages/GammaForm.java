package it.algos.simple.backend.packages;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.simple.ui.ProvaField;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.ui.fields.ATextField;
import it.algos.vaadflow14.ui.form.AForm;
import it.algos.vaadflow14.ui.form.WrapForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static it.algos.vaadflow14.backend.application.FlowCost.FIELD_CODE;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 05-set-2020
 * Time: 08:04
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GammaForm extends AForm {

    //    private ATextField fieldText;

    //    private ATextAreaField fieldArea;

    //    private ADateTimeField fieldDateTime;

    /**
     * Mappa di tutti i fields del form <br>
     * La chiave è la propertyName del field <br>
     * Serve per recuperarli dal nome per successive elaborazioni <br>
     */
    protected LinkedHashMap<String, CustomField> fieldsMap2;

    private CustomField field;


    public GammaForm(WrapForm wrap) {
        super(wrap);
    }


    /**
     * Preferenze standard <br>
     * Normalmente il primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    /**
     * Regola alcune properties (grafiche e non grafiche) <br>
     * Regola la business logic di questa classe <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixProperties() {
        super.fixProperties();
    }


    /**
     * Crea i fields normali <br>
     * Associa i fields normali al binder <br>
     * Trasferisce (binder read) i valori dal DB alla UI <br>
     * <p>
     * Lista ordinata di tutti i fields normali del form <br>
     * Serve per presentarli (ordinati) dall' alto in basso nel form <br>
     */
    @Override
    public void creaFieldsBinder() {
        fieldsMap2 = new LinkedHashMap<>();

        //        fieldText = appContext.getBean(ATextField.class, FIELD_CODE, "testo");
        //        binder.forField(fieldText.getBinder()).bind(FIELD_CODE);
        //
        //        fieldArea = appContext.getBean(ATextAreaField.class, FIELD_NOTE, "area");
        //        binder.forField(fieldArea.getBinder()).bind(FIELD_NOTE);
        //
        //        fieldDateTime = appContext.getBean(ADateTimeField.class, "uno", "datetime");
        //        binder.forField(fieldDateTime).bind("uno");

        //        fieldProva = appContext.getBean(ProvaField.class);
        //        binder.forField(fieldProva).bind("uno");
        field = creaField(binder, FIELD_CODE, AETypeField.text);
        fieldsMap2.put(FIELD_CODE,field);

        field = creaField(binder, "uno", AETypeField.localDateTime);
        fieldsMap2.put("uno", field);

        binder.readBean(entityBean);
    }


    public CustomField creaField(Binder binder, String fieldKey, AETypeField type) {
        CustomField field = null;

        switch (type) {
            case text:
                field = appContext.getBean(ATextField.class, fieldKey, "caption");
                break;
            case localDateTime:
                field = appContext.getBean(ProvaField.class,fieldKey,"prova");
                break;
            default:
                logger.warn("Switch - caso non definito", this.getClass(), "nomeDelMetodo");
                break;
        }

        //--aggiungere i validator
        binder.forField(field).bind(fieldKey);

        return field;
    }


    /**
     * Aggiunge ogni singolo field della lista fieldsList al layout <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addFieldsToLayout() {
        for (CustomField field : fieldsMap2.values()) {
            topLayout.add(field);
        }
    }


    /**
     * Crea una mappa fieldMap, per recuperare i fields dal nome <br>
     */
    @Override
    protected void creaMappaFields() {
    }


    /**
     * Regola in lettura eventuali valori NON associati al binder. <br>
     * Dal DB alla UI <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void readFieldsExtra() {
    }


    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dalla  UI al DB
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void writeFieldsExtra() {
    }

}
