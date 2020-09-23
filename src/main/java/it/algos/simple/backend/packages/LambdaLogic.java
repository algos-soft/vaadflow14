package it.algos.simple.backend.packages;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.ui.form.AGenericForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 20-set-2020
 * Time: 06:21
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Lambda ; <br>
 * 2) StaticContextAccessor.getBean(Lambda.class) (senza parametri) <br>
 * 3) appContext.getBean(Lambda.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LambdaLogic extends ALogic {


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
    public LambdaLogic() {
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
    public LambdaLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Lambda.class;
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
//        this.formClazz = LambdaForm.class;
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param keyPropertyValue obbligatorio
     *
     * @return la nuova entity appena creata e salvata
     */
    public Lambda creaIfNotExist(String keyPropertyValue) {
        return (Lambda) checkAndSave(newEntity(keyPropertyValue));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Lambda newEntity() {
        return newEntity(VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Lambda newEntity( String uno) {//@TODO: Le properties riportate sono INDICATIVE e debbono essere sostituite
        Lambda newEntityBean = Lambda.builderLambda()

                .uno(text.isValid(uno) ? uno : null)

                .build();

        return (Lambda) fixKey(newEntityBean);
    }

}