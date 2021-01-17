package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEDir;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

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

        if (array.isAllValid(packages)) {

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
            mappaWizBox.put(check.name(), new WizBox(check));
        }

        unCheckbox = mappaWizBox.get(AECheck.rowIndex.name()).getBox();
        unCheckbox.addValueChangeListener(e -> { sincroRow(); });
        unCheckbox = mappaWizBox.get(AECheck.ordine.name()).getBox();
        unCheckbox.addValueChangeListener(e -> { sincroOrdine(); });
        //        unCheckbox = mappaWizBox.get(AECheck.docFile.name()).getBox();
        //        unCheckbox.addValueChangeListener(e -> { sincroDocPackage(); });

        super.addCheckBoxMap();
    }

    /**
     * legge i nomi dei 4 fields base (int, String, String, boolean) (se ci sono)
     */
    private void leggeFieldsEsistenti(String packageName) {
        String nameSourceText = AEDir.pathTargetAllPackages.get() + packageName + FlowCost.SLASH + text.primaMaiuscola(packageName) + FlowCost.JAVA_SUFFIX;
        String sourceText = file.leggeFile(nameSourceText);

        check(sourceText, AETypeField.integer, "int", 1, AECheck.ordine);
        check(sourceText, AETypeField.text, "String", 1, AECheck.code);
        check(sourceText, AETypeField.text, "String", 2, AECheck.descrizione);
        check(sourceText, AETypeField.booleano, "boolean", 1, AECheck.valido);
    }

    /**
     * Controlla se nel file AEntity esiste una property del tipo indicato (nella posizione) <br>
     */
    private void check(String sourceText, AETypeField type, String tag, int pos, AECheck check) {
        String tagType = "type = AETypeField." + type.name();
        String tagIni = "public " + tag;
        String tagEnd = FlowCost.PUNTO_VIRGOLA;
        int posIni = 0;
        int posEnd = 0;
        String fieldName;

        check.setAcceso(false);
        check.setFieldName(VUOTA);

        if (sourceText.contains(tagType)) {
            posIni = sourceText.indexOf(tagType);
            if (pos == 2) {
                posIni = sourceText.indexOf(tagType, posIni + tagType.length());
            }
            posIni = sourceText.indexOf(tagIni, posIni);
            posIni += tagIni.length();
            posEnd = sourceText.indexOf(tagEnd, posIni);

            fieldName = sourceText.substring(posIni, posEnd).trim();

            if (text.isValid(fieldName)) {
                check.setAcceso(true);
                check.setFieldName(fieldName);
            }
        }
    }


    protected void sincroRow() {
        Checkbox checkRow = mappaWizBox.get(AECheck.rowIndex.name()).getBox();
        Checkbox checkOrdine = mappaWizBox.get(AECheck.ordine.name()).getBox();

        if (checkRow.getValue()) {
            checkOrdine.setValue(false);
        }
    }

    protected void sincroOrdine() {
        Checkbox checkRow = mappaWizBox.get(AECheck.rowIndex.name()).getBox();
        Checkbox checkOrdine = mappaWizBox.get(AECheck.ordine.name()).getBox();

        if (checkOrdine.getValue()) {
            checkRow.setValue(false);
        }
    }

    protected void sincroDocPackage() {
        Checkbox checkDocPackage = mappaWizBox.get(AECheck.docFile.name()).getBox();

        if (checkDocPackage.getValue()) {
            for (String key : mappaWizBox.keySet()) {
                if (!key.equals(AECheck.docFile.name())) {
                    mappaWizBox.get(key).setValue(false);
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
        leggeFieldsEsistenti(packageName);

        for (AECheck check : AECheck.getUpdatePackage()) {
            mappaWizBox.put(check.name(), new WizBox(check));
        }

        super.addCheckBoxMap();

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

        if (mappaWizBox != null && mappaWizBox.get(AECheck.docFile.name()) != null && mappaWizBox.get(AECheck.docFile.name()).is()) {
            AEDir.modificaPackageAll(VUOTA);
        }
        else {
            status = status && AEDir.modificaPackageAll(packageName);
        }

        return status;
    }

}
