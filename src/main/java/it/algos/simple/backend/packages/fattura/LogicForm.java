package it.algos.simple.backend.packages.fattura;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.icon.*;
import de.codecamp.vaadin.components.messagedialog.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.ui.button.*;
import it.algos.vaadflow14.ui.enumeration.*;
import it.algos.vaadflow14.ui.form.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 01-mar-2021
 * Time: 18:42
 */
public abstract class LogicForm extends Logic {

    /**
     * The Form Class  (obbligatoria per costruire la currentForm)
     */
    protected Class<? extends AForm> formClazz;


    protected AForm currentForm;


    /**
     * Property per il tipo di view (List o Form) <br>
     * Property per il tipo di operazione (solo Form) <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTypeView() {
        String operationTxt;
        super.fixTypeView();

        if (routeParameter != null && text.isValid(routeParameter.get(KEY_FORM_TYPE))) {
            operationTxt = routeParameter.get(KEY_FORM_TYPE);
            if (text.isValid(operationTxt)) {
                operationForm = AEOperation.valueOf(operationTxt);
            }
        }
    }

    /**
     *
     */
    protected void fixEntityBean() {
        String keyID = routeParameter.get(KEY_BEAN_ENTITY) != null ? routeParameter.get(KEY_BEAN_ENTITY) : VUOTA;
        if (text.isEmpty(keyID) || keyID.equals(KEY_NULL)) {
            entityBean = entityService.newEntity();
        }
        else {
            entityBean = entityService.findById(keyID);
        }
    }

    /**
     * Preferenze usate da questa 'logica' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaBottoneBack = true;
        this.usaBottoneRegistra = true;
    }

    /**
     * Costruisce il corpo principale (obbligatorio) della Grid <br>
     */
    @Override
    protected void fixBodyLayout() {
        if (entityBean != null) {
            currentForm = appContext.getBean(AGenericForm.class, entityService, this, getWrapForm(entityBean));
        }
        else {
            logger.warn("Manca entityBean", this.getClass(), "fixBody");
            //            form = entityLogic.getBodyFormLayout(entityLogic.newEntity()); //@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
        }

        if (bodyPlaceHolder != null && currentForm != null) {
            bodyPlaceHolder.add(currentForm);
        }
    }

    /**
     * Costruisce un wrapper di dati <br>
     * I dati sono gestiti da questa 'logic' (nella sottoclasse eventualmente) <br>
     * I dati vengono passati alla View che li usa <br>
     * Può essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     *
     * @param entityBean interessata
     *
     * @return wrapper di dati per il Form
     */
    public WrapForm getWrapForm(AEntity entityBean) {
        return new WrapForm(entityBean, operationForm);
    }

    /**
     * Costruisce una lista ordinata di nomi delle properties del Form. <br>
     * La lista viene usata per la costruzione automatica dei campi e l' inserimento nel binder <br>
     * Nell' ordine: <br>
     * 1) Cerca nell' annotation @AIForm della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica di xxxLogic <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Se serve, modifica l' ordine della lista oppure esclude una property che non deve andare nel binder <br>
     * todo ancora da sviluppare
     *
     * @return lista di nomi di properties
     */
    public List<String> getFormPropertyNamesList() {
        List<String> fieldsNameList = annotation.getListaPropertiesForm(entityClazz);

        if (array.isEmpty(fieldsNameList)) {
            reflection.getFieldsName(entityBean.getClass());
        }

        if (FlowVar.usaCompany && annotation.usaCompany(entityBean.getClass())) {
            fieldsNameList.add(0, FIELD_COMPANY);
        }

        return fieldsNameList;
    }

    /**
     * Costruisce un layout (semi-obbligatorio) per i bottoni di comando della view <br>
     * Può essere sovrascritto senza invocare il metodo della superclasse <br>
     */
    @Override
    protected void fixBottomLayout() {
        AButtonLayout bottomLayout = appContext.getBean(ABottomLayout.class, getWrapButtonsBottom());

        if (bottomLayout != null) {
            bottomLayout.setAllListener(this);
        }

        if (bottomPlaceHolder != null && bottomLayout != null) {
            bottomPlaceHolder.add(bottomLayout);
        }
    }

    /**
     * Costruisce un wrapper (obbligatorio) di dati <br>
     * I dati sono gestiti da questa 'logic' <br>
     * I dati vengono passati alla View che li usa <br>
     *
     * @return wrapper di dati per la view
     */
    protected WrapButtons getWrapButtonsBottom() {
        List<AEButton> listaAEBottoni = this.getListaAEBottoni();
        //        List<Button> listaBottoniSpecifici = this.getListaBottoniSpecifici();

        return appContext.getBean(WrapButtons.class, this, listaAEBottoni);
    }

    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     *
     * @param azione selezionata da eseguire
     */
    @Override
    public void performAction(AEAction azione) {

        switch (azione) {
            case resetForm:
                //                this.reloadForm(entityBean);
                break;
            case back:
            case annulla:
                this.openConfirmExitForm(entityBean);
                break;
            case conferma:
            case registra:
                this.back();

//                if (saveDaForm()) {
//                    this.back();
//                }
                break;
            case delete:
                //                this.deleteForm();
                //                this.back();
                break;
            case prima:
                //                this.prima(entityBean);
                break;
            case dopo:
                //                this.dopo(entityBean);
                break;
            default:
                logger.warn("Switch - caso non definito", this.getClass(), "performAction(azione)");
                break;
        }
    }


    /**
     * Opens the confirmation dialog before exiting form. <br>
     * <p>
     * The dialog will display the given title and message(s), then call <br>
     * Può essere sovrascritto dalla classe specifica se servono avvisi diversi <br>
     */
    protected final void openConfirmExitForm(AEntity entityBean) {
        MessageDialog messageDialog;
        String message = "La entity è stata modificata. Sei sicuro di voler perdere le modifiche? L' operazione non è reversibile.";
        VaadinIcon iconBack = VaadinIcon.ARROW_LEFT;

        if (operationForm == AEOperation.addNew) {
            back();
            return;
        }

        if (currentForm.isModificato()) {
            if (mongo.isValid(entityClazz)) {
                messageDialog = new MessageDialog().setTitle("Ritorno alla lista").setMessage(message);
                messageDialog.addButton().text("Rimani").primary().clickShortcutEscape().clickShortcutEnter().closeOnClick();
                messageDialog.addButtonToLeft().text("Back").icon(iconBack).error().onClick(e -> back()).closeOnClick();
                messageDialog.open();
            }
        }
        else {
            back();
        }
    }

    /**
     * Azione proveniente dal click sul bottone Annulla
     */
    protected void back() {
        UI.getCurrent().getPage().getHistory().back();
    }

}
