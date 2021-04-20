package it.algos.simple.backend.packages;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;


@Route(value = "alex")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PiUnoView extends VerticalLayout {

    @Autowired
    private PiService piService;

    @Autowired
    private ADataProviderService dataProviderService;


    @PostConstruct
    private void postConstruct() {
        setHeight("100%");
        //            this.getElement().getStyle().set("background-color", EAColor.lime.getEsadecimale());


//        this.add(creaGrid());
//        this.add(creaGrid2());
        this.add(creaGrid3());//quello più simile a AGrid

    }


    private Grid creaGrid() {

        PiGrid grid = new PiGrid();

        grid.addColumn(Anno::getId).setHeader("Id");
        grid.addColumn(Anno::getAnno).setHeader("Anno");
        grid.addColumn(Anno::isBisestile).setHeader("bisestile");

        DataProvider dataProvider = piService.createDataProvider(Anno.class);
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        return grid;
    }


    private Grid creaGrid2() {

        Grid<Anno> grid = new Grid<Anno>(Anno.class,false);

        grid.addColumn(Anno::getId).setHeader("Id");
        grid.addColumn(Anno::getAnno).setHeader("Anno");
        grid.addColumn(Anno::isBisestile).setHeader("bisestile");

        DataProvider dataProvider = dataProviderService.creaDataProvider(Anno.class,null);
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        return grid;
    }


    private Grid creaGrid3() {

        Grid grid = new Grid(Anno.class,false);

        grid.addColumn("id").setHeader("Id");
        grid.addColumn("anno").setHeader("Anno");
        grid.addColumn("bisestile").setHeader("bisestile");

        DataProvider dataProvider = dataProviderService.creaDataProvider(Anno.class,null);
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        return grid;
    }
}
