package it.algos.vaadflow14.ui.button;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.wrapper.*;
import it.algos.vaadflow14.ui.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: sab, 09-mag-2020
 * Time: 14:20
 * Wrap di informazioni passato da ALogic alla creazione di AButtonLayout <br>
 * La ALogic mantiene lo stato ed elabora informazioni che verranno usate dal AButtonLayout <br>
 * Questo wrapper contiene:
 * A - (obbligatorio) la AILogic con cui regolare i listener per l'evento/azione da eseguire
 * B - (semi-obbligatorio) una serie di bottoni standard, sotto forma di List<AEButton>
 * C - (facoltativo) una mappa di combobox di selezione e filtro, LinkedHashMap<String, ComboBox>
 * D - (facoltativo) una serie di bottoni non-standard, sotto forma di List<Button>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WrapButtons {


    /**
     * A - (obbligatorio) la AILogic con cui regolare i listener per l'evento/azione da eseguire
     */
    private AILogic entityLogic;

    /**
     * B - (semi-obbligatorio) una serie di bottoni standard, sotto forma di List<AEButton>
     */
    private List<AEButton> listaAEBottoni;


    /**
     * C - (facoltativo) una mappa di combobox di selezione e filtro, LinkedHashMap<String, ComboBox>
     */
    private LinkedHashMap<String, ComboBox> mappaComboBox;

    /**
     * D - (facoltativo) una serie di bottoni non-standard, sotto forma di List<Button>
     */
    private List<Button> listaBottoni;


    /**
     * Property per le informazioni per la ricerca (facoltativa)
     */
    private WrapSearch wrapSearch;


//    /**
//     * Property per selezionare i bottoni standard in base al tipo di Form (usata solo in ABottomLayout)
//     */
//    private AEOperation operationForm;


    /**
     * Costruttore senza bottoni <br>
     * La classe viene costruita con appContext.getBean(WrapButtons.class, entityLogic) in ALogic <br>
     * Se l'istanza viene creata SENZA una lista di bottoni, presenta solo il bottone 'New' <br>
     *
     * @param entityLogic a cui rinviare l'evento/azione da eseguire
     */
    public WrapButtons(final AILogic entityLogic) {
        this(entityLogic, (List<AEButton>) Collections.singletonList(AEButton.nuovo));
    }


    /**
     * Costruttore con una serie di bottoni standard, sotto forma di List<AEButton> <br>
     * La classe viene costruita con appContext.getBean(WrapButtons.class, entityLogic, listaAEBottoni) in ALogic <br>
     *
     * @param entityLogic    a cui rinviare l'evento/azione da eseguire
     * @param listaAEBottoni una serie di bottoni standard
     */
    public WrapButtons(final AILogic entityLogic, final List<AEButton> listaAEBottoni) {
        this.entityLogic = entityLogic;
        this.listaAEBottoni = listaAEBottoni;
    }

//    public WrapButtons(List<AEButton> iniziali) {
//        this(iniziali, null, null, null, null, null, null);
//    }
//
//
//    public WrapButtons(List<AEButton> iniziali, List<Button> specifici, List<AEButton> finali, AEOperation operationForm) {
//        this(iniziali, null, null, specifici, null, finali, operationForm);
//    }


//    public WrapButtons(List<AEButton> iniziali, WrapSearch wrapSearch, List<AEButton> centrali, List<Button> specifici, LinkedHashMap<String, ComboBox> mappaComboBox, List<AEButton> finali, AEOperation operationForm) {
//        this.iniziali = iniziali;
//        this.wrapSearch = wrapSearch != null ? wrapSearch : new WrapSearch();
//        this.centrali = centrali;
//        this.specifici = specifici;
//        this.mappaComboBox = mappaComboBox;
//        this.finali = finali;
//        this.operationForm = operationForm != null ? operationForm : AEOperation.edit;
//    }


    public AILogic getEntityLogic() {
        return entityLogic;
    }

    public List<AEButton> getListaAEBottoni() {
        return listaAEBottoni;
    }

    public LinkedHashMap<String, ComboBox> getMappaComboBox() {
        return mappaComboBox;
    }

    public List<Button> getListaBottoni() {
        return listaBottoni;
    }

}
