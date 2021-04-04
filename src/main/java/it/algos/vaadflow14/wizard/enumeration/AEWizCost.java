package it.algos.vaadflow14.wizard.enumeration;

import it.algos.vaadflow14.backend.application.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static it.algos.vaadflow14.backend.application.FlowCost.SLASH;
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
    pathCurrent(AETypeWiz.sistema, "Directory dove gira il programma in uso. Recuperata dal System", VALORE_MANCANTE),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameUser(AETypeWiz.sistema, "Programmatore. Ricavato dal path della directory corrente.", VALORE_MANCANTE),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    projectCurrentLower(AETypeWiz.sistema, "Nome minuscolo del programma in uso. Ricavato dal path della directory corrente", VALORE_MANCANTE),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    projectCurrentUpper(AETypeWiz.sistema, "Nome maiuscolo del programma in uso. Ricavato dal path della directory corrente", VALORE_MANCANTE),

    /**
     * Nome del progetto target. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetProjectUpper(AETypeWiz.nomeFile, "Nome maiuscolo del progetto target", VALORE_MANCANTE),

    /**
     * Nome minuscolo del progetto target. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetProjectLower(AETypeWiz.nomeFile, "Nome minuscolo del progetto target", VALORE_MANCANTE),

    /**
     * Root del progetto target. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectRoot(AETypeWiz.path, "Path root del progetto target", VALORE_MANCANTE),

    /**
     * Root iniziale. Hardcoded su di un singolo computer. <br>
     * Valore standard che verrà controllato in funzione di AEDIR.pathCurrent effettivo <br>
     * Potrebbe essere diverso <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathRoot(AETypeWiz.directory, "Root iniziale del computer utilizzato", "/Users/gac/Documents/"),

    /**
     * Cartella base dei progetti. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirProjects(AETypeWiz.directory, "Cartella di partenza dei projects Idea", "IdeaProjects/"),

    /**
     * Percorso base dei progetti. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathProjectsDirStandard(AETypeWiz.directory, "Path base dei projects Idea", pathRoot.value + dirProjects.value),

    /**
     * Cartella dei progetti operativi. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirOperativi(AETypeWiz.directory, "Cartella dei projects operativi", "operativi/"),

    /**
     * Percorso dei progetti operativi. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathOperativiDirStandard(AETypeWiz.directory, "Path dei projects operativi", pathProjectsDirStandard.value + dirOperativi.value),

    /**
     * Nome del progetto base vaadflow14. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameVaadFlow14(AETypeWiz.directory, "Nome del progetto base vaadflow14", "Vaadflow14"),

    /**
     * Cartella del progetto base vaadflow14. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirVaadFlow14(AETypeWiz.directory, "Cartella del progetto base vaadflow14", nameVaadFlow14.value.toLowerCase() + SLASH),

    /**
     * Percorso del progetto base vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Root(AETypeWiz.directory, "Path root del progetto base vaadflow14", pathOperativiDirStandard.value + dirVaadFlow14.value),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirMain(AETypeWiz.directory, "Directory files main", "src/main/"),

    /**
     * Cartella a livello di modulo. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirModulo(AETypeWiz.directory, "Directory files modulo", dirMain.value + "java/it/algos/"),

    /**
     * Cartella a livello di resources. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirResources(AETypeWiz.directory, "Directory resources", dirMain.value + "resources/"),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootConfig(AETypeWiz.directory, String.format("Directory root CONFIG di risorse on-line esterne al JAR (da %s)", nameVaadFlow14.value), "config/", AECopyWiz.dirAddingOnly, "", ""),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootDoc(AETypeWiz.directory, String.format("Directory root DOC di documentazione (da %s)", nameVaadFlow14.value), "doc/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di root. <br>
     * contiene images/ (di solito)
     * contiene src/ (di solito)
     * contiene styles/ (sempre)
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootFrontend(AETypeWiz.directory, String.format("Directory root FRONTEND per il Client (da %s)", nameVaadFlow14.value), "frontend/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootLinks(AETypeWiz.directory, String.format("Directory root LINKS a siti web utili (da %s)", nameVaadFlow14.value), "links/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootSnippets(AETypeWiz.directory, String.format("Directory root SNIPPETS di codice suggerito (da %s)", nameVaadFlow14.value), "snippets/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di modulo. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirModuloVaadFlow14(AETypeWiz.directory, String.format("Directory modulo BASE %s (da %s, Wizard compreso)", nameVaadFlow14.value, nameVaadFlow14.value), dirModulo.value + dirVaadFlow14.value, AECopyWiz.dirDeletingAll,VUOTA,VUOTA,true,true,true,true),

    /**
     * Percorso del modulo base vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Modulo(AETypeWiz.directory, "Path del modulo base vaadflow14", pathVaadFlow14Root.value + dirModuloVaadFlow14.value),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootWizard(AETypeWiz.directory, "Directory wizard", dirModuloVaadFlow14.value + "wizard/"),

    /**
     * Percorso della directory wizard sources di vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Wizard(AETypeWiz.directory, "Directory vaadflow14.wizard", pathVaadFlow14Root.value + dirRootWizard.value),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootWizardEnumeration(AETypeWiz.directory, "Directory wizard.enumeration", dirRootWizard.value + "enumeration/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootWizardScripts(AETypeWiz.directory, "Directory wizard.scripts", dirRootWizard.value + "scripts/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirVaadFlow14WizardSources(AETypeWiz.directory, "Directory sources", dirRootWizard.value + "sources/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirWizardSources(AETypeWiz.directory, "Directory wizard/sources", "wizard/sources/"),

    /**
     * Percorso della directory wizard sources di vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14WizSources(AETypeWiz.directory, "Directory vaadflow14.wizard.sources", pathVaadFlow14Root.value + dirVaadFlow14WizardSources.value),

    /**
     * Modulo del progetto target. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectModulo(AETypeWiz.path, "Directory MODULO del progetto", VALORE_MANCANTE, AECopyWiz.dirAddingOnly),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirBoot(AETypeWiz.directory, "Nome della directory boot", "backend/boot/"),

    /**
     * Percorso della directory boot. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectBoot(AETypeWiz.path, "Directory target boot", pathTargetProjectModulo.value + dirBoot.value),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirPackages(AETypeWiz.directory, "Nome della directory packages del modulo target", "backend/packages/"),

    /**
     * Percorso della directory packages. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectPackages(AETypeWiz.path, "Directory target packages", pathTargetProjectModulo.value + dirPackages.value),

    /**
     * Percorso della directory target sources. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectSources(AETypeWiz.path, "Directory target sources (da cancellare)", pathTargetProjectModulo.value + "wizard/sources/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirBackend(AETypeWiz.directory, "Nome della directory backend del modulo target", "backend/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirApplication(AETypeWiz.directory, "Nome della directory application del modulo target", "backend/application/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirData(AETypeWiz.directory, "Nome della directory data del modulo target", "backend/data/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirUI(AETypeWiz.directory, "Nome della directory UI del modulo target", "ui/"),

    /**
     * Nome della directory sources. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameSources(AETypeWiz.directory, "Nome della directory sources", "../vaadflow14/wizard/sources"),

    /**
     * Nome del file wizard. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameWizard(AETypeWiz.directory, "Nome del file wizard", "Wizard.java"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirMetaInf(AETypeWiz.directory, String.format("Directory META-INF (da %s)", nameVaadFlow14.value), dirResources.value + "META-INF", AECopyWiz.dirAddingOnly, "", "resources"),

    /**
     * File. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileProperties(AETypeWiz.directory, String.format("File application.properties (da %s)", nameSources.value), dirResources.value + "application.properties", AECopyWiz.sourceCheckFlagSeEsiste, "properties", dirMain.value),

    /**
     * File. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileBanner(AETypeWiz.directory, String.format("File banner (da %s)", nameSources.value), dirResources.value + "banner.txt", AECopyWiz.sourceSoloSeNonEsiste, "banner.txt"),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootGit(AETypeWiz.directory, String.format("File root GIT di esclusione (da %s)", nameSources.value), ".gitignore", AECopyWiz.sourceSovrascriveSempreAncheSeEsiste),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootPOM(AETypeWiz.directory, String.format("File root POM.xml di Maven (da %s)", nameSources.value), "pom.xml", AECopyWiz.sourceCheckFlagSeEsiste, "pom"),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootREAD(AETypeWiz.directory, String.format("File root README con note di testo (da %s)", nameSources.value), "README.txt", AECopyWiz.sourceCheckFlagSeEsiste),


    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootTEST(AETypeWiz.directory, String.format("File root/test ATEST (da %s)", nameVaadFlow14.value), "src/test/java/it/algos/test/ATest.java", AECopyWiz.fileCheckFlagSeEsiste),


    /**
     * Nome del package da creare. Eventualmente con sub-directory <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetPackagePunto(AETypeWiz.packages, "Nome del package da creare/modificare usando punto", VALORE_MANCANTE),

    /**
     * Nome del package da creare. Eventualmente con sub-directory <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetPackageSlash(AETypeWiz.packages, "Nome del package da creare/modificare usando slash", VALORE_MANCANTE),

    /**
     * Nome del package da creare. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetFileUpper(AETypeWiz.packages, "Nome del file da creare/modificare con iniziale maiuscola", VALORE_MANCANTE),

    /**
     * Percorso del package. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetPackageSlash(AETypeWiz.packages, "Directory del package da creare/modificare", VALORE_MANCANTE),

    ;

    private AETypeWiz type;

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
    AEWizCost(AETypeWiz type, String descrizione, String value) {
        this(type, descrizione, value, (AECopyWiz) null, VUOTA, VUOTA, false, false, true, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(AETypeWiz type, String descrizione, String value, AECopyWiz copyWiz) {
        this(type, descrizione, value, copyWiz, value, VUOTA, true, true, true, false);
    }


    /**
     * Costruttore parziale <br>
     */
    AEWizCost(AETypeWiz type, String descrizione, String value, AECopyWiz copyWiz, String sourcesNameSeDiversoDaValue) {
        this(type, descrizione, value, copyWiz, sourcesNameSeDiversoDaValue, VUOTA, true, true, true, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(AETypeWiz type, String descrizione, String value, AECopyWiz copyWiz, String sourcesNameSeDiversoDaValue, String pathBreve) {
        this(type, descrizione, value, copyWiz, sourcesNameSeDiversoDaValue, pathBreve, true, true, true, false);
    }


    /**
     * Costruttore completo <br>
     */
    AEWizCost(AETypeWiz type, String descrizione, String value, AECopyWiz copyWiz, String sourcesNameSeDiversoDaValue, String pathBreve, boolean newProject, boolean updateProject, boolean accesoInizialmenteNew, boolean accesoInizialmenteUpdate) {
        this.type = type;
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


    private static List<AEWizCost> getTypeWiz(AETypeWiz type) {
        List<AEWizCost> listaWizCost = new ArrayList<>();

        for (AEWizCost aeWizCost : AEWizCost.values()) {
            if (aeWizCost.getType() == type) {
                listaWizCost.add(aeWizCost);
            }
        }

        return listaWizCost;
    }

    public static List<AEWizCost> getSistema() {
        return getTypeWiz(AETypeWiz.sistema);
    }

    public static List<AEWizCost> getDirectory() {
        return getTypeWiz(AETypeWiz.directory);
    }

    public static List<AEWizCost> getNomeFile() {
        return getTypeWiz(AETypeWiz.nomeFile);
    }

    public static List<AEWizCost> getPath() {
        return getTypeWiz(AETypeWiz.path);
    }

    public static List<AEWizCost> getPackages() {
        return getTypeWiz(AETypeWiz.packages);
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
    public static void printInfoBase(List<AEWizCost> lista, String titolo) {
        System.out.println(VUOTA);
        System.out.println("********************");
        System.out.println(titolo);
        System.out.println("********************");
        for (AEWizCost aeWizCost : lista) {
            System.out.print("AEWizCost." + aeWizCost.name() + ": \"" + aeWizCost.descrizione + "\" " + FlowCost.UGUALE + aeWizCost.value);
            if (aeWizCost.isNewProject() || aeWizCost.isUpdateProject()) {
                System.out.print(FlowCost.FORWARD + "AECopyWiz." + aeWizCost.copyWiz.name());
            }
            System.out.println(VUOTA);
        }
        System.out.println(VUOTA);
    }

    //--metodo statico invocato da Wizard.initView()
    public static void printInfo() {
        printInfoBase(getDirectory(), "Directory di percorso. Valori statici ed immutabili");
        printInfoBase(getSistema(), "Variabili di sistema. Dipende dal programma in uso");
        printInfoBase(getNomeFile(), "Nome e file di percorso. Dipende dal programma in uso");
        printInfoBase(getPath(), "Path di percorso. Dipende dal programma in uso");
        printInfoBase(getPackages(), "Variabili del package. Dipende dal package selezionato");
        //        System.out.println(FlowCost.VUOTA);
        //        System.out.println("********************");
        //        System.out.println("Costanti statiche indipendenti dal progetto che sta girando");
        //        System.out.println("********************");
        //        for (AEWizCost aeWizCost : AEWizCost.values()) {
        //            System.out.print("AEWizCost." + aeWizCost.name() + ": \"" + aeWizCost.descrizione + "\" " + FlowCost.UGUALE + aeWizCost.value);
        //            if (aeWizCost.isNewProject() || aeWizCost.isUpdateProject()) {
        //                System.out.print(FlowCost.FORWARD + "AECopyWiz." + aeWizCost.copyWiz.name());
        //            }
        //            System.out.println(FlowCost.VUOTA);
        //        }
        //        System.out.println(FlowCost.VUOTA);
    }


    //--metodo statico
    public static void printVuote() {
        System.out.println(VUOTA);
        System.out.println("********************");
        System.out.println("Costanti statiche a cui manca ancora il valore");
        System.out.println("********************");
        for (AEWizCost aeWizCost : AEWizCost.getVuote()) {
            System.out.println("AEWizCost." + aeWizCost.name() + ": " + aeWizCost.descrizione);
        }
        System.out.println(VUOTA);
    }

    public AETypeWiz getType() {
        return type;
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
