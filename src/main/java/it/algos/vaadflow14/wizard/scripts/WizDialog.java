package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import it.algos.vaadflow14.backend.service.AArrayService;
import it.algos.vaadflow14.backend.service.AFileService;
import it.algos.vaadflow14.backend.service.ALogService;
import it.algos.vaadflow14.backend.service.ATextService;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEDir;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.LinkedHashMap;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.NORMAL_HEIGHT;
import static it.algos.vaadflow14.wizard.scripts.WizCost.NORMAL_WIDTH;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:17
 * <p>
 * Classe astratta per alcuni dialoghi di regolazione dei parametri per il Wizard <br>
 */
public abstract class WizDialog extends Dialog {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WizService wizService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ALogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected ATextService text;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected AArrayService array;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected AFileService file;

    protected WizRecipient wizRecipient;

    protected LinkedHashMap<String, Checkbox> mappaCheckbox;

    protected boolean isNuovoProgetto;

    protected boolean isNuovoPackage;

    protected boolean isStartThisProgetto;

    protected Button confirmButton;

    protected Button cancelButton;

    protected Button buttonForzaDirectory;

    protected TextField fieldPackageName;

    /**
     * Sezione superiore,coi titoli e le info <br>
     */
    protected VerticalLayout topLayout;

    /**
     * Sezione centrale con la scelta del progetto <br>
     */
    protected VerticalLayout selezioneLayout;

    /**
     * Sezione centrale con la selezione dei flags <br>
     */
    protected VerticalLayout checkBoxLayout;

    /**
     * Sezione inferiore coi bottoni di uscita e conferma <br>
     */
    protected VerticalLayout bottomLayout;

    protected H3 titoloCorrente;

    protected ComboBox<File> fieldComboProgetti;


    /**
     * Regolazioni grafiche
     */
    protected void inizia() {
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
        this.removeAll();

        //--regolazione di property varie
        this.regolaVariabili();

        //--creazione iniziale dei bottoni (chiamati anche da selezioneLayout)
        this.creaBottoni();

        //--info di avvisi iniziali
        this.creaTopLayout();

        //--solo per newProject
        this.creaSelezioneLayout();

        //--checkbox di spunta
        this.creaCheckBoxLayout();

        //--creazione bottoni di comando
        this.creaBottomLayout();

        //--superClasse
        super.open();
    }// end of method


    /**
     * Regolazioni iniziali in base al dialogo che viene aperto <br>
     */
    protected void regolaVariabili() {
        //        AECheck.reset();
        //
        //        stampaIngresso();
        //
        //        //        if (FLAG_DEBUG_WIZ) {
        //        //            WizCost.printInfo(log);
        //        //        }// end of if cycle
        //
        //        this.pathUserDir = System.getProperty("user.dir") + SLASH;
        //        this.pathVaadFlow = PATH_VAADFLOW_DIR_STANDARD;
        //        if (isNuovoProgetto && !pathVaadFlow.equals(pathUserDir)) {
        //            //                        logger.error("Attenzione. La directory di VaadFlow è cambiata", WizDialog.class, "regolaVariabili");
        //        }// end of if/else cycle
        //
        //        //valido SOLO per new project
        //        if (isNuovoProgetto) {
        //            this.pathIdeaProjects = file.levaDirectoryFinale(pathVaadFlow);
        //            this.pathIdeaProjects = file.levaDirectoryFinale(pathIdeaProjects);
        //            if (!pathIdeaProjects.equals(PATH_PROJECTS_DIR_STANDARD)) {
        //                //                logger.error("Attenzione. La directory dei Projects è cambiata", WizDialog.class, "regolaVariabili");
        //            }// end of if cycle
        //        } else {
        //            File unaDirectory = new File(pathUserDir);
        //            this.nameTargetProject = unaDirectory.getName();
        //            this.pathTargetProject = pathUserDir;
        //        }// end of if/else cycle
        //
        //        if (isStartThisProgetto) {
        //            File unaDirectory = new File(pathUserDir);
        //            this.nameTargetProject = unaDirectory.getName();
        //            this.pathTargetProject = pathUserDir;
        //        }
        //
        //        this.pathSources = pathVaadFlow + DIR_VAADFLOW_SOURCES;
        //
        //        //        if (FLAG_DEBUG_WIZ) {
        //        //            System.out.println("********************");
        //        //            System.out.println("Ingresso del dialogo");
        //        //            System.out.println("********************");
        //        //            System.out.println("Directory di esecuzione: pathUserDir=" + pathUserDir);
        //        //            System.out.println("Directory VaadFlow: pathVaadFlow=" + pathVaadFlow);
        //        //            if (isNuovoProgetto) {
        //        //                System.out.println("Directory dei nuovi progetti: pathIdeaProjects=" + pathIdeaProjects);
        //        //            }// end of if cycle
        //        //            System.out.println("Sorgenti VaadFlow: pathSources=" + pathSources);
        //        //            System.out.println("");
        //        //        }// end of if cycle
    }// end of method


    /**
     * Controlla che il dialogo possa usare alcuni flag compatibili (tra di loro) <br>
     */
    protected boolean check() {
        boolean valido = true;

        //--Deve essere o un progetto o un package
        valido = valido && (AEFlag.isProject.is() || AEFlag.isPackage.is());

        //--Se è un progetto, deve essere nuovo o update
        if (AEFlag.isProject.is()) {
            valido = valido && (AEFlag.isNewProject.is() != AEFlag.isUpdateProject.is());
        }

        //--Se è un package, deve essere nuovo o update
        if (AEFlag.isPackage.is()) {
            valido = valido && (AEFlag.isNewPackage.is() != AEFlag.isUpdatePackage.is());
        }

        if (!valido) {
            wizService.printInfo("Blocco entrata dialogo");
            logger.warn("Il dialogo non è stato aperto perché alcuni flags non sono validi per operare correttamente");
        }

        return valido;
    }


    /**
     * Sezione superiore,coi titoli e le info <br>
     * Legenda iniziale <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaTopLayout() {
    }// end of method


    /**
     * Sezione centrale con la scelta del progetto <br>
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera i possibili progetti 'vuoti' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superClasse <br>
     */
    protected void creaSelezioneLayout() {
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
    protected void creaCheckBoxLayout() {
        checkBoxLayout = fixSezione("Flags di regolazione");
        this.add(checkBoxLayout);
        mappaCheckbox = new LinkedHashMap<>();
    }


    protected void creaBottoni() {
        cancelButton = new Button("Annulla", event -> {
            esceDalDialogo(false);
        });//end of lambda expressions
        cancelButton.setIcon(VaadinIcon.ARROW_LEFT.create());
        cancelButton.addClickShortcut(Key.ARROW_LEFT);
        cancelButton.setWidth(NORMAL_WIDTH);
        cancelButton.setHeight(NORMAL_HEIGHT);
        cancelButton.setVisible(true);

        confirmButton = new Button("Conferma", event -> {
            esceDalDialogo(true);
        });//end of lambda expressions
        confirmButton.setIcon(VaadinIcon.EDIT.create());
        confirmButton.setWidth(NORMAL_WIDTH);
        confirmButton.setHeight(NORMAL_HEIGHT);
        confirmButton.setVisible(true);
        confirmButton.setEnabled(!isNuovoProgetto);
    }


    protected void creaBottomLayout() {
        bottomLayout = new VerticalLayout();
        HorizontalLayout layoutFooter = new HorizontalLayout();
        layoutFooter.setSpacing(true);
        layoutFooter.setMargin(true);

        layoutFooter.add(cancelButton, confirmButton);
        bottomLayout.add(layoutFooter);
        this.add(bottomLayout);
    }


    /**
     * Aggiunge al layout i checkbox di controllo <br>
     */
    protected void addCheckBoxMap() {
        for (String key : mappaCheckbox.keySet()) {
            checkBoxLayout.add(mappaCheckbox.get(key));
        }
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola tutti i valori della Enumeration AEDir che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected void regolazioniFinali() {
        this.regolaAEDir();
        this.regolaAEToken();
        this.regolaAECheck();

        wizService.printInfo("Uscita del dialogo");
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola tutti i valori della Enumeration AEDir che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected void regolaAEDir() {
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola tutti i valori della Enumeration AECheck che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected void regolaAECheck() {
        for (AECheck check : AECheck.values()) {
            if (mappaCheckbox != null && mappaCheckbox.get(check.name()) != null) {
                check.setAcceso(mappaCheckbox.get(check.name()).getValue());
            }
        }
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected void regolaAEToken() {
        AEToken.reset();
        //        AEToken.nameTargetProject.setValue(nameTargetProject);
        //        AEToken.pathTargetProject.setValue(pathTargetProject);
        //
        //        AEToken.projectNameUpper.setValue(nameTargetProject.toUpperCase());
        //        AEToken.moduleNameMinuscolo.setValue(nameTargetProject.toLowerCase());
        //        AEToken.moduleNameMaiuscolo.setValue(text.primaMaiuscola(nameTargetProject));
        //        AEToken.first.setValue(text.isValid(nameTargetProject) ? nameTargetProject.substring(0, 1).toUpperCase() : VUOTA);

//        String project = VUOTA;
//        project = AEDir.nameTargetProject.get();
//        AEToken.moduleNameMinuscolo.setValue(project);
//        AEToken.moduleNameMaiuscolo.setValue(text.primaMaiuscola(project));


        Object a = isNuovoPackage;
        Object b = fieldPackageName;
        if (isNuovoPackage && fieldPackageName != null && text.isValid(fieldPackageName.getValue())) {
            //            AEToken.packageName.setValue(fieldPackageName.getValue().toLowerCase());
        }
    }


    protected VerticalLayout fixSezione(String titolo) {
        return fixSezione(titolo, "black");
    }


    protected VerticalLayout fixSezione(String titolo, String color) {
        VerticalLayout layoutTitolo = new VerticalLayout();
        H3 titoloH3 = new H3(text.primaMaiuscola(titolo));

        layoutTitolo.setMargin(false);
        layoutTitolo.setSpacing(false);
        layoutTitolo.setPadding(false);
        titoloH3.getElement().getStyle().set("color", "blue");
        layoutTitolo.add(titoloH3);

        return layoutTitolo;
    }


    /**
     * Esce dal dialogo con due possibilità (a seconda del flag)
     * 1) annulla
     * 2) esegue
     */
    protected void esceDalDialogo(boolean esegue) {
        regolazioniFinali();
        this.close();
        if (esegue) {
            wizRecipient.esegue();
        }
    }

}
