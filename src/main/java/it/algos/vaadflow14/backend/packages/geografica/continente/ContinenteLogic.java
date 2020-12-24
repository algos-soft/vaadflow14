package it.algos.vaadflow14.backend.packages.geografica.continente;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.packages.geografica.GeografiaLogic;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 29-set-2020
 * Time: 15:41
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Continente ; <br>
 * 2) StaticContextAccessor.getBean(Continente.class) (senza parametri) <br>
 * 3) appContext.getBean(Continente.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContinenteLogic extends GeografiaLogic {


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
    public ContinenteLogic() {
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
    public ContinenteLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Continente.class;
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

        super.usaBottonePaginaWiki = true;
        super.wikiPageTitle = "Continente";
        super.formClazz = ContinenteForm.class;
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param aeContinente: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata e salvata
     */
    public Continente creaIfNotExist(AEContinente aeContinente) {
        return creaIfNotExist(aeContinente.getOrd(), aeContinente.getNome(), aeContinente.isAbitato());
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param nome obbligatorio
     *
     * @return la nuova entity appena creata e salvata
     */
    public Continente creaIfNotExist(int ordine, String nome, boolean abitato) {
        return (Continente) checkAndSave(newEntity(ordine, nome, abitato));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Continente newEntity() {
        return newEntity(0, VUOTA, true);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Continente newEntity(int ordine, String nome, boolean abitato) {
        Continente newEntityBean = Continente.builderContinente()

                .ordine(ordine > 0 ? ordine : getNewOrdine())

                .nome(text.isValid(nome) ? nome : null)

                .abitato(abitato)

                .build();

        return (Continente) fixKey(newEntityBean);
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
    public Continente findById(String keyID) {
        return (Continente) super.findById(keyID);
    }


    /**
     * Retrieves an entity by its keyProperty.
     *
     * @param keyValue must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    public Continente findByKey(String keyValue) {
        return (Continente) super.findByKey(keyValue);
    }

    //    /**
    //     * Creazione di alcuni dati iniziali <br>
    //     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo in alcuni casi) <br>
    //     * I dati possono essere presi da una Enumeration o creati direttamente <br>
    //     * DEVE essere sovrascritto <br>
    //     *
    //     * @return false se non esiste il metodo sovrascritto
    //     * ....... true se esiste il metodo sovrascritto è la collection viene ri-creata
    //     */
    //    @Override
    //    public boolean reset() {
    //        super.deleteAll();
    //
    //        for (AEContinente aeContinente : AEContinente.values()) {
    //            creaIfNotExist(aeContinente);
    //        }
    //
    //        return mongo.isValid(entityClazz);
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
//        AIResult result = super.resetEmptyOnly();
//        int numRec = 0;
//
//        if (result.isErrato()) {
//            return result;
//        }
//
//        for (AEContinente aeContinente : AEContinente.values()) {
//            numRec = creaIfNotExist(aeContinente) != null ? numRec + 1 : numRec;
//        }
//
//        return super.fixPostReset(AETypeReset.enumeration, numRec);
//    }

}