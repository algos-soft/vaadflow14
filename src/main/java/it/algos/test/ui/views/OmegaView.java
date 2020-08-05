package it.algos.test.ui.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.packages.crono.anno.AnnoLogic;
import it.algos.vaadflow14.ui.MainLayout;
import it.algos.vaadflow14.ui.service.AColumnService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 29-lug-2020
 * Time: 18:46
 */
@Route(value = "omega", layout = MainLayout.class)
public class OmegaView extends VerticalLayout {

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

    public AnnoLogic annoLogic;

    private Grid<Anno> grid;


    /**
     * Instantiates a new Omega view.
     */
    public OmegaView() {
        add(VaadinIcon.HOSPITAL.create());
        add(new Span("Seconda prova di View"));
        add(new Span("Usa @Route e usa MainLayout.class"));
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        provaGrid();
    }


    public void provaGrid() {


        //        DataProvider<Anno,Void> dataProvider = DataProvider.fromCallbacks(
        //                // First callback fetches items based on a query
        //                query -> {
        //                    // The index of the first item to load
        //                    int offset = query.getOffset();
        //
        //                    // The number of items to load
        //                    int limit = query.getLimit();
        //
        //                    List<Anno> listaAnni = annoLogic.fetchAnni(offset, limit);
        //
        //                    return listaAnni != null ? listaAnni.stream() : null;
        //                },
        //
        //                // Second callback fetches the number of items for a query
        //                query -> annoLogic.getCount()
        //        );
        List<Anno> items = annoLogic.fetchAnni(5, 24);
        grid = new Grid<>();
        grid.setPageSize(10);

        List<String> colonne = Arrays.asList("ordine", "secolo", "nome", "bisestile");
        for (String name : colonne) {
            columnService.add(grid, Anno.class, name);
        }

        grid.setItems(items);
        VerticalLayout gridPlaceholder = new VerticalLayout();
        gridPlaceholder.add(grid);
        gridPlaceholder.setFlexGrow(0);
        this.add(gridPlaceholder);
    }


}
