package it.algos.vaadflow14.ui.service;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import it.algos.vaadflow14.backend.annotation.StaticContextAccessor;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import it.algos.vaadflow14.backend.service.AAbstractService;
import it.algos.vaadflow14.ui.exception.RangeException;
import it.algos.vaadflow14.ui.fields.*;
import it.algos.vaadflow14.ui.validator.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
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


    //    /**
    //     * Create a single field.
    //     *
    //     * @param entityBean di riferimento
    //     * @param fieldKey   della property
    //     */
    //    public AField creaOnly(AEntity entityBean, String fieldKey) {
    //        Field reflectionJavaField;
    //
    //        if (entityBean == null) {
    //            return null;
    //        }
    //
    //        reflectionJavaField = reflection.getField(entityBean.getClass(), fieldKey);
    //        return creaOnly(reflectionJavaField);
    //    }


    /**
     * Create a single field.
     *
     * @param reflectionJavaField di riferimento
     */
    public AField creaOnly(Field reflectionJavaField) {
        AField field = null;
        AETypeField type;
        String caption = VUOTA;
        AETypeBoolField typeBool;
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
                    boolEnum = annotation.getBoolEnumField(reflectionJavaField);
                    caption = annotation.getFormFieldName(reflectionJavaField);
                    field = appContext.getBean(ABooleanField.class, typeBool, boolEnum, caption);
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
                    isRequired = annotation.isRequired(reflectionJavaField);
                    isAllowCustomValue = annotation.isAllowCustomValue(reflectionJavaField);
                    items = getComboItems(reflectionJavaField);
                    if (items != null) {
                        field = appContext.getBean(AComboField.class, items, isRequired, isAllowCustomValue);
                    } else {
                        logger.warn("Mancano gli items per il combobox di " + fieldKey, this.getClass(), "creaOnly.combo");
                    }
                    break;
                case enumeration:
                    items = getEnumerationItems(reflectionJavaField);
                    if (items != null) {
                        field = appContext.getBean(AComboField.class, items, true, false);
                    } else {
                        logger.warn("Mancano gli items per l' enumeration di " + fieldKey, this.getClass(), "creaOnly.enumeration");
                    }
                    break;
                default:
                    logger.warn("Switch - caso non definito per type=" + type, this.getClass(), "creaOnly");
                    break;
            }
        }

        if (field != null) {
            if (text.isEmpty(caption)) {
                caption = annotation.getFormFieldName(reflectionJavaField);
                if (true) {//@todo Funzionalità ancora da implementare con preferenza
                    caption = caption.toLowerCase();
                } else {
                    caption = text.primaMaiuscola(caption);
                }
                field.setLabel(caption);
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
        //        ANotNullValidator notNullValidator = null;
        StringLengthValidator stringLengthValidator = null;
        AIntegerValidator integerValidator = null;
        AUniqueValidator uniqueValidator = null;
        String message = VUOTA;
        String messageSize = VUOTA;
        String messageNotBlank = VUOTA;
        String messageNotNull = VUOTA;
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
            //            notNullValidator = appContext.getBean(ANotNullValidator.class, messageNotNull);
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
                    } else {
                        integerValidator = appContext.getBean(AIntegerValidator.class, numType, intMin, intMax);
                    }
                }
            } else {
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
                    if (isRequired) {
                        builder.asRequired();
                        //                        builder.withValidator(notNullValidator);
                    }
                    break;
                case enumeration:
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


    //    /**
    //     * Create a single field and add it to the binder. <br>
    //     * The field type is chosen according to the annotation @AIField. <br>
    //     *
    //     * @param operation    per differenziare tra nuova entity e modifica di esistente
    //     * @param binder       collegamento tra i fields e la entityBean
    //     * @param entityBean   Entity di riferimento
    //     * @param propertyName della property
    //     */
    //    @Deprecated
    //    public AIField create(AEOperation operation, Binder binder, AEntity entityBean, String propertyName) {
    //        AIField field = null;
    //        Field reflectionJavaField = reflection.getField(entityBean.getClass(), propertyName);
    //
    //        if (reflectionJavaField == null) {
    //            logger.warn("Manca il field per la property " + propertyName, this.getClass(), "create");
    //            return null;
    //        }
    //
    //        //        field = creaOnly(reflectionJavaField, entityBean);
    //        if (field != null) {
    //            try {
    //                addFieldToBinder(operation, binder, entityBean, reflectionJavaField, field);
    //            } catch (Exception unErrore) {
    //                logger.warn("La property " + propertyName + " non è stata aggiunta al Form", this.getClass(), "create");
    //                if (unErrore instanceof RangeException) {
    //                    logger.error(unErrore.getMessage() + " per la property " + propertyName, this.getClass(), "create");
    //                }
    //                return null;
    //            }
    //        }
    //
    //        return field;
    //    }


    //    /**
    //     * Create a single field.
    //     *
    //     * @param entityClazz  di riferimento
    //     * @param propertyName della property
    //     */
    //    @Deprecated
    //    public AIField creaOnly(Class entityClazz, String propertyName, AEntity entityBean) {
    //        Field reflectionJavaField = reflection.getField(entityClazz, propertyName);
    //
    //        if (reflectionJavaField != null) {
    //            //            return creaOnly(reflectionJavaField, entityBean);
    //            return null;
    //        } else {
    //            logger.warn("Manca il field per la property " + propertyName, this.getClass(), "creaOnly");
    //            return null;
    //        }
    //    }


    /**
     *
     */
    public List getComboItems(Field reflectionJavaField) {
        List items = null;
        Class comboClazz;
        Class logicClazz;
        boolean usaComboMethod;
        Sort sort;
        String methodName;
        Method metodo;
        Object serviceInstance;

        comboClazz = annotation.getComboClass(reflectionJavaField);
        sort = annotation.getSort(comboClazz);
        usaComboMethod = annotation.usaComboMethod(reflectionJavaField);

        if (usaComboMethod) {
            logicClazz = annotation.getLogicClass(reflectionJavaField);
             methodName = annotation.getMethodName(reflectionJavaField);
            try {
                metodo = logicClazz.getDeclaredMethod(methodName);
                serviceInstance = StaticContextAccessor.getBean(logicClazz);
                items = (List) metodo.invoke(serviceInstance);
            } catch (Exception unErrore) {
                logger.error(unErrore, this.getClass(), "nomeDelMetodo");
            }
        } else {
            items = comboClazz != null ? mongo.findAll(comboClazz, sort) : null;
        }

        return items;
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
        if (array.isValid(enumItems)) {
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


    //    /**
    //     * Create a single field.
    //     *
    //     * @param reflectionJavaField di riferimento
    //     */
    //    @Deprecated
    //    public AIField creaOnly(Field reflectionJavaField, AEntity entityBean) {
    //        AIField field = null;
    //        String fieldKey;
    //        String caption = VUOTA;
    //        String boolEnum = VUOTA;
    //        AETypeField type = null;
    //        AETypeBoolField typeBool = AETypeBoolField.checkBox;
    //        String width = VUOTA;
    //        String placeholder = VUOTA;
    //        boolean hasFocus = false;
    //        int intMin = 0;
    //        int intMax = 0;
    //        String widthForNumber = "8em";
    //        Class comboClazz = null;
    //        List items;
    //
    //        if (reflectionJavaField == null) {
    //            return null;
    //        }
    //
    //        fieldKey = reflectionJavaField.getName();
    //        type = annotation.getFormType(reflectionJavaField);
    //        caption = annotation.getFormFieldNameCapital(reflectionJavaField);
    //        boolEnum = annotation.getBoolEnumField(reflectionJavaField);
    //        width = annotation.getFormWith(reflectionJavaField);
    //        placeholder = annotation.getPlaceholder(reflectionJavaField);
    //        hasFocus = annotation.focus(reflectionJavaField);
    //        intMin = annotation.getNumberMin(reflectionJavaField);
    //        intMax = annotation.getNumberMax(reflectionJavaField);
    //        typeBool = annotation.getTypeBoolField(reflectionJavaField);
    //        comboClazz = annotation.getComboClass(reflectionJavaField);
    //
    //        if (type != null) {
    //            switch (type) {
    //                case text:
    //                case phone:
    //                    field = appContext.getBean(ATextField.class);
    //                    break;
    //                case email:
    //                    field = appContext.getBean(AEmailField.class, fieldKey, caption);
    //                    //                    ((AEmailField) field).getMail().setClearButtonVisible(true);
    //                    field.setErrorMessage("Inserisci un indirizzo eMail valido");
    //                    break;
    //                case password:
    //                    field = appContext.getBean(APasswordField.class, fieldKey, caption);
    //                    break;
    //                case textArea:
    //                    field = appContext.getBean(ATextAreaField.class, fieldKey, caption);
    //                    break;
    //                case integer:
    //                    field = appContext.getBean(AIntegerField.class, fieldKey, caption);
    //                    if (field != null) {
    //                        if (intMin > 0) {
    //                            ((IntegerField) field.getBinder()).setHasControls(true);
    //                            ((IntegerField) field.getBinder()).setMin(intMin);
    //                            field.setWidth(widthForNumber);
    //                        }
    //                        if (intMax > 0) {
    //                            ((IntegerField) field.getBinder()).setHasControls(true);
    //                            ((IntegerField) field.getBinder()).setMax(intMax);
    //                            field.setWidth(widthForNumber);
    //                        }
    //                    }
    //                    break;
    //                case booleano:
    //                    field = appContext.getBean(ABooleanField.class, fieldKey, typeBool, caption, boolEnum);
    //                    //                    if (text.isValid(captionRadio)) {
    //                    //                        field = new ABooleanField(caption, typeBool, captionRadio);
    //                    //                    } else {
    //                    //                        field = new ABooleanField(caption, typeBool);
    //                    //                    }
    //                    break;
    //                case localDate:
    //                    field = appContext.getBean(ADateField.class, fieldKey, caption);
    //                    //                    field = appContext.getBean(ProvaField.class, caption);
    //                    break;
    //                case localDateTime:
    //                    //                    field = appContext.getBean(ADateField.class, fieldKey, caption);
    //                    //                    field = appContext.getBean(ProvaField.class, caption);
    //                    //                    field = appContext.getBean(ADateTimeField.class, fieldKey, caption);
    //                    break;
    //                case localTime:
    //                    //                    field = appContext.getBean(ADateField.class, fieldKey, caption);
    //                    //                    field = appContext.getBean(ATimeField.class, fieldKey, caption);
    //                    break;
    //                case combo:
    //                    if (comboClazz != null) {
    //                        items = mongo.findAll(comboClazz);
    //                        if (items != null) {
    //                            field = appContext.getBean(AComboField.class, fieldKey, caption, items);
    //                            boolean sttaus = ((AComboField<?>) field).isReadOnly();
    //                            sttaus = ((AComboField<?>) field).isReadOnly();
    //                        } else {
    //                            logger.warn("Mancano gli items per il combobox di " + fieldKey, this.getClass(), "creaOnly.combo");
    //                        }
    //                    } else {
    //                        logger.warn("Manca la comboClazz per " + fieldKey, this.getClass(), "creaOnly.combo");
    //                    }
    //                    break;
    //                case enumeration:
    //                    field = getEnumerationField(reflectionJavaField, fieldKey, caption);
    //                    break;
    //                case preferenza:
    //                    field = appContext.getBean(APreferenzaField.class, fieldKey, caption, entityBean);
    //                    break;
    //                case gridShowOnly:
    //                    break;
    //                default:
    //                    logger.warn("Switch - caso non definito per il field \"" + reflectionJavaField.getName() + "\" del tipo " + type, this.getClass(), "creaOnly");
    //                    break;
    //            }
    //        }
    //
    //        if (field == null) {
    //            return null;
    //        }
    //
    //        field.setWidth(width);
    //
    //        if (field != null && text.isValid(placeholder)) {
    //            //            field.setPlaceholder(placeholder);//@todo Funzionalità ancora da implementare
    //        }
    //
    //        if (hasFocus) {
    //            field.setAutofocus();
    //        }
    //
    //        return field;
    //    }


    //    @Deprecated
    //    public void addFieldToBinder(AEOperation operation, Binder binder, AEntity entityBean, Field reflectionJavaField, AIField field) throws Exception {
    //        Binder.BindingBuilder builder = null;
    //        AETypeField fieldType = annotation.getFormType(reflectionJavaField);
    //        String fieldName = VUOTA;
    //        AETypeNum numType = AETypeNum.positiviOnly;
    //        AStringBlankValidator stringBlankValidator = null;
    //        StringLengthValidator stringLengthValidator = null;
    //        AIntegerValidator integerValidator = null;
    //        AUniqueValidator uniqueValidator = null;
    //        String message = VUOTA;
    //        String messageSize = VUOTA;
    //        String messageNotBlank = VUOTA;
    //        String messageNotNull = VUOTA;
    //        int stringMin = 0;
    //        int stringMax = 0;
    //        int intMin = 0;
    //        int intMax = 0;
    //        boolean isRequired = false;
    //        boolean isUnique = false;
    //        Serializable propertyOldValue = null;
    //
    //        //        Class comboClazz = annotation.getComboClass(reflectionJavaField);
    //        //        Class serviceClazz = annotation.getServiceClass(reflectionJavaField);
    //        //        Class linkClazz = annotation.getLinkClass(reflectionJavaField);
    //        //        List enumItems = annotation.getEnumItems(reflectionJavaField);
    //        //        List items;
    //
    //        if (binder == null || reflectionJavaField == null || field == null) {
    //            return;
    //        }
    //
    //        fieldName = reflectionJavaField.getName();
    //        message = annotation.getMessage(reflectionJavaField);
    //        messageSize = annotation.getMessageSize(reflectionJavaField);
    //        messageNotBlank = annotation.getMessageBlank(reflectionJavaField);
    //        messageNotNull = annotation.getMessageNull(reflectionJavaField);
    //        numType = annotation.getTypeNumber(reflectionJavaField);
    //        stringMin = annotation.getStringMin(reflectionJavaField);
    //        stringMax = annotation.getStringMax(reflectionJavaField);
    //        intMin = annotation.getNumberMin(reflectionJavaField);
    //        intMax = annotation.getNumberMax(reflectionJavaField);
    //        isRequired = annotation.isRequired(reflectionJavaField);
    //        isUnique = annotation.isUnique(reflectionJavaField);
    //
    //        if (isRequired) {
    //            stringBlankValidator = appContext.getBean(AStringBlankValidator.class, messageNotBlank);
    //        }
    //
    //        if (stringMin > 0 || stringMax > 0) {
    //            stringLengthValidator = new StringLengthValidator(messageSize, stringMin, stringMax);
    //        }
    //        if (isUnique) {
    //            propertyOldValue = (Serializable) reflection.getPropertyValue(entityBean, fieldName);
    //            uniqueValidator = appContext.getBean(AUniqueValidator.class, operation, entityBean, fieldName, propertyOldValue);
    //        }
    //
    //        if (fieldType == AETypeField.integer) {
    //            if (numType == AETypeNum.range || numType == AETypeNum.rangeControl) {
    //                if (intMin > 0 || intMax > 0) {
    //                    if (intMin >= intMax) {
    //                        throw new RangeException("I valori del range sono errati");
    //                    } else {
    //                        integerValidator = appContext.getBean(AIntegerValidator.class, numType, intMin, intMax);
    //                    }
    //                }
    //            } else {
    //                integerValidator = appContext.getBean(AIntegerValidator.class, numType);
    //            }
    //        }
    //
    //        if (fieldType != null) {
    //            builder = binder.forField(field.getBinder());
    //            switch (fieldType) {
    //                case text:
    //                    if (stringBlankValidator != null) {
    //                        builder.withValidator(stringBlankValidator);
    //                    }
    //                    if (stringLengthValidator != null) {
    //                        builder.withValidator(stringLengthValidator);
    //                    }
    //                    if (uniqueValidator != null) {
    //                        builder.withValidator(uniqueValidator);
    //                    }
    //                    break;
    //                case phone:
    //                    builder.withValidator(new APhoneValidator());
    //                    break;
    //                case integer:
    //                    if (integerValidator != null) {
    //                        builder.withValidator(integerValidator);
    //                    }
    //                    if (uniqueValidator != null) {
    //                        builder.withValidator(uniqueValidator);
    //                    }
    //                    break;
    //                case booleano:
    //                    break;
    //                case localDate:
    //                    break;
    //                case combo:
    //                    //                    field.getBinder().setReadOnly(false);
    //                    break;
    //                case enumeration:
    //                    break;
    //                case password:
    //                    if (stringBlankValidator != null) {
    //                        builder.withValidator(stringBlankValidator);
    //                    }
    //                    break;
    //                case textArea:
    //                    break;
    //                case email:
    //                    break;
    //                case gridShowOnly:
    //                    break;
    //                default:
    //                    logger.warn("Switch - caso non definito per il field \"" + reflectionJavaField.getName() + "\" del tipo " + fieldType, this.getClass(), "addFieldToBinder");
    //                    break;
    //            }
    //            if (builder != null) {
    //                builder.bind(fieldName);
    //            }
    //        }
    //    }


    //    /**
    //     * Prima cerca i valori nella @Annotation items=... dell' interfaccia AIField <br>
    //     * Poi cerca i valori di una classe enumeration definita in enumClazz=... dell' interfaccia AIField <br>
    //     * Poi cerca i valori di una collection definita con serviceClazz=...dell' interfaccia AIField <br>
    //     *
    //     * @param reflectionJavaField di riferimento
    //     * @param fieldKey            nome interno del field
    //     * @param caption             label sopra il field
    //     */
    //    public AField getEnumerationField(Field reflectionJavaField, String fieldKey, String caption) {
    //        AField field = null;
    //        List<String> enumItems = null;
    //        List enumObjects = null;
    //        Class enumClazz = null;
    //
    //        if (reflectionJavaField == null) {
    //            return null;
    //        }
    //
    //        enumItems = annotation.getEnumItems(reflectionJavaField);
    //        if (array.isEmpty(enumItems)) {
    //            enumClazz = annotation.getEnumClass(reflectionJavaField);
    //            if (enumClazz != null) {
    //                Object[] elementi = enumClazz.getEnumConstants();
    //                if (elementi != null) {
    //                    enumObjects = Arrays.asList(elementi);
    //                    field = appContext.getBean(AComboField.class, fieldKey, caption);
    //                    ((AComboField) field).setItem(enumObjects);
    //                    return field;
    //                }
    //            }
    //        }
    //
    //        if (array.isValid(enumItems)) {
    //            field = appContext.getBean(AComboField.class, fieldKey, caption);
    //            ((AComboField) field).setItem(enumItems);
    //        }
    //
    //        return field;
    //    }

}