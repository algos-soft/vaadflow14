package it.algos.vaadflow14.backend.packages.geografica.regione;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.packages.geografica.*;
import it.algos.vaadflow14.backend.packages.geografica.stato.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 10:38
 * <p>
 * Classe specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * Collegamento tra le views (List, Form) e il 'backend'. Mantiene lo ''stato' <br>
 * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName), entityService, operationForm) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AIScript(sovraScrivibile = false)
public class RegioneLogicOld extends GeografiaLogicOld {

    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public StatoService statoService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AWikiService wiki;

    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName), entityService, operationForm) <br>
     *
     * @param entityService layer di collegamento tra il 'backend' e mongoDB
     * @param operationForm tipologia di Form in uso
     */
    public RegioneLogicOld(AIService entityService, AEOperation operationForm) {
        super(entityService, operationForm);
    }


    /**
     * Preferenze standard <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.operationForm = AEPreferenza.usaDebug.is() ? AEOperation.edit : AEOperation.showOnly;
        super.usaBottoneDelete = AEPreferenza.usaDebug.is();
        super.usaBottoneResetList = AEPreferenza.usaDebug.is();
        super.usaBottoneNew = AEPreferenza.usaDebug.is();
        super.usaBottonePaginaWiki = true;
        super.searchType = AESearch.editField;
        super.wikiPageTitle = "ISO_3166-2";
    }



    /**
     * Informazioni (eventuali) specifiche di ogni modulo, mostrate nella List <br>
     * Costruisce una liste di 'span' per costruire l' istanza di AHeaderSpan <br>
     * DEVE essere sovrascritto <br>
     *
     * @return una liste di 'span'
     */
    protected List<Span> getSpanList() {
        List<Span> lista = new ArrayList<>();

        lista.add(html.getSpanBlu("Suddivisioni geografica di secondo livello. Codifica secondo ISO 3166-2"));
        lista.add(html.getSpanBlu("Recuperati dalla pagina wiki: " + wikiPageTitle));
        lista.add(html.getSpanBlu("Codice ISO, sigla abituale e 'status' normativo"));
        lista.add(html.getSpanBlu("Ordinamento alfabetico: prima Italia poi altri stati europei"));
        if (AEPreferenza.usaDebug.is()) {
            lista.add(html.getSpanRosso("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente il searchField ed i comboBox 'Stato' e 'Status'"));
        }

        return lista;
    }

    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {

        if (AEPreferenza.usaBandiereStati.is()) {
            mappaComboBox.put("stato", statoService.creaComboStati());//@todo con bandierine
        }
        else {
            super.creaComboBox("stato", AEStato.italia.getStato());//@todo senza bandierine
        }

        super.creaComboBox("status", 14);
    }

}