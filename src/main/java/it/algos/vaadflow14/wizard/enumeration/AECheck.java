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
    security("Utilizza Spring Security", true, false, false, false, VUOTA, VUOTA, false),

    config("Directory CONFIG di risorse on-line esterne al JAR (VaadFlow)", true, true, false, false, VUOTA, VUOTA, false),

    documentation("Directory DOC di documentazione (VaadFlow)", true, true, false, false, VUOTA, VUOTA, false),

    frontend("Directory FRONTEND del Client (VaadFlow)", true, true, false, false, VUOTA, VUOTA, false),

    links("Directory LINKS a siti web utili (VaadFlow)", true, true, false, false, VUOTA, VUOTA, false),

    snippets("Directory SNIPPETS di codice suggerito (VaadFlow)", true, true, false, false, VUOTA, VUOTA, false),

    flow("Directory BASE di VaadFlow (Wizard compreso)", true, true, false, false, VUOTA, VUOTA, false),

    projectNew("Directory modulo del nuovo progetto (...)", true, false, false, false, VUOTA, VUOTA, false),

    projectUpdate("Directory modulo del progetto selezionato (...)", false, true, false, false, VUOTA, VUOTA, false),

    resources("Directory RESOURCES (VaadFlow)", true, true, false, false, VUOTA, VUOTA, false),

    property("File application.PROPERTIES (sources)", true, true, false, false, VUOTA, VUOTA, false),

    banner("File BANNER di SpringBoot (sources)", true, true, false, false, VUOTA, VUOTA, false),

    git("File GIT di esclusione (sources)", true, true, false, false, VUOTA, VUOTA, false),

    pom("File POM.xml di Maven (sources)", true, true, false, false, VUOTA, VUOTA, false),

    read("File README con note di testo (sources)", true, true, false, false, VUOTA, VUOTA, false),

    all("Abilita/disabilita tutti i checkBox", true, true, false, false, VUOTA, VUOTA, false),

    file("Sovrascrive il singolo FILE", false, false, false, false, VUOTA, VUOTA, false),

    directory("Sovrascrive la DIRECTORY", false, false, false, false, VUOTA, VUOTA, false),

    entity("Entity base del package", false, false, true, true, VUOTA, VUOTA, true),

    logic("Business logic del package", false, false, true, true, VUOTA, VUOTA, false),

    form("Form specifico del package", false, false, true, true, VUOTA, VUOTA, false),

    list("List specifico del package", false, false, true, true, VUOTA, VUOTA, false),

    service("Service specifico del package", false, false, true, true, VUOTA, VUOTA, false),

    company("Entity subclass di Company", false, false, true, true, VUOTA, VUOTA, false),

    rowIndex("Entity usa rowIndex", false, false, true, true, VUOTA, VUOTA, false),

    ordine("Entity usa property Ordine", false, false, true, true, "ordine", "PropertyOrdine", true),

    code("Entity usa property Code", false, false, true, true, "code", "PropertyCode", true),

    descrizione("Entity usa property Descrizione", false, false, true, true, "descrizione", "PropertyDescrizione", true),

    docFile("Update doc of all packages", false, false, false, true, VUOTA, VUOTA, false),

    sovrascrivePackage("Sovrascrive un package esistente", false, false, false, false, VUOTA, VUOTA, false),

    ;


    private String caption;

    private boolean newProject;

    private boolean updateProject;

    private boolean newPackage;

    private boolean updatePackage;

    private String field;

    private String sourcesTag;

    private boolean acceso;

    private boolean accesoInizialmente;


    /**
     * Costruttore completo <br>
     */
    AECheck(String caption, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, String field, String sourcesTag, boolean accesoInizialmente) {
        this.caption = caption;
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.newPackage = newPackage;
        this.updatePackage = updatePackage;
        this.field = field;
        this.sourcesTag = sourcesTag;
        this.accesoInizialmente = accesoInizialmente;
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

    public boolean isAccesoInizialmente() {
        return accesoInizialmente;
    }
}

