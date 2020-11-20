package it.algos.unit;

import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 19-nov-2020
 * Time: 20:14
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("JavaServiceTest")
@DisplayName("Test di unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AJavaTest extends ATest {


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
    }

    @Test
    @Order(1)
    @DisplayName("Function")
    void function() {
        Function<Long, Long> adder = value -> value + 5;
        Long resultLambda = adder.apply((long) 8);
        System.out.println("resultLambda = " + resultLambda);

        Function<String, String> upper = value -> value.toUpperCase();
        ottenuto = upper.apply("sopra");
        System.out.println("resultLambda = " + ottenuto);
    }

    @Test
    @Order(2)
    @DisplayName("Lambda")
    void lambda() {
        List<Integer> numbers = Arrays.asList(5, 9, 8, 1);
        numbers.forEach(n -> System.out.println(n));

        List<String> lista = Arrays.asList("alfa", "beta", "gamma", "delta");
        lista.forEach(n -> System.out.println(n));

        Runnable funzione = () -> System.out.println("Funziona");
        funzione.run();
    }

    @Test
    @Order(3)
    @DisplayName("Supplier")
    void supplier() {
        // This function returns a random value.
        Supplier<Double> randomValue = () -> Math.random();

        // Print the random value using get()
        System.out.println(randomValue.get());
        System.out.println(randomValue.get());
    }

    @Test
    @Order(4)
    @DisplayName("Supplier again")
    void supplier4() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Supplier<LocalDateTime> s = () -> LocalDateTime.now();
        LocalDateTime time = s.get();

        System.out.println("Non formattato: " + time);

        Supplier<String> s1 = () -> dtf.format(LocalDateTime.now());
        String time2 = s1.get();

        System.out.println("Formattato: " + time2);
    }

    @Test
    @Order(5)
    @DisplayName("Supplier more")
    void supplier5() {
        Supplier<String> supplier = () -> "Marcella bella";
        System.out.println(supplier.get());
    }

    @Test
    @Order(6)
    @DisplayName("Supplier in stream")
    void supplier6() {
        Supplier<Integer> randomNumbersSupp = () -> new Random().nextInt(10);
        Stream.generate(randomNumbersSupp)
                .limit(5)
                .forEach(System.out::println);
    }

    @Test
    @Order(7)
    @DisplayName("Supplier student")
    void supplier7() {
        Supplier<Student> studentSupplier = () -> new Student(1, "Beretta", "M", 19);
        Student student = studentSupplier.get();
        System.out.println(student);

        studentSupplier = () -> new Student(2, "Mantovani", "F", 21);
        student = studentSupplier.get();
        System.out.println(student);
    }

    @Test
    @Order(8)
    @DisplayName("Supplier strings")
    void supplier8() {
        System.out.println("Java8 Supplier strings\n");

        List<String> names = Arrays.asList("Harry", "Daniel", "Lucifer", "April O' Neil");
        names.stream().forEach((item) -> { printNamesSupplier(() -> item); });
    }

    @Test
    @Order(9)
    @DisplayName("Consumer")
    void consumer() {
        System.out.println("Java8 Consumer\n");

        Consumer<String> consumer = AJavaTest::printNamesConsumer;
        consumer.accept("C++");
        consumer.accept("Java");
        consumer.accept("Python");
        consumer.accept("Ruby On Rails");
    }


    private void printNamesSupplier(Supplier<String> supplier) {
        System.out.println(supplier.get());
    }

    private static void printNamesConsumer(String consumer) {
        System.out.println(consumer);
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


    public class Student {

        private int id;

        private String name;

        private String gender;

        private int age;

        public Student(int id, String name, String gender, int age) {
            super();
            this.id = id;
            this.name = name;
            this.gender = gender;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", age=" + age + "]";
        }

    }

}