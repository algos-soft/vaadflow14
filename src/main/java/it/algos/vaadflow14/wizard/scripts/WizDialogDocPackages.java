package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import it.algos.vaadflow14.wizard.enumeration.AEPackage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.LinkedHashMap;

/**
 * Project vaadwiki14
 * Created by Algos
 * User: gac
 * Date: gio, 14-gen-2021
 * Time: 18:58
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogDocPackages extends WizDialog {

    /**
     * Apertura del dialogo <br>
     */
    public void open(WizRecipient wizRecipient) {
        super.wizRecipient = wizRecipient;
        AEFlag.isDocPackages.set(true);

        super.inizia();
    }


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
//        if (AEFlag.isBaseFlow.is()) {
//            logger.error("Non dovrebbe arrivare qui", this.getClass(), "creaTopLayout");
//        }
//        else {
//            topLayout = fixSezione("Doc delle classi standard", "green");
//        }
        topLayout = fixSezione("Doc delle classi standard", "green");
        this.add(topLayout);

        topLayout.add(text.getLabelGreenBold("Update della documentazione iniziale delle classi standard"));
        topLayout.add(text.getLabelRedBold("Seleziona le classi da aggiornare"));
    }

    /**
     * Sezione centrale con la selezione delle classi da modificare <br>
     * Crea i checkbox di controllo <br>
     * Spazzola la Enumeration AEPackage per aggiungere solo i checkbox adeguati: <br>
     * entity
     * logic
     * service
     * list
     * form
     * Spazzola la Enumeration e regola a 'true' i chekBox secondo il flag 'isAcceso' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaCheckBoxLayout() {
        checkBoxLayout = fixSezione("Flags di selezione");
        this.add(checkBoxLayout);
        mappaWizBox = new LinkedHashMap<>();

        //--regola tutti i checkbox
        for (AEPackage pack : AEPackage.values()) {
            mappaWizBox.put(pack.name(), new WizBox(pack));
            mappaWizBox.get(pack.name()).getBox().addValueChangeListener(e -> {
                sincroDoc();
            });
        }

        super.addCheckBoxMap();
        sincroDoc();
    }

    private void sincroDoc() {
        boolean acceso = false;

        for (WizBox box : mappaWizBox.values()) {
            acceso = acceso || box.is();
        }
        confirmButton.setEnabled(acceso);
    }


    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected boolean regolaAEToken() {
        return true;
    }

}
