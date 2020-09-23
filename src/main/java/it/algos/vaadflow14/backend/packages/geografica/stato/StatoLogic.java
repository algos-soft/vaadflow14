package it.algos.vaadflow14.backend.packages.geografica.stato;

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
import it.algos.vaadflow14.backend.packages.preferenza.AEPreferenza;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.TRE_PUNTI;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 06:54
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Stato ; <br>
 * 2) StaticContextAccessor.getBean(Stato.class) (senza parametri) <br>
 * 3) appContext.getBean(Stato.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatoLogic extends ALogic {


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
    public StatoLogic() {
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
    public StatoLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Stato.class;
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
        super.wikiPageTitle = "ISO_3166-1";
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

        blue.add("Stati del mondo. Codifica secondo ISO 3166-1");
        blue.add("Recuperati dalla pagina wiki: " + wikiPageTitle);
        blue.add("Codici: numerico, alfa-due, alfa-tre e ISO locale");
        blue.add("Ordinamento alfabetico: prima Italia, UE e poi gli altri");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente il searchField ");
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
    //            lista.add("Stati del mondo. Codifica secondo ISO 3166-1");
    //            lista.add("Recuperati dalla pagina wiki: " + wikiPageTitle);
    //            lista.add("Codici: numerico, alfa-due, alfa-tre e ISO locale");
    //            lista.add("Ordinamento alfabetico: prima Italia, UE e poi gli altri ");
    //        }
    //
    //        if (typeVista == AEVista.form) {
    //            lista.add("Scheda NON modificabile");
    //            lista.add("Stato codificato ISO 3166-1");
    //        }
    //
    //        return lista;
    //    }


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
    public Stato findById(String keyID) {
        return (Stato) super.findById(keyID);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param ordine   di presentazione nel popup/combobox (obbligatorio, unico)
     * @param stato    (obbligatorio, unico)
     * @param ue       appartenenza all' unione europea (obbligatorio)
     * @param numerico di riferimento (obbligatorio)
     * @param alfatre  (obbligatorio, unico)
     * @param alfadue  (obbligatorio, unico)
     * @param locale   (obbligatorio, unico)
     *
     * @return la nuova entity appena creata e salvata
     */
    public Stato crea(int ordine, String stato, boolean ue, String numerico, String alfatre, String alfadue, String locale) {
        return (Stato) checkAndSave(newEntity(ordine, stato, ue, numerico, alfatre, alfadue, locale));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Stato newEntity() {
        return newEntity(0, VUOTA, false, VUOTA, VUOTA, VUOTA, VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine   di presentazione nel popup/combobox (obbligatorio, unico)
     * @param stato    (obbligatorio, unico)
     * @param ue       appartenenza all' unione europea (obbligatorio)
     * @param numerico di riferimento (obbligatorio)
     * @param alfatre  (obbligatorio, unico)
     * @param alfadue  (obbligatorio, unico)
     * @param locale   (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Stato newEntity(int ordine, String stato, boolean ue, String numerico, String alfatre, String alfadue, String locale) {
        Stato newEntityBean = Stato.builderStato()

                .ordine(ordine > 0 ? ordine : getNewOrdine())

                .stato(text.isValid(stato) ? stato : null)

                .ue(ue)

                .numerico(text.isValid(numerico) ? numerico : null)

                .alfatre(text.isValid(alfatre) ? alfatre : null)

                .alfadue(text.isValid(alfadue) ? alfadue : null)

                .locale(text.isValid(locale) ? locale : null)

                .build();

        return (Stato) fixKey(newEntityBean);
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
        String nome;
        int pos = AEStatoEuropeo.values().length;
        int posEuropeo;
        int posCorrente;
        boolean ue;

        List<List<String>> listaStati = wiki.getStati();
        if (array.isValid(listaStati)) {
            for (List<String> riga : listaStati) {
                nome = riga.get(0);
                posEuropeo = AEStatoEuropeo.getPosizione(nome);
                if (posEuropeo > 0) {
                    posCorrente = posEuropeo;
                    ue = true;
                } else {
                    pos++;
                    posCorrente = pos;
                    ue = false;
                }
                crea(posCorrente, nome, ue, riga.get(1), riga.get(2), riga.get(3), riga.get(4));
            }
        }

        return mongo.isValid(entityClazz);
    }


    public ComboBox creaComboStati() {
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
        combo.setValue(getItalia());

        if (AEPreferenza.usaBandiereStati.is()) {
            combo = addBandiere(combo);
        }

        //        combo.setRenderer(new ComponentRenderer<>(entityStato -> {
        //            Div text = new Div();
        //            String sigla = entityStato.getAlfadue().toLowerCase();
        //            text.setText(entityStato.getStato());
        //
        //            Image image = imageService.getBandiera(sigla);
        //            image.setWidth("21px");
        //            image.setHeight("21px");
        //
        //            FlexLayout wrapper = new FlexLayout();
        //            text.getStyle().set("margin-left", "0.5em");
        //            wrapper.add(image, text);
        //            return wrapper;
        //        }));
        return combo;
    }


    public ComboBox addBandiere(ComboBox combo) {
        combo.setRenderer(new ComponentRenderer<>(entityStato -> {
            Div text = new Div();
            String sigla = ((Stato) entityStato).getAlfadue().toLowerCase();
            text.setText(((Stato) entityStato).getStato());

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


    public Stato getItalia() {
        return findById("italia");
    }


    public Stato getFrancia() {
        return findById("francia");
    }


    public Stato getSvizzera() {
        return findById("svizzera");
    }


    public Stato getAustria() {
        return findById("austria");
    }


    public Stato getGermania() {
        return findById("germania");
    }


    public Stato getSpagna() {
        return findById("spagna");
    }


    public Stato getPortogallo() {
        return findById("portogallo");
    }


    public Stato getSlovenia() {
        return findById("slovenia");
    }


    public Stato getBelgio() {
        return findById("belgio");
    }


    public Stato getOlanda() {
        return findById("paesibassi");
    }


    public Stato getCroazia() {
        return findById("croazia");
    }


    public Stato getAlbania() {
        return findById("albania");
    }


    public Stato getGrecia() {
        return findById("grecia");
    }


    public Stato getCechia() {
        return findById("repubblicaceca");
    }


    public Stato getSlovacchia() {
        return findById("slovacchia");
    }


    public Stato getUngheria() {
        return findById("ungheria");
    }


    public Stato getRomania() {
        return findById("romania");
    }


    public Stato getBulgaria() {
        return findById("bulgaria");
    }


    public Stato getPolonia() {
        return findById("polonia");
    }


    public Stato getDanimarca() {
        return findById("danimarca");
    }


}