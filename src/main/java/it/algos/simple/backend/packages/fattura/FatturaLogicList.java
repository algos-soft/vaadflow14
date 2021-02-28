package it.algos.simple.backend.packages.fattura;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.ui.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 26-feb-2021
 * Time: 17:06
 */
@Route(value = "fattura", layout = MainLayout.class)
public class FatturaLogicList extends LogicList {

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

    }

    /**
     * Costruisce una lista (eventuale) di 'span' da mostrare come header della view <br>
     * DEVE essere sovrascritto <br>
     *
     * @return una liste di 'span'
     */
    @Override
    protected List<Span> getSpanList() {
        boolean singola = false;

        //--Riga singola, oppure righe multiple
        if (singola) {
            return Collections.singletonList(html.getSpanVerde("Esempio verde."));
        }
        else {
            List<Span> lista = new ArrayList<>();
            lista.add(html.getSpanBlu("Esempio di label realizzato con 'span' di colore blue"));
            lista.add(html.getSpanVerde("Scritta verde"));
            lista.add(html.getSpanVerde("Seconda scritta verde bold", AETypeWeight.bold));
            lista.add(html.getSpanRosso("Scritta rossa"));
            return lista;
        }
    }

}
