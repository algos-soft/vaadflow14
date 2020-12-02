package it.algos.vaadflow14.ui.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.QueryParameters;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.service.AAbstractService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import static it.algos.vaadflow14.backend.application.FlowCost.*;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 17-ago-2019
 * Time: 11:26
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ARouteService extends AAbstractService {

    /**
     * The name of a supported <a href="../lang/package-summary.html#charenc">character encoding</a>.
     */
    private static final String CODE = "UTF-8";


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     * Novo record <br>
     *
     * @param entityClazz verso cui navigare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(Class<?> entityClazz) {
        return entityClazz != null ? getQueryForm(entityClazz.getCanonicalName(), VUOTA, AEOperation.addNew) : null;
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     *
     * @param entityClazz          verso cui navigare
     * @param integerEntityBeanKey key ID della entityBean come intero
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(Class<?> entityClazz, int integerEntityBeanKey) {
        return getQueryForm(entityClazz, integerEntityBeanKey + VUOTA, AEOperation.edit);
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     * Nuovo record <br>
     *
     * @param entityClazzCanonicalName verso cui navigare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(String entityClazzCanonicalName) {
        return getQueryForm(entityClazzCanonicalName, VUOTA, AEOperation.addNew);
    }

    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     *
     * @param entityClazz   verso cui navigare
     * @param entityBeanKey key ID della entityBean
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(Class<?> entityClazz, String entityBeanKey) {
        return entityClazz != null ? getQueryForm(entityClazz.getCanonicalName(), entityBeanKey, AEOperation.edit) : null;
    }

    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     *
     * @param entityClazz   verso cui navigare
     * @param entityBeanKey key ID della entityBean
     * @param operationForm tipologia di Form da usare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(Class<?> entityClazz, String entityBeanKey, AEOperation operationForm) {
        return entityClazz != null ? getQueryForm(entityClazz.getCanonicalName(), entityBeanKey, operationForm) : null;
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     *
     * @param entityClazzCanonicalName verso cui navigare
     * @param entityBeanKey            key ID della entityBean
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(String entityClazzCanonicalName, String entityBeanKey) {
        return getQueryForm(entityClazzCanonicalName, entityBeanKey, AEOperation.edit);
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     *
     * @param entityClazzCanonicalName verso cui navigare
     * @param entityBeanKey            key ID della entityBean
     * @param operationForm            tipologia di Form da usare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(String entityClazzCanonicalName, String entityBeanKey, AEOperation operationForm) {
        if (text.isEmpty(entityClazzCanonicalName)) {
            return null;
        }

        // Se manca il parametro, uso quello più probabile
        if (operationForm == null) {
            operationForm = AEOperation.edit;
        }

        // Se entityBeanKey è vuoto, vuol dire che è un nuovo record
        if (entityBeanKey.equals(VUOTA)) {
            operationForm = AEOperation.addNew;
        }

        Map<String, List<String>> mappaQuery = new HashMap<>();
        mappaQuery.put(KEY_BEAN_CLASS, array.getLista(entityClazzCanonicalName));
        mappaQuery.put(KEY_BEAN_ENTITY, array.getLista(text.isValid(entityBeanKey) ? entityBeanKey : KEY_NULL));
        mappaQuery.put(KEY_FORM_TYPE, array.getLista(operationForm.name()));
        return new QueryParameters(mappaQuery);
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'list' <br>
     *
     * @param entityClazz verso cui navigare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryList(Class<?> entityClazz) {
        return entityClazz != null ? getQueryList(entityClazz.getCanonicalName()) : null;
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'list' <br>
     *
     * @param entityClazzCanonicalName verso cui navigare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryList(String entityClazzCanonicalName) {
        if (text.isEmpty(entityClazzCanonicalName)) {
            return null;
        }

        Map<String, List<String>> mappaQuery = new HashMap<>();
        mappaQuery.put(KEY_BEAN_CLASS, array.getLista(entityClazzCanonicalName));
        return new QueryParameters(mappaQuery);
    }


    /**
     * Costruisce una query di parametri da una singola coppia chiave-valore <br>
     * Entrambe le stringhe devono essere valori validi <br>
     *
     * @param keyMap   di chiave=valore
     * @param valueMap di chiave=valore
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQuery(String keyMap, String valueMap) {
        return getQuery(array.getMappa(keyMap, valueMap));
    }


    /**
     * Costruisce una query di parametri da una mappa <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (size maggiore di 0) <br>
     * La mappa in ingresso è semplice: un valore per ogni chiave <br>
     * La classe QueryParameters è però prevista per valori multipli per ogni chiave <br>
     * Occorre quindi costruire una mappa chiave=lista di valori, con un solo valore <br>
     *
     * @param mappa di chiave=valore
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQuery(Map<String, String> mappa) {
        QueryParameters query;
        ArrayList<String> lista;
        HashMap<String, List<String>> mappaQuery = new HashMap<String, List<String>>();

        if (mappa == null) {
            return null;
        }

        if (mappa.size() < 1) {
            return null;
        }

        for (String key : mappa.keySet()) {
            lista = new ArrayList<>();
            lista.add(mappa.get(key));
            mappaQuery.put(key, lista);
        }

        query = new QueryParameters(mappaQuery);

        return query;
    }


    /**
     * Naviga verso l' URL indicato con la mappa di parametri <br>
     *
     * @param interfacciaUtente del chiamante
     * @param routeName         destinazione del Router
     * @param mappa             di chiave=valore
     */
    public void navigate(Optional<UI> interfacciaUtente, String routeName, HashMap<String, String> mappa) {
        QueryParameters query = getQuery(mappa);
        UI ui = null;

        if (interfacciaUtente != null) {
            ui = interfacciaUtente.get();
        }// end of if cycle

        try { // prova ad eseguire il codice
            if (ui != null) {
                ui.navigate(routeName, query);
            }// end of if cycle
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

    }// end of method


    /**
     * Codifica un testo DOPO averlo ricevuto come URL dal browser <br>
     * <p>
     * Decodes a {@code application/x-www-form-urlencoded} string using a specific encoding scheme.
     * The supplied encoding is used to determine what characters are represented by any consecutive sequences of the
     * form "<i>{@code %xy}</i>".
     * <p>
     * <em><strong>Note:</strong> The <a href="http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that UTF-8 should be used.
     * Not doing so may introduce incompatibilities.</em>
     *
     * @param textUTF8 the {@code String} to decode
     *
     * @return the newly decoded {@code String}
     *
     * @throws UnsupportedEncodingException If character encoding needs to be consulted, but
     *                                      named character encoding is not supported
     * @see URLEncoder#encode(String, String)
     * @since 1.4
     */
    public String decodifica(String textUTF8) {
        String text = textUTF8;

        try {
            text = URLDecoder.decode(textUTF8, CODE);
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }

        return text;
    }


    /**
     * Parameters passed through the URL
     * <p>
     * This method is called by the Router, based on values extracted from the URL
     * This method will always be invoked before a navigation target is activated.
     * URL parameters can be annotated as optional using @OptionalParameter.
     * Where more parameters are needed, the URL parameter can also be annotated with @WildcardParameter.
     * The wildcard parameter will never be null.
     * It is possible to get any query parameters contained in a URL, for example ?name1=value1&name2=value2.
     * Use the getQueryParameters() method of the Location class to access query parameters.
     * You can obtain the Location class through the BeforeEvent parameter of the setParameter method.
     * A Location object represents a relative URL made up of path segments and query parameters,
     * but without the hostname, e.g. new Location("foo/bar/baz?name1=value1").
     * getQueryParameters() supports multiple values associated with the same key,
     * for example https://example.com/?one=1&two=2&one=3
     * will result in the corresponding map {"one" : [1, 3], "two": [2]}}.
     */
    public Parametro estraeParametri(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        String primoSegmento = VUOTA;
        QueryParameters queryParameters = location.getQueryParameters();
        Map<String, List<String>> multiParametersMap = queryParameters.getParameters();
        Parametro parametro = null;

        if (location.getFirstSegment() != null) {
            primoSegmento = location.getFirstSegment();
        }

        if (text.isEmpty(parameter) && text.isValid(primoSegmento)) {
            parametro = new Parametro(VUOTA, primoSegmento);
        }

        if (text.isValid(parameter)) {
            parametro = new Parametro(decodifica(parameter), primoSegmento);
        }

        if (array.isAllValid(multiParametersMap)) {
            if (array.isMappaSemplificabile(multiParametersMap)) {
                parametro = new Parametro(array.semplificaMappa(multiParametersMap), primoSegmento);
                parametro.setMappa(true);
                parametro.setMultiMappa(false);
            } else {
                parametro = new Parametro();
                parametro.setMultiParametersMap(multiParametersMap);
                parametro.setPrimoSegmento(primoSegmento);
                parametro.setMappa(false);
                parametro.setMultiMappa(true);
            }
        }
        return parametro;
    }


    /**
     * Estrae un' istanza di classe AService dalla mappa di parametri arrivata dal browser. <br>
     * Costruisce l' istanza dal nome completo <br>
     */
    public ALogic getService(Parametro parametro) {
        ALogic entityLogic = null;
        String canonicalName = VUOTA;
        Object istanza;

        if ((parametro == null) || (parametro.isSingoloParametro())) {
            return null;
        }

        if (parametro.isMappa()) {
            if (parametro.containsKey(KEY_BEAN_CLASS)) {
                canonicalName = parametro.get(KEY_BEAN_CLASS);
                canonicalName += SUFFIX_BUSINESS_LOGIC;
            }

            if (parametro.containsKey(KEY_SERVICE_CLASS)) {
                canonicalName = parametro.get(KEY_SERVICE_CLASS);
            }

            if (text.isValid(canonicalName)) {
                try {
                    istanza = appContext.getBean(Class.forName(canonicalName));

                    if (istanza instanceof ALogic) {
                        entityLogic = (ALogic) istanza;
                    }
                } catch (Exception unErrore) {
                    logger.error(unErrore);
                }
            }

        }
        return entityLogic;
    }


    public final void openList(Class<? extends AEntity> entityClazz) {
        openList(entityClazz.getCanonicalName());
    }


    public final void openList(String canonicalName) {
        final QueryParameters query = this.getQueryList(canonicalName);
        UI.getCurrent().navigate(ROUTE_NAME_GENERIC_LIST, query);
    }


}


