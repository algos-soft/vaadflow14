package it.algos.unit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import it.algos.vaadflow14.backend.enumeration.AECrono;
import it.algos.vaadflow14.backend.enumeration.AEGeografia;
import org.junit.jupiter.api.*;

import java.util.*;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 29-apr-2020
 * Time: 14:46
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("array")
@DisplayName("Unit test per gli array")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AArrayServiceTest extends ATest {

    /**
     * The constant ARRAY_STRING.
     */
    protected static final String[] ARRAY_STRING = {"primo", "secondo", "quarto", "quinto", "1Ad", "terzo", "a10"};

    /**
     * The constant LIST_STRING.
     */
    protected final static List<String> LIST_STRING = new ArrayList(Arrays.asList(ARRAY_STRING));


    /**
     * The constant ARRAY_OBJECT.
     */
    protected static final Object[] ARRAY_OBJECT = {new Label("Alfa"), new Button()};

    /**
     * The constant LIST_OBJECT.
     */
    protected static final List<Object> LIST_OBJECT = new ArrayList(Arrays.asList(ARRAY_OBJECT));

    /**
     * The constant ARRAY_LONG.
     */
    protected static final Long[] ARRAY_LONG = {234L, 85L, 151099L, 123500L, 3L, 456772L};

    /**
     * The constant LIST_LONG.
     */
    protected static final ArrayList<Long> LIST_LONG = new ArrayList(Arrays.asList(ARRAY_LONG));


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    void setUpIniziale() {
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
    @DisplayName("1 - isAllValid (since Java 11) array")
    void isAllValid() {
        listaStr = new ArrayList<>();

        ottenutoBooleano = array.isAllValid((List) null);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isAllValid((Map) null);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isAllValid((new ArrayList()));
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isAllValid((listaStr));
        assertFalse(ottenutoBooleano);

        listaStr.add(null);
        ottenutoBooleano = array.isAllValid(listaStr);
        assertFalse(ottenutoBooleano);

        listaStr = new ArrayList<>();
        listaStr.add(VUOTA);
        ottenutoBooleano = array.isAllValid(listaStr);
        assertFalse(ottenutoBooleano);

        listaStr = new ArrayList<>();
        listaStr.add(PIENA);
        ottenutoBooleano = array.isAllValid(listaStr);
        assertTrue(ottenutoBooleano);

        listaStr.add("Mario");
        ottenutoBooleano = array.isAllValid(listaStr);
        assertTrue(ottenutoBooleano);

        listaStr.add(null);
        ottenutoBooleano = array.isAllValid(listaStr);
        assertFalse(ottenutoBooleano);


        ottenutoBooleano = array.isAllValid(LIST_STRING);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isAllValid(LIST_OBJECT);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isAllValid(LIST_LONG);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(2)
    @DisplayName("2 - isAllValid (since Java 11) matrice")
    void isAllValid2() {
        sorgenteMatrice = null;
        ottenutoBooleano = array.isAllValid((String[]) null);
        assertFalse(ottenutoBooleano);

        sorgenteMatrice = null;
        ottenutoBooleano = array.isAllValid(sorgenteMatrice);
        assertFalse(ottenutoBooleano);

        sorgenteMatrice = new String[]{"Codice", "Regioni"};
        ottenutoBooleano = array.isAllValid(sorgenteMatrice);
        assertTrue(ottenutoBooleano);

        sorgenteMatrice = new String[]{VUOTA, "Regioni"};
        ottenutoBooleano = array.isAllValid(sorgenteMatrice);
        assertFalse(ottenutoBooleano);

        sorgenteMatrice = new String[]{VUOTA};
        ottenutoBooleano = array.isAllValid(sorgenteMatrice);
        assertFalse(ottenutoBooleano);

        sorgenteMatrice = new String[]{"Mario",VUOTA, "Regioni"};
        ottenutoBooleano = array.isAllValid(sorgenteMatrice);
        assertFalse(ottenutoBooleano);

        sorgenteMatrice = new String[]{VUOTA,VUOTA,VUOTA};
        ottenutoBooleano = array.isAllValid(sorgenteMatrice);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(3)
    @DisplayName("3 - isAllValid (since Java 11) map")
    void isAllValid3() {
        mappaSorgente = null;
        ottenutoBooleano = array.isAllValid((LinkedHashMap)null);
        assertFalse(ottenutoBooleano);

        mappaSorgente = null;
        ottenutoBooleano = array.isAllValid(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new HashMap();
        ottenutoBooleano = array.isAllValid(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        ottenutoBooleano = array.isAllValid(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put(null, "irrilevante2");
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isAllValid(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put(VUOTA, "irrilevante2");
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isAllValid(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put("alfa", null);
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isAllValid(mappaSorgente);
        assertTrue(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put("alfa", VUOTA);
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isAllValid(mappaSorgente);
        assertTrue(ottenutoBooleano);
    }

    @Test
    @Order(4)
    @DisplayName("4 - isEmpty (since Java 11) array")
    void isEmpty() {
        listaStr = new ArrayList<>();

        ottenutoBooleano = array.isEmpty((List) null);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty((Map) null);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty((new ArrayList()));
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty((listaStr));
        assertTrue(ottenutoBooleano);

        listaStr.add(null);
        ottenutoBooleano = array.isEmpty(listaStr);
        assertTrue(ottenutoBooleano);

        listaStr = new ArrayList<>();
        listaStr.add(VUOTA);
        ottenutoBooleano = array.isEmpty(listaStr);
        assertTrue(ottenutoBooleano);

        listaStr = new ArrayList<>();
        listaStr.add(PIENA);
        ottenutoBooleano = array.isEmpty(listaStr);
        assertFalse(ottenutoBooleano);

        listaStr.add("Mario");
        ottenutoBooleano = array.isEmpty(listaStr);
        assertFalse(ottenutoBooleano);

        listaStr.add(null);
        ottenutoBooleano = array.isEmpty(listaStr);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty(LIST_STRING);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty(LIST_OBJECT);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty(LIST_LONG);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(5)
    @DisplayName("5 - isEmpty (since Java 11) matrice")
    void isEmpty2() {
        sorgenteMatrice = null;
        ottenutoBooleano = array.isEmpty((String[]) null);
        assertTrue(ottenutoBooleano);

        sorgenteMatrice = null;
        ottenutoBooleano = array.isEmpty(sorgenteMatrice);
        assertTrue(ottenutoBooleano);

        sorgenteMatrice = new String[]{"Codice", "Regioni"};
        ottenutoBooleano = array.isEmpty(sorgenteMatrice);
        assertFalse(ottenutoBooleano);

        sorgenteMatrice = new String[]{VUOTA, "Regioni"};
        ottenutoBooleano = array.isEmpty(sorgenteMatrice);
        assertFalse(ottenutoBooleano);

        sorgenteMatrice = new String[]{VUOTA};
        ottenutoBooleano = array.isEmpty(sorgenteMatrice);
        assertTrue(ottenutoBooleano);

        sorgenteMatrice = new String[]{"Mario",VUOTA, "Regioni"};
        ottenutoBooleano = array.isEmpty(sorgenteMatrice);
        assertFalse(ottenutoBooleano);

        sorgenteMatrice = new String[]{VUOTA,VUOTA,VUOTA};
        ottenutoBooleano = array.isEmpty(sorgenteMatrice);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(6)
    @DisplayName("6 - isEmpty (since Java 11) map")
    void isEmpty3() {
        mappaSorgente = null;
        ottenutoBooleano = array.isEmpty((LinkedHashMap)null);
        assertTrue(ottenutoBooleano);

        mappaSorgente = null;
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertTrue(ottenutoBooleano);

        mappaSorgente = new HashMap();
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertTrue(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertTrue(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("", "irrilevante");
        mappaSorgente.put(null, "irrilevante2");
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertTrue(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put(VUOTA, "irrilevante2");
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put(null, "irrilevante2");
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("", "irrilevante");
        mappaSorgente.put(null, "irrilevante2");
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put("alfa", null);
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put("alfa", VUOTA);
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        mappaSorgente.put("beta", null);
        mappaSorgente.put("alfa", VUOTA);
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(8)
    @DisplayName("8 - isMappaSemplificabile")
    void isMappaSemplificabile() {
        Map<String, List<String>> mappaSorgenteConListe = null;
        ottenutoBooleano = array.isMappaSemplificabile(mappaSorgenteConListe);
        Assertions.assertFalse(ottenutoBooleano);

        mappaSorgenteConListe = new HashMap<>();
        ottenutoBooleano = array.isMappaSemplificabile(mappaSorgenteConListe);
        Assertions.assertFalse(ottenutoBooleano);

        mappaSorgenteConListe = new HashMap<>();
        mappaSorgenteConListe.put("uno", LIST_STRING);
        mappaSorgenteConListe.put("due", LIST_SHORT_STRING);
        ottenutoBooleano = array.isMappaSemplificabile(mappaSorgenteConListe);
        Assertions.assertFalse(ottenutoBooleano);

        mappaSorgenteConListe = new HashMap<>();
        mappaSorgenteConListe.put("uno", LIST_SHORT_STRING);
        mappaSorgenteConListe.put("due", LIST_SHORT_STRING);
        ottenutoBooleano = array.isMappaSemplificabile(mappaSorgenteConListe);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(9)
    @DisplayName("9 - semplificaMappa")
    void semplificaMappa() {
        Map<String, List<String>> mappaSorgenteConListe = null;
        Map<String, String> mappaPrevista = null;

        mappaOttenuta = array.semplificaMappa(mappaSorgenteConListe);
        Assertions.assertNull(mappaOttenuta);

        mappaSorgenteConListe = new HashMap<>();
        mappaOttenuta = array.semplificaMappa(mappaSorgenteConListe);
        Assertions.assertNull(mappaOttenuta);

        mappaSorgenteConListe = new HashMap<>();
        mappaSorgenteConListe.put("uno", LIST_STRING);
        mappaSorgenteConListe.put("due", LIST_SHORT_STRING);
        mappaOttenuta = array.semplificaMappa(mappaSorgenteConListe);
        Assertions.assertNull(mappaOttenuta);

        mappaSorgenteConListe = new HashMap<>();
        mappaSorgenteConListe.put("uno", LIST_SHORT_STRING);
        mappaSorgenteConListe.put("due", LIST_SHORT_STRING);
        mappaPrevista = new HashMap<>();
        mappaPrevista.put("uno", CONTENUTO);
        mappaPrevista.put("due", CONTENUTO);
        mappaOttenuta = array.semplificaMappa(mappaSorgenteConListe);
        Assertions.assertNotNull(mappaOttenuta);
        Assertions.assertEquals(mappaPrevista, mappaOttenuta);
    }


    @Test
    @Order(10)
    @DisplayName("10 - getMappa")
    void getMappa() {
        sorgente = "valore";
        Map<String, String> mappaPrevista = null;
        mappaPrevista = new HashMap<>();
        mappaPrevista.put("uno", sorgente);

        mappaOttenuta = array.getMappa("uno", sorgente);
        Assertions.assertNotNull(mappaOttenuta);
        Assertions.assertEquals(mappaPrevista, mappaOttenuta);
    }


    @Test
    @Order(11)
    @DisplayName("11 - getLista")
    void getLista() {
        sorgente = "valore";
        previstoArray = new ArrayList<>();
        previstoArray.add(sorgente);

        ottenutoArray = array.getLista(null);
        Assertions.assertNotNull(ottenutoArray);
        Assertions.assertEquals(0, ottenutoArray.size());

        ottenutoArray = array.getLista(VUOTA);
        Assertions.assertNotNull(ottenutoArray);
        Assertions.assertEquals(0, ottenutoArray.size());

        ottenutoArray = array.getLista(sorgente);
        Assertions.assertNotNull(ottenutoArray);
        Assertions.assertEquals(1, ottenutoArray.size());
        Assertions.assertEquals(previstoArray, ottenutoArray);
    }

    @Test
    @Order(12)
    @DisplayName("12 - check enumeration")
    void checkEnumeration() {

        sorgente = "giorno";
        ottenutoBooleano = AECrono.getValue().contains(sorgente);
        assertTrue(ottenutoBooleano);

        sorgente = "giorno";
        ottenutoBooleano = AEGeografia.getValue().contains(sorgente);
        assertFalse(ottenutoBooleano);

        sorgente = "provincia";
        ottenutoBooleano = AEGeografia.getValue().contains(sorgente);
        assertTrue(ottenutoBooleano);

        sorgente = "provincia";
        ottenutoBooleano = AECrono.getValue().contains(sorgente);
        assertFalse(ottenutoBooleano);

        sorgente = "company";
        ottenutoBooleano = AECrono.getValue().contains(sorgente);
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = AEGeografia.getValue().contains(sorgente);
        assertFalse(ottenutoBooleano);
    }

}