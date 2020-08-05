package it.algos.vaadflow14.ui.service;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEFieldType;
import it.algos.vaadflow14.backend.service.AAbstractService;
import it.algos.vaadflow14.ui.fields.AField;
import it.algos.vaadflow14.ui.fields.AIField;
import it.algos.vaadflow14.ui.fields.AIntegerField;
import it.algos.vaadflow14.ui.fields.ATextField;
import it.algos.vaadflow14.ui.validator.AIntegerZeroValidator;
import it.algos.vaadflow14.ui.validator.AStringBlankValidator;
import org.springframework.stereotype.Service;

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


    /**
     * Create a single field and add it to the binder. <br>
     * The field type is chosen according to the annotation @AIField. <br>
     *
     * @param binder       collegamento tra i fields e la entityBean
     * @param entityBean   Entity di riferimento
     * @param propertyName della property
     */
    public AIField create(Binder binder, AEntity entityBean, String propertyName) {
        AField field = null;
        Field reflectionJavaField = reflection.getField(entityBean.getClass(), propertyName);

        if (reflectionJavaField == null) {
            return null;
        }

        field = creaOnly(reflectionJavaField);
        if (field != null) {
            addFieldToBinder(binder, reflectionJavaField, field);
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

        if (reflectionJavaField == null) {
            return null;
        }

        type = annotation.getFormType(reflectionJavaField);
        caption = annotation.getFormFieldNameCapital(reflectionJavaField);
        width = annotation.getFormWith(reflectionJavaField);
        placeholder = annotation.getPlaceholder(reflectionJavaField);

        if (type != null) {
            switch (type) {
                case text:
                    field = new ATextField(caption);
                    break;
                case integer:
                    field = new AIntegerField(caption);
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

        return field;
    }


    protected void addFieldToBinder(Binder binder, Field reflectionJavaField, AField field) {
        Binder.BindingBuilder builder = null;
        AEFieldType type = annotation.getFormType(reflectionJavaField);
        String fieldName = VUOTA;
        AStringBlankValidator stringBlankValidator = null;
        StringLengthValidator stringLengthValidator = null;
        AIntegerZeroValidator integerZeroValidator = null;
        String message = VUOTA;
        String messageSize = VUOTA;
        String messageNotNull = VUOTA;
        int min = 0;
        int max = 0;
        String widthForNumber = "8em";

        //        Class comboClazz = annotation.getComboClass(reflectionJavaField);
        //        Class serviceClazz = annotation.getServiceClass(reflectionJavaField);
        //        Class linkClazz = annotation.getLinkClass(reflectionJavaField);
        //        List enumItems = annotation.getEnumItems(reflectionJavaField);
        //        List items;

        if (binder == null || reflectionJavaField == null || field == null) {
            return;
        }

        message = annotation.getMessage(reflectionJavaField);
        messageSize = annotation.getMessageSize(reflectionJavaField);
        messageNotNull = annotation.getMessageNull(reflectionJavaField);
        min = annotation.getSizeMin(reflectionJavaField);
        max = annotation.getSizeMax(reflectionJavaField);
        stringBlankValidator = new AStringBlankValidator(message);
        stringLengthValidator = new StringLengthValidator(messageSize, min, max);
        integerZeroValidator = new AIntegerZeroValidator();

        fieldName = reflectionJavaField.getName();
        if (type != null) {
            builder = binder.forField(field.getBinder());
            switch (type) {
                case text:
                    if (stringBlankValidator != null) {
                        builder.withValidator(stringBlankValidator);
                    }
                    if (stringLengthValidator != null) {
                        builder.withValidator(stringLengthValidator);
                    }
                    break;
                case integer:
                    if (min > 0) {
                        ((IntegerField) field.getBinder()).setHasControls(true);
                        ((IntegerField) field.getBinder()).setMin(min);
                        field.setWidth(widthForNumber);
                    }
                    if (max > 0) {
                        ((IntegerField) field.getBinder()).setHasControls(true);
                        ((IntegerField) field.getBinder()).setMax(max);
                        field.setWidth(widthForNumber);
                    }

                    if (integerZeroValidator != null) {
                        builder.withValidator(integerZeroValidator);
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
                    logger.warn("Switch - caso non definito per il field \"" + reflectionJavaField.getName() + "\" del tipo " + type, this.getClass(), "addFieldToBinder");
                    break;
            }
            if (builder != null) {
                builder.bind(fieldName);
            }
        }
    }

}