package it.algos.vaadflow14.backend.packages.geografica.regione;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.packages.geografica.stato.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Project: vaadflow14 <br>
 * Created by Algos <br>
 * User: gac <br>
 * Fix date: ven, 12-mar-2021 <br>
 * Fix time: 7:36 <br>
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
@Route(value = "regione", layout = MainLayout.class)
@AIScript(sovraScrivibile = false)
public class RegioneLogicList extends LogicList {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public StatoService statoService;

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Costruttore con parametro <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     * Il framework SpringBoot/Vaadin con l'Annotation @Autowired inietta automaticamente un riferimento al singleton xxxService <br>
     * L'annotation @Autowired potrebbe essere omessa perché c'è un solo costruttore <br>
     * Usa un @Qualifier perché la classe AService è astratta ed ha diverse sottoclassi concrete <br>
     * Regola (nella superclasse) la entityClazz (final) associata a questa logicView <br>
     *
     * @param entityService (@Autowired) (@Qualifier) riferimento al service specifico correlato a questa istanza (prototype) di LogicList
     */
    public RegioneLogicList(@Autowired @Qualifier("regioneService") final AIService entityService) {
        super(entityService, Regione.class);
    }// end of Vaadin/@Route constructor


    /**
     * Preferenze usate da questa 'logica' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.operationForm = AEPreferenza.usaDebug.is() ? AEOperation.edit : AEOperation.showOnly;
        super.usaBottoneDeleteAll = AEPreferenza.usaDebug.is();
        super.usaBottoneResetList = AEPreferenza.usaDebug.is();
        super.usaBottoneNew = AEPreferenza.usaDebug.is();
        super.usaBottonePaginaWiki = true;
//        super.searchType = AESearch.editField;//@todo Funzionalità ancora da implementare
//        super.wikiPageTitle = "ISO_3166-2";//@todo Funzionalità ancora da implementare
    }



    /**
     * Costruisce una lista (eventuale) di 'span' da mostrare come header della view <br>
     * DEVE essere sovrascritto, senza invocare il metodo della superclasse <br>
     */
    @Override
    protected void fixSpanList() {
        addSpanBlu("Suddivisioni geografica di secondo livello. Codifica secondo ISO 3166-2");
        addSpanBlu("Codice ISO, sigla abituale e 'status' normativo");
        addSpanBlu("Ordinamento alfabetico: prima Italia poi altri stati europei");
        addSpanRosso("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente il searchField ed i comboBox 'Stato' e 'Status'");
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
//    @Override
    protected void fixMappaComboBox() {

//        if (AEPreferenza.usaBandiereStati.is()) {
//            mappaComboBox.put("stato", statoService.creaComboStati());//@todo con bandierine
//        }
//        else {
//            super.creaComboBox("stato", AEStato.italia.getStato());//@todo senza bandierine
//        }
//
//        super.creaComboBox("status", 14);
    }

}// end of Route class