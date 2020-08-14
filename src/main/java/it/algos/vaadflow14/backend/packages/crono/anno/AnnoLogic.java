package it.algos.vaadflow14.backend.packages.crono.anno;

import com.google.gson.Gson;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.entity.ALogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.packages.crono.secolo.AESecolo;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import it.algos.vaadflow14.backend.service.ADateService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 07:45
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Anno ; <br>
 * 2) StaticContextAccessor.getBean(Anno.class) (senza parametri) <br>
 * 3) appContext.getBean(Anno.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnnoLogic extends ALogic {

    /**
     * Costanti usate nell' ordinamento delle categorie
     */
    public static final int ANNO_INIZIALE = 2000;

    public static final int ANTE_CRISTO = 1000;

    public static final int DOPO_CRISTO = 2030;

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
    public ADateService date;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public AnnoLogic() {
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
    public AnnoLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Anno.class;
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

        super.operationForm = AEOperation.showOnly;
        super.usaBottoneDeleteAll = true;
        super.usaBottoneReset = true;
        super.usaBottoneNew = false;
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox(Secolo.class, "12em", true, false);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param ordine    (obbligatorio, unico)
     * @param secolo    di riferimento (obbligatorio)
     * @param nome      (obbligatorio, unico)
     * @param bisestile (obbligatorio)
     *
     * @return true se la entity è stata creata
     */
    public boolean crea(int ordine, Secolo secolo, String nome, boolean bisestile) {
        return checkAndSave(newEntity(ordine, secolo, nome, bisestile));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Anno newEntity() {
        return newEntity(0, null, VUOTA, false);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine    (obbligatorio, unico)
     * @param secolo    di riferimento (obbligatorio)
     * @param nome      (obbligatorio, unico)
     * @param bisestile (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Anno newEntity(int ordine, Secolo secolo, String nome, boolean bisestile) {
        Anno newEntityBean = Anno.builderAnno()

                .ordine(ordine)

                .secolo(secolo)

                .nome(text.isValid(nome) ? nome : null)

                .bisestile(bisestile)

                .build();

        return (Anno) fixKey(newEntityBean);
    }


    public List<Anno> fetchAnni(int offset, int limit) {
        List<Anno> lista = new ArrayList<>();
        Gson gson = new Gson();
        Anno anno;

        List<Document> products = mongo.mongoOp.getCollection("anno").find().skip(offset).limit(limit).into(new ArrayList<>());

        for (Document doc : products) {

            // 1. JSON file to Java object
            anno = gson.fromJson(doc.toJson(), Anno.class);
            lista.add(anno);
        }

        return lista;
    }


    /**
     * Creazione di alcuni dati iniziali <br>
     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo in alcuni casi) <br>
     * I dati possono essere presi da una Enumeration o creati direttamente <br>
     * Deve essere sovrascritto <br>
     *
     * @return false se non esiste il metodo sovrascritto
     * ....... true se esiste il metodo sovrascritto è la collection viene ri-creata
     */
    @Override
    public boolean reset() {
        super.deleteAll();
        int ordine;
        String titolo;
        AESecolo secoloEnum;
        Secolo secolo;
        String titoloSecolo;
        boolean bisestile = false;

        //--costruisce gli anni prima di cristo dal 1000
        for (int k = ANTE_CRISTO; k > 0; k--) {
            ordine = ANNO_INIZIALE - k;
            titolo = k + AESecolo.TAG_AC;
            secoloEnum = AESecolo.getSecoloAC(k);
            titoloSecolo = secoloEnum.getNome();
            titoloSecolo = titoloSecolo.toLowerCase();
            titoloSecolo = text.levaSpaziInterni(titoloSecolo);
            secolo = (Secolo) mongo.findById(Secolo.class, titoloSecolo);
            bisestile = false; //non ci sono anni bisestili prima di Cristo
            if (ordine != ANNO_INIZIALE) {
                crea(ordine, secolo, titolo, bisestile);
            }
        }

        //--costruisce gli anni dopo cristo fino al 2030
        for (int k = 1; k <= DOPO_CRISTO; k++) {
            ordine = k + ANNO_INIZIALE;
            titolo = k + FlowCost.VUOTA;
            secoloEnum = AESecolo.getSecoloDC(k);
            titoloSecolo = secoloEnum.getNome();
            titoloSecolo = titoloSecolo.toLowerCase();
            titoloSecolo = text.levaSpaziInterni(titoloSecolo);
            secolo = (Secolo) mongo.findById(Secolo.class, titoloSecolo);
            bisestile = date.bisestile(k);
            if (ordine != ANNO_INIZIALE) {
                crea(ordine, secolo, titolo, bisestile);
            }
        }

        return mongo.isValid(entityClazz);
    }


    public int getCount() {
        return mongo.count("anno");
    }

}