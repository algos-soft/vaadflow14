package it.algos.vaadflow14.backend.application;

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
 * Executed on container startup, before any browse command <br>
 * Running logic after the Spring context has been initialized
 * La sottoclasse concreta di ogni applicazione usa le API di ServletContextListener e riceve un @EventListener <br>
 * (una volta sola) che rimanda al metodo onApplicationEvent() di questa classe <br>
 * <p>
 * Not annotated with @SpringComponent (sbagliato) <br>
 * Not annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (sbagliato) <br>
 * <p>
 * Viene normalmente creata una sottoclasse per l'applicazione specifica:
 * - per regolare eventualmente alcuni flag in maniera non standard
 * - lanciare gli schedulers in background <br>
 * - costruire e regolare una versione demo <br>
 * - controllare l'esistenza di utenti abilitati all'accesso <br>
 * <p>
 * Stampa a video (productionMode) i valori per controllo
 */
public class FlowBoot implements ServletContextListener {

//    @Autowired
//    ALogService logService;
//@todo Funzionalità ancora da implementare


    /**
     * Utilizzato per:
     * - registrare nella xxxApp, il servlet context non appena è disponibile
     * - regolare alcuni flag dell'applicazione, uguali e validi per tutte le sessioni e tutte le request <br>
     * - lanciare gli schedulers in background <br>
     * - costruire e regolare una versione demo <br>
     * - controllare l'esistenza di utenti abilitati all'accesso <br>
     *
     * @param event the event
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.iniziaDBMongo();
        //        this.iniziaVersioni();
        this.regolaRiferimenti();
        this.regolaApplicationProperties();

        //@todo Funzionalità ancora da implementare
//        logService.startup();
        //@todo Funzionalità ancora da implementare

        //        this.creaPreferenze();
        //        this.fixPreferenze();
        //                this.iniziaDataStandard();
        //        this.iniziaDataProgettoSpecifico();
        this.addMenuRoutes();
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
     * Riferimento alla sottoclasse specifica di ABoot per utilizzare il metodo sovrascritto resetPreferenze() <br>
     * Deve essere sovrascritto nella sottoclasse specifica <br>
     */
    protected void regolaRiferimenti() {
    }


    /**
     * Regola alcune variabili generali dell'applicazione al loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l'applicazione ma il loro valore può essere modificato <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void regolaApplicationProperties() {

        /*
         * Controlla se l'applicazione gira in 'debug mode' oppure no <br>
         * Di default (per sicurezza) uguale a true <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.usaDebug = true;

        /*
         * Controlla se l'applicazione usa il login oppure no <br>
         * Se si usa il login, occorre la classe SecurityConfiguration <br>
         * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
         * Di default (per sicurezza) uguale a true <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.usaSecurity = true;


        /*
         * Controlla se l'applicazione è multi-company oppure no <br>
         * Di default (per sicurezza) uguale a true <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.usaCompany = true;

        /*
         * Nome identificativo dell'applicazione <br>
         * Usato (eventualmente) nella barra di menu in testa pagina <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.projectName = VUOTA;


        /*
         * Descrizione completa dell'applicazione <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.projectDescrizione = VUOTA;


        /*
         * Versione dell'applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.projectVersion = 0.1;

        /*
         *
         * Data della versione dell'applicazione <br>
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
         * Path per recuperare dalle risorse un'immagine da inserire nella barra di menu di MainLayout. <br>
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
     * Aggiunge le @Route (view) specifiche di questa applicazione. <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in FlowVar <br>
     * Vengono aggiunte dopo quelle standard <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addMenuRoutes() {
        //@todo Funzionalità ancora da implementare
    }

}
