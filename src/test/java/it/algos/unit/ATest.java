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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mar, 28-apr-2020
 * Time: 21:18
 */
public class ATest {

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

    // alcune date di riferimento
    protected final static Date DATE_UNO = new Date(1413870120000L); // 21 ottobre 2014, 7 e 42

    protected final static Date DATE_DUE = new Date(1412485440000L); // 5 ottobre 2014, 7 e 04

    protected final static Date DATE_TRE = new Date(1412485920000L); // 5 ottobre 2014, 7 e 12

    protected final static Date DATE_QUATTRO = new Date(1394259124000L); // 8 marzo 2014, 7 e 12 e 4

    protected final static LocalDate LOCAL_DATE_UNO = LocalDate.of(2014, 10, 21);

    protected final static LocalDate LOCAL_DATE_DUE = LocalDate.of(2014, 10, 5);

    protected final static LocalDate LOCAL_DATE_TRE = LocalDate.of(2015, 10, 5);

    protected final static LocalDate LOCAL_DATE_QUATTRO = LocalDate.of(2015, 3, 8);

    protected final static LocalDate LOCAL_DATE_VUOTA = LocalDate.of(1970, 1, 1);

    protected final static LocalDate LOCAL_DATE_PRIMO_VALIDO = LocalDate.of(1970, 1, 2);

    protected final static LocalDate LOCAL_DATE_OLD = LocalDate.of(1946, 10, 28);

    protected final static LocalDateTime LOCAL_DATE_TIME_UNO = LocalDateTime.of(2014, 10, 21, 7, 42);

    protected final static LocalDateTime LOCAL_DATE_TIME_DUE = LocalDateTime.of(2014, 10, 5, 7, 4);

    protected final static LocalDateTime LOCAL_DATE_TIME_VUOTA = LocalDateTime.of(1970, 1, 1, 0, 0);

    protected final static LocalDateTime LOCAL_DATE_TIME_PRIMO_VALIDO = LocalDateTime.of(1970, 1, 1, 0, 1);

    protected final static LocalDateTime LOCAL_DATE_TIME_OLD = LocalDateTime.of(1946, 10, 28, 0, 0);

    protected final static LocalTime LOCAL_TIME_UNO = LocalTime.of(7, 42);

    protected final static LocalTime LOCAL_TIME_DUE = LocalTime.of(7, 4);

    protected final static LocalTime LOCAL_TIME_TRE = LocalTime.of(22, 0);

    protected final static LocalTime LOCAL_TIME_QUATTRO = LocalTime.of(6, 0);

    protected final static LocalTime LOCAL_TIME_VUOTO = LocalTime.of(0, 0);

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
    protected  AFileService file;

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

    protected AEntity entityBean;

    protected List<AEntity> listaBean;

    protected Query query;

    protected Sort sort;

    protected AFiltro filtro;

    protected List<AFiltro> listaFiltri;

    protected List<String> lista;


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
        Assertions.assertNotNull(date);

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
    }


    /**
     * Qui passa ad ogni test delle sottoclassi <br>
     */
    protected void setUp() {
        previstoBooleano = false;
        ottenutoBooleano = false;
        sorgente = VUOTA;
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
        lista = null;
    }


}// end of class
