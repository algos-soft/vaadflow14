package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AECopyFile;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.wizard.enumeration.AEWizCost;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Locale;


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

        for (AEWizCost aeWizCost : AEWizCost.getNewUpdateProject()) {
            if (aeWizCost.isAcceso()) {
                value = aeWizCost.getValue();
                if (text.isEmpty(value)) {
                    logger.error(String.format("In AEWizCost.%s manca il valore del path", aeWizCost.name()));
                    break;
                }

                if (value.endsWith(FlowCost.SLASH)) {
                    wizService.copyDirectoryProject(aeWizCost.getCopy(), srcPath + value, destPath + value);
                }
                else {
                    wizService.creaFile(aeWizCost.getCopy(),value,destPath + value,AEWizCost.projectCurrent.getValue().toLowerCase());
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
