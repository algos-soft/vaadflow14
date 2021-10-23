package it.algos.vaadflow14.backend.wrapper;

import com.vaadin.flow.data.provider.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.io.*;
import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 12-ott-2021
 * Time: 08:36
 */
public class WrapFiltri {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     */
    @Autowired
    public static TextService text;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     */
    @Autowired
    public static AnnotationService annotation;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     */
    @Autowired
    public static ReflectionService reflection;

    /**
     * Filtri per dataProvider <br>
     */
    private Map<String, AFiltro> mappaFiltri;

    // Esistono DUE tipi di Sort: quello di Spring e quello di Vaadin
    private Sort sortSpring;

    // Ordine delle colonne
    private List<QuerySortOrder> sortVaadin;

    public WrapFiltri() {
        this.mappaFiltri = new HashMap<>();
    }

    public static WrapFiltri crea(final Class<? extends AEntity> entityClazz, AETypeFilter filter, String propertyField, final Serializable propertyValue) throws AlgosException {
        WrapFiltri wrap = new WrapFiltri();
        String message;
        String keyField;

        if (entityClazz == null) {
            throw AlgosException.stack("Manca la entityClazz", WrapFiltri.class, "crea");
        }

        if (!AEntity.class.isAssignableFrom(entityClazz)) {
            throw AlgosException.stack(String.format("La entityClazz %s non è una classe valida", entityClazz.getSimpleName()), WrapFiltri.class, "crea");
        }

        if (filter == null) {
            throw AlgosException.stack("Manca la tipologia del filtro", WrapFiltri.class, "crea");
        }

        if (text.isEmpty(propertyField)) {
            throw AlgosException.stack("Manca la propertyName del filtro", WrapFiltri.class, "crea");
        }

        propertyField = text.levaCoda(propertyField, FIELD_NAME_ID_LINK);
        keyField = propertyField;

        if (!reflection.isEsisteFieldOnSuperClass(entityClazz, propertyField)) {
            message = String.format("La entityClazz %s esiste ma non esiste la property %s", entityClazz.getSimpleName(), propertyField);
            throw AlgosException.stack(message, WrapFiltri.class, "crea");
        }

        if (propertyValue == null) {
            throw AlgosException.stack("Manca la propertyValue del filtro", WrapFiltri.class, "crea");
        }

        if (annotation.isDBRef(entityClazz, propertyField)) {
            propertyField += FIELD_NAME_ID_LINK;
            filter = AETypeFilter.link;
        }

        switch (filter) {
            case uguale:
                if (propertyValue instanceof String) {
                    wrap.mappaFiltri.put(keyField, AFiltro.ugualeStr(propertyField, (String) propertyValue));
                }
                else {
                    wrap.mappaFiltri.put(keyField, AFiltro.ugualeObj(propertyField, propertyValue));
                }
                break;
            case inizia:
                wrap.mappaFiltri.put(keyField, AFiltro.start(propertyField, (String) propertyValue));
                break;
            case contiene:
                wrap.mappaFiltri.put(keyField, AFiltro.contains(propertyField, (String) propertyValue));
                break;
            case link:
                wrap.mappaFiltri.put(keyField, AFiltro.ugualeObj(propertyField, propertyValue));
                break;
            default:
                throw AlgosException.stack(String.format("Manca il filtro %s nello switch", filter), WrapFiltri.class, "crea");
        }

        wrap.mappaFiltri.get(keyField).setType(filter);
        return wrap;
    }

    public static WrapFiltri start(final Class<? extends AEntity> entityClazz, final String propertyName, final String propertyValue) throws AlgosException {
        return WrapFiltri.crea(entityClazz, AETypeFilter.inizia, propertyName, propertyValue);
    }

    public static WrapFiltri contains(final Class<? extends AEntity> entityClazz, final String propertyName, final String propertyValue) throws AlgosException {
        return WrapFiltri.crea(entityClazz, AETypeFilter.contiene, propertyName, propertyValue);
    }

    public Map<String, AFiltro> getMappaFiltri() {
        return mappaFiltri;
    }

}
