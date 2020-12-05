package it.algos.vaadflow14.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.AIScript;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.enumeration.AECrono;
import it.algos.vaadflow14.backend.enumeration.AEGeografia;
import it.algos.vaadflow14.backend.enumeration.AETypeLog;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.logic.AILogic;
import it.algos.vaadflow14.backend.service.AClassService;
import it.algos.vaadflow14.backend.service.AFileService;
import it.algos.vaadflow14.backend.service.ALogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static it.algos.vaadflow14.backend.application.FlowCost.SUFFIX_BUSINESS_LOGIC;
import static it.algos.vaadflow14.backend.application.FlowCost.TAG_GENERIC_LOGIC;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 20-ott-2018
 * Time: 08:53
 * <p>
 * Poiché siamo in fase di boot, la sessione non esiste ancora <br>
 * Questo vuol dire che eventuali classi @VaadinSessionScope
 * NON possono essere iniettate automaticamente da Spring <br>
 * Vengono costruite con la BeanFactory <br>
 * <p>
 * Superclasse astratta per la costruzione iniziale delle Collections <br>
 * Viene invocata PRIMA della chiamata del browser, tramite un metodo @PostConstruct della sottoclasse <br>
 * Non si possono quindi usare i service specifici dei package che sono @VaadinSessionScope <br>
 * Viceversa le repository specifiche dei package sono delle interfacce e pertanto vengono 'create' al volo <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio per le injections) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AIScript(sovraScrivibile = false)
public class FlowData implements AIData {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    protected AFileService file;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    protected AClassService classService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    protected ALogService logger;


    /**
     * Alcune entities possono non essere usate direttamente nel programma <br>
     * Nella costruzione del menu FlowBoot.fixMenuRoutes() due flags regolano <br>
     * quali entities facoltative mostrare nel menu <br>
     * <p>
     * Controlla il flag di ogni entity tra quelle facoltative <br>
     * Accetta solo quelle col flag positivo <br>
     */
    protected Predicate<String> checkEntity = canonicalName -> {
        String name = file.estraeClasseFinale(canonicalName).toLowerCase();
        if (!FlowVar.usaCronoPackages && AECrono.getValue().contains(name)) {
            return false;
        }
        if (!FlowVar.usaGeografiaPackages && AEGeografia.getValue().contains(name)) {
            return false;
        }
        return true;
    };

    /**
     * Controllo la singola collezione <br>
     * <p>
     * Costruisco un' istanza della classe xxxLogic corrispondente alla entityClazz <br>
     * Controllo se l' istanza xxxLogic è creabile <br>
     * Controllo se esiste un metodo resetEmptyOnly() nella classe xxxLogic specifica <br>
     * Invoco il metodo standard resetEmptyOnly(), presente nell' interfaccia <br>
     */
    protected Consumer<String> resetEmptyOnly = canonicalEntityName -> {
        final AIResult result;
        final String entityLogicPrevista = file.estraeClasseFinale(canonicalEntityName) + SUFFIX_BUSINESS_LOGIC;
        final AILogic entityLogic = classService.getLogicFromEntityName(canonicalEntityName);
        final String nameLogic = entityLogic.getClass().getSimpleName();
        boolean methodExists = false;
        String message;

        if (entityLogic == null || nameLogic.equals(TAG_GENERIC_LOGIC)) {
            message = String.format("Non esiste la classe %s per effettuare il Reset dei dati", entityLogicPrevista);
            logger.log(AETypeLog.checkData, message);
        }

        try {
            methodExists = entityLogic.getClass().getDeclaredMethod("resetEmptyOnly") != null;
        } catch (Exception unErrore) {
        }

        if (methodExists) {
            result = entityLogic.resetEmptyOnly();
            logger.log(AETypeLog.checkData, result.getMessage());
        }
        else {
            message = String.format("Non esiste il metodo resetEmptyOnly() nella classe %s", nameLogic);
            logger.log(AETypeLog.checkData, message);
        }
    };

    /**
     * Alcune entities possono non essere usate direttamente nel programma <br>
     * Nella costruzione del menu FlowBoot.fixMenuRoutes() due flags regolano <br>
     * quali entities facoltative mostrare nel menu <br>
     * <p>
     * Controlla i flags di tutte le entities <br>
     * Accetta solo quelle col flag positivo <br>
     *
     * @since java 8
     */
    protected Function<List<String>, Long> checkFiles = listaCanonicalNamesAllEntity -> {
        return listaCanonicalNamesAllEntity.stream()
                .filter(checkEntity)
                .count();
    };

    /**
     * Constructor with @Autowired on setter. Usato quando ci sono sottoclassi. <br>
     * Per evitare di avere nel costruttore tutte le property che devono essere iniettate e per poterle aumentare <br>
     * senza dover modificare i costruttori delle sottoclassi, l'iniezione tramite @Autowired <br>
     * viene delegata ad alcuni metodi setter() che vengono qui invocati con valore (ancora) nullo. <br>
     * Al termine del ciclo init() del costruttore il framework SpringBoot/Vaadin, inietterà la relativa istanza <br>
     */
    public FlowData() {
        this.setFile(file);
        this.setClassService(classService);
        this.setLogger(logger);
    }// end of constructor with @Autowired on setter

    /**
     * Check iniziale di alcune collections <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Vengono create se mancano e se esiste un metodo resetEmptyOnly() nella classe xxxLogic specifica <br>
     * Crea un elenco di entities/collections che implementano il metodo resetEmptyOnly() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @since java 8
     */
    public void fixData() {
        String moduleName = "vaadflow14";
        List<String> allEntities;
        long entities;
        String message;

        //--spazzola tutta la directory packages
        allEntities = file.getModuleSubFilesEntity(moduleName);

        //--conta le collections valide
        entities = checkFiles.apply(allEntities);
        message = String.format("Sono state trovate %d classi di tipo AEntity da controllare", entities);
        logger.log(AETypeLog.checkData, message);

        //--elabora le collections valide
        allEntities.stream()
                .filter(checkEntity)
                .forEach(resetEmptyOnly);
        message = "Controllati i dati iniziali di vaadflow14";
        logger.log(AETypeLog.checkData, message);
    }


    /**
     * Crea le preferenze standard dell'applicazione <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Per un reset ai valori di default, c'è il metodo reset() chiamato da preferenzaLogic <br>
     */
    public void fixPreferenze() {
    }


    public void fixLog() {
        logger.error("Non ho trovato nessuna classe xxxData", this.getClass(), "fixLog");
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setFile(AFileService file) {
        this.file = file;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setClassService(AClassService classService) {
        this.classService = classService;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setLogger(ALogService logger) {
        this.logger = logger;
    }

}
