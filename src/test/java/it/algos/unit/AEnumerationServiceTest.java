package it.algos.unit;

import it.algos.vaadflow14.backend.service.AEnumerationService;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 30-ago-2020
 * Time: 10:23
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("AEnumeration")
@DisplayName("Test di unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AEnumerationServiceTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    AEnumerationService service;


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


    @Test
    @Order(1)
    @DisplayName("Matrice dei valori e del valore selezionato")
    void getParti() {
        sorgente = "alfa,beta,gamma;delta";
        previstoMatrice = new String[]{"alfa,beta,gamma", "delta"};
        ottenutoMatrice = service.getParti(sorgente);
        Assertions.assertNotNull(ottenutoMatrice);
        Assert.assertEquals(previstoMatrice, ottenutoMatrice);
        Assert.assertEquals(ottenutoMatrice.length, 2);
        Assert.assertEquals(ottenutoMatrice[0], "alfa,beta,gamma");
        Assert.assertEquals(ottenutoMatrice[1], "delta");

        sorgente = "alfa,beta,gamma";
        previstoMatrice = new String[]{"alfa,beta,gamma"};
        ottenutoMatrice = service.getParti(sorgente);
        Assertions.assertNotNull(ottenutoMatrice);
        Assert.assertEquals(previstoMatrice, ottenutoMatrice);
        Assert.assertEquals(ottenutoMatrice.length, 1);
        Assert.assertEquals(ottenutoMatrice[0], "alfa,beta,gamma");

        sorgente = "alfa";
        previstoMatrice = new String[]{"alfa"};
        ottenutoMatrice = service.getParti(sorgente);
        Assertions.assertNotNull(ottenutoMatrice);
        Assert.assertEquals(previstoMatrice, ottenutoMatrice);
        Assert.assertEquals(ottenutoMatrice.length, 1);
        Assert.assertEquals(ottenutoMatrice[0], "alfa");

        sorgente = "alfa,beta,gamma;delta,epsilon";
        previstoMatrice = new String[]{"alfa,beta,gamma", "delta,epsilon"};
        ottenutoMatrice = service.getParti(sorgente);
        Assertions.assertNotNull(ottenutoMatrice);
        Assert.assertEquals(previstoMatrice, ottenutoMatrice);
        Assert.assertEquals(ottenutoMatrice.length, 2);
        Assert.assertEquals(ottenutoMatrice[0], "alfa,beta,gamma");
        Assert.assertEquals(ottenutoMatrice[1], "delta,epsilon");

        sorgente = "alfa,beta,gamma;delta;epsilon";
        previstoMatrice = new String[]{"alfa,beta,gamma", "delta;epsilon"};
        ottenutoMatrice = service.getParti(sorgente);
        Assertions.assertNotNull(ottenutoMatrice);
        Assert.assertEquals(previstoMatrice, ottenutoMatrice);
        Assert.assertEquals(ottenutoMatrice.length, 2);
        Assert.assertEquals(ottenutoMatrice[0], "alfa,beta,gamma");
        Assert.assertEquals(ottenutoMatrice[1], "delta;epsilon");
    }


    @Test
    @Order(2)
    @DisplayName("Estrae la parte dopo il punto e virgola")
    void convertToPresentation() {
        sorgente = "alfa,beta,gamma;delta";
        previsto = "delta";
        ottenuto = service.convertToPresentation(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        sorgente = "alfa,beta,gamma";
        previsto = VUOTA;
        ottenuto = service.convertToPresentation(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        sorgente = "alfa";
        previsto = VUOTA;
        ottenuto = service.convertToPresentation(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        sorgente = "alfa,beta,gamma;delta,epsilon";
        previsto = "delta,epsilon";
        ottenuto = service.convertToPresentation(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        sorgente = "alfa,beta,gamma;delta;epsilon";
        previsto = "delta;epsilon";
        ottenuto = service.convertToPresentation(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(3)
    @DisplayName("Estrae la parte prima del punto e virgola")
    void getStringaValori() {
        sorgente = "alfa,beta,gamma;delta";
        previsto = "alfa,beta,gamma";
        ottenuto = service.getStringaValori(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        sorgente = "alfa,beta,gamma";
        previsto = "alfa,beta,gamma";
        ottenuto = service.getStringaValori(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        sorgente = "alfa";
        previsto = "alfa";
        ottenuto = service.getStringaValori(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        sorgente = "alfa,beta,gamma;delta,epsilon";
        previsto = "alfa,beta,gamma";
        ottenuto = service.getStringaValori(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        sorgente = "alfa,beta,gamma;delta;epsilon";
        previsto = "alfa,beta,gamma";
        ottenuto = service.getStringaValori(sorgente);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(4)
    @DisplayName("Estrae la lista dei valori")
    void getList() {
        previstoArray = new ArrayList<>(Arrays.asList("alfa", "beta", "gamma"));

        sorgente = "alfa,beta,gamma;delta";
        ottenutoArray = service.getList(sorgente);
        Assertions.assertNotNull(ottenutoArray);
        Assert.assertEquals(previstoArray, ottenutoArray);

        sorgente = "alfa,beta,gamma";
        ottenutoArray = service.getList(sorgente);
        Assertions.assertNotNull(ottenutoArray);
        Assert.assertEquals(previstoArray, ottenutoArray);

        sorgente = "alfa,beta,gamma;delta,epsilon";
        ottenutoArray = service.getList(sorgente);
        Assertions.assertNotNull(ottenutoArray);
        Assert.assertEquals(previstoArray, ottenutoArray);

        sorgente = "alfa,beta,gamma;delta;epsilon";
        ottenutoArray = service.getList(sorgente);
        Assertions.assertNotNull(ottenutoArray);
        Assert.assertEquals(previstoArray, ottenutoArray);

        previstoArray = new ArrayList<>(Arrays.asList("alfa"));
        sorgente = "alfa";
        ottenutoArray = service.getList(sorgente);
        Assertions.assertNotNull(ottenutoArray);
        Assert.assertEquals(previstoArray, ottenutoArray);
    }


    @Test
    @Order(5)
    @DisplayName("Costruisce la stringa da memorizzare")
    void convertToModel() {
        String rawValue;
        String newSelectedValue;

        rawValue = VUOTA;
        newSelectedValue = VUOTA;
        previsto = VUOTA;
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = VUOTA;
        newSelectedValue = "veloce";
        previsto = VUOTA;
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = "veloce";
        newSelectedValue = VUOTA;
        previsto = "veloce";
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = "veloce,lento";
        newSelectedValue = VUOTA;
        previsto = "veloce,lento";
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = "veloce,lento;";
        newSelectedValue = VUOTA;
        previsto = "veloce,lento";
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = "veloce,lento";
        newSelectedValue = "lento";
        previsto = "veloce,lento;lento";
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = "veloce,lento;";
        newSelectedValue = "lento";
        previsto = "veloce,lento;lento";
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = "veloce,lento;veloce";
        newSelectedValue = "lento";
        previsto = "veloce,lento;lento";
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = "veloce,lento";
        newSelectedValue = "adagio";
        previsto = "veloce,lento";
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = "veloce,lento;veloce";
        newSelectedValue = "adagio";
        previsto = "veloce,lento;veloce";
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);

        rawValue = "veloce,lento;";
        newSelectedValue = "adagio";
        previsto = "veloce,lento";
        ottenuto = service.convertToModel(rawValue, newSelectedValue);
        Assertions.assertNotNull(ottenuto);
        Assert.assertEquals(previsto, ottenuto);
    }

}