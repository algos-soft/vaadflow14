package it.algos.vaadflow14.backend.packages.security;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AERole;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.company.Company;
import it.algos.vaadflow14.backend.packages.company.CompanyLogic;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.MONGO_FIELD_USER;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 20-ago-2020
 * Time: 12:55
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Utente ; <br>
 * 2) StaticContextAccessor.getBean(Utente.class) (senza parametri) <br>
 * 3) appContext.getBean(Utente.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UtenteLogic extends ALogic {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public CompanyLogic companyLogic;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    @Deprecated
    public UtenteLogic() {
        this(AEOperation.edit);
    }


    /**
     * Costruttore con parametro <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AFormView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName), operationForm) <br>
     *
     * @param operationForm tipologia di Form in uso
     */
    public UtenteLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Utente.class;
    }


    /**
     * Preferenze standard <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        if (FlowVar.usaSecurity) {
            if (vaadinService.isDeveloper()) {
                super.usaBottoneDeleteAll = true;
                super.usaBottoneResetList = true;

            }
            if (!vaadinService.isAdminOrDeveloper()) {
                super.operationForm = AEOperation.showOnly;
            }

            super.usaBottoneNew = vaadinService.isAdminOrDeveloper();
            super.usaBottoneExport = vaadinService.isAdminOrDeveloper();
        } else {
            if (AEPreferenza.usaDebug.is()) {
                super.usaBottoneDeleteAll = true;
                super.usaBottoneResetList = true;
            }
            super.usaBottoneNew = true;
        }
    }

    /**
     * Costruisce un wrapper di liste di informazioni per costruire l' istanza di AHeaderWrap <br>
     * Informazioni (eventuali) specifiche di ogni modulo <br>
     * Deve essere sovrascritto <br>
     * Esempio:     return new AlertWrap(new ArrayList(Arrays.asList("uno", "due", "tre")));
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return wrapper per passaggio dati
     */
    @Override
    protected AlertWrap getAlertWrap(AEVista typeVista) {
        List<String> red = new ArrayList<>();

        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' (e anche questo avviso) solo in fase di debug. Sempre presente bottone 'New'");
            red.add("Di norma utilizzato solo in applicazioni con usaSecurity=true");
        }

        return new AlertWrap(null, null, red, false);
    }

    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param username o nickName
     * @param password in chiaro
     *
     * @return la nuova entity appena creata e salvata
     */
    public Utente creaIfNotExist(String username, String password) {
        return (Utente) checkAndSave(newEntity(username, password, (AERole) null));
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     * Può forzare una company DIVERSA da quella corrente usata da newEntity() <br>
     *
     * @param company  obbligatoria se FlowVar.usaCompany=true
     * @param username o nickName
     * @param password in chiaro
     * @param role     authority per il login
     *
     * @return la nuova entity appena creata e salvata
     */
    public Utente creaIfNotExist(Company company, String username, String password, AERole role) {
        Utente entity = newEntity(username, password, role);
        entity.company = company;
        return (Utente) checkAndSave(entity);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Senza properties per compatibilità con la superclasse <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public AEntity newEntity() {
        return newEntity(VUOTA, VUOTA, (AERole) null);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * <p>
     * //     * @param company  obbligatoria se FlowVar.usaCompany=true
     *
     * @param username o nickName
     * @param password in chiaro
     * @param role     authority per il login
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Utente newEntity(String username, String password, AERole role) {
        Utente newEntityBean = Utente.builderUtente()

                .username(text.isValid(username) ? username : null)

                .password(text.isValid(password) ? password : null)

                .accountNonExpired(true)

                .accountNonLocked(true)

                .credentialsNonExpired(true)

                .enabled(true)

                .role(role != null ? role : AERole.user)

                .build();

        return (Utente) fixKey(newEntityBean);
    }


    /**
     * Operazioni eseguite PRIMA di save o di insert <br>
     * Regolazioni automatiche di property <br>
     * Controllo della validità delle properties obbligatorie <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, Edit)
     *
     * @return the modified entity
     */
    @Override
    public Utente beforeSave(AEntity entityBean, AEOperation operation) {
        Utente entity = (Utente) super.beforeSave(entityBean, operation);

        if (entity != null && entity.username != null) {
            entity.username = text.levaSpazi(entity.username);
        }

        return entity;
    }


    /**
     * Retrieves an entity by its id.
     *
     * @param keyID must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    public Utente findById(String keyID) {
        return (Utente) super.findById(keyID);
    }

    /**
     * Retrieves an entity by its keyProperty.
     *
     * @param keyValue must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    public Utente findByKey(String keyValue) {
        return (Utente) super.findByKey(keyValue);
    }

    /**
     * Retrieves an entity by userName.
     *
     * @param userName must not be {@literal null}.
     */
    public Utente findByUser(String userName) {
        return (Utente) mongo.findOneUnique(Utente.class, MONGO_FIELD_USER, userName);
    }


    /**
     * Creazione di alcuni dati iniziali <br>
     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo in alcuni casi) <br>
     * I dati possono essere presi da una Enumeration o creati direttamente <br>
     * DEVE essere sovrascritto <br>
     *
     * @return false se non esiste il metodo sovrascritto
     * ....... true se esiste il metodo sovrascritto è la collection viene ri-creata
     */
    @Override
    public boolean reset() {
        super.deleteAll();

        creaIfNotExist(companyLogic.getAlgos(), "Gac", "fulvia", AERole.developer);
        creaIfNotExist(companyLogic.getDemo(), "mario_rossi", "rossi123", AERole.admin);
        creaIfNotExist(null, "marco.beretta", "beretta123", AERole.admin);
        creaIfNotExist(companyLogic.getTest(), "antonia-pellegrini", "pellegrini123", AERole.user);
        creaIfNotExist(null, "paolo cremona", "cremona123", AERole.guest);

        return mongo.isValid(entityClazz);
    }

}