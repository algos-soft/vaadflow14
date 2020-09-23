package it.algos.simple.backend.packages;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.anagrafica.via.Via;
import it.algos.vaadflow14.backend.packages.anagrafica.via.ViaLogic;
import it.algos.vaadflow14.ui.fields.AComboField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 11-set-2020
 * Time: 07:22
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Delta ; <br>
 * 2) StaticContextAccessor.getBean(Delta.class) (senza parametri) <br>
 * 3) appContext.getBean(Delta.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeltaLogic extends ALogic {

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
    public ViaLogic viaLogic;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public DeltaLogic() {
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
    public DeltaLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Delta.class;
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


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param keyPropertyValue obbligatorio
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public Delta creaIfNotExist(String keyPropertyValue) {
        return (Delta) checkAndSave(newEntity(keyPropertyValue));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Delta newEntity() {
        return newEntity(VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Delta newEntity(String code) {
        Delta newEntityBean = Delta.builderDelta()

                .code(text.isValid(code) ? code : null)

                .build();

        return (Delta) fixKey(newEntityBean);
    }


    /**
     * Save proveniente da un click sul bottone 'registra' del Form. <br>
     * La entityBean viene recuperare dal form <br>
     *
     * @return true se la entity è stata registrata o definitivamente scartata; esce dal dialogo
     * .       false se manca qualche field e la situazione è recuperabile; resta nel dialogo
     */
    @Override
    public boolean saveDaForm() {
        Delta entityBean = null;
        if (currentForm != null) {
            entityBean = (Delta) currentForm.getValidBean();
        }

        AComboField combo = (AComboField) currentForm.fieldsMap.get("via");
        ComboBox box = combo.comboBox;
        Object obj = box.getValue();
        if (obj instanceof String) {
            Via via = viaLogic.creaIfNotExist((String)obj);
            entityBean.via = via;
        }

        return entityBean != null ? save(entityBean) : false;
    }


}