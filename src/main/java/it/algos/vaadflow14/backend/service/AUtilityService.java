package it.algos.vaadflow14.backend.service;

import com.vaadin.flow.data.provider.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 30-apr-2021
 * Time: 08:27
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AUtilityService.class); <br>
 * 3) @Autowired public AUtilityService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AUtilityService extends AAbstractService {


    /**
     * Estrae il contenuto testuale dal Sort (springframework) <br>
     *
     * @param sort di springframework
     *
     * @return array di parti
     */
    private String[] getParti(final Sort sort) {
        String[] parti = null;
        String sortTxt = VUOTA;
        String[] partiTmp = null;

        if (sort != null) {
            sortTxt = sort.toString();
        }

        if (text.isValid(sortTxt) && sortTxt.contains(DUE_PUNTI)) {
            partiTmp = sortTxt.split(DUE_PUNTI);
        }

        if (partiTmp != null && partiTmp.length == 2) {
            parti = partiTmp;
        }

        return parti;
    }


    /**
     * Estrae il field dal Sort (springframework) <br>
     *
     * @param sort di springframework
     *
     * @return fieldName
     */
    public String getSortField(final Sort sort) {
        String[] parti = getParti(sort);
        return parti != null ? parti[0].trim() : VUOTA;
    }


    /**
     * Estrae la Direction dal Sort (springframework) <br>
     *
     * @param sort di springframework
     *
     * @return direction di springframework
     */
    public Sort.Direction getSortDirection(Sort sort) {
        Sort.Direction directionSpring = null;
        String[] parti = getParti(sort);
        String directionTxt = VUOTA;

        if (parti != null) {
            directionTxt = parti[1].trim();
            if (directionTxt.equals(SORT_SPRING_ASC)) {
                directionSpring = Sort.Direction.ASC;
            }
            if (directionTxt.equals(SORT_SPRING_DESC)) {
                directionSpring = Sort.Direction.DESC;
            }
        }

        return directionSpring;
    }


    /**
     * Estrae la Direction dal sortOrder (vaadin) <br>
     *
     * @param sortOrder di vaadin
     *
     * @return direction di springframework
     */
    public Sort.Direction getSortDirection(QuerySortOrder sortOrder) {
        Sort.Direction directionSpring = null;
        SortDirection directionVaadin = null;

        directionVaadin = sortOrder.getDirection();
        if (directionVaadin.name().equals(SORT_VAADIN_ASC)) {
            directionSpring = Sort.Direction.ASC;
        }
        if (directionVaadin.name().equals(SORT_VAADIN_DESC)) {
            directionSpring = Sort.Direction.DESC;
        }

        return directionSpring;
    }

    /**
     * DataProvider usa QuerySortOrder (Vaadin) <br>
     * Query di MongoDB usa Sort (springframework) <br>
     * Qui effettuo la conversione
     *
     * @param sortOrder di Vaadin
     *
     * @return sort di springframework
     */
    public Sort sortVaadinToSpring(QuerySortOrder sortOrder) {
        Sort sort;

        SortDirection direction = sortOrder.getDirection();
        String field = sortOrder.getSorted();
        if (direction == SortDirection.ASCENDING) {
            sort = Sort.by(Sort.Direction.ASC, field);
        }
        else {
            sort = Sort.by(Sort.Direction.DESC, field);
        }

        return sort;
    }


    /**
     * DataProvider usa QuerySortOrder (Vaadin) <br>
     * Query di MongoDB usa Sort (springframework) <br>
     * Qui effettuo la conversione
     *
     * @param sorts sort di Vaadin
     *
     * @return sort di springframework
     */
    public Sort sortVaadinToSpring(List<QuerySortOrder> sorts) {
        Sort sort = null;
        Sort.Direction direction = null;
        String fieldName = VUOTA;

        if (sorts != null) {
            for (QuerySortOrder sortOrder : sorts) {
                direction = getSortDirection(sortOrder);
                fieldName = sortOrder.getSorted();
                if (sort == null) {
                    sort = Sort.by(direction, fieldName);
                }
                else {
                    sort = sort.and(Sort.by(direction, fieldName));
                }
            }
        }

        return sort;
    }

}