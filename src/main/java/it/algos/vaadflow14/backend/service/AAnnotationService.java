package it.algos.vaadflow14.backend.service;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.ui.view.AView;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;


/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 07-dic-2017
 * Time: 21:58
 * <p>
 * Classe di servizio per le Annotation <br>
 * Gestisce le interfacce specifiche di VaadFlow: AIEntity, AIView, AIList, AIForm, AIColumn, AIField <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private AAnnotationService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AAnnotationService extends AAbstractService {


    /**
     * The constant TESTO_NULL.
     */
    public static final String TESTO_NULL = " non può essere vuoto";

    /**
     * The constant OBJECT_NULL.
     */
    public static final String OBJECT_NULL = " non può essere nullo";

    /**
     * The constant INT_NULL.
     */
    public static final String INT_NULL = " deve contenere solo caratteri numerici";

    /**
     * The constant INT_ZERO.
     */
    public static final String INT_ZERO = " deve essere maggiore di zero";


    /**
     * The constant TAG_PX.
     */
    public static final String TAG_PX = "px";

    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Get the annotation Algos AIEntity.
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific annotation
     */
    public AIEntity getAIEntity(final Class<? extends AEntity> entityClazz) {
        return (entityClazz != null && AEntity.class.isAssignableFrom(entityClazz)) ? entityClazz.getAnnotation(AIEntity.class) : null;
    }


    /**
     * Get the annotation Algos AIList.
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific annotation
     */
    public AIList getAIList(final Class<? extends AEntity> entityClazz) {
        return (entityClazz != null && AEntity.class.isAssignableFrom(entityClazz)) ? entityClazz.getAnnotation(AIList.class) : null;
    }


    /**
     * Get the annotation Algos AIForm.
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific annotation
     */
    public AIForm getAIForm(final Class<? extends AEntity> entityClazz) {
        return (entityClazz != null && AEntity.class.isAssignableFrom(entityClazz)) ? entityClazz.getAnnotation(AIForm.class) : null;
    }


    /**
     * Get the annotation Algos AIView.
     *
     * @param entityViewClazz the class of type AEntity or AView
     *
     * @return the specific annotation
     */
    public AIView getAIView(final Class<?> entityViewClazz) {
        AIView annotation = null;

        // controlla che il parametro in ingresso sia di una delle due classi previste
        if (entityViewClazz != null && AEntity.class.isAssignableFrom(entityViewClazz)) {
            annotation = getAIViewEntity((Class<? extends AEntity>) entityViewClazz);
            return annotation;
        }

        // controlla che il parametro in ingresso sia di una delle due clasi previste
        if (entityViewClazz != null && AView.class.isAssignableFrom(entityViewClazz)) {
            annotation = getAIViewView((Class<? extends AView>) entityViewClazz);
            return annotation;
        }

        return null;
    }


    /**
     * Get the annotation Algos AIView.
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific annotation
     */
    public AIView getAIViewEntity(final Class<? extends AEntity> entityClazz) {
        return (entityClazz != null && AEntity.class.isAssignableFrom(entityClazz)) ? entityClazz.getAnnotation(AIView.class) : null;
    }


    /**
     * Get the annotation Algos AIView.
     *
     * @param viewClazz the class of type AView
     *
     * @return the specific annotation
     */
    public AIView getAIViewView(final Class<? extends AView> viewClazz) {
        return (viewClazz != null && AView.class.isAssignableFrom(viewClazz)) ? viewClazz.getAnnotation(AIView.class) : null;
    }


    /**
     * Get the annotation Algos AIColumn.
     *
     * @param reflectionJavaField di riferimento per estrarre l'interfaccia
     *
     * @return the specific annotation
     */
    public AIColumn getAIColumn(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(AIColumn.class) : null;
    }


    /**
     * Get the annotation Algos AIColumn.
     *
     * @param entityClazz the class of type AEntity
     * @param fieldName   the property name
     *
     * @return the specific annotation
     */
    public AIColumn getAIColumn(Class<? extends AEntity> entityClazz, String fieldName) {
        AIColumn annotation = null;
        List<Field> listaFields;

        // Controlla che il parametro in ingresso non sia nullo
        if (entityClazz == null) {
            return null;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (text.isEmpty(fieldName)) {
            return null;
        }

        try {
            listaFields = reflection.getFields(entityClazz);

            //            if (array.isValid(listaFields)) { //@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
            if (listaFields != null) {
                for (Field reflectionJavaField : listaFields) {
                    if (reflectionJavaField.getName().equals(fieldName)) {
                        annotation = getAIColumn(reflectionJavaField);
                        break;
                    }// end of if cycle
                }// end of for cycle

            }// end of if cycle
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }

        return annotation;
    }


    /**
     * Get the annotation Algos AIField.
     *
     * @param reflectionJavaField di riferimento per estrarre l'interfaccia
     *
     * @return the specific annotation
     */
    public AIField getAIField(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(AIField.class) : null;
    }


    /**
     * Get the annotation Algos AIField.
     *
     * @param entityClazz the class of type AEntity
     * @param fieldName   the property name
     *
     * @return the specific annotation
     */
    public AIField getAIField(Class<? extends AEntity> entityClazz, String fieldName) {
        AIField annotation = null;
        Field reflectionJavaField;

        // Controlla che il parametro in ingresso non sia nullo
        if (entityClazz == null) {
            return null;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (text.isEmpty(fieldName)) {
            return null;
        }

        try {
            reflectionJavaField = entityClazz.getDeclaredField(fieldName);
            annotation = getAIField(reflectionJavaField);
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }

        return annotation;
    }


    /**
     * Get the annotation Route.
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che esista l' annotation specifica <br>
     *
     * @param genericClazz of all types
     *
     * @return the specific Annotation
     */
    public Route getRoute(final Class<?> genericClazz) {
        return genericClazz != null ? genericClazz.getAnnotation(Route.class) : null;
    }


    /**
     * Check if the view has a @Route annotation.
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che esista l'annotation specifica <br>
     *
     * @param genericClazz of all types
     *
     * @return true if the class as a @Route
     */
    public boolean isRouteView(final Class<?> genericClazz) {
        return getRoute(genericClazz) != null;
    }


    /**
     * Check if the class is an entityBean class.
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     *
     * @param genericClazz to be checked if is of type AEntity
     *
     * @return true if the class is of type AEntity
     */
    public boolean isEntityClass(final Class<?> genericClazz) {
        return (genericClazz != null) && (AEntity.class.isAssignableFrom(genericClazz));
    }


    /**
     * Get the annotation Qualifier.
     *
     * @param viewClazz the class of type AView
     *
     * @return the specific Annotation
     */
    public Qualifier getQualifier(final Class<? extends AView> viewClazz) {
        return (viewClazz != null && AView.class.isAssignableFrom(viewClazz)) ? viewClazz.getAnnotation(Qualifier.class) : null;
    }


    /**
     * Get the annotation Document.
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific Annotation
     */
    public Document getDocument(final Class<? extends AEntity> entityClazz) {
        return (entityClazz != null && AEntity.class.isAssignableFrom(entityClazz)) ? entityClazz.getAnnotation(Document.class) : null;
    }


    /**
     * Get the annotation NotNull.
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public NotNull getNotNull(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(NotNull.class) : null;
    }


    /**
     * Get the annotation NotBlank.
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public NotBlank getNotBlank(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(NotBlank.class) : null;
    }


    /**
     * Get the annotation Indexed.
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public Indexed getIndexed(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(Indexed.class) : null;
    }


    /**
     * Get the annotation Size.
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public Size getSize(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(Size.class) : null;
    }


    /**
     * Get the annotation Range.
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public Range getRange(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(Range.class) : null;
    }


    /**
     * Get the annotation DBRef.
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public DBRef getDBRef(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(DBRef.class) : null;
    }


    /**
     * Lista dei fields statici PUBBLICI dichiarati in una classe di tipo AEntity, che usano @DBRef <br>
     * Controlla che il parametro in ingresso non sia nullo <br>
     * Ricorsivo. Comprende la entity e tutte le sue superclassi (fino a ACEntity e AEntity) <br>
     * Esclusi i fields: PROPERTY_SERIAL, PROPERT_NOTE, PROPERTY_CREAZIONE, PROPERTY_MODIFICA <br>
     * Esclusi i fields PRIVATI <br>
     * Fields NON ordinati <br>
     * Class.getDeclaredFields() prende fields pubblici e privati della classe <br>
     * Class.getFields() prende fields pubblici della classe e delle superclassi     * Nomi NON ordinati <br>
     * ATTENZIONE - Comprende ANCHE eventuali fields statici pubblici che NON siano property per il DB <br>
     *
     * @param entityClazz da cui estrarre i fields statici
     *
     * @return lista di static fields della Entity e di tutte le sue superclassi, che usano @DBRef
     */
    public List<Field> getDBRefFields(Class<? extends AEntity> entityClazz) {
        List<Field> listaFields = null;
        List<Field> listaGrezza = null;

        //--Controlla che il parametro in ingresso non sia nullo
        if (entityClazz == null) {
            return null;
        }

        listaGrezza = listaGrezza = reflection.getAllFields(entityClazz);
        if (array.isAllValid(listaGrezza)) {
            listaFields = new ArrayList<>();
            for (Field field : listaGrezza) {
                if (getDBRef(field) != null) {
                    listaFields.add(field);
                }
            }
        }

        return listaFields;
    }


    /**
     * Get the name of the route.
     *
     * @param genericClazz of all types
     *
     * @return the name of the vaadin-view @route
     */
    public String getRouteName(final Class<?> genericClazz) {
        Route annotation = genericClazz != null ? this.getRoute(genericClazz) : null;
        return annotation != null ? annotation.value() : VUOTA;
    }


    /**
     * Get the name of the qualifier.
     *
     * @param viewClazz the class of type AView
     *
     * @return the name of the qualifier of the spring-view
     */
    public String getQualifierName(final Class<? extends AView> viewClazz) {
        Qualifier annotation = (viewClazz != null && AView.class.isAssignableFrom(viewClazz)) ? this.getQualifier(viewClazz) : null;
        return annotation != null ? annotation.value() : VUOTA;
    }


    /**
     * Restituisce il nome della property per navigare verso la View <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l'annotation specifica <br>
     * 4) Cerca la property 'routeFormName' nell'Annotation @AIView della classe AViewList <br>
     *
     * @param viewClazz the class of type AView
     *
     * @return the name of the property
     */
    public String getFormRouteName(final Class<? extends AView> viewClazz) {
        AIView annotation = this.getAIViewView(viewClazz);
        return annotation != null ? annotation.routeFormName() : VUOTA;
    }


    /**
     * Restituisce il nome del menu. <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l' annotation specifica <br>
     * 4) Cerca la property 'menuName' nell' Annotation @AIView della classe AViewList <br>
     * 5) Se non la trova, cerca nella classe la property statica MENU_NAME <br>
     * 6) Se non la trova, di default usa la property 'value' di @Route <br>
     * 7) Se non la trova, di default usa nome della classe <br>
     *
     * @param entityViewClazz the class of type AEntity or AView
     *
     * @return the name of the spring-view
     */
    public String getMenuName(final Class<?> entityViewClazz) {
        String menuName = VUOTA;
        String tagRouteVuotaDefault = "___NAMING_CONVENTION___";
        AIView annotationView = this.getAIView(entityViewClazz);
        Route annotationRoute = null;

        // Cerca in @AIView della classe la property 'menuName'
        if (annotationView != null) {
            menuName = annotationView.menuName();
        }

        // Se non la trova, cerca nella classe la property statica MENU_NAME
        if (text.isEmpty(menuName)) {
            //            menuName = reflection.getMenuName(entityViewClazz);  //@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
        }

        // Se non la trova, di default usa la property 'value' di @Route
        if (text.isEmpty(menuName)) {
            annotationRoute = this.getRoute(entityViewClazz);
        }

        if (annotationRoute != null) {
            menuName = annotationRoute.value();
        }

        if (menuName.equals(tagRouteVuotaDefault)) {
            menuName = VUOTA;
        }

        // Se non la trova, di default usa nome della classe
        if (text.isEmpty(menuName)) {
            menuName = entityViewClazz != null ? entityViewClazz.getSimpleName() : VUOTA;
        }

        return text.isValid(menuName) ? text.primaMaiuscola(menuName) : VUOTA;
    }


    /**
     * Valore della VaadinIcon di una view
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l'annotation specifica <br>
     * 4) Se la classe NON è una AView, prova a controllarla come AEntity <br>
     *
     * @param entityViewClazz the class of type AEntity or AView
     *
     * @return the menu vaadin icon
     */
    public VaadinIcon getMenuVaadinIcon(final Class<?> entityViewClazz) {
        AIView annotation = this.getAIView(entityViewClazz);
        return annotation != null ? annotation.menuIcon() : DEFAULT_ICON;
    }


    /**
     * Valore della Icon di una view
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l'annotation specifica <br>
     *
     * @param entityViewClazz the class of type AEntity or AView
     *
     * @return the menu icon
     */
    public Icon getMenuIcon(final Class<?> entityViewClazz) {
        return getMenuVaadinIcon(entityViewClazz) != null ? getMenuVaadinIcon(entityViewClazz).create() : null;
    }


    /**
     * Flag per indicare un menu del progetto base VaadFlow15 <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l'annotation specifica <br>
     *
     * @param entityViewClazz the class of type AEntity or AView
     *
     * @return true se appartiene al progetto base VaadinFlow15
     */
    public boolean isMenuProgettoBase(final Class<?> entityViewClazz) {
        AIView annotation = this.getAIView(entityViewClazz);
        return annotation != null && annotation.vaadFlow();
    }


    /**
     * Restituisce il nome della property per le ricerche con searchField <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l'annotation specifica <br>
     *
     * @param entityViewClazz the class of type AEntity or AView
     *
     * @return the name of the property
     */
    public String getSearchPropertyName(final Class<?> entityViewClazz) {
        AIView annotation = this.getAIView(entityViewClazz);
        return annotation != null ? annotation.searchProperty() : VUOTA;
    }


    /**
     * Flag per mostrare una Grid vuota all'apertura. Da usare SOLO se ci sono filtri di selezione. <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l'annotation specifica <br>
     *
     * @param entityViewClazz the class of type AEntity or AView
     *
     * @return true per mostrare ka lista vuota all'apertura della Grid
     */
    public boolean isStartListEmpty(final Class<?> entityViewClazz) {
        AIView annotation = this.getAIView(entityViewClazz);
        return annotation != null && annotation.startListEmpty();
    }


    /**
     * Get the sort for the Grid Columns.
     *
     * @param entityViewClazz the class of type AEntity or AView
     *
     * @return sort
     */
    public Sort getSort(final Class<?> entityViewClazz) {
        Sort sort = null;
        String sortDirectionTxt = VUOTA;
        Sort.Direction sortDirection = Sort.Direction.ASC;
        String sortPropertyTxt = VUOTA;
        AIView annotation = this.getAIView(entityViewClazz);

        if (annotation != null) {
            sortPropertyTxt = annotation.sortProperty();
            sortDirectionTxt = annotation.sortDirection();
            if (text.isValid(sortDirectionTxt) && (sortDirectionTxt.equals("DESC") || sortDirectionTxt.equals("desc"))) {
                sortDirection = Sort.Direction.DESC;
            }
        }

        if (text.isValid(sortPropertyTxt)) {
            sort = Sort.by(sortDirection, sortPropertyTxt);
        }

        return sort;
    }


    /**
     * Get the name of the mongo collection.
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Cerca nella classe l'annotation @Document ed estrae il valore (minuscolo) di 'collection' <br>
     * 4) Se non la trova, di default usa il nome (minuscolo) della classe AEntity
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the name of the mongo collection
     */
    public String getCollectionName(final Class<? extends AEntity> entityClazz) {
        String entityName = (entityClazz != null && AEntity.class.isAssignableFrom(entityClazz)) ? entityClazz.getSimpleName().toLowerCase() : VUOTA;
        Document annotation = (entityClazz != null && AEntity.class.isAssignableFrom(entityClazz)) ? this.getDocument(entityClazz) : null;
        return annotation != null ? annotation.collection().toLowerCase() : text.isValid(entityName) ? entityName : VUOTA;
    }


    /**
     * Get the key field for mongoDB.
     * Si può usare una chiave più breve del nome della property <br>
     * Da usare solo nelle Collection con molte entity <br>
     * 1) Controlla che i parametri in ingresso non siano nulli <br>
     * 2) Controlla che il parametro della classe in ingresso sia della classe prevista <br>
     * 3) Controlla che nella classe AEntity in ingresso esista il Field indicato <br>
     * 3) Cerca nel field l'annotation @Document ed estrae il valore (minuscolo) di 'collection' <br>
     *
     * @param entityClazz  the class of type AEntity
     * @param propertyName the property name
     *
     * @return the name for the mongoDb database query
     */
    public String getKeyFieldMongo(final Class<? extends AEntity> entityClazz, String propertyName) {
        String fieldKeyMongo = VUOTA;
        org.springframework.data.mongodb.core.mapping.Field fieldAnnotation = null;
        Field reflectionJavaField = null;


        // Controlla che il parametro in ingresso non sia nullo
        if (entityClazz == null) {
            return VUOTA;
        }

        // Controlla che il parametro in ingresso non sia nullo
        if (text.isEmpty(propertyName)) {
            return VUOTA;
        }

        // Controlla che il parametro in ingresso sia della classe prevista
        if (!AEntity.class.isAssignableFrom(entityClazz)) {
            return VUOTA;
        }


        reflectionJavaField = reflection.getField(entityClazz, propertyName);

        if (reflectionJavaField != null) {
            fieldAnnotation = reflectionJavaField.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
            fieldKeyMongo = (fieldAnnotation != null) ? fieldAnnotation.value() : VUOTA;
        }

        return fieldKeyMongo;
    }


    /**
     * Restituisce il nome della property chiave <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l'annotation specifica <br>
     * 4) Cerca in @AIEntity della classe AEntity la property 'keyPropertyName'
     * 5) Se non la trova, usa '_id' <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the menu icon
     */
    public String getKeyPropertyName(final Class<? extends AEntity> entityClazz) {
        AIEntity annotation = this.getAIEntity(entityClazz);
        return annotation != null ? annotation.keyPropertyName() : VUOTA;
    }


    /**
     * Get the alert on top of list (optional).
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the alert
     */
    public String getHeaderAlert(final Class<? extends AEntity> entityClazz) {
        String headerAlert = VUOTA;
        AIList annotation = this.getAIList(entityClazz);

        if (annotation != null) {
            headerAlert = annotation.headerAlert();
        }

        return headerAlert;
    }


    /**
     * Get the title of list (optional).
     * Default to collection name
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the alert
     */
    public String getTitleList(final Class<? extends AEntity> entityClazz) {
        String title = VUOTA;
        AIList annotation = this.getAIList(entityClazz);

        if (annotation != null) {
            title = annotation.title();
        }

        if (text.isEmpty(title)) {
            title = getCollectionName(entityClazz);
        }

        return title;
    }


    /**
     * Flag per usare il field della superclasse AEntity. <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the status
     */
    public boolean usaRowIndex(final Class<? extends AEntity> entityClazz) {
        AIList annotation = this.getAIList(entityClazz);
        return annotation != null ? annotation.usaRowIndex() : false;
    }


    /**
     * Get the width of the index (optional).
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the width of the index
     */
    public String getIndexWith(final Class<? extends AEntity> entityClazz) {
        String widthTxt = VUOTA;
        double widthDouble = 0;
        String tagIndex = TAG_EM;
        AIList annotation = this.getAIList(entityClazz);

        if (annotation != null) {
            widthDouble = annotation.rowIndexWidthEM();
        }

        if (widthDouble > 0) {
            widthTxt = widthDouble + tagIndex;
        }

        return widthTxt;
    }


    /**
     * Flag per usare il field della superclasse AEntity. <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the status
     */
    public boolean usaCompany(final Class<? extends AEntity> entityClazz) {
        AIEntity annotation = this.getAIEntity(entityClazz);
        return annotation != null ? annotation.usaCompany() : FlowVar.usaCompany;
    }


    /**
     * Flag per usare il field della superclasse AEntity. <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the status
     */
    public boolean usaNote(final Class<? extends AEntity> entityClazz) {
        AIEntity annotation = this.getAIEntity(entityClazz);
        return annotation != null ? annotation.usaNote() : false;
    }


    /**
     * Flag per usare il field della superclasse AEntity. <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the status
     */
    public boolean usaCreazioneModifica(final Class<? extends AEntity> entityClazz) {
        AIEntity annotation = this.getAIEntity(entityClazz);
        return annotation != null ? annotation.usaCreazioneModifica() : false;
    }


    /**
     * Restituisce il nome del record (da usare nel Dialog)
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l' annotation specifica <br>
     * 4) Cerca in @AIEntity della classe AEntity la property 'recordName'
     * 5) Se non lo trova, cerca in @Document della classe AEntity la property collection
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the name of the recordName
     */
    public String getRecordName(final Class<? extends AEntity> entityClazz) {
        String recordName = VUOTA;
        AIEntity annotation = this.getAIEntity(entityClazz);

        // Cerca in @AIEntity della classe AEntity la property 'recordName'
        if (annotation != null) {
            recordName = annotation.recordName();
        }

        // Se non la trova, cerca in @Document della classe AEntity la property collection
        if (text.isEmpty(recordName)) {
            recordName = getCollectionName(entityClazz);
            recordName = text.primaMaiuscola(recordName);
        }

        return recordName;
    }


    /**
     * Lista dei nomi delle properties della Grid, estratti dall'annotation @AIList della Entity. <br>
     * Se la classe AEntity-@AIList prevede una lista specifica, usa quella lista (con o senza ID) <br>
     * Se l'annotation @AIList non esiste od è vuota, <br>
     * restituisce tutte le colonne (properties della classe e superclasse) <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l'annotation specifica <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return lista di nomi di property, oppure null se non esiste l'Annotation specifica @AIList() nella Entity
     */
    public List<String> getListaPropertiesGrid(final Class<? extends AEntity> entityClazz) {
        List listaNomi;
        String stringaMultipla;
        AIList annotationEntity = this.getAIList(entityClazz);

        if (annotationEntity != null) {
            stringaMultipla = annotationEntity.fields();
            if (text.isValid(stringaMultipla)) {
                listaNomi = text.getArray(stringaMultipla);
            } else {
                listaNomi = reflection.getFieldsName(entityClazz);
            }
        } else {
            listaNomi = reflection.getFieldsName(entityClazz);
        }

        return listaNomi;
    }


    /**
     * Lista dei nomi delle properties del Form, estratti dall'annotation @AIForm della Entity. <br>
     * Se la classe AEntity-@AIForm prevede una lista specifica, usa quella lista (con o senza ID) <br>
     * Se l'annotation @AIForm non esiste od è vuota, <br>
     * restituisce tutte i fields (properties della classe e superclasse)  <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * 3) Controlla che esista l'annotation specifica <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return lista di nomi di property, oppure null se non esiste l'Annotation specifica @AIForm() nella Entity
     */
    public List<String> getListaPropertiesForm(final Class<? extends AEntity> entityClazz) {
        List listaNomi = null;
        String stringaMultipla;
        AIForm annotationEntity = this.getAIForm(entityClazz);

        if (annotationEntity != null) {
            stringaMultipla = annotationEntity.fields();
            if (text.isValid(stringaMultipla)) {
                listaNomi = text.getArray(stringaMultipla);
            } else {
                listaNomi = reflection.getFieldsName(entityClazz);
            }
        } else {
            listaNomi = reflection.getFieldsName(entityClazz);
        }

        return listaNomi;
    }


    /**
     * Get the type (field) of the property.
     *
     * @param reflectionJavaField di riferimento
     *
     * @return the type for the specific field
     */
    public AETypeField getFormType(final Field reflectionJavaField) {
        AETypeField type = null;
        AETypeField standard = AETypeField.text;
        AIField annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            type = annotation.type();
        } else {
            type = standard;
        }

        return type;
    }


    /**
     * Get the type (column) of the property.
     *
     * @param reflectionJavaField di riferimento
     *
     * @return the type for the specific column
     */
    public AETypeField getColumnType(final Field reflectionJavaField) {
        AETypeField type = null;
        AETypeField standard = AETypeField.text;
        AIColumn annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIColumn(reflectionJavaField);
        if (annotation != null) {
            type = annotation.type();
            if (type != null && type == AETypeField.ugualeAlForm) {
                type = getFormType(reflectionJavaField);
            }
        } else {
            type = getFormType(reflectionJavaField);
        }

        return type;
    }


    /**
     * Get the icon of the property.
     * Default a VaadinIcon.YOUTUBE che sicuramente non voglio usare
     * e posso quindi escluderlo
     *
     * @param reflectionJavaField di riferimento
     *
     * @return the icon of the field
     */
    public VaadinIcon getHeaderIcon(final Field reflectionJavaField) {
        VaadinIcon icon = null;
        AIColumn annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIColumn(reflectionJavaField);
        if (annotation != null) {
            icon = annotation.headerIcon();
            icon = (icon == VaadinIcon.YOUTUBE) ? null : icon;
        } else {
            icon = null;
        }

        return icon;
    }


    /**
     * Get the color of the property.
     *
     * @param reflectionJavaField di riferimento
     *
     * @return the color of the icon
     */
    public String getHeaderIconColor(final Field reflectionJavaField) {
        String color = VUOTA;
        VaadinIcon icon = null;
        AIColumn annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIColumn(reflectionJavaField);
        if (annotation != null) {
            color = annotation.headerIconColor();
        }

        return color;
    }


    /**
     * Get the name (columnService) of the property.
     * Se manca, usa il nome della property
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the name (columnService) of the field
     */
    public String getColumnHeader(final Field reflectionJavaField) {
        String header = VUOTA;
        AIColumn annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIColumn(reflectionJavaField);
        if (annotation != null) {
            header = annotation.header();
        } else {
            header = reflectionJavaField.getName();
        }

        if (text.isEmpty(header)) {
            header = reflectionJavaField.getName();
        }

        return header;
    }


    /**
     * Get the width of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the width of the field
     */
    public String getColumnWith(final Field reflectionJavaField) {
        String widthTxt = VUOTA;
        double widthDouble = 0;
        AIColumn annotation = null;
        AETypeField type = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIColumn(reflectionJavaField);
        if (annotation != null) {
            widthDouble = annotation.widthEM();
        }

        if (widthDouble > 0) {
            widthTxt = widthDouble + TAG_EM;
        } else {
            type = getColumnType(reflectionJavaField);
            if (type != null) {
                widthTxt = type.getWidthColumn() + TAG_EM;
            }
        }

        return widthTxt;
    }


    /**
     * Get the status flexibility of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    public boolean isFlexGrow(final Field reflectionJavaField) {
        boolean status = false;
        AIColumn annotation = null;

        if (reflectionJavaField == null) {
            return false;
        }

        annotation = this.getAIColumn(reflectionJavaField);
        if (annotation != null) {
            status = annotation.flexGrow();
        }

        return status;
    }


    //    /**
    //     * Nomi delle properties del, estratti dalle @Annotation della Entity
    //     * Se la classe AEntity->@AIForm prevede una lista specifica, usa quella lista (con o senza ID)
    //     * Se l'annotation @AIForm non esiste od è vuota,
    //     * restituisce tutti i campi (properties della classe e superclasse)
    //     * Sovrascrivibile
    //     *
    //     * @return lista di nomi di property, oppure null se non esiste l'Annotation specifica @AIForm() nella Entity
    //     */
    //    public ArrayList<String> getFormPropertiesName(final Class<? extends AEntity> entityClazz) {
    //        ArrayList<String> lista = null;
    //        String[] properties = null;
    //        AIForm annotation = this.getAIForm(entityClazz);
    //
    //        if (annotation != null) {
    //            properties = annotation.fields();
    //        }// end of if cycle
    //
    //        if (array.isValid(properties)) {
    //            lista = new ArrayList<String>(Arrays.asList(properties));
    //        }// end of if cycle
    //
    //        if (array.isEmpty(lista)) {
    //            lista = reflection.getAllFieldsNameNoCrono(entityClazz);
    //        }// end of if cycle
    //
    //        return lista;
    //    }// end of method


    //    /**
    //     * Nomi dei fields da considerare per estrarre i Java Reflected Field dalle @Annotation della Entity
    //     * Se la classe AEntity->@AIForm prevede una lista specifica, usa quella lista (con o senza ID)
    //     * Sovrascrivibile
    //     *
    //     * @return nomi dei fields, oppure null se non esiste l'Annotation specifica @AIForm() nella Entity
    //     */
    //    @SuppressWarnings("all")
    //    public ArrayList<String> getFormFieldsName(final Class<? extends AEntity> entityClazz) {
    //        ArrayList<String> lista = null;
    //        String[] fields = null;
    //        AIForm annotation = this.getAIForm(entityClazz);
    //
    //        if (annotation != null) {
    //            fields = annotation.fields();
    //        }// end of if cycle
    //
    //        if (array.isValid(fields)) {
    //            lista = new ArrayList(Arrays.asList(fields));
    //        }// end of if cycle
    //
    //        return lista;
    //    }// end of method


    //    /**
    //     * Get the status 'nonUsata, facoltativa, obbligatoria' of the class.
    //     *
    //     * @param clazz the entity class
    //     */
    //    @SuppressWarnings("all")
    //    public EACompanyRequired getCompanyRequired(final Class<? extends AEntity> entityClazz) {
    //        EACompanyRequired companyRequired = EACompanyRequired.nonUsata;
    //        AIEntity annotation = getAIEntity(entityClazz);
    //
    //        if (annotation != null) {
    //            companyRequired = annotation.company();
    //        }// end of if cycle
    //
    //        return companyRequired;
    //    }// end of method


    //    /**
    //     * Get the roleTypeVisibility of the View class.
    //     * La Annotation @AIView ha un suo valore di default per la property @AIView.roleTypeVisibility()
    //     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
    //     *
    //     * @param clazz the view class
    //     *
    //     * @return the roleTypeVisibility of the class
    //     */
    //    @SuppressWarnings("all")
    //    public EARoleType getViewRoleType(final Class<? extends IAView> clazz) {
    //        EARoleType roleTypeVisibility = null;
    //        AIView annotation = this.getAIView(clazz);
    //
    //        if (annotation != null) {
    //            roleTypeVisibility = annotation.roleTypeVisibility();
    //        }// end of if cycle
    //
    //        return roleTypeVisibility != null ? roleTypeVisibility : EARoleType.user;
    //    }// end of method


    //    /**
    //     * Get the accessibility status of the class for the developer login.
    //     *
    //     * @param clazz the view class
    //     *
    //     * @return true if the class is visible
    //     */
    //    @SuppressWarnings("all")
    //    public boolean getViewAccessibilityDev(final Class<? extends IAView> clazz) {
    //        EARoleType roleTypeVisibility = getViewRoleType(clazz);
    //        return (roleTypeVisibility != null && roleTypeVisibility == EARoleType.developer);
    //    }// end of method


    //    /**
    //     * Get the visibility of the columnService.
    //     * Di default true
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the visibility of the columnService
    //     */
    //    @Deprecated
    //    public boolean isColumnVisibile(final Field reflectionJavaField) {
    //        boolean visibile = false;
    //        EARoleType roleTypeVisibility = EARoleType.nobody;
    //        AIColumn annotation = this.getAIColumn(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            roleTypeVisibility = annotation.roleTypeVisibility();
    //        }// end of if cycle
    //
    //        switch (roleTypeVisibility) {
    //            case nobody:
    //                visibile = false;
    //                break;
    //            case developer:
    //                //@todo RIMETTERE
    //
    ////                if (LibSession.isDeveloper()) {
    //                visibile = true;
    ////                }// end of if cycle
    //                break;
    //            case admin:
    //                //@todo RIMETTERE
    //
    //                //                if (LibSession.isAdmin()) {
    //                visibile = true;
    ////                }// end of if cycle
    //                break;
    //            case user:
    //                visibile = true;
    //                break;
    //            case guest:
    //                visibile = true;
    //                break;
    //            default:
    //                visibile = true;
    //                break;
    //        } // end of switch statement
    //
    //        return visibile;
    //    }// end of method


    //    /**
    //     * Get the specific annotation of the field.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the Annotation for the specific field
    //     */
    //    public int getSizeMin(final Field reflectionJavaField) {
    //        int min = 0;
    //        Size annotation = this.getSize(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            min = annotation.min();
    //        }// end of if cycle
    //
    //        return min;
    //    }// end of method

    //    /**
    //     * Get the status flexibility of the property.
    //     *
    //     * @param entityClazz the entity class
    //     * @param fieldName   the property name
    //     *
    //     * @return status of field
    //     */
    //    public boolean isFlexGrow(Class<? extends AEntity> entityClazz, String fieldName) {
    //        boolean status = false;
    //        Field field = reflection.getField(entityClazz, fieldName);
    //
    //        if (field != null) {
    //            status = isFlexGrow(field);
    //        }// end of if cycle
    //
    //        return status;
    //    }// end of method


    //    /**
    //     * Get the status flexibility of the property.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return status of field
    //     */
    //    public boolean isFlexGrow(Field reflectionJavaField) {
    //        boolean status = false;
    //        AIColumn annotation = this.getAIColumn(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            status = annotation.flexGrow();
    //        }// end of if cycle
    //
    //        return status;
    //    }// end of method


    //    /**
    //     * Get the status sortable of the property.
    //     *
    //     * @param entityClazz the entity class
    //     * @param fieldName   the property name
    //     *
    //     * @return status of field
    //     */
    //    public boolean isSortable(Class<? extends AEntity> entityClazz, String fieldName) {
    //        boolean status = false;
    //        Field field = reflection.getField(entityClazz, fieldName);
    //
    //        if (field != null) {
    //            status = isSortable(field);
    //        }// end of if cycle
    //
    //        return status;
    //    }// end of method


    //    /**
    //     * Get the status flexibility of the property.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return status of field
    //     */
    //    public boolean isSortable(Field reflectionJavaField) {
    //        boolean status = false;
    //        AIColumn annotation = this.getAIColumn(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            status = annotation.sortable();
    //        }// end of if cycle
    //
    //        return status;
    //    }// end of method


    //    /**
    //     * Get the color of the property.
    //     *
    //     * @param entityClazz the entity class
    //     * @param fieldName   the property name
    //     *
    //     * @return the color (columnService) of the field
    //     */
    //    public String getColumnColor(Class<? extends AEntity> entityClazz, String fieldName) {
    //        String color = "";
    //        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);
    //
    //        if (annotation != null) {
    //            color = annotation.color();
    //        }// end of if cycle
    //
    //        return color;
    //    }// end of method


    //    /**
    //     * Get the color of the property.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the color of the field
    //     */
    //    public String getFieldColor(final Field reflectionJavaField) {
    //        String color = "";
    //        AIField annotation = this.getAIField(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            color = annotation.color();
    //        }// end of if cycle
    //
    //        return color;
    //    }// end of method


    //    /**
    //     * Get the items (String) of the enum.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the items
    //     */
    //    public List<String> getEnumItems(final Field reflectionJavaField) {
    //        List<String> items = null;
    //        String value = "";
    //        AIField annotation = this.getAIField(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            value = annotation.items();
    //        }// end of if cycle
    //
    //        if (text.isValid(value)) {
    //            items = array.getList(value);
    //        }// end of if cycle
    //
    //        return items;
    //    }// end of method


    //    /**
    //     * Get the clazz of the property.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the type for the specific columnService
    //     */
    //    public Class getClazz(final Field reflectionJavaField) {
    //        Class clazz = null;
    //        AIField annotation = this.getAIField(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            clazz = annotation.serviceClazz();
    //        }// end of if cycle
    //
    //        return clazz;
    //    }// end of method


    /**
     * Get the name (field) of the property <br>
     * Se manca, usa il nome della property <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the name (rows) of the field
     */
    public String getFormFieldName(final Field reflectionJavaField) {
        String name = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            name = annotation.caption();
        }

        if (text.isEmpty(name)) {
            name = reflectionJavaField.getName();
        }

        return name;
    }


    /**
     * Get the name (field) of the property.
     * Se manca, usa il nome della property
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the capitalized name (rows) of the field
     */
    public String getFormFieldNameCapital(final Field reflectionJavaField) {
        return text.primaMaiuscola(getFormFieldName(reflectionJavaField));
    }


    /**
     * Get the width of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the width of the field
     */
    public String getFormWith(final Field reflectionJavaField) {
        String widthTxt = VUOTA;
        double widthDouble = 0;
        AIField annotation = null;
        AETypeField type = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            widthDouble = annotation.widthEM();
        }

        if (widthDouble > 0) {
            widthTxt = widthDouble + TAG_EM;
        } else {
            type = getColumnType(reflectionJavaField);
            if (type != null) {
                widthTxt = type.getWidthField() > 0 ? type.getWidthField() + TAG_EM : VUOTA;
            }
        }

        return widthTxt;
    }


    /**
     * Get the width of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the width of the field
     */
    public String getFormHeight(final Field reflectionJavaField) {
        String heightTxt = VUOTA;
        double heightDouble = 0;
        AIField annotation = null;
        AETypeField type = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            heightDouble = annotation.heightEM();
        }

        if (heightDouble > 0) {
            heightTxt = heightDouble + TAG_EM;
        } else {
            type = getColumnType(reflectionJavaField);
            if (type != null) {
                heightTxt = type.getWidthField() > 0 ? type.getWidthField() + TAG_EM : VUOTA;
            }
        }

        return heightTxt;
    }


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class
     */
    @SuppressWarnings("all")
    public Class getComboClass(Field reflectionJavaField) {
        Class comboClazz = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            comboClazz = annotation.comboClazz();
        }

        return comboClazz == Object.class ? null : comboClazz;
    }


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class
     */
    @SuppressWarnings("all")
    public Class getEnumClass(Field reflectionJavaField) {
        Class enumClazz = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            enumClazz = annotation.enumClazz();
        }

        return enumClazz == Object.class ? null : enumClazz;
    }


    /**
     * Get the items (String) of the enum.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the items
     */
    public List<String> getEnumItems(final Field reflectionJavaField) {
        List<String> items = null;
        String value = VUOTA;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            value = annotation.items();
        }

        if (text.isValid(value)) {
            items = array.fromStringa(value);
        }

        return items;
    }


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class
     */
    @SuppressWarnings("all")
    public Class getLogicClass(Field reflectionJavaField) {
        Class logicClazz = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            logicClazz = annotation.logicClazz();
        }

        return logicClazz == Object.class ? null : logicClazz;
    }


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class
     */
    @SuppressWarnings("all")
    public Class getLinkClass(Field reflectionJavaField) {
        Class linkClazz = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            linkClazz = annotation.linkClazz();
        }

        return linkClazz == Object.class ? null : linkClazz;
    }


    /**
     * Get the properties (String) of the linked Grid.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the items
     */
    public List<String> getLinkProperties(final Field reflectionJavaField) {
        List<String> properties = null;
        String value = VUOTA;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            value = annotation.properties();
        }

        if (text.isValid(value)) {
            properties = array.fromStringa(value);
        }

        return properties;
    }


    /**
     * Get the property for linked classes.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the width of the field
     */
    public String getLinkProperty(final Field reflectionJavaField) {
        String linkProperty = VUOTA;
        AIField annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            linkProperty = annotation.linkProperty();
        }

        return linkProperty;
    }


    /**
     * Get the status of specific method for items.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    public boolean usaComboMethod(final Field reflectionJavaField) {
        boolean usaComboMethod = false;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            usaComboMethod = annotation.usaComboMethod();
        }

        return usaComboMethod;
    }


    /**
     * Get the method name for reflection.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the method name
     */
    public String getMethodName(final Field reflectionJavaField) {
        String methodName = "";
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            methodName = annotation.methodName();
        }

        return methodName;
    }


    /**
     * Get the alert message from @NotBlank
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the alert message
     */
    public String getMessageBlank(Field reflectionJavaField) {
        String message = VUOTA;
        NotBlank annotation = null;
        AETypeField type;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getNotBlank(reflectionJavaField);
        if (annotation != null) {
            message = annotation.message();
        }

        if (message.equals("{javax.validation.constraints.NotBlank.message}") || text.isEmpty(message)) {
            message = VUOTA;
            type = getFormType(reflectionJavaField);
            if (type == AETypeField.text || type == AETypeField.password || type == AETypeField.phone) {
                message = text.primaMaiuscola(reflectionJavaField.getName()) + TESTO_NULL;
            }
            if (type == AETypeField.integer) {
                message = text.primaMaiuscola(reflectionJavaField.getName()) + INT_NULL;
            }
        }

        return message;
    }


    /**
     * Get the alert message from @NotNull
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the alert message
     */
    public String getMessageNull(Field reflectionJavaField) {
        String message = VUOTA;
        NotNull annotation = null;
        AETypeField type;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getNotNull(reflectionJavaField);
        if (annotation != null) {
            message = annotation.message();
        }

        if (message.equals("{javax.validation.constraints.NotNull.message}")) {
            type = getFormType(reflectionJavaField);
            if (type == AETypeField.integer) {
                message = text.primaMaiuscola(reflectionJavaField.getName()) + INT_NULL;
            } else {
                message = text.primaMaiuscola(reflectionJavaField.getName()) + OBJECT_NULL;
            }
        }

        return message;
    }


    /**
     * Get the alert message from @Size
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the alert message
     */
    public String getMessageSize(Field reflectionJavaField) {
        String message = VUOTA;
        Size annotation = null;
        int min = 0;
        int max = MAX;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getSize(reflectionJavaField);
        if (annotation == null) {
            message = this.getMessage(reflectionJavaField);
        } else {
            message = annotation.message();
            if (message.equals("{javax.validation.constraints.Size.message}")) {
                min = annotation.min();
                max = annotation.max();
                if (min == max) {
                    message = "Esattamente " + min + " caratteri";
                    return message;
                }
                if (min > 0 && max < MAX) {
                    message = "Da " + min + " a " + max + " caratteri";
                    return message;
                }
                if (min > 0 && max == MAX) {
                    message = "Almeno " + min + " caratteri";
                    return message;
                }
                if (min == 0 && max < MAX) {
                    message = "Massimo " + max + " caratteri";
                    return message;
                }
            }
        }
        return env.getProperty("javax.validation.constraints.Size.message");
    }


    /**
     * Get the alert message from @NotNull or from @Size
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the alert message
     */
    public String getMessage(Field reflectionJavaField) {
        return getMessageNull(reflectionJavaField);
    }


    /**
     * Get the placeholder from @AIField
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the placeholder message
     */
    public String getPlaceholder(Field reflectionJavaField) {
        String placeholder = VUOTA;
        AIField annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            placeholder = annotation.placeholder();
        }

        return placeholder;
    }


    /**
     * Get the specific annotation of the field. <br>
     * Size si usa SOLO per le stringhe <br>
     * Per i numeri si usa @Range <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the value
     */
    public int getStringMin(final Field reflectionJavaField) {
        int min = 0;
        Size annotation = null;

        if (reflectionJavaField == null) {
            return 0;
        }

        annotation = this.getSize(reflectionJavaField);
        if (annotation != null) {
            min = annotation.min();
        }

        return min;
    }


    /**
     * Get the specific annotation of the field.
     * Size si usa SOLO per le stringhe <br>
     * Per i numeri si usa @Range <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the value
     */
    public int getStringMax(final Field reflectionJavaField) {
        int max = 0;
        Size annotation = null;

        if (reflectionJavaField == null) {
            return 0;
        }

        annotation = this.getSize(reflectionJavaField);
        if (annotation != null) {
            max = annotation.max();
        }

        return max;
    }


    /**
     * Get the specific annotation of the field. <br>
     * Range si usa SOLO per i numeri <br>
     * Per le stringhe si usa @Size <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the value
     */
    public int getNumberMin(final Field reflectionJavaField) {
        long min = 0;
        Range annotation = null;

        if (reflectionJavaField == null) {
            return 0;
        }

        annotation = this.getRange(reflectionJavaField);
        if (annotation != null) {
            min = annotation.min();
        }

        return min > 0 ? Math.toIntExact(min) : 0;
    }


    /**
     * Get the specific annotation of the field. <br>
     * Range si usa SOLO per i numeri <br>
     * Per le stringhe si usa @Size <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the value
     */
    public int getNumberMax(final Field reflectionJavaField) {
        long max = 0;
        Range annotation = null;

        if (reflectionJavaField == null) {
            return 0;
        }

        annotation = this.getRange(reflectionJavaField);
        if (annotation != null) {
            max = annotation.max();
        }

        return max > 0 ? Math.toIntExact(max) : 0;
    }


    /**
     * Get the specific annotation of the field. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     */
    public AETypeNum getTypeNumber(final Field reflectionJavaField) {
        AETypeNum type = AETypeNum.positiviOnly;
        AIField annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        if (getFormType(reflectionJavaField) != AETypeField.integer) {
            return null;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            type = annotation.typeNum();
        }

        return type;
    }


    /**
     * Get the specific annotation of the field. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     */
    public AETypeBoolCol getTypeBoolCol(final Field reflectionJavaField) {
        AETypeBoolCol type = AETypeBoolCol.boolGrezzo;
        AIColumn annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIColumn(reflectionJavaField);
        if (annotation != null) {
            type = annotation.typeBool();
        }

        return type;
    }


    /**
     * Get the specific annotation of the field. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     */
    public AETypeBoolField getTypeBoolField(final Field reflectionJavaField) {
        AETypeBoolField type = AETypeBoolField.checkBox;
        AIField annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            type = annotation.typeBool();
        }

        return type;
    }


    /**
     * Get the 'enum' (two strings) of the property <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the Strings
     */
    public String getBoolEnumField(final Field reflectionJavaField) {
        String stringa = VUOTA;
        AIField annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            stringa = annotation.boolEnum();
        }

        return stringa;
    }


    /**
     * Get the 'enum' (two strings) of the property <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the Strings
     */
    public List<String> getBoolEnumCol(final Field reflectionJavaField) {
        List<String> lista = null;
        String stringa = VUOTA;
        AIColumn annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIColumn(reflectionJavaField);
        if (annotation != null) {
            stringa = annotation.boolEnum();
        }

        if (text.isValid(stringa) && stringa.contains(VIRGOLA)) {
            lista = text.getArray(stringa);
        }

        return (lista != null && lista.size() == 2) ? lista : null;
    }


    /**
     * Get the specific annotation of the field. <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     */
    public AETypeData getTypeDataCol(final Field reflectionJavaField) {
        AETypeData type = AETypeData.standard;
        AIColumn annotation = null;

        if (reflectionJavaField == null) {
            return null;
        }

        annotation = this.getAIColumn(reflectionJavaField);
        if (annotation != null) {
            type = annotation.typeData();
        }

        return type;
    }


    /**
     * Get the status required of the property.
     * Per i field di testo, controlla sia l' annotation @NotBlank() sia @AIField(required = true) <br>
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the value
     */
    public boolean isRequired(Field reflectionJavaField) {
        boolean status = false;
        NotBlank annotationNotBlank = null;
        NotNull annotationNotNull = null;
        AIField annotationAIField = null;

        if (reflectionJavaField == null) {
            return false;
        }

        annotationNotBlank = getNotBlank(reflectionJavaField);
        if (annotationNotBlank != null) {
            return true;
        }

        annotationNotNull = getNotNull(reflectionJavaField);
        if (annotationNotNull != null) {
            return true;
        }

        annotationAIField = this.getAIField(reflectionJavaField);
        if (annotationAIField != null) {
            status = annotationAIField.required();
        }

        return status;
    }


    /**
     * Get the status required of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the value
     */
    public boolean isAllowCustomValue(Field reflectionJavaField) {
        boolean status = false;
        AIField annotationAIField = null;

        if (reflectionJavaField == null) {
            return false;
        }

        annotationAIField = this.getAIField(reflectionJavaField);
        if (annotationAIField != null) {
            status = annotationAIField.allowCustomValue();
        }

        return status;
    }


    /**
     * Get the status unique of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the value
     */
    public boolean isUnique(Field reflectionJavaField) {
        boolean status = false;
        Indexed annotation = null;

        if (reflectionJavaField == null) {
            return false;
        }

        annotation = this.getIndexed(reflectionJavaField);
        if (annotation != null) {
            status = annotation.unique();
        }

        return status;
    }


    /**
     * Get the status focus of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    public boolean focus(Field reflectionJavaField) {
        boolean status = false;
        AIField annotation = null;

        if (reflectionJavaField == null) {
            return false;
        }

        annotation = this.getAIField(reflectionJavaField);
        if (annotation != null) {
            status = annotation.focus();
        }

        return status;
    }

    //    /**
    //     * Get the status required of the property.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return status of field
    //     */
    //    public boolean isRequired(Field reflectionJavaField) {
    //        boolean status = false;
    //        AIField annotation = this.getAIField(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            status = annotation.required();
    //        }// end of if cycle
    //
    //        return status;
    //    }// end of method


    //    /**
    //     * Get the status required of the property.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return status of field
    //     */
    //    public boolean isNotNull(Field reflectionJavaField) {
    //        return getNotNull(reflectionJavaField) != null;
    //    }// end of method


    //    /**
    //     * Bottoni visibili nella toolbar
    //     *
    //     * @param clazz the entity class
    //     *
    //     * @return lista di bottoni visibili nella toolbar
    //     */
    //    @SuppressWarnings("all")
    //    public EAFormButton getFormBottonDev(final Class<? extends AEntity> clazz) {
    //        EAFormButton listaNomiBottoni = EAFormButton.standard;
    //        AIForm annotation = this.getAIForm(clazz);
    //
    //        if (annotation != null) {
    //            listaNomiBottoni = annotation.buttonsDev();
    //        }// end of if cycle
    //
    //        return listaNomiBottoni;
    //    }// end of method


    //    /**
    //     * Bottoni visibili nella toolbar
    //     *
    //     * @param clazz the entity class
    //     *
    //     * @return lista di bottoni visibili nella toolbar
    //     */
    //    @SuppressWarnings("all")
    //    public EAFormButton getFormBottonAdmin(final Class<? extends AEntity> clazz) {
    //        EAFormButton listaNomiBottoni = EAFormButton.standard;
    //        AIForm annotation = this.getAIForm(clazz);
    //
    //        if (annotation != null) {
    //            listaNomiBottoni = annotation.buttonsAdmin();
    //        }// end of if cycle
    //
    //        return listaNomiBottoni;
    //    }// end of method


    //    /**
    //     * Bottoni visibili nella toolbar
    //     *
    //     * @param clazz the entity class
    //     *
    //     * @return lista di bottoni visibili nella toolbar
    //     */
    //    @SuppressWarnings("all")
    //    public EAFormButton getFormBottonUser(final Class<? extends AEntity> clazz) {
    //        EAFormButton listaNomiBottoni = EAFormButton.standard;
    //        AIForm annotation = this.getAIForm(clazz);
    //
    //        if (annotation != null) {
    //            listaNomiBottoni = annotation.buttonsUser();
    //        }// end of if cycle
    //
    //        return listaNomiBottoni;
    //    }// end of method


    //    /**
    //     * Get the icon of the property.
    //     * Default a VaadinIcon.YOUTUBE che sicuramente non voglio usare
    //     * e posso quindi escluderlo
    //     *
    //     * @param entityClazz the entity class
    //     * @param fieldName   the property name
    //     *
    //     * @return the icon of the field
    //     */
    //    public VaadinIcon getHeaderIcon(Class<? extends AEntity> entityClazz, String fieldName) {
    //        VaadinIcon icon = null;
    //        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);
    //
    //        if (annotation != null) {
    //            icon = annotation.headerIcon();
    //        }// end of if cycle
    //
    //        if (icon == VaadinIcon.YOUTUBE) {
    //            icon = null;
    //        }// end of if cycle
    //
    //        return icon;
    //    }// end of method


    //    /**
    //     * Get the size of the icon of the property.
    //     *
    //     * @param entityClazz the entity class
    //     * @param fieldName   the property name
    //     *
    //     * @return the size of the icon
    //     */
    //    public String getHeaderIconSizePX(Class<? extends AEntity> entityClazz, String fieldName) {
    //        int widthInt = 0;
    //        int standard = 20;
    //        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);
    //
    //        if (annotation != null) {
    //            widthInt = annotation.headerIconSizePX();
    //        }// end of if cycle
    //
    //        if (widthInt == 0) {
    //            widthInt = standard;
    //        }// end of if cycle
    //
    //        return widthInt + TAG_PX;
    //    }// end of method


    //    /**
    //     * Get the color of the property.
    //     *
    //     * @param entityClazz the entity class
    //     * @param fieldName   the property name
    //     *
    //     * @return the color of the icon
    //     */
    //    public String getHeaderIconColor(Class<? extends AEntity> entityClazz, String fieldName) {
    //        String color = "";
    //        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);
    //
    //        if (annotation != null) {
    //            color = annotation.headerIconColor();
    //        }// end of if cycle
    //
    //        return color;
    //    }// end of method


    //    /**
    //     * Get the color of the property.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the color of the field
    //     */
    //    public String xxxx(final Field reflectionJavaField) {
    //        String color = "";
    //        AIField annotation = this.getAIField(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            color = annotation.color();
    //        }// end of if cycle
    //
    //        return color;
    //    }// end of method


    //    /**
    //     * Get the method name for reflection.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the method name
    //     */
    //    public String getMethodNameField(final Field reflectionJavaField) {
    //        String methodName = "";
    //        AIField annotation = this.getAIField(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            methodName = annotation.methodName();
    //        }// end of if cycle
    //
    //        return methodName;
    //    }// end of method


    //    /**
    //     * Get the method name for reflection.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the method name
    //     */
    //    public String getPropertyLinkata(final Field reflectionJavaField) {
    //        String methodName = "";
    //        AIField annotation = this.getAIField(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            methodName = annotation.propertyLinkata();
    //        }// end of if cycle
    //
    //        return methodName;
    //    }// end of method


}// end of class
