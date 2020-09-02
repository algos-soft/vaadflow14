package it.algos.vaadflow14.backend.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: lun, 27-apr-2020
 * Time: 14:55
 * <p>
 * Annotation per le Entity Class <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //--Class, interface (including annotation type), or enum declaration
public @interface AIEntity {

    /**
     * (Optional) Label del record
     * Usato nel dialog come Edit...recordName oppure Modifica...recordName
     * Di default usa il 'name' della collection mongoDB @Document
     *
     * @return the string
     */
    String recordName() default VUOTA;

    /**
     * (Optional) key property unica
     * Di default usa la property 'id' della collection mongoDB
     *
     * @return the string
     */
    String keyPropertyName() default VUOTA;

    /**
     * (Optional) il campo della superclasse
     * Di default false
     *
     * @return the status
     */
    boolean usaCompany() default true;

    /**
     * (Optional) il campo della superclasse
     * Di default false
     *
     * @return the status
     */
    boolean usaNote() default false;


    /**
     * (Optional) registra la creazione e la modifica
     * Di default false
     *
     * @return the status
     */
    boolean usaCreazioneModifica() default false;

    /**
     * (Optional) prima colonna indice qa sinistra della grid
     * Di default false
     *
     * @return the status
     */
    boolean usaRowIndex() default false;


}