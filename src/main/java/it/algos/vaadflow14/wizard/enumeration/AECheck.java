package it.algos.vaadflow14.wizard.enumeration;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.wizard.scripts.WizCost.FLAG_DEBUG_WIZ;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 04-nov-2020
 * Time: 18:56
 */
public enum AECheck {
    security("Utilizza Spring Security", true, false, false, false, false),

    config("Directory di risorse on-line esterne al JAR (VaadFlow)", true, true, false, false, true),

    documentation("Directory documentazione (VaadFlow)", true, true, false, false, true),

    links("Directory links a web (VaadFlow)", true, true, false, false, true),

    snippets("Directory snippets di aiuto (VaadFlow)", true, true, false, false, true),

    flow("Directory framework VaadFlow (Wizard compreso)", true, true, false, false, true),

   project("Directory modulo del nuovo progetto (...)", true, false, false, false, false),

    frontend("Directory frontend (VaadFlow)", true, true, false, false, true),

    resources("Directory resources (VaadFlow)", true, true, false, false, false),

    property("File application.properties (sources)", true, true, false, false, true),

    banner("File banner di SpringBoot (sources)", true, true, false, false, true),

    git("File GIT di esclusione (sources)", true, true, false, false, true),

    pom("File Maven di POM.xml (sources)", true, true, false, false, true),

   read("File READ con note di testo (sources)", true, true, false, false, true),

    file("Sovrascrive il singolo FILE", false, false, false, false, false),

    directory("Sovrascrive la DIRECTORY", false, false, false, false, false),

    entity("Entity base del package", false, false, true, true, true),

    logic("Business logic del package", false, false, true, true, false),

    form("Form specifico del package", false, false, true, true, false),

    list("List specifico del package", false, false, true, true, false),

    service("Service specifico del package", false, false, true, true, false),

    company("Entity subclass di Company", false, false, true, true, false),

    crowIndex("Entity usa rowIndex", false, false, true, true, false),

    sovrascrivePackage("Sovrascrive un package esistente", false, false, true, true, false),

    ;


    private String caption;

    private boolean newProject;

    private boolean updateProject;

    private boolean newPackage;

    private boolean updatePackage;

    private boolean defaultAcceso;

    private boolean acceso;


    /**
     * Costruttore completo <br>
     */
    AECheck(String caption, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, boolean defaultAcceso) {
        this.caption = caption;
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.newPackage = newPackage;
        this.updatePackage = updatePackage;

        this.defaultAcceso = defaultAcceso;
        this.acceso = defaultAcceso;
    }


    /**
     * Ripristina il valore di default <br>
     */
    public static void reset() {
        for (AECheck aeCheck : AECheck.values()) {
            aeCheck.setAcceso(aeCheck.defaultAcceso);
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
            if ( aeWiz.isNewPackage()) {
                listaChecks.add(aeWiz);
            }
        }

        return listaChecks;
    }

    public static List<AECheck> getUpdatePackage() {
        List<AECheck> listaChecks = new ArrayList<>();

        for (AECheck aeWiz : AECheck.values()) {
            if ( aeWiz.isUpdatePackage()) {
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
                System.out.println("AECheck." + flag.name() + " \"" + flag.caption + "\" = " + flag.isAbilitato());
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


    public boolean isAbilitato() {
        return acceso;
    }


    public boolean isAcceso() {
        return acceso;
    }


    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }


    public String getCaption() {
        return caption;
    }


    public void setCaption(String caption) {
        this.caption = caption;
    }
}

