package it.algos.vaadflow14.backend.packages.crono.mese;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.packages.crono.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.header.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 31-lug-2020
 * Time: 22:07
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
public class MeseLogic extends CronoLogic {


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
    public MeseLogic(AIService entityService, AEOperation operationForm) {
        super(entityService, operationForm);
    }


    /**
     * Informazioni (eventuali) specifiche di ogni modulo, mostrate nella List <br>
     * Costruisce un wrapper di liste d'informazioni per costruire l' istanza di AHeaderWrap <br>
     * DEVE essere sovrascritto <br>
     *
     * @return wrapper per passaggio dati
     */
    @Override
    protected AlertWrap getAlertWrapList() {
        List<String> blu = new ArrayList<>();
        List<String> red = new ArrayList<>();

        blu.add("Mesi dell' anno, coi giorni. Tiene conto degli anni bisestili per il mese di febbraio.");
        blu.add("Ci sono 12 mesi. Non si possono cancellare ne aggiungere elementi.");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset' e 'New' (e anche questo avviso) solo in fase di debug. Sempre presente bottone 'Esporta'");
        }

        return new AlertWrap(null, blu, red, false);
    }

}