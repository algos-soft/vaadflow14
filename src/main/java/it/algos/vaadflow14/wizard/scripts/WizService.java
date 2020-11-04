package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.service.AFileService;
import it.algos.vaadflow14.backend.service.ATextService;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import it.algos.vaadflow14.wizard.enumeration.AEWiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.time.LocalDate;

import static it.algos.vaadflow14.backend.application.FlowCost.VIRGOLA_SPAZIO;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
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
    public void regolaVariabili(boolean isNuovoProgetto) {
        AEWiz.reset();

        AEWiz.pathUserDir.setValue(System.getProperty("user.dir") + SLASH);
        AEWiz.pathVaadFlow.setValue(PATH_VAADFLOW_DIR_STANDARD);

        //        if (isNuovoProgetto && !pathVaadFlow.equals(pathUserDir)) {
        //                        logger.error("Attenzione. La directory di VaadFlow è cambiata", WizDialog.class, "regolaVariabili");
        //        }// end of if/else cycle

        //valido SOLO per new project
        if (isNuovoProgetto) {
            AEWiz.pathIdeaProjects.setValue(file.levaDirectoryFinale(AEWiz.pathVaadFlow.getValue()));
            AEWiz.pathIdeaProjects.setValue(file.levaDirectoryFinale(AEWiz.pathIdeaProjects.getValue()));
            if (!AEWiz.pathIdeaProjects.getValue().equals(PATH_PROJECTS_DIR_STANDARD)) {
                //                logger.error("Attenzione. La directory dei Projects è cambiata", WizDialog.class, "regolaVariabili");
            }
        } else {
            File unaDirectory = new File(AEWiz.pathUserDir.getValue());
            AEWiz.nameTargetProject.setValue(unaDirectory.getName());
            AEWiz.pathTargetProject.setValue(AEWiz.pathUserDir.getValue());
        }

        if (false) {
            File unaDirectory = new File(AEWiz.pathUserDir.getValue());
            AEWiz.nameTargetProject.setValue(unaDirectory.getName());
            AEWiz.pathTargetProject.setValue(AEWiz.pathUserDir.getValue());
        }

        if (FLAG_DEBUG_WIZ) {
            System.out.println("********************");
            System.out.println("Ingresso del dialogo");
            System.out.println("********************");
            System.out.println("Directory di esecuzione: pathUserDir=" + AEWiz.pathUserDir.getValue());
            System.out.println("Directory VaadFlow: pathVaadFlow=" + AEWiz.pathVaadFlow.getValue());
            if (isNuovoProgetto) {
                System.out.println("Directory dei nuovi progetti: pathIdeaProjects=" + AEWiz.pathIdeaProjects.getValue());
            }// end of if cycle
            //            System.out.println("Sorgenti VaadFlow: pathSources=" + AEWiz.pathSources.getValue());
            System.out.println("");
        }
    }


    /**
     * Crea un nuovo file java <br>
     * <p>
     * Costruisce il testo prendendolo dalla directory wiz.sources del VaadFlow <br>
     * Elabora il testo sostituendo i 'tag' coi valori attuali <br>
     * Scrive il file col path della directory designata <br>
     * Scrive il file col nome finale uguale al nome sorgente col prefisso moduleNameMaiuscolo <br>
     * Scrive il file col suffisso standard .java <br>
     *
     * @param nomeFileSrc nome del file presente in wiz.sources
     * @param destPath    directory in cui costruire il file
     */
    public void scriveNewFileCreatoDaSource(String nomeFileSrc, String destPath) {
        scriveNewFileCreatoDaSource(nomeFileSrc, destPath, AEToken.moduleNameMaiuscolo.getValue() + nomeFileSrc, JAVA_SUFFIX);
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
    public void scriveNewFileCreatoDaSource(String nomeFileSrc, String destPath, String nomeFileDest, String suffix) {
        String sourceText = leggeFile(nomeFileSrc);
        sourceText = elaboraFileCreatoDaSource(sourceText);

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

        if (!nomeFileTxt.endsWith(TXT_SUFFIX)) {
            nomeFileTxt += TXT_SUFFIX;
        }// end of if cycle

        return file.leggeFile(AEToken.pathVaadFlowWizTxtSources.getValue() + nomeFileTxt);
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
     * Sovrascrive o aggiunge a seconda del flag 'flagSovrascriveDirectory' <br>
     */
    public void copyCartellaRootProject(String dirName) {
        String srcPath = AEWiz.pathVaadFlow.getValue() + dirName;
        String destPath = AEWiz.pathTargetProject.getValue() + dirName;

        if (AEWiz.flagDirectory.isAbilitato()) {
            file.copyDirectoryDeletingAll(srcPath, destPath);
        } else {
            file.copyDirectoryAddingOnly(srcPath, destPath);
        }
    }


    /**
     * Crea o modifica a seconda del flag 'flagSovrascriveFile' <br>
     */
    public void copyFileRootProject(String fileName) {
        String srcPath = AEWiz.pathVaadFlow.getValue() + fileName;
        String destPath = AEWiz.pathTargetProject.getValue() + fileName;

        file.copyFileDeletingAll(srcPath, destPath);
    }

}
