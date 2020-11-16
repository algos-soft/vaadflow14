package it.algos.unit;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Label;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mar, 28-apr-2020
 * Time: 20:42
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("text")
@DisplayName("Unit test per elaborazione stringhe")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ATextServiceTest extends ATest {


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
    @DisplayName("1 - Controlla testo vuoto")
    void testIsEmpty() {
        ottenutoBooleano = text.isEmpty(null);
        Assertions.assertTrue(ottenutoBooleano);

        ottenutoBooleano = text.isEmpty(VUOTA);
        Assertions.assertTrue(ottenutoBooleano);

        ottenutoBooleano = text.isEmpty(PIENA);
        Assertions.assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(2)
    @DisplayName("2 - Controlla testo valido")
    void testIsValid() {
        ottenutoBooleano = text.isValid(null);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = text.isValid(VUOTA);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = text.isValid(PIENA);
        Assertions.assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(3)
    @DisplayName("3 - Controlla testo valido")
    void testIsValid2() {
        ottenutoBooleano = text.isValid((List) null);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = text.isValid((new ArrayList()));
        Assertions.assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(4)
    @DisplayName("4 - Prima maiuscola")
    void primaMaiuscola() {
        sorgente = null;
        previsto = VUOTA;
        ottenuto = text.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);

        sorgente = VUOTA;
        previsto = VUOTA;
        ottenuto = text.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);

        sorgente = "MARIO";
        previsto = "MARIO";
        ottenuto = text.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);

        sorgente = "mario";
        previsto = "Mario";
        ottenuto = text.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);

        sorgente = " mario";
        previsto = "Mario";
        ottenuto = text.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);

        sorgente = "mario ";
        previsto = "Mario";
        ottenuto = text.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);

        sorgente = " mario ";
        previsto = "Mario";
        ottenuto = text.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Restituisce una lista di stringhe")
    void getArray() {
        ottenutoArray = text.getArray(null);
        assertNull(ottenutoArray);

        sorgente = VUOTA;
        ottenutoArray = text.getArray(sorgente);
        assertNull(ottenutoArray);

        sorgente = "codedescrizioneordine";
        String[] stringArray2 = {"codedescrizioneordine"};
        previstoArray = new ArrayList(Arrays.asList(stringArray2));
        ottenutoArray = text.getArray(sorgente);
        assertNotNull(ottenutoArray);
        assertEquals(ottenutoArray, previstoArray);

        sorgente = "code,descrizione,ordine";
        String[] stringArray3 = {"code", "descrizione", "ordine"};
        previstoArray = new ArrayList(Arrays.asList(stringArray3));
        ottenutoArray = text.getArray(sorgente);
        assertNotNull(ottenutoArray);
        assertEquals(ottenutoArray, previstoArray);

        sorgente = "code, descrizione , ordine ";
        String[] stringArray4 = {"code", "descrizione", "ordine"};
        previstoArray = new ArrayList(Arrays.asList(stringArray4));
        ottenutoArray = text.getArray(sorgente);
        assertNotNull(ottenutoArray);
        assertEquals(ottenutoArray, previstoArray);
    }


    @Test
    @Order(6)
    @DisplayName("6 - Restituisce una lista di interi")
    void getArrayInt() {
        ottenutoInteroArray = text.getArrayInt(null);
        assertNull(ottenutoInteroArray);

        sorgente = VUOTA;
        ottenutoInteroArray = text.getArrayInt(sorgente);
        assertNull(ottenutoInteroArray);

        sorgente = "4";
        Integer[] intArray2 = {4};
        previstoInteroArray = new ArrayList(Arrays.asList(intArray2));
        ottenutoInteroArray = text.getArrayInt(sorgente);
        assertNotNull(ottenutoInteroArray);
        assertEquals(previstoInteroArray, ottenutoInteroArray);

        sorgente = "8,25,17";
        Integer[] intArray3 = {8, 25, 17};
        previstoInteroArray = new ArrayList(Arrays.asList(intArray3));
        ottenutoInteroArray = text.getArrayInt(sorgente);
        assertNotNull(ottenutoInteroArray);
        assertEquals(previstoInteroArray, ottenutoInteroArray);

        sorgente = "8, 25 , 17 ";
        Integer[] intArray4 = {8, 25, 17};
        previstoInteroArray = new ArrayList(Arrays.asList(intArray4));
        ottenutoInteroArray = text.getArrayInt(sorgente);
        assertNotNull(ottenutoInteroArray);
        assertEquals(previstoInteroArray, ottenutoInteroArray);
    }


    @Test
    @Order(7)
    @DisplayName("7 - Leva un testo iniziale")
    void levaTesta() {
        sorgente = "Non Levare questo inizio ";
        tag = "Non";
        previsto = "Levare questo inizio";
        ottenuto = text.levaTesta(sorgente, tag);
        assertEquals(previsto, ottenuto);

        sorgente = "Non Levare questo inizio ";
        tag = "";
        previsto = "Non Levare questo inizio";
        ottenuto = text.levaTesta(sorgente, tag);
        assertEquals(previsto, ottenuto);

        sorgente = "Non Levare questo inizio ";
        tag = "NonEsisteQuestoTag";
        previsto = "Non Levare questo inizio";
        ottenuto = text.levaTesta(sorgente, tag);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(8)
    @DisplayName("8 - Leva un testo finale")
    void levaCoda() {
        sorgente = " Levare questa fine Non ";
        tag = "Non";
        previsto = "Levare questa fine";
        ottenuto = text.levaCoda(sorgente, tag);
        assertEquals(previsto, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "";
        previsto = "Non Levare questa fine";
        ottenuto = text.levaCoda(sorgente, tag);
        assertEquals(previsto, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "NonEsisteQuestoTag";
        previsto = "Non Levare questa fine";
        ottenuto = text.levaCoda(sorgente, tag);
        assertEquals(previsto, ottenuto);

    }


    @Test
    @Order(9)
    @DisplayName("9 - Leva un testo finale a partire da")
    void levaCodaDa() {
        sorgente = " Levare questa fine Non ";
        tag = "N";
        previsto = "Levare questa fine";
        ottenuto = text.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "";
        previsto = "Non Levare questa fine";
        ottenuto = text.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "questa";
        previsto = "Non Levare";
        ottenuto = text.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);
    }


    void getLabelHost() {
        //        sorgente = "black";
        //        Label label = text.getLabelHost(sorgente);
        //        assertNotNull(label);
        //        System.out.println(label.getText());
    }


    @Test
    @Order(10)
    @DisplayName("10 - Label user verde")
    void getLabelUser() {
        sorgente = "green";
        Label label = text.getLabelUser(sorgente);
        assertNotNull(label);
        System.out.println(label.getText());
    }


    @Test
    @Order(11)
    @DisplayName("11 - Label admin blue")
    void getLabelAdmin() {
        sorgente = "blue";
        Label label = text.getLabelAdmin(sorgente);
        assertNotNull(label);
        System.out.println(label.getText());
    }


    @Test
    @Order(12)
    @DisplayName("12 - Label developer rossa")
    void getLabelDev() {
        sorgente = "red";
        Label label = text.getLabelDev(sorgente);
        assertNotNull(label);
        System.out.println(label.getText());
    }


    @Test
    @Order(13)
    @DisplayName("13 - Restituisce una matrice di stringhe")
    public void getMatrice() {
        sorgente = VUOTA;
        ottenutoMatrice = text.getMatrice(sorgente);
        assertNull(ottenutoMatrice);

        sorgente = "MarioFrancesca";
        previstoMatrice = new String[]{"MarioFrancesca"};
        ottenutoMatrice = text.getMatrice(sorgente);
        assertNotNull(ottenutoMatrice);
        assertTrue(Arrays.equals(previstoMatrice, ottenutoMatrice));

        previstoMatrice = new String[]{"Mario", "Giovanni", "Francesca"};
        sorgente = "Mario,Giovanni,Francesca";
        ottenutoMatrice = text.getMatrice(sorgente);
        assertNotNull(ottenutoMatrice);
        assertTrue(Arrays.equals(previstoMatrice, ottenutoMatrice));

        sorgente = "Mario, Giovanni, Francesca";
        ottenutoMatrice = text.getMatrice(sorgente);
        assertNotNull(ottenutoMatrice);
        assertTrue(Arrays.equals(previstoMatrice, ottenutoMatrice));
    }


    @Test
    @Order(14)
    @DisplayName("14 - Restituisce una matrice di interi")
    public void getMatriceInt() {
        sorgente = VUOTA;
        ottenutoInteroMatrice = text.getMatriceInt(sorgente);
        assertNull(ottenutoInteroMatrice);

        sorgente = "4";
        previstoInteroMatrice = new Integer[]{4};
        ottenutoInteroMatrice = text.getMatriceInt(sorgente);
        assertNotNull(ottenutoInteroMatrice);
        assertTrue(Arrays.equals(previstoInteroMatrice, ottenutoInteroMatrice));

        previstoInteroMatrice = new Integer[]{8, 25, 17};
        sorgente = "8,25,17";
        ottenutoInteroMatrice = text.getMatriceInt(sorgente);
        assertNotNull(ottenutoInteroMatrice);
        assertTrue(Arrays.equals(previstoInteroMatrice, ottenutoInteroMatrice));

        sorgente = "8 , 25 , 17";
        ottenutoInteroMatrice = text.getMatriceInt(sorgente);
        assertNotNull(ottenutoInteroMatrice);
        assertTrue(Arrays.equals(previstoInteroMatrice, ottenutoInteroMatrice));
    }


    @Test
    @Order(15)
    @DisplayName("15 - Elimina parentesi quadre in testa e coda della stringa")
    public void setNoQuadre() {
        System.out.println(VUOTA);
        System.out.println("Elimina parentesi quadre in testa e coda della stringa");

        sorgente = VUOTA;
        previsto = VUOTA;
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(sorgente, ottenuto);

        previsto = "mario";

        sorgente = " mario  ";
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[mario]]";
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[mario]]";
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[ mario ]]";
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[[mario]]]";
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[mario]]";
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[[[mario]]]]";
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        previsto = "m[ario";

        sorgente = "m[ario";
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = " m[ario";
        ottenuto = text.setNoQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);
    }


    @Test
    @Order(16)
    @DisplayName("16 - Aggiunge parentesi quadre in testa e coda alla stringa")
    public void setQuadre() {
        System.out.println(VUOTA);
        System.out.println("Aggiunge parentesi quadre in testa e coda alla stringa");

        sorgente = VUOTA;
        previsto = VUOTA;
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(sorgente, ottenuto);

        sorgente = "mario";
        previsto = "[mario]";
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = " mario";
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = " mario  ";
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[mario]]";
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[mario]]";
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[ mario ]]";
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[mario]";
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[mario]]";
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[[mario]]]";
        ottenuto = text.setQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

    }


    @Test
    @Order(17)
    @DisplayName("17 - Aggiunge doppie parentesi quadre in testa e coda alla stringa")
    public void setDoppieQuadre() {
        System.out.println(VUOTA);
        System.out.println("Aggiunge doppie parentesi quadre in testa e coda alla stringa");

        sorgente = VUOTA;
        previsto = VUOTA;
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(sorgente, ottenuto);

        sorgente = "mario";
        previsto = "[[mario]]";
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = " mario";
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = " mario  ";
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[mario]]";
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[mario]]";
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[ mario ]]";
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[mario]";
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[mario]]";
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "[[[mario]]]";
        ottenuto = text.setDoppieQuadre(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

    }


    @Test
    @Order(18)
    @DisplayName("18 - Allunga un testo alla lunghezza desiderata")
    public void rightPad() {
        sorgenteIntero = 7;

        sorgente = VUOTA;
        previsto = SPAZIO + SPAZIO + SPAZIO + SPAZIO + SPAZIO + SPAZIO + SPAZIO;
        ottenuto = text.rightPad(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);

        sorgente = "mario";
        previsto = "mario  ";
        ottenuto = text.rightPad(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);

        sorgente = " aldo";
        previsto = "aldo   ";
        ottenuto = text.rightPad(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);

        sorgente = "  x";
        previsto = "x      ";
        ottenuto = text.rightPad(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(19)
    @DisplayName("19 - Forza un testo alla lunghezza desiderata")
    public void fixSize() {
        sorgenteIntero = 7;

        sorgente = VUOTA;
        previsto = SPAZIO + SPAZIO + SPAZIO + SPAZIO + SPAZIO + SPAZIO + SPAZIO;
        ottenuto = text.fixSize(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);

        sorgente = "mario";
        previsto = "mario  ";
        ottenuto = text.fixSize(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);

        sorgente = " aldo";
        previsto = "aldo   ";
        ottenuto = text.fixSize(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);

        sorgente = "Mario Rossi";
        previsto = "Mario R";
        ottenuto = text.fixSize(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);

        sorgente = "Settimo";
        previsto = "Settimo";
        ottenuto = text.fixSize(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);

        sorgente = "Setti";
        previsto = "Setti  ";
        ottenuto = text.fixSize(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);

        sorgente = "SettimoDoppio";
        previsto = "Settimo";
        ottenuto = text.fixSize(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length());
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(20)
    @DisplayName("20 - Forza un testo alla lunghezza desiderata ed aggiunge parentesi quadre")
    public void fixSizeQuadre() {
        sorgenteIntero = 4;

        sorgente = "gaps";
        previsto = "[gaps]";
        ottenuto = text.fixSizeQuadre(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length() - 2);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "pap";
        previsto = "[pap ]";
        ottenuto = text.fixSizeQuadre(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length() - 2);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = " gaps ";
        previsto = "[gaps]";
        ottenuto = text.fixSizeQuadre(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length() - 2);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "  pap";
        previsto = "[pap ]";
        ottenuto = text.fixSizeQuadre(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length() - 2);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);

        sorgente = "Mariolino Birichino";
        sorgenteIntero = 5;
        previsto = "[Mario]";
        ottenuto = text.fixSizeQuadre(sorgente, sorgenteIntero);
        assertNotNull(ottenuto);
        assertEquals(sorgenteIntero, ottenuto.length() - 2);
        assertEquals(previsto, ottenuto);
        print(sorgente, ottenuto);
    }


    @Test
    @Order(21)
    @DisplayName("21 - Elimina gli spazi interni della stringa")
    public void levaSpaziInterni() {
        sorgente = "Mariolino Birichino";
        previsto = "MariolinoBirichino";
        ottenuto = text.levaSpazi(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        sorgente = "XVI secolo a.C.";
        previsto = "XVIsecoloa.C.";
        ottenuto = text.levaSpazi(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        sorgente = "999 a.C.";
        previsto = "999a.C.";
        ottenuto = text.levaSpazi(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        sorgente = "7 gennaio";
        previsto = "7gennaio";
        ottenuto = text.levaSpazi(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        sorgente = "Ascoli Piceno";
        previsto = "AscoliPiceno";
        ottenuto = text.levaSpazi(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        sorgente = "Valle d'Aosta";
        previsto = "Valled'Aosta";
        ottenuto = text.levaSpazi(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        sorgente = "Corea del Nord";
        previsto = "CoreadelNord";
        ottenuto = text.levaSpazi(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        sorgente = " Corea del Nord ";
        previsto = "CoreadelNord";
        ottenuto = text.levaSpazi(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(22)
    @DisplayName("22 - levaTestoPrimaDiEnneRipetizioni")
    public void levaTestoPrimaDiEnneRipetizioni() {
        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow14";
        sorgente2 = SLASH;

        ottenuto = text.testoDopoTagRipetuto(VUOTA, VUOTA, 0);
        assertNotNull(ottenuto);
        assertEquals(VUOTA, ottenuto);

        ottenuto = text.testoDopoTagRipetuto(VUOTA, VUOTA, 1);
        assertNotNull(ottenuto);
        assertEquals(VUOTA, ottenuto);

        ottenuto = text.testoDopoTagRipetuto(VUOTA, sorgente2, 0);
        assertNotNull(ottenuto);
        assertEquals(VUOTA, ottenuto);

        ottenuto = text.testoDopoTagRipetuto(sorgente, VUOTA, 0);
        assertNotNull(ottenuto);
        assertEquals(sorgente, ottenuto);

        ottenuto = text.testoDopoTagRipetuto(VUOTA, sorgente2, 2);
        assertNotNull(ottenuto);
        assertEquals(VUOTA, ottenuto);

        ottenuto = text.testoDopoTagRipetuto(sorgente, VUOTA, 2);
        assertNotNull(ottenuto);
        assertEquals(sorgente, ottenuto);

        ottenuto = text.testoDopoTagRipetuto(sorgente, sorgente2, 0);
        assertNotNull(ottenuto);
        assertEquals(sorgente, ottenuto);

        sorgenteIntero = 1;
        previsto = "gac/Documents/IdeaProjects/operativi/vaadflow14";
        ottenuto = text.testoDopoTagRipetuto(sorgente, sorgente2, 1);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(23)
    @DisplayName("23 - setApici")
    public void setApici() {
        previsto = "\\\"mario\\\"";

        sorgente = "mario";
        ottenuto = text.setApici(sorgente);
        assertEquals(previsto, ottenuto);

        sorgente = "\\\"mario";
        ottenuto = text.setApici(sorgente);
        assertEquals(previsto, ottenuto);

        sorgente = " mario";
        ottenuto = text.setApici(sorgente);
        assertEquals(previsto, ottenuto);

        sorgente = " \\\"mario";
        ottenuto = text.setApici(sorgente);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(24)
    @DisplayName("24 - valueTextReplace")
    public void replaceValue() {
        tag = "\" value \":{\"";
        String tagEnd = "},";
        int ini = 0;
        int end = 0;
        previstoBooleano = true;
        sorgente = "{\" id \":\" usadebug \",\" code \":\" usaDebug \",\" descrizione \":\" Flag generale di debug \",\" type \":\" bool \",\" value \":{\" type \":0,\" data \":[0]},\" note \":\" Ce ne possono essere di specifici, validi solo se questo è vero \",\" _class \":\" preferenza \"}";

        if (sorgente.contains(tag)) {
            ottenutoBooleano = true;
        }
        assertTrue(ottenutoBooleano);

        previsto = "{\" id \":\" usadebug \",\" code \":\" usaDebug \",\" descrizione \":\" Flag generale di debug \",\" type \":\" bool \",\" note \":\" Ce ne possono essere di specifici, validi solo se questo è vero \",\" _class \":\" preferenza \"}";
        ini = sorgente.indexOf(tag);
        end = sorgente.indexOf(tagEnd, ini) + tagEnd.length();
        tag = sorgente.substring(ini, end);
        ottenuto = sorgente.replace(tag, VUOTA);
        assertEquals(previsto, ottenuto);
    }


    private void print(String sorgente, String ottenuto) {
        String sep = " -> ";
        System.out.println(sorgente + sep + ottenuto);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme