package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEDir;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.TITOLO_NEW_PACKAGE;

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

        //        addListener();//@todo Funzionalità ancora da implementare
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
        Checkbox unCheckbox;

        //--spenge tutti i checkbox escluso flagEntity
        AECheck.reset();
        AECheck.entity.setAcceso(true);

        for (AECheck check : AECheck.getNewPackage()) {
            unCheckbox = new Checkbox(check.getCaption(), check.is());
            mappaCheckbox.put(check.name(), unCheckbox);
        }

        super.addCheckBoxMap();
    }


    protected void creaBottoni() {
        super.creaBottoni();
        confirmButton.setEnabled(true);

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
    }


    private void addListener() {
        fieldPackageName.addKeyUpListener(event -> sincroPackageName((TextField) event.getSource()));
    }


    private void sincroPackageName(TextField source) {
        String value = source.getValue();

        if (text.isValid(value) && value.length() > 1) {
            confirmButton.setEnabled(true);
            AEToken.packageName.setValue(value);
        } else {
            confirmButton.setEnabled(false);
            AEToken.packageName.setValue(VUOTA);
        }
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Resetta i valori regolabili della Enumeration AEDir <br>
     * Elabora tutti i valori della Enumeration AEDir dipendenti dal nome del progetto <br>
     * Verranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected boolean regolaAEDir() {
        boolean status = true;
        String packageName;
        super.regolaAEDir();

        if (fieldPackageName != null && text.isValid(fieldPackageName.getValue())) {
            packageName = fieldPackageName.getValue();
            status = AEDir.modificaPackageAll(packageName);
        } else {
            status = false;
        }

        return status;
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewPackage e WizElaboraUpdatePackage <br>
     */
    @Override
    protected boolean regolaAEToken() {
        boolean status = true;
        boolean usaCompany = AECheck.company.is();
        String tagEntity = "AEntity";
        String tagCompany = "AECompany";
        String projectName;
        String packageName;
        super.regolaAEToken();

        projectName = AEDir.nameTargetProject.get();
        packageName = AEDir.nameTargetPackage.get();

        if (text.isValid(projectName) && text.isValid(packageName)) {
            AEToken.nameTargetProject.setValue(projectName);
            AEToken.projectNameUpper.setValue(projectName.toUpperCase());
            AEToken.moduleNameMinuscolo.setValue(projectName.toLowerCase());
            AEToken.moduleNameMaiuscolo.setValue(text.primaMaiuscola(projectName));
            AEToken.first.setValue(packageName.substring(0, 1).toUpperCase());
            AEToken.packageName.setValue(packageName.toLowerCase());
            AEToken.user.setValue(AEDir.nameUser.get());
            AEToken.today.setValue(date.get());
            AEToken.entity.setValue(text.primaMaiuscola(packageName));
            AEToken.usaCompany.setValue(usaCompany ? "true" : "false");
            AEToken.superClassEntity.setValue(usaCompany ? tagCompany : tagEntity);
            AEToken.usaSecurity.setValue(AECheck.security.is() ? ")" : ", exclude = {SecurityAutoConfiguration.class}");
            AEToken.properties.setValue(fixProperties());
            AEToken.propertyOrdine.setValue(fixProperty(AECheck.ordine));
            AEToken.propertyCode.setValue(fixProperty(AECheck.code));
            AEToken.propertyDescrizione.setValue(fixProperty(AECheck.descrizione));
            AEToken.toString.setValue(fixString());
        }

        return status;
    }

}
