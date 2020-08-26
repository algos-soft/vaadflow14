package it.algos.vaadflow14.backend.service;

import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AELogLivello;
import it.algos.vaadflow14.backend.enumeration.AETypeLog;
import it.algos.vaadflow14.backend.enumeration.AETime;
import it.algos.vaadflow14.backend.enumeration.AILogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static it.algos.vaadflow14.backend.service.AConsoleColorService.RESET;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: dom, 03-mag-2020
 * Time: 09:22
 * <p>
 * Classe di servizio per i log. <br>
 * <p>
 * Diverse modalità di 'uscita' dei logs, regolate da due flags: <br>
 * A) nella finestra di output del terminale (sempre) <br>
 * B) nella cartella di log (sempre) <br>
 * C) nella collection del database (facoltativo) <br>
 * D) in una mail (facoltativo e di norma solo per 'error') <br>
 * <p>
 * Nel log, incolonnare la data, alcuni campi fissi (di larghezza) e poi la descrizione libera <br>
 * A) Se è una multi-company con security, i campi fissi sono: la company, l' utente, l'IP ed il type <br>
 * B) Se l' applicazione non è multi-company e non ha security, l' unico campo fisso è il type <br>
 * <p>
 * Nella mail, invece di incolonnare i campi fissi, si va a capo <br>
 * <p>
 * I type di log vengono presi da una enum <br>
 * Il progetto specifico può aggiungere dei types presi da una propria enum <br>
 * Le enum implementano un' interfaccia comune, per poter essere intercambiabili <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ALogService extends AAbstractService {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ABeanService beanService;

    /**
     * Riferimento al logger usato <br>
     * È nella directory 'config', il file 'logback-spring.xml' <br>
     * Deve essere creato subito dalla factory class LoggerFactory <br>
     * Va selezionato un appender da usare e che sia presente nel file di configurazione <br>
     */
    public Logger adminLogger;

    /**
     * Flag per abilitare la registrazione del log anche nel database mongo <br>
     */
    private boolean usaDatabase;

    /**
     * Flag per abilitare la spedizione di una mail <br>
     * Di solito solo nel caso di un messaggio di errore <br>
     */
    private boolean usaMail;

    /**
     * Flag per mostrare alcuni campi tipici delle applicazioni multi company con security <br>
     * Company, utente e IP <br>
     */
    private boolean isMultiCompanyAndSecured;


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        adminLogger = LoggerFactory.getLogger("wam.admin");

        this.usaDatabase = false;
        this.usaMail = false;
        this.isMultiCompanyAndSecured = false;
    }


    /**
     * Logger specifico <br>
     * Gestisce un messaggio alla partenza del programma <br>
     */
    public void startup() {
        String message = VUOTA;
        message += FlowVar.projectName;
        message += SEP;
        message += FlowVar.projectDescrizione;
        message += SEP;
        message += "Versione ";
        message += FlowVar.projectVersion;
        message += " del ";
        message += date.get(FlowVar.versionDate, AETime.normal.getPattern());

        info(AETypeLog.startup, message);
    }


    /**
     * Logger specifico <br>
     * Creazione di una nuova entity salvata nel database mongo <br>
     *
     * @param entityBean appena creata
     */
    public void nuovo(AEntity entityBean) {
        String message = VUOTA;

        if (entityBean != null) {
            message = beanService.getModifiche(entityBean, null);
            info(AETypeLog.nuovo, message);
        } else {
            error("Non sono riuscito a creare la entity");
        }
    }

    /**
     * Logger specifico <br>
     * Modifica di una entity esistente <br>
     *
     * @param entityBean    da modificare
     */
    public void modifica(AEntity entityBean) {
        modifica(entityBean,mongo.find(entityBean));
    }

    /**
     * Logger specifico <br>
     * Modifica di una entity esistente <br>
     *
     * @param entityBean    da modificare
     * @param entityBeanOld originaria preesistente
     */
    public void modifica(AEntity entityBean, AEntity entityBeanOld) {
        String message = VUOTA;

        if (entityBeanOld == null && entityBean == null) {
            error("Non sono riuscito a modificare la entity");
            return;
        }

        if (entityBeanOld == null && entityBean == null) {
            error("Non sono riuscito a trovare la entity");
            return;
        }

        if (entityBean == null) {
            error("Non sono riuscito a modificare la entity");
        }

        message += beanService.getModifiche(entityBean, entityBeanOld);
        esegue(AETypeLog.modifica, message);
    }


    /**
     * Logger specifico <br>
     * Cancellazione di una entity salvata nel database mongo <br>
     *
     * @param entityBean da cancellare
     */
    public void delete(AEntity entityBean) {
        String message = VUOTA;

        if (entityBean != null) {
            message += entityBean.getClass().getSimpleName();
            message += SEP;
            message += entityBean.toString();
            info(AETypeLog.delete, message);
        } else {
            error("Non sono riuscito a cancellare la entity");
        }
    }


    /**
     * Logger specifico <br>
     * Cancellazione di una collection e ri-creazione della stessa con dati prefissati <br>
     *
     * @param entityClazz the class of type AEntity
     */
    public void reset(Class<? extends AEntity> entityClazz) {
        String message = VUOTA;

        if (entityClazz != null) {
            message += entityClazz.getSimpleName();
            message += SEP;
            message += "Ricreati i dati originari della collection";
            info(AETypeLog.reset, message);
        } else {
            error("Non trovo la entityClazz", this.getClass(), "reset");
        }
    }


    /**
     * Logger specifico <br>
     * Cancellazione completa di una collection  <br>
     *
     * @param entityClazz the class of type AEntity
     */
    public void deleteAll(Class<? extends AEntity> entityClazz) {
        String message = VUOTA;

        if (entityClazz != null) {
            message += entityClazz.getSimpleName();
            message += SEP;
            message += "Cancellata completamente la collection";
            info(AETypeLog.deleteAll, message);
        } else {
            error("Non trovo la entityClazz", this.getClass(), "reset");
        }
    }


    /**
     * Gestisce un log generico <br>
     * Di default usa il livello 'info' <br>
     *
     * @param message da registrare
     */
    public void log(String message) {
        info(message);
    }


    /**
     * Gestisce un log generico <br>
     * Di default usa il livello 'info' <br>
     *
     * @param message da registrare
     */
    public void log(AILogType type, String message) {
        info(type, message);
    }


    /**
     * Gestisce un log generico <br>
     * Di default usa il livello 'info' <br>
     *
     * @param message da registrare
     */
    public void info(String message) {
        info((AILogType) null, message);
    }


    public void info(AILogType type, String message) {
        String typeTxt;

        typeTxt = type != null ? type.getTag() : AETypeLog.system.getTag();
        typeTxt = text.fixSizeQuadre(typeTxt, 10);

        message = typeTxt + DOPPIO_SPAZIO + message;
        adminLogger.info(message.trim());
    }


    /**
     * Gestisce un log di info <br>
     *
     * @param descrizione della informazione da gestire
     * @param clazz       di provenienza della richiesta
     * @param methodName  di provenienza della richiesta
     */
    public void info(String descrizione, Class clazz, String methodName) {
        //@todo Funzionalità ancora da implementare
        sendTerminale(AELogLivello.info, descrizione, clazz, methodName);
        //@todo Funzionalità ancora da implementare
    }


    /**
     * Gestisce un log di error <br>
     *
     * @param unErrore da gestire
     */
    public void error(Exception unErrore) {
        error(unErrore.toString());
    }


    /**
     * Gestisce un log di error <br>
     *
     * @param message da registrare
     */
    public void error(String message) {
        error(message, null, VUOTA);
    }


    /**
     * Gestisce un log di error <br>
     *
     * @param unErrore   da gestire
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    public void error(Exception unErrore, Class clazz, String methodName) {
        error(unErrore.toString(), clazz, methodName);
    }


    /**
     * Gestisce un log di error <br>
     *
     * @param message    da registrare
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    public void error(String message, Class clazz, String methodName) {
        esegue(AETypeLog.error, message, clazz, methodName);
    }


    /**
     * Gestisce un log di warning <br>
     *
     * @param message da registrare
     */
    public void warn(String message) {
        warn(message, null, VUOTA);
    }


    /**
     * Gestisce un log di error <br>
     *
     * @param unErrore   da gestire
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    public void warn(Exception unErrore, Class clazz, String methodName) {
        warn(unErrore.toString(), clazz, methodName);
    }


    /**
     * Gestisce un log di warning <br>
     *
     * @param message    da registrare
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    public void warn(String message, Class clazz, String methodName) {
        esegue(AETypeLog.warn, message, clazz, methodName);
    }


    /**
     * Gestisce un log di warning <br>
     *
     * @param type    livello di log
     * @param message da registrare
     */
    private void esegue(AETypeLog type, String message) {
        esegue(type, message, (Class) null, VUOTA);
    }


    /**
     * Gestisce un log di warning <br>
     *
     * @param type       livello di log
     * @param message    da registrare
     * @param clazz      di provenienza della richiesta
     * @param methodName di provenienza della richiesta
     */
    private void esegue(AETypeLog type, String message, Class clazz, String methodName) {
        String clazzTxt;
        String sep = " --- ";
        String end = "()";
        String typeTxt = type.getTag();
        typeTxt = text.fixSizeQuadre(typeTxt, 10);
        message = typeTxt + DOPPIO_SPAZIO + message;


        if (clazz != null) {
            clazzTxt = clazz.getSimpleName();
            message += sep + clazzTxt;
        }

        if (text.isValid(methodName)) {
            message += PUNTO + methodName + end;
        }

        if (type != null) {
            switch (type) {
                case info:
                    break;
                case modifica:
                    adminLogger.info(message.trim());
                    break;
                case warn:
                    adminLogger.warn(message.trim());
                    break;
                case error:
                    adminLogger.error(message.trim());
                    break;
                default:
                    this.warn("Switch - caso non definito", this.getClass(), "esegue");
                    break;
            }
        }

    }


    /**
     * Elabora il log da presentare a terminale <br>
     *
     * @param logLevel    del messaggio
     * @param descrizione della informazione da gestire
     * @param clazz       di provenienza della richiesta
     * @param methodName  di provenienza della richiesta
     */
    public void sendTerminale(AELogLivello logLevel, String descrizione, Class clazz, String methodName) {
        String message = VUOTA;
        String adesso = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM yyyy"));//@todo Funzionalità ancora da implementare in libreria
        String sep = SEP;
        sep = SPAZIO;
        String appName = FlowVar.projectName;

        if (logLevel == null) {
            return;
        } else {
            message += logLevel.color + logLevel.name().toUpperCase() + RESET;
        }

        if (text.isValid(appName)) {
            message += sep;
            message += logLevel.color + "(App)" + RESET;
            message += appName;
        }

        if (clazz != null) {
            message += sep;
            message += logLevel.color + "(Class)" + RESET;
            message += clazz.getSimpleName();
        }

        if (text.isValid(methodName)) {
            message += sep;
            message += logLevel.color + "(Method)" + RESET;
            message += methodName;
        }

        message += sep;
        message += logLevel.color + "(Message)" + RESET;
        message += descrizione;
        System.out.println(message);
        adminLogger.info(message.trim());
    }

}