package it.algos.vaadflow14.backend.annotation;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

import java.lang.annotation.*;


/**
 * /**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: lun, 27-apr-2020
 * Time: 14:55
 * <p>
 * Annotation per le Entity Class <br>
 * Lista dei fields automatici nel dialogo del Form <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //--Class, interface (including annotation type), or enum declaration
public @interface AIForm  {


    /**
     * (Optional) List of visible fields on Form
     * Presentati in successione e separati da virgola
     * Vengono poi convertiti in una List
     * Defaults to all.
     *
     * @return the string commas separate
     */
    String fields() default VUOTA;


    /**
     * (Optional) The width of the ID field.
     * Expressed in int, to be converted in String ending with "em"
     * Defaults to "16em".
     *
     * @return the int
     */
    int widthIDEM() default 16;


    /**
     * (Optional) utilizzo delle frecce per spostarsi tra schede precedenti e successive
     * Di default false
     *
     * @return the status
     */
    boolean usaSpostamentoTraSchede() default false;

}
