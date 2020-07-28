package it.algos.vaadflow14.backend.annotation;

import com.vaadin.flow.component.icon.VaadinIcon;

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
 * Time: 11:15
 * <p>
 * Annotation per le View Class che usano @Route <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //--Class, interface (including annotation type), or enum declaration
public @interface AIView {


    /**
     * (Optional) Icona visibile nel menu
     * Di default un asterisco
     *
     * @return the vaadin icon
     */
    VaadinIcon menuIcon() default VaadinIcon.ASTERISK;

    /**
     * (Optional) Label del menu
     * Vaadin usa SEMPRE il 'name' della Annotation @Route per identificare (internamente) e recuperare la view
     * Nella menuBar appare invece visibile (con il primo carattere maiuscolo) il menuName, indicato qui
     * Di default usa il 'name' della view (@Route)
     *
     * @return the string
     */
    String menuName() default "";

    /**
     * (Optional) Tag della @Route per aprire il Form
     *
     * @return the string
     */
    String routeFormName() default "";

    /**
     * (Optional) Mostra la lista vuota all'apertura. Da usare SOLO se ci sono filtri di selezione.
     * Altrimenti non si vedrà mai niente
     *
     * @return the boolean
     */
    boolean startListEmpty() default false;

    /**
     * (Optional) Property per la ricerca tramite il searchField
     *
     * @return the string
     */
    String searchProperty() default "";

    /**
     * (Optional) Property per l'ordinamento
     *
     * @return the string
     */
    String sortProperty() default "";

    /**
     * (Optional) Direzione per l'ordinamento
     *
     * @return the string
     */
    String sortDirection() default "ASC";

    /**
     * (Optional) Appartenenza al progetto Base VaadFlow15
     *
     * @return the boolean
     */
    boolean vaadFlow() default false;


}
