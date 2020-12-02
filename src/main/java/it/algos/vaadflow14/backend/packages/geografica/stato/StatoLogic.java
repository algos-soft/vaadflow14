package it.algos.vaadflow14.backend.packages.geografica.stato;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.geografica.continente.AEContinente;
import it.algos.vaadflow14.backend.packages.geografica.continente.Continente;
import it.algos.vaadflow14.backend.packages.geografica.continente.ContinenteLogic;
import it.algos.vaadflow14.backend.packages.geografica.regione.Regione;
import it.algos.vaadflow14.backend.packages.geografica.regione.RegioneLogic;
import it.algos.vaadflow14.backend.service.AResourceService;
import it.algos.vaadflow14.backend.wrapper.AResult;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.algos.vaadflow14.backend.application.FlowCost.TRE_PUNTI;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 06:54
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Stato ; <br>
 * 2) StaticContextAccessor.getBean(Stato.class) (senza parametri) <br>
 * 3) appContext.getBean(Stato.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatoLogic extends ALogic {

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
    public AResourceService resourceService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ContinenteLogic continenteLogic;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public StatoLogic() {
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
    public StatoLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Stato.class;
    }


    /**
     * Preferenze standard <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        boolean debug = AEPreferenza.usaDebug.is();
        super.fixPreferenze();

        super.operationForm = debug ? AEOperation.edit : AEOperation.showOnly;
        super.usaBottoneDeleteAll = debug;
        super.usaBottoneResetList = debug;
        super.usaBottoneNew = debug;
        super.usaBottonePaginaWiki = true;
        super.searchType = AESearch.editField;
        super.usaBottoneResetForm = debug;
        super.wikiPageTitle = "ISO_3166-1";
        super.usaBottoniSpostamentoForm = true;
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

        blue.add("Stati del mondo. Codifica secondo ISO 3166-1");
        blue.add("Recuperati dalla pagina wiki: " + wikiPageTitle);
        blue.add("Codici: numerico, alfa-due, alfa-tre e ISO locale");
        blue.add("Ordinamento alfabetico: prima Italia, UE e poi gli altri");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente il searchField ");
        }

        return new AlertWrap(null, blue, red, false);
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox("continente", "europa");
    }


    /**
     * Costruisce una lista ordinata di nomi delle properties del Form. <br>
     * La lista viene usata per la costruzione automatica dei campi e l' inserimento nel binder <br>
     * Nell' ordine: <br>
     * 1) Cerca nell' annotation @AIForm della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica di xxxLogic <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Se serve, modifica l' ordine della lista oppure esclude una property che non deve andare nel binder <br>
     *
     * @return lista di nomi di properties
     */
    @Override
    public List<String> getFormPropertyNamesList() {
        String propertyStato = "stato";
        String tagRegioni = "regioni";
        boolean esistonoRegioni = false;
        List<String> lista = super.getFormPropertyNamesList();

        if (AEPreferenza.usaDebug.is()) {
            return lista;
        }

        esistonoRegioni = mongo.esistono(Regione.class, propertyStato, entityBean);
        if (!esistonoRegioni && lista.contains(tagRegioni)) {
            lista.remove(tagRegioni);
        }

        return lista;
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
    public Stato creaIfNotExist(int ordine, String stato, boolean ue, String numerico, String alfatre, String alfadue, String locale, String bandiera, Continente continente) {
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
    public Stato newEntity(int ordine, String stato, boolean ue, String numerico, String alfatre, String alfadue, String locale, String bandiera, Continente continente) {
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
    public Stato findById(String keyID) {
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
    public Stato findByKey(String keyValue) {
        return (Stato) super.findByKey(keyValue);
    }


    /**
     * Azione proveniente dal click sul bottone Prima <br>
     * Recupera la lista FILTRATA e ORDINATA delle properties, ricevuta dalla Grid <br>@todo da realizzare
     * Si sposta alla precedente <br>
     * Carica il form relativo <br>
     */
    protected void prima(AEntity currentEntityBean) {
        AEntity previousEntityBean = mongo.findPrevious(entityClazz, currentEntityBean.id);
        executeRoute(previousEntityBean);
    }


    public void resetForm(AEntity entityBean) {
        AIResult result;
        RegioneLogic regioneLogic = appContext.getBean(RegioneLogic.class);
        result = regioneLogic.creaRegioniDiUnoStato((Stato) entityBean);
        logger.log(AETypeLog.reset, result.getMessage());
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
        Stato stato;
        String nome;
        int pos = AEStatoEuropeo.values().length;
        int posEuropeo;
        int posCorrente;
        boolean ue;
        String bandieraTxt = VUOTA;
        Map<String, Continente> mappa;
        Continente continente = null;
        Continente continenteDefault = continenteLogic.findById(AEContinente.antartide.getNome());
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
        ContinenteLogic continenteLogic;

        if (mongo.isValid(collection)) {
            return AResult.valido("La collezione " + collection + " esiste già e non è stata modificata");
        }
        else {
            continenteLogic = appContext.getBean(ContinenteLogic.class);
            if (continenteLogic == null) {
                return AResult.errato("Manca la classe ContinenteLogic");
            }
            else {
                return continenteLogic.resetEmptyOnly();
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
            continente = continenteLogic.findById(keyTag);
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


    public ComboBox addBandiere(ComboBox combo) {
        combo.setRenderer(new ComponentRenderer<>(entityStato -> {
            Div text = new Div();
            String sigla = ((Stato) entityStato).getAlfadue().toLowerCase();
            text.setText(((Stato) entityStato).getStato());

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