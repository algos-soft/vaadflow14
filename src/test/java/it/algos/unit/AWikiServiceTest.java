package it.algos.unit;

import it.algos.vaadflow14.backend.service.AWikiService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 20:25
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("AWikiServiceTest")
@DisplayName("Gestione dei collegamenti con wikipedia")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AWikiServiceTest extends ATest {

    public static final String PAGINA_PIOZZANO = "Piozzano";

    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    AWikiService service;

    private List<List<String>> listaGrezza;


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
        service.web = web;
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
    }


    @Test
    @Order(1)
    @DisplayName("1 - legge testo grezzo html")
    public void getSorgente() {
        sorgente = PAGINA_PIOZZANO;
        ottenuto = service.getSorgente(sorgente);
        assertNotNull(ottenuto);
        System.out.println("Legge il testo grezzo della pagina html. Non lo faccio vedere perché troppo lungo");
    }


    @Test
    @Order(2)
    @DisplayName("2 - legge testo wiki")
    public void legge() {
        sorgente = PAGINA_PIOZZANO;
        previsto = "{{Divisione amministrativa\n" + "|Nome=Piozzano";
        ottenuto = service.legge(sorgente);
        assertTrue(text.isValid(ottenuto));
        assertTrue(ottenuto.startsWith(previsto));
        System.out.println("Legge il testo wiki della pagina. Non lo faccio vedere perché troppo lungo");
    }


    @Test
    @Order(3)
    @DisplayName("3 - legge una table wiki")
    public void leggeTable() {
        sorgente = "ISO 3166-2:IT";
        previsto = "{| class=\"wikitable sortable\"";
        ottenuto = service.leggeTable(sorgente);
        assertTrue(text.isValid(ottenuto));
        assertTrue(ottenuto.startsWith(previsto));
        System.out.println("Legge una tabella wiki completa");
        System.out.println("");
        System.out.println(ottenuto);
    }


    @Test
    @Order(4)
    @DisplayName("4 - legge le righe delle regioni")
    public void getTableRegioni() {
        sorgente = "ISO 3166-2:IT";

        //--regioni
        previsto = "<code>IT-65</code>";
        previsto2 = "{{bandiera|Abruzzo|nome}}";
        listaGrezza = service.getTable(sorgente);
        assertNotNull(listaGrezza);
        assertTrue(listaGrezza.size() > 0);
        assertEquals(previsto, listaGrezza.get(0).get(0));
        assertEquals(previsto2, listaGrezza.get(0).get(1));
        System.out.println("Legge le righe di una tabella wiki");
        System.out.println("");
        System.out.println("Regioni: " + listaGrezza.size());
        System.out.println("*******");
        print(listaGrezza);
    }


    @Test
    @Order(5)
    @DisplayName("5 - legge le righe delle province")
    public void getTableProvince() {
        sorgente = "ISO 3166-2:IT";

        //--province
        previsto = "<code>IT-AG</code>";
        previsto2 = "[[Sicilia]] (<code>82</code>)";
        listaGrezza = service.getTable(sorgente, 3);
        assertNotNull(listaGrezza);
        assertTrue(listaGrezza.size() > 0);
        assertEquals(previsto, listaGrezza.get(0).get(0));
        assertEquals(previsto2, listaGrezza.get(0).get(2));
        System.out.println("");
        System.out.println("Province: " + listaGrezza.size());
        System.out.println("********");
        print(listaGrezza);
    }


    @Test
    @Order(6)
    @DisplayName("6 - legge le righe della Francia")
    public void getTableFrancia() {
        sorgente = "ISO_3166-2:FR";
        listaGrezza = service.getTable(sorgente);
        assertNotNull(listaGrezza);
        assertTrue(listaGrezza.size() > 0);
        System.out.println("");
        System.out.println("Francia: " + listaGrezza.size());
        System.out.println("********");
        print(listaGrezza);
    }


    @Test
    @Order(7)
    @DisplayName("7 - legge le righe della Svizzera")
    public void getTableSvizzera() {
        sorgente = "Cantoni della Svizzera";
        listaGrezza = service.getTable(sorgente, 1, 1);
        assertNotNull(listaGrezza);
        assertTrue(listaGrezza.size() > 0);
        System.out.println("");
        System.out.println("Svizzera: " + listaGrezza.size());
        System.out.println("********");
        print(listaGrezza);
    }


    @Test
    @Order(8)
    @DisplayName("8 - legge le righe della Austria")
    public void getTableAustria() {
        sorgente = "Stati federati dell'Austria";
        listaGrezza = service.getTable(sorgente, 1, 1);
        assertNotNull(listaGrezza);
        assertTrue(listaGrezza.size() > 0);
        System.out.println("");
        System.out.println("Austria: " + listaGrezza.size());
        System.out.println("********");
        print(listaGrezza);
    }


    @Test
    @Order(8)
    @DisplayName("8 - legge le righe della Germania")
    public void getTableGermania() {
        sorgente = "Stati federati della Germania";
        listaGrezza = service.getTable(sorgente, 1, 1);
        assertNotNull(listaGrezza);
        assertTrue(listaGrezza.size() > 0);
        System.out.println("");
        System.out.println("Germania: " + listaGrezza.size());
        System.out.println("********");
        print(listaGrezza);
    }


    @Test
    @Order(9)
    @DisplayName("9 - legge le righe della Spagna")
    public void getTableSpagna() {
        sorgente = "Comunità autonome della Spagna";
        listaGrezza = service.getTable(sorgente, 1, 2);
        assertNotNull(listaGrezza);
        assertTrue(listaGrezza.size() > 0);
        System.out.println("");
        System.out.println("Spagna: " + listaGrezza.size());
        System.out.println("********");
        print(listaGrezza);
    }


    private void print(List<List<String>> listaTable) {
        if (array.isValid(listaTable)) {
            for (List<String> lista : listaTable) {
                if (array.isValid(lista)) {
                    for (String stringa : lista) {
                        System.out.println(stringa);
                    }
                }
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