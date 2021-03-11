package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.SLASH;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
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
public class WizDialogNewPackage extends WizDialog {


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
        String packName;
        String fileName;

        //--ci sono diversi VALORE_MANCANTE di cui 7 regolati all'ingresso del dialogo
        if (fieldPackageName != null && text.isValid(fieldPackageName.getValue())) {
            packName = fieldPackageName.getValue();
            packName = text.fixPuntoToSlash(packName);
            AEWizCost.nameTargetPackage.setValue(packName);
            fileName = text.levaTestoPrimaDi(packName, SLASH);
            AEWizCost.nameTargetFileUpper.setValue(text.primaMaiuscola(fileName));
            AEWizCost.pathTargetPackage.setValue(AEWizCost.pathTargetProjectPackages.get() + AEWizCost.nameTargetPackage.get() + SLASH);
        }

        AEWizCost.printVuote();
        AEWizCost.printInfoBase(AEWizCost.getPackages(), "Variabili del package. Dipende dal package selezionato");
        return true;
    }



}
