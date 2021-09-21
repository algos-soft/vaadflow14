package it.algos.unit;

import it.algos.simple.backend.packages.*;
import it.algos.simple.backend.packages.bolla.*;
import it.algos.test.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 16-mag-2021
 * Time: 17:41
 * <p>
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("ClassService - Utility di Class e path.")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClassServiceTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private ClassService service;

    private static String[] CANONICAL() {
        return new String[]{VUOTA, "CanonicalNameInesistente", VIA_ENTITY_CLASS.getCanonicalName(), VIA_ENTITY_CLASS.getCanonicalName() + JAVA_SUFFIX};
    }

    private static String[] SIMPLE() {
        return new String[]{VUOTA, "NomeClasseInesistente", VIA_ENTITY_CLASS.getSimpleName(), VIA_ENTITY_CLASS.getSimpleName() + JAVA_SUFFIX, "Via", "via", "Bolla"};
    }

    private static String[] PATH() {
        return new String[]{VUOTA, "PathErrato", "/Users/gac/IdeaProjects/operativi/vaadwiki/src/main/java/backend/packages/anagrafica/via/Via", "/Users/gac/Documents/IdeaProjects/operativi/vaadwiki/src/main/java/it/algos/vaadflow14/backend/packages/anagrafica/via/Via", "/Users/gac/Documents/IdeaProjects/operativi/vaadwiki/src/main/java/it/algos/vaadflow14/backend/packages/anagrafica/via/Via.java"};
    }

    private static Class[] CLAZZ() {
        return new Class[]{null, Via.class, Bolla.class, AIType.class, GammaService.class};
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    void setUpIniziale() {
        super.setUpStartUp();

        //--reindirizzo l'istanza della superclasse
        service = classService;
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    void setUpEach() {
        super.setUp();
    }

    @Test
    @Order(1)
    @DisplayName("1 - Test 'a freddo' (senza service)")
    void first() {
        Class clazz = VIA_ENTITY_CLASS;
        String canonicalName = clazz.getCanonicalName();
        assertTrue(textService.isValid(canonicalName));
        System.out.println(canonicalName);
        Class clazz2 = null;

        try {
            clazz2 = Class.forName(canonicalName);
        } catch (Exception unErrore) {
            System.out.println(String.format(unErrore.getMessage()));
        }
        assertNotNull(clazz2);
        System.out.println(clazz2.getSimpleName());
        System.out.println(clazz2.getName());
        System.out.println(clazz2.getCanonicalName());
    }

    @Test
    @Order(2)
    @DisplayName("2 - getNameFromPath")
    void getNameFromPath() {
        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/vaadwiki/src/main/java/it/algos/vaadflow14/backend/packages/anagrafica/via/Via.java";

        ottenuto = service.getNameFromPath(VUOTA);
        assertTrue(textService.isEmpty(ottenuto));

        previsto = "it/algos/vaadflow14/backend/packages/anagrafica/via/Via";
        ottenuto = service.getNameFromPath(sorgente);
        assertEquals(previsto, ottenuto);
        System.out.println(sorgente);
        System.out.println(previsto);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "CANONICAL")
    @Order(3)
    @DisplayName("3 - getClazzFromCanonicalName")
    void getClazzFromCanonicalName(String canonicalName) {
        System.out.println("3 - Ricerca di una classe dal canonicalName");

        try {
            clazz = service.getClazzFromCanonicalName(canonicalName);
            printClazz(canonicalName, clazz);
            Assertions.assertNotNull(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
            Assertions.assertNull(clazz);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "SIMPLE")
    @Order(4)
    @DisplayName("4 - getClazzFromSimpleName")
    void getClazzFromSimpleName(String simpleName) {
        System.out.println("4 - Ricerca di una classe dal simpleName");

        try {
            clazz = service.getClazzFromSimpleName(simpleName);
            printClazz(simpleName, clazz);
            Assertions.assertNotNull(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
            Assertions.assertNull(clazz);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "PATH")
    @Order(5)
    @DisplayName("5 - getClazzFromPath")
    void getClazzFromPath(String pathCompleto) {
        System.out.println("5 - Ricerca di una classe dal path completo");

        try {
            clazz = service.getClazzFromPath(pathCompleto);
            printClazz(pathCompleto, clazz);
            Assertions.assertNotNull(clazz);
        } catch (AlgosException unErrore) {
            printError(unErrore);
            Assertions.assertNull(clazz);
        }
    }

    @Test
    @Order(6)
    @DisplayName("6- getProjectName")
    void getProjectName() {
        ottenuto = service.getProjectName();
        assertTrue(textService.isValid(ottenuto));
        System.out.println(String.format("Nome del progetto corrente: %s", ottenuto));
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ")
    @Order(7)
    @DisplayName("7 - getEntityFromClazz")
    void getEntityFromClazz(Class clazz) {
        System.out.println("7 - Creazione della entityBean di una classe");

        try {
            entityBean = service.getEntityFromClazz(clazz);
            printEntityBeanFromClazz(clazz);
            Assertions.assertNotNull(entityBean);
        } catch (AlgosException unErrore) {
            printError(unErrore);
            Assertions.assertNull(entityBean);
        }
    }


    void printEntityBeanFromClazz(final Class clazz) {
        if (clazz != null) {
            System.out.println(String.format("Creata una entityBean (vuota) di classe %s", clazz.getSimpleName()));
        }
        else {
            System.out.print("Non esiste la classe indicata");
        }
        System.out.println(VUOTA);
    }

    void printEntityBean(final AEntity entityBean) {
        if (entityBean != null) {
            System.out.println("EntityBean");
            System.out.print("KeyID");
            System.out.print(FORWARD);
            System.out.println(entityBean.getId());
        }
        else {
            System.out.print("Non esiste una entityBean");
        }
        System.out.println(VUOTA);
    }

    void printClazz(final String sorgente, final Class clazz) {
        if (clazz != null) {
            System.out.println("Classe trovata");
            System.out.print("Sorgente");
            System.out.print(FORWARD);
            System.out.println(sorgente);

            System.out.print("Name");
            System.out.print(FORWARD);
            System.out.println(clazz.getName());

            System.out.print("SimpleName");
            System.out.print(FORWARD);
            System.out.println(clazz.getSimpleName());

            System.out.print("CanonicalName");
            System.out.print(FORWARD);
            System.out.println(clazz.getCanonicalName());
        }
        else {
            System.out.print("Non esiste la classe");
            System.out.print(FORWARD);
            System.out.println(sorgente);
        }
        System.out.println(VUOTA);
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
    @AfterAll
    void tearDownAll() {
    }

}