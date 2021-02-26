package it.algos.vaadflow14.backend.packages.geografica.provincia;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.packages.geografica.*;
import it.algos.vaadflow14.backend.packages.geografica.regione.*;
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
 * Date: mar, 15-set-2020
 * Time: 17:59
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
public class ProvinciaLogicOld extends GeografiaLogicOld {

    public static final String FIELD_REGIONE = "regione";

    public static final String FIELD_STATO = "stato";

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
    public RegioneLogicOld regioneLogic;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public StatoLogicOld statoLogic;


    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName), entityService, operationForm) <br>
     *
     * @param entityService layer di collegamento tra il 'backend' e mongoDB
     * @param operationForm tipologia di Form in uso
     */
    public ProvinciaLogicOld(AIService entityService, AEOperation operationForm) {
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

        super.usaBottoneDelete = false;
        super.usaBottoneResetList = true;
        super.usaBottoneNew = true;
        super.usaBottonePaginaWiki = true;
        super.searchType = AESearch.editField;
        super.wikiPageTitle = "ISO_3166-2";
        super.formClazz = ProvinciaForm.class;
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

        lista.add(html.getSpanBlu("Province italiane. Codifica secondo ISO 3166-2."));
        lista.add(html.getSpanBlu("Recuperati dalla pagina wiki: " + wikiPageTitle));
        lista.add(html.getSpanBlu("Codice ISO, sigla abituale e 'status' normativo"));
        lista.add(html.getSpanBlu("Ordinamento alfabetico: prima le città metropolitane e poi le altre"));

        return lista;
    }

    /**
     * Operazioni eseguite PRIMA di save o di insert <br>
     * Regolazioni automatiche di property <br>
     * Controllo della validità delle properties obbligatorie <br>
     * Controllo per la presenza della company se FlowVar.usaCompany=true <br>
     * Controlla se la entity registra le date di creazione e modifica <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, Edit)
     *
     * @return the modified entity
     */
    //    @Override
    public AEntity beforeSave2(AEntity entityBean, AEOperation operation) {
        Provincia provincia = (Provincia) super.beforeSave(entityBean, operation);

        //--controllla la congruità dei due comboBox: master e slave
        //--dovrebbe già essere controllato nel Form, ma meglio ricontrollare
        if (provincia.regione.stato.id.equals(provincia.stato.id)) {
            return provincia;
        }
        else {
            Notification.show("La regione " + provincia.regione.divisione + " non appartiene allo stato " + provincia.stato.stato + " e non è stata registrata", 3000, Notification.Position.MIDDLE);
            logger.error("La regione " + provincia.regione.divisione + " non appartiene allo stato " + provincia.stato.stato, this.getClass(), "beforeSave");
            return null;
        }
    }

}