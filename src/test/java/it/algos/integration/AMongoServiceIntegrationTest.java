package it.algos.integration;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.algos.simple.SimpleApplication;
import it.algos.simple.backend.packages.Omega;
import it.algos.unit.ATest;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.packages.crono.giorno.Giorno;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import it.algos.vaadflow14.backend.packages.crono.mese.MeseLogic;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import it.algos.vaadflow14.backend.packages.geografica.regione.Regione;
import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.backend.service.AMongoService;
import it.algos.vaadflow14.backend.wrapper.AFiltro;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.SEP;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 17-lug-2020
 * Time: 06:27
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Mongo Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AMongoServiceIntegrationTest extends ATest {

    /**
     * Inietta da Spring
     */
    @Autowired
    public MongoOperations mongoOp;

    /**
     * The Service.
     */
    @Autowired
    AMongoService service;

    @InjectMocks
    MeseLogic meseLogic;

    private Mese meseUno;

    private Mese meseDue;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    void setUpAll() {
        super.setUpStartUp();

        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        Assertions.assertNotNull(service);
        MockitoAnnotations.initMocks(meseLogic);
        Assertions.assertNotNull(meseLogic);
        Assertions.assertNotNull(mongoOp);

        service.annotation = annotation;
        service.text = text;
        service.mongoOp = mongoOp;
        service.logger = logger;
        annotation.text = text;
        service.mongoOp = mongoOp;
        meseLogic.mongo = service;
        mongo.mongoOp = mongoOp;


        this.cancellazioneEntitiesProvvisorie();
        this.creazioneInizialeEntitiesProvvisorie();
    }


    @BeforeEach
    void setUpEach() {
        super.setUp();
    }


    @Test
    @Order(0)
    @DisplayName("0 - execute")
    void execute() {
        String jsonCommand = "db.getCollection('secolo').find({}, {\"_id\":0,\"ordine\": 1})";
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("vaadflow14");
        MongoCollection collection = database.getCollection("mese");

        BasicDBObject command = new BasicDBObject("find", "mese");
        Document alfa = mongoOp.executeCommand(String.valueOf(command));
//        String gamma = alfa.getString("cursor");
        ObjectId beta = alfa.getObjectId("cursor");
        int a = 87;
        //        String jsonCommand = "db.getCollection('secolo').find({}, {\"_id\":0,\"ordine\": 1})";
        //        Object alfga = mongo.mongoOp.executeCommand(jsonCommand);

    }


    @Test
    @Order(1)
    @DisplayName("1 - isValid")
    void isValid() {
        System.out.println("isValid() rimanda a count()");

        ottenutoBooleano = service.isValid(null);
        Assert.assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid(Omega.class);
        Assert.assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid(Mese.class);
        Assert.assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isValid(MeseLogic.class);
        Assert.assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(2)
    @DisplayName("2 - isEmpty")
    void isEmpty() {
        System.out.println("isEmpty() rimanda a count()");

        ottenutoBooleano = service.isEmpty(null);
        Assert.assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEmpty(Omega.class);
        Assert.assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEmpty(Mese.class);
        Assert.assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isEmpty(MeseLogic.class);
        Assert.assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(3)
    @DisplayName("3 - count")
    void count() {
        System.out.println("metodo semplice per l'intera collection");
        System.out.println("rimanda al metodo base con filtro (query) nullo");

        ottenutoIntero = service.count(Regione.class);
        Assert.assertTrue(ottenutoIntero > 0);

        ottenutoIntero = service.count(Omega.class);
        Assert.assertTrue(ottenutoIntero == 0);

        ottenutoIntero = service.count(Mese.class);
        Assert.assertTrue(ottenutoIntero == 12);

        ottenutoIntero = service.count((Class) null);
        Assert.assertTrue(ottenutoIntero == 0);
    }


    @Test
    @Order(4)
    @DisplayName("4 - countQuery")
    void countQuery() {
        System.out.println("metodo base eventualmente filtrato con una query");

        Query query = new Query();

        previstoIntero = 366;
        ottenutoIntero = service.count(Giorno.class, (Query) null);
        Assert.assertTrue(ottenutoIntero > 0);
        Assert.assertEquals(previstoIntero, ottenutoIntero);

        previstoIntero = 31;
        query.addCriteria(Criteria.where("mese.$id").is("maggio"));
        ottenutoIntero = service.count(Giorno.class, query);
        Assert.assertTrue(ottenutoIntero > 0);
        Assert.assertEquals(previstoIntero, ottenutoIntero);
    }


    @Test
    @Order(5)
    @DisplayName("5 - findAllFiltri")
    void findAllFiltri() {
        System.out.println("una serie di filtri");

        listaFiltri = new ArrayList<>();

        listaBean = service.findAll(Mese.class, (List<AFiltro>) null);
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() > 1);
        System.out.println(VUOTA);
        printLista(listaBean);

        filtro = new AFiltro(Criteria.where("mese.$id").is("marzo"));
        listaFiltri.add(filtro);
        listaBean = service.findAll(Giorno.class, listaFiltri);
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() > 1);
        System.out.println(VUOTA);
        printLista(listaBean);

        listaFiltri = new ArrayList<>();
        filtro = new AFiltro(Criteria.where("secolo").is("xxsecolo"));
        listaFiltri.add(filtro);
        sort = Sort.by(Sort.Direction.ASC, "ordine");
        filtro = new AFiltro(Criteria.where("ordine").gt(3970), sort);
        listaFiltri.add(filtro);
        listaBean = service.findAll(Anno.class, listaFiltri);
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() > 1);
        System.out.println(VUOTA);
        printLista(listaBean);
    }


    @Test
    @Order(6)
    @DisplayName("6 - findAllFiltriSort")
    void findAllFiltriSort() {
        System.out.println("una serie di filtri e ordinamenti vari");

        listaFiltri = new ArrayList<>();
        listaBean = service.findAll(Mese.class, (List<AFiltro>) null);
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() > 1);
        System.out.println(VUOTA);
        printLista(listaBean);

        listaFiltri = new ArrayList<>();
        sort = Sort.by(Sort.Direction.DESC, "mese");
        filtro = new AFiltro(sort);
        listaFiltri.add(filtro);
        listaBean = service.findAll(Mese.class, listaFiltri);
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() > 1);
        System.out.println(VUOTA);
        printLista(listaBean);

        listaFiltri = new ArrayList<>();
        filtro = new AFiltro(Criteria.where("ordine").lt(10));
        listaFiltri.add(filtro);
        sort = Sort.by(Sort.Direction.ASC, "alfadue");
        filtro = new AFiltro(Criteria.where("ue").is(true), sort);
        listaFiltri.add(filtro);
        listaBean = service.findAll(Stato.class, listaFiltri);
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() > 1);
        System.out.println(VUOTA);
        printLista(listaBean);
    }


    @Test
    @Order(7)
    @DisplayName("7 - find")
    void find() {
        System.out.println("find rimanda a findAll");

        listaBean = service.find((Class) null);
        Assert.assertNull(listaBean);

        listaBean = service.find(Secolo.class);
        Assert.assertNotNull(listaBean);
        System.out.println(VUOTA);
        printLista(listaBean);
    }


    @Test
    @Order(8)
    @DisplayName("8 - findAll")
    void findAll() {
        System.out.println("tutta la collection");

        listaBean = service.findAll((Class) null);
        Assert.assertNull(listaBean);

        listaBean = service.findAll(Mese.class);
        Assert.assertNotNull(listaBean);
        System.out.println(VUOTA);
        printLista(listaBean);
    }


    @Test
    @Order(9)
    @DisplayName("9 - findAllQuery")
    void findAllQuery() {
        System.out.println("metodo base - tutta la collection filtrata da una query");

        query = new Query();
        sort = Sort.by(Sort.Direction.DESC, "ordine");

        listaBean = service.findAll((Class) null, (Query) null);
        Assert.assertTrue(array.isEmpty(listaBean));

        listaBean = service.findAll(Mese.class, (Query) null);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query nulla");

        listaBean = service.findAll(Mese.class, query);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query vuota ordine interno");

        query.with(sort);
        listaBean = service.findAll(Mese.class, query);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query vuota ma ordinata");

        query = new Query();
        query.with(sort);
        query.addCriteria(Criteria.where("ordine").lt(8));
        listaBean = service.findAll(Mese.class, query);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query filtrata e ordinata");

        query = new Query();
        query.addCriteria(Criteria.where("ordine").lt(8));
        query.with(sort);
        query.fields().exclude("ordine");
        listaBean = service.findAll(Mese.class, query);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query filtrata e ordinata e con campo escluso");

        query = new Query();
        query.addCriteria(Criteria.where("ordine").lt(8));
        query.with(sort);
        query.fields().include("mese");
        listaBean = service.findAll(Mese.class, query);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query filtrata e ordinata e con singolo campo selezionato");
    }


    @Test
    @Order(10)
    @DisplayName("findAllQueryProjection")
    void findAllQueryProjection() {
        System.out.println("metodo base - la projection non si usa");
        System.out.println(VUOTA);

        query = new Query();
        sort = Sort.by(Sort.Direction.ASC, "code");

        listaBean = service.findAll((Class) null, (Query) null, VUOTA);
        Assert.assertTrue(array.isEmpty(listaBean));

        listaBean = service.findAll(Mese.class, (Query) null, VUOTA);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query nulla");

        listaBean = service.findAll(Mese.class, query, VUOTA);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query vuota");

        query.with(sort);
        listaBean = service.findAll(Mese.class, query, VUOTA);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query ordinata");

        query.addCriteria(Criteria.where("ordine").lt(10));
        listaBean = service.findAll(Mese.class, query, VUOTA);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query filtrata e ordinata");

        query.fields().exclude("ordine");
        listaBean = service.findAll(Mese.class, query, VUOTA);
        Assert.assertTrue(array.isValid(listaBean));
        printLista(listaBean, "lista con query filtrata e ordinata e selezione dei campi");

        String projection = "{'code': 1}";
        query = new Query();
        listaBean = service.findAll(Mese.class, query, projection);
        Assert.assertFalse(array.isValid(listaBean));
        printLista(listaBean, "la projection non funziona");
    }


    @Test
    @Order(11)
    @DisplayName("11 - findId")
    void findId() {
        System.out.println("find rimanda a findById");
        System.out.println(VUOTA);

        entityBean = service.find(Mese.class, "brumaio");
        Assert.assertNull(entityBean);

        previsto = "ottobre";
        entityBean = service.find(Mese.class, "ottobre");
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);
    }


    @Test
    @Order(12)
    @DisplayName("12 - findById")
    void findById() {
        System.out.println("singola entity recuperata da keyID");
        System.out.println(VUOTA);

        entityBean = service.findById(Mese.class, "brumaio");
        Assert.assertNull(entityBean);

        previsto = "ottobre";
        entityBean = service.findById(Mese.class, "ottobre");
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);
    }


    @Test
    @Order(13)
    @DisplayName("13 - findByKey")
    void findByKey() {
        System.out.println("singola entity recuperata da keyID");
        System.out.println(VUOTA);

        entityBean = service.findByKey(Mese.class, "brumaio");
        Assert.assertNull(entityBean);

        previsto = "ottobre";
        entityBean = service.findByKey(Mese.class, "ottobre");
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);
    }


    @Test
    @Order(14)
    @DisplayName("14 - findOneFirst")
    void findOneFirst() {
        System.out.println("singola entity - se ce n'è più di una restituisce solo la prima");

        entityBean = service.findOneFirst((Class) null, VUOTA, VUOTA);
        Assert.assertNull(entityBean);

        entityBean = service.findOneFirst((Class) null, "nome", "user");
        Assert.assertNull(entityBean);

        entityBean = service.findOneFirst(Secolo.class, VUOTA, "xxsecolo");
        Assert.assertNull(entityBean);

        entityBean = service.findOneFirst(Secolo.class, "secolo", VUOTA);
        Assert.assertNull(entityBean);

        entityBean = service.findOneFirst(Secolo.class, VUOTA, VUOTA);
        Assert.assertNull(entityBean);

        entityBean = service.findOneFirst(Secolo.class, "anteCristo", "true");
        Assert.assertNull(entityBean);

        entityBean = service.findOneFirst(Secolo.class, "secolo", "francesco");
        Assert.assertNull(entityBean);

        previsto = "xxsecolo";
        entityBean = service.findOneFirst(Secolo.class, "secolo", "XX secolo");
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Secolo) entityBean).id, previsto);
        System.out.println(entityBean);

        entityBean = service.findOneFirst(Mese.class, "mese", VUOTA);
        Assert.assertNull(entityBean);

        previsto = "marzo";
        entityBean = service.findOneFirst(Mese.class, "sigla", "mar");
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);

        previsto = "giugno";
        entityBean = service.findOneFirst(Mese.class, "ordine", 6);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);

        previsto = "febbraio";
        entityBean = service.findOneFirst(Mese.class, "giorni", 28);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);

        previsto = "gennaio";
        entityBean = service.findOneFirst(Mese.class, "giorni", 31);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);

        query = new Query();
        sort = Sort.by(Sort.Direction.ASC, "mese");

        entityBean = service.findOneFirst((Class) null, (Query) null);
        Assert.assertNull(entityBean);

        entityBean = service.findOneFirst((Class) null, query);
        Assert.assertNull(entityBean);

        entityBean = service.findOneFirst(Mese.class, (Query) null);
        Assert.assertNull(entityBean);

        previsto = "gennaio";
        entityBean = service.findOneFirst(Mese.class, query);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());

        query = new Query();
        previsto = "aprile";
        query.addCriteria(Criteria.where("mese").is("aprile"));
        entityBean = service.findOneFirst(Mese.class, query);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());

        previsto = "agosto";
        query = new Query();
        query.addCriteria(Criteria.where("giorni").is(31));
        sort = Sort.by(Sort.Direction.ASC, "mese");
        query.with(sort);
        entityBean = service.findOneFirst(Mese.class, query);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());

        previsto = "gennaio";
        query = new Query();
        query.addCriteria(Criteria.where("giorni").is(31));
        sort = Sort.by(Sort.Direction.ASC, "ordine");
        query.with(sort);
        entityBean = service.findOneFirst(Mese.class, query);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());

        previsto = "febbraio";
        query = new Query();
        query.addCriteria(Criteria.where("giorni").is(28));
        entityBean = service.findOneFirst(Mese.class, query);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());

        previsto = "febbraio";
        entityBean = service.findOneFirst(Mese.class, new Query(Criteria.where("giorni").is(28)));
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());

        previsto = "gennaio";
        entityBean = service.findOneFirst(Mese.class);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
    }


    @Test
    @Order(15)
    @DisplayName("15 - findOneUnique")
    void findOneUnique() {
        System.out.println("singola entity - se ce n'è più di una NON restituisce nulla");
        query = new Query();
        sort = Sort.by(Sort.Direction.ASC, "nome");

        entityBean = service.findOneUnique((Class) null, VUOTA, VUOTA);
        Assert.assertNull(entityBean);

        entityBean = service.findOneUnique((Class) null, "mese", "user");
        Assert.assertNull(entityBean);

        entityBean = service.findOneUnique(Mese.class, VUOTA, "marzo");
        Assert.assertNull(entityBean);

        entityBean = service.findOneUnique(Mese.class, "mese", VUOTA);
        Assert.assertNull(entityBean);

        entityBean = service.findOneUnique(Mese.class, VUOTA, VUOTA);
        Assert.assertNull(entityBean);

        entityBean = service.findOneUnique(Mese.class, "giorni", 30);
        Assert.assertNull(entityBean);

        previsto = "febbraio";
        entityBean = service.findOneUnique(Mese.class, "giorni", 28);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);

        entityBean = service.findOneUnique(Mese.class, "nome", VUOTA);
        Assert.assertNull(entityBean);

        previsto = "marzo";
        entityBean = service.findOneUnique(Mese.class, "sigla", "mar");
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);

        previsto = "ottobre";
        entityBean = service.findOneUnique(Mese.class, "mese", "ottobre");
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);

        previsto = "febbraio";
        entityBean = service.findOneUnique(Mese.class, "giorni", 28);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(((Mese) entityBean).mese, previsto);
        System.out.println(entityBean);

        previsto = "gennaio";
        entityBean = service.findOneUnique(Mese.class, "giorni", 31);
        Assert.assertNull(entityBean);

        entityBean = service.findOneUnique((Class) null, (Query) null);
        Assert.assertNull(entityBean);

        entityBean = service.findOneUnique((Class) null, query);
        Assert.assertNull(entityBean);

        entityBean = service.findOneUnique(Mese.class, (Query) null);
        Assert.assertNull(entityBean);

        previsto = "gennaio";
        entityBean = service.findOneUnique(Mese.class, query);
        Assert.assertNull(entityBean);

        previsto = "aprile";
        query.addCriteria(Criteria.where("mese").is("aprile"));
        entityBean = service.findOneUnique(Mese.class, query);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());

        previsto = "agosto";
        query = new Query();
        query.addCriteria(Criteria.where("giorni").is(31));
        sort = Sort.by(Sort.Direction.ASC, "nome");
        query.with(sort);
        entityBean = service.findOneUnique(Mese.class, query);
        Assert.assertNull(entityBean);

        previsto = "gennaio";
        query = new Query();
        query.addCriteria(Criteria.where("giorni").is(31));
        sort = Sort.by(Sort.Direction.ASC, "ordine");
        query.with(sort);
        entityBean = service.findOneUnique(Mese.class, query);
        Assert.assertNull(entityBean);

        previsto = "febbraio";
        query = new Query();
        query.addCriteria(Criteria.where("giorni").is(28));
        entityBean = service.findOneUnique(Mese.class, query);
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());

        previsto = "febbraio";
        entityBean = service.findOneUnique(Mese.class, new Query(Criteria.where("giorni").is(28)));
        Assert.assertNotNull(entityBean);
        Assert.assertEquals(previsto, ((Mese) entityBean).getMese());
    }


    //    @Test
    //    @Order(15)
    //    @DisplayName("findEntity")
    //    void findEntity() {
    //        System.out.println("singola entity");
    //        entityBean = service.find((AEntity) null);
    //
    //        roleQuattro = roleLogic.newEntity("Zeta");
    //        roleQuattro.ordine = 88;
    //        roleQuattro.id = "mario";
    //        Assert.assertNotNull(roleQuattro);
    //        entityBean = service.find(roleQuattro);
    //        Assert.assertNull(entityBean);
    //
    //        previsto = "febbraio";
    //        query = new Query();
    //        query.addCriteria(Criteria.where("giorni").is(28));
    //        entityBean = service.findOneUnique(Mese.class, query);
    //        Assert.assertNotNull(entityBean);
    //        Assert.assertEquals(previsto, ((Mese) entityBean).getNome());
    //
    //        Mese mese = (Mese) service.find(entityBean);
    //        Assert.assertNotNull(mese);
    //        Assert.assertEquals(previsto, mese.getNome());
    //    }


    @Test
    @Order(16)
    @DisplayName("16 - getNewOrder")
    void getNewOrder() {
        System.out.println("recupera ordinamento progressivo");

        sorgenteIntero = service.count(Mese.class);
        previstoIntero = sorgenteIntero + 1;

        ottenutoIntero = meseLogic.getNewOrdine();
        Assert.assertEquals(previstoIntero, ottenutoIntero);
    }


    @Test
    @Order(17)
    @DisplayName("isEsiste")
    void isEsiste() {
        System.out.println("17 - controlla se esiste");

        ottenutoBooleano = service.isEsiste((AEntity) null);
        Assert.assertFalse(ottenutoBooleano);

        previsto = "febbraio";
        query = new Query();
        query.addCriteria(Criteria.where("giorni").is(28));
        entityBean = service.findOneUnique(Mese.class, query);
        Assert.assertNotNull(entityBean);

        ottenutoBooleano = service.isEsiste(entityBean);
        Assert.assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEsiste(Mese.class, "brumaio");
        Assert.assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isEsiste(Mese.class, "marzo");
        Assert.assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(18)
    @DisplayName("insertConKey")
    void insertConKey() {
        //        System.out.println("inserimento di una nuova entity con keyID - controlla le properties uniche");
        //        roleQuattro = roleLogic.newEntity("Zeta");
        //        roleQuattro.id = "mario";
        //        roleQuattro.ordine = 88;
        //        Assert.assertNotNull(roleQuattro);
        //        roleCinque = (Role) roleLogic.insert(roleQuattro);
        //        Assert.assertNotNull(roleCinque);
        //        System.out.println(roleCinque);
        //
        //        roleQuattro = roleLogic.newEntity("Pippo");
        //        roleQuattro.id = "francesco";
        //        roleQuattro.ordine = 88;
        //        Assert.assertNotNull(roleQuattro);
        //        roleCinque = (Role) roleLogic.insert(roleQuattro);
        //        Assert.assertNull(roleCinque);
        //        service.delete(roleQuattro);
    }


    @Test
    @Order(20)
    @DisplayName("save")
    void save() {
        //        System.out.println("modifica di una entity esistente");
        //        roleUno.ordine = 345;
        //        roleQuattro = (Role) service.save(roleUno);
        //        Assert.assertNotNull(roleQuattro);
        //
        //        roleCinque = roleLogic.newEntity("finestra");
        //        roleCinque.ordine = 87;
        //        roleQuattro = (Role) service.save(roleCinque);
        //        Assert.assertNotNull(roleQuattro);
        //
        //        roleCinque = roleLogic.newEntity("porta");
        //        roleCinque.ordine = 18;
        //        roleQuattro = (Role) service.save(roleCinque);
        //        Assert.assertNull(roleQuattro);
    }


    @Test
    @Order(99)
    @DisplayName("insert")
    void insert() {
        //        System.out.println("inserimento di una nuova entity senza keyID - controlla le properties uniche");
        //        roleQuattro = roleLogic.newEntity("Zeta");
        //        roleQuattro.ordine = 88;
        //        Assert.assertNotNull(roleQuattro);
        //        roleCinque = (Role) roleLogic.insert(roleQuattro);
        //        Assert.assertNotNull(roleCinque);
        //        System.out.println(roleCinque);
        //
        //        roleQuattro = roleLogic.newEntity("Pippo");
        //        roleQuattro.ordine = 88;
        //        Assert.assertNotNull(roleQuattro);
        //        roleCinque = (Role) roleLogic.insert(roleQuattro);
        //        Assert.assertNull(roleCinque);
        //        service.delete(Role.class, new Query(Criteria.where("code").is("Zeta")));
    }


    @Test
    @Order(173)
    @DisplayName("173 - tempiCount")
    void tempiCount() {
        long numRec = 0;
        Query query = new Query();
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("vaadflow14");
        MongoCollection collection = database.getCollection("mese");

        long inizio = System.currentTimeMillis();
        for (int k = 0; k < 1000; k++) {
            service.count(Mese.class);
        }
        long fine = System.currentTimeMillis();
        System.out.println("tempo count mongoService: " + (fine - inizio));


        inizio = System.currentTimeMillis();
        for (int k = 0; k < 1000; k++) {
            mongoOp.count(new Query(), Mese.class);
        }
        fine = System.currentTimeMillis();
        System.out.println("tempo count mongoOP: " + (fine - inizio));

        inizio = System.currentTimeMillis();
        for (int k = 0; k < 1000; k++) {
            mongoOp.count(query, Mese.class);
        }
        fine = System.currentTimeMillis();
        System.out.println("tempo count mongoOP con query: " + (fine - inizio));


        collection.countDocuments();
        inizio = System.currentTimeMillis();
        for (int k = 0; k < 1000; k++) {
            collection.count();
        }
        fine = System.currentTimeMillis();
        System.out.println("tempo count mongoClient: " + (fine - inizio));

        inizio = System.currentTimeMillis();
        for (int k = 0; k < 1000; k++) {
            numRec = service.count(Mese.class);
        }
        fine = System.currentTimeMillis();
        System.out.println("tempo count mongoService new: " + (fine - inizio) + " per " + numRec + " elementi");
    }


    @Test
    @Order(174)
    @DisplayName("174 - tempiFindAll")
    void tempiFindAll() {
        Query query = new Query();
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("vaadflow14");
        MongoCollection collection = database.getCollection("mese");

        long inizio = System.currentTimeMillis();
        for (int k = 0; k < 1000; k++) {
            service.find(Mese.class);
        }
        long fine = System.currentTimeMillis();
        System.out.println("tempo findAll mongoService: " + (fine - inizio));

        inizio = System.currentTimeMillis();
        for (int k = 0; k < 1000; k++) {
            mongoOp.find(new Query(), Mese.class);
        }
        fine = System.currentTimeMillis();
        System.out.println("tempo findAll mongoOP: " + (fine - inizio));

        inizio = System.currentTimeMillis();
        for (int k = 0; k < 1000; k++) {
            mongoOp.find(query, Mese.class);
        }
        fine = System.currentTimeMillis();
        System.out.println("tempo findAll mongoOP con query : " + (fine - inizio));

        inizio = System.currentTimeMillis();
        for (int k = 0; k < 1000; k++) {
            collection.find().first();
        }
        fine = System.currentTimeMillis();
        System.out.println("tempo findAll mongoClient: " + (fine - inizio));
    }


    @Test
    @Order(175)
    @DisplayName("174 - tempiFindAll con esclusione di property")
    void tempiFindAllExclude() {
        int cicli;
        Query query = new Query();
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("vaadflow14");

        cicli = 100;
        long inizio = System.currentTimeMillis();
        for (int k = 0; k < cicli; k++) {
            listaBean = service.findAll(Stato.class);
        }
        long fine = System.currentTimeMillis();
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() == 249);
        System.out.println(VUOTA);
        System.out.println("tempo findAll Stato completo per " + cicli + " cicli: " + (fine - inizio));

        query.fields().exclude("bandiera");
        inizio = System.currentTimeMillis();
        for (int k = 0; k < cicli; k++) {
            listaBean = service.findAll(Stato.class, query);
        }
        fine = System.currentTimeMillis();
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() == 249);
        System.out.println("tempo findAll Stato senza bandiere per " + cicli + " cicli: " + (fine - inizio));

        cicli = 10;
        inizio = System.currentTimeMillis();
        for (int k = 0; k < cicli; k++) {
            listaBean = service.findAll(Anno.class);
        }
        fine = System.currentTimeMillis();
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() == 3030);
        System.out.println("tempo findAll Anno completo per " + cicli + " cicli: " + (fine - inizio));

        query = new Query();
        query.fields().include("bisestile");
        inizio = System.currentTimeMillis();
        for (int k = 0; k < cicli; k++) {
            listaBean = service.findAll(Anno.class, query);
        }
        fine = System.currentTimeMillis();
        Assert.assertNotNull(listaBean);
        Assert.assertTrue(listaBean.size() == 3030);
        System.out.println("tempo findAll Anno con solo property 'bisestile' per " + cicli + " cicli: " + (fine - inizio));
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
        cancellazioneEntitiesProvvisorie();
    }


    private void printLista(List<AEntity> lista) {
        printLista(lista, VUOTA);
    }


    private void printLista(List<AEntity> lista, String message) {
        int cont = 0;

        if (lista != null) {
            if (text.isValid(message)) {
                System.out.println(VUOTA);
                System.out.println(message);
            }

            for (AEntity entityBean : lista) {
                System.out.print(Integer.toString(++cont) + SEP + entityBean);
                if (entityBean instanceof Mese) {
                    System.out.print(SEP + ((Mese) entityBean).ordine + SEP + ((Mese) entityBean).mese);
                }
                System.out.println();
            }
        }
    }


    /**
     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
     */
    private void creazioneInizialeEntitiesProvvisorie() {
        //        roleUno = roleLogic.newEntity("Alfa");
        //        roleUno.ordine = 17;
        //        service.insert(roleUno);
        //
        //        roleDue = roleLogic.newEntity("Beta");
        //        roleDue.ordine = 18;
        //        service.insert(roleDue);
        //
        //        roleTre = roleLogic.newEntity("Gamma");
        //        roleTre.ordine = 19;
        //        service.insert(roleTre);
    }


    /**
     * Cancellazione finale di tutti i files/directories creati in questo test <br>
     */
    private void cancellazioneEntitiesProvvisorie() {
        //        roleLogic.reset();
    }

}