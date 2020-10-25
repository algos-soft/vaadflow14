package it.algos.simple.backend.packages;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PiView1 extends Div {

    @Autowired
    private PiService service;


    @PostConstruct
    private void postConstruct() {
        setHeight("90%");


        Grid grid = creaGrid();
        this.add(grid);

    }


    private Grid creaGrid() {

        PiGrid grid = new PiGrid();

        grid.addColumn(Anno::getId).setHeader("Id");
        grid.addColumn(Anno::getAnno).setHeader("Anno");
        grid.addColumn(Anno::isBisestile).setHeader("bisestile");

        DataProvider dataProvider = service.createDataProvider();
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        return grid;
    }
}
