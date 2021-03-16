package it.algos.integration;

import it.algos.simple.*;
import it.algos.unit.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.service.*;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 16-mar-2021
 * Time: 11:31
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Text Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AClassServiceTest extends ATest {

    /**
     * The Service.
     */
    @InjectMocks
    AClassService service;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();

        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        Assertions.assertNotNull(service);

        service.logger = logger;
        service.fileService = file;
        service.text = text;
        service.annotation = annotation;
    }


    @BeforeEach
    void setUpEach() {
    }


    @Test
    @Order(1)
    @DisplayName("getLogicListClassFromEntityClazz")
    void getLogicListClassFromEntityClazz() {
        sorgente = "black";
        System.out.println(VUOTA);
        System.out.println(VUOTA);

        ottenutoClasse = service.getLogicListClassFromEntityClazz(null);
        Assert.assertNull(ottenutoClasse);

        ottenutoClasse = service.getLogicListClassFromEntityClazz(ANNO_LOGIC_LIST);
        Assert.assertNull(ottenutoClasse);

        previstoClasse = ViaLogicList.class;
        ottenutoClasse = service.getLogicListClassFromEntityClazz(VIA_ENTITY_CLASS);
        Assert.assertNotNull(ottenutoClasse);
        Assert.assertEquals(previstoClasse, ottenutoClasse);
        System.out.println("getLogicListClassFromEntityClazz: " + VIA_ENTITY_CLASS.getSimpleName() + " -> " + ottenutoClasse.getSimpleName());

        ottenutoClasse = service.getLogicListClassFromEntityClazz(BOLLA_ENTITY_CLASS);
        Assert.assertNull(ottenutoClasse);


    System.out.println(VUOTA);
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
