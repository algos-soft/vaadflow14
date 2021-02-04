package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.wizard.scripts.WizCost.DIR_VAADFLOW;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-apr-2020
 * Time: 09:55
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WizElaboraFeedbackWizard extends WizElabora {

    @Override
    public void esegue() {
        super.isNuovoProgetto = false;

        this.copiaFileWizard();
        this.copiaDirectoryEnumeration();
        this.copiaDirectoryScripts();
    }


    /**
     * File Wizard <br>
     * Ricopia il file Wizard da questo progetto a VaadFlow <br>
     * Sovrascrive completamente il file Wizard esistente su VaadFlow che viene perso <br>
     * I termini 'src' e 'dest', sono invertiti <br>
     */
    protected void copiaFileWizard() {
        String dirWizard = AEWizCost.dirRootWizard.get();
        String pathFile = dirWizard + AEWizCost.nameWizard.get();
        String srcPath = AEWizCost.pathTargetProjectRoot.get() + pathFile;
        String destPath = AEWizCost.pathVaadFlow14Root.get() + pathFile;

        if (!file.isEsisteFile(srcPath)) {
            logger.warn("Errato il path per il file Wizard locale da ricopiare", this.getClass(), "copiaFileWizard");
        }

        if (!file.isEsisteFile(destPath)) {
            logger.warn("Errato il path per il file Wizard da sostituire su VaadFlow14", this.getClass(), "copiaFileWizard");
        }

        wizService.copyFile(AECopyWiz.fileSovrascriveSempreAncheSeEsiste, srcPath, destPath, DIR_VAADFLOW);
    }


    /**
     * Directory Enumeration <br>
     * Ricopia la directory Wizard/Enumeration da questo progetto a VaadFlow <br>
     * Sovrascrive completamente la directory Wizard/Enumeration esistente su VaadFlow che viene persa <br>
     * I termini 'src' e 'dest', sono invertiti <br>
     */
    protected void copiaDirectoryEnumeration() {
        String srcPath = AEWizCost.pathTargetProjectRoot.get() + AEWizCost.dirRootWizardEnumeration.get();
        String destPath = AEWizCost.pathVaadFlow14Root.get() + AEWizCost.dirRootWizardEnumeration.get();

        if (!file.isEsisteDirectory(srcPath)) {
            logger.warn("Errato il path per la directory enum locale da ricopiare", this.getClass(), "copiaDirectoryEnumeration");
        }

        if (!file.isEsisteDirectory(destPath)) {
            logger.warn("Errato il path per la directory enum da sostituire su VaadFlow14", this.getClass(), "copiaDirectoryEnumeration");
        }

        wizService.copyDir(AECopyWiz.dirDeletingAll, srcPath, destPath, DIR_VAADFLOW);
    }


    /**
     * Directory Scripts <br>
     * Ricopia la directory Wizard/Scripts da questo progetto a VaadFlow <br>
     * Sovrascrive completamente la directory Wizard/Scripts esistente su VaadFlow che viene persa <br>
     * I termini 'src' e 'dest', sono invertiti <br>
     */
    protected void copiaDirectoryScripts() {
        String srcPath = AEWizCost.pathTargetProjectRoot.get() + AEWizCost.dirRootWizardScripts.get();
        String destPath = AEWizCost.pathVaadFlow14Root.get() + AEWizCost.dirRootWizardScripts.get();

        if (!file.isEsisteDirectory(srcPath)) {
            logger.warn("Errato il path per la directory scripts locale da ricopiare", this.getClass(), "copiaDirectoryScripts");
        }

        if (!file.isEsisteDirectory(destPath)) {
            logger.warn("Errato il path per la directory scripts da sostituire su VaadFlow14", this.getClass(), "copiaDirectoryScripts");
        }

        wizService.copyDir(AECopyWiz.dirDeletingAll, srcPath, destPath, DIR_VAADFLOW);
    }

}
