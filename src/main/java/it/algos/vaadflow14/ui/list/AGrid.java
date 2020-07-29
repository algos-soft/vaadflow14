package it.algos.vaadflow14.ui.list;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.entity.AILogic;
import it.algos.vaadflow14.backend.entity.ALogic;
import it.algos.vaadflow14.backend.enumeration.AEColor;
import it.algos.vaadflow14.backend.service.AAnnotationService;
import it.algos.vaadflow14.backend.service.AArrayService;
import it.algos.vaadflow14.backend.service.ALogService;
import it.algos.vaadflow14.ui.button.AEAction;
import it.algos.vaadflow14.ui.service.AColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.SEP;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 01-mag-2020
 * Time: 17:39
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AGrid extends Grid {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AColumnService columnService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ALogService logger;

    protected AILogic service;

    protected List<String> gridPropertyNamesList;

    protected Class<? extends AEntity> beanType;

    protected Label headerLabelPlaceHolder;


    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected AArrayService array;

    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected AAnnotationService annotation;


    public AGrid() {
    }


    public AGrid(Class<? extends AEntity> beanType) {
        super(beanType);
    }


    public AGrid(Class<? extends AEntity> beanType, ALogic service) {
        super(beanType, false);
        this.service = service;
        this.beanType = beanType;
    }


    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Prima viene chiamato il costruttore <br>
     * Prima viene chiamato init(); <br>
     * Viene chiamato @PostConstruct (con qualsiasi firma) <br>
     * Dopo viene chiamato setParameter(); <br>
     * Dopo viene chiamato beforeEnter(); <br>
     * <p>
     * Le preferenze vengono (eventualmente) lette da mongo e (eventualmente) sovrascritte nella sottoclasse
     * Creazione e posizionamento dei componenti UI <br>
     * Possono essere sovrascritti nelle sottoclassi <br>
     */
    @PostConstruct
    protected void postConstruct() {
        this.setHeightByRows(true);

        if (FlowVar.usaDebug) {//@todo Funzionalità ancora da implementare nelle preferenze
            this.getElement().getStyle().set("background-color", AEColor.blue.getEsadecimale());
        }

        //--Costruisce una lista di nomi delle properties della Grid
        gridPropertyNamesList = service != null ? service.getGridPropertyNamesList() : null;

        //--Colonne normali indicate in @AIList(fields =... , aggiunte in automatico
        this.addColumnsGrid();
        this.creaGridHeader();
    }


    //    /**
    //     * Costruisce una lista di nomi delle properties <br>
    //     * 1) Cerca nell'annotation @AIList della Entity e usa quella lista (con o senza ID) <br>
    //     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
    //     * 3) Sovrascrive il metodo getGridPropertyNamesList() nella sottoclasse specifica di xxxService <br>
    //     * Un eventuale modifica dell'ordine di presentazione delle colonne viene regolata nel metodo sovrascritto <br>
    //     */
    //    protected List<String> getGridPropertyNamesList() {
    //        List<String> gridPropertyNamesList = service != null ? service.getGridPropertyNamesList() : null;
    //        return gridPropertyNamesList;
    //    }


    /**
     * Aggiunge in automatico le colonne previste in gridPropertyNamesList <br>
     * Se si usa una PaginatedGrid, il metodo DEVE essere sovrascritto nella classe APaginatedGridViewList <br>@todo non è proprio cosi
     */
    protected void addColumnsGrid() {
        String header = VUOTA;

        if (gridPropertyNamesList != null) {
            for (String propertyName : gridPropertyNamesList) {
//                header = annotation.getHeaderName(beanType, propertyName);
//                this.addColumn(propertyName).setHeader(header).setResizable(true);
                 columnService.add(this, beanType, propertyName);
            }// end of for cycle

            for (Object colonna : this.getColumns()) {
                ((Column) colonna).setAutoWidth(true);
            }// end of for cycle
            this.recalculateColumnWidths();

        }// end of if cycle
    }// end of method


    @Override
    public void setItems(Collection items) {

        if (array.isValid(items)) {
            super.deselectAll();
            super.setItems(items);
        }

        fixGridHeader(items);
    }


    /**
     * Aggiunta di tutti i listener <br>
     * Chiamato da AEntityService <br>
     * Aggiunge il listener alla riga, specificando l'azione di ritorno associata <br>
     *
     * @param service a cui rinviare l'evento/azione da eseguire
     */
    public void setAllListener(AILogic service) {
        this.service = service;

        this.addItemDoubleClickListener(event -> performAction((ItemClickEvent) event, AEAction.doubleClick));
    }


    //@todo Funzionalità ancora da implementare
    public void detail(ItemDoubleClickEvent click) {
        String keyID = VUOTA;
        keyID = ((AEntity) click.getItem()).id;
        //        openDialogRoute(keyID);
    }


    /**
     * Esegue l'azione del bottone. <br>
     * <p>
     * Passa a AEntityService.performAction(azione) <br>
     *
     * @param azione da eseguire
     */
    public void performAction(ItemClickEvent event, AEAction azione) {
        AEntity entityBean = (AEntity) event.getItem();

        if (service != null) {
            service.performAction(azione, entityBean);
        }
    }


    /**
     * PlaceHolder per un eventuale header text <br>
     * Il PlaceHolder (una label) esiste SEMPRE. Il contenuto viene modificato da setItems() <br>
     */
    protected void creaGridHeader() {
        this.headerLabelPlaceHolder = new Label();

        try {
             //@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
//            HeaderRow topRow = this.prependHeaderRow();
//            Grid.Column[] matrix = array.getColumnArray(this);
//            if (matrix != null && matrix.length > 0) {
//                HeaderRow.HeaderCell informationCell = topRow.join(matrix);
//                informationCell.setComponent(headerLabelPlaceHolder);
//            }
            //@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
        } catch (Exception unErrore) {
            logger.error(unErrore.toString());
        }
    }


    /**
     * Eventuale header text <br>
     * Se si usa una PaginatedGrid, il metodo DEVE essere sovrascritto nella classe APaginatedGridViewList <br>
     */
    protected void fixGridHeader(Collection items) {
        String message = VUOTA;

        if (true) {//@todo Funzionalità ancora da implementare con preferenza locale
            message = beanType.getSimpleName().toUpperCase() + SEP;
            if (items != null && items.size() > 0) {
                if (items.size() == 1) {
                    message += "Lista di un solo elemento";
                } else {
                    message += "Lista di " + items.size() + " elementi";
                }
            } else {
                message += "Al momento non ci sono elementi in questa collezione";
            }

            if (headerLabelPlaceHolder!=null) {
                headerLabelPlaceHolder.setText(message);
            }
        }
    }

    //    /**
    //     * Esegue l'azione del bottone. <br>
    //     * <p>
    //     * Passa a AEntityService.performAction(azione) <br>
    //     *
    //     * @param azione da eseguire
    //     */
    //    public void performAction(AEAction azione, AEntity entityBean) {
    //        if (service != null) {
    //            service.performAction(azione, entityBean);
    //        }
    //    }

}
