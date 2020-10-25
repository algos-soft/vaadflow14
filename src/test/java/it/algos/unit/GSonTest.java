package it.algos.unit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import org.bson.Document;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VIRGOLA;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    private Gson gSon;

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

        gSon = new Gson();
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
        String jsonString = gSonService.toStringa(student);
        System.out.println(jsonString);

        //map JSON content to Student object
        Student student1 = gSon.fromJson(jsonString, Student.class);
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
        String jsonString = gSonService.toStringa(mese);
        System.out.println(jsonString);

        //map JSON content to Mese object
        AEntity mese1 = gSon.fromJson(jsonString, clazz);
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

        String jsonString = gSonService.toStringa(student);
        System.out.println(jsonString);
        student = gSon.fromJson(jsonString, Student.class);

        System.out.println("Roll No: " + student.getRollNo());
        System.out.println("First Name: " + student.getName().firstName);
        System.out.println("Last Name: " + student.getName().lastName);

        String nameString = gSonService.toStringa(name);
        System.out.println(nameString);

        name = gSon.fromJson(nameString, Student.Name.class);
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
        String jsonString = gSonService.toStringa(annoPre);
        System.out.println(jsonString);
        gSonService.toMap(annoPre);

        //map JSON content to Mese object
        AEntity annoPost = gSon.fromJson(jsonString, clazz);
        System.out.println(annoPost);
    }


    @Test
    @Order(5)
    @DisplayName("5 - estraeGraffe")
    void estraeGraffe() {
        String jsonString;
        String dbRefString;
        Long propertiesNumber;
        AEntity entity;
        String clazzName = "via";
        String clazzName2 = "anno";
        Class clazz = Secolo.class;
        previstoIntero = 1;
        collection = database.getCollection(clazzName);
        propertiesNumber = collection.countDocuments();
        assertNotNull(propertiesNumber);
        System.out.println("Nella collezione 'Via' ci sono " + propertiesNumber + " elementi");

        Document doc = (Document) collection.find().first();
        jsonString = doc.toJson();
        listaStr = gSonService.estraeGraffe(jsonString);
        assertNotNull(listaStr);
        Assert.assertEquals(previstoIntero, listaStr.size());

        previstoIntero = 2;
        collection = database.getCollection(clazzName2);
        propertiesNumber = collection.countDocuments();
        assertNotNull(propertiesNumber);
        System.out.println("Nella collezione 'Anno' ci sono " + propertiesNumber + " elementi");

//        doc = (Document) collection.find().first();
//        jsonString = gSonService.toStringa(doc);
//        listaStr = gSonService.estraeGraffe(jsonString);
//        assertNotNull(listaStr);
//        Assert.assertEquals(previstoIntero, listaStr.size());
//        System.out.println(listaStr.get(0));
//
//        dbRefString = listaStr.get(0);
//        entity = (AEntity) gSon.fromJson(dbRefString, clazz);
//        assertNotNull(entity);
    }


    @Test
    @Order(6)
    @DisplayName("6 - from real database")
    void execute() {
        offset = 2970;
        limit = 1;
        String jsonString;
        Class clazz = Anno.class;
        AEntity entity;
        List<Field> listaRef;
        Anno anno = null;
        String clazzName = "anno";
        String[] parti = null;

        collection = database.getCollection(clazzName);
        Collection<Document> documents = collection.find().skip(offset).limit(limit).into(new ArrayList());
        listaRef = annotation.getDBRefFields(clazz);

        for (Document doc : documents) {
            jsonString = gSonService.toStringa(doc);
            entity = (AEntity) gSon.fromJson(jsonString, clazz);
            assertNotNull(entity.id);

            if (entity instanceof Anno) {
                anno = (Anno) entity;
            }
            parti = jsonString.split(VIRGOLA);
            JsonElement element = gSon.toJsonTree(doc);
            JsonObject obj = element.getAsJsonObject();
            JsonElement sec = obj.get("secolo");
            JsonObject obj2 = sec.getAsJsonObject();
            JsonElement sec2 = obj2.get("id");
            String value = sec2.getAsString();
            if (listaRef != null && listaRef.size() > 0) {
                for (Field field : listaRef) {
                    clazzName = field.getName();
                    collection = database.getCollection(clazzName);
                    //                    collection.find({ -id: { $is:  } })
                }
            }

            Assert.assertEquals("1971", anno.anno);
            System.out.println(anno.anno);
            //            Assert.assertEquals("XX secolo", anno.secolo.secolo);
            //            System.out.println(anno.secolo.secolo);
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