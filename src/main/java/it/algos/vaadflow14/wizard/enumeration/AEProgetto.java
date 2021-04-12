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

    bio("Wiki", "vaadwiki", VUOTA),
    wam("Wam", "vaadwam", VUOTA),
    beta("Beta", "beta", "/Users/gac/Documents/IdeaProjects/tutorial/beta/"),
    untitled("Untitled", "untitled", "/Users/gac/Documents/IdeaProjects/untitled/"),
    moneglia("Moneglia", "moneglia", VUOTA),
    gestione("Gestione", "gestione", VUOTA),

    ;


    private String projectNameUpper;

    private String directoryAndProjectModuloLower;

    //--path completo se diverso da /Users/gac/Documents/IdeaProjects/operativi/...
    private String pathCompleto;


    AEProgetto(String projectNameUpper, String directoryAndProjectModuloLower, String pathCompleto) {
        this.projectNameUpper = projectNameUpper;
        this.directoryAndProjectModuloLower = directoryAndProjectModuloLower;
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
            nomi.add(progetto.getProjectNameUpper());
        }

        return nomi;
    }


    public static AEProgetto getProgetto(String nameProject) {
        for (AEProgetto progetto : AEProgetto.values()) {
            if (progetto.getProjectNameUpper().equalsIgnoreCase(nameProject)) {
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
                System.out.println("AEProgetto." + progetto.name() + " -> " + progetto.getProjectNameUpper()
                        + " - " + progetto.getDirectoryAndProjectModuloLower());
            }
            System.out.println("");
        }
    }

    public String getProjectNameUpper() {
        return projectNameUpper;
    }

    public String getDirectoryAndProjectModuloLower() {
        return directoryAndProjectModuloLower;
    }

    public String getPathCompleto() {
        return pathCompleto;
    }
}// end of enumeration class
