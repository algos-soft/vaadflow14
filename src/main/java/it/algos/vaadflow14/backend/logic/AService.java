package it.algos.vaadflow14.backend.logic;

import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.entity.ACEntity;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AETypeReset;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.packages.company.Company;
import it.algos.vaadflow14.backend.service.AAbstractService;
import it.algos.vaadflow14.backend.service.AIService;
import it.algos.vaadflow14.backend.wrapper.AResult;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static it.algos.vaadflow14.backend.application.FlowCost.FIELD_ORDINE;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 21-dic-2020
 * Time: 07:18
 * <p>
 * Layer di collegamento del backend con mongoDB <br>
 * Classe astratta di servizio per la Entity di un package <br>
 * Le sottoclassi concrete sono SCOPE_SINGLETON e non mantengono dati <br>
 * L'unico dato mantenuto nelle sottoclassi concrete:
 * la property final entityClazz <br>
 * Se la sottoclasse xxxService non esiste (non è indispensabile), usa la classe
 * generica EntityService; i metodi esistono ma occorre un cast in uscita <br>
 */
public abstract class AService extends AAbstractService implements AIService {

    /**
     * The Entity Class  (obbligatoria sempre e final)
     */
    protected final Class<? extends AEntity> entityClazz;


    /**
     * Flag di preferenza per specificare la property della entity da usare come ID <br>
     */
    protected String keyPropertyName;


    /**
     * Costruttore senza parametri <br>
     * Regola la entityClazz (final) associata a questo service <br>
     */
    public AService(final Class<? extends AEntity> entityClazz) {
        this.entityClazz = entityClazz;
    }

    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        this.fixPreferenze();
    }


    /**
     * Preferenze usate da questo service <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.keyPropertyName = annotation.getKeyPropertyName(entityClazz);
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
    public AEntity creaIfNotExist(final String keyPropertyValue) {
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
    public AEntity checkAndSave(final AEntity newEntityBean) {
        AEntity entityBean;
        boolean valido = false;
        String message = VUOTA;

        //--controlla che la newEntityBean non esista già
        if (isEsiste(newEntityBean.id)) {
            return null;
        }

        valido = true;

        if (valido) {
            entityBean = beforeSave(newEntityBean, AEOperation.addNew);
            valido = mongo.insert(entityBean) != null;
            return entityBean;
        }
        else {
            message = "Duplicate key error ";
            message += beanService.getModifiche(newEntityBean);
            logger.warn(message, this.getClass(), "checkAndSave");
            return newEntityBean;
        }
    }

    /**
     * Check the existence of a single entity. <br>
     *
     * @param keyId chiave identificativa
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
    public AEntity beforeSave(final AEntity entityBean, final AEOperation operation) {
        AEntity entityBeanWithID;
        Company company;

        entityBeanWithID = fixKey(entityBean);

        if (FlowVar.usaCompany && entityBeanWithID instanceof ACEntity) {
            company = ((ACEntity) entityBeanWithID).company;
            company = company != null ? company : vaadinService.getCompany();
            if (company == null) {
                return null;
            }
            else {
                ((ACEntity) entityBeanWithID).company = company;
            }
        }

        if (annotation.usaCreazioneModifica(entityClazz)) {
            if (operation == AEOperation.addNew) {
                entityBeanWithID.creazione = LocalDateTime.now();
            }
            if (operation != AEOperation.showOnly) {
                if (beanService.isModificata(entityBeanWithID)) {
                    entityBeanWithID.modifica = LocalDateTime.now();
                }
            }
        }

        return entityBeanWithID;
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

    protected AIResult fixPostReset(final AETypeReset type, final int numRec) {
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
     * Ordine di presentazione (facoltativo) <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property <br>
     * Incrementa di uno il risultato <br>
     */
    protected int getNewOrdine() {
        return mongo.getNewOrder(entityClazz, FIELD_ORDINE);
    }


}
