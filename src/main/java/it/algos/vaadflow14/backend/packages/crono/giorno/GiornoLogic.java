package it.algos.vaadflow14.backend.packages.crono.giorno;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.packages.crono.CronoLogic;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import it.algos.vaadflow14.backend.packages.crono.mese.MeseLogic;
import it.algos.vaadflow14.backend.packages.crono.secolo.AESecolo;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import it.algos.vaadflow14.ui.enumerastion.AEVista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 14-ago-2020
 * Time: 15:27
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Giorno ; <br>
 * 2) StaticContextAccessor.getBean(Giorno.class) (senza parametri) <br>
 * 3) appContext.getBean(Giorno.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GiornoLogic extends CronoLogic {


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
    public MeseLogic meseLogic;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public GiornoLogic() {
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
    public GiornoLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Giorno.class;
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

        lista.add("Giorni dell'anno. 366 giorni per tenere conto dei 29 giorni di febbraio negli anni bisestili.");
        lista.add("<span style=\"color:red\">Bottone new provvisorio</span>");

        return lista;
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox(Mese.class, "10em", true, false);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param ordine (obbligatorio, unico)
     * @param mese   di riferimento (obbligatorio)
     * @param nome   (obbligatorio, unico)
     *
     * @return la nuova entity appena creata e salvata
     */
    public Giorno crea(int ordine, Mese mese, String nome) {
        return (Giorno)checkAndSave(newEntity(ordine, mese, nome));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Giorno newEntity() {
        return newEntity(0, (Mese) null, VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param ordine (obbligatorio, unico)
     * @param mese   di riferimento (obbligatorio)
     * @param nome   (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Giorno newEntity(int ordine, Mese mese, String nome) {
        Giorno newEntityBean = Giorno.builderGiorno()

                .ordine(getNewOrdine())

                .mese(mese)

                .nome(text.isValid(nome) ? nome : null)

                .build();

        return (Giorno) fixKey(newEntityBean);
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
        String titoloMese;
        List<HashMap> lista;
        Mese mese;

        //costruisce i 366 records
        lista = date.getAllGiorni();
        for (HashMap mappaGiorno : lista) {
            titolo = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
            titoloMese = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO);
            mese = (Mese) mongo.findById(Mese.class, titoloMese);
            ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_NORMALE);

            crea(ordine, mese, titolo);
        }

        return mongo.isValid(entityClazz);
    }

}