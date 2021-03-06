package it.algos.vaadflow14.backend.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AETypePref;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project it.algos.vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 27-mag-2018
 * Time: 14:11
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AConverterPrefByte implements Converter<String, byte[]> {

    private AETypePref type;


    @Override
    public Result<byte[]> convertToModel(String stringValue, ValueContext valueContext) {
        if (type != null) {
            return Result.ok((byte[]) type.objectToBytes(stringValue));
        } else {
            return Result.ok((byte[]) null);
        }
    }


    @Override
    public String convertToPresentation(byte[] bytes, ValueContext valueContext) {
        String stringValue = "";
        Object genericValue = null;

        if (type != null && bytes != null) {
            genericValue = type.bytesToObject(bytes);

            if (genericValue instanceof String) {
                stringValue = (String) genericValue;
            }

            if (genericValue instanceof Integer) {
                stringValue = ((Integer) genericValue).toString();
            }

            if (genericValue instanceof Boolean) {
                stringValue = genericValue.toString();
            }
        }

        return stringValue;
    }


    public AETypePref getType() {
        return type;
    }


    public void setType(AETypePref type) {
        this.type = type;
    }

}
