package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-apr-2020
 * Time: 09:55
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WizElaboraUpdateProject extends WizElabora {

    @Override
    public void esegue() {
        this.copyRoot();

        //        if (AECheck.projectUpdate.is()) {
        ////            super.creaModuloProgetto();
        //        }
    }

    public void copyRoot() {
        String srcPath = AEWizCost.pathVaadFlow14Root.getValue();
        String destPath = AEWizCost.pathTargetProjectRoot.getValue();
        String value;
        AECopyWiz copyWiz;
        String sourcesName;

        for (AEWizCost aeWizCost : AEWizCost.getNewUpdateProject()) {
            if (aeWizCost.isAcceso()) {
                copyWiz = aeWizCost.getCopyWiz();
                value = aeWizCost.getValue();
                sourcesName = aeWizCost.getSourcesName();

                if (text.isEmpty(value)) {
                    logger.error(String.format("In AEWizCost.%s manca il valore del path", aeWizCost.name()));
                    break;
                }

                switch (copyWiz) {
                    case dirDeletingAll:
                        wizService.copyDirectoryProject(aeWizCost.getCopyWiz(), srcPath + value, destPath + value);
                        break;
                    case dirAddingOnly:
                        wizService.copyDirectoryProject(aeWizCost.getCopyWiz(), srcPath + value, destPath + value);
                        break;
                    case dirSoloSeNonEsiste:
                        wizService.copyDirectoryProject(aeWizCost.getCopyWiz(), srcPath + value, destPath + value);
                        break;
                    case fileSovrascriveSempreAncheSeEsiste:
                        wizService.creaFile(aeWizCost.getCopyWiz(), value, destPath + value, AEWizCost.projectCurrent.getValue().toLowerCase());
                        break;
                    case fileSoloSeNonEsiste:
                        wizService.creaFile(aeWizCost.getCopyWiz(), value, destPath + value, AEWizCost.projectCurrent.getValue().toLowerCase());
                        break;
                    case fileCheckFlagSeEsiste:
                        wizService.creaFile(aeWizCost.getCopyWiz(), value, destPath + value, AEWizCost.projectCurrent.getValue().toLowerCase());
                        break;
                    case sourceSovrascriveSempreAncheSeEsiste:
                        wizService.creaFile(aeWizCost.getCopyWiz(), value, destPath + value, AEWizCost.projectCurrent.getValue().toLowerCase());
                        break;
                    case sourceSoloSeNonEsiste:
                        wizService.creaFile(aeWizCost.getCopyWiz(), value, destPath + value, AEWizCost.projectCurrent.getValue().toLowerCase());
                        break;
                    case sourceCheckFlagSeEsiste:
                        if (text.isValid(sourcesName)) {
                            wizService.creaFile(aeWizCost.getCopyWiz(), sourcesName, destPath + value, AEWizCost.projectCurrent.getValue().toLowerCase());
                        }
                        break;
                    default:
                        logger.warn("Switch - caso non definito", this.getClass(), "copyRoot");
                        break;
                }

                if (value.endsWith(FlowCost.SLASH)) {
                    wizService.copyDirectoryProject(aeWizCost.getCopyWiz(), srcPath + value, destPath + value);
                }
                else {
                }
            }
        }

        //
        //        this.copiaDirectoryVaadFlow();
        //
        //        this.copiaDirectoryMetaInf();
        //        this.creaFileProperties();
        //        this.creaFileBanner();
        //
    }

}
