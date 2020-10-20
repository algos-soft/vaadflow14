package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AEWiz;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.wizard.scripts.WizCost.NAME_VAADFLOW;
import static it.algos.vaadflow14.wizard.scripts.WizCost.VAADFLOW_STANDARD;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-apr-2020
 * Time: 09:52
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogUpdateProject extends WizDialog {


    /**
     * Apertura del dialogo <br>
     */
    public void open(WizRecipient wizRecipient) {
        super.wizRecipient = wizRecipient;
        super.isNuovoProgetto = false;
        super.titoloCorrente = new H3();

        super.inizia();
    }


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        topLayout = fixSezione("Modifica progetto", "green");
        this.add(topLayout);

        topLayout.add(text.getLabelGreenBold("Update del modulo base di questo progetto"));
        topLayout.add(text.getLabelGreenBold("Il modulo " + NAME_VAADFLOW + " viene sovrascritto"));
        topLayout.add(text.getLabelGreenBold("I sorgenti sono in  " + VAADFLOW_STANDARD));
        topLayout.add(text.getLabelGreenBold("Eventuali modifiche locali vengono perse"));
        topLayout.add(text.getLabelGreenBold("Il modulo specifico " + nameTargetProject + " di questo progetto NON viene toccato"));
        topLayout.add(text.getLabelRedBold("Seleziona le cartelle/files da aggiornare"));
    }


    /**
     * Crea i checkbox di controllo <br>
     * Spazzola (nella sottoclasse) la Enumeration per aggiungere solo i checkbox adeguati: <br>
     * newProject
     * updateProject
     * newPackage
     * updatePackage
     * Spazzola la Enumeration e regola a 'true' i chekBox secondo il flag 'isAcceso' <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void creaCheckBoxLayout() {
        super.creaCheckBoxLayout();
        Checkbox unCheckbox;

        //--spenge tutti i checkbox escluso flagFlow
        for (AEWiz flag : AEWiz.values()) {
            if (flag.isCheckBox()) {
                flag.setAcceso(false);
            }
        }
        AEWiz.flagFlow.setAcceso(true);

        for (AEWiz flag : AEWiz.values()) {
            if (flag.isCheckBox() && flag.isUpdateProject()) {
                unCheckbox = new Checkbox(flag.getLabelBox(), flag.isAcceso());
                mappaCheckbox.put(flag.name(), unCheckbox);
            }
        }

        super.addCheckBoxMap();
    }


    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
    }

}
