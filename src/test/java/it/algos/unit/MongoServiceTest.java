package it.algos.unit;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.mongodb.client.*;
import it.algos.simple.backend.packages.*;
import it.algos.simple.backend.packages.bolla.*;
import it.algos.test.*;
import it.algos.vaadflow14.backend.application.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.packages.company.*;
import it.algos.vaadflow14.backend.packages.crono.anno.*;
import it.algos.vaadflow14.backend.packages.crono.giorno.*;
import it.algos.vaadflow14.backend.packages.crono.mese.*;
import it.algos.vaadflow14.backend.packages.geografica.regione.*;
import it.algos.vaadflow14.backend.packages.security.utente.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.backend.wrapper.*;
import org.bson.*;
import org.bson.conversions.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.*;

import java.io.*;
import java.text.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 30-apr-2021
 * Time: 07:51
 * <p>
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("Mongo Service (senza mongoOp)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MongoServiceTest extends ATest {

    protected static final String COLLEZIONE_INESISTENTE = "pomeriggio";

    protected static final String COLLEZIONE_VUOTA = "utente";

    protected static final String COLLEZIONE_VALIDA = "giorno";

    private static final String DATA_BASE_NAME = "vaadflow14";

    //    static Stream<Class> classi = Stream.of(Via.class, Bolla.class, AIType.class, GammaService.class, Utente.class, LogicList.class);

    /**
     * Inietta da Spring
     */
    @Autowired
    public MongoTemplate mongoOp;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    //    @InjectMocks
    protected AIMongoService service;

    protected MongoCollection collection;

    protected Bson bSon;

    protected Document doc;


    private static Class[] CLAZZ() {
        return new Class[]{null, Via.class, Bolla.class, AIType.class, GammaService.class, Utente.class, LogicList.class};
    }

    private static String[] NOMI() {
        return new String[]{VUOTA, "pomeriggio", Via.class.getSimpleName(), Via.class.getCanonicalName(), "via", "Via", Via.class.getSimpleName().toLowerCase(Locale.ROOT), "Utente", "LogicList"};
    }

    private static Stream<Arguments> CLAZZ_COUNT() {
        return Stream.of(
                Arguments.of(null, 0, false),
                Arguments.of(LogicList.class, 0, false),
                Arguments.of(Utente.class, 0, false),
                Arguments.of(Bolla.class, 5, false),
                Arguments.of(Mese.class, 12, true),
                Arguments.of(Giorno.class, 366, true),
                Arguments.of(Via.class, 26, false)
        );
    }

    private static Stream<Arguments> CLAZZ_PROPERTY() {
        return Stream.of(
                Arguments.of((Class) null, VUOTA, null, 0),
                Arguments.of(Utente.class, VUOTA, null, 0),
                Arguments.of(Mese.class, VUOTA, null, 0),
                Arguments.of(Mese.class, "manca", null, 0),
                Arguments.of(Mese.class, "manca", 31, 0),
                Arguments.of(Mese.class, "mese", "pippoz", 0),
                Arguments.of(Mese.class, "mese", null, 0),
                Arguments.of(Mese.class, "mese", VUOTA, 12),
                Arguments.of(Mese.class, "giorni", 31, 7),
                Arguments.of(Mese.class, "giorni", 30, 4),
                Arguments.of(Mese.class, "giorni", 28, 1),
                Arguments.of(Via.class, "belzebù", "piazza", 0),
                Arguments.of(Via.class, "nome", "belzebù", 0),
                Arguments.of(Via.class, "nome", "piazza", 1)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    void setUpIniziale() {
        super.setUpStartUp();

        //--reindirizzo l'istanza della superclasse
        service = mongoService;
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    void setUpEach() {
        super.setUp();

        collection = null;
        bSon = null;
        doc = null;
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


    @ParameterizedTest
    @MethodSource(value = "CLAZZ")
    @NullSource
    @Order(2)
    @DisplayName("2 - Collezioni per clazz")
    /*
      2 - Collezioni per clazz
      Controlla l'esistenza della collezione (dall'elenco di tutte le condizioni esistenti nel mongoDB)
      Recupera la collezione
      Controlla l'esistenza della collezione (dal numero di entities presenti)
      Controlla se la collezione è vuota (dal numero di entities presenti)
     */
    void collectionClazz(Class clazz) {
        try {
            ottenutoBooleano = service.isExistsCollection(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCollection(clazz, ottenutoBooleano);
        collection = service.getCollection(clazz);
        printCollection(clazz, collection);
        try {
            ottenutoBooleano = service.isValidCollection(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCollectionValida(clazz, ottenutoBooleano);
        try {
            ottenutoBooleano = service.isEmptyCollection(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCollectionVuota(clazz, ottenutoBooleano);

        System.out.println(VUOTA);
    }

    //    @ParameterizedTest
    //    @MethodSource(value = "NOMI")
    //    @EmptySource
    //    @Order(3)
    //    @DisplayName("3 - Collezioni per nome")
    //    /*
    //      3 - Collezioni per nome
    //      Controlla l'esistenza della collezione (dall'elenco di tutte le condizioni esistenti nel mongoDB)
    //      Recupera la collezione
    //      Controlla l'esistenza della collezione (dal numero di entities presenti)
    //      Controlla se la collezione è vuota (dal numero di entities presenti)
    //     */
    //    void collectionName(String nome) {
    //        try {
    //            ottenutoBooleano = service.isExistsCollection(nome);
    //        } catch (AlgosException unErrore) {
    //            printError(unErrore);
    //        }
    //        printCollection(nome, ottenutoBooleano);
    //        collection = service.getCollection(nome);
    //        printCollection(nome, collection);
    //        try {
    //            ottenutoBooleano = service.isValidCollection(nome);
    //        } catch (AlgosException unErrore) {
    //            printError(unErrore);
    //        }
    //        printCollectionValida(nome, ottenutoBooleano);
    //        try {
    //            ottenutoBooleano = service.isEmptyCollection(nome);
    //        } catch (AlgosException unErrore) {
    //            printError(unErrore);
    //        }
    //        printCollectionVuota(nome, ottenutoBooleano);
    //
    //        System.out.println(VUOTA);
    //    }

    @ParameterizedTest
    @MethodSource(value = "CLAZZ_COUNT")
    @Order(4)
    @DisplayName("4 - Count totale per clazz")
    /*
      4 - Count totale per clazz
      metodo semplice per l'intera collection
      rimanda al metodo base collection.countDocuments();
      non usa ne gson ne spring
     */
    void countClazz(final Class clazz, final int previstoIntero, final boolean risultatoEsatto) {
        try {
            ottenutoIntero = service.count(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCount(clazz, previstoIntero, ottenutoIntero, risultatoEsatto);
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_PROPERTY")
    @Order(5)
    @DisplayName("5 - Count spring filtrato (propertyName, propertyValue) per clazz")
    /*
      5 - Count filtrato (propertyName, propertyValue) per clazz
      FlowVar.typeSerializing = AETypeSerializing.spring
    */
    void countPropertySpring(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero) {
        FlowVar.typeSerializing = AETypeSerializing.spring;
        try {
            ottenutoIntero = service.count(clazz, propertyName, propertyValue);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCount(clazz, propertyName, propertyValue, previstoIntero, ottenutoIntero);
        System.out.println(VUOTA);
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_PROPERTY")
    @Order(6)
    @DisplayName("6 - Count gson filtrato (propertyName, propertyValue) per clazz")
    /*
      6 - Count filtrato (propertyName, propertyValue) per clazz
      FlowVar.typeSerializing = AETypeSerializing.gson
    */
    void countPropertyGson(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero) {
        FlowVar.typeSerializing = AETypeSerializing.gson;
        try {
            ottenutoIntero = service.count(clazz, propertyName, propertyValue);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCount(clazz, propertyName, propertyValue, previstoIntero, ottenutoIntero);
        System.out.println(VUOTA);
    }

    @Test
    @Order(7)
    @DisplayName("7 - Count spring filtrato (AFiltro) singolo")
    void countFiltroSpring() {
        System.out.println("7 - Count spring filtrato (AFiltro) singolo");
        FlowVar.typeSerializing = AETypeSerializing.spring;
        this.count78();
    }


    @Test
    @Order(8)
    @DisplayName("8 - Count gson filtrato (AFiltro) singolo")
    void countFiltroGson() {
        System.out.println("8 - Count gson filtrato (AFiltro) singolo");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        this.count78();
    }


    void count78() {
        AFiltro filtro;
        Map<String, AFiltro> mappaFiltri;

        clazz = VIA_ENTITY_CLASS;
        String filtroContains = "co";
        filtro = AFiltro.contains(NAME_NOME, filtroContains);
        mappaFiltri = Collections.singletonMap(filtro.getCriteria().getKey(), filtro);
        previstoIntero = 6;
        try {
            ottenutoIntero = service.count(clazz, mappaFiltri);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCount(clazz, filtro, previstoIntero, ottenutoIntero);
        System.out.println(VUOTA);
        assertEquals(previstoIntero, ottenutoIntero);

        clazz = VIA_ENTITY_CLASS;
        String filtroStart = "v";
        filtro = AFiltro.start(NAME_NOME, filtroStart);
        mappaFiltri = Collections.singletonMap(filtro.getCriteria().getKey(), filtro);
        previstoIntero = 4;
        try {
            ottenutoIntero = service.count(clazz, mappaFiltri);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCount(clazz, filtro, previstoIntero, ottenutoIntero);
        assertEquals(previstoIntero, ottenutoIntero);

        clazz = VIA_ENTITY_CLASS;
        filtroStart = "c";
        filtro = AFiltro.start(NAME_NOME, filtroStart);
        mappaFiltri = Collections.singletonMap(filtro.getCriteria().getKey(), filtro);
        previstoIntero = 3;
        try {
            ottenutoIntero = service.count(clazz, mappaFiltri);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCount(clazz, filtro, previstoIntero, ottenutoIntero);
        System.out.println("(Esempio volutamente sbagliato)");
        assertNotEquals(previstoIntero, ottenutoIntero);
    }

    @Test
    @Order(9)
    @DisplayName("9 - Count spring filtrato (mappaFiltri)")
    void countFiltroMappaSpring() {
        System.out.println("9 - Count spring filtrato (mappaFiltri)");
        FlowVar.typeSerializing = AETypeSerializing.spring;
        this.count910();
    }

    @Test
    @Order(10)
    @DisplayName("10 - Count gson filtrato (mappaFiltri)")
    void countFiltroMappaGson() {
        System.out.println("10 - Count gson filtrato (mappaFiltri)");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        this.count910();
    }

    void count910() {
        AFiltro filtro;
        clazz = VIA_ENTITY_CLASS;

        String filtroStart = "v";
        filtro = AFiltro.start(NAME_NOME, filtroStart);
        mappaFiltri.put("a", filtro);

        String filtroContains = "co";
        AFiltro filtro2 = AFiltro.contains(NAME_NOME, filtroContains);
        mappaFiltri.put("b", filtro2);
        previstoIntero = 2;
        try {
            ottenutoIntero = service.count(clazz, mappaFiltri);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCount(clazz, mappaFiltri, previstoIntero, ottenutoIntero);
    }


    @Test
    @Order(12)
    @DisplayName("12 - Crea un Doc (spring) da mongoDb con keyId")
    void findDocById() {
        System.out.println("12 - Crea un Doc (spring) da mongoDb con keyId");
        FlowVar.typeSerializing = AETypeSerializing.spring;

        System.out.println(VUOTA);
        clazz = FlowCost.class;
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);

        System.out.println(VUOTA);
        clazz = Via.class;
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);

        System.out.println(VUOTA);
        clazz = Via.class;
        sorgente = "sbagliata";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);

        System.out.println(VUOTA);
        clazz = Via.class;
        sorgente = "piazza";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
            System.out.println(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);

        System.out.println(VUOTA);
        clazz = Regione.class;
        sorgente = "calabria";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
            System.out.println(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);
    }


    @Test
    @Order(13)
    @DisplayName("13 - Crea un Doc (gson) da mongoDb con keyId")
    void findDocById2() {
        System.out.println("13 - Crea un Doc (gson) da mongoDb con keyId");
        FlowVar.typeSerializing = AETypeSerializing.gson;

        System.out.println(VUOTA);
        clazz = FlowCost.class;
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);

        System.out.println(VUOTA);
        clazz = Via.class;
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);

        System.out.println(VUOTA);
        clazz = Via.class;
        sorgente = "sbagliata";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);

        System.out.println(VUOTA);
        clazz = Via.class;
        sorgente = "piazza";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
            System.out.println(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);

        System.out.println(VUOTA);
        clazz = Regione.class;
        sorgente = "calabria";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
            System.out.println(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);
    }


    @Test
    @Order(14)
    @DisplayName("14 - Crea una entity (spring) da un Doc con keyId")
    void creaByDoc() {
        System.out.println("14 - Crea una entity (spring) da un Doc con keyId");
        FlowVar.typeSerializing = AETypeSerializing.spring;

        System.out.println(VUOTA);
        clazz = null;
        entityBean = null;
        try {
            entityBean = service.creaByDoc(clazz, doc);
            assertNull(entityBean);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        System.out.println(VUOTA);
        clazz = Via.class;
        entityBean = null;
        try {
            entityBean = service.creaByDoc(clazz, doc);
            assertNull(entityBean);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        System.out.println(VUOTA);
        clazz = Via.class;
        sorgente = "piazza";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);
        System.out.println(VUOTA);
        entityBean = null;
        try {
            entityBean = service.creaByDoc(clazz, doc);
            assertNotNull(entityBean);
            assertNotNull(entityBean.id);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", entityBean.id, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        System.out.println(VUOTA);
        clazz = Mese.class;
        sorgente = "marzo";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);
        System.out.println(VUOTA);
        entityBean = null;
        try {
            entityBean = service.creaByDoc(clazz, doc);
            assertNotNull(entityBean);
            assertNotNull(entityBean.id);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", entityBean.id, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        System.out.println(VUOTA);
        clazz = Giorno.class;
        sorgente = "2agosto";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);
        System.out.println(VUOTA);
        entityBean = null;
        try {
            entityBean = service.creaByDoc(clazz, doc);
            assertNotNull(entityBean);
            assertNotNull(entityBean.id);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", entityBean.id, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }


    @Test
    @Order(15)
    @DisplayName("15 - Crea una entity (gson) da un Doc con keyId")
    void creaByDoc2() {
        System.out.println("15 - Crea una entity (gson) da un Doc con keyId");
        FlowVar.typeSerializing = AETypeSerializing.gson;

        System.out.println(VUOTA);
        clazz = Via.class;
        sorgente = "piazza";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);
        System.out.println(VUOTA);
        entityBean = null;
        try {
            entityBean = service.creaByDoc(clazz, doc);
            assertNotNull(entityBean);
            assertNotNull(entityBean.id);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", entityBean.id, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        System.out.println(VUOTA);
        clazz = Mese.class;
        sorgente = "marzo";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);
        System.out.println(VUOTA);
        entityBean = null;
        try {
            entityBean = service.creaByDoc(clazz, doc);
            assertNotNull(entityBean);
            assertNotNull(entityBean.id);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", entityBean.id, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        System.out.println(VUOTA);
        clazz = Giorno.class;
        sorgente = "2agosto";
        doc = null;
        try {
            doc = service.findDocById(clazz, sorgente);
            assertNotNull(doc);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printDoc(doc);
        System.out.println(VUOTA);
        entityBean = null;
        try {
            entityBean = service.creaByDoc(clazz, doc);
            assertNotNull(entityBean);
            assertNotNull(entityBean.id);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", entityBean.id, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }

    @Test
    @Order(16)
    @DisplayName("16 - Crea una entity (gson) da mongoDb con keyId")
    void crea() {
        System.out.println("16 - Crea una entity (gson) da mongoDb con keyId");
        FlowVar.typeSerializing = AETypeSerializing.gson;

        System.out.println(VUOTA);
        clazz = Via.class;
        sorgente = "piazza";
        entityBean = null;
        try {
            entityBean = service.crea(clazz, sorgente);
            assertNotNull(entityBean);
            assertNotNull(entityBean.id);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", entityBean.id, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        System.out.println(VUOTA);
        clazz = Giorno.class;
        sorgente = "2agosto";
        entityBean = null;
        try {
            entityBean = service.crea(clazz, sorgente);
            assertNotNull(entityBean);
            assertNotNull(entityBean.id);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", entityBean.id, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        System.out.println(VUOTA);
        clazz = Regione.class;
        sorgente = "calabria";
        entityBean = null;
        try {
            entityBean = service.crea(clazz, sorgente);
            assertNotNull(entityBean);
            assertNotNull(entityBean.id);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", entityBean.id, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }


    @Test
    @Order(18)
    @DisplayName("18 - Save base (gson) di una entity")
    void save() {
        System.out.println("18 - Save base (gson) di una entity");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        Company company = null;
        Company companyReborn = null;

        //--costruisco una entityBean
        sorgente = "doppia";
        sorgente2 = "Porta Valori Associato";
        company = companyService.newEntity(sorgente, sorgente2, VUOTA, VUOTA);
        company.setCreazione(LocalDateTime.now());
        assertNotNull(company);

        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            mapper.setDateFormat(format);
            json = mapper.writeValueAsString(company);
            System.out.println(json);
        } catch (JsonProcessingException unErrore) {
            System.out.println(unErrore);
            assertNotNull(null);
        }

        //        collection.insertOne(Document.parse(json));

        //        --salvo la entityBean
        try {
            //            ((MongoService) service).save(company);
            companyService.save(company);
        } catch (AMongoException unErrore) {
            //            System.out.println(unErrore);
            loggerService.info(unErrore.getMessage());
        }
    }

    //    @Test
    @Order(13)
    @DisplayName("13 - Trova singola entity by id")
    void findById() {
        System.out.println("13 - Trova singola entity by id");

        //            sorgente = "104";
        //            clazz = Anno.class;
        //            entityBean = service.findById(clazz, sorgente);
        //            assertNotNull(entityBean);
        //            System.out.println(VUOTA);
        //            System.out.println(String.format("Recupero di un bean di classe %s", clazz.getSimpleName()));
        //            System.out.println(entityBean);
        //
        //            sorgente = "via";
        //            clazz = Via.class;
        //            entityBean = service.findById(clazz, sorgente);
        //            assertNotNull(entityBean);
        //            System.out.println(VUOTA);
        //            System.out.println(String.format("Recupero di un bean di classe %s", clazz.getSimpleName()));
        //            System.out.println(entityBean);

        sorgente = "terzo";
        clazz = Company.class;
        try {
            entityBean = service.findById(clazz, sorgente);
        } catch (Exception unErrore) {
        }
        assertNotNull(entityBean);
        System.out.println(VUOTA);
        System.out.println(String.format("Recupero di un bean di classe %s", clazz.getSimpleName()));
        System.out.println(entityBean);
    }


    //    @Test
    @Order(6)
    @DisplayName("6 - Trova singola entity by key")
    void findByKey() {
        System.out.println("6 - Trova singola entity by key");

        clazz = Giorno.class;
        sorgente = "titolo";
        sorgente2 = "4 novembre";
        try {
            entityBean = service.findByProperty(clazz, sorgente, sorgente2);
        } catch (AMongoException unErrore) {
        }
        assertNotNull(entityBean);
        System.out.println(VUOTA);
        System.out.println(String.format("EntityBean di classe %s recuperato dal valore '%s' della property '%s'", clazz.getSimpleName(), sorgente2, sorgente));
        System.out.println(entityBean);
    }


    //    @Test
    @Order(7)
    @DisplayName("7 - Save di una entity")
    void save2() {
        System.out.println("7 - Save di una entity");
        int originario;
        int daModificare;
        int modificato;
        int finale;
        String jsonInString;

        //--leggo una entityBean e memorizzo una property
        clazz = Via.class;
        sorgente = "corte";
        try {
            entityBean = service.findByKey(clazz, sorgente);
        } catch (AMongoException unErrore) {
        }
        assertNotNull(entityBean);
        originario = ((Via) entityBean).getOrdine();
        System.out.println(VUOTA);
        System.out.println(String.format("Nella entity originale [%s] il valore di 'ordine' è [%s]", sorgente, originario));

        //--modifico la entityBean
        daModificare = mathService.random();
        ((Via) entityBean).setOrdine(daModificare);

        //--registro la entityBean modificata
        try {
            //            jsonInString = gSonService.legge(entityBean);
            //            System.out.println(String.format("Stringa in formato json -> %s", jsonInString));
            ((MongoService) service).save(entityBean);
        } catch (AMongoException unErrore) {
            System.out.println(unErrore);
        }

        //--ri-leggo la entityBean (dal vecchio id) controllo la property per vedere se è stata modificata e registrata
        try {
            entityBean = service.findById(clazz, entityBean.getId());
        } catch (Exception unErrore) {
        }
        modificato = ((Via) entityBean).getOrdine();
        assertEquals(daModificare, modificato);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella entity modificata [%s] il valore di 'ordine' è [%s]", sorgente, modificato));

        //--ri-modifico la entityBean
        ((Via) entityBean).setOrdine(originario);

        //--ri-registro la entityBean come in origine
        try {
            ((MongoService) service).save(entityBean);
        } catch (AMongoException unErrore) {
            System.out.println(unErrore);
        }

        //--ri-leggo la entityBean e ri-controllo la property
        try {
            entityBean = service.findByKey(clazz, sorgente);
        } catch (AMongoException unErrore) {
        }
        assertNotNull(entityBean);
        finale = ((Via) entityBean).getOrdine();
        assertEquals(originario, finale);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella entity ricostruita [%s] il valore di 'ordine' è [%s], uguale al valore originario [%s]", sorgente, finale, originario));

    }

    //    @Test
    @Order(8)
    @DisplayName("8 - Trova l'ordine successivo")
    void sss() {
        System.out.println("8 - Trova l'ordine successivo\"");
        boolean isExistsCollection = false;

        clazz = Via.class;
        sorgente = "ordine";
        try {
            entityBean = service.findByKey(clazz, sorgente);
        } catch (AMongoException unErrore) {
        }

        //--il database mongoDB potrebbe anche essere vuoto
        try {
            isExistsCollection = service.isExistsCollection(clazz);
        } catch (AlgosException unErrore) {
            System.out.println(unErrore);
        }

        if (isExistsCollection) {
            try {
                ottenutoIntero = service.getNewOrder(clazz, sorgente);
            } catch (AMongoException unErrore) {
            }
            System.out.println(String.format("Successivo ordine %d", ottenutoIntero));
        }
        else {
            System.out.println("Il database 'via' è vuoto");
        }
    }

    //    @Test
    @Order(5)
    @DisplayName("5 - Lista di tutte le entities")
    void fetch() {
        System.out.println("5 - Lista di tutte le entities");

        sorgenteClasse = Via.class;
        previstoIntero = 26;
        inizio = System.currentTimeMillis();
        try {
            listaBean = ((MongoService) service).fetch(sorgenteClasse);
        } catch (Exception unErrore) {
            loggerService.error(unErrore, this.getClass(), "fetch");
        }
        assertNotNull(listaBean);
        assertEquals(previstoIntero, listaBean.size());
        System.out.println(String.format("Nella collezione '%s' ci sono %s entities recuperate in %s", sorgenteClasse.getSimpleName(), textService.format(listaBean.size()), dateService.deltaTextEsatto(inizio)));

        sorgenteClasse = Giorno.class;
        previstoIntero = 366;
        inizio = System.currentTimeMillis();
        try {
            listaBean = ((MongoService) service).fetch(sorgenteClasse);
        } catch (AQueryException unErrore) {
            loggerService.error(unErrore, this.getClass(), "fetch");
        } catch (AMongoException unErrore) {
            loggerService.error(unErrore, this.getClass(), "fetch");
        }
        assertNotNull(listaBean);
        assertEquals(previstoIntero, listaBean.size());
        System.out.println(String.format("Nella collezione '%s' ci sono %s entities recuperate in %s", sorgenteClasse.getSimpleName(), textService.format(listaBean.size()), dateService.deltaTextEsatto(inizio)));

        sorgenteClasse = Anno.class;
        previstoIntero = 3030;
        inizio = System.currentTimeMillis();
        try {
            listaBean = ((MongoService) service).fetch(sorgenteClasse);
        } catch (AQueryException unErrore) {
            loggerService.error(unErrore, this.getClass(), "fetch");
        } catch (AMongoException unErrore) {
            loggerService.error(unErrore, this.getClass(), "fetch");
        }
        assertNotNull(listaBean);
        assertEquals(previstoIntero, listaBean.size());
        System.out.println(String.format("Nella collezione '%s' ci sono %s entities recuperate in %s", sorgenteClasse.getSimpleName(), textService.format(listaBean.size()), dateService.deltaTextEsatto(inizio)));
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


    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

    void printCount(final Class clazz, final int size, final Document bSon) {
        printCount(clazz.getSimpleName(), size, bSon);
    }


    void printCount(final String simpleName, final int size, final Document bSon) {
        String key = (String) bSon.keySet().toArray()[0];
        int value = (int) bSon.values().toArray()[0];
        printCount(clazz.getSimpleName(), size, key, value);
    }

    void printCount(final Class clazz, final int size, final String property, final Object value) {
        printCount(clazz.getSimpleName(), size, property, value);
    }

    void printCount(final String simpleName, final int size, final String property, final Object value) {
        System.out.println(String.format(String.format("La classe %s ha %s entities filtrate con %s=%s", simpleName, size, property, value)));
        System.out.println(VUOTA);
    }

    void printDoc(final Document doc) {
        String key;
        Object value;
        System.out.println(VUOTA);

        if (doc != null) {
            System.out.println(String.format("Il documento contiene %s parametri chiave=valore, più keyID e classe", doc.size() - 2));
            System.out.println(VUOTA);
            for (Map.Entry<String, Object> mappa : doc.entrySet()) {
                key = mappa.getKey();
                value = mappa.getValue();
                System.out.println(String.format("%s: %s", key, value));
            }
        }
        else {
            System.out.println(String.format("Nessun documento trovato"));
        }
    }

    protected void printCount(final Class clazz, final int previstoIntero, final int ottenutoIntero, final boolean risultatoEsatto) {
        if (clazz == null) {
            System.out.println("Manca la entityClazz");
            return;
        }
        if (ottenutoIntero == previstoIntero) {
            if (risultatoEsatto) {
                System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono esattamente quelli previsti (obbligatori)", clazz.getSimpleName(), ottenutoIntero));
            }
            else {
                System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono uguali a quelli indicativamente previsti (facoltativi)", clazz.getSimpleName(), ottenutoIntero));
            }
        }
        else {
            if (ottenutoIntero > previstoIntero) {
                if (risultatoEsatto) {
                    System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono più dei %s previsti e non va bene", clazz.getSimpleName(), ottenutoIntero, previstoIntero));
                }
                else {
                    System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono più dei %s indicativamente previsti", clazz.getSimpleName(), ottenutoIntero, previstoIntero));
                }
            }
            else {
                if (risultatoEsatto) {
                    System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono meno dei %s previsti e non va bene", clazz.getSimpleName(), ottenutoIntero, previstoIntero));
                }
                else {
                    System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono meno dei %s indicativamente previsti", clazz.getSimpleName(), ottenutoIntero, previstoIntero));
                }
            }
        }

        if (risultatoEsatto) {
            assertEquals(previstoIntero, ottenutoIntero);
        }
    }


    protected void printCount(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero, final int ottenutoIntero) {
        String clazzName;
        if (clazz == null) {
            System.out.println(String.format("Manca la entityClazz"));
            return;
        }
        else {
            clazzName = clazz.getSimpleName();
        }

        if (ottenutoIntero == previstoIntero) {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) filtrati con %s=%s che sono quelli previsti", clazzName, ottenutoIntero, propertyName, propertyValue));
        }
        else {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) filtrati con %s=%s che non sono i %s previsti", clazzName, ottenutoIntero, propertyName, propertyValue, previstoIntero));
        }
    }

    protected void printCount(final Class clazz, AFiltro filtro, final int previstoIntero, final int ottenutoIntero) {
        String clazzName;
        String key = filtro.getCriteria().getCriteriaObject().keySet().toString();
        String value = filtro.getCriteria().getCriteriaObject().values().toString();

        if (clazz == null) {
            System.out.println(String.format("Manca la entityClazz"));
            return;
        }
        else {
            clazzName = clazz.getSimpleName();
        }

        if (ottenutoIntero == previstoIntero) {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) filtrati con %s = %s che sono quelli previsti", clazzName, ottenutoIntero, key, value));
        }
        else {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) filtrati con %s = %s che non sono i %s previsti", clazzName, ottenutoIntero, key, value, previstoIntero));
        }
    }

    protected void printCount(final Class clazz, Map<String, AFiltro> mappaFiltri, final int previstoIntero, final int ottenutoIntero) {
        String clazzName;
        AFiltro filtro;
        String property;
        String value;
        if (clazz == null) {
            System.out.println(String.format("Manca la entityClazz"));
            return;
        }
        else {
            clazzName = clazz.getSimpleName();
        }

        if (ottenutoIntero == previstoIntero) {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) che sono quelli previsti", clazzName, ottenutoIntero));
            for (String key : mappaFiltri.keySet()) {
                filtro = mappaFiltri.get(key);
                property = filtro.getCriteria().getCriteriaObject().keySet().toString();
                value = filtro.getCriteria().getCriteriaObject().values().toString();
                System.out.println(String.format("Filtro %s = %s", property, value));
            }
        }
        else {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) che non sono i %s previsti", clazzName, ottenutoIntero, previstoIntero));
            for (String key : mappaFiltri.keySet()) {
                filtro = mappaFiltri.get(key);
                property = filtro.getCriteria().getCriteriaObject().keySet().toString();
                value = filtro.getCriteria().getCriteriaObject().values().toString();
                System.out.println(String.format("Filtro %s = %s", property, value));
            }
        }
    }

}
