package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.application.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.backend.wrapper.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import org.apache.commons.io.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.io.*;
import java.time.*;
import java.util.*;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 20-apr-2020
 * Time: 14:39
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WizService {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ATextService text;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AArrayService array;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ALogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ADateService date;

    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con @Autowired <br>
     * Disponibile al termine del costruttore di questa classe <br>
     */
    @Autowired
    protected AFileService file;

    /**
     * Regolazioni iniziali indipendenti dal dialogo di input <br>
     * Chiamato da Wizard.initView() <br>
     * Esegue un reset di ogni enumeration <br>
     * Elabora le varie enumeration nell'ordine (obbligatorio) <br>
     */
    public void fixVariabiliIniziali() {
        //--reset di tutte le enumeration
        reset();

        fixAEWizCost();

        //        AEModulo.fixValues(AEWizCost.pathTargetProjectModulo.get(), AEWizCost.projectCurrent.get());

        //        fixAEFlag();
        fixAEDir();
    }


    /**
     * Regolazioni iniziali indipendenti dal dialogo di input <br>
     * Chiamato da Wizard.initView() <br>
     */
    public void fixAEWizCost() {
        String pathCurrent = System.getProperty("user.dir") + FlowCost.SLASH;
        AEWizCost.pathCurrent.setValue(pathCurrent);

        String user = pathCurrent.substring(1);
        user = text.levaTestoPrimaDi(user, FlowCost.SLASH);
        user = user.substring(0, user.indexOf(FlowCost.SLASH));
        AEWizCost.nameUser.setValue(user);

        String project = file.estraeDirectoryFinaleSenzaSlash(pathCurrent);
        AEWizCost.projectCurrentLower.setValue(project.toLowerCase());
        project = text.primaMaiuscola(project);
        AEWizCost.projectCurrent.setValue(project);

        //--isBaseFlow
        String nameProject = file.estraeDirectoryFinaleSenzaSlash(pathCurrent);
        AEFlag.isBaseFlow.set(nameProject.equals(AEWizCost.nameVaadFlow14.get().toLowerCase()));
        if (AEFlag.isBaseFlow.is()) {
            AEWizCost.nameTargetProject.setValue(nameProject);
            AEWizCost.nameTargetProjectLower.setValue(nameProject.toLowerCase());
            AEWizCost.pathTargetProjectRoot.setValue(pathCurrent);
            AEWizCost.pathTargetProjectModulo.setValue(pathCurrent + AEWizCost.dirModulo.get() + project.toLowerCase(Locale.ROOT) + FlowCost.SLASH);
            AEWizCost.pathTargetProjectBoot.setValue(AEWizCost.pathTargetProjectModulo.get() + AEWizCost.dirBoot.get());
            AEWizCost.pathTargetProjectPackages.setValue(AEWizCost.pathTargetProjectModulo.get() + AEWizCost.dirPackages.get());
            AEWizCost.pathTargetProjectSources.setValue(AEWizCost.pathTargetProjectRoot.get() + AEWizCost.dirVaadFlow14WizardSources.get());
        }
        else {
            AEWizCost.nameTargetProject.setValue(nameProject);
            AEWizCost.nameTargetProjectLower.setValue(nameProject.toLowerCase());
            AEWizCost.pathTargetProjectRoot.setValue(pathCurrent);
            AEWizCost.pathTargetProjectModulo.setValue(pathCurrent + AEWizCost.dirModulo.get() + project.toLowerCase(Locale.ROOT) + FlowCost.SLASH);
            AEWizCost.pathTargetProjectBoot.setValue(AEWizCost.pathTargetProjectModulo.get() + AEWizCost.dirBoot.get());
            AEWizCost.pathTargetProjectPackages.setValue(AEWizCost.pathTargetProjectModulo.get() + AEWizCost.dirPackages.get());
            AEWizCost.pathTargetProjectSources.setValue(AEWizCost.pathTargetProjectRoot.get() + AEWizCost.dirVaadFlow14WizardSources.get());
        }

        AEWizCost.printVuote();
        AEWizCost.printInfo();
        AEWizCost.printVuote();
        AEWizCost.printInfo();
    }


    /**
     * Regolazioni iniziali indipendenti dal dialogo di input <br>
     * Chiamato da Wizard.initView() <br>
     */
    public void fixAEDir() {
        String pathCurrent;
        String projectName;

        //--regola i valori elaborabili di AEDir
        pathCurrent = System.getProperty("user.dir") + FlowCost.SLASH;
        AEDir.elaboraAll(pathCurrent);

        //--se è un progetto specifico, ne conosco il nome e lo inserisco nelle enumeration AEDir modificabili
        if (AEFlag.isBaseFlow.is()) {
            projectName = file.estraeDirectoryFinale(pathCurrent);
            if (projectName.endsWith(FlowCost.SLASH)) {
                projectName = text.levaCoda(projectName, FlowCost.SLASH);
            }

            AEDir.modificaProjectAll(projectName);
        }
        else {
            projectName = file.estraeDirectoryFinale(pathCurrent);
            if (projectName.endsWith(FlowCost.SLASH)) {
                projectName = text.levaCoda(projectName, FlowCost.SLASH);
            }

            AEDir.modificaProjectAll(projectName);
        }

    }


    /**
     * Reset delle enumeration <br>
     */
    public void reset() {
        AEToken.reset();
        AEFlag.reset();
        AECheck.reset();
    }


    /**
     * Visualizzazione iniziale di controllo <br>
     */
    public void printStart() {
        AEWizCost.printInfo();
        //        AEProgetto.printInfo();
    }


    /**
     * Visualizzazione iniziale di controllo <br>
     */
    public void printInfo(String message) {
        AEDir.printInfo(message);
        AEFlag.printInfo(message);
        AECheck.printInfo(message);
        AEPackage.printInfo(message);
    }

    /**
     * Visualizzazione finale di controllo <br>
     */
    public void printInfoCompleto(String message) {
        printInfo(message);
        AEToken.printInfo(message);
    }

    /**
     * Copia una cartella da VaadFlow al progetto <br>
     *
     * @param copyWiz   modalità di comportamento se esiste la directory di destinazione
     * @param srcPath   nome completo della directory sorgente
     * @param destPath  nome completo della directory destinazione
     * @param directory da cui iniziare il pathBreve del messaggio
     */
    public AIResult copyDir(final AECopyWiz copyWiz, final String srcPath, final String destPath, final String directory) {
        AIResult result = AResult.errato();
        String message = VUOTA;
        File srcDir = new File(srcPath);
        File destDir = new File(destPath);
        String dirPath = text.isValid(directory) ? directory : AEWizCost.projectCurrent.get().toLowerCase();
        String pathBreve = file.findPathBreveDa(destPath, dirPath);
        String type = text.setTonde(copyWiz.name());

        switch (copyWiz) {
            case dirDeletingAll:
                message = String.format("la directory %s %s non esisteva ma è stata creata.", pathBreve, type);
                if (file.isEsisteDirectory(destPath)) {
                    delete(destDir);
                    message = String.format("la directory %s %s esisteva già ed è stata cancellata e completamente sostituita.", pathBreve, type);
                }
                copy(srcDir, destDir);
                message = String.format("la directory %s %s è stata creata.", pathBreve, type);
                result = AResult.valido(message);
                break;
            case dirAddingOnly:
                message = String.format("la directory %s %s esiste già ed è stata integrata.", pathBreve, type);
                if (file.isNotEsisteDirectory(destPath)) {
                    message = String.format("la directory %s %s non esisteva ma è stata creata.", pathBreve, type);
                }
                copy(srcDir, destDir);
                result = AResult.valido(message);
                break;
            case dirSoloSeNonEsiste:
                message = String.format("la directory %s %s esiste già e non è stata modificata.", pathBreve, type);
                if (file.isNotEsisteDirectory(destPath)) {
                    copy(srcDir, destDir);
                    message = String.format("la directory %s %s non esisteva ma è stata creata.", pathBreve, type);
                }
                break;
            default:
                logger.warn("Switch - caso non definito", this.getClass(), "copyDir");
                break;
        }
        //        logger.log(AETypeLog.wizard, message);

        return result;
    }


    private boolean delete(final File destDir) {
        try {
            FileUtils.deleteDirectory(destDir);
            return true;
        } catch (Exception unErrore) {
            logger.error(unErrore, this.getClass(), "delete");
            return false;
        }
    }


    private boolean copy(final File srcDir, final File destDir) {
        try {
            FileUtils.copyDirectory(srcDir, destDir);
            return true;
        } catch (Exception unErrore) {
            logger.error(unErrore, this.getClass(), "copy");
            return false;
        }
    }

    //    /**
    //     * Copia un file <br>
    //     * <p>
    //     * Controlla che siano validi i path di riferimento <br>
    //     * Controlla che esista il path del file sorgente  <br>
    //     * Se manca il file sorgente, non fa nulla <br>
    //     * Se esiste il file di destinazione ed è AECopyFile.soloSeNonEsiste, non fa nulla <br>
    //     * Se esiste il file di destinazione ed è AECopyDir.sovrascriveSempreAncheSeEsiste, lo sovrascrive <br>
    //     * Se esiste il file di destinazione ed è AECopyFile.checkFlagSeEsiste, controlla il flag sovraScrivibile <br>
    //     * Nei messaggi di avviso, accorcia il destPath eliminando i livelli precedenti alla directory indicata <br>
    //     *
    //     * @param typeCopy modalità di comportamento se esiste il file di destinazione
    //     * @param srcPath  nome completo di suffisso del file sorgente
    //     * @param destPath nome completo di suffisso del file da creare
    //     */
    //    public void copyFile(AECopyWiz typeCopy, String srcPath, String destPath) {
    //        copyFile(typeCopy, srcPath, destPath, DIR_PROJECTS);
    //    }

    /**
     * Copia un file <br>
     * <p>
     * Controlla che siano validi i path di riferimento <br>
     * Controlla che esista il path del file sorgente  <br>
     * Se manca il file sorgente, non fa nulla <br>
     * Se esiste il file di destinazione ed è AECopyFile.soloSeNonEsiste, non fa nulla <br>
     * Se esiste il file di destinazione ed è AECopyDir.sovrascriveSempreAncheSeEsiste, lo sovrascrive <br>
     * Se esiste il file di destinazione ed è AECopyFile.checkFlagSeEsiste, controlla il flag sovraScrivibile <br>
     * Nei messaggi di avviso, accorcia il destPath eliminando i livelli precedenti alla directory indicata <br>
     *
     * @param copyWiz  modalità di comportamento se esiste il file di destinazione
     * @param srcPath  nome completo di suffisso del file sorgente
     * @param destPath nome completo di suffisso del file da creare
     * @param firstDir prima directory per troncare il path nel messaggio di avviso
     */
    public void copyFile(final AECopyWiz copyWiz, final String srcPath, final String destPath, final String firstDir) {
        String sourceTextElaborato;
        String type = text.setTonde(copyWiz.name());

        sourceTextElaborato = file.leggeFile(srcPath);
        copy(copyWiz, destPath, sourceTextElaborato, firstDir, type);
    }

    //    @Deprecated
    //    public boolean copyDirectory(AECopy typeCopy, String srcPath, String destPath) {
    //        return file.copyDirectory(typeCopy, srcPath, destPath, DIR_PROJECTS);
    //    }

    //    /**
    //     * Crea un nuovo file leggendo il testo da wizard.sources di VaadFlow14 ed elaborandolo <br>
    //     * <p>
    //     * Controlla che sia valido il path di riferimento <br>
    //     * Controlla che nella directory wizard.sources di VaadFlow14 esista il file sorgente da copiare <br>
    //     * Se manca il file sorgente, non fa nulla <br>
    //     * Se non esiste la directory di destinazione, la crea <br>
    //     * Se esiste il file di destinazione ed è AECopyFile.soloSeNonEsiste, non fa nulla <br>
    //     * Se esiste la directory di destinazione ed è AECopyDir.deletingAll, la cancella e poi la copia <br>
    //     * Se esiste la directory di destinazione ed è AECopyFile.checkFlagSeEsiste, controlla il flag sovraScrivibile <br>
    //     * Nei messaggi di avviso, accorcia il pathFileToBeWritten eliminando i primi 4 livelli (/Users/gac/Documents/IdeaProjects) <br>
    //     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
    //     * Scrive il file col path e suffisso indicati <br>
    //     *
    //     * @param typeCopy            modalità di comportamento se esiste il file di destinazione
    //     * @param nameSourceText      nome del file di testo presente nella directory wizard.sources di VaadFlow14
    //     * @param pathFileToBeWritten nome completo di suffisso del file da creare
    //     */
    //    @Deprecated
    //    public void creaFileProject(AECopyWiz typeCopy, String nameSourceText, String pathFileToBeWritten) {
    //        creaFile(typeCopy, nameSourceText, pathFileToBeWritten, DIR_PROJECTS);
    //    }

    /**
     * Crea un nuovo file leggendo il testo da wizard.sources di VaadFlow14 ed elaborandolo <br>
     * <p>
     * Controlla che sia valido il path di riferimento <br>
     * Controlla che nella directory wizard.sources di VaadFlow14 esista il file sorgente da copiare <br>
     * Se manca il file sorgente, non fa nulla <br>
     * Se non esiste la directory di destinazione, la crea <br>
     * Se esiste il file di destinazione ed è AECopyFile.soloSeNonEsiste, non fa nulla <br>
     * Se esiste la directory di destinazione ed è AECopyDir.deletingAll, la cancella e poi la copia <br>
     * Se esiste la directory di destinazione ed è AECopyFile.checkFlagSeEsiste, controlla il flag sovraScrivibile <br>
     * Nei messaggi di avviso, accorcia il pathFileToBeWritten eliminando i primi 4 livelli (/Users/gac/Documents/IdeaProjects) <br>
     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
     * Scrive il file col path e suffisso indicati <br>
     *
     * @param typeCopy            modalità di comportamento se esiste il file di destinazione
     * @param nameSourceText      nome del file di testo presente nella directory wizard.sources di VaadFlow14
     * @param pathFileToBeWritten nome completo di suffisso del file da creare
     */
    public AIResult creaFilePackage(AECopyWiz typeCopy, String nameSourceText, String pathFileToBeWritten) {
        return creaFile(typeCopy, nameSourceText, pathFileToBeWritten, FlowCost.DIR_PACKAGES);
    }

    /**
     * Crea un nuovo file leggendo il testo da wizard.sources di VaadFlow14 ed elaborandolo <br>
     * <p>
     * Controlla che sia valido il path di riferimento <br>
     * Controlla che nella directory wizard.sources di VaadFlow14 esista il file sorgente da copiare <br>
     * Se manca il file sorgente, non fa nulla <br>
     * Se non esiste la directory di destinazione, la crea <br>
     * Se esiste il file di destinazione ed è AECopyFile.soloSeNonEsiste, non fa nulla <br>
     * Se esiste la directory di destinazione ed è AECopyDir.deletingAll, la cancella e poi la copia <br>
     * Se esiste la directory di destinazione ed è AECopyFile.checkFlagSeEsiste, controlla il flag sovraScrivibile <br>
     * Nei messaggi di avviso, accorcia il pathFileToBeWritten eliminando i primi 4 livelli (/Users/gac/Documents/IdeaProjects) <br>
     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
     * Scrive il file col path e suffisso indicati <br>
     *
     * @param copyWiz             modalità di comportamento se esiste il file di destinazione
     * @param nameSourceText      nome del file di testo presente nella directory wizard.sources di VaadFlow14
     * @param pathFileToBeWritten nome completo di suffisso del file da creare
     * @param firstDir            prima directory per troncare il path nel messaggio di avviso
     */
    public AIResult creaFile(final AECopyWiz copyWiz, final String nameSourceText, final String pathFileToBeWritten, final String firstDir) {
        AIResult result;
        String message;
        String sourceTextGrezzo;
        String sourceTextElaborato;
        String fileName = nameSourceText;
        String pathSource;
        String tagToken = "@.+@";
        String type = text.setTonde(copyWiz.name());

        if (!fileName.endsWith(FlowCost.TXT_SUFFIX)) {
            fileName += FlowCost.TXT_SUFFIX;
        }

        pathSource = file.findPathBreve(AEWizCost.pathVaadFlow14WizSources.get() + fileName, AEWizCost.nameVaadFlow14.get().toLowerCase());
        if (!file.isEsisteFile(AEWizCost.pathVaadFlow14WizSources.get(), fileName)) {
            message = String.format("Non sono riuscito a trovare il file sorgente %s %s", pathSource, type);
            logger.log(AETypeLog.wizard, message);
            return AResult.errato(message);
        }

        sourceTextGrezzo = leggeFile(fileName);
        if (text.isEmpty(sourceTextGrezzo)) {
            message = String.format("Non sono riuscito ad elaborare il file sorgente %s %s", pathSource, type);
            logger.log(AETypeLog.wizard, message);
            return AResult.errato(message);
        }

        sourceTextElaborato = elaboraFileCreatoDaSource(sourceTextGrezzo);
        if (sourceTextElaborato.matches(tagToken)) {
            message = String.format("Non sono riuscito ad elaborare i tokens del file sorgente %s %s", pathSource, type);
            logger.log(AETypeLog.wizard, message);
            return AResult.errato(message);
        }

        result = copy(copyWiz, pathFileToBeWritten, sourceTextElaborato, firstDir, type);
        return result;
    }


    private AIResult copy(final AECopyWiz copyWiz, final String pathFileToBeWritten, final String sourceTextElaborato, final String firstDir, String type) {
        AIResult result = AResult.errato();
        AIResult resultCheck = AResult.errato();
        String message = VUOTA;
        boolean esisteFileDest = false;
        String dirPath = text.isValid(firstDir) ? firstDir : AEWizCost.projectCurrent.get().toLowerCase();
        String pathBreve = file.findPathBreveDa(pathFileToBeWritten, dirPath);

        esisteFileDest = file.isEsisteFile(pathFileToBeWritten);
        switch (copyWiz) {
            case fileSovrascriveSempreAncheSeEsiste:
            case sourceSovrascriveSempreAncheSeEsiste:
                if (esisteFileDest) {
                    message = String.format("il file %s %s esisteva già ma è stato riscritto", pathBreve, type);
                    result.setMessage(message);
                }
                else {
                    message = String.format("il file %s %s non esisteva ed è stato creato", pathBreve, type);
                    result.setMessage(message);
                }
                file.scriveFile(pathFileToBeWritten, sourceTextElaborato, true, firstDir);
                break;
            case fileSoloSeNonEsiste:
            case sourceSoloSeNonEsiste:
                if (esisteFileDest) {
                    message = String.format("il file %s %s esisteva già e non è stato modificato", pathBreve, type);
                    result.setMessage(message);
                }
                else {
                    file.scriveFile(pathFileToBeWritten, sourceTextElaborato, true, firstDir);
                    message = String.format("il file %s %s non esisteva ed è stato creato.", pathBreve, type);
                    result.setMessage(message);
                }
                break;
            case fileCheckFlagSeEsiste:
            case sourceCheckFlagSeEsiste:
                if (esisteFileDest) {
                    resultCheck = checkFileCanBeModified(pathFileToBeWritten, pathBreve);
                    if (resultCheck.isValido()) {
                        file.scriveFile(pathFileToBeWritten, sourceTextElaborato, true, firstDir);
                    }
                    result = resultCheck;
                }
                else {
                    result = file.scriveFile(pathFileToBeWritten, sourceTextElaborato, true, firstDir);
                    message = String.format("il file %s %s non esisteva ed è stato creato.", pathBreve, type);
                    result.setValidationMessage(message);
                }
                break;
            default:
                logger.warn("Switch - caso non definito", this.getClass(), "creaFile");
                break;
        }

        return result;
    }


    /**
     * Sostituisce l'header di un file leggendo il testo da wizard.sources di VaadFlow14 ed elaborandolo <br>
     * <p>
     * Modifica il testo esistente dal termine dei dati cronologici fino al tag @AIScript(... <br>
     * Controlla che esista il file destinazione da modificare <br>
     * Controlla che nella directory wizard.sources di VaadFlow14 esista il file sorgente da copiare <br>
     * Nei messaggi di avviso, accorcia il pathFileToBeWritten eliminando i primi 4 livelli (/Users/gac/Documents/IdeaProjects) <br>
     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
     * Modifica il file col path e suffisso indicati <br>
     *
     * @param packageName          nome della directory per il package in esame
     * @param nameSourceText       nome del file di testo presente nella directory wizard.sources di VaadFlow14
     * @param suffisso             del file da modificare
     * @param pathFileDaModificare nome completo del file da modificare
     * @param inizioFile           per la modifica dell'header
     */
    public AIResult fixDocFile(String packageName, String nameSourceText, String suffisso, String pathFileDaModificare, boolean inizioFile) {
        AIResult risultato = AResult.errato();
        String message = VUOTA;
        String tagIni = inizioFile ? "package" : "* <p>";
        String tagEnd = "@AIScript(";
        String oldHeader;
        String newHeader;
        String realText = file.leggeFile(pathFileDaModificare);
        String sourceText = leggeFile(nameSourceText);
        String path = file.findPathBreve(pathFileDaModificare, FlowCost.DIR_PACKAGES);
        String fileName = file.estraeClasseFinaleSenzaJava(pathFileDaModificare);

        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a trovare il file " + nameSourceText + " nella directory wizard.sources di VaadFlow14", this.getClass(), "fixDocFile");
            return risultato;
        }

        sourceText = elaboraFileCreatoDaSource(sourceText);
        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a elaborare i tokens del file " + path, this.getClass(), "fixDocFile");
            return risultato;
        }

        if (!file.isEsisteFile(pathFileDaModificare)) {
            logger.warn("Non esiste il file " + path, this.getClass(), "fixDocFile");
            return risultato;
        }

        if (realText.contains(tagIni) && realText.contains(tagEnd)) {
            oldHeader = realText.substring(realText.indexOf(tagIni), realText.indexOf(tagEnd));
            newHeader = sourceText.substring(sourceText.indexOf(tagIni), sourceText.indexOf(tagEnd));
            if (text.isValid(oldHeader) && text.isValid(newHeader)) {
                if (newHeader.trim().equals(oldHeader.trim())) {
                    message = String.format("Nel package %s non è stato modificato il file %s", packageName, fileName);
                    logger.log(AETypeLog.wizardDoc, message); //@todo PROVVISORIO
                    risultato = AResult.errato(message);
                }
                else {
                    realText = text.sostituisce(realText, oldHeader, newHeader);
                    risultato = file.scriveFile(pathFileDaModificare, realText, true, FlowCost.DIR_PACKAGES);
                    if (risultato.isValido()) {
                        message = String.format("Nel package %s è stato modificato il file %s", packageName, fileName);
                        risultato = AResult.valido(message);
                    }
                }
            }
            else {
                message = String.format("Nel package %s non sono riuscito a elaborare il file %s", packageName, path);
                logger.log(AETypeLog.wizardDoc, message);
            }
        }
        else {
            message = String.format("Nel package %s manca il tag @AIScript nel file %s che non è stato modificato", packageName, path);
            logger.log(AETypeLog.wizardDoc, message);
        }

        return risultato;
    }

    //    /**
    //     * Crea un nuovo file <br>
    //     * <p>
    //     * Costruisce il testo prendendolo dalla directory wizard/sources di VaadFlow14 <br>
    //     * Elabora il testo sostituendo i 'token' coi valori attuali <br>
    //     * Scrive il file col path designato <br>
    //     *
    //     * @param nomeFileSrc         nome del file presente in wizard/sources
    //     * @param pathFileToBeWritten nome completo del file da scrivere
    //     */
    //    @Deprecated
    //    public void sovraScriveNewFileCreatoDaSource(String nomeFileSrc, String pathFileToBeWritten) {
    //        String sourceText = leggeFile(nomeFileSrc);
    //
    //        if (text.isEmpty(sourceText)) {
    //            logger.warn("Non sono riuscito a leggere il file dai sorgenti di VaadFlow14", this.getClass(), "scriveNewFileCreatoDaSource");
    //            return;
    //        }
    //
    //        sourceText = elaboraFileCreatoDaSource(sourceText);
    //        if (text.isEmpty(sourceText)) {
    //            logger.warn("Non sono riuscito a elaborare il file", this.getClass(), "scriveNewFileCreatoDaSource");
    //            return;
    //        }
    //
    //        file.scriveFile(pathFileToBeWritten, sourceText, true, DIR_PROJECTS);
    //    }// end of method

    //    /**
    //     * Crea un nuovo file <br>
    //     * <p>
    //     * Costruisce il testo prendendolo dalla directory wiz.sources del VaadFlow <br>
    //     * Elabora il testo sostituendo i 'tag' coi valori attuali <br>
    //     * Scrive il file col path della directory designata <br>
    //     * Scrive il file col nome finale (potrebbe esser diverso dal nome del file presente in wiz.sources) <br>
    //     * Scrive il file col suffisso indicato <br>
    //     *
    //     * @param nomeFileSrc nome del file presente in wiz.sources
    //     * @param destPath    directory in cui costruire il file
    //     * @param suffix      nome del file destinazione che potrebbe essere diverso da .java o .text
    //     */
    //    @Deprecated
    //    public void scriveNewFileCreatoDaSource(String nomeFileSrc, String destPath, String suffix) {
    //        scriveNewFileCreatoDaSource(nomeFileSrc, destPath, nomeFileSrc, suffix);
    //    }// end of method

    //    /**
    //     * Crea un nuovo file <br>
    //     * <p>
    //     * Costruisce il testo prendendolo dalla directory wiz.sources del VaadFlow <br>
    //     * Elabora il testo sostituendo i 'tag' coi valori attuali <br>
    //     * Scrive il file col path della directory designata <br>
    //     * Scrive il file col nome finale (potrebbe esser diverso dal nome del file presente in wiz.sources) <br>
    //     * Scrive il file col suffisso indicato <br>
    //     *
    //     * @param nomeFileSrc  nome del file presente in wiz.sources
    //     * @param destPath     directory in cui costruire il file
    //     * @param nomeFileDest nome del file destinazione che potrebbe essere diverso da nomeFileSrc
    //     * @param suffix       nome del file destinazione che potrebbe essere diverso da .java o .text
    //     */
    //    @Deprecated
    //    public void scriveNewFileCreatoDaSource(String nomeFileSrc, String destPath, String nomeFileDest, String suffix) {
    //        String sourceText = leggeFile(nomeFileSrc);
    //
    //        if (text.isEmpty(sourceText)) {
    //            logger.warn("Non sono riuscito a leggere il file dai sorgenti di VaadFlow14", this.getClass(), "scriveNewFileCreatoDaSource");
    //            return;
    //        }
    //
    //        sourceText = elaboraFileCreatoDaSource(sourceText);
    //        if (text.isEmpty(sourceText)) {
    //            logger.warn("Non sono riuscito a elaborare il file", this.getClass(), "scriveNewFileCreatoDaSource");
    //            return;
    //        }
    //
    //        suffix = text.isValid(suffix) ? suffix : ".java";
    //        String pathFileToBeWritten = destPath + nomeFileDest + suffix;
    //        file.scriveFile(pathFileToBeWritten, sourceText, true);
    //    }// end of method

    //    /**
    //     * Crea un nuovo file <br>
    //     * <p>
    //     * Costruisce il testo prendendolo dalla directory wiz.sources del VaadFlow <br>
    //     * Elabora il testo sostituendo i 'tag' coi valori attuali <br>
    //     * Scrive il file col path della directory designata <br>
    //     * Scrive il file col nome finale (potrebbe esser diverso dal nome del file presente in wiz.sources) <br>
    //     * Scrive il file col suffisso indicato <br>
    //     *
    //     * @param nomeFileSrc  nome del file presente in wiz.sources
    //     * @param destPath     directory in cui costruire il file
    //     * @param nomeFileDest nome del file destinazione che potrebbe essere diverso da nomeFileSrc
    //     */
    //    @Deprecated
    //    public void scriveFileCreatoDaSource(String nomeFileSrc, String destPath, String nomeFileDest) {
    //        String suffix = ".java";
    //        String sourceText = leggeFile(nomeFileSrc);
    //        sourceText = elaboraFileCreatoDaSource(sourceText);
    //
    //        String pathFileToBeWritten = destPath + nomeFileDest + suffix;
    //        file.scriveFile(pathFileToBeWritten, sourceText, true);
    //    }


    public String leggeFile(String nomeFileTextSorgente) {
        String nomeFileTxt = nomeFileTextSorgente;

        if (!nomeFileTxt.endsWith(FlowCost.TXT_SUFFIX)) {
            nomeFileTxt += FlowCost.TXT_SUFFIX;
        }

        return file.leggeFile(AEWizCost.pathVaadFlow14WizSources.get() + nomeFileTxt);
    }


    /**
     * Elabora un nuovo file <br>
     * <p>
     * Elabora il testo sostituendo i 'tag' coi valori attuali <br>
     *
     * @param testoGrezzoDaElaborare proveniente da wiz.sources
     *
     * @return testo elaborato
     */
    protected String elaboraFileCreatoDaSource(String testoGrezzoDaElaborare) {
        String testoFinaleElaborato = testoGrezzoDaElaborare;

        for (AEToken token : AEToken.values()) {
            try {
                testoFinaleElaborato = AEToken.replace(token, testoFinaleElaborato, token.getValue());
            } catch (Exception unErrore) {
                logger.error(token.getTokenTag(), this.getClass(), "elaboraFileCreatoDaSource");
                testoFinaleElaborato = AEToken.replace(token, testoFinaleElaborato, token.getValue());
            }
        }

        return testoFinaleElaborato;
    }


    /**
     * Prepara la data attuale in forma 'text' per la sostituzione <br>
     *
     * @return testo nella forma 'LocalDate.of(2020, 10, 19)'
     */
    public String fixVersionDate() {
        String testoData = VUOTA;
        LocalDate dataAttuale = LocalDate.now();

        testoData = "LocalDate.of(";
        testoData += dataAttuale.getYear();
        testoData += FlowCost.VIRGOLA_SPAZIO;
        testoData += dataAttuale.getMonthValue();
        testoData += FlowCost.VIRGOLA_SPAZIO;
        testoData += dataAttuale.getDayOfMonth();
        testoData += ")";

        return testoData;
    }

    //    /**
    //     * Copia una cartella a livello di root da VaadFlow al progetto <br>
    //     * //     * Se è isNewProject()=true, la crea nuova o la sovrascrive se esisteva già <br>
    //     * //     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
    //     *
    //     * @param typeCopy modalità di comportamento se esiste la directory di destinazione
    //     * @param dirName  della cartella da copiare che DEVE essere presente, come srcPath, a livello di ROOT
    //     *
    //     * @return true se la directory  è stata copiata
    //     */
    //    public boolean copyDirectoryProjectRoot(AECopyWiz typeCopy, String dirName) {
    //        String srcPath = AEDir.pathVaadFlowRoot.get() + dirName;
    //        String destPath = AEDir.pathTargetRoot.get() + dirName;
    //
    //        return copyDirectoryProject(typeCopy, srcPath, destPath);
    //    }

    //    /**
    //     * Copia una cartella da VaadFlow al progetto <br>
    //     * //     * Se è isNewProject()=true, la crea nuova o la sovrascrive se esisteva già <br>
    //     * //     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
    //     *
    //     * @param typeCopy modalità di comportamento se esiste la directory di destinazione
    //     * @param srcPath  nome completo della directory sorgente
    //     * @param destPath nome completo della directory destinazione
    //     *
    //     * @return true se la directory  è stata copiata
    //     */
    //    @Deprecated
    //    public boolean copyDirectoryProject(AECopyWiz typeCopy, String srcPath, String destPath) {
    //        boolean copiata = false;
    //        int numLivelli = 4;
    //
    //        switch (typeCopy) {//@todo Funzionalità ancora da implementare
    //            case dirSoloSeNonEsiste:
    //                copiata = copyDirectory(AECopy.dirSoloSeNonEsiste, srcPath, destPath);
    //                break;
    //            case dirDeletingAll:
    //                copiata = copyDirectory(AECopy.dirDeletingAll, srcPath, destPath);
    //                break;
    //            case dirAddingOnly:
    //                copiata = copyDirectory(AECopy.dirAddingOnly, srcPath, destPath);
    //                break;
    //            default:
    //                logger.warn("Switch - caso non definito", this.getClass(), "copyDirectoryProject");
    //                break;
    //        }
    //
    //        //        if (AEFlag.isNewProject.is()) {
    //        //            file.copyDirectory(AECopyDir.deletingAll, srcPath, destPath, numLivelli);
    //        //        } else {
    //        //            if (AECheck.directory.isAbilitato()) {
    //        //                file.copyDirectory(AECopyDir.deletingAll, srcPath, destPath, numLivelli);
    //        //            } else {
    //        //                file.copyDirectory(AECopyDir.addingOnly, srcPath, destPath, numLivelli);
    //        //            }
    //        //        }
    //
    //        return copiata;
    //    }

    //    /**
    //     * Crea o modifica a seconda del flag 'flagSovrascriveFile' <br>
    //     */
    //    @Deprecated
    //    public void copyFileRootProject(String fileName) {
    //        //        String srcPath = AEDir.pathVaadFlow.get() + fileName;
    //        String srcPath = FlowCost.VUOTA;
    //        String destPath = AEDir.pathTargetRoot.get() + fileName;
    //
    //        file.copyFileDeletingAll(srcPath, destPath);
    //    }


    /**
     * Esamina un file per controllare lo stato del flag 'sovraScrivibile' (se esiste) <br>
     * Di default è uguale a true <br>
     *
     * @param pathFileToBeChecked nome completo del file da controllare
     *
     * @return true se il flag non esiste o è sovraScrivibile=true
     * .       false se il flag esiste ed è sovraScrivibile=false
     * <p>
     * .       0 non esiste il flag sovraScrivibile
     * .       1 il flag sovraScrivibile esiste ma non è completo
     * .       2 il flag sovraScrivibile esiste ed è sovrascrivibile=true
     * .       3 il flag sovraScrivibile esiste ed è sovrascrivibile=false
     * .       4 il flag sovraScrivibile esiste ma non è ne true ne false
     */
    public AIResult checkFileCanBeModified(String pathFileToBeChecked, String pathBreve) {
        AIResult result = AResult.errato();
        String message;
        int risultato = 0;
        String oldText = VUOTA;

        if (!file.isEsisteFile(pathFileToBeChecked)) {
            result = AResult.errato();
        }

        oldText = file.leggeFile(pathFileToBeChecked);
        if (text.isValid(oldText)) {
            risultato = checkFlagSovrascrivibile(oldText);
            switch (risultato) {
                case 0:
                    message = String.format("il file %s esisteva ma è stato modificato perché mancava il flag sovraScrivibile.", pathBreve);
                    result = AResult.valido(message);
                    break;
                case 1:
                    message = String.format("il file %s esiste già col flag sovraScrivibile incompleto e non accetta modifiche.", pathBreve);
                    result = AResult.errato(message);
                    break;
                case 2:
                    message = String.format("il file %s esisteva col flag sovraScrivibile=true ed è stato modificato.", pathBreve);
                    result = AResult.valido(message);
                    break;
                case 3:
                    message = String.format("il file %s esiste già col flag sovraScrivibile=false e non accetta modifiche.", pathBreve);
                    result = AResult.errato(message);
                    break;
                case 4:
                    message = String.format("il file %s esiste già col flag sovraScrivibile ne true ne false e non accetta modifiche.", pathBreve);
                    result = AResult.errato(message);
                    break;
                default:
                    logger.warn("Switch - caso non definito", this.getClass(), "checkFileCanBeModified");
                    break;
            }
        }
        else {
            result = AResult.errato("Non esiste il file");
        }

        return result;
    }


    /**
     * Controlla lo stato del flag 'sovraScrivibile' (se esiste) <br>
     *
     * @param oldFileText testo completo del file da controllare
     *
     * @return 0 non esiste il flag sovraScrivibile
     * .       1 il flag sovraScrivibile esiste ma non è completo
     * .       2 il flag sovraScrivibile esiste ed è sovrascrivibile=true
     * .       3 il flag sovraScrivibile esiste ed è sovrascrivibile=false
     * .       4 il flag sovraScrivibile esiste ma non è ne true ne false
     */
    public int checkFlagSovrascrivibile(final String oldFileText) {
        int risultato = 0;
        String tag1 = "@AIScript(sovra";
        String tag2 = "@AIScript(Sovra";
        List<String> tagsTrue = new ArrayList<>();
        List<String> tagsFalse = new ArrayList<>();
        List<String> tagsNullo = new ArrayList<>();

        if (oldFileText.contains(tag1) || oldFileText.contains(tag2)) {
            risultato = 1;
        }
        else {
            return risultato;
        }

        tagsTrue.add("@AIScript(sovrascrivibile = true)");
        tagsTrue.add("@AIScript(sovrascrivibile=true)");
        tagsTrue.add("@AIScript(sovrascrivibile= true)");
        tagsTrue.add("@AIScript(sovrascrivibile =true)");
        tagsTrue.add("@AIScript(sovraScrivibile = true)");
        tagsTrue.add("@AIScript(sovraScrivibile=true)");
        tagsTrue.add("@AIScript(sovraScrivibile= true)");
        tagsTrue.add("@AIScript(sovraScrivibile =true)");

        tagsFalse.add("@AIScript(sovrascrivibile = false)");
        tagsFalse.add("@AIScript(sovrascrivibile=false)");
        tagsFalse.add("@AIScript(sovrascrivibile= false)");
        tagsFalse.add("@AIScript(sovrascrivibile =false)");
        tagsFalse.add("@AIScript(sovraScrivibile = false)");
        tagsFalse.add("@AIScript(sovraScrivibile=false)");
        tagsFalse.add("@AIScript(sovraScrivibile= false)");
        tagsFalse.add("@AIScript(sovraScrivibile =false)");

        tagsNullo.add("@AIScript(sovrascrivibile = )");
        tagsNullo.add("@AIScript(sovrascrivibile=)");
        tagsNullo.add("@AIScript(sovrascrivibile= )");
        tagsNullo.add("@AIScript(sovrascrivibile =)");
        tagsNullo.add("@AIScript(sovraScrivibile = )");
        tagsNullo.add("@AIScript(sovraScrivibile=)");
        tagsNullo.add("@AIScript(sovraScrivibile= )");
        tagsNullo.add("@AIScript(sovraScrivibile =)");

        if (text.isContiene(oldFileText, tagsTrue)) {
            risultato = 2;
        }
        if (text.isContiene(oldFileText, tagsFalse)) {
            risultato = 3;
        }
        if (text.isContiene(oldFileText, tagsNullo)) {
            risultato = 4;
        }

        return risultato;
    }


    /**
     * Lista dei packages esistenti nel target project <br>
     * Controlla che non sia una directory di raggruppamento. Nel caso entra nelle sub-directories <br>
     * Prende il nome della directory di livello più interno <br>
     */
    public List<String> getPackages() {
        List<String> packages = new ArrayList<>();
        String path = AEWizCost.pathTargetProjectPackages.get();

        if (text.isValid(path)) {
            packages = getLastSubDirNames(path);
        }

        if (array.isEmpty(packages)) {
            logger.log(AETypeLog.wizard, "Non ci sono packages");
        }

        return packages;
    }


    /**
     * Estrae l'ultimo livello di sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<String> getLastSubDirNames(String pathDirectoryToBeScanned) {
        List<String> pathSubDirName = new ArrayList<>();
        List<File> pathSubDir = file.getSubDirectories(pathDirectoryToBeScanned);
        List<File> pathSubSub;
        List<String> pathSubSubName = new ArrayList<>();

        if (pathSubDir != null) {
            for (File subDir : pathSubDir) {
                pathSubSub = file.getSubDirectories(subDir.getAbsolutePath());
                if (pathSubSub != null && pathSubSub.size() > 0) {
                    pathSubSubName = getLastSubDirNames(subDir.getAbsolutePath());
                    for (String nome : pathSubSubName) {
                        pathSubDirName.add(subDir.getName() + SLASH + nome);
                    }
                }
                else {
                    pathSubDirName.add(subDir.getName());
                }
            }
        }

        return pathSubDirName;
    }

    /**
     * Regola alcuni valori della Enumeration EAToken che saranno usati da: <br>
     * WizElaboraNewPackage e WizElaboraUpdatePackage <br>
     */
    public boolean regolaAEToken(String projectName, String packageName) {
        boolean status = true;
        boolean usaCompany = AECheck.company.is();
        String tagEntity = "AEntity";
        String tagCompany = "AECompany";
        AEToken.reset();

        if (text.isEmpty(projectName) && text.isEmpty(packageName)) {
            logger.warn("Manca sia projectName che packageName.", this.getClass(), "regolaAEToken");
            return false;
        }
        if (AEFlag.isProject.is() && text.isEmpty(projectName)) {
            logger.warn("Stiamo modificando il progetto e manca projectName.", this.getClass(), "regolaAEToken");
            return false;
        }
        if (AEFlag.isPackage.is() && !AECheck.docFile.is() && text.isEmpty(packageName)) {
            logger.warn("Stiamo modificando un package e manca packageName.", this.getClass(), "regolaAEToken");
            return false;
        }

        if (text.isValid(packageName)) {
            AEToken.first.setValue(packageName.substring(0, 1).toUpperCase());
            AEToken.packageName.setValue(packageName.toLowerCase());
            AEToken.entity.setValue(text.primaMaiuscola(packageName));
        }
        AEToken.nameTargetProject.setValue(projectName);
        AEToken.nameTargetProjectLower.setValue(projectName.toLowerCase());
        AEToken.projectNameUpper.setValue(projectName.toUpperCase());
        AEToken.moduleNameMinuscolo.setValue(projectName.toLowerCase());
        AEToken.moduleNameMaiuscolo.setValue(text.primaMaiuscola(projectName));
        AEToken.first.setValue(projectName.substring(0, 1).toUpperCase());
        AEToken.packageName.setValue(packageName);
        AEToken.user.setValue(AEWizCost.nameUser.get());
        AEToken.today.setValue(date.getCompletaShort(LocalDate.now()));
        AEToken.todayAnno.setValue(String.valueOf(LocalDate.now().getYear()));
        AEToken.todayMese.setValue(String.valueOf(LocalDate.now().getMonthValue()));
        AEToken.todayGiorno.setValue(String.valueOf(LocalDate.now().getDayOfMonth()));
        AEToken.time.setValue(date.getOrario());
        AEToken.versionDate.setValue(fixVersion());
        AEToken.usaCompany.setValue(usaCompany ? "true" : "false");
        AEToken.superClassEntity.setValue(usaCompany ? tagCompany : tagEntity);
        AEToken.usaSecurity.setValue(AECheck.security.is() ? ")" : ", exclude = {SecurityAutoConfiguration.class}");
        AEToken.keyProperty.setValue(AEPackage.code.is() ? AEPackage.code.getFieldName().toLowerCase() : VUOTA);
        AEToken.searchProperty.setValue(AEPackage.code.is() ? AEPackage.code.getFieldName().toLowerCase() : VUOTA);
        AEToken.sortProperty.setValue(AEPackage.ordine.is() ? AEPackage.ordine.getFieldName().toLowerCase() : AEPackage.code.is() ? AEPackage.code.getFieldName().toLowerCase() : VUOTA);
        AEToken.rowIndex.setValue(AEPackage.rowIndex.is() ? "true" : "false");
        AEToken.properties.setValue(fixProperties());
        AEToken.propertyOrdineName.setValue(AEPackage.ordine.getFieldName().toLowerCase());
        AEToken.propertyOrdine.setValue(fixProperty(AEPackage.ordine));
        AEToken.propertyCodeName.setValue(AEPackage.code.getFieldName().toLowerCase());
        AEToken.propertyCode.setValue(fixProperty(AEPackage.code));
        AEToken.propertyDescrizioneName.setValue(AEPackage.description.getFieldName().toLowerCase());
        AEToken.propertyDescrizione.setValue(fixProperty(AEPackage.description));
        AEToken.propertyValidoName.setValue(AEPackage.valido.getFieldName().toLowerCase());
        AEToken.propertyValido.setValue(fixProperty(AEPackage.valido));
        AEToken.propertiesRinvio.setValue(fixPropertiesRinvio());
        AEToken.propertiesDoc.setValue(fixPropertiesDoc());
        AEToken.propertiesParams.setValue(fixPropertiesParams());
        AEToken.propertiesBuild.setValue(fixPropertiesBuild());
        AEToken.creaIfNotExist.setValue(fixCreaIfNotExist());
        AEToken.codeDoc.setValue(fixCodeDoc());
        AEToken.codeParams.setValue(fixCodeParams());
        AEToken.codeRinvio.setValue(fixCodeRinvio());
        AEToken.newEntityKeyUnica.setValue(fixNewEntityUnica());
        AEToken.toString.setValue(fixString());

        return status;
    }

    protected String fixProperty(AEPackage pack) {
        String testo = VUOTA;
        String sourceText = VUOTA;
        String tagSources = pack.getSourcesName();

        if (pack.is()) {
            sourceText = this.leggeFile(tagSources);
            testo = this.elaboraFileCreatoDaSource(sourceText);
        }

        return testo;
    }


    protected String fixProperties() {
        String testo = VUOTA;

        if (AEPackage.ordine.is()) {
            testo += AEPackage.ordine.getFieldName() + FlowCost.VIRGOLA;
        }

        if (AEPackage.code.is()) {
            testo += AEPackage.code.getFieldName() + FlowCost.VIRGOLA;
        }

        if (AEPackage.description.is()) {
            testo += AEPackage.description.getFieldName() + FlowCost.VIRGOLA;
        }

        if (AEPackage.valido.is()) {
            testo += AEPackage.valido.getFieldName() + FlowCost.VIRGOLA;
        }

        testo = text.levaCoda(testo, FlowCost.VIRGOLA);
        return text.setApici(testo).trim();
    }

    protected String fixPropertiesRinvio() {
        String testo = VUOTA;

        if (AEPackage.ordine.is()) {
            testo += "0" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AEPackage.code.is()) {
            testo += "VUOTA" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AEPackage.description.is()) {
            testo += "VUOTA" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AEPackage.valido.is()) {
            testo += "false" + FlowCost.VIRGOLA_SPAZIO;
        }

        return text.levaCoda(testo, FlowCost.VIRGOLA_SPAZIO);
    }

    protected String fixPropertiesDoc() {
        String testo = VUOTA;
        String sep = FlowCost.A_CAPO + FlowCost.TAB + FlowCost.SPAZIO;

        if (AEPackage.ordine.is()) {
            testo += String.format("* @param %s (obbligatorio, unico)" + sep, AEPackage.ordine.getFieldName());
        }

        if (AEPackage.code.is()) {
            testo += String.format("* @param %s di riferimento (obbligatorio, unico)" + sep, AEPackage.code.getFieldName());
        }

        if (AEPackage.description.is()) {
            testo += String.format("* @param %s (facoltativo, non unico)" + sep, AEPackage.description.getFieldName());
        }

        if (AEPackage.valido.is()) {
            testo += String.format("* @param %s flag (facoltativo, di default false)" + sep, AEPackage.valido.getFieldName());
        }

        return testo.trim();
    }

    protected String fixPropertiesParams() {
        String testo = VUOTA;

        if (AEPackage.ordine.is()) {
            testo += String.format("final int %s" + FlowCost.VIRGOLA_SPAZIO, AEPackage.ordine.getFieldName());
        }

        if (AEPackage.code.is()) {
            testo += String.format("final String %s" + FlowCost.VIRGOLA_SPAZIO, AEPackage.code.getFieldName());
        }

        if (AEPackage.description.is()) {
            testo += String.format("final String %s" + FlowCost.VIRGOLA_SPAZIO, AEPackage.description.getFieldName());
        }

        if (AEPackage.valido.is()) {
            testo += String.format("final boolean %s" + FlowCost.VIRGOLA_SPAZIO, AEPackage.valido.getFieldName());
        }

        testo = text.levaCoda(testo, FlowCost.VIRGOLA_SPAZIO);
        return testo;
    }

    protected String fixPropertiesBuild() {
        String testo = VUOTA;
        String sep = FlowCost.A_CAPO + FlowCost.A_CAPO + FlowCost.TAB + FlowCost.TAB + FlowCost.TAB + FlowCost.TAB;

        if (AEPackage.ordine.is()) {
            testo += String.format(".%1$s(%1$s > 0 ? %1$s : this.getNewOrdine())" + sep, AEPackage.ordine.getFieldName());
        }

        if (AEPackage.code.is()) {
            testo += String.format(".%1$s(text.isValid(%1$s) ? %s : null)" + sep, AEPackage.code.getFieldName());
        }

        if (AEPackage.description.is()) {
            testo += String.format(".%1$s(text.isValid(%1$s) ? %1$s : null)" + sep, AEPackage.description.getFieldName());
        }

        if (AEPackage.valido.is()) {
            testo += String.format(".%s(%s)" + sep, AECheck.valido.getFieldName(), AEPackage.valido.getFieldName());
        }

        return testo.trim();
    }

    protected String fixCreaIfNotExist() {
        String testo = VUOTA;
        String tagSources = "MethodCreaIfNotExist";
        String sourceText = VUOTA;

        if (AEPackage.code.is()) {
            sourceText = this.leggeFile(tagSources);
            testo = this.elaboraFileCreatoDaSource(sourceText);
        }

        return testo;
    }


    protected String fixCodeDoc() {
        String testo = VUOTA;

        if (AEPackage.code.is()) {
            testo += String.format("* @param %s di riferimento (obbligatorio, unico)", AEPackage.code.getFieldName());
        }

        return testo.trim();
    }

    protected String fixCodeParams() {
        String testo = VUOTA;

        if (AEPackage.code.is()) {
            testo += String.format("final String %s", AEPackage.code.getFieldName());
        }

        return testo.trim();
    }

    protected String fixCodeRinvio() {
        String testo = VUOTA;

        if (AEPackage.ordine.is()) {
            testo += "0" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AEPackage.code.is()) {
            testo += AEPackage.code.getFieldName() + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AEPackage.description.is()) {
            testo += "VUOTA" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AEPackage.valido.is()) {
            testo += "false" + FlowCost.VIRGOLA_SPAZIO;
        }

        return text.levaCoda(testo, FlowCost.VIRGOLA_SPAZIO);
    }

    protected String fixNewEntityUnica() {
        String testo = VUOTA;
        String tagSources = "MethodNewEntityKeyUnica";
        String sourceText = VUOTA;

        if (AEPackage.code.is() && (AEPackage.ordine.is() || AEPackage.description.is() || AEPackage.valido.is())) {
            sourceText = this.leggeFile(tagSources);
            testo = this.elaboraFileCreatoDaSource(sourceText);
        }

        return testo;
    }

    protected String fixString() {
        String toString = "VUOTA";

        if (AEPackage.code.is()) {
            toString = AEPackage.code.getFieldName();
        }
        else {
            if (AEPackage.description.is()) {
                toString = AEPackage.description.getFieldName();
            }
        }

        return toString;
    }

    protected String fixVersion() {
        String versione = VUOTA;
        String tag = "LocalDate.of(";
        String anno;
        String mese;
        String giorno;
        LocalDate localDate = LocalDate.now();

        anno = localDate.getYear() + VUOTA;
        mese = localDate.getMonth().getValue() + VUOTA;
        giorno = localDate.getDayOfMonth() + VUOTA;
        versione = tag + anno + FlowCost.VIRGOLA + mese + FlowCost.VIRGOLA + giorno + ")";

        return versione;
    }

}
