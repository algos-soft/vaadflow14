package it.algos.integration;

import it.algos.simple.*;
import it.algos.simple.backend.packages.bolla.*;
import it.algos.simple.backend.packages.fattura.*;
import it.algos.test.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
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

        protected static Class<? extends AEntity> FATTURA_ENTITY_CLASS = Fattura.class;

        protected static Class<? extends AEntity> BOLLA_ENTITY_CLASS = Bolla.class;

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
        System.out.println(VUOTA);
        System.out.println(VUOTA);

        ottenutoClasse = service.getLogicListClassFromEntityClazz(null);
        Assert.assertNull(ottenutoClasse);

        ottenutoClasse = service.getLogicListClassFromEntityClazz(ANNO_LOGIC_LIST);
        Assert.assertNull(ottenutoClasse);

        previstoClasse = FatturaLogicList.class;
        previsto = previstoClasse.getSimpleName();
        ottenutoClasse = service.getLogicListClassFromEntityClazz(FATTURA_ENTITY_CLASS);
        Assert.assertNotNull(ottenutoClasse);
        Assert.assertEquals(previstoClasse, ottenutoClasse);
        Assert.assertEquals(previsto, ottenutoClasse.getSimpleName());
        System.out.println("getLogicListClassFromEntityClazz: " + FATTURA_ENTITY_CLASS.getSimpleName() + " -> " + ottenutoClasse.getSimpleName());

        ottenutoClasse = service.getLogicListClassFromEntityClazz(BOLLA_ENTITY_CLASS);
        Assert.assertNull(ottenutoClasse);

        ottenutoBooleano = service.isLogicListClassFromEntityClazz(FATTURA_ENTITY_CLASS);
        Assert.assertTrue(ottenutoBooleano);
        System.out.println("isLogicListClassFromEntityClazz: " + FATTURA_ENTITY_CLASS.getSimpleName() + " -> " + ottenutoBooleano);

        ottenutoBooleano = service.isLogicListClassFromEntityClazz(BOLLA_ENTITY_CLASS);
        Assert.assertFalse(ottenutoBooleano);
        System.out.println("isLogicListClassFromEntityClazz: " + BOLLA_ENTITY_CLASS.getSimpleName() + " -> " + ottenutoBooleano);

        System.out.println(VUOTA);
    }


    @Test
    @Order(2)
    @DisplayName("getLogicFormClassFromEntityClazz")
    void getLogicFormClassFromEntityClazz() {
        ottenutoClasse = service.getLogicFormClassFromEntityClazz(null);
        Assert.assertNull(ottenutoClasse);

        ottenutoClasse = service.getLogicFormClassFromEntityClazz(ANNO_LOGIC_LIST);
        Assert.assertNull(ottenutoClasse);

        previstoClasse = FatturaLogicForm.class;
        previsto = previstoClasse.getSimpleName();
        ottenutoClasse = service.getLogicFormClassFromEntityClazz(FATTURA_ENTITY_CLASS);
        Assert.assertNotNull(ottenutoClasse);
        Assert.assertEquals(previstoClasse, ottenutoClasse);
        Assert.assertEquals(previsto, ottenutoClasse.getSimpleName());
        System.out.println("getLogicFormClassFromEntityClazz: " + FATTURA_ENTITY_CLASS.getSimpleName() + " -> " + ottenutoClasse.getSimpleName());
//
//        ottenutoClasse = service.getLogicFormClassFromEntityClazz(BOLLA_ENTITY_CLASS);
//        Assert.assertNull(ottenutoClasse);

        ottenutoBooleano = service.isLogicFormClassFromEntityClazz(FATTURA_ENTITY_CLASS);
        Assert.assertTrue(ottenutoBooleano);
        System.out.println("isLogicFormClassFromEntityClazz: " + FATTURA_ENTITY_CLASS.getSimpleName() + " -> " + ottenutoBooleano);

        ottenutoBooleano = service.isLogicFormClassFromEntityClazz(BOLLA_ENTITY_CLASS);
        Assert.assertFalse(ottenutoBooleano);
        System.out.println("isLogicFormClassFromEntityClazz: " + BOLLA_ENTITY_CLASS.getSimpleName() + " -> " + ottenutoBooleano);

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
