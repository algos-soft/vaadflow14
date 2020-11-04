package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import it.algos.vaadflow14.wizard.enumeration.AEWiz;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.SLASH;

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
        super.isNuovoProgetto = false;
        super.esegue();

        this.packageName = AEToken.packageName.getValue();
        this.pathPackage = pathProjectDirPackages + packageName + SLASH;
        this.fileName = text.primaMaiuscola(packageName);
        this.sovrascrive = AEWiz.flagSovrascrivePackage.isAbilitato();

        if (creaDirectory()) {
            this.creaFileEntity();
        }
    }


    /**
     * Regolazioni iniziali <br>
     */
    protected void regolazioniIniziali() {
        super.regolazioniIniziali();

        //        AEToken.nameTargetProject.setValue("Pippo");
        //        AEToken.pathTargetProject.setValue("");
        //        AEToken.projectNameUpper.setValue(newProjectNameUpper);
//        AEToken.moduleNameMinuscolo.setValue(packageName);
//        AEToken.moduleNameMaiuscolo.setValue(text.primaMaiuscola(packageName));
//        //        AEToken.first.setValue("");
//        //        AEToken.pathVaadFlowWizTxtSources.setValue("");
//
//        AEToken.packageName.setValue(packageName);

        AEToken.projectCost.setValue("");
        AEToken.user.setValue("Gac");
        AEToken.today.setValue("oggi");
        AEToken.qualifier.setValue("Pippo");
        //        AEToken.tagView.setValue("");
//        AEToken.entity.setValue("Bolla");
        AEToken.estendeEntity.setValue("AEntity");
        AEToken.superClassEntity.setValue("");
    }


    protected boolean creaDirectory() {
        if (file.isEsisteDirectory(pathPackage) && !sovrascrive) {
            System.out.println("Il package " + packageName + " esiste già e non può essere sovrascritto");
            Notification.show("Il package " + packageName + " esiste già e non può essere sovrascritto", 3000, Notification.Position.MIDDLE);
            return false;
        } else {
            file.creaDirectory(pathPackage);
            return true;
        }
    }


    protected void creaFileEntity() {
        String tag = "Entity";
        String nomeFileDest = text.primaMaiuscola(packageName);
        if (AEWiz.flagEntity.isAcceso()) {
            wizService.scriveFileCreatoDaSource(tag, pathPackage, nomeFileDest);
        }
    }


    protected void creaFileBase(String pathDest, String testo) {
        if (!file.scriveNewFile(pathDest, testo)) {
            if (sovrascrive) {
                file.sovraScriveFile(pathDest, testo);
            }
        }
    }

}
