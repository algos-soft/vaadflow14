package it.algos.vaadflow14.ui.service;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeBool;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.service.AAbstractService;
import it.algos.vaadflow14.ui.fields.ACheckBox;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: sab, 25-lug-2020
 * Time: 13:55
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired public AArrayService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@VaadinSessionScope()
public class AColumnService extends AAbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Create a single column.
     * The column type is chosen according to the annotation @AIColumn or, if is not present, a @AIField.
     *
     * @param grid         a cui aggiungere la colonna
     * @param entityClazz  modello-dati specifico
     * @param propertyName della property
     */
    public Grid.Column<AEntity> add(Grid grid, Class<? extends AEntity> entityClazz, String propertyName) {
        return add(grid, entityClazz, propertyName, VUOTA);
    }


    /**
     * Create a single column.
     * The column type is chosen according to the annotation @AIColumn or, if is not present, a @AIField.
     *
     * @param grid           a cui aggiungere la colonna
     * @param entityClazz    modello-dati specifico
     * @param propertyName   della property
     * @param propertySearch per l' ordinamento
     */
    public Grid.Column<AEntity> add(Grid grid, Class<? extends AEntity> entityClazz, String propertyName, String propertySearch) {
        Grid.Column<AEntity> colonna = null;
        Field field = reflection.getField(entityClazz, propertyName);
        AETypeField type = null;
        AETypeBool typeBool = AETypeBool.checkBox;
        String header = VUOTA;
        VaadinIcon headerIcon = null;
        String colorHeaderIcon = VUOTA;
        boolean isFlexGrow = false;
        String width = VUOTA;
        Label label = null;
        boolean sortable = false;

        if (field != null) {
            type = annotation.getColumnType(field);
            typeBool = annotation.getTypeBoolean(field);
            header = annotation.getColumnHeader(field);
            headerIcon = annotation.getHeaderIcon(field);
            colorHeaderIcon = annotation.getHeaderIconColor(field);
            width = annotation.getColumnWith(field);
            isFlexGrow = annotation.isFlexGrow(field);
        }

        if (type == null) {
            logger.error("Manca il type del field " + propertyName + " della entity " + entityClazz.getSimpleName(), this.getClass(), "add");
            return null;
        } else {
            switch (type) {
                case text:
                case email:
                    colonna = grid.addColumn(propertyName);
                    sortable = true;
                    break;
                case textArea:
                    colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                        String testo = VUOTA;
                        try {
                            if (field.get(entity) instanceof String) {
                                testo = (String) field.get(entity);
                            }
                        } catch (Exception unErrore) {
                            logger.error(unErrore, this.getClass(), "add.text");
                        }

                        return new Label(testo);
                    }));//end of lambda expressions and anonymous inner class
                    break;
                case integer:
                    colonna = grid.addColumn(propertyName);
                    sortable = true;

                    //                    colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    //                        String testo = VUOTA;
                    //                        int value;
                    //
                    //                        try {
                    //                            value = field.getInt(entity);
                    //                            testo = value + "";//@todo Funzionalità ancora da implementare per formattazione
                    //                        } catch (Exception unErrore) {
                    //                            logger.error(unErrore, this.getClass(), "add.integer");
                    //                        }
                    //
                    //                        return new Label(testo);
                    //                    }));//end of lambda expressions and anonymous inner class
                    break;
                case booleano:
                    //                    colonna = grid.addColumn(propertyName);

                    colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                        boolean status = false;

                        try {
                            status = field.getBoolean(entity);
                        } catch (Exception unErrore) {
                            logger.error(unErrore, this.getClass(), "add.booleano");
                        }

                        return new ACheckBox(status);
                    }));//end of lambda expressions and anonymous inner class
                    break;
                case combo:
                    colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                        String testo = VUOTA;

                        try {
                            testo = field.get(entity) != null ? field.get(entity).toString() : VUOTA;
                        } catch (Exception unErrore) {
                            logger.error(unErrore, this.getClass(), "add.combo");
                        }

                        return new Label(testo);
                    }));//end of lambda expressions and anonymous inner class
                    break;
                default:
                    logger.warn("Switch - caso non definito", this.getClass(), "add");
                    break;
            }
        }

        if (colonna != null) {
            String headerText = "";
            label = new Label();
            label.getElement().getStyle().set("color", "blue");

            //--l'header di default viene sempre uguale al nome della property
            //--può essere minuscolo o con la prima maiuscola, a seconda del flag di preferenza
            //--può essere modificato con name = "Xyz" nell'annotation @AIColumn della Entity
            //            if (pref.isBool(USA_GRID_HEADER_PRIMA_MAIUSCOLA)) {
            //                headerNotNull = text.primaMaiuscola(headerNotNull);
            //            } else {
            //                headerNotNull = headerNotNull.toLowerCase();
            //            }// end of if/else cycle
            header = text.primaMaiuscola(header);//@todo Funzionalità ancora da implementare
            colonna.setHeader(header);

            //--eventuale aggiunta di una icona e l' header non è una String ma diventa un Component
            //--se c'è l' icona e manca il testo della annotation, NON usa il nome della property ma solo l' icona
            if (headerIcon != null) {
                Icon icon = new Icon(headerIcon);
                //                icon.setSize(widthHeaderIcon);
                icon.setColor(colorHeaderIcon);
                label.add(icon);
            } else {
                label.setText(header);
                //                colonna.setHeader(headerNotNull);
            }
            colonna.setHeader(label);

            //            if (propertyName.equals(searchProperty)) {
            //                Icon icon = new Icon(VaadinIcon.SEARCH);
            //                icon.setSize("10px");
            //                icon.getStyle().set("float", "center");
            //                icon.setColor("red");
            //                Label label = new Label();
            //                label.add(icon);
            //                label.add(text.primaMaiuscola(headerNotNull));
            //                label.getElement().getStyle().set("fontWeight", "bold");
            //                colonna.setHeader(label);
            //            }// end of if cycle

            //            //--di default le colonne NON sono sortable
            //            //--può essere modificata con sortable = true, nell'annotation @AIColumn della Entity
            //            colonna.setSortable(sortable);
            //            //            colonna.setSortProperty(propertyName);

            //            //            if (property.equals("id")) {
            //            //                colonna.setWidth("1px");
            //            //            }// end of if cycle
            colonna.setSortable(sortable);
            colonna.setSortProperty(propertyName);

            colonna.setWidth(width);
            colonna.setFlexGrow(isFlexGrow ? 1 : 0);
        }
        return colonna;
    }

}