package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.packages.preferenza.Preferenza;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Collection;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 27-ago-2020
 * Time: 18:05
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class APreferenzaField extends AField<byte[]> {

    private final TextField innerField;

    protected String fieldKey;

    protected String caption;

    private Preferenza entityBean;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(APreferenzaField.class, fieldKey, caption) <br>
     *
     * @param fieldKey nome interno del field
     * @param caption  label visibile del field
     * @param entityBean  in elaborazione
     */
    public APreferenzaField(String fieldKey, String caption, Preferenza entityBean) {
        this.fieldKey = fieldKey;
        this.caption = caption;
        this.entityBean = entityBean;
        innerField = new TextField(caption);
        add(innerField);
    } // end of SpringBoot constructor


    @Override
    protected byte[] generateModelValue() {
//        return entityBean.getType().objectToBytes("mariolino");
        return null;
    }


    @Override
    protected void setPresentationValue(byte[] o) {
    }


    @Override
    public void setItem(Collection collection) {

    }


    @Override
    public void setWidth(String width) {

    }


    @Override
    public void setText(String caption) {

    }


    @Override
    public AbstractSinglePropertyField getBinder() {
        return innerField;
    }


    //    @Override
    //    public Component get() {
    //        return innerField;
    //    }


    @Override
    public void setAutofocus() {

    }


    @Override
    public String getKey() {
        return fieldKey;
    }


    @Override
    public void setErrorMessage(String errorMessage) {

    }

}
