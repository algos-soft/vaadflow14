package it.algos.vaadflow14.wizard.enumeration;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 08-mar-2018
 * Time: 08:23
 * <p>
 * Enumeration dei progetti gestiti da Wizard <br>
 * Di default stanno nel path -> /Users/gac/Documents/IdeaProjects/operativi/ <br>
 * Altrimenti usare la property pathCompleto <br>
 */
public enum AEProgetto {

    bio("vaadwiki", "Wiki", "vaadwiki", VUOTA),
    wam("vaadwam", "Wam", "vaadwam", VUOTA),
    beta("beta", "beta", "beta", "/Users/gac/Documents/IdeaProjects/tutorial/beta/"),
    untitled("untitled", "Untitled", "untitled", "/Users/gac/Documents/IdeaProjects/untitled/"),
    moneglia("moneglia", "Moneglia", "", VUOTA),
    gestione("gestione", "Gestione", "", VUOTA),

    ;

    private String directory;

    private String projectNameUpper;

    private String projectNameModuloLower;

    //--path completo se diverso da /Users/gac/Documents/IdeaProjects/operativi/...
    private String pathCompleto;


    AEProgetto(String directory, String projectNameUpper, String projectNameModuloLower, String pathCompleto) {
        this.directory = directory;
        this.projectNameUpper = projectNameUpper;
        this.projectNameModuloLower = projectNameModuloLower;
        this.pathCompleto = pathCompleto;
    }

    public static List<AEProgetto> get() {
        List<AEProgetto> lista = new ArrayList<>();

        for (AEProgetto project : AEProgetto.values()) {
            lista.add(project);
        }

        return lista;
    }

    public static List<String> getNames() {
        List<String> nomi = new ArrayList<>();

        for (AEProgetto progetto : AEProgetto.values()) {
            nomi.add(progetto.directory);
        }

        return nomi;
    }


    public static AEProgetto getProgetto(String nameProject) {
        for (AEProgetto progetto : AEProgetto.values()) {
            if (progetto.getDirectory().equals(nameProject)) {
                return progetto;
            }
        }

        return null;
    }


    /**
     * Visualizzazione di controllo <br>
     */
    public static void printInfo() {
        if (FLAG_DEBUG_WIZ) {
            System.out.println("********************");
            System.out.println("Progetti della enumeration AEProgetto");
            System.out.println("********************");
            for (AEProgetto progetto : AEProgetto.values()) {
                System.out.println("AEProgetto." + progetto.name() + " -> " + progetto.getDirectory() + " - " + progetto.getProjectNameUpper());
            }
            System.out.println("");
        }
    }


    public String getDirectory() {
        return directory;
    }


    public String getProjectNameUpper() {
        return projectNameUpper;
    }


    public String getProjectNameModuloLower() {
        return projectNameModuloLower;
    }


    public String getPathCompleto() {
        return pathCompleto;
    }

}// end of enumeration class
