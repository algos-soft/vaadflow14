package it.algos.vaadflow14.ui.list;

import com.vaadin.flow.router.Route;
import it.algos.vaadflow14.ui.MainLayout;
import it.algos.vaadflow14.ui.button.ATopLayout;
import it.algos.vaadflow14.ui.view.AView;

import static it.algos.vaadflow14.backend.application.FlowCost.ROUTE_NAME_GENERIC_LIST;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mar, 28-apr-2020
 * Time: 19:00
 * Superclasse per visualizzare la Grid <br>
 */
@Route(value = ROUTE_NAME_GENERIC_LIST, layout = MainLayout.class)
public class AViewList extends AView {


    /**
     * Costruttore @Autowired <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     * Nella sottoclasse concreta si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Nella sottoclasse concreta si usa una costante statica, per scrivere sempre uguali i riferimenti <br>
     * Passa nella superclasse anche la entityClazz che viene definita qui (specifica di questo modulo) <br>
     */
    public AViewList() {
    }// end of Vaadin/@Route constructor


    /**
     * Costruisce un layout per i bottoni di comando in topPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Tipicamente usato SOLO nella List <br>
     * Nell' implementazione standard di default presenta solo il bottone 'New' <br>
     * Recupera dal service specifico i menu/bottoni previsti <br>
     * Costruisce un' istanza dedicata con i bottoni <br>
     * Inserisce l' istanza (grafica) in topPlacehorder della view <br>
     */
    @Override
    protected void fixTopLayout() {
        ATopLayout topLayout = null;

        if (entityLogic != null) {
            topLayout = entityLogic.getTopLayout();
        }

        if (topPlacehorder != null && topLayout != null) {
            topPlacehorder.add(topLayout);
        }

        this.add(topPlacehorder);
    }


    /**
     * Costruisce il 'corpo' centrale della view <br>
     * <p>
     * Differenziato tra AViewList e AViewForm <br>
     * Costruisce un' istanza dedicata <br>
     * Inserisce l' istanza (grafica) in bodyPlacehorder della view <br>
     */
    @Override
    protected void fixBody() {
        AGrid grid;

        grid = entityLogic.getBodyGridLayout();

        if (bodyPlacehorder != null && grid.getGrid() != null) {
            bodyPlacehorder.add(grid.getGrid());
        }

        this.add(bodyPlacehorder);
    }


    /**
     * Costruisce un layout per i bottoni di comando in footerPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Tipicamente usato SOLO nel Form <br>
     * Nell' implementazione standard di default presenta solo il bottone 'New' <br>
     * Recupera dal service specifico i menu/bottoni previsti <br>
     * Costruisce un' istanza dedicata con i bottoni <br>
     * Inserisce l' istanza (grafica) in bottomPlacehorder della view <br>
     */
    @Override
    protected void fixBottomLayout() {
    }

}
