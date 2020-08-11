package it.algos.vaadflow14.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.entity.AILogic;
import it.algos.vaadflow14.backend.entity.GenericLogic;
import it.algos.vaadflow14.backend.enumeration.AETypeLog;
import it.algos.vaadflow14.backend.service.AAnnotationService;
import it.algos.vaadflow14.backend.service.AClassService;
import it.algos.vaadflow14.backend.service.ALogService;
import it.algos.vaadflow14.backend.service.AMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

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
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public abstract class AData  {


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
     * Nome della collezione su mongoDB <br>
     * Viene regolato dalla sottoclasse nel costruttore <br>
     */
    protected String collectionName;

    /**
     * Flag per usare i log di sistema. Normalmente true <br>
     */
    protected boolean usaLogger = false; //@todo Creare una preferenza e sostituirla qui



    /**
     * Controllo se la collection è vuota. Se esiste, non faccio nulla. <br>
     * Costruisco un' istanza della classe xxxLogic corrispondente alla entityClazz <br>
     * Controllo se l' istanza xxxLogic è creabile <br>
     * Utilizzo il metodo standard 'reset', presente nell' interfaccia <br>
     *
     * @param entityClazz modello-dati specifico di un package
     */
    protected void checkSingolaCollection(Class<? extends AEntity> entityClazz) {
        AILogic service = classService.getLogicFromEntity(entityClazz);
        String collectionName = annotation.getCollectionName(entityClazz);

        if (service != null && !(service instanceof GenericLogic)) {
            if (mongo.isEmpty(entityClazz)) {
                if (service.reset()) {
                    logDataCreata(collectionName);
                } else {
                    logger.error("Non sono riuscito a trovare  il metodo 'reset' e a creare la collezione " + collectionName, this.getClass(), "checkSingolaCollection");
                }
            } else {
                logDataPresente(collectionName);
            }
        } else {
            logger.error("Non esiste la classe " + entityClazz.getSimpleName() + "Logic", this.getClass(), "checkSingolaCollection");
        }
    }


    /**
     * Semplice log di avviso <br>
     * Controllato da flag/preferenza <br>
     */
    protected void logDataCreata(String collectionName) {
        if (usaLogger) {
            logger.log(AETypeLog.checkData, "La collezione " + collectionName + " mancava ed è stata completamente riscritta");
        }
    }


    /**
     * Semplice log di avviso <br>
     * Controllato da flag/preferenza <br>
     */
    protected void logDataPresente(String collectionName) {
        if (usaLogger) {
            logger.log(AETypeLog.checkData, "La collezione " + collectionName + " è presente");
        }
    }


}
