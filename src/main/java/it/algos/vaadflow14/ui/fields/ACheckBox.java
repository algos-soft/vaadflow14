package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project it.algos.vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 28-mag-2018
 * Time: 08:37
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ACheckBox extends Checkbox {

    public ACheckBox() {
    }


    public ACheckBox(String labelText) {
        super(labelText);
    }


    public ACheckBox(boolean initialValue) {
        super(initialValue);
    }


    public ACheckBox(String labelText, boolean initialValue) {
        super(labelText, initialValue);
    }

}
