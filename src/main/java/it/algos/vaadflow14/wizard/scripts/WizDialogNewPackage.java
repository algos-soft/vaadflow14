package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project provider
 * Created by Algos
 * User: gac
 * Date: dom, 25-ott-2020
 * Time: 17:57
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogNewPackage extends WizDialogPackage {


    /**
     * Apertura del dialogo <br>
     */
    public void open(WizRecipient wizRecipient) {
        super.wizRecipient = wizRecipient;
        super.isNuovoProgetto = false;
        super.titoloCorrente = new H3();
        AEFlag.isPackage.set(true);
        AEFlag.isNewPackage.set(true);

        super.inizia();
    }


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        topLayout = fixSezione(TITOLO_NEW_PACKAGE, "green");
        this.add(topLayout);

        topLayout.add(text.getLabelGreenBold("Creazione di un nuovo package funzionante"));
    }


    /**
     * Sezione centrale con la scelta del nome del package <br>
     */
    @Override
    protected void creaSelezioneLayout() {
        selezioneLayout = fixSezione("Nome del package");
        this.add(selezioneLayout);

        fieldPackageName = new TextField("Nome del package");
        fieldPackageName.setAutofocus(true);
        selezioneLayout.add(fieldPackageName);

        //@todo Funzionalità ancora da implementare- Non riesco a sincronizzarlo
        //        confirmButton.setEnabled(false);
        //        addListener();
    }


    /**
     * Sezione centrale con la selezione dei flags <br>
     * Crea i checkbox di controllo <br>
     * Spazzola (nella sottoclasse) la Enumeration per aggiungere solo i checkbox adeguati: <br>
     * newProject
     * updateProject
     * newPackage
     * updatePackage
     * Spazzola la Enumeration e regola a 'true' i chekBox secondo il flag 'isAcceso' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaCheckBoxLayout() {
        super.creaCheckBoxLayout();

        for (AEPackage pack : AEPackage.values()) {
            mappaWizBox.put(pack.name(), new WizBox(pack));
        }
        super.addCheckBoxMap();
    }


    protected void creaBottoni() {
        super.creaBottoni();
        confirmButton.setEnabled(true);

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
        confirmButton.setEnabled(true);
    }


    private void addListener() {
        fieldPackageName.addKeyUpListener(event -> sincroPackageName((TextField) event.getSource()));
    }


    private void sincroPackageName(TextField source) {
        String value = source.getValue();

        if (text.isValid(value) && value.length() > 1) {
            confirmButton.setEnabled(true);
            AEToken.packageNamePunti.setValue(text.fixSlashToPunto(value));
            AEToken.packageNameSlash.setValue(text.fixPuntoToSlash(value));
        }
        else {
            confirmButton.setEnabled(false);
            AEToken.packageNamePunti.setValue(VUOTA);
            AEToken.packageNameSlash.setValue(VUOTA);
        }
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola i valori regolabili della Enumeration AEWizCost <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected boolean regolaAEWizCost() {
        String pathProject = VUOTA;
        String packageName = VUOTA;

        if (AEFlag.isBaseFlow.is()) {
        }
        else {
            //--recupera il path completo del progetto in esecuzione
            pathProject = AEWizCost.pathCurrent.getValue();
        }

        if (fieldPackageName != null && text.isValid(fieldPackageName.getValue())) {
            packageName = fieldPackageName.getValue();
        }

        return super.regolaPackages(pathProject,packageName);

//        if (text.isEmpty(pathProject)) {
//            message = String.format("Non è stato selezionato il progetto di riferimento.");
//            logger.log(AETypeLog.wizard, message);
//            return false;
//        }
//
//        if (text.isEmpty(packageName)) {
//            message = String.format("Manca il nome del package da creare/modificare.");
//            logger.log(AETypeLog.wizard, message);
//            return false;
//        }
//
//        //--inserisce il path completo del progetto in esecuzione
//        AEWizCost.pathTargetProjectRoot.setValue(pathProject);
//
//        //--inserisce il nome del package da creare/modificare
//        AEWizCost.nameTargetPackagePunto.setValue(text.fixSlashToPunto(packageName));
//
//        //--regola tutti i valori automatici, dopo aver inserito quelli fondamentali
//        AEWizCost.fixValoriDerivati();
//
//        return true;
    }

}
