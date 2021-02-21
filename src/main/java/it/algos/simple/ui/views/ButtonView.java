package it.algos.simple.ui.views;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.button.*;
import it.algos.vaadflow14.ui.enumeration.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;

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
    private ATextService text;


    /**
     * Instantiates a new ButtonView.
     */
    @PostConstruct
    private void postConstruct() {
        this.disegnaBottoni();
    }

    private void disegnaBottoni() {
        Button button;

        for (AEButton bottone : AEButton.values()) {
            button = FactoryButton.get(bottone);
            this.add(new HorizontalLayout(button, new Label(bottone.toolTip)));
        }
    }

}
