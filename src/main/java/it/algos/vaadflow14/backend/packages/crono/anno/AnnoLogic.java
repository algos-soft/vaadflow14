package it.algos.vaadflow14.backend.packages.crono.anno;

import com.google.gson.Gson;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.packages.crono.CronoLogic;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import it.algos.vaadflow14.backend.service.ADateService;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 07:45
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Anno ; <br>
 * 2) StaticContextAccessor.getBean(Anno.class) (senza parametri) <br>
 * 3) appContext.getBean(Anno.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnnoLogic extends CronoLogic {

    /**
     * Costanti usate nell' ordinamento delle categorie
     */
    public static final int ANNO_INIZIALE = 2000;

    public static final int ANTE_CRISTO = 1000;

    public static final int DOPO_CRISTO = 2030;

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
    public ADateService date;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public AnnoLogic() {
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
    public AnnoLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Anno.class;
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
        List<String> blu = new ArrayList<>();
        List<String> red = new ArrayList<>();

        blu.add("Pacchetto convenzionale di 3030 anni. 1000 anni ANTE Cristo e 2030 anni DOPO Cristo");
        blu.add("Sono indicati gli anni bisestili secondo il calendario Giuliano (fino al 1582) e Gregoriano poi");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente bottone 'Esporta' e comboBox selezione 'Secolo'");
            red.add("Manca providerData e pagination. Troppi records. Browser lentissimo. Metodo refreshGrid() provvisorio per mostrare solo una trentina di records");
        }

        return new AlertWrap(null, blu, red, false);
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox("secolo");
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param ordine    (obbligatorio, unico)
     * @param anno      (obbligatorio, unico)
     * @param bisestile (obbligatorio)
     * @param secolo    di riferimento (obbligatorio)
     *
     * @return la nuova entity appena creata e salvata
     */
    public Anno creaIfNotExist(int ordine, String anno, boolean bisestile, Secolo secolo) {
        return (Anno) checkAndSave(newEntity(ordine, anno, bisestile, secolo));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Anno newEntity() {
        return newEntity(0, VUOTA, false, (Secolo) null);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine    (obbligatorio, unico)
     * @param anno      (obbligatorio, unico)
     * @param bisestile (obbligatorio)
     * @param secolo    di riferimento (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Anno newEntity(int ordine, String anno, boolean bisestile, Secolo secolo) {
        Anno newEntityBean = Anno.builderAnno()

                .ordine(ordine > 0 ? ordine : getNewOrdine())

                .anno(text.isValid(anno) ? anno : null)

                .bisestile(bisestile)

                .secolo(secolo)

                .build();

        return (Anno) fixKey(newEntityBean);
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
    public Anno findById(String keyID) {
        return (Anno) super.findById(keyID);
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
    public Anno findByKey(String keyValue) {
        return (Anno) super.findByKey(keyValue);
    }

    //    /**
    //     * Aggiorna gli items della Grid, utilizzando i filtri. <br>
    //     * Chiamato per modifiche effettuate ai filtri, popup, newEntity, deleteEntity, ecc... <br>
    //     */
    //    //@todo provvisorio perché è troppo lento a visualizzare tutti i records
    //    @Override
    //    public void refreshGrid() {
    //        List<? extends AEntity> items;
    //        Query query = new Query();
    //
    //        if (grid != null && grid.getGrid() != null) {
    //            updateFiltri();
    //            //            items = mongo.findAll(entityClazz, filtri, sortView);
    //            query.addCriteria(Criteria.where("ordine").gte(1990).lte(2025));
    //            query.with(Sort.by(Sort.Direction.ASC, "ordine"));
    //            items = mongo.findAll(entityClazz, query);
    //            grid.deselectAll();
    //            grid.refreshAll();
    //            grid.setItems(items);
    //        }
    //    }


    public List<Anno> fetchAnni(int offset, int limit) {
        List<Anno> lista = new ArrayList<>();
        Gson gson = new Gson();
        Anno anno;

        List<Document> products = mongo.mongoOp.getCollection("anno").find().skip(offset).limit(limit).into(new ArrayList<>());

        for (Document doc : products) {

            // 1. JSON file to Java object
            anno = gson.fromJson(doc.toJson(), Anno.class);
            lista.add(anno);
        }

        return lista;
    }

    //    /**
    //     * Creazione di alcuni dati iniziali <br>
    //     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo in alcuni casi) <br>
    //     * I dati possono essere presi da una Enumeration o creati direttamente <br>
    //     * Deve essere sovrascritto <br>
    //     *
    //     * @return false se non esiste il metodo sovrascritto
    //     * ....... true se esiste il metodo sovrascritto è la collection viene ri-creata
    //     */
    //    @Override
    //    public boolean reset() {
    //        super.deleteAll();
    //        int ordine;
    //        String nome;
    //        AESecolo secoloEnum;
    //        Secolo secolo;
    //        String titoloSecolo;
    //        boolean bisestile = false;
    //
    //        //--costruisce gli anni prima di cristo dal 1000
    //        for (int k = ANTE_CRISTO; k > 0; k--) {
    //            ordine = ANNO_INIZIALE - k;
    //            nome = k + AESecolo.TAG_AC;
    //            secoloEnum = AESecolo.getSecoloAC(k);
    //            titoloSecolo = secoloEnum.getNome();
    //            titoloSecolo = titoloSecolo.toLowerCase();
    //            titoloSecolo = text.levaSpazi(titoloSecolo);
    //            secolo = (Secolo) mongo.findById(Secolo.class, titoloSecolo);
    //            bisestile = false; //non ci sono anni bisestili prima di Cristo
    //            if (ordine != ANNO_INIZIALE && secolo != null && text.isValid(nome)) {
    //                creaIfNotExist(ordine, nome, bisestile, secolo);
    //            }
    //        }
    //
    //        //--costruisce gli anni dopo cristo fino al 2030
    //        for (int k = 1; k <= DOPO_CRISTO; k++) {
    //            ordine = k + ANNO_INIZIALE;
    //            nome = k + FlowCost.VUOTA;
    //            secoloEnum = AESecolo.getSecoloDC(k);
    //            titoloSecolo = secoloEnum.getNome();
    //            titoloSecolo = titoloSecolo.toLowerCase();
    //            titoloSecolo = text.levaSpazi(titoloSecolo);
    //            secolo = (Secolo) mongo.findById(Secolo.class, titoloSecolo);
    //            bisestile = date.bisestile(k);
    //            if (ordine != ANNO_INIZIALE && secolo != null && text.isValid(nome)) {
    //                creaIfNotExist(ordine, nome, bisestile, secolo);
    //            }
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
//        AIResult resultCollectionPropedeutica;
//        int ordine;
//        String nome;
//        AESecolo secoloEnum;
//        Secolo secolo;
//        String titoloSecolo;
//        boolean bisestile = false;
//        int numRec = 0;
//
//        if (result.isErrato()) {
//            return result;
//        }
//
//        resultCollectionPropedeutica = checkSecolo();
//        if (resultCollectionPropedeutica.isValido()) {
//            logger.log(AETypeLog.checkData, resultCollectionPropedeutica.getMessage());
//        }
//        else {
//            return resultCollectionPropedeutica;
//        }
//
//        //--costruisce gli anni prima di cristo dal 1000
//        for (int k = ANTE_CRISTO; k > 0; k--) {
//            ordine = ANNO_INIZIALE - k;
//            nome = k + AESecolo.TAG_AC;
//            secoloEnum = AESecolo.getSecoloAC(k);
//            titoloSecolo = secoloEnum.getNome();
//            titoloSecolo = titoloSecolo.toLowerCase();
//            titoloSecolo = text.levaSpazi(titoloSecolo);
//            secolo = (Secolo) mongo.findById(Secolo.class, titoloSecolo);
//            bisestile = false; //non ci sono anni bisestili prima di Cristo
//            if (ordine != ANNO_INIZIALE && secolo != null && text.isValid(nome)) {
//                if (creaIfNotExist(ordine, nome, bisestile, secolo) != null) {
//                    numRec++;
//                }
//            }
//        }
//
//        //--costruisce gli anni dopo cristo fino al 2030
//        for (int k = 1; k <= DOPO_CRISTO; k++) {
//            ordine = k + ANNO_INIZIALE;
//            nome = k + FlowCost.VUOTA;
//            secoloEnum = AESecolo.getSecoloDC(k);
//            titoloSecolo = secoloEnum.getNome();
//            titoloSecolo = titoloSecolo.toLowerCase();
//            titoloSecolo = text.levaSpazi(titoloSecolo);
//            secolo = (Secolo) mongo.findById(Secolo.class, titoloSecolo);
//            bisestile = date.bisestile(k);
//            if (ordine != ANNO_INIZIALE && secolo != null && text.isValid(nome)) {
//                if (creaIfNotExist(ordine, nome, bisestile, secolo) != null) {
//                    numRec++;
//                }
//            }
//        }
//
//        return super.fixPostReset(AETypeReset.hardCoded, numRec);
//    }
//
//    private AIResult checkSecolo() {
//        String collection = "secolo";
//        SecoloLogic secoloLogic;
//
//        if (mongo.isValid(collection)) {
//            return AResult.valido("La collezione " + collection + " esiste già e non è stata modificata");
//        }
//        else {
//            secoloLogic = appContext.getBean(SecoloLogic.class);
//            if (secoloLogic == null) {
//                return AResult.errato("Manca la classe SecoloLogic");
//            }
//            else {
//                return secoloLogic.resetEmptyOnly();
//            }
//        }
//    }

}