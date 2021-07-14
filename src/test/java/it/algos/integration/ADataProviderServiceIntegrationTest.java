package it.algos.integration;

import com.vaadin.flow.component.html.Label;
import it.algos.simple.SimpleApplication;
import it.algos.test.*;
import it.algos.vaadflow14.backend.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 10-mag-2021
 * Time: 19:37
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Text Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ADataProviderServiceIntegrationTest extends ATest {

    /**
     * The Service.
     */
    @InjectMocks
    DataProviderService service;


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
    }


    @Test
    @Order(1)
    @DisplayName("Primo test")
    void getLabelHost() {
        sorgente = "black";
        Label label = text.getLabelAdmin(sorgente);
        Assertions.assertNotNull(label);
        System.out.println(label.getText());
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
