package it.algos.vaadflow14.backend.packages.geografica.stato;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.renderer.*;
import it.algos.vaadflow14.backend.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.packages.geografica.continente.*;
import it.algos.vaadflow14.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 25-dic-2020
 * Time: 18:40
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di un'istanza entityBean <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@AIScript(sovraScrivibile = false)
public class StatoService extends AService {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin nel costruttore <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    ContinenteService continenteService;


    /**
     * Costruttore @Autowired. <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * L' @Autowired (esplicito o implicito) funziona SOLO per UN costruttore <br>
     * Se ci sono DUE o più costruttori, va in errore <br>
     * Se ci sono DUE costruttori, di cui uno senza parametri, inietta quello senza parametri <br>
     * Regola la entityClazz (final) associata a questo service <br>
     */
    public StatoService(final ContinenteService continenteService) {
        super(Stato.class);
        this.continenteService = continenteService;
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param ordine   di presentazione nel popup/combobox (obbligatorio, unico)
     * @param stato    (obbligatorio, unico)
     * @param ue       appartenenza all' unione europea (obbligatorio)
     * @param numerico di riferimento (obbligatorio)
     * @param alfatre  (obbligatorio, unico)
     * @param alfadue  (obbligatorio, unico)
     * @param locale   (obbligatorio, unico)
     * @param bandiera (facoltativa)
     *
     * @return la nuova entity appena creata e salvata
     */
    public Stato creaIfNotExist(final int ordine, final String stato, final boolean ue, final String numerico, final String alfatre, final String alfadue, final String locale, final String bandiera, final Continente continente) {
        return (Stato) checkAndSave(newEntity(ordine, stato, ue, numerico, alfatre, alfadue, locale, bandiera, continente));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Stato newEntity() {
        return newEntity(0, VUOTA, false, VUOTA, VUOTA, VUOTA, VUOTA, VUOTA, (Continente) null);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine   di presentazione nel popup/combobox (obbligatorio, unico)
     * @param stato    (obbligatorio, unico)
     * @param ue       appartenenza all' unione europea (obbligatorio)
     * @param numerico di riferimento (obbligatorio)
     * @param alfatre  (obbligatorio, unico)
     * @param alfadue  (obbligatorio, unico)
     * @param locale   (obbligatorio, unico)
     * @param bandiera (facoltativa)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Stato newEntity(final int ordine, final String stato, final boolean ue, final String numerico, final String alfatre, final String alfadue, final String locale, final String bandiera, final Continente continente) {
        Stato newEntityBean = Stato.builderStato()

                .ordine(ordine > 0 ? ordine : getNewOrdine())

                .stato(text.isValid(stato) ? stato : null)

                .ue(ue)

                .numerico(text.isValid(numerico) ? numerico : null)

                .alfatre(text.isValid(alfatre) ? alfatre : null)

                .alfadue(text.isValid(alfadue) ? alfadue : null)

                .locale(text.isValid(locale) ? locale : null)

                .bandiera(text.isValid(bandiera) ? bandiera : null)

                .continente(continente)

                .build();

        return (Stato) fixKey(newEntityBean);
    }


    /**
     * Cerca tutte le entities di questa collection. <br>
     *
     * @return lista di entityBeans
     *
     * @see(https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find/)
     */
    public List<Stato> findAllStato() {
        return mongo.findAll(Stato.class, Sort.by(Sort.Direction.ASC, "ordine"));
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
    public Stato findById(final String keyID) {
        return (Stato) super.findById(keyID);
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
    public Stato findByKey(final String keyValue) {
        return (Stato) super.findByKey(keyValue);
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
        AIResult resultCollectionPropedeutica;
        Stato stato;
        String nome;
        int pos = AEStatoEuropeo.values().length;
        int posEuropeo;
        int posCorrente;
        boolean ue;
        String bandieraTxt = VUOTA;
        Map<String, Continente> mappa;
        Continente continente = null;
        Continente continenteDefault = continenteService.findById(AEContinente.antartide.getNome());
        String alfaTre = VUOTA;

        if (result.isErrato()) {
            return result;
        }

        resultCollectionPropedeutica = checkContinente();
        if (resultCollectionPropedeutica.isValido()) {
            logger.log(AETypeLog.checkData, resultCollectionPropedeutica.getMessage());
        }
        else {
            return resultCollectionPropedeutica;
        }

        mappa = creaMappa();
        List<List<String>> listaStati = wiki.getStati();
        if (array.isAllValid(listaStati)) {
            for (List<String> riga : listaStati) {
                continente = null;
                nome = riga.get(0);
                posEuropeo = AEStatoEuropeo.getPosizione(nome);
                if (posEuropeo > 0) {
                    posCorrente = posEuropeo;
                    ue = true;
                }
                else {
                    pos++;
                    posCorrente = pos;
                    ue = false;
                }
                if (text.isValid(riga.get(2))) {
                    alfaTre = riga.get(2);
                }
                if (text.isValid(riga.get(3))) {
                    bandieraTxt = resourceService.getSrcBandieraPng(riga.get(3));
                }
                if (text.isValid(alfaTre)) {
                    if (mappa.get(alfaTre) != null) {
                        continente = mappa.get(alfaTre);
                    }
                }
                continente = continente != null ? continente : continenteDefault;

                stato = creaIfNotExist(posCorrente, nome, ue, riga.get(1), riga.get(2), riga.get(3), riga.get(4), bandieraTxt, continente);

                if (stato != null) {
                    numRec++;
                }

            }
        }

        return super.fixPostReset(AETypeReset.wikipedia, numRec);
    }


    private AIResult checkContinente() {
        String collection = "continente";

        if (continenteService == null) {
            continenteService = appContext.getBean(ContinenteService.class);
        }

        if (mongo.isValid(collection)) {
            return AResult.valido("La collezione " + collection + " esiste già e non è stata modificata");
        }
        else {
            if (continenteService == null) {
                return AResult.errato("Manca la classe ContinenteService");
            }
            else {
                return continenteService.resetEmptyOnly();
            }

        }
    }

    private Map<String, Continente> creaMappa() {
        Map<String, Continente> mappa = new HashMap<>();
        List<String> lista;
        Continente continente;
        String keyTag;

        for (AEContinente aeContinente : AEContinente.values()) {
            keyTag = aeContinente.name();
            continente = continenteService.findById(keyTag);
            lista = resourceService.leggeListaConfig(keyTag, false);
            if (array.isAllValid(lista)) {
                for (String riga : lista) {
                    mappa.put(riga, continente);
                }
            }
        }

        return mappa;
    }

    /**
     * Costruisce un ComboBox degli stati uguale per tutti <br>
     * Viene invocato da RegioneLogic.fixMappaComboBox(): combo di selezione degli stati nella lista delle regioni <br>
     * Viene invocato da AFieldService.getCombo(), tramite 'metodo.invoke' coi parametri passati da @AIField della AEntity Regione <br>
     * Può essere invocata anche da altri <br>
     * Aggiunge o meno le bandierine, secondo il flag AEPreferenza.usaBandiereStati <br>
     */
    public ComboBox creaComboStati() {
        ComboBox<Stato> combo = new ComboBox();
        String tag = TRE_PUNTI;
        String widthEM = "12em";
        Sort sort = Sort.by("ordine");
        List items;

        items = mongo.findAll(Stato.class, sort);
        combo.setWidth(widthEM);
        combo.setPreventInvalidInput(true);
        combo.setAllowCustomValue(false);
        combo.setPlaceholder(text.primaMaiuscola("Stati") + tag);
        combo.setClearButtonVisible(true);
        combo.setRequired(false);

        combo.setItems(items);
        combo.setValue(AEStato.italia.getStato());

        if (AEPreferenza.usaBandiereStati.is()) {
            combo = addBandiere(combo);
        }

        return combo;
    }


    public ComboBox addBandiere(final ComboBox combo) {
        combo.setRenderer(new ComponentRenderer<>(entityStato -> {
            Div text = new Div();
            String sigla = ((Stato) entityStato).alfadue.toLowerCase();
            text.setText(((Stato) entityStato).stato);

            Image image = imageService.getBandiera(sigla);
            image.setWidth("21px");
            image.setHeight("21px");

            FlexLayout wrapper = new FlexLayout();
            text.getStyle().set("margin-left", "0.5em");
            wrapper.add(image, text);
            return wrapper;
        }));
        return combo;
    }

}