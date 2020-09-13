package it.algos.unit;

import it.algos.vaadflow14.backend.service.AWikiService;
import it.algos.vaadflow14.backend.wrapper.WrapDueStringhe;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
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

    private List<WrapDueStringhe> listaWrap;

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
        listaWrap = null;
        dueStringhe = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - legge testo grezzo html")
    public void getSorgente() {
        sorgente = PAGINA_PIOZZANO;
        ottenuto = service.getSorgente(sorgente);
        assertNotNull(ottenuto);
        System.out.println("1 - Legge il testo grezzo della pagina html. Non lo faccio vedere perché troppo lungo");
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
        System.out.println("2 - Legge il testo wiki della pagina. Non lo faccio vedere perché troppo lungo");
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
        System.out.println("3 - Legge una tabella wiki completa");
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @Test
    @Order(4)
    @DisplayName("4 - legge un template")
    public void getTemplateBandierina() {
        sorgente = "ES-AN";
        dueStringhe = service.getTemplateBandierina(sorgente);
        assertNotNull(dueStringhe);
        System.out.println(VUOTA);
        System.out.println(sorgente + FORWARD + dueStringhe.getPrima() + SEP + dueStringhe.getSeconda());

        sorgente = "{{ES-CB}}";
        dueStringhe = service.getTemplateBandierina(sorgente);
        assertNotNull(dueStringhe);
        System.out.println(VUOTA);
        System.out.println(sorgente + FORWARD + dueStringhe.getPrima() + SEP + dueStringhe.getSeconda());
    }


    @Test
    @Order(5)
    @DisplayName("5 - legge una colonna")
    public void getColonna() {
        sorgente = "ISO_3166-2:ES";
        lista = service.getColonna(sorgente, 1, 2, 2);
        assertNotNull(lista);
        System.out.println(VUOTA);
        System.out.println("5 - Template Spagna: " + lista.size());
        System.out.println(VUOTA);
        printColonna(lista);
    }


    //    @Test
    @Order(6)
    @DisplayName("6 - legge una lista di template")
    public void getTemplateBandierine() {
        sorgente = "ISO_3166-2:ES";
        previstoIntero = 17;
        lista = service.getColonna(sorgente, 1, 2, 2);
        assertNotNull(lista);
        assertEquals(previstoIntero, lista.size());
        listaWrap = service.getTemplateList(lista);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("6 - Spagna: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(7)
    @DisplayName("7 - legge una coppia di colonne da una table")
    public void getDueColonne() {
        sorgente = "13 - Comunità autonome della Spagna";
        previstoIntero = 17;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 2, 11);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println("7 - Spagna: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(8)
    @DisplayName("8 - legge le righe delle regioni italiane")
    public void getTableRegioni() {
        sorgente = "ISO 3166-2:IT";

        //--regioni
        previstoIntero = 20;
        previsto = "<code>IT-65</code>";
        previsto2 = "{{bandiera|Abruzzo|nome}}";
        listaGrezza = service.getTable(sorgente);
        assertNotNull(listaGrezza);
        assertEquals(previstoIntero, listaGrezza.size());
        assertEquals(previsto, listaGrezza.get(0).get(0));
        assertEquals(previsto2, listaGrezza.get(0).get(1));
        System.out.println("Legge le righe di una tabella wiki");
        System.out.println(VUOTA);
        System.out.println("8 - Regioni: " + listaGrezza.size());
        System.out.println("*******");
        print(listaGrezza);
    }


    //    @Test
    @Order(9)
    @DisplayName("9 - legge le righe delle province")
    public void getTableProvince() {
        sorgente = "ISO 3166-2:IT";

        //--province
        previstoIntero = 93;
        previsto = "<code>IT-AG</code>";
        previsto2 = "[[Sicilia]] (<code>82</code>)";
        listaGrezza = service.getTable(sorgente, 3);
        assertNotNull(listaGrezza);
        assertEquals(previstoIntero, listaGrezza.size());
        assertEquals(previsto, listaGrezza.get(0).get(0));
        assertEquals(previsto2, listaGrezza.get(0).get(2));
        System.out.println(VUOTA);
        System.out.println("9 - Province: " + listaGrezza.size());
        System.out.println("********");
        print(listaGrezza);
    }


    //    @Test
    @Order(10)
    @DisplayName("10 - legge le regioni della Francia")
    public void getTableFrancia() {
        sorgente = "ISO_3166-2:FR";
        previstoIntero = 13;
        listaWrap = service.getTemplateList(sorgente, 1, 2, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("10 - Francia: " + listaWrap.size());
        printWrap(listaWrap);

        previstoIntero = 3;
        listaWrap = service.getDueColonne(sorgente, 3, 2, 2, 4);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("10 - Francia: " + listaWrap.size());
        printWrap(listaWrap);

        previstoIntero = 9;
        listaWrap = service.getDueColonne(sorgente, 4, 2, 1, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("10 - Francia: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(11)
    @DisplayName("11 - legge i cantoni della Svizzera")
    public void getTableSvizzera() {
        sorgente = "ISO_3166-2:CH";
        previstoIntero = 26;
        listaWrap = service.getTemplateList(sorgente, 1, 2, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("11 - Svizzera: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(12)
    @DisplayName("12 - legge i lander della Austria")
    public void getTableAustria() {
        sorgente = "ISO_3166-2:AT";
        previstoIntero = 9;
        listaWrap = service.getTemplateList(sorgente, 1, 2, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("12 - Austria: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //        @Test
    @Order(13)
    @DisplayName("13 - legge i lander della Germania")
    public void getTableGermania() {
        sorgente = "ISO_3166-2:DE";
        previstoIntero = 16;
        listaWrap = service.getTemplateList(sorgente, 1, 2, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("13 - Germania: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(14)
    @DisplayName("14 - legge le comunità della Spagna")
    public void getTableSpagna() {
        sorgente = "ISO_3166-2:ES";
        previstoIntero = 17;
        listaWrap = service.getTemplateList(sorgente, 1, 2, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("14 - Spagna: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(15)
    @DisplayName("15 - legge i distretti del Portogallo")
    public void getTablePortogallo() {
        sorgente = "ISO_3166-2:PT";
        previstoIntero = 18;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 2, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("15 - Portogallo: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(16)
    @DisplayName("16 - legge i comuni della Slovenia")
    public void getTableSlovenia() {
        sorgente = "ISO_3166-2:SI";
        previstoIntero = 211;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 1, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("16 - Slovenia: " + listaWrap.size());
        printWrap(listaWrap);
    }


//    @Test
    @Order(17)
    @DisplayName("17 - legge i comuni del Belgio")
    public void getTableBelgio() {
        sorgente = "ISO_3166-2:BE";
        previstoIntero = 3;
        listaWrap = service.getTemplateList(sorgente, 1, 2, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("17 - Belgio: " + listaWrap.size());
        printWrap(listaWrap);
    }

//    @Test
    @Order(18)
    @DisplayName("18 - legge le province dell'Olanda")
    public void getTableOlanda() {
        sorgente = "ISO_3166-2:NL";
        previstoIntero = 12;
        listaWrap = service.getTemplateList(sorgente, 1, 2, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("18 - Olanda: " + listaWrap.size());
        printWrap(listaWrap);
    }

    @Test
    @Order(19)
    @DisplayName("19 - legge le province della Croazia")
    public void getTableCroazia() {
        sorgente = "ISO_3166-2:HR";
        previstoIntero = 21;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 1, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("19 - Croazia: " + listaWrap.size());
        printWrap(listaWrap);
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


    private void printColonna(List<String> listaColonna) {
        if (array.isValid(listaColonna)) {
            for (String stringa : lista) {
                System.out.println(stringa);
            }
        }
    }


    private void printWrap(List<WrapDueStringhe> listaWrap) {
        System.out.println("********");
        if (array.isValid(listaWrap)) {
            for (WrapDueStringhe wrap : listaWrap) {
                System.out.println(wrap.getPrima() + SEP + wrap.getSeconda());
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