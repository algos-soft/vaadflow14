package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AECopyFile;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEDir;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

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
        super.copiaDirectoryConfig();
        super.copiaDirectoryDoc();
        super.copiaDirectoryFrontend();
        super.copiaDirectoryLinks();
        super.copiaDirectorySnippets();

        this.copiaDirectoryVaadFlow();
        this.creaModuloNuovoProgetto();

        super.copiaDirectoryMetaInf();
        super.creaFileProperties();
        super.creaFileBanner();

        super.creaFileGit();
        super.creaFilePom();
        super.creaFileRead();
    }


    public void creaModuloNuovoProgetto() {
        if (AECheck.project.is()) {

            //--crea directory principale del modulo target (empty)
            file.creaDirectory(AEDir.pathTargetModulo.get());

            //--classe principale dell'applicazione col metodo 'main'
            creaFileApplicationMainClass();

            //--crea subDirectory backend (empty)
            file.creaDirectory(AEDir.pathTargetModulo.get() + DIR_BACKEND);

            //--crea subDirectory application (empty) in backend
            file.creaDirectory(AEDir.pathTargetApplication.get());

            //--crea subDirectory boot (empty) in backend
            file.creaDirectory(AEDir.pathTargetBoot.get());

            //--crea subDirectory packages (empty) in backend
            file.creaDirectory(AEDir.pathTargetModulo.get() + DIR_BACKEND + DIR_PACKAGES);

            //--crea subDirectory ui (empty)
            file.creaDirectory(AEDir.pathTargetModulo.get() + DIR_UI);

            //--crea contenuto della directory Application
            creaFileCost();

            //--crea contenuto della directory boot
            creaFileBoot();

            //            creaDirectorySecurity();
        }
    }


    /**
     * Crea il file principale con la MainClass <br>
     */
    public void creaFileApplicationMainClass() {
        String nameSourceText = APP_NAME;
        String destPathSuffix = AEDir.pathTargetModulo.get();
        destPathSuffix += AEDir.nameTargetProjectUpper.get();
        destPathSuffix += "Application";
        destPathSuffix += JAVA_SUFFIX;

        wizService.creaFile(AECopyFile.sovrascriveSempreAncheSeEsiste, nameSourceText, destPathSuffix);
    }


    protected void creaFileCost() {
        wizService.creaFile(AECopyFile.sovrascriveSempreAncheSeEsiste, FILE_COST, AEDir.fileTargetCost.get());
    }

    public void creaFileBoot() {
        wizService.creaFile(AECopyFile.sovrascriveSempreAncheSeEsiste, FILE_BOOT, AEDir.fileTargetBoot.get());
    }




    public void scriveFileVers() {
        //        wizService.scriveNewFileCreatoDaSource(FILE_VERS, pathProjectDirApplication);
    }


    public void scriveFileHome() {
        //        wizService.scriveNewFileCreatoDaWizSource(FILE_HOME, pathProjectDirApplication);
    }


    public void creaDirectorySecurity() {
    }

}
