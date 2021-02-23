package it.algos.vaadflow14.ui.button;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.value.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.ui.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.*;
import java.util.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: sab, 09-mag-2020
 * Time: 11:27
 * <p>
 * Costruisce un layout (grafico) con i bottoni di comando della view (List) <br>
 * Creata da ALogic.getTopLayout() con appContext.getBean(ATopLayout.class,...); <br>
 * L'istanza viene inserita in topPlacehorder della view (List) SOPRA la Grid <br>
 * // * Nell' implementazione standard di default presenta solo il bottone 'New' <br>
 * <p>
 * Il costruttore può avere 4 parametri:
 * A - (obbligatorio) la AILogic con cui regolare i listener per l'evento/azione da eseguire
 * B - (semi-obbligatorio) una serie di bottoni standard, sotto forma di List<AEButton>
 * C - (facoltativo) una mappa di combobox di selezione e filtro, LinkedHashMap<String, ComboBox>
 * D - (facoltativo) una serie di bottoni non-standard, sotto forma di List<Button>
 * <p>
 * <p>
 * <p>
 * // * Se il costruttore è senza il service, i listener vengono regolati successivamente
 * // * I listeners dei bottoni non standard devono essere regolati singolarmente dalla Logic chiamante <br>
 * I listeners dei bottoni vengono regolati dalla AILogic chiamante <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ATopLayout extends AButtonLayout {

    private AILogic entityLogic;

    private List<AEButton> listaAEBottoni;

    private WrapButtons wrapper;

    /**
     * Costruttore senza parametri<br>
     */
    public ATopLayout() {
        this(null);
    }

    /**
     * Costruttore <br>
     * La classe viene costruita con appContext.getBean(ATopLayout.class, wrapButtons) in ALogic <br>
     *
     * @param wrapper di informazioni tra 'logic' e 'view'
     */
    public ATopLayout(final WrapButtons wrapper) {
        this.wrapper = wrapper;
        this.entityLogic = wrapper != null ? wrapper.getEntityLogic() : null;
        this.listaAEBottoni = wrapper != null ? wrapper.getListaAEBottoni() : null;
    }

    //    /**
    //     * Costruttore senza bottoni <br>
    //     * La classe viene costruita con appContext.getBean(ATopLayout.class, entityLogic) in ALogic <br>
    //     * Se l'istanza viene creata SENZA una lista di bottoni, presenta solo il bottone 'New' <br>
    //     *
    //     * @param entityLogic a cui rinviare l'evento/azione da eseguire
    //     */
    //    public ATopLayout(final AILogic entityLogic) {
    //        this(entityLogic, (List<AEButton>) Collections.singletonList(AEButton.nuovo));
    //    }

    //    /**
    //     * Costruttore con una serie di bottoni standard, sotto forma di List<AEButton> <br>
    //     * La classe viene costruita con appContext.getBean(ATopLayout.class, entityLogic, listaAEBottoni) in ALogic <br>
    //     *
    //     * @param entityLogic    a cui rinviare l'evento/azione da eseguire
    //     * @param listaAEBottoni una serie di bottoni standard
    //     */
    //    public ATopLayout(final AILogic entityLogic, final List<AEButton> listaAEBottoni) {
    //        this.entityLogic = entityLogic;
    //        super.service = entityLogic;
    //        this.listaAEBottoni = listaAEBottoni;
    //    }


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le (eventuali) istanze @Autowired <br>
     * Questo metodo viene chiamato subito dopo che il framework ha terminato l' init() implicito <br>
     * del costruttore e PRIMA di qualsiasi altro metodo <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l' ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        this.checkParametri();
        this.creaBottoni();
    }

    protected void checkParametri() {
        String message;

        if (wrapper == null) {
            logger.error("Creazione di un TopLayout senza WrapButtons", this.getClass(), "checkParametri");
        }

        if (entityLogic == null) {
            logger.error("Manca la entityLogic nel WrapButtons", this.getClass(), "checkParametri");
        }

        if (listaAEBottoni == null) {
            message = String.format("Non ci sono bottoni nella view (List) chiamata da %s", entityLogic.getClass().getSimpleName());
            logger.info(message, this.getClass(), "checkParametri");
        }

    }

    protected void creaBottoni() {
        Button button;

        if (listaAEBottoni != null && listaAEBottoni.size() > 0) {
            for (AEButton bottone : listaAEBottoni) {
                button = FactoryButton.get(bottone);
                button.addClickListener(event -> performAction(bottone.action));
                this.add(button);
            }
        }
    }

    /**
     * Costruisce il gruppo di ricerca testuale <br>
     * 1) nessuna <br>
     * 2) campo editText di selezione per una property specificata in searchProperty <br>
     * 3) bottone per aprire un dialogo di selezione <br>
     * DEVE essere sovrascritto. <br>
     */
    @Override
    protected void fixSearch() {
        Button button;
        switch (searchType) {
            case nonUsata:
                break;
            case editField:
                this.fixSearchField();
                break;
            case dialog:
                button = FactoryButton.get(AEButton.searchDialog);
                this.mappaBottoni.put(AEButton.searchDialog, button);
                break;
            default:
                logger.warn("Switch - caso non definito", this.getClass(), "creaSearch");
                break;
        }
    }


    /**
     * Campo EditSearch predisposto su un'unica property. <br>
     */
    private void fixSearchField() {
        HorizontalLayout layout;
        String placeHolder = text.isValid(searchProperty) ? text.primaMaiuscola(searchProperty) + "..." : "Cerca...";
        String toolTip = "Caratteri iniziali della ricerca" + (text.isValid(searchProperty) ? " nel campo '" + searchProperty + "'" : VUOTA);
        searchField = new TextField(VUOTA, placeHolder);
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.getElement().setAttribute("title", toolTip);
        searchField.addClassName("view-toolbar__search-field");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);

        layout = new HorizontalLayout(searchField);
        layout.setSpacing(false);

        //--bottone piccolo per pulire il campo testo di ricerca
        if (AEPreferenza.usaSearchClearButton.is()) {
            //        buttonClearFilter = new Button(new Icon("lumo", "cross"));
            buttonClearFilter = new Button(VaadinIcon.CLOSE_SMALL.create());
            buttonClearFilter.setEnabled(false);
            buttonClearFilter.getElement().setAttribute("title", "Pulisce il campo di ricerca");
            layout.add(buttonClearFilter);
        }

        this.add(layout);
    }


    /**
     * Aggiunta di tutti i listener <br>
     * Chiamato da AEntityService <br>
     * Recupera le istanze concrete dei bottoni dalla mappa <AEButton, Button> <br>
     * Aggiunge il listener al bottone, specificando l'azione di ritorno associata al singolo bottone <br>
     *
     * @param service a cui rinviare l'evento/azione da eseguire
     */
    @Override
    public void setAllListener(AILogic service) {
        super.setAllListener(service);

        if (searchField != null) {
            searchField.addValueChangeListener(event -> {
                if (buttonClearFilter != null) {
                    if (searchField.getValue().isEmpty()) {
                        buttonClearFilter.setEnabled(false);
                    }
                    else {
                        buttonClearFilter.setEnabled(true);
                    }
                }
                performAction(AEAction.searchField, searchField.getValue());
            });
        }

        if (buttonClearFilter != null) {
            buttonClearFilter.addClickListener(event -> searchField.clear());
        }

        if (array.isAllValid(mappaComboBox)) {
            for (Map.Entry<String, ComboBox> mappaEntry : mappaComboBox.entrySet()) {
                mappaEntry.getValue().addValueChangeListener(event -> performAction(AEAction.valueChanged));
            }
        }
    }

}
