package it.algos.unit;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.mongodb.client.*;
import it.algos.test.*;
import it.algos.vaadflow14.backend.application.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.packages.company.*;
import it.algos.vaadflow14.backend.packages.crono.anno.*;
import it.algos.vaadflow14.backend.packages.crono.giorno.*;
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
@DisplayName("Mongo Service (senza mongoOp)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MongoServiceTest extends MongoTest {

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

        //--property statica utilizzata nel test
        FlowVar.typeSerializing = AETypeSerializing.spring;
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
    @DisplayName("2 - Collezioni del database")
    /*
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
            ottenutoBooleano = !service.isValidCollection(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCollectionVuota(clazz, ottenutoBooleano);

        System.out.println(VUOTA);
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_COUNT")
    @Order(3)
    @DisplayName("3 - Count totale (gson/spring) per clazz")
    /*
      metodo semplice per l'intera collection
      rimanda al metodo base collection.countDocuments();
      non usa ne gson ne spring
     */
    void countClazz(final Class clazz, final int previstoIntero, final boolean risultatoEsatto) {
        System.out.println("3 - Count totale (gson/spring) per clazz");
        String message = String.format("Count totale di %s", clazz != null ? clazz.getSimpleName() : "(manca la classe)");
        System.out.println(message);

        ottenutoIntero = 0;
        try {
            ottenutoIntero = service.count(clazz);
            System.out.println(String.format("Risultato -> %d", ottenutoIntero));
            System.out.println(VUOTA);
            printCount(clazz, previstoIntero, ottenutoIntero, risultatoEsatto);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_PROPERTY")
    @Order(4)
    @DisplayName("4 - Count gson filtrato (propertyName, propertyValue)")
    void countPropertyGson(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero) {
        System.out.println("4 - Count gson filtrato (propertyName, propertyValue)");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        count45(clazz, propertyName, propertyValue, previstoIntero);
    }

    @ParameterizedTest
    @MethodSource(value = "CLAZZ_PROPERTY")
    @Order(5)
    @DisplayName("5 - Count spring filtrato (propertyName, propertyValue)")
    void countPropertySpring(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero) {
        System.out.println("5 - Count spring filtrato (propertyName, propertyValue)");
        FlowVar.typeSerializing = AETypeSerializing.spring;
        count45(clazz, propertyName, propertyValue, previstoIntero);
    }

    private void count45(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero) {
        String message = String.format("Count filtrato di %s", clazz != null ? clazz.getSimpleName() : "(manca la classe)");
        System.out.println(message);
        message = String.format("Query -> %s=%s", propertyName, propertyValue);
        System.out.println(message);

        ottenutoIntero = 0;
        try {
            ottenutoIntero = service.count(clazz, propertyName, propertyValue);
            System.out.println(String.format("Risultato = %d", ottenutoIntero));
            System.out.println(VUOTA);
            printCount(clazz, propertyName, propertyValue, previstoIntero, ottenutoIntero);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        if (flagRisultatiEsattiObbligatori) {
            assertEquals(previstoIntero, ottenutoIntero);
        }
        System.out.println(VUOTA);
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FILTER")
    @Order(6)
    @DisplayName("6 - Count gson filtrato (WrapFiltro)")
    void countWrapFiltroGson(final Class clazz, final AETypeFilter filter, final String propertyName, final String propertyValue, final int previstoIntero) {
        System.out.println("6 - Count gson filtrato (WrapFiltro)");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        count67(clazz, filter, propertyName, propertyValue, previstoIntero);
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FILTER")
    @Order(7)
    @DisplayName("7 - Count spring filtrato (WrapFiltro)")
    void countWrapFiltroSpring(final Class clazz, final AETypeFilter filter, final String propertyName, final String propertyValue, final int previstoIntero) {
        System.out.println("7 - Count spring filtrato (WrapFiltro)");
        FlowVar.typeSerializing = AETypeSerializing.spring;
        count67(clazz, filter, propertyName, propertyValue, previstoIntero);
    }


    private void count67(final Class clazz, final AETypeFilter filter, final String propertyName, final String propertyValue, final int previstoIntero) {
        String message = String.format("Count filtrato di %s", clazz != null ? clazz.getSimpleName() : "(manca la classe)");
        System.out.println(message);
        WrapFiltri wrapFiltri = null;
        ottenutoIntero = 0;

        try {
            wrapFiltri = WrapFiltri.crea(clazz, filter, propertyName, propertyValue);
            message = String.format("Query -> %s", filter.getOperazione(propertyName, propertyValue));
            System.out.println(message);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        if (wrapFiltri != null) {
            try {
                ottenutoIntero = service.count(clazz, wrapFiltri);
                System.out.println(String.format("Risultato = %d", ottenutoIntero));
                System.out.println(VUOTA);
            } catch (AlgosException unErrore) {
                printError(unErrore);
            }
            System.out.println(VUOTA);
            printWrapFiltro(clazz, filter, propertyName, propertyValue, previstoIntero, ottenutoIntero);
        }
        assertEquals(previstoIntero, ottenutoIntero);
    }


    //    @ParameterizedTest
    @MethodSource(value = "CLAZZ_KEY_ID")
    @Order(12)
    @DisplayName("12 - Crea un Doc (gson) da mongoDb con keyId")
    void creaDocById(final Class clazz, final Serializable keyPropertyValue, final boolean valida) {
        FlowVar.typeSerializing = AETypeSerializing.spring;
        System.out.println("12 - Crea un Doc (gson) da mongoDb con keyId");

        try {
            doc = service.findDocById(clazz, keyPropertyValue);
            printDoc(clazz, keyPropertyValue, doc);
        } catch (AlgosException unErrore) {
            System.out.println(String.format("Ricerca di %s.%s", clazz != null ? clazz.getSimpleName() : VUOTA, keyPropertyValue));
            printError(unErrore);
        }
        if (valida) {
            assertNotNull(doc);
        }
        else {
            assertNull(doc);
        }
    }

    //    @ParameterizedTest
    @MethodSource(value = "CLAZZ_PROPERTY")
    @Order(13)
    @DisplayName("13 - Crea un Doc (gson) da mongoDb con propertyName")
    void creaDocByProperty(final Class clazz, final String propertyName, final Serializable propertyValue, final int count, final boolean valida) {
        FlowVar.typeSerializing = AETypeSerializing.gson;
        System.out.println("13 - Crea un Doc (gson) da mongoDb con propertyName");

        try {
            doc = service.findDocByProperty(clazz, propertyName, propertyValue);
            printDoc(clazz, propertyName, propertyValue, doc);
        } catch (AlgosException unErrore) {
            System.out.println(String.format("Ricerca di %s.%s=%s", clazz != null ? clazz.getSimpleName() : VUOTA, propertyName, propertyValue));
            printError(unErrore);
        }
        if (valida) {
            assertNotNull(doc);
        }
        else {
            assertNull(doc);
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "CLAZZ_KEY_ID")
    @Order(14)
    @DisplayName("14 - Crea una entity (gson) da un Doc con keyId")
    void creaEntityByDocGson(final Class clazz, final Serializable keyPropertyValue, final boolean valida) {
        System.out.println("14 - Crea una entity (gson) da un Doc con keyId");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        creaEntity1415(clazz, keyPropertyValue, valida);
    }


    //    @ParameterizedTest
    @MethodSource(value = "CLAZZ_KEY_ID")
    @Order(15)
    @DisplayName("15 - Crea una entity (spring) da un Doc con keyId")
    void creaEntityByDocSpring(final Class clazz, final Serializable keyPropertyValue, final boolean valida) {
        System.out.println("15 - Crea una entity (spring) da un Doc con keyId");
        FlowVar.typeSerializing = AETypeSerializing.spring;
        creaEntity1415(clazz, keyPropertyValue, valida);
    }


    void creaEntity1415(final Class clazz, final Serializable keyPropertyValue, final boolean valida) {
        doc = null;
        entityBean = null;

        try {
            doc = service.findDocById(clazz, keyPropertyValue);
        } catch (AlgosException unErrore) {
            System.out.println(String.format("Ricerca di %s.%s", clazz != null ? clazz.getSimpleName() : VUOTA, keyPropertyValue));
            printError(unErrore);
        }
        if (valida) {
            assertNotNull(doc);
        }
        else {
            assertNull(doc);
        }
        try {
            entityBean = service.creaByDoc(clazz, doc);
            System.out.println(String.format("Creata la entity [%s] della classe '%s'", keyPropertyValue, clazz.getSimpleName()));
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        System.out.println(VUOTA);
    }


//    @ParameterizedTest
    @MethodSource(value = "CLAZZ_KEY_ID")
    @Order(16)
    @DisplayName("16 - Find (gson) entityBean by keyId")
    void findByIdGson(final Class clazz, final Serializable keyPropertyValue, final boolean valida) {
        System.out.println("16 - Find (gson) entityBean by keyId");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        findById1617(clazz, keyPropertyValue, valida);
    }


//    @ParameterizedTest
    @MethodSource(value = "CLAZZ_KEY_ID")
    @Order(17)
    @DisplayName("17 - Find (spring) entityBean by keyId")
    void findByIdSpring(final Class clazz, final Serializable keyPropertyValue, final boolean valida) {
        System.out.println("17 - Find (spring) entityBean by keyId");
        FlowVar.typeSerializing = AETypeSerializing.spring;
        findById1617(clazz, keyPropertyValue, valida);
    }


    void findById1617(final Class clazz, final Serializable keyPropertyValue, final boolean valida) {
        FlowVar.typeSerializing = AETypeSerializing.spring;

        entityBean = null;
        try {
            entityBean = service.find(clazz, keyPropertyValue);
            printEntityBeanFromKeyId(clazz, keyPropertyValue, entityBean, previstoIntero);
        } catch (AlgosException unErrore) {
            System.out.println(String.format("Ricerca di %s.%s", clazz != null ? clazz.getSimpleName() : VUOTA, keyPropertyValue));
            printError(unErrore);
        }
        if (valida) {
            assertNotNull(entityBean);
        }
        else {
            assertNull(entityBean);
        }
    }


//    @ParameterizedTest
    @MethodSource(value = "CLAZZ_PROPERTY")
    @Order(22)
    @DisplayName("22 - Find (gson) entityBean by property")
    void findByPropertyGson(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero) {
        System.out.println("22 - Find (gson) entityBean by property");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        findByProperty2223(clazz, propertyName, propertyValue, previstoIntero);
    }


//    @ParameterizedTest
    @MethodSource(value = "CLAZZ_PROPERTY")
    @Order(23)
    @DisplayName("23 - Find (spring) entityBean by property")
    void findByPropertySpring(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero) {
        System.out.println("23 - Find (spring) entityBean by property");
        FlowVar.typeSerializing = AETypeSerializing.spring;
        findByProperty2223(clazz, propertyName, propertyValue, previstoIntero);
    }


    void findByProperty2223(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero) {
        try {
            entityBean = service.find(clazz, propertyName, propertyValue);
            printEntityBeanFromProperty(clazz, propertyName, propertyValue, entityBean, previstoIntero);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }

//    @ParameterizedTest
    @MethodSource(value = "CLAZZ_COUNT")
    @Order(24)
    @DisplayName("24 - Fetch completo (gson) di una classe")
    void fetchGson(final Class clazz, final int previstoIntero, final boolean risultatoEsatto) {
        System.out.println("24 - Fetch completo (gson) di una classe");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        fetch2425(clazz, previstoIntero, risultatoEsatto);
    }

//    @ParameterizedTest
    @MethodSource(value = "CLAZZ_COUNT")
    @Order(25)
    @DisplayName("25 - Fetch completo (spring) di una classe")
    void fetchSpring(final Class clazz, final int previstoIntero, final boolean risultatoEsatto) {
        System.out.println("25 - Fetch completo (spring) di una classe");
        FlowVar.typeSerializing = AETypeSerializing.spring;
        fetch2425(clazz, previstoIntero, risultatoEsatto);
    }

    private void fetch2425(final Class clazz, final int previstoIntero, final boolean risultatoEsatto) {
        String message;

        message = String.format("Fetch completo di %s", clazz != null ? clazz.getSimpleName() : "(manca la classe)");
        System.out.println(message);
        try {
            ottenutoIntero = service.count(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
            return;
        }
        if (risultatoEsatto) {
            assertEquals(previstoIntero, ottenutoIntero);
        }

        try {
            listaBean = service.fetch(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        if (listaBean != null) {
            if (ottenutoIntero == listaBean.size()) {
                printWrapFiltro(clazz, previstoIntero, listaBean, risultatoEsatto);
            }
            else {
                message = String.format("Qualcosa non quadra perché il fetch() ha recuperato %d entities mentre avrebbero dovuto essere %d secondo il count()", listaBean.size(), ottenutoIntero);
                System.out.println(message);
                assertEquals(previstoIntero, listaBean.size());
            }
        }
        else {
            if (previstoIntero != 0) {
                System.out.println("Qualcosa non quadra perché erano previste entities che non sono state trovate");
            }
        }
    }


//    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FILTER")
    @Order(26)
    @DisplayName("26 - Fetch gson filtrato (WrapFiltro)")
    void fetchGson(final Class clazz, final AETypeFilter filter, final String propertyName, final String propertyValue, final int previstoIntero) {
        System.out.println("26 - Fetch gson filtrato (WrapFiltro)");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        fetch2627(clazz, filter, propertyName, propertyValue, previstoIntero);
    }


//    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FILTER")
    @Order(27)
    @DisplayName("27 - Fetch spring filtrato (WrapFiltro)")
    void fetchSpring(final Class clazz, final AETypeFilter filter, final String propertyName, final String propertyValue, final int previstoIntero) {
        System.out.println("27 - Fetch spring filtrato (WrapFiltro)");
        FlowVar.typeSerializing = AETypeSerializing.spring;
        fetch2627(clazz, filter, propertyName, propertyValue, previstoIntero);
    }


    void fetch2627(final Class clazz, final AETypeFilter filter, final String propertyName, final String propertyValue, final int previstoIntero) {
        WrapFiltri wrapFiltri = null;
        String message;

        try {
            wrapFiltri = WrapFiltri.crea(clazz, filter, propertyName, propertyValue);
            message = String.format("Query -> %s", filter.getOperazione(propertyName, propertyValue));
            System.out.println(message);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }

        if (wrapFiltri != null) {
            try {
                listaBean = service.fetch(clazz, wrapFiltri);
                System.out.println(String.format("Risultato = %d", listaBean.size()));
                System.out.println(VUOTA);
            } catch (AlgosException unErrore) {
                printError(unErrore);
            }
            System.out.println(VUOTA);
            assertEquals(previstoIntero, listaBean.size());
            printWrapFiltro(clazz, filter, propertyName, propertyValue, previstoIntero, listaBean);
        }
    }


    //    @Test
    @Order(30)
    @DisplayName("30 - Save base (gson) di una entity")
    void save() {
        System.out.println("30 - Save base (gson) di una entity");
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
        } catch (AlgosException unErrore) {
            //            System.out.println(unErrore);
            loggerService.info(unErrore.getMessage());
        }
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
            entityBean = service.find(clazz, sorgente);
        } catch (AlgosException unErrore) {
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
        } catch (AlgosException unErrore) {
            System.out.println(unErrore);
        }

        //--ri-leggo la entityBean (dal vecchio id) controllo la property per vedere se è stata modificata e registrata
        try {
            entityBean = service.find(clazz, entityBean.getId());
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
        } catch (AlgosException unErrore) {
            System.out.println(unErrore);
        }

        //--ri-leggo la entityBean e ri-controllo la property
        try {
            entityBean = service.find(clazz, sorgente);
        } catch (AlgosException unErrore) {
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
            entityBean = service.find(clazz, sorgente);
        } catch (AlgosException unErrore) {
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
        } catch (AlgosException unErrore) {
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
        } catch (AlgosException unErrore) {
            loggerService.error(unErrore, this.getClass(), "fetch");
        }
        assertNotNull(listaBean);
        assertEquals(previstoIntero, listaBean.size());
        System.out.println(String.format("Nella collezione '%s' ci sono %s entities recuperate in %s", sorgenteClasse.getSimpleName(), textService.format(listaBean.size()), dateService.deltaTextEsatto(inizio)));
    }

    //    @Test
    @Order(7)
    @DisplayName("7 - Count gson filtrato (AFiltro) singolo")
    void countFiltroGson() {
        System.out.println("7 - Count gson filtrato (AFiltro) singolo");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        this.count78();
    }


    //    @Test
    @Order(8)
    @DisplayName("8 - Count spring filtrato (AFiltro) singolo")
    void countFiltroSpring() {
        System.out.println("8 - Count spring filtrato (AFiltro) singolo");
        FlowVar.typeSerializing = AETypeSerializing.spring;
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
        filtroStart = "circ";
        filtro = AFiltro.start(NAME_NOME, filtroStart);
        mappaFiltri = Collections.singletonMap(filtro.getCriteria().getKey(), filtro);
        previstoIntero = 1;
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
        previstoIntero = 452;
        try {
            ottenutoIntero = service.count(clazz, mappaFiltri);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
        printCount(clazz, filtro, previstoIntero, ottenutoIntero);
        System.out.println("(Esempio volutamente sbagliato)");
        assertNotEquals(previstoIntero, ottenutoIntero);
    }

    //    @Test
    @Order(9)
    @DisplayName("9 - Count gson filtrato (mappaFiltri)")
    void countFiltroMappaGson() {
        System.out.println("9 - Count gson filtrato (mappaFiltri)");
        FlowVar.typeSerializing = AETypeSerializing.gson;
        this.count910();
    }

    //    @Test
    @Order(10)
    @DisplayName("10 - Count spring filtrato (mappaFiltri)")
    void countFiltroMappaSpring() {
        System.out.println("10 - Count spring filtrato (mappaFiltri)");
        FlowVar.typeSerializing = AETypeSerializing.spring;
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

    //    void printCount(final Class clazz, final int size, final Document bSon) {
    //        printCount(clazz.getSimpleName(), size, bSon);
    //    }


    void printCount(final String simpleName, final int size, final Document bSon) {
        String key = (String) bSon.keySet().toArray()[0];
        int value = (int) bSon.values().toArray()[0];
        printCount(clazz.getSimpleName(), size, key, value);
    }


    void printDoc(final Class clazz, Serializable keyId, final Document doc) {
        printDoc(clazz, FlowCost.FIELD_ID, keyId, doc);
    }


    void printDoc(final Class clazz, final String propertyName, final Serializable propertyValue, final Document doc) {
        String key;
        Object value;

        if (doc != null) {
            System.out.println(String.format("Ricerca di %s.%s=%s", clazz != null ? clazz.getSimpleName() : VUOTA, propertyName, propertyValue));
            System.out.println(String.format("Trovato: il documento contiene %s parametri chiave=valore, più keyID e classe", doc.size() - 2));
            for (Map.Entry<String, Object> mappa : doc.entrySet()) {
                key = mappa.getKey();
                value = mappa.getValue();
                System.out.println(String.format("%s: %s", key, value));
            }
        }
        else {
            System.out.println(String.format("Nessun documento trovato"));
        }
        System.out.println(VUOTA);
    }

}
