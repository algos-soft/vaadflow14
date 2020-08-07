package it.algos.vaadflow14.ui.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.service.ALogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 06-ago-2020
 * Time: 15:39
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AUniqueValidator implements Validator {

    private static final long serialVersionUID = 1L;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ALogService logger;

    private AEOperation operation;


    /**
     * Costruttore base senza parametri <br>
     * Non usato. Serve solo per 'coprire' un piccolo bug di Idea <br>
     * Se manca, manda in rosso i parametri del costruttore usato <br>
     */
    public AUniqueValidator() {
    } // end of SpringBoot constructor


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(AUniqueValidator.class, operation) <br>
     *
     * @param operation per differenziare tra nuova entity e modifica di esistente
     */
    public AUniqueValidator(AEOperation operation) {
        this.operation = operation;
    } // end of SpringBoot constructor


    /**
     * Prima passa da StringToIntegerConverter ed arriva sempre un integer
     * Se il field è vuoto, arriva un integer uguale a zero
     * Non viene accettato
     *
     * @return the function result
     */
    @Override
    public ValidationResult apply(Object obj, ValueContext valueContext) {
        int numero = 0;

        if (obj == null) {
            return ValidationResult.error("Occorre inserire un valore");
        }

        if (operation != null) {
            switch (operation) {
                case addNew:

                    break;
                case edit:
                case editDaLink:
                case editNoDelete:
                case editProfile:
                    break;
                case showOnly:

                    break;
                default:
                    logger.warn("Switch - caso non definito", this.getClass(), "apply");
                    break;
            }
        }

        return ValidationResult.error("Qualcosa non ha funzionato");
    }


    /**
     * Applies this function to the given arguments.
     *
     * @param o  the first function argument
     * @param o2 the second function argument
     *
     * @return the function result
     */
    @Override
    public Object apply(Object o, Object o2) {
        return null;
    }

}
