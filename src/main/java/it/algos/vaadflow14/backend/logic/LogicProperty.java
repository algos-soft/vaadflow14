package it.algos.vaadflow14.backend.logic;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.button.*;
import it.algos.vaadflow14.ui.enumeration.*;
import it.algos.vaadflow14.ui.service.*;
import it.algos.vaadflow14.ui.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 27-feb-2021
 * Time: 14:37
 * Superclasse astratta di LogicList. <br>
 * Serve per 'dichiarare' i riferimenti ad altre classi ed usarli nelle sottoclassi concrete <br>
 * I riferimenti sono 'public' per poterli usare con TestUnit <br>
 * Serve per 'dichiarare' le property ed usarle nelle sottoclassi concrete <br>
 * Le properties sono 'protected' per poterle usare nelle sottoclassi <br>
 * <p>
 * Alleggerisce la 'lettura' della sottoclasse principale. Non contiene 'business logic' <br>
 * Le property sono regolarmente disponibili in LogicList ed in tutte le sue (probabili) sottoclassi <br>
 */
public abstract class LogicProperty extends VerticalLayout {

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
    public AHtmlService html;

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
    public AAnnotationService annotation;

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
    public ARouteService route;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AClassService classService;

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
    public ADateService date;

    /**
     * PlaceHolder iniziale per avvisi <br>
     * Label o altro per informazioni specifiche; di norma per il developer <br>
     * Contenuto facoltativo, assente di default <br>
     */
    protected VerticalLayout alertPlaceHolder;

    /**
     * PlaceHolder per bottoni di comando SOPRA la Grid <br>
     * Contenuto semi-obbligatorio <br>
     */
    protected VerticalLayout topPlaceHolder;

    /**
     * PlaceHolder principale per la GRID <br>
     * Alcune regolazioni da preferenza o da parametro (bottone Edit, ad esempio) <br>
     * Contenuto obbligatorio <br>
     */
    protected VerticalLayout bodyPlaceHolder;

    /**
     * PlaceHolder per bottoni di comando SOTTO la Grid <br>
     * Contenuto facoltativo, assente di default <br>
     */
    protected VerticalLayout bottomPlaceHolder;

    /**
     * PlaceHolder finale per messaggi <br>
     * Barra inferiore di messaggi per l'utilizzatore <br>
     * Contenuto facoltativo, assente di default <br>
     */
    protected VerticalLayout footerPlaceHolder;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected ADataProviderService dataService;

    /**
     * The entityClazz obbligatorio di tipo AEntity, per liste e form <br>
     */
    protected Class<? extends AEntity> entityClazz;

    /**
     * The entityBean, istanza di entityClazz obbligatorio solo per il form <br>
     */
    protected AEntity entityBean;

    /**
     * The entityService obbligatorio, singleton di tipo xxxService <br>
     */
    protected AIService entityService;

    /**
     * Wrapper di dati recuperati dall'url del browser, obbligatorio per il form <br>
     */
    protected Parametro routeParameter;


    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneDeleteAll;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneResetList;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneNew;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneSearch;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneExport;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottonePaginaWiki;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneUpdate;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneUpload;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneDownload;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneElabora;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneCheck;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneModulo;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneTest;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneStatistiche;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneResetForm;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneBack;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneAnnulla;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneConferma;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneRegistra;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneCancella;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottonePrima;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneDopo;

    /**
     * Flag di preferenza per specificare il massimo numero di bottoni della prima riga <br>
     */
    protected int maxNumeroBottoniPrimaRiga;

    /**
     * Riferimento al contenitore dei bottoni per eventuali regolazioni <br>
     */
    protected ATopLayout topLayout;

    /**
     * Riferimento al contenitore dei bottoni per eventuali regolazioni <br>
     */
    protected AButtonLayout bottomLayout;

    /**
     * The Type vista.
     */
    protected AEVista typeVista;

    /**
     * Tipologia di Form in uso <br>
     * Si recupera nel metodo AView.setParameter(), chiamato dall'interfaccia HasUrlParameter <br>
     */
    protected AEOperation operationForm = AEOperation.listNoForm;


    /**
     * Preferenze usate da questa 'logica' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.usaBottoneDeleteAll = false;
        this.usaBottoneResetList = false;
        this.usaBottoneNew = false;
        this.usaBottoneSearch = false;
        this.usaBottoneExport = false;
        this.usaBottonePaginaWiki = false;
        this.usaBottoneUpdate = false;
        this.usaBottoneUpload = false;
        this.usaBottoneDownload = false;
        this.usaBottoneElabora = false;
        this.usaBottoneCheck = false;
        this.usaBottoneModulo = false;
        this.usaBottoneTest = false;
        this.usaBottoneStatistiche = false;

        this.usaBottoneResetForm = false;
        this.usaBottoneBack = false;
        this.usaBottoneAnnulla = false;
        this.usaBottoneConferma = false;
        this.usaBottoneRegistra = false;
        this.usaBottoneCancella = false;
        this.usaBottonePrima = false;
        this.usaBottoneDopo = false;

        this.maxNumeroBottoniPrimaRiga = AEPreferenza.numeroBottoni.getInt();
    }


    /**
     * Costruisce i 5 oggetti base (placeholder) di questa view <br>
     * <p>
     * Chiamato da LogicList.initView() <br>
     * Può essere sovrascritto, per modificare il layout standard <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixLayout() {
        this.removeAll();
        this.setMargin(false);
        this.setSpacing(false);
        this.setPadding(true);

        //--Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
        this.alertPlaceHolder = new AVerticalLayout();

        //--Costruisce un layout (semi-obbligatorio) per i bottoni di comando della view <br>
        //--Eventualmente i bottoni potrebbero andare su due righe <br>
        this.topPlaceHolder = new AVerticalLayout();

        //--Costruisce il corpo principale (obbligatorio) della Grid <br>
        this.bodyPlaceHolder = new AVerticalLayout();

        //--Costruisce un (eventuale) layout per i bottoni sotto la Grid <br>
        this.bottomPlaceHolder = new AVerticalLayout();

        //--Costruisce un (eventuale) layout per scritte in basso della pagina <br>
        this.footerPlaceHolder = new AVerticalLayout();

        if (AEPreferenza.usaDebug.is()) {
            this.getElement().getStyle().set("background-color", AEColor.yellow.getEsadecimale());
            this.alertPlaceHolder.getElement().getStyle().set("background-color", AEColor.bisque.getEsadecimale());
            this.topPlaceHolder.getElement().getStyle().set("background-color", AEColor.lightgreen.getEsadecimale());
            this.bodyPlaceHolder.getElement().getStyle().set("background-color", AEColor.lightpink.getEsadecimale());
            this.bottomPlaceHolder.getElement().getStyle().set("background-color", AEColor.gainsboro.getEsadecimale());
            this.footerPlaceHolder.getElement().getStyle().set("background-color", AEColor.silver.getEsadecimale());
        }
    }


    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * <p>
     * Chiamato da LogicList.initView() <br>
     * Nell' implementazione standard di default NON presenta nessun avviso <br>
     * Recupera dal service specifico gli (eventuali) avvisi <br>
     * Costruisce un' istanza dedicata con le liste di avvisi <br>
     * Gli avvisi sono realizzati con tag html 'span' differenziati per colore anche in base all'utente collegato <br>
     * Se l'applicazione non usa security, il colore è deciso dal service specifico <br<
     * Se esiste, inserisce l' istanza (grafica) in alertPlaceHolder della view <br>
     */
    protected void fixAlertLayout() {
    }


    /**
     * Costruisce un layout per i bottoni di comando superiori della view <br>
     * Semi-obbligatorio per la List, facoltativo per il Form <br>
     * Aggiunge tutti i listeners ai bottoni <br>
     * Eventualmente i bottoni potrebbero andare su due righe <br>
     * <p>
     * Chiamato da LogicList.initView() <br>
     * Se esiste, inserisce l' istanza (grafica) in topPlaceHolder della view <br>
     */
    protected void fixTopLayout() {
    }


    /**
     * Costruisce il corpo principale (obbligatorio) della Grid <br>
     * <p>
     * Chiamato da LogicList.initView() <br>
     * Costruisce un' istanza dedicata con la Grid <br>
     * Inserisce l' istanza (grafica) in bodyPlacehorder della view <br>
     */
    protected void fixBodyLayout() {
    }


    /**
     * Costruisce un layout per i bottoni di comando inferiori della view <br>
     * Obbligatorio per il Form, facoltativo per la List <br>
     * <p>
     * Chiamato da LogicList.initView() <br>
     * Se esiste, inserisce l' istanza (grafica) in bottomPlaceHolder della view <br>
     */
    protected void fixBottomLayout() {
    }


    /**
     * Costruisce un (eventuale) layout per scritte in basso della pagina <br>
     * <p>
     * Chiamato da LogicList.initView() <br>
     * Normalmente non usato <br>
     * Se esiste, inserisce l' istanza (grafica) in footerPlaceHolder della view <br>
     */
    protected void fixFooterLayout() {
    }

    /**
     * Costruisce una lista di bottoni (enumeration) <br>
     * Di default costruisce (come da flag) i bottoni 'delete' e 'reset' <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected List<AEButton> getListaAEBottoni() {
        List<AEButton> listaBottoni = new ArrayList<>();

        if (usaBottoneDeleteAll) {
            listaBottoni.add(AEButton.deleteAll);
        }
        if (usaBottoneResetList && entityService != null) {
            //--se manca la classe specifica il metodo è vuoto e il bottone non potrebbe funzionare
            if (entityService.resetEmptyOnly().isErrato()) {
                listaBottoni.add(AEButton.resetList);
            }
        }
        if (usaBottoneNew) {
            listaBottoni.add(AEButton.nuovo);
        }
        if (usaBottoneSearch) {
            listaBottoni.add(AEButton.searchDialog);
        }
        if (usaBottoneExport) {
            listaBottoni.add(AEButton.export);
        }
        if (usaBottonePaginaWiki) {
            listaBottoni.add(AEButton.wiki);
        }
        if (usaBottoneUpdate) {
            listaBottoni.add(AEButton.update);
        }
        if (usaBottoneUpload) {
            listaBottoni.add(AEButton.upload);
        }
        if (usaBottoneDownload) {
            listaBottoni.add(AEButton.download);
        }
        if (usaBottoneElabora) {
            listaBottoni.add(AEButton.elabora);
        }
        if (usaBottoneCheck) {
            listaBottoni.add(AEButton.check);
        }
        if (usaBottoneModulo) {
            listaBottoni.add(AEButton.modulo);
        }
        if (usaBottoneTest) {
            listaBottoni.add(AEButton.test);
        }
        if (usaBottoneStatistiche) {
            listaBottoni.add(AEButton.statistiche);
        }

        if (usaBottoneResetForm) {
            listaBottoni.add(AEButton.resetForm);
        }
        if (usaBottoneBack) {
            listaBottoni.add(AEButton.back);
        }
        if (usaBottoneAnnulla) {
            listaBottoni.add(AEButton.annulla);
        }
        if (usaBottoneConferma) {
            listaBottoni.add(AEButton.conferma);
        }
        if (usaBottoneRegistra) {
            listaBottoni.add(AEButton.registra);
        }
        if (usaBottoneCancella) {
            listaBottoni.add(AEButton.delete);
        }
        if (usaBottonePrima) {
            listaBottoni.add(AEButton.prima);
        }
        if (usaBottoneDopo) {
            listaBottoni.add(AEButton.dopo);
        }

        return listaBottoni;
    }

    /**
     * Aggiunge i 5 oggetti base (placeholder) alla view, se sono utilizzati <br>
     */
    protected void addToLayout() {
        if (alertPlaceHolder != null && alertPlaceHolder.getComponentCount() > 0) {
            this.add(alertPlaceHolder);
        }
        if (topPlaceHolder != null && topPlaceHolder.getComponentCount() > 0) {
            this.add(topPlaceHolder);
        }
        if (bodyPlaceHolder != null && bodyPlaceHolder.getComponentCount() > 0) {
            this.add(bodyPlaceHolder);
        }
        if (bottomPlaceHolder != null && bottomPlaceHolder.getComponentCount() > 0) {
            this.add(bottomPlaceHolder);
        }
        if (footerPlaceHolder != null && footerPlaceHolder.getComponentCount() > 0) {
            this.add(footerPlaceHolder);
        }

        this.addFooterCopyright();
    }

    /**
     * Aggiunge il footer <br>
     * //@todo Funzionalità ancora da implementare per andare in basso alla finestra
     */
    protected void addFooterCopyright() {
        Span span;
        String message;
        String copy = DEVELOPER_COMPANY;
        String project = FlowVar.projectName;
        String version = String.valueOf(FlowVar.projectVersion);
        String data = date.get(FlowVar.versionDate);

        message = String.format("%s - %s %s del %s", copy, project, version, data);
        if (AEPreferenza.usaDebug.is() && text.isValid(FlowVar.projectNote)) {
            message += SEP + FlowVar.projectNote;
        }
        span = html.getSpanBlu(message, AETypeWeight.bold, AETypeSize.small);
        this.add(span);
    }

}
