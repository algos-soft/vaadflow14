package it.algos.simple.backend.packages.fattura;

import com.vaadin.flow.router.*;
import it.algos.vaadflow14.ui.button.*;
import it.algos.vaadflow14.ui.enumeration.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 26-feb-2021
 * Time: 17:06
 */
@Route(value = "fattura")
public class FatturaView extends LogicView {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public FatturaService fatturaService;

    /**
     * Instantiates a new ButtonView.
     */
    @PostConstruct
    private void postConstruct() {
//        super.entityClazz = Fattura.class;
//        super.entityService = fatturaService;
//        super.initView();

        this.disegnaBottoni();
    }

    private void disegnaBottoni() {
        this.add(FactoryButton.get(AEButton.resetList));
    }

}
