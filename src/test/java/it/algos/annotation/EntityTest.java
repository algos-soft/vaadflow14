package it.algos.annotation;

import it.algos.simple.backend.packages.*;
import it.algos.simple.backend.packages.alfa.*;
import it.algos.test.*;
import it.algos.vaadflow14.backend.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 21-mag-2021
 * Time: 17:39
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("EntityTest")
@DisplayName("Annotation @AIEntity")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    AnnotationService service;


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

//        service.text = text;
    }


    /**
     * Qui passa ad ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    void setUpEach() {
        super.setUp();
    }

    @Test
    @Order(1)
    @DisplayName("1 - isEntityClass")
    void isEntityClass() {
        AIEntity entity;

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);

        ottenutoBooleano = service.isEntityClass(VIA_ENTITY_CLASS);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEntityClass(VIA_LIST_CLASS);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(2)
    @DisplayName("2 - getRecordName")
    void getRecordName() {
        AIEntity entity;
        previsto = "Via";

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);

        ottenuto = service.getRecordName(VIA_ENTITY_CLASS);
        assertEquals(previsto, ottenuto);

        ottenuto = service.getRecordName(VIA_LIST_CLASS);
        assertEquals(VUOTA, ottenuto);

        previsto = "alfaRecord";
        ottenuto = service.getRecordName(Alfa.class);
        assertEquals(previsto, ottenuto);

        previsto = "Beta";
        ottenuto = service.getRecordName(Beta.class);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(3)
    @DisplayName("3 - getKeyPropertyName")
    void getKeyPropertyName() {
        AIEntity entity;
        previsto = "nome";

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);

        ottenuto = service.getKeyPropertyName(VIA_ENTITY_CLASS);
        assertEquals(previsto, ottenuto);

        ottenuto = service.getKeyPropertyName(VIA_LIST_CLASS);
        assertEquals(VUOTA, ottenuto);

        previsto = FIELD_ID;
        ottenuto = service.getKeyPropertyName(Alfa.class);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(4)
    @DisplayName("4 - usaReset")
    void usaReset() {
        AIEntity entity;

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);

        ottenutoBooleano = service.usaReset(VIA_ENTITY_CLASS);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.usaReset(VIA_LIST_CLASS);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(5)
    @DisplayName("5 - usaBoot")
    void usaBoot() {
        AIEntity entity;

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);

        ottenutoBooleano = service.usaBoot(VIA_ENTITY_CLASS);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.usaBoot(VIA_LIST_CLASS);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(6)
    @DisplayName("6 - usaNew")
    void usaNew() {
        AIEntity entity;

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);

        ottenutoBooleano = service.usaNew(VIA_ENTITY_CLASS);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.usaNew(VIA_LIST_CLASS);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(7)
    @DisplayName("7 - usaCompany")
    void usaCompany() {
        AIEntity entity;

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);

        ottenutoBooleano = service.usaCompany(VIA_ENTITY_CLASS);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaCompany(VIA_LIST_CLASS);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaCompany(Alfa.class);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.usaCompany(Beta.class);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(8)
    @DisplayName("8 - usaNote")
    void usaNote() {
        AIEntity entity;

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);

        ottenutoBooleano = service.usaNote(VIA_ENTITY_CLASS);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaNote(VIA_LIST_CLASS);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaNote(Alfa.class);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaNote(Beta.class);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(9)
    @DisplayName("9 - usaTimeStamp")
    void usaTimeStamp() {
        AIEntity entity;

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);

        ottenutoBooleano = service.usaTimeStamp(VIA_ENTITY_CLASS);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaTimeStamp(VIA_LIST_CLASS);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaTimeStamp(Alfa.class);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaTimeStamp(Beta.class);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaTimeStamp(Delta.class);
        assertTrue(ottenutoBooleano);
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