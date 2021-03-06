package it.algos.vaadflow14.backend.logic;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
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

    protected int backSteps = -1;

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

        super.usaBottoneBack = true;
        super.usaBottoneRegistra = true;
        super.usaBottoneCancella = operationForm.isDeleteEnabled();

        if (operationForm.isUsaFrecceSpostamento() && annotation.usaSpostamentoTraSchede(entityClazz)) {
            super.usaBottonePrima = true;
            super.usaBottoneDopo = true;
        }
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
        Button bottone;

        bottomLayout = appContext.getBean(ABottomLayout.class, getWrapButtonsBottom());

        if (bottomLayout != null) {
            bottomLayout.setAllListener(this);
        }

        if (bottomPlaceHolder != null && bottomLayout != null) {
            bottomPlaceHolder.add(bottomLayout);
        }

        //--regola l'aspetto dei bottoni spostamento (se esistono)
        if (operationForm.isUsaFrecceSpostamento() && annotation.usaSpostamentoTraSchede(entityClazz)) {
            bottone = bottomLayout.getMappaBottoni().get(AEButton.prima);
            if (bottone != null) {
                bottone.setEnabled(isNotPrimo());
            }
            bottone = bottomLayout.getMappaBottoni().get(AEButton.dopo);
            if (bottone != null) {
                bottone.setEnabled(isNotUltimo());
            }
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
                if (saveDaForm()) {
                    this.back();
                }
                break;
            case delete:
                this.deleteForm();
                this.back();
                break;
            case prima:
                this.prima();
                break;
            case dopo:
                this.dopo();
                break;
            default:
                logger.warn("Switch - caso non definito", this.getClass(), "performAction(azione)");
                break;
        }
    }

    public boolean deleteForm() {
        boolean status = false;
        AEntity entityBean = (currentForm != null) ? currentForm.getValidBean() : null;

        if (mongo.delete(entityBean)) {
            status = true;
            logger.delete(entityBean);
        }

        return status;
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
            backToList();
        }
    }

    /**
     * Azione proveniente dal click sul bottone Annulla
     */
    protected void backToList() {
        UI.getCurrent().getPage().getHistory().go(backSteps);
    }

    /**
     * Azione proveniente dal click sul bottone Annulla
     */
    protected void back() {
        UI.getCurrent().getPage().getHistory().back();
    }

    /**
     * Azione proveniente dal click sul bottone Before <br>
     * Recupera la lista FILTRATA e ORDINATA delle entities, ricevuta dalla Grid <br> @todo da realizzare
     * Si sposta alla precedente <br>
     * Carica il form relativo <br>
     */
    protected void prima() {
        backSteps += -1;
        AEntity previousEntityBean = mongo.findPrevious(entityClazz, entityBean.id);
        executeRoute(previousEntityBean);
    }


    /**
     * Azione proveniente dal click sul bottone Next <br>
     * Recupera la lista FILTRATA e ORDINATA delle entities, ricevuta dalla Grid <br> @todo da realizzare
     * Si sposta alla successiva <br>
     * Carica il form relativo <br>
     */
    protected void dopo() {
        backSteps += -1;
        AEntity nextEntityBean = mongo.findNext(entityClazz, entityBean.id);
        executeRoute(nextEntityBean);
    }

    protected boolean isNotPrimo() {
        return mongo.findPrevious(entityClazz, entityBean.id) != null;

    }

    protected boolean isNotUltimo() {
        return mongo.findNext(entityClazz, entityBean.id) != null;
    }

    /**
     * Save proveniente da un click sul bottone 'registra' del Form. <br>
     * La entityBean viene recuperare dal form <br>
     *
     * @return true se la entity è stata registrata o definitivamente scartata; esce dal dialogo
     * .       false se manca qualche field e la situazione è recuperabile; resta nel dialogo
     */
    public boolean saveDaForm() {
        AEntity entityBean = null;
        if (currentForm != null) {
            entityBean = currentForm.getValidBean();
        }

        return entityBean != null ? save(entityBean) : false;
    }


    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     *
     * @return true se la entity è stata registrata o definitivamente scartata; esce dal dialogo
     * .       false se manca qualche field e la situazione è recuperabile; resta nel dialogo
     */
    public boolean save(final AEntity entityToSave) {
        boolean status = false;
        AEntity oldEntityBean;
        //        AEntity entityBean = beforeSave(entityToSave, operationForm);
        AEntity entityBean = entityToSave;

        if (entityBean == null) {
            return status;
        }

        //        if (beanService.isModificata(entityBean)) {
        //        }
        //        else {
        //            return true;
        //        }

        if (text.isEmpty(entityBean.id) && !(operationForm == AEOperation.addNew)) {
            logger.error("operationForm errato in una nuova entity che NON è stata salvata", LogicForm.class, "save");
            return status;
        }

        if (entityBean != null) {
            if (operationForm == AEOperation.addNew && entityBean.id == null) {
                //                entityBean = fixKey(entityBean);//@todo implementare
            }
            oldEntityBean = mongo.find(entityBean);
            entityBean = mongo.save(entityBean);
            status = entityBean != null;
            if (status) {
                if (operationForm == AEOperation.addNew) {
                    //                    ALogService.messageSuccess(entityBean.toString() + " è stato creato"); //@todo Creare una preferenza e sostituirla qui
                    logger.nuovo(entityBean);
                }
                else {
                    //                    ALogService.messageSuccess(entityBean.toString() + " è stato modificato"); //@todo Creare una preferenza e sostituirla qui
                    logger.modifica(entityBean, oldEntityBean);
                }
            }
        }
        else {
            logger.error("Object to save must not be null", this.getClass(), "save");
        }

        if (entityBean == null) {
            if (operationForm != null) {
                switch (operationForm) {
                    case addNew:
                        logger.warn("Non sono riuscito a creare la entity ", this.getClass(), "save");
                        break;
                    case edit:
                        logger.warn("Non sono riuscito a modificare la entity ", this.getClass(), "save");
                        break;
                    default:
                        logger.warn("Switch - caso non definito", this.getClass(), "save");
                        break;
                }
            }
            else {
                logger.warn("Non sono riuscito a creare la entity ", this.getClass(), "save");
            }
        }

        return status;
    }

}
