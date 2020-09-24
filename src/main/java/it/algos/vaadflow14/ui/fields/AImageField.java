package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.simple.backend.packages.Delta;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.io.ByteArrayInputStream;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 24-set-2020
 * Time: 20:35
 * <p>
 * Simple layer around TextField <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AImageField extends AField<String> {


    private final Image imageField;


    /**
     * Costruttore senza parametri <br>
     * L' istanza viene costruita con appContext.getBean(ATextField.class) <br>
     */
    public AImageField() {
        imageField = new Image();
        add(imageField);
    } // end of SpringBoot constructor


    @Override
    protected String generateModelValue() {
        return VUOTA;
    }


    @Override
    protected void setPresentationValue(String value) {
        byte[]  bytesDue= Base64.decodeBase64( value);
        StreamResource resource = null;
        try {
            resource = new StreamResource("dummyImageName.jpg", () -> new ByteArrayInputStream(bytesDue));
        } catch (Exception unErrore) {
        }
//        imageField.setWidth("21px");
//        imageField.setHeight("21px");

        imageField.setSrc(resource);
    }



    @Override
    public void setWidth(String width) {
        imageField.setWidth("21px");
        imageField.setHeight("21px");
    }

}
