package it.algos.vaadflow14.wizard.enumeration;

import it.algos.vaadflow14.backend.application.*;
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
    projectCurrentLower("Nome minuscolo del programma in uso. Ricavato dal path della directory corrente", VALORE_MANCANTE),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    projectCurrent("Nome del programma in uso. Ricavato dal path della directory corrente", VALORE_MANCANTE),

    /**
     * Nome del progetto target. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetProject("Nome del progetto target", VALORE_MANCANTE),

    /**
     * Nome minuscolo del progetto target. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetProjectLower("Nome minuscolo del progetto target", VALORE_MANCANTE),

    /**
     * Root del progetto target. <br>
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
    dirRootConfig(String.format("Directory root CONFIG di risorse on-line esterne al JAR (da %s)", nameVaadFlow14.value), "config/", AECopyWiz.dirAddingOnly, "", ""),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootDoc(String.format("Directory root DOC di documentazione (da %s)", nameVaadFlow14.value), "doc/", AECopyWiz.dirAddingOnly),

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
    dirRootSnippets(String.format("Directory root SNIPPETS di codice suggerito (da %s)", nameVaadFlow14.value), "snippets/", AECopyWiz.dirAddingOnly),

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
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirWizardSources("Directory wizard/sources",  "wizard/sources/"),

    /**
     * Percorso della directory wizard sources di vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14WizSources("Directory vaadflow14.wizard.sources", pathVaadFlow14Root.value + dirVaadFlow14WizardSources.value),

    /**
     * Modulo del progetto target. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectModulo("Directory MODULO del progetto", VALORE_MANCANTE, AECopyWiz.dirAddingOnly),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirBoot("Nome della directory boot", "backend/boot/"),

    /**
     * Percorso della directory boot. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectBoot("Directory target boot", pathTargetProjectModulo.value + dirBoot.value),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirPackages("Nome della directory packages del modulo target", "backend/packages/"),

    /**
     * Percorso della directory packages. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectPackages("Directory target packages", pathTargetProjectModulo.value + dirPackages.value),

    /**
     * Percorso della directory target sources. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectSources("Directory target sources (da cancellare)", pathTargetProjectModulo.value + "wizard/sources/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirBackend("Nome della directory backend del modulo target", "backend/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirApplication("Nome della directory application del modulo target", "backend/application/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirData("Nome della directory data del modulo target", "backend/data/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirUI("Nome della directory UI del modulo target", "ui/"),

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
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirMetaInf(String.format("Directory META-INF (da %s)", nameVaadFlow14.value), dirResources.value + "META-INF", AECopyWiz.dirAddingOnly, "", "resources"),

    /**
     * File. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileProperties(String.format("File application.properties (da %s)", nameSources.value), dirResources.value + "application.properties", AECopyWiz.sourceCheckFlagSeEsiste, "properties", dirMain.value),

    /**
     * File. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileBanner(String.format("File banner (da %s)", nameSources.value), dirResources.value + "banner.txt", AECopyWiz.sourceSoloSeNonEsiste, "banner.txt"),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootGit(String.format("File root GIT di esclusione (da %s)", nameSources.value), ".gitignore", AECopyWiz.sourceSovrascriveSempreAncheSeEsiste),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootPOM(String.format("File root POM.xml di Maven (da %s)", nameSources.value), "pom.xml", AECopyWiz.sourceCheckFlagSeEsiste, "pom"),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootREAD(String.format("File root README con note di testo (da %s)", nameSources.value), "README.txt", AECopyWiz.sourceCheckFlagSeEsiste),

    /**
     * Nome del package da creare. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetPackage("Nome del package da creare/modificare", VALORE_MANCANTE),

    /**
     * Nome del package da creare. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetPackageUpper("Nome del package da creare/modificare iniziale maiuscola", VALORE_MANCANTE),

    /**
     * Percorso del package. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetSingoloPackage("Directory del package da creare/modificare", VALORE_MANCANTE),

    ;

    private String descrizione;

    private String value;

    private String sourcesName;

    private String pathBreve;

    private boolean newProject;

    private boolean updateProject;

    private boolean accesoInizialmenteNew;

    private boolean accesoInizialmenteUpdate;

    private boolean acceso;

    private AECopyWiz copyWiz;

    private boolean isValida;

    //    /**
    //     * Costruttore parziale <br>
    //     */
    //    AEWizCost(String descrizione) {
    //        this(descrizione, WizCost.VALORE_MANCANTE);
    //    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione, String value) {
        this(descrizione, value, (AECopyWiz) null, FlowCost.VUOTA, FlowCost.VUOTA, false, false, true, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione, String value, AECopyWiz copyWiz) {
        this(descrizione, value, copyWiz, value, FlowCost.VUOTA, true, true, true, false);
    }


    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione, String value, AECopyWiz copyWiz, String sourcesNameSeDiversoDaValue) {
        this(descrizione, value, copyWiz, sourcesNameSeDiversoDaValue, FlowCost.VUOTA, true, true, true, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(String descrizione, String value, AECopyWiz copyWiz, String sourcesNameSeDiversoDaValue, String pathBreve) {
        this(descrizione, value, copyWiz, sourcesNameSeDiversoDaValue, pathBreve, true, true, true, false);
    }


    /**
     * Costruttore completo <br>
     */
    AEWizCost(String descrizione, String value, AECopyWiz copyWiz, String sourcesNameSeDiversoDaValue, String pathBreve, boolean newProject, boolean updateProject, boolean accesoInizialmenteNew, boolean accesoInizialmenteUpdate) {
        this.descrizione = descrizione;
        this.value = value;
        this.copyWiz = copyWiz;
        this.sourcesName = sourcesNameSeDiversoDaValue;
        this.pathBreve = pathBreve;
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.accesoInizialmenteNew = accesoInizialmenteNew;
        this.accesoInizialmenteNew = false;
        this.accesoInizialmenteUpdate = accesoInizialmenteUpdate;
    }


    public static List<AEWizCost> getNewProject() {
        List<AEWizCost> listaWizCost = new ArrayList<>();

        for (AEWizCost aeWizCost : AEWizCost.values()) {
            if (aeWizCost.isNewProject()) {
                listaWizCost.add(aeWizCost);
            }
        }

        return listaWizCost;
    }

    public static List<AEWizCost> getUpdateProject() {
        List<AEWizCost> listaWizCost = new ArrayList<>();

        for (AEWizCost aeWizCost : AEWizCost.values()) {
            if (aeWizCost.isUpdateProject()) {
                listaWizCost.add(aeWizCost);
            }
        }

        return listaWizCost;
    }


    public static List<AEWizCost> getNewUpdateProject() {
        List<AEWizCost> listaWizCost;
        if (AEFlag.isBaseFlow.is()) {
            listaWizCost = AEWizCost.getNewProject();
        }
        else {
            listaWizCost = AEWizCost.getUpdateProject();
        }

        return listaWizCost;
    }

    public static List<AEWizCost> getValide() {
        List<AEWizCost> listaWizCost = new ArrayList<>();

        for (AEWizCost aeWizCost : AEWizCost.values()) {
            if (aeWizCost.value != null && aeWizCost.value.length() > 0 && !aeWizCost.value.startsWith(VALORE_MANCANTE)) {
                listaWizCost.add(aeWizCost);
            }
        }

        return listaWizCost;
    }

    public static List<AEWizCost> getVuote() {
        List<AEWizCost> listaWizCost = new ArrayList<>();

        for (AEWizCost aeWizCost : AEWizCost.values()) {
            if (aeWizCost.value != null && aeWizCost.value.length() > 0 && !aeWizCost.value.startsWith(VALORE_MANCANTE)) {
            }
            else {
                listaWizCost.add(aeWizCost);
            }
        }

        return listaWizCost;
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
                System.out.print(FlowCost.FORWARD + "AECopyWiz." + aeWizCost.copyWiz.name());
            }
            System.out.println(FlowCost.VUOTA);
        }
        System.out.println(FlowCost.VUOTA);
    }


    //--metodo statico
    public static void printVuote() {
        System.out.println(FlowCost.VUOTA);
        System.out.println("********************");
        System.out.println("Costanti statiche a cui manca ancora il valore");
        System.out.println("********************");
        for (AEWizCost aeWizCost : AEWizCost.getVuote()) {
            System.out.println("AEWizCost." + aeWizCost.name() + ": " + aeWizCost.descrizione);
        }
        System.out.println(FlowCost.VUOTA);
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String get() {
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

    public boolean isAccesoInizialmenteNew() {
        return accesoInizialmenteNew;
    }

    public boolean isAccesoInizialmenteUpdate() {
        return accesoInizialmenteUpdate;
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
