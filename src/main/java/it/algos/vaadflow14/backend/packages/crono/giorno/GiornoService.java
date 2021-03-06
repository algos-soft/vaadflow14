package it.algos.vaadflow14.backend.packages.crono.giorno;

import it.algos.vaadflow14.backend.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.packages.crono.mese.*;
import it.algos.vaadflow14.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 25-dic-2020
 * Time: 16:55
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di un'istanza entityBean <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@AIScript(sovraScrivibile = false)
public class GiornoService extends AService {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin nel costruttore <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    MeseService meseService;

    /**
     * Costruttore @Autowired. <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * L' @Autowired (esplicito o implicito) funziona SOLO per UN costruttore <br>
     * Se ci sono DUE o più costruttori, va in errore <br>
     * Se ci sono DUE costruttori, di cui uno senza parametri, inietta quello senza parametri <br>
     * Regola la entityClazz (final) associata a questo service <br>
     */
    public GiornoService(final MeseService meseService) {
        super(Giorno.class);
        this.meseService = meseService;
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
    public Giorno creaIfNotExist(final int ordine, final String giorno, final Mese mese) {
        return (Giorno) checkAndSave(newEntity(ordine, giorno, mese));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
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
    public Giorno newEntity(final int ordine,final String giorno,final Mese mese) {
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
    public Giorno findById(final String keyID) {
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
    public Giorno findByKey(final String keyValue) {
        return (Giorno) super.findByKey(keyValue);
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
        AIResult resultCollectionPropedeutica;
        int ordine;
        String titolo;
        String titoloMese;
        List<HashMap> lista;
        Mese mese;
        int numRec = 0;

        if (result.isErrato()) {
            return result;
        }

        resultCollectionPropedeutica = checkMese();
        if (resultCollectionPropedeutica.isValido()) {
            logger.log(AETypeLog.checkData, resultCollectionPropedeutica.getMessage());
        }
        else {
            return resultCollectionPropedeutica;
        }

        //costruisce i 366 records
        lista = date.getAllGiorni();
        for (HashMap mappaGiorno : lista) {
            titolo = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
            titoloMese = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO);
            mese = (Mese) mongo.findById(Mese.class, titoloMese);
            ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);

            numRec = creaIfNotExist(ordine, titolo, mese) != null ? numRec + 1 : numRec;
        }

        return super.fixPostReset(AETypeReset.hardCoded, numRec);
    }


    private AIResult checkMese() {
        String collection = "mese";

        if (meseService == null) {
            meseService = appContext.getBean(MeseService.class);
        }

        if (mongo.isValid(collection)) {
            return AResult.valido("La collezione " + collection + " esiste già e non è stata modificata");
        }
        else {
            if (meseService == null) {
                return AResult.errato("Manca la classe MeseService");
            }
            else {
                return meseService.resetEmptyOnly();
            }
        }
    }

}