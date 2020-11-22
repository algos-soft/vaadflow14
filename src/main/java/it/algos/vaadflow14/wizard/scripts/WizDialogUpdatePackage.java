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

import java.time.LocalDate;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

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
        }
        else {
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

        List<String> packages = wizService.getPackages();

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

        //--regola tutti i checkbox
        AECheck.reset();
        for (AECheck check : AECheck.getUpdatePackage()) {
            check.setAcceso(check.isAccesoInizialmente());
        }

        for (AECheck check : AECheck.getUpdatePackage()) {
            unCheckbox = new Checkbox(check.getCaption(), check.is());
            mappaCheckbox.put(check.name(), unCheckbox);
        }

        unCheckbox = mappaCheckbox.get(AECheck.rowIndex.name());
        unCheckbox.addValueChangeListener(e -> { sincroRow(); });
        unCheckbox = mappaCheckbox.get(AECheck.ordine.name());
        unCheckbox.addValueChangeListener(e -> { sincroOrdine(); });
        unCheckbox = mappaCheckbox.get(AECheck.docFile.name());
        unCheckbox.addValueChangeListener(e -> { sincroDocPackage(); });

        super.addCheckBoxMap();
    }

    protected void sincroRow() {
        Checkbox checkRow = mappaCheckbox.get(AECheck.rowIndex.name());
        Checkbox checkOrdine = mappaCheckbox.get(AECheck.ordine.name());

        if (checkRow.getValue()) {
            checkOrdine.setValue(false);
        }
    }

    protected void sincroOrdine() {
        Checkbox checkRow = mappaCheckbox.get(AECheck.rowIndex.name());
        Checkbox checkOrdine = mappaCheckbox.get(AECheck.ordine.name());

        if (checkOrdine.getValue()) {
            checkRow.setValue(false);
        }
    }

    protected void sincroDocPackage() {
        Checkbox checkDocPackage = mappaCheckbox.get(AECheck.docFile.name());

        if (checkDocPackage.getValue()) {
            for (String key : mappaCheckbox.keySet()) {
                if (!key.equals(AECheck.docFile.name())) {
                    mappaCheckbox.get(key).setValue(false);
                }
            }
            confirmButton.setEnabled(true);
        }
        else {
            confirmButton.setEnabled(false);
        }
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
        String packageName = VUOTA;
        super.regolaAEDir();

        if (fieldComboPackages != null && fieldComboPackages.getValue() != null) {
            packageName = fieldComboPackages.getValue();
        }

        if (mappaCheckbox != null && mappaCheckbox.get(AECheck.docFile.name()) != null && mappaCheckbox.get(AECheck.docFile.name()).getValue()) {
            AEDir.modificaPackageAll(VUOTA);
        }
        else {
            status = status && AEDir.modificaPackageAll(packageName);
        }

        return status;
    }

}
