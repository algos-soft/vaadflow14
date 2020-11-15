package it.algos.vaadflow14.wizard.scripts;

import it.algos.vaadflow14.backend.enumeration.AECopyDir;
import it.algos.vaadflow14.backend.enumeration.AECopyFile;
import it.algos.vaadflow14.backend.service.AFileService;
import it.algos.vaadflow14.backend.service.ALogService;
import it.algos.vaadflow14.backend.service.ATextService;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEDir;
import org.springframework.beans.factory.annotation.Autowired;

import static it.algos.vaadflow14.backend.application.FlowCost.JAVA_SUFFIX;
import static it.algos.vaadflow14.backend.application.FlowCost.TXT_SUFFIX;
import static it.algos.vaadflow14.backend.application.FlowCost.XML_SUFFIX;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
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

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected ALogService logger;


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
    }


    /**
     * Regolazioni iniziali indipendenti (in parte) dal dialogo di input <br>
     */
    protected void regolazioniIniziali() {
        //        this.pathUserDir = AEWiz.pathCurrent.getValue();
        //        this.pathVaadFlow = AEWiz.pathVaadFlow.getValue();
        //        this.pathVaadFlowMain = pathVaadFlow + DIR_MAIN;
        //        this.pathVaadFlowAlgos = pathVaadFlow + DIR_ALGOS;
        //        this.pathVaadFlowWizTxtSources = pathVaadFlow + DIR_VAADFLOW_SOURCES;
        //        this.pathVaadFlowResources = pathVaadFlow + DIR_RESOURCES;
        //        this.pathVaadFlowFrontend = pathVaadFlow + DIR_FRONT_END;
        //
        //        this.newProjectName = AEWiz.nameTargetProject.getValue();
        //        this.newProjectNameUpper = text.primaMaiuscola(newProjectName);
        //        this.pathProject = AEWiz.pathTargetProject.getValue();
        //
        //        this.pathProjectMain = pathProject + DIR_MAIN;
        //        this.pathProjectAlgos = pathProject + DIR_ALGOS;
        //        this.pathProjectModulo = pathProjectAlgos + newProjectName + SLASH;
        //        this.pathProjectDirApplication = pathProjectModulo + DIR_APPLICATION;
        //        this.pathProjectDirPackages = pathProjectModulo + DIR_PACKAGES;
        //        this.pathProjectMeta = pathProject + DIR_META;
        //        this.pathProjectResources = pathProject + DIR_RESOURCES;
        //        this.pathProjectFrontend = pathProject + DIR_FRONT_END;
        //        this.pathProjectVaadFlowModulo = pathProjectAlgos + DIR_VAADFLOW;
        //        this.pathProjectSourcesDeleting = pathProjectVaadFlowModulo + DIR_SOURCES;
        //
        //        AEToken.pathVaadFlowWizTxtSources.setValue(pathVaadFlowWizTxtSources);
        //
        //        //--visualizzazione di controllo
        //        if (FLAG_DEBUG_WIZ) {
        //            System.out.println("");
        //            System.out.println("********************");
        //            if (isNuovoProgetto) {
        //                System.out.println("Ingresso in WizElaboraNewProject");
        //            } else {
        //                System.out.println("Ingresso in WizElaboraUpdateProject");
        //            }// end of if/else cycle
        //            System.out.println("********************");
        //            System.out.println("Progetto corrente: pathUserDir=" + pathUserDir);
        //            System.out.println("Directory VaadFlow: pathVaadFlow=" + pathVaadFlow);
        //            System.out.println("Nome target progetto: newProjectName=" + newProjectName);
        //            System.out.println("Nome target progetto maiuscolo: newProjectNameUpper=" + newProjectNameUpper);
        //            System.out.println("Path target progetto: pathProject=" + pathProject);
        //
        //            System.out.println("");
        //            System.out.println("Cartella 'root'' di VaadFlow: pathVaadFlow=" + pathVaadFlow);
        //            System.out.println("Cartella 'main' di VaadFlow: pathVaadFlowMain=" + pathVaadFlowMain);
        //            System.out.println("Cartella 'algos' di VaadFlow: pathVaadFlowAlgos=" + pathVaadFlowAlgos);
        //            System.out.println("Sorgenti di testo di VaadFlow: pathVaadFlowWizTxtSources=" + pathVaadFlowWizTxtSources);
        //            System.out.println("Cartella 'resources' di VaadFlow: pathVaadFlowResources=" + pathVaadFlowResources);
        //            System.out.println("Cartella 'frontend' di VaadFlow': pathVaadFlowFrontend=" + pathVaadFlowFrontend);
        //
        //            System.out.println("");
        //            System.out.println("Nome minuscolo del project': newProjectName=" + newProjectName);
        //            System.out.println("Nome maiuscolo del project': newProjectNameUpper=" + newProjectNameUpper);
        //            System.out.println("Cartella 'root' del target project': pathProject=" + pathProject);
        //
        //            System.out.println("");
        //            System.out.println("Cartella 'nomeDelProjectMinuscolo' del target project: pathProjectModulo=" + pathProjectModulo);
        //            System.out.println("Cartella 'main' del target project: pathProjectMain=" + pathProjectMain);
        //            System.out.println("Cartella 'algos' del target project': pathProjectAlgos=" + pathProjectAlgos);
        //            System.out.println("Cartella 'application' del target project: pathProjectDirApplication=" + pathProjectDirApplication);
        //            System.out.println("Cartella 'packages' del target project: pathProjectDirModules=" + pathProjectDirPackages);
        //            System.out.println("Cartella 'resources' del target project: pathProjectResources=" + pathProjectResources);
        //            System.out.println("Cartella 'frontend' del target project: pathProjectFrontend=" + pathProjectFrontend);
        //            System.out.println("Cartella 'vaadflow14' del target project: pathProjectVaadFlowModulo=" + pathProjectVaadFlowModulo);
        //            System.out.println("Cartella 'sourcesToBeDeleted' del target project: pathProjectVaadFlowModulo=" + pathProjectSourcesDeleting);
        //
        //            System.out.println("");
        //        }// end of if cycle
        //
    }


    /**
     * Crea la cartella CONFIG di risorse on-line extra JAR (in formati vari) <br>
     * Posizionata a livello di root <br>
     * Copia la directory omonima di VaadFlow <br>
     * Se esiste già, aggiunge nuovi elementi SENZA cancellare quelli esistenti <br>
     * Se è isNewProject()=true, la crea nuova o la integra se esisteva già <br>
     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
     */
    protected void copiaDirectoryConfig() {
        if (AECheck.config.is()) {
            wizService.copyDirectoryProjectRoot(AECopyDir.addingOnly, ROOT_DIR_CONFIG);
        }
    }


    /**
     * Crea la cartella DOC di documentazione ALGOS (in formati vari) <br>
     * Posizionata a livello di root <br>
     * Copia la directory omonima di VaadFlow <br>
     * Se esiste già, ci va sopra cancellando la preesistente versione <br>
     * Se è isNewProject()=true, la crea nuova o la sovrascrive se esisteva già <br>
     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
     */
    protected void copiaDirectoryDoc() {
        if (AECheck.documentation.is()) {
            wizService.copyDirectoryProjectRoot(AECopyDir.deletingAll, ROOT_DIR_DOC);
        }
    }


    /**
     * Crea la cartella FRONTEND di risorse per NPM (in formati vari) <br>
     * Posizionata a livello di root <br>
     * Copia la directory omonima di VaadFlow <br>
     * Se esiste già, aggiunge nuovi elementi SENZA cancellare quelli esistenti <br>
     * Se è isNewProject()=true, la crea nuova o la integra se esisteva già <br>
     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
     */
    protected void copiaDirectoryFrontend() {
        if (AECheck.frontend.is()) {
            wizService.copyDirectoryProjectRoot(AECopyDir.addingOnly, ROOT_DIR_FRONTEND);
        }
    }


    /**
     * Crea la cartella LINKS di siti WEB interessanti (in formato text) <br>
     * Posizionata a livello di root <br>
     * Copia la directory omonima di VaadFlow <br>
     * Se esiste già, ci va sopra cancellando la preesistente versione <br>
     * Se è isNewProject()=true, la crea nuova o la sovrascrive se esisteva già <br>
     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
     */
    protected void copiaDirectoryLinks() {
        if (AECheck.links.is()) {
            wizService.copyDirectoryProjectRoot(AECopyDir.deletingAll, ROOT_DIR_LINKS);
        }
    }


    /**
     * Crea la cartella SNIPPETS di pezzetti utili di codice (in formato txt) <br>
     * Posizionata a livello di root <br>
     * Copia la directory omonima di VaadFlow <br>
     * Se esiste già, ci va sopra cancellando la preesistente versione <br>
     * Se è isNewProject()=true, la crea nuova o la sovrascrive se esisteva già <br>
     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
     */
    protected void copiaDirectorySnippets() {
        if (AECheck.snippets.is()) {
            wizService.copyDirectoryProjectRoot(AECopyDir.deletingAll, ROOT_DIR_SNIPPETS);
        }
    }


    /**
     * Crea la cartella VAADFLOW14 <br>
     * Se esiste già, ci va sopra cancellando la preesistente versione <br>
     * Se è isNewProject()=true, la crea nuova o la sovrascrive se esisteva già <br>
     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
     * Cancella la sub-directory SOURCES (deve esserci un originale unico mantenuto nel progetto base) <br>
     */
    public void copiaDirectoryVaadFlow() {
        String srcPath = AEDir.pathVaadFlowAlgos.get() + DIR_VAADFLOW;
        String destPath = AEDir.pathTargetAlgos.get() + DIR_VAADFLOW;

        if (AECheck.flow.is()) {
            wizService.copyDirectoryProject(AECopyDir.deletingAll, srcPath, destPath);
        }
        file.deleteDirectory(AEDir.pathTargetSources.get());
    }


    /**
     * Crea la cartella META-INF di risorse (in formati vari) <br>
     * Copia la directory omonima di VaadFlow <br>
     * Se esiste già, aggiunge elementi SENZA cancellare quelli esistenti <br>
     * Se è isNewProject()=true, la crea nuova o la sovrascrive se esisteva già <br>
     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
     */
    protected void copiaDirectoryMetaInf() {
        String srcPath = AEDir.pathVaadFlowResources.get() + DIR_META;
        String destPath = AEDir.pathTargetResources.get() + DIR_META;

        if (AECheck.resources.is()) {
            wizService.copyDirectoryProject(AECopyDir.addingOnly, srcPath, destPath);
        }
    }


    /**
     * Crea il file application.properties (in formato txt) <br>
     * Legge il testo da wizard.sources di VaadFlow14 <br>
     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
     * Se esiste già il file, controlla che NON ci sia il flag sovraScrivibile=false <br>
     */
    protected void creaFileProperties() {
        String nameSourceText = FILE_PROPERTIES;
        String destPathSuffix = AEDir.pathTargetResources.get() + FILE_PROPERTIES_DEST;

        if (AECheck.property.is()) {
            wizService.creaFile(AECopyFile.checkFlagSeEsiste, nameSourceText, destPathSuffix);
        }
    }


    /**
     * Crea il file BANNER (in formato txt) <br>
     * Legge il testo da wizard.sources di VaadFlow14 <br>
     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
     * Se esiste già, ci va sopra cancellando la preesistente versione <br>
     */
    protected void creaFileBanner() {
        String nameSourceText = FILE_BANNER;
        String destPathSuffix = AEDir.pathTargetResources.get() + FILE_BANNER + TXT_SUFFIX;

        if (AECheck.banner.is()) {
            wizService.creaFile(AECopyFile.sovrascriveSempreAncheSeEsiste, nameSourceText, destPathSuffix);
        }
    }


    /**
     * Crea il file GIT (senza formato) <br>
     * Posizionato a livello di root <br>
     * Legge il testo da wizard.sources di VaadFlow14 <br>
     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
     * Se esiste già, ci va sopra cancellando la preesistente versione <br>
     */
    protected void creaFileGit() {
        String nameSourceText = FILE_GIT;
        String destPathSuffix = AEDir.pathTargetRoot.get() + FILE_GIT;

        if (AECheck.git.is()) {
            wizService.creaFile(AECopyFile.sovrascriveSempreAncheSeEsiste, nameSourceText, destPathSuffix);
        }
    }


    /**
     * Crea il file POM di Maven (in formato xml) <br>
     * Posizionato a livello di root <br>
     * Legge il testo da wizard.sources di VaadFlow14 <br>
     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
     * Se esiste già il file, controlla che NON ci sia il flag sovraScrivibile=false <br>
     */
    protected void creaFilePom() {
        String nameSourceText = FILE_POM;
        String destPathSuffix = AEDir.pathTargetRoot.get() + FILE_POM + XML_SUFFIX;

        if (AECheck.pom.is()) {
            wizService.creaFile(AECopyFile.checkFlagSeEsiste, nameSourceText, destPathSuffix);
        }
    }


    /**
     * Sovrascrive il file README (in formato MD) <br>
     * Posizionato a livello di root <br>
     * Legge il testo da wizard.sources di VaadFlow14 <br>
     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
     * Se esiste già, ci va sopra cancellando la preesistente versione <br>
     */
    protected void creaFileRead() {
        String nameSourceText = FILE_READ;
        String destPathSuffix = AEDir.pathTargetRoot.get() + FILE_READ + TXT_SUFFIX;

        if (AECheck.read.is()) {
            wizService.creaFile(AECopyFile.sovrascriveSempreAncheSeEsiste, nameSourceText, destPathSuffix);
        }
    }


    /**
     * Creazione del file nella directory dei packages <br>
     */
    protected void creaFileEntity() {
        String tag = "Entity";
        String nomeFileDest = AEDir.nameTargetPackageUpper.get();
        String pathFileToBeWritten = AEDir.pathTargetPackages.get() + nomeFileDest + JAVA_SUFFIX;
        if (AECheck.entity.is()) {
            wizService.creaFile(AECopyFile.sovrascriveSempreAncheSeEsiste, tag, pathFileToBeWritten);
        }
    }


    /**
     * Creazione del menu nel file xxxBoot <br>
     * Aggiunta import necessario in testa al file <br>
     */
    protected void fixBoot() {
        this.fixBootMenu();
        this.fixBootImport();
    }


    /**
     * Creazione del menu nel file xxxBoot <br>
     */
    protected void fixBootMenu() {
        String testo = VUOTA;
        String tagOld = "super.addMenuRoutes();";
        String pack = AEDir.nameTargetPackageUpper.get();
        String tagNew = "FlowVar.menuRouteList.add(" + pack + ".class);";
        String pathError;

        String pathFileBoot = AEDir.fileTargetBoot.get();
        testo = file.leggeFile(pathFileBoot);
        pathError = file.findPathDopoDirectory(pathFileBoot, DIR_BACKEND);

        if (testo.contains(tagOld)) {
            if (!testo.contains(tagNew)) {
                tagNew = tagOld + A_CAPO + TAB + TAB + tagNew;
                testo = testo.replace(tagOld, tagNew);
                file.sovraScriveFile(pathFileBoot, testo);
                logger.info("Aggiunto menuRoutes nel file " + pathError, this.getClass(), "fixBootMenu");
            }
        } else {
            logger.warn("Nel file " + pathError + " manca un codice di riferimento essenziale", this.getClass(), "fixBootMenu");
        }


    }


    /**
     * Aggiunta import necessario in testa al file <br>
     */
    protected void fixBootImport() {
        String testo = VUOTA;
        String tagOld = "import it.algos.vaadflow14.backend.application.FlowVar;";
        String project = AEDir.nameTargetProject.get();
        String pack = AEDir.nameTargetPackage.get();
        String clazz = AEDir.nameTargetPackageUpper.get();
        String tagNew = "import it.algos." + project + ".backend.packages." + pack + "." + clazz + ";";
        String pathError = VUOTA;

        String pathFileBoot = AEDir.fileTargetBoot.get();
        testo = file.leggeFile(pathFileBoot);
        pathError = file.findPathDopoDirectory(pathFileBoot, DIR_BACKEND);

        if (testo.contains(tagOld)) {
            if (!testo.contains(tagNew)) {
                tagNew = tagOld + A_CAPO + tagNew;
                testo = testo.replace(tagOld, tagNew);
                file.sovraScriveFile(pathFileBoot, testo);
                logger.info("Aggiunto import della Entity nel file " + pathError, this.getClass(), "fixBootImport");
            }
        } else {
            logger.warn("Nel file " + pathError + " manca un codice di riferimento essenziale", this.getClass(), "fixBootImport");
        }
    }


    //    protected String leggeFile(String nomeFileTextSorgente) {
    //        String nomeFileTxt = nomeFileTextSorgente;
    //
    //        if (!nomeFileTxt.endsWith(TXT_SUFFIX)) {
    //            nomeFileTxt += TXT_SUFFIX;
    //        }// end of if cycle
    //
    //        return file.leggeFile(pathVaadFlowWizTxtSources + nomeFileTxt);
    //    }// end of method


}
