package it.algos.simple.ui.views;

import com.vaadin.componentfactory.combobox.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.packages.geografica.stato.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.button.*;
import it.algos.vaadflow14.ui.enumeration.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 21-feb-2021
 * Time: 18:06
 */
@Route(value = "buttonView")
public class ButtonView extends VerticalLayout {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    private TextService text;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    private MongoService mongo;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public DataProviderService provider;

    /**
     * Instantiates a new ButtonView.
     */
    @PostConstruct
    private void postConstruct() {
//        this.disegnaBottoni();
        //        this.add(new Label("Pippoz"));
        //        ComboBox<String> comboBox = new V8ComboBox<>("Select an option", "Option 1", "Option 2", "Option 3");
        //        comboBox.setClearButtonVisible(true);
        //        comboBox.setLabel("Forse");
        //        comboBox.getElement();
        //        this.add(comboBox);

        List items = null;
        List items2 = null;
        try {
            items = mongo.fetch(Stato.class);
            items2 = mongo.fetch(Stato.class);
        } catch (AlgosException unErrore) {
        }

        ComboBox<String> comboBox = new ComboBox<>("Items");
        comboBox.setWidth("400px");
        comboBox.setItems(items);
        this.add(comboBox);

        DataProvider dataProvider;
        dataProvider =  provider.creaDataProvider(Stato.class);
        ComboBox<String> comboBox2 = new ComboBox<>("Data provider");
        comboBox2.setWidth("400px");
        comboBox2.setDataProvider(dataProvider);
        this.add(comboBox2);


//        V8ComboBox<String> comboBox2 = new V8ComboBox<>("Altra prova");
//        comboBox2.setWidth("400px");
//        comboBox2.setItems(items);
//        this.add(comboBox2);

    }

    private void disegnaBottoni() {
        Button button;

        for (AEButton bottone : AEButton.values()) {
            button = FactoryButton.get(bottone);
            this.add(new HorizontalLayout(button, new Label(bottone.toolTip)));
        }
    }

}
