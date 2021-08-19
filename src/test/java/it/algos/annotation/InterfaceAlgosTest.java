package it.algos.annotation;

import it.algos.simple.backend.packages.*;
import it.algos.test.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 21-mag-2021
 * Time: 13:34
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("InterfaceAlgosTest")
@DisplayName("Interfaces Algos")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InterfaceAlgosTest extends ATest {


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
    @DisplayName("1 - getAIScript")
    void getAIScript() {
        AIScript script;

        script = Beta.class.getAnnotation(AIScript.class);
        assertNull(script);
        script = service.getAIScript(Beta.class);
        assertNull(script);

        script = VIA_ENTITY_CLASS.getAnnotation(AIScript.class);
        assertNotNull(script);
        script = service.getAIScript(VIA_ENTITY_CLASS);
        assertNotNull(script);
    }

    @Test
    @Order(2)
    @DisplayName("2 - getAIEntity")
    void getAIEntity() {
        AIEntity entity;

        entity = VIA_ENTITY_CLASS.getAnnotation(AIEntity.class);
        assertNotNull(entity);
        entity = service.getAIEntity(VIA_ENTITY_CLASS);
        assertNotNull(entity);
    }


    @Test
    @Order(3)
    @DisplayName("3 - getAIView")
    void getAIView() {
        AIView view;

        view = VIA_ENTITY_CLASS.getAnnotation(AIView.class);
        assertNotNull(view);
        view = service.getAIView(VIA_ENTITY_CLASS);
        assertNotNull(view);

        view = (AIView) VIA_LIST_CLASS.getAnnotation(AIView.class);
        assertNull(view);
        view = service.getAIView(VIA_LIST_CLASS);
        assertNull(view);

        view = (AIView) VIA_SERVICE_CLASS.getAnnotation(AIView.class);
        assertNull(view);
        view = service.getAIView(VIA_SERVICE_CLASS);
        assertNull(view);
    }


    @Test
    @Order(4)
    @DisplayName("4 - getAIList")
    void getAIList() {
        AIList list;

        list = VIA_ENTITY_CLASS.getAnnotation(AIList.class);
        assertNotNull(list);
        list = service.getAIList(VIA_ENTITY_CLASS);
        assertNotNull(list);

        list = (AIList) VIA_LIST_CLASS.getAnnotation(AIList.class);
        assertNull(list);
        list = service.getAIList(VIA_LIST_CLASS);
        assertNull(list);

        list = (AIList) VIA_SERVICE_CLASS.getAnnotation(AIList.class);
        assertNull(list);
        list = service.getAIList(VIA_SERVICE_CLASS);
        assertNull(list);
    }


    @Test
    @Order(5)
    @DisplayName("5 - getAIForm")
    void getAIForm() {
        AIForm form;

        form = VIA_ENTITY_CLASS.getAnnotation(AIForm.class);
        assertNotNull(form);
        form = service.getAIForm(VIA_ENTITY_CLASS);
        assertNotNull(form);

        form = (AIForm) VIA_LIST_CLASS.getAnnotation(AIForm.class);
        assertNull(form);
        form = service.getAIForm(VIA_LIST_CLASS);
        assertNull(form);

        form = (AIForm) VIA_SERVICE_CLASS.getAnnotation(AIForm.class);
        assertNull(form);
        form = service.getAIForm(VIA_SERVICE_CLASS);
        assertNull(form);
    }


    @Test
    @Order(6)
    @DisplayName("6 - getAIField")
    void getAIField() {
        AIField field;

        sorgente = FlowCost.FIELD_ORDINE;
        ottenutoField = reflectionService.getField(ANNO_ENTITY_CLASS, sorgente);
        field = ottenutoField.getAnnotation(AIField.class);
        assertNotNull(field);
        field = service.getAIField(ottenutoField);
        assertNotNull(field);
    }

    @Test
    @Order(7)
    @DisplayName("7 - getAIColumn")
    void getAIColumn() {
        AIColumn column;

        sorgente = FlowCost.FIELD_ORDINE;
        ottenutoField = reflectionService.getField(ANNO_ENTITY_CLASS, sorgente);
        column = ottenutoField.getAnnotation(AIColumn.class);
        assertNotNull(column);
        column = service.getAIColumn(ottenutoField);
        assertNotNull(column);
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