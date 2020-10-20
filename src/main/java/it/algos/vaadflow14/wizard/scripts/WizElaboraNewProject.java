package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import it.algos.vaadflow14.wizard.enumeration.AEWiz;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:31
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WizElaboraNewProject extends WizElabora {


    @Override
    public void esegue() {
        super.isNuovoProgetto = true;
        super.esegue();

        super.copiaDirectoryDoc();
        super.copiaDirectoryLinks();
        super.copiaDirectorySnippets();

        this.copiaCartellaVaadFlow();
        this.creaModuloNuovoProgetto();

        super.copiaFrontend();
        super.copiaMetaInf();
        super.scriveFileProperties();
        super.scriveFileBanner();

        super.scriveFileGit();
        super.scriveFilePom();
        super.scriveFileRead();
    }// end of method


    public void creaModuloNuovoProgetto() {
        if (AEWiz.flagProject.isAbilitato()) {

            //--creaDirectoryProjectModulo
            file.creaDirectory(pathProjectModulo);

            //--classe principale dell'applicazione col metodo 'main'
            creaFileApplicationMainClass();

            //--creaDirectoryBackend
            file.creaDirectory(pathProjectDirApplication);

            //--creaDirectoryApplication all'interno di backend
            file.creaDirectory(pathProjectDirApplication);

            //--creaDirectoryPackages (empty) all'interno di backend
            file.creaDirectory(pathProjectDirApplication);

            //--creaDirectoryUi (empty)
            file.creaDirectory(pathProjectDirPackages);

            //--crea contenuto della directory Application
            scriveFileCost();
            scriveFileBoot();

            creaDirectorySecurity();
        }
    }


    public void creaFileApplicationMainClass() {
        String mainApp = newProjectName + text.primaMaiuscola(APP_NAME);
        mainApp = text.primaMaiuscola(mainApp);
        String destPath = pathProjectModulo + mainApp + JAVA_SUFFIX;
        String testoApp = leggeFile(APP_NAME + TXT_SUFFIX);

        testoApp = AEToken.replace(AEToken.moduleNameMinuscolo, testoApp, newProjectName);
        testoApp = AEToken.replace(AEToken.moduleNameMaiuscolo, testoApp, text.primaMaiuscola(newProjectName));

        if (AEWiz.flagSecurity.isAbilitato()) {
            testoApp = AEToken.replace(AEToken.usaSecurity, testoApp, VUOTA);
        } else {
            testoApp = AEToken.replace(AEToken.usaSecurity, testoApp, ", exclude = {SecurityAutoConfiguration.class}");
        }// end of if/else cycle

        file.scriveFile(destPath, testoApp, true);
    }// end of method


    protected void scriveFileCost() {
        wizService.scriveNewFileCreatoDaWizSource(FILE_COST, pathProjectDirApplication);
    }// end of method


    public void scriveFileBoot() {
        wizService.scriveNewFileCreatoDaWizSource(FILE_BOOT, pathProjectDirApplication);
    }// end of method


    public void scriveFileVers() {
        wizService.scriveNewFileCreatoDaWizSource(FILE_VERS, pathProjectDirApplication);
    }// end of method


    public void scriveFileHome() {
//        wizService.scriveNewFileCreatoDaWizSource(FILE_HOME, pathProjectDirApplication);
    }// end of method


    public void creaDirectorySecurity() {
    }// end of method

}// end of class
