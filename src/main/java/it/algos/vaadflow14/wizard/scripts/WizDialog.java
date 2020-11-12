package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import it.algos.vaadflow14.wizard.enumeration.AEProgetto;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.LinkedHashMap;

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

    public ADateService date;

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

    protected ComboBox<File> fieldComboProgettiNuovi;

    protected ComboBox<AEProgetto> fieldComboProgetti;


    /**
     * Regolazioni grafiche
     */
    protected void inizia() {
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
        this.removeAll();

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
     * Regola tutti i valori delle enumeration AEDir, AECheck e EAToken che saranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     */
    protected boolean regolazioniFinali() {
        boolean status = true;

        status = status && this.regolaAEDir();
        status = status && this.regolaAECheck();
        status = status && this.regolaAEToken();

        return status;
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Resetta i valori regolabili della Enumeration AEDir <br>
     * Elabora tutti i valori della Enumeration AEDir dipendenti dal nome del progetto <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected boolean regolaAEDir() {
        return true;
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Elabora tutti i valori della Enumeration AECheck dipendenti dal nome del progetto <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     */
    protected boolean regolaAECheck() {
        for (AECheck check : AECheck.values()) {
            if (mappaCheckbox != null && mappaCheckbox.get(check.name()) != null) {
                check.setAcceso(mappaCheckbox.get(check.name()).getValue());
            }
        }

        return true;
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Elabora tutti i valori della Enumeration AEToken dipendenti dal nome del progetto <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     */
    protected boolean regolaAEToken() {
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


        //        Object a = isNuovoPackage;
        //        Object b = fieldPackageName;
        //        if (isNuovoPackage && fieldPackageName != null && text.isValid(fieldPackageName.getValue())) {
        //            //            AEToken.packageName.setValue(fieldPackageName.getValue().toLowerCase());
        //        }

        return true;
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
     * Esce dal dialogo con due possibilità (a seconda del flag) <br>
     * 1) annulla <br>
     * 2) esegue <br>
     */
    protected void esceDalDialogo(boolean esegue) {
        if (esegue) {
            if (!regolazioniFinali()) {
                logger.error("Mancano alcuni dati essenziali per l'elaborazione richiesta", this.getClass(), "esceDalDialogo");
                this.close();
                return;
            }
            wizService.printInfo("Uscita del dialogo");
            this.close();
            wizRecipient.esegue();
        } else {
            this.close();
            Notification.show("Dialogo annullato", 2000, Notification.Position.MIDDLE);
        }
    }

}
