package it.algos.vaadflow14.ui.button;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.value.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.wrapper.*;
import it.algos.vaadflow14.ui.enumeration.*;
import it.algos.vaadflow14.ui.interfaces.*;
import it.algos.vaadflow14.ui.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;


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

    /**
     * Valore di default del numero di bottoni nella prima riga sopra la Grid
     */
    private static int NUMBER_BUTTONS_STANDARD = 4;

    /**
     * Property per il numero di bottoni nella prima riga sopra la Grid (facoltativa)
     */
    protected int maxNumeroBottoniPrimaRiga;


    /**
     * Costruttore <br>
     * La classe viene costruita con appContext.getBean(ATopLayout.class, wrapper) in ALogic <br>
     *
     * @param wrapper di informazioni tra 'logic' e 'view'
     */
    public ATopLayout(final WrapTop wrapper) {
        super(wrapper);
    }


    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        this.maxNumeroBottoniPrimaRiga = wrapper != null ? wrapper.getMaxNumeroBottoniPrimaRiga() : 0;
        this.maxNumeroBottoniPrimaRiga = maxNumeroBottoniPrimaRiga > 0 ? maxNumeroBottoniPrimaRiga : NUMBER_BUTTONS_STANDARD;
    }

    /**
     * Qui va tutta la logica iniziale della view <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void initView() {
        super.initView();

        super.primaRiga = new AHorizontalLayout();
        super.secondaRiga = new AHorizontalLayout();
        primaRiga.setAlignItems(Alignment.CENTER);
        secondaRiga.setAlignItems(Alignment.CENTER);
    }

    //    protected void creaAll() {
    //        Button button;
    //        ComboBox combo;
    //        Checkbox check;
    //
    //        if (mappaComponenti != null && mappaComponenti.size() > 0) {
    //            for (String key : mappaComponenti.keySet()) {
    //                Object obj = mappaComponenti.get(key);
    //
    //                if (obj instanceof AIButton) {
    //                    button = ((AIButton) obj).get();
    //                    button.addClickListener(event -> performAction(((AIButton) obj).getAction()));
    //                    this.addComp(button);
    //                }
    //
    //                if (obj instanceof ComboBox) {
    //                    combo = ((ComboBox) obj);
    //                    this.addComp(combo);
    //                }
    //
    //                if (obj instanceof Checkbox) {
    //                    check = ((Checkbox) obj);
    //                    this.addComp(check);
    //                }
    //            }
    //        }
    //    }

    //    @Override
    //    protected void creaAllBottoni() {
    //
    //        if (listaTop != null && listaTop.size() > 0) {
    //            for (Component comp : listaTop) {
    //                this.addComp(comp);
    //            }
    //        }
    //        if (listaAEBottoni != null && listaAEBottoni.size() > 0) {
    //            for (AIButton bottone : listaAEBottoni) {
    //                if (bottone == AEButton.searchDialog) {
    //                    addSearchGroup();
    //                    //-- tutti i combobox@todo cosi pero non mette i combobox se manca il campo search
    //                    if (mappaComboBox != null && mappaComboBox.size() > 0) {
    //                        for (Map.Entry<String, ComboBox> entry : mappaComboBox.entrySet()) {
    //                            this.addCombo(entry.getValue());
    //                        }
    //                    }
    //                }
    //                else {
    //                    this.addBottoneEnum(bottone);
    //                }
    //            }
    //        }
    //        if (mappaCheckBox != null && mappaCheckBox.size() > 0) {
    //            for (Map.Entry<String, Checkbox> entry : mappaCheckBox.entrySet()) {
    //                this.addCheck(entry.getValue());
    //            }
    //        }
    //
    //        if (listaBottoniSpecifici != null && listaBottoniSpecifici.size() > 0) {
    //            for (Button bottone : listaBottoniSpecifici) {
    //                this.addBottoneSpecifico(bottone);
    //            }
    //        }
    //    }

    //    /**
    //     * Preferenze usate da questa 'view' <br>
    //     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
    //     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
    //     */
    //    @Override
    //    protected void fixPreferenze() {
    //        this.maxNumeroBottoniPrimaRiga = wrapper != null ? wrapper.getMaxNumeroBottoniPrimaRiga() : 0;
    //        this.maxNumeroBottoniPrimaRiga = maxNumeroBottoniPrimaRiga > 0 ? maxNumeroBottoniPrimaRiga : NUMBER_BUTTONS_STANDARD;
    //    }

    //    protected void addBottoneEnum(AIButton aeButton) {
    //        Button button = getButton(aeButton);
    //
    //        if (primaRiga.getComponentCount() < maxNumeroBottoniPrimaRiga) {
    //            primaRiga.add(button);
    //        }
    //        else {
    //            secondaRiga.add(button);
    //        }
    //    }

    //    protected void addSearchGroup() {
    //        HorizontalLayout layout = null;
    //        layout = fixSearchGroup();
    //        if (primaRiga.getComponentCount() < maxNumeroBottoniPrimaRiga) {
    //            primaRiga.add(layout);
    //        }
    //        else {
    //            secondaRiga.add(layout);
    //        }
    //    }

    //    protected void addCombo(ComboBox combo) {
    //        if (primaRiga.getComponentCount() < maxNumeroBottoniPrimaRiga) {
    //            primaRiga.add(combo);
    //        }
    //        else {
    //            secondaRiga.add(combo);
    //        }
    //    }

    //    protected void addCheck(Checkbox check) {
    //        if (primaRiga.getComponentCount() < maxNumeroBottoniPrimaRiga) {
    //            primaRiga.add(check);
    //        }
    //        else {
    //            secondaRiga.add(check);
    //        }
    //    }

    //    protected void addBottoneSpecifico(Button bottone) {
    //        if (primaRiga.getComponentCount() < maxNumeroBottoniPrimaRiga) {
    //            primaRiga.add(bottone);
    //        }
    //        else {
    //            secondaRiga.add(bottone);
    //        }
    //    }

    //    /**
    //     * Qui va tutta la logica iniziale della view <br>
    //     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
    //     */
    //    @Override
    //    protected void initView() {
    //        super.initView();
    //
    //        super.primaRiga = new AHorizontalLayout();
    //        super.secondaRiga = new AHorizontalLayout();
    //    }

    protected void creaAll() {
        Button button;
        ComboBox combo;
        Checkbox check;

        if (mappaComponenti != null && mappaComponenti.size() > 0) {
            for (String key : mappaComponenti.keySet()) {
                Object obj = mappaComponenti.get(key);

                if (obj instanceof AIButton) {
                    if (obj == AEButton.searchDialog) {
                        this.addComp(fixSearchGroup());
                    }
                    else {
                        button = ((AIButton) obj).get();
                        button.addClickListener(event -> performAction(((AIButton) obj).getAction()));
                        this.addComp(button);
                    }
                }

                if (obj instanceof ComboBox) {
                    combo = ((ComboBox) obj);
                    this.addComp(combo);
                }

                if (obj instanceof Checkbox) {
                    check = ((Checkbox) obj);
                    String fieldName=check.getLabel();
                    IndeterminateCheckbox indeterminateCheckbox = new IndeterminateCheckbox(text.primaMaiuscola(check.getLabel()));
                    indeterminateCheckbox.addValueChangeListener(event -> {
                        if (indeterminateCheckbox.isIndeterminate()) {
                            // new "value" is indeterminate. (Pseudo "null")
                            // warning: event.getValue() and event.isFromClient() both return false here!
                            performAction(AEAction.check, fieldName,null);
                        }
                        else if (event.getValue()) {
                            // new value is true
                            performAction(AEAction.check, fieldName,true);
                        }
                        else {
                            // new value is false
                            performAction(AEAction.check, fieldName,false);
                        }
                    });
                    this.addComp(indeterminateCheckbox);
                }
            }
        }
    }

    protected void addComp(Component comp) {
        if (primaRiga.getComponentCount() < maxNumeroBottoniPrimaRiga) {
            primaRiga.add(comp);
        }
        else {
            secondaRiga.add(comp);
        }
    }

    @Override
    protected void addAllToView() {
        if (primaRiga.getComponentCount() > 0) {
            this.add(primaRiga);
        }
        if (secondaRiga.getComponentCount() > 0) {
            this.add(secondaRiga);
        }
    }

    //    protected HorizontalLayout fixSearchAndCombo() {
    //        HorizontalLayout layout = new HorizontalLayout();
    //        layout.add(new Button("Pippo"));
    //        return layout;
    //    }


    /**
     * Costruisce il gruppo di ricerca testuale <br>
     * 1) nessuna <br>
     * 2) campo editText di selezione per una property specificata in searchProperty <br>
     * 3) bottone per aprire un dialogo di selezione <br>
     * DEVE essere sovrascritto. <br>
     */
    protected HorizontalLayout fixSearchGroup() {
        HorizontalLayout layout = null;
        Button button;

        layout = this.fixSearchField();

        //        switch (searchType) {
        //            case nonUsata:
        //                break;
        //            case editField:
        //                layout = this.fixSearchField();
        //                break;
        //            case dialog:
        //                button = FactoryButton.get(AEButton.searchDialog);
        //                this.mappaBottoni.put(AEButton.searchDialog, button);
        //                break;
        //            default:
        //                logger.warn("Switch - caso non definito", this.getClass(), "creaSearch");
        //                break;
        //        }

        return layout;
    }

    //        if (array.isAllValid(mappaBottoni)) {
    //            for (Map.Entry<AEButton, Button> mappaEntry : mappaBottoni.entrySet()) {
    //                mappaEntry.getValue().addClickListener(event -> performAction(mappaEntry.getKey().action));
    //            }
    //        }


    /**
     * Campo EditSearch predisposto su un'unica property. <br>
     */
    private HorizontalLayout fixSearchField() {
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

        return layout;
    }


    /**
     * Aggiunta di tutti i listener <br>
     * Chiamato da AEntityService <br>
     * Recupera le istanze concrete dei bottoni dalla mappa <AEButton, Button> <br>
     * Aggiunge il listener al bottone, specificando l'azione di ritorno associata al singolo bottone <br>
     *
     * @param entityLogic a cui rinviare l'evento/azione da eseguire
     */
    @Override
    public void setAllListener(AILogic entityLogic) {
        super.setAllListener(entityLogic);

        if (mappaComponenti != null && mappaComponenti.size() > 0) {
            for (String key : mappaComponenti.keySet()) {
                Object obj = mappaComponenti.get(key);

                if (obj instanceof ComboBox) {
                    ComboBox combo = (ComboBox) obj;
                    combo.addValueChangeListener(event -> performAction(AEAction.valueChanged, key, combo.getValue()));
                }

                //                if (obj instanceof Checkbox) {
                //                    Checkbox check = ((Checkbox) obj);
                //                    check.addClickListener(event -> performAction(AEAction.click, key, check.getValue()));
                //                }
            }
        }

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
    }

}
