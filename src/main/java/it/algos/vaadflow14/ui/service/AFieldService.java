package it.algos.vaadflow14.ui.service;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEFieldType;
import it.algos.vaadflow14.backend.service.AAbstractService;
import it.algos.vaadflow14.ui.fields.*;
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
        AIField algosField = null;
        Field reflectionJavaField = reflection.getField(entityBean.getClass(), propertyName);

        if (reflectionJavaField == null) {
            return null;
        }

        algosField = creaOnly(reflectionJavaField);
        algosField.setText(propertyName);

        if (algosField != null) {
            addFieldToBinder(binder, reflectionJavaField, algosField);
        }

        return algosField;
    }


    /**
     * Create a single field.
     * The field type is chosen according to the annotation @AIField.
     *
     * @param reflectionJavaField di riferimento
     */
    public AIField creaOnly(Field reflectionJavaField) {
        AIField field = null;
        AEFieldType type = annotation.getFormType(reflectionJavaField);

        if (type != null) {
            switch (type) {
                case text:
                    field = new ATextField();
                    break;
                case integer:
                    field = new AIntegerField();
                    break;
                case yesNo:
                    field = new ABooleanField();
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

        return field;
    }


    protected void addFieldToBinder(Binder binder, Field reflectionJavaField, AIField algosField) {
        AEFieldType type = annotation.getFormType(reflectionJavaField);
        String fieldName = VUOTA;
        //        Class comboClazz = annotation.getComboClass(reflectionJavaField);
        //        Class serviceClazz = annotation.getServiceClass(reflectionJavaField);
        //        Class linkClazz = annotation.getLinkClass(reflectionJavaField);
        //        List enumItems = annotation.getEnumItems(reflectionJavaField);
        //        List items;

        if (binder == null || reflectionJavaField == null || algosField == null) {
            return;
        }

        fieldName = reflectionJavaField.getName();
        switch (type) {
            case text:
                binder.forField((TextField)algosField.getComp()).bind(fieldName);

//                        .withValidator(stringNullValidator)
//
//                        .withValidator(lengthValidator)
//
//                        .withValidator(uniqueValidator)

                break;
            case integer:
                binder.forField((IntegerField)algosField.getComp()).bind(fieldName);
                break;
            case yesNo:
//                binder.forField(algosField.get()).bind(fieldName);
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
    }

}