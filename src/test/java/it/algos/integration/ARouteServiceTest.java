package it.algos.integration;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.router.*;
import it.algos.simple.*;
import it.algos.test.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.ui.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;

/**
 * Project wikibio
 * Created by Algos
 * User: gac
 * Date: mer, 31-mar-2021
 * Time: 19:41
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Text Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ARouteServiceTest extends ATest {

    /**
     * The Service.
     */
    @InjectMocks
    ARouteService service;

    private QueryParameters queryParameters;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();

        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        assertNotNull(service);

        service.text = text;
        service.array = array;
        service.classService = classService;
    }


    @BeforeEach
    void setUpEach() {
        queryParameters = null;
    }


    @Test
    @Order(1)
    @DisplayName("getQueryList")
    void getQueryList() {
        queryParameters = service.getQueryList((Class)null);
        assertNull(queryParameters);

        queryParameters = service.getQueryList(VIA_ENTITY_CLASS);
        assertNotNull(queryParameters);
        assertEquals(VIA_ENTITY_CLASS.getCanonicalName(), queryParameters.getParameters().get(KEY_BEAN_CLASS).get(0));
        printQueryParameters("getQueryList",queryParameters);

        queryParameters = service.getQueryList(ANNO_ENTITY_CLASS.getCanonicalName());
        assertNotNull(queryParameters);
        assertEquals(ANNO_ENTITY_CLASS.getCanonicalName(), queryParameters.getParameters().get(KEY_BEAN_CLASS).get(0));
        printQueryParameters("getQueryList",queryParameters);
    }


    @Test
    @Order(2)
    @DisplayName("getQueryForm")
    void getQueryForm() {
        queryParameters = service.getQueryForm((Class)null);
        assertNull(queryParameters);

        queryParameters = service.getQueryForm(ANNO_ENTITY_CLASS);
        assertNotNull(queryParameters);
        assertEquals(ANNO_ENTITY_CLASS.getCanonicalName(), queryParameters.getParameters().get(KEY_BEAN_CLASS).get(0));
        printQueryParameters("getQueryForm",queryParameters);

        queryParameters = service.getQueryForm(VIA_ENTITY_CLASS);
        assertNotNull(queryParameters);
        assertEquals(VIA_ENTITY_CLASS.getCanonicalName(), queryParameters.getParameters().get(KEY_BEAN_CLASS).get(0));
        printQueryParameters("getQueryForm",queryParameters);

        queryParameters = service.getQueryForm(VIA_ENTITY_CLASS, AEOperation.edit);
        assertNotNull(queryParameters);
        assertEquals(VIA_ENTITY_CLASS.getCanonicalName(), queryParameters.getParameters().get(KEY_BEAN_CLASS).get(0));
        printQueryParameters("getQueryForm",queryParameters);

        queryParameters = service.getQueryForm(VIA_ENTITY_CLASS, AEOperation.edit,"keyTest");
        assertNotNull(queryParameters);
        assertEquals(VIA_ENTITY_CLASS.getCanonicalName(), queryParameters.getParameters().get(KEY_BEAN_CLASS).get(0));
        printQueryParameters("getQueryForm",queryParameters);

        queryParameters = service.getQueryForm(VIA_ENTITY_CLASS, AEOperation.edit,"keyTest","keyPrima","keyDopo");
        assertNotNull(queryParameters);
        assertEquals(VIA_ENTITY_CLASS.getCanonicalName(), queryParameters.getParameters().get(KEY_BEAN_CLASS).get(0));
        printQueryParameters("getQueryForm",queryParameters);
    }



    void printQueryParameters(QueryParameters queryParameters) {
        printQueryParameters(VUOTA,queryParameters);
    }


    void printQueryParameters(String titolo,QueryParameters queryParameters) {
        Map<String, List<String>> mappa = queryParameters.getParameters();
        if (text.isValid(titolo)) {
            System.out.println(titolo);
        }

        System.out.println("QueryString: " + queryParameters.getQueryString());
        System.out.println("Numero di parametri: " + mappa.size());
        if (mappa.size() > 0) {
            for (String key : mappa.keySet()) {
                System.out.println("Parametro: " + key + " = " + text.setNoQuadre((String)mappa.get(key).get(0)));
            }
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
