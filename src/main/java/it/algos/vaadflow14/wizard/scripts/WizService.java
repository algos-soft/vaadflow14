package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.enumeration.AECopyDir;
import it.algos.vaadflow14.backend.enumeration.AECopyFile;
import it.algos.vaadflow14.backend.service.ADateService;
import it.algos.vaadflow14.backend.service.AFileService;
import it.algos.vaadflow14.backend.service.ALogService;
import it.algos.vaadflow14.backend.service.ATextService;
import it.algos.vaadflow14.wizard.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.DIR_PROJECTS;
import static it.algos.vaadflow14.wizard.scripts.WizCost.PROJECT_VAADFLOW;


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

        fixAEFlag();
        fixAEDir();
    }


    /**
     * Regolazioni iniziali indipendenti dal dialogo di input <br>
     * Chiamato da Wizard.initView() <br>
     */
    public void fixAEFlag() {
        String pathCurrent;
        String path;

        //--isBaseFlow
        pathCurrent = System.getProperty("user.dir") + SLASH;
        path = file.estraeDirectoryFinale(pathCurrent);
        AEFlag.isBaseFlow.set(path.equals(PROJECT_VAADFLOW));
    }


    /**
     * Regolazioni iniziali indipendenti dal dialogo di input <br>
     * Chiamato da Wizard.initView() <br>
     */
    public void fixAEDir() {
        String pathCurrent;
        String projectName;

        //--regola i valori elaborabili di AEDir
        pathCurrent = System.getProperty("user.dir") + SLASH;
        AEDir.elaboraAll(pathCurrent);

        //--se è un progetto specifico, ne conosco il nome e lo inserisco nelle enumeration AEDir modificabili
        if (!AEFlag.isBaseFlow.is()) {
            projectName = file.estraeDirectoryFinale(pathCurrent);
            if (projectName.endsWith(SLASH)) {
                projectName = text.levaCoda(projectName, SLASH);
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
        WizCost.printInfo();
        AEProgetto.printInfo();
    }


    /**
     * Visualizzazione iniziale di controllo <br>
     */
    public void printInfo(String message) {
        AEDir.printInfo(message);
        AEFlag.printInfo(message);
        AECheck.printInfo(message);
    }

    /**
     * Visualizzazione finale di controllo <br>
     */
    public void printInfoCompleto(String message) {
        printInfo(message);
        AEToken.printInfo(message);
    }

    public void creaFileProject(AECopyFile typeCopy, String nameSourceText, String pathFileToBeWritten) {
        creaFile(typeCopy, nameSourceText, pathFileToBeWritten, DIR_PROJECTS);
    }

    public void creaFilePackage(AECopyFile typeCopy, String nameSourceText, String pathFileToBeWritten) {
        creaFile(typeCopy, nameSourceText, pathFileToBeWritten, DIR_PACKAGES);
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
     * @param directory           da cui iniziare il path
     */
    public void creaFile(AECopyFile typeCopy, String nameSourceText, String pathFileToBeWritten, String directory) {
        boolean esisteFileDest = false;
        String message = VUOTA;
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
        switch (typeCopy) {
            case sovrascriveSempreAncheSeEsiste:
                file.scriveFile(pathFileToBeWritten, sourceText, true, directory);
                break;
            case soloSeNonEsiste:
                if (esisteFileDest) {
                    message = "Il file: " + path + " esiste già e non è stato modificato.";
                    logger.info(message, this.getClass(), "creaFile");
                }
                else {
                    file.scriveFile(pathFileToBeWritten, sourceText, true, directory);
                    message = "Il file: " + path + " non esisteva ed è stato creato da source.";
                    logger.info(message, this.getClass(), "creaFile");
                }
                break;
            case checkFlagSeEsiste:
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
     * Modifica il testo esistenza dall'inizio fino al tag @AIScript(... <br>
     * Controlla che esista il file destinazione da modificare <br>
     * Controlla che nella directory wizard.sources di VaadFlow14 esista il file sorgente da copiare <br>
     * Nei messaggi di avviso, accorcia il pathFileToBeWritten eliminando i primi 4 livelli (/Users/gac/Documents/IdeaProjects) <br>
     * Elabora il testo sostituendo i 'tokens' coi valori attuali <br>
     * Modifica il file col path e suffisso indicati <br>
     *
     * @param nameSourceText      nome del file di testo presente nella directory wizard.sources di VaadFlow14
     * @param pathFileToBeWritten nome completo di suffisso del file da creare
     */
    public void fixDocFile(String nameSourceText, String pathFileToBeWritten) {
        String tag = "@AIScript(";
        String oldHeader;
        String newHeader;
        String realText = file.leggeFile(pathFileToBeWritten);
        String sourceText = leggeFile(nameSourceText);
        String path = file.findPathBreve(pathFileToBeWritten, DIR_PACKAGES);

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

        if (realText.contains(tag)) {
            oldHeader = realText.substring(0, realText.indexOf(tag));
            newHeader = sourceText.substring(0, sourceText.indexOf(tag));
            if (text.isValid(oldHeader) && text.isValid(newHeader)) {
                realText = text.sostituisce(realText, oldHeader, newHeader);
            }
            file.scriveFile(pathFileToBeWritten, realText, true, DIR_PACKAGES);
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
    public void copyFile(AECopyFile typeCopy, String srcPath, String destPath) {
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
    public void copyFile(AECopyFile typeCopy, String srcPath, String destPath, String firstDir) {
        boolean esisteFileDest;
        String message;
        String path;

        switch (typeCopy) {
            case sovrascriveSempreAncheSeEsiste:
            case soloSeNonEsiste:
                file.copyFile(typeCopy, srcPath, destPath, firstDir);
                break;
            case checkFlagSeEsiste:
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
            testoFinaleElaborato = AEToken.replace(token, testoFinaleElaborato, token.getValue());
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
        String testoData = VUOTA;
        LocalDate dataAttuale = LocalDate.now();

        testoData = "LocalDate.of(";
        testoData += dataAttuale.getYear();
        testoData += VIRGOLA_SPAZIO;
        testoData += dataAttuale.getMonthValue();
        testoData += VIRGOLA_SPAZIO;
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
    public boolean copyDirectoryProjectRoot(AECopyDir typeCopy, String dirName) {
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
    public boolean copyDirectoryProject(AECopyDir typeCopy, String srcPath, String destPath) {
        boolean copiata = false;
        int numLivelli = 4;

        switch (typeCopy) {//@todo Funzionalità ancora da implementare
            case soloSeNonEsiste:
                copiata = copyDirectory(AECopyDir.soloSeNonEsiste, srcPath, destPath);
                break;
            case deletingAll:
                copiata = copyDirectory(AECopyDir.deletingAll, srcPath, destPath);
                break;
            case addingOnly:
                copiata = copyDirectory(AECopyDir.addingOnly, srcPath, destPath);
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


    public boolean copyDirectory(AECopyDir typeCopy, String srcPath, String destPath) {
        return file.copyDirectory(typeCopy, srcPath, destPath, DIR_PROJECTS);
    }


    /**
     * Crea o modifica a seconda del flag 'flagSovrascriveFile' <br>
     */
    public void copyFileRootProject(String fileName) {
        //        String srcPath = AEDir.pathVaadFlow.get() + fileName;
        String srcPath = VUOTA;
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
        String oldText = VUOTA;

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

        if (!AEFlag.isPackage.is() && !AEFlag.isProject.is()) {
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

        AEToken.nameTargetProject.setValue(projectName);
        AEToken.projectNameUpper.setValue(projectName.toUpperCase());
        AEToken.moduleNameMinuscolo.setValue(projectName.toLowerCase());
        AEToken.moduleNameMaiuscolo.setValue(text.primaMaiuscola(projectName));
        AEToken.first.setValue(projectName.substring(0, 1).toUpperCase());
        AEToken.user.setValue(AEDir.nameUser.get());
        AEToken.today.setValue(date.getCompletaShort(LocalDate.now()));
        AEToken.time.setValue(date.getOrario());
        AEToken.versionDate.setValue(fixVersion());
        AEToken.usaCompany.setValue(usaCompany ? "true" : "false");
        AEToken.superClassEntity.setValue(usaCompany ? tagCompany : tagEntity);
        AEToken.usaSecurity.setValue(AECheck.security.is() ? ")" : ", exclude = {SecurityAutoConfiguration.class}");
        AEToken.keyProperty.setValue(AECheck.code.is() ? AECheck.code.getField() : VUOTA);
        AEToken.searchProperty.setValue(AECheck.code.is() ? AECheck.code.getField() : VUOTA);
        AEToken.sortProperty.setValue(AECheck.ordine.is() ? AECheck.ordine.getField() : VUOTA);
        AEToken.rowIndex.setValue(AECheck.rowIndex.is() ? "true" : "false");
        AEToken.properties.setValue(fixProperties());
        AEToken.propertyOrdine.setValue(fixProperty(AECheck.ordine));
        AEToken.propertyCode.setValue(fixProperty(AECheck.code));
        AEToken.propertyDescrizione.setValue(fixProperty(AECheck.descrizione));
        AEToken.toString.setValue(fixString());
        if (text.isValid(packageName)) {
            AEToken.first.setValue(packageName.substring(0, 1).toUpperCase());
            AEToken.packageName.setValue(packageName.toLowerCase());
            AEToken.entity.setValue(text.primaMaiuscola(packageName));
        }

        return status;
    }

    protected String fixProperty(AECheck check) {
        String testo = VUOTA;
        String sourceText = VUOTA;
        String tagSources = check.getSourcesTag();

        if (check.is()) {
            sourceText = this.leggeFile(tagSources);
            testo = this.elaboraFileCreatoDaSource(sourceText);
        }

        return testo;
    }


    protected String fixProperties() {
        String testo = VUOTA;

        if (AECheck.ordine.is()) {
            testo += AECheck.ordine.getField() + VIRGOLA;
        }

        if (AECheck.code.is()) {
            testo += AECheck.code.getField() + VIRGOLA;
        }

        if (AECheck.descrizione.is()) {
            testo += AECheck.descrizione.getField() + VIRGOLA;
        }

        testo = text.levaCoda(testo, VIRGOLA);
        return text.setApici(testo).trim();
    }

    protected String fixString() {
        String toString = "VUOTA";

        if (AECheck.code.is()) {
            toString = "code";
        }
        else {
            if (AECheck.descrizione.is()) {
                toString = "descrizione";
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
        versione = tag + anno + VIRGOLA + mese + VIRGOLA + giorno + ")";

        return versione;
    }

}
