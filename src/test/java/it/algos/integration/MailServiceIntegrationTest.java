package it.algos.integration;

import com.vaadin.flow.component.html.Label;
import it.algos.simple.SimpleApplication;
import it.algos.test.*;
import it.algos.vaadflow14.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 04-mar-2022
 * Time: 10:22
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Mail Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MailServiceIntegrationTest extends ATest {

    /**
     * The Service.
     */
    @InjectMocks
    MailService service;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();

        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        Assertions.assertNotNull(service);
    }


    @BeforeEach
    void setUpEach() {
        super.setUp();
    }


    /**
     * Spedizione standard senza mittente e senza destinatario
     */
    @Test
    @Order(1)
    @DisplayName("Primo test")
    public void send() {
        sorgente = "Prova";
        sorgente2 = "Primo tentativo";

        ottenutoBooleano = service.send(sorgente, sorgente2);
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
