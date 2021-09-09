package it.algos.annotation;

import it.algos.simple.backend.packages.*;
import it.algos.simple.backend.packages.alfa.*;
import it.algos.test.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 21-mag-2021
 * Time: 15:12
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("ScriptTest")
@DisplayName("Annotation @AIScript")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScriptTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    AnnotationService service;


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
    @DisplayName("1 - isSovrascrivibile")
    void isSovrascrivibile() {
        AIScript script;

        script = VIA_ENTITY_CLASS.getAnnotation(AIScript.class);
        assertNotNull(script);

        ottenutoBooleano = service.isSovrascrivibile(VIA_ENTITY_CLASS);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isSovrascrivibile(GammaService.class);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(2)
    @DisplayName("2 - getDocFile")
    void getDocFile() {
        AIScript script;
        AEWizDoc docOttenuto;
        AEWizDoc docPrevisto = AEWizDoc.inizioRevisione;

        script = Beta.class.getAnnotation(AIScript.class);
        assertNull(script);
        docOttenuto = service.getDocFile(Beta.class);
        assertNotNull(docOttenuto);
        assertEquals(docPrevisto, docOttenuto);

        script = VIA_ENTITY_CLASS.getAnnotation(AIScript.class);
        assertNotNull(script);
        docOttenuto = service.getDocFile(VIA_ENTITY_CLASS);
        assertNotNull(docOttenuto);
        assertEquals(docPrevisto, docOttenuto);

        docPrevisto = AEWizDoc.inizioFile;
        script = Alfa.class.getAnnotation(AIScript.class);
        assertNotNull(script);
        docOttenuto = service.getDocFile(Alfa.class);
        assertNotNull(docOttenuto);
        assertEquals(docPrevisto, docOttenuto);
    }

    @Test
    @Order(3)
    @DisplayName("3 - getTypeFile")
    void getTypeFile() {
        AIScript script;
        AETypeFile typeOttenuto;
        AETypeFile typePrevisto = AETypeFile.nessuno;

        script = Beta.class.getAnnotation(AIScript.class);
        assertNull(script);
        typeOttenuto = service.getTypeFile(Beta.class);
        assertNotNull(typeOttenuto);
        assertEquals(typePrevisto, typeOttenuto);

        script = Alfa.class.getAnnotation(AIScript.class);
        assertNotNull(script);
        typeOttenuto = service.getTypeFile(Alfa.class);
        assertNotNull(typeOttenuto);
        assertEquals(typePrevisto, typeOttenuto);

        script = VIA_ENTITY_CLASS.getAnnotation(AIScript.class);
        assertNotNull(script);
        typePrevisto = AETypeFile.entity;
        typeOttenuto = service.getTypeFile(VIA_ENTITY_CLASS);
        assertNotNull(typeOttenuto);
        assertEquals(typePrevisto, typeOttenuto);

        script = (AIScript) VIA_LIST_CLASS.getAnnotation(AIScript.class);
        assertNotNull(script);
        typePrevisto = AETypeFile.list;
        typeOttenuto = service.getTypeFile(VIA_LIST_CLASS);
        assertNotNull(typeOttenuto);
        assertEquals(typePrevisto, typeOttenuto);
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