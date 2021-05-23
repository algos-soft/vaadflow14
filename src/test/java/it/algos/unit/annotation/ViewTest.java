package it.algos.unit.annotation;

import com.vaadin.flow.component.icon.*;
import it.algos.simple.backend.packages.*;
import it.algos.simple.backend.packages.alfa.*;
import it.algos.test.*;
import it.algos.vaadflow14.backend.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 22-mag-2021
 * Time: 08:06
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("ViewTest")
@DisplayName("Annotation @AIView")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ViewTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    AAnnotationService service;


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

        service.text = text;
        service.reflection = reflection;
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
    @DisplayName("1 - getMenuName")
    void getMenuName() {
        AIView view;
        previsto = "Via";

        view = VIA_ENTITY_CLASS.getAnnotation(AIView.class);
        assertNotNull(view);

        ottenuto = service.getMenuName(VIA_ENTITY_CLASS);
        assertEquals(previsto, ottenuto);

        previsto = "Via";
        ottenuto = service.getMenuName(VIA_LIST_CLASS);
        assertEquals(previsto, ottenuto);

        previsto = "ViaService";
        ottenuto = service.getMenuName(VIA_SERVICE_CLASS);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(2)
    @DisplayName("2 - getMenuVaadinIcon")
    void getMenuVaadinIcon() {
        AIView view;
        VaadinIcon vaadinIconPrevista = VaadinIcon.ASTERISK;
        VaadinIcon vaadinIconOttenuta;

        view = VIA_ENTITY_CLASS.getAnnotation(AIView.class);
        assertNotNull(view);

        vaadinIconOttenuta = service.getMenuVaadinIcon(VIA_ENTITY_CLASS);
        assertEquals(vaadinIconPrevista, vaadinIconOttenuta);

        vaadinIconPrevista = VaadinIcon.CALENDAR;
        vaadinIconOttenuta = service.getMenuVaadinIcon(ANNO_ENTITY_CLASS);
        assertEquals(vaadinIconPrevista, vaadinIconOttenuta);
    }


    @Test
    @Order(3)
    @DisplayName("3 - getMenuIcon")
    void getMenuIcon() {
        AIView view;
        Icon iconPrevista = VaadinIcon.ASTERISK.create();
        Icon iconOttenuta;

        view = VIA_ENTITY_CLASS.getAnnotation(AIView.class);
        assertNotNull(view);

        iconOttenuta = service.getMenuIcon(VIA_ENTITY_CLASS);
        assertEquals(iconPrevista.getElement().toString(), iconOttenuta.getElement().toString());

        iconPrevista = VaadinIcon.CALENDAR.create();
        iconOttenuta = service.getMenuIcon(ANNO_ENTITY_CLASS);
        assertEquals(iconPrevista.getElement().toString(), iconOttenuta.getElement().toString());
    }


    @Test
    @Order(4)
    @DisplayName("4 - getSearchPropertyName")
    void getSearchPropertyName() {
        AIView view;
        previsto = "nome";

        view = VIA_ENTITY_CLASS.getAnnotation(AIView.class);
        assertNotNull(view);

        ottenuto = service.getSearchPropertyName(VIA_ENTITY_CLASS);
        assertEquals(previsto, ottenuto);

        previsto = VUOTA;
        ottenuto = service.getSearchPropertyName(VIA_LIST_CLASS);
        assertEquals(previsto, ottenuto);

        previsto = "code";
        ottenuto = service.getSearchPropertyName(Alfa.class);
        assertEquals(previsto, ottenuto);

        previsto = VUOTA;
        ottenuto = service.getSearchPropertyName(Lambda.class);
        assertEquals(previsto, ottenuto);

        previsto = VUOTA;
        ottenuto = service.getSearchPropertyName(Beta.class);
        assertEquals(previsto, ottenuto);
    }

    @Test
    @Order(5)
    @DisplayName("5 - usaSearchField")
    void usaSearchField() {
        AIView view;

        view = VIA_ENTITY_CLASS.getAnnotation(AIView.class);
        assertNotNull(view);

        ottenutoBooleano = service.usaSearchField(VIA_ENTITY_CLASS);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.usaSearchField(VIA_LIST_CLASS);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaSearchField(Alfa.class);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.usaSearchField(Lambda.class);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.usaSearchField(Lambda.class);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(6)
    @DisplayName("6 - getSortProperty")
    void getSortProperty() {
        AIView view;

        view = VIA_ENTITY_CLASS.getAnnotation(AIView.class);
        assertNotNull(view);

        previsto = "ordine";
        ottenuto = service.getSortProperty(VIA_ENTITY_CLASS);
        assertEquals(previsto, ottenuto);

        ottenuto = service.getSortProperty(VIA_LIST_CLASS);
        assertEquals(VUOTA, ottenuto);

        ottenuto = service.getSortProperty(ANNO_ENTITY_CLASS);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(7)
    @DisplayName("7 - getSortDirectionSpring")
    void getSortDirectionSpring() {
        AIView view;
        Sort.Direction sortDirectionSpringOttenuto = null;
        Sort.Direction sortDirectionSpringPrevisto = Sort.Direction.ASC;

        view = VIA_ENTITY_CLASS.getAnnotation(AIView.class);
        assertNotNull(view);

        sortDirectionSpringOttenuto = service.getSortDirectionSpring(VIA_ENTITY_CLASS);
        assertEquals(sortDirectionSpringPrevisto, sortDirectionSpringOttenuto);

        sortDirectionSpringOttenuto = service.getSortDirectionSpring(VIA_LIST_CLASS);
        assertEquals(sortDirectionSpringPrevisto, sortDirectionSpringOttenuto);

        sortDirectionSpringPrevisto = Sort.Direction.DESC;
        sortDirectionSpringOttenuto = service.getSortDirectionSpring(ANNO_ENTITY_CLASS);
        assertEquals(sortDirectionSpringPrevisto, sortDirectionSpringOttenuto);
    }


    @Test
    @Order(8)
    @DisplayName("8 - getSortSpring")
    void getSortSpring() {
        AIView view;
        Sort sortSpringOttenuto = null;
        Sort sortSpringPrevisto = null;
        sorgente = "ordine";

        view = VIA_ENTITY_CLASS.getAnnotation(AIView.class);
        assertNotNull(view);

        sortSpringPrevisto = Sort.by(Sort.Direction.ASC, sorgente);
        sortSpringOttenuto = service.getSortSpring(VIA_ENTITY_CLASS);
        assertEquals(sortSpringPrevisto, sortSpringOttenuto);

        sortSpringOttenuto = service.getSortSpring(VIA_LIST_CLASS);
        assertNull(sortSpringOttenuto);

        sortSpringPrevisto = Sort.by(Sort.Direction.DESC, sorgente);
        sortSpringOttenuto = service.getSortSpring(ANNO_ENTITY_CLASS);
        assertEquals(sortSpringPrevisto, sortSpringOttenuto);
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