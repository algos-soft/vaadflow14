package it.algos.vaadflow14.backend.packages.geografica.regione;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AESearch;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.backend.packages.geografica.stato.StatoLogic;
import it.algos.vaadflow14.backend.wrapper.WrapDueStringhe;
import it.algos.vaadflow14.ui.enumerastion.AEVista;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.Field;
import java.util.ArrayList;
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

        super.operationForm = FlowVar.usaDebug ? AEOperation.edit : AEOperation.showOnly;
        super.usaBottoneDeleteAll = FlowVar.usaDebug;
        super.usaBottoneReset = FlowVar.usaDebug;
        super.usaBottoneNew = FlowVar.usaDebug;
        super.usaBottonePaginaWiki = true;
        super.searchType = AESearch.editField;
        super.wikiPageTitle = "ISO_3166-2";
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
        List<String> blue = new ArrayList<>();
        List<String> red = new ArrayList<>();

        blue.add("Suddivisioni geografica di secondo livello. Codifica secondo ISO 3166-2");
        blue.add("Recuperati dalla pagina wiki: " + wikiPageTitle);
        blue.add("Codice ISO, sigla abituale e 'status' normativo");
        blue.add("Ordinamento alfabetico: prima Italia poi altri stati europei");
        if (FlowVar.usaDebug) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente il searchField ed i comboBox 'Stato' e 'Status' ");
        }
        return new AlertWrap(null, blue, red, false);
    }


    //    /**
    //     * Costruisce una lista di informazioni per costruire l' istanza di AHeaderList <br>
    //     * Informazioni (eventuali) specifiche di ogni modulo <br>
    //     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
    //     * Esempio:     return new ArrayList(Arrays.asList("uno", "due", "tre"));
    //     *
    //     * @param typeVista in cui inserire gli avvisi
    //     *
    //     * @return wrapper per passaggio dati
    //     */
    //    @Override
    //    protected List<String> getAlertList(AEVista typeVista) {
    //        List<String> lista = super.getAlertList(typeVista);
    //        String message;
    //
    //        if (typeVista == AEVista.list) {
    //            lista.add("Suddivisioni geografica di secondo livello. Codifica secondo ISO 3166-2.");
    //            lista.add("Recuperati dalla pagina wiki: " + wikiPageTitle);
    //            lista.add("Codice ISO, sigla abituale e 'status' normativo");
    //            lista.add("Ordinamento alfabetico: prima Italia poi altri stati europei");
    //        }
    //
    //        if (typeVista == AEVista.form) {
    //            lista.add("Scheda NON modificabile");
    //            lista.add("Stato codificato ISO 3166-2");
    //        }
    //
    //        return lista;
    //    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        //        super.creaComboBox("stato", statoLogic.getItalia());
        ComboBox comboStati = this.creaComboStati();
        mappaComboBox.put("stato", comboStati);

        super.creaComboBox("status", 14);
    }


    protected ComboBox creaComboStati() {
        ComboBox<Stato> combo = new ComboBox();
        Field reflectionJavaField = null;
        String tag = TRE_PUNTI;
        Class comboEnumClazz = null;
        String widthEM = "12em";
        Sort sort = Sort.by("ordine");
        List items;


        items = mongo.findAll(Stato.class, sort);
        combo.setWidth(widthEM);
        combo.setPreventInvalidInput(true);
        combo.setAllowCustomValue(false);
        combo.setPlaceholder(text.primaMaiuscola("Stati") + tag);
        combo.setClearButtonVisible(true);
        combo.setRequired(false);

        combo.setItems(items);
//        if (initialValue != null) {
//            combo.setValue(initialValue);
//        }


        combo.setRenderer(new ComponentRenderer<>(entityStato -> {
            Div text = new Div();
            String sigla=entityStato.getAlfadue().toLowerCase();
            text.setText(entityStato.getStato());

            Image image = imageService.getBandiera(sigla);
            image.setWidth("21px");
            image.setHeight("21px");

            FlexLayout wrapper = new FlexLayout();
            text.getStyle().set("margin-left", "0.5em");
            wrapper.add(image, text);
            return wrapper;
        }));
        return combo;
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param regione (obbligatorio, unico)
     * @param stato   (obbligatorio)
     * @param iso     di riferimento (obbligatorio, unico)
     * @param sigla   (consuetudinaria, obbligatoria)
     * @param status  (obbligatorio)
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public Regione crea(String regione, Stato stato, String iso, String sigla, AEStatuto status) {
        return (Regione) checkAndSave(newEntity(regione, stato, iso, sigla, status));
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
     * @param regione (obbligatorio, unico)
     * @param stato   (obbligatorio)
     * @param iso     di riferimento (obbligatorio, unico)
     * @param sigla   (consuetudinaria, obbligatoria)
     * @param status  (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity(String regione, Stato stato, String iso, String sigla, AEStatuto status) {
        Regione newEntityBean = Regione.builderRegione()

                .ordine(this.getNewOrdine())

                .regione(text.isValid(regione) ? regione : null)

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
        return findAllByStato("italia");
    }


    /**
     * Retrieves all entities.
     *
     * @return the entity with the given id or {@literal null} if none found
     */
    public List<Regione> findAllByStato(String statoKeyID) {
        List<Regione> items;
        Query query = new Query();

        query.addCriteria(Criteria.where("stato.id").is(statoKeyID));
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
            belgio();
            olanda();
            croazia();
            albania();
            grecia();
            cechia();
            slovacchia();
            ungheria();
            romania();
            bulgaria();
            polonia();
            danimarca();
            //        slovenia(); // sono troppi
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
     * Regioni francesi (35) <br>
     */
    public void francia() {
        String paginaWiki = "ISO_3166-2:FR";
        Stato stato = statoLogic.getFrancia();
        String isoTag = "FR-";

        //--13 regioni metropolitane
        templateList(stato, AEStatuto.franciaMetropolitana, paginaWiki, isoTag, 1, 2, 2);

        //--3 regioni d'oltremare
        dueColonne(stato, AEStatuto.franciaMetropolitana, paginaWiki, 3, 2, 2, 4);

        //--9 collettività d'oltremare
        dueColonne(stato, AEStatuto.franciaCollettivita, paginaWiki, 4, 2, 1, 3);
    }


    /**
     * Cantoni svizzeri (26) <br>
     */
    public void svizzera() {
        String paginaWiki = "ISO_3166-2:CH";
        Stato stato = statoLogic.getSvizzera();
        String isoTag = "CH-";

        templateList(stato, AEStatuto.svizzera, paginaWiki, isoTag, 1, 2, 2);
    }


    /**
     * Länder austriaci (9) <br>
     */
    public void austria() {
        String paginaWiki = "ISO_3166-2:AT";
        Stato stato = statoLogic.getAustria();
        String isoTag = "AT-";

        templateList(stato, AEStatuto.austria, paginaWiki, isoTag, 1, 2, 2);
    }


    /**
     * Länder tedeschi (16) <br>
     */
    public void germania() {
        String paginaWiki = "ISO_3166-2:DE";
        Stato stato = statoLogic.getGermania();
        String isoTag = "DE-";

        templateList(stato, AEStatuto.germania, paginaWiki, isoTag, 1, 2, 2);
    }


    /**
     * Comunità spagnole <br>
     */
    public void spagna() {
        String paginaWiki = "ISO_3166-2:ES";
        Stato stato = statoLogic.getSpagna();
        String isoTag = "ES-";

        templateList(stato, AEStatuto.spagna, paginaWiki, isoTag, 1, 2, 2);
    }


    /**
     * Distretti portoghesi (20) <br>
     */
    public void portogallo() {
        String paginaWiki = "ISO_3166-2:PT";
        Stato stato = statoLogic.getPortogallo();

        //--18 distretti
        dueColonne(stato, AEStatuto.portogalloDistretto, paginaWiki, 1, 2, 2, 3);

        //--2 regioni autonome
        dueColonne(stato, AEStatuto.portogalloRegione, paginaWiki, 2, 2, 2, 3);
    }


    /**
     * Regioni belghe (3) <br>
     */
    public void belgio() {
        String paginaWiki = "ISO_3166-2:BE";
        Stato stato = statoLogic.getBelgio();
        String isoTag = "BE-";

        templateList(stato, AEStatuto.belgio, paginaWiki, isoTag, 1, 2, 2);
    }


    /**
     * Province olandesi (12) <br>
     */
    public void olanda() {
        String paginaWiki = "ISO_3166-2:NL";
        Stato stato = statoLogic.getOlanda();
        String isoTag = "NL-";

        templateList(stato, AEStatuto.olanda, paginaWiki, isoTag, 1, 2, 2);
    }


    /**
     * Regioni croate (21) <br>
     */
    public void croazia() {
        String paginaWiki = "ISO_3166-2:HR";
        Stato stato = statoLogic.getCroazia();

        dueColonne(stato, AEStatuto.croazia, paginaWiki, 1, 2, 1, 2);
    }


    /**
     * Distretti albanesi (36) <br>
     */
    public void albania() {
        String paginaWiki = "ISO_3166-2:AL";
        Stato stato = statoLogic.getAlbania();

        dueColonne(stato, AEStatuto.albania, paginaWiki, 1, 1, 1, 2);
    }


    /**
     * Distretti greci (64) <br>
     */
    public void grecia() {
        String paginaWiki = "ISO_3166-2:GR";
        Stato stato = statoLogic.getGrecia();

        //--13 periferie
        dueColonne(stato, AEStatuto.greciaPeriferia, paginaWiki, 1, 2, 1, 2);

        //--51 prefetture (Attica è doppia)
        dueColonne(stato, AEStatuto.greciaPrefettura, paginaWiki, 2, 2, 2, 3);
    }


    /**
     * Regioni ceche (14) <br>
     */
    public void cechia() {
        String paginaWiki = "ISO_3166-2:CZ";
        Stato stato = statoLogic.getCechia();
        String isoTag = "CZ-";

        templateList(stato, AEStatuto.cechia, paginaWiki, isoTag, 1, 2, 3);
    }


    /**
     * Regioni slovacche (8) <br>
     */
    public void slovacchia() {
        String paginaWiki = "ISO_3166-2:SK";
        Stato stato = statoLogic.getSlovacchia();

        dueColonne(stato, AEStatuto.slovacchia, paginaWiki, 1, 2, 1, 2);
    }


    /**
     * Province ungheresi (19) <br>
     */
    public void ungheria() {
        String paginaWiki = "ISO_3166-2:HU";
        Stato stato = statoLogic.getUngheria();
        String isoTag = "CZ-";

        templateList(stato, AEStatuto.ungheria, paginaWiki, isoTag, 1, 2, 2);
    }


    /**
     * Distretti rumeni (42) <br>
     */
    public void romania() {
        String paginaWiki = "ISO_3166-2:RO";
        Stato stato = statoLogic.getRomania();

        dueColonne(stato, AEStatuto.romaniaDistretto, paginaWiki, 1, 2, 2, 3);
        dueColonne(stato, AEStatuto.romaniaCapitale, paginaWiki, 2, 2, 2, 3);
    }


    /**
     * Distretti bulgari (28) <br>
     */
    public void bulgaria() {
        String paginaWiki = "ISO_3166-2:BG";
        Stato stato = statoLogic.getBulgaria();

        dueColonne(stato, AEStatuto.bulgaria, paginaWiki, 1, 2, 2, 3);
    }


    /**
     * Voivodati polacchi (16) <br>
     */
    public void polonia() {
        String paginaWiki = "ISO_3166-2:PL";
        Stato stato = statoLogic.getPolonia();

        dueColonne(stato, AEStatuto.polonia, paginaWiki, 1, 2, 2, 3);
    }


    /**
     * Regioni danesi (5) <br>
     */
    public void danimarca() {
        String paginaWiki = "ISO_3166-2:DK";
        Stato stato = statoLogic.getDanimarca();

        dueColonne(stato, AEStatuto.danimarca, paginaWiki, 1, 2, 1, 2);
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
     * Costruzione utilizzando un template <br>
     */
    private void templateList(Stato stato, AEStatuto status, String paginaWiki, String isoTag, int posTabella, int rigaIniziale, int numColonna) {
        List<WrapDueStringhe> listaWrap;
        String nome;
        String sigla;
        String iso;

        listaWrap = wiki.getTemplateList(paginaWiki, posTabella, rigaIniziale, numColonna);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, status);
            }
        }
    }


    /**
     * Costruzione utilizzando due colonne <br>
     */
    private void dueColonne(Stato stato, AEStatuto status, String paginaWiki, int posTabella, int rigaIniziale, int numColonnaUno, int numColonnaDue) {
        List<WrapDueStringhe> listaWrap;
        String nome;
        String sigla;
        String iso;

        listaWrap = wiki.getDueColonne(paginaWiki, posTabella, rigaIniziale, numColonnaUno, numColonnaDue);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, status);
            }
        }
    }

}