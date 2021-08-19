package it.algos.simple.backend.packages;

import com.mongodb.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.packages.crono.anno.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.*;


@Route(value = "alex")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PiUnoView extends VerticalLayout {

    @Autowired
    private PiService piService;

    @Autowired
    private DataProviderService dataProviderService;


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
        grid.addColumn(Anno::getTitolo).setHeader("Anno");
        grid.addColumn(Anno::isBisestile).setHeader("bisestile");

        DataProvider dataProvider = piService.createDataProvider(Anno.class);
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        return grid;
    }


    private Grid creaGrid2() {

        Grid<Anno> grid = new Grid<Anno>(Anno.class,false);

        grid.addColumn(Anno::getId).setHeader("Id");
        grid.addColumn(Anno::getTitolo).setHeader("Anno");
        grid.addColumn(Anno::isBisestile).setHeader("bisestile");

        DataProvider dataProvider = dataProviderService.creaDataProvider(Anno.class,(BasicDBObject)null);
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        return grid;
    }


    private Grid creaGrid3() {

        Grid grid = new Grid(Anno.class,false);

        grid.addColumn("id").setHeader("Id");
        grid.addColumn("anno").setHeader("Anno");
        grid.addColumn("bisestile").setHeader("bisestile");

        DataProvider dataProvider = dataProviderService.creaDataProvider(Anno.class,(BasicDBObject)null);
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        return grid;
    }
}
