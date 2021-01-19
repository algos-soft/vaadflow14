package it.algos.vaadflow14.wizard.enumeration;

import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.enumeration.AECopyDir;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.SLASH;
import static it.algos.vaadflow14.wizard.scripts.WizCost.VALORE_MANCANTE;

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
    pathCurrent("Directory dove gira il programma in uso. Recuperata dal System", VALORE_MANCANTE),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameUser("Programmatore. Ricavato dal path della directory corrente.", VALORE_MANCANTE),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    projectCurrent("Nome del programma in uso. Ricavato dal path della directory corrente", VALORE_MANCANTE),

    /**
     * Root del progetto target. Hardcoded su di un singolo computer. <br>
     * Valore standard che verrà controllato in funzione di AEDIR.pathCurrent effettivo <br>
     * Potrebbe essere diverso <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectRoot("Path root del progetto target", VALORE_MANCANTE),

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
     * Cartella a livello di modulo. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirModulo("Percorso standard completo files java", "src/main/java/it/algos/"),

    /**
     * Cartella a livello di modulo. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirModuloVaadFlow14("Percorso standard completo modulo base", dirModulo.value + dirVaadFlow14.value),

    /**
     * Percorso del modulo base vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Modulo("Path del modulo base vaadflow14", pathVaadFlow14Root.value + dirModuloVaadFlow14.value),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootConfig(true, true, String.format("Directory root CONFIG di risorse on-line esterne al JAR (%s)", nameVaadFlow14.value), "config/"),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootDoc(true, true, String.format("Directory root DOC di documentazione (%s)", nameVaadFlow14.value), "doc/", true),

    /**
     * Cartella a livello di root. <br>
     * contiene images/ (di solito)
     * contiene src/ (di solito)
     * contiene styles/ (sempre)
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootFrontend(true, true, String.format("Directory root FRONTEND per il Client (%s)", nameVaadFlow14.value), "frontend/"),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootLinks(true, true, String.format("Directory root LINKS a siti web utili (%s)", nameVaadFlow14.value), "links/"),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootSnippets(true, true, String.format("Directory root SNIPPETS di codice suggerito (%s)", nameVaadFlow14.value), "snippets/"),

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
    pathTargetProjectModulo("Modulo del progetto target", VALORE_MANCANTE),

    /**
     * Nome del file wizard. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameWizard("Nome del file wizard", "Wizard.java"),

    ;

    private boolean newProject;

    private boolean updateProject;

    private String descrizione;

    private String value;

    private boolean accesoInizialmente;

    private boolean acceso;
    private AECopyDir copy;


    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione, String value) {
        this(false, false, descrizione, value);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(boolean newProject, boolean updateProject, String descrizione, String value) {
        this(newProject, updateProject, descrizione, value, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(boolean newProject, boolean updateProject, String descrizione, String value, boolean accesoInizialmente) {
        this(newProject, updateProject, descrizione, value, accesoInizialmente, AECopyDir.addingOnly);
    }

    /**
     * Costruttore completo <br>
     */
    AEWizCost(boolean newProject, boolean updateProject, String descrizione, String value, boolean accesoInizialmente, AECopyDir copy) {
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.descrizione = descrizione;
        this.value = value;
        this.accesoInizialmente = accesoInizialmente;
        this.acceso = accesoInizialmente;
        this.copy = copy;
    }

    //--metodo statico invocato da Wizard.initView()
    public static void printInfo() {
        System.out.println(VUOTA);
        System.out.println("********************");
        System.out.println("Costanti statiche indipendenti dal progetto che sta girando");
        System.out.println("********************");
        for (AEWizCost cost : AEWizCost.values()) {
            System.out.println("AEWizCost." + cost.name() + ": \"" + cost.descrizione + "\" " + FlowCost.UGUALE + cost.value);
        }
        System.out.println(VUOTA);
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isNewProject() {
        return newProject;
    }

    public void setNewProject(boolean newProject) {
        this.newProject = newProject;
    }

    public boolean isUpdateProject() {
        return updateProject;
    }

    public void setUpdateProject(boolean updateProject) {
        this.updateProject = updateProject;
    }

    public boolean isAccesoInizialmente() {
        return accesoInizialmente;
    }

    public void setAccesoInizialmente(boolean accesoInizialmente) {
        this.accesoInizialmente = accesoInizialmente;
    }

    public boolean isAcceso() {
        return acceso;
    }

    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public AECopyDir getCopy() {
        return copy;
    }

    public void setCopy(AECopyDir copy) {
        this.copy = copy;
    }

    //            System.out.println("PATH_VAADFLOW_DIR_STANDARD = " + PATH_VAADFLOW_DIR_STANDARD);
    //            System.out.println("DIR_MAIN = " + ROOT_DIR_MAIN);
    //            System.out.println("DIR_ALGOS = " + ROOT_DIR_ALGOS);
    //            //            System.out.println("DIR_RESOURCES = " + DIR_RESOURCES);
    //            System.out.println("DIR_FRONTEND = " + ROOT_DIR_FRONTEND);
    //            System.out.println("DIR_VAADFLOW_SOURCES = " + DIR_VAADFLOW_SOURCES);
    //            System.out.println("");
    //        }
    //    }

}
