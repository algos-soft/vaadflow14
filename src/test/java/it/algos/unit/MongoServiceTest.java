package it.algos.unit;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.mongodb.client.*;
import it.algos.test.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.packages.company.*;
import it.algos.vaadflow14.backend.packages.crono.anno.*;
import it.algos.vaadflow14.backend.packages.crono.giorno.*;
import it.algos.vaadflow14.backend.packages.crono.mese.*;
import it.algos.vaadflow14.backend.packages.security.utente.*;
import it.algos.vaadflow14.backend.service.*;
import org.bson.*;
import org.bson.conversions.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.text.*;
import java.time.*;
import java.util.*;

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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("Mongo service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MongoServiceTest extends ATest {

    protected static final String COLLEZIONE_INESISTENTE = "pomeriggio";

    protected static final String COLLEZIONE_VUOTA = "utente";

    protected static final String COLLEZIONE_VALIDA = "giorno";

    private static final String DATA_BASE_NAME = "vaadflow14";

    @InjectMocks
    protected CompanyService companyService;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    protected AIMongoService service;

    protected MongoCollection collection;

    protected Bson bSon;

    private static String[] COLLEZIONI() {
        return new String[]{"pomeriggio", "alfa", "via"};
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

        MockitoAnnotations.initMocks(companyService);
        Assertions.assertNotNull(companyService);

        companyService.text = textService;
        companyService.logger = loggerService;
        companyService.annotation = annotationService;
        companyService.reflection = reflectionService;
        companyService.mongo = mongoService;
        companyService.beanService = beanService;
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

    @Test
    @Order(3)
    @DisplayName("3 - Esistenza delle collezioni")
    void isExistsCollection() {
        System.out.println("3 - Esistenza delle collezioni dalla classe");
        clazz = null;
        ottenutoBooleano = service.isExistsCollection(clazz);
        assertFalse(ottenutoBooleano);
        printCollection(clazz, "non esiste");

        clazz = Utente.class;
        ottenutoBooleano = service.isExistsCollection(clazz);
        assertTrue(ottenutoBooleano);
        printCollection(clazz, "esiste");

        clazz = Via.class;
        ottenutoBooleano = service.isExistsCollection(clazz);
        assertTrue(ottenutoBooleano);
        printCollection(clazz, "esiste");

        System.out.println(VUOTA);
        System.out.println(VUOTA);
        System.out.println("3 - Esistenza delle collezioni dal nome");
        sorgente = COLLEZIONE_INESISTENTE;
        ottenutoBooleano = service.isExistsCollection(sorgente);
        assertFalse(ottenutoBooleano);
        printCollection(sorgente, "non esiste");

        sorgente = COLLEZIONE_VUOTA;
        ottenutoBooleano = service.isExistsCollection(sorgente);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, "esiste");

        sorgente = COLLEZIONE_VALIDA;
        ottenutoBooleano = service.isExistsCollection(sorgente);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, "esiste");
    }

    @Test
    @Order(4)
    @DisplayName("4 - Validità delle collezioni")
    void isValidCollection() {
        System.out.println("4 - Validità delle collezioni dalla classe");

        clazz = null;
        ottenutoBooleano = service.isValidCollection(clazz);
        assertFalse(ottenutoBooleano);
        printCollection(clazz, "non è valida");

        clazz = Utente.class;
        ottenutoBooleano = service.isValidCollection(clazz);
        assertFalse(ottenutoBooleano);
        printCollection(clazz, "non è valida");

        clazz = Via.class;
        ottenutoBooleano = service.isValidCollection(clazz);
        assertTrue(ottenutoBooleano);
        printCollection(clazz, "è valida");

        System.out.println(VUOTA);
        System.out.println(VUOTA);
        System.out.println("4 - Validità delle collezioni dal nome");

        sorgente = COLLEZIONE_INESISTENTE;
        ottenutoBooleano = service.isValidCollection(sorgente);
        assertFalse(ottenutoBooleano);
        printCollection(sorgente, "non è valida");

        sorgente = COLLEZIONE_VUOTA;
        ottenutoBooleano = service.isValidCollection(sorgente);
        assertFalse(ottenutoBooleano);
        printCollection(sorgente, "non è valida");

        sorgente = COLLEZIONE_VALIDA;
        ottenutoBooleano = service.isValidCollection(sorgente);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, "è valida");
    }

    @Test
    @Order(5)
    @DisplayName("5 - Check collezione vuota")
    void isEmptyCollection() {
        System.out.println("5 - Check collezione vuota dalla classe");

        clazz = null;
        ottenutoBooleano = service.isEmptyCollection(clazz);
        assertTrue(ottenutoBooleano);
        printCollection(clazz, "è vuota");

        clazz = Utente.class;
        ottenutoBooleano = service.isEmptyCollection(clazz);
        assertTrue(ottenutoBooleano);
        printCollection(clazz, "è vuota");

        clazz = Via.class;
        ottenutoBooleano = service.isEmptyCollection(clazz);
        assertFalse(ottenutoBooleano);
        printCollection(clazz, "non è vuota");

        System.out.println(VUOTA);
        System.out.println(VUOTA);
        System.out.println("5 - Check collezione vuota dal nome");

        sorgente = COLLEZIONE_INESISTENTE;
        ottenutoBooleano = service.isEmptyCollection(sorgente);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, "è vuota");

        sorgente = COLLEZIONE_VUOTA;
        ottenutoBooleano = service.isEmptyCollection(sorgente);
        assertTrue(ottenutoBooleano);
        printCollection(sorgente, "non è vuota");

        sorgente = COLLEZIONE_VALIDA;
        ottenutoBooleano = service.isEmptyCollection(sorgente);
        assertFalse(ottenutoBooleano);
        printCollection(sorgente, "non è vuota");
    }

    @Test
    @Order(6)
    @DisplayName("6 - Count totale della classe")
    void count() {
        System.out.println("6 - Count totale della collezione dalla classe");

        clazz = null;
        ottenutoIntero = service.count(clazz);
        assertTrue(ottenutoIntero == 0);
        printCount(clazz, ottenutoIntero);

        clazz = Utente.class;
        ottenutoIntero = service.count(clazz);
        assertTrue(ottenutoIntero == 0);
        printCount(clazz, ottenutoIntero);

        clazz = Giorno.class;
        previstoIntero = 366;
        ottenutoIntero = service.count(clazz);
        assertEquals(previstoIntero, ottenutoIntero);
        printCount(clazz, ottenutoIntero);

        System.out.println(VUOTA);
        System.out.println(VUOTA);
        System.out.println("6 - Count totale della collezione dal nome");

        sorgente = VUOTA;
        ottenutoIntero = service.count(sorgente);
        assertTrue(ottenutoIntero == 0);
        printCount(sorgente, ottenutoIntero);

        sorgente = COLLEZIONE_INESISTENTE;
        ottenutoIntero = service.count(sorgente);
        assertTrue(ottenutoIntero == 0);
        printCount(sorgente, ottenutoIntero);

        sorgente = COLLEZIONE_VUOTA;
        ottenutoIntero = service.count(sorgente);
        assertTrue(ottenutoIntero == 0);
        printCount(sorgente, ottenutoIntero);

        sorgente = COLLEZIONE_VALIDA;
        previstoIntero = 366;
        ottenutoIntero = service.count(sorgente);
        assertEquals(previstoIntero, ottenutoIntero);
        printCount(sorgente, ottenutoIntero);
    }

    @Test
    @Order(7)
    @DisplayName("7 - Count filtrato (bSon) dalla classe")
    void count2() {
        System.out.println("7 - Count filtrato (bSon) dalla classe");

        ottenutoIntero = service.count(clazz, bSon);
        assertTrue(ottenutoIntero == 0);
        System.out.println(String.format("Manca la classe"));

        clazz = Utente.class;
        ottenutoIntero = service.count(clazz, bSon);
        assertTrue(ottenutoIntero == 0);
        System.out.println(String.format("La collezione %s ha %s entities", clazz.getSimpleName(), ottenutoIntero));

        clazz = Mese.class;
        previstoIntero = 12;
        ottenutoIntero = service.count(clazz, bSon);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(String.format("Manca il valore di bSon; l'intera collezione %s ha %s entities", clazz.getSimpleName(), ottenutoIntero));

        clazz = Mese.class;
        sorgente = "giorni";
        sorgenteIntero = 30;
        previstoIntero = 4;
        bSon = new Document(sorgente, sorgenteIntero);
        ottenutoIntero = service.count(clazz, bSon);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(String.format("La classe %s ha %s entities filtrate", clazz.getSimpleName(), ottenutoIntero));

        System.out.println(VUOTA);
        clazz = Mese.class;
        sorgente = "giorni";
        sorgenteIntero = 31;
        previstoIntero = 7;
        bSon = new Document(sorgente, sorgenteIntero);
        ottenutoIntero = service.count(clazz, bSon);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(String.format("La classe %s ha %s entities filtrate", clazz.getSimpleName(), ottenutoIntero));

        System.out.println(VUOTA);
        clazz = Mese.class;
        sorgente = "giorni";
        sorgenteIntero = 28;
        previstoIntero = 1;
        bSon = new Document(sorgente, sorgenteIntero);
        ottenutoIntero = service.count(clazz, bSon);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(String.format("La classe %s ha %s entities filtrate", clazz.getSimpleName(), ottenutoIntero));
    }


    @Test
    @Order(8)
    @DisplayName("8 - Count filtrato (propertyName, propertyValue) dalla classe")
    void count3() {
        System.out.println("8 - Count filtrato (propertyName, propertyValue) dalla classe");

        clazz = null;
        sorgente = VUOTA;
        sorgente2 = VUOTA;
        ottenutoIntero = service.count(clazz, sorgente, sorgente2);
        assertTrue(ottenutoIntero == 0);
        System.out.println(String.format("Manca la classe"));

        clazz = Mese.class;
        sorgente = VUOTA;
        sorgente2 = VUOTA;
        previstoIntero = 12;
        ottenutoIntero = service.count(clazz, sorgente, sorgente2);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(String.format("Manca la propertyName e anche la propertyValue e restituisce tutta la collection"));

        clazz = Mese.class;
        sorgente = "giorni";
        sorgente2 = VUOTA;
        previstoIntero = 12;
        ottenutoIntero = service.count(clazz, sorgente, sorgente2);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(String.format("Manca la propertyValue e restituisce tutta la collection"));

        clazz = Mese.class;
        sorgente = VUOTA;
        sorgenteIntero = 28;
        previstoIntero = 12;
        ottenutoIntero = service.count(clazz, sorgente, sorgenteIntero);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(String.format("Manca la propertyName e restituisce tutta la collection"));

        clazz = Mese.class;
        sorgente = "giorni";
        sorgenteIntero = 31;
        previstoIntero = 7;
        ottenutoIntero = service.count(clazz, sorgente, sorgenteIntero);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(String.format("Manca la propertyName e restituisce tutta la collection"));
    }


    @Test
    @Order(9)
    @DisplayName("9 - Save base di una entity")
    void save() {
        System.out.println("9 - Save base di una entity");
        Company company = null;
        Company companyReborn = null;

        //--costruisco una entityBean
        sorgente = "doppio";
        sorgente2 = "Porta yyyy Valori Associato";
        company = companyService.newEntity(sorgente, sorgente2, VUOTA, VUOTA);
        company.setCreazione(LocalDateTime.now());
        assertNotNull(company);

        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            mapper.setDateFormat(format);
            json = mapper.writeValueAsString(company);
            System.out.println(json);
        } catch (JsonProcessingException unErrore) {
            System.out.println(unErrore);
        }

//        collection.insertOne(Document.parse(json));


        //--salvo la entityBean
//        try {
//            //            ((MongoService)service).save(company);
//            companyService.save(company, null);
//        } catch (AMongoException unErrore) {
//            System.out.println(unErrore);
//        }
    }

    @Test
    @Order(10)
    @DisplayName("10 - Trova singola entity by id")
    void findById() {
        System.out.println("10 - Trova singola entity by id");

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
        entityBean = service.findById(clazz, sorgente);
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
        entityBean = service.findByKey(clazz, sorgente, sorgente2);
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
        entityBean = service.findByKey(clazz, sorgente);
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
        entityBean = service.findById(clazz, entityBean.getId());
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
        entityBean = service.findByKey(clazz, sorgente);
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

        clazz = Via.class;
        sorgente = "ordine";
        entityBean = service.findByKey(clazz, sorgente);

        //--il database mongoDB potrebbe anche essere vuoto
        if (service.isExistsCollection(clazz.getSimpleName().toLowerCase())) {
            ottenutoIntero = ((MongoService) service).getNewOrder(clazz, sorgente);
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

    protected void printCollection(final Class clazz, final String status) {
        System.out.println(String.format("La collezione '%s' %s", clazz != null ? clazz.getSimpleName() : VUOTA, status));
    }

    protected void printCollection(final String collectionName, final String status) {
        System.out.println(String.format("La collezione '%s' %s", collectionName, status));
    }

    protected void printCount(final Class clazz, final int records) {
        System.out.println(String.format("La collezione '%s' contiene %s records (entities)", clazz != null ? clazz.getSimpleName() : VUOTA, records));
    }

    protected void printCount(final String collectionName, final int records) {
        System.out.println(String.format("La collezione '%s' contiene %s records (entities)", collectionName, records));
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