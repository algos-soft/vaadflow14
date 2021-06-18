package it.algos.unit;

import it.algos.test.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.*;

import java.util.*;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: gio, 07-mag-2020
 * Time: 07:56
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("Test di controllo per i collegamenti base del web.")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AWebServiceTest extends ATest {

    public static final String URL_ERRATO = "htp://www.altos.it/hellogac.html";
    public static final String URL_GAC = "http://www.algos.it/hellogac.html";

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
    @DisplayName("1 - Legge un indirizzo URL errato (inesistente)")
    public void leggeErrato() {
        sorgente = URL_ERRATO;

        ottenuto = web.leggeWeb(sorgente);
        assertTrue(text.isEmpty(ottenuto));
    }

    @Test
    @Order(2)
    @DisplayName("2 - Legge un indirizzo URL generico")
    public void leggeGac() {
        sorgente = URL_GAC;
        previsto = "<!DOCTYPE html><html><body><h1>Telefoni</h1><p style=\"font-family:verdana;font-size:60px\">Gac: 338 9235040</p>";
        ottenuto = web.leggeWeb(sorgente);
        assertTrue(text.isValid(ottenuto));
        assertTrue(ottenuto.startsWith(previsto));

        System.out.println("Legge il testo grezzo della pagina html. Sorgente del browser");
        System.out.println("Faccio vedere solo l'inizio, perché troppo lungo");
        System.out.println("Sorgente restituito in formato html");
        System.out.println(String.format("Tempo impiegato per leggere %d pagine: %s", cicli, getTime()));
        System.out.println(VUOTA);
        System.out.println(ottenuto.substring(0, previsto.length()));
    }

    @Test
    @Order(3)
    @DisplayName("3 - Legge un body di un URL generico")
    public void leggeBodyGac() {
        sorgente = URL_GAC;
        previsto = "<h1>Telefoni</h1><p style=\"font-family:verdana;font-size:60px\">Gac: 338 9235040</p>";
        previsto2 = "<p style=\"font-family:verdana;font-size:60px\">2NT-3F: No</p>";

        ottenuto = web.leggeBodyWeb(sorgente);
        assertTrue(text.isValid(ottenuto));
        assertTrue(ottenuto.startsWith(previsto));

        System.out.println("Legge il body di una pagina html. Sorgente del browser");
        System.out.println("Faccio vedere solo l'inizio, perché troppo lungo");
        System.out.println("Sorgente restituito in formato html");
        System.out.println(String.format("Tempo impiegato per leggere %d pagine: %s", cicli, getTime()));
        System.out.println(VUOTA);
        System.out.println(ottenuto.substring(0, previsto.length()));
        System.out.println(ottenuto.substring(ottenuto.length() - previsto2.length()));
    }

    @Test
    @Order(19)
    @DisplayName("Legge un indirizzo URL generico")
    public void legge() {
        sorgente = URL_GENERICO;
        ottenuto = web.leggeWeb(sorgente);
        assertNotNull(ottenuto);
    }


    @Test
    @Order(29)
    @DisplayName("Legge una pagina wiki")
    public void leggeWiki() {
        sorgente = PAGINA;
        ottenuto = web.leggeSorgenteWiki(sorgente);
        assertNotNull(ottenuto);
    }


    @Test
    @Order(39)
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
    @Order(49)
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
    @Order(59)
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
    @Order(69)
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
    @Order(79)
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
    @Order(89)
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
    @Order(99)
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