package it.algos.vaadflow14.backend.wrapper;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.ui.interfaces.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 26-mag-2021
 * Time: 18:57
 * Wrap di informazioni passato da ALogic alla creazione di ATopLayout <br>
 * La ALogic mantiene lo stato ed elabora informazioni che verranno usate dal ATopLayout <br>
 * Questo wrapper viene costruito da ALogic e contiene:
 * A - (obbligatorio) la AILogic con cui regolare i listener per l'evento/azione da eseguire
 * B - (semi-obbligatorio) una serie di bottoni standard, sotto forma di List<AEButton>
 * C - (facoltativo) un wrapper di dati per il dialogo/campo di ricerca
 * D - (facoltativo) una mappa di combobox di selezione e filtro, Map<String, ComboBox>
 * E - (facoltativo) una mappa di checkbox di filtro, Map<String, CheckBox>
 * F - (facoltativo) una serie di bottoni non-standard, sotto forma di List<Button>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WrapTop {

    /**
     * A - (obbligatorio) la AILogic con cui regolare i listener per l'evento/azione da eseguire
     */
    private AILogic entityLogic;

    /**
     * B - (semi-obbligatorio) una serie di bottoni standard, sotto forma di List<AIButton>
     */
    private List<AIButton> listaABottoni;

    /**
     * C - (facoltativo) wrapper di dati per il dialogo/campo di ricerca
     */
    private WrapSearch wrapSearch;

    /**
     * D - (facoltativo) una mappa di combobox di selezione e filtro, Map<String, ComboBox>
     */
    private Map<String, ComboBox> mappaComboBox;

    /**
     * E - (facoltativo) una mappa di checkbox di selezione e filtro, Map<String, Checkbox>
     */
    private Map<String, Checkbox> mappaCheckBox;

    /**
     * F - (facoltativo) una serie di bottoni non-standard, sotto forma di List<Button>
     */
    private List<Button> listaBottoniSpecifici;

    /**
     * Property per il numero di bottoni nella prima riga sopra la Grid (facoltativa)
     */
    private int maxNumeroBottoniPrimaRiga;

    /**
     * Mappa di componenti di selezione e filtro <br>
     */
    private Map<String, Object> mappaComponenti;


    public WrapTop(final AILogic entityLogic, final Map<String, Object> mappaComponenti,int maxNumeroBottoniPrimaRiga) {
        this.entityLogic = entityLogic;
        this.mappaComponenti = mappaComponenti;
        this.maxNumeroBottoniPrimaRiga = maxNumeroBottoniPrimaRiga;
    }

    /**
     * Costruttore con tutti i parametri <br>
     * Costruito con appContext.getBean(WrapTop.class, entityLogic, listaAEBottoni, wrapSearch, numBottoni) <br>
     *
     * @param entityLogic               a cui rinviare l'evento/azione da eseguire
     * @param listaABottoni             una serie di bottoni standard
     * @param wrapSearch                wrapper di dati per la ricerca
     * @param mappaComboBox             di selezione e filtro
     * @param mappaCheckBox             di selezione e filtro
     * @param listaBottoniSpecifici     non-standard
     * @param maxNumeroBottoniPrimaRiga nella prima riga sopra la Grid
     */
    public WrapTop(final AILogic entityLogic, final List<AIButton> listaABottoni, final WrapSearch wrapSearch, final Map<String, ComboBox> mappaComboBox, final Map<String, Checkbox> mappaCheckBox, final List<Button> listaBottoniSpecifici, final int maxNumeroBottoniPrimaRiga) {
        this.entityLogic = entityLogic;
        this.listaABottoni = listaABottoni;
        this.wrapSearch = wrapSearch;
        this.mappaComboBox = mappaComboBox;
        this.mappaCheckBox = mappaCheckBox;
        this.listaBottoniSpecifici = listaBottoniSpecifici;
        this.maxNumeroBottoniPrimaRiga = maxNumeroBottoniPrimaRiga;
    }

    public AILogic getEntityLogic() {
        return entityLogic;
    }

    public List<AIButton> getListaABottoni() {
        return listaABottoni;
    }

    public WrapSearch getWrapSearch() {
        return wrapSearch;
    }

    public Map<String, ComboBox> getMappaComboBox() {
        return mappaComboBox;
    }

    public Map<String, Checkbox> getMappaCheckBox() {
        return mappaCheckBox;
    }

    public List<Button> getListaBottoniSpecifici() {
        return listaBottoniSpecifici;
    }

    public int getMaxNumeroBottoniPrimaRiga() {
        return maxNumeroBottoniPrimaRiga;
    }

    public Map<String, Object> getMappaComponenti() {
        return mappaComponenti;
    }

}
