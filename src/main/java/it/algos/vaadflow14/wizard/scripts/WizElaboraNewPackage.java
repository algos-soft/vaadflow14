package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AEDir;
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
public class WizElaboraNewPackage extends WizElabora {


    private String packageName;

    private String pathPackage;

    private String fileName;

    private String suffisso;

    private String pathSource;

    private String pathDest;

    private String testoFile;

    private boolean sovrascrive = false;

    /**
     * Per ogni file serve:
     * - nome del package
     * - nome del file: suffisso
     * - testo file recuperato dalle sources
     * - path di destinazione
     */


    /**
     * Creazione completa del package
     * Crea una directory
     * Crea i files previsti nella enumeration
     */
    @Override
    public void esegue() {
        if (creaDirectory()) {
            this.creaFileEntity();
            this.fixBoot();
        }
    }




    protected boolean creaDirectory() {
        if (file.isEsisteDirectory(AEDir.pathTargetPackages.get()) ) {
            logger.info("La directory " + AEDir.nameTargetPackage.get() + " esiste già e non può essere sovrascritta",this.getClass(),"creaDirectory");
            return false;
        } else {
            file.creaDirectory(AEDir.pathTargetPackages.get());
            logger.info("La directory per il package " + AEDir.nameTargetPackageUpper.get() + " è stato creata",this.getClass(),"creaDirectory");
            return true;
        }
    }



    protected void creaFileBase(String pathDest, String testo) {
//        if (!file.scriveNewFile(pathDest, testo)) {
//            if (sovrascrive) {
//                file.sovraScriveFile(pathDest, testo);
//            }
//        }
    }

}
