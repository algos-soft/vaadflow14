package it.algos.simple.backend.packages;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 05-set-2020
 * Time: 07:24
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Gamma ; <br>
 * 2) StaticContextAccessor.getBean(Gamma.class) (senza parametri) <br>
 * 3) appContext.getBean(Gamma.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GammaLogicOld extends ALogicOld {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public GammaLogicOld() {
        //        this(AEOperation.edit);
    }


    /**
     * Costruttore con parametro <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AFormView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName), operationForm) <br>
     *
     * @param operationForm tipologia di Form in uso
     */
    public GammaLogicOld(AIService entityService, AEOperation operationForm) {
        super(entityService, operationForm);
    }

    //    public GammaLogic(AEOperation operationForm) {
    //        super(operationForm);
    //        super.entityClazz = Gamma.class;
    //    }


    /**
     * Preferenze standard <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaBottoneDelete = true;
        this.usaBottoneResetList = true;
        this.usaBottoneNew = true;
        this.usaBottoneSearch = true;
        this.usaBottoneExport = true;
        this.usaBottonePaginaWiki = true;
        //        this.usaBottoneUpdate = true;
        //        this.usaBottoneUpload = true;
        //        this.usaBottoneDownload = true;
        //        this.usaBottoneElabora = true;
        //        this.usaBottoneCheck = true;
        //        this.usaBottoneModulo = true;
        //        this.usaBottoneTest = true;
        //        this.usaBottoneStatistiche = true;
        this.maxNumeroBottoniPrimaRiga = 6;
    }

    /**
     * Informazioni (eventuali) specifiche di ogni modulo, mostrate nella List <br>
     * Costruisce una liste di 'span' per costruire l' istanza di AHeaderSpan <br>
     * DEVE essere sovrascritto <br>
     *
     * @return una liste di 'span'
     */
    protected List<Span> getSpanList() {
        String message = "Codifica delle più comuni tipologie di indirizzi. Presentate nelle anagrafiche in un popup di selezione.";
        return Collections.singletonList(html.getSpanVerde(message));
    }


    /**
     * Costruisce una mappa di ComboBox di selezione e filtro <br>
     * DEVE essere sovrascritto nella sottoclasse <br>
     */
    @Override
    protected void fixMappaComboBox() {
        super.creaComboBox("mese");
        super.creaComboBox("secolo");
    }

    /**
     * Costruisce una lista di bottoni specifici <br>
     * Di default non costruisce nulla <br>
     * Deve essere sovrascritto. Invocare PRIMA il metodo della superclasse <br>
     */
    protected List<Button> getListaBottoniSpecifici() {
        return Collections.singletonList(new Button("Specifico"));
    }

    //    /**
    //     * Creazione in memoria di una nuova entity che NON viene salvata <br>
    //     * Usa il @Builder di Lombok <br>
    //     * Eventuali regolazioni iniziali delle property <br>
    //     *
    //     * @return la nuova entity appena creata (non salvata)
    //     */
    //    public Gamma newEntity(String code) {
    //        Gamma newEntityBean = Gamma.builderGamma()
    //
    //                .code(text.isValid(code) ? code : null)
    //
    //                .build();
    //
    //        return (Gamma) fixKey(newEntityBean);
    //    }

}