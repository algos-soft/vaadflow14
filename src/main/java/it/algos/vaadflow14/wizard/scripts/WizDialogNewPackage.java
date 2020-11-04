package it.algos.vaadflow14.wizard.scripts;

import com.vaadin.flow.component.InputEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.ui.fields.ATextField;
import it.algos.vaadflow14.wizard.enumeration.AEToken;
import it.algos.vaadflow14.wizard.enumeration.AEWiz;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.TITOLO_NEW_PACKAGE;

/**
 * Project provider
 * Created by Algos
 * User: gac
 * Date: dom, 25-ott-2020
 * Time: 17:57
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogNewPackage extends WizDialog {


    /**
     * Apertura del dialogo <br>
     */
    public void open(WizRecipient wizRecipient) {
        super.wizRecipient = wizRecipient;
        super.isNuovoProgetto = false;
        super.isNuovoPackage = true;
        super.titoloCorrente = new H3();

        super.inizia();
    }


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        topLayout = fixSezione(TITOLO_NEW_PACKAGE, "green");
        this.add(topLayout);

        topLayout.add(text.getLabelGreenBold("Creazione di un nuovo package funzionante"));
    }


    /**
     * Sezione centrale con la scelta del nome del package <br>
     */
    @Override
    protected void creaSelezioneLayout() {
        selezioneLayout = fixSezione("Nome del package");
        this.add(selezioneLayout);

        fieldPackageName = new TextField("Nome del package");
        fieldPackageName.setAutofocus(true);
        selezioneLayout.add(fieldPackageName);

//        addListener();//@todo Funzionalità ancora da implementare
    }


    /**
     * Sezione centrale con la selezione dei flags <br>
     * Crea i checkbox di controllo <br>
     * Spazzola (nella sottoclasse) la Enumeration per aggiungere solo i checkbox adeguati: <br>
     * newProject
     * updateProject
     * newPackage
     * updatePackage
     * Spazzola la Enumeration e regola a 'true' i chekBox secondo il flag 'isAcceso' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaCheckBoxLayout() {
        super.creaCheckBoxLayout();
        Checkbox unCheckbox;

        //--spenge tutti i checkbox escluso flagEntity
        AEWiz.reset();

        for (AEWiz flag : AEWiz.getFlagsNewPackage()) {
            unCheckbox = new Checkbox(flag.getLabelBox(), flag.isAcceso());
            mappaCheckbox.put(flag.name(), unCheckbox);
        }

        super.addCheckBoxMap();
    }


    protected void creaBottoni() {
        super.creaBottoni();
        confirmButton.setEnabled(true);

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
    }


    private void addListener() {
        fieldPackageName.addKeyUpListener(event -> sincroPackageName((TextField) event.getSource()));
    }

    private void sincroPackageName(TextField source) {
        String value = source.getValue();

        if (text.isValid(value) && value.length() > 1) {
            confirmButton.setEnabled(true);
            AEToken.packageName.setValue(value);
        } else {
            confirmButton.setEnabled(false);
            AEToken.packageName.setValue(VUOTA);
        }
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola tutti i valori della Enumeration EAWiz che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    @Override
    protected void regolaAEWiz() {
        super.regolaAEWiz();
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    @Override
    protected void regolaAEToken() {
        super.regolaAEToken();

        AEToken.moduleNameMinuscolo.setValue( AEToken.nameTargetProject.getValue());
        AEToken.moduleNameMaiuscolo.setValue(AEToken.projectNameUpper.getValue());
        AEToken.entity.setValue(text.primaMaiuscola(AEToken.packageName.getValue()));
    }

}
