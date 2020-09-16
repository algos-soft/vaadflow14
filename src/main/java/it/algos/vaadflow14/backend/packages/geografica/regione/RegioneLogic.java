package it.algos.vaadflow14.backend.packages.geografica.regione;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AESearch;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.backend.packages.geografica.stato.StatoLogic;
import it.algos.vaadflow14.backend.wrapper.WrapDueStringhe;
import it.algos.vaadflow14.ui.enumerastion.AEVista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.TRATTINO;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

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
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public StatoLogic statoLogic;


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
        super.usaBottoneDeleteAll = false;
        super.usaBottoneReset = true;
        super.usaBottoneNew = false;
        super.usaBottonePaginaWiki = true;
        super.searchType = AESearch.editField;
        super.wikiPageTitle = "ISO_3166-2";
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

        if (typeVista == AEVista.list) {
            lista.add("Suddivisioni geografica di secondo livello. Codifica secondo ISO 3166-2.");
            lista.add("Recuperati dalla pagina wiki: " + wikiPageTitle);
            lista.add("Codice ISO, sigla abituale e 'status' normativo");
            lista.add("Ordinamento alfabetico: prima Italia poi altri stati europei");
        }

        if (typeVista == AEVista.form) {
            lista.add("Scheda NON modificabile");
            lista.add("Stato codificato ISO 3166-2");
        }

        return lista;
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox("stato", statoLogic.getItalia());
        super.creaComboBox("status", 14);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param nome   (obbligatorio, unico)
     * @param stato  (obbligatorio)
     * @param iso    di riferimento (obbligatorio, unico)
     * @param sigla  (consuetudinaria, obbligatoria)
     * @param status (obbligatorio)
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public Regione crea(String nome, Stato stato, String iso, String sigla, AEStatuto status) {
        return (Regione) checkAndSave(newEntity(nome, stato, iso, sigla, status));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity() {
        return newEntity(VUOTA, (Stato) null, VUOTA, VUOTA, (AEStatuto) null);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome   (obbligatorio, unico)
     * @param stato  (obbligatorio)
     * @param iso    di riferimento (obbligatorio, unico)
     * @param sigla  (consuetudinaria, obbligatoria)
     * @param status (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity(String nome, Stato stato, String iso, String sigla, AEStatuto status) {
        Regione newEntityBean = Regione.builderRegione()

                .ordine(this.getNewOrdine())

                .nome(text.isValid(nome) ? nome : null)

                .stato(stato)

                .iso(text.isValid(iso) ? iso : null)

                .sigla(text.isValid(sigla) ? sigla : null)

                .status(status)

                .build();

        return (Regione) fixKey(newEntityBean);
    }


    /**
     * Retrieves an entity by its key.
     *
     * @param key must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     */
    public Regione findByIsoItalian(String key) {
        return (Regione) mongo.findOneUnique(Regione.class, "iso", "IT-" + key);
    }


    /**
     * Retrieves all entities.
     *
     * @return the entity with the given id or {@literal null} if none found
     */
    public List<Regione> findAllItalian() {
        List<Regione> items;
        Query query = new Query();

        query.addCriteria(Criteria.where("stato.id").is("italia"));
        query.with(Sort.by(Sort.Direction.ASC, "ordine"));
        items = mongo.findAll(entityClazz, query);

        return items;
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
        //--controlla che esista la collection 'Stato', indispensabile
        if (mongo.isEmpty(Stato.class)) {
            logger.warn("Manca la collection 'Stato'. Reset non eseguito.", this.getClass(), "reset");
            return false;
        }

        super.deleteAll();

        italia();
        //--aggiunge le province
        //        this.addProvince();

        if (true) {
            francia();
            svizzera();
            austria();
            germania();
            spagna();
            portogallo();
            //        slovenia(); // sono troppi
            belgio();
            olanda();
            croazia();
        }

        return mongo.isValid(entityClazz);
    }


    /**
     * Regioni italiane <br>
     */
    public void italia() {
        String path = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow14/config/regioni";
        List<LinkedHashMap<String, String>> mappaCSV;
        String nome = VUOTA;
        Stato stato = statoLogic.getItalia();
        String iso = VUOTA;
        String sigla = VUOTA;
        String statusTxt = VUOTA;
        AEStatuto status = null;

        mappaCSV = fileService.leggeMappaCSV(path);
        for (LinkedHashMap<String, String> riga : mappaCSV) {
            nome = riga.get("nome");
            iso = riga.get("iso");
            sigla = riga.get("sigla");
            statusTxt = riga.get("status");
            status = AEStatuto.valueOf(statusTxt);
            crea(nome, stato, iso, sigla, status);
        }
    }


    /**
     * Regioni francesi <br>
     */
    public void francia() {
        String paginaWiki = "ISO_3166-2:FR";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getFrancia();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "FR-";

        //--13 regioni metropolitane
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.franciaMetropolitana);
            }
        }

        //--3 regioni d'oltremare
        listaWrap = wiki.getDueColonne(paginaWiki, 3, 2, 2, 4);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.franciaOltremare);
            }
        }

        //--9 collettività d'oltremare
        listaWrap = wiki.getDueColonne(paginaWiki, 4, 2, 1, 3);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.franciaCollettivita);
            }
        }
    }


    /**
     * Cantoni svizzeri <br>
     */
    public void svizzera() {
        String paginaWiki = "ISO_3166-2:CH";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getSvizzera();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "CH-";

        //--26 cantoni
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.svizzera);
            }
        }
    }


    /**
     * Länder austriaci <br>
     */
    public void austria() {
        String paginaWiki = "ISO_3166-2:AT";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getAustria();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "AT-";

        //--9 lander
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.austria);
            }
        }
    }


    /**
     * Länder tedeschi <br>
     */
    public void germania() {
        String paginaWiki = "ISO_3166-2:DE";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getGermania();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "DE-";

        //--16 lander
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.germania);
            }
        }
    }


    /**
     * Comunità spagnole <br>
     */
    public void spagna() {
        String paginaWiki = "ISO_3166-2:ES";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getSpagna();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "ES-";

        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.spagna);
            }
        }
    }


    /**
     * Distretti portoghesi <br>
     */
    public void portogallo() {
        String paginaWiki = "ISO_3166-2:PT";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getPortogallo();
        String iso = VUOTA;
        String sigla = VUOTA;

        //--18 distretti
        listaWrap = wiki.getDueColonne(paginaWiki, 1, 2, 2, 3);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.portogalloDistretto);
            }
        }

        //--2 regioni autonome
        listaWrap = wiki.getDueColonne(paginaWiki, 2, 2, 2, 3);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.portogalloRegione);
            }
        }
    }


    /**
     * Comuni sloveni <br>
     */
    public void slovenia() {
        String paginaWiki = "ISO_3166-2:SI";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getSlovenia();
        String iso = VUOTA;
        String sigla = VUOTA;

        //--212 comuni
        listaWrap = wiki.getDueColonne(paginaWiki, 1, 2, 1, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.slovenia);
            }
        }
    }


    /**
     * Regioni belghe <br>
     */
    public void belgio() {
        String paginaWiki = "ISO_3166-2:BE";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getBelgio();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "BE-";

        //--3 regioni
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.belgio);
            }
        }
    }


    /**
     * Province olandesi <br>
     */
    public void olanda() {
        String paginaWiki = "ISO_3166-2:NL";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getOlanda();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "NL-";

        //--9 province
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.olanda);
            }
        }
    }


    /**
     * Regioni croate <br>
     */
    public void croazia() {
        String paginaWiki = "ISO_3166-2:HR";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getCroazia();
        String iso = VUOTA;
        String sigla = VUOTA;

        //--21 regioni
        listaWrap = wiki.getDueColonne(paginaWiki, 1, 2, 1, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.croazia);
            }
        }
    }

}