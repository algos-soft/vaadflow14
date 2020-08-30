package it.algos.vaadflow14.backend.service;

import com.vaadin.flow.data.binder.Binder;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.wrapper.WrapDueObject;
import it.algos.vaadflow14.ui.fields.AField;
import it.algos.vaadflow14.ui.fields.AIField;
import it.algos.vaadflow14.ui.service.AFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static it.algos.vaadflow14.backend.application.FlowCost.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 24-giu-2020
 * Time: 10:19
 * <p>
 * Classe di servizio <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private ABeanService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ABeanService extends AAbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AAnnotationService annotation;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AReflectionService reflection;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AFieldService fieldService;


    /**
     * Crea i fields e li posiziona in una mappa <br>
     * <p>
     * Mappa ordinata di tutti i fields del form <br>
     * La chiave è la propertyName del field <br>
     * Serve per presentarli (ordinati) dall' alto in basso nel form <br>
     * Serve per recuperarli dal nome per successive elaborazioni <br>
     *
     * @param entityBean    selezionata
     * @param operationForm tipologia di Form in uso
     * @param binder        layer tra DB e UI
     */
    public List<AIField> creaFields(AEntity entityBean, AEOperation operationForm, Binder binder) {
        return creaFields(entityBean, operationForm, binder, (List) null);
    }


    /**
     * Crea i fields e li posiziona in una mappa <br>
     * <p>
     * Mappa ordinata di tutti i fields del form <br>
     * La chiave è la propertyName del field <br>
     * Serve per presentarli (ordinati) dall' alto in basso nel form <br>
     * Serve per recuperarli dal nome per successive elaborazioni <br>
     *
     * @param entityBean    selezionata
     * @param operationForm tipologia di Form in uso
     * @param binder        layer tra DB e UI
     * @param listaNomi     delle property da associare al binder
     */
    public List<AIField> creaFields(AEntity entityBean, AEOperation operationForm, Binder binder, List<String> listaNomi) {
        List<AIField> fieldsList = new ArrayList<>();
        AIField field;

        if (array.isEmpty(listaNomi)) {
            listaNomi = annotation.getListaPropertiesForm(entityBean.getClass());
        }

        for (String fieldName : listaNomi) {
            field = fieldService.create(operationForm, binder, entityBean, fieldName);
            if (field != null) {
                fieldsList.add(field);
            }
        }

        binder.readBean((AEntity) entityBean);

        return fieldsList;
    }


    /**
     * Controlla (prima di salvarla) se la entity indicata è stata modificata <br>
     * Confronta la versione corrente con quella (se esiste) precedentemente salvata su mongo <br>
     *
     * @param entityBeanCurrent in memoria
     *
     * @return true se esisteva ed è stata modificata oppure se non esisteva ed è stata creata
     */
    public boolean isModificata(AEntity entityBeanCurrent) {
        AEntity entityBeanRegistrataSulDatabaseMongo = null;

        if (entityBeanCurrent == null) {
            return false;
        }

        entityBeanRegistrataSulDatabaseMongo = mongo.find(entityBeanCurrent);
        return !entityBeanCurrent.equals(entityBeanRegistrataSulDatabaseMongo);
    }


    /**
     * Estrae le differenze delle sole properties modificate <br>
     * Controlla che le due entities esistano e siano della stessa classe <br>
     *
     * @param entityBeanNew modificata
     * @param entityBeanOld originaria
     *
     * @return mappa delle properties modificate con la coppia di valori vecchio e nuovo
     */
    public Map<String, WrapDueObject> getMappaModifiche(AEntity entityBeanNew, AEntity entityBeanOld) {
        Map<String, WrapDueObject> mappaModifiche = new LinkedHashMap<>();
        List<String> listaProperties;
        Object oldValue;
        Object newValue;
        WrapDueObject wrap;

        if (entityBeanNew == null && entityBeanOld == null) {
            return null;
        }

        listaProperties = reflection.getFieldsName(entityBeanNew.getClass());
        for (String key : listaProperties) {
            newValue = reflection.getPropertyValue(entityBeanNew, key);
            oldValue = entityBeanOld != null ? reflection.getPropertyValue(entityBeanOld, key) : null;

            if (oldValue == null && newValue != null) {
                wrap = new WrapDueObject(oldValue, newValue);
                mappaModifiche.put(key, wrap);
            }

            if (oldValue != null && newValue == null) {
                wrap = new WrapDueObject(oldValue, newValue);
                mappaModifiche.put(key, wrap);
            }

            if (oldValue != null && !oldValue.equals(newValue)) {
                wrap = new WrapDueObject(oldValue, newValue);
                mappaModifiche.put(key, wrap);
            }
        }
        return mappaModifiche;
    }


    /**
     * Estrae le differenze delle sole properties modificate <br>
     * Controlla che le due entities esistano e siano della stessa classe <br>
     *
     * @param entityBeanNew modificata
     *
     * @return lista delle properties modificate con la coppia di valori vecchio e nuovo
     */
    public String getModifiche(AEntity entityBeanNew) {
        return getModifiche(entityBeanNew, null);
    }


    /**
     * Estrae le differenze delle sole properties modificate <br>
     * Controlla che le due entities esistano e siano della stessa classe <br>
     *
     * @param entityBeanNew modificata
     * @param entityBeanOld originaria
     *
     * @return lista delle properties modificate con la coppia di valori vecchio e nuovo
     */
    public String getModifiche(AEntity entityBeanNew, AEntity entityBeanOld) {
        String message = VUOTA;
        Map<String, WrapDueObject> mappaModifiche = getMappaModifiche(entityBeanNew, entityBeanOld);
        WrapDueObject wrap;
        String valori = VUOTA;

        if (mappaModifiche != null && mappaModifiche.size() > 0) {
            message += entityBeanNew.getClass().getSimpleName();
            message += DUE_PUNTI;
            message += entityBeanNew.id;
            message += SPAZIO;
            for (Map.Entry<String, WrapDueObject> mappa : mappaModifiche.entrySet()) {
                wrap = mappa.getValue();
                message += mappa.getKey();
                message += DUE_PUNTI;
                valori = text.isValid(wrap.getPrimo()) ? wrap.getPrimo() + FORWARD + wrap.getSecondo() : VUOTA + wrap.getSecondo();
                message += text.setQuadre(valori);
                message += SPAZIO;
            }
        }

        return message.trim();
    }

}