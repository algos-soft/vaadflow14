package it.algos.vaadflow14.wizard.scripts;

import it.algos.vaadflow14.backend.service.AFileService;
import it.algos.vaadflow14.backend.service.ATextService;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import it.algos.vaadflow14.wizard.enumeration.AEWiz;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:29
 */
public abstract class WizElabora implements WizRecipient {

    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con @Autowired <br>
     * Disponibile al termine del costruttore di questa classe <br>
     */
    @Autowired
    protected AFileService file;

    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con @Autowired <br>
     * Disponibile al termine del costruttore di questa classe <br>
     */
    @Autowired
    protected ATextService text;

    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con @Autowired <br>
     * Disponibile al termine del costruttore di questa classe <br>
     */
    @Autowired
    protected WizService wizService;

    //--flag di controllo regolato nella sottoclasse concreta
    protected boolean isNuovoProgetto;

    //--flag di controllo regolato nella sottoclasse concreta
    protected boolean isStartThisProgetto;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trova attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory dove gira questo programma; recuperata dal System
    protected String pathUserDir;

    //    //--regolata indipendentemente dai risultati del dialogo
    //    //--dipende solo da dove si trova attualmente il progetto VaadFlow
    //    //--posso spostarlo (è successo) senza che cambi nulla
    //    //--directory che contiene i nuovi programmi appena creati da Idea
    //    protected String pathProjectsDir;

    //--regolata indipendentemente dai risultati del dialogo
    //--directory di 'root'
    //--dipende solo da dove si trova attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory che contiene il programma VaadFlow
    //--normalmente uguale a pathUserDir
    protected String pathVaadFlow;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trova attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory che contiene java e resources (da elaborare)
    //--pathVaadFlow più DIR_MAIN
    protected String pathVaadFlowMain;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trova attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory che contiene il modulo vaadflow ed il modulo del programmma corrente (da elaborare)
    //--pathVaadFlow più DIR_ALGOS
    protected String pathVaadFlowAlgos;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trova attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory dei sorgenti testuali di VaadFlow (da elaborare)
    //--pathVaadFlow più DIR_VAADFLOW_SOURCES
    protected String pathVaadFlowWizTxtSources;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trova attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--pathVaadFlow più DIR_RESOURCES
    protected String pathVaadFlowResources;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trova attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--pathVaadFlow più DIR_FRONT_END
    protected String pathVaadFlowFrontend;

    //--regolata in base ai risultati del dialogo
    //--ha senso solo se isNuovoProgetto=true
    protected String newProjectName;

    //--regolata in base ai risultati del dialogo
    //--ha senso solo se isNuovoProgetto=true
    //--newProjectName con la prima lettera maiuscola da usare per costruire i nomi delle classi
    protected String newProjectNameUpper;

    //    //--regolata in base ai risultati del dialogo
    //    //--ha senso solo se isNuovoProgetto=false
    //    protected String targetProjectName;

    //--regolata in base ai risultati del dialogo
    //--directory di 'root'
    //--path completo del progetto da creare/modificare
    protected String pathProject;

    //--regolata in base ai risultati del dialogo
    //--directory che contiene java e resources (da elaborare)
    //--pathProject più DIR_MAIN
    protected String pathProjectMain;

    //--regolata in base ai risultati del dialogo
    //--pathProject più DIR_JAVA
    protected String pathProjectAlgos;

    //--regolata in base ai risultati del dialogo
    //--pathProject più meta
    protected String pathProjectMeta;

    //--regolata in base ai risultati del dialogo
    //--pathProject più DIR_RESOURCES
    protected String pathProjectResources;

    //--regolata in base ai risultati del dialogo
    //--pathProject più DIR_FRONT_END
    protected String pathProjectFrontend;

    //--regolata in base ai risultati del dialogo
    //--pathProjectAlgos più newProjectName
    protected String pathProjectModulo;

    //--regolata in base ai risultati del dialogo
    //--pathProjectModulo più DIR_APPLICATION
    protected String pathProjectDirApplication;

    //--regolata in base ai risultati del dialogo
    //--pathProjectModulo più DIR_PACKAGES
    protected String pathProjectDirPackages;

    //--regolata in base ai risultati del dialogo
    //--pathProject più DIR_VAADFLOW
    protected String pathProjectVaadFlowModulo;

    //--regolata in base ai risultati del dialogo
    //--pathProjectVaadFlowModulo più DIR_SOURCES
    protected String pathProjectSourcesDeleting;


    /**
     * Evento lanciato alla chiusura del dialogo
     */
    @Override
    public void esegue() {
        this.regolazioniIniziali();
    }// end of method


    /**
     * Regolazioni iniziali indipendenti (in parte) dal dialogo di input <br>
     */
    protected void regolazioniIniziali() {
        this.pathUserDir = AEWiz.pathUserDir.getValue();
        this.pathVaadFlow = AEWiz.pathVaadFlow.getValue();
        this.pathVaadFlowMain = pathVaadFlow + DIR_MAIN;
        this.pathVaadFlowAlgos = pathVaadFlow + DIR_ALGOS;
        this.pathVaadFlowWizTxtSources = pathVaadFlow + DIR_VAADFLOW_SOURCES;
        this.pathVaadFlowResources = pathVaadFlow + DIR_RESOURCES;
        this.pathVaadFlowFrontend = pathVaadFlow + DIR_FRONT_END;

        this.newProjectName = AEWiz.nameTargetProject.getValue();
        this.newProjectNameUpper = text.primaMaiuscola(newProjectName);
        this.pathProject = AEWiz.pathTargetProjet.getValue();

        this.pathProjectMain = pathProject + DIR_MAIN;
        this.pathProjectAlgos = pathProject + DIR_ALGOS;
        this.pathProjectModulo = pathProjectAlgos + newProjectName + SLASH;
        this.pathProjectDirApplication = pathProjectModulo + DIR_APPLICATION;
        this.pathProjectDirPackages = pathProjectModulo + DIR_PACKAGES;
        this.pathProjectMeta = pathProject + DIR_META;
        this.pathProjectResources = pathProject + DIR_RESOURCES;
        this.pathProjectFrontend = pathProject + DIR_FRONT_END;
        this.pathProjectVaadFlowModulo = pathProjectAlgos + DIR_VAADFLOW;
        this.pathProjectSourcesDeleting = pathProjectVaadFlowModulo + DIR_SOURCES;

        AEToken.pathVaadFlowWizTxtSources.setValue(pathVaadFlowWizTxtSources);

        //--visualizzazione di controllo
        if (FLAG_DEBUG_WIZ) {
            System.out.println("");
            System.out.println("********************");
            if (isNuovoProgetto) {
                System.out.println("Ingresso in WizElaboraNewProject");
            } else {
                System.out.println("Ingresso in WizElaboraUpdateProject");
            }// end of if/else cycle
            System.out.println("********************");
            System.out.println("Progetto corrente: pathUserDir=" + pathUserDir);
            System.out.println("Directory VaadFlow: pathVaadFlow=" + pathVaadFlow);
            System.out.println("Nome target progetto: newProjectName=" + newProjectName);
            System.out.println("Nome target progetto maiuscolo: newProjectNameUpper=" + newProjectNameUpper);
            System.out.println("Path target progetto: pathProject=" + pathProject);

            System.out.println("");
            System.out.println("Cartella 'root'' di VaadFlow: pathVaadFlow=" + pathVaadFlow);
            System.out.println("Cartella 'main' di VaadFlow: pathVaadFlowMain=" + pathVaadFlowMain);
            System.out.println("Cartella 'algos' di VaadFlow: pathVaadFlowAlgos=" + pathVaadFlowAlgos);
            System.out.println("Sorgenti di testo di VaadFlow: pathVaadFlowWizTxtSources=" + pathVaadFlowWizTxtSources);
            System.out.println("Cartella 'resources' di VaadFlow: pathVaadFlowResources=" + pathVaadFlowResources);
            System.out.println("Cartella 'frontend' di VaadFlow': pathVaadFlowFrontend=" + pathVaadFlowFrontend);

            System.out.println("");
            System.out.println("Nome minuscolo del project': newProjectName=" + newProjectName);
            System.out.println("Nome maiuscolo del project': newProjectNameUpper=" + newProjectNameUpper);
            System.out.println("Cartella 'root' del target project': pathProject=" + pathProject);

            System.out.println("");
            System.out.println("Cartella 'nomeDelProjectMinuscolo' del target project: pathProjectModulo=" + pathProjectModulo);
            System.out.println("Cartella 'main' del target project: pathProjectMain=" + pathProjectMain);
            System.out.println("Cartella 'algos' del target project': pathProjectAlgos=" + pathProjectAlgos);
            System.out.println("Cartella 'application' del target project: pathProjectDirApplication=" + pathProjectDirApplication);
            System.out.println("Cartella 'packages' del target project: pathProjectDirModules=" + pathProjectDirPackages);
            System.out.println("Cartella 'resources' del target project: pathProjectResources=" + pathProjectResources);
            System.out.println("Cartella 'frontend' del target project: pathProjectFrontend=" + pathProjectFrontend);
            System.out.println("Cartella 'vaadflow14' del target project: pathProjectVaadFlowModulo=" + pathProjectVaadFlowModulo);
            System.out.println("Cartella 'sourcesToBeDeleted' del target project: pathProjectVaadFlowModulo=" + pathProjectSourcesDeleting);

            System.out.println("");
        }// end of if cycle

    }


    /**
     * Cartella di documentazione (in formati vari)
     * Copia la directory da root di VaadFlow
     */
    protected void copiaDirectoryDoc() {
        if (AEWiz.flagDocumentation.isAbilitato()) {
            wizService.copyCartellaRootProject(DIR_DOC);
        }
    }


    /**
     * Cartella di LINKS utili in text
     * Copia la directory da root di VaadFlow
     */
    protected void copiaDirectoryLinks() {
        if (AEWiz.flagLinks.isAbilitato()) {
            wizService.copyCartellaRootProject(DIR_LINKS);
        }
    }


    /**
     * Cartella di snippets utili in text
     * Copia la directory da root di VaadFlow
     */
    protected void copiaDirectorySnippets() {
        if (AEWiz.flagSnippets.isAbilitato()) {
            wizService.copyCartellaRootProject(DIR_SNIPPETS);
        }
    }


    /**
     * Copia tutta la directory MENO la cartella 'sources'
     */
    public void copiaCartellaVaadFlow() {
        if (AEWiz.flagFlow.isAbilitato()) {
            file.copyDirectoryDeletingAll(pathVaadFlowAlgos, pathProjectAlgos, NAME_VAADFLOW);
        }
        file.deleteDirectory(pathProjectSourcesDeleting);
    }


    /**
     * Cartella di risorse indispensabili per npm
     * Copia la directory da root di VaadFlow
     */
    protected void copiaFrontend() {
        if (AEWiz.flagFrontend.isAbilitato()) {
            wizService.copyCartellaRootProject(DIR_FRONT_END);
        }
    }


    /**
     * Cartella di resources META-INF
     */
    protected void copiaMetaInf() {
        if (AEWiz.flagResources.isAbilitato()) {
            file.copyDirectoryAddingOnly(pathVaadFlowMain, pathProjectMain, DIR_META_NAME);
        }
    }


    /**
     * File application.properties
     * Controlla se esiste la directory e nel caso la crea
     * Elabora il file dai sorgenti di VaadFlow
     * Sovrascrive dopo aver controllato se non c'è lo stop nel testo
     */
    protected void scriveFileProperties() {
        if (AEWiz.flagProperty.isAbilitato()) {
            if (checkFileCanBeModified(pathProjectResources, FILE_PROPERTIES_DEST)) {
                wizService.scriveNewFileCreatoDaWizSource(FILE_PROPERTIES, pathProjectResources, FILE_PROPERTIES_DEST, VUOTA);
            }
        }
    }


    /**
     * Se non esiste, lo scrive dai sorgenti <br>
     * Elabora il file dai sorgenti di VaadFlow
     */
    protected void scriveFileBanner() {
        if (AEWiz.flagBanner.isAbilitato()) {
            wizService.scriveNewFileCreatoDaWizSource(FILE_BANNER, pathProjectResources, TXT_SUFFIX);
        }
    }


    /**
     * File di esclusioni GIT di text
     * Copia la directory da root di VaadFlow
     */
    protected void scriveFileGit() {
        if (AEWiz.flagGit.isAbilitato()) {
            if (checkFileCanBeModified(pathProject, FILE_GIT)) {
                wizService.scriveNewFileCreatoDaWizSource(FILE_GIT, pathProject, VUOTA);
            }
        }
    }


    /**
     * File MAVEN di script
     * Elabora il file dai sorgenti di VaadFlow
     */
    protected void scriveFilePom() {
        if (AEWiz.flagPom.isAbilitato()) {
            wizService.scriveNewFileCreatoDaWizSource(FILE_POM, pathProject, XML_SUFFIX);
        }
    }


    /**
     * File README di text
     * Elabora il file dai sorgenti di VaadFlow
     */
    protected void scriveFileRead() {
        if (AEWiz.flagRead.isAbilitato()) {
            wizService.scriveNewFileCreatoDaWizSource(FILE_READ, pathProject, TXT_SUFFIX);
        }
    }


    protected String leggeFile(String nomeFileTextSorgente) {
        String nomeFileTxt = nomeFileTextSorgente;

        if (!nomeFileTxt.endsWith(TXT_SUFFIX)) {
            nomeFileTxt += TXT_SUFFIX;
        }// end of if cycle

        return file.leggeFile(pathVaadFlowWizTxtSources + nomeFileTxt);
    }// end of method


    private boolean checkFileCanBeModified(String pathDirectory, String nameFile) {
        String path = pathDirectory + nameFile;
        String oldText = file.leggeFile(path);

        if (!file.isEsisteFile(pathDirectory, nameFile)) {
            return true;
        }

        if (text.isValid(oldText) && checkFile(oldText)) {
            return true;
        }

        return false;
    }


    private boolean checkFile(String oldFileText) {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("@AIScript(sovrascrivibile = false)");
        tags.add("@AIScript(sovrascrivibile=false)");
        tags.add("@AIScript(sovrascrivibile= false)");
        tags.add("@AIScript(sovrascrivibile =false)");
        tags.add("@AIScript(sovraScrivibile = false)");
        tags.add("@AIScript(sovraScrivibile=false)");
        tags.add("@AIScript(sovraScrivibile= false)");
        tags.add("@AIScript(sovraScrivibile =false)");

        return text.nonContiene(oldFileText, tags);
    }

}
