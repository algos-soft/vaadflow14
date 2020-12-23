package it.algos.vaadflow14.backend.packages.geografica.continente;

import it.algos.vaadflow14.backend.enumeration.AETypeReset;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.logic.AService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 21-dic-2020
 * Time: 20:37
 * <p>
 * Classe SINGLETON di servizio per la entity <br>
 * Estende la classe astratta AService <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ContinenteService extends AService {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Costruttore senza parametri <br>
     */
    public ContinenteService() {
        super.entityClazz = Continente.class;
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

                .ordine(ordine)

                .nome(text.isValid(nome) ? nome : null)

                .abitato(abitato)

                .build();

        return (Continente) fixKey(newEntityBean);
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

        for (AEContinente aeContinente : AEContinente.values()) {
            numRec = creaIfNotExist(aeContinente) != null ? numRec + 1 : numRec;
        }

        return super.fixPostReset(AETypeReset.hardCoded, numRec);
    }

}