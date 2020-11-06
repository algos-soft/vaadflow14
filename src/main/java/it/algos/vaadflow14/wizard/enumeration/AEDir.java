package it.algos.vaadflow14.wizard.enumeration;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 04-nov-2020
 * Time: 19:40
 */
public enum AEDir {
    //--regolata inizialmente, indipendentemente dall' apertura del dialogo
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    pathCurrent("Directory recuperata dal System dove gira il programma in uso"),

    //--regolata inizialmente, indipendentemente dall' apertura del dialogo
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    pathRoot("Directory base in cui si trova la cartella IdeaProjects"),

    //--regolata inizialmente, indipendentemente dall' apertura del dialogo
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    //--dipende solo da dove si trova attualmente il progetto base VaadFlow
    //--dovrebbe essere PATH_VAAD_FLOW_DIR_STANDARD
    //--posso spostarla (è successo) senza che cambi nulla
    pathVaadFlow("Directory che contiene il programma VaadFlow",PATH_VAADFLOW_DIR_STANDARD),

    //--regolata inizialmente, indipendentemente dall' apertura del dialogo
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    //--directory che contiene i nuovi programmi appena creati da Idea
    //--dovrebbe essere PATH_PROJECTS_DIR_STANDARD
    //--posso spostarla (è successo) senza che cambi nulla
    pathIdeaProjects("Directory che contiene i nuovi programmi appena creati da Idea",PATH_PROJECTS_DIR_STANDARD),

    //--regolata inizialmente, indipendentemente dall' apertura del dialogo
    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    pathVaadFlowSources("Directory dei sorgenti testuali di VaadFlow da elaborare"),

    //--può essere un new project oppure un update di un progetto esistente
    //--se siamo in VaadFlow14, regolato dai dialoghi WizDialogNewProject o WizDialogUpdateProject
    //--se siamo in un altro progetto, il nome del progetto stesso
    nameTargetProject("Nome breve new/update project"),

    //--tutte le property il cui nome inizia con 'path' finiscono con SLASH
    //--può essere un new project oppure un update di un progetto esistente
    //--se siamo in VaadFlow14, regolato dai dialoghi WizDialogNewProject o WizDialogUpdateProject
    //--se siamo in un altro progetto, il nome del progetto stesso
    pathTargetProject("Path completo new/update project"),

    ;

    private String descrizione;

    private String defaultValue = VUOTA;

    private String value;


    /**
     * Costruttore per i path con valore stringa <br>
     */
    AEDir(String descrizione) {
        this(descrizione, VUOTA);
    }


    /**
     * Costruttore completo <br>
     */
    AEDir(String descrizione, String defaultValue) {
        this.descrizione = descrizione;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }


    public static void reset() {
        for (AEDir aeDir : AEDir.values()) {
            aeDir.setValue(aeDir.defaultValue);
        }
    }


    public static List<AEDir> geValues() {
        List<AEDir> listaDirs = new ArrayList<>();

        for (AEDir aeDir : AEDir.values()) {
            listaDirs.add(aeDir);
        }

        return listaDirs;
    }


    /**
     * Visualizzazione di controllo <br>
     */
    public static void printInfo(String posizione) {
        if (FLAG_DEBUG_WIZ) {
            System.out.println("********************");
            System.out.println("AEDir  - " + posizione);
            System.out.println("********************");
            for (AEDir aeDir : AEDir.values()) {
                System.out.println("AEDir." + aeDir.name() + " \"" + aeDir.getDescrizione() + "\" = " + aeDir.get());
            }
            System.out.println("");
        }
    }

    /**
     * Check di controllo per verificare se il valore corrente è uguale al valore standard <br>
     */
    public boolean isStandard() {
        return value.equals(defaultValue);
    }

    /**
     * Check di controllo per verificare se il valore corrente non è uguale al valore standard <br>
     */
    public boolean isNotStandard() {
        return !value.equals(defaultValue);
    }

    public String getDescrizione() {
        return descrizione;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public String getDefaultValue() {
        return defaultValue;
    }


    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


    public String get() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }
}
