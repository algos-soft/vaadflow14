package it.algos.simple.backend.packages;

import it.algos.vaadflow14.backend.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.packages.crono.mese.*;
import it.algos.vaadflow14.backend.packages.crono.secolo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 25-feb-2021
 * Time: 15:00
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@AIScript(sovraScrivibile = true)
public class GammaService extends AService {

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
    public MeseService meseService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public SecoloService secoloService;


    /**
     * Costruttore senza parametri <br>
     * Regola la entityClazz (final) associata a questo service <br>
     */
    public GammaService() {
        super(Gamma.class);
    }


    /**
     * Crea e registra una entityBean solo se non esisteva <br>
     * Deve esistere la keyPropertyName della collezione, in modo da poter creare una nuova entityBean <br>
     * solo col valore di un parametro da usare anche come keyID <br>
     * Controlla che non esista già una entityBean con lo stesso keyID <br>
     * Deve esistere il metodo newEntity(keyPropertyValue) con un solo parametro <br>
     *
     * @param keyPropertyValue obbligatorio
     *
     * @return la nuova entityBean appena creata e salvata
     */
    public Gamma creaIfNotExist(final String code, final Mese mese, final Secolo secolo) {
        return (Gamma) checkAndSave(newEntity(code, mese, secolo));
    }


    /**
     * Creazione in memoria di una nuova entityBean che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Senza properties per compatibilità con la superclasse <br>
     *
     * @return la nuova entityBean appena creata (non salvata)
     */
    @Override
    public Gamma newEntity() {
        return newEntity(VUOTA, null, null);
    }


    /**
     * Creazione in memoria di una nuova entityBean che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param ordine di presentazione nel popup/combobox (obbligatorio, unico)
     * @param code   (obbligatorio, unico)
     *
     * @return la nuova entityBean appena creata (non salvata)
     */
    public Gamma newEntity(final String code, final Mese mese, final Secolo secolo) {//@TODO: Le properties riportate sono INDICATIVE e debbono essere sostituite
        Gamma newEntityBean = Gamma.builderGamma()
                .code(text.isValid(code) ? code : null)
                .mese(mese != null ? mese : null)
                .secolo(mese != null ? secolo : null)
                .build();

        return (Gamma) fixKey(newEntityBean);
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
    public Gamma findById(final String keyID) throws AMongoException {
        return (Gamma) super.findById(keyID);
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
    public Gamma findByKey(final String keyValue) throws AMongoException {
        return (Gamma) super.findByKey(keyValue);
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
    //    @Override
    public AIResult resetEmptyOnly() {
        AIResult result = null;
        //        AIResult result = super.resetEmptyOnly();
        int numRec = 6;
        Mese mese1 = null;
        Mese mese2 = null;
        Mese mese3 = null;
        Mese mese4 = null;
        Secolo secolo1 = null;
        Secolo secolo2 = null;
        Secolo secolo3 = null;
        Secolo secolo4 = null;

        try {
            mese1 = (Mese) meseService.findByKey("aprile");
            mese2 = (Mese) meseService.findByKey("ottobre");
            mese3 = (Mese) meseService.findByKey("gennaio");
            mese4 = (Mese) meseService.findByKey("marzo");
            secolo1 = (Secolo) secoloService.findByKey("XVII secolo a.C.");
            secolo2 = (Secolo) secoloService.findByKey("X secolo");
            secolo3 = (Secolo) secoloService.findByKey("XIV secolo");
            secolo4 = (Secolo) secoloService.findByKey("XX secolo");
        } catch (AMongoException unErrore) {
            logger.warn(unErrore, this.getClass(), "resetEmptyOnly");
        }

        if (result.isErrato()) {
            return result;
        }

        creaIfNotExist("alfa", mese1, secolo3);
        creaIfNotExist("beta", mese2, secolo4);
        creaIfNotExist("delta", mese3, secolo1);
        creaIfNotExist("gamma", mese4, secolo2);
        creaIfNotExist("epsilon", mese3, secolo3);
        creaIfNotExist("omega", mese2, secolo4);

        return result;
    }

}