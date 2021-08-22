package it.algos.integration;

import it.algos.simple.SimpleApplication;
import it.algos.simple.backend.enumeration.AESimplePreferenza;
import it.algos.test.ATest;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.packages.preferenza.PreferenzaService;
import it.algos.vaadflow14.backend.service.DateService;
import it.algos.vaadflow14.backend.service.MongoService;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.algos.simple.backend.application.SimpleCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 08-dic-2020
 * Time: 07:32
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Text Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APreferenzaServiceTest extends ATest {

    /**
     * The Service.
     */
    @InjectMocks
    PreferenzaService pref;

    /**
     * The Service.
     */
    @Autowired
    MongoService mongo;

    /**
     * The Service.
     */
    @Autowired
    DateService date;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();

        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(pref);
        Assertions.assertNotNull(pref);
        MockitoAnnotations.initMocks(mongo);
        Assertions.assertNotNull(mongo);
        MockitoAnnotations.initMocks(date);
        Assertions.assertNotNull(date);
        pref.mongo = mongo;
    }


    @BeforeEach
    void setUpEach() {
    }


    @Test
    @Order(1)
    @DisplayName("Valore da AEPreferenza")
    public void aEPreferenza() {
        previsto = "gac@algos.it";
        sorgente = previsto;

        ottenuto = (String) AEPreferenza.mailTo.getValue();
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);
    }

    @Test
    @Order(2)
    @DisplayName("Valore da FlowCost")
    public void flowCost() {
        previsto = "gac@algos.it";
        sorgente = previsto;

        ottenuto = (String) pref.getValue(FlowCost.PREF_EMAIL);
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);
    }

    @Test
    @Order(3)
    @DisplayName("Valore da 'code'")
    public void code() {
        previsto = "gac@algos.it";
        sorgente = previsto;

        ottenuto = (String) pref.getValue("email");
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);
    }

    @Test
    @Order(4)
    @DisplayName("Stringa da AEPreferenza'")
    public void stringa() {
        previsto = "gac@algos.it";
        ottenuto = AEPreferenza.mailTo.getStr();
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        previsto = "false";
        ottenuto = AEPreferenza.usaDebug.getStr();
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        previsto = "edit";
        ottenuto = AEPreferenza.iconaEdit.getStr();
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        previsto = "3";
        ottenuto = AEPreferenza.maxEnumRadio.getStr();
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        previsto = date.get(DATA);
        ottenuto = AESimplePreferenza.datauno.getStr();
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        previsto = date.get(DATA_TIME);
        ottenuto = AESimplePreferenza.datadue.getStr();
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        previsto = date.get(TIME);
        ottenuto = AESimplePreferenza.timeuno.getStr();
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);
    }

    @Test
    @Order(5)
    @DisplayName("Boolean da AEPreferenza'")
    public void booleano() {
        ottenutoBooleano = AEPreferenza.mailTo.is();
        Assert.assertFalse(ottenutoBooleano);

        ottenutoBooleano = AEPreferenza.usaGridHeaderMaiuscola.is();
        Assert.assertTrue(ottenutoBooleano);

        ottenutoBooleano = AEPreferenza.maxEnumRadio.is();
        Assert.assertFalse(ottenutoBooleano);
    }

    @Test
    @Order(6)
    @DisplayName("Intero da AEPreferenza'")
    public void intero() {

        previstoIntero = 3;
        ottenutoIntero = AEPreferenza.maxEnumRadio.getInt();
        Assert.assertEquals(previstoIntero, ottenutoIntero);

        previstoIntero = 0;
        ottenutoIntero = AEPreferenza.usaGridHeaderMaiuscola.getInt();
        Assert.assertEquals(previstoIntero, ottenutoIntero);
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
