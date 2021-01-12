package it.algos.vaadflow14.backend.packages.anagrafica.via;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.service.AIService;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 10-set-2020
 * Time: 11:30
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Via ; <br>
 * 2) StaticContextAccessor.getBean(Via.class) (senza parametri) <br>
 * 3) appContext.getBean(Via.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ViaLogic extends ALogic {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


//    /**
//     * Costruttore senza parametri <br>
//     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
//     * Costruttore usato da AListView <br>
//     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName), entityService) <br>
//     */
//    public ViaLogic(AIService entityService) {
//        this(entityService, AEOperation.listNoForm);
//    }


    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName), entityService, operationForm) <br>
     *
     * @param operationForm tipologia di Form in uso
     */
    public ViaLogic(AIService entityService, AEOperation operationForm) {
        super(operationForm);
        super.entityService = entityService;
        super.entityClazz = entityService.getEntityClazz();
    }

    /**
     * Preferenze usate da questo service <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneDelete = true;
        super.usaBottoneResetList = true;
    }


    /**
     * Informazioni (eventuali) specifiche di ogni modulo, mostrate nella List <br>
     * Costruisce un wrapper di liste di informazioni per costruire l' istanza di AHeaderWrap <br>
     * DEVE essere sovrascritto <br>
     *
     * @return wrapper per passaggio dati
     */
    @Override
    protected AlertWrap getAlertWrapList() {
        return new AlertWrap("Codifica delle più comuni tipologie di indirizzi. Presentate nelle anagrafiche in un popup di selezione.");
    }

}