package it.algos.vaadflow14.backend.packages.geografica.provincia;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.packages.geografica.regione.Regione;
import it.algos.vaadflow14.backend.packages.geografica.regione.RegioneLogic;
import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.ui.fields.AField;
import it.algos.vaadflow14.ui.form.AForm;
import it.algos.vaadflow14.ui.form.WrapForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 16-set-2020
 * Time: 17:48
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProvinciaForm extends AForm {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public RegioneLogic regioneLogic;


    public ProvinciaForm(WrapForm wrap) {
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
     * Crea in automatico i fields normali associati al binder <br>
     * Aggiunge i fields normali al binder <br>
     * Trasferisce (binder read) i valori dal DB alla UI <br>
     * Li aggiunge alla fieldsList <br>
     * Lista ordinata di tutti i fields normali del form <br>
     * Serve per presentarli (ordinati) dall' alto in basso nel form <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaFieldsBinder() {
        super.creaFieldsBinder();
    }


    /**
     * Costruisce una lista ordinata di nomi delle properties del Form. <br>
     * La lista viene usata per la costruzione automatica dei campi e l' inserimento nel binder <br>
     * Nell' ordine: <br>
     * 1) Cerca nell' annotation @AIForm della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica di xxxLogic <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Se serve, modifica l' ordine della lista oppure esclude una property che non deve andare nel binder <br>
     *
     * @return lista di nomi di properties
     */
    @Override
    protected List<String> getPropertyNamesList() {
        List<String> lista = null;

        if (operationForm == AEOperation.addNew) {
            lista = super.getPropertyNamesList();
        } else {
            lista = array.fromString("ordine,nome,sigla,iso,status");
        }

        return lista;
    }


    /**
     * Crea gli eventuali fields extra NON associati al binder, oltre a quelli normali <br>
     * Li aggiunge alla fieldsList <br>
     * Lista ordinata di tutti i fields normali del form <br>
     * Serve per presentarli (ordinati) dall' alto in basso nel form <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaFieldsExtra() {
        super.creaFieldsExtra();

        if (operationForm != AEOperation.addNew) {
            String fieldKey = "regione";
            AField field = fieldService.crea(entityBean, binder, operationForm, fieldKey);
            if (field != null) {
                fieldsList.add(field);
            }
        }
    }


    /**
     * Riordina (eventualmente) la lista fieldsList <br>
     * I fieldsExtra vengono necessariamente inseriti DOPO i fields normali mentre potrebbero dover apparire prima <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void reorderFieldList() {
        super.reorderFieldList();
    }


    /**
     * Aggiunge ogni singolo field della lista fieldsList al layout <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addFieldsToLayout() {
        super.addFieldsToLayout();
    }


    /**
     * Crea una mappa fieldMap, per recuperare i fields dal nome <br>
     */
    @Override
    protected void creaMappaFields() {
        super.creaMappaFields();
    }


    /**
     * Regola in lettura eventuali valori NON associati al binder. <br>
     * Dal DB alla UI <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void readFieldsExtra() {
        super.readFieldsExtra();
    }


    /**
     * Eventuali aggiustamenti finali al layout <br>
     * Aggiunge eventuali altri componenti direttamente al layout grafico (senza binder e senza fieldMap) <br>
     * Regola eventuali valori delle property in apertura della scheda <br>
     * Può essere sovrascritto <br>
     */
    @Override
    protected void fixLayoutFinal() {
        if (operationForm == AEOperation.addNew) {
            super.fixDueCombo(ProvinciaLogic.FIELD_REGIONE, ProvinciaLogic.FIELD_STATO);
        }
    }


    /**
     * Evento generato dal AComboField 'master' <br>
     * DEVE essere sovrascritto <br>
     */
    protected void sincroMaster(HasValue.ValueChangeEvent event) {
        Regione value = (Regione) event.getValue();
        Stato slaveValue = (Stato) fieldSlave.getValue();
        Stato masterValue = value.stato;

        super.sincroDueCombo(masterValue, slaveValue);
    }


    /**
     * Evento generato dal AComboField 'slave' <br>
     * DEVE essere sovrascritto <br>
     */
    protected void sincroSlave(HasValue.ValueChangeEvent event) {
        Stato value = (Stato) event.getValue();

        List items = regioneLogic.findAllByStato(value.id);
        fieldMaster.setItems(items);
        fieldMaster.setItems(items); //@todo Non capisco perché ma se chiamo setItems() solo una volta NON funziona
    }


    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dalla  UI al DB
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void writeFieldsExtra() {
        super.writeFieldsExtra();
    }

}
