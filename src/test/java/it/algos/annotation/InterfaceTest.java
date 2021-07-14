package it.algos.annotation;

import com.vaadin.flow.router.*;
import it.algos.test.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.service.*;
import org.hibernate.validator.constraints.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 21-mag-2021
 * Time: 08:52
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("AnnotationTest")
@DisplayName("Interfaces NON Algos")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InterfaceTest extends ATest {


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
    @DisplayName("1 - getRoute")
    void getRoute() {
        Route route;

        route =  VIA_ENTITY_CLASS.getAnnotation(Route.class);
        assertNull(route);
        route = service.getRoute(VIA_ENTITY_CLASS);
        assertNull(route);

        route = (Route) VIA_LIST_CLASS.getAnnotation(Route.class);
        assertNotNull(route);
        route = service.getRoute(VIA_LIST_CLASS);
        assertNotNull(route);

        route = (Route) VIA_SERVICE_CLASS.getAnnotation(Route.class);
        assertNull(route);
        route = service.getRoute(VIA_SERVICE_CLASS);
        assertNull(route);
    }

    @Test
    @Order(2)
    @DisplayName("2 - getQualifier")
    void getQualifier() {
        Qualifier qualifier;

        qualifier = (Qualifier) VIA_LIST_CLASS.getAnnotation(Qualifier.class);
        assertNull(qualifier);
        qualifier = service.getQualifier(VIA_LIST_CLASS);
        assertNull(qualifier);

        qualifier = (Qualifier) VIA_SERVICE_CLASS.getAnnotation(Qualifier.class);
        assertNotNull(qualifier);
        qualifier = service.getQualifier(VIA_SERVICE_CLASS);
        assertNotNull(qualifier);
    }


    @Test
    @Order(3)
    @DisplayName("3 - getDocument")
    void getDocument() {
        Document document;

        document = (Document) VIA_ENTITY_CLASS.getAnnotation(Document.class);
        assertNotNull(document);
        document = service.getDocument(VIA_ENTITY_CLASS);
        assertNotNull(document);

        document = (Document) VIA_LIST_CLASS.getAnnotation(Document.class);
        assertNull(document);
        document = service.getDocument(VIA_LIST_CLASS);
        assertNull(document);

        document = (Document) VIA_SERVICE_CLASS.getAnnotation(Document.class);
        assertNull(document);
        document = service.getDocument(VIA_SERVICE_CLASS);
        assertNull(document);
    }

    @Test
    @Order(4)
    @DisplayName("4 - getNotNull")
    void getNotNull() {
        NotNull notNull;

        sorgente = FlowCost.FIELD_ORDINE;
        ottenutoField = reflection.getField(ANNO_ENTITY_CLASS, sorgente);
        notNull = ottenutoField.getAnnotation(NotNull.class);
        assertNull(notNull);
        notNull = service.getNotNull(ottenutoField);
        assertNull(notNull);

        sorgente = "secolo";
        ottenutoField = reflection.getField(ANNO_ENTITY_CLASS, sorgente);
        notNull = ottenutoField.getAnnotation(NotNull.class);
        assertNotNull(notNull);
        notNull = service.getNotNull(ottenutoField);
        assertNotNull(notNull);
    }


    @Test
    @Order(5)
    @DisplayName("5 - getNotBlank")
    void getNotBlank() {
        NotBlank notBlank;

        sorgente = FlowCost.FIELD_ORDINE;
        ottenutoField = reflection.getField(ANNO_ENTITY_CLASS, sorgente);
        notBlank = ottenutoField.getAnnotation(NotBlank.class);
        assertNull(notBlank);
        notBlank = service.getNotBlank(ottenutoField);
        assertNull(notBlank);

        sorgente = "anno";
        ottenutoField = reflection.getField(ANNO_ENTITY_CLASS, sorgente);
        notBlank = ottenutoField.getAnnotation(NotBlank.class);
        assertNotNull(notBlank);
        notBlank = service.getNotBlank(ottenutoField);
        assertNotNull(notBlank);
    }

    @Test
    @Order(6)
    @DisplayName("6 - getIndexed")
    void getIndexed() {
        Indexed indexed;

        sorgente = FlowCost.FIELD_ORDINE;
        ottenutoField = reflection.getField(ANNO_ENTITY_CLASS, sorgente);
        indexed = ottenutoField.getAnnotation(Indexed.class);
        assertNotNull(indexed);
        indexed = service.getIndexed(ottenutoField);
        assertNotNull(indexed);

        sorgente = "secolo";
        ottenutoField = reflection.getField(ANNO_ENTITY_CLASS, sorgente);
        indexed = ottenutoField.getAnnotation(Indexed.class);
        assertNull(indexed);
        indexed = service.getIndexed(ottenutoField);
        assertNull(indexed);
    }

    @Test
    @Order(7)
    @DisplayName("7 - getSize")
    void getSize() {
        Size size;

        sorgente = FlowCost.FIELD_ORDINE;
        ottenutoField = reflection.getField(MESE_ENTITY_CLASS, sorgente);
        size = ottenutoField.getAnnotation(Size.class);
        assertNull(size);
        size = service.getSize(ottenutoField);
        assertNull(size);

        sorgente = "sigla";
        ottenutoField = reflection.getField(MESE_ENTITY_CLASS, sorgente);
        size = ottenutoField.getAnnotation(Size.class);
        assertNotNull(size);
        size = service.getSize(ottenutoField);
        assertNotNull(size);
    }

    @Test
    @Order(8)
    @DisplayName("8 - getRange")
    void getRange() {
        Range range;

        sorgente = FlowCost.FIELD_ORDINE;
        ottenutoField = reflection.getField(MESE_ENTITY_CLASS, sorgente);
        range = ottenutoField.getAnnotation(Range.class);
        assertNull(range);
        range = service.getRange(ottenutoField);
        assertNull(range);

        sorgente = "giorni";
        ottenutoField = reflection.getField(MESE_ENTITY_CLASS, sorgente);
        range = ottenutoField.getAnnotation(Range.class);
        assertNotNull(range);
        range = service.getRange(ottenutoField);
        assertNotNull(range);
    }

    @Test
    @Order(9)
    @DisplayName("9 - getDBRef")
    void getDBRef() {
        DBRef dbRef;

        sorgente = FlowCost.FIELD_ORDINE;
        ottenutoField = reflection.getField(ANNO_ENTITY_CLASS, sorgente);
        dbRef = ottenutoField.getAnnotation(DBRef.class);
        assertNull(dbRef);
        dbRef = service.getDBRef(ottenutoField);
        assertNull(dbRef);

        sorgente = "secolo";
        ottenutoField = reflection.getField(ANNO_ENTITY_CLASS, sorgente);
        dbRef = ottenutoField.getAnnotation(DBRef.class);
        assertNotNull(dbRef);
        dbRef = service.getDBRef(ottenutoField);
        assertNotNull(dbRef);
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