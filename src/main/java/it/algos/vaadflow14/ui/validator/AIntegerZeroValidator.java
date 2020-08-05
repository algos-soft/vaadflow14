package it.algos.vaadflow14.ui.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project it.algos.vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 07-giu-2018
 * Time: 17:03
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AIntegerZeroValidator implements Validator {

    private static final long serialVersionUID = 1L;


    /**
     * Prima passa da StringToIntegerConverter ed arriva sempre un integer
     * Se il field è vuoto, arriva un integer uguale a zero
     * Non viene accettato
     *
     * @return the function result
     */
    @Override
    public ValidationResult apply(Object obj, ValueContext valueContext) {
        String testo;

        if (obj == null) {
            return ValidationResult.error("Occorre inserire un numero");
        }

        if (obj instanceof Integer) {
            if ((Integer) obj <= 0) {
                if ((Integer) obj == 0) {
                    return ValidationResult.error("Il valore zero non è ammesso");
                } else {
                    return ValidationResult.error("Il valore deve essere maggiore di zero");
                }
            } else {
                return ValidationResult.ok();
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
