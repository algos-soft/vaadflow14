package it.algos.vaadflow14.backend.packages.crono.giorno;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.ALogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import it.algos.vaadflow14.backend.packages.crono.secolo.AESecolo;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.HashMap;
import java.util.List;

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
public class GiornoLogic extends ALogic {


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
     * Preferenze standard <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param code
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public boolean crea(String code) {
        Giorno newEntityBean =null;
        return insert(newEntityBean) != null;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param ordine (obbligatorio, unico)
     * @param mese   di riferimento (obbligatorio)
     * @param nome (obbligatorio, unico)
     * @return la nuova entity appena creata (non salvata)
     */
    public Giorno newEntity(int ordine, Mese mese, String nome){
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
        AESecolo secoloEnum;
        Secolo secolo;
        String titoloSecolo;
        List<HashMap> lista;

        //costruisce i 366 records
        lista = date.getAllGiorni();
        for (HashMap mappaGiorno : lista) {
//            titolo = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
//            bisestile = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);
//            mese = meseService.findByKeyUnica((String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO));
//            ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_NORMALE);
//
//            crea(aeMese);
        }

        return mongo.isValid(entityClazz);
    }
}