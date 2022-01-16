package it.algos.integration;

import it.algos.simple.*;
import it.algos.test.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.wrapper.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.*;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 11-gen-2022
 * Time: 11:51
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("WrapQuery wrapper Integration")
@Tag("testAllIntegration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WrapQueryIntegrationTest extends ATest {

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    //--property
    private WrapQuery istanza;

    //--property
    private WrapQuery.Type typeExpected;

    //--property
    private AETypeFilter typeFilter;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();

        MockitoAnnotations.initMocks(this);
        //        MockitoAnnotations.openMocks(this);
        //        Assertions.assertNotNull(appContext);
        WrapQuery.appContext = appContext;
    }


    @BeforeEach
    void setUpEach() {
        super.setUp();

        istanza = null;
        typeFilter = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Creazione semplice nulla con throws Exception")
    void creazioneBaseNulla() {
        System.out.println("1 - Creazione semplice nulla con throws Exception");

        clazz = null;
        try {
            istanza = appContext.getBean(WrapQuery.class, clazz);
        } catch (Exception unErrore) {
            printError(unErrore);
        }
        Assertions.assertNull(istanza);
    }


    @Test
    @Order(2)
    @DisplayName("2 - Creazione semplice non valida con throws Exception")
    void creazioneBaseNonValida() {
        System.out.println("2 - Creazione semplice non valida con throws Exception");

        clazz = LogicList.class;
        try {
            istanza = appContext.getBean(WrapQuery.class, clazz);
        } catch (Exception unErrore) {
            printError(unErrore);
        }
        Assertions.assertNull(istanza);
    }

    @Test
    @Order(3)
    @DisplayName("3 - Creazione semplice di base valida")
    void creazioneBaseSemplice() {
        System.out.println("3 - Creazione semplice di base valida");
        entityClazz = GIORNO_ENTITY_CLASS;
        typeExpected = WrapQuery.Type.validaSenzaFiltri;

        try {
            istanza = appContext.getBean(WrapQuery.class, entityClazz);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (Exception unErrore) {
            printError(unErrore);
        }
    }

    @Test
    @Order(4)
    @DisplayName("4 - Creazione semplice non valida con metodo statico Crea")
    void creazioneBaseStatica() {
        System.out.println("4 - Creazione semplice non valida con metodo statico Crea");

        clazz = null;
        try {
            istanza = WrapQuery.crea(clazz);
        } catch (Exception unErrore) {
            printError(unErrore);
        }
        Assertions.assertNull(istanza);
    }

    @Test
    @Order(5)
    @DisplayName("5 - Creazione semplice non valida con metodo statico Crea (2)")
    void creazioneBaseStatica2() {
        System.out.println("5 - Creazione semplice non valida con metodo statico Crea (2)");

        clazz = LogicList.class;
        try {
            istanza = WrapQuery.crea(clazz);
        } catch (Exception unErrore) {
            printError(unErrore);
        }
        Assertions.assertNull(istanza);
    }

    @Test
    @Order(6)
    @DisplayName("6 - Creazione semplice valida con metodo statico")
    void creazioneBaseStatica3() {
        System.out.println("6 - Creazione semplice valida con metodo statico");

        entityClazz = GIORNO_ENTITY_CLASS;
        typeExpected = WrapQuery.Type.validaSenzaFiltri;
        try {
            istanza = WrapQuery.crea(entityClazz);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }

    @Test
    @Order(7)
    @DisplayName("7 - Creazione con ordinamento ascendente non valida")
    void creazioneOrdinataNonValida() {
        System.out.println("7 - Creazione con ordinamento ascendente");

        entityClazz = MESE_ENTITY_CLASS;
        sorgente = "propertyNotFound";
        String propertyValue = "giovanni";
        sorgente2 = VUOTA;
        typeExpected = WrapQuery.Type.incompleta;
        try {
            istanza = WrapQuery.creaAscendente(entityClazz, sorgente, propertyValue, sorgente2);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }

    @Test
    @Order(8)
    @DisplayName("8 - Creazione completa con ordinamento ascendente valida")
    void creazioneOrdinataUp() {
        System.out.println("8 - Creazione completa con ordinamento ascendente valida");

        entityClazz = MESE_ENTITY_CLASS;
        typeExpected = WrapQuery.Type.validaSenzaFiltri;
        try {
            istanza = WrapQuery.creaAscendente(entityClazz);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }

    @Test
    @Order(9)
    @DisplayName("9 - Creazione completa con ordinamento discendente valida")
    void creazioneOrdinataDown() {
        System.out.println("9 - Creazione completa con ordinamento discendente valida");

        entityClazz = MESE_ENTITY_CLASS;
        typeExpected = WrapQuery.Type.validaSenzaFiltri;
        try {
            istanza = WrapQuery.creaDiscendente(entityClazz);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }


    @Test
    @Order(10)
    @DisplayName("10 - Creazione statica con filtro")
    void creazioneStaticaProperty() {
        System.out.println("10 - Creazione statica con filtro");

        entityClazz = MESE_ENTITY_CLASS;
        sorgente = "giorni";
        int propertyValue = 31;
        typeExpected = WrapQuery.Type.validaConFiltri;
        try {
            istanza = WrapQuery.crea(entityClazz, sorgente, propertyValue);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }


    @Test
    @Order(11)
    @DisplayName("11 - Creazione filtrata con 'key'' ascendente")
    void creazioneAscendente() {
        System.out.println("11 - Creazione filtrata con 'key'' ascendente");

        entityClazz = MESE_ENTITY_CLASS;
        sorgente = "giorni";
        int propertyValue = 31;
        typeExpected = WrapQuery.Type.validaConFiltri;
        try {
            istanza = WrapQuery.creaAscendente(entityClazz, sorgente, propertyValue);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }


    @Test
    @Order(12)
    @DisplayName("12 - Creazione filtrata con 'ordine'' ascendente")
    void creazioneStaticaPropertyAscendente() {
        System.out.println("12 - Creazione filtrata con 'ordine'' ascendente");

        entityClazz = MESE_ENTITY_CLASS;
        sorgente = "giorni";
        int propertyValue = 31;
        sorgente2 = "ordine";
        typeExpected = WrapQuery.Type.validaConFiltri;
        try {
            istanza = WrapQuery.creaAscendente(entityClazz, sorgente, propertyValue, sorgente2);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }

    @Test
    @Order(13)
    @DisplayName("13 - Creazione filtrata con 'mese'' ascendente")
    void creazioneStaticaPropertyAscendente2() {
        System.out.println("13 - Creazione filtrata con 'mese'' ascendente");

        entityClazz = MESE_ENTITY_CLASS;
        sorgente = "giorni";
        int propertyValue = 31;
        sorgente2 = "mese";
        typeExpected = WrapQuery.Type.validaConFiltri;
        try {
            istanza = WrapQuery.creaAscendente(entityClazz, sorgente, propertyValue, sorgente2);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }


    @Test
    @Order(14)
    @DisplayName("14 - Creazione filtrata con 'key'' discendente")
    void creazioneDiscendente() {
        System.out.println("14 - Creazione filtrata con 'key'' discendente");

        entityClazz = MESE_ENTITY_CLASS;
        sorgente = "giorni";
        int propertyValue = 31;
        typeExpected = WrapQuery.Type.validaConFiltri;
        try {
            istanza = WrapQuery.creaDiscendente(entityClazz, sorgente, propertyValue);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }

    @Test
    @Order(15)
    @DisplayName("15 - Creazione filtrata con 'ordine'' discendente")
    void creazioneStaticaPropertyDiscendente() {
        System.out.println("15 - Creazione filtrata con 'ordine''");

        entityClazz = MESE_ENTITY_CLASS;
        sorgente = "giorni";
        int propertyValue = 31;
        sorgente2 = "ordine";
        typeExpected = WrapQuery.Type.validaConFiltri;
        try {
            istanza = WrapQuery.creaDiscendente(entityClazz, sorgente, propertyValue, sorgente2);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }


    @Test
    @Order(16)
    @DisplayName("16 - Creazione filtrata con 'mese'' discendente")
    void creazioneStaticaPropertyDiscendente2() {
        System.out.println("16 - Creazione filtrata con 'mese''");

        entityClazz = MESE_ENTITY_CLASS;
        sorgente = "giorni";
        int propertyValue = 31;
        sorgente2 = "mese";
        typeExpected = WrapQuery.Type.validaConFiltri;
        try {
            istanza = WrapQuery.creaDiscendente(entityClazz, sorgente, propertyValue, sorgente2);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }

    @Test
    @Order(17)
    @DisplayName("17 - Creazione con typeFilter specifico di inizio campo testo")
    void creazioneFiltroInizio() {
        System.out.println("17 - Creazione con typeFilter specifico di inizio campo testo");

        entityClazz = MESE_ENTITY_CLASS;
        sorgente = "mese";
        sorgente2 = "{$regex : \"^m.*\"}";
        typeFilter= AETypeFilter.inizia;
        typeExpected = WrapQuery.Type.validaConFiltri;
        try {
            istanza = WrapQuery.crea(entityClazz, typeFilter,sorgente, sorgente2);
            printWrap(entityClazz, istanza, typeExpected);
        } catch (AlgosException unErrore) {
            printError(unErrore);
        }
    }

    private void printWrap(final Class<? extends AEntity> entityClazzExpected, final WrapQuery wrapQuery, final WrapQuery.Type typeExpected) {
        Assertions.assertNotNull(istanza);
        Assertions.assertEquals(entityClazzExpected, istanza.getEntityClazz());
        Assertions.assertEquals(typeExpected, istanza.getType());

        System.out.println(VUOTA);
        if (entityClazzExpected == null) {
            System.out.println(String.format("Creata un'istanza di WrapQuery ma manca la entityClazz"));
        }
        else {
            System.out.println(String.format("Creata un'istanza di WrapQuery (%s) con %s come entityClazz", istanza.getType(), entityClazzExpected.getSimpleName()));
        }

        System.out.println(wrapQuery.getQuery());
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

}
