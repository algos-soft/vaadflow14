package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

import java.util.Collection;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 10-giu-2020
 * Time: 16:25
 */
public class AField<T> extends CustomField<T> implements AIField {

    @Override
    public void setItem(Collection collection) {
    }


    @Override
    protected T generateModelValue() {
        return null;
    }


    @Override
    protected void setPresentationValue(T o) {
    }


    //    @Override
    //    public void setValue(T o) {
    //
    //    }


    @Override
    public Registration addValueChangeListener(ValueChangeListener valueChangeListener) {
        return null;
    }


    @Override
    public void setWidth(String width) {

    }


    @Override
    public void setText(String caption) {

    }


    @Override
    public void setValue(Object o) {

    }


    @Override
    public AbstractSinglePropertyField getBinder() {
        return null;
    }


    @Override
    public AField get() {
        return this;
    }
    @Override
    public void setPlaceholder(String placeholder) {
    }

}
