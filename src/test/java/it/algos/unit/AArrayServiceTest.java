package it.algos.unit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


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


    /**
     * Is valid.
     */
    @Test
    void isValid() {
        lista = new ArrayList<>();
        ottenutoBooleano = array.isValid((List) null);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isValid((String[]) null);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isValid((Map) null);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isValid((new ArrayList()));
        Assertions.assertFalse(ottenutoBooleano);

        lista.add(null);
        ottenutoBooleano = array.isValid(lista);
        Assertions.assertFalse(ottenutoBooleano);

        lista.add(PIENA);
        ottenutoBooleano = array.isValid(lista);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isValid((Collection) null);
        Assertions.assertFalse(ottenutoBooleano);

        lista = LIST_STRING;
        ottenutoBooleano = array.isValid(lista);
        Assertions.assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isValid(LIST_OBJECT);
        Assertions.assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isValid(LIST_LONG);
        Assertions.assertTrue(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        ottenutoBooleano = array.isValid(mappaSorgente);
        Assertions.assertFalse(ottenutoBooleano);

        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put("alfa", "irrilevante2");
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isValid(mappaSorgente);
        Assertions.assertTrue(ottenutoBooleano);

        Collection collezione = new ArrayList();
        collezione.add(24);

        ottenutoBooleano = array.isValid(collezione);
        Assertions.assertTrue(ottenutoBooleano);
    }


    /**
     * Is empty.
     */
    @Test
    void isEmpty() {
        lista = new ArrayList<>();
        ottenutoBooleano = array.isEmpty((List) null);
        Assertions.assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty((String[]) null);
        Assertions.assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty((Map) null);
        Assertions.assertTrue(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty((new ArrayList()));
        Assertions.assertTrue(ottenutoBooleano);

        lista.add(null);
        ottenutoBooleano = array.isEmpty(lista);
        Assertions.assertTrue(ottenutoBooleano);

        lista.add(PIENA);
        ottenutoBooleano = array.isEmpty(lista);
        Assertions.assertTrue(ottenutoBooleano);

        lista = LIST_STRING;
        ottenutoBooleano = array.isEmpty(lista);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty(LIST_OBJECT);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = array.isEmpty(LIST_LONG);
        Assertions.assertFalse(ottenutoBooleano);

        mappaSorgente = new LinkedHashMap();
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        Assertions.assertTrue(ottenutoBooleano);

        mappaSorgente.put("beta", "irrilevante");
        mappaSorgente.put("alfa", "irrilevante2");
        mappaSorgente.put("delta", "irrilevante3");
        ottenutoBooleano = array.isEmpty(mappaSorgente);
        Assertions.assertFalse(ottenutoBooleano);
    }


    @Test
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
        Assertions.assertTrue(ottenutoBooleano);
    }


    @Test
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

}