package it.algos.simple.backend.packages.viale;

import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 22-apr-2021
 * Time: 11:07
 */
@Route(value = "viale2", layout = MainLayout.class)
public class VialeView extends VerticalLayout implements BeforeEnterObserver {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ADataProviderService service;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ViaService viaService;

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        this.primaGrid();
    }


    public void primaGrid() {
        Grid<Via> grid = new Grid<>(Via.class, false);
        DataProvider dataProvider = service.creaDataProvider(Via.class, null);
        grid.setDataProvider(dataProvider);

        // Will be sortable by the user
        // When sorting by this column, the query
        // will have a SortOrder
        // where getSorted() returns "name"
        grid.addColumn("ordine")
                .setHeader("Ordine")
                .setSortProperty("ordine");
        grid.addColumn("nome")
                .setHeader("Name")
                .setSortProperty("nome");

        this.add(grid);
    }


}
