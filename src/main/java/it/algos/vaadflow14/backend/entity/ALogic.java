package it.algos.vaadflow14.backend.entity;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.QueryParameters;
import de.codecamp.vaadin.components.messagedialog.MessageDialog;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AESearch;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.backend.wrapper.AFiltro;
import it.algos.vaadflow14.backend.wrapper.WrapSearch;
import it.algos.vaadflow14.ui.button.ABottomLayout;
import it.algos.vaadflow14.ui.button.AEAction;
import it.algos.vaadflow14.ui.button.ATopLayout;
import it.algos.vaadflow14.ui.button.WrapButtons;
import it.algos.vaadflow14.ui.enumerastion.AEButton;
import it.algos.vaadflow14.ui.enumerastion.AEVista;
import it.algos.vaadflow14.ui.form.AForm;
import it.algos.vaadflow14.ui.form.WrapForm;
import it.algos.vaadflow14.ui.header.AHeader;
import it.algos.vaadflow14.ui.header.AHeaderList;
import it.algos.vaadflow14.ui.header.AHeaderWrap;
import it.algos.vaadflow14.ui.header.AlertWrap;
import it.algos.vaadflow14.ui.list.AGrid;
import it.algos.vaadflow14.ui.service.ARouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static it.algos.vaadflow14.backend.application.FlowCost.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: gio, 30-apr-2020
 * Time: 07:46
 * Classe astratta di gestione della 'business logic' di una Entity e di un package <br>
 * Collegamento tra il 'backend' e le views <br>
 * Le sottoclassi concrete sono SCOPE_PROTOTYPE e mantengono lo stato del package <br>
 * Viene creata un istanza per ogni view. <br>
 * Quindi la ViewList (con la Grid) e la ViewForm ( con il Form), hanno istanze diverse della sottoclasse xxxLogic <br>
 * <p>
 * Questo 'service' garantisce i metodi di collegamento per accedere al database <br>
 * Implementa le API dell'interfaccia  <br>
 * Classe astratta. L'implementazione concreta standard è GenericLogic <br>
 * Contiene i riferimenti ad altre classi per usarli nelle sottoclassi concrete <br>
 * I riferimenti sono 'public' per poterli usare con TestUnit <br>
 * <p>
 * Le sottoclassi concrete vengono create in AView.fixEntityLogic() <br>
 * col metodo logic = (AILogic) appContext.getBean(Class.forName(canonicalName)); <br>
 * sono pertanto SCOPE_PROTOTYPE e ne viene creata un'istanza diversa per ogni view <br>
 * possono quini mantenere delle property senza possibilità che si 'mischino' con altri utenti di altri browser <br>
 */
public abstract class ALogic implements AILogic {

    protected static final String BLOCCATA = "Collezione bloccata. Non si può ne creare, ne modificare, ne cancellare la singola entity.";

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ATextService text;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AArrayService array;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AReflectionService reflection;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AAnnotationService annotation;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ADateService date;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ARouteService route;


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
    public ALogService logger;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AMongoService mongo;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ABeanService beanService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AFileService fileService;


    /**
     * The Entity Class  (obbligatoria sempre; in ViewForm può essere ricavata dalla entityBean)
     */
    protected Class<? extends AEntity> entityClazz;


    /**
     * The Entity Bean  (obbligatoria per ViewForm)
     */
    protected AEntity entityBean;


    /**
     * The Grid  (obbligatoria per ViewList)
     */
    protected AGrid grid;


    /**
     * The Form (obbligatoria nel ViewForm)
     */
    protected AForm form;


    /**
     * Tipologia di Form in uso <br>
     */
    protected AEOperation operationForm;


    /**
     * Flag di preferenza per specificare la property della entity da usare come ID <br>
     */
    protected String keyPropertyName;


    /**
     * Flag di preferenza per selezionare la ricerca testuale: <br>
     * 1) nessuna <br>
     * 2) campo editText di selezione per una property specificata in searchProperty <br>
     * 3) bottone che apre un dialogo di selezione <br>
     */
    protected AESearch searchType;


    /**
     * Flag di preferenza per specificare la property della entity su cui effettuare la ricerca <br>
     * Ha senso solo se searchType=EASearch.editField
     */
    protected String searchProperty;


    /**
     * Flag di preferenza per l'utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneDeleteAll;


    /**
     * Flag di preferenza per l'utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneReset;


    /**
     * Flag di preferenza per l'utilizzo del bottone. Di default true. <br>
     */
    protected boolean usaBottoneNew;


    /**
     * Flag di preferenza per l'utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottonePaginaWiki;


    /**
     * Flag di preferenza per specificare il titolo della pagina wiki da mostrare in lettura <br>
     */
    protected String wikiPageTitle;

    /**
     * Flag di preferenza per i messaggi di avviso in alertPlacehorder <br>
     * Si può usare la classe AHeaderWrap con i messaggi suddivisi per ruolo (user, admin, developer) <br>
     * Oppure si può usare la classe AHeaderList con i messaggi in Html (eventualmente colorati) <br>
     * Di defaul false <br>
     */
    protected boolean usaHeaderWrap;


    protected String searchFieldValue = VUOTA;

    protected List<AFiltro> filtri;

    protected Sort sortView;

    protected List<AEButton> listaBottoni;

    protected LinkedHashMap<String, ComboBox> mappaComboBox;


    public ALogic() {
    }


    public ALogic(AEOperation operationForm) {
        this.operationForm = operationForm;
    }


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        fixProperties();
        fixPreferenze();
    }


    /**
     * Costruisce le properties di questa istanza <br>
     */
    private void fixProperties() {
        mappaComboBox = new LinkedHashMap<>();
        this.fixMappaComboBox();
    }


    /**
     * Preferenze usate da questa istanza e dalle Views collegate <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        //        this.keyPropertyName = annotation.getKeyPropertyName(entityClazz);
        this.searchType = AESearch.nonUsata;
        this.searchProperty = annotation.getSearchPropertyName(entityClazz);
        this.usaBottoneDeleteAll = false;
        this.usaBottoneReset = false;
        this.usaBottoneNew = true;
        this.usaBottonePaginaWiki = false;
        this.wikiPageTitle = VUOTA;
        this.usaHeaderWrap = false;
    }


    /**
     * Costruisce un (eventuale) layout per avvisi aggiuntivi in alertPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Normalmente ad uso esclusivo del developer <br>
     * Nell' implementazione standard di default NON presenta nessun avviso <br>
     * Recupera dal service specifico gli (eventuali) avvisi <br>
     * Costruisce un' istanza dedicata (secondo il flag usaHeaderWrap) con le liste di avvisi <br>
     * <p>
     * AHeaderWrap:
     * Gli avvisi sono realizzati con label differenziate per colore in base all' utente collegato <br>
     * Se l' applicazione non usa security, il colore è unico <br<
     * Se esiste, inserisce l' istanza (grafica) in alertPlacehorder della view <br>
     * alertPlacehorder viene sempre aggiunto, per poter (eventualmente) essere utilizzato dalle sottoclassi <br>
     * <p>
     * AHeaderList:
     * Gli avvisi sono realizzati con elementi html con possibilità di color e bold <br>
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public AHeader getAlertHeaderLayout(AEVista typeVista) {
        AHeader header = null;
        AlertWrap wrap = getAlertWrap(typeVista);
        List<String> alertHtmlList = getAlertList(typeVista);

        if (usaHeaderWrap) {
            if (wrap != null) {
                header = appContext.getBean(AHeaderWrap.class, wrap);
            }
        } else {
            if (alertHtmlList != null) {
                header = appContext.getBean(AHeaderList.class, alertHtmlList);
            }
        }

        return header;
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
    protected AlertWrap getAlertWrap(AEVista typeVista) {
        return null;
    }


    /**
     * Costruisce una lista di informazioni per costruire l' istanza di AHeaderList <br>
     * Informazioni (eventuali) specifiche di ogni modulo <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Esempio:     return new ArrayList(Arrays.asList("uno", "due", "tre"));
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return wrapper per passaggio dati
     */
    protected List<String> getAlertList(AEVista typeVista) {
        return new ArrayList<String>();
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    protected void fixMappaComboBox() {
    }


    /**
     * Costruisce un layout per i bottoni di comando in topPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * 1) Recupera dal service specifico una List<AEButton> di bottoni previsti <br>
     * Se List<AEButton> è vuota, ATopLayout usa i bottoni di default (solo New) <br>
     * 2) Recupera dal service specifico la condizione e la property previste (searchType,searchProperty) <br>
     * 3) Recupera dal service specifico una List<ComboBox> di popup di selezione e filtro <br>
     * Se List<ComboBox> è vuota, ATopLayout non usa popup <br>
     * Costruisce un'istanza dedicata con i bottoni, il campo textEdit di ricerca (eventuale) ed i comboBox (eventuali) <br>
     * Inserisce l'istanza (grafica) in topPlacehorder della view <br>
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public ATopLayout getTopLayout() {
        ATopLayout topLayout = appContext.getBean(ATopLayout.class, getWrapButtonsTop());
        this.addTopListeners(topLayout);

        return topLayout;
    }


    /**
     * Costruisce un wrapper di dati <br>
     * I dati sono gestiti da questa 'logic' (nella sottoclasse eventualmente) <br>
     * I dati vengono passati alla View che li usa <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     *
     * @return wrapper di dati per la view
     */
    public WrapButtons getWrapButtonsTop() {
        List<AEButton> iniziali = this.getListaBottoniIniziali();
        WrapSearch wrapSearch = this.getWrapSearch();
        List<AEButton> centrali = this.getListaBottoniCentrali();
        List<Button> specifici = this.getListaBottoniSpecifici();
        List<AEButton> finali = this.getListaBottoniFinali();
        AEOperation operationForm = null;

        return new WrapButtons(iniziali, wrapSearch, centrali, specifici, mappaComboBox, finali, operationForm);
    }


    /**
     * Costruisce una lista di bottoni (enumeration) per il gruppo iniziale <br>
     * Di default costruisce (come da flag) i bottoni 'delete' e 'reset' <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected List<AEButton> getListaBottoniIniziali() {
        List<AEButton> listaBottoni = new ArrayList<>();

        if (usaBottoneDeleteAll) {
            listaBottoni.add(AEButton.deleteAll);
        }

        if (usaBottoneReset) {
            listaBottoni.add(AEButton.reset);
        }

        return listaBottoni;
    }


    /**
     * Costruisce un wrap per la ricerca <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected WrapSearch getWrapSearch() {
        if (searchType == AESearch.editField && text.isEmpty(searchProperty)) {
            logger.error("Tipo di ricerca prevede un campo edit ma manca il nome della property", this.getClass(), "getWrapSearch");
            return null;
        } else {
            return new WrapSearch(searchType, searchProperty);
        }
    }


    /**
     * Costruisce una lista di bottoni (enumeration) per il gruppo centrale <br>
     * Di default costruisce (come da flag) solo il bottone 'new' <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected List<AEButton> getListaBottoniCentrali() {
        List<AEButton> listaBottoni = new ArrayList<>();

        if (usaBottoneNew) {
            listaBottoni.add(AEButton.nuovo);
        }

        return listaBottoni;
    }


    /**
     * Costruisce una lista di bottoni specifici <br>
     * Di default non costruisce nulla <br>
     * Deve essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected List<Button> getListaBottoniSpecifici() {
        List<Button> listaBottoni = new ArrayList<>();

        return listaBottoni;
    }


    /**
     * Costruisce una lista di bottoni (enumeration) per il gruppo finale <br>
     * Di default non costruisce nulla <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected List<AEButton> getListaBottoniFinali() {
        List<AEButton> listaBottoni = new ArrayList<>();

        if (usaBottonePaginaWiki) {
            listaBottoni.add(AEButton.wiki);
        }

        return listaBottoni;
    }


    /**
     * Aggiunge tutti i listeners ai bottoni di 'topPlaceholder' che sono stati creati SENZA listeners <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected void addTopListeners(ATopLayout topLayout) {
        if (topLayout != null) {
            topLayout.setAllListener(this);
        }
    }


    /**
     * Costruisce un layout per la Grid in bodyPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Costruisce un'istanza dedicata <br>
     * Inserisce l'istanza (grafica) in bodyPlacehorder della view <br>
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public AGrid getBodyGridLayout() {
        grid = appContext.getBean(AGrid.class, entityClazz, this);
        refreshGrid();
        addGridListeners();
        return grid;
    }


    /**
     * Aggiunge tutti i listeners alla Grid di 'bodyPlaceholder' che è stata creata SENZA listeners <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected void addGridListeners() {
        if (grid != null && grid.getGrid() != null) {
            grid.setAllListener(this);
        }
    }


    /**
     * Costruisce un layout per il Form in bodyPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Costruisce un'istanza dedicata <br>
     * Inserisce l'istanza (grafica) in bodyPlacehorder della view <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public AForm getBodyFormLayout(Class<? extends AEntity> entityClazz) {
        form = null;

        //--entityClazz dovrebbe SEMPRE esistere, ma meglio controllare
        if (entityClazz != null) {
            form = appContext.getBean(AForm.class, entityClazz);
        }

        return form;
    }


    /**
     * Costruisce un layout per il Form in bodyPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Costruisce un'istanza dedicata <br>
     * Passa all'istanza un wrapper di dati <br>
     * Inserisce l'istanza (grafica) in bodyPlacehorder della view <br>
     *
     * @param entityBean interessata
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public AForm getBodyFormLayout(AEntity entityBean) {
        form = null;

        //--entityBean dovrebbe SEMPRE esistere (anche vuoto), ma meglio controllare
        if (entityBean != null) {
            form = appContext.getBean(AForm.class, getWrapForm(entityBean));
        }

        return form;
    }


    /**
     * Costruisce un wrapper di dati <br>
     * I dati sono gestiti da questa 'logic' (nella sottoclasse eventualmente) <br>
     * I dati vengono passati alla View che li usa <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     *
     * @param entityBean interessata
     *
     * @return wrapper di dati per il Form
     */
    public WrapForm getWrapForm(AEntity entityBean) {
        return new WrapForm(entityBean);
    }


    /**
     * Costruisce un layout per i bottoni di comando in bottomPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Recupera dal service specifico una List<AEButton> di bottoni previsti <br>
     * Se List<AEButton> è vuota, ATopLayout usa i bottoni di default (solo New) <br>
     * Costruisce un'istanza dedicata con i bottoni <br>
     * Inserisce l'istanza (grafica) in bottomPlacehorder della view <br>
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public ABottomLayout getBottomLayout(AEOperation operationForm) {
        ABottomLayout bottomLayout = appContext.getBean(ABottomLayout.class, getWrapButtonsBottom());
        this.addBottomListeners(bottomLayout);

        return bottomLayout;
    }


    /**
     * Costruisce un wrapper di dati <br>
     * I dati sono gestiti da questa 'logic' (nella sottoclasse eventualmente) <br>
     * I dati vengono passati alla View che li usa <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     *
     * @return wrapper di dati per la view
     */
    public WrapButtons getWrapButtonsBottom() {
        List<AEButton> listaBottom = this.getListaBottoniBottom();
        return new WrapButtons(listaBottom, operationForm);
    }


    /**
     * Costruisce una lista di bottoni (enumeration) per l'istanza di ABottomLayout <br>
     * Di default non costruisce nulla <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     * Di default costruisce i bottoni 'Back, Save e Delete' che la sottoclasse può modificare <br>
     * Se 'listaBottoni' rimane vuota, il layout usa i bottoni di default previsti per il tipo dìi operationForm <br>
     */
    protected List<AEButton> getListaBottoniBottom() {
        return null;
    }


    /**
     * Aggiunge tutti i listeners ai bottoni di 'bottomPlaceholder' che sono stati creati SENZA listeners <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected void addBottomListeners(ABottomLayout bottomLayout) {
        if (bottomLayout != null) {
            bottomLayout.setAllListener(this, entityBean);
        }
    }


    /**
     * Costruisce una lista di nomi delle properties del Form nell'ordine:
     * 1) Cerca nell'annotation @AIForm della Entity e usa quella lista (con o senza ID)
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse)
     * 3) Sovrascrive la lista nella sottoclasse specifica di xxxService
     *
     * @return lista di nomi delle properties da usare nel form
     */
    @Override
    //@todo Funzionalità ancora da implementare
    public List<String> getListaPropertiesForm() {
        List<String> lista = null;
        lista = annotation.getListaPropertiesForm(entityClazz);

        //        if (lista.contains(FIELD_NAME_COMPANY) && !context.getLogin().isDeveloper()) {
        //            lista.remove(FIELD_NAME_COMPANY);
        //        }// end of if cycle

        return lista;
    }


    /**
     * Costruisce una lista di nomi delle properties del Form, specializzata per una specifica operazione <br>
     * Di default utilizza la lista generale di getListaPropertiesForm() <br>
     * Sovrascritto nella sottoclasse concreta <br>
     *
     * @return lista di nomi delle properties da usare nel form
     */
    @Override
    public List<String> getListaPropertiesFormNew() {
        return getListaPropertiesForm();
    }


    /**
     * Costruisce una lista di nomi delle properties del Form, specializzata per una specifica operazione <br>
     * Di default utilizza la lista generale di getListaPropertiesForm() <br>
     * Sovrascritto nella sottoclasse concreta <br>
     *
     * @return lista di nomi delle properties da usare nel form
     */
    @Override
    public List<String> getListaPropertiesFormEdit() {
        return getListaPropertiesForm();
    }


    /**
     * Costruisce una lista di nomi delle properties del Form, specializzata per una specifica operazione <br>
     * Di default utilizza la lista generale di getListaPropertiesForm() <br>
     * Sovrascritto nella sottoclasse concreta <br>
     *
     * @return lista di nomi delle properties da usare nel form
     */
    @Override
    public List<String> getListaPropertiesFormDelete() {
        return getListaPropertiesForm();
    }


    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     *
     * @param azione selezionata da eseguire
     */
    public void performAction(AEAction azione) {
        this.performAction(azione, VUOTA, (AEntity) null);
    }


    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     * Invocata solo dalla Grid <br>
     *
     * @param azione           selezionata da eseguire
     * @param searchFieldValue valore corrente del campo editText (solo per List)
     */
    public void performAction(AEAction azione, String searchFieldValue) {
        this.performAction(azione, searchFieldValue, (AEntity) null);
    }


    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     * Invocata solo dal Form <br>
     *
     * @param azione     selezionata da eseguire
     * @param entityBean selezionata (solo per Form)
     */
    @Override
    public void performAction(AEAction azione, AEntity entityBean) {
        this.performAction(azione, VUOTA, entityBean);
    }


    private void performAction(AEAction azione, String searchFieldValue, AEntity entityBean) {
        switch (azione) {
            case deleteAll:
                this.openConfirmDeleteAll();
                break;
            case reset:
                this.openConfirmReset();
                break;
            case doubleClick:
                openDialogRoute(entityBean);
                break;
            case nuovo:
                this.openDialogRoute();
                break;
            case edit:
                break;
            case show:
                break;
            case editNoDelete:
                break;
            case back:
            case annulla:
                this.back();
                break;
            case conferma:
            case registra:
                if (saveDaForm(entityBean)) {
                    this.back();
                }
                break;
            case delete:
                this.delete();
                this.back();
                break;
            case searchField:
                this.searchFieldValue = searchFieldValue;
                refreshGrid();
                break;
            case searchDialog:
                Notification.show("Not yet. Coming soon.", 3000, Notification.Position.MIDDLE);
                logger.info("Not yet. Coming soon", this.getClass(), "performAction");
                break;
            case valueChanged:
                refreshGrid();
                break;
            case showWiki:
                openWikiPage();
                break;
            default:
                logger.warn("Switch - caso non definito", ALogic.class, "performAction(azione, keyID)");
                break;
        }
    }


    /**
     * Azione proveniente dal click sul bottone Annulla
     */
    protected void back() {
        UI.getCurrent().getPage().getHistory().back();
    }


    protected final void openDetail() {
        //        //@todo Funzionalità ancora da implementare
        //        if (false) {
        //            openDialogForm();
        //        } else {
        //            openDialogRoute();
        //        }
    }


    //@todo Funzionalità ancora da implementare
    protected final void openDialogForm() {
    }


    protected final void openDialogRoute() {
        final QueryParameters query = route.getQueryForm(entityClazz);
        UI.getCurrent().navigate(ROUTE_NAME_GENERIC_FORM, query);
    }


    protected final void openDialogRoute(AEntity entityBean) {
        final QueryParameters query = route.getQueryForm(entityClazz, entityBean.id, operationForm);
        UI.getCurrent().navigate(ROUTE_NAME_GENERIC_FORM, query);
    }


    /**
     * Opens the confirmation dialog before deleting all items. <br>
     * <p>
     * The dialog will display the given title and message(s), then call <br>
     * Può essere sovrascritto dalla classe specifica se servono avvisi diversi <br>
     */
    protected final void openConfirmDeleteAll() {
        MessageDialog messageDialog;
        String message = "Vuoi veramente cancellare tutto? L'operazione non è reversibile.";
        VaadinIcon icon = VaadinIcon.WARNING;

        if (mongo.isValid(entityClazz)) {
            messageDialog = new MessageDialog().setTitle("Delete").setMessage(message);
            messageDialog.addButton().text("Cancella").icon(icon).error().onClick(e -> clickDeleteAll()).closeOnClick();
            messageDialog.addButtonToLeft().text("Annulla").primary().clickShortcutEscape().clickShortcutEnter().closeOnClick();
            messageDialog.open();
        }

    }


    /**
     * Opens the confirmation dialog before reset all items. <br>
     * <p>
     * The dialog will display the given title and message(s), then call <br>
     * Può essere sovrascritto dalla classe specifica se servono avvisi diversi <br>
     */
    protected final void openConfirmReset() {
        MessageDialog messageDialog;
        String message = "Vuoi veramente ripristinare i valori originali predeterminati di questa collezione? L'operazione cancellerà tutti i valori successivamente aggiunti o modificati.";
        VaadinIcon icon = VaadinIcon.WARNING;

        if (mongo.isEmpty(entityClazz)) {
            clickReset();
        } else {
            messageDialog = new MessageDialog().setTitle("Reset").setMessage(message);
            messageDialog.addButton().text("Continua").icon(icon).error().onClick(e -> clickReset()).closeOnClick();
            messageDialog.addButtonToLeft().text("Annulla").primary().clickShortcutEscape().clickShortcutEnter().closeOnClick();
            messageDialog.open();
        }
    }


    /**
     * Apre una pagina di wikipedia. <br>
     */
    protected final void openWikiPage() {
        String link = "\"" + PATH_WIKI + wikiPageTitle + "\"";
        UI.getCurrent().getPage().executeJavaScript("window.open(" + link + ");");
    }


    /**
     * Costruisce una lista di nomi delle properties della Grid nell'ordine: <br>
     * 1) Cerca nell'annotation @AIList della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica <br>
     * todo ancora da sviluppare
     *
     * @param entityClazz the class of type AEntity
     *
     * @return lista di nomi di properties
     */
    //    @Override
    //    public List<String> getGridPropertyNamesList(Class<? extends AEntity> entityClazz) {
    public List<String> getGridPropertyNamesList() {
        return annotation.getListaPropertiesGrid(entityClazz);
    }


    /**
     * Aggiorna gli items della Grid, utilizzando i filtri. <br>
     * Chiamato per modifiche effettuate ai filtri, popup, newEntity, deleteEntity, ecc... <br>
     */
    public void refreshGrid() {
        List<? extends AEntity> items;

        if (grid != null && grid.getGrid() != null) {
            updateFiltri();
            items = mongo.findAll(entityClazz, filtri, sortView);
            grid.deselectAll();
            grid.refreshAll();
            grid.setItems(items);
        }
    }


    /**
     * Recupera ed aggiorna i filtri. <br>
     */
    public void updateFiltri() {
        AFiltro filtro = null;
        filtri = new ArrayList<>();

        //--filtro base della entity
        this.creaFiltroBaseEntity();

        //--filtro del campo search
        this.creaFiltroSearch();

        //--filtri aggiuntivi dei comboBox
        this.creaFiltriComboBox();
    }


    /**
     * Filtro base della entity. <br>
     */
    public void creaFiltroBaseEntity() {
        AFiltro filtro = null;
        Sort sort = annotation.getSort(entityClazz);

        if (sort != null) {
            filtro = new AFiltro(sort);
        }

        if (filtro != null) {
            filtri.add(filtro);
        }
    }


    /**
     * Filtro (eventuale) del searchField. <br>
     */
    public void creaFiltroSearch() {
        AFiltro filtro = null;
        CriteriaDefinition criteria = null;

        if (text.isValid(searchFieldValue)) {
            //            if (pref.isBool(USA_SEARCH_CASE_SENSITIVE)) { //@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
            if (false) {
                filtro = new AFiltro(Criteria.where(searchProperty).regex("^" + searchFieldValue));
            } else {
                if (text.isValid(searchFieldValue)) {
                    filtro = new AFiltro(Criteria.where(searchProperty).regex("^" + searchFieldValue, "i"));
                }
            }
        }
        if (filtro != null) {
            filtri.add(filtro);
        }
    }


    /**
     * Filtri (eventuali) dei comboBox. <br>
     */
    public void creaFiltriComboBox() {
        AFiltro filtro = null;
        ComboBox combo;
        CriteriaDefinition criteria = null;

        if (array.isValid(mappaComboBox)) {
            for (Map.Entry<String, ComboBox> entry : mappaComboBox.entrySet()) {
                combo = entry.getValue();
                if (combo.getValue() != null) {
                    filtro = new AFiltro(Criteria.where(entry.getKey()).is(combo.getValue()));
                    logger.info(combo.getValue().toString(), this.getClass(), "creaFiltriComboBox");
                }
            }
        }
        if (filtro != null) {
            filtri.add(filtro);
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Senza properties per compatibilità con la superclasse <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public AEntity newEntity() {
        AEntity newEntityBean = null;

        try {
            newEntityBean = (AEntity) entityClazz.newInstance();
        } catch (Exception unErrore) {
            logger.warn(unErrore.toString(), this.getClass(), "newEntity");
        }

        return newEntityBean;
    }


    /**
     * Ordine di presentazione (facoltativo) <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property <br>
     * Incrementa di uno il risultato <br>
     */
    public int getNewOrdine() {
        return mongo.getNewOrder(entityClazz, "ordine");
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
    public AEntity findById(String keyID) {
        return mongo.findById(entityClazz, keyID);
    }


    //    /**
    //     * Registra una entity solo se non esisteva <br>
    //     *
    //     * @param entityBean nuova entity appena creata (non salvata e senza keyID)
    //     *
    //     * @return true se la entity è stata registrata
    //     */
    //    public boolean saveIfNotExist(AEntity entityBean) {
    //        return mongo.saveIfNotKey(entityBean, keyPropertyName) != null;
    //    }


    //    /**
    //     * Inserisce una entity solo se non esisteva <br>
    //     *
    //     * @param newEntityBean to be inserted
    //     *
    //     * @return true se la entity è stata inserita
    //     */
    //    public boolean insertIfNotExist(AEntity newEntityBean) {
    //        if (mongo.isEsiste(entityClazz, newEntityBean.id)) {
    //            return false;
    //        } else {
    //            try {
    //                return insert(newEntityBean) != null;
    //            } catch (Exception unErrore) {
    //                logger.error(unErrore, this.getClass(), "nomeDelMetodo");
    //                return false;
    //            }
    //
    //        }
    //    }


    /**
     * Inserts a document into a collection. <br>
     *
     * @param newEntityBean to be inserted
     *
     * @return the saved entity
     */
    public AEntity insert(AEntity newEntityBean) {
        AEntity entityBean = null;
        newEntityBean = fixKey(newEntityBean);
        entityBean = mongo.insert(newEntityBean);

        if (entityBean != null) {
            logger.nuovo(entityBean);
        }
        return entityBean;
    }


    /**
     * Regola la chiave se esiste il campo keyPropertyName. <br>
     *
     * @param newEntityBean to be checked
     *
     * @return the checked entity
     */
    public AEntity fixKey(AEntity newEntityBean) {
        String keyPropertyName;
        String keyPropertyValue;

        if (text.isEmpty(newEntityBean.id)) {
            keyPropertyName = annotation.getKeyPropertyName(newEntityBean.getClass());
            if (text.isValid(keyPropertyName)) {
                keyPropertyValue = reflection.getPropertyValueStr(newEntityBean, keyPropertyName);
                if (text.isValid(keyPropertyValue)) {
                    keyPropertyValue = text.levaSpaziInterni(keyPropertyValue);
                    newEntityBean.id = keyPropertyValue.toLowerCase();
                }
            }
        }

        return newEntityBean;
    }


    /**
     * Save proveniente da un click sul bottone 'registra' del Form. <br>
     * La entityBean che arriva NON è necessariamente sincronizzata <br>
     * Meglio recuperare dal form la versione più affidabile <br>
     *
     * @return the saved entity
     */
    public boolean saveDaForm(AEntity entityBean) {
        if (form != null) {
            entityBean = form.getValidBean();
        }

        if (entityBean == null) {
            Notification.show("Alcuni campi non sono adeguati", 3000, Notification.Position.MIDDLE);
            return false;
        }

        if (operationForm != null) {
            switch (operationForm) {
                case addNew:
                    return insert(entityBean) != null;
                case edit:
                case editNoDelete:
                case editProfile:
                case editDaLink:
                    return save(entityBean) != null;
                case showOnly:
                    break;
                default:
                    logger.warn("Switch - caso non definito", this.getClass(), "saveDaForm");
                    break;
            }
        } else {
            return false;
        }
        return false;
    }


    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     *
     * @return the saved entity
     */
    public AEntity save(AEntity entityBean) {
        boolean modificata = false;
        AEntity entityBeanOld = null;

        if (entityBean == null) {
            logger.error("La entityBean è nulla", ALogic.class, "save");
            return null;
        }

        if (beanService.isModificata(entityBean)) {
            if (operationForm == AEOperation.addNew) {
                logger.nuovo(entityBean);
            } else {
                modificata = true;
                entityBeanOld = mongo.find(entityBean);
            }
        }

        if (text.isEmpty(entityBean.id) && !(operationForm == AEOperation.addNew)) {
            logger.error("operationForm errato in una nuova entity che NON è stata salvata", ALogic.class, "save");
            return null;
        }

        if (entityBean != null) {
            if (operationForm == AEOperation.addNew && entityBean.id == null && text.isValid(keyPropertyName)) {
                entityBean.id = reflection.getPropertyValueStr(entityBean, keyPropertyName);
            }
            entityBean = mongo.save(entityBean);
        } else {
            logger.error("Object to save must not be null", this.getClass(), "save");
        }

        if (entityBean == null) {
            if (operationForm != null) {
                switch (operationForm) {
                    case addNew:
                        logger.warn("Non sono riuscito a creare la entity ", this.getClass(), "save");
                        break;
                    case edit:
                        logger.warn("Non sono riuscito a modificare la entity ", this.getClass(), "save");
                        break;
                    default:
                        logger.warn("Switch - caso non definito", this.getClass(), "save");
                        break;
                }
            } else {
                logger.warn("Non sono riuscito a creare la entity ", this.getClass(), "save");
            }
        } else {
            if (modificata && entityBeanOld != null) {
                logger.modifica(entityBean, entityBeanOld);
            }
        }

        return entityBean;
    }


    public boolean delete() {
        boolean status = false;
        AEntity entityBean = (form != null) ? form.getValidBean() : null;

        if (mongo.delete(entityBean)) {
            status = true;
            logger.delete(entityBean);
        }

        return status;
    }


    /**
     * Cancellazione effettiva (dopo dialogo di conferma) di tutte le entities della collezione. <br>
     * Azzera gli items <br>
     * Ridisegna la GUI <br>
     */
    public void clickDeleteAll() {
        if (deleteAll()) {
            logger.deleteAll(entityClazz);
            this.refreshGrid();
            UI.getCurrent().getPage().reload();
        }
    }


    /**
     * Deletes all entities of the collection. <br>
     * Può essere sovrascritto nel service specifico <br>
     *
     * @return true se la collection è stata cancellata
     */
    @Override
    public boolean deleteAll() {
        if (mongo.isValid(entityClazz)) {
            mongo.mongoOp.remove(new Query(), entityClazz);
            return true;
        } else {
            return true;
        }
    }


    /**
     * Azione proveniente dal click sul bottone Reset <br>
     * Creazione di alcuni dati iniziali <br>
     * Rinfresca la griglia <br>
     */
    public void clickReset() {
        if (reset()) {
            logger.reset(entityClazz);
            this.refreshGrid();
        }
    }


    /**
     * Creazione di alcuni dati iniziali <br>
     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo in alcuni casi) <br>
     * I dati possono essere presi da una Enumeration o creati direttamente <br>
     * DEVE essere sovrascritto <br>
     *
     * @return false se non esiste il metodo sovrascritto
     * ....... true se esiste il metodo sovrascritto è la collection viene ri-creata
     */
    @Override
    public boolean reset() {
        return false;
    }


    /**
     * Crea un ComboBox e lo aggiunge alla mappa <br>
     */
    protected void creaComboBox(Class entityClazz) {
        creaComboBox(entityClazz, "8em", true, true);
    }


    /**
     * Crea un ComboBox e lo aggiunge alla mappa <br>
     */
    protected void creaComboBox(Class entityClazz, boolean clearButtonVisible, boolean required) {
        creaComboBox(entityClazz, "8em", clearButtonVisible, required);
    }


    /**
     * Crea un ComboBox e lo aggiunge alla mappa <br>
     */
    protected void creaComboBox(Class entityClazz, String width, boolean clearButtonVisible, boolean required) {
        String tag = TRE_PUNTI;
        ComboBox combo = new ComboBox();
        combo.setWidth(width);
        combo.setPreventInvalidInput(true);
        combo.setAllowCustomValue(false);
        combo.setPlaceholder(annotation.getRecordName(entityClazz) + tag);
        combo.setClearButtonVisible(clearButtonVisible);
        combo.setRequired(required);

        combo.setItems(mongo.find(entityClazz));
        mappaComboBox.put(annotation.getCollectionName(entityClazz), combo);
    }


    /**
     * Aggiunge un ComboBox alla mappa <br>
     */
    protected void addComboBox(ComboBox combo, String fieldName, List items, String width, String placeHolder, boolean clearButtonVisible, boolean required) {
        combo.setWidth(width);
        combo.setPreventInvalidInput(true);
        combo.setAllowCustomValue(false);
        combo.setPlaceholder(placeHolder);
        combo.setClearButtonVisible(clearButtonVisible);
        combo.setRequired(required);

        combo.setItems(items);
        mappaComboBox.put(fieldName, combo);
    }

}
