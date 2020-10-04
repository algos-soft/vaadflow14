package it.algos.unit;

import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.backend.wrapper.AFiltro;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mar, 28-apr-2020
 * Time: 21:18
 */
public abstract class ATest {

    /**
     * The constant PIENA.
     */
    protected static final String PIENA = "Piena";

    protected static final String CONTENUTO = "contenuto";

    /**
     * The constant ARRAY_STRING.
     */
    protected static final String[] ARRAY_SHORT_STRING = {CONTENUTO};

    /**
     * The constant LIST_STRING.
     */
    protected static final List<String> LIST_SHORT_STRING = new ArrayList(Arrays.asList(ARRAY_SHORT_STRING));


    /**
     * The App context.
     */
    @Mock
    protected ApplicationContext appContext;

    @InjectMocks
    protected ATextService text;

    @InjectMocks
    protected AArrayService array;

    @InjectMocks
    protected ADateService date;

    @InjectMocks
    protected AAnnotationService annotation;

    @InjectMocks
    protected AReflectionService reflection;

    @InjectMocks
    protected ALogService logger;

    @InjectMocks
    protected ABeanService bean;

    @InjectMocks
    protected AMongoService mongo;

    @InjectMocks
    protected AWebService web;

    @InjectMocks
    protected AFileService file;

    @InjectMocks
    protected AMathService math;

    protected Logger adminLogger;

    /**
     * The Previsto booleano.
     */
    protected boolean previstoBooleano;

    /**
     * The Ottenuto booleano.
     */
    protected boolean ottenutoBooleano;

    /**
     * The Sorgente.
     */
    protected String sorgente;

    /**
     * The Sorgente.
     */
    protected String sorgente2;

    /**
     * The Previsto.
     */
    protected String previsto;

    /**
     * The Previsto.
     */
    protected String previsto2;

    /**
     * The Previsto.
     */
    protected String previsto3;

    /**
     * The Ottenuto.
     */
    protected String ottenuto;

    /**
     * The Sorgente classe.
     */
    protected Class sorgenteClasse;

    /**
     * The Sorgente field.
     */
    protected Field sorgenteField;

    protected Field ottenutoField;

    protected List<String> sorgenteArray;

    protected List<String> previstoArray;

    protected List<String> ottenutoArray;

    protected List<Integer> previstoInteroArray;

    protected List<Integer> ottenutoInteroArray;

    protected String[] stringArray;

    protected String[] sorgenteMatrice;

    protected String[] previstoMatrice;

    protected String[] ottenutoMatrice;

    protected Integer[] sorgenteInteroMatrice;

    protected Integer[] previstoInteroMatrice;

    protected Integer[] ottenutoInteroMatrice;

    protected Map mappaSorgente;

    protected Map mappaPrevista;

    protected Map mappaOttenuta;

    protected String tag;

    protected Object obj;

    protected int sorgenteIntero;

    protected int previstoIntero;

    protected int ottenutoIntero;

    protected double previstoDouble = 0;

    protected double ottenutoDouble = 0;

    protected int dividendo;

    protected int divisore;

    protected AEntity entityBean;


    protected Query query;

    protected Sort sort;

    protected AFiltro filtro;

    protected List<AFiltro> listaFiltri;

    protected List<String> listaStr;

    protected List<AEntity> listaBean;

    protected byte[] bytes;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     */
    protected void setUpStartUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(appContext);
        Assertions.assertNotNull(appContext);

        MockitoAnnotations.initMocks(text);
        Assertions.assertNotNull(text);

        MockitoAnnotations.initMocks(array);
        Assertions.assertNotNull(array);

        MockitoAnnotations.initMocks(logger);
        Assertions.assertNotNull(logger);

        MockitoAnnotations.initMocks(date);

        adminLogger = LoggerFactory.getLogger("wam.admin");
        Assertions.assertNotNull(adminLogger);

        MockitoAnnotations.initMocks(annotation);
        Assertions.assertNotNull(annotation);

        MockitoAnnotations.initMocks(reflection);
        Assertions.assertNotNull(reflection);

        MockitoAnnotations.initMocks(bean);
        Assertions.assertNotNull(bean);

        MockitoAnnotations.initMocks(mongo);
        Assertions.assertNotNull(mongo);

        MockitoAnnotations.initMocks(web);
        Assertions.assertNotNull(web);

        MockitoAnnotations.initMocks(file);
        Assertions.assertNotNull(file);

        MockitoAnnotations.initMocks(math);
        Assertions.assertNotNull(math);

        array.text = text;
        text.array = array;
        logger.text = text;
        logger.adminLogger = adminLogger;
        annotation.array = array;
        reflection.array = array;
        reflection.text = text;
        reflection.text = text;
        bean.mongo = mongo;
        mongo.text = text;
        web.text = text;
        file.text = text;
        file.array = array;
        file.logger = logger;
        date.math = math;
        sort = null;

    }


    /**
     * Qui passa ad ogni test delle sottoclassi <br>
     */
    protected void setUp() {
        previstoBooleano = false;
        ottenutoBooleano = false;
        sorgente = VUOTA;
        sorgente2 = VUOTA;
        ottenuto = VUOTA;
        previsto = VUOTA;
        previsto2 = VUOTA;
        previsto3 = VUOTA;
        sorgenteClasse = null;
        sorgenteField = null;
        sorgenteMatrice = null;
        previstoMatrice = null;
        ottenutoMatrice = null;
        previstoArray = null;
        ottenutoArray = null;
        previstoInteroArray = null;
        ottenutoInteroArray = null;
        query = null;
        sort = null;
        filtro = null;
        listaFiltri = null;
        listaBean = null;
        listaStr = null;
        bytes = null;
    }


    protected void print(List<String> lista) {
        if (array.isValid(lista)) {
            for (String stringa : lista) {
                System.out.println(stringa);
            }
        }
    }


    protected void printList(List<List<String>> listaTable) {
        if (array.isValid(listaTable)) {
            for (List<String> lista : listaTable) {
                System.out.println(VUOTA);
                if (array.isValid(lista)) {
                    for (String stringa : lista) {
                        System.out.println(stringa);
                    }
                }
            }
        }
    }


    protected void printMappa(Map<String, List<String>> mappa) {
        List<String> lista;
        if (array.isValid(mappa)) {
            for (String key : mappa.keySet()) {
                lista = mappa.get(key);
                System.out.println(VUOTA);
                if (array.isValid(lista)) {
                    print(lista);
                }
            }
        }
    }

}// end of class
