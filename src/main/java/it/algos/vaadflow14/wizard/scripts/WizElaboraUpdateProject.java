package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
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
        super.copiaDirectoryConfig();
        super.copiaDirectoryDoc();
        super.copiaDirectoryFrontend();
        super.copiaDirectoryLinks();
        super.copiaDirectorySnippets();

        this.copiaDirectoryVaadFlow();

        super.copiaDirectoryMetaInf();
        super.creaFileProperties();
        super.creaFileBanner();

        super.creaFileGit();
        super.creaFilePom();
        super.creaFileRead();
    }

}
