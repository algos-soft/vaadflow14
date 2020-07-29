package it.algos.vaadflow14.backend.annotation;


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
 * Annotation per le Entity Class <br>
 * Lista dei fields automatici nella Grid delle liste <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //--Class, interface (including annotation type), or enum declaration
public @interface AIList {


    /**
     * (Optional) List of visible fields on Grid
     * Presentati in successione e separati da virgola
     * Vengono poi convertiti in una List
     * Defaults to all.
     *
     * @return the string commas separate
     */
    String fields() default VUOTA;


    /**
     * (Optional) The width of the ID columnService.
     * Defaults to 290
     *
     * @return the int
     */
    int widthID() default 290;


}
