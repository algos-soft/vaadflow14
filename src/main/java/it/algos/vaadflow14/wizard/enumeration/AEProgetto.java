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
 */
public enum AEProgetto {

    bio("vaadwiki", "Wiki",VUOTA),

    wam("vaadwam", "Wam",VUOTA),

    beta("beta", "beta","/Users/gac/Documents/IdeaProjects/tutorial/beta/"),

    moneglia("moneglia", "Moneglia",VUOTA),
    gestione("gestione", "Gestione",VUOTA),

    ;

    private String nameProject;

    private String nameUpper;

    //--path completo se diverso da /Users/gac/Documents/IdeaProjects/operativi/...
    private String pathCompleto;


    AEProgetto(String nameProject, String nameUpper, String pathCompleto) {
        this.setNameProject(nameProject);
        this.setNameUpper(nameUpper);
        this.setPathCompleto(pathCompleto);
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
            nomi.add(progetto.nameProject);
        }

        return nomi;
    }


    public static AEProgetto getProgetto(String nameProject) {
        for (AEProgetto progetto : AEProgetto.values()) {
            if (progetto.getNameProject().equals(nameProject)) {
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
                System.out.println("AEProgetto." + progetto.name() + " -> " + progetto.getNameProject() + " - " + progetto.getNameUpper());
            }
            System.out.println("");
        }
    }


    public String getNameProject() {
        return nameProject;
    }


    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }


    public String getNameUpper() {
        return nameUpper;
    }


    public void setNameUpper(String nameUpper) {
        this.nameUpper = nameUpper;
    }


    public String getPathCompleto() {
        return pathCompleto;
    }


    public void setPathCompleto(String pathCompleto) {
        this.pathCompleto = pathCompleto;
    }

}// end of enumeration class
