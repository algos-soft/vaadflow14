package it.algos.simple.backend.packages.fattura;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.*;
import it.algos.vaadflow14.ui.button.*;
import it.algos.vaadflow14.ui.enumeration.*;
import org.springframework.beans.factory.annotation.*;

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
     * Costruttore senza parametri <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     */
    public FatturaLogicList(@Qualifier("FatturaService") AIService fatturaService)  {
        super.entityClazz = FatturaEntity.class;
        super.entityService = fatturaService ;
    }// end of Vaadin/@Route constructor


    /**
     * Preferenze usate da questa 'logica' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneDeleteAll = true;
        super.usaBottoneResetList = true;
        super.usaBottoneNew = true;
        super.usaBottoneSearch = false;
        super.usaBottoneExport = true;
        super.usaBottonePaginaWiki = false;
        super.usaBottoneUpdate = true;
        super.usaBottoneUpload = false;
        super.usaBottoneDownload = true;
        super.usaBottoneElabora = false;
        super.usaBottoneCheck = false;
        super.usaBottoneModulo = true;
        super.usaBottoneTest = false;
        super.usaBottoneStatistiche = false;
        super.maxNumeroBottoniPrimaRiga = 7;
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

    /**
     * Costruisce un (eventuale) layout per i bottoni sotto la Grid <br>
     * Può essere sovrascritto senza invocare il metodo della superclasse <br>
     */
    @Override
    protected void fixBottomLayout() {
        List<AEButton> listaAEBottoni = Collections.singletonList(AEButton.statistiche);
        List<Button> listaBottoniSpecifici = Collections.singletonList(new Button("Bottoni su un'unica riga sotto la Grid"));
        WrapButtons wrapper = appContext.getBean(WrapButtons.class, this, listaAEBottoni, null, listaBottoniSpecifici);
        AButtonLayout bottomLayout = appContext.getBean(ABottomLayout.class, wrapper);

        if (bottomPlaceHolder != null && bottomLayout != null) {
            bottomPlaceHolder.add(bottomLayout);
        }
    }

    /**
     * Costruisce un (eventuale) layout per scritte in basso della pagina <br>
     * Può essere sovrascritto senza invocare il metodo della superclasse <br>
     */
    @Override
    protected void fixFooterLayout() {
        Span span = html.getSpanVerde("Riga di informazioni sotto la Grid.", AETypeWeight.bold, AETypeSize.small);

        if (footerPlaceHolder != null && span != null) {
            footerPlaceHolder.add(span);
        }
    }

}
