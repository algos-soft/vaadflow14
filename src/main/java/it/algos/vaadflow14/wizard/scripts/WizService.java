package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

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
        project = text.primaMaiuscola(project);
        AEWizCost.projectCurrent.setValue(project);

        //--isBaseFlow
        boolean isBaseFlow = false;
        String dirProject = file.estraeDirectoryFinale(pathCurrent);
        isBaseFlow = dirProject.equals(AEWizCost.dirVaadFlow14.getValue());
        if (!isBaseFlow) {
            AEWizCost.pathTargetProjectRoot.setValue(pathCurrent);
            AEWizCost.pathTargetProjectModulo.setValue(pathCurrent + AEWizCost.dirModulo.getValue() + project.toLowerCase(Locale.ROOT) + FlowCost.SLASH);
        }
        AEFlag.isBaseFlow.set(isBaseFlow);
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
    public void creaFileProject(AECopyWiz typeCopy, String nameSourceText, String pathFileToBeWritten) {
        creaFile(typeCopy, nameSourceText, pathFileToBeWritten, DIR_PROJECTS);
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
     * @param typeCopy            modalità di comportamento se esiste il file di destinazione
     * @param nameSourceText      nome del file di testo presente nella directory wizard.sources di VaadFlow14
     * @param pathFileToBeWritten nome completo di suffisso del file da creare
     */
    public void creaFilePackage(AECopyWiz typeCopy, String nameSourceText, String pathFileToBeWritten) {
        creaFile(typeCopy, nameSourceText, pathFileToBeWritten, FlowCost.DIR_PACKAGES);
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
     * @param directory           da cui iniziare il path
     */
    public void creaFile(AECopyWiz copyWiz, String nameSourceText, String pathFileToBeWritten, String directory) {
        boolean esisteFileDest = false;
        String message;
        String sourceText = leggeFile(nameSourceText);
        String path = file.findPathBreve(pathFileToBeWritten, directory);

        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a trovare il file " + nameSourceText + " nella directory wizard.sources di VaadFlow14", this.getClass(), "creaFile");
            return;
        }

        sourceText = elaboraFileCreatoDaSource(sourceText);
        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a elaborare i tokens del file " + path, this.getClass(), "creaFile");
            return;
        }

        esisteFileDest = file.isEsisteFile(pathFileToBeWritten);
        switch (copyWiz) {
            case sourceSovrascriveSempreAncheSeEsiste:
                file.scriveFile(pathFileToBeWritten, sourceText, true, directory);
                break;
            case sourceSoloSeNonEsiste:
                if (esisteFileDest) {
                    message = "Il file: " + path + " esisteva già e non è stato modificato.";
                    logger.info(message, this.getClass(), "creaFile");
                }
                else {
                    file.scriveFile(pathFileToBeWritten, sourceText, true, directory);
                    message = "Il file: " + path + " non esisteva ed è stato creato da source.";
                    logger.info(message, this.getClass(), "creaFile");
                }
                break;
            case sourceCheckFlagSeEsiste:
                if (esisteFileDest) {
                    if (checkFileCanBeModified(pathFileToBeWritten)) {
                        file.scriveFile(pathFileToBeWritten, sourceText, true, directory);
                    }
                    else {
                        message = "Il file: " + path + " esiste già col flag sovraScrivibile=false e NON accetta modifiche.";
                        logger.info(message, this.getClass(), "creaFile");
                    }
                }
                else {
                    file.scriveFile(pathFileToBeWritten, sourceText, true, directory);
                }
                break;
            default:
                logger.warn("Switch - caso non definito", this.getClass(), "creaFile");
                break;
        }
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
     * @param nameSourceText      nome del file di testo presente nella directory wizard.sources di VaadFlow14
     * @param pathFileToBeWritten nome completo di suffisso del file da creare
     * @param inizioFile          per la modifica dell'header
     */
    public void fixDocFile(String nameSourceText, String pathFileToBeWritten, boolean inizioFile) {
        String message;
        String tagIni = inizioFile ? "package" : "* <p>";
        String tagEnd = "@AIScript(";
        String oldHeader;
        String newHeader;
        String realText = file.leggeFile(pathFileToBeWritten);
        String sourceText = leggeFile(nameSourceText);
        String path = file.findPathBreve(pathFileToBeWritten, FlowCost.DIR_PACKAGES);

        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a trovare il file " + nameSourceText + " nella directory wizard.sources di VaadFlow14", this.getClass(), "fixDocFile");
            return;
        }

        sourceText = elaboraFileCreatoDaSource(sourceText);
        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a elaborare i tokens del file " + path, this.getClass(), "fixDocFile");
            return;
        }

        if (!file.isEsisteFile(pathFileToBeWritten)) {
            logger.warn("Non esiste il file " + path, this.getClass(), "fixDocFile");
            return;
        }

        if (realText.contains(tagIni) && realText.contains(tagEnd)) {
            oldHeader = realText.substring(realText.indexOf(tagIni), realText.indexOf(tagEnd));
            newHeader = sourceText.substring(sourceText.indexOf(tagIni), sourceText.indexOf(tagEnd));
            if (text.isValid(oldHeader) && text.isValid(newHeader)) {
                realText = text.sostituisce(realText, oldHeader, newHeader);
            }
            file.scriveFile(pathFileToBeWritten, realText, true, FlowCost.DIR_PACKAGES);
        }
        else {
            message = String.format("Documentazione - Manca il tag @AIScript nel file %s", path);
            logger.info(message, this.getClass(), "fixDocFile");
        }
    }

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
     * @param typeCopy modalità di comportamento se esiste il file di destinazione
     * @param srcPath  nome completo di suffisso del file sorgente
     * @param destPath nome completo di suffisso del file da creare
     */
    public void copyFile(AECopy typeCopy, String srcPath, String destPath) {
        copyFile(typeCopy, srcPath, destPath, DIR_PROJECTS);
    }

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
     * @param typeCopy modalità di comportamento se esiste il file di destinazione
     * @param srcPath  nome completo di suffisso del file sorgente
     * @param destPath nome completo di suffisso del file da creare
     * @param firstDir prima directory per troncare il path nel messaggio di avviso
     */
    public void copyFile(AECopy typeCopy, String srcPath, String destPath, String firstDir) {
        boolean esisteFileDest;
        String message;
        String path;

        switch (typeCopy) {
            case fileSovrascriveSempreAncheSeEsiste:
            case fileSoloSeNonEsiste:
                file.copyFile(typeCopy, srcPath, destPath, firstDir);
                break;
            case fileCheckFlagSeEsiste:
                path = file.findPathBreve(destPath, firstDir);
                esisteFileDest = file.isEsisteFile(destPath);
                if (esisteFileDest) {
                    if (checkFileCanBeModified(destPath)) {
                        file.copyFileDeletingAll(srcPath, destPath);
                        message = "Il file: " + path + " esisteva già ma è stato sovrascritto.";
                    }
                    else {
                        message = "Il file: " + path + " esiste già col flag sovraScrivibile=false e NON accetta modifiche.";
                    }
                }
                else {
                    file.copyFileDeletingAll(srcPath, destPath);
                    message = "Il file: " + path + " non esisteva ed è stato copiato.";
                }
                logger.info(message, this.getClass(), "copyFile");
                break;
            default:
                logger.warn("Switch - caso non definito", this.getClass(), "copyFile");
                break;
        }
    }


    /**
     * Crea un nuovo file <br>
     * <p>
     * Costruisce il testo prendendolo dalla directory wizard/sources di VaadFlow14 <br>
     * Elabora il testo sostituendo i 'token' coi valori attuali <br>
     * Scrive il file col path designato <br>
     *
     * @param nomeFileSrc         nome del file presente in wizard/sources
     * @param pathFileToBeWritten nome completo del file da scrivere
     */
    @Deprecated
    public void sovraScriveNewFileCreatoDaSource(String nomeFileSrc, String pathFileToBeWritten) {
        String sourceText = leggeFile(nomeFileSrc);

        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a leggere il file dai sorgenti di VaadFlow14", this.getClass(), "scriveNewFileCreatoDaSource");
            return;
        }

        sourceText = elaboraFileCreatoDaSource(sourceText);
        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a elaborare il file", this.getClass(), "scriveNewFileCreatoDaSource");
            return;
        }

        file.scriveFile(pathFileToBeWritten, sourceText, true, DIR_PROJECTS);
    }// end of method


    /**
     * Crea un nuovo file <br>
     * <p>
     * Costruisce il testo prendendolo dalla directory wiz.sources del VaadFlow <br>
     * Elabora il testo sostituendo i 'tag' coi valori attuali <br>
     * Scrive il file col path della directory designata <br>
     * Scrive il file col nome finale (potrebbe esser diverso dal nome del file presente in wiz.sources) <br>
     * Scrive il file col suffisso indicato <br>
     *
     * @param nomeFileSrc nome del file presente in wiz.sources
     * @param destPath    directory in cui costruire il file
     * @param suffix      nome del file destinazione che potrebbe essere diverso da .java o .text
     */
    @Deprecated
    public void scriveNewFileCreatoDaSource(String nomeFileSrc, String destPath, String suffix) {
        scriveNewFileCreatoDaSource(nomeFileSrc, destPath, nomeFileSrc, suffix);
    }// end of method


    /**
     * Crea un nuovo file <br>
     * <p>
     * Costruisce il testo prendendolo dalla directory wiz.sources del VaadFlow <br>
     * Elabora il testo sostituendo i 'tag' coi valori attuali <br>
     * Scrive il file col path della directory designata <br>
     * Scrive il file col nome finale (potrebbe esser diverso dal nome del file presente in wiz.sources) <br>
     * Scrive il file col suffisso indicato <br>
     *
     * @param nomeFileSrc  nome del file presente in wiz.sources
     * @param destPath     directory in cui costruire il file
     * @param nomeFileDest nome del file destinazione che potrebbe essere diverso da nomeFileSrc
     * @param suffix       nome del file destinazione che potrebbe essere diverso da .java o .text
     */
    @Deprecated
    public void scriveNewFileCreatoDaSource(String nomeFileSrc, String destPath, String nomeFileDest, String suffix) {
        String sourceText = leggeFile(nomeFileSrc);

        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a leggere il file dai sorgenti di VaadFlow14", this.getClass(), "scriveNewFileCreatoDaSource");
            return;
        }

        sourceText = elaboraFileCreatoDaSource(sourceText);
        if (text.isEmpty(sourceText)) {
            logger.warn("Non sono riuscito a elaborare il file", this.getClass(), "scriveNewFileCreatoDaSource");
            return;
        }

        suffix = text.isValid(suffix) ? suffix : ".java";
        String pathFileToBeWritten = destPath + nomeFileDest + suffix;
        file.scriveFile(pathFileToBeWritten, sourceText, true);
    }// end of method


    /**
     * Crea un nuovo file <br>
     * <p>
     * Costruisce il testo prendendolo dalla directory wiz.sources del VaadFlow <br>
     * Elabora il testo sostituendo i 'tag' coi valori attuali <br>
     * Scrive il file col path della directory designata <br>
     * Scrive il file col nome finale (potrebbe esser diverso dal nome del file presente in wiz.sources) <br>
     * Scrive il file col suffisso indicato <br>
     *
     * @param nomeFileSrc  nome del file presente in wiz.sources
     * @param destPath     directory in cui costruire il file
     * @param nomeFileDest nome del file destinazione che potrebbe essere diverso da nomeFileSrc
     */
    @Deprecated
    public void scriveFileCreatoDaSource(String nomeFileSrc, String destPath, String nomeFileDest) {
        String suffix = ".java";
        String sourceText = leggeFile(nomeFileSrc);
        sourceText = elaboraFileCreatoDaSource(sourceText);

        String pathFileToBeWritten = destPath + nomeFileDest + suffix;
        file.scriveFile(pathFileToBeWritten, sourceText, true);
    }


    public String leggeFile(String nomeFileTextSorgente) {
        String nomeFileTxt = nomeFileTextSorgente;

        if (!nomeFileTxt.endsWith(FlowCost.TXT_SUFFIX)) {
            nomeFileTxt += FlowCost.TXT_SUFFIX;
        }// end of if cycle

        return file.leggeFile(AEDir.pathVaadFlowSources.get() + nomeFileTxt);
    }// end of method


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

        //        testoFinaleElaborato = AEToken.replace(AEToken.projectNameUpper, testoFinaleElaborato, AEToken.projectNameUpper.getValue());
        //        testoFinaleElaborato = AEToken.replace(AEToken.moduleNameMinuscolo, testoFinaleElaborato, AEToken.moduleNameMinuscolo.getValue());
        //        testoFinaleElaborato = AEToken.replace(AEToken.moduleNameMaiuscolo, testoFinaleElaborato, AEToken.moduleNameMaiuscolo.getValue());
        //        testoFinaleElaborato = AEToken.replace(AEToken.versionDate, testoFinaleElaborato, fixVersionDate());

        return testoFinaleElaborato;
    }// end of method


    /**
     * Prepara la data attuale in forma 'text' per la sostituzione <br>
     *
     * @return testo nella forma 'LocalDate.of(2020, 10, 19)'
     */
    public String fixVersionDate() {
        String testoData = FlowCost.VUOTA;
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


    /**
     * Copia una cartella a livello di root da VaadFlow al progetto <br>
     * //     * Se è isNewProject()=true, la crea nuova o la sovrascrive se esisteva già <br>
     * //     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
     *
     * @param typeCopy modalità di comportamento se esiste la directory di destinazione
     * @param dirName  della cartella da copiare che DEVE essere presente, come srcPath, a livello di ROOT
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryProjectRoot(AECopyWiz typeCopy, String dirName) {
        String srcPath = AEDir.pathVaadFlowRoot.get() + dirName;
        String destPath = AEDir.pathTargetRoot.get() + dirName;

        return copyDirectoryProject(typeCopy, srcPath, destPath);
    }


    /**
     * Copia una cartella da VaadFlow al progetto <br>
     * //     * Se è isNewProject()=true, la crea nuova o la sovrascrive se esisteva già <br>
     * //     * Se è isUpdateProject()=true, controlla il flagDirectory del dialogo <br>
     *
     * @param typeCopy modalità di comportamento se esiste la directory di destinazione
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryProject(AECopyWiz typeCopy, String srcPath, String destPath) {
        boolean copiata = false;
        int numLivelli = 4;

        switch (typeCopy) {//@todo Funzionalità ancora da implementare
            case dirSoloSeNonEsiste:
                copiata = copyDirectory(AECopy.dirSoloSeNonEsiste, srcPath, destPath);
                break;
            case dirDeletingAll:
                copiata = copyDirectory(AECopy.dirDeletingAll, srcPath, destPath);
                break;
            case dirAddingOnly:
                copiata = copyDirectory(AECopy.dirAddingOnly, srcPath, destPath);
                break;
            default:
                logger.warn("Switch - caso non definito", this.getClass(), "copyDirectoryProject");
                break;
        }

        //        if (AEFlag.isNewProject.is()) {
        //            file.copyDirectory(AECopyDir.deletingAll, srcPath, destPath, numLivelli);
        //        } else {
        //            if (AECheck.directory.isAbilitato()) {
        //                file.copyDirectory(AECopyDir.deletingAll, srcPath, destPath, numLivelli);
        //            } else {
        //                file.copyDirectory(AECopyDir.addingOnly, srcPath, destPath, numLivelli);
        //            }
        //        }

        return copiata;
    }


    public boolean copyDirectory(AECopy typeCopy, String srcPath, String destPath) {
        return file.copyDirectory(typeCopy, srcPath, destPath, DIR_PROJECTS);
    }


    /**
     * Crea o modifica a seconda del flag 'flagSovrascriveFile' <br>
     */
    public void copyFileRootProject(String fileName) {
        //        String srcPath = AEDir.pathVaadFlow.get() + fileName;
        String srcPath = FlowCost.VUOTA;
        String destPath = AEDir.pathTargetRoot.get() + fileName;

        file.copyFileDeletingAll(srcPath, destPath);
    }


    /**
     * Esamina un file per controllare lo stato del flag 'sovraScrivibile' (se esiste) <br>
     * Di default è uguale a true <br>
     *
     * @param pathFileToBeChecked nome completo del file da controllare
     *
     * @return true se il flag non esiste o è sovraScrivibile=true
     * .       false se il flag esiste ed è sovraScrivibile=false
     */
    public boolean checkFileCanBeModified(String pathFileToBeChecked) {
        String oldText = FlowCost.VUOTA;

        if (!file.isEsisteFile(pathFileToBeChecked)) {
            return true;
        }

        oldText = file.leggeFile(pathFileToBeChecked);
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


    /**
     * Lista dei packages esistenti nel target project <br>
     */
    public List<String> getPackages() {
        List<String> packages = new ArrayList<>();
        String path = AEDir.pathTargetAllPackages.get();
        if (text.isValid(path)) {
            packages = file.getSubDirectoriesName(path);
        }

        return packages;
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
        AEToken.projectNameUpper.setValue(projectName.toUpperCase());
        AEToken.moduleNameMinuscolo.setValue(projectName.toLowerCase());
        AEToken.moduleNameMaiuscolo.setValue(text.primaMaiuscola(projectName));
        AEToken.first.setValue(projectName.substring(0, 1).toUpperCase());
        AEToken.packageName.setValue(packageName);
        AEToken.user.setValue(AEDir.nameUser.get());
        AEToken.today.setValue(date.getCompletaShort(LocalDate.now()));
        AEToken.time.setValue(date.getOrario());
        AEToken.versionDate.setValue(fixVersion());
        AEToken.usaCompany.setValue(usaCompany ? "true" : "false");
        AEToken.superClassEntity.setValue(usaCompany ? tagCompany : tagEntity);
        AEToken.usaSecurity.setValue(AECheck.security.is() ? ")" : ", exclude = {SecurityAutoConfiguration.class}");
        AEToken.keyProperty.setValue(AECheck.code.is() ? AECheck.code.getFieldName().toLowerCase() : FlowCost.VUOTA);
        AEToken.searchProperty.setValue(AECheck.code.is() ? AECheck.code.getFieldName().toLowerCase() : FlowCost.VUOTA);
        AEToken.sortProperty.setValue(AECheck.ordine.is() ? AECheck.ordine.getFieldName().toLowerCase() : AECheck.code.is() ? AECheck.code.getFieldName().toLowerCase() : FlowCost.VUOTA);
        AEToken.rowIndex.setValue(AECheck.rowIndex.is() ? "true" : "false");
        AEToken.properties.setValue(fixProperties());
        AEToken.propertyOrdineName.setValue(AECheck.ordine.getFieldName().toLowerCase());
        AEToken.propertyOrdine.setValue(fixProperty(AECheck.ordine));
        AEToken.propertyCodeName.setValue(AECheck.code.getFieldName().toLowerCase());
        AEToken.propertyCode.setValue(fixProperty(AECheck.code));
        AEToken.propertyDescrizioneName.setValue(AECheck.descrizione.getFieldName().toLowerCase());
        AEToken.propertyDescrizione.setValue(fixProperty(AECheck.descrizione));
        AEToken.propertyValidoName.setValue(AECheck.valido.getFieldName().toLowerCase());
        AEToken.propertyValido.setValue(fixProperty(AECheck.valido));
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

    protected String fixProperty(AECheck check) {
        String testo = FlowCost.VUOTA;
        String sourceText = FlowCost.VUOTA;
        String tagSources = check.getSourcesTag();

        if (check.is()) {
            sourceText = this.leggeFile(tagSources);
            testo = this.elaboraFileCreatoDaSource(sourceText);
        }

        return testo;
    }


    protected String fixProperties() {
        String testo = FlowCost.VUOTA;

        if (AECheck.ordine.is()) {
            testo += AECheck.ordine.getFieldName() + FlowCost.VIRGOLA;
        }

        if (AECheck.code.is()) {
            testo += AECheck.code.getFieldName() + FlowCost.VIRGOLA;
        }

        if (AECheck.descrizione.is()) {
            testo += AECheck.descrizione.getFieldName() + FlowCost.VIRGOLA;
        }

        if (AECheck.valido.is()) {
            testo += AECheck.valido.getFieldName() + FlowCost.VIRGOLA;
        }

        testo = text.levaCoda(testo, FlowCost.VIRGOLA);
        return text.setApici(testo).trim();
    }

    protected String fixPropertiesRinvio() {
        String testo = FlowCost.VUOTA;

        if (AECheck.ordine.is()) {
            testo += "0" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AECheck.code.is()) {
            testo += "VUOTA" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AECheck.descrizione.is()) {
            testo += "VUOTA" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AECheck.valido.is()) {
            testo += "false" + FlowCost.VIRGOLA_SPAZIO;
        }

        return text.levaCoda(testo, FlowCost.VIRGOLA_SPAZIO);
    }

    protected String fixPropertiesDoc() {
        String testo = FlowCost.VUOTA;
        String sep = FlowCost.A_CAPO + FlowCost.TAB + FlowCost.SPAZIO;

        if (AECheck.ordine.is()) {
            testo += String.format("* @param %s (obbligatorio, unico)" + sep, AECheck.ordine.getFieldName());
        }

        if (AECheck.code.is()) {
            testo += String.format("* @param %s di riferimento (obbligatorio, unico)" + sep, AECheck.code.getFieldName());
        }

        if (AECheck.descrizione.is()) {
            testo += String.format("* @param %s (facoltativo, non unico)" + sep, AECheck.descrizione.getFieldName());
        }

        if (AECheck.valido.is()) {
            testo += String.format("* @param %s flag (facoltativo, di default false)" + sep, AECheck.valido.getFieldName());
        }

        return testo.trim();
    }

    protected String fixPropertiesParams() {
        String testo = FlowCost.VUOTA;

        if (AECheck.ordine.is()) {
            testo += String.format("final int %s" + FlowCost.VIRGOLA_SPAZIO, AECheck.ordine.getFieldName());
        }

        if (AECheck.code.is()) {
            testo += String.format("final String %s" + FlowCost.VIRGOLA_SPAZIO, AECheck.code.getFieldName());
        }

        if (AECheck.descrizione.is()) {
            testo += String.format("final String %s" + FlowCost.VIRGOLA_SPAZIO, AECheck.descrizione.getFieldName());
        }

        if (AECheck.valido.is()) {
            testo += String.format("final boolean %s" + FlowCost.VIRGOLA_SPAZIO, AECheck.valido.getFieldName());
        }

        testo = text.levaCoda(testo, FlowCost.VIRGOLA_SPAZIO);
        return testo;
    }

    protected String fixPropertiesBuild() {
        String testo = FlowCost.VUOTA;
        String sep = FlowCost.A_CAPO + FlowCost.A_CAPO + FlowCost.TAB + FlowCost.TAB + FlowCost.TAB + FlowCost.TAB;

        if (AECheck.ordine.is()) {
            testo += String.format(".%1$s(%1$s > 0 ? %1$s : this.getNewOrdine())" + sep, AECheck.ordine.getFieldName());
        }

        if (AECheck.code.is()) {
            testo += String.format(".%1$s(text.isValid(%1$s) ? %s : null)" + sep, AECheck.code.getFieldName());
        }

        if (AECheck.descrizione.is()) {
            testo += String.format(".%1$s(text.isValid(%1$s) ? %1$s : null)" + sep, AECheck.descrizione.getFieldName());
        }

        if (AECheck.valido.is()) {
            testo += String.format(".%s(%s)" + sep, AECheck.valido.getFieldName(), AECheck.valido.getFieldName());
        }

        return testo.trim();
    }

    protected String fixCreaIfNotExist() {
        String testo = FlowCost.VUOTA;
        String tagSources = "MethodCreaIfNotExist";
        String sourceText = FlowCost.VUOTA;

        if (AECheck.code.is()) {
            sourceText = this.leggeFile(tagSources);
            testo = this.elaboraFileCreatoDaSource(sourceText);
        }

        return testo;
    }

    protected String fixNewEntityUnica() {
        String testo = FlowCost.VUOTA;
        String tagSources = "MethodNewEntityKeyUnica";
        String sourceText = FlowCost.VUOTA;

        if (AECheck.code.is() && (AECheck.ordine.is() || AECheck.descrizione.is() || AECheck.valido.is())) {
            sourceText = this.leggeFile(tagSources);
            testo = this.elaboraFileCreatoDaSource(sourceText);
        }

        return testo;
    }


    protected String fixCodeDoc() {
        String testo = FlowCost.VUOTA;

        if (AECheck.code.is()) {
            testo += String.format("* @param %s di riferimento (obbligatorio, unico)", AECheck.code.getFieldName());
        }

        return testo.trim();
    }

    protected String fixCodeParams() {
        String testo = FlowCost.VUOTA;

        if (AECheck.code.is()) {
            testo += String.format("final String %s", AECheck.code.getFieldName());
        }

        return testo.trim();
    }

    protected String fixCodeRinvio() {
        String testo = FlowCost.VUOTA;

        if (AECheck.ordine.is()) {
            testo += "0" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AECheck.code.is()) {
            testo += AECheck.code.getFieldName() + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AECheck.descrizione.is()) {
            testo += "VUOTA" + FlowCost.VIRGOLA_SPAZIO;
        }

        if (AECheck.valido.is()) {
            testo += "false" + FlowCost.VIRGOLA_SPAZIO;
        }

        return text.levaCoda(testo, FlowCost.VIRGOLA_SPAZIO);
    }

    protected String fixString() {
        String toString = "VUOTA";

        if (AECheck.code.is()) {
            toString = AECheck.code.getFieldName();
        }
        else {
            if (AECheck.descrizione.is()) {
                toString = AECheck.descrizione.getFieldName();
            }
        }

        return toString;
    }

    protected String fixVersion() {
        String versione = FlowCost.VUOTA;
        String tag = "LocalDate.of(";
        String anno;
        String mese;
        String giorno;
        LocalDate localDate = LocalDate.now();

        anno = localDate.getYear() + FlowCost.VUOTA;
        mese = localDate.getMonth().getValue() + FlowCost.VUOTA;
        giorno = localDate.getDayOfMonth() + FlowCost.VUOTA;
        versione = tag + anno + FlowCost.VIRGOLA + mese + FlowCost.VIRGOLA + giorno + ")";

        return versione;
    }

}
