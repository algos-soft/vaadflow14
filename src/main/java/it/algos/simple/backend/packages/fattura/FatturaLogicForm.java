package it.algos.simple.backend.packages.fattura;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 01-mar-2021
 * Time: 18:41
 */
@Route(value = ROUTE_NAME_GENERIC_FORM, layout = MainLayout.class)
public class FatturaLogicForm extends LogicForm {


    /**
     * Costruttore senza parametri <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     */
    public FatturaLogicForm(@Qualifier("FatturaService") AIService fatturaService) {
        super.entityClazz = FatturaEntity.class;
        super.entityService = fatturaService;
    }// end of Vaadin/@Route constructor

    /**
     * Preferenze usate da questa 'logica' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
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
            lista.add(html.getSpanVerde("Siamo nel Form", AETypeWeight.bold));
            lista.add(html.getSpanRosso("Evviva"));
            return lista;
        }
    }

}
