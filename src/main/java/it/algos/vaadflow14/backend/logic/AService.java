package it.algos.vaadflow14.backend.logic;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.entity.ACEntity;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AESearch;
import it.algos.vaadflow14.backend.enumeration.AETypeReset;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.packages.company.Company;
import it.algos.vaadflow14.backend.service.AAbstractService;
import it.algos.vaadflow14.backend.service.ABeanService;
import it.algos.vaadflow14.backend.service.AIService;
import it.algos.vaadflow14.backend.service.AVaadinService;
import it.algos.vaadflow14.backend.wrapper.AResult;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import it.algos.vaadflow14.ui.form.AForm;
import it.algos.vaadflow14.ui.form.AGenericForm;
import it.algos.vaadflow14.ui.header.AHeader;
import it.algos.vaadflow14.ui.header.AHeaderList;
import it.algos.vaadflow14.ui.header.AHeaderWrap;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 21-dic-2020
 * Time: 07:18
 * <p>
 * Classe astratta di gestione dei 'service' di una Entity e di un package <br>
 * Collegamento tra il 'backend' e le views <br>
 * Le sottoclassi concrete sono SCOPE_SINGLETON <br>
 * <p>
 * Questo 'service' garantisce i metodi di collegamento per accedere al database <br>
 * Contiene i riferimenti ad altre classi per usarli nelle sottoclassi concrete <br>
 * I riferimenti sono 'public' per poterli usare con TestUnit <br>
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public abstract class AService extends AAbstractService implements AIService {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AVaadinService vaadinService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ABeanService beanService;

    /**
     * Flag di preferenza per aprire il dialog di detail con un bottone Edit. Normalmente true. <br>
     */
    public boolean usaBottoneEdit;

    /**
     * The Entity Class  (obbligatoria sempre; in ViewForm può essere ricavata dalla entityBean)
     */
    protected Class<? extends AEntity> entityClazz;

    /**
     * Flag di preferenza per specificare la property della entity da usare come ID <br>
     */
    protected String keyPropertyName;

    /**
     * Flag di preferenza per selezionare la ricerca testuale: <br>
     * 1) nessuna <br>
     * 2) campo editText di selezione per una property specificata in searchProperty <br>
     * 3) bottone che apre un dialogo di selezione <br>
     */
    protected AESearch searchType;

    /**
     * Flag di preferenza per specificare la property della entity su cui effettuare la ricerca <br>
     * Ha senso solo se searchType=EASearch.editField
     */
    protected String searchProperty;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneDeleteAll;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneResetList;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default true. <br>
     */
    protected boolean usaBottoneNew;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottonePaginaWiki;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneExport;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneResetForm;

    /**
     * Flag di preferenza per l' utilizzo dei bottoni di spostamento. Di default false. <br>
     */
    protected boolean usaBottoniSpostamentoForm;

    /**
     * Flag di preferenza per specificare il titolo della pagina wiki da mostrare in lettura <br>
     */
    protected String wikiPageTitle;


    /**
     * Flag di preferenza per i messaggi di avviso in alertPlacehorder <br>
     * Si può usare la classe AHeaderWrap con i messaggi suddivisi per ruolo (user, admin, developer) <br>
     * Oppure si può usare la classe AHeaderList con i messaggi in Html (eventualmente colorati) <br>
     * Di default false <br>
     */
    protected boolean usaHeaderWrap;


    /**
     * The Form Class  (obbligatoria per costruire la currentForm)
     */
    protected Class<? extends AForm> formClazz;


    /**
     * Mappa di ComboBox di selezione e filtro <br>
     */
    protected LinkedHashMap<String, ComboBox> mappaComboBox;


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        fixProperties();
        fixPreferenze();
    }


    /**
     * Costruisce le properties di questa istanza <br>
     */
    private void fixProperties() {
        this.mappaComboBox = new LinkedHashMap<>();
        this.fixMappaComboBox();
    }


    /**
     * Preferenze usate da questo service <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.keyPropertyName = annotation.getKeyPropertyName(entityClazz);
        this.searchType = AESearch.nonUsata;
        this.searchProperty = annotation.getSearchPropertyName(entityClazz);
        this.usaBottoneDeleteAll = false;
        this.usaBottoneResetList = false;
        this.usaBottoneNew = true;
        this.usaBottoneExport = false;
        this.usaBottonePaginaWiki = false;
        this.wikiPageTitle = VUOTA;
        this.usaHeaderWrap = true;
        this.usaBottoneEdit = true;
        this.usaBottoneResetForm = false;
        this.usaBottoniSpostamentoForm = false;
        this.formClazz = AGenericForm.class;
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    protected void fixMappaComboBox() {
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     * Deve esistere la keyPropertyName della collezione, in modo da poter creare una nuova entity <br>
     * solo col valore di un parametro da usare anche come keyID <br>
     * Controlla che non esista già una entity con lo stesso keyID <br>
     * Deve esistere il metodo newEntity(keyPropertyValue) con un solo parametro <br>
     *
     * @param keyPropertyValue obbligatorio
     *
     * @return la nuova entity appena creata e salvata
     */
    public Object creaIfNotExist(final String keyPropertyValue) {
        return null;
    }

    /**
     * Crea e registra una entity solo se non esisteva <br>
     * Controlla che la entity sia valida e superi i validators associati <br>
     *
     * @param newEntityBean da registrare
     *
     * @return la nuova entity appena creata e salvata
     */
    public AEntity checkAndSave( AEntity newEntityBean) {
        boolean valido = false;
        String message = VUOTA;

        //--controlla che la newEntityBean non esista già
        if (isEsiste(newEntityBean.id)) {
            return null;
        }

        valido = true;

        if (valido) {
            newEntityBean = beforeSave(newEntityBean, AEOperation.addNew);
            valido = mongo.insert(newEntityBean) != null;
        }
        else {
            message = "Duplicate key error ";
            message += beanService.getModifiche(newEntityBean);
            logger.warn(message, this.getClass(), "checkAndSave");
        }

        return newEntityBean;
    }

    /**
     * Check the existence of a single entity. <br>
     *
     * @param keyId       chiave identificativa
     *
     * @return true if exist
     */
    public boolean isEsiste(final String keyId) {
        return mongo.isEsiste(entityClazz, keyId);
    }


    /**
     * Operazioni eseguite PRIMA di save o di insert <br>
     * Regolazioni automatiche di property <br>
     * Controllo della validità delle properties obbligatorie <br>
     * Controllo per la presenza della company se FlowVar.usaCompany=true <br>
     * Controlla se la entity registra le date di creazione e modifica <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, Edit)
     *
     * @return the modified entity
     */
    public AEntity beforeSave(AEntity entityBean, AEOperation operation) {
        Company company;

        entityBean = fixKey(entityBean);

        if (FlowVar.usaCompany && entityBean instanceof ACEntity) {
            company = ((ACEntity) entityBean).company;
            company = company != null ? company : vaadinService.getCompany();
            if (company == null) {
                return null;
            }
            else {
                ((ACEntity) entityBean).company = company;
            }
        }

        if (annotation.usaCreazioneModifica(entityClazz)) {
            if (operation == AEOperation.addNew) {
                entityBean.creazione = LocalDateTime.now();
            }
            if (operation != AEOperation.showOnly) {
                if (beanService.isModificata(entityBean)) {
                    entityBean.modifica = LocalDateTime.now();
                }
            }
        }

        return entityBean;
    }


    /**
     * Regola la chiave se esiste il campo keyPropertyName. <br>
     * Se la company è nulla, la recupera dal login <br>
     * Se la company è ancora nulla, la entity viene creata comunque
     * ma verrà controllata ancora nel metodo beforeSave() <br>
     *
     * @param newEntityBean to be checked
     *
     * @return the checked entity
     */
    public AEntity fixKey(final AEntity newEntityBean) {
        String keyPropertyName;
        String keyPropertyValue;
        Company company;

        if (text.isEmpty(newEntityBean.id)) {
            keyPropertyName = annotation.getKeyPropertyName(newEntityBean.getClass());
            if (text.isValid(keyPropertyName)) {
                keyPropertyValue = reflection.getPropertyValueStr(newEntityBean, keyPropertyName);
                if (text.isValid(keyPropertyValue)) {
                    keyPropertyValue = text.levaSpazi(keyPropertyValue);
                    newEntityBean.id = keyPropertyValue.toLowerCase();
                }
            }
        }

        if (newEntityBean instanceof ACEntity) {
            company = vaadinService.getCompany();
            ((ACEntity) newEntityBean).company = company;
        }

        return newEntityBean;
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
    public AEntity findById(final String keyID) {
        return mongo.findById(entityClazz, keyID);
    }


    /**
     * Retrieves an entity by its keyProperty.
     *
     * @param keyPropertyValue must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    public AEntity findByKey(final String keyPropertyValue) {
        if (text.isValid(keyPropertyName)) {
            return mongo.findOneUnique(entityClazz, keyPropertyName, keyPropertyValue);
        }
        else {
            return findById(keyPropertyValue);
        }
    }


    /**
     * Creazione o ricreazione di alcuni dati iniziali standard <br>
     * Invocato in fase di 'startup' e dal bottone Reset di alcune liste <br>
     * <p>
     * 1) deve esistere lo specifico metodo sovrascritto
     * 2) deve essere valida la entityClazz
     * 3) deve esistere la collezione su mongoDB
     * 4) la collezione non deve essere vuota
     * <p>
     * I dati possono essere: <br>
     * 1) recuperati da una Enumeration interna <br>
     * 2) letti da un file CSV esterno <br>
     * 3) letti da Wikipedia <br>
     * 4) creati direttamente <br>
     * DEVE essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @return wrapper col risultato ed eventuale messaggio di errore
     */
    public AIResult resetEmptyOnly() {
        String collection;

        if (entityClazz == null) {
            return AResult.errato("Manca la entityClazz nella businessService specifica");
        }

        collection = entityClazz.getSimpleName().toLowerCase();
        if (mongo.isExists(collection)) {
            if (mongo.isValid(entityClazz)) {
                return AResult.errato("La collezione " + collection + " esiste già e non c'è bisogno di crearla");
            }
            else {
                return AResult.valido();
            }
        }
        else {
            return AResult.errato("La collezione " + collection + " non esiste");
        }
    }

    protected AIResult fixPostReset(AETypeReset type, final int numRec) {
        String collection;

        if (entityClazz == null) {
            return AResult.errato("Manca la entityClazz nella businessService specifica");
        }

        collection = entityClazz.getSimpleName().toLowerCase();
        if (mongo.isValid(entityClazz)) {
            return AResult.valido("La collezione " + collection + " era vuota e sono stati inseriti " + numRec + " elementi " + type.get());
        }
        else {
            return AResult.errato("Non è stato possibile creare la collezione " + collection);
        }
    }

    /**
     * Costruisce un (eventuale) layout per avvisi aggiuntivi in alertPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Normalmente ad uso esclusivo del developer <br>
     * Nell' implementazione standard di default NON presenta nessun avviso <br>
     * Recupera dal service specifico gli (eventuali) avvisi <br>
     * Costruisce un' istanza dedicata (secondo il flag usaHeaderWrap) con le liste di avvisi <br>
     * <p>
     * AHeaderWrap:
     * Gli avvisi sono realizzati con label differenziate per colore in base all' utente collegato <br>
     * Se l' applicazione non usa security, il colore è unico <br<
     * Se esiste, inserisce l' istanza (grafica) in alertPlacehorder della view <br>
     * alertPlacehorder viene sempre aggiunto, per poter (eventualmente) essere utilizzato dalle sottoclassi <br>
     * <p>
     * AHeaderList:
     * Gli avvisi sono realizzati con elementi html con possibilità di color e bold <br>
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public AHeader getAlertHeaderLayout(AEVista typeVista) {
        AHeader header = null;
        AlertWrap wrap = getAlertWrap(typeVista);
        List<String> alertHtmlList = getAlertList(typeVista);

        if (usaHeaderWrap) {
            if (wrap != null) {
                header = appContext.getBean(AHeaderWrap.class, wrap);
            }
        }
        else {
            if (alertHtmlList != null) {
                header = appContext.getBean(AHeaderList.class, alertHtmlList);
            }
        }

        return header;
    }

    /**
     * Costruisce un wrapper di liste di informazioni per costruire l' istanza di AHeaderWrap <br>
     * Informazioni (eventuali) specifiche di ogni modulo <br>
     * Deve essere sovrascritto <br>
     * Esempio:     return new AlertWrap(new ArrayList(Arrays.asList("uno", "due", "tre")));
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return wrapper per passaggio dati
     */
    protected AlertWrap getAlertWrap(AEVista typeVista) {
        return null;
    }

    /**
     * Costruisce una lista di informazioni per costruire l' istanza di AHeaderList <br>
     * Informazioni (eventuali) specifiche di ogni modulo <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Esempio:     return new ArrayList(Arrays.asList("uno", "due", "tre"));
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return wrapper per passaggio dati
     */
    protected List<String> getAlertList(AEVista typeVista) {
        String headerAlert = annotation.getHeaderAlert(entityClazz);
        return text.isValid(headerAlert) ? new ArrayList(Arrays.asList(headerAlert)) : new ArrayList<String>();
    }

}
