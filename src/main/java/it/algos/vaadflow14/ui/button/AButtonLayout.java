package it.algos.vaadflow14.ui.button;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.entity.AILogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AESearch;
import it.algos.vaadflow14.backend.service.AArrayService;
import it.algos.vaadflow14.backend.service.ALogService;
import it.algos.vaadflow14.backend.service.ATextService;
import it.algos.vaadflow14.ui.enumerastion.AEButton;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 20-mag-2020
 * Time: 20:42
 * Superclasse astratta per la gestione delle barre di bottoni (sopra e sotto) <br>
 * Contenuto variabile in base ad una lista di bottoni inviata nel costruttore dalla Logic <br>
 * <p>
 * Se il costruttore arriva SENZA parametri, mostra solo quanto previsto nelle preferenze <br>
 */
public abstract class AButtonLayout extends HorizontalLayout {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ATextService text;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected AArrayService array;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected ALogService logger;

    /**
     * Flag di preferenza per selezionare la ricerca testuale:
     * 1) nessuna
     * 2) campo editText di selezione per una property specificata in searchProperty
     * 3) dialogo di selezione
     */
    protected AESearch searchType;

    /**
     * Flag di preferenza per specificare la property della entity su cui effettuare la ricerca <br>
     * Ha senso solo se searchType=EASearch.editField
     */
    protected String searchProperty;

    /**
     * Campo testo editabile per la ricerca <br>
     * Ha senso solo se searchType=EASearch.editField
     */
    protected TextField searchField;

    /**
     * Bottone standard per la pulizia del searchField (opzionale, un flag controlla se mostrarlo o meno) <br>
     */
    protected Button buttonClearFilter;

    /**
     * Riferimento iniettato per la gestione degli eventi/azioni <br>
     */
    protected AILogic service;


    /**
     * Property per la lista di bottoni iniziali (facoltativa, può anche essere costruita utilizzando listaAEBottoniIniziali)
     */
    protected List<AEButton> iniziali;

    /**
     * Property per la lista di bottoni centrali (facoltativa, può anche essere costruita utilizzando listaAEBottoniCentrali)
     */
    protected List<AEButton> centrali;

    /**
     * Property per la lista di bottoni finali (facoltativa, può anche essere costruita utilizzando listaAEBottoniFinali)
     */
    protected List<AEButton> finali;

    /**
     * Mappa dei comboBox (facoltativa, possono esserci o meno) <br>
     * La chiave generalmente è il nome della property della entity con cui costruire il comboBox <br>
     */
    protected LinkedHashMap<String, ComboBox> mappaComboBox;

    /**
     * Property per la lista di bottoni finali (facoltativa)
     */
    protected List<Button> specifici;

    /**
     * Mappa dei bottoni standard (obbligatoria) <br>
     * La chiave è l'enumeration del bottone che contiene riferimenti per gli eventi <br>
     */
    protected Map<AEButton, Button> mappaBottoniStandard;

    /**
     * Property per selezionare i bottoni standard in base al tipo di Form (usata solo in ABottomLayout)
     */
    protected AEOperation operationForm;


    /**
     * Costruttore base senza parametri <br>
     * Non utilizzato. Serve per automatismi di SpringBoot <br>
     */
    public AButtonLayout() {
    }


    /**
     * Costruttore base con parametro wrapper di passaggio dati <br>
     * La classe viene costruita con appContext.getBean(xxxLayout.class, wrapButtons) in AEntityService <br>
     *
     * @param wrapButtons wrap di informazioni
     */
    public AButtonLayout(WrapButtons wrapButtons) {
        this.iniziali = wrapButtons.getIniziali();
        this.searchType = wrapButtons.getWrapSearch().getSearchType();
        this.searchProperty = wrapButtons.getWrapSearch().getSearchProperty();
        this.centrali = wrapButtons.getCentrali();
        this.specifici = wrapButtons.getSpecifici();
        this.mappaComboBox = wrapButtons.getMappaComboBox();
        this.finali = wrapButtons.getFinali();
        this.operationForm = wrapButtons.getOperationForm();
    }


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        this.setMargin(false);
        this.setSpacing(true);
        this.setPadding(false);
        mappaBottoniStandard = new HashMap<AEButton, Button>();

        this.fixProperties();
    }


    /**
     * Creazione iniziale di eventuali properties indispensabili per l'istanza <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Metodo private che NON può essere sovrascritto <br>
     */
    protected void fixProperties() {

        this.fixPreferenze();
        this.fixBottoniIniziali();
        this.fixSearch();
        this.fixBottoniCentrali();
        this.fixBottoniSpecifici();
        this.fixComboboxFiltri();
        this.fixBottoniFinali();

        this.fixMappaBottoni();
    }


    /**
     * Preferenze standard di questa istanza <br>
     * <p>
     * Metodo chiamato dopo init() (implicito del costruttore),
     * postConstruct() (facoltativo) e fixProperties() (facoltativo) <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
    }


    /**
     * Costruisce i bottoni del gruppo iniziale <br>
     * Di default costruisce (come da flag) i bottoni 'delete' e 'reset' <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixBottoniIniziali() {
        this.creaBottoni(iniziali);
    }


    /**
     * Costruisce il gruppo di ricerca testuale <br>
     * 1) nessuna <br>
     * 2) campo editText di selezione per una property specificata in searchProperty <br>
     * 3) bottone per aprire un dialogo di selezione <br>
     * DEVE essere sovrascritto. <br>
     */
    protected void fixSearch() {
    }


    /**
     * Costruisce i bottoni del gruppo centrale <br>
     * Di default costruisce (come da flag) solo il bottone 'new' <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixBottoniCentrali() {
        this.creaBottoni(centrali);
    }


    /**
     * Costruisce i bottoni specifici posti dopo il gruppo centrale <br>
     * Di default non costruisce nessun bottone <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixBottoniSpecifici() {
        if (array.isValid(specifici)) {
            for (Button button : specifici) {
                this.add(button);
            }
        }

    }


    /**
     * Costruisce il gruppo di combobox di selezione e filtro <br>
     */
    protected void fixComboboxFiltri() {
        if (mappaComboBox != null) {
            for (Map.Entry<String, ComboBox> mappaEntry : mappaComboBox.entrySet()) {
                this.add(mappaEntry.getValue());
            }
        }
    }


    /**
     * Costruisce i bottoni del gruppo finale <br>
     * Di default non costruisce nessun bottone <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixBottoniFinali() {
        this.creaBottoni(finali);
    }


    /**
     * Viene creata una mappa interna di bottoni per poterli successivamente recuperare <br>
     * Al bottone NON viene aggiunto il listener che viene regolato successivamente <br>
     */
    protected void fixMappaBottoni() {

    }


    /**
     * Crea i bottoni partendo dalla enumeration standard <br>
     * Prevede: text, icona, theme, title, class <br>
     * Aggiunge alla GUI i bottoni creati <br>
     * Aggiunge alla mappa i bottoni creati <br>
     * Ai bottoni NON viene aggiunto il listener che viene regolato successivamente <br>
     *
     * @param listaEnumeration di bottoni da creare ed aggiungere alla parte grafica
     */
    protected void creaBottoni(List<AEButton> listaEnumeration) {
        Button button;

        if (listaEnumeration != null && mappaBottoniStandard != null) {
            for (AEButton aeButton : listaEnumeration) {
                button = FactoryButton.get(aeButton);
                this.add(button);
                mappaBottoniStandard.put(aeButton, button);
            }
        }
    }


    /**
     * Aggiunta di tutti i listener <br>
     * Chiamato da AEntityService <br>
     * Recupera le istanze concrete dei bottoni dalla mappa <AEButton, Button> <br>
     * Aggiunge il listener al bottone, specificando l'azione di ritorno associata al singolo bottone <br>
     *
     * @param service a cui rinviare l'evento/azione da eseguire
     */
    public void setAllListener(AILogic service) {
        this.service = service;

        if (array.isValid(mappaBottoniStandard)) {
            for (Map.Entry<AEButton, Button> mappaEntry : mappaBottoniStandard.entrySet()) {
                mappaEntry.getValue().addClickListener(event -> performAction(mappaEntry.getKey().action));
            }
        }
    }


    /**
     * Aggiunta di tutti i listener <br>
     * Chiamato da AEntityService <br>
     * Recupera le istanze concrete dei bottoni dalla mappa <AEButton, Button> <br>
     * Aggiunge il listener al bottone, specificando l'azione di ritorno associata al singolo bottone <br>
     *
     * @param service    a cui rinviare l'evento/azione da eseguire
     * @param entityBean da considerare
     */
    public void setAllListener(AILogic service, AEntity entityBean) {
        this.service = service;

        if (array.isValid(mappaBottoniStandard)) {
            for (Map.Entry<AEButton, Button> mappaEntry : mappaBottoniStandard.entrySet()) {
                mappaEntry.getValue().addClickListener(event -> performAction(mappaEntry.getKey().action, entityBean));
            }
        }
    }


    /**
     * Esegue l'azione del bottone. <br>
     * <p>
     * Passa a AEntityService.performAction(azione) <br>
     *
     * @param azione selezionata da eseguire
     */
    public void performAction(AEAction azione) {
        service.performAction(azione);
    }


    /**
     * Esegue l'azione del bottone. <br>
     * <p>
     * Passa a AEntityService.performAction(azione) <br>
     *
     * @param azione           selezionata da eseguire
     * @param searchFieldValue valore corrente del campo editText (solo per List)
     */
    public void performAction(AEAction azione, String searchFieldValue) {
        service.performAction(azione, searchFieldValue);
    }


    /**
     * Esegue l'azione del bottone. <br>
     * <p>
     * Passa a AEntityService.performAction(azione) <br>
     *
     * @param azione     selezionata da eseguire
     * @param entityBean selezionata (solo per Form)
     */
    public void performAction(AEAction azione, AEntity entityBean) {
        service.performAction(azione, entityBean);
    }


    /**
     * Recupera il bottone <br>
     *
     * @param key della mappa interna
     *
     * @return bottone richiesto
     */
    public Button getBottone(String key) {
        Button button = null;

        if (array.isValid(mappaBottoniStandard) && mappaBottoniStandard.get(key) != null) {
            return mappaBottoniStandard.get(key);
        }

        return button;
    }

}
