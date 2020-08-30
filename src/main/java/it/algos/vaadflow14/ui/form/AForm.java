package it.algos.vaadflow14.ui.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.entity.AILogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.fields.AIField;
import it.algos.vaadflow14.ui.service.AFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 22-mag-2020
 * Time: 17:18
 * <p>
 * Scheda di dettaglio <br>
 * Può essere inserita in un dialogo oppure in una view <br>
 * I bottoni sono gestiti dal service e non da questa classe <br>
 * La scheda grafica è composta da due diversi FormLayout sovrapposti in modo da poter avere due diverse suddivisioni
 * di colonne. Tipicamente due nella prima ed una sola nella seconda <br>
 */
public abstract class AForm extends VerticalLayout {

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AAnnotationService annotation;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AReflectionService reflection;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ATextService text;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ALogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AArrayService array;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AFieldService fieldService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AClassService classService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ABeanService beanService;


    @Autowired
    public AMongoService mongo;

    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
    //     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    public RegioneLogic regioneLogic;

    /**
     * La scheda grafica è composta da due diversi FormLayout sovrapposti <br>
     */
    protected FormLayout topLayout;

    /**
     * La scheda grafica è composta da due diversi FormLayout sovrapposti <br>
     * L'uso di questo layout è regolata dal parametro 'usaTopLayout', di default 'true' <br>
     */
    protected boolean usaTopLayout;

    /**
     * La scheda grafica è composta da due diversi FormLayout sovrapposti <br>
     * I FormLayout.ResponsiveStep sono regolati nella property 'stepTopLayout' (integer) <br>
     * Tipicamente il primo FormLayout ha una colonna <br>
     */
    protected int stepTopLayout;

    /**
     * La scheda grafica è composta da due diversi FormLayout sovrapposti <br>
     */
    protected FormLayout bottomLayout;

    /**
     * La scheda grafica è composta da due diversi FormLayout sovrapposti <br>
     * L'uso di questo layout è regolata dal parametro 'usaBottomLayout', di default 'false' <br>
     */
    protected boolean usaBottomLayout;

    /**
     * La scheda grafica è composta da due diversi FormLayout sovrapposti <br>
     * I FormLayout.ResponsiveStep sono regolati nella property 'stepBottomLayout' (integer) <br>
     * Tipicamente il secondo FormLayout ha una sola colonna <br>
     */
    protected int stepBottomLayout;

    /**
     * Preferenza per la larghezza 'minima' del Form. Normalmente "50em". <br>
     */
    protected String minWidthForm;

    /**
     * The Entity Bean  (obbligatorio  per il form)
     */
    protected AEntity entityBean;

    //--collegamento tra i fields e la entityBean
    protected Binder binder;

    /**
     * The Entity Class  (obbligatorio per liste e form)
     */
    protected Class<? extends AEntity> entityClazz;

    /**
     * Lista ordinata di tutti i fields del form <br>
     * Serve per presentarli (ordinati) dall' alto in basso nel form <br>
     */
    protected List<AIField> fieldsList;

    /**
     * Mappa di tutti i fields del form <br>
     * La chiave è la propertyName del field <br>
     * Serve per recuperarli dal nome per successive elaborazioni <br>
     */
    protected HashMap<String, AIField> fieldsMap;

    /**
     * The Entity Logic (obbligatorio per liste e form)
     */
    protected AILogic entityLogic;

    protected WrapForm wrap;

    private LinkedHashMap<String, List> enumMap;

    private List<String> listaNomi;

    /**
     * Tipologia di Form in uso <br>
     */
    protected AEOperation operationForm;

    private boolean usaFieldNote = false;


    public AForm() {
        System.err.println("È stato chiamato il costruttore AForm senza parametri. Non può funzionare");
    }


    public AForm(WrapForm wrap) {
        this.wrap = wrap;
    }


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le (eventuali) istanze @Autowired <br>
     * Questo metodo viene chiamato subito dopo che il framework ha terminato l' init() implicito <br>
     * del costruttore e PRIMA di qualsiasi altro metodo <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l' ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        this.fixParameters();
        this.fixPreferenze();
        this.fixProperties();
        this.fixView();
    }


    /**
     * Regola i parametri arrivati col wrapper <br>
     */
    private void fixParameters() {
        if (wrap != null) {
            this.entityClazz = wrap.getEntityClazz();
            this.entityBean = wrap.getEntityBean();
            this.listaNomi = wrap.getFieldsName();
            this.fieldsMap = wrap.getFieldsMap();
            this.enumMap = wrap.getEnumMap();
            this.operationForm = wrap.getOperationForm();
        }
    }


    /**
     * Preferenze standard <br>
     * Normalmente il primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto per modificarle <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        if (wrap != null) {
            this.usaTopLayout = wrap.isUsaTopLayout();
            this.stepTopLayout = wrap.getStepTopLayout();
            this.usaBottomLayout = wrap.isUsaBottomLayout();
            this.stepBottomLayout = wrap.getStepBottomLayout();
            this.minWidthForm = wrap.getMinWidthForm();
        }
    }


    /**
     * Regola alcune properties (grafiche e non grafiche) <br>
     * Regola la business logic di questa classe <br>
     */
    protected void fixProperties() {
        this.setMargin(false);
        this.setSpacing(false);
        this.setPadding(false);

        if (usaTopLayout) {
            this.topLayout = new FormLayout();
            this.topLayout.addClassName("no-padding");
            this.fixColonne(topLayout, stepTopLayout, minWidthForm);
            this.add(topLayout);
        }

        if (usaBottomLayout) {
            this.bottomLayout = new FormLayout();
            this.bottomLayout.addClassName("no-padding");
            this.fixColonne(bottomLayout, stepBottomLayout, minWidthForm);
            this.add(bottomLayout);
        }

        if (entityClazz == null && entityBean != null) {
            entityClazz = entityBean.getClass();
        }

        if (entityClazz != null) {
            entityLogic = entityLogic != null ? entityLogic : classService.getLogicFromEntity(entityClazz);
        }

        if (entityBean == null && entityLogic != null) {
            entityBean = entityLogic.newEntity();
        }

        if (entityClazz == null) {
            logger.warn("Manca la entityClazz", this.getClass(), "fixProperties");
            return;
        }

        this.usaFieldNote = annotation.isUsaNote(entityClazz);

        //--Crea un nuovo binder (vuoto) per questo Form e questa entityBean
        binder = new Binder(entityClazz);
    }


    /**
     * Costruisce graficamente la scheda <br>
     * <p>
     * Crea i fields e li posiziona in una mappa <br>
     * Associa i valori di entityBean al binder. Dal DB alla UI <br>
     * Aggiunge ogni singolo field della fieldMap al layout <br>
     */
    protected void fixView() {
        //--Crea i fields normali in automatico
        this.creaFieldsBase();

        //--Eventuali fields specifici aggiunti oltre quelli automatici
        this.creaFieldsExtra();

        //--Aggiunge ogni singolo field della lista fieldsList al layout grafico
        this.addFieldsToLayout();

        //--Crea una mappa fieldMap, per recuperare i fields dal nome
        this.creaMappaFields();

        //--Regola in lettura eventuali fields extra non associati al binder. Dal DB alla UI
        this.readFieldsExtra();
    }


    /**
     * Crea i fields normali <br>
     * Associa i fields normali al binder <br>
     * Trasferisce (binder read) i valori dal DB alla UI <br>
     * <p>
     * Lista ordinata di tutti i fields normali del form <br>
     * Serve per presentarli (ordinati) dall' alto in basso nel form <br>
     */
    public void creaFieldsBase() {
        this.fieldsList = beanService.creaFields(entityBean, operationForm, binder);
    }


    /**
     * Crea i fields (eventuali) extra oltre a quelli normali <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void creaFieldsExtra() {
        AIField field = null;

        if (usaFieldNote) {
            field = fieldService.creaOnly(AEntity.class, FlowCost.FIELD_NOTE,entityBean);
            if (field != null) {
                fieldsList.add(field);
            }
        }
    }


    /**
     * Aggiunge ogni singolo field della lista fieldsList al layout <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addFieldsToLayout() {
        topLayout.removeAll();
        Component comp;

        if (array.isValid(fieldsList)) {
            for (AIField field : fieldsList) {
                comp = field.get();
                if (comp != null) {
                    topLayout.add(comp);
                } else {
                    logger.error("Manca il field "+field.getKey()+" dalla lista", this.getClass(), "addFieldsToLayout");
                }
            }
        } else {
            logger.warn("La fieldsList è vuota", this.getClass(), "addFieldsToLayout");
        }
    }


    /**
     * Crea una mappa fieldMap, per recuperare i fields dal nome <br>
     */
    public void creaMappaFields() {
        if (array.isValid(fieldsList)) {
            if (fieldsMap == null) {
                fieldsMap = new HashMap<String, AIField>();

                for (AIField field : fieldsList) {
                    fieldsMap.put(field.getKey(), field);
                }
            }
        }
    }


    /**
     * Regola in lettura eventuali valori NON associati al binder. <br>
     * Dal DB alla UI <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void readFieldsExtra() {
        AIField field = null;

        if (usaFieldNote) {
            if (fieldsMap != null) {
                field = fieldsMap.get(FlowCost.FIELD_NOTE);
                //                field.get().setValue(entityBean.note);
            }
        }
    }


    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dalla  UI al DB
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void writeFieldsExtra() {
        AIField field = null;

        if (usaFieldNote) {
            if (fieldsMap != null) {
                field = fieldsMap.get(FlowCost.FIELD_NOTE);
                //                entityBean.note = (String) field.getValue();
            }
        }
    }


    /**
     * Get the current valid entity.
     * Use the returned instance for further operations
     *
     * @return the checked entity
     */
    public AEntity getValidBean() {
        writeFieldsExtra();

        //--Associa i valori del binder a entityBean. Dalla UI alla business logic
        return binder.writeBeanIfValid(entityBean) ? entityBean : null;
    }


    /**
     * Regola i ResponsiveStep (colonne) del FormLayout indicato <br>
     *
     * @param formLayout   componente grafico Form
     * @param step         numero di 'colonne'
     * @param minWidthForm larghezza minima
     */
    public void fixColonne(FormLayout formLayout, int step, String minWidthForm) {
        if (step == 1) {
            formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep(minWidthForm, 1));
        }
        if (step == 2) {
            formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep(minWidthForm, 2));
        }
    }

}
