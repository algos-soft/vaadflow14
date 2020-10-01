package it.algos.unit;

import org.junit.jupiter.api.*;

import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: gio, 07-mag-2020
 * Time: 07:56
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("web")
@DisplayName("Unit test per i collegamenti web")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AWebServiceTest extends ATest {


    private static String URL_GENERICO = "https://it.wikipedia.org/wiki/ISO_3166-2:IT";

    private static String PAGINA = "ISO 3166-2:IT";


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
    @DisplayName("Legge un indirizzo URL generico")
    public void legge() {
        sorgente = URL_GENERICO;
        ottenuto = web.leggeWeb(sorgente);
        assertNotNull(ottenuto);
    }


    @Test
    @Order(2)
    @DisplayName("Legge una pagina wiki")
    public void leggeWiki() {
        sorgente = PAGINA;
        ottenuto = web.leggeSorgenteWiki(sorgente);
        assertNotNull(ottenuto);
    }


    @Test
    @Order(3)
    @DisplayName("Titoli tabella")
    public void costruisceTagTitoliTable() {
        String[] titoli;

        previsto = VUOTA;
        ottenuto = web.costruisceTagTitoliTable(null);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        titoli = new String[]{"Codice"};
        previsto = VUOTA;
        previsto += "<table class=\"wikitable sortable\">";
        previsto += "<tbody><tr>";
        previsto += "<th>";
        previsto += "Codice";

        ottenuto = web.costruisceTagTitoliTable(titoli);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        System.out.println("");
        System.out.println(ottenuto);


        titoli = new String[]{"Codice", "Province"};
        previsto = VUOTA;
        previsto += "<table class=\"wikitable sortable\">";
        previsto += "<tbody><tr>";
        previsto += "<th>";
        previsto += "Codice";
        previsto += "</th>";
        previsto += "<th>";
        previsto += "Province";

        ottenuto = web.costruisceTagTitoliTable(titoli);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        System.out.println("");
        System.out.println(ottenuto);


        titoli = new String[]{"Codice", "Province", "Nella regione"};
        previsto = VUOTA;
        previsto += "<table class=\"wikitable sortable\">";
        previsto += "<tbody><tr>";
        previsto += "<th>";
        previsto += "Codice";
        previsto += "</th>";
        previsto += "<th>";
        previsto += "Province";
        previsto += "</th>";
        previsto += "<th>";
        previsto += "Nella regione";

        ottenuto = web.costruisceTagTitoliTable(titoli);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        System.out.println("");
        System.out.println(ottenuto);
    }


    @Test
    @Order(4)
    @DisplayName("Estrae una tavola")
    public void estraeTableWiki() {
        sorgente = web.leggeSorgenteWiki(PAGINA);

        String[] titoli = new String[]{"Codice", "Città metropolitane", "Nella regione"};

        ottenuto = web.estraeTableWiki(sorgente, titoli);
        assertNotNull(ottenuto);
        System.out.println("");
        System.out.println(ottenuto);
    }


    @Test
    @Order(5)
    @DisplayName("Estrae un'altra tavola")
    public void estraeTableWiki2() {
        sorgente = web.leggeSorgenteWiki(PAGINA);
        String[] titoli = new String[]{"Codice", "Regioni"};

        ottenuto = web.estraeTableWiki(sorgente, titoli);
        assertNotNull(ottenuto);
        System.out.println("");
        System.out.println(ottenuto);
    }


    @Test
    @Order(6)
    @DisplayName("Estrae le righe")
    public void getRigheTableWiki() {
        List<String> lista = null;
        String[] titoli = new String[]{"Codice", "Regioni"};
        int previstoIntero = 20;

        lista = web.getRigheTableWiki(PAGINA, titoli);
        assertNotNull(lista);
        assertEquals(previstoIntero, lista.size());
        System.out.println("");
        for (String riga : lista) {
            System.out.println("");
            System.out.println(riga);
        }
    }


    @Test
    @Order(7)
    @DisplayName("Estrae la mappa")
    public void getMappaTableWiki() {
        LinkedHashMap<String, LinkedHashMap<String, String>> mappaTable = null;
        String[] titoli = new String[]{"Codice", "Regioni"};
        int previstoIntero = 20;

        mappaTable = web.getMappaTableWiki(PAGINA, titoli);
        assertNotNull(mappaTable);
        assertEquals(previstoIntero, mappaTable.size());
        System.out.println("");
        for (String key : mappaTable.keySet()) {
            System.out.println("");
            for (String key2 : mappaTable.get(key).keySet()) {
                System.out.println(mappaTable.get(key).get(key2));
            }
        }
    }


    @Test
    @Order(8)
    @DisplayName("Estrae la matrice")
    public void getMatriceTableWiki() {
        List<List<String>> matriceTable = null;
        String[] titoli = new String[]{"Codice", "Regioni"};

        int previstoIntero = 20;

        matriceTable = web.getMatriceTableWiki(PAGINA, titoli);
        assertNotNull(matriceTable);
        assertEquals(previstoIntero, matriceTable.size());
    }


    @Test
    @Order(9)
    @DisplayName("Estrae un'altra matrice")
    public void getMatriceTableWiki2() {
        List<List<String>> matriceTable = null;
        String[] titoli = new String[]{"pos.", "comune"};

        int previstoIntero = 136;

        matriceTable = web.getMatriceTableWiki("Comuni del Molise", titoli);
        assertNotNull(matriceTable);
        assertEquals(previstoIntero, matriceTable.size());
    }

}