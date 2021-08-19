package it.algos.unit;

import com.mongodb.client.*;
import it.algos.simple.backend.packages.alfa.*;
import it.algos.test.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.packages.crono.anno.*;
import it.algos.vaadflow14.backend.packages.crono.giorno.*;
import it.algos.vaadflow14.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 30-apr-2021
 * Time: 07:51
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("Mongo service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MongoServiceTest extends ATest {

    protected static final String COLLEZIONE_INESISTENTE = "pomeriggio";

    protected static final String COLLEZIONE_VUOTA = "alfa";

    protected static final String COLLEZIONE_VALIDA = "via";

    private static final String DATA_BASE_NAME = "vaadflow14";

    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    private AMongoService service;

    @InjectMocks
    private GsonService gSonService;

    private static String[] COLLEZIONI() {
        return new String[]{"pomeriggio", "alfa", "via"};
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();

        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        Assertions.assertNotNull(service);

        MockitoAnnotations.initMocks(gSonService);
        Assertions.assertNotNull(gSonService);

        service.text = text;
        service.array = array;
        service.annotation = annotation;
        service.gSonService = gSonService;

        gSonService.text = text;
        gSonService.array = array;
        gSonService.reflection = reflection;
        gSonService.annotation = annotation;

        service.fixProperties(DATA_BASE_NAME);
        gSonService.fixProperties(DATA_BASE_NAME);
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    void setUpEach() {
        super.setUp();
    }


    @Test
    @Order(1)
    @DisplayName("1 - Stato del database")
    void status() {
        System.out.println("1- Stato del database");
        MongoDatabase dataBase;

        previsto = DATA_BASE_NAME;
        ottenuto = service.getDatabaseName();
        assertTrue(text.isValid(ottenuto));
        assertEquals(previsto, ottenuto);
        System.out.println(VUOTA);
        System.out.println(String.format("Nome del dataBase corrente: [%s]", ottenuto));

        dataBase = service.getDataBase();
        assertNotNull(dataBase);
        System.out.println(VUOTA);
        System.out.println(String.format("DataBase corrente: [%s]", dataBase));

        listaStr = service.getCollezioni();
        assertNotNull(listaStr);
        System.out.println(VUOTA);
        System.out.println(String.format("Collezioni esistenti: %s", listaStr));
    }

    @Test
    @Order(2)
    @DisplayName("2 - Stato delle collezioni")
    void isExists() {
        System.out.println("2 - Esistenza delle collezioni");

        sorgente = COLLEZIONE_INESISTENTE;
        ottenutoBooleano = service.isExists(sorgente);
        assertFalse(ottenutoBooleano);
        printCollection(sorgente, "non esiste");

        sorgente = COLLEZIONE_VUOTA;
        ottenutoBooleano = service.isExists(sorgente);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, "esiste");
        ottenutoBooleano = service.isExists(Alfa.class);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, " (letta dalla classe) esiste ");

        sorgente = COLLEZIONE_VALIDA;
        ottenutoBooleano = service.isExists(sorgente);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, "esiste");
        ottenutoBooleano = service.isExists(Via.class);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, " (letta dalla classe) esiste");

        System.out.println(VUOTA);
        System.out.println(VUOTA);
        System.out.println("2 - Validità delle collezioni");

        sorgente = COLLEZIONE_INESISTENTE;
        ottenutoBooleano = service.isValid(sorgente);
        assertFalse(ottenutoBooleano);
        printCollection(sorgente, "non è valida");

        sorgente = COLLEZIONE_VUOTA;
        ottenutoBooleano = service.isValid(sorgente);
        assertFalse(ottenutoBooleano);
        printCollection(sorgente, "esiste ma non è valida");
        ottenutoBooleano = service.isValid(Alfa.class);
        assertFalse(ottenutoBooleano);
        printCollection(sorgente, " (letta dalla classe) esiste ma non è valida");

        sorgente = COLLEZIONE_VALIDA;
        ottenutoBooleano = service.isValid(sorgente);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, "è valida");
        ottenutoBooleano = service.isValid(Via.class);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, " (letta dalla classe) è valida");
    }

    @Test
    @Order(3)
    @DisplayName("3 - Lista di tutte le entities")
    void fetch() {
        System.out.println("3 - Lista di tutte le entities");

        sorgenteClasse = Via.class;
        previstoIntero = 26;
        inizio = System.currentTimeMillis();
        try {
            listaBean = service.fetch(sorgenteClasse);
        } catch (Exception unErrore) {
            logger.error(unErrore, this.getClass(), "fetch");
        }
        assertNotNull(listaBean);
        assertEquals(previstoIntero, listaBean.size());
        System.out.println(String.format("Nella collezione '%s' ci sono %s entities recuperate in %s", sorgenteClasse.getSimpleName(), text.format(listaBean.size()), date.deltaTextEsatto(inizio)));

        sorgenteClasse = Giorno.class;
        previstoIntero = 366;
        inizio = System.currentTimeMillis();
        try {
            listaBean = service.fetch(sorgenteClasse);
        } catch (AQueryException unErrore) {
            logger.error(unErrore, this.getClass(), "fetch");
        } catch (AMongoException unErrore) {
            logger.error(unErrore, this.getClass(), "fetch");
        }
        assertNotNull(listaBean);
        assertEquals(previstoIntero, listaBean.size());
        System.out.println(String.format("Nella collezione '%s' ci sono %s entities recuperate in %s", sorgenteClasse.getSimpleName(), text.format(listaBean.size()), date.deltaTextEsatto(inizio)));

        sorgenteClasse = Anno.class;
        previstoIntero = 3030;
        inizio = System.currentTimeMillis();
        try {
            listaBean = service.fetch(sorgenteClasse);
        } catch (AQueryException unErrore) {
            logger.error(unErrore, this.getClass(), "fetch");
        } catch (AMongoException unErrore) {
            logger.error(unErrore, this.getClass(), "fetch");
        }
        assertNotNull(listaBean);
        assertEquals(previstoIntero, listaBean.size());
        System.out.println(String.format("Nella collezione '%s' ci sono %s entities recuperate in %s", sorgenteClasse.getSimpleName(), text.format(listaBean.size()), date.deltaTextEsatto(inizio)));
    }


    //    @ParameterizedTest
    //    @MethodSource(value = "COLLEZIONI")
    //    @Order(2)
    //    @DisplayName("2 - Stato delle collezioni")
    //    void testWithStringParameterOld(String collectionName) {
    //        System.out.println("2 - Stato delle collezioni");
    //
    //        sorgente = collectionName;
    //        ottenutoBooleano = service.isExists(sorgente);
    //        assertFalse(ottenutoBooleano);
    //    }

    protected void printCollection(final String collectionName, final String status) {
        System.out.println(VUOTA);
        System.out.println(String.format("La collezione '%s' %s", collectionName, status));
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterEach
    void tearDownAll() {
    }

}