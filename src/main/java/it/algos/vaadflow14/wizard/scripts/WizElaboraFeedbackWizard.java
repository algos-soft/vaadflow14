package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AEWiz;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.wizard.scripts.WizCost.*;

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
        super.esegue();

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
        String dirWizard = DIR_ALGOS + DIR_VAADFLOW + DIR_WIZARD + FILE_WIZARD + JAVA_SUFFIX;

        String srcPath = AEWiz.pathUserDir.getValue() + dirWizard;
        if (!file.isEsisteFile(srcPath)) {
            logger.warn("Errato il path per il file Wizard locale da ricopiare", this.getClass(), "copiaFileWizard");
        }

        String destPath = AEWiz.pathVaadFlow.getValue() + dirWizard;
        if (!file.isEsisteFile(destPath)) {
            logger.warn("Errato il path per il file Wizard da sostituire su VaadFlow14", this.getClass(), "copiaFileWizard");
        }

        file.copyFileDeletingAll(srcPath, destPath);
    }


    /**
     * Directory Enumeration <br>
     * Ricopia la directory Wizard/Enumeration da questo progetto a VaadFlow <br>
     * Sovrascrive completamente la directory Wizard/Enumeration esistente su VaadFlow che viene persa <br>
     * I termini 'src' e 'dest', sono invertiti <br>
     */
    protected void copiaDirectoryEnumeration() {
        String dirEnum = DIR_ALGOS + DIR_VAADFLOW + DIR_WIZARD + "enumeration/";

        String srcPath = AEWiz.pathUserDir.getValue() + dirEnum;
        if (!file.isEsisteDirectory(srcPath)) {
            logger.warn("Errato il path per la directory enum locale da ricopiare", this.getClass(), "copiaDirectoryEnumeration");
        }

        String destPath = AEWiz.pathVaadFlow.getValue() + dirEnum;
        if (!file.isEsisteDirectory(destPath)) {
            logger.warn("Errato il path per la directory enum da sostituire su VaadFlow14", this.getClass(), "copiaDirectoryEnumeration");
        }

        file.copyDirectoryDeletingAll(srcPath, destPath);
    }


    /**
     * Directory Scripts <br>
     * Ricopia la directory Wizard/Scripts da questo progetto a VaadFlow <br>
     * Sovrascrive completamente la directory Wizard/Scripts esistente su VaadFlow che viene persa <br>
     * I termini 'src' e 'dest', sono invertiti <br>
     */
    protected void copiaDirectoryScripts() {
        String dirScripts = DIR_ALGOS + DIR_VAADFLOW + DIR_WIZARD + "scripts/";

        String srcPath = AEWiz.pathUserDir.getValue() + dirScripts;
        if (!file.isEsisteDirectory(srcPath)) {
            logger.warn("Errato il path per la directory scripts locale da ricopiare", this.getClass(), "copiaDirectoryScripts");
        }

        String destPath = AEWiz.pathVaadFlow.getValue() + dirScripts;
        if (!file.isEsisteDirectory(destPath)) {
            logger.warn("Errato il path per la directory scripts da sostituire su VaadFlow14", this.getClass(), "copiaDirectoryScripts");
        }

        file.copyDirectoryDeletingAll(srcPath, destPath);
    }

}
