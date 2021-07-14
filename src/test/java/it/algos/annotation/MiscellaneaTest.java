package it.algos.annotation;

import it.algos.test.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 30-giu-2021
 * Time: 17:18
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("MiscellaneaTest")
@DisplayName("Annotation variee")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MiscellaneaTest extends ATest {

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

        service.text = text;
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
    @DisplayName("1 - getCollectionName")
    void getCollectionName() {
        previsto = "via";

        ottenuto = service.getCollectionName(VIA_ENTITY_CLASS);
        assertEquals(previsto, ottenuto);

        Via via = new Via();
        ottenuto = service.getCollectionName(via.getClass());
        assertEquals(previsto, ottenuto);
    }

}
