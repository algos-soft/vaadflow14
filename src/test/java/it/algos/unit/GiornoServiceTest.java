package it.algos.unit;

import it.algos.test.*;
import it.algos.vaadflow14.backend.packages.crono.giorno.*;
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
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    void setUpEach() {
        super.setUp();

        service.mongo = mongoService;
        mongoService.gSonService=gSonService;
        gSonService.reflection=reflectionService;
        gSonService.annotation=annotationService;
    }


    @Test
    @Order(1)
    @DisplayName("1 - findById")
    void findById() {
        sorgente = "29gennaio";
        entityBean = service.findById(sorgente);
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