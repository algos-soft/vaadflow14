package it.algos.vaadflow14.backend.packages.crono.mese;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEMese;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.packages.company.Company;
import it.algos.vaadflow14.backend.packages.crono.CronoLogic;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 31-lug-2020
 * Time: 22:07
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private MeseLogic ; <br>
 * 2) StaticContextAccessor.getBean(MeseLogic.class) (senza parametri) <br>
 * 3) appContext.getBean(MeseLogic.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MeseLogic extends CronoLogic {


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
    public MeseLogic() {
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
    public MeseLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Mese.class;
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

        blu.add("Mesi dell' anno, coi giorni. Tiene conto degli anni bisestili per il mese di febbraio.");
        blu.add("Ci sono 12 mesi. Non si possono cancellare ne aggiungere elementi.");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente bottone 'Esporta'");
        }

        return new AlertWrap(null, blu, red, false);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param aeMese: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata e salvata
     */
    public Mese creaIfNotExist(AEMese aeMese) {
        return creaIfNotExist(aeMese.getNome(), aeMese.getGiorni(), aeMese.getGiorniBisestili(), aeMese.getSigla());
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param mese            nome completo (obbligatorio, unico)
     * @param giorni          numero di giorni presenti (obbligatorio)
     * @param giorniBisestile numero di giorni presenti in un anno bisestile (obbligatorio)
     * @param sigla           nome abbreviato di tre cifre (obbligatorio, unico)
     *
     * @return la nuova entity appena creata e salvata
     */
    public Mese creaIfNotExist(String mese, int giorni, int giorniBisestile, String sigla) {
        return (Mese) checkAndSave(newEntity(mese, giorni, giorniBisestile, sigla));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Mese newEntity() {
        return newEntity(VUOTA, 0, 0, VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param aeMese: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Mese newEntity(AEMese aeMese) {
        return newEntity(aeMese.getNome(), aeMese.getGiorni(), aeMese.getGiorniBisestili(), aeMese.getSigla());
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param mese            nome completo (obbligatorio, unico)
     * @param giorni          numero di giorni presenti (obbligatorio)
     * @param giorniBisestile numero di giorni presenti in un anno bisestile (obbligatorio)
     * @param sigla           nome abbreviato di tre cifre (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Mese newEntity(String mese, int giorni, int giorniBisestile, String sigla) {
        Mese newEntityBean = Mese.builderMese()

                .ordine(getNewOrdine())

                .mese(text.isValid(mese) ? mese : null)

                .giorni(giorni)

                .giorniBisestile(giorniBisestile)

                .sigla(text.isValid(sigla) ? sigla : null)

                .build();

        return (Mese) fixKey(newEntityBean);
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
    public Mese findById(String keyID) {
        return (Mese) super.findById(keyID);
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
    public Company findByKey(String keyValue) {
        return (Company) super.findByKey(keyValue);
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
//        for (AEMese aeMese : AEMese.values()) {
//            creaIfNotExist(aeMese);
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
//        for (AEMese aeMese : AEMese.values()) {
//            numRec = creaIfNotExist(aeMese) != null ? numRec+1 : numRec;
//        }
//
//        return super.fixPostReset(AETypeReset.enumeration, numRec);
//    }

}