package it.algos.vaadflow14.backend.packages.preferenza;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.data.FlowData;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.enumeration.AESearch;
import it.algos.vaadflow14.backend.enumeration.AETypePref;
import it.algos.vaadflow14.backend.interfaces.AIPreferenza;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.ui.form.AForm;
import it.algos.vaadflow14.ui.form.WrapForm;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.TAG_FLOW_DATA;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 25-ago-2020
 * Time: 21:30
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private PreferenzaLogic ; <br>
 * 2) StaticContextAccessor.getBean(PreferenzaLogic.class, operationForm) (con parametro) <br>
 * 3) appContext.getBean(PreferenzaLogic.class, operationForm) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 * <p>
 * Le preferenze memorizzate nella collezione del mongoDB sono di due tipi:
 * A - Standard, inserite all'avvio del programma prendendole dalla enumeration AEPreferenza <br>
 * B - Specifiche, inserite all'avvio del programma prendendole da una enumeration specifica <br>
 * <p>
 * Ogni preferenza ha alcuni field chiave: <br>
 * A - 'code' per individuare e selezionare la preferenza richiesta; se usaCompany = true, DEVE contenere anche la sigla della company <br>
 * B - 'descrizione' per individuare lo scopo ed il funzionamento della preferenza <br>
 * C - 'type' per suddividere le preferenze secondo la enumeration AETypePref <br>
 * D - 'value' per memorizzare il valore nel mongoDB sotto forma di byte[], convertiti secondo il 'type' <br>
 * <p>
 * Funzionamento:
 * 1 - Quando parte il programma la prima volta (quando è vuota la collection 'preferenza' sul mongoDB), vengono create
 * TUTTE le preferenze 'standard' e 'specifiche' provenienti dalle enumeration
 * 2 - Quando il programma parte le volte successive (quando la collection 'preferenza' sul mongoDB non è vuota),
 * vengono create tutte e sole le preferenze ('standard' e 'specifiche').
 * NON presenti. Quelli presenti NON vengono modificate.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PreferenzaLogic extends ALogic {

    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public APreferenzaService pref;

    /**
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    public FlowData dataInstance;

    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    @Deprecated
    public PreferenzaLogic() {
        this(AEOperation.edit);
    }


    /**
     * Costruttore con parametro <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AFormView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName), operationForm) <br>
     *
     * @param operationForm tipologia di Form in uso
     */
    public PreferenzaLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Preferenza.class;
        this.setDataInstance(dataInstance);
    }


    /**
     * Preferenze standard <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.searchType = AESearch.editField;
        this.searchProperty = annotation.getSearchPropertyName(entityClazz);
        this.usaBottoneDelete = false;
        this.usaBottoneResetList = true;
        this.usaBottoneNew = true;
        this.usaBottoneExport = false;
        this.usaBottonePaginaWiki = false;
        this.wikiPageTitle = VUOTA;
        this.usaBottoneEdit = true;
    }


    /**
     * Informazioni (eventuali) specifiche di ogni modulo, mostrate nella List <br>
     * Costruisce un wrapper di liste di informazioni per costruire l' istanza di AHeaderWrap <br>
     * DEVE essere sovrascritto <br>
     *
     * @return wrapper per passaggio dati
     */
    @Override
    protected AlertWrap getAlertWrapList() {
        List<String> green = new ArrayList<>();
        List<String> blue = new ArrayList<>();
        List<String> red = new ArrayList<>();

        if (AEPreferenza.usaDebug.is()) {
            green.add("PreferenzaLogic è SCOPE_PROTOTYPE mentre APreferenzaService è SCOPE_SINGLETON");
            green.add("PreferenzaLogic è usato come normale classe del package di preferenze e viene ricreato per ogni Grid e Form");
            green.add("APreferenzaService è usato per 'leggere' le preferenze da qualsiasi 'service' singleton");
            green.add("Alcune preferenze sono Enumeration e possono essere lette direttamente: AEPreferenza.usaDebug.is()");
            green.add("Altre preferenze sono inserite dall'utente e possono essere lette dal singleton APreferenzaService: pref.isBool(\"usaDebug\")");
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente il searchField ed il comboBox 'Type'");
        }
        return new AlertWrap(green, blue, red, false);
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox("type");
    }


    /**
     * Costruisce un wrapper di dati <br>
     * I dati sono gestiti da questa 'logic' (nella sottoclasse eventualmente) <br>
     * I dati vengono passati alla View che li usa <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     *
     * @param entityBean interessata
     *
     * @return wrapper di dati per il Form
     */
    @Override
    public WrapForm getWrapForm(AEntity entityBean) {
        WrapForm wrap = super.getWrapForm(entityBean);
        wrap.setUsaBottomLayout(true);
        return wrap;
    }


    /**
     * Costruisce un layout per il Form in bodyPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Costruisce un' istanza dedicata <br>
     * Passa all' istanza un wrapper di dati <br>
     * Inserisce l' istanza (grafica) in bodyPlacehorder della view <br>
     *
     * @param entityBean interessata
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public AForm getBodyFormLayout(AEntity entityBean) {
        currentForm = null;

        //--entityBean dovrebbe SEMPRE esistere (anche vuoto), ma meglio controllare
        if (entityBean != null) {
            currentForm = appContext.getBean(PreferenzaForm.class, this, getWrapForm(entityBean));
        }

        return currentForm;
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param aePref: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata e salvata
     */
    public Preferenza creaIfNotExist(AIPreferenza aePref, boolean generale) {
        return creaIfNotExist(aePref.getKeyCode(), aePref.getDescrizione(), aePref.getType(), aePref.getDefaultValue(), aePref.isCompanySpecifica(), generale, aePref.getNote());
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param code         codice di riferimento (obbligatorio)
     * @param descrizione  (obbligatoria)
     * @param type         (obbligatorio) per convertire in byte[] i valori
     * @param defaultValue (obbligatorio) memorizza tutto in byte[]
     * @param usaCompany   (obbligatorio) se FlowVar.usaCompany=false, sempre false
     * @param generale     (obbligatorio) preferenza di vaadflow, di default true
     *
     * @return la nuova entity appena creata e salvata
     */
    public Preferenza creaIfNotExist(String code, String descrizione, AETypePref type, Object defaultValue, boolean usaCompany, boolean generale, String note) {
        return (Preferenza) checkAndSave(newEntity(code, descrizione, type, defaultValue, usaCompany, generale, note));
    }


//    /**
//     * Creazione in memoria di una nuova entity che NON viene salvata <br>
//     * Usa il @Builder di Lombok <br>
//     * Eventuali regolazioni iniziali delle property <br>
//     *
//     * @return la nuova entity appena creata (non salvata)
//     */
//    public Preferenza newEntity() {
//        return newEntity(VUOTA, VUOTA, (AETypePref) null, (Object) null, FlowVar.usaCompany ? true : false, true, VUOTA);
//    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * <p>
     * //     * @param ordine      di presentazione (obbligatorio con inserimento automatico se è zero)
     *
     * @param code         codice di riferimento (obbligatorio)
     * @param descrizione  (obbligatoria)
     * @param type         (obbligatorio) per convertire in byte[] i valori
     * @param defaultValue (obbligatorio) memorizza tutto in byte[]
     * @param usaCompany   (obbligatorio) se FlowVar.usaCompany=false, sempre false
     * @param generale     (obbligatorio) preferenza di vaadflow, di default true
     * @param note         (facoltativo)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Preferenza newEntity(String code, String descrizione, AETypePref type, Object defaultValue, boolean usaCompany, boolean generale, String note) {
        Preferenza newEntityBean = Preferenza.builderPreferenza()

                .code(text.isValid(code) ? code : null)

                .descrizione(text.isValid(descrizione) ? descrizione : null)

                .type(type != null ? type : AETypePref.string)

                .value(type != null ? type.objectToBytes(defaultValue) : (byte[]) null)

                .usaCompany(FlowVar.usaCompany ? usaCompany : false)

                .generale(generale)

                .build();

        if (text.isValid(note)) {
            newEntityBean.note = note;
        }

        return (Preferenza) fixKey(newEntityBean);
    }


    /**
     * Operazioni eseguite PRIMA di save o di insert <br>
     * Regolazioni automatiche di property <br>
     * Controllo della validità delle properties obbligatorie <br>
     * Controllo per la presenza della company se FlowVar.usaCompany=true <br>
     * Controlla se la entity registra le date di creazione e modifica <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, Edit)
     *
     * @return the modified entity
     */
    @Override
    public AEntity beforeSave(AEntity entityBean, AEOperation operation) {
        Preferenza entityPreferenza = (Preferenza) super.beforeSave(entityBean, operation);

        return fixValue(entityPreferenza);
    }


    /**
     * Regola il valore (obbligatorio) della entity prima di salvarla <br>
     *
     * @param entityPreferenza da regolare prima del save
     *
     * @return true se la entity è stata salvata
     */
    public Preferenza fixValue(Preferenza entityPreferenza) {
        Object value;
        //        Preferenza entity = setValue(keyCode, value, companyPrefix);
        //
        //        if (entity != null) {
        //            entity = (Preferenza) this.save(entity);
        //            salvata = entity != null;
        //        }// end of if cycle

        //        if (value == null) {
        //            entityPreferenza = null;
        //        }
        //        entityPreferenza.value = AETypePref.string.objectToBytes(value);
        //
        return entityPreferenza;
    }


    /**
     * Retrieves an entity by its id.
     *
     * @param keyID must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    public Preferenza findById(String keyID) {
        return (Preferenza) super.findById(keyID);
    }


//    /**
//     * Retrieves an entity by its keyProperty.
//     *
//     * @param keyValue must not be {@literal null}.
//     *
//     * @return the entity with the given id or {@literal null} if none found
//     *
//     * @throws IllegalArgumentException if {@code id} is {@literal null}
//     */
//    @Override
//    public Preferenza findByKey(String keyValue) {
//        return (Preferenza) super.findByKey(keyValue);
//    }


    public Object getValue(String keyID) {
        Object value = null;
        Preferenza pref = findById(keyID);

        if (pref != null) {
            value = pref.getType().bytesToObject(pref.value);
        }

        return value;
    }


    public String getString(String keyID) {
        String value = VUOTA;
        Object objValue = getValue(keyID);

        if (objValue != null && objValue instanceof String) {
            value = (String) objValue;
        }

        return value;
    }


    public Boolean isBool(String keyCode) {
        boolean status = false;
        Object objValue = getValue(keyCode);

        if (objValue != null && objValue instanceof Boolean) {
            status = (boolean) objValue;
        }
        else {
            logger.error("Algos - Preferenze. La preferenza: " + keyCode + " è del tipo sbagliato");
        }

        return status;
    }


    public String getEnumRawValue(String keyID) {
        return getString(keyID);
    }



//    /**
//     * Creazione o ricreazione di alcuni dati iniziali standard <br>
//     * Invocato in fase di 'startup' e dal bottone Reset di alcune liste <br>
//     * <p>
//     * 1) deve esistere lo specifico metodo sovrascritto
//     * 2) deve essere valida la entityClazz
//     * 3) deve esistere la collezione su mongoDB
//     * 4) la collezione non deve essere vuota
//     * <p>
//     * I dati possono essere: <br>
//     * 1) recuperati da una Enumeration interna <br>
//     * 2) letti da un file CSV esterno <br>
//     * 3) letti da Wikipedia <br>
//     * 4) creati direttamente <br>
//     * DEVE essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
//     *
//     * @return wrapper col risultato ed eventuale messaggio di errore
//     */
//    @Override
//    public AIResult resetEmptyOnly() {
//        AIResult result = null;
//        AIData dataClazz;
//
//        if (FlowVar.dataClazz != null) {
//            if (FlowVar.dataClazz.equals(FlowData.class)) {
//                result = dataInstance.resetPreferenze(this,true);
//            }
//            else {
//                dataClazz = (AIData) appContext.getBean(FlowVar.dataClazz);
//                if (dataClazz != null) {
//                    result = dataClazz.resetPreferenze(this,true);
//                }
//            }
//        }
//
//        return result;
//    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(TAG_FLOW_DATA)
    public void setDataInstance(FlowData dataInstance) {
        this.dataInstance = dataInstance;
    }

}