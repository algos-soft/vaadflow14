package it.algos.vaadflow14.backend.packages.crono.mese;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.packages.crono.CronoLogic;
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
public class MeseLogic extends CronoLogic {


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
        List<String> blu = new ArrayList<>();
        List<String> red = new ArrayList<>();

        blu.add("Mesi dell' anno, coi giorni. Tiene conto degli anni bisestili per il mese di febbraio.");
        blu.add("Ci sono 12 mesi. Non si possono cancellare ne aggiungere elementi.");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente bottone 'Esporta'");
        }

        return new AlertWrap(null, blu, red, false);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param aeMese: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata e salvata
     */
    public Mese creaIfNotExist(AEMese aeMese) {
        return creaIfNotExist(aeMese.getNome(), aeMese.getGiorni(), aeMese.getGiorniBisestili(), aeMese.getSigla());
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param mese            nome completo (obbligatorio, unico)
     * @param giorni          numero di giorni presenti (obbligatorio)
     * @param giorniBisestile numero di giorni presenti in un anno bisestile (obbligatorio)
     * @param sigla           nome abbreviato di tre cifre (obbligatorio, unico)
     *
     * @return la nuova entity appena creata e salvata
     */
    public Mese creaIfNotExist(String mese, int giorni, int giorniBisestile, String sigla) {
        return (Mese) checkAndSave(newEntity(mese, giorni, giorniBisestile, sigla));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Mese newEntity() {
        return newEntity(VUOTA, 0, 0, VUOTA);
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
        return newEntity(aeMese.getNome(), aeMese.getGiorni(), aeMese.getGiorniBisestili(), aeMese.getSigla());
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param mese            nome completo (obbligatorio, unico)
     * @param giorni          numero di giorni presenti (obbligatorio)
     * @param giorniBisestile numero di giorni presenti in un anno bisestile (obbligatorio)
     * @param sigla           nome abbreviato di tre cifre (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Mese newEntity(String mese, int giorni, int giorniBisestile, String sigla) {
        Mese newEntityBean = Mese.builderMese()

                .ordine(getNewOrdine())

                .mese(text.isValid(mese) ? mese : null)

                .giorni(giorni)

                .giorniBisestile(giorniBisestile)

                .sigla(text.isValid(sigla) ? sigla : null)

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
            creaIfNotExist(aeMese);
        }

        return mongo.isValid(entityClazz);
    }

}