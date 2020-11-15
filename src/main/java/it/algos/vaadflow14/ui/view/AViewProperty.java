package it.algos.vaadflow14.ui.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEColor;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.logic.AILogic;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.packages.preferenza.APreferenzaService;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import it.algos.vaadflow14.ui.service.ARouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: Mon, 20-May-2019
 * Time: 07:06
 * <p>
 * Superclasse astratta della view AView. <br>
 * Serve per 'dichiarare' in un posto solo i riferimenti ad altre classi ed usarli nelle sottoclassi concrete <br>
 * I riferimenti sono 'public' per poterli usare con TestUnit <br>
 * <p>
 * Superclasse di servizio per separare le property di AView in una classe 'dedicata' <br>
 * Alleggerisce la 'lettura' della sottoclasse principale. Non contiene 'business logic' <br>
 * Le property sono regolarmente disponibili in AView ed in tutte le sue sottoclassi <br>
 */
public abstract class AViewProperty extends VerticalLayout {


    /**
     * Istanza di una interfaccia <br>
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
    public ARouteService route;

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
    public AClassService classService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public APreferenzaService pref;


    //    @Autowired
    //    public ABootService boot;


    //    @Autowired
    //    public AColumnService column;


    //    @Autowired
    //    public ADateService date;


    //    @Autowired
    //    public AFieldService field;


    //        @Autowired
    //        public AMongoService mongo;


    //    @Autowired
    //    public AEnumerationService enumService;


    /**
     * Placeholder iniziale per avvisi (list e form) <br>
     * Contenuto eventuale, non presente di default <br>
     * Label o altro per informazioni specifiche; di norma per il developer <br>
     */
    protected VerticalLayout alertPlaceholder;

    /**
     * Placeholder per bottoni di menu/comando PRIMA del body (solo list) <br>
     * Contenuto obbligatorio <br>
     */
    protected VerticalLayout topPlaceholder;

    /**
     * Contenuto obbligatorio di uno dei due tipi: (list e form) <br>
     * Placeholder per la GRID principale CON o SENZA paginazione <br>
     * Placeholder per il Form <br>
     * Alcune regolazioni da preferenza o da parametro (bottone Edit, ad esempio) <br>
     */
    protected VerticalLayout bodyPlaceholder;

    /**
     * Placeholder per bottoni di menu/comando DOPO il body (list e form) <br>
     * Contenuto facoltativo, assente di default (list) <br>
     * Contenuto obbligatorio (form) <br>
     */
    protected VerticalLayout bottomPlaceholder;

    /**
     * Placeholder finale per messaggi (list e form) <br>
     * Contenuto facoltativo, presente di default (list) <br>
     * Contenuto facoltativo, assente di default (form) <br>
     * Barra inferiore di messaggi per l'utilizzattore <br>
     */
    protected VerticalLayout footerPlaceholder;

    /**
     * The Entity Logic (obbligatorio per liste e form)
     */
    protected AILogic entityLogic;

    /**
     * The Entity Class  (obbligatorio per liste e form)
     */
    protected Class<? extends AEntity> entityClazz;

    /**
     * The Entity Bean  (obbligatorio  per il form)
     */
    protected AEntity entityBean;


    /**
     * The Type vista.
     */
    protected AEVista typeVista;

    /**
     * Tipologia di Form in uso <br>
     * Si recupera nel metodo AView.setParameter(), chiamato dall'interfaccia HasUrlParameter <br>
     */
    protected AEOperation operationForm;


    /**
     * Costruisce gli oggetti base (placeholder) di questa view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Può essere sovrascritto, per modificare il layout standard <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixLayout() {
        this.removeAll();
        this.setMargin(false);
        this.setSpacing(true);
        this.setPadding(true);

        //--Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
        this.alertPlaceholder = new VerticalLayout();
        this.alertPlaceholder.removeAll();
        this.alertPlaceholder.setMargin(false);
        this.alertPlaceholder.setSpacing(false);
        this.alertPlaceholder.setPadding(false);

        //--Costruisce un layout (obbligatorio) per i menu ed i bottoni di comando della view <br>
        this.topPlaceholder = new VerticalLayout();
        this.topPlaceholder.removeAll();
        this.topPlaceholder.addClassName("view-toolbar");
        this.topPlaceholder.setMargin(false);
        this.topPlaceholder.setSpacing(false);
        this.topPlaceholder.setPadding(false);

        this.bodyPlaceholder = new VerticalLayout();
        this.bodyPlaceholder.removeAll();
        bodyPlaceholder.setMargin(false);
        bodyPlaceholder.setSpacing(false);
        bodyPlaceholder.setPadding(false);

        this.bottomPlaceholder = new VerticalLayout();
        this.bottomPlaceholder.removeAll();
        bottomPlaceholder.setMargin(false);
        bottomPlaceholder.setSpacing(false);
        bottomPlaceholder.setPadding(false);

        this.footerPlaceholder = new VerticalLayout();
        this.footerPlaceholder.removeAll();
        footerPlaceholder.setMargin(false);
        footerPlaceholder.setSpacing(false);
        footerPlaceholder.setPadding(false);

        if (AEPreferenza.usaDebug.is()) {
            this.getElement().getStyle().set("background-color", AEColor.yellow.getEsadecimale());
            alertPlaceholder.getElement().getStyle().set("background-color", AEColor.bisque.getEsadecimale());
            topPlaceholder.getElement().getStyle().set("background-color", AEColor.lightpink.getEsadecimale());
            bodyPlaceholder.getElement().getStyle().set("background-color", AEColor.lightgreen.getEsadecimale());
            bottomPlaceholder.getElement().getStyle().set("background-color", AEColor.gainsboro.getEsadecimale());
            footerPlaceholder.getElement().getStyle().set("background-color", AEColor.silver.getEsadecimale());
        }
    }


    /**
     * Regola le property indispensabili per gestire questa view <br>
     * Possono provenire da una sottoclasse concreta oppure dai parametri del browser <br>
     * <p>
     * Se arriva qui da una sottoclasse, vuol dire:
     * - la @Route NON è ROUTE_NAME_GENERIC_VIEW
     * - che è stata chiamata una @Route di una classe tipo xxxList
     * - la stringa del browser NON ha parametri
     * - le property sono già regolate
     * <p>
     * Se arriva qui da una view generica, vuol dire:
     * - la @Route DEVE essere ROUTE_NAME_GENERIC_VIEW
     * - la stringa del browser DEVE avere dei parametri
     * - le property devono essere regolate
     * <p>
     * The Entity Service (obbligatorio) di tipo AEntityService
     * The Entity Class  (obbligatorio) (per la Grid) di tipo AEntity
     * The Entity Bean  (obbligatorio) (per il Form)
     */
    public void fixProperty() {
        this.entityClazz = null;
        this.entityLogic = null;
        this.entityBean = null;
        this.typeVista = null;
    }

}
