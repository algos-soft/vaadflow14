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
import it.algos.vaadflow14.wizard.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedHashMap;

import static it.algos.vaadflow14.backend.application.FlowCost.VIRGOLA;
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

    protected ComboBox<String> fieldComboPackages;


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
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected boolean regolaAEToken() {
        boolean status = true;
        String projectName;
        AEToken.reset();

        projectName = AEDir.nameTargetProject.get();

        if (text.isValid(projectName)) {
            AEToken.nameTargetProject.setValue(projectName);
            AEToken.projectNameUpper.setValue(projectName.toUpperCase());
            AEToken.moduleNameMinuscolo.setValue(projectName.toLowerCase());
            AEToken.moduleNameMaiuscolo.setValue(text.primaMaiuscola(projectName));
            AEToken.first.setValue(projectName.substring(0, 1).toUpperCase());
            AEToken.packageName.setValue(projectName.toLowerCase());
            AEToken.user.setValue(AEDir.nameUser.get());
            AEToken.today.setValue(date.getCompletaShort(LocalDate.now()));
            AEToken.time.setValue(date.getOrario());
            AEToken.versionDate.setValue(fixVersion());
            AEToken.entity.setValue(text.primaMaiuscola(projectName));
            AEToken.usaSecurity.setValue(AECheck.security.is() ? ")" : ", exclude = {SecurityAutoConfiguration.class}");
        }

        return status;
    }


    protected String fixVersion() {
        String versione = VUOTA;
        String tag = "LocalDate.of(";
        String anno;
        String mese;
        String giorno;
        LocalDate localDate = LocalDate.now();

        anno = localDate.getYear() + VUOTA;
        mese = localDate.getMonth().getValue() + VUOTA;
        giorno = localDate.getDayOfMonth() + VUOTA;
        versione = tag + anno + VIRGOLA + mese + VIRGOLA + giorno + ")";

        return versione;
    }


    protected String fixProperties() {
        String testo = VUOTA;

        if (AECheck.ordine.is()) {
            testo += AECheck.ordine.getField() + VIRGOLA;
        }

        if (AECheck.code.is()) {
            testo += AECheck.code.getField() + VIRGOLA;
        }

        if (AECheck.descrizione.is()) {
            testo += AECheck.descrizione.getField() + VIRGOLA;
        }

        testo = text.levaCoda(testo, VIRGOLA);
        return text.setApici(testo).trim();
    }


    protected String fixProperty(AECheck check) {
        String testo = VUOTA;
        String sourceText = VUOTA;
        String tagSources = check.getSourcesTag();

        if (check.is()) {
            sourceText = wizService.leggeFile(tagSources);
            testo = wizService.elaboraFileCreatoDaSource(sourceText);
        }

        return testo;
    }


    protected String fixString() {
        String toString = "VUOTA";

        if (AECheck.code.is()) {
            toString = "code";
        }
        else {
            if (AECheck.descrizione.is()) {
                toString = "descrizione";
            }
        }

        return toString;
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
                if (AEFlag.isNewPackage.is()) {
                    logger.info("Manca il nome del nuovo package che non può quindi essere creato ", this.getClass(), "esceDalDialogo");
                }
                else {
                    logger.info("Mancano alcuni dati essenziali per l'elaborazione richiesta, che è stata quindi abortita", this.getClass(), "esceDalDialogo");
                }
                this.close();
                return;
            }
            wizService.printInfoCompleto("Uscita del dialogo");

            this.close();
            wizRecipient.esegue();
        }
        else {
            this.close();
            Notification.show("Dialogo annullato", 2000, Notification.Position.MIDDLE);
        }
    }

}
