package it.algos.vaadflow14.backend.annotation;

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
 * Annotation per le Domain Class <br>
 * Lista dei fields automatici nel dialogo del Form <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //--Class, interface (including annotation type), or enum declaration
public @interface AIForm  {


    /**
     * (Optional) List of visible fields on Grid
     * Defaults to all.
     *
     * @return the string commas separate
     */
    String fields() default "";


    /**
     * (Optional) The width of the ID field.
     * Expressed in int, to be converted in String ending with "em"
     * Defaults to "16em".
     *
     * @return the int
     */
    int widthIDEM() default 16;


}
