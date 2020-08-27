package it.algos.vaadflow14.backend.packages.preferenza;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.entity.ALogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AETypePref;
import it.algos.vaadflow14.ui.form.AForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 25-ago-2020
 * Time: 21:30
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Preferenza ; <br>
 * 2) StaticContextAccessor.getBean(Preferenza.class) (senza parametri) <br>
 * 3) appContext.getBean(Preferenza.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PreferenzaLogic extends ALogic {


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
    public PreferenzaLogic() {
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
    public PreferenzaLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Preferenza.class;
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
     * Costruisce un layout per il Form in bodyPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Costruisce un' istanza dedicata <br>
     * Inserisce l' istanza (grafica) in bodyPlacehorder della view <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public AForm getBodyFormLayout(Class<? extends AEntity> entityClazz) {
        return appContext.getBean(PreferenzaForm.class, entityClazz);
    }


    /**
     * Costruisce un layout per il Form in bodyPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Costruisce un' istanza dedicata <br>
     * Passa all' istanza un wrapper di dati <br>
     * Inserisce l' istanza (grafica) in bodyPlacehorder della view <br>
     *
     * @param entityBean interessata
     *
     * @return componente grafico per il placeHolder
     */
    @Override
    public AForm getBodyFormLayout(AEntity entityBean) {
        return appContext.getBean(PreferenzaForm.class);
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (obbligatoria)
     * @param type        (obbligatorio) per convertire in byte[] i valori
     * @param value       (obbligatorio) memorizza tutto in byte[]
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public boolean crea(String code, String descrizione, AETypePref type, Object value) {
        return checkAndSave(newEntity(code, descrizione, type, value));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Preferenza newEntity() {
        return newEntity(VUOTA, VUOTA, (AETypePref) null, (Object) null);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * <p>
     * //     * @param ordine      di presentazione (obbligatorio con inserimento automatico se è zero)
     *
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (obbligatoria)
     * @param type        (obbligatorio) per convertire in byte[] i valori
     * @param value       (obbligatorio) memorizza tutto in byte[]
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Preferenza newEntity(String code, String descrizione, AETypePref type, Object value) {
        Preferenza newEntityBean = Preferenza.builderPreferenza()

                .ordine(this.getNewOrdine())

                .code(text.isValid(code) ? code : null)

                .descrizione(text.isValid(descrizione) ? descrizione : null)

                .type(type != null ? type : AETypePref.string)

                .value(type != null ? type.objectToBytes(value) : (byte[]) null)

                .build();

        return (Preferenza) fixKey(newEntityBean);
    }


    /**
     * Operazioni eseguite PRIMA di save o di insert <br>
     * Regolazioni automatiche di property <br>
     * Controllo della validità delle properties obbligatorie <br>
     * Controllo per la presenza della company se FlowVar.usaCompany=true <br>
     * Controlla se la entity registra le date di creazione e modifica <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, Edit)
     *
     * @return the modified entity
     */
    @Override
    public AEntity beforeSave(AEntity entityBean, AEOperation operation) {
        Preferenza entityPreferenza = (Preferenza) super.beforeSave(entityBean, operation);

        return fixValue(entityPreferenza);
    }


    /**
     * Regola il valore (obbligatorio) della entity prima di salvarla <br>
     *
     * @param entityPreferenza da regolare prima del save
     *
     * @return true se la entity è stata salvata
     */
    public Preferenza fixValue(Preferenza entityPreferenza) {
        Object value = "mario";
        //        Preferenza entity = setValue(keyCode, value, companyPrefix);
        //
        //        if (entity != null) {
        //            entity = (Preferenza) this.save(entity);
        //            salvata = entity != null;
        //        }// end of if cycle

        if (value == null) {
            entityPreferenza = null;
        }
        entityPreferenza.value = AETypePref.string.objectToBytes(value);

        return entityPreferenza;
    }

}