package it.algos.unit;

import it.algos.test.*;
import it.algos.vaadflow14.backend.packages.crono.giorno.*;
import it.algos.vaadflow14.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 19-ago-2021
 * Time: 18:56
 * <p>
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("GiornoService - Entity cronologica")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoServiceTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    private GiornoService service;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    void setUpIniziale() {
        super.setUpStartUp();

        service.mongo = mongoService;
        service.text = textService;
        service.annotation = annotationService;
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
    @DisplayName("1 - findById")
    void findById() {
        sorgente = "29 gennaio";
        entityBean = service.findById(sorgente);
        assertNull(entityBean);

        sorgente = "29gennaio";
        entityBean = service.findById(sorgente);
        assertNotNull(entityBean);
    }


    @Test
    @Order(2)
    @DisplayName("2 - findByProperty")
    void findByKey() {
        sorgente = "29gennaio";
        entityBean = service.findByProperty( sorgente2, sorgente);
        assertNull(entityBean);

        sorgente = "29gennaio";
        sorgente2 = "titolo";
        entityBean = service.findByProperty( sorgente2, sorgente);
        assertNull(entityBean);

        sorgente = "29 gennaio";
        sorgente2 = "titolo";
        entityBean = service.findByProperty( sorgente2, sorgente);
        assertNotNull(entityBean);
    }


    @Test
    @Order(3)
    @DisplayName("3 - findByKey")
    void findByKey3() {
        sorgente = "29gennaio";
        entityBean = service.findByKey( sorgente);
        assertNull(entityBean);

        sorgente = "29 gennaio";
        entityBean = service.findByKey( sorgente);
        assertNotNull(entityBean);
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