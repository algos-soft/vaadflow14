package it.algos.unit;

import it.algos.vaadflow14.backend.service.AWikiService;
import it.algos.vaadflow14.backend.wrapper.WrapDueStringhe;
import it.algos.vaadflow14.backend.wrapper.WrapTreStringhe;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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
        listaWrapTre = null;
        dueStringhe = null;
    }


    //    @Test
    @Order(1)
    @DisplayName("1 - legge testo grezzo html")
    public void getSorgente() {
        sorgente = PAGINA_PIOZZANO;
        ottenuto = service.getSorgente(sorgente);
        assertNotNull(ottenuto);
        System.out.println("1 - Legge il testo grezzo della pagina html. Non lo faccio vedere perché troppo lungo");
    }


    //    @Test
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


    //    @Test
    @Order(3)
    @DisplayName("3 - legge una table wiki")
    public void leggeTable() {
        sorgente = "ISO 3166-2:IT";
        previsto = "{| class=\"wikitable sortable\"";

        //--regione
        ottenuto = service.leggeTable(sorgente);
        assertTrue(text.isValid(ottenuto));
        assertTrue(ottenuto.startsWith(previsto));
        System.out.println("3 - Legge una tabella wiki completa");
        System.out.println(VUOTA);
        System.out.println(ottenuto);

        //--provincia
        try {
            ottenuto = service.leggeTable(sorgente, 2);
        } catch (Exception unErrore) {
        }

        assertTrue(text.isValid(ottenuto));
        assertTrue(ottenuto.startsWith(previsto));
        System.out.println(VUOTA);
        System.out.println("3 - Legge una tabella wiki completa");
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    //    @Test
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


    //    @Test
    @Order(5)
    @DisplayName("5 - legge una colonna")
    public void getColonna() {
        sorgente = "ISO_3166-2:ES";
        listaStr = service.getColonna(sorgente, 1, 2, 2);
        assertNotNull(listaStr);
        System.out.println(VUOTA);
        System.out.println("5 - Template Spagna: " + listaStr.size());
        System.out.println(VUOTA);
        printColonna(listaStr);

        sorgente = "ISO_3166-2:IT";
        listaStr = service.getColonna(sorgente, 2, 2, 2);
        assertNotNull(listaStr);
        System.out.println(VUOTA);
        System.out.println("5 - province: " + listaStr.size());
        System.out.println(VUOTA);
        printColonna(listaStr);
    }


    //    @Test
    @Order(6)
    @DisplayName("6 - legge una lista di template")
    public void getTemplateBandierine() {
        sorgente = "ISO_3166-2:ES";
        previstoIntero = 18;
        listaStr = service.getColonna(sorgente, 1, 2, 2);
        assertNotNull(listaStr);
        assertEquals(previstoIntero, listaStr.size());
        listaWrap = service.getTemplateList(listaStr);
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
        sorgente = "ISO_3166-2:ES";
        previstoIntero = 18;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 2, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println("7 - Spagna: " + listaWrap.size());
        printWrap(listaWrap);

        sorgente = "ISO_3166-2:IT";
        previstoIntero = 15;
        listaWrap = service.getDueColonne(sorgente, 2, 2, 1, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("7 - Province (capoluogo): " + listaWrap.size());
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
        try {
            listaGrezza = service.getTable(sorgente);
        } catch (Exception unErrore) {
        }
        assertNotNull(listaGrezza);
        assertEquals(previstoIntero, listaGrezza.size());
        assertEquals(previsto, listaGrezza.get(0).get(0));
        assertEquals(previsto2, listaGrezza.get(0).get(1));
        System.out.println("Legge le righe di una tabella wiki");
        System.out.println(VUOTA);
        System.out.println("8 - Regioni: " + listaGrezza.size());
        System.out.println("*******");
        printList(listaGrezza);
    }


    //        @Test
    @Order(9)
    @DisplayName("9 - legge le righe delle province")
    public void getTableProvince() {
        sorgente = "ISO 3166-2:IT";

        //--province
        previstoIntero = 14;
        listaWrapTre = service.getTemplateList(sorgente, 2, 3, 1, 3);
        assertNotNull(listaWrapTre);
        assertEquals(previstoIntero, listaWrapTre.size());
        System.out.println(VUOTA);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
        System.out.println("9 - Province: " + listaWrapTre.size());
        printWrapTre(listaWrapTre);
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


    //    @Test
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


    //        @Test
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


    //    @Test
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


    //    @Test
    @Order(20)
    @DisplayName("20 - legge i distretti della Albania")
    public void getTableAlbania() {
        sorgente = "ISO_3166-2:AL";
        previstoIntero = 36;
        listaWrap = service.getDueColonne(sorgente, 1, 1, 1, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("20 - Albania: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(21)
    @DisplayName("21 - legge i distretti della Grecia")
    public void getTableGrecia() {
        sorgente = "ISO_3166-2:GR";

        //--periferie
        previstoIntero = 13;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 1, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("21 - Grecia: " + listaWrap.size());
        printWrap(listaWrap);

        //--prefetture
        previstoIntero = 52;
        listaWrap = service.getDueColonne(sorgente, 2, 2, 2, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("21 - Grecia: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(22)
    @DisplayName("22 - legge le regioni della Cechia")
    public void getTableCechia() {
        sorgente = "ISO_3166-2:CZ";
        previstoIntero = 14;
        listaWrap = service.getTemplateList(sorgente, 1, 2, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("22 - Cechia: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(23)
    @DisplayName("23 - legge le regioni della Slovacchia")
    public void getTableSlovacchia() {
        sorgente = "ISO_3166-2:SK";
        previstoIntero = 8;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 1, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("23 - Slovacchia: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(24)
    @DisplayName("24 - legge le province della Ungheria")
    public void getTableUngheria() {
        sorgente = "ISO_3166-2:HU";
        previstoIntero = 19;
        listaWrap = service.getTemplateList(sorgente, 1, 2, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("24 - Ungheria: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(25)
    @DisplayName("25 - legge i distretti della Romania")
    public void getTableRomania() {
        sorgente = "ISO_3166-2:RO";

        //--distretti
        previstoIntero = 41;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 2, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("25 - Romania: " + listaWrap.size());
        printWrap(listaWrap);

        //--capitale
        previstoIntero = 1;
        listaWrap = service.getDueColonne(sorgente, 2, 2, 2, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("25 - Romania: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(26)
    @DisplayName("26 - legge i distretti della Bulgaria")
    public void getTableBulgaria() {
        sorgente = "ISO_3166-2:BG";
        previstoIntero = 28;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 2, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("26 - Bulgaria: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(27)
    @DisplayName("27 - legge i voivodati della Polonia")
    public void getTablePolonia() {
        sorgente = "ISO_3166-2:PL";
        previstoIntero = 16;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 2, 3);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("27 - Polonia: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(28)
    @DisplayName("28 - legge le regioni della Danimarca")
    public void getTableDanimarca() {
        sorgente = "ISO_3166-2:DK";
        previstoIntero = 5;
        listaWrap = service.getDueColonne(sorgente, 1, 2, 1, 2);
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("28 - Danimarca: " + listaWrap.size());
        printWrap(listaWrap);
    }


    //    @Test
    @Order(29)
    @DisplayName("29 - legge i distretti di Finlandia")
    public void getTableFinlandia() {
        sorgente = "ISO_3166-2:FI";
        previstoIntero = 19 + 1;
        try {
            listaWrap = service.getRegioni(sorgente);
        } catch (Exception unErrore) {
        }
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("29 - Finlandia: " + (listaWrap.size() - 1) + " + titolo");
        printWrap(listaWrap);
    }

    //    @Test
    @Order(30)
    @DisplayName("30 - legge i distretti di Azerbaigian")
    public void getTableAzerbaigian() {
        sorgente = "ISO_3166-2:AZ";
        previstoIntero = 77 + 1;
        try {
            listaWrap = service.getRegioni(sorgente);
        } catch (Exception unErrore) {
        }
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("30 - Azerbaigian: " + (listaWrap.size() - 1) + " + titolo");
        printWrap(listaWrap);
    }

    //    @Test
    @Order(31)
    @DisplayName("31 - legge i distretti di Belize")
    public void getTableBelize() {
        sorgente = "ISO_3166-2:BZ";
        previstoIntero = 6 + 1;
        try {
            listaWrap = service.getRegioni(sorgente);
        } catch (Exception unErrore) {
        }
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("31 - Belize: " + (listaWrap.size() - 1) + " + titolo");
        printWrap(listaWrap);
    }

    //    @Test
    @Order(32)
    @DisplayName("32 - legge i distretti di Guatemala")
    public void getTableGuatemala() {
        sorgente = "ISO_3166-2:GT";
        previstoIntero = 22 + 1;
        try {
            listaWrap = service.getRegioni(sorgente);
        } catch (Exception unErrore) {
        }
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("32 - Guatemala: " + (listaWrap.size() - 1) + " + titolo");
        printWrap(listaWrap);
    }

    //    @Test
    @Order(33)
    @DisplayName("33 - legge i distretti di Guinea Bissau")
    public void getTableGuinea() {
        sorgente = "ISO_3166-2:GW";
        previstoIntero = 9 + 1;
        try {
            listaWrap = service.getRegioni(sorgente);
        } catch (Exception unErrore) {
        }
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("33 - Guinea Bissau: " + (listaWrap.size() - 1) + " + titolo");
        printWrap(listaWrap);
    }

    //    @Test
    @Order(34)
    @DisplayName("34 - legge i distretti di Slovenia")
    public void getTableSlovenia2() {
        sorgente = "ISO_3166-2:SI";
        previstoIntero = 211 + 1;
        try {
            listaWrap = service.getRegioni(sorgente);
        } catch (Exception unErrore) {
        }
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("33 - Guinea Bissau: " + (listaWrap.size() - 1) + " + titolo");
        printWrap(listaWrap);
    }

    //    @Test
    @Order(35)
    @DisplayName("35 - legge i distretti di Kirghizistan")
    public void getTableKirghizistan() {
        sorgente = "ISO_3166-2:KG";
        previstoIntero = 8 + 1;
        try {
            listaWrap = service.getRegioni(sorgente);
        } catch (Exception unErrore) {
        }
        assertNotNull(listaWrap);
        assertEquals(previstoIntero, listaWrap.size());
        System.out.println(VUOTA);
        System.out.println("33 - Guinea Bissau: " + (listaWrap.size() - 1) + " + titolo");
        printWrap(listaWrap);
    }

    //    @Test
    @Order(40)
    @DisplayName("40 - legge le regioni dei primi 50 stati")
    public void readStati1() {
        List<List<String>> listaStati = service.getStati();
        assertNotNull(listaStati);
        listaStati = listaStati.subList(0, 50);
        readStati(listaStati);
    }

    //    @Test
    @Order(41)
    @DisplayName("41 - legge le regioni degli stati 50-100")
    public void readStati2() {
        List<List<String>> listaStati = service.getStati();
        assertNotNull(listaStati);
        listaStati = listaStati.subList(50, 100);
        readStati(listaStati);
    }

    //    @Test
    @Order(42)
    @DisplayName("42 - legge le regioni degli stati 100-150")
    public void readStati3() {
        List<List<String>> listaStati = service.getStati();
        assertNotNull(listaStati);
        listaStati = listaStati.subList(100, 150);
        readStati(listaStati);
    }

    //    @Test
    @Order(43)
    @DisplayName("43 - legge le regioni degli stati 150-200")
    public void readStati4() {
        List<List<String>> listaStati = service.getStati();
        assertNotNull(listaStati);
        listaStati = listaStati.subList(150, 200);
        readStati(listaStati);
    }

    //    @Test
    @Order(44)
    @DisplayName("44 - legge le regioni degli stati 200-250")
    public void readStati5() {
        List<List<String>> listaStati = service.getStati();
        assertNotNull(listaStati);
        listaStati = listaStati.subList(200, listaStati.size() - 1);
        readStati(listaStati);
    }

    private void readStati(List<List<String>> listaStati) {
        String nome;
        String tag = "ISO 3166-2:";
        List<String> valide = new ArrayList<>();
        List<String> errate = new ArrayList<>();

        System.out.println("Legge le regioni di " + listaStati.size() + " stati (" + AWikiService.PAGINA_ISO_1 + ")");
        System.out.println(VUOTA);
        System.out.println("Valide                    Errate");
        System.out.println("********************************");
        for (List<String> lista : listaStati) {
            nome = lista.get(0);
            sorgente = tag + lista.get(3);
            try {
                listaWrap = service.getRegioni(sorgente);
                if (listaWrap != null && listaWrap.size() > 0) {
                    valide.add(nome);
                    System.out.println(nome);
                }
            } catch (Exception unErrore) {
                errate.add(nome);
                System.out.println("                          " + nome);
            }
        }
        System.out.println(VUOTA);
        System.out.println("Stati con regioni valide: " + valide.size());
        System.out.println("Stati con regioni errate: " + errate.size());
    }

    @Test
    @Order(45)
    @DisplayName("45 - legge le province italiane")
    public void getTableProvinceItaliane() {
        previstoIntero = 107;

        listaWrapTre = service.getProvince();

        assertNotNull(listaWrapTre);
        assertEquals(previstoIntero, listaWrapTre.size());
        System.out.println(VUOTA);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
        System.out.println("45 - Province: " + listaWrapTre.size());
        printWrapTre(listaWrapTre);
    }

    private void printColonna(List<String> listaColonna) {
        if (array.isAllValid(listaColonna)) {
            for (String stringa : listaColonna) {
                System.out.println(stringa);
            }
        }
    }


    private void printWrap(List<WrapDueStringhe> listaWrap) {
        System.out.println("********");
        if (array.isAllValid(listaWrap)) {
            for (WrapDueStringhe wrap : listaWrap) {
                System.out.println(wrap.getPrima() + SEP + wrap.getSeconda());
            }
        }
    }


    private void printWrapTre(List<WrapTreStringhe> listaWrap) {
        System.out.println("********");
        if (array.isAllValid(listaWrap)) {
            for (WrapTreStringhe wrap : listaWrap) {
                System.out.println(wrap.getPrima() + SEP + wrap.getSeconda() + SEP + wrap.getTerza());
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