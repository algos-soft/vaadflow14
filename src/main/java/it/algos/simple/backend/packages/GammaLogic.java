package it.algos.simple.backend.packages;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.preferenza.PreferenzaForm;
import it.algos.vaadflow14.ui.form.AForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

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
public class GammaLogic extends ALogic {


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
    public GammaLogic() {
        this(AEOperation.edit);
    }


    /**
     * Costruttore con parametro <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AFormView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName), operationForm) <br>
     *
     * @param operationForm tipologia di Form in uso
     */
    public GammaLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Gamma.class;
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
    }



//    /**
//     * Costruisce un layout per il Form in bodyPlacehorder della view <br>
//     * <p>
//     * Chiamato da AView.initView() <br>
//     * Costruisce un' istanza dedicata <br>
//     * Passa all' istanza un wrapper di dati <br>
//     * Inserisce l' istanza (grafica) in bodyPlacehorder della view <br>
//     *
//     * @param entityBean interessata
//     *
//     * @return componente grafico per il placeHolder
//     */
//    @Override
//    public AForm getBodyFormLayout(AEntity entityBean) {
//        form = null;
//
//        //--entityBean dovrebbe SEMPRE esistere (anche vuoto), ma meglio controllare
//        if (entityBean != null) {
//            form = appContext.getBean(GammaForm.class, getWrapForm(entityBean));
//        }
//
//        return form;
//    }

    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param code obbligatorio
     *
     * @return la nuova entity appena creata e salvata
     */
    public Gamma crea(String code) {
        return (Gamma)checkAndSave(newEntity( code));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Gamma newEntity() {
        return newEntity( VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Gamma newEntity( String code) {//@TODO: Le properties riportate sono INDICATIVE e debbono essere sostituite
        Gamma newEntityBean = Gamma.builderGamma()

                .code(text.isValid(code) ? code : null)

                .build();

        return (Gamma) fixKey(newEntityBean);
    }

}