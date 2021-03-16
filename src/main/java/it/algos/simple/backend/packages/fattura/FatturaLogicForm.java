package it.algos.simple.backend.packages.fattura;

import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 01-mar-2021
 * Time: 18:41
 */
@Route(value = "fatturaForm", layout = MainLayout.class)
public class FatturaLogicForm extends LogicForm {


    /**
     * Costruttore senza parametri <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     */
    public FatturaLogicForm(@Qualifier("FatturaService") AIService fatturaService) {
        super.entityClazz = Fattura.class;
        super.entityService = fatturaService;
    }// end of Vaadin/@Route constructor

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        super.beforeEnter(beforeEnterEvent);
    }

    //    /**
//     * Costruisce una lista (eventuale) di 'span' da mostrare come header della view <br>
//     * DEVE essere sovrascritto <br>
//     *
//     * @return una liste di 'span'
//     */
//    @Override
//    protected List<Span> getSpanList() {
//        boolean singola = false;
//
//        //--Riga singola, oppure righe multiple
//        if (singola) {
//            return Collections.singletonList(html.getSpanVerde("Esempio verde."));
//        }
//        else {
//            List<Span> lista = new ArrayList<>();
//            lista.add(html.getSpanVerde("Siamo nel Form", AETypeWeight.bold));
//            lista.add(html.getSpanRosso("Evviva"));
//            return lista;
//        }
//    }

}
