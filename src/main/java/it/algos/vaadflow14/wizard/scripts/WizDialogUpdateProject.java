package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AEWiz;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

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
    protected void creaLegenda() {
        super.creaLegenda();

        layoutLegenda.add(new Label("Update di un progetto esistente"));
        layoutLegenda.add(new Label("Il modulo vaadflow viene sovrascritto"));
        layoutLegenda.add(new Label("Eventuali modifiche locali vengono perse"));
        layoutLegenda.add(new Label("Il modulo di questo progetto NON viene toccato"));
    }


    /**
     * Crea i checkbox di controllo <br>
     * Spazzola (nella sottoclasse) la Enumeration per aggiungere solo i checkbox adeguati: <br>
     * newProject
     * updateProject
     * newPackage
     * updatePackage
     * Spazzola la Enumeration e regola a 'true' i chekbox secondo il flag 'isAcceso' <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void creaCheckBoxMap() {
        Checkbox unCheckbox;

        for (AEWiz flag : AEWiz.values()) {
            if (flag.isCheckBox() && flag.isUpdateProject()) {
                unCheckbox = new Checkbox(flag.getLabelBox(), flag.isAcceso());
                mappaCheckbox.put(flag.name(), unCheckbox);
            }
        }
    }

}
