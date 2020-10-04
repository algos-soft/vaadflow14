package it.algos.vaadflow14.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.logic.ALogic;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 27-ago-2020
 * Time: 13:52
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AGenericForm extends AForm {


    public AGenericForm(ALogic logic, WrapForm wrap) {
        super(logic, wrap);
    }

}
