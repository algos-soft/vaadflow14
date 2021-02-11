package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;


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
        super.esegue();
        super.creaModuloProgetto();
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
