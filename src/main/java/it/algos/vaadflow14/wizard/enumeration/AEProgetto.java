package it.algos.vaadflow14.wizard.enumeration;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 08-mar-2018
 * Time: 08:23
 */
public enum AEProgetto {

    bio("vaadwiki", "Wiki"),

    wam("vaadwam", "Wam"),
    ;

    private String nameProject;

    private String nameShort;


    AEProgetto(String nameProject, String nameShort) {
        this.setNameProject(nameProject);
        this.setNameShort(nameShort);
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


    public String getNameProject() {
        return nameProject;
    }


    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }


    public String getNameShort() {
        return nameShort;
    }


    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

}// end of enumeration class
