package it.algos.vaadflow14.ui.service;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import it.algos.vaadflow14.backend.annotation.StaticContextAccessor;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.service.AAbstractService;
import it.algos.vaadflow14.ui.exception.RangeException;
import it.algos.vaadflow14.ui.fields.*;
import it.algos.vaadflow14.ui.validator.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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
 * L' istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired public AArrayService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AFieldService extends AAbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Create a single field.
     *
     * @param entityBean    di riferimento
     * @param operationForm tipologia di Form in uso
     * @param fieldKey      della property
     */
    public AField crea(AEntity entityBean, Binder binder, AEOperation operationForm, String fieldKey) {
        AField field = null;
        Field reflectionJavaField = reflection.getField(entityBean.getClass(), fieldKey);
        field = this.creaOnly(entityBean, reflectionJavaField, operationForm);

        if (field != null) {
            this.addToBinder(entityBean, binder, operationForm, reflectionJavaField, field);
        }
        else {
            AETypeField type = annotation.getFormType(reflection.getField(entityBean.getClass(), fieldKey));
            logger.warn("Non sono riuscito a creare il field " + fieldKey + " di type " + type, this.getClass(), "creaFieldsBinder");
        }

        return field;
    }


    /**
     * Create a single field.
     *
     * @param entityBean          di riferimento
     * @param reflectionJavaField di riferimento
     * @param operationForm       tipologia di Form in uso
     */
    public AField creaOnly(AEntity entityBean, Field reflectionJavaField, AEOperation operationForm) {
        AField field = null;
        AETypeField type;
        String caption = VUOTA;
        AETypeBoolField typeBool;
        AETypePref typePref;
        String boolEnum;
        String fieldKey;
        //        Class comboClazz = null;
        //        Sort sort;
        List items;
        List<String> enumItems;
        boolean isRequired = false;
        boolean isAllowCustomValue = false;
        //        boolean usaComboMethod = false;
        String width = VUOTA;
        String height = VUOTA;

        if (reflectionJavaField == null) {
            return null;
        }

        fieldKey = reflectionJavaField.getName();
        width = annotation.getFormWith(reflectionJavaField);
        type = annotation.getFormType(reflectionJavaField);
        if (type != null) {
            switch (type) {
                case text:
                case phone:
                    field = appContext.getBean(ATextField.class);
                    break;
                case textArea:
                    field = appContext.getBean(ATextAreaField.class);
                    break;
                case password:
                    field = appContext.getBean(APasswordField.class);
                    break;
                case email:
                    field = appContext.getBean(AEmailField.class);
                    break;
                case cap:
                    field = appContext.getBean(ATextField.class);
                    break;
                case integer:
                    field = appContext.getBean(AIntegerField.class);
                    break;
                case booleano:
                    typeBool = annotation.getTypeBoolField(reflectionJavaField);
                    if (typeBool == AETypeBoolField.checkBox) {
                        caption = annotation.getFormFieldName(reflectionJavaField);
                        field = appContext.getBean(ABooleanField.class, typeBool, caption);
                    }
                    else {
                        boolEnum = annotation.getBoolEnumField(reflectionJavaField);
                        field = appContext.getBean(ABooleanField.class, typeBool, boolEnum);
                    }
                    break;
                case localDateTime:
                    field = appContext.getBean(ADateTimeField.class);
                    break;
                case localDate:
                    //                    field = appContext.getBean(ADateField.class);
                    field = new ADateField();
                    break;
                case localTime:
                    field = appContext.getBean(ATimeField.class);
                    break;
                case combo:
                    field = creaComboField(reflectionJavaField);
                    //                    isRequired = annotation.isRequired(reflectionJavaField);
                    //                    isAllowCustomValue = annotation.isAllowCustomValue(reflectionJavaField);
                    //                    items = getComboItems(reflectionJavaField);
                    //                    if (items != null) {
                    //                        field = appContext.getBean(AComboField.class, items, isRequired, isAllowCustomValue);
                    //                    } else {
                    //                        logger.warn("Mancano gli items per il combobox di " + fieldKey, this.getClass(), "creaOnly.combo");
                    //                    }
                    break;
                case enumeration:
                    items = getEnumerationItems(reflectionJavaField);
                    if (items != null) {
                        field = appContext.getBean(AComboField.class, items, true, false);
                    }
                    else {
                        logger.warn("Mancano gli items per l' enumeration di " + fieldKey, this.getClass(), "creaOnly.enumeration");
                    }
                    break;
                case preferenza:
                    field = appContext.getBean(APreferenzaField.class, entityBean, operationForm);
                    caption = "nonUsata";
                    break;
                case image:
                    height = annotation.getFormHeight(reflectionJavaField);
                    field = appContext.getBean(AImageField.class);
                    ((AImageField) field).setHeight(height);
                    break;
                case gridShowOnly:
                    field = creaGridField(entityBean, reflectionJavaField);
                    break;
                default:
                    logger.warn("Switch - caso non definito per type=" + type, this.getClass(), "creaOnly");
                    break;
            }
        }

        if (field != null) {
            if (text.isEmpty(caption)) {
                caption = annotation.getFormFieldName(reflectionJavaField);
                if (AEPreferenza.usaFormFieldMaiuscola.is()) {
                    caption = text.primaMaiuscola(caption);
                }
                else {
                    caption = text.primaMinuscola(caption);
                }
                if (text.isEmpty(field.getLabel())) {
                    field.setLabel(caption);
                }
            }
            field.setFieldKey(fieldKey);
            field.setWidth(width);
        }

        return field;
    }


    public void addToBinder(AEntity entityBean, Binder binder, AEOperation operation, Field reflectionJavaField, AField field) {
        Binder.BindingBuilder builder = null;
        AETypeField fieldType = annotation.getFormType(reflectionJavaField);
        String fieldName = VUOTA;
        AETypeNum numType = AETypeNum.positiviOnly;
        AStringBlankValidator stringBlankValidator = null;
        ANotNullValidator notNullValidator = null;
        StringLengthValidator stringLengthValidator = null;
        AIntegerValidator integerValidator = null;
        AUniqueValidator uniqueValidator = null;
        String message = VUOTA;
        String messageSize = VUOTA;
        String messageNotBlank = VUOTA;
        String messageNotNull = VUOTA;
        String messageMail = "Indirizzo eMail non valido";
        int stringMin = 0;
        int stringMax = 0;
        int intMin = 0;
        int intMax = 0;
        boolean isRequired = false;
        boolean isUnique = false;
        Serializable propertyOldValue = null;

        fieldName = reflectionJavaField.getName();
        message = annotation.getMessage(reflectionJavaField);
        messageSize = annotation.getMessageSize(reflectionJavaField);
        messageNotBlank = annotation.getMessageBlank(reflectionJavaField);
        messageNotNull = annotation.getMessageNull(reflectionJavaField);
        numType = annotation.getTypeNumber(reflectionJavaField);
        stringMin = annotation.getStringMin(reflectionJavaField);
        stringMax = annotation.getStringMax(reflectionJavaField);
        intMin = annotation.getNumberMin(reflectionJavaField);
        intMax = annotation.getNumberMax(reflectionJavaField);
        isRequired = annotation.isRequired(reflectionJavaField);
        isUnique = annotation.isUnique(reflectionJavaField);

        if (isRequired) {
            stringBlankValidator = appContext.getBean(AStringBlankValidator.class, messageNotBlank);
            notNullValidator = appContext.getBean(ANotNullValidator.class, messageNotNull);
        }

        if (stringMin > 0 || stringMax > 0) {
            stringLengthValidator = new StringLengthValidator(messageSize, stringMin, stringMax);
        }
        if (isUnique) {
            try {
                propertyOldValue = (Serializable) reflectionJavaField.get(entityBean);
            } catch (Exception unErrore) {
                logger.error(unErrore, this.getClass(), "addToBinder");
            }
            uniqueValidator = appContext.getBean(AUniqueValidator.class, operation, entityBean, fieldName, propertyOldValue);
        }

        if (fieldType == AETypeField.integer) {
            if (numType == AETypeNum.range || numType == AETypeNum.rangeControl) {
                if (intMin > 0 || intMax > 0) {
                    if (intMin >= intMax) {
                        throw new RangeException("I valori del range sono errati");
                    }
                    else {
                        integerValidator = appContext.getBean(AIntegerValidator.class, numType, intMin, intMax);
                    }
                }
            }
            else {
                integerValidator = appContext.getBean(AIntegerValidator.class, numType);
            }
        }

        if (fieldType != null) {
            builder = binder.forField(field);
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
                    if (isRequired) {
                        builder.asRequired();
                    }
                    break;
                case phone:
                    builder.withValidator(appContext.getBean(APhoneValidator.class));
                    if (isRequired) {
                        builder.asRequired();
                    }
                    break;
                case password:
                    if (stringBlankValidator != null) {
                        builder.withValidator(stringBlankValidator);
                    }
                    if (isRequired) {
                        builder.asRequired();
                    }
                    break;
                case email:
                    if (isRequired) {
                        builder.asRequired();
                    }
                    break;
                case cap:
                    builder.withValidator(appContext.getBean(ACapValidator.class));
                    if (isRequired) {
                        builder.asRequired();
                    }
                    break;
                case textArea:
                    break;
                case integer:
                    if (integerValidator != null) {
                        builder.withValidator(integerValidator);
                    }
                    if (uniqueValidator != null) {
                        builder.withValidator(uniqueValidator);
                    }
                    break;
                case booleano:
                    break;
                case localDateTime:
                    break;
                case localDate:
                    break;
                case localTime:
                    break;
                case combo:
                    if (notNullValidator != null) {
                        builder.withValidator(notNullValidator);
                    }
                    if (isRequired) {
                        builder.asRequired();
                        //                        builder.withValidator(notNullValidator);
                    }
                    break;
                case enumeration:
                    if (notNullValidator != null) {
                        builder.withValidator(notNullValidator);
                    }
                    if (isRequired) {
                        builder.asRequired();
                    }
                    break;
                case preferenza:
                    if (isRequired) {
                        builder.asRequired();
                    }
                    break;
                case image:
                    break;
                case gridShowOnly:
                    break;
                default:
                    logger.warn("Switch - caso non definito per il field \"" + reflectionJavaField.getName() + "\" del tipo " + fieldType, this.getClass(), "addToBinder");
                    break;
            }
            if (builder != null) {
                builder.bind(fieldName);
            }
        }
    }


    public AField creaComboField(Field reflectionJavaField) {
        AField field = null;
        String fieldKey;
        boolean isRequired = false;
        boolean isAllowCustomValue = false;
        List items = null;

        fieldKey = reflectionJavaField.getName();
        ComboBox combo = getCombo(reflectionJavaField);
        field = appContext.getBean(AComboField.class, combo);
        //        isRequired = annotation.isRequired(reflectionJavaField);
        //        isAllowCustomValue = annotation.isAllowCustomValue(reflectionJavaField);
        //        items = getComboItems(reflectionJavaField);
        //        if (items != null) {
        //            field = appContext.getBean(AComboField.class, items, isRequired, isAllowCustomValue);
        //        } else {
        //            logger.warn("Mancano gli items per il combobox di " + fieldKey, this.getClass(), "creaOnly.combo");
        //        }

        return field;
    }


    /**
     *
     */
    public ComboBox getCombo(Field reflectionJavaField) {
        ComboBox<T> combo = new ComboBox();
        boolean usaComboMethod;
        Class<AEntity> comboClazz;
        Class<ALogic> logicClazz;
        String methodName;
        Method metodo;
        ALogic logicInstance;
        List items = null;

        usaComboMethod = annotation.usaComboMethod(reflectionJavaField);
        comboClazz = annotation.getComboClass(reflectionJavaField);
        if (usaComboMethod) {
            logicClazz = annotation.getLogicClass(reflectionJavaField);
            logicInstance = (ALogic) StaticContextAccessor.getBean(logicClazz);
            methodName = annotation.getMethodName(reflectionJavaField);
            try {
                metodo = logicClazz.getDeclaredMethod(methodName);
                combo = (ComboBox) metodo.invoke(logicInstance);
            } catch (Exception unErrore) {
                logger.error(unErrore, this.getClass(), "getCombo");
            }
        }
        else {
            items = mongo.findAll(comboClazz);
            combo = new ComboBox<>();
            combo.setItems(items);
        }

        return combo;
    }


    /**
     * Prima cerca i valori nella @Annotation items=... dell' interfaccia AIField <br>
     * Poi cerca i valori di una classe enumeration definita in enumClazz=... dell' interfaccia AIField <br>
     * Poi cerca i valori di una collection definita con serviceClazz=...dell' interfaccia AIField <br>
     */
    public List getEnumerationItems(Field reflectionJavaField) {
        List items = null;
        Class enumClazz = null;
        List<String> enumItems;
        Object[] elementiEnum = null;

        enumItems = annotation.getEnumItems(reflectionJavaField);
        if (array.isAllValid(enumItems)) {
            return enumItems;
        }

        enumClazz = annotation.getEnumClass(reflectionJavaField);
        if (enumClazz != null) {
            elementiEnum = enumClazz.getEnumConstants();
            if (elementiEnum != null) {
                return Arrays.asList(elementiEnum);
            }
        }

        return items;
    }


    /**
     *
     */
    public AGridField creaGridField(AEntity entityBean, Field reflectionJavaField) {
        AGridField field = null;
        List items;
        Class linkClazz;
        List<String> gridProperties;
        String linkProperty;
        String caption;

        if (entityBean == null || reflectionJavaField == null) {
            return null;
        }

        linkClazz = annotation.getLinkClass(reflectionJavaField);
        linkProperty = annotation.getLinkProperty(reflectionJavaField);
        items = mongo.findAll(linkClazz, linkProperty, entityBean);
        gridProperties = annotation.getLinkProperties(reflectionJavaField);
        caption = annotation.getFormFieldName(reflectionJavaField);
        caption = AEPreferenza.usaFormFieldMaiuscola.is() ? text.primaMaiuscola(caption) : caption;

        field = appContext.getBean(AGridField.class, linkClazz, gridProperties, items);
        field.setLabel(caption + items.size());
        return field;
    }

}