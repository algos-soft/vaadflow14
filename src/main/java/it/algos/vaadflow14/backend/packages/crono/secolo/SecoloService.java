package it.algos.vaadflow14.backend.packages.crono.secolo;

import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.enumeration.AETypeReset;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.logic.AService;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 23-dic-2020
 * Time: 06:58
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SecoloService extends AService {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Costruttore senza parametri <br>
     * Regola la entityClazz associata a questo service <br>
     */
    public SecoloService() {
        super(Secolo.class);
    }


    /**
     * Informazioni (eventuali) specifiche di ogni modulo, mostrate nella List <br>
     * Costruisce un wrapper di liste di informazioni per costruire l' istanza di AHeaderWrap <br>
     * DEVE essere sovrascritto <br>
     * Esempio:     return new AlertWrap(new ArrayList(Arrays.asList("uno", "due", "tre")));
     *
     * @return wrapper per passaggio dati
     */
    @Override
    protected AlertWrap getAlertWrapList() {
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

                .ordine(4)

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
    @Override
    public AIResult resetEmptyOnly() {
        AIResult result = super.resetEmptyOnly();
        int numRec = 0;

        if (result.isErrato()) {
            return result;
        }

        for (AESecolo eaSecolo : AESecolo.values()) {
            numRec = creaIfNotExist(eaSecolo) != null ? numRec + 1 : numRec;
        }

        return super.fixPostReset(AETypeReset.enumeration, numRec);
    }

}