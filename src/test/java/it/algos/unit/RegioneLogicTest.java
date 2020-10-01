package it.algos.unit;

import it.algos.vaadflow14.backend.service.AFileService;
import it.algos.vaadflow14.backend.service.AResourceService;
import it.algos.vaadflow14.backend.service.AWikiService;
import it.algos.vaadflow14.backend.wrapper.WrapDueStringhe;
import it.algos.vaadflow14.backend.wrapper.WrapTreStringhe;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 30-set-2020
 * Time: 20:56
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("RegioneLogicTest")
@DisplayName("Test di unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegioneLogicTest extends ATest {

    private static final String ISO = "ISO 3166-2:";

    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    AWikiService service;

    @InjectMocks
    AResourceService resource;

    @InjectMocks
    AFileService fileService;

    private List<String> listaAlfaDue;

    private List<List<String>> listaGrezza;

    private List<WrapDueStringhe> listaWrap;

    private List<WrapTreStringhe> listaWrapTre;

    private WrapDueStringhe dueStringhe;


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
        MockitoAnnotations.initMocks(resource);
        Assertions.assertNotNull(resource);
        MockitoAnnotations.initMocks(fileService);
        Assertions.assertNotNull(fileService);
        service.text = text;
        service.array = array;
        service.web = web;
        service.logger = logger;
        resource.fileService = fileService;
        fileService.logger = logger;

        creazioneLista();
    }


    /**
     * Qui passa ad ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    void setUpEach() {
        super.setUp();

        listaGrezza = null;
        listaWrap = null;
        listaWrapTre = null;
        dueStringhe = null;
    }


    /**
     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
     */
    private void creazioneLista() {
        String[] parti = null;
        ottenuto = resource.leggeConfig("3166-2");
        parti = ottenuto.split(A_CAPO);
        if (parti != null && parti.length > 0) {
            listaAlfaDue = new ArrayList<>();
            for (String riga : parti) {
                riga = text.estrae(riga, QUADRE_INI, QUADRE_END);
                riga = text.levaTestaDa(riga, PIPE);
                listaAlfaDue.add(riga);
            }
        }
    }


    @Test
    @Order(1)
    @DisplayName("1 - confronto prima riga")
    void getTable() {
        int max = 15;
        int pos = 0;
        String alfaDue;

        for (int k = 10; k < max; k++) {
            alfaDue = listaAlfaDue.get(k);
            sorgente = ISO + alfaDue;
            try {
                listaGrezza = service.getTable(sorgente, 1, 1);
                if (array.isValid(listaGrezza)) {
                    System.out.println(listaGrezza.get(0));
                    System.out.println(listaGrezza.get(1));
                    System.out.println(VUOTA);
                } else {
                    System.out.println("La pagina wiki " + sorgente + " non contiene nessuna wikitable");
                }
            } catch (Exception unErrore) {
                //                System.out.println("Non ho trovato la pagina "+sorgente);
            }
        }
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