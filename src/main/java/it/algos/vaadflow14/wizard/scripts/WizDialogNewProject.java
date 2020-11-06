package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEDir;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.NORMAL_HEIGHT;
import static it.algos.vaadflow14.wizard.scripts.WizCost.TITOLO_NUOVO_PROGETTO;


/**
 * Project my-project
 * Created by Algos
 * User: gac
 * Date: lun, 12-ott-2020
 * Time: 12:24
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogNewProject extends WizDialog {

    private static String LABEL_COMBO_UNO = "Progetti vuoti esistenti (nella directory IdeaProjects)";

    private static String LABEL_COMBO_DUE = "Tutti i progetti esistenti (nella directory IdeaProjects)";


    public WizDialogNewProject() {
        super();
    }// end of constructor


    /**
     * Apertura del dialogo <br>
     */
    public void open(WizRecipient wizRecipient) {
        this.getElement().getStyle().set("background-color", "#ffffff");
        super.wizRecipient = wizRecipient;
        super.isNuovoProgetto = true;
        super.isStartThisProgetto = false;
        super.titoloCorrente = new H3(TITOLO_NUOVO_PROGETTO);
        AEFlag.isProject.set(true);
        AEFlag.isNewProject.set(true);

        if (check()) {
            this.inizia();
        } else {
            super.esceDalDialogo(false);
        }
    }


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        topLayout = fixSezione("Nuovo progetto", "green");
        this.add(topLayout);

        topLayout.add(text.getLabelGreenBold("Creazione di un nuovo project"));
        topLayout.add(text.getLabelGreenBold("Devi prima creare un new project IntelliJIdea"));
        topLayout.add(text.getLabelGreenBold("Di tipo 'MAVEN' senza selezionare archetype"));
        topLayout.add(text.getLabelGreenBold("Rimane il POM vuoto, ma verrà sovrascritto"));
        topLayout.add(text.getLabelRedBold("Seleziona il progetto dalla lista sottostante"));
        topLayout.add(text.getLabelRedBold("Spunta i checkBox di regolazione che vuoi attivare"));
        topLayout.add(text.getLabelRedBold("Nel progetto vai su pom.xml, click destro -> Maven.Reload "));
        topLayout.add(text.getLabelRedBold("Aggiungi il nuovo progetto alla enumeration AEProgetto"));
    }


    /**
     * Sezione centrale con la scelta del progetto <br>
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera i possibili progetti 'vuoti' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaSelezioneLayout() {
        selezioneLayout = fixSezione("Selezione...");
        this.add(selezioneLayout);

        List<File> progetti = file.getEmptyProjects(AEDir.pathIdeaProjects.get());

        fieldComboProgetti = new ComboBox<>();
        // Choose which property from Department is the presentation value
        fieldComboProgetti.setItemLabelGenerator(File::getName);
        fieldComboProgetti.setWidth("22em");
        fieldComboProgetti.setAllowCustomValue(false);
        fieldComboProgetti.setLabel(LABEL_COMBO_UNO);

        fieldComboProgetti.setItems(progetti);
        if (progetti.size() == 1) {
            fieldComboProgetti.setValue(progetti.get(0));
            confirmButton.setEnabled(true);
        }

        buttonForzaDirectory = new Button("Forza directory");
        buttonForzaDirectory.setIcon(VaadinIcon.REFRESH.create());
        buttonForzaDirectory.getElement().setAttribute("theme", "error");
        buttonForzaDirectory.addClickListener(e -> forzaProgetti());
        buttonForzaDirectory.setWidth("12em");
        buttonForzaDirectory.setHeight(NORMAL_HEIGHT);
        buttonForzaDirectory.setVisible(true);
        buttonForzaDirectory.setEnabled(progetti.size() < 1);

        addListener();
        selezioneLayout.add(fieldComboProgetti, buttonForzaDirectory);
    }


    /**
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera tutti i progetti esistenti <br>
     * Esclude le sottoDirectories di 'ideaProjects' <br>
     */
    protected void forzaProgetti() {
        List<File> progetti = file.getProjects(AEDir.pathIdeaProjects.get());
        fieldComboProgetti.setItems(progetti);
        fieldComboProgetti.setLabel(LABEL_COMBO_DUE);
        if (progetti.size() == 1) {
            fieldComboProgetti.setValue(progetti.get(0));
            confirmButton.setVisible(true);
        }
        buttonForzaDirectory.setEnabled(false);
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

        //--accende tutti i checkbox escluso flagSecurity
        for (AECheck check : AECheck.getNewProject()) {
            check.setAcceso(true);
        }
        AECheck.security.setAcceso(false);

        for (AECheck check : AECheck.getNewProject()) {
            unCheckbox = new Checkbox(check.getCaption(), check.isAcceso());
            mappaCheckbox.put(check.name(), unCheckbox);
        }

        super.addCheckBoxMap();
    }


    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "secondary");
        confirmButton.getElement().setAttribute("theme", "primary");
    }


    private void addListener() {
        fieldComboProgetti.addValueChangeListener(event -> sincroProject(event.getValue()));
    }


    private void sincroProject(File valueFromProject) {
        confirmButton.setEnabled(valueFromProject != null);
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola tutti i valori della Enumeration AEDir che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected void regolaAEDir() {
        String projectName;
        super.regolaAEDir();

        if (fieldComboProgetti != null && fieldComboProgetti.getValue() != null) {
            projectName = fieldComboProgetti.getValue().getName();
            AEDir.nameTargetProject.setValue(projectName);
            AEDir.pathTargetProject.setValue(AEDir.pathIdeaProjects.get() + projectName + FlowCost.SLASH);
        }
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    @Override
    protected void regolaAEToken() {
        String project = VUOTA;
        super.regolaAEToken();

        project = AEDir.nameTargetProject.get();
        AEToken.nameTargetProject.setValue(project);
        //        AEToken.moduleNameMinuscolo.setValue(AEToken.nameTargetProject.getValue());
        //        AEToken.moduleNameMaiuscolo.setValue(AEToken.projectNameUpper.getValue());
        //        AEToken.entity.setValue(text.primaMaiuscola(AEToken.packageName.getValue()));
    }

}
