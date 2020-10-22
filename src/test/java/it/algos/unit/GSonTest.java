package it.algos.unit;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import it.algos.vaadflow14.backend.service.AMongoService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 21-ott-2020
 * Time: 11:31
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("MongoServiceTest")
@DisplayName("Test di unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GSonTest extends ATest {

    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    AMongoService service;

    private Gson gson;

    private MongoClient mongoClient = new MongoClient("localhost");

    private MongoDatabase database = mongoClient.getDatabase("vaadflow14");

    private MongoCollection collection = database.getCollection("mese");

    private int offset;

    private int limit;


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
        service.text = text;
        service.array = array;

        mongoClient = new MongoClient("localhost");
        database = mongoClient.getDatabase("vaadflow14");
    }


    /**
     * Qui passa ad ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    void setUpEach() {
        super.setUp();

        gson = new Gson();
        collection = null;
        offset = 0;
        limit = 0;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Student instance")
    void studentInstance() {
        //Create a studente instance
        Student student = new Student();
        student.setAge(10);
        student.setNome("Mahesh");

        //map Student object to JSON content
        String jsonString = gson.toJson(student);
        System.out.println(jsonString);

        //map JSON content to Student object
        Student student1 = gson.fromJson(jsonString, Student.class);
        System.out.println(student1);
    }


    @Test
    @Order(2)
    @DisplayName("2 - Mese instance")
    void meseInstance() {
        Class<? extends AEntity> clazz = Mese.class;
        String clazzName = clazz.getSimpleName().toLowerCase();

        //Create an instance
        Mese mese = Mese.builderMese().mese("marzo").giorni(30).giorniBisestile(30).sigla("mar").build();

        //map Mese object to JSON content
        String jsonString = gson.toJson(mese);
        System.out.println(jsonString);

        //map JSON content to Mese object
        AEntity mese1 = gson.fromJson(jsonString, clazz);
        System.out.println(mese1);
    }


    @Test
    @Order(3)
    @DisplayName("3 - Inner class")
    void innerClass() {
        Student student = new Student();
        student.setRollNo(1);
        Student.Name name = student.new Name();

        name.firstName = "Mahesh";
        name.lastName = "Kumar";
        student.setName(name);

        String jsonString = gson.toJson(student);
        System.out.println(jsonString);
        student = gson.fromJson(jsonString, Student.class);

        System.out.println("Roll No: " + student.getRollNo());
        System.out.println("First Name: " + student.getName().firstName);
        System.out.println("Last Name: " + student.getName().lastName);

        String nameString = gson.toJson(name);
        System.out.println(nameString);

        name = gson.fromJson(nameString, Student.Name.class);
        System.out.println(name.getClass());
        System.out.println("First Name: " + name.firstName);
        System.out.println("Last Name: " + name.lastName);
    }


    @Test
    @Order(4)
    @DisplayName("4 - Anno instance")
    void annoInstance() {
        Class<? extends AEntity> clazz = Anno.class;
        String clazzName = clazz.getSimpleName().toLowerCase();

        //Create an instance
        Secolo secolo = Secolo.builderSecolo().ordine(4).secolo("XX secolo").anteCristo(false).inizio(34).fine(87).build();
        secolo.id = "mario";
        Anno annoPre = Anno.builderAnno().ordine(3).anno("1874").bisestile(false).secolo(secolo).build();
        annoPre.id = "francesco";

        //map Mese object to JSON content
        String jsonString = gson.toJson(annoPre);
        System.out.println(jsonString);

        //map JSON content to Mese object
        AEntity annoPost = gson.fromJson(jsonString, clazz);
        System.out.println(annoPost);
    }


    @Test
    @Order(5)
    @DisplayName("5 - from real database")
    void execute() {
        offset = 2970;
        limit = 1;
        String jsonString;
        Anno anno;

        collection = database.getCollection("anno");
        Collection<Document> documents = collection.find().skip(offset).limit(limit).into(new ArrayList());

        for (Document doc : documents) {
            jsonString = gson.toJson(doc);
            jsonString = jsonString.replace("_id", "id");
            anno = gson.fromJson(jsonString, Anno.class);
            Assert.assertEquals("1971", anno.anno);
            System.out.println(anno.anno);
            Assert.assertEquals("XX secolo", anno.secolo.secolo);
            System.out.println(anno.secolo.secolo);
        }



//        BasicDBObject command = new BasicDBObject("find", "mese");
//        Document alfa = mongoOp.executeCommand(String.valueOf(command));
//        //        String gamma = alfa.getString("cursor");
//        ObjectId beta = alfa.getObjectId("cursor");
//        int a = 87;
//        //        String jsonCommand = "db.getCollection('secolo').find({}, {\"_id\":0,\"ordine\": 1})";
//        //        Object alfga = mongo.mongoOp.executeCommand(jsonCommand);

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


    class Student {

        private String nome;

        private Name name;

        private int rollNo;

        private int age;


        public Student() {
        }


        public int getRollNo() {
            return rollNo;
        }


        public void setRollNo(int rollNo) {
            this.rollNo = rollNo;
        }


        public Name getName() {
            return name;
        }


        public void setName(Name name) {
            this.name = name;
        }


        public int getAge() {
            return age;
        }


        public void setAge(int age) {
            this.age = age;
        }


        public String getNome() {
            return nome;
        }


        public void setNome(String nome) {
            this.nome = nome;
        }


        public String toString() {
            return "Student [nome:" + nome + ", age:" + age + "]";
        }


        class Name {

            public String firstName;

            public String lastName;

        }

    }

}