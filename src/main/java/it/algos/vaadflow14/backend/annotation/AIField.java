package it.algos.vaadflow14.backend.annotation;

import it.algos.vaadflow14.backend.enumeration.AEFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * /**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: lun, 27-apr-2020
 * Time: 14:55
 * <p>
 * Annotation per i fields (property) delle Domain Class <br>
 * Annotation to add some property for a single field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //--Field declaration (includes enum constants)
public @interface AIField {


    /**
     * (Optional) Classe della property.
     * Utilizzato nei Link.
     *
     * @return the class
     */
    Class<? extends Object> linkClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nelle Enumeration.
     *
     * @return the class
     */
    Class<? extends Object> enumClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nei Combo.
     *
     * @return the class
     */
    Class<? extends Object> logicClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nei Combo.
     */
    Class<? extends Object> comboClazz() default Object.class;

    /**
     * (Optional) valori (items) della enumeration
     * Defaults to "".
     *
     * @return the string
     */
    String items() default "";


    /**
     * (Required) The type of the field.
     * Defaults to the text type.
     *
     * @return the ae field type
     */
    AEFieldType type() default AEFieldType.text;


    /**
     * (Optional) The name of the field.
     * Defaults to the property or field name.
     *
     * @return the string
     */
    String name() default "";


    /**
     * (Optional) The width of the field.
     * Expressed in int, to be converted in String ending with "em"
     * Defaults to 0.
     *
     * @return the int
     */
    int widthEM() default 0;


    /**
     * (Optional) The number of rows of textArea field.
     * Expressed in int
     * Defaults to 3.
     *
     * @return the int
     */
    int numRowsTextArea() default 3;


    /**
     * (Optional) Status (field required for DB) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean required() default false;


    /**
     * (Optional) field that get focus
     * Only one for form
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean focus() default false;


    /**
     * (Optional) help text on rollover
     * Defaults to null.
     *
     * @return the string
     */
    String help() default "";


    /**
     * (Optional) Status (first letter capital) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean firstCapital() default false;


    /**
     * (Optional) Status (all letters upper) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean allUpper() default false;


    /**
     * (Optional) Status (all letters lower) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean allLower() default false;


    /**
     * (Optional) Status (only number) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean onlyNumber() default false;


    /**
     * (Optional) Status (only letters) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean onlyLetter() default false;


    /**
     * (Optional) Status (allowed null selection in popup) of the the field.
     * Meaning sense only for EAFieldType.combo.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean nullSelectionAllowed() default false;


    /**
     * (Optional) Status (allowed new selection in popup) of the the field.
     * Meaning sense only for EAFieldType.combo.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean newItemsAllowed() default false;

    /**
     * (Optional) color of the component
     * Defaults to "".
     *
     * @return the string
     */
    String color() default "";

    /**
     * (Optional) method name for reflection
     * Defaults to findItems.
     *
     * @return the string
     */
    String methodName() default "findItems";

    /**
     * (Optional) property name for reflection
     * Defaults to "".
     *
     * @return the string
     */
    String propertyLinkata() default "";


}
