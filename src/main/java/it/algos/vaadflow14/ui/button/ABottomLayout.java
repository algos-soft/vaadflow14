package it.algos.vaadflow14.ui.button;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEColor;
import it.algos.vaadflow14.ui.enumeration.AEButton;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 20-mag-2020
 * Time: 07:21
 * Placeholder SOTTO il Form <br>
 * Contenuto variabile in base ad una lista di componenti inviata con un wrapper nel costruttore. Nell'ordine: <br>
 * - con un bottone per annullare/tornare indietro (obbligatorio) <br>
 * - con un bottone di conferma dell'operazione (facoltativi) <br>
 * - con eventuali altri bottoni specifici (facoltativi) <br>
 * <p>
 * I bottoni vengono passati nel wrapper come enumeration standard. <br>
 * Se si usano bottoni non standard vengono inseriti per ultimi <br>
 * I listeners dei bottoni non standard devono essere regolati singolarmente dalla Logic chiamante <br>
 * Se il costruttore arriva SENZA parametri, mostra solo quanto previsto nelle preferenze <br>
 * La classe viene costruita con appContext.getBean(ABottomLayout.class, wrapButtons) in AEntityService <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ABottomLayout extends AButtonLayout {


    /**
     * Costruttore base senza parametri <br>
     * La classe viene costruita con appContext.getBean(AxxxLayout.class) in AEntityService <br>
     */
    @Deprecated
    public ABottomLayout() {
    }


    /**
     * Costruttore base con parametro wrapper di passaggio dati <br>
     * La classe viene costruita con appContext.getBean(xxxLayout.class, wrapButtons) in AEntityService <br>
     *
     * @param wrapButtons wrap di informazioni
     */
    public ABottomLayout(WrapButtonsOld wrapButtons) {
        super(wrapButtons);
    }


    /**
     * Creazione iniziale di eventuali properties indispensabili per l'istanza <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Metodo private che NON può essere sovrascritto <br>
     */
    protected void fixProperties() {
        this.fixPreferenze();
        this.fixBottoni();
        this.creaBottoni();
    }


    /**
     * Preferenze per avere dei bottoni standard se è nullo il parametro listaBottoni <br>
     * <p>
     * Deve essere sovrascritto, per specificare i bottoni specifici della sottoclasse <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
        this.getElement().getStyle().set("background-color", AEColor.grigio1.getEsadecimale());
    }


    /**
     * Usa la lista iniziali se esiste. <br>
     * Altrimenti seleziona i bottoni in base al parametro operationForm <br>
     */
    protected void fixBottoni() {
        if (array.isEmpty(iniziali) && operationForm != null) {
            iniziali = new ArrayList<>();
            switch (operationForm) {
                case listNoForm:
                    break;
                case edit:
                    iniziali.add(AEButton.back);
                    iniziali.add(AEButton.registra);
                    iniziali.add(AEButton.delete);
                    break;
                case editNoDelete:
                case addNew:
                    iniziali.add(AEButton.back);
                    iniziali.add(AEButton.registra);
                    break;
                case showOnly:
                    iniziali.add(AEButton.back);
                    break;
                default:
                    logger.warn("Switch - caso non definito", this.getClass(), "fixBottoni");
                    break;
            }
        }
    }


    /**
     * Crea i bottoni partendo dalla enumeration standard <br>
     * Usa la lista iniziali. <br>
     * Prevede: text, icona, theme, title, class <br>
     * Aggiunge alla GUI i bottoni creati <br>
     * Aggiunge alla mappa i bottoni creati <br>
     * Ai bottoni NON viene aggiunto il listener che viene regolato successivamente <br>
     */
    protected void creaBottoni() {
        Button button;

        if (iniziali != null) {
            for (AEButton aeButton : iniziali) {
                button = FactoryButton.get(aeButton);
                this.add(button);
                mappaBottoni.put(aeButton, button);
            }
        }

        if (specifici != null) {
            for (Button bottone : specifici) {
                this.add(bottone);
            }
        }

        if (finali != null) {
            for (AEButton aeButton : finali) {
                button = FactoryButton.get(aeButton);
                this.add(button);
                mappaBottoni.put(aeButton, button);
            }
        }
    }

}
