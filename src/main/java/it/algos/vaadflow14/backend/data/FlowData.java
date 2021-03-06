package it.algos.vaadflow14.backend.data;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.packages.preferenza.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;
import java.util.function.*;

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
 * Viene invocata PRIMA della chiamata del browser, tramite il <br>
 * metodo FlowBoot.onContextRefreshEvent() <br>
 * Crea i dati di alcune collections sul DB mongo <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
 *
 * @since java 8
 */
@SpringComponent
@Qualifier(TAG_FLOW_DATA)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AIScript(sovraScrivibile = false)
public class FlowData implements AIData {

    /**
     * Messaggio di errore <br>
     *
     * @since java 8
     */
    public Runnable mancaPrefLogic = () -> System.out.println("Non ho trovato la classe PreferenzaLogic");

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
     * Le preferenze vengono gestite a parte in fixPreferenze() <br>
     */
    protected Predicate<String> checkEntity = canonicalName -> {
        String name = file.estraeClasseFinale(canonicalName).toLowerCase();

        if (name.equals(Preferenza.class.getSimpleName().toLowerCase())) {
            return false;
        }

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
     * Costruisco un' istanza della classe xxxService corrispondente alla entityClazz <br>
     * Controllo se l' istanza xxxService è creabile <br>
     * Un package standard contiene sia xxxService che xxxLogic <br>
     * Controllo se esiste un metodo resetEmptyOnly() nella classe xxxService specifica <br>
     * Invoco il metodo API resetEmptyOnly() della interfaccia AIService <br>
     */
    protected Consumer<String> resetEmptyOnly = canonicalEntityName -> {
        final AIResult result;
        final String entityServicePrevista = file.estraeClasseFinale(canonicalEntityName) + SUFFIX_ENTITY_SERVICE;
        final AIService entityService = classService.getServiceFromEntityName(canonicalEntityName);
        final String nameService;
        final String packageName = file.estraeClasseFinale(canonicalEntityName).toLowerCase();
        boolean methodExists = false;
        String message;

        if (entityService == null) {
            logger.error(String.format("Nel package %s manca la entityService specifica e non sono neanche riuscito a creare la EntityService generica", packageName));
            return;
        }

        nameService = entityService.getClass().getSimpleName();
        if (nameService.equals(TAG_GENERIC_SERVICE)) {
            message = String.format("Nel package %s non esiste la classe %s e usa EntityService. Non esiste il metodo resetEmptyOnly()", packageName, entityServicePrevista);
            logger.log(AETypeLog.checkData, message);
        }

        try {
            methodExists = !packageName.equals(TAG_GENERIC_SERVICE) && entityService.getClass().getDeclaredMethod("resetEmptyOnly") != null;
        } catch (Exception unErrore) {
        }

        if (methodExists) {
            result = entityService.resetEmptyOnly();
            logger.log(AETypeLog.checkData, result.getMessage());
        }
        else {
            if (!nameService.equals(TAG_GENERIC_SERVICE)) {
                message = String.format("Nel package %s la classe %s non ha il metodo resetEmptyOnly() ", packageName,entityServicePrevista);
                logger.log(AETypeLog.checkData, message);
            }
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
        this.fixData("vaadflow14");
    }


    /**
     * Check iniziale di alcune collections <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Vengono create se mancano e se esiste un metodo resetEmptyOnly() nella classe xxxLogic specifica <br>
     * Crea un elenco di entities/collections che implementano il metodo resetEmptyOnly() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @param moduleName da controllare
     *
     * @since java 8
     */
    protected void fixData(final String moduleName) {
        List<String> allEntities;
        long entities;
        String message;

        //--spazzola tutta la directory packages
        allEntities = file.getModuleSubFilesEntity(moduleName);

        //--conta le collections valide
        entities = checkFiles.apply(allEntities);
        message = String.format("In %s sono stati trovati %d packages con classi di tipo AEntity da controllare", moduleName, entities);
        logger.log(AETypeLog.checkData, message);

        //--elabora le collections valide
        allEntities.stream()
                .filter(checkEntity)
                .forEach(resetEmptyOnly);
        message = String.format("Controllati i dati iniziali di %s", moduleName);
        logger.log(AETypeLog.checkData, message);
    }



//    /**
//     * Ricostruisce le preferenze standard dell'applicazione <br>
//     * Se non esistono, le crea <br>
//     * Se esistono, NON modifica i valori esistenti <br>
//     * <p>
//     *
//     * @param isReset true: invocato da xxxLogic.resetEmptyOnly(), con click sul bottone Reset di PreferenzaList
//     *                false: invocato da xxxData.fixPreferenze(), in fase di Startup <br>
//     *                <br>
//     */
//    @Override
//    public AIResult resetPreferenze(final PreferenzaService preferenzaService, final boolean isReset) {
//        AIResult result;
//        int numRec = 0;
//
//        if (Preferenza.class == null) {
//            return AResult.errato("Manca la entityClazz nella businessLogic specifica");
//        }
//
//        //-- standard (obbligatorie) di Vaadflow14, prese dalla enumeration AEPreferenza
//        for (AIPreferenza aePref : AEPreferenza.values()) {
//            numRec = preferenzaService.creaIfNotExist(aePref) != null ? numRec + 1 : numRec;
//        }
//
//        if (numRec == 0) {
//            result = AResult.valido("Non ci sono nuove preferenze generali da aggiungere.");
//        }
//        else {
//            if (numRec == 1) {
//                result = AResult.valido("Mancava una preferenza generale che è stata aggiunta senza modificare i valori di quelle esistenti");
//            }
//            else {
//                result = AResult.valido("Mancavano " + numRec + " preferenze generali che sono state aggiunte senza modificare i valori di quelle esistenti");
//            }
//        }
//
//        logger.log(isReset ? AETypeLog.reset : AETypeLog.checkData, result.getMessage());
//        return result;
//
//    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setFile(final AFileService file) {
        this.file = file;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setClassService(final AClassService classService) {
        this.classService = classService;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setLogger(final ALogService logger) {
        this.logger = logger;
    }

}
