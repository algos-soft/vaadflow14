package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEDir;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.List;

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

    private static final String LABEL_COMBO_UNO = "Progetti vuoti esistenti (nella directory IdeaProjects)";

    private static final String LABEL_COMBO_DUE = "Tutti i progetti esistenti (nella directory IdeaProjects)";


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
        AEFlag.isUpdateProject.set(false);
        AEFlag.isPackage.set(false);

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

        fieldComboProgettiNuovi = new ComboBox<>();
        // Choose which property from Department is the presentation value
        fieldComboProgettiNuovi.setItemLabelGenerator(File::getName);
        fieldComboProgettiNuovi.setWidth("22em");
        fieldComboProgettiNuovi.setAllowCustomValue(false);
        fieldComboProgettiNuovi.setLabel(LABEL_COMBO_UNO);

        fieldComboProgettiNuovi.setItems(progetti);
        if (progetti.size() == 1) {
            fieldComboProgettiNuovi.setValue(progetti.get(0));
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
        selezioneLayout.add(fieldComboProgettiNuovi, buttonForzaDirectory);
    }


    /**
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera tutti i progetti esistenti <br>
     * Esclude le sottoDirectories di 'ideaProjects' <br>
     */
    protected void forzaProgetti() {
        List<File> progetti = file.getAllProjects(AEDir.pathIdeaProjects.get());
        fieldComboProgettiNuovi.setItems(progetti);
        fieldComboProgettiNuovi.setLabel(LABEL_COMBO_DUE);
        if (progetti.size() == 1) {
            fieldComboProgettiNuovi.setValue(progetti.get(0));
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
            unCheckbox = new Checkbox(check.getCaption(), check.is());
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
        fieldComboProgettiNuovi.addValueChangeListener(event -> sincroProject(event.getValue()));
    }


    private void sincroProject(File valueFromProject) {
        confirmButton.setEnabled(valueFromProject != null);
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

        if (fieldComboProgettiNuovi != null && fieldComboProgettiNuovi.getValue() != null) {
            projectName = fieldComboProgettiNuovi.getValue().getName();
            status = status && AEDir.modificaProjectAll(projectName);
        }

        return status;
    }


}
