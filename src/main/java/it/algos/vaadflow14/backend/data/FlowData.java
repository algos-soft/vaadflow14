package it.algos.vaadflow14.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.AIScript;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.enumeration.AECrono;
import it.algos.vaadflow14.backend.enumeration.AEGeografia;
import it.algos.vaadflow14.backend.enumeration.AETypeLog;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.logic.AILogic;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext applicationContext;

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
    public AClassService classService;

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
    public AMongoService mongo;

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
    public AFileService file;

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
     * Nome della collezione su mongoDB <br>
     * Viene regolato dalla sottoclasse nel costruttore <br>
     */
    public String collectionName;

    /**
     * Controlla i flags di due enumerations <br>
     * Esclude i files appartenenti se i rispettivi flag sono falsi <br>
     */
    public Predicate<String> checkFile = canonicalName -> {
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
     * Check iniziale di alcune collections <br>
     * Crea un elenco specifico di collections che implementano il metodo 'reset' nella classe xxxLogic <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     */
    public void fixData() {
        String moduleName = "vaadflow14";
        List<String> listaCanonicalNamesEntity = file.getModuleSubFilesEntity(moduleName);
        List<String> listaEntityEffettivamenteUsate;
        String collezione;

        listaEntityEffettivamenteUsate = checkFiles(listaCanonicalNamesEntity);
        logger.log(AETypeLog.checkData, "Sono state trovate " + listaEntityEffettivamenteUsate.size() + " classi di tipo AEntity da controllare");
        if (array.isAllValid(listaEntityEffettivamenteUsate)) {
            for (String canonicalName : listaEntityEffettivamenteUsate) {
                collezione = file.estraeClasseFinale(canonicalName).toLowerCase();

                if (checkFile(canonicalName)) {
                    checkSingolaCollection(canonicalName);
                }
                else {
                    logger.log(AETypeLog.checkData, "La collezione " + collezione + " non è prevista in questa applicazione");
                }
            }
        }
        logger.log(AETypeLog.checkData, "Controllati i dati iniziali di vaadflow14");
    }

    /**
     * Controlla i flags di due enumerations <br>
     * Esclude i files appartenenti se i rispettivi flag sono falsi <br>
     */
    protected List<String> checkFiles(List<String> listaCanonicalNameEntity) {

       return listaCanonicalNameEntity.stream()
                .filter(checkFile)
                .collect(Collectors.toList());
    }

    /**
     * Controlla i flags di due enumerations <br>
     * Esclude i files appartenenti se i rispettivi flag sono falsi <br>
     */
    protected boolean checkFile(String canonicalName) {
        String name = file.estraeClasseFinale(canonicalName).toLowerCase();

        if (!FlowVar.usaCronoPackages && AECrono.getValue().contains(name)) {
            return false;
        }
        if (!FlowVar.usaGeografiaPackages && AEGeografia.getValue().contains(name)) {
            return false;
        }

        return true;
    }

    /**
     * Controllo se la collection è vuota. Se esiste, non faccio nulla. <br>
     * Costruisco un' istanza della classe xxxLogic corrispondente alla entityClazz <br>
     * Controllo se l' istanza xxxLogic è creabile <br>
     * Utilizzo il metodo standard 'reset', presente nell' interfaccia <br>
     */
    protected boolean checkSingolaCollection(String canonicalEntityName) {
        AIResult result;
        String entityLogicPrevista = file.estraeClasseFinale(canonicalEntityName) + SUFFIX_BUSINESS_LOGIC;
        AILogic entityLogic = classService.getLogicFromEntityName(canonicalEntityName);
        String nameLogic = entityLogic.getClass().getSimpleName();
        boolean methodExists = false;

        if (entityLogic == null || nameLogic.equals(TAG_GENERIC_LOGIC)) {
            logger.log(AETypeLog.checkData, "Non esiste la classe " + entityLogicPrevista + " per effettuare il Reset dei dati");
            return false;
        }

        try {
            methodExists = entityLogic.getClass().getDeclaredMethod("resetEmptyOnly") != null;
        } catch (Exception unErrore) {
        }

        if (methodExists) {
            result = entityLogic.resetEmptyOnly();
            logger.log(AETypeLog.checkData, result.getMessage());

            return result.isValido();
        }
        else {
            logger.log(AETypeLog.checkData, "Non esiste il metodo resetEmptyOnly() nella classe " + nameLogic);
            return false;
        }
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

}
