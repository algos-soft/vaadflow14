package it.algos.vaadflow14.wizard.scripts;

import it.algos.vaadflow14.backend.application.*;
import static it.algos.vaadflow14.backend.application.FlowCost.DIR_PACKAGES;
import static it.algos.vaadflow14.backend.application.FlowCost.JAVA_SUFFIX;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.backend.wrapper.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;
import org.springframework.beans.factory.annotation.*;


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
        logger.log(AETypeLog.wizard, VUOTA);
    }


    /**
     * Creazione o modifica dei files previsti per il package <br>
     */
    protected void fixPackage() {
        for (AEPackage pack : AEPackage.values()) {
            if (pack.is()) {
                if (pack.isFile()) {
                    fixFile(pack);
                }
                if (pack == AEPackage.menu) {
                    fixMenu();
                }
            }
        }
    }


    /**
     * Creazione del file nella directory del package <br>
     */
    protected void fixFile(AEPackage pack) {
        AIResult result;
        String suffix = pack.getSuffix() + JAVA_SUFFIX;
        String nameSourceText = pack.getSourcesName();
        String pathFileToBeWritten = AEWizCost.pathTargetSingoloPackage.get() + AEWizCost.nameTargetPackageUpper.get();
        pathFileToBeWritten += suffix;

        if (pack.is()) {
            result = wizService.creaFilePackage(AECopyWiz.fileCheckFlagSeEsiste, nameSourceText, pathFileToBeWritten);
            logger.log(AETypeLog.wizard, "Nel package " + result.getMessage());
        }
    }


    /**
     * Creazione del menu nel file xxxBoot <br>
     * Aggiunta import necessario in testa al file <br>
     */
    protected void fixMenu() {
        this.fixBootMenu();
        this.fixBootImport();
    }


    /**
     * Creazione del menu nel file xxxBoot <br>
     */
    protected void fixBootMenu() {
        String message;
        String oldText;
        String newText;
        String tagIni = "super.fixMenuRoutes();";
        String nomeFileTextSorgente = "Boot";
        String packageName = AEWizCost.nameTargetPackageUpper.get();
        String tagNew = "FlowVar.menuRouteList.add(" + packageName + ".class);";
        String pathFileBoot = AEWizCost.pathTargetProjectBoot.get();
        pathFileBoot += AEWizCost.projectCurrent.get() + nomeFileTextSorgente;
        String pathBreve = file.findPathBreve(pathFileBoot, DIR_BACKEND);
        pathFileBoot += JAVA_SUFFIX;

        oldText = file.leggeFile(pathFileBoot);

        if (oldText.contains(tagIni)) {
            if (!oldText.contains(tagNew)) {
                tagNew = tagIni + FlowCost.A_CAPO + FlowCost.TAB + FlowCost.TAB + tagNew;
                newText = oldText.replace(tagIni, tagNew);
                file.sovraScriveFile(pathFileBoot, newText);
                message = String.format("Aggiunto link alla Entity %s in %s.fixMenuRoutes()", packageName, pathBreve);
                logger.log(AETypeLog.wizard, message);
            }
            else {
                message = String.format("Nel package esisteva già il link a %s in %s.fixMenuRoutes()", packageName, pathBreve);
                logger.log(AETypeLog.wizard, message);
            }
        }
        else {
            message = String.format("Nel file %s manca un codice di riferimento essenziale", pathBreve);
            logger.log(AETypeLog.wizard, message);
        }
    }


    /**
     * Aggiunta import necessario in testa al file <br>
     */
    protected void fixBootImport() {
        String message;
        String oldText = VUOTA;
        String newText = VUOTA;
        String nomeFileTextSorgente = "Boot";
        String tagOld = "import it.algos.vaadflow14.backend.application.FlowVar;";
        String project = AEWizCost.projectCurrentLower.get();
        String pack = AEWizCost.nameTargetPackage.get();
        String clazz = AEWizCost.nameTargetPackageUpper.get();
        String packageName = AEWizCost.nameTargetPackageUpper.get();
        String pathFileBoot = AEWizCost.pathTargetProjectBoot.get();
        pathFileBoot += AEWizCost.projectCurrent.get() + nomeFileTextSorgente + JAVA_SUFFIX;
        String tagNew = "import it.algos." + project + ".backend.packages." + pack + "." + clazz + ";";
        String pathEntity = AEWizCost.pathTargetSingoloPackage.get() + text.primaMaiuscola(pack);
        String pathBreveEntity = file.findPathBreve(pathEntity, DIR_PACKAGES);
        String pathBreveBoot = file.findPathBreve(pathFileBoot, DIR_BACKEND);

        oldText = file.leggeFile(pathFileBoot);
        if (oldText.contains(tagOld)) {
            if (!oldText.contains(tagNew)) {
                tagNew = tagOld + FlowCost.A_CAPO + tagNew;
                newText = oldText.replace(tagOld, tagNew);
                file.sovraScriveFile(pathFileBoot, newText);
                message = String.format("Aggiunto l'import di %s nel file %s", pathBreveEntity, pathBreveBoot);
                logger.log(AETypeLog.wizard, message);
            }
            else {
                message = String.format("Nel package esisteva già l'import di %s nel file %s", packageName, pathBreveBoot);
                logger.log(AETypeLog.wizard, message);
            }
        }
        else {
            message = String.format("Nel file %s manca un codice di riferimento essenziale", pathBreveBoot);
            logger.log(AETypeLog.wizard, message);
        }
    }


    /**
     * Modifica della documentazione dei files di un package <br>
     *
     * @param inizioFile per la modifica dell'header
     */
    protected void fixDocPackage(boolean inizioFile) {
        AIResult risultato = AResult.errato();
        boolean status = true;
        int numFiles = 0;
        String message = VUOTA;
        String projectName = AEDir.nameTargetProject.get();

        for (String packageName : wizService.getPackages()) {
            numFiles = 0;
            status = status && wizService.regolaAEToken(projectName, packageName);
            if (status) {
                for (AEPackage pack : AEPackage.getFiles()) {
                    if (pack.is()) {
                        risultato = elaboraDoc(packageName, pack, inizioFile);
                        if (risultato.isValido()) {
                            message = risultato.getValidationMessage();
                            numFiles++;
                        }
                    }
                }
            }

            if (numFiles > 0) {
                if (numFiles > 1) {
                    message = String.format("Documentazione - Controllati %d files standard nel package %s", numFiles, packageName);
                }
            }
            else {
                message = String.format("Documentazione - Nel package %s non è stato modificato nessun file", packageName);
            }
            logger.log(AETypeLog.wizard, message);
        }
    }

    /**
     * Il packageName può arrivare maiuscolo o minuscolo <br>
     * Il packageName può arrivare con degli slash <br>
     * fileName è la parte terminale di packageName dopo lo slash <br>
     * upperName è la parte terminale di packageName dopo lo slash con la prima maiuscola <br>
     * pathFileToBeWritten è il path completo con upperName, il suffisso del package ed il suffisso java <br>
     *
     * @param inizioFile per la modifica dell'header
     */
    protected AIResult elaboraDoc(String packageName, AEPackage pack, boolean inizioFile) {
        AIResult risultato = AResult.errato();
        String fileName;
        String upperName;
        String pathFileToBeWritten;
        String message;

        fileName = packageName.contains("/") ? text.levaTestoPrimaDi(packageName, FlowCost.SLASH) : packageName;
        fileName = fileName.toLowerCase();
        upperName = text.primaMaiuscola(fileName);

        AEWizCost.nameTargetPackage.setValue(fileName);
        AEWizCost.nameTargetPackageUpper.setValue(upperName);
        AEWizCost.pathTargetSingoloPackage.setValue(AEWizCost.pathTargetProjectPackages.get() + packageName + FlowCost.SLASH);
        pathFileToBeWritten = AEWizCost.pathTargetSingoloPackage.get() + upperName + pack.getSuffix() + JAVA_SUFFIX;

        if (file.isEsisteFile(pathFileToBeWritten)) {
            wizService.regolaAEToken(AEWizCost.projectCurrent.get(), fileName);
            risultato = wizService.fixDocFile(packageName, text.isValid(pack.getSuffix()) ? pack.getSuffix() : "Entity", pathFileToBeWritten, inizioFile);
        }
        else {
            message = String.format("Documentazione - Manca il file standard %s nel package %s", text.isValid(pack.getSuffix()) ? pack.getSuffix() : "Entity", packageName);
            logger.log(AETypeLog.wizard, message);
        }

        return risultato;
    }

    public void creaModuloProgetto() {
        AIResult result;
        String message = VUOTA;
        String path = VUOTA;
        String projectUpper = AEWizCost.nameTargetProject.get();
        String projectLower = AEWizCost.nameTargetProjectLower.get();
        String pathBreve;

        //--crea directory principale del modulo target (empty)
        //--crea subDirectory backend (empty)
        //--crea subDirectory application (empty) in backend
        //--crea subDirectory boot (empty) in backend
        //--crea subDirectory data (empty) in backend
        //--crea subDirectory packages (empty) in backend
        //--crea subDirectory ui (empty)
        for (AEModulo mod : AEModulo.getDirectories()) {
            path = mod.getAbsolutePath();
            pathBreve = file.findPathBreveDa(path, projectLower);
            if (pathBreve.equals(path)) {
                pathBreve = "..";
            }
            pathBreve += FlowCost.SLASH;

            if (text.isEmpty(path) || path.equals(VALORE_MANCANTE)) {
                message = String.format("Nel target %s manca il path della directory %s", projectUpper, mod.getTag());
                logger.log(AETypeLog.wizard, message);
            }
            else {
                if (file.isEsisteDirectory(path)) {
                    message = String.format("Nel target %s esisteva già la directory %s ", projectUpper, pathBreve);
                    logger.log(AETypeLog.wizard, message);
                }
                else {
                    file.creaDirectory(path);
                    message = String.format("Nel target %s creata la directory %s", projectUpper, pathBreve);
                    logger.log(AETypeLog.wizard, message);
                }
            }
        }

        //--crea files del modulo target
        //--crea file Cost
        //--crea file Boot
        //--crea file Data
        for (AEModulo mod : AEModulo.getSourceFiles()) {
            path = mod.getAbsolutePath();
            String firstDir = "algos/" + projectLower;
            pathBreve = file.findPathBreve(path, mod.getDirectory());
            if (text.isEmpty(path) || path.equals(VALORE_MANCANTE)) {
                message = String.format("Nel target %s manca il path del file %s", projectUpper, mod.getTag());
                logger.log(AETypeLog.wizard, message);
            }
            else {
                result = wizService.creaFile(mod.getCopyWiz(), mod.getSourcesName(), mod.getAbsolutePath(), firstDir);
                message = String.format("Nel target %s ", projectUpper, pathBreve) + result.getMessage();
                logger.log(AETypeLog.wizard, message);
            }
        }
    }

}
