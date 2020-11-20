package it.algos.vaadflow14.wizard.enumeration;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.FLAG_DEBUG_WIZ;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 04-nov-2020
 * Time: 18:56
 */
public enum AECheck {
    security("Utilizza Spring Security", true, false, false, false, VUOTA, VUOTA),

    config("Directory CONFIG di risorse on-line esterne al JAR (VaadFlow)", true, true, false, false, VUOTA, VUOTA),

    documentation("Directory DOC di documentazione (VaadFlow)", true, true, false, false, VUOTA, VUOTA),

    frontend("Directory FRONTEND del Client (VaadFlow)", true, true, false, false, VUOTA, VUOTA),

    links("Directory LINKS a siti web utili (VaadFlow)", true, true, false, false, VUOTA, VUOTA),

    snippets("Directory SNIPPETS di codice suggerito (VaadFlow)", true, true, false, false, VUOTA, VUOTA),

    flow("Directory BASE di VaadFlow (Wizard compreso)", true, true, false, false, VUOTA, VUOTA),

    projectNew("Directory modulo del nuovo progetto (...)", true, false, false, false, VUOTA, VUOTA),

    projectUpdate("Directory modulo del progetto selezionato (...)", false, true, false, false, VUOTA, VUOTA),

    resources("Directory RESOURCES (VaadFlow)", true, true, false, false, VUOTA, VUOTA),

    property("File application.PROPERTIES (sources)", true, true, false, false, VUOTA, VUOTA),

    banner("File BANNER di SpringBoot (sources)", true, true, false, false, VUOTA, VUOTA),

    git("File GIT di esclusione (sources)", true, true, false, false, VUOTA, VUOTA),

    pom("File POM.xml di Maven (sources)", true, true, false, false, VUOTA, VUOTA),

    read("File README con note di testo (sources)", true, true, false, false, VUOTA, VUOTA),

    file("Sovrascrive il singolo FILE", false, false, false, false, VUOTA, VUOTA),

    directory("Sovrascrive la DIRECTORY", false, false, false, false, VUOTA, VUOTA),

    entity("Entity base del package", false, false, true, true, VUOTA, VUOTA),

    logic("Business logic del package", false, false, true, true, VUOTA, VUOTA),

    form("Form specifico del package", false, false, true, true, VUOTA, VUOTA),

    list("List specifico del package", false, false, true, true, VUOTA, VUOTA),

    service("Service specifico del package", false, false, true, true, VUOTA, VUOTA),

    company("Entity subclass di Company", false, false, true, true, VUOTA, VUOTA),

    crowIndex("Entity usa rowIndex", false, false, true, true, VUOTA, VUOTA),

    ordine("Entity usa property Ordine", false, false, true, true, "ordine", "PropertyOrdine"),

    code("Entity usa property Code", false, false, true, true, "code", "PropertyCode"),

    descrizione("Entity usa property Descrizione", false, false, true, true, "descrizione", "PropertyDescrizione"),

    sovrascrivePackage("Sovrascrive un package esistente", false, false, true, true, VUOTA, VUOTA),

    ;


    private String caption;

    private boolean newProject;

    private boolean updateProject;

    private boolean newPackage;

    private boolean updatePackage;

    private String field;

    private String sourcesTag;

    private boolean acceso;


    /**
     * Costruttore completo <br>
     */
    AECheck(String caption, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, String field, String sourcesTag) {
        this.caption = caption;
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.newPackage = newPackage;
        this.updatePackage = updatePackage;
        this.field = field;
        this.sourcesTag = sourcesTag;
    }


    /**
     * Ripristina il valore di default <br>
     */
    public static void reset() {
        for (AECheck aeCheck : AECheck.values()) {
            aeCheck.setAcceso(false);
        }
    }


    public static List<AECheck> getNewProject() {
        List<AECheck> listaChecks = new ArrayList<>();

        for (AECheck aeCheck : AECheck.values()) {
            if (aeCheck.isNewProject()) {
                listaChecks.add(aeCheck);
            }
        }

        return listaChecks;
    }


    public static List<AECheck> getUpdateProject() {
        List<AECheck> listaChecks = new ArrayList<>();

        for (AECheck aeCheck : AECheck.values()) {
            if (aeCheck.isUpdateProject()) {
                listaChecks.add(aeCheck);
            }
        }

        return listaChecks;
    }


    public static List<AECheck> getNewPackage() {
        List<AECheck> listaChecks = new ArrayList<>();

        for (AECheck aeWiz : AECheck.values()) {
            if (aeWiz.isNewPackage()) {
                listaChecks.add(aeWiz);
            }
        }

        return listaChecks;
    }


    public static List<AECheck> getUpdatePackage() {
        List<AECheck> listaChecks = new ArrayList<>();

        for (AECheck aeWiz : AECheck.values()) {
            if (aeWiz.isUpdatePackage()) {
                listaChecks.add(aeWiz);
            }
        }

        return listaChecks;
    }


    /**
     * Visualizzazione di controllo <br>
     */
    public static void printInfo(String posizione) {
        if (FLAG_DEBUG_WIZ) {
            System.out.println("********************");
            System.out.println("AECheck  - " + posizione);
            System.out.println("********************");
            for (AECheck flag : AECheck.values()) {
                System.out.println("AECheck." + flag.name() + " \"" + flag.caption + "\" = " + flag.is());
            }
            System.out.println("");
        }
    }


    public boolean isNewProject() {
        return newProject;
    }


    public boolean isUpdateProject() {
        return updateProject;
    }


    public boolean isNewPackage() {
        return newPackage;
    }


    public boolean isUpdatePackage() {
        return updatePackage;
    }


    public boolean is() {
        return acceso;
    }


    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }


    public String getCaption() {
        return caption;
    }


    public String getField() {
        return field;
    }


    public String getSourcesTag() {
        return sourcesTag;
    }
}

