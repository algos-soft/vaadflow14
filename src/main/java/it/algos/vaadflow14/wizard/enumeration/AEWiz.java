package it.algos.vaadflow14.wizard.enumeration;


import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 18-apr-2020
 * Time: 06:27
 */
public enum AEWiz {
    flagSecurity(true, "Utilizza Spring Security", true, false, false, false, false),
    flagDocumentation(true, "Directory documentazione (VaadFlow)", true, true, false, false, true),
    flagLinks(true, "Directory links a web (VaadFlow)", true, true, false, false, true),
    flagSnippets(true, "Directory snippets di aiuto (VaadFlow)", true, true, false, false, true),
    flagFlow(true, "Copia la directory VaadFlow", true, true, false, false, false),
    flagProject(true, "Crea la directory del nuovo progetto", true, false, false, false, false),
    flagResources(true, "Directory resources - ATTENZIONE", true, true, false, false, false),
    flagProperty(true, "File application.properties (VaadFlow)", true, true, false, false, true),
    flagRead(true, "File READ con note di testo", true, true, false, false, true),
    flagGit(true, "File GIT di esclusione (sources)", true, true, false, false, true),
    flagPom(true, "File Maven di POM.xml - ATTENZIONE", true, true, false, false, false),
    flagFile(true, "Sovrascrive il singolo FILE", false, false, false, false, false),
    flagDirectory(true, "Sovrascrive la DIRECTORY", false, false, false, false, false),
    flagBanner(true, "File banner di SpringBoot (sources)", true, true, false, false, true),
    pathUserDir(false, "Directory recuperata dal System dove gira il programma in uso", true, true, false, false, false, VUOTA),
    pathVaadFlow(false, "Directory che contiene il programma VaadFlow", true, true, false, false, false, VUOTA),
    pathIdeaProjects(false, "Directory che contiene i nuovi programmi appena creati da Idea", true, true, false, false, false, VUOTA),
    //    pathVaadFlowSources(false, "Directory dei sorgenti testuali di VaadFlow da elaborare", true, true, false, false, false, VUOTA),
    nameTargetProject(false, "Nome breve new/update project", true, true, false, false, false, VUOTA),
    pathTargetProjet(false, "Path new/update project", true, true, false, false, false, VUOTA),
    ;


    private boolean checkBox;

    private String labelBox;

    private String descrizione;

    private boolean newProject;

    private boolean updateProject;

    private boolean newPackage;

    private boolean updatePackage;

    private boolean defaultAcceso = false;

    private boolean acceso;

    private String defaultValue = VUOTA;

    private String value;


    /**
     * Costruttore per i flag con valore booleano <br>
     */
    AEWiz(boolean checkBox, String unTesto, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, boolean defaultAcceso) {
        this(checkBox, unTesto, newProject, updateProject, newPackage, updatePackage, defaultAcceso, VUOTA);
    }


    /**
     * Costruttore per i path con valore stringa <br>
     */
    AEWiz(boolean checkBox, String unTesto, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, String defaultValue) {
        this(checkBox, unTesto, newProject, updateProject, newPackage, updatePackage, false, defaultValue);
    }


    /**
     * Costruttore completo <br>
     */
    AEWiz(boolean checkBox, String unTesto, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, boolean defaultAcceso, String defaultValue) {
        this.checkBox = checkBox;
        if (checkBox) {
            this.labelBox = unTesto;
        } else {
            this.descrizione = unTesto;
        }// end of if/else cycle
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.newPackage = newPackage;
        this.updatePackage = updatePackage;

        if (checkBox) {
            this.defaultAcceso = defaultAcceso;
            this.acceso = defaultAcceso;
        } else {
            this.defaultValue = defaultValue;
            this.value = defaultValue;
        }
    }


    public static void reset() {
        for (AEWiz aeWiz : AEWiz.values()) {
            aeWiz.setAcceso(aeWiz.defaultAcceso);
            aeWiz.setValue(aeWiz.defaultValue);
        }
    }


    public boolean isCheckBox() {
        return checkBox;
    }


    public String getLabelBox() {
        return labelBox;
    }


    public String getDescrizione() {
        if (checkBox) {
            if (descrizione != null && descrizione.length() > 0) {
                return descrizione;
            } else {
                return labelBox;
            }
        } else {
            return descrizione;
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


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


}
