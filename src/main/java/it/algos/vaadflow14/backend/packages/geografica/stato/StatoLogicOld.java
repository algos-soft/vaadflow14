package it.algos.vaadflow14.backend.packages.geografica.stato;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.packages.geografica.*;
import it.algos.vaadflow14.backend.packages.geografica.regione.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 06:54
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
public class StatoLogicOld extends GeografiaLogicOld {

    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName), entityService, operationForm) <br>
     *
     * @param entityService layer di collegamento tra il 'backend' e mongoDB
     * @param operationForm tipologia di Form in uso
     */
    public StatoLogicOld(AIService entityService, AEOperation operationForm) {
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
        boolean debug = AEPreferenza.usaDebug.is();
        super.fixPreferenze();

        super.operationForm = debug ? AEOperation.edit : AEOperation.showOnly;
        super.usaBottoneDelete = debug;
        super.usaBottoneResetList = debug;
        super.usaBottoneNew = debug;
        super.usaBottonePaginaWiki = true;
        super.searchType = AESearch.editField;
        super.usaBottoneResetForm = debug;
        super.wikiPageTitle = "ISO_3166-1";
        super.usaBottoniSpostamentoForm = true;
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

        lista.add(html.getSpanBlu("Stati del mondo. Codifica secondo ISO 3166-1"));
        lista.add(html.getSpanBlu("Recuperati dalla pagina wiki: " + wikiPageTitle));
        lista.add(html.getSpanBlu("Codici: numerico, alfa-due, alfa-tre e ISO locale"));
        lista.add(html.getSpanBlu("Ordinamento alfabetico: prima Italia, UE e poi gli altri"));
        if (AEPreferenza.usaDebug.is()) {
            lista.add(html.getSpanRosso("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente il searchField"));
        }

        return lista;
    }

    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox("continente", "europa");
    }


    /**
     * Costruisce una lista ordinata di nomi delle properties del Form. <br>
     * La lista viene usata per la costruzione automatica dei campi e l' inserimento nel binder <br>
     * Nell' ordine: <br>
     * 1) Cerca nell' annotation @AIForm della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica di xxxLogic <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Se serve, modifica l' ordine della lista oppure esclude una property che non deve andare nel binder <br>
     *
     * @return lista di nomi di properties
     */
    @Override
    public List<String> getFormPropertyNamesList() {
        String propertyStato = "stato";
        String tagRegioni = "regioni";
        boolean esistonoRegioni = false;
        List<String> lista = super.getFormPropertyNamesList();

        if (AEPreferenza.usaDebug.is()) {
            return lista;
        }

        esistonoRegioni = mongo.esistono(Regione.class, propertyStato, entityBean);
        if (!esistonoRegioni && lista.contains(tagRegioni)) {
            lista.remove(tagRegioni);
        }

        return lista;
    }


    /**
     * Azione proveniente dal click sul bottone Prima <br>
     * Recupera la lista FILTRATA e ORDINATA delle properties, ricevuta dalla Grid <br>@todo da realizzare
     * Si sposta alla precedente <br>
     * Carica il form relativo <br>
     */
    protected void prima(final AEntity currentEntityBean) {
        AEntity previousEntityBean = mongo.findPrevious(entityClazz, currentEntityBean.id);
        executeRoute(previousEntityBean);
    }


//    public void resetForm(final AEntity entityBean) {
//        AIResult result;
//        RegioneLogic regioneLogic = appContext.getBean(RegioneLogic.class);
//        result = regioneLogic.creaRegioniDiUnoStato((Stato) entityBean);
//        logger.log(AETypeLog.reset, result.getMessage());
//    }


}