package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.wizard.scripts.WizCost.TITOLO_FEEDBACK_PROGETTO;

/**
 * Project provider
 * Created by Algos
 * User: gac
 * Date: dom, 25-ott-2020
 * Time: 17:57
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogFeedbackWizard extends WizDialog {

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
        topLayout = fixSezione(TITOLO_FEEDBACK_PROGETTO, "green");
        this.add(topLayout);

        topLayout.add(text.getLabelGreenBold("Ricopia su vaadflow14 la directory wizard di questo progetto"));
        topLayout.add(text.getLabelGreenBold("La sub-directory sources di wizard, viene mantenuta"));
        topLayout.add(text.getLabelGreenBold("Le altre sub-directory di wizard su vaadflow, vengono perse"));
        topLayout.add(text.getLabelRedBold("Le modifiche sono irreversibili"));
    }



    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
    }

}
