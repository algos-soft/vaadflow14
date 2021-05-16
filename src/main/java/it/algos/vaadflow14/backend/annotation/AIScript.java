package it.algos.vaadflow14.backend.annotation;

import it.algos.vaadflow14.backend.enumeration.*;

import java.lang.annotation.*;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: lun, 27-apr-2020
 * Time: 12:12
 * <p>
 * Annotation Algos per tutte le classi <br>
 * Controlla il flusso degli scripts di modifica del framework. <br>
 * <p>
 * Regola:
 * sovraScrivibile()=false -> Se il file può essere sovrascritto
 * type()=AETypeFile.nessuno -> Tipologia dei file Algos sia generici che del package
 * <p>
 * Standard:
 * AIScript(sovraScrivibile = false, type = AETypeFile.nessuno)
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
//--Class, interface (including annotation type), or enum declaration
public @interface AIScript {


    /**
     * (Optional) Status of this file for Wizard re-working.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean sovraScrivibile() default false;

    /**
     * (Required) The type of the Algos file.
     * Defaults to AETypeFile.nessuno.
     *
     * @return the Algos file type
     */
    AETypeFile type() default AETypeFile.nessuno;

}
