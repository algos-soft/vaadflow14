package it.algos.vaadflow14.backend.wrapper;

import com.vaadin.flow.data.provider.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

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

    public static WrapFiltri crea(final Class<? extends AEntity> entityClazz, final AETypeFilter filter, final String propertyName, final String propertyValue) throws AlgosException {
        WrapFiltri wrap = new WrapFiltri();
        String message;

        if (entityClazz == null) {
            throw AlgosException.stack("Manca la entityClazz", WrapFiltri.class, "crea");
        }

        if (filter == null) {
            throw AlgosException.stack("Manca la tipologia del filtro", WrapFiltri.class, "crea");
        }

        if (text.isEmpty(propertyName)) {
            throw AlgosException.stack("Manca la propertyName del filtro", WrapFiltri.class, "crea");
        }

        if (!reflection.isEsisteFieldOnSuperClass(entityClazz, propertyName)) {
            message = String.format("La entityClazz %s esiste ma non esiste la property %s", entityClazz.getSimpleName(), propertyName);
            throw AlgosException.stack(message, WrapFiltri.class, "crea");
        }

        if (text.isEmpty(propertyValue)) {
            throw AlgosException.stack("Manca la propertyValue del filtro", WrapFiltri.class, "crea");
        }

        switch (filter) {
            case uguale:
                wrap.mappaFiltri.put(propertyName, AFiltro.ugualeStr(propertyName, propertyValue));
                break;
            case inizia:
                wrap.mappaFiltri.put(propertyName, AFiltro.start(propertyName, propertyValue));
                break;
            case contiene:
                wrap.mappaFiltri.put(propertyName, AFiltro.contains(propertyName, propertyValue));
                break;
            default:
                throw AlgosException.stack(String.format("Manca il filtro %s nello switch", filter), WrapFiltri.class, "crea");
        }

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
