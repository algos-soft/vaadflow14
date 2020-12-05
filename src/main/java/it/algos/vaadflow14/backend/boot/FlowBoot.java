package it.algos.vaadflow14.backend.boot;

import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.data.AIData;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

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
 * Any class that use this @EventListener annotation, will be executed
 * before the application is up and its onContextRefreshEvent method will be called
 * The method onApplicationEvent() will be executed nella sottoclasse before
 * the application is up and <br>
 * <p>
 * Questa classe è astratta e NON ricevere un @EventListener, che viene gestito dalla indispensabile sottoclasse concreta <br>
 * La sottoclasse concreta di ogni applicazione usa le API di ServletContextListener e riceve un @EventListener <br>
 * (una volta sola) che rimanda al metodo onContextRefreshEvent() di questa classe astratta <br>
 * <p>
 * Not annotated with @SpringComponent (sbagliato, SpringBoot crea la sottoclasse concreta) <br>
 * Not annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (sbagliato) <br>
 * <p>
 * Deve essere creata una sottoclasse concreta per l' applicazione specifica che: <br>
 * 1) regola alcuni parametri standard del database MongoDB <br>
 * 2) regola le variabili generali dell'applicazione <br>
 * 3) crea i dati di alcune collections sul DB mongo <br>
 * 4) crea le preferenze standard e specifiche dell'applicazione <br>
 * 5) aggiunge le @Route (view) standard e specifiche <br>
 * 6) lancia gli schedulers in background <br>
 * 7) costruisce una versione demo <br>
 * 8) controllare l' esistenza di utenti abilitati all' accesso <br>
 */
public abstract class FlowBoot implements ServletContextListener {

    /**
     * Azione implementata nel metodo della classe specifica <br>
     *
     * @since java 8
     */
    public static Consumer<AIData> fixData = AIData::fixData;

    /**
     * Azione implementata nel metodo della classe specifica <br>
     *
     * @since java 8
     */
    public static Consumer<AIData> fixPreferenze = AIData::fixPreferenze;

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    public Environment environment;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    public AMongoService mongo;

    /**
     * Messaggio di errore <br>
     *
     * @since java 8
     */
    public Runnable mancaDataClazz = () -> System.out.println("Non ho trovato la classe xxxData");


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    public ALogService logger;


    /**
     * Constructor with @Autowired on setter. Usato quando ci sono sottoclassi. <br>
     * Per evitare di avere nel costruttore tutte le property che devono essere iniettate e per poterle aumentare <br>
     * senza dover modificare i costruttori delle sottoclassi, l'iniezione tramite @Autowired <br>
     * viene delegata ad alcuni metodi setter() che vengono qui invocati con valore (ancora) nullo. <br>
     * Al termine del ciclo init() del costruttore il framework SpringBoot/Vaadin, inietterà la relativa istanza <br>
     */
    public FlowBoot() {
        this.setEnvironment(environment);
        this.setMongo(mongo);
        this.setLogger(logger);
    }// end of constructor with @Autowired on setter


    /**
     * Primo ingresso nel programma <br>
     * <p>
     * registrare nella xxxApp, il servlet context non appena è disponibile @todo forse
     * <p>
     * 1) regola alcuni parametri standard del database MongoDB <br>
     * 2) regola le variabili generali dell'applicazione <br>
     * 3) crea le preferenze standard e specifiche dell'applicazione <br>
     * 4) crea i dati di alcune collections sul DB mongo <br>
     * 5) aggiunge al menu le @Route (view) standard e specifiche <br>
     * 6) lancia gli schedulers in background <br>
     * 7) costruisce una versione demo <br>
     * 8) controllare l' esistenza di utenti abilitati all' accesso <br>
     * <p>
     * Metodo privato perché non può essere sovrascritto <br>
     */
    @EventListener(ContextRefreshedEvent.class)
    private void onContextRefreshEvent() {
        logger.startupIni();

        this.fixDBMongo();
        this.fixVariabili();
        this.fixData();
        this.fixPreferenze();
        this.fixMenuRoutes();
        this.fixSchedules();
        this.fixDemo();
        this.fixUsers();

        logger.startupEnd();
    }


    /**
     * Regola alcuni parametri standard del database MongoDB <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixDBMongo() {
        mongo.getMaxBlockingSortBytes();
        mongo.fixMaxBytes();
    }


    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixVariabili() {

        /**
         * Controlla se l' applicazione gira in 'debug mode' oppure no <br>
         * Di default (per sicurezza) uguale a true <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.usaDebug = true;

        /**
         * Controlla se l' applicazione è multi-company oppure no <br>
         * Di default (per sicurezza) uguale a true <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         */
        FlowVar.usaCompany = true;

        /**
         * Controlla se l' applicazione usa il login oppure no <br>
         * Se si usa il login, occorre la classe SecurityConfiguration <br>
         * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
         * Di default (per sicurezza) uguale a true <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         * Può essere true anche se usaCompany=false <br>
         */
        FlowVar.usaSecurity = true;

        /**
         * Nome identificativo dell' applicazione <br>
         * Usato (eventualmente) nella barra di menu in testa pagina <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.projectName = VUOTA;

        /**
         * Descrizione completa dell' applicazione <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.projectDescrizione = VUOTA;

        /**
         * Versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.projectVersion = Double.parseDouble(environment.getProperty("algos.vaadflow.version"));

        /**
         * Data della versione dell' applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.versionDate = START_DATE;

        /**
         * Eventuali informazioni aggiuntive da utilizzare nelle informazioni <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.projectNote = VUOTA;

        /**
         * Flag per usare le icone VaadinIcon <br>
         * In alternativa usa le icone 'lumo' <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.usaVaadinIcon = true;

        /**
         * Classe da usare per lo startup del programma <br>
         * Di default FlowBoot oppure probabile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.bootClazz = FlowBoot.class;

        /**
         * Classe da usare per lo startup del programma <br>
         * Di default FlowData oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.dataClazz = FlowData.class;

        /**
         * Classe da usare per le Company (o sottoclassi) <br>
         * Di default 'company' oppure eventuale sottoclasse specializzata per Company particolari <br>
         * Eventuale casting a carico del chiamante <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.companyClazz = Company.class;

        /**
         * Path per recuperare dalle risorse un' immagine da inserire nella barra di menu di MainLayout. <br>
         * Ogni applicazione può modificarla <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
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
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.usaCronoPackages = false;

        /**
         * Mostra i quattro packages geografici (stato, regione, provincia, comune) <br>
         * Di default (per sicurezza) uguale a false <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        FlowVar.usaGeografiaPackages = false;
    }

    /**
     * Primo ingresso nel programma nella classe concreta, tramite il <br>
     * metodo FlowBoot.onContextRefreshEvent() della superclasse astratta <br>
     * Crea i dati di alcune collections sul DB mongo <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * <p>
     * Invoca il metodo fixData() di FlowData oppure della sottoclasse <br>
     *
     * @since java 8
     */
    protected void fixData() {
        Optional<AIData> opt = Optional.ofNullable((AIData) appContext.getBean(FlowVar.dataClazz));
        opt.ifPresentOrElse(fixPreferenze, mancaDataClazz);
    }


    /**
     * Crea le preferenze standard e specifiche dell'applicazione <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Per un reset ai valori di default, c'è il metodo reset() chiamato da preferenzaLogic <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @since java 8
     */
    protected void fixPreferenze() {
        Optional<AIData> opt = Optional.ofNullable((AIData) appContext.getBean(FlowVar.dataClazz));
        opt.ifPresentOrElse(fixPreferenze, mancaDataClazz);
    }


    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * <p>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * Se NON usa la security, le @Route vengono create solo qui <br>
     * Se USA la security, le @Route vengono sovrascritte all' apertura del browser nella classe AUserDetailsService <br>
     * <p>
     * Nella sottoclasse concreta che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in FlowVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixMenuRoutes() {
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
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixSchedules() {
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixDemo() {
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixUsers() {
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
     * @param logger the log service
     */
    @Autowired
    public void setLogger(ALogService logger) {
        this.logger = logger;
    }


}
