package it.algos.vaadflow14.backend.data;

import it.algos.vaadflow14.backend.enumeration.AETypeLog;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.logic.AILogic;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static it.algos.vaadflow14.backend.application.FlowCost.SUFFIX_BUSINESS_LOGIC;
import static it.algos.vaadflow14.backend.application.FlowCost.TAG_GENERIC_LOGIC;

/**
 * Project vbase
 * Created by Algos
 * User: gac
 * Date: lun, 19-mar-2018
 * Time: 21:10
 * <p>
 * Superclasse astratta per la costruzione iniziale delle Collections <br>
 * Viene invocata PRIMA della chiamata del browser, tramite un metodo @PostConstruct della sottoclasse <br>
 * Non si possono quindi usare i service specifici dei package che sono @VaadinSessionScope <br>
 * Viceversa le repository specifiche dei package sono delle interfacce e pertanto vengono 'create' al volo <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio per le injections) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 */
//@SpringComponent
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//@Slf4j
public abstract class AData {


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
     * Nome della collezione su mongoDB <br>
     * Viene regolato dalla sottoclasse nel costruttore <br>
     */
    protected String collectionName;

    public void fixData() {
    }
    public void fixLog() {
        logger.error("Non ho trovato nessuna classe xxxData", this.getClass(), "fixLog");

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

}
