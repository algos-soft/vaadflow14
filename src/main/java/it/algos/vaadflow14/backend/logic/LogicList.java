package it.algos.vaadflow14.backend.logic;

import com.mongodb.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.data.provider.*;
import de.codecamp.vaadin.components.messagedialog.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.ui.button.*;
import it.algos.vaadflow14.ui.enumeration.*;
import it.algos.vaadflow14.ui.header.*;
import it.algos.vaadflow14.ui.interfaces.*;
import it.algos.vaadflow14.ui.list.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 26-feb-2021
 * Time: 17:27
 */
public abstract class LogicList extends Logic {


    /**
     * The Grid  (obbligatoria per ViewList)
     */
    protected AGrid grid;

    /**
     * Preferenze usate da questa 'logica' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneDeleteAll = AEPreferenza.usaMenuReset.is() && annotation.usaReset(entityClazz);
        super.usaBottoneResetList = AEPreferenza.usaMenuReset.is() && annotation.usaReset(entityClazz);
        super.usaBottoneNew = AEPreferenza.usaMenuReset.is() && annotation.usaCreazione(entityClazz);
    }

    //    /**
    //     * Controlla che esista il riferimento alla entityClazz <br>
    //     * Se non esiste nella List, è un errore <br>
    //     * Se non esiste nel Form, lo crea dall'url del browser <br>
    //     * Deve essere sovrascritto, senza invocare il metodo della superclasse <br>
    //     */
    //    @Override
    //    protected void fixEntityClazz() {
    //        if (entityClazz == null) {
    //            logger.error("Non esiste la entityClazz", LogicList.class, "fixEntityClazz");
    //        }
    //    }


    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     */
    @Override
    protected void fixAlertLayout() {
        AIHeader headerSpan = appContext.getBean(AHeaderSpanList.class, getSpanList());

        if (alertPlaceHolder != null && headerSpan != null) {
            alertPlaceHolder.add(headerSpan.get());
        }
    }

    /**
     * Costruisce una lista (eventuale) di 'span' da mostrare come header della view <br>
     * DEVE essere sovrascritto <br>
     *
     * @return una liste di 'span'
     */
    protected List<Span> getSpanList() {
        return null;
    }


    /**
     * Costruisce un wrapper (obbligatorio) di dati <br>
     * I dati sono gestiti da questa 'logic' <br>
     * I dati vengono passati alla View che li usa <br>
     *
     * @return wrapper di dati per la view
     */
    protected WrapButtons getWrapButtonsTop() {
        List<AIButton> listaAEBottoni = this.getListaAEBottoni();
        //        WrapSearch wrapSearch = this.getWrapSearch();
        //        LinkedHashMap<String, ComboBox> mappaComboBox = this.mappaComboBox;
        //        List<Button> listaBottoniSpecifici = this.getListaBottoniSpecifici();
        //        AEOperation operationForm = null;

        return appContext.getBean(WrapButtons.class, this, listaAEBottoni, null, null, null, maxNumeroBottoniPrimaRiga);
    }


    /**
     * Costruisce il corpo principale (obbligatorio) della Grid <br>
     */
    @Override
    protected void fixBodyLayout() {
        DataProvider dataProvider;
        String sortProperty = annotation.getSortProperty(entityClazz);
        BasicDBObject sort = null;
        grid = appContext.getBean(AGrid.class, entityClazz, this);
        sort = new BasicDBObject(sortProperty, 1);

        dataProvider = dataService.creaDataProvider(entityClazz, sort);
        grid.getGrid().setDataProvider(dataProvider);
        grid.getGrid().setHeight("100%");
        grid.fixGridHeader(dataProvider.size(null));
        this.addGridListeners();

        if (bodyPlaceHolder != null && grid != null) {
            bodyPlaceHolder.add(grid.getGrid());
        }
    }

    /**
     * Aggiunge tutti i listeners alla Grid di 'bodyPlaceHolder' che è stata creata SENZA listeners <br>
     */
    protected void addGridListeners() {
        if (grid != null && grid.getGrid() != null) {
            grid.setAllListener(this);
        }
    }

    /**
     * Costruisce una lista ordinata di nomi delle properties della Grid. <br>
     * Nell' ordine: <br>
     * 1) Cerca nell' annotation @AIList della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica xxxLogicList <br>
     * Può essere sovrascritto senza invocare il metodo della superclasse <br>
     *
     * @return lista di nomi di properties
     */
    @Override
    public List<String> getGridColumns() {
        return annotation.getGridColumns(entityClazz);
    }


    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     * Interfaccia utilizzata come parametro per poter sovrascrivere il metodo <br>
     * Nella classe base eseguirà un casting a AEAction <br>
     * Nella (eventuale) sottoclasse specifica del progetto eseguirà un casting a AExxxAction <br>
     *
     * @param iAzione interfaccia dell'azione selezionata da eseguire
     *
     * @return true se l'azione esiste nello Switch, false -> Switch - caso non definito
     */
    @Override
    public boolean performAction(AIAction iAzione) {
        boolean status = true;
        AEAction azione;

        if (iAzione instanceof AEAction) {
            azione = (AEAction) iAzione;
        }
        else {
            return false;
        }

        switch (azione) {
            case deleteAll:
                this.openConfirmDeleteAll();
                break;
            case resetList:
                this.openConfirmReset();
                break;
            case doubleClick:
                break;
            case nuovo:
                this.operationForm = AEOperation.addNew;
                this.executeRoute();
                break;
            case edit:
            case show:
            case editNoDelete:
            case searchField:
                //                this.searchFieldValue = searchFieldValue;
                //                refreshGrid();
                break;
            case searchDialog:
                //                Notification.show("Not yet. Coming soon.", 3000, Notification.Position.MIDDLE);
                //                logger.info("Not yet. Coming soon", this.getClass(), "performAction");
                break;
            case valueChanged:
                //                refreshGrid();
                break;
            case export:
                //                export();
                break;
            case showWiki:
                openWikiPage(wikiPageTitle);
                break;
            //            case modulo:
            //                openWikiPage(wikiModuloTitle);
            //                break;
            //            case statistiche:
            //                openWikiPage(wikiStatisticheTitle);
            //                break;
            default:
                status = false;
                //                logger.warn("Switch - caso non definito", this.getClass(), "performAction(azione)");
                break;
        }

        return status;
    }

    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     * Interfaccia utilizzata come parametro per poter sovrascrivere il metodo <br>
     * Nella classe base eseguirà un casting a AEAction <br>
     * Nella (eventuale) sottoclasse specifica del progetto eseguirà un casting a AExxxAction <br>
     *
     * @param iAzione    interfaccia dell'azione selezionata da eseguire
     * @param entityBean selezionata
     *
     * @return true se l'azione esiste nello Switch, false -> Switch - caso non definito
     */
    @Override
    public boolean performAction(AIAction iAzione, AEntity entityBean) {
        boolean status = true;
        AEAction azione = (AEAction) iAzione;

        switch (azione) {
            case doubleClick:
                this.operationForm = AEOperation.edit;
                this.executeRoute(entityBean);
                break;
            default:
                status = false;
                break;
        }

        return status;
    }

    protected void executeRoute() {
        executeRoute(VUOTA, VUOTA, VUOTA);
    }

    /**
     * Lancia una @route con la visualizzazione di una singola scheda. <br>
     * Se il package usaSpostamentoTraSchede=true, costruisce una query
     * con le keyIDs della scheda precedente e di quella successiva
     * (calcolate secondo l'ordinamento previsto) <br>
     */
    protected void executeRoute(final AEntity entityBean) {
        if (entityBean == null) {
            executeRoute(VUOTA, VUOTA, VUOTA);
            return;
        }

        final String sortProperty = annotation.getSortProperty(entityClazz);
        final Object valueProperty = reflection.getPropertyValue(entityBean, sortProperty);
        final String beanPrevID = text.isValid(entityBeanPrevID) ? entityBeanPrevID : mongo.findPreviousID(entityClazz, sortProperty, valueProperty);
        final String beanNextID = text.isValid(entityBeanNextID) ? entityBeanPrevID : mongo.findNextID(entityClazz, sortProperty, valueProperty);

        executeRoute(entityBean.id, beanPrevID, beanNextID);
    }

    /**
     * Opens the confirmation dialog before deleting all items. <br>
     * <p>
     * The dialog will display the given title and message(s), then call <br>
     * Può essere sovrascritto dalla classe specifica se servono avvisi diversi <br>
     */
    protected final void openConfirmDeleteAll() {
        MessageDialog messageDialog;
        String message = "Vuoi veramente cancellare tutto? L' operazione non è reversibile.";
        VaadinIcon icon = VaadinIcon.WARNING;

        if (mongo.isValid(entityClazz)) {
            messageDialog = new MessageDialog().setTitle("Delete").setMessage(message);
            messageDialog.addButton().text("Cancella").icon(icon).error().onClick(e -> clickDeleteAll()).closeOnClick();
            messageDialog.addButtonToLeft().text("Annulla").primary().clickShortcutEscape().clickShortcutEnter().closeOnClick();
            messageDialog.open();
        }
    }

    /**
     * Cancellazione effettiva (dopo dialogo di conferma) di tutte le entities della collezione. <br>
     * Azzera gli items <br>
     * Ridisegna la GUI <br>
     */
    public void clickDeleteAll() {
        entityService.deleteAll();
        logger.deleteAll(entityClazz);
        this.refreshGrid();
        //            this.reloadList();//@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
    }

    /**
     * Opens the confirmation dialog before reset all items. <br>
     * <p>
     * The dialog will display the given title and message(s), then call <br>
     * Può essere sovrascritto dalla classe specifica se servono avvisi diversi <br>
     */
    protected final void openConfirmReset() {
        MessageDialog messageDialog;
        String message = "Vuoi veramente ripristinare i valori originali predeterminati di questa collezione? L' operazione cancellerà tutti i valori successivamente aggiunti o modificati.";
        VaadinIcon icon = VaadinIcon.WARNING;

        if (mongo.isEmpty(entityClazz)) {
            clickReset();
        }
        else {
            messageDialog = new MessageDialog().setTitle("Reset").setMessage(message);
            messageDialog.addButton().text("Continua").icon(icon).error().onClick(e -> clickReset()).closeOnClick();
            messageDialog.addButtonToLeft().text("Annulla").primary().clickShortcutEscape().clickShortcutEnter().closeOnClick();
            messageDialog.open();
        }
    }


    /**
     * Azione proveniente dal click sul bottone Reset <br>
     * Creazione di alcuni dati iniziali <br>
     * Rinfresca la griglia <br>
     */
    public void clickReset() {
        if (resetDeletingAll()) {
            this.refreshGrid();
            //            this.reloadList();
        }
    }

    /**
     * Ricreazione di alcuni dati iniziali standard <br>
     * Invocato dal bottone Reset di alcune liste <br>
     * Cancella la collection (parzialmente, se usaCompany=true) <br>
     * I dati possono essere: <br>
     * 1) recuperati da una Enumeration interna <br>
     * 2) letti da un file CSV esterno <br>
     * 3) letti da Wikipedia <br>
     * 4) creati direttamente <br>
     *
     * @return false se non esiste il metodo sovrascritto o se la collection
     * ....... true se esiste il metodo sovrascritto è la collection viene ri-creata
     */
    private boolean resetDeletingAll() {
        AIResult result;
        entityService.delete();
        result = entityService.resetEmptyOnly();

        logger.log(AETypeLog.reset, result.getMessage());
        return result.isValido();
    }


    /**
     * Aggiorna gli items della Grid, utilizzando (anche) i filtri. <br>
     * Chiamato inizialmente alla creazione della Grid <br>
     * Chiamato per modifiche effettuate ai filtri, popup, newEntity, deleteEntity, ecc... <br>
     */
    public void refreshGrid() {
        List<? extends AEntity> items;

        if (grid != null && grid.getGrid() != null) {
            //            updateFiltri();
            items = mongo.findAll(entityClazz);
            grid.getGrid().deselectAll();
            grid.setItems(items);
            grid.getGrid().getDataProvider().refreshAll();
        }
    }


}
