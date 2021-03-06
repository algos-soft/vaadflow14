package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import it.algos.vaadflow14.wizard.enumeration.AEWizCost;
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
        AEFlag.isProject.set(true);

        super.inizia();
    }


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        String pathWizard;
        String pathProject;
        String pathModuloBase;
        String pathSources;
        topLayout = fixSezione(TITOLO_FEEDBACK_PROGETTO, "green");
        this.add(topLayout);


        if (!AEFlag.isBaseFlow.is()) {
            pathWizard = file.findPathBreve(AEWizCost.pathVaadFlow14Wizard.get(), "algos");
            pathProject = file.findPathBreve(AEWizCost.projectCurrent.get(), "algos");
            pathModuloBase = file.findPathBreve(AEWizCost.pathVaadFlow14Root.get(), "operativi");
            topLayout.add(text.getLabelGreenBold(String.format("Ricopia la directory %s di %s su %s", pathWizard, pathProject, pathModuloBase)));

            pathSources = file.findPathBreve(AEWizCost.pathVaadFlow14WizSources.get(), AEWizCost.dirVaadFlow14.get());
            topLayout.add(text.getLabelGreenBold(String.format("Non modifica la sub-directory %s esistente su %s", pathSources, pathModuloBase)));

            topLayout.add(text.getLabelRedBold("Le modifiche sono irreversibili"));
        }

    }

    protected void creaCheckBoxLayout() {
    }


    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
        confirmButton.setEnabled(true);
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola tutti i valori delle enumeration AEDir, AECheck e EAToken che saranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     */
    @Override
    protected boolean regolazioniFinali() {
        return true;
    }

}
