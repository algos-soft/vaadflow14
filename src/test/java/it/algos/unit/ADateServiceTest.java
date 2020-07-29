package it.algos.unit;

import it.algos.vaadflow14.backend.enumeration.AETime;
import it.algos.vaadflow14.backend.service.ADateService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: sab, 06-giu-2020
 * Time: 19:18
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("ADateServiceTest")
@DisplayName("Unit test sulle date")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ADateServiceTest extends ATest {




    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();
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
    @DisplayName("Data corrente in formato standard")
    void get() {
        ottenuto = date.get();
        Assertions.assertNotNull(ottenuto);
        System.out.println(ottenuto);
    }


    @Test
    @Order(2)
    @DisplayName("Altre date in formato standard")
    void get2() {
        ottenuto = date.get(LOCAL_DATE_UNO);
        Assertions.assertNotNull(ottenuto);
        System.out.println(ottenuto);

        ottenuto = date.get(LOCAL_DATE_DUE);
        Assertions.assertNotNull(ottenuto);
        System.out.println(ottenuto);

        ottenuto = date.get(LOCAL_DATE_TRE);
        Assertions.assertNotNull(ottenuto);
        System.out.println(ottenuto);

        ottenuto = date.get(LOCAL_DATE_QUATTRO);
        Assertions.assertNotNull(ottenuto);
        System.out.println(ottenuto);

        ottenuto = date.get(LOCAL_DATE_VUOTA);
        Assertions.assertNotNull(ottenuto);
        System.out.println(ottenuto);

        ottenuto = date.get(LOCAL_DATE_PRIMO_VALIDO);
        Assertions.assertNotNull(ottenuto);
        System.out.println(ottenuto);
    }


    @Test
    @Order(3)
    @DisplayName("Date con pattern variabili")
    void get3() {
        System.out.println("Date con pattern variabili");
        String tag = ": ";

        for (AETime pattern : AETime.values()) {
            ottenuto = date.get(LOCAL_DATE_UNO, pattern.getPattern());
            System.out.println(pattern + tag + ottenuto);
        }
    }


    @Test
    @Order(4)
    @DisplayName("Costruisce tutti i giorni dell'anno")
    void getAllGiorni() {
        System.out.println("Costruisce tutti i giorni dell'anno");
        String sep=" - ";

        List<HashMap> ottenutoGiorni = date.getAllGiorni();

        for (HashMap mappa : ottenutoGiorni) {
            System.out.print(mappa.get(KEY_MAPPA_GIORNI_NOME));
            System.out.print(sep);
            System.out.print(mappa.get(KEY_MAPPA_GIORNI_NORMALE));
            System.out.print(sep);
            System.out.print(mappa.get(KEY_MAPPA_GIORNI_BISESTILE));
            System.out.println(VUOTA);
        }
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa ad termine di ogni singolo test <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}