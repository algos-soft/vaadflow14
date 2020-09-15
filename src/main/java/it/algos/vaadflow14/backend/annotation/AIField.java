package it.algos.vaadflow14.backend.annotation;

import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import it.algos.vaadflow14.backend.enumeration.AETypeData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * /**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: lun, 27-apr-2020
 * Time: 14:55
 * <p>
 * Annotation per i fields (property) delle Entity Class <br>
 * Annotation to add some property for a single field <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //--Field declaration (includes enum constants)
public @interface AIField {


    /**
     * (Required) The type of the field.
     * Defaults to the text type.
     *
     * @return the ae field type
     */
    AETypeField type() default AETypeField.text;


    /**
     * (Optional) Classe della property.
     * Utilizzato nei Link.
     *
     * @return the class
     */
    Class<?> linkClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nelle Enumeration.
     *
     * @return the class
     */
    Class<?> enumClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nei fields calcolati (ed altro).
     *
     * @return the class
     */
    Class<?> logicClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nei Combo.
     */
    Class<?> comboClazz() default Object.class;

    /**
     * (Optional) valori (items) della enumeration
     * Presentati in successione e separati da virgola
     * Vengono poi convertiti in una List
     * Defaults to vuota.
     *
     * @return the string
     */
    String items() default VUOTA;


    /**
     * (Optional) The visible name of the field.
     * Defaults to the property or field name.
     *
     * @return the string
     */
    String caption() default VUOTA;


    /**
     * (Optional) The width of the field.
     * Expressed in double, to be converted in String ending with "em"
     * Defaults to 0.
     *
     * @return the int
     */
    double widthEM() default 0;


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
     * Defaults to vuota.
     *
     * @return the string
     */
    String help() default VUOTA;


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
     * Meaning sense only for AETypeField.combo.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean allowCustomValue() default false;

    /**
     * (Optional) color of the component
     * Defaults to vuota.
     *
     * @return the string
     */
    String color() default VUOTA;

    /**
     * (Optional) method name for reflection
     * Defaults to findItems.
     *
     * @return the string
     */
    String methodName() default "findItems";

    /**
     * (Optional) property name for reflection
     * Defaults to vuota.
     *
     * @return the string
     */
    String propertyLinkata() default VUOTA;

    /**
     * (Optional) placeholder for empty field
     * Defaults to vuota.
     *
     * @return the string
     */
    String placeholder() default VUOTA;

    /**
     * (Optional) The type of the number range.
     * Defaults to the positiviOnly type.
     *
     * @return the field type
     */
    AETypeNum typeNum() default AETypeNum.positiviOnly;


    /**
     * (Optional) The type of the boolean type.
     * Defaults to the checkBox type.
     *
     * @return the field type
     */
    AETypeBoolField typeBool() default AETypeBoolField.checkBox;


    /**
     * (Optional) The two strings for boolean type.
     * Defaults to vuota.
     *
     * @return the strings
     */
    String boolEnum() default VUOTA;

    /**
     * The type of the data type
     * Defaults to standard type.
     *
     * @return the strings
     */
    AETypeData getTypeData() default AETypeData.standard;

}
