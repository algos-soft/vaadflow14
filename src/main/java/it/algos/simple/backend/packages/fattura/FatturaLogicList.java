package it.algos.simple.backend.packages.fattura;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import it.algos.simple.ui.enumeration.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.*;
import it.algos.vaadflow14.ui.button.*;
import it.algos.vaadflow14.ui.enumeration.*;
import it.algos.vaadflow14.ui.interfaces.*;
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
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneModulo;

    /**
     * Flag di preferenza per l' utilizzo del bottone. Di default false. <br>
     */
    protected boolean usaBottoneStatistiche;


    /**
     * Flag di preferenza per specificare il titolo del modulo wiki da mostrare in lettura <br>
     */
    protected String fatturaModuloTitle;


    /**
     * Flag di preferenza per specificare il titolo della pagina wiki da mostrare in lettura <br>
     */
    protected String fatturaStatisticheTitle;

    /**
     * Costruttore con parametro <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     * Il framework SpringBoot/Vaadin con l'Annotation @Autowired inietta automaticamente un riferimento al singleton xxxService <br>
     * L'annotation @Autowired potrebbe essere omessa perché c'è un solo costruttore <br>
     * Usa un @Qualifier perché la classe AService è astratta ed ha diverse sottoclassi concrete <br>
     */
    public FatturaLogicList(@Autowired @Qualifier("FatturaService") final AIService entityService) {
        super(Fattura.class, entityService);
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
        super.usaBottonePaginaWiki = true;
        super.usaBottoneDownload = true;
        super.usaBottoneUpload = true;

        super.maxNumeroBottoniPrimaRiga = 5;

        super.wikiPageTitle = "Piozzano";
        this.usaBottoneModulo = true;
        this.usaBottoneStatistiche = true;
        this.fatturaModuloTitle = "Modulo:Bio/Plurale_attività";
        this.fatturaStatisticheTitle = "Progetto:Biografie/Attività";
    }

    /**
     * Regola il modo di presentare la scheda (Form) prima di lanciare la @Route. <br>
     * 1) Usa l'annotation @AIForm.operationForm() nella AEntity del package <br>
     * - nel package la classe AEntity esiste sempre <br>
     * - se esiste l'annotation, la usa <br>
     * - valore fisso per tutto il programma <br>
     * - se non esiste l'annotation, viene comunque gestito un valore di default <br>
     * 2) Si può modificare il valore di operationForm in xxxLogicList.fixOperationForm(); <br>
     * - nel package la classe xxxLogicList è facoltativa <br>
     * - se esiste la classe specifica xxxLogicList, può regolare il valore <br>
     * - per differenziarlo ad esempio in base all'utente collegato <br>
     * - se manca la classe specifica xxxLogicList nel package, usa il valore della AEntity <br>
     * 3) Il valore viene usato da executeRoute() di questa xxxLogicList <br>
     * - viene passato alla @Route come parametro KEY_FORM_TYPE <br>
     * - viene estratto da routeParameter in setParameter() della xxxLogicForm <br>
     * - viene recepito in fixTypeView() della xxxLogicForm <br>
     * 4) Potrebbe eventualmente essere modificato anche in xxxLogicForm.fixPreferenze(); <br>
     * - nel package la classe xxxLogicForm è facoltativa <br>
     * - se esiste la classe specifica xxxLogicForm, può regolare il valore <br>
     * - se manca la classe specifica xxxLogicForm nel package, usa il valore della @Route <br>
     * Può essere sovrascritto senza invocare il metodo della superclasse <br>
     */
    @Override
    protected void fixOperationForm() {
        this.operationForm = AEOperation.editProfile;
    }

    /**
     * Costruisce una lista (eventuale) di 'span' da mostrare come header della view <br>
     * DEVE essere sovrascritto, senza invocare il metodo della superclasse <br>
     *
     * @return una lista di elementi html di tipo 'span'
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
     * Costruisce una lista di bottoni (enumeration) <br>
     * Di default costruisce (come da flag) i bottoni 'delete' e 'reset' <br>
     * Nella sottoclasse possono essere aggiunti i bottoni specifici dell'applicazione <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected List<AIButton> getListaAEBottoniTop() {
        List<AIButton> listaBottoni = super.getListaAEBottoniTop();

        if (usaBottoneModulo) {
            listaBottoni.add(AESimpleButton.modulo);
        }

        if (usaBottoneStatistiche) {
            listaBottoni.add(AESimpleButton.statistiche);
        }

        return listaBottoni;
    }

    /**
     * Costruisce un (eventuale) layout per i bottoni sotto la Grid <br>
     * Può essere sovrascritto senza invocare il metodo della superclasse <br>
     */
    @Override
    protected void fixBottomLayout() {
        List<AIButton> listaAEBottoni = Collections.singletonList(AEButton.upload);
        List<Button> listaBottoniSpecifici = Collections.singletonList(new Button("Bottoni su un'unica riga sotto la Grid"));
        WrapButtons wrapper = appContext.getBean(WrapButtons.class, this, listaAEBottoni, null, listaBottoniSpecifici);
        bottomLayout = appContext.getBean(ABottomLayout.class, wrapper);

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

    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     * Interfaccia utilizzata come parametro per poter sovrascrivere il metodo <br>
     * Nella classe base eseguirà un casting a AEAction <br>
     * Nella (eventuale) sottoclasse specifica del progetto eseguirà un casting a AExxxAction <br>
     *
     * @param iAzione interfaccia dell'azione selezionata da eseguire
     *
     * @return false se il parametro non è una enumeration valida o manca lo switch
     */
    @Override
    public boolean performAction(AIAction iAzione) {
        boolean status = super.performAction(iAzione);
        AESimpleAction azione = iAzione instanceof AESimpleAction ? (AESimpleAction) iAzione : null;

        if (azione == null) {
            return false;
        }

        if (status) {
            return true;
        }
        else {
            status = true;
            switch (azione) {
                case modulo:
                    openWikiPage(fatturaModuloTitle);
                    break;
                case statistiche:
                    openWikiPage(fatturaStatisticheTitle);
                    break;
                default:
                    status = false;
                    break;
            }
        }

        return status;
    }

}
