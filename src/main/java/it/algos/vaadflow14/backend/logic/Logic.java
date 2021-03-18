package it.algos.vaadflow14.backend.logic;

import com.vaadin.flow.component.*;
import com.vaadin.flow.router.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.ui.button.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 01-mar-2021
 * Time: 21:29
 */
public abstract class Logic extends LogicProperty implements AILogic, HasUrlParameter<String>, BeforeEnterObserver {


    /**
     * Regola i parametri del browser per una view costruita da @Route <br>
     * Usato per costruire GenericLogicList e GenericLogicForm <br>
     * Se c'è solo il primo segmento, routeParameter NON è valido (non serve) <br>
     * <p>
     * Chiamato da com.vaadin.flow.router.Router tramite l' interfaccia HasUrlParameter <br>
     * Chiamato DOPO @PostConstruct ma PRIMA di beforeEnter() <br>
     *
     * @param beforeEvent  con la location, ui, navigationTarget, source, ecc
     * @param bodyTextUTF8 stringa del browser da decodificare
     */
    @Override
    public void setParameter(final BeforeEvent beforeEvent, @OptionalParameter String bodyTextUTF8) {
        routeParameter = route.estraeParametri(beforeEvent, bodyTextUTF8);

        //--Regola le property indispensabili per gestire questa view
        //--Se c'è solo il primo segmento, routeParameter NON è valido (non serve)
        if (routeParameter != null && routeParameter.isValido()) {
            fixProperty();
        }
    }

    /**
     * Regola alcune property indispensabili per gestire questa view <br>
     * Possono provenire da una sottoclasse concreta oppure dai parametri del browser <br>
     * <p>
     * Se arriva qui da una sottoclasse, vuol dire:
     * - la @Route NON è ROUTE_NAME_GENERIC_VIEW
     * - che è stata chiamata una @Route di una classe tipo xxxList
     * - la stringa del browser NON ha parametri
     * - le property sono già regolate
     * <p>
     * Se arriva qui da una view generica, vuol dire:
     * - la @Route DEVE essere ROUTE_NAME_GENERIC_VIEW
     * - la stringa del browser DEVE avere dei parametri
     * - le property devono essere regolate
     * <p>
     * Questa classe, chiamata da @Route è praticamente un prototype <br>
     * le property vanno pertanto azzera ad ogni utilizzo <br>
     * <p>
     * The entityClazz obbligatorio di tipo AEntity, per la Grid <br>
     * The entityService obbligatorio, singleton di tipo xxxService <br>
     * The entityBean obbligatorio, istanza di entityClazz per il Form <br>
     */
    protected void fixProperty() {
        this.entityBean = null;
        this.routeNameForm = VUOTA;

        if (routeParameter == null && annotation.getRouteName(this.getClass()).equals(ROUTE_NAME_GENERIC_VIEW)) {
            logger.error("Qualcosa non quadra", Logic.class, "fixProperty");
        }

        this.fixTypeView();
        this.fixEntityClazz();
        this.fixEntityService();
        this.fixEntityBean();
    }


    /**
     * Property per il tipo di view (List o Form) <br>
     * Property per il tipo di operazione (solo Form) <br>
     * Può essere sovrascritto, SENZA invocare prima il metodo della superclasse <br>
     */
    protected void fixTypeView() {
        String operationTxt;

        if (routeParameter != null && text.isValid(routeParameter.get(KEY_FORM_TYPE))) {
            operationTxt = routeParameter.get(KEY_FORM_TYPE);
            if (text.isValid(operationTxt)) {
                operationForm = AEOperation.valueOf(operationTxt);
            }
        }
    }


    /**
     * Controlla che esista il riferimento alla entityClazz <br>
     * Se non esiste nella List, è un errore <br>
     * Se non esiste nel Form, lo crea dall'url del browser <br>
     * Deve essere sovrascritto, senza invocare il metodo della superclasse <br>
     */
    protected void fixEntityClazz() {
        String canonicalName;

        if (entityClazz == null) {
            canonicalName = routeParameter.get(KEY_BEAN_CLASS);
            try {
                entityClazz = (Class<AEntity>) Class.forName(canonicalName);
            } catch (Exception unErrore) {
                logger.error("Non sono riuscito a creare la entityClazz", Logic.class, "fixEntityClazz");
            }
        }
    }


    /**
     * Controlla che esista il riferimento alla classe entityService <br>
     * Se non esiste, lo crea <br>
     */
    protected void fixEntityService() {
        if (entityService == null) {
            entityService = classService.getServiceFromEntityClazz(entityClazz);
        }
    }


    /**
     *
     */
    protected void fixEntityBean() {
        String keyID;

        keyID = routeParameter.get(KEY_BEAN_ENTITY) != null ? routeParameter.get(KEY_BEAN_ENTITY) : VUOTA;
        if (text.isEmpty(keyID) || keyID.equals(KEY_NULL)) {
            entityBean = entityService.newEntity();
        }
        else {
            entityBean = entityService.findById(keyID);
        }
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        this.fixEntityClazz();
        this.fixEntityService();
//        this.fixLogicForm();
        this.fixPreferenze();
        this.initView();
    }

//    /**
//     *
//     */
//    protected void fixLogicForm() {
//        if (text.isEmpty(logicFormRoute)) {
//        }
//    }

    /**
     * Qui va tutta la logica iniziale della view <br>
     */
    protected void initView() {
        //--Costruisce gli oggetti base (placeholder) di questa view
        super.fixLayout();

        //--Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
        this.fixAlertLayout();

        //--Costruisce un layout (obbligatorio) per i menu ed i bottoni di comando della view <br>
        //--Eventualmente i bottoni potrebbero andare su due righe <br>
        this.fixTopLayout();

        //--Corpo principale della Grid (obbligatorio) <br>
        this.fixBodyLayout();

        //--Eventuali bottoni sotto la grid (eventuale) <br>
        this.fixBottomLayout();

        //--Eventuali scritte in basso al video (eventuale) <br>
        this.fixFooterLayout();

        //--Aggiunge i 5 oggetti base (placeholder) alla view, se sono utilizzati <br>
        super.addToLayout();
    }


    /**
     * Costruisce un wrapper (obbligatorio) di dati <br>
     * I dati sono gestiti da questa 'logic' <br>
     * I dati vengono passati alla View che li usa <br>
     *
     * @return wrapper di dati per la view
     */
    protected WrapButtons getWrapButtonsTop() {
        return null;
    }


    /**
     * Costruisce il corpo principale (obbligatorio) della Grid <br>
     */
    @Override
    protected void fixBodyLayout() {
    }


    /**
     * Costruisce un (eventuale) layout per scritte in basso della pagina <br>
     * Può essere sovrascritto senza invocare il metodo della superclasse <br>
     */
    @Override
    protected void fixFooterLayout() {
    }

    /**
     *
     */
    public List<String> getFormPropertyNamesList() {
        return null;
    }

    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     *
     * @param azione selezionata da eseguire
     */
    @Override
    public void performAction(AEAction azione) {
    }

    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     *
     * @param azione     selezionata da eseguire
     * @param entityBean selezionata
     */
    public void performAction(AEAction azione, AEntity entityBean) {
    }

    /**
     *
     */
    @Override
    public List<String> getGridColumns() {
        return null;
    }

    protected final void executeRoute() {
        executeRoute((AEntity) null);
    }

    /**
     * Apre una pagina di wikipedia. <br>
     */
    protected final void openWikiPage() {
        String link = "\"" + PATH_WIKI + wikiPageTitle + "\"";
        UI.getCurrent().getPage().executeJavaScript("window.open(" + link + ");");
    }

    /**
     * Lancia una @route con la visualizzazione di una singola scheda. <br>
     * Se il package usaSpostamentoTraSchede=true, costruisce una query
     * con le keyIDs della scheda precedente e di quella successiva
     * (calcolate secondo l'ordinamento previsto) <br>
     */
    protected void executeRoute(final AEntity entityBean) {
        final QueryParameters query;
        final AEntity entityBeanPrev;
        final AEntity entityBeanNext;

        if (annotation.usaSpostamentoTraSchede(entityClazz)) {
            entityBeanPrev = entityBean != null ? mongo.findPrevious(entityClazz, entityBean.id) : null;
            entityBeanNext = entityBean != null ? mongo.findNext(entityClazz, entityBean.id) : null;
            query = route.getQueryForm(entityClazz, operationForm, entityBean, entityBeanPrev, entityBeanNext);
        }
        else {
            query = route.getQueryForm(entityClazz, operationForm, entityBean);
        }

        UI.getCurrent().navigate(routeNameForm, query);
    }

}
