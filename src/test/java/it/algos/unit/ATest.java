package it.algos.unit;

import com.mongodb.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.packages.crono.anno.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.backend.wrapper.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.slf4j.*;
import org.springframework.context.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;

import java.lang.reflect.Field;
import java.time.*;
import java.util.*;


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

    protected static final String CONTENUTO_DUE = "mariolino";

    /**
     * The constant ARRAY_STRING.
     */
    protected static final String[] ARRAY_SHORT_STRING = {CONTENUTO};

    protected static final String[] ARRAY_SHORT_STRING_DUE = {CONTENUTO_DUE};

    /**
     * The constant LIST_STRING.
     */
    protected static final List<String> LIST_SHORT_STRING = new ArrayList(Arrays.asList(ARRAY_SHORT_STRING));

    protected static final List<String> LIST_SHORT_STRING_DUE = new ArrayList(Arrays.asList(ARRAY_SHORT_STRING_DUE));

    protected final static String FIELD_NAME_NOTE = "note";

    protected final static String FIELD_NAME_ORDINE = "ordine";

    protected final static String FIELD_NAME_CODE = "code";

    protected final static String HEADER_ORDINE = "#";

    protected final static String HEADER_CODE = "code";

    protected final static String HEADER_NOME = "nome";

    protected final static String NAME_ORDINE = "ordine";

    protected final static String NAME_CODE = "code";

    protected final static String NAME_NOME = "nome";

    protected static Class<? extends AEntity> VIA_ENTITY_CLASS = Via.class;

    protected static Class<? extends AEntity> ANNO_ENTITY_CLASS = Anno.class;

    protected static Field FIELD_ORDINE;

    protected static Field FIELD_NOME;

    protected static String PATH = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow14/src/main/java/it/algos/vaadflow14/wizard/";

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

    @InjectMocks
    protected AGSonService gSonService;

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
     * The Sorgente.
     */
    protected String sorgente3;

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

    protected LocalDateTime previstoDataTime;

    protected LocalDateTime ottenutoDataTime;

    protected LocalDate previstoData;

    protected LocalDate ottenutoData;

    protected LocalTime previstoOrario;

    protected LocalTime ottenutoOrario;

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

    protected BasicDBObject objectQuery;

    protected Sort sort;

    protected AFiltro filtro;

    protected List<AFiltro> listaFiltri;

    protected List<Field> listaFields;

    protected List<String> listaStr;

    protected List<AEntity> listaBean;

    protected byte[] bytes;

    protected Class clazz;

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

        MockitoAnnotations.initMocks(gSonService);
        Assertions.assertNotNull(gSonService);

        array.text = text;
        text.array = array;
        logger.text = text;
        logger.adminLogger = adminLogger;
        annotation.text = text;
        annotation.array = array;
        annotation.logger = logger;
        annotation.reflection = reflection;
        reflection.array = array;
        reflection.text = text;
        gSonService.text = text;
        gSonService.array = array;
        bean.mongo = mongo;
        mongo.text = text;
        mongo.annotation = annotation;
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
        listaFields = null;
        bytes = null;
        FIELD_ORDINE = reflection.getField(VIA_ENTITY_CLASS, NAME_ORDINE);
        FIELD_NOME = reflection.getField(VIA_ENTITY_CLASS, NAME_NOME);
        entityBean = null;
        clazz = null;
    }


    protected void print(List<String> lista) {
        System.out.println(VUOTA);
        if (array.isAllValid(lista)) {
            for (String stringa : lista) {
                System.out.println(stringa);
            }
        }
    }


    protected void printList(List<List<String>> listaTable) {
        if (array.isAllValid(listaTable)) {
            for (List<String> lista : listaTable) {
                System.out.println(VUOTA);
                if (array.isAllValid(lista)) {
                    for (String stringa : lista) {
                        System.out.println(stringa);
                    }
                }
            }
        }
    }


    protected void printMappa(Map<String, List<String>> mappa) {
        List<String> lista;
        if (array.isAllValid(mappa)) {
            for (String key : mappa.keySet()) {
                lista = mappa.get(key);
                System.out.println(VUOTA);
                if (array.isAllValid(lista)) {
                    print(lista);
                }
            }
        }
    }

}// end of class
