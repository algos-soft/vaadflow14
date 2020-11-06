package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.service.AFileService;
import it.algos.vaadflow14.backend.service.ALogService;
import it.algos.vaadflow14.backend.service.ATextService;
import it.algos.vaadflow14.wizard.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;

import static it.algos.vaadflow14.backend.application.FlowCost.SLASH;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;


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
     * Istanza unica di una classe (@Scope = 'singleton') di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con @Autowired <br>
     * Disponibile al termine del costruttore di questa classe <br>
     */
    @Autowired
    protected AFileService file;


    /**
     * Regolazioni iniziali indipendenti dal dialogo di input <br>
     * Chiamato da Wizard.initView() <br>
     */
    public void regolaVariabiliIniziali(boolean isNuovoProgetto) {
        String sepA = SEP;
        String sepB = UGUALE;
        String pathRoot;
        String pathFlow;
        String path;
        boolean status;

        //--reset di tutte le enumeration
        reset();

        //--pathCurrent
        AEDir.pathCurrent.setValue(System.getProperty("user.dir") + SLASH);

        //--pathRoot
        pathRoot = AEDir.pathCurrent.get();
        pathRoot = file.levaDirectoryFinale(pathRoot);
        pathRoot = file.levaDirectoryFinale(pathRoot);
        pathRoot = file.levaDirectoryFinale(pathRoot);
        AEDir.pathRoot.setValue(pathRoot);

        //--pathVaadFlow
        pathFlow = AEDir.pathRoot.get();
        pathFlow += VAADFLOW_DIR_STANDARD;
        AEDir.pathVaadFlow.setValue(pathFlow);

        //--pathIdeaProjects
        path = PATH_VAADFLOW_DIR_STANDARD;
        path = file.levaDirectoryFinale(path);
        path = file.levaDirectoryFinale(path);
        AEDir.pathIdeaProjects.setValue(path);

        //--pathVaadFlowSources
        path = AEDir.pathVaadFlow.get();
        path += DIR_VAADFLOW_SOURCES;
        AEDir.pathVaadFlowSources.setValue(path);

        //--modifica di un path standard
        if (AEDir.pathVaadFlow.isNotStandard()) {
            logger.warn("Modificato il path di VaadFlow14");
        }

        //--modifica di un path standard
        if (AEDir.pathIdeaProjects.isNotStandard()) {
            logger.warn("Modificato il path dei nuovi programmi");
        }

        //--isBaseFlow
        status = AEDir.pathCurrent.get().equals(pathFlow);
        AEFlag.isBaseFlow.set(status);
        //        if (isNuovoProgetto && !pathVaadFlow.equals(pathUserDir)) {
        //                        logger.error("Attenzione. La directory di VaadFlow è cambiata", WizDialog.class, "regolaVariabili");
        //        }// end of if/else cycle

        //valido SOLO per new project
        if (isNuovoProgetto) {
            //            AEWiz.pathIdeaProjects.setValue(file.levaDirectoryFinale(AEWiz.pathVaadFlow.getValue()));
            //            AEWiz.pathIdeaProjects.setValue(file.levaDirectoryFinale(AEWiz.pathIdeaProjects.getValue()));
            //            if (!AEWiz.pathIdeaProjects.getValue().equals(PATH_PROJECTS_DIR_STANDARD)) {
            //                //                logger.error("Attenzione. La directory dei Projects è cambiata", WizDialog.class, "regolaVariabili");
            //            }
        } else {
            //            File unaDirectory = new File(AEWiz.pathCurrent.getValue());
            //            AEWiz.nameTargetProject.setValue(unaDirectory.getName());
            //            AEWiz.pathTargetProject.setValue(AEWiz.pathCurrent.getValue());
        }

        if (false) {
            //            File unaDirectory = new File(AEWiz.pathCurrent.getValue());
            //            AEWiz.nameTargetProject.setValue(unaDirectory.getName());
            //            AEWiz.pathTargetProject.setValue(AEWiz.pathCurrent.getValue());
        }

        //        if (FLAG_DEBUG_WIZ) {
        //            System.out.println("********************");
        //            System.out.println("Creazione della view");
        //            System.out.println("********************");
        //
        //            System.out.println("AEWiz");
        //            System.out.println("********************");
        //            for (AEDir aeDir : AEDir.values()) {
        //                System.out.println(aeDir.name() + sepA + aeDir.getDescrizione() + sepB + aeDir.getValue());
        //            }
        //
        //            System.out.println(VUOTA);
        //            System.out.println("AEFlag");
        //            System.out.println("********************");
        //            for (AEFlag flag : AEFlag.values()) {
        //                System.out.println(flag.name() + sepB + flag.is());
        //            }
        //        }
    }


    /**
     * Reset delle enumeration <br>
     */
    public void reset() {
        AEDir.reset();
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
        AEToken.printInfo(message);
        AEFlag.printInfo(message);
        AECheck.printInfo(message);
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
     * @param nomeFileSrc nome del file presente in wiz.sources
     * @param destPath    directory in cui costruire il file
     * @param suffix      nome del file destinazione che potrebbe essere diverso da .java o .text
     */
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
     * Se è isNewProject()=true, sovrascrive sempre <br>
     * Se è isUpdateProject()=true, controlla il flagDirectory <br>
     */
    public void copyCartellaRootProject(String dirName) {
        String srcPath = AEDir.pathVaadFlow.get() + dirName;
        String destPath = AEDir.pathTargetProject.get() + dirName;

        if (AEFlag.isNewProject.is()) {
            file.copyDirectoryDeletingAll(srcPath, destPath);
        } else {
            if (AECheck.directory.isAbilitato()) {
                file.copyDirectoryDeletingAll(srcPath, destPath);
            } else {
                file.copyDirectoryAddingOnly(srcPath, destPath);
            }
        }
    }


    /**
     * Crea o modifica a seconda del flag 'flagSovrascriveFile' <br>
     */
    public void copyFileRootProject(String fileName) {
        String srcPath = AEDir.pathVaadFlow.get() + fileName;
        String destPath = AEDir.pathTargetProject.get() + fileName;

        file.copyFileDeletingAll(srcPath, destPath);
    }

}
