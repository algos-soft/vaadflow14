package it.algos.vaadflow14.backend.packages.company;

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
 * Time: 17:48
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CompanyService extends AService {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Costruttore senza parametri <br>
     * Regola la entityClazz associata a questo service <br>
     */
    public CompanyService() {
        super(Company.class);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param code        di riferimento
     * @param descrizione completa
     *
     * @return la nuova entity appena creata e salvata
     */
    public Company creaIfNotExist(String code, String descrizione) {
        return creaIfNotExist(code, descrizione, VUOTA, VUOTA);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param code        di riferimento
     * @param descrizione completa
     *
     * @return la nuova entity appena creata e salvata
     */
    public Company creaIfNotExist(String code, String descrizione, String telefono, String email) {
        return (Company) checkAndSave(newEntity(code, descrizione, telefono, email));
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Company newEntity() {
        return newEntity(VUOTA, VUOTA, VUOTA, VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param code        di riferimento
     * @param descrizione completa
     * @param telefono    fisso o cellulare
     * @param email       di posta elettronica
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Company newEntity(String code, String descrizione, String telefono, String email) {
        Company newEntityBean = Company.builderCompany()

                .code(text.isValid(code) ? code : null)

                .descrizione(text.isValid(descrizione) ? descrizione : null)

                .telefono(text.isValid(telefono) ? telefono : null)

                .email(text.isValid(email) ? email : null)

                .build();

        return (Company) fixKey(newEntityBean);
    }

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
//    public Company findById(String keyID) {
//        return (Company) super.findById(keyID);
//    }
//
//
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
//    public Company findByKey(String keyValue) {
//        return (Company) super.findByKey(keyValue);
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
     * @return wrapper col risultato ed eventuale messaggio di errore
     */
    @Override
    public AIResult resetEmptyOnly() {
        AIResult result = super.resetEmptyOnly();
        int numRec = 0;

        if (result.isErrato()) {
            return result;
        }

        numRec = creaIfNotExist("Algos", "Company Algos di prova", VUOTA, "info@algos.it") != null ? numRec + 1 : numRec;
        numRec = creaIfNotExist("Demo", "Company demo", "345 994487", "demo@algos.it") != null ? numRec + 1 : numRec;
        numRec = creaIfNotExist("Test", "Company di test", "", "presidentePonteTaro@crocerossa.it") != null ? numRec + 1 : numRec;

        return super.fixPostReset(AETypeReset.hardCoded, numRec);
    }

}