package it.algos.vaadflow14.backend.packages.crono.mese;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.ALogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.ui.enumerastion.AEVista;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 31-lug-2020
 * Time: 22:07
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private MeseLogic ; <br>
 * 2) StaticContextAccessor.getBean(MeseLogic.class) (senza parametri) <br>
 * 3) appContext.getBean(MeseLogic.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MeseLogic extends ALogic {


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
    public MeseLogic() {
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
    public MeseLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Mese.class;
    }


    /**
     * Preferenze usate da questa istanza e dalle Views collegate <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        //        super.operationForm = AEOperation.showOnly; //@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
        super.usaBottoneDeleteAll = true;
        super.usaBottoneReset = true;
        super.usaBottoneNew = false;

        //--provvisorio
        super.usaBottoneNew = true;
    }


    /**
     * Costruisce una lista di informazioni per costruire l' istanza di AHeaderList <br>
     * Informazioni (eventuali) specifiche di ogni modulo <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Esempio:     return new ArrayList(Arrays.asList("uno", "due", "tre"));
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return wrapper per passaggio dati
     */
    @Override
    protected List<String> getAlertList(AEVista typeVista) {
        List<String> lista = super.getAlertList(typeVista);

        lista.add("<span style=\"color:green\">Mesi dell' anno, coi giorni. Tiene conto degli <span style=\"color:red\">anni bisestili</span> per il mese di febbraio.</span>");
        lista.add("Ci sono 12 mesi. Non si possono cancellare ne aggiungere elementi.");
        lista.add("<span style=\"color:red\">Bottone new provvisorio</span>");

        return lista;
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param aeMese: enumeration per la creazione-reset di tutte le entities
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public boolean crea(AEMese aeMese) {
        return crea(aeMese.getGiorni(), aeMese.getGiorniBisestili(), aeMese.getSigla(), aeMese.getNome());
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param giorni          numero di giorni presenti (obbligatorio)
     * @param giorniBisestile numero di giorni presenti in un anno bisestile (obbligatorio)
     * @param sigla           nome abbreviato di tre cifre (obbligatorio, unico)
     * @param nome            nome completo (obbligatorio, unico)
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public boolean crea(int giorni, int giorniBisestile, String sigla, String nome) {
        return checkAndSave(newEntity(giorni, giorniBisestile, sigla, nome ));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Mese newEntity() {
        return newEntity(0, 0, VUOTA, VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param aeMese: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Mese newEntity(AEMese aeMese) {
        return newEntity(aeMese.getGiorni(), aeMese.getGiorniBisestili(), aeMese.getSigla(), aeMese.getNome());
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param giorni          numero di giorni presenti (obbligatorio)
     * @param giorniBisestile numero di giorni presenti in un anno bisestile (obbligatorio)
     * @param sigla           nome abbreviato di tre cifre (obbligatorio, unico)
     * @param nome            nome completo (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Mese newEntity(int giorni, int giorniBisestile, String sigla, String nome) {
        Mese newEntityBean = Mese.builderMese()

                .ordine(getNewOrdine())

                .giorni(giorni)

                .giorniBisestile(giorniBisestile)

                .sigla(text.isValid(sigla) ? sigla : null)

                .nome(text.isValid(nome) ? nome : null)

                .build();

        return (Mese) fixKey(newEntityBean);
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

        for (AEMese aeMese : AEMese.values()) {
            crea(aeMese);
        }

        return mongo.isValid(entityClazz);
    }

}