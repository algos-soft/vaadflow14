package it.algos.integration;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.google.gson.*;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.*;
import it.algos.simple.*;
import it.algos.test.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.packages.company.*;
import it.algos.vaadflow14.backend.packages.crono.anno.*;
import it.algos.vaadflow14.backend.packages.crono.giorno.*;
import it.algos.vaadflow14.backend.packages.crono.mese.*;
import it.algos.vaadflow14.backend.packages.crono.secolo.*;
import it.algos.vaadflow14.backend.packages.geografica.stato.*;
import it.algos.vaadflow14.backend.packages.security.utente.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.backend.wrapper.*;
import static junit.framework.TestCase.*;
import org.bson.*;
import org.bson.conversions.*;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.test.context.junit.jupiter.*;

import java.text.*;
import java.util.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 17-lug-2020
 * Time: 06:27
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Mongo Service Integration (con mongoOp)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MongoServiceIntegrationTest extends ATest {

    protected static final String COLLEZIONE_INESISTENTE = "pomeriggio";

    protected static final String COLLEZIONE_VUOTA = "utente";

    protected static final String COLLEZIONE_VALIDA = "giorno";

    private static final String DATA_BASE_NAME = "vaadflow14";

    /**
     * Inietta da Spring
     */
    @Autowired
    public MongoTemplate mongoOp;

    protected MongoCollection collection;

    protected Bson bSon;

    /**
     * The Service.
     */
    @Autowired
    MongoService service;

    @InjectMocks
    MeseService meseService;

    @InjectMocks
    SecoloService secoloService;

    private Mese meseUno;

    private Mese meseDue;

    private AETypeSerializing oldType;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();

        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        Assertions.assertNotNull(service);

        MockitoAnnotations.initMocks(meseService);
        Assertions.assertNotNull(meseService);

        MockitoAnnotations.initMocks(secoloService);
        Assertions.assertNotNull(secoloService);

        MockitoAnnotations.initMocks(service.mongoOp);
        Assertions.assertNotNull(service.mongoOp);

//        this.cancellazioneEntitiesProvvisorie();
//        this.creazioneInizialeEntitiesProvvisorie();
        oldType = FlowVar.typeSerializing;

        companyService.mongo = service;
        //        service.mongoOp = mongoOp;
    }


    @BeforeEach
    void setUpEach() {
        super.setUp();

        collection = null;
        bSon = null;
        FlowVar.typeSerializing = oldType;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Stato del database")
    void status() {
        System.out.println("1- Stato del database");
        MongoDatabase dataBase;

        ottenuto = service.getDatabaseName();
        assertTrue(textService.isValid(ottenuto));
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
    @DisplayName("2 - Collezioni")
    void getCollection() {
        System.out.println("2- Collezioni");

        clazz = null;
        collection = service.getCollection(clazz);
        assertNull(collection);
        System.out.println(String.format("La collezione %s non esiste", clazz));

        clazz = Via.class;
        collection = service.getCollection(clazz);
        assertNotNull(collection);
        System.out.println(String.format("La collezione %s esiste", clazz));

        collection = service.getCollection(sorgente);
        assertNull(collection);
        System.out.println(String.format("La collezione %s non esiste", sorgente));

        sorgente = "via";
        collection = service.getCollection(sorgente);
        assertNotNull(collection);
        System.out.println(String.format("La collezione %s esiste", sorgente));

        sorgente = Via.class.getCanonicalName();
        collection = service.getCollection(sorgente);
        assertNotNull(collection);
        System.out.println(String.format("La collezione %s esiste", sorgente));

        sorgente = Via.class.getName();
        collection = service.getCollection(sorgente);
        assertNotNull(collection);
        System.out.println(String.format("La collezione %s esiste", sorgente));

        sorgente = Via.class.getSimpleName().toLowerCase(Locale.ROOT);
        collection = service.getCollection(sorgente);
        assertNotNull(collection);
        System.out.println(String.format("La collezione %s esiste", sorgente));
    }

//    @Test
//    @Order(3)
//    @DisplayName("3 - Esistenza delle collezioni")
//    void isExistsCollection() {
//        System.out.println("3 - Esistenza delle collezioni dalla classe");
//        clazz = null;
//        ottenutoBooleano = service.isExistsCollection(clazz);
//        assertFalse(ottenutoBooleano);
//        printCollection(clazz, "non esiste");
//
//        clazz = Utente.class;
//        ottenutoBooleano = service.isExistsCollection(clazz);
//        assertTrue(ottenutoBooleano);
//        printCollection(clazz, "esiste");
//
//        clazz = Via.class;
//        ottenutoBooleano = service.isExistsCollection(clazz);
//        assertTrue(ottenutoBooleano);
//        printCollection(clazz, "esiste");
//
//        System.out.println(VUOTA);
//        System.out.println(VUOTA);
//        System.out.println("3 - Esistenza delle collezioni dal nome");
//        sorgente = COLLEZIONE_INESISTENTE;
//        ottenutoBooleano = service.isExistsCollection(sorgente);
//        assertFalse(ottenutoBooleano);
//        printCollection(sorgente, "non esiste");
//
//        sorgente = COLLEZIONE_VUOTA;
//        ottenutoBooleano = service.isExistsCollection(sorgente);
//        assertTrue(ottenutoBooleano);
//        printCollection(sorgente, "esiste");
//
//        sorgente = COLLEZIONE_VALIDA;
//        ottenutoBooleano = service.isExistsCollection(sorgente);
//        assertTrue(ottenutoBooleano);
//        printCollection(sorgente, "esiste");
//    }
//
//    @Test
//    @Order(4)
//    @DisplayName("4 - Validità delle collezioni")
//    void isValidCollection() {
//        System.out.println("4 - Validità delle collezioni dalla classe");
//
//        clazz = null;
//        try {
//            ottenutoBooleano = service.isValidCollection(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertFalse(ottenutoBooleano);
//        printCollection(clazz, "non è valida");
//
//        clazz = Utente.class;
//        try {
//            ottenutoBooleano = service.isValidCollection(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertFalse(ottenutoBooleano);
//        printCollection(clazz, "non è valida");
//
//        clazz = Via.class;
//        try {
//            ottenutoBooleano = service.isValidCollection(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoBooleano);
//        printCollection(clazz, "è valida");
//
//        System.out.println(VUOTA);
//        System.out.println(VUOTA);
//        System.out.println("4 - Validità delle collezioni dal nome");
//
//        sorgente = COLLEZIONE_INESISTENTE;
//        try {
//            ottenutoIntero = service.count(VIA_ENTITY_CLASS, filtro);
//        } catch (AlgosException unErrore) {
//        }
//        try {
//            ottenutoBooleano = service.isValidCollection(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertFalse(ottenutoBooleano);
//        printCollection(sorgente, "non è valida");
//
//        sorgente = COLLEZIONE_VUOTA;
//        try {
//            ottenutoBooleano = service.isValidCollection(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertFalse(ottenutoBooleano);
//        printCollection(sorgente, "non è valida");
//
//        sorgente = COLLEZIONE_VALIDA;
//        try {
//            ottenutoBooleano = service.isValidCollection(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoBooleano);
//        printCollection(sorgente, "è valida");
//    }
//
//    @Test
//    @Order(5)
//    @DisplayName("5 - Check collezione vuota")
//    void isEmptyCollection() {
//        System.out.println("5 - Check collezione vuota dalla classe");
//
//        clazz = null;
//        try {
//            ottenutoBooleano = service.isEmptyCollection(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoBooleano);
//        printCollection(clazz, "è vuota");
//
//        clazz = Utente.class;
//        try {
//            ottenutoBooleano = service.isEmptyCollection(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoBooleano);
//        printCollection(clazz, "è vuota");
//
//        clazz = Via.class;
//        try {
//            ottenutoBooleano = service.isEmptyCollection(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertFalse(ottenutoBooleano);
//        printCollection(clazz, "non è vuota");
//
//        System.out.println(VUOTA);
//        System.out.println(VUOTA);
//        System.out.println("5 - Check collezione vuota dal nome");
//
//        sorgente = COLLEZIONE_INESISTENTE;
//        try {
//            ottenutoBooleano = service.isEmptyCollection(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoBooleano);
//        printCollection(sorgente, "è vuota");
//
//        sorgente = COLLEZIONE_VUOTA;
//        try {
//            ottenutoBooleano = service.isEmptyCollection(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoBooleano);
//        printCollection(sorgente, "non è vuota");
//
//        sorgente = COLLEZIONE_VALIDA;
//        try {
//            ottenutoBooleano = service.isEmptyCollection(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertFalse(ottenutoBooleano);
//        printCollection(sorgente, "non è vuota");
//    }
//
//    @Test
//    @Order(6)
//    @DisplayName("6 - Count totale della classe (gson e spring)")
//    void count() {
//        System.out.println("6 - Count totale della collezione dalla classe");
//        System.out.println("metodo semplice per l'intera collection");
//        System.out.println("rimanda al metodo base con filtro (query) nullo");
//
//        System.out.println(VUOTA);
//        System.out.println("Test eseguito con typeSerializing=gson");
//        FlowVar.typeSerializing = AETypeSerializing.gson;
//        clazz = null;
//        try {
//            ottenutoIntero = service.count(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(clazz, ottenutoIntero);
//
//        clazz = Utente.class;
//        try {
//            ottenutoIntero = service.count(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(clazz, ottenutoIntero);
//
//        clazz = Giorno.class;
//        previstoIntero = 366;
//        try {
//            ottenutoIntero = service.count(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        printCount(clazz, ottenutoIntero);
//
//        System.out.println(VUOTA);
//        System.out.println(VUOTA);
//        System.out.println("6 - Count totale della collezione dal nome");
//
//        sorgente = VUOTA;
//        try {
//            ottenutoIntero = service.count(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(sorgente, ottenutoIntero);
//
//        sorgente = COLLEZIONE_INESISTENTE;
//        try {
//            ottenutoIntero = service.count(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(sorgente, ottenutoIntero);
//
//        sorgente = COLLEZIONE_VUOTA;
//        try {
//            ottenutoIntero = service.count(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(sorgente, ottenutoIntero);
//
//        sorgente = COLLEZIONE_VALIDA;
//        previstoIntero = 366;
//        try {
//            ottenutoIntero = service.count(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        printCount(sorgente, ottenutoIntero);
//
//        System.out.println(VUOTA);
//        System.out.println(VUOTA);
//        System.out.println("Test eseguito con typeSerializing=spring");
//        FlowVar.typeSerializing = AETypeSerializing.spring;
//        clazz = null;
//        try {
//            ottenutoIntero = service.count(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(clazz, ottenutoIntero);
//
//        clazz = Utente.class;
//        try {
//            ottenutoIntero = service.count(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(clazz, ottenutoIntero);
//
//        clazz = Giorno.class;
//        previstoIntero = 366;
//        try {
//            ottenutoIntero = service.count(clazz);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        printCount(clazz, ottenutoIntero);
//
//        System.out.println(VUOTA);
//        System.out.println(VUOTA);
//        System.out.println("6 - Count totale della collezione dal nome");
//
//        sorgente = VUOTA;
//        try {
//            ottenutoIntero = service.count(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(sorgente, ottenutoIntero);
//
//        sorgente = COLLEZIONE_INESISTENTE;
//        try {
//            ottenutoIntero = service.count(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(sorgente, ottenutoIntero);
//
//        sorgente = COLLEZIONE_VUOTA;
//        try {
//            ottenutoIntero = service.count(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        printCount(sorgente, ottenutoIntero);
//
//        sorgente = COLLEZIONE_VALIDA;
//        previstoIntero = 366;
//        try {
//            ottenutoIntero = service.count(sorgente);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        printCount(sorgente, ottenutoIntero);
//    }
//
//    @Test
//    @Order(7)
//    @DisplayName("7 - Count filtrato (gson e spring) dalla classe")
//    void count2() {
//        System.out.println("7 - Count filtrato (gson e spring) dalla classe");
//        FlowVar.typeSerializing = AETypeSerializing.gson;
//
//        try {
//            ottenutoIntero = service.count(clazz, bSon);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        System.out.println(String.format("Manca la classe"));
//
//        clazz = Utente.class;
//        try {
//            ottenutoIntero = service.count(clazz, bSon);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        System.out.println(String.format("La collezione %s ha %s entities", clazz.getSimpleName(), ottenutoIntero));
//
//        clazz = Mese.class;
//        previstoIntero = 12;
//        try {
//            ottenutoIntero = service.count(clazz, bSon);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("Manca il valore di bSon; l'intera collezione %s ha %s entities", clazz.getSimpleName(), ottenutoIntero));
//
//        clazz = Mese.class;
//        sorgente = "giorni";
//        sorgenteIntero = 30;
//        previstoIntero = 4;
//        bSon = new Document(sorgente, sorgenteIntero);
//        try {
//            ottenutoIntero = service.count(clazz, bSon);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("La classe %s ha %s entities filtrate", clazz.getSimpleName(), ottenutoIntero));
//
//        System.out.println(VUOTA);
//        clazz = Mese.class;
//        sorgente = "giorni";
//        sorgenteIntero = 31;
//        previstoIntero = 7;
//        bSon = new Document(sorgente, sorgenteIntero);
//        try {
//            ottenutoIntero = service.count(clazz, bSon);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("La classe %s ha %s entities filtrate", clazz.getSimpleName(), ottenutoIntero));
//
//        System.out.println(VUOTA);
//        clazz = Mese.class;
//        sorgente = "giorni";
//        sorgenteIntero = 28;
//        previstoIntero = 1;
//        bSon = new Document(sorgente, sorgenteIntero);
//        try {
//            ottenutoIntero = service.count(clazz, bSon);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("La classe %s ha %s entities filtrate", clazz.getSimpleName(), ottenutoIntero));
//
//        System.out.println(VUOTA);
//        sorgente2 = Mese.class.getSimpleName().toLowerCase(Locale.ROOT);
//        sorgente = "giorni";
//        sorgenteIntero = 28;
//        previstoIntero = 1;
//        bSon = new Document(sorgente, sorgenteIntero);
//        try {
//            ottenutoIntero = service.count(sorgente2, bSon);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("La classe %s ha %s entities filtrate", clazz.getSimpleName(), ottenutoIntero));
//
//        System.out.println(VUOTA);
//        sorgente2 = "non esiste";
//        sorgente = "giorni";
//        sorgenteIntero = 28;
//        previstoIntero = 0;
//        bSon = new Document(sorgente, sorgenteIntero);
//        try {
//            ottenutoIntero = service.count(sorgente2, bSon);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("La classe %s ha %s entities filtrate", clazz.getSimpleName(), ottenutoIntero));
//    }
//
//
//    @Test
//    @Order(8)
//    @DisplayName("8 - Count gson filtrato (propertyName, propertyValue) dalla classe")
//    void count3() {
//        System.out.println("8 - Count gson filtrato (propertyName, propertyValue) dalla classe");
//        FlowVar.typeSerializing = AETypeSerializing.gson;
//
//        clazz = null;
//        sorgente = VUOTA;
//        sorgente2 = VUOTA;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgente2);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        System.out.println(String.format("Manca la classe"));
//
//        clazz = Mese.class;
//        sorgente = VUOTA;
//        sorgente2 = VUOTA;
//        previstoIntero = 12;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgente2);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("Manca la propertyName e anche la propertyValue e restituisce tutta la collection"));
//
//        clazz = Mese.class;
//        sorgente = "giorni";
//        sorgente2 = VUOTA;
//        previstoIntero = 12;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgente2);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("Manca la propertyValue e restituisce tutta la collection"));
//
//        clazz = Mese.class;
//        sorgente = VUOTA;
//        sorgenteIntero = 28;
//        previstoIntero = 12;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgenteIntero);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("Manca la propertyName e restituisce tutta la collection"));
//
//        clazz = Mese.class;
//        sorgente = "giorni";
//        sorgenteIntero = 31;
//        previstoIntero = 7;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgenteIntero);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("La classe %s ha %s entities filtrate con %s=%s", clazz.getSimpleName(), ottenutoIntero, sorgente, sorgenteIntero));
//    }
//
//
//    @Test
//    @Order(9)
//    @DisplayName("9 - Count spring filtrato (propertyName, propertyValue) dalla classe")
//    void count4() {
//        System.out.println("9 - Count spring filtrato (propertyName, propertyValue) dalla classe");
//        System.out.println("9 - Funziona SOLO in MongoServiceIntegrationTest");
//        System.out.println(VUOTA);
//        FlowVar.typeSerializing = AETypeSerializing.spring;
//
//        clazz = null;
//        sorgente = VUOTA;
//        sorgente2 = VUOTA;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgente2);
//        } catch (AlgosException unErrore) {
//        }
//        assertTrue(ottenutoIntero == 0);
//        System.out.println(String.format("Manca la classe"));
//
//        clazz = Mese.class;
//        sorgente = VUOTA;
//        sorgente2 = VUOTA;
//        previstoIntero = 12;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgente2);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("Manca la propertyName e anche la propertyValue e restituisce tutta la collection"));
//
//        clazz = Mese.class;
//        sorgente = "giorni";
//        sorgente2 = VUOTA;
//        previstoIntero = 12;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgente2);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("Manca la propertyValue e restituisce tutta la collection"));
//
//        clazz = Mese.class;
//        sorgente = VUOTA;
//        sorgenteIntero = 28;
//        previstoIntero = 12;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgenteIntero);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("Manca la propertyName e restituisce tutta la collection"));
//
//        clazz = Mese.class;
//        sorgente = "giorni";
//        sorgenteIntero = 31;
//        previstoIntero = 7;
//        try {
//            ottenutoIntero = service.count(clazz, sorgente, sorgenteIntero);
//        } catch (AlgosException unErrore) {
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//        System.out.println(String.format("Manca la propertyName e restituisce tutta la collection"));
//    }
//
//    //    @Test
//    //    @Order(10)
//    //    @DisplayName("10 - Count spring filtrato (query) dalla classe")
//    //    void countQuery() {
//    //        System.out.println("10 - Count spring filtrato (query) dalla classe");
//    //
//    //        Query query = new Query();
//    //
//    //        previstoIntero = 0;
//    //        ottenutoIntero = service.count((Class) null, (Query) null);
//    //        Assert.assertTrue(ottenutoIntero == 0);
//    //
//    //        query.addCriteria(Criteria.where("mese.$id").is("maggio"));
//    //        ottenutoIntero = service.count(Giorno.class, (Query) null);
//    //        Assert.assertTrue(ottenutoIntero == 0);
//    //        Assert.assertEquals(previstoIntero, ottenutoIntero);
//    //
//    //        previstoIntero = 31;
//    //        ottenutoIntero = service.count(Giorno.class, query);
//    //        Assert.assertTrue(ottenutoIntero > 0);
//    //        Assert.assertEquals(previstoIntero, ottenutoIntero);
//    //    }
//
//    @Test
//    @Order(11)
//    @DisplayName("11 - Count gson filtrato (AFiltro) singolo")
//    void count5() {
//        System.out.println("11 - Count gson filtrato (AFiltro) singolo");
//        FlowVar.typeSerializing = AETypeSerializing.gson;
//        AFiltro filtro;
//
//        String filtroText = "co";
//        filtro = AFiltro.contains(NAME_NOME, filtroText);
//        previstoIntero = 6;
//        try {
//            ottenutoIntero = service.count(VIA_ENTITY_CLASS, filtro);
//        } catch (AlgosException unErrore) {
//            System.out.println(unErrore);
//        }
//        assertEquals(previstoIntero, ottenutoIntero);
//
//        String filtroStart = "v";
//        filtro = AFiltro.start(NAME_NOME, filtroStart);
//        previstoIntero = 4;
//        try {
//            ottenutoIntero = service.count(VIA_ENTITY_CLASS, filtro);
//        } catch (AlgosException unErrore) {
//            System.out.println(unErrore);
//        }
//        Assertions.assertEquals(previstoIntero, ottenutoIntero);
//    }
//
//    @Test
//    @Order(12)
//    @DisplayName("12 - Count gson filtrato (mappaFiltri)")
//    void count6() {
//        System.out.println("12 - Count gson filtrato (mappaFiltri)");
//        System.out.println(VUOTA);
//        FlowVar.typeSerializing = AETypeSerializing.gson;
//        AFiltro filtro;
//
//        String filtroStart = "v";
//        filtro = AFiltro.start(NAME_NOME, filtroStart);
//        mappaFiltri.put("a", filtro);
//
//        String filtroText = "co";
//        AFiltro filtro2 = AFiltro.contains(NAME_NOME, filtroText);
//        mappaFiltri.put("b", filtro2);
//        previstoIntero = 2;
//        try {
//            ottenutoIntero = service.count(VIA_ENTITY_CLASS, mappaFiltri);
//        } catch (AlgosException unErrore) {
//            System.out.println(unErrore.getCause().getMessage());
//            System.out.println(unErrore.getMessage());
//        }
//        Assertions.assertEquals(previstoIntero, ottenutoIntero);
//    }
//
//
//    @Test
//    @Order(13)
//    @DisplayName("13 - Save base di una entity")
//    void save() {
//        System.out.println("13 - Save base di una entity");
//        Company company = null;
//        Company companyReborn = null;
//
//        //--costruisco una entityBean
//        sorgente = "rot";
//        sorgente2 = "Porta Valori Associato";
//        company = companyService.newEntity(sorgente, sorgente2, VUOTA, VUOTA);
//        //        company.setCreazione(LocalDateTime.now());
//        assertNotNull(company);
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json;
//        try {
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
//            mapper.setDateFormat(format);
//            json = mapper.writeValueAsString(company);
//            System.out.println(json);
//        } catch (JsonProcessingException unErrore) {
//            System.out.println(unErrore);
//        }
//
//        //        collection.insertOne(Document.parse(json));
//
//        //        --salvo la entityBean
//        try {
//            //            ((MongoService) service).save(company);
//            companyService.save(company);
//        } catch (AMongoException unErrore) {
//            System.out.println(unErrore);
//        }
//    }
//
//    //    @Test
//    @Order(105)
//    @DisplayName("105 - Via (filtro manca)")
//    void fetch5() {
//        int offset = 0;
//        int limit = 2000;
//        List<Via> listaVia = null;
//        AFiltro filtro = null;
//        Sort.Direction sortDirection;
//        String sortProperty = "nome";
//        String starting = "vi";
//        String contains = "l";
//        Sort sort = null;
//        Class<? extends AEntity> clazz = Via.class;
//        List<AFiltro> filtri = new ArrayList<>();
//        previsto = "banchi";
//        previsto2 = "vicolo";
//        previsto3 = "via";
//        //        String alfa = "^" + Pattern.quote(starting) + ".*";
//        //        String beta = ".*" + Pattern.quote(contains) + ".*";
//
//        previstoIntero = 26;
//        try {
//            listaBean = service.fetch(clazz);
//        } catch (AMongoException unErrore) {
//            assertTrue(false);
//        } catch (AQueryException unErrore) {
//            assertTrue(false);
//        }
//        assertNotNull(listaBean);
//        assertEquals(previstoIntero, listaBean.size());
//        print(listaBean, String.format("%s records di Via completi (filtro manca)", previstoIntero));
//    }
//
//    //    @Test
//    @Order(106)
//    @DisplayName("106 - Via (filtro=null)")
//    void fetch6() {
//        try {
//            previstoIntero = service.count(VIA_ENTITY_CLASS);
//        } catch (AlgosException unErrore) {
//        }
//        try {
//            listaBean = service.fetch(VIA_ENTITY_CLASS, (AFiltro) null);
//        } catch (AlgosException unErrore) {
//            assertTrue(false);
//        }
//        assertNotNull(listaBean);
//        assertEquals(previstoIntero, listaBean.size());
//        print(listaBean, String.format("%s records di Via completi (filtro=null)", previstoIntero));
//    }
//
//    //    @Test
//    @Order(7)
//    @DisplayName("7 - Via (filtri=null)")
//    void fetch7() {
//        try {
//            previstoIntero = service.count(VIA_ENTITY_CLASS);
//        } catch (AlgosException unErrore) {
//        }
//        try {
//            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri);
//        } catch (AlgosException unErrore) {
//        }
//        assertNotNull(listaBean);
//        assertEquals(previstoIntero, listaBean.size());
//        print(listaBean, String.format("%s records di Via completi (filtri=null)", previstoIntero));
//    }
//
//
//    //    @Test
////    @Order(8)
////    @DisplayName("8 - Via (filtri=null, sort=null)")
////    void fetch8() {
////        previstoIntero = service.count(VIA_ENTITY_CLASS);
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri, sortSpring);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via completi (filtri=null, sort=null)", previstoIntero));
////    }
//
//    //    @Test
////    @Order(9)
////    @DisplayName("9 - Via (filtri=null, sort=null, offset=0, limit=0)")
////    void fetch9() {
////        int offset = 0;
////        int limit = 0;
////        previstoIntero = limit > 0 ? limit : service.count(VIA_ENTITY_CLASS) - offset;
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri, sortSpring, offset, limit);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via con (filtri=null, sort=%s, offset=%d, limit=%d)", previstoIntero, sortSpring, offset, limit));
////    }
//
//
//    //    @Test
////    @Order(10)
////    @DisplayName("10 - Via (filtri=null, sort=null, offset=0, limit=15)")
////    void fetch10() {
////        int offset = 0;
////        int limit = 15;
////        previstoIntero = limit > 0 ? limit : service.count(VIA_ENTITY_CLASS) - offset;
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri, sortSpring, offset, limit);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via con (filtri=null, sort=%s, offset=%d, limit=%d)", previstoIntero, sortSpring, offset, limit));
////    }
//
//
//    //    @Test
////    @Order(11)
////    @DisplayName("11 - Via (filtri=null, sort=null, offset=14, limit=0)")
////    void fetch11() {
////        int offset = 14;
////        int limit = 0;
////        previstoIntero = limit > 0 ? limit : service.count(VIA_ENTITY_CLASS) - offset;
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri, sortSpring, offset, limit);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via con (filtri=null, sort=%s, offset=%d, limit=%d)", previstoIntero, sortSpring, offset, limit));
////    }
//
//    //    @Test
////    @Order(12)
////    @DisplayName("12 - Via (filtri=null, sort=null, offset=14, limit=5)")
////    void fetch12() {
////        int offset = 14;
////        int limit = 5;
////        previstoIntero = limit > 0 ? limit : service.count(VIA_ENTITY_CLASS) - offset;
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri, sortSpring, offset, limit);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via con (filtri=null, sort=%s, offset=%d, limit=%d)", previstoIntero, sortSpring, offset, limit));
////    }
//
//    //    @Test
////    @Order(13)
////    @DisplayName("13 - Via (filtri=null, sort=ASC, offset=0, limit=0)")
////    void fetch13() {
////        int offset = 0;
////        int limit = 0;
////        sortSpring = Sort.by(Sort.Direction.ASC, NAME_NOME);
////        previstoIntero = limit > 0 ? limit : service.count(VIA_ENTITY_CLASS) - offset;
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri, sortSpring, offset, limit);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via con (filtri=null, sort=%s, offset=%d, limit=%d)", previstoIntero, sortSpring, offset, limit));
////    }
//
//    //    @Test
////    @Order(14)
////    @DisplayName("14 - Via (filtri=null, sort=DESC, offset=0, limit=0)")
////    void fetch14() {
////        int offset = 0;
////        int limit = 0;
////        sortSpring = Sort.by(Sort.Direction.DESC, NAME_NOME);
////        previstoIntero = limit > 0 ? limit : service.count(VIA_ENTITY_CLASS) - offset;
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri, sortSpring, offset, limit);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via con (filtri=null, sort=%s, offset=%d, limit=%d)", previstoIntero, sortSpring, offset, limit));
////    }
//
//
//    //    @Test
////    @Order(15)
////    @DisplayName("15 - Via (filtri=null, sort=ordine.ASC, offset=7, limit=4)")
////    void fetch15() {
////        int offset = 7;
////        int limit = 4;
////        sortSpring = Sort.by(Sort.Direction.ASC, NAME_ORDINE);
////        previstoIntero = limit > 0 ? limit : service.count(VIA_ENTITY_CLASS) - offset;
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri, sortSpring, offset, limit);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        System.out.println(String.format("%s records di Via con (filtri=null, sort=%s, offset=%d, limit=%d)", previstoIntero, sortSpring, offset, limit));
////        for (AEntity bean : listaBean) {
////            System.out.print(((Via) bean).ordine);
////            System.out.print(SEP);
////            System.out.print(((Via) bean).nome);
////            System.out.println();
////        }
////    }
//
//    //    @Test
////    @Order(16)
////    @DisplayName("16 - Via (filtro=vi)")
////    void fetch16() {
////        String filtroStart = "vi";
////        filtro = AFiltro.start(NAME_NOME, filtroStart);
////        try {
////            previstoIntero = service.count(VIA_ENTITY_CLASS, filtro);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, filtro);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via con (filtro=%s)", listaBean.size(), filtroStart));
////    }
//
//
//    //    @Test
////    @Order(17)
////    @DisplayName("17 - Via (filtro=azz)")
////    void fetch17() {
////        String filtroText = "azz";
////        filtro = AFiltro.contains(NAME_NOME, filtroText);
////        try {
////            previstoIntero = service.count(VIA_ENTITY_CLASS, filtro);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, filtro);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via con (filtro=%s)", listaBean.size(), filtroText));
////    }
//
//
//    //    @Test
////    @Order(18)
////    @DisplayName("18 - Via (filtro=p) con mappa")
////    void fetch18() {
////        String filtroStart = "p";
////        filtro = AFiltro.start(NAME_NOME, filtroStart);
////        mappaFiltri = Collections.singletonMap(filtro.getCriteria().getKey(), filtro);
////        try {
////            previstoIntero = service.count(VIA_ENTITY_CLASS, mappaFiltri);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////        print(listaBean, String.format("%s records di Via con (filtro=%s) con mappa", listaBean.size(), filtroStart));
////    }
//
//
//    //    @Test
////    @Order(19)
////    @DisplayName("19 - Via (filtro=a+co)")
////    void fetch19() {
////        AFiltro filtro = null;
////        String filtroStart = "23";
////        filtro = AFiltro.start(NAME_NOME, filtroStart);
////        mappaFiltri.put("a", filtro);
////        String filtroText = "co";
////        AFiltro filtro2 = AFiltro.contains(NAME_NOME, filtroText);
////        mappaFiltri.put("b", filtro2);
////        try {
////            previstoIntero = service.count(VIA_ENTITY_CLASS, mappaFiltri);
////        } catch (AQueryException unErrore) {
////            assertTrue(true);
////            System.out.println(unErrore);
////        }
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, mappaFiltri);
////            assertNotNull(listaBean);
////            assertEquals(previstoIntero, listaBean.size());
////            print(listaBean, String.format("%s records di Via con (filtro=%s) + (filtro=%s)", listaBean.size(), filtroStart, filtroText));
////        } catch (AMongoException unErrore) {
////            assertTrue(true);
////            System.out.println(unErrore);
////        } catch (AQueryException unErrore) {
////            assertTrue(true);
////            System.out.println(unErrore);
////        }
////    }
//
//    //    @Test
////    @Order(20)
////    @DisplayName("20 - Via (filtro=23+III secolo)")
////    void fetch202() {
////        Secolo unSecolo = null;
////
////        try {
////            unSecolo = (Secolo) secoloService.findByKey("III secolo");
////        } catch (AMongoException unErrore) {
////        }
////        assertNotNull(unSecolo);
////        String fieldName = "secolo";
////        String filtroStart = "23";
////        filtro = AFiltro.start(NAME_ANNO, filtroStart);
////        mappaFiltri.put(NAME_ANNO, filtro);
////
////        previstoIntero = 10;
////        AFiltro filtro2 = AFiltro.ugualeObj(fieldName, unSecolo);
////        mappaFiltri.put(fieldName, filtro2);
////        try {
////            listaBean = service.fetch(ANNO_ENTITY_CLASS, mappaFiltri);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        ottenutoIntero = listaBean.size();
////        assertEquals(previstoIntero, ottenutoIntero);
////        System.out.println(String.format("Ci sono %s entities nella collezione %s con mappaFiltri %s", ottenutoIntero, ANNO_ENTITY_CLASS.getSimpleName(), unSecolo));
////    }
//
//
//    //        @Test
////    @Order(3)
////    @DisplayName("3 - ugualeStr")
////    void ugualeStr() {
////        String filtroText = "viale";
////        previstoIntero = 1;
////        filtro = AFiltro.ugualeStr(NAME_NOME, filtroText);
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, filtro);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////
////        filtroText = "vial";
////        previstoIntero = 0;
////        filtro = AFiltro.ugualeStr(NAME_NOME, filtroText);
////        try {
////            listaBean = service.fetch(VIA_ENTITY_CLASS, filtro);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        assertEquals(previstoIntero, listaBean.size());
////    }
//
//    //    @Test
////    @Order(4)
////    @DisplayName("4 - ugualeObj")
////    void ugualeObj() {
////        Secolo unSecolo = null;
////        try {
////            unSecolo = (Secolo) secoloService.findByKey("XVIII secolo");
////        } catch (AMongoException unErrore) {
////        }
////        assertNotNull(unSecolo);
////        Query query;
////        String fieldName = "secolo";
////        int totRec = service.count(ANNO_ENTITY_CLASS);
////
////        previstoIntero = totRec;
////        try {
////            listaBean = service.fetch(ANNO_ENTITY_CLASS);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        ottenutoIntero = listaBean.size();
////        assertEquals(previstoIntero, ottenutoIntero);
////        System.out.println(String.format("Ci sono %s entities nella collezione %s senza filtri", ottenutoIntero, ANNO_ENTITY_CLASS.getSimpleName()));
////
////        previstoIntero = totRec;
////        try {
////            listaBean = service.fetch(ANNO_ENTITY_CLASS, filtro);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        ottenutoIntero = listaBean.size();
////        assertEquals(previstoIntero, ottenutoIntero);
////        System.out.println(String.format("Ci sono %s entities nella collezione %s con filtro nullo", ottenutoIntero, ANNO_ENTITY_CLASS.getSimpleName()));
////
////        previstoIntero = 100;
////        query = new Query();
////        query.addCriteria(Criteria.where(fieldName).is(unSecolo));
////        listaBean = service.findAll(ANNO_ENTITY_CLASS, query);
////        assertNotNull(listaBean);
////        ottenutoIntero = listaBean.size();
////        assertEquals(previstoIntero, ottenutoIntero);
////        System.out.println(String.format("Ci sono %s entities nella collezione %s con query %s", ottenutoIntero, ANNO_ENTITY_CLASS.getSimpleName(), unSecolo));
////
////        previstoIntero = 100;
////        filtro = AFiltro.ugualeObj(fieldName, unSecolo);
////        try {
////            listaBean = service.fetch(ANNO_ENTITY_CLASS, filtro);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        ottenutoIntero = listaBean.size();
////        assertEquals(previstoIntero, ottenutoIntero);
////        System.out.println(String.format("Ci sono %s entities nella collezione %s con filtro %s", ottenutoIntero, ANNO_ENTITY_CLASS.getSimpleName(), unSecolo));
////
////        previstoIntero = 100;
////        filtro = AFiltro.ugualeObj(fieldName, unSecolo);
////        mappaFiltri.put("keyNonUsata", filtro);
////        try {
////            listaBean = service.fetch(ANNO_ENTITY_CLASS, mappaFiltri);
////        } catch (AMongoException unErrore) {
////            assertTrue(false);
////        } catch (AQueryException unErrore) {
////            assertTrue(false);
////        }
////        assertNotNull(listaBean);
////        ottenutoIntero = listaBean.size();
////        assertEquals(previstoIntero, ottenutoIntero);
////        System.out.println(String.format("Ci sono %s entities nella collezione %s con mappaFiltri %s", ottenutoIntero, ANNO_ENTITY_CLASS.getSimpleName(), unSecolo));
////    }
//
//    //    @Test
//    //    @Order(5)
//    //    @DisplayName("5 - findAllFiltri")
//    //    void findAllFiltri() {
//    //        System.out.println("una serie di filtri");
//    //
//    //        listaFiltri = new ArrayList<>();
//    //
//    //        listaBean = service.findAll(Mese.class, (List<AFiltro>) null);
//    //        Assert.assertNotNull(listaBean);
//    //        Assert.assertTrue(listaBean.size() > 1);
//    //        System.out.println(VUOTA);
//    //        printLista(listaBean);
//    //
//    //        filtro = new AFiltro(Criteria.where("mese.$id").is("marzo"));
//    //        listaFiltri.add(filtro);
//    //        listaBean = service.findAll(Giorno.class, listaFiltri);
//    //        Assert.assertNotNull(listaBean);
//    //        Assert.assertTrue(listaBean.size() > 1);
//    //        System.out.println(VUOTA);
//    //        printLista(listaBean);
//    //
//    //        listaFiltri = new ArrayList<>();
//    //        filtro = new AFiltro(Criteria.where("secolo").is("xxsecolo"));
//    //        listaFiltri.add(filtro);
//    //        sort = Sort.by(Sort.Direction.ASC, "ordine");
//    //        filtro = new AFiltro(Criteria.where("ordine").gt(3970), sort);
//    //        listaFiltri.add(filtro);
//    //        listaBean = service.findAll(Anno.class, listaFiltri);
//    //        Assert.assertNotNull(listaBean);
//    //        Assert.assertTrue(listaBean.size() > 1);
//    //        System.out.println(VUOTA);
//    //        printLista(listaBean);
//    //    }
//    //
//    //
//    //    @Test
//    //    @Order(6)
//    //    @DisplayName("6 - findAllFiltriSort")
//    //    void findAllFiltriSort() {
//    //        System.out.println("una serie di filtri e ordinamenti vari");
//    //
//    //        listaFiltri = new ArrayList<>();
//    //        listaBean = service.findAll(Mese.class, (List<AFiltro>) null);
//    //        Assert.assertNotNull(listaBean);
//    //        Assert.assertTrue(listaBean.size() > 1);
//    //        System.out.println(VUOTA);
//    //        printLista(listaBean);
//    //
//    //        listaFiltri = new ArrayList<>();
//    //        sort = Sort.by(Sort.Direction.DESC, "mese");
//    //        filtro = new AFiltro(sort);
//    //        listaFiltri.add(filtro);
//    //        listaBean = service.findAll(Mese.class, listaFiltri);
//    //        Assert.assertNotNull(listaBean);
//    //        Assert.assertTrue(listaBean.size() > 1);
//    //        System.out.println(VUOTA);
//    //        printLista(listaBean);
//    //
//    //        listaFiltri = new ArrayList<>();
//    //        filtro = new AFiltro(Criteria.where("ordine").lt(10));
//    //        listaFiltri.add(filtro);
//    //        sort = Sort.by(Sort.Direction.ASC, "alfadue");
//    //        filtro = new AFiltro(Criteria.where("ue").is(true), sort);
//    //        listaFiltri.add(filtro);
//    //        listaBean = service.findAll(Stato.class, listaFiltri);
//    //        Assert.assertNotNull(listaBean);
//    //        Assert.assertTrue(listaBean.size() > 1);
//    //        System.out.println(VUOTA);
//    //        printLista(listaBean);
//    //    }
//    //
//    //
//    //    @Test
//    //    @Order(7)
//    //    @DisplayName("7 - find")
//    //    void find() {
//    //        System.out.println("find rimanda a findAll");
//    //
//    //        listaBean = service.find((Class) null);
//    //        Assert.assertNull(listaBean);
//    //
//    //        listaBean = service.find(Secolo.class);
//    //        Assert.assertNotNull(listaBean);
//    //        System.out.println(VUOTA);
//    //        printLista(listaBean);
//    //    }
//    //
//    //
//    //    @Test
//    //    @Order(8)
//    //    @DisplayName("8 - findAll")
//    //    void findAll() {
//    //        System.out.println("tutta la collection");
//    //
//    //        listaBean = service.findAll((Class) null);
//    //        Assert.assertNull(listaBean);
//    //
//    //        listaBean = service.findAll(Mese.class);
//    //        Assert.assertNotNull(listaBean);
//    //        System.out.println(VUOTA);
//    //        printLista(listaBean);
//    //    }
//    //
//    //
//    //    @Test
//    //    @Order(9)
//    //    @DisplayName("9 - findAllQuery")
//    //    void findAllQuery() {
//    //        System.out.println("metodo base - tutta la collection filtrata da una query");
//    //
//    //        query = new Query();
//    //        sort = Sort.by(Sort.Direction.DESC, "ordine");
//    //
//    //        listaBean = service.findAll((Class) null, (Query) null);
//    //        Assert.assertTrue(array.isEmpty(listaBean));
//    //
//    //        listaBean = service.findAll(Mese.class, (Query) null);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query nulla");
//    //
//    //        listaBean = service.findAll(Mese.class, query);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query vuota ordine interno");
//    //
//    //        query.with(sort);
//    //        listaBean = service.findAll(Mese.class, query);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query vuota ma ordinata");
//    //
//    //        query = new Query();
//    //        query.with(sort);
//    //        query.addCriteria(Criteria.where("ordine").lt(8));
//    //        listaBean = service.findAll(Mese.class, query);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query filtrata e ordinata");
//    //
//    //        query = new Query();
//    //        query.addCriteria(Criteria.where("ordine").lt(8));
//    //        query.with(sort);
//    //        query.fields().exclude("ordine");
//    //        listaBean = service.findAll(Mese.class, query);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query filtrata e ordinata e con campo escluso");
//    //
//    //        query = new Query();
//    //        query.addCriteria(Criteria.where("ordine").lt(8));
//    //        query.with(sort);
//    //        query.fields().include("mese");
//    //        listaBean = service.findAll(Mese.class, query);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query filtrata e ordinata e con singolo campo selezionato");
//    //    }
//    //
//    //
//    //    @Test
//    //    @Order(10)
//    //    @DisplayName("10 - findAllQueryProjection")
//    //    void findAllQueryProjection() {
//    //        System.out.println("metodo base - la projection non si usa");
//    //        System.out.println(VUOTA);
//    //
//    //        query = new Query();
//    //        sort = Sort.by(Sort.Direction.ASC, "code");
//    //
//    //        listaBean = service.findAll((Class) null, (Query) null, VUOTA);
//    //        Assert.assertTrue(array.isEmpty(listaBean));
//    //
//    //        listaBean = service.findAll(Mese.class, (Query) null, VUOTA);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query nulla");
//    //
//    //        listaBean = service.findAll(Mese.class, query, VUOTA);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query vuota");
//    //
//    //        query.with(sort);
//    //        listaBean = service.findAll(Mese.class, query, VUOTA);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query ordinata");
//    //
//    //        query.addCriteria(Criteria.where("ordine").lt(10));
//    //        listaBean = service.findAll(Mese.class, query, VUOTA);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query filtrata e ordinata");
//    //
//    //        query.fields().exclude("ordine");
//    //        listaBean = service.findAll(Mese.class, query, VUOTA);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query filtrata e ordinata e selezione dei campi");
//    //
//    //        query = new Query();
//    //        query.fields().include("mese");
//    //        listaBean = service.findAll(Mese.class, query, VUOTA);
//    //        Assert.assertTrue(array.isAllValid(listaBean));
//    //        printLista(listaBean, "lista con query filtrata e ordinata e selezione di un solo campo");
//    //
//    //        String projection = "{'code': 1}";
//    //        query = new Query();
//    //        listaBean = service.findAll(Mese.class, query, projection);
//    //        Assert.assertFalse(array.isAllValid(listaBean));
//    //        printLista(listaBean, "la projection non funziona");
//    //    }
//    //
//    //
//    //    @Test
//    //    @Order(11)
//    //    @DisplayName("11 - findSet")
//    //    void findSet() {
//    //        int offset = 0;
//    //        int limit = 0;
//    //        Class<? extends AEntity> clazz;
//    //
//    //        clazz = Via.class;
//    //        offset = 0;
//    //        limit = 12;
//    //        previstoIntero = limit;
//    //        listaBean = mongo.findSet(clazz, offset, limit);
//    //        Assert.assertNotNull(listaBean);
//    //        Assert.assertEquals(previstoIntero, listaBean.size());
//    //        printLista(listaBean, "Set di entities per Via");
//    //
//    //        offset = 4;
//    //        limit = 5;
//    //        previstoIntero = limit;
//    //        listaBean = mongo.findSet(clazz, offset, limit);
//    //        Assert.assertNotNull(listaBean);
//    //        Assert.assertEquals(previstoIntero, listaBean.size());
//    //        printLista(listaBean, "Set di entities per Via");
//    //
//    //        clazz = Anno.class;
//    //        offset = 2850;
//    //        limit = 4;
//    //        previstoIntero = limit;
//    //        listaBean = mongo.findSet(clazz, offset, limit);
//    //        Assert.assertNotNull(listaBean);
//    //        Assert.assertEquals(previstoIntero, listaBean.size());
//    //        Assert.assertNotNull(((Anno) listaBean.get(0)).secolo.secolo);
//    //        printLista(listaBean, "Set di entities per Anno");
//    //        System.out.println(((Anno) listaBean.get(0)).secolo.secolo);
//    //
//    //        //        clazz = Mese.class;
//    //        //        offset = 0;
//    //        //        limit = 1;
//    //        //        previstoIntero = 1;
//    //        //        listaBean = mongo.findSet(clazz, offset, limit);
//    //        //        Assert.assertNotNull(listaBean);
//    //        //        Assert.assertEquals(previstoIntero, listaBean.size());
//    //        //        Assert.assertNotNull(((Mese) listaBean.get(0)).id);
//    //        //        Assert.assertNotNull(((Mese) listaBean.get(0)).mese);
//    //        //        Assert.assertNotNull(((Mese) listaBean.get(0)).sigla);
//    //        //        printLista(listaBean, "Set di entities per Mese");
//    //        //        System.out.println(((Anno) listaBean.get(0)).secolo.secolo);
//    //    }
//
//
//    //    @Test
//    @Order(12)
//    @DisplayName("12 - findId")
//    void findId() {
//        System.out.println("find rimanda a findById");
//        System.out.println(VUOTA);
//
//        try {
//            entityBean = service.find(Mese.class, "brumaio");
//        } catch (Exception unErrore) {
//        }
//        Assert.assertNull(entityBean);
//
//        previsto = "ottobre";
//        try {
//            entityBean = service.find(Mese.class, "ottobre");
//        } catch (Exception unErrore) {
//        }
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//    }
//
//
//    //    @Test
//    @Order(13)
//    @DisplayName("13 - findById")
//    void findById() {
//        System.out.println("singola entity recuperata da keyID");
//        System.out.println(VUOTA);
//
//        entityBean = service.findByIdOld(Mese.class, "brumaio");
//        Assert.assertNull(entityBean);
//
//        previsto = "ottobre";
//        entityBean = service.findByIdOld(Mese.class, "ottobre");
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//    }
//
//
//    //    @Test
//    @Order(14)
//    @DisplayName("14 - findByKey")
//    void findByKey() {
//        System.out.println("singola entity recuperata da keyID");
//        System.out.println(VUOTA);
//
//        entityBean = service.findByKeyOld(Mese.class, "brumaio");
//        Assert.assertNull(entityBean);
//
//        previsto = "ottobre";
//        entityBean = service.findByKeyOld(Mese.class, "ottobre");
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//    }
//
//
//    //    @Test
//    @Order(15)
//    @DisplayName("15 - findOneFirst")
//    void findOneFirst() {
//        System.out.println("singola entity - se ce n'è più di una restituisce solo la prima");
//
//        entityBean = service.findOneFirst((Class) null, VUOTA, VUOTA);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneFirst((Class) null, "nome", "user");
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneFirst(Secolo.class, VUOTA, "xxsecolo");
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneFirst(Secolo.class, "secolo", VUOTA);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneFirst(Secolo.class, VUOTA, VUOTA);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneFirst(Secolo.class, "anteCristo", "true");
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneFirst(Secolo.class, "secolo", "francesco");
//        Assert.assertNull(entityBean);
//
//        previsto = "xxsecolo";
//        entityBean = service.findOneFirst(Secolo.class, "secolo", "XX secolo");
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Secolo) entityBean).id, previsto);
//        System.out.println(entityBean);
//
//        entityBean = service.findOneFirst(Mese.class, "mese", VUOTA);
//        Assert.assertNull(entityBean);
//
//        previsto = "marzo";
//        entityBean = service.findOneFirst(Mese.class, "sigla", "mar");
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//
//        previsto = "giugno";
//        entityBean = service.findOneFirst(Mese.class, "ordine", 6);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//
//        previsto = "febbraio";
//        entityBean = service.findOneFirst(Mese.class, "giorni", 28);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//
//        previsto = "gennaio";
//        entityBean = service.findOneFirst(Mese.class, "giorni", 31);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//
//        query = new Query();
//        sortSpring = Sort.by(Sort.Direction.ASC, "mese");
//
//        entityBean = service.findOneFirst((Class) null, (Query) null);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneFirst((Class) null, query);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneFirst(Mese.class, (Query) null);
//        Assert.assertNull(entityBean);
//
//        previsto = "gennaio";
//        entityBean = service.findOneFirst(Mese.class, query);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//
//        query = new Query();
//        previsto = "aprile";
//        query.addCriteria(Criteria.where("mese").is("aprile"));
//        entityBean = service.findOneFirst(Mese.class, query);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//
//        previsto = "agosto";
//        query = new Query();
//        query.addCriteria(Criteria.where("giorni").is(31));
//        sortSpring = Sort.by(Sort.Direction.ASC, "mese");
//        query.with(sortSpring);
//        entityBean = service.findOneFirst(Mese.class, query);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//
//        previsto = "gennaio";
//        query = new Query();
//        query.addCriteria(Criteria.where("giorni").is(31));
//        sortSpring = Sort.by(Sort.Direction.ASC, "ordine");
//        query.with(sortSpring);
//        entityBean = service.findOneFirst(Mese.class, query);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//
//        previsto = "febbraio";
//        query = new Query();
//        query.addCriteria(Criteria.where("giorni").is(28));
//        entityBean = service.findOneFirst(Mese.class, query);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//
//        previsto = "febbraio";
//        entityBean = service.findOneFirst(Mese.class, new Query(Criteria.where("giorni").is(28)));
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//
//        previsto = "gennaio";
//        entityBean = service.findOneFirst(Mese.class);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//    }
//
//
//    //    @Test
//    @Order(16)
//    @DisplayName("16 - findOneUnique")
//    void findOneUnique() {
//        System.out.println("singola entity - se ce n'è più di una NON restituisce nulla");
//        query = new Query();
//        sortSpring = Sort.by(Sort.Direction.ASC, "nome");
//
//        entityBean = service.findOneUnique((Class) null, VUOTA, VUOTA);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneUnique((Class) null, "mese", "user");
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneUnique(Mese.class, VUOTA, "marzo");
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneUnique(Mese.class, "mese", VUOTA);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneUnique(Mese.class, VUOTA, VUOTA);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneUnique(Mese.class, "giorni", 30);
//        Assert.assertNull(entityBean);
//
//        previsto = "febbraio";
//        entityBean = service.findOneUnique(Mese.class, "giorni", 28);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//
//        entityBean = service.findOneUnique(Mese.class, "nome", VUOTA);
//        Assert.assertNull(entityBean);
//
//        previsto = "marzo";
//        entityBean = service.findOneUnique(Mese.class, "sigla", "mar");
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//
//        previsto = "ottobre";
//        entityBean = service.findOneUnique(Mese.class, "mese", "ottobre");
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//
//        previsto = "febbraio";
//        entityBean = service.findOneUnique(Mese.class, "giorni", 28);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(((Mese) entityBean).mese, previsto);
//        System.out.println(entityBean);
//
//        previsto = "gennaio";
//        entityBean = service.findOneUnique(Mese.class, "giorni", 31);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneUnique((Class) null, (Query) null);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneUnique((Class) null, query);
//        Assert.assertNull(entityBean);
//
//        entityBean = service.findOneUnique(Mese.class, (Query) null);
//        Assert.assertNull(entityBean);
//
//        previsto = "gennaio";
//        entityBean = service.findOneUnique(Mese.class, query);
//        Assert.assertNull(entityBean);
//
//        previsto = "aprile";
//        query.addCriteria(Criteria.where("mese").is("aprile"));
//        entityBean = service.findOneUnique(Mese.class, query);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//
//        previsto = "agosto";
//        query = new Query();
//        query.addCriteria(Criteria.where("giorni").is(31));
//        sortSpring = Sort.by(Sort.Direction.ASC, "nome");
//        query.with(sortSpring);
//        entityBean = service.findOneUnique(Mese.class, query);
//        Assert.assertNull(entityBean);
//
//        previsto = "gennaio";
//        query = new Query();
//        query.addCriteria(Criteria.where("giorni").is(31));
//        sortSpring = Sort.by(Sort.Direction.ASC, "ordine");
//        query.with(sortSpring);
//        entityBean = service.findOneUnique(Mese.class, query);
//        Assert.assertNull(entityBean);
//
//        previsto = "febbraio";
//        query = new Query();
//        query.addCriteria(Criteria.where("giorni").is(28));
//        entityBean = service.findOneUnique(Mese.class, query);
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//
//        previsto = "febbraio";
//        entityBean = service.findOneUnique(Mese.class, new Query(Criteria.where("giorni").is(28)));
//        Assert.assertNotNull(entityBean);
//        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
//    }
//
//    //    @Test
//    //    @Order(15)
//    //    @DisplayName("findEntity")
//    //    void findEntity() {
//    //        System.out.println("singola entity");
//    //        entityBean = service.find((AEntity) null);
//    //
//    //        roleQuattro = roleLogic.newEntity("Zeta");
//    //        roleQuattro.ordine = 88;
//    //        roleQuattro.id = "mario";
//    //        Assert.assertNotNull(roleQuattro);
//    //        entityBean = service.find(roleQuattro);
//    //        Assert.assertNull(entityBean);
//    //
//    //        previsto = "febbraio";
//    //        query = new Query();
//    //        query.addCriteria(Criteria.where("giorni").is(28));
//    //        entityBean = service.findOneUnique(Mese.class, query);
//    //        Assert.assertNotNull(entityBean);
//    //        Assert.assertEquals(previsto, ((Mese) entityBean).getNome());
//    //
//    //        Mese mese = (Mese) service.find(entityBean);
//    //        Assert.assertNotNull(mese);
//    //        Assert.assertEquals(previsto, mese.getNome());
//    //    }
//
//
//    //    @Test
////    @Order(17)
////    @DisplayName("17 - getNewOrder")
////    void getNewOrder() {
////        System.out.println("recupera ordinamento progressivo");
////
////        sorgenteIntero = service.count(Mese.class);
////        previstoIntero = sorgenteIntero + 1;
////
////        ottenutoIntero = meseService.getNewOrdine();
////        Assert.assertEquals(previstoIntero, ottenutoIntero);
////    }
//
//
//    //    @Test
////    @Order(18)
////    @DisplayName("18 - isEsiste")
////    void isEsiste() {
////        System.out.println("17 - controlla se esiste");
////
////        try {
////            ottenutoBooleano = service.isEsiste((AEntity) null);
////        } catch (Exception unErrore) {
////        }
////        Assert.assertFalse(ottenutoBooleano);
////
////        previsto = "febbraio";
////        query = new Query();
////        query.addCriteria(Criteria.where("giorni").is(28));
////        entityBean = service.findOneUnique(Mese.class, query);
////        Assert.assertNotNull(entityBean);
////
////        try {
////            ottenutoBooleano = service.isEsiste(entityBean);
////        } catch (Exception unErrore) {
////        }
////        Assert.assertTrue(ottenutoBooleano);
////
////        try {
////            ottenutoBooleano = service.isEsiste(Mese.class, "brumaio");
////        } catch (Exception unErrore) {
////        }
////        Assert.assertFalse(ottenutoBooleano);
////
////        try {
////            ottenutoBooleano = service.isEsiste(Mese.class, "marzo");
////        } catch (Exception unErrore) {
////        }
////        Assert.assertTrue(ottenutoBooleano);
////    }
//
//
//    //    @Test
////    @Order(19)
////    @DisplayName("19 - findAll by property")
////    void findAll2() {
////        System.out.println("18 - findAll filtrato da una property");
////
////        sorgente = "mese";
////        sorgente2 = "maggio";
////        listaBean = service.findAll((Class) null, VUOTA, VUOTA);
////        Assert.assertNull(listaBean);
////
////        listaBean = service.findAll((Class) null, sorgente, VUOTA);
////        Assert.assertNull(listaBean);
////
////        listaBean = service.findAll((Class) null, sorgente, sorgente2);
////        Assert.assertNull(listaBean);
////
////        previstoIntero = 366;
////        listaBean = service.findAll(Giorno.class, VUOTA, VUOTA);
////        Assert.assertNotNull(listaBean);
////        Assert.assertEquals(previstoIntero, listaBean.size());
////
////        previstoIntero = 31;
////        listaBean = service.findAll(Giorno.class, sorgente, sorgente2);
////        Assert.assertNotNull(listaBean);
////        Assert.assertEquals(previstoIntero, listaBean.size());
////    }
//
//
//    //    @Test
////    @Order(20)
////    @DisplayName("20 - getCollection")
////    void pippoz() {
////        Gson gson;
////        String json;
////        ObjectMapper mapper = new ObjectMapper();
////        int offset = 0;
////        int limit = 12;
////        Class<? extends AEntity> clazz = Mese.class;
////        AEntity entity;
////        String clazzName = clazz.getSimpleName().toLowerCase();
////        List<AEntity> lista = new ArrayList();
////        Collection<Document> documents = ((MongoService) mongoService).mongoOp.getCollection(clazzName).find().skip(offset).limit(limit).into(new ArrayList());
////
////        for (Document doc : documents) {
////            try {
////                gson = new Gson();
////                json = gson.toJson(doc);
////                System.out.println(json);
////                json = json.replace("_id", "id");
////                json = json.replace("_class", "note");
////                System.out.println(json);
////                System.out.println(VUOTA);
////                entity = mapper.readValue(json, clazz);
////                lista.add(entity);
////            } catch (Exception unErrore) {
////                System.out.println(unErrore);
////            }
////        }
////
////        if (lista != null && lista.size() > 0) {
////            for (AEntity bean : lista) {
////                System.out.println(bean.id);
////            }
////        }
////
////    }
//
//    //    @Test
////    @Order(21)
////    @DisplayName("21 - execute")
////    void execute() {
////        String jsonCommand = "db.getCollection('secolo').find({}, {\"_id\":0,\"ordine\": 1})";
////        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/" + "vaadflow14");
////        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
////                .applyConnectionString(connectionString)
////                .build();
////        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
////        MongoDatabase database = mongoClient.getDatabase("vaadflow14");
////        MongoCollection collection = database.getCollection("mese");
////
////        //        BasicDBObject command = new BasicDBObject("find", "mese");
////        //        Document alfa = mongoOp.executeCommand(String.valueOf(command));
////        //        String gamma = alfa.getString("cursor");
////        //        ObjectId beta = alfa.getObjectId("cursor");
////        int a = 87;
////        //        String jsonCommand = "db.getCollection('secolo').find({}, {\"_id\":0,\"ordine\": 1})";
////        //        Object alfga = mongo.mongoOp.executeCommand(jsonCommand);
////
////    }
//
//
//    //    @Test
//    @Order(22)
//    @DisplayName("22 - find next")
//    void findNext() {
//        String valuePropertyID = "australia";
//        previsto = "austria";
//        Stato statoOttenuto = (Stato) service.findNext(Stato.class, valuePropertyID);
//        Assert.assertNotNull(statoOttenuto);
//        assertEquals(previsto, statoOttenuto.id);
//    }
//
//
//    //    @Test
//    @Order(23)
//    @DisplayName("23 - find next ordered")
//    void findNext2() {
//        Stato statoOttenuto = null;
//        String sortProperty;
//        int sortIndex;
//        String valueProperty;
//
//        sortProperty = "stato";
//        valueProperty = "Australia";
//        previsto = "Austria";
//        statoOttenuto = (Stato) service.findNext(Stato.class, sortProperty, valueProperty);
//        Assert.assertNotNull(statoOttenuto);
//        assertEquals(previsto, statoOttenuto.stato);
//
//        sortProperty = "ordine";
//        sortIndex = 40;
//        previsto = "Azerbaigian";
//        statoOttenuto = (Stato) service.findNext(Stato.class, sortProperty, sortIndex);
//        Assert.assertNotNull(statoOttenuto);
//        assertEquals(previsto, statoOttenuto.stato);
//    }
//
//
//    //    @Test
//    @Order(24)
//    @DisplayName("24 - find previous")
//    void findPrevious() {
//        String valuePropertyID = "burkinafaso";
//        previsto = "bulgaria";
//        Stato statoOttenuto = (Stato) service.findPrevious(Stato.class, valuePropertyID);
//        Assert.assertNotNull(statoOttenuto);
//        assertEquals(previsto, statoOttenuto.id);
//    }
//
//    //    @Test
//    @Order(25)
//    @DisplayName("25 - find previous ordered")
//    void findPrevious2() {
//        Stato statoOttenuto = null;
//        String sortProperty;
//        int sortIndex;
//        String valueProperty;
//
//        sortProperty = "stato";
//        valueProperty = "Burkina Faso";
//        previsto = "Bulgaria";
//        statoOttenuto = (Stato) service.findPrevious(Stato.class, sortProperty, valueProperty);
//        Assert.assertNotNull(statoOttenuto);
//        assertEquals(previsto, statoOttenuto.stato);
//
//        sortProperty = "ordine";
//        sortIndex = 57;
//        previsto = "Brunei";
//        statoOttenuto = (Stato) service.findPrevious(Stato.class, sortProperty, sortIndex);
//        Assert.assertNotNull(statoOttenuto);
//        assertEquals(previsto, statoOttenuto.stato);
//    }
//
//    //    @Test
//    @Order(26)
//    @DisplayName("26 - next and previous ordered")
//    void findOrdered() {
//        Via viaOttenuta = null;
//        String sortProperty;
//        int sortIndex;
//        String valueProperty;
//
//        valueProperty = "largo";
//        previsto = "lungomare";
//        viaOttenuta = (Via) service.findNext(Via.class, valueProperty);
//        Assert.assertNotNull(viaOttenuta);
//        assertEquals(previsto, viaOttenuta.nome);
//
//        sortProperty = "ordine";
//        valueProperty = "largo";
//        sortIndex = 2;
//        previsto = "corso";
//        viaOttenuta = (Via) service.findNext(Via.class, sortProperty, sortIndex);
//        Assert.assertNotNull(viaOttenuta);
//        assertEquals(previsto, viaOttenuta.nome);
//
//        sortProperty = "nome";
//        valueProperty = "largo";
//        previsto = "lungomare";
//        viaOttenuta = (Via) service.findNext(Via.class, sortProperty, valueProperty);
//        Assert.assertNotNull(viaOttenuta);
//        assertEquals(previsto, viaOttenuta.nome);
//    }
//
//    //    @Test
//    //    @Order(27)
//    //    @DisplayName("27 - findSetQuery")
//    //    void findSetQuery() {
//    //        int offset = 0;
//    //        int limit = 12;
//    //        List<Anno> listaAnni;
//    //        Class<? extends AEntity> clazz = Anno.class;
//    //        BasicDBObject query = new BasicDBObject("ordine", new BasicDBObject("$lt", 3000));
//    //
//    //        previstoIntero = limit;
//    //        listaAnni = mongo.findSet(clazz, offset, limit, query, null);
//    //        Assert.assertNotNull(listaAnni);
//    //        Assert.assertEquals(previstoIntero, listaAnni.size());
//    //        System.out.println(VUOTA);
//    //        System.out.println("Anni (12) minori di 3000");
//    //        for (Anno anno : listaAnni) {
//    //            System.out.print(anno.anno + SEP + anno.ordine);
//    //            System.out.println();
//    //        }
//    //
//    //        BasicDBObject query2 = new BasicDBObject("ordine", new BasicDBObject("$gt", 3000));
//    //        previstoIntero = limit;
//    //        listaAnni = mongo.findSet(clazz, offset, limit, query2, null);
//    //        Assert.assertNotNull(listaAnni);
//    //        Assert.assertEquals(previstoIntero, listaAnni.size());
//    //        System.out.println(VUOTA);
//    //        System.out.println("Anni (12) maggiori di 3000");
//    //        for (Anno anno : listaAnni) {
//    //            System.out.print(anno.anno + SEP + anno.ordine);
//    //            System.out.println();
//    //        }
//    //
//    //        previstoIntero = limit;
//    //        listaAnni = mongo.findSet(clazz, offset, limit, query, null);
//    //        Assert.assertNotNull(listaAnni);
//    //        Assert.assertEquals(previstoIntero, listaAnni.size());
//    //        System.out.println(VUOTA);
//    //        System.out.println("Anni (12) minori di 3000");
//    //        for (Anno anno : listaAnni) {
//    //            System.out.print(anno.anno + SEP + anno.ordine);
//    //            System.out.println();
//    //        }
//    //    }
//    //
//    //    @Test
//    //    @Order(28)
//    //    @DisplayName("28 - findSetQuery2")
//    //    void findSetQuery2() {
//    //        int offset = 0;
//    //        int limit = 2000;
//    //        List<Via> listaVia;
//    //        Sort.Direction sortDirection;
//    //        String sortProperty = "nome";
//    //        Class<? extends AEntity> clazz = Via.class;
//    //        int totRec = service.count(clazz);
//    //        Document regexQuery;
//    //        BasicDBObject sort;
//    //        previsto = "banchi";
//    //        previsto2 = "vicolo";
//    //        previsto3 = "via";
//    //
//    //        regexQuery = new Document();
//    //        regexQuery.append("$regex", "^" + Pattern.quote("p") + ".*");
//    //        BasicDBObject query2 = new BasicDBObject(sortProperty, regexQuery);
//    //        listaVia = mongo.findSet(clazz, offset, limit, query2, null);
//    //        assertNotNull(listaVia);
//    //        printVia(listaVia, "Via iniziano con 'p'");
//    //
//    //        regexQuery = new Document();
//    //        regexQuery.append("$regex", "^" + Pattern.quote("v") + ".*");
//    //        BasicDBObject query = new BasicDBObject(sortProperty, regexQuery);
//    //        listaVia = mongo.findSet(clazz, offset, limit, query, null);
//    //        assertNotNull(listaVia);
//    //        printVia(listaVia, "Via iniziano con 'v'");
//    //
//    //        sort = new BasicDBObject(sortProperty, 1);
//    //        listaVia = mongo.findSet(clazz, offset, limit, query, sort);
//    //        assertNotNull(listaVia);
//    //        printVia(listaVia, "Via iniziano con 'v' ascendenti");
//    //
//    //        sort = new BasicDBObject(sortProperty, -1);
//    //        listaVia = mongo.findSet(clazz, offset, limit, query, sort);
//    //        assertNotNull(listaVia);
//    //        printVia(listaVia, "Via iniziano con 'v' discendenti");
//    //
//    //        sort = new BasicDBObject(sortProperty, 1);
//    //        listaVia = mongo.findSet(clazz, offset, limit, null, sort);
//    //        assertNotNull(listaVia);
//    //        assertEquals(totRec, listaVia.size());
//    //        assertEquals(previsto, listaVia.get(0).nome);
//    //        assertEquals(previsto2, listaVia.get(listaVia.size() - 1).nome);
//    //        printVia(listaVia, "Via tutte ascendenti - query nulla");
//    //
//    //        sort = new BasicDBObject(sortProperty, -1);
//    //        listaVia = mongo.findSet(clazz, offset, limit, null, sort);
//    //        assertNotNull(listaVia);
//    //        assertEquals(totRec, listaVia.size());
//    //        assertEquals(previsto2, listaVia.get(0).nome);
//    //        assertEquals(previsto, listaVia.get(listaVia.size() - 1).nome);
//    //        printVia(listaVia, "Via tutte discendenti - query nulla");
//    //
//    //        listaVia = mongo.findSet(clazz, offset, limit, null, null);
//    //        assertNotNull(listaVia);
//    //        assertEquals(totRec, listaVia.size());
//    //        assertEquals(previsto3, listaVia.get(0).nome);
//    //        printVia(listaVia, "Via tutte ascendenti- query e sort nulli");
//    //
//    //        listaVia = mongo.findSet(clazz, offset, limit);
//    //        assertNotNull(listaVia);
//    //        assertEquals(totRec, listaVia.size());
//    //        assertEquals(previsto3, listaVia.get(0).nome);
//    //        printVia(listaVia, "Via tutte ascendenti- parametri query e sort non presenti");
//    //    }
//
//
//    //    @Test
//    @Order(94)
//    @DisplayName("insertConKey")
//    void insertConKey() {
//        //        System.out.println("inserimento di una nuova entity con keyID - controlla le properties uniche");
//        //        roleQuattro = roleLogic.newEntity("Zeta");
//        //        roleQuattro.id = "mario";
//        //        roleQuattro.ordine = 88;
//        //        Assert.assertNotNull(roleQuattro);
//        //        roleCinque = (Role) roleLogic.insert(roleQuattro);
//        //        Assert.assertNotNull(roleCinque);
//        //        System.out.println(roleCinque);
//        //
//        //        roleQuattro = roleLogic.newEntity("Pippo");
//        //        roleQuattro.id = "francesco";
//        //        roleQuattro.ordine = 88;
//        //        Assert.assertNotNull(roleQuattro);
//        //        roleCinque = (Role) roleLogic.insert(roleQuattro);
//        //        Assert.assertNull(roleCinque);
//        //        service.delete(roleQuattro);
//    }
//
//
//    //    @Test
//    @Order(95)
//    @DisplayName("save")
//    void save2() {
//        //        System.out.println("modifica di una entity esistente");
//        //        roleUno.ordine = 345;
//        //        roleQuattro = (Role) service.save(roleUno);
//        //        Assert.assertNotNull(roleQuattro);
//        //
//        //        roleCinque = roleLogic.newEntity("finestra");
//        //        roleCinque.ordine = 87;
//        //        roleQuattro = (Role) service.save(roleCinque);
//        //        Assert.assertNotNull(roleQuattro);
//        //
//        //        roleCinque = roleLogic.newEntity("porta");
//        //        roleCinque.ordine = 18;
//        //        roleQuattro = (Role) service.save(roleCinque);
//        //        Assert.assertNull(roleQuattro);
//    }
//
//
//    //    @Test
//    @Order(99)
//    @DisplayName("insert")
//    void insert() {
//        //        System.out.println("inserimento di una nuova entity senza keyID - controlla le properties uniche");
//        //        roleQuattro = roleLogic.newEntity("Zeta");
//        //        roleQuattro.ordine = 88;
//        //        Assert.assertNotNull(roleQuattro);
//        //        roleCinque = (Role) roleLogic.insert(roleQuattro);
//        //        Assert.assertNotNull(roleCinque);
//        //        System.out.println(roleCinque);
//        //
//        //        roleQuattro = roleLogic.newEntity("Pippo");
//        //        roleQuattro.ordine = 88;
//        //        Assert.assertNotNull(roleQuattro);
//        //        roleCinque = (Role) roleLogic.insert(roleQuattro);
//        //        Assert.assertNull(roleCinque);
//        //        service.delete(Role.class, new Query(Criteria.where("code").is("Zeta")));
//    }
//
//
//    //    @Test
////    @Order(173)
////    @DisplayName("173 - tempiCount")
////    void tempiCount() {
////        long numRec = 0;
////        Query query = new Query();
////        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/" + "vaadflow14");
////        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
////                .applyConnectionString(connectionString)
////                .build();
////        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
////        MongoDatabase database = mongoClient.getDatabase("vaadflow14");
////        MongoCollection collection = database.getCollection("mese");
////
////        long inizio = System.currentTimeMillis();
////        for (int k = 0; k < 1000; k++) {
////            service.count(Mese.class);
////        }
////        long fine = System.currentTimeMillis();
////        System.out.println("tempo count mongoService: " + (fine - inizio));
////
////        inizio = System.currentTimeMillis();
////        for (int k = 0; k < 1000; k++) {
////            //            mongoOp.count(new Query(), Mese.class);
////        }
////        fine = System.currentTimeMillis();
////        System.out.println("tempo count mongoOP: " + (fine - inizio));
////
////        inizio = System.currentTimeMillis();
////        for (int k = 0; k < 1000; k++) {
////            //            mongoOp.count(query, Mese.class);
////        }
////        fine = System.currentTimeMillis();
////        System.out.println("tempo count mongoOP con query: " + (fine - inizio));
////
////        collection.countDocuments();
////        inizio = System.currentTimeMillis();
////        for (int k = 0; k < 1000; k++) {
////            collection.countDocuments();
////        }
////        fine = System.currentTimeMillis();
////        System.out.println("tempo count mongoClient: " + (fine - inizio));
////
////        inizio = System.currentTimeMillis();
////        for (int k = 0; k < 1000; k++) {
////            numRec = service.count(Mese.class);
////        }
////        fine = System.currentTimeMillis();
////        System.out.println("tempo count mongoService new: " + (fine - inizio) + " per " + numRec + " elementi");
////    }
//
//
//    //    @Test
//    @Order(174)
//    @DisplayName("174 - tempiFindAll")
//    void tempiFindAll() {
//        Query query = new Query();
//        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/" + "vaadflow14");
//        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .build();
//        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
//        MongoDatabase database = mongoClient.getDatabase("vaadflow14");
//        MongoCollection collection = database.getCollection("mese");
//
//        long inizio = System.currentTimeMillis();
//        for (int k = 0; k < 1000; k++) {
//            service.find(Mese.class);
//        }
//        long fine = System.currentTimeMillis();
//        System.out.println("tempo findAll mongoService: " + (fine - inizio));
//
//        inizio = System.currentTimeMillis();
//        for (int k = 0; k < 1000; k++) {
//            //            mongoOp.find(new Query(), Mese.class);
//        }
//        fine = System.currentTimeMillis();
//        System.out.println("tempo findAll mongoOP: " + (fine - inizio));
//
//        inizio = System.currentTimeMillis();
//        for (int k = 0; k < 1000; k++) {
//            //            mongoOp.find(query, Mese.class);
//        }
//        fine = System.currentTimeMillis();
//        System.out.println("tempo findAll mongoOP con query : " + (fine - inizio));
//
//        inizio = System.currentTimeMillis();
//        for (int k = 0; k < 1000; k++) {
//            collection.find().first();
//        }
//        fine = System.currentTimeMillis();
//        System.out.println("tempo findAll mongoClient: " + (fine - inizio));
//    }
//
//
//    //    @Test
//    @Order(175)
//    @DisplayName("174 - tempiFindAll con esclusione di property")
//    void tempiFindAllExclude() {
//        int cicli;
//        Query query = new Query();
//
//        cicli = 100;
//        long inizio = System.currentTimeMillis();
//        for (int k = 0; k < cicli; k++) {
//            listaBean = service.findAll(Stato.class);
//        }
//        long fine = System.currentTimeMillis();
//        Assert.assertNotNull(listaBean);
//        Assert.assertTrue(listaBean.size() == 249);
//        System.out.println(VUOTA);
//        System.out.println("tempo findAll Stato completo per " + cicli + " cicli: " + (fine - inizio));
//
//        query.fields().exclude("bandiera");
//        inizio = System.currentTimeMillis();
//        for (int k = 0; k < cicli; k++) {
//            listaBean = service.findAll(Stato.class, query);
//        }
//        fine = System.currentTimeMillis();
//        Assert.assertNotNull(listaBean);
//        Assert.assertTrue(listaBean.size() == 249);
//        System.out.println("tempo findAll Stato senza bandiere per " + cicli + " cicli: " + (fine - inizio));
//
//        cicli = 10;
//        inizio = System.currentTimeMillis();
//        for (int k = 0; k < cicli; k++) {
//            listaBean = service.findAll(Anno.class);
//        }
//        fine = System.currentTimeMillis();
//        Assert.assertNotNull(listaBean);
//        Assert.assertTrue(listaBean.size() == 3030);
//        System.out.println("tempo findAll Anno completo per " + cicli + " cicli: " + (fine - inizio));
//
//        query = new Query();
//        query.fields().include("bisestile");
//        inizio = System.currentTimeMillis();
//        for (int k = 0; k < cicli; k++) {
//            listaBean = service.findAll(Anno.class, query);
//        }
//        fine = System.currentTimeMillis();
//        Assert.assertNotNull(listaBean);
//        Assert.assertTrue(listaBean.size() == 3030);
//        System.out.println("tempo findAll Anno con solo property 'bisestile' per " + cicli + " cicli: " + (fine - inizio));
//    }
//
//
//    /**
//     * Qui passa al termine di ogni singolo test <br>
//     */
//    @AfterEach
//    void tearDown() {
//    }
//
//
//    /**
//     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
//     */
//    @AfterAll
//    void tearDownAll() {
//        cancellazioneEntitiesProvvisorie();
//    }
//
//
//    private void printLista(List<AEntity> lista) {
//        printLista(lista, VUOTA);
//    }
//
//    private void print(List<AEntity> lista, String titolo) {
//        System.out.println(titolo);
//        for (AEntity bean : lista) {
//            System.out.print(bean);
//            System.out.println();
//        }
//    }
//
//
//    private void printLista(List<AEntity> lista, String message) {
//        int cont = 0;
//
//        if (lista != null) {
//            if (textService.isValid(message)) {
//                System.out.println(VUOTA);
//                System.out.println(message);
//            }
//
//            for (AEntity entityBean : lista) {
//                System.out.print(Integer.toString(++cont) + SEP + entityBean);
//                if (entityBean instanceof Mese) {
//                    System.out.print(SEP + ((Mese) entityBean).ordine + SEP + ((Mese) entityBean).mese);
//                }
//                System.out.println();
//            }
//        }
//    }
//
//
//    /**
//     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
//     */
//    private void creazioneInizialeEntitiesProvvisorie() {
//        //        roleUno = roleLogic.newEntity("Alfa");
//        //        roleUno.ordine = 17;
//        //        service.insert(roleUno);
//        //
//        //        roleDue = roleLogic.newEntity("Beta");
//        //        roleDue.ordine = 18;
//        //        service.insert(roleDue);
//        //
//        //        roleTre = roleLogic.newEntity("Gamma");
//        //        roleTre.ordine = 19;
//        //        service.insert(roleTre);
//    }
//
//
//    /**
//     * Cancellazione finale di tutti i files/directories creati in questo test <br>
//     */
//    private void cancellazioneEntitiesProvvisorie() {
//        //        roleLogic.reset();
//    }

}
