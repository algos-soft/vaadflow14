package it.algos.vaadflow14.wizard.enumeration;

import it.algos.vaadflow14.backend.service.AFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.wizard.scripts.WizCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 04-nov-2020
 * Time: 19:40
 */
public enum AEDir {

    //--regolata inizialmente dal system, indipendentemente dall' apertura dei dialoghi
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathCurrent(true, false, "Directory dove gira il programma in uso. Recuperata dal System") {
        @Override
        public void elabora(String pathCurrent) {
            this.setValue(pathCurrent);
        }
    },// end of single enumeration

    //--elaborata inizialmente partendo dal pathCurrent
    //--path per arrivare alla directory IdeaProjects
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathRoot(true, false, "Directory base in cui si trova la cartella IdeaProjects") {
        @Override
        public void elabora(String pathCurrent) {
            String tagDirectory = DIR_PROJECTS;
            String path = file.findPathDirectory(pathCurrent, tagDirectory);
            this.setValue(path);
        }
    },// end of single enumeration

    //--elaborata inizialmente partendo dal pathRoot
    //--aggiunge la directory IdeaProjects a pathRoot
    //--directory che contiene i nuovi programmi appena creati da Idea
    //--posso spostarla (è successo) senza che cambi nulla. Occorre modificare la creazione in questa enumeration.
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathIdeaProjects(true, false, "Directory che contiene i nuovi programmi appena creati da Idea") {
        @Override
        public void elabora(String pathCurrent) {
            String path = pathRoot.get();
            path += DIR_PROJECTS;
            this.setValue(path);
        }
    },// end of single enumeration

    //--elaborata inizialmente partendo dal pathRoot
    //--se siamo in AEFlag.isBaseFlow(), usa pathCurrent altrimenti cerca nel fileSystem la directory vaadFlow14
    //--posso spostarla (è successo) senza che cambi nulla.
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathVaadFlowRoot(true, false, "Directory del programma vaadFlow14 a livello di root") {
        @Override
        public void elabora(String pathCurrent) {
            String path = pathRoot.get();
            path += DIR_PROJECTS + DIR_OPERATIVI + DIR_VAADFLOW;
            this.setValue(path);
        }
    },// end of single enumeration

    //--elaborata inizialmente partendo dal pathVaadFlowRoot
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathVaadFlowResources(true, false, "Directory delle risorse di vaadFlow14") {
        @Override
        public void elabora(String pathCurrent) {
            String path = pathVaadFlowRoot.get();
            path += ROOT_DIR_MAIN + DIR_RESOURCES;
            this.setValue(path);
        }
    },// end of single enumeration

    //--elaborata inizialmente partendo dal pathVaadFlowResources
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathVaadFlowMetaInf(true, false, "Directory della cartella META-INF di vaadFlow14") {
        @Override
        public void elabora(String pathCurrent) {
            String path = pathVaadFlowResources.get();
            path += DIR_META;
            this.setValue(path);
        }
    },// end of single enumeration

    //--elaborata inizialmente partendo dal pathVaadFlowRoot
    //--contiene i moduli, di solito due (vaadFlow14 e xxx)
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathVaadFlowAlgos(true, false, "Directory che contiene sia il modulo vaadFlow14 sia modulo progetto target") {
        @Override
        public void elabora(String pathCurrent) {
            String path = pathVaadFlowRoot.get();
            path += ROOT_DIR_MAIN + DIR_ALGOS;
            this.setValue(path);
        }
    },// end of single enumeration

    //--elaborata inizialmente partendo dal pathVaadFlowAlgos
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathVaadFlowSources(true, false, "Directory che contiene i sorgenti testuali di vaadFlow14 da elaborare") {
        @Override
        public void elabora(String pathCurrent) {
            String path = pathVaadFlowAlgos.get();
            path += DIR_VAADFLOW + DIR_WIZARD + DIR_SOURCES;
            this.setValue(path);
        }
    },// end of single enumeration


    //--regolata DOPO essere passati da un dialogo (WizDialogNewProject, WizDialogUpdateProject, WizDialogNewPackage, WizDialogUpdatePackage)
    //--tutte le property il cui nome NON inizia con 'path' sono nomi parziali di files/directories
    //--può essere un new project oppure un update di un progetto esistente
    //--se siamo in un altro progetto, il nome del progetto stesso
    nameTargetProject(false, true, "Nome breve new/update project") {
        @Override
        public void modifica(String projectName) {
            this.setValue(projectName);
        }
    },// end of single enumeration

    //--regolata DOPO essere passati da un dialogo (WizDialogNewProject, WizDialogUpdateProject, WizDialogNewPackage, WizDialogUpdatePackage)
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    //--può essere un new project oppure un update di un progetto esistente
    //--se siamo in un altro progetto, il path del progetto stesso
    pathTargetRoot(false, true, "Path completo new/update project") {
        @Override
        public void modifica(String projectName) {
            String path = pathIdeaProjects.get() + nameTargetProject.get() + SLASH;
            this.setValue(path);
        }
    },// end of single enumeration


    //--elaborata inizialmente partendo dal pathTargetRoot
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathTargetResources(false, true, "Directory delle risorse del progetto target") {
        @Override
        public void modifica(String projectName) {
            String path = pathTargetRoot.get();
            path += ROOT_DIR_MAIN + DIR_RESOURCES;
            this.setValue(path);
        }
    },// end of single enumeration

    //--elaborata inizialmente partendo dal pathTargetResources
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathTargetMetaInf(false, true, "Directory della cartella META-INF del progetto target") {
        @Override
        public void modifica(String projectName) {
            String path = pathTargetResources.get();
            path += DIR_META;
            this.setValue(path);
        }
    },// end of single enumeration

    //--regolata DOPO essere passati da un dialogo (WizDialogNewProject, WizDialogUpdateProject, WizDialogNewPackage, WizDialogUpdatePackage)
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    ////parte dal livello di root del progetto
    //--contiene i moduli, di solito due (vaadFlow14 e xxx)
    pathTargetAlgos(false, true, "Directory che contiene sia il modulo vaadFlow14 sia modulo progetto target") {
        @Override
        public void modifica(String projectName) {
            String path = pathTargetRoot.get();
            path += ROOT_DIR_MAIN + DIR_ALGOS;
            this.setValue(path);
        }
    },// end of single enumeration

    //--elaborata inizialmente partendo dal pathTargetAlgos
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    pathTargetSources(false, true, "Directory che contiene i sorgenti wizard di vaadFlow14 da cancellare nel progetto target") {
        @Override
        public void modifica(String projectName) {
            String path = pathTargetAlgos.get();
            path += DIR_VAADFLOW + DIR_WIZARD + DIR_SOURCES;
            this.setValue(path);
        }
    },// end of single enumeration

    //    String destPath = AEDir.pathTargetProject.get() + ROOT_DIR_ALGOS + DIR_VAADFLOW;
    //    String sourcesPath = AEDir.pathTargetProject.get() + ROOT_DIR_ALGOS + DIR_VAADFLOW + DIR_SOURCES;

    ;

    //--riferimento iniettato nella classe statico FileServiceServiceInjector
    protected AFileService file;

    //--elaborazione SOLO all'apertura della view Wizard. Poi i valori restano statici (non modificabili)
    private boolean elaborabile;

    //--valori modificabili da ogni dialog
    private boolean modificabile;

    private String descrizione;

    private String value;


    /**
     * Costruttore completo <br>
     */
    AEDir(boolean elaborabile, boolean modificabile, String descrizione) {
        this.setElaborabile(elaborabile);
        this.setModificabile(modificabile);
        this.setDescrizione(descrizione);
    }


    /**
     * Elaborazione iniziale di ogni enumeration 'elaborabile', partendo da pathCurrent <br>
     * pathCurrent è stato ricavato da System.getProperty("user.dir") <br>
     * Si applica alle enumeration che hanno il flag elaborabile=true <br>
     * Elabora tutti i valori della Enumeration AEDir dipendenti dal pathCurrent <br>
     * Verranno usati dai dialoghi: <br>
     * WizDialogNewProject, WizDialogUpdateProject, WizDialogNewPackage, WizDialogUpdatePackage <br>
     *
     * @param pathCurrent directory dove gira il programma in uso. Recuperata dal System.
     */
    public static void elaboraAll(String pathCurrent) {
        for (AEDir aeDir : AEDir.getElaborabili()) {
            aeDir.elabora(pathCurrent);
        }
    }


    /**
     * Elaborazione di ogni enumeration 'modificabile', partendo da projectName <br>
     * Chiamato (di solito) alla dismissione di un dialogo: <br>
     * WizDialogNewProject, WizDialogUpdateProject, WizDialogNewPackage, WizDialogUpdatePackage <br>
     * Elabora tutti i valori della Enumeration AEDir dipendenti dal nome del progetto <br>
     * Verranno usati dagli scripts: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     *
     * @param projectName usato per regolare le istanze modificabili della enumeration. Recuperato dal dialogo.
     */
    public static void modificaAll(String projectName) {
        for (AEDir aeDir : AEDir.getModificabili()) {
            aeDir.modifica(projectName);
        }
    }


    public static List<AEDir> geValues() {
        List<AEDir> listaDirs = new ArrayList<>();

        for (AEDir aeDir : AEDir.values()) {
            listaDirs.add(aeDir);
        }

        return listaDirs;
    }

    //    public static void reset() {
    //        for (AEDir aeDir : AEDir.getModificabili()) {
    //            //            aeDir.setValue(aeDir.defaultValue);
    //        }
    //    }


    /**
     * Lista di enumerations che vengono regolate SOLO inizialmente alla creazione della view Wizard <br>
     * Partono dal valore di System.getProperty("user.dir") <br>
     */
    public static List<AEDir> getElaborabili() {
        List<AEDir> listaDirs = new ArrayList<>();

        for (AEDir aeDir : AEDir.values()) {
            if (aeDir.isElaborabile()) {
                listaDirs.add(aeDir);
            }
        }

        return listaDirs;
    }


    /**
     * Lista di enumerations che vengono regolate all'uscita di un dialogo: <br>
     * WizDialogNewProject, WizDialogUpdateProject, WizDialogNewPackage, WizDialogUpdatePackage <br>
     */
    public static List<AEDir> getModificabili() {
        List<AEDir> listaDirs = new ArrayList<>();

        for (AEDir aeDir : AEDir.values()) {
            if (aeDir.isModificabile()) {
                listaDirs.add(aeDir);
            }
        }

        return listaDirs;
    }


    /**
     * Visualizzazione di controllo <br>
     */
    public static void printInfo(String posizione) {
        if (FLAG_DEBUG_WIZ) {
            System.out.println("********************");
            System.out.println("AEDir  - " + posizione);
            System.out.println("********************");
            for (AEDir aeDir : AEDir.values()) {
                System.out.println("AEDir." + aeDir.name() + " \"" + aeDir.getDescrizione() + "\" = " + aeDir.get());
            }
            System.out.println("");
        }
    }


    /**
     * Elaborazione iniziale di una enumeration 'elaborabile', partendo dal pathCurrent <br>
     * Sovrascritto nella enumeration specifica <br>
     *
     * @param pathCurrent directory dove gira il programma in uso. Recuperata dal System.
     */
    public void elabora(String pathCurrent) {
    }


    /**
     * Elaborazione di ogni enumeration 'modificabile', partendo da projectName <br>
     * Chiamato (di solito) alla dismissione di un dialogo: <br>
     * Sovrascritto nella enumeration specifica <br>
     *
     * @param projectName usato per regolare le istanze modificabili della enumeration. Recuperato dal dialogo.
     */
    public void modifica(String projectName) {
    }


    public void setFile(AFileService file) {
        this.file = file;
    }


    public boolean isElaborabile() {
        return elaborabile;
    }


    public void setElaborabile(boolean elaborabile) {
        this.elaborabile = elaborabile;
    }


    public boolean isModificabile() {
        return modificabile;
    }


    public void setModificabile(boolean modificabile) {
        this.modificabile = modificabile;
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


    @Component
    public static class FileServiceServiceInjector {

        @Autowired
        private AFileService file;


        @PostConstruct
        public void postConstruct() {
            for (AEDir aeDir : AEDir.values()) {
                aeDir.setFile(file);
            }
        }

    }
}
