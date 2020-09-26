package it.algos.unit;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import it.algos.vaadflow14.backend.service.AResourceService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 24-set-2020
 * Time: 21:13
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("ResourceServiceTest")
@DisplayName("Test di unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AResourceServiceTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    AResourceService service;


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
        service.array = array;
        service.fileService = file;
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
    @DisplayName("1 - Legge nella directory 'config'")
    void leggeConfig() {
        previsto = "gac=aggiungere";

        sorgente = "config.password.txt";
        ottenuto = service.leggeConfig(sorgente);
        assertTrue(text.isEmpty(ottenuto));

        sorgente = "/config.password.txt";
        ottenuto = service.leggeConfig(sorgente);
        assertTrue(text.isEmpty(ottenuto));

        sorgente = "/config/password.txt";
        ottenuto = service.leggeConfig(sorgente);
        assertTrue(text.isEmpty(ottenuto));

        sorgente = "config/password.txt";
        ottenuto = service.leggeConfig(sorgente);
        assertTrue(text.isEmpty(ottenuto));

        sorgente = "password.txt";
        ottenuto = service.leggeConfig(sorgente);
        assertTrue(text.isValid(ottenuto));
        assertEquals(previsto, ottenuto);

        sorgente = "at.png";
        ottenuto = service.leggeConfig(sorgente);
        assertTrue(text.isValid(ottenuto));
    }


    @Test
    @Order(2)
    @DisplayName("2 - Legge nella directory 'frontend'")
    void leggeFrontend() {
        previsto = ".login-information";

        sorgente = "styles.shared-styles.css";
        ottenuto = service.leggeFrontend(sorgente);
        assertTrue(text.isEmpty(ottenuto));

        sorgente = "styles/shared-styles.css";
        ottenuto = service.leggeFrontend(sorgente);
        assertTrue(text.isValid(ottenuto));
        assertTrue(ottenuto.startsWith(previsto));
    }


    @Test
    @Order(3)
    @DisplayName("3 - Legge nella directory META-INF")
    void leggeMetaInf() {
        sorgente = "rainbow.png";
        ottenuto = service.leggeMetaInf(sorgente);
        assertTrue(text.isEmpty(ottenuto));

        sorgente = "img.rainbow.png";
        ottenuto = service.leggeMetaInf(sorgente);
        assertTrue(text.isEmpty(ottenuto));

        sorgente = "img/rainbow.png";
        ottenuto = service.leggeMetaInf(sorgente);
        assertTrue(text.isValid(ottenuto));

        sorgente = "bandiere/at.png";
        ottenuto = service.leggeMetaInf(sorgente);
        assertTrue(text.isValid(ottenuto));
    }


    @Test
    @Order(4)
    @DisplayName("4 - Legge i bytes[]")
    void getBytes() {
        sorgente = "rainbow.png";
        bytes = service.getBytes(sorgente);
        assertNull(bytes);

        sorgente = "img/rainbow.png" ;
        bytes = service.getBytes(sorgente);
        assertNotNull(bytes);

        sorgente = "src/main/resources/META-INF/resources/img/rainbow.png" ;
        bytes = service.getBytes(sorgente);
        assertNotNull(bytes);

        sorgente="bandiere/ca.png";
        bytes = service.getBytes(sorgente);
        assertNotNull(bytes);

        sorgente = "src/main/resources/META-INF/resources/bandiere/ca.png";
        bytes = service.getBytes(sorgente);
        assertNotNull(bytes);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Legge le risorse")
    void getSrc() {
        StreamResource resource;

        sorgente = "rainbow.png";
        ottenuto = service.getSrc(sorgente);
        assertNull(ottenuto);

        sorgente = "src/main/resources/META-INF/resources/img/"  + sorgente;
        ottenuto = service.getSrc(sorgente);
        assertNotNull(ottenuto);

        sorgente="ca.png";
        sorgente = "src/main/resources/META-INF/resources/bandiere/"  + sorgente;
        ottenuto = service.getSrc(sorgente);
        assertNotNull(ottenuto);
    }


//    @Test
//    @Order(6)
//    @DisplayName("6 - Costruisce una Image da un file")
//    void getImage() {
//        Image image;
//
//        sorgente = "rainbow.png";
//        image = service.getImage(sorgente);
//        assertNull(image);
//
////        sorgente = "src/main/resources/META-INF/resources/img/"  + sorgente;
////        image = service.getImage(sorgente);
////        assertNotNull(image);
//
//        sorgente="ca.png";
//        sorgente = "src/main/resources/META-INF/resources/bandiere/"  + sorgente;
//        image = service.getImage(sorgente);
//        assertNotNull(image);
//    }


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