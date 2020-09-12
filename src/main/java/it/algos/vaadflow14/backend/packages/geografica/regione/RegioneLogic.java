package it.algos.vaadflow14.backend.packages.geografica.regione;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AESearch;
import it.algos.vaadflow14.backend.logic.ALogic;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 10:38
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Regione ; <br>
 * 2) StaticContextAccessor.getBean(Regione.class) (senza parametri) <br>
 * 3) appContext.getBean(Regione.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegioneLogic extends ALogic {


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
    public RegioneLogic() {
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
    public RegioneLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Regione.class;
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

        super.operationForm = AEOperation.edit;
        super.usaBottoneDeleteAll = true;
        super.usaBottoneReset = true;
        super.usaBottoneNew = false;
        this.usaBottonePaginaWiki = true;
        this.searchType = AESearch.editField;
        this.wikiPageTitle = "ISO_3166-2:IT";
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param nome    (obbligatorio, unico)
     * @param iso     di riferimento (obbligatorio, unico)
     * @param sigla   (consuetudinaria, obbligatoria)
     * @param statuto (obbligatorio)
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public Regione crea(String nome, String iso, String sigla, AEStatuto statuto) {
        return (Regione) checkAndSave(newEntity(nome, iso, sigla, statuto));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity() {
        return newEntity(VUOTA, VUOTA, VUOTA, (AEStatuto) null);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome    (obbligatorio, unico)
     * @param iso     di riferimento (obbligatorio, unico)
     * @param sigla   (consuetudinaria, obbligatoria)
     * @param statuto (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity(String nome, String iso, String sigla, AEStatuto statuto) {
        Regione newEntityBean = Regione.builderRegione()

                .nome(text.isValid(nome) ? nome : null)

                .iso(text.isValid(iso) ? iso : null)

                .sigla(text.isValid(sigla) ? sigla : null)

                .statuto(statuto)

                .build();

        return (Regione) fixKey(newEntityBean);
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

        italia();
        //--aggiunge le province
        //        this.addProvince();

        francia();
        svizzera();
        austria();
        germania();
        //        spagna();

        return mongo.isValid(entityClazz);
    }


    /**
     * Regioni italiane <br>
     */
    public void italia() {
        String path = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow14/config/regioni";
        List<LinkedHashMap<String, String>> mappaCSV;
        String nome = VUOTA;
        String iso = VUOTA;
        String sigla = VUOTA;
        String statutoTxt = VUOTA;
        AEStatuto statuto = null;

        mappaCSV = fileService.leggeMappaCSV(path);
        for (LinkedHashMap<String, String> riga : mappaCSV) {
            nome = riga.get("nome");
            iso = riga.get("iso");
            sigla = riga.get("sigla");
            statutoTxt = riga.get("tipo");
            statuto = AEStatuto.get(statutoTxt);
            crea(nome, iso, sigla, statuto);
        }
    }


    /**
     * Regioni francesi <br>
     */
    public void francia() {
        String paginaWiki = "ISO_3166-2:FR";
        List<List<String>> listaGrezza = wiki.getTable(paginaWiki);
        String nome = VUOTA;
        String iso = VUOTA;
        String sigla = VUOTA;
        String[] parti = null;

        if (listaGrezza != null && listaGrezza.size() > 1) {
            for (List<String> rigaGrezza : listaGrezza) {
                parti = rigaGrezza.get(0).split(DOPPIO_PIPE_REGEX);
                if (parti.length > 2) {
                    nome = parti[2];
                    iso = parti[0];
                    sigla = text.levaTestaDa(iso, TRATTINO);
                    crea(nome, iso, sigla, AEStatuto.francia);
                }
            }
        }
    }


    /**
     * Cantoni svizzeri <br>
     */
    public void svizzera() {
        String paginaWiki = "Cantoni della Svizzera";
        List<List<String>> listaGrezza = wiki.getTable(paginaWiki);
        String nome = VUOTA;
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "CH-";

        if (listaGrezza != null && listaGrezza.size() >= 25) {
            listaGrezza = listaGrezza.subList(0, 25);
            for (List<String> rigaGrezza : listaGrezza) {
                if (rigaGrezza.size() > 2) {
                    nome = rigaGrezza.get(3);
                    nome = text.setNoQuadre(nome);
                    if (nome.contains(PIPE)) {
                        nome = text.levaTestaDa(nome, PIPE);
                    }
                    sigla = rigaGrezza.get(2);
                    iso = isoTag + sigla;
                    crea(nome, iso, sigla, AEStatuto.svizzera);
                }
            }
        }
    }


    /**
     * Länder austriaci <br>
     */
    public void austria() {
        String paginaWiki = "Stati federati dell'Austria";
        List<List<String>> listaGrezza = wiki.getTable(paginaWiki,1,1);
        String nome = VUOTA;
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "AT-";
        int pos = 1;

        if (listaGrezza != null && listaGrezza.size() > 1) {
            for (List<String> rigaGrezza : listaGrezza) {
                if (rigaGrezza.size() > 2) {
                    nome = rigaGrezza.get(2);
                    if (nome.contains("<br")) {
                        nome = text.levaCodaDa(nome, "<br");
                    }
                    nome = text.setNoQuadre(nome);
                    if (nome.contains(PIPE)) {
                        nome = text.levaTestaDa(nome, PIPE);
                    }
                    iso = isoTag + pos;
                    sigla = VUOTA + pos;
                    pos++;
                    crea(nome, iso, sigla, AEStatuto.austria);
                }
            }
        }
    }


    /**
     * Länder tedeschi <br>
     */
    public void germania() {
        String paginaWiki = "Stati federati della Germania";
        List<List<String>> listaGrezza = wiki.getTable(paginaWiki,1,1);
        String nome = VUOTA;
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "DE-";

        if (listaGrezza != null && listaGrezza.size() > 1) {
            for (List<String> rigaGrezza : listaGrezza) {
                if (rigaGrezza.size() > 5) {
                    nome = rigaGrezza.get(1);
                    nome = text.setNoQuadre(nome);
                    if (nome.contains(PIPE)) {
                        nome = text.levaTestaDa(nome, PIPE);
                    }
                    sigla = rigaGrezza.get(5);
                    sigla = text.setNoApici(sigla);
                    iso = isoTag + sigla;
                    crea(nome, iso, sigla, AEStatuto.germania);
                }
            }
        }
    }


    /**
     * Länder tedeschi <br>
     */
    public void spagna() {
        String paginaWiki = "Comunità autonome della Spagna";
        List<List<String>> listaGrezza = wiki.getTable(paginaWiki);
        String nome = VUOTA;
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "DE-";

        if (listaGrezza != null && listaGrezza.size() > 1) {
            for (List<String> rigaGrezza : listaGrezza) {
                if (rigaGrezza.size() > 5) {
                    nome = rigaGrezza.get(1);
                    nome = text.setNoQuadre(nome);
                    if (nome.contains(PIPE)) {
                        nome = text.levaTestaDa(nome, PIPE);
                    }
                    sigla = rigaGrezza.get(5);
                    sigla = text.setNoApici(sigla);
                    iso = isoTag + sigla;
                    crea(nome, iso, sigla, AEStatuto.spagna);
                }
            }
        }
    }


}