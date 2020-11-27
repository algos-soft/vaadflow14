package it.algos.vaadflow14.backend.packages.crono.secolo;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.packages.crono.CronoLogic;
import it.algos.vaadflow14.ui.enumeration.AEVista;
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
 * Date: dom, 02-ago-2020
 * Time: 07:07
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Secolo ; <br>
 * 2) StaticContextAccessor.getBean(Secolo.class) (senza parametri) <br>
 * 3) appContext.getBean(Secolo.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SecoloLogic extends CronoLogic {


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
    public SecoloLogic() {
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
    public SecoloLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Secolo.class;
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
    @Override
    protected AlertWrap getAlertWrap(AEVista typeVista) {
        List<String> blu = new ArrayList<>();
        List<String> red = new ArrayList<>();

        blu.add("Secoli ante e post Cristo. Venti secoli AnteCristo e ventun secoli DopoCristo");
        blu.add("Sono indicati gli anni iniziali e finali di ogni secolo. L' anno 0 NON esiste nei calendari");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente bottone 'Esporta'");
        }

        return new AlertWrap(null, blu, red, false);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param aeSecolo: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata e salvata
     */
    public Secolo creaIfNotExist(AESecolo aeSecolo) {
        return creaIfNotExist(aeSecolo.getNome(), aeSecolo.isAnteCristo(), aeSecolo.getInizio(), aeSecolo.getFine());
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param secolo     (obbligatorio, unico)
     * @param anteCristo flag per i secoli prima di cristo (obbligatorio)
     * @param inizio     (obbligatorio, unico)
     * @param fine       (obbligatorio, unico)
     *
     * @return la nuova entity appena creata e salvata
     */
    public Secolo creaIfNotExist(String secolo, boolean anteCristo, int inizio, int fine) {
        return (Secolo) checkAndSave(newEntity(secolo, anteCristo, inizio, fine));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Secolo newEntity() {
        return newEntity(VUOTA, false, 0, 0);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param aeSecolo: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Secolo newEntity(AESecolo aeSecolo) {
        return newEntity(aeSecolo.getNome(), aeSecolo.isAnteCristo(), aeSecolo.getInizio(), aeSecolo.getFine());
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param secolo     (obbligatorio, unico)
     * @param anteCristo flag per i secoli prima di cristo (obbligatorio)
     * @param inizio     (obbligatorio, unico)
     * @param fine       (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Secolo newEntity(String secolo, boolean anteCristo, int inizio, int fine) {
        Secolo newEntityBean = Secolo.builderSecolo()

                .ordine(getNewOrdine())

                .secolo(text.isValid(secolo) ? secolo : null)

                .anteCristo(anteCristo)

                .inizio(inizio)

                .fine(fine)

                .build();

        return (Secolo) fixKey(newEntityBean);
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
    public Secolo findById(String keyID) {
        return (Secolo) super.findById(keyID);
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
    public Secolo findByKey(String keyValue) {
        return (Secolo) super.findByKey(keyValue);
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
//    public boolean reset() {
//        super.deleteAll();
//
//        for (AESecolo eaSecolo : AESecolo.values()) {
//            creaIfNotExist(eaSecolo);
//        }
//
//        return mongo.isValid(entityClazz);
//    }

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
     * @return vuota se è stata creata, altrimenti un messaggio di errore
     */
    @Override
    public String resetEmptyOnly() {
        String message = super.resetEmptyOnly();
        int numRec = 0;

        if (!message.equals(VUOTA)) {
            return message;
        }

        for (AESecolo eaSecolo : AESecolo.values()) {
            numRec = creaIfNotExist(eaSecolo) != null ? numRec + 1 : numRec;
        }

        return super.fixPostReset(numRec);
    }

}