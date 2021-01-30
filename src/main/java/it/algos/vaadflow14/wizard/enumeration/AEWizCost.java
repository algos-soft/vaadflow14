package it.algos.vaadflow14.wizard.enumeration;

import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.wizard.scripts.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;

import java.util.*;

/**
 * Project vaadwiki14
 * Created by Algos
 * User: gac
 * Date: lun, 18-gen-2021
 * Time: 07:09
 * <p>
 * Costanti statiche praticamente fisse <br>
 * Possono essere (eventualmente) modificate se modifico la struttura delle directory che uso <br>
 * Gestite come Enumeration per tenerle sotto controllo, spazzolarle e stamparle tutte insieme <br>
 */
public enum AEWizCost {

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathCurrent("Directory dove gira il programma in uso. Recuperata dal System"),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameUser("Programmatore. Ricavato dal path della directory corrente."),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    projectCurrent("Nome del programma in uso. Ricavato dal path della directory corrente"),

    /**
     * Root del progetto target. Hardcoded su di un singolo computer. <br>
     * Valore standard che verrà controllato in funzione di AEDIR.pathCurrent effettivo <br>
     * Potrebbe essere diverso <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectRoot("Path root del progetto target"),

    /**
     * Root iniziale. Hardcoded su di un singolo computer. <br>
     * Valore standard che verrà controllato in funzione di AEDIR.pathCurrent effettivo <br>
     * Potrebbe essere diverso <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathRoot("Root iniziale del computer utilizzato", "/Users/gac/Documents/"),

    /**
     * Cartella base dei progetti. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirProjects("Cartella di partenza dei projects Idea", "IdeaProjects/"),

    /**
     * Percorso base dei progetti. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathProjectsDirStandard("Path base dei projects Idea", pathRoot.value + dirProjects.value),

    /**
     * Cartella dei progetti operativi. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirOperativi("Cartella dei projects operativi", "operativi/"),

    /**
     * Percorso dei progetti operativi. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathOperativiDirStandard("Path dei projects operativi", pathProjectsDirStandard.value + dirOperativi.value),

    /**
     * Nome del progetto base vaadflow14. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameVaadFlow14("Nome del progetto base vaadflow14", "Vaadflow14"),

    /**
     * Cartella del progetto base vaadflow14. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirVaadFlow14("Cartella del progetto base vaadflow14", nameVaadFlow14.value.toLowerCase() + SLASH),

    /**
     * Percorso del progetto base vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Root("Path root del progetto base vaadflow14", pathOperativiDirStandard.value + dirVaadFlow14.value),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirMain("Directory files main", "src/main/"),

    /**
     * Cartella a livello di modulo. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirModulo("Directory files modulo", dirMain.value + "java/it/algos/"),

    /**
     * Cartella a livello di resources. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirResources("Directory resources", dirMain.value + "resources/"),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootConfig(String.format("Directory root CONFIG di risorse on-line esterne al JAR (da %s)", nameVaadFlow14.value), "config/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootDoc(String.format("Directory root DOC di documentazione (da %s)", nameVaadFlow14.value), "doc/", AECopyWiz.dirDeletingAll),

    /**
     * Cartella a livello di root. <br>
     * contiene images/ (di solito)
     * contiene src/ (di solito)
     * contiene styles/ (sempre)
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootFrontend(String.format("Directory root FRONTEND per il Client (da %s)", nameVaadFlow14.value), "frontend/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootLinks(String.format("Directory root LINKS a siti web utili (da %s)", nameVaadFlow14.value), "links/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootSnippets(String.format("Directory root SNIPPETS di codice suggerito (da %s)", nameVaadFlow14.value), "snippets/", AECopyWiz.dirSoloSeNonEsiste),

    /**
     * Cartella a livello di modulo. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirModuloVaadFlow14(String.format("Directory modulo BASE %s (da %s, Wizard compreso)", nameVaadFlow14.value, nameVaadFlow14.value), dirModulo.value + dirVaadFlow14.value, AECopyWiz.dirDeletingAll),

    /**
     * Percorso del modulo base vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Modulo("Path del modulo base vaadflow14", pathVaadFlow14Root.value + dirModuloVaadFlow14.value),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootWizard("Directory wizard", dirModuloVaadFlow14.value + "wizard/"),

    /**
     * Percorso della directory wizard sources di vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Wizard("Directory vaadflow14.wizard", pathVaadFlow14Root.value + dirRootWizard.value),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootWizardEnumeration("Directory wizard.enumeration", dirRootWizard.value + "enumeration/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootWizardScripts("Directory wizard.scripts", dirRootWizard.value + "scripts/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirVaadFlow14WizardSources("Directory sources", dirRootWizard.value + "sources/"),

    /**
     * Percorso della directory wizard sources di vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14WizSources("Directory vaadflow14.wizard.sources", pathVaadFlow14Root.value + dirVaadFlow14WizardSources.value),

    /**
     * Modulo del progetto target. Hardcoded su di un singolo computer. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectModulo("Directory nuovo MODULO di questo progetto", VALORE_MANCANTE, AECopyWiz.dirAddingOnly),

    /**
     * Nome della directory sources. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameSources("Nome della directory sources", "../vaadflow14/wizard/sources"),

    /**
     * Nome del file wizard. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameWizard("Nome del file wizard", "Wizard.java"),

    /**
     * File. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileProperties(String.format("File application.properties (da %s)", nameSources.value), dirResources.value + "application.properties", AECopyWiz.sourceCheckFlagSeEsiste, "properties", dirMain.value),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootGit(String.format("File root GIT di esclusione (da %s)", nameSources.value), ".gitignore", AECopyWiz.sourceSovrascriveSempreAncheSeEsiste),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootPOM(String.format("File root POM.xml di Maven (da %s)", nameSources.value), "pom.xml", AECopyWiz.sourceCheckFlagSeEsiste),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootREAD(String.format("File root README con note di testo (da %s)", nameSources.value), "README.txt", AECopyWiz.sourceSoloSeNonEsiste),

    ;

    private String descrizione;

    private String value;

    private String sourcesName;

    private String pathBreve;

    private boolean newProject;

    private boolean updateProject;

    private boolean accesoInizialmente;

    private boolean acceso;

    private AECopyWiz copyWiz;


    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione) {
        this(descrizione, WizCost.VALORE_MANCANTE);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione, String value) {
        this(descrizione, value, (AECopyWiz) null, FlowCost.VUOTA, FlowCost.VUOTA, false, false, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione, String value, AECopyWiz copyWiz) {
        this(descrizione, value, copyWiz, value, FlowCost.VUOTA, true, true, false);
    }


    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione, String value, AECopyWiz copyWiz, String sourcesName) {
        this(descrizione, value, copyWiz, sourcesName, FlowCost.VUOTA, true, true, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione, String value, AECopyWiz copyWiz, String sourcesName, String pathBreve) {
        this(descrizione, value, copyWiz, sourcesName, pathBreve, true, true, false);
    }


    /**
     * Costruttore completo <br>
     */
    AEWizCost(String descrizione, String value, AECopyWiz copyWiz, String sourcesName, String pathBreve, boolean newProject, boolean updateProject, boolean accesoInizialmente) {
        this.descrizione = descrizione;
        this.value = value;
        this.copyWiz = copyWiz;
        this.sourcesName = sourcesName;
        this.pathBreve = pathBreve;
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.accesoInizialmente = accesoInizialmente;
    }

    //--metodo statico invocato da Wizard.initView()
    public static void printInfo() {
        System.out.println(FlowCost.VUOTA);
        System.out.println("********************");
        System.out.println("Costanti statiche indipendenti dal progetto che sta girando");
        System.out.println("********************");
        for (AEWizCost aeWizCost : AEWizCost.values()) {
            System.out.print("AEWizCost." + aeWizCost.name() + ": \"" + aeWizCost.descrizione + "\" " + FlowCost.UGUALE + aeWizCost.value);
            if (aeWizCost.isNewProject() || aeWizCost.isUpdateProject()) {
                System.out.print(FlowCost.FORWARD + "AECopy." + aeWizCost.copyWiz.name());
            }
            System.out.println(FlowCost.VUOTA);
        }
        System.out.println(FlowCost.VUOTA);
    }

    public static List<AEWizCost> getNewProject() {
        List<AEWizCost> listaCost = new ArrayList<>();

        for (AEWizCost aeCheck : AEWizCost.values()) {
            if (aeCheck.isNewProject()) {
                listaCost.add(aeCheck);
            }
        }

        return listaCost;
    }

    public static List<AEWizCost> getUpdateProject() {
        List<AEWizCost> listaCost = new ArrayList<>();

        for (AEWizCost aeCheck : AEWizCost.values()) {
            if (aeCheck.isUpdateProject()) {
                listaCost.add(aeCheck);
            }
        }

        return listaCost;
    }


    public static List<AEWizCost> getNewUpdateProject() {
        List<AEWizCost> listaCost;
        if (AEFlag.isBaseFlow.is()) {
            listaCost = AEWizCost.getNewProject();
        }
        else {
            listaCost = AEWizCost.getUpdateProject();
        }

        return listaCost;
    }


    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSourcesName() {
        return sourcesName;
    }

    public String getPathBreve() {
        return pathBreve;
    }

    public boolean isNewProject() {
        return newProject;
    }

    public boolean isUpdateProject() {
        return updateProject;
    }

    public boolean isAccesoInizialmente() {
        return accesoInizialmente;
    }

    public boolean isAcceso() {
        return acceso;
    }

    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public AECopyWiz getCopyWiz() {
        return copyWiz;
    }

}
