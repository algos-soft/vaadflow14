package it.algos.vaadflow14.backend.packages.anagrafica.via;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.logic.ALogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 10-set-2020
 * Time: 11:30
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Via ; <br>
 * 2) StaticContextAccessor.getBean(Via.class) (senza parametri) <br>
 * 3) appContext.getBean(Via.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ViaLogic extends ALogic {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ViaService viaService;

    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public ViaLogic() {
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
    public ViaLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Via.class;
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

        super.usaBottoneDeleteAll = true;
        super.usaBottoneResetList = true;
    }


//    /**
//     * Crea e registra una entity solo se non esisteva <br>
//     * Deve esistere la keyPropertyName della collezione, in modo da poter creare una nuova entity <br>
//     * solo col valore di un parametro da usare anche come keyID <br>
//     * Controlla che non esista già una entity con lo stesso keyID <br>
//     * Deve esistere il metodo newEntity(keyPropertyValue) con un solo parametro <br>
//     *
//     * @param keyPropertyValue obbligatorio
//     *
//     * @return la nuova entity appena creata e salvata
//     */
//    @Override
//    public Via creaIfNotExist(String keyPropertyValue) {
//        return (Via) checkAndSave(newEntity(keyPropertyValue));
//    }


//    /**
//     * Crea e registra una entity solo se non esisteva <br>
//     *
//     * @param aeVia: enumeration per la creazione-reset di tutte le entities
//     *
//     * @return la nuova entity appena creata e salvata
//     */
//    public Via creaIfNotExist(AEVia aeVia) {
//        return creaIfNotExist(aeVia.getPos(), aeVia.toString());
//    }


//    /**
//     * Crea e registra una entity solo se non esisteva <br>
//     *
//     * @param ordine di presentazione nel popup/combobox (obbligatorio, unico)
//     * @param nome   nome completo (obbligatorio, unico)
//     *
//     * @return true se la nuova entity è stata creata e salvata
//     */
//    public Via creaIfNotExist(int ordine, String nome) {
//        return (Via) checkAndSave(newEntity(ordine, nome));
//    }


//    /**
//     * Creazione in memoria di una nuova entity che NON viene salvata <br>
//     * Usa il @Builder di Lombok <br>
//     * Eventuali regolazioni iniziali delle property <br>
//     *
//     * @return la nuova entity appena creata (non salvata)
//     */
//    public Via newEntity() {
//        return newEntity(0, VUOTA);
//    }


//    /**
//     * Creazione in memoria di una nuova entity che NON viene salvata <br>
//     * Usa il @Builder di Lombok <br>
//     * Eventuali regolazioni iniziali delle property <br>
//     *
//     * @param nome nome completo (obbligatorio, unico)
//     *
//     * @return la nuova entity appena creata (non salvata)
//     */
//    public Via newEntity(String nome) {
//        return newEntity(0, nome);
//    }


//    /**
//     * Creazione in memoria di una nuova entity che NON viene salvata <br>
//     * Usa il @Builder di Lombok <br>
//     * Eventuali regolazioni iniziali delle property <br>
//     *
//     * @param ordine di presentazione nel popup/combobox (obbligatorio, unico)
//     * @param nome   nome completo (obbligatorio, unico)
//     *
//     * @return la nuova entity appena creata (non salvata)
//     */
//    public Via newEntity(int ordine, String nome) {
//        Via newEntityBean = Via.builderVia()
//
//                .ordine(ordine > 0 ? ordine : getNewOrdine())
//
//                .nome(text.isValid(nome) ? nome : null)
//
//                .build();
//
//        return (Via) fixKey(newEntityBean);
//    }

//    /**
//     * Retrieves an entity by its id.
//     *
//     * @param keyID must not be {@literal null}.
//     *
//     * @return the entity with the given id or {@literal null} if none found
//     *
//     * @throws IllegalArgumentException if {@code id} is {@literal null}
//     */
//    @Override
//    public Via findById(String keyID) {
//        return (Via) super.findById(keyID);
//    }

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
//    public Via findByKey(String keyValue) {
//        return (Via) super.findByKey(keyValue);
//    }


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
//        return viaService.resetEmptyOnly();
//        AIResult result = super.resetEmptyOnly();
//        int numRec = 0;
//
//        if (result.isErrato()) {
//            return result;
//        }
//
//        for (AEVia aeVia : AEVia.values()) {
//            numRec = creaIfNotExist(aeVia) != null ? numRec+1 : numRec;
//        }
//
//        return super.fixPostReset(AETypeReset.enumeration,numRec);
//    }
}