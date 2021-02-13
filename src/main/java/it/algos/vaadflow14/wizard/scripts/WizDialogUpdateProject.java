package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.NAME_VAADFLOW;
import static it.algos.vaadflow14.backend.application.FlowCost.SLASH;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

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
        AEFlag.isProject.set(true);
        AEFlag.isUpdateProject.set(true);

        super.inizia();
    }


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        String pathModuloBase;
        String pathModuloCorrente;
        String pathSorgenti;
        String pathBreve = file.findPathBreve(AEWizCost.pathVaadFlow14WizSources.get(), "vaadflow14");

        if (AEFlag.isBaseFlow.is()) {
            topLayout = fixSezione("Aggiornamento di un progetto", "green");
        }
        else {
            topLayout = fixSezione(String.format("Aggiornamento di %s", AEWizCost.projectCurrent.get()), "green");
        }
        this.add(topLayout);

        if (AEFlag.isBaseFlow.is()) {
            topLayout.add(text.getLabelGreenBold("Update del progetto selezionato"));
            topLayout.add(text.getLabelGreenBold("Il modulo " + NAME_VAADFLOW + " viene sovrascritto"));
            topLayout.add(text.getLabelGreenBold("Eventuali modifiche locali vengono perse"));
            topLayout.add(text.getLabelGreenBold("I sorgenti (che non vengono modificati) sono in  " + pathBreve));
            topLayout.add(text.getLabelRedBold("Il progetto deve esistere nella enum AEProgetto"));
            topLayout.add(text.getLabelRedBold("Seleziona il progetto dalla lista sottostante"));
            topLayout.add(text.getLabelRedBold("Seleziona le cartelle/files da aggiornare"));
        }
        else {
            topLayout.add(text.getLabelGreenBold("Update di questo progetto"));
            topLayout.add(text.getLabelGreenBold("Le varie directory vengono copiate o sovrascritte (dipende da un flag)"));
            topLayout.add(text.getLabelGreenBold("I vari files vengono modificati o sovrascritti (dipende da un flag)"));

            pathModuloBase = file.findPathBreve(AEWizCost.pathVaadFlow14Modulo.get(), "it");
            topLayout.add(text.getLabelGreenBold(String.format("Il modulo base %s di questo progetto, viene completamente sovrascritto", pathModuloBase)));

            pathModuloCorrente = file.findPathBreve(AEWizCost.pathTargetProjectModulo.get(), "it");
            topLayout.add(text.getLabelGreenBold(String.format("Il modulo corrente %s di questo progetto, viene integrato", pathModuloCorrente)));

            pathModuloBase = file.findPathBreve(AEWizCost.pathVaadFlow14Root.get(), AEWizCost.dirProjects.get());
            topLayout.add(text.getLabelGreenBold(String.format("Il modulo base da ricopiare qui viene da %s", pathModuloBase)));

            pathSorgenti = file.findPathBreve(AEWizCost.pathVaadFlow14WizSources.get(), AEWizCost.dirVaadFlow14.get());
            topLayout.add(text.getLabelGreenBold(String.format("I sorgenti per integrare il modulo corrente vengono da %s", pathSorgenti)));
            topLayout.add(text.getLabelRedBold("Seleziona le cartelle/files da aggiornare"));
        }
    }


    /**
     * Sezione centrale con la scelta del progetto <br>
     * Se NON siamo in AEFlag.isBaseFlow.is(), non fa nulla <br>
     * Spazzola la enumeration AEProgetto <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaSelezioneLayout() {
        if (AEFlag.isBaseFlow.is()) {

            selezioneLayout = fixSezione("Selezione...");
            this.add(selezioneLayout);

            List<AEProgetto> progetti = AEProgetto.get();
            String label = "AEProgetti esistenti (nella directory ../operativi)";

            fieldComboProgetti = new ComboBox<>();
            // Choose which property from Department is the presentation value
            //        fieldComboProgetti.setItemLabelGenerator(AEProgetto::nameProject);
            fieldComboProgetti.setWidth("22em");
            fieldComboProgetti.setAllowCustomValue(false);
            fieldComboProgetti.setLabel(label);

            fieldComboProgetti.setItems(progetti);
            confirmButton.setEnabled(false);
            //            if (progetti.contains(AEProgetto.alfa)) {
            //                fieldComboProgetti.setValue(AEProgetto.alfa);
            //                confirmButton.setEnabled(true);
            //            }

            addListener();
            selezioneLayout.add(fieldComboProgetti);
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

        for (AEWizCost aeCost : AEWizCost.getNewUpdateProject()) {
            mappaWizBox.put(aeCost.name(), new WizBox(aeCost, aeCost.isAccesoInizialmenteUpdate()));
        }
        super.addCheckBoxMap();
    }


    protected void sincroAll() {
        Checkbox checkAll = mappaWizBox.get(AECheck.all.name()).getBox();
        boolean accesi = checkAll.getValue();

        for (String key : mappaWizBox.keySet()) {
            if (!key.equals(AECheck.all.name())) {
                mappaWizBox.get(key).setValue(accesi);
            }
        }
    }

    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
        confirmButton.setEnabled(true);
    }


    private void addListener() {
        fieldComboProgetti.addValueChangeListener(event -> sincroProject(event.getValue()));
    }


    private void sincroProject(AEProgetto valueFromProject) {
        confirmButton.setEnabled(valueFromProject != null);
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola i valori regolabili della Enumeration AEWizCost <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected boolean regolaAEWizCost() {
        AEProgetto progettoTarget = null;
        String nameProject = VUOTA;
        String nameUpper = VUOTA;
        String pathProject = VUOTA;

        if (AEFlag.isBaseFlow.is()) {
            progettoTarget = fieldComboProgetti != null ? fieldComboProgetti.getValue() : null;
            if (progettoTarget == null) {
                return false;
            }
            nameProject = progettoTarget.getNameProject();
            nameUpper = progettoTarget.getNameUpper();
            pathProject = progettoTarget.getPathCompleto();

            if (text.isEmpty(pathProject)) {
                pathProject = AEWizCost.pathRoot.get();
                pathProject += AEWizCost.dirProjects.get();
                pathProject += AEWizCost.dirOperativi.get();
                pathProject += nameProject;
            }
        }
        else {
            nameProject = AEWizCost.projectCurrentLower.get();
            nameUpper = AEWizCost.projectCurrent.get();
            pathProject = AEWizCost.pathCurrent.get();
        }

        AEWizCost.nameTargetProject.setValue(nameUpper);
        AEWizCost.nameTargetProjectLower.setValue(nameProject.toLowerCase());
        AEWizCost.pathTargetProjectRoot.setValue(pathProject);
        AEWizCost.pathTargetProjectModulo.setValue(pathProject + SLASH + AEWizCost.dirModulo.get() + nameProject.toLowerCase(Locale.ROOT) + SLASH);
        AEWizCost.pathTargetProjectBoot.setValue(AEWizCost.pathTargetProjectModulo.get() + AEWizCost.dirBoot.get());
        AEWizCost.pathTargetProjectPackages.setValue(AEWizCost.pathTargetProjectModulo.get() + AEWizCost.dirPackages.get());

        for (AEWizCost aeCost : AEWizCost.getNewUpdateProject()) {
            if (mappaWizBox != null && mappaWizBox.get(aeCost.name()) != null) {
                aeCost.setAcceso(mappaWizBox.get(aeCost.name()).is());
            }
        }
        AEWizCost.printVuote();
        AEWizCost.printInfo();
        return true;
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
        String projectName;
        super.regolaAEDir();

        if (fieldComboProgetti != null && fieldComboProgetti.getValue() != null) {
            projectName = fieldComboProgetti.getValue().getNameProject();
            status = status && checkProject(projectName);
            status = status && AEDir.modificaProjectAll(projectName);
        }

        return status;
    }

    /**
     * Controlla che il progetto selezionato esista nella directory deputata <br>
     */
    protected boolean checkProject(String projectName) {
        String pathOperativa = PATH_OPERATIVI_DIR_STANDARD + projectName;
        String pathProgetti = PATH_PROJECTS_DIR_STANDARD + projectName;
        String message;
        String projectNameUpper = text.primaMaiuscola(projectName);
        String pathBreve = file.findPathBreve(pathOperativa, DIR_OPERATIVI);

        //--posizione standard corretta
        if (file.isEsisteDirectory(pathOperativa)) {
            return true;
        }

        //--prova a vedere se per caso è rimasto nella directory di creazione
        if (file.isEsisteDirectory(pathProgetti)) {
            message = String.format("Update project (%s): è rimasto nella directory %s, devi spostarlo in %s", projectNameUpper, DIR_PROJECTS, DIR_OPERATIVI);
            logger.log(AETypeLog.wizard, message);
        }
        else {
            message = String.format("Update project (%s): non rintracciabile nella directory %s", projectNameUpper, pathBreve);
            logger.log(AETypeLog.wizard, message);
        }

        return false;
    }

}
