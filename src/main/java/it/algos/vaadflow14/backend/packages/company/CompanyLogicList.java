package it.algos.vaadflow14.backend.packages.company;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.ui.*;

import java.util.*;

/**
 * Project: vaadflow14 <br>
 * Created by Algos <br>
 * User: gac <br>
 * Fix date: ven, 12-mar-2021 <br>
 * Fix time: 7:24 <br>
 * <p>
 * Classe (facoltativa) di un package con personalizzazioni <br>
 * Se manca, si usa la classe GenericLogicList con @Route <br>
 * Gestione della 'business logic' e della 'grafica' di @Route <br>
 * Mantiene lo 'stato' <br>
 * L' istanza (PROTOTYPE) viene creata ad ogni chiamata del browser <br>
 * Eventuali parametri (opzionali) devono essere passati nell'URL <br>
 * <p>
 * Annotated with @Route (obbligatorio) <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@Route(value = "company", layout = MainLayout.class)
@AIScript(sovraScrivibile = false)
public class CompanyLogicList extends LogicList {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Costruttore senza parametri <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     */
    public CompanyLogicList() {
        super.entityClazz = Company.class;
    }// end of Vaadin/@Route constructor


    /**
     * Preferenze usate da questa 'logica' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        if (FlowVar.usaCompany) {
            if (vaadinService.isDeveloper()) {
                super.usaBottoneDeleteAll = true;
                super.usaBottoneResetList = true;

            }
            if (!vaadinService.isAdminOrDeveloper()) {
                super.operationForm = AEOperation.showOnly;
            }

            super.usaBottoneNew = vaadinService.isAdminOrDeveloper();
            super.usaBottoneExport = vaadinService.isAdminOrDeveloper();
        }
        else {
            if (AEPreferenza.usaDebug.is()) {
                super.usaBottoneDeleteAll = true;
                super.usaBottoneResetList = true;
            }
            super.usaBottoneNew = true;
        }
    }


    /**
     * Costruisce una lista (eventuale) di 'span' da mostrare come header della view <br>
     * DEVE essere sovrascritto <br>
     *
     * @return una liste di 'span'
     */
    @Override
    protected List<Span> getSpanList() {
        List<Span> lista = new ArrayList<>();

        if (AEPreferenza.usaDebug.is()) {
            lista.add(html.getSpanRosso("Bottoni 'DeleteAll', 'Reset' (e anche questo avviso) solo in fase di debug. Sempre presente bottone 'New'"));
            lista.add(html.getSpanRosso("Di norma utilizzato solo in applicazioni con usaCompany=true"));
        }

        return lista;
    }


}// end of Route class