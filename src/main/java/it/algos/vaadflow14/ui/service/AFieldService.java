package it.algos.vaadflow14.ui.service;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEFieldType;
import it.algos.vaadflow14.backend.enumeration.AENumType;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.service.AAbstractService;
import it.algos.vaadflow14.ui.exception.RangeException;
import it.algos.vaadflow14.ui.fields.AField;
import it.algos.vaadflow14.ui.fields.AIField;
import it.algos.vaadflow14.ui.fields.AIntegerField;
import it.algos.vaadflow14.ui.fields.ATextField;
import it.algos.vaadflow14.ui.validator.AIntegerValidator;
import it.algos.vaadflow14.ui.validator.AStringBlankValidator;
import it.algos.vaadflow14.ui.validator.AUniqueValidator;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Field;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 03-ago-2020
 * Time: 15:28
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired public AArrayService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@VaadinSessionScope()
public class AFieldService extends AAbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;

    private AEntity entityBean;


    /**
     * Create a single field and add it to the binder. <br>
     * The field type is chosen according to the annotation @AIField. <br>
     *
     * @param operation    per differenziare tra nuova entity e modifica di esistente
     * @param binder       collegamento tra i fields e la entityBean
     * @param entityBean   Entity di riferimento
     * @param propertyName della property
     */
    public AIField create(AEOperation operation, Binder binder, AEntity entityBean, String propertyName) {
        AField field = null;
        this.entityBean = entityBean;
        Field reflectionJavaField = reflection.getField(entityBean.getClass(), propertyName);

        if (reflectionJavaField == null) {
            return null;
        }

        field = creaOnly(reflectionJavaField);
        if (field != null) {
            try {
                addFieldToBinder(operation, binder, reflectionJavaField, field);
            } catch (Exception unErrore) {
                if (unErrore instanceof RangeException) {
                    logger.error(unErrore.getMessage() + " per la property " + propertyName, this.getClass(), "create");
                }
                return null;
            }
        }

        return field;
    }


    /**
     * Create a single field.
     * The field type is chosen according to the annotation @AIField.
     *
     * @param reflectionJavaField di riferimento
     */
    public AField creaOnly(Field reflectionJavaField) {
        AField field = null;
        String caption = VUOTA;
        AEFieldType type = null;
        String width = VUOTA;
        String placeholder = VUOTA;
        boolean hasFocus = false;
        int intMin = 0;
        int intMax = 0;
        String widthForNumber = "8em";

        if (reflectionJavaField == null) {
            return null;
        }

        type = annotation.getFormType(reflectionJavaField);
        caption = annotation.getFormFieldNameCapital(reflectionJavaField);
        width = annotation.getFormWith(reflectionJavaField);
        placeholder = annotation.getPlaceholder(reflectionJavaField);
        hasFocus = annotation.focus(reflectionJavaField);
        intMin = annotation.getNumberMin(reflectionJavaField);
        intMax = annotation.getNumberMax(reflectionJavaField);

        if (type != null) {
            switch (type) {
                case text:
                    field = new ATextField(caption);
                    break;
                case integer:
                    field = new AIntegerField(caption);
                    if (field != null) {
                        if (intMin > 0) {
                            ((IntegerField) field.getBinder()).setHasControls(true);
                            ((IntegerField) field.getBinder()).setMin(intMin);
                            field.setWidth(widthForNumber);
                        }
                        if (intMax > 0) {
                            ((IntegerField) field.getBinder()).setHasControls(true);
                            ((IntegerField) field.getBinder()).setMax(intMax);
                            field.setWidth(widthForNumber);
                        }
                    }
                    break;
                case yesNo:
                    field = new ATextField(caption);
                    break;
                case combo:
                case enumeration:
                case gridShowOnly:
                    break;
                default:
                    logger.warn("Switch - caso non definito per il field \"" + reflectionJavaField.getName() + "\" del tipo " + type, this.getClass(), "creaOnly");
                    break;
            }
        }

        if (field != null) {
            field.setWidth(width);
        }

        if (field != null && text.isValid(placeholder)) {
            field.setPlaceholder(placeholder);
        }

        if (hasFocus) {
            field.setAutofocus();
        }

        return field;
    }


    protected void addFieldToBinder(AEOperation operation, Binder binder, Field reflectionJavaField, AField field) throws Exception {
        Binder.BindingBuilder builder = null;
        AEFieldType fieldType = annotation.getFormType(reflectionJavaField);
        String fieldName = VUOTA;
        AENumType numType = AENumType.positiviOnly;
        AStringBlankValidator stringBlankValidator = null;
        StringLengthValidator stringLengthValidator = null;
        AIntegerValidator integerValidator = null;
        AUniqueValidator uniqueValidator = null;
        String message = VUOTA;
        String messageSize = VUOTA;
        String messageNotNull = VUOTA;
        int stringMin = 0;
        int stringMax = 0;
        int intMin = 0;
        int intMax = 0;
        boolean isUnique = false;
        Serializable propertyOldValue = null;

        //        Class comboClazz = annotation.getComboClass(reflectionJavaField);
        //        Class serviceClazz = annotation.getServiceClass(reflectionJavaField);
        //        Class linkClazz = annotation.getLinkClass(reflectionJavaField);
        //        List enumItems = annotation.getEnumItems(reflectionJavaField);
        //        List items;

        if (binder == null || reflectionJavaField == null || field == null) {
            return;
        }

        fieldName = reflectionJavaField.getName();
        message = annotation.getMessage(reflectionJavaField);
        messageSize = annotation.getMessageSize(reflectionJavaField);
        messageNotNull = annotation.getMessageNull(reflectionJavaField);
        numType = annotation.getNumberType(reflectionJavaField);
        stringMin = annotation.getStringMin(reflectionJavaField);
        stringMax = annotation.getStringMax(reflectionJavaField);
        intMin = annotation.getNumberMin(reflectionJavaField);
        intMax = annotation.getNumberMax(reflectionJavaField);
        isUnique = annotation.isUnique(reflectionJavaField);

        stringBlankValidator = appContext.getBean(AStringBlankValidator.class, message);
        if (stringMin > 0 || stringMax > 0) {
            stringLengthValidator = new StringLengthValidator(messageSize, stringMin, stringMax);
        }
        if (isUnique) {
            propertyOldValue = (Serializable) reflection.getPropertyValue(entityBean, fieldName);
            uniqueValidator = appContext.getBean(AUniqueValidator.class, operation, entityBean, fieldName, propertyOldValue);
        }

        if (numType == AENumType.range || numType == AENumType.rangeControl) {
            if (intMin > 0 || intMax > 0) {
                if (intMin >= intMax) {
                    throw new RangeException("I valori del range sono errati");
                } else {
                    integerValidator = appContext.getBean(AIntegerValidator.class, numType, intMin, intMax);
                }
            }
        } else {
            integerValidator = appContext.getBean(AIntegerValidator.class, numType);
        }

        if (fieldType != null) {
            builder = binder.forField(field.getBinder());
            switch (fieldType) {
                case text:
                    if (stringBlankValidator != null) {
                        builder.withValidator(stringBlankValidator);
                    }
                    if (stringLengthValidator != null) {
                        builder.withValidator(stringLengthValidator);
                    }
                    if (uniqueValidator != null) {
                        builder.withValidator(uniqueValidator);
                    }
                    break;
                case integer:
                    if (integerValidator != null) {
                        builder.withValidator(integerValidator);
                    }
                    if (uniqueValidator != null) {
                        builder.withValidator(uniqueValidator);
                    }
                    break;
                case yesNo:
                    binder.forField(field).bind(fieldName);
                    break;
                case combo:
                    break;
                case enumeration:
                    break;
                case gridShowOnly:
                    break;
                default:
                    logger.warn("Switch - caso non definito per il field \"" + reflectionJavaField.getName() + "\" del tipo " + fieldType, this.getClass(), "addFieldToBinder");
                    break;
            }
            if (builder != null) {
                builder.bind(fieldName);
            }
        }
    }

}