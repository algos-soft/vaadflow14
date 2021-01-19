package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AECopyDir;
import it.algos.vaadflow14.wizard.enumeration.AECheck;
import it.algos.vaadflow14.wizard.enumeration.AEWizCost;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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

        if (AECheck.projectUpdate.is()) {
//            super.creaModuloProgetto();
        }
    }

    public void copyRoot() {
        String srcPath = AEWizCost.pathVaadFlow14Root.getValue();
        String destPath = AEWizCost.pathTargetProjectRoot.getValue();

        for (AEWizCost aeCost : AEWizCost.getNewUpdateProject()) {
            if (aeCost.isAcceso()) {
                srcPath += aeCost.getValue();
                destPath += aeCost.getValue();
                wizService.copyDirectoryProject(aeCost.getCopy(), srcPath, destPath);
            }
        }

        //
        //        this.copiaDirectoryVaadFlow();
        //
        //        this.copiaDirectoryMetaInf();
        //        this.creaFileProperties();
        //        this.creaFileBanner();
        //
        //        this.creaFileGit();
        //        this.creaFilePom();
        //        this.creaFileRead();
    }

}
