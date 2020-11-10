package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
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
//        super.copiaDirectoryConfig();
//        super.copiaDirectoryDoc();
//        super.copiaDirectoryFrontend();
//        super.copiaDirectoryLinks();
//        super.copiaDirectorySnippets();

        this.copiaDirectoryVaadFlow();
//        this.creaModuloNuovoProgetto();
//
//        super.copiaDirectoryMetaInf();
//        super.creaFileProperties();
//        super.creaFileBanner();

//        super.creaFileGit();
//        super.creaFilePom();
//        super.creaFileRead();
    }// end of method


    public void creaModuloNuovoProgetto() {
        if (AECheck.project.isAbilitato()) {

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

        if (AECheck.security.isAbilitato()) {
            testoApp = AEToken.replace(AEToken.usaSecurity, testoApp, VUOTA);
        } else {
            testoApp = AEToken.replace(AEToken.usaSecurity, testoApp, ", exclude = {SecurityAutoConfiguration.class}");
        }// end of if/else cycle

        file.scriveFile(destPath, testoApp, true);
    }// end of method


    protected void scriveFileCost() {
//        wizService.scriveNewFileCreatoDaSource(FILE_COST, pathProjectDirApplication);
    }// end of method


    public void scriveFileBoot() {
//        wizService.scriveNewFileCreatoDaSource(FILE_BOOT, pathProjectDirApplication);
    }// end of method


    public void scriveFileVers() {
//        wizService.scriveNewFileCreatoDaSource(FILE_VERS, pathProjectDirApplication);
    }// end of method


    public void scriveFileHome() {
//        wizService.scriveNewFileCreatoDaWizSource(FILE_HOME, pathProjectDirApplication);
    }// end of method


    public void creaDirectorySecurity() {
    }// end of method

}// end of class
