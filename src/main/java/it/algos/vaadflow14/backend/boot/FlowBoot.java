package it.algos.vaadflow14.backend.boot;

import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.data.AData;
import it.algos.vaadflow14.backend.data.FlowData;
import it.algos.vaadflow14.backend.packages.company.Company;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.packages.crono.giorno.Giorno;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import it.algos.vaadflow14.backend.packages.geografica.continente.Continente;
import it.algos.vaadflow14.backend.packages.geografica.provincia.Provincia;
import it.algos.vaadflow14.backend.packages.geografica.regione.Regione;
import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.backend.packages.preferenza.Preferenza;
import it.algos.vaadflow14.backend.packages.security.Utente;
import it.algos.vaadflow14.backend.service.ALogService;
import it.algos.vaadflow14.backend.service.AMongoService;
import it.algos.vaadflow14.wizard.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.servlet.ServletContextListener;
import java.util.ArrayList;

import static it.algos.vaadflow14.backend.application.FlowCost.START_DATE;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 01-mag-2020
 * Time: 10:33
 * <p>
 * Running logic after the Spring context has been initialized <br>
 * Executed on container startup, before any browse command <br>
 * The method onApplicationEvent() will be executed nella sottoclasse before the application is up and <br>
 * Questa classe è astratta in modo da NON ricevere un @EventListener, che viene gestito dalla sottoclasse concreta <br>
 * La sottoclasse concreta di ogni applicazione usa le API di ServletContextListener e riceve un @EventListener <br>
 * (una volta sola) che rimanda al metodo onApplicationEvent() di questa classe <br>
 * <p>
 * Not annotated with @SpringComponent (sbagliato, SpringBoot crea la sottoclasse concreta) <br>
 * Not annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (sbagliato) <br>
 * <p>
 * Viene normalmente creata una sottoclasse concreta per l' applicazione specifica: <br>
 * - per regolare eventualmente alcuni flag in maniera non standard <br>
 * - lanciare gli schedulers in background <br>
 * - costruire e regolare una versione demo <br>
 * - controllare l' esistenza di utenti abilitati all' accesso <br>
 * <p>
 * Stampa a video (productionMode) i valori per controllo
 */
public abstract class FlowBoot implements ServletContextListener {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    public Environment environment;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    public AMongoService mongo;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    public FlowData flowData;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    public ALogService logger;

    //--riferimento alla sottoclasse di AData da usare per inizializzare i dati
    protected AData aData;


    /**
     * Constructor with @Autowired on setter.<br>
     * Per evitare di avere nel costruttore tutte le property che devono essere iniettate e per poterle aumentare <br>
     * senza dover modificare i costruttori delle sottoclassi, l'iniezione tramite @Autowired <br>
     * viene delegata ad alcuni metodi setter() che vengono qui invocati con valore (ancora) nullo. <br>
     * Al termine del ciclo init() del costruttore il framework SpringBoot/Vaadin, inietterà la relativa istanza <br>
     */
    public FlowBoot() {
        this.setEnvironment(environment);
        this.setMongo(mongo);
        this.setFlowData(flowData);
        this.setLogService(logger);
    }// end of constructor with @Autowired on setter


    /**
     * Executed on container startup
     * Setup non-UI logic here
     * Utilizzato per:
     * - registrare nella xxxApp, il servlet context non appena è disponibile
     * - regolare alcuni flag dell' applicazione, uguali e validi per tutte le sessioni e tutte le request <br>
     * - lanciare gli schedulers in background <br>
     * - costruire e regolare una versione demo <br>
     * - controllare l' esistenza di utenti abilitati all'accesso <br>
     * Running logic after the Spring context has been initialized
     * Any class that use this @EventListener annotation,
     * will be executed before the application is up and its onApplicationEvent method will be called
     * <p>
     * Viene normalmente creata una sottoclasse per l'applicazione specifica:
     * - per regolare eventualmente alcuni flag in maniera non standard
     * - lanciare gli schedulers in background <br>
     * - costruire e regolare una versione demo <br>
     * - controllare l' esistenza di utenti abilitati all' accesso <br>
     * <p>
     * Stampa a video (productionMode) i valori per controllo
     * Deve essere sovrascritto dalla sottoclasse concreta che invocherà questo metodo()
     */


    /**
     * Utilizzato per:
     * - registrare nella xxxApp, il servlet context non appena è disponibile
     * - regolare alcuni flag dell' applicazione, uguali e validi per tutte le sessioni e tutte le request <br>
     * - lanciare gli schedulers in background <br>
     * - costruire e regolare una versione demo <br>
     * - controllare l' esistenza di utenti abilitati all' accesso <br>
     *
     * @param event the event
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.fixDBMongo();
        this.fixVersioni();
        this.fixRiferimenti();
        this.iniziaDataPreliminari();
        this.creaPreferenze();
        this.fixApplicationVar();
        this.initData();
        this.fixPreferenze();
        this.addMenuRoutes();

        logger.startup();
    }


    /**
     * Inizializzazione di alcuni parametri del database mongoDB <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixDBMongo() {
        mongo.getMaxBlockingSortBytes();
        mongo.fixMaxBytes();
    }


    /**
     * Inizializzazione delle versioni standard di vaadinFlow <br>
     * Inizializzazione delle versioni del programma specifico <br>
     */
    @Deprecated()
    protected void fixVersioni() {
    }


    /**
     * Riferimento alla sottoclasse specifica di ABoot per utilizzare il metodo sovrascritto resetPreferenze() <br>
     * DEVE essere sovrascritto <br>
     */
    @Deprecated()
    protected void fixRiferimenti() {
        //        preferenzaService.applicationBoot = this;
    }


    /**
     * Inizializzazione dei dati di alcune collections essenziali per la partenza <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Deprecated()
    protected void iniziaDataPreliminari() {
    }


    /**
     * Crea le preferenze standard, se non esistono <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Per un reset ai valori di default, c'è il metodo reset() chiamato da preferenzaService <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected int creaPreferenze() {
        int numPref = 0;
        //        List<? extends AEntity> listaCompany = null;
        //        IAService serviceCompany;
        //
        //        if (usaCompany) {
        //            serviceCompany = (IAService) StaticContextAccessor.getBean(FlowVar.companyServiceClazz);
        //            listaCompany = serviceCompany.findAllAll();
        //            for (EAPreferenza eaPref : EAPreferenza.values()) {
        //                //--se usa company ed è companySpecifica=true, crea una preferenza per ogni company
        //                if (eaPref.isCompanySpecifica()) {
        //                    for (AEntity company : listaCompany) {
        //                        if (company instanceof Company) {
        //                            numPref = preferenzaService.creaIfNotExist(eaPref, (Company) company) ? numPref + 1 : numPref;
        //                        }// end of if cycle
        //                    }// end of for cycle
        //                } else {
        //                    numPref = preferenzaService.creaIfNotExist(eaPref) ? numPref + 1 : numPref;
        //                }// end of if/else cycle
        //            }// end of for cycle
        //        } else {
        //            for (EAPreferenza eaPref : EAPreferenza.values()) {
        //                numPref = preferenzaService.creaIfNotExist(eaPref) ? numPref + 1 : numPref;
        //            }// end of for cycle
        //        }// end of if/else cycle

        return numPref;
    }




    /**
     * Regola alcune variabili generali dell' applicazione al loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione, ma il loro valore può essere modificato <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixApplicationVar() {

        /**
         * Controlla se l' applicazione è multi-company oppure no <br>
         * Di default (per sicurezza) uguale a true <br>
         * Può essere modificato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         */
        FlowVar.usaCompany = true;

        /**
         * Controlla se l' applicazione usa il login oppure no <br>
         * Se si usa il login, occorre la classe SecurityConfiguration <br>
         * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
         * Di default (per sicurezza) uguale a true <br>
         * Può essere modificato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         * Può essere true anche se usaCompany=false <br>
         */
        FlowVar.usaSecurity = true;

        /**
         * Nome identificativo dell' applicazione <br>
         * Usato (eventualmente) nella barra di menu in testa pagina <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.projectName = VUOTA;

        /**
         * Descrizione completa dell' applicazione <br>
         * Deve essere regolato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.projectDescrizione = VUOTA;

        /**
         * Versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Può essere modificato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.projectVersion = Double.parseDouble(environment.getProperty("algos.framework.version"));
        ;

        /**
         * Data della versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.versionDate = START_DATE;

        /**
         * Eventuali informazioni aggiuntive da utilizzare nelle informazioni <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.projectNote = VUOTA;

        /**
         * Flag per usare le icone VaadinIcon <br>
         * In alternativa usa le icone 'lumo' <br>
         * Deve essere regolato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.usaVaadinIcon = true;

        /**
         * Eventuali titolo della pagina <br>
         * Deve essere regolato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.layoutTitle = VUOTA;

        /**
         * Nome da usare per recuperare la lista delle Company (o sottoclassi) <br>
         * Di default 'company' oppure eventuale sottoclasse specializzata per Company particolari <br>
         * Eventuale casting a carico del chiamante <br>
         * Può essere modificato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.companyClazzName = "company";

        /**
         * Path per recuperare dalle risorse un' immagine da inserire nella barra di menu di MainLayout. <br>
         * Ogni applicazione può modificarla <br>
         * Può essere modificato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.pathLogo = "img/medal.ico";

        /**
         * Lista dei moduli di menu da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
         * Regolata dall'applicazione durante l'esecuzione del 'container startup' (non-UI logic) <br>
         * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
         */
        FlowVar.menuRouteList = new ArrayList<>();

        /**
         * Mostra i quattro packages cronologici (secolo, anno, mese, giorno) <br>
         * Di default (per sicurezza) uguale a false <br>
         * Può essere modificato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.usaCronoPackages = false;

        /**
         * Mostra i quattro packages geografici (stato, regione, provincia, comune) <br>
         * Di default (per sicurezza) uguale a false <br>
         * Può essere modificato in xxxBoot.fixApplicationVar() sempre presente nella directory 'backend.boot' <br>
         */
        FlowVar.usaGeografiaPackages = false;
    }


    /**
     * Inizializzazione dei dati di alcune collections sul DB mongo <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void initData() {
        flowData.initData();
    }


    /**
     * Regolazione delle preferenze standard effettuata nella sottoclasse specifica <br>
     * Serve per modificare solo per l'applicazione specifica il valore standard della preferenza <br>
     * Eventuali modifiche delle preferenze specifiche (che peraltro possono essere modificate all'origine) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
    }




    /**
     * Questa classe viene invocata PRIMA della chiamata del browser
     * Se NON usa la security, le @Route vengono create solo qui
     * Se USA la security, le @Route vengono sovrascritte all' apertura del browser nella classe AUserDetailsService
     * <p>
     * Aggiunge le @Route (view) standard
     * Nella sottoclasse concreta che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in FlowVar
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addMenuRoutes() {
        if (FlowVar.usaCronoPackages) {
            FlowVar.menuRouteList.add(Secolo.class);
            FlowVar.menuRouteList.add(Anno.class);
            FlowVar.menuRouteList.add(Mese.class);
            FlowVar.menuRouteList.add(Giorno.class);
        }

        if (FlowVar.usaCompany) {
            FlowVar.menuRouteList.add(Utente.class);
            FlowVar.menuRouteList.add(Company.class);
        }

        FlowVar.menuRouteList.add(Preferenza.class);
        FlowVar.menuRouteList.add(Wizard.class);

        if (FlowVar.usaGeografiaPackages) {
            FlowVar.menuRouteList.add(Continente.class);
            FlowVar.menuRouteList.add(Stato.class);
            FlowVar.menuRouteList.add(Regione.class);
            FlowVar.menuRouteList.add(Provincia.class);
        }

    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public void setMongo(AMongoService mongo) {
        this.mongo = mongo;
    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     *
     * @param flowData the flow data
     */
    @Autowired
    public void setFlowData(FlowData flowData) {
        this.flowData = flowData;
    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     *
     * @param logger the log service
     */
    @Autowired
    public void setLogService(ALogService logger) {
        this.logger = logger;
    }


}
