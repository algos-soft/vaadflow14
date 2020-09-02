package it.algos.vaadflow14.backend.packages.crono.secolo;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.packages.crono.CronoLogic;
import it.algos.vaadflow14.ui.enumerastion.AEVista;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 07:07
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Secolo ; <br>
 * 2) StaticContextAccessor.getBean(Secolo.class) (senza parametri) <br>
 * 3) appContext.getBean(Secolo.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SecoloLogic extends CronoLogic {


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
    public SecoloLogic() {
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
    public SecoloLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Secolo.class;
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
        String message;

        lista.add("Secoli ante e post Cristo. Venti secoli AnteCristo e ventun secoli DopoCristo ");
        message = "Sono indicati gli anni iniziali e finali di ogni secolo.";
        message += " L' anno <span style=\"color:black\"><b>0</b></span>";
        message += " <span style=\"color:red\"><b>NON esiste</b></span> nei calendari.";
        lista.add(message);
        return lista;
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param aeSecolo: enumeration per la creazione-reset di tutte le entities
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public boolean crea(AESecolo aeSecolo) {
        return crea(aeSecolo.isAnteCristo(), aeSecolo.getInizio(), aeSecolo.getFine(), aeSecolo.getNome());
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param anteCristo flag per i secoli prima di cristo (obbligatorio)
     * @param inizio     (obbligatorio, unico)
     * @param fine       (obbligatorio, unico)
     * @param nome       (obbligatorio, unico)
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public boolean crea(boolean anteCristo, int inizio, int fine, String nome) {
        return checkAndSave(newEntity(anteCristo, inizio, fine, nome));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Secolo newEntity() {
        return newEntity(false, 0, 0, VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param aeSecolo: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Secolo newEntity(AESecolo aeSecolo) {
        return newEntity(aeSecolo.isAnteCristo(), aeSecolo.getInizio(), aeSecolo.getFine(), aeSecolo.getNome());
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param anteCristo flag per i secoli prima di cristo (obbligatorio)
     * @param inizio     (obbligatorio, unico)
     * @param fine       (obbligatorio, unico)
     * @param nome       (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Secolo newEntity(boolean anteCristo, int inizio, int fine, String nome) {
        Secolo newEntityBean = Secolo.builderSecolo()

                .anteCristo(anteCristo)

                .inizio(inizio)

                .fine(fine)

                .nome(text.isValid(nome) ? nome : null)

                .build();

        return (Secolo) fixKey(newEntityBean);
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
    public boolean reset() {
        super.deleteAll();

        for (AESecolo eaSecolo : AESecolo.values()) {
            crea(eaSecolo);
        }

        return mongo.isValid(entityClazz);
    }

}