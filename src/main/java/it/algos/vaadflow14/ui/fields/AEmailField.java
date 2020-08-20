package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 16-ago-2020
 * Time: 17:44
 * Simple layer around EmailField <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 * Normalmente i fields vengono creati con new xxxField() <br>
 * Se necessitano di injection, occorre usare appContext.getBean(xxxField.class) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AEmailField extends AField<String> {

    private final EmailField innerField;


    public AEmailField(String fieldKey, String label) {
        innerField = new EmailField(label);
        super.fieldKey = fieldKey;
        innerField.setAutoselect(true);
        add(innerField);
    }


    @Override
    protected String generateModelValue() {
        return innerField.getValue();
    }


    @Override
    protected void setPresentationValue(String value) {
        innerField.setValue(value);
    }


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     *
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    public int compareTo(String o) {
        return innerField.getValue().compareTo(o);
    }


    @Override
    public void setWidth(String width) {
        innerField.setWidth(width);
    }


    @Override
    public AbstractSinglePropertyField getBinder() {
        return innerField;
    }


    @Override
    public void setAutofocus() {
        innerField.setAutofocus(true);
    }


    public EmailField getMail() {
        return innerField;
    }

}
