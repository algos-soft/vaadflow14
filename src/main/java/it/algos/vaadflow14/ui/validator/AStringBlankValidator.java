package it.algos.vaadflow14.ui.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project it.algos.vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 07-giu-2018
 * Time: 16:28
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AStringBlankValidator implements Validator {

    private static final long serialVersionUID = 1L;

    private String message = VUOTA;


    public AStringBlankValidator() {
    }


    public AStringBlankValidator(String message) {
        this.message = message;
    }


    @Override
    public ValidationResult apply(Object obj, ValueContext valueContext) {
        String testo;

        if (obj instanceof String) {
            testo = (String) obj;
            if (testo.length() == 0) {
                if (message.equals("")) {
                    return ValidationResult.error("Il contenuto di questo campo non può essere vuoto");
                } else {
                    return ValidationResult.error(message);
                }
            } else {
                return ValidationResult.ok();
            }
        } else {
            if (message.equals("")) {
                return ValidationResult.error("Il contenuto di questo campo non può essere vuoto");
            } else {
                return ValidationResult.error(message);
            }
        }
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
