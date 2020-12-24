package it.algos.vaadflow14.backend.packages.crono.giorno;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.packages.crono.CronoLogic;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import it.algos.vaadflow14.backend.packages.crono.mese.MeseLogic;
import it.algos.vaadflow14.ui.header.AlertWrap;
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
 * Date: ven, 14-ago-2020
 * Time: 15:27
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Giorno ; <br>
 * 2) StaticContextAccessor.getBean(Giorno.class) (senza parametri) <br>
 * 3) appContext.getBean(Giorno.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GiornoLogic extends CronoLogic {


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
    public MeseLogic meseLogic;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public GiornoLogic() {
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
    public GiornoLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Giorno.class;
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

        blu.add("Giorni dell' anno. 366 giorni per tenere conto dei 29 giorni di febbraio negli anni bisestili");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente bottone 'Esporta' e comboBox selezione 'Mese'");
        }

        return new AlertWrap(null, blu, red, false);
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox("mese");
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param giorno (obbligatorio, unico)
     * @param ordine (obbligatorio, unico)
     * @param mese   di riferimento (obbligatorio)
     *
     * @return la nuova entity appena creata e salvata
     */
    public Giorno creaIfNotExist(int ordine, String giorno, Mese mese) {
        return (Giorno) checkAndSave(newEntity(ordine, giorno, mese));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Giorno newEntity() {
        return newEntity(0, VUOTA, (Mese) null);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param ordine (obbligatorio, unico)
     * @param giorno (obbligatorio, unico)
     * @param mese   di riferimento (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Giorno newEntity(int ordine, String giorno, Mese mese) {
        Giorno newEntityBean = Giorno.builderGiorno()

                .ordine(ordine > 0 ? ordine : getNewOrdine())

                .giorno(text.isValid(giorno) ? giorno : null)

                .mese(mese)

                .build();

        return (Giorno) fixKey(newEntityBean);
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
    public Giorno findById(String keyID) {
        return (Giorno) super.findById(keyID);
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
    public Giorno findByKey(String keyValue) {
        return (Giorno) super.findByKey(keyValue);
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
    //        String titolo;
    //        String titoloMese;
    //        List<HashMap> lista;
    //        Mese mese;
    //
    //        //costruisce i 366 records
    //        lista = date.getAllGiorni();
    //        for (HashMap mappaGiorno : lista) {
    //            titolo = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
    //            titoloMese = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO);
    //            mese = (Mese) mongo.findById(Mese.class, titoloMese);
    //            ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);
    //
    //            creaIfNotExist(ordine, titolo, mese);
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
//        String titolo;
//        String titoloMese;
//        List<HashMap> lista;
//        Mese mese;
//        int numRec = 0;
//
//        if (result.isErrato()) {
//            return result;
//        }
//
//        resultCollectionPropedeutica = checkMese();
//        if (resultCollectionPropedeutica.isValido()) {
//            logger.log(AETypeLog.checkData, resultCollectionPropedeutica.getMessage());
//        }
//        else {
//            return resultCollectionPropedeutica;
//        }
//
//        //costruisce i 366 records
//        lista = date.getAllGiorni();
//        for (HashMap mappaGiorno : lista) {
//            titolo = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
//            titoloMese = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO);
//            mese = (Mese) mongo.findById(Mese.class, titoloMese);
//            ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);
//
//            numRec = creaIfNotExist(ordine, titolo, mese) != null ? numRec + 1 : numRec;
//        }
//
//        return super.fixPostReset(AETypeReset.file, numRec);
//    }
//
//    private AIResult checkMese() {
//        String collection = "mese";
//        MeseLogic meseLogic;
//
//        if (mongo.isValid(collection)) {
//            return AResult.valido("La collezione " + collection + " esiste già e non è stata modificata");
//        }
//        else {
//            meseLogic = appContext.getBean(MeseLogic.class);
//            if (meseLogic == null) {
//                return AResult.errato("Manca la classe MeseLogic");
//            }
//            else {
//                return meseLogic.resetEmptyOnly();
//            }
//        }
//    }

}

