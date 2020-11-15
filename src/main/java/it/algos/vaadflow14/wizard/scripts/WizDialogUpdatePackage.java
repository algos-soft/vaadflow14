package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEDir;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.DIR_BACKEND;
import static it.algos.vaadflow14.wizard.scripts.WizCost.DIR_PACKAGES;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 04-nov-2020
 * Time: 18:04
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogUpdatePackage extends WizDialog {

    /**
     * Apertura del dialogo <br>
     */
    public void open(WizRecipient wizRecipient) {
        super.wizRecipient = wizRecipient;
        AEFlag.isPackage.set(true);
        AEFlag.isUpdatePackage.set(true);

        super.inizia();
    }


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        if (AEFlag.isBaseFlow.is()) {
            logger.error("Non dovrebbe arrivare qui", this.getClass(), "creaTopLayout");
        } else {
            topLayout = fixSezione("Modifica di un package", "green");
        }
        this.add(topLayout);

        topLayout.add(text.getLabelGreenBold("Update di un package esistente in questo progetto"));
        topLayout.add(text.getLabelRedBold("Seleziona il package da aggiornare"));
    }


    /**
     * Sezione centrale con la scelta del progetto <br>
     * Spazzola la la directory packages <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaSelezioneLayout() {
        selezioneLayout = fixSezione("Selezione...");
        this.add(selezioneLayout);

        String path = AEDir.pathTargetModulo.get() + DIR_BACKEND + DIR_PACKAGES;
        List<String> packages = null;
        if (text.isValid(path)) {
            packages = file.getSubDirectoriesName(path);
        }

        String label = "Packages esistenti nella directory di questo progetto";

        if (array.isValid(packages)) {

            fieldComboPackages = new ComboBox<>();
            // Choose which property from Department is the presentation value
            //        fieldComboProgetti.setItemLabelGenerator(AEProgetto::nameProject);
            fieldComboPackages.setWidth("22em");
            fieldComboPackages.setAllowCustomValue(false);
            fieldComboPackages.setLabel(label);

            fieldComboPackages.setItems(packages);
            confirmButton.setEnabled(false);
            if (packages.size() == 1) {
                fieldComboPackages.setValue(packages.get(0));
                confirmButton.setEnabled(true);
            }
            addListener();
            selezioneLayout.add(fieldComboPackages);
        }

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

        //--spenge tutti i checkbox
        for (AECheck check : AECheck.getUpdatePackage()) {
            check.setAcceso(false);
        }
        AECheck.entity.setAcceso(true);

        for (AECheck check : AECheck.getUpdatePackage()) {
            unCheckbox = new Checkbox(check.getCaption(), check.is());
            mappaCheckbox.put(check.name(), unCheckbox);
        }

        super.addCheckBoxMap();
    }


    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
    }


    private void addListener() {
        fieldComboPackages.addValueChangeListener(event -> sincroProject(event.getValue()));
    }


    private void sincroProject(String packageName) {
        confirmButton.setEnabled(text.isValid(packageName));
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

        if (fieldComboPackages != null && fieldComboPackages.getValue() != null) {
            packageName = fieldComboPackages.getValue();
            status = status && AEDir.modificaPackageAll(packageName);
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
            AEToken.keyProperty.setValue(AECheck.code.is()?AECheck.code.getField():VUOTA);
            AEToken.searchProperty.setValue(AECheck.code.is()?AECheck.code.getField():VUOTA);
            AEToken.sortProperty.setValue(AECheck.ordine.is()?AECheck.ordine.getField():VUOTA);
            AEToken.properties.setValue(fixProperties());
            AEToken.propertyOrdine.setValue(fixProperty(AECheck.ordine));
            AEToken.propertyCode.setValue(fixProperty(AECheck.code));
            AEToken.propertyDescrizione.setValue(fixProperty(AECheck.descrizione));
            AEToken.toString.setValue(fixString());
        }

        return status;
    }




//    protected String fixOrdine() {
//        String testo = VUOTA;
//        String tagSources = "PropertyOrdine";
//        String sourceText = VUOTA;
//
//        if (AECheck.ordine.is()) {
//            sourceText = wizService.leggeFile(tagSources);
//            testo = wizService.elaboraFileCreatoDaSource(sourceText);
//        }
//
//        return testo;
//    }
//
//
//    protected String fixCode() {
//        String testo = VUOTA;
//        String tagSources = "PropertyCode";
//        String sourceText = VUOTA;
//
//        if (AECheck.code.is()) {
//            sourceText = wizService.leggeFile(tagSources);
//            testo = wizService.elaboraFileCreatoDaSource(sourceText);
//        }
//
//        return testo;
//    }
//
//
//    protected String fixDescrizione() {
//        String testo = VUOTA;
//        String tagSources = "PropertyDescrizione";
//        String sourceText = VUOTA;
//
//        if (AECheck.descrizione.is()) {
//            sourceText = wizService.leggeFile(tagSources);
//            testo = wizService.elaboraFileCreatoDaSource(sourceText);
//        }
//
//        return testo;
//    }

}
