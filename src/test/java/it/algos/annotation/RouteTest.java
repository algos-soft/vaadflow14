package it.algos.annotation;

import com.vaadin.flow.router.*;
import it.algos.test.*;
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
 * Time: 14:05
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("RouteTest")
@DisplayName("Annotation @Route")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RouteTest extends ATest {


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
    @DisplayName("1 - isRouteView")
    void isRouteView() {
        Route route;

        route = VIA_ENTITY_CLASS.getAnnotation(Route.class);
        assertNull(route);
        ottenutoBooleano = service.isRouteView(VIA_ENTITY_CLASS);
        assertFalse(ottenutoBooleano);

        route = (Route) VIA_LIST_CLASS.getAnnotation(Route.class);
        assertNotNull(route);
        ottenutoBooleano = service.isRouteView(VIA_LIST_CLASS);
        assertTrue(ottenutoBooleano);

        route = (Route) VIA_SERVICE_CLASS.getAnnotation(Route.class);
        assertNull(route);
        ottenutoBooleano = service.isRouteView(VIA_SERVICE_CLASS);
        assertFalse(ottenutoBooleano);
    }

    @Test
    @Order(2)
    @DisplayName("2 - getRouteName")
    void getRouteName() {
        Route route;
        previsto = VUOTA;

        route = VIA_ENTITY_CLASS.getAnnotation(Route.class);
        assertNull(route);
        ottenuto = service.getRouteName(VIA_ENTITY_CLASS);
        assertEquals(previsto, ottenuto);

        previsto = "via";
        route = (Route) VIA_LIST_CLASS.getAnnotation(Route.class);
        assertNotNull(route);
        ottenuto = service.getRouteName(VIA_LIST_CLASS);
        assertEquals(previsto, ottenuto);

        route = (Route) VIA_SERVICE_CLASS.getAnnotation(Route.class);
        assertNull(route);
        ottenuto = service.getRouteName(VIA_SERVICE_CLASS);
        assertFalse(ottenutoBooleano);
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