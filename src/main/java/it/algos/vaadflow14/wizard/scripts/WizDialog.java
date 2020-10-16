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
import it.algos.vaadflow14.backend.service.AArrayService;
import it.algos.vaadflow14.backend.service.AFileService;
import it.algos.vaadflow14.backend.service.ATextService;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import it.algos.vaadflow14.wizard.enumeration.AEWiz;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.LinkedHashMap;

import static it.algos.vaadflow14.backend.application.FlowCost.SLASH;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;


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

    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
    //     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    protected LogService logger;

    protected WizRecipient wizRecipient;

    protected LinkedHashMap<String, Checkbox> mappaCheckbox;

    protected boolean isNuovoProgetto;

    protected boolean isStartThisProgetto;

    protected Button confirmButton;

    protected Button cancelButton;

    protected Button buttonForzaDirectory;

    protected VerticalLayout layoutLegenda;

    protected VerticalLayout layoutTitolo;

    protected VerticalLayout layoutCheckBox;

    protected VerticalLayout layoutBottoni;

    protected H3 titoloCorrente;

    protected ComboBox<File> fieldComboProgetti;

    //--regolata iniziale indipendentemente dai risultati del dialogo
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    //--directory recuperata dal System dove gira il programma in uso
    protected String pathUserDir;

    //--regolata iniziale indipendentemente dai risultati del dialogo
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    //--dipende solo da dove si trova attualmente il progetto base VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory che contiene il programma VaadFlow
    //--dovrebbe essere PATH_VAAD_FLOW_DIR_STANDARD
    //--posso spostarlo (è successo) senza che cambi nulla
    protected String pathVaadFlow;

    //--regolata iniziale indipendentemente dai risultati del dialogo
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    //--directory che contiene i nuovi programmi appena creati da Idea
    //--dovrebbe essere PATH_PROJECTS_DIR_STANDARD
    //--posso spostarla (è successo) senza che cambi nulla
    protected String pathIdeaProjects;

    //--regolata iniziale indipendentemente dai risultati del dialogo
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    //--dipende solo da dove si trova attualmente il progetto base VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory dei sorgenti testuali di VaadFlow (da elaborare)
    //--pathVaadFlow più DIR_SOURCES
    protected String pathSources;

    //--regolata da questo dialogo
    //--può essere un new project oppure un update di un progetto esistente
    protected String nameTargetProject = VUOTA;

    //--regolata da questo dialogo
    //--può essere il path completo di un new project oppure di un update esistente
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    protected String pathTargetProject = VUOTA;


    /**
     * Regolazioni grafiche
     */
    protected void inizia() {
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
        this.removeAll();

        //--regolazione di property varie
        this.regolaVariabili();

        //--info di avvisi
        this.creaLegenda();

        //--creazione bottoni di comando
        this.creaBottoni();

        //--solo per newProject
        this.spazzolaDirectory();

        //--checkbox di spunta
        mappaCheckbox = new LinkedHashMap<>();
        creaCheckBoxMap();
        this.addCheckBoxMap();

        //--aggiunge bottoni al layout
        this.addBottoni();

        //--superClasse
        super.open();
    }// end of method


    /**
     * Regolazioni iniziali indipendenti dal dialogo di input
     */
    protected void regolaVariabili() {
        AEWiz.reset();

        //        if (FLAG_DEBUG_WIZ) {
        //            WizCost.printInfo(log);
        //        }// end of if cycle

        this.pathUserDir = System.getProperty("user.dir") + SLASH;
        this.pathVaadFlow = PATH_VAADFLOW_DIR_STANDARD;
        if (isNuovoProgetto && !pathVaadFlow.equals(pathUserDir)) {
            //                        logger.error("Attenzione. La directory di VaadFlow è cambiata", WizDialog.class, "regolaVariabili");
        }// end of if/else cycle

        //valido SOLO per new project
        if (isNuovoProgetto) {
            this.pathIdeaProjects = file.levaDirectoryFinale(pathVaadFlow);
            this.pathIdeaProjects = file.levaDirectoryFinale(pathIdeaProjects);
            if (!pathIdeaProjects.equals(PATH_PROJECTS_DIR_STANDARD)) {
                //                logger.error("Attenzione. La directory dei Projects è cambiata", WizDialog.class, "regolaVariabili");
            }// end of if cycle
        } else {
            File unaDirectory = new File(pathUserDir);
            this.nameTargetProject = unaDirectory.getName();
            this.pathTargetProject = pathUserDir;
        }// end of if/else cycle

        if (isStartThisProgetto) {
            File unaDirectory = new File(pathUserDir);
            this.nameTargetProject = unaDirectory.getName();
            this.pathTargetProject = pathUserDir;
        }

        this.pathSources = pathVaadFlow + DIR_VAADFLOW_SOURCES;

        if (FLAG_DEBUG_WIZ) {
            System.out.println("********************");
            System.out.println("Ingresso del dialogo");
            System.out.println("********************");
            System.out.println("Directory di esecuzione: pathUserDir=" + pathUserDir);
            System.out.println("Directory VaadFlow: pathVaadFlow=" + pathVaadFlow);
            if (isNuovoProgetto) {
                System.out.println("Directory dei nuovi progetti: pathIdeaProjects=" + pathIdeaProjects);
            }// end of if cycle
            System.out.println("Sorgenti VaadFlow: pathSources=" + pathSources);
            System.out.println("");
        }// end of if cycle
    }// end of method


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    protected void creaLegenda() {
        if (isNuovoProgetto) {
            this.titoloCorrente = new H3("Selezione...");
        } else {
            this.titoloCorrente = new H3(text.primaMaiuscola(nameTargetProject));
        }// end of if/else cycle

        layoutTitolo = new VerticalLayout();
        layoutTitolo.setMargin(false);
        layoutTitolo.setSpacing(false);
        layoutTitolo.setPadding(true);
        titoloCorrente.getElement().getStyle().set("color", "blue");
        layoutTitolo.add(titoloCorrente);

        layoutLegenda = new VerticalLayout();
        layoutLegenda.setMargin(false);
        layoutLegenda.setSpacing(false);
        layoutLegenda.setPadding(true);
        layoutLegenda.getElement().getStyle().set("color", "green");
        this.setWidth("25em");

        this.add(layoutTitolo);
        this.add(layoutLegenda);
    }// end of method


    protected void creaBottoni() {
        cancelButton = new Button("Annulla", event -> {
            esceDalDialogo(false);
        });//end of lambda expressions
        cancelButton.setIcon(VaadinIcon.ARROW_LEFT.create());
        cancelButton.getElement().setAttribute("theme", "secondary");
        cancelButton.addClickShortcut(Key.ARROW_LEFT);
        cancelButton.setWidth(NORMAL_WIDTH);
        cancelButton.setHeight(NORMAL_HEIGHT);
        cancelButton.setVisible(true);

        confirmButton = new Button("Conferma", event -> {
            esceDalDialogo(true);
        });//end of lambda expressions
        confirmButton.setIcon(VaadinIcon.EDIT.create());
        confirmButton.getElement().setAttribute("theme", "primary");
        confirmButton.setWidth(NORMAL_WIDTH);
        confirmButton.setHeight(NORMAL_HEIGHT);
        confirmButton.setVisible(true);
        //        confirmButton.setEnabled(!isNuovoProgetto); //@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
    }// end of method


    /**
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera i possibili progetti 'vuoti' <br>
     * Sovrascritto nella sottoclasse <br>
     */
    protected void spazzolaDirectory() {
    }


    /**
     * Crea i checkbox di controllo <br>
     * Spazzola (nella sottoclasse) la Enumeration per aggiungere solo i checkbox adeguati: <br>
     * newProject
     * updateProject
     * newPackage
     * updatePackage
     * Spazzola la Enumeration e regola a 'true' i chekbox secondo il flag 'isAcceso' <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    protected void creaCheckBoxMap() {
    }


    /**
     * Aggiunge al layout i checkbox di controllo <br>
     */
    protected void addCheckBoxMap() {
        layoutCheckBox = new VerticalLayout();
        layoutCheckBox.setMargin(false);
        layoutCheckBox.setSpacing(false);
        layoutCheckBox.setPadding(true);

        for (String key : mappaCheckbox.keySet()) {
            layoutCheckBox.add(mappaCheckbox.get(key));
        }// end of for cycle

        this.add(layoutCheckBox);
    }


    protected void addBottoni() {
        layoutBottoni = new VerticalLayout();
        HorizontalLayout layoutFooter = new HorizontalLayout();
        layoutFooter.setSpacing(true);
        layoutFooter.setMargin(true);

        layoutFooter.add(cancelButton, confirmButton);
        layoutBottoni.add(layoutFooter);
        this.add(layoutBottoni);
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola tutti i valori della Enumeration EAWiz che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected void regolazioniFinali() {
        if (isNuovoProgetto) {
            if (fieldComboProgetti != null && fieldComboProgetti.getValue() != null) {
                nameTargetProject = fieldComboProgetti.getValue().getName();
                pathTargetProject = fieldComboProgetti.getValue().getAbsolutePath() + SLASH;
            }
        }

        regolaEAWiz();
        regolaEAToken();
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola tutti i valori della Enumeration EAWiz che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected void regolaEAWiz() {
        AEWiz.pathUserDir.setValue(pathUserDir);
        AEWiz.pathVaadFlow.setValue(pathVaadFlow);
        AEWiz.pathIdeaProjects.setValue(pathIdeaProjects);
        AEWiz.nameTargetProject.setValue(nameTargetProject);
        AEWiz.pathTargetProjet.setValue(pathTargetProject);

        for (AEWiz flag : AEWiz.values()) {
            if (mappaCheckbox.get(flag.name()) != null) {
                flag.setAcceso(mappaCheckbox.get(flag.name()).getValue());
            }
        }

        //--visualizzazione di controllo
        if (FLAG_DEBUG_WIZ) {
            System.out.println("********************");
            System.out.println("Uscita dal dialogo - EAWiz");
            System.out.println("********************");
            for (AEWiz flag : AEWiz.values()) {
                if (flag.isCheckBox()) {
                    System.out.println("EAWiz." + flag.name() + " \"" + flag.getLabelBox() + "\" = " + flag.isAbilitato());
                } else {
                    System.out.println("EAWiz." + flag.name() + " \"" + flag.getDescrizione() + "\" = " + flag.getValue());
                }
            }
            System.out.println("");
        }
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected void regolaEAToken() {
        AEToken.reset();
        AEToken.nameTargetProject.setValue(nameTargetProject);
        AEToken.pathTargetProjet.setValue(pathTargetProject);

        AEToken.projectNameUpper.setValue(nameTargetProject.toUpperCase());
        AEToken.moduleNameMinuscolo.setValue(nameTargetProject.toLowerCase());
        AEToken.moduleNameMaiuscolo.setValue(text.primaMaiuscola(nameTargetProject));
        AEToken.first.setValue(text.isValid(nameTargetProject) ? nameTargetProject.substring(0, 1).toUpperCase() : VUOTA);

        //--visualizzazione di controllo
        if (FLAG_DEBUG_WIZ) {
            System.out.println("********************");
            System.out.println("Uscita dal dialogo - EAToken");
            System.out.println("********************");
            for (AEToken token : AEToken.values()) {
                if (token.isUsaValue()) {
                    System.out.println("EAToken." + token.name() + "  - " + token.getTokenTag() + " = " + token.getValue());
                }
            }
            System.out.println("");
        }
    }


    /**
     * Esce dal dialogo con due possibilità (a seconda del flag)
     * 1) annulla
     * 2) esegue
     */
    private void esceDalDialogo(boolean esegue) {
        regolazioniFinali();
        this.close();
        if (esegue) {
            wizRecipient.esegue();
        }
    }

}
