package it.algos.vaadflow14.backend.packages.geografica.regione;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.geografica.stato.AEStato;
import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.backend.packages.geografica.stato.StatoLogic;
import it.algos.vaadflow14.backend.service.AResourceService;
import it.algos.vaadflow14.backend.service.AWikiService;
import it.algos.vaadflow14.backend.wrapper.AResult;
import it.algos.vaadflow14.backend.wrapper.WrapDueStringhe;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 10:38
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Regione ; <br>
 * 2) StaticContextAccessor.getBean(Regione.class) (senza parametri) <br>
 * 3) appContext.getBean(Regione.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegioneLogic extends ALogic {

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
    public StatoLogic statoLogic;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AWikiService wiki;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AResourceService resource;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public RegioneLogic() {
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
    public RegioneLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Regione.class;
    }


    /**
     * Preferenze standard <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.operationForm = AEPreferenza.usaDebug.is() ? AEOperation.edit : AEOperation.showOnly;
        super.usaBottoneDeleteAll = AEPreferenza.usaDebug.is();
        super.usaBottoneResetList = AEPreferenza.usaDebug.is();
        super.usaBottoneNew = AEPreferenza.usaDebug.is();
        super.usaBottonePaginaWiki = true;
        super.searchType = AESearch.editField;
        super.wikiPageTitle = "ISO_3166-2";
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
        List<String> blue = new ArrayList<>();
        List<String> red = new ArrayList<>();

        blue.add("Suddivisioni geografica di secondo livello. Codifica secondo ISO 3166-2");
        blue.add("Recuperati dalla pagina wiki: " + wikiPageTitle);
        blue.add("Codice ISO, sigla abituale e 'status' normativo");
        blue.add("Ordinamento alfabetico: prima Italia poi altri stati europei");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente il searchField ed i comboBox 'Stato' e 'Status' ");
        }
        return new AlertWrap(null, blue, red, false);
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {

        if (AEPreferenza.usaBandiereStati.is()) {
            mappaComboBox.put("stato", statoLogic.creaComboStati());//@todo con bandierine
        }
        else {
            super.creaComboBox("stato", AEStato.italia.getStato());//@todo senza bandierine
        }

        super.creaComboBox("status", 14);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param divisione (obbligatorio, unico)
     * @param stato     (obbligatorio)
     * @param iso       di riferimento (obbligatorio, unico)
     * @param sigla     (consuetudinaria, obbligatoria)
     * @param status    (obbligatorio)
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public Regione creaIfNotExist(String divisione, Stato stato, String iso, String sigla, AEStatus status) {
        return (Regione) checkAndSave(newEntity(divisione, stato, iso, sigla, status));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity() {
        return newEntity(VUOTA, (Stato) null, VUOTA, VUOTA, (AEStatus) null);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param divisione (obbligatorio, unico)
     * @param stato     (obbligatorio)
     * @param iso       di riferimento (obbligatorio, unico)
     * @param sigla     (consuetudinaria, obbligatoria)
     * @param status    (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity(String divisione, Stato stato, String iso, String sigla, AEStatus status) {
        Regione newEntityBean = Regione.builderRegione()

                .ordine(this.getNewOrdine())

                .divisione(text.isValid(divisione) ? divisione : null)

                .stato(stato)

                .iso(text.isValid(iso) ? iso : null)

                .sigla(text.isValid(sigla) ? sigla : null)

                .status(status)

                .build();

        return (Regione) fixKey(newEntityBean);
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
    public Regione findById(String keyID) {
        return (Regione) super.findById(keyID);
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
    public Regione findByKey(String keyValue) {
        return (Regione) super.findByKey(keyValue);
    }


    /**
     * Retrieves an entity by its key.
     *
     * @param key must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     */
    public Regione findByIsoItalian(String key) {
        return (Regione) mongo.findOneUnique(Regione.class, "iso", "IT-" + key);
    }


    /**
     * Retrieves all entities.
     *
     * @return the entity with the given id or {@literal null} if none found
     */
    public List<Regione> findAllItalian() {
        return findAllByStato("italia");
    }


    /**
     * Retrieves all entities.
     *
     * @return the entity with the given id or {@literal null} if none found
     */
    public List<Regione> findAllByStato(Stato stato) {
        return findAllByStato(stato.id);
    }


    /**
     * Retrieves all entities.
     *
     * @return the entity with the given id or {@literal null} if none found
     */
    public List<Regione> findAllByStato(String statoKeyID) {
        List<Regione> items;
        Query query = new Query();

        query.addCriteria(Criteria.where("stato.id").is(statoKeyID));
        query.with(Sort.by(Sort.Direction.ASC, "ordine"));
        items = mongo.findAll(entityClazz, query);

        return items;
    }

    /**
     * Costruisce un ComboBox delle regioni filtrato sull'Italia <br>
     * Viene invocato da ProvinciaLogic.fixMappaComboBox(): combo di selezione delle regioni nella lista delle province <br>
     * Viene invocato da AFieldService.getCombo(), tramite 'metodo.invoke' coi parametri passati da @AIField della AEntity Provincia <br>
     * Può essere invocata anche da altri <br>
     */
    public ComboBox creaComboRegioniItaliane() {
        ComboBox<Regione> combo = new ComboBox();
        String tag = TRE_PUNTI;
        String widthEM = "12em";
        Sort sort = Sort.by("ordine");
        List items;

        items = findAllItalian();
        combo.setWidth(widthEM);
        combo.setPreventInvalidInput(true);
        combo.setAllowCustomValue(false);
        combo.setPlaceholder(text.primaMaiuscola("Regione") + tag);
        combo.setClearButtonVisible(true);
        combo.setRequired(false);

        combo.setItems(items);

        return combo;
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
        int numRec = 0;

        if (result.isErrato()) {
            return result;
        }

        resultCollectionPropedeutica = checkStato();
        if (resultCollectionPropedeutica.isValido()) {
            logger.log(AETypeLog.checkData, resultCollectionPropedeutica.getMessage());
        }
        else {
            return resultCollectionPropedeutica;
        }

        return creaRegioniAllStati();
    }


    private AIResult checkStato() {
        String collection = "stato";
        StatoLogic statoLogic;

        if (mongo.isValid(collection)) {
            return AResult.valido("La collezione " + collection + " esiste già e non è stata modificata");
        }
        else {
            statoLogic = appContext.getBean(StatoLogic.class);
            if (statoLogic == null) {
                return AResult.errato("Manca la classe StatoLogic");
            }
            else {
                return statoLogic.resetEmptyOnly();
            }
        }
    }


    /**
     * Recupera le suddivisioni di secondo livello per tutti gli stati <br>
     */
    public AIResult creaRegioniAllStati() {
        AIResult result = AResult.errato();
        int numRec = 0;

        List<Stato> listaStati = statoLogic.findAllStato();
        List<WrapDueStringhe> listaWrap = null;

        if (array.isValid(listaStati)) {
            for (Stato stato : listaStati) {
                result = creaRegioniDiUnoStato(stato);
                if (result.isErrato()) {
                    logger.log(AETypeLog.checkData, "Non sono riuscito a creare le regioni di " + stato.stato);
                }
                else {
                    numRec = numRec + result.getValore();
                }
            }
            return AResult.valido("Sono state create " + numRec + " regioni di " + listaStati.size() + " stati " + AETypeReset.wikipedia.get());
        }

        return AResult.errato("Manca la collezione stati");
    }


    /**
     * Recupera le suddivisioni di secondo livello per il singolo stato <br>
     * Le legge (se riesce) dalla pagina wiki 'ISO_3166-2:xx' col codice iso-alfa2 di ogni stato <br>
     * Cancella eventualmente le regioni esistenti per ricrearle correttamente <br>
     */
    public AIResult creaRegioniDiUnoStato(Stato stato) {
        AIResult result = AResult.errato();
        Regione regione;
        int numRec = 0;
        String tagWiki = "ISO 3166-2:";
        String alfaDue;
        String wikiPagina = VUOTA;
        String nome;
        String sigla;
        String iso;
        String status = VUOTA;
        AEStatus aeStatus = AEStatus.regione;
        List<WrapDueStringhe> listaWrap = null;
        WrapDueStringhe wrapTitoli;
        List<Regione> regioniDaResettare = findAllByStato(stato);
        if (regioniDaResettare != null && regioniDaResettare.size() > 0) {
            mongo.delete(regioniDaResettare, Regione.class);
        }

        alfaDue = stato.alfadue;
        if (text.isValid(alfaDue)) {
            wikiPagina = tagWiki + alfaDue;

            try {
                listaWrap = wiki.getRegioni(wikiPagina);
            } catch (Exception unErrore) {
                return AResult.errato("Non sono riuscito a leggere la pagina di wikipedia");
            }
        }

        if (listaWrap != null) {
            wrapTitoli = listaWrap.get(0);
            status = wrapTitoli != null ? wrapTitoli.getSeconda() : VUOTA;
            aeStatus = getStatus(status);
            for (WrapDueStringhe wrap : listaWrap.subList(1, listaWrap.size())) {
                nome = fixNome(wrap.getSeconda());
                iso = wrap.getPrima();
                sigla = text.levaTestoPrimaDi(iso, TRATTINO);
                aeStatus = stato.id.equals("italia") ? fixStatusItalia(nome) : aeStatus;
                if (text.isValid(nome) && stato != null && text.isValid(iso) && text.isValid(sigla)) {
                    regione = creaIfNotExist(nome, stato, iso, sigla, aeStatus);
                    if (regione != null) {
                        numRec++;
                    }
                }
                else {
                    logger.warn("Mancano dati essenziali", this.getClass(), "creaRegioniDiUnoStato: " + stato.stato);
                }
            }
            result = AResult.valido("Regioni di " + stato.stato, numRec);
        }
        else {
            result = AResult.errato("Non sono riuscito a trovare la table nella pagina di wikipedia " + wikiPagina);
        }

        return result;
    }


    public AEStatus getStatus(String status) {
        return AEStatus.get(status);
    }


    public String fixNome(String nomeIn) {
        String nomeOut = nomeIn;

        if (nomeOut.contains(PIPE)) {
            nomeOut = text.levaTestoPrimaDi(nomeOut, PIPE);
        }

        return nomeOut;
    }


    public AEStatus fixStatusItalia(String nome) {
        Map<String, List<String>> mappa = resource.leggeMappaConfig("regioni");
        String status = mappa.get(nome).get(4);
        return AEStatus.get(status);
    }


    /**
     * Regioni italiane <br>
     */
    @Deprecated
    public void italia() {
        File regioniCSV = new File("config" + File.separator + "regioni");
        String path = regioniCSV.getAbsolutePath();
        List<LinkedHashMap<String, String>> mappaCSV;
        String nome = VUOTA;
        Stato stato = AEStato.italia.getStato();
        ;
        String iso = VUOTA;
        String sigla = VUOTA;
        String statusTxt = VUOTA;
        AEStatus status = null;

        mappaCSV = fileService.leggeMappaCSV(path);
        for (LinkedHashMap<String, String> riga : mappaCSV) {
            nome = riga.get("nome");
            iso = riga.get("iso");
            sigla = riga.get("sigla");
            statusTxt = riga.get("status");
            status = AEStatus.valueOf(statusTxt);
            creaIfNotExist(nome, stato, iso, sigla, status);
        }
    }


    /**
     * Costruzione utilizzando un template <br>
     */
    @Deprecated
    private void templateList(AEStato aEuropa, AEStatus status, int posTabella, int rigaIniziale, int numColonna) {
        List<WrapDueStringhe> listaWrap;
        String nome;
        String sigla;
        String iso;

        listaWrap = wiki.getTemplateList(aEuropa.getPaginaWiki(), posTabella, rigaIniziale, numColonna);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = aEuropa.getIsoTag() + sigla;
                creaIfNotExist(nome, aEuropa.getStato(), iso, sigla, status);
            }
        }
    }


    /**
     * Costruzione utilizzando due colonne <br>
     */
    @Deprecated
    private void dueColonne(AEStato aEuropa, AEStatus status, int posTabella, int rigaIniziale, int numColonnaUno, int numColonnaDue) {
        List<WrapDueStringhe> listaWrap;
        String nome;
        String sigla;
        String iso;

        listaWrap = wiki.getDueColonne(aEuropa.getPaginaWiki(), posTabella, rigaIniziale, numColonnaUno, numColonnaDue);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestoPrimaDi(iso, TRATTINO);
                creaIfNotExist(nome, aEuropa.getStato(), iso, sigla, status);
            }
        }
    }

}