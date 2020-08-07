package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;

import java.util.Collection;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 10-giu-2020
 * Time: 16:19
 */
public interface AIField {

    void setItem(Collection collection);

    void setWidth(String width);

    void setText(String caption);

    AbstractSinglePropertyField getBinder();

    AField get();

    public void setPlaceholder(String placeholder);
    public void setAutofocus();

}
