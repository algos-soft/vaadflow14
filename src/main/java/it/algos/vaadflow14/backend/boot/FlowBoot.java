package it.algos.vaadflow14.backend.boot;

import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.data.AData;
import it.algos.vaadflow14.backend.data.FlowData;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.packages.crono.giorno.Giorno;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import it.algos.vaadflow14.backend.service.ALogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

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

    //    @Autowired
    //    ALogService logService;
    //@todo Funzionalità ancora da implementare

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public FlowData flowData;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ALogService logService;


    //--riferimento alla sottoclasse di AData da usare per inizializzare i dati
    protected AData aData;


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
        this.iniziaDBMongo();
        this.iniziaVersioni();
        this.regolaRiferimenti();
        this.iniziaDataPreliminari();
        this.creaPreferenze();
        this.fixPreferenze();
        this.inizializzaData();
        this.regolaApplicationProperties();
        this.addMenuRoutes();
        logService.startup();
    }


    /**
     * Inizializzazione di alcuni parametri del database mongoDB <br>
     */
    protected void iniziaDBMongo() {
        //        mongo.getMaxBlockingSortBytes();
        //        mongo.fixMaxBytes();
        //        mongo.getMaxBlockingSortBytes();
    }


    /**
     * //@todo Forse non serve
     * Inizializzazione delle versioni standard di vaadinFlow <br>
     * Inizializzazione delle versioni del programma specifico <br>
     */
    @Deprecated
    protected void iniziaVersioni() {
    }


    /**
     * Riferimento alla sottoclasse specifica di ABoot per utilizzare il metodo sovrascritto resetPreferenze() <br>
     * DEVE essere sovrascritto <br>
     */
    protected void regolaRiferimenti() {
        // Riferimento alla sottoclasse specifica di ABoot per utilizzare il metodo sovrascritto resetPreferenze()
        //        preferenzaService.applicationBoot = this;
    }


    /**
     * Inizializzazione dei dati di alcune collections essenziali per la partenza <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void iniziaDataPreliminari() {
    }


    /**
     * Crea le preferenze standard <br>
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
     * Eventuali regolazioni delle preferenze standard effettuata nella sottoclasse specifica <br>
     * Serve per modificare solo per l'applicazione specifica il valore standard della preferenza <br>
     * Eventuali modifiche delle preferenze specifiche (che peraltro possono essere modificate all'origine) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
    }


    /**
     * Inizializzazione dei dati di alcune collections sul DB mongo <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void inizializzaData() {
        flowData.initData();
    }


    /**
     * Regola alcune variabili generali dell' applicazione al loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione ma il loro valore può essere modificato <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void regolaApplicationProperties() {

        /*
         * Controlla se l' applicazione gira in 'debug mode' oppure no <br>
         * Di default (per sicurezza) uguale a true <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.usaDebug = true;

        /*
         * Controlla se l' applicazione usa il login oppure no <br>
         * Se si usa il login, occorre la classe SecurityConfiguration <br>
         * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
         * Di default (per sicurezza) uguale a true <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.usaSecurity = true;


        /*
         * Controlla se l' applicazione è multi-company oppure no <br>
         * Di default (per sicurezza) uguale a true <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.usaCompany = true;

        /*
         * Nome identificativo dell' applicazione <br>
         * Usato (eventualmente) nella barra di menu in testa pagina <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.projectName = VUOTA;


        /*
         * Descrizione completa dell' applicazione <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.projectDescrizione = VUOTA;


        /*
         * Versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.projectVersion = 0.1;

        /*
         *
         * Data della versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.versionDate = START_DATE;

        /*
         * Eventuali informazioni aggiuntive da utilizzare nelle informazioni <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.projectNote = VUOTA;

        /*
         * Flag per usare le icone VaadinIcon <br>
         * In alternativa usa le icone 'lumo' <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'backend.application' <br>
         */
        FlowVar.usaVaadinIcon = true;


        /*
         * Eventuali titolo della pagina <br>
         */
        FlowVar.layoutTitle = VUOTA;


        /*
         * Nome da usare per recuperare la lista delle Company (o sottoclassi) <br>
         * Di default 'company' oppure eventuale sottoclasse specializzata per Company particolari <br>
         * Eventuale casting a carico del chiamante <br>
         * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.companyClazzName = "company";


        /*
         * Path per recuperare dalle risorse un' immagine da inserire nella barra di menu di MainLayout. <br>
         * Ogni applicazione può modificarla <br>
         * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.pathLogo = "img/medal.ico";


        /**
         * Lista dei moduli di menu da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
         * Regolata dall'applicazione durante l'esecuzione del 'container startup' (non-UI logic) <br>
         * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
         */
        FlowVar.menuRouteList = new ArrayList<>();
    }


    /**
     * Questa classe viene invocata PRIMA della chiamata del browser
     * Se NON usa la security, le @Route vengono create solo qui
     * Se USA la security, le @Route vengono sovrascritte all' apertura del brose nella classe AUserDetailsService
     * <p>
     * Aggiunge le @Route (view) standard
     * Nella sottoclasse concreta che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in FlowVar
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addMenuRoutes() {
        FlowVar.menuRouteList.add(Secolo.class);
        FlowVar.menuRouteList.add(Anno.class);
        FlowVar.menuRouteList.add(Mese.class);
        FlowVar.menuRouteList.add(Giorno.class);
    }


}
