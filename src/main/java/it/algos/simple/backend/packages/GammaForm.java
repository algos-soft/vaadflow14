package it.algos.simple.backend.packages;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.ui.form.AForm;
import it.algos.vaadflow14.ui.form.WrapForm;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 05-set-2020
 * Time: 07:27
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GammaForm extends AForm {

    public GammaForm(WrapForm wrap) {
        super(wrap);
    }

}
