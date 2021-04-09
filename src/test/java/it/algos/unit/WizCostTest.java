package it.algos.unit;

import it.algos.test.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 08-apr-2021
 * Time: 22:17
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("WizCostTest")
@DisplayName("Test di unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WizCostTest extends ATest {

    private List<AEWizCost> listaWiz;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();

        MockitoAnnotations.initMocks(this);
    }


    /**
     * Qui passa ad ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    void setUpEach() {
        super.setUp();
        listaWiz = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Tutte")
    void getAll() {
        listaWiz = AEWizCost.getAll();
        printAll("Enumeration completa con tutte le costanti", listaWiz);
    }

    @Test
    @Order(2)
    @DisplayName("2 - getNonModificabile")
    void getNonModificabile() {
        listaWiz = AEWizCost.getNonModificabile();
        print(AETypeWiz.nonModificabile.getDescrizione(), listaWiz);
    }


    @Test
    @Order(3)
    @DisplayName("3 - getRegolatoSistema")
    void getRegolatoSistema() {
        listaWiz = AEWizCost.getRegolatoSistema();
        print(AETypeWiz.regolatoSistema.getDescrizione(), listaWiz);
    }


    @Test
    @Order(4)
    @DisplayName("4 - getNecessarioEntrambi")
    void getNecessarioEntrambi() {
        listaWiz = AEWizCost.getNecessarioEntrambi();
        print(AETypeWiz.necessarioEntrambi.getDescrizione(), listaWiz);
    }

    @Test
    @Order(5)
    @DisplayName("5 - getNecessarioProgetto")
    void getNecessarioProgetto() {
        listaWiz = AEWizCost.getNecessarioProgetto();
        print(AETypeWiz.necessarioProgetto.getDescrizione(), listaWiz);
    }

    @Test
    @Order(6)
    @DisplayName("6 - getNecessarioPackage")
    void getNecessarioPackage() {
        listaWiz = AEWizCost.getNecessarioPackage();
        print(AETypeWiz.necessarioPackage.getDescrizione(), listaWiz);
    }

    @Test
    @Order(7)
    @DisplayName("7 - getNecessitanoValore")
    void getNecessitanoValore() {
        listaWiz = AEWizCost.getNecessitanoValore();
        print("Costanti che hanno bisogno di un valore in runtime", listaWiz);
    }

    @Test
    @Order(8)
    @DisplayName("8 - All tipo AETypeFile.nome")
    void getNome() {
        listaWiz = AEWizCost.getNome();
        print(AETypeFile.nome.name(), listaWiz);
    }

    @Test
    @Order(9)
    @DisplayName("9 - All tipo AETypeFile.file")
    void getFile() {
        listaWiz = AEWizCost.getFile();
        print(AETypeFile.file.name(), listaWiz);
    }

    @Test
    @Order(10)
    @DisplayName("10 - All tipo AETypeFile.source")
    void getSource() {
        listaWiz = AEWizCost.getSource();
        print(AETypeFile.source.name(), listaWiz);
    }

    @Test
    @Order(11)
    @DisplayName("11 - All tipo AETypeFile.dir")
    void getDir() {
        listaWiz = AEWizCost.getDir();
        print(AETypeFile.dir.name(), listaWiz);
    }

    @Test
    @Order(12)
    @DisplayName("12 - All tipo AETypeFile.path")
    void getPath() {
        listaWiz = AEWizCost.getPath();
        print(AETypeFile.path.name(), listaWiz);
    }


    @Test
    @Order(13)
    @DisplayName("13 - getNewProject")
    void getNewProject() {
        listaWiz = AEWizCost.getNewProject();
        print("Soluzioni possibili (nel dialogo) per un nuovo progetto", listaWiz);
    }

    @Test
    @Order(14)
    @DisplayName("14 - getUpdateProject")
    void getUpdateProject() {
        listaWiz = AEWizCost.getUpdateProject();
        print("Soluzioni possibili (nel dialogo) per un Update del progetto", listaWiz);
    }

    @Test
    @Order(15)
    @DisplayName("15 - getNewUpdateProject")
    void getNewUpdateProject() {
        listaWiz = AEWizCost.getNewUpdateProject();
        print("Soluzioni possibili (nel dialogo) per un progetto sia nuovo sia update", listaWiz);
    }


    @Test
    @Order(16)
    @DisplayName("16 - getValide")
    void getValorizzate() {
        listaWiz = AEWizCost.getValide();
        print("Costanti che hanno un valore valido tra quelle che dovrebbero averlo", listaWiz);
    }


    @Test
    @Order(17)
    @DisplayName("17 - getVuote")
    void getVuote() {
        listaWiz = AEWizCost.getVuote();
        print("Costanti a cui manca un valore indispensabile", listaWiz);
    }

//    @Test
//    @Order(19)
//    @DisplayName("19 - getVuoteProject")
//    void getVuoteProject() {
//        listaWiz = AEWizCost.getVuoteProject();
//        print("Quelle di project a cui manca un valore da inserire", listaWiz);
//    }



    private void printAll(String titolo, List<AEWizCost> lista) {
        String serve = " (serve valore) ";
        String nonServe = " (non serve valore) ";
        String sep = DUE_PUNTI_SPAZIO;
        String sep2 = UGUALE_SPAZIATO;
        String sep3 = SEP;
        String riga;

        System.out.print(titolo);
        System.out.println(" (" + lista.size() + ")");
        System.out.println(VUOTA);
        if (array.isAllValid(lista)) {
            for (AEWizCost wiz : lista) {
                riga = VUOTA;
                riga += wiz.getTypeWiz();
                riga += sep3;
                riga += wiz.name();
                riga += sep;
                riga += wiz.getDescrizione();
                riga += sep2 + wiz.get();

                System.out.println(riga);
            }
        }
    }


    private void print(String titolo, List<AEWizCost> lista) {
        String serve = " (serve valore) ";
        String nonServe = " (non serve valore) ";
        String sep = DUE_PUNTI_SPAZIO;
        String sep2 = UGUALE_SPAZIATO;
        String sep3 = SEP;
        String riga;

        System.out.print(titolo);
        System.out.println(" (" + lista.size() + ")");
        System.out.println(VUOTA);
        if (array.isAllValid(lista)) {
            for (AEWizCost wiz : lista) {
                riga = VUOTA;
                riga += wiz.name();
                riga += sep;
                riga += wiz.getDescrizione();
                riga += sep2 + wiz.get();

                System.out.println(riga);
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