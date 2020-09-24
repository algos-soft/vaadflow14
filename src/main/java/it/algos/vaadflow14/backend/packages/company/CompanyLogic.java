package it.algos.vaadflow14.backend.packages.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.preferenza.AEPreferenza;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 23-ago-2020
 * Time: 15:44
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Company ; <br>
 * 2) StaticContextAccessor.getBean(Company.class) (senza parametri) <br>
 * 3) appContext.getBean(Company.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompanyLogic extends ALogic {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public CompanyLogic() {
        this(AEOperation.edit);
    }


    /**
     * Costruttore con parametro <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AFormView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName), operationForm) <br>
     *
     * @param operationForm tipologia di Form in uso
     */
    public CompanyLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Company.class;
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

        if (FlowVar.usaCompany) {
            if (vaadinService.isDeveloper()) {
                super.usaBottoneDeleteAll = true;
                super.usaBottoneReset = true;

            }
            if (!vaadinService.isAdminOrDeveloper()) {
                super.operationForm = AEOperation.showOnly;
            }

            super.usaBottoneNew = vaadinService.isAdminOrDeveloper();
            super.usaBottoneExport = vaadinService.isAdminOrDeveloper();
        } else {
            if (AEPreferenza.usaDebug.is()) {
                super.usaBottoneDeleteAll = true;
                super.usaBottoneReset = true;
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
            red.add("Di norma utilizzato solo in applicazioni con usaCompany=true");
        }

        return new AlertWrap(null, null, red, false);
    }

    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param code        di riferimento
     * @param descrizione completa
     *
     * @return la nuova entity appena creata e salvata
     */
    public Company crea(String code, String descrizione) {
        return crea(code, descrizione, VUOTA, VUOTA);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param code        di riferimento
     * @param descrizione completa
     *
     * @return la nuova entity appena creata e salvata
     */
    public Company crea(String code, String descrizione, String telefono, String email) {
        return (Company) checkAndSave(newEntity(code, descrizione, telefono, email));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Company newEntity() {
        return newEntity(VUOTA, VUOTA, VUOTA, VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param code        di riferimento
     * @param descrizione completa
     * @param telefono    fisso o cellulare
     * @param email       di posta elettronica
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Company newEntity(String code, String descrizione, String telefono, String email) {
        Company newEntityBean = Company.builderCompany()

                .code(text.isValid(code) ? code : null)

                .descrizione(text.isValid(descrizione) ? descrizione : null)

                .telefono(text.isValid(telefono) ? telefono : null)

                .email(text.isValid(email) ? email : null)

                .build();

        return (Company) fixKey(newEntityBean);
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
    public Company findById(String keyID) {
        return (Company) super.findById(keyID);
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

        crea("Algos", "Company Algos di prova",VUOTA,"info@algos.it");
        crea("Demo", "Company demo","345 994487","demo@algos.it");
        crea("Test", "Company di test","","presidentePonteTaro@crocerossa.it");

        return mongo.isValid(entityClazz);
    }


    /**
     * Recupera dal db mongo la company (se esiste)
     */
    public Company getAlgos() {
        return findById(FlowCost.COMPANY_ALGOS);
    }


    /**
     * Recupera dal db mongo la company (se esiste)
     */
    public Company getDemo() {
        return findById(FlowCost.COMPANY_DEMO);
    }


    /**
     * Recupera dal db mongo la company (se esiste)
     */
    public Company getTest() {
        return findById(FlowCost.COMPANY_TEST);
    }

}