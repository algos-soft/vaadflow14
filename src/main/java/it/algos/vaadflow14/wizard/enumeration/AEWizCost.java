package it.algos.vaadflow14.wizard.enumeration;

import static it.algos.vaadflow14.backend.application.FlowCost.SLASH;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;

/**
 * Project vaadwiki14
 * Created by Algos
 * User: gac
 * Date: lun, 18-gen-2021
 * Time: 07:09
 * <p>
 * Enumeration di costanti statiche praticamente fisse <br>
 * Possono essere (eventualmente) modificate se modifico la struttura delle directory che uso <br>
 * <p>
 * Enumeration di property variabili in funzione del target di project o di package <br>
 * <p>
 * Gestite come Enumeration per tenerle sotto controllo, spazzolarle e stamparle tutte insieme <br>
 */
public enum AEWizCost {

    /**
     * Root iniziale. Hardcoded su di un singolo computer. <br>
     * Valore standard che verrà controllato in funzione di AEDIR.pathCurrent effettivo <br>
     * Potrebbe essere diverso <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathRoot(AETypeWiz.nonModificabile, AETypeFile.path, "Root iniziale del computer utilizzato", "/Users/gac/Documents/"),

    /**
     * Cartella base dei progetti. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirProjects(AETypeWiz.nonModificabile, AETypeFile.dir, "Cartella di partenza dei projects Idea", "IdeaProjects/"),

    /**
     * Percorso base dei progetti. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathProjectsDirStandard(AETypeWiz.nonModificabile, AETypeFile.path, "Path base dei projects Idea", pathRoot.value + dirProjects.value),

    /**
     * Cartella dei progetti operativi. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirOperativi(AETypeWiz.nonModificabile, AETypeFile.dir, "Cartella dei projects operativi", "operativi/"),

    /**
     * Percorso dei progetti operativi. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathOperativiDirStandard(AETypeWiz.nonModificabile, AETypeFile.path, "Path dei projects operativi", pathProjectsDirStandard.value + dirOperativi.value),

    /**
     * Nome del progetto base vaadflow14. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameVaadFlow14(AETypeWiz.nonModificabile, AETypeFile.nome, "Nome del progetto base vaadflow14", "Vaadflow14"),

    /**
     * Cartella del progetto base vaadflow14. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirVaadFlow14(AETypeWiz.nonModificabile, AETypeFile.dir, "Cartella del progetto base vaadflow14", nameVaadFlow14.value.toLowerCase() + SLASH),

    /**
     * Percorso del progetto base vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Root(AETypeWiz.nonModificabile, AETypeFile.path, "Path root del progetto base vaadflow14", pathOperativiDirStandard.value + dirVaadFlow14.value),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirMain(AETypeWiz.nonModificabile, AETypeFile.dir, "Directory files main", "src/main/"),

    /**
     * Cartella a livello di modulo. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirModulo(AETypeWiz.nonModificabile, AETypeFile.dir, "Directory files modulo", dirMain.value + "java/it/algos/"),

    /**
     * Cartella a livello di resources. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirResources(AETypeWiz.nonModificabile, AETypeFile.dir, "Directory resources", dirMain.value + "resources/"),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootConfig(AETypeWiz.nonModificabile, AETypeFile.dir, String.format("Directory root CONFIG di risorse on-line esterne al JAR (da %s)", nameVaadFlow14.value), "config/", AECopyWiz.dirAddingOnly, "", ""),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootDoc(AETypeWiz.nonModificabile, AETypeFile.dir, String.format("Directory root DOC di documentazione (da %s)", nameVaadFlow14.value), "doc/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di root. <br>
     * contiene images/ (di solito)
     * contiene src/ (di solito)
     * contiene styles/ (sempre)
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootFrontend(AETypeWiz.nonModificabile, AETypeFile.dir, String.format("Directory root FRONTEND per il Client (da %s)", nameVaadFlow14.value), "frontend/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootLinks(AETypeWiz.nonModificabile, AETypeFile.dir, String.format("Directory root LINKS a siti web utili (da %s)", nameVaadFlow14.value), "links/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di root. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootSnippets(AETypeWiz.nonModificabile, AETypeFile.dir, String.format("Directory root SNIPPETS di codice suggerito (da %s)", nameVaadFlow14.value), "snippets/", AECopyWiz.dirAddingOnly),

    /**
     * Cartella a livello di modulo. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirModuloVaadFlow14(AETypeWiz.nonModificabile, AETypeFile.dir, String.format("Directory modulo BASE %s (da %s, Wizard compreso)", nameVaadFlow14.value, nameVaadFlow14.value), dirModulo.value + dirVaadFlow14.value, AECopyWiz.dirDeletingAll, VUOTA, VUOTA, true, true, true, true),

    /**
     * Percorso del modulo base vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Modulo(AETypeWiz.nonModificabile, AETypeFile.path, "Path del modulo base vaadflow14", pathVaadFlow14Root.value + dirModuloVaadFlow14.value),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootWizard(AETypeWiz.nonModificabile, AETypeFile.dir, "Directory wizard", dirModuloVaadFlow14.value + "wizard/"),

    /**
     * Percorso della directory wizard sources di vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14Wizard(AETypeWiz.nonModificabile, AETypeFile.path, "Directory vaadflow14.wizard", pathVaadFlow14Root.value + dirRootWizard.value),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootWizardEnumeration(AETypeWiz.nonModificabile, AETypeFile.dir, "Directory wizard.enumeration", dirRootWizard.value + "enumeration/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirRootWizardScripts(AETypeWiz.nonModificabile, AETypeFile.dir, "Directory wizard.scripts", dirRootWizard.value + "scripts/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirVaadFlow14WizardSources(AETypeWiz.nonModificabile, AETypeFile.dir, "Directory sources", dirRootWizard.value + "sources/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirWizardSources(AETypeWiz.nonModificabile, AETypeFile.dir, "Directory wizard/sources", "wizard/sources/"),

    /**
     * Percorso della directory wizard sources di vaadflow14. Nei Documents di Gac <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathVaadFlow14WizSources(AETypeWiz.nonModificabile, AETypeFile.path, "Directory vaadflow14.wizard.sources", pathVaadFlow14Root.value + dirVaadFlow14WizardSources.value),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirBoot(AETypeWiz.nonModificabile, AETypeFile.dir, "Nome della directory boot", "backend/boot/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirPackages(AETypeWiz.nonModificabile, AETypeFile.dir, "Nome della directory packages del modulo target", "backend/packages/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirBackend(AETypeWiz.nonModificabile, AETypeFile.dir, "Nome della directory backend del modulo target", "backend/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirApplication(AETypeWiz.nonModificabile, AETypeFile.path, "Nome della directory application del modulo target", "backend/application/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirData(AETypeWiz.nonModificabile, AETypeFile.dir, "Nome della directory data del modulo target", "backend/data/"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirUI(AETypeWiz.nonModificabile, AETypeFile.dir, "Nome della directory UI del modulo target", "ui/"),

    /**
     * Nome della directory sources. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    dirSources(AETypeWiz.nonModificabile, AETypeFile.dir, "Nome della directory sources", "../vaadflow14/wizard/sources"),

    /**
     * Nome del file wizard. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileWizard(AETypeWiz.nonModificabile, AETypeFile.file, "Nome del file wizard", "Wizard.java"),

    /**
     * Cartella. <br>
     * Tutte le enums il cui nome inizia con 'dir', finiscono con uno SLASH <br>
     */
    dirMetaInf(AETypeWiz.nonModificabile, AETypeFile.dir, String.format("Directory META-INF (da %s)", nameVaadFlow14.value), dirResources.value + "META-INF", AECopyWiz.dirAddingOnly, "", "resources"),

    /**
     * File. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    sourceProperties(AETypeWiz.nonModificabile, AETypeFile.source, String.format("File application.properties (da %s)", dirSources.value), dirResources.value + "application.properties", AECopyWiz.sourceCheckFlagSeEsiste, "properties", dirMain.value),

    /**
     * File. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    sourceBanner(AETypeWiz.nonModificabile, AETypeFile.source, String.format("File banner (da %s)", dirSources.value), dirResources.value + "banner.txt", AECopyWiz.sourceSoloSeNonEsiste, "banner.txt"),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    sourceRootGit(AETypeWiz.nonModificabile, AETypeFile.source, String.format("File root GIT di esclusione (da %s)", dirSources.value), ".gitignore", AECopyWiz.sourceSovrascriveSempreAncheSeEsiste),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    sourceRootPOM(AETypeWiz.nonModificabile, AETypeFile.source, String.format("File root POM.xml di Maven (da %s)", dirSources.value), "pom.xml", AECopyWiz.sourceCheckFlagSeEsiste, "pom"),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    sourceRootREAD(AETypeWiz.nonModificabile, AETypeFile.source, String.format("File root README con note di testo (da %s)", dirSources.value), "README.txt", AECopyWiz.sourceCheckFlagSeEsiste),

    /**
     * File a livello di root. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    fileRootTEST(AETypeWiz.nonModificabile, AETypeFile.file, String.format("File root/test ATEST (da %s)", nameVaadFlow14.value), "src/test/java/it/algos/test/ATest.java", AECopyWiz.fileCheckFlagSeEsiste),

    /////////////////

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathCurrent(AETypeWiz.regolatoSistema, AETypeFile.path, "Directory dove gira il programma in uso. Recuperata dal System", VALORE_MANCANTE),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameUser(AETypeWiz.regolatoSistema, AETypeFile.nome, "Programmatore. Ricavato dal path della directory corrente.", VALORE_MANCANTE),

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameProjectCurrentUpper(AETypeWiz.regolatoSistema
            , AETypeFile.nome, "Nome maiuscolo del programma in uso. Ricavato dal path della directory corrente", VALORE_MANCANTE) {
    },

    /**
     * Regolata inizialmente dal system, indipendentemente dall'apertura di un dialogo. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameProjectCurrentLower(AETypeWiz.regolatoSistemaAutomatico
            , AETypeFile.nome, "Nome minuscolo del programma in uso. Ricavato dal path della directory corrente", VALORE_MANCANTE) {
        @Override
        public void setValue() {
            if (nameProjectCurrentUpper.valida) {
                this.value = nameProjectCurrentUpper.getValue().toLowerCase();
                this.setValida(true);
            }
        }
    },

    /////////////////

    /**
     * Nome del progetto target. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetProjectUpper(AETypeWiz.necessarioEntrambi, AETypeFile.nome, "Nome maiuscolo del progetto target", VALORE_MANCANTE),

    /**
     * Nome minuscolo del progetto target. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetProjectLower(AETypeWiz.necessarioEntrambiAutomatico, AETypeFile.nome, "Nome minuscolo del progetto target", VALORE_MANCANTE) {
        @Override
        public void setValue() {
            if (nameTargetProjectUpper.valida) {
                this.value = nameTargetProjectUpper.getValue().toLowerCase();
                this.setValida(true);
            }
        }
    },

    /**
     * Root del progetto target. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectRoot(AETypeWiz.necessarioEntrambiAutomatico, AETypeFile.path, "Path root del progetto target", VALORE_MANCANTE) {
        public void setValue() {
        }
    },

    /**
     * Modulo del progetto target. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectModulo(AETypeWiz.necessarioEntrambiAutomatico, AETypeFile.path, "Directory MODULO del progetto", VALORE_MANCANTE, AECopyWiz.dirAddingOnly) {
        public void setValue() {
        }
    },

    /**
     * Percorso della directory boot. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectBoot(AETypeWiz.necessarioProgettoAutomatico, AETypeFile.path, "Directory target boot", pathTargetProjectModulo.value + dirBoot.value) {
        public void setValue() {
        }
    },

    /**
     * Percorso della directory packages. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectPackages(AETypeWiz.necessarioEntrambiAutomatico, AETypeFile.path, "Directory target packages", pathTargetProjectModulo.value + dirPackages.value) {
        public void setValue() {
        }
    },

    /**
     * Percorso della directory target sources. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetProjectSources(AETypeWiz.necessarioProgettoAutomatico, AETypeFile.path, "Directory target sources (da cancellare)", pathTargetProjectModulo.value + "wizard/sources/") {
        public void setValue() {
        }
    },

    /**
     * Nome del package da creare. Eventualmente con sub-directory <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetPackagePunto(AETypeWiz.necessarioPackage, AETypeFile.nome, "Nome del package da creare/modificare usando punto", VALORE_MANCANTE),

    /**
     * Nome del package da creare. Eventualmente con sub-directory <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetPackageSlash(AETypeWiz.necessarioPackageAutomatico, AETypeFile.nome, "Nome del package da creare/modificare usando slash", VALORE_MANCANTE) {
        public void setValue() {
        }
    },

    /**
     * Nome del package da creare. <br>
     * Tutte le enums il cui nome NON inizia con 'path' sono nomi o files o sub-directory, non path completi <br>
     */
    nameTargetFileUpper(AETypeWiz.necessarioPackageAutomatico, AETypeFile.nome, "Nome del file da creare/modificare con iniziale maiuscola", VALORE_MANCANTE) {
        public void setValue() {
        }
    },

    /**
     * Percorso del package. <br>
     * Tutte le enums il cui nome inizia con 'path', iniziano e finiscono con uno SLASH <br>
     */
    pathTargetPackageSlash(AETypeWiz.necessarioPackageAutomatico, AETypeFile.path, "Directory del package da creare/modificare", VALORE_MANCANTE) {
        public void setValue() {
        }
    },

    ;


    protected String value;

    private AETypeWiz typeWiz;

    private AETypeFile typeFile;

    private String descrizione;

    private String sourcesName;

    private String pathBreve;

    private boolean newProject;

    private boolean updateProject;

    private boolean accesoInizialmenteNew;

    private boolean accesoInizialmenteUpdate;

    private boolean acceso;

    private AECopyWiz copyWiz;

    private boolean valida;


    /**
     * Costruttore parziale <br>
     */
    AEWizCost(AETypeWiz typeWiz, AETypeFile typeFile, String descrizione, String value) {
        this(typeWiz, typeFile, descrizione, value, (AECopyWiz) null, VUOTA, VUOTA, false, false, false, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(AETypeWiz typeWiz, AETypeFile typeFile, String descrizione, String value, boolean newProject, boolean updateProject, boolean accesoInizialmenteNew, boolean accesoInizialmenteUpdate) {
        this(typeWiz, typeFile, descrizione, value, (AECopyWiz) null, VUOTA, VUOTA, newProject, updateProject, accesoInizialmenteNew, accesoInizialmenteUpdate);
    }


    /**
     * Costruttore parziale <br>
     */
    AEWizCost(AETypeWiz typeWiz, AETypeFile typeFile, String descrizione, String value, AECopyWiz copyWiz) {
        this(typeWiz, typeFile, descrizione, value, copyWiz, value, VUOTA, true, true, true, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(AETypeWiz typeWiz, AETypeFile typeFile, String descrizione, String value, AECopyWiz copyWiz, boolean newProject, boolean updateProject, boolean accesoInizialmenteNew, boolean accesoInizialmenteUpdate) {
        this(typeWiz, typeFile, descrizione, value, copyWiz, VUOTA, VUOTA, newProject, updateProject, accesoInizialmenteNew, accesoInizialmenteUpdate);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(AETypeWiz typeWiz, AETypeFile typeFile, String descrizione, String value, AECopyWiz copyWiz, String sourcesNameSeDiversoDaValue) {
        this(typeWiz, typeFile, descrizione, value, copyWiz, sourcesNameSeDiversoDaValue, VUOTA, true, true, true, false);
    }

    /**
     * Costruttore parziale <br>
     */
    AEWizCost(AETypeWiz typeWiz, AETypeFile typeFile, String descrizione, String value, AECopyWiz copyWiz, String sourcesNameSeDiversoDaValue, String pathBreve) {
        this(typeWiz, typeFile, descrizione, value, copyWiz, sourcesNameSeDiversoDaValue, pathBreve, true, true, true, false);
    }


    /**
     * Costruttore completo <br>
     */
    AEWizCost(AETypeWiz typeWiz, AETypeFile typeFile, String descrizione, String value, AECopyWiz copyWiz, String sourcesNameSeDiversoDaValue, String pathBreve, boolean newProject, boolean updateProject, boolean accesoInizialmenteNew, boolean accesoInizialmenteUpdate) {
        this.typeWiz = typeWiz;
        this.typeFile = typeFile;
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
        this.valida = !value.startsWith(VALORE_MANCANTE);
    }

    public static void fixValue() {
        for (AEWizCost aeWizCost : AEWizCost.values()) {
            aeWizCost.setValue();
        }
    }

    public void setValue() {
    }

    public AETypeWiz getTypeWiz() {
        return typeWiz;
    }

    public AETypeFile getTypeFile() {
        return typeFile;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        this.valida = true;
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

    public boolean isValida() {
        return valida;
    }

    public void setValida(boolean valida) {
        this.valida = valida;
    }
}
