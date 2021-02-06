package it.algos.vaadflow14.backend.packages.crono.giorno;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.AIScript;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.packages.crono.CronoLogic;
import it.algos.vaadflow14.backend.service.AIService;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 14-ago-2020
 * Time: 15:27
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
public class GiornoLogic extends CronoLogic {


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
    public GiornoLogic(AIService entityService, AEOperation operationForm) {
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

        blu.add("Giorni dell' anno. 366 giorni per tenere conto dei 29 giorni di febbraio negli anni bisestili");
        if (AEPreferenza.usaDebug.is()) {
            red.add("Bottoni 'DeleteAll', 'Reset', 'New' (e anche questo avviso) solo in fase di debug. Sempre presente bottone 'Esporta' e comboBox selezione 'Mese'");
        }

        return new AlertWrap(null, blu, red, false);
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox("mese");
    }

}