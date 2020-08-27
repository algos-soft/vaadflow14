package it.algos.vaadflow14.backend.packages.preferenza;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.ui.form.AForm;
import it.algos.vaadflow14.ui.form.WrapForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 27-ago-2020
 * Time: 06:37
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PreferenzaForm extends AForm {



    public PreferenzaForm(WrapForm wrap) {
        super(wrap);
    }

}
