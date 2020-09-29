package it.algos.vaadflow14.backend.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.wrapper.AFiltro;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mar, 05-mag-2020
 * Time: 17:36
 * <p>
 * Classe di servizio per l'accesso al database <br>
 * Prioritario l'utilizzo di MongoOperations, inserito automaticamente da SpringBoot <br>
 * Per query più specifiche si può usare MongoClient <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private AMongoService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AMongoService<capture> extends AAbstractService {

    public final static int STANDARD_MONGO_MAX_BYTES = 33554432;

    public final static int EXPECTED_ALGOS_MAX_BYTES = 50151432;

    /**
     * Versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * Inietta da Spring
     */
    public MongoOperations mongoOp;


    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    private MongoClient mongoClient;

    private MongoDatabase database;


    /**
     * Costruttore @Autowired. <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * L' @Autowired (esplicito o implicito) funziona SOLO per UN costruttore <br>
     * Se ci sono DUE o più costruttori, va in errore <br>
     * Se ci sono DUE costruttori, di cui uno senza parametri, inietta quello senza parametri <br>
     */
    public AMongoService(MongoOperations mongoOp) {
        this.mongoOp = mongoOp;
    }


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    protected void postConstruct() {
        fixProperties();
    }


    /**
     * Creazione iniziale di eventuali properties indispensabili per l'istanza <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Metodo private che NON può essere sovrascritto <br>
     */
    private void fixProperties() {
        mongoClient = new MongoClient("localhost");

        if (text.isValid(databaseName)) {
            database = mongoClient.getDatabase(databaseName);
        }
    }


    /**
     * Collection del database. <br>
     *
     * @param entityClazz della collezione da controllare
     *
     * @return collection if exist
     */
    private MongoCollection<Document> getCollection(Class<? extends AEntity> entityClazz) {
        return getCollection(annotation.getCollectionName(entityClazz));
    }


    /**
     * Collection del database. <br>
     *
     * @param collectionName The name of the collection or view
     *
     * @return collection if exist
     */
    private MongoCollection<Document> getCollection(String collectionName) {
        if (text.isValid(collectionName)) {
            return database != null ? database.getCollection(collectionName) : null;
        } else {
            return null;
        }
    }


    /**
     * Check the existence of a collection. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection has entities
     */
    public boolean isValid(Class<? extends AEntity> entityClazz) {
        return count(entityClazz) > 0;
    }


    /**
     * Check if a collection is empty. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection is empty
     */
    public boolean isEmpty(Class<? extends AEntity> entityClazz) {
        return count(entityClazz) < 1;
    }


    /**
     * Conteggio di tutte le entities di una collection NON filtrate. <br>
     *
     * @param collectionName The name of the collection or view to count
     *
     * @return numero totale di entities
     */
    public int count(String collectionName) {
        return count(collectionName, (Query) null);
    }


    /**
     * Conteggio di tutte le entities di una collection NON filtrate. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return numero totale di entities
     */
    public int count(Class<? extends AEntity> entityClazz) {
        return count(entityClazz, (Query) null);
    }


    /**
     * Conteggio di tutte le entities di una collection filtrate con una query. <br>
     * Se la query è nulla o vuota, restituisce il totale dell'intera collection <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param query       Optional. A query that selects which documents to count in the collection or view.
     *
     * @return numero di entities eventualmente filtrate
     */
    public int count(Class<? extends AEntity> entityClazz, Query query) {
        return count(annotation.getCollectionName(entityClazz), query);
    }


    /**
     * Conteggio di tutte le entities di una collection filtrate con una query. <br>
     * Se la query è nulla o vuota, restituisce il totale dell'intera collection <br>
     * <p>
     * Counts the number of documents in a collection or a view. <br>
     * Returns a document that contains this count and as well as the command status. <br>
     * Avoid using the count and its wrapper methods without a query predicate since without the query predicate, <br>
     * these operations return results based on the collection’s metadata, which may result in an approximate count. <br>
     *
     * @param collectionName The name of the collection or view to count
     * @param query          Optional. A query that selects which documents to count in the collection or view.
     *
     * @return numero di entities eventualmente filtrate
     *
     * @see(https://docs.mongodb.com/manual/reference/command/count/)
     */
    public int count(String collectionName, Query query) {
        if (text.isEmpty(collectionName)) {
            return 0;
        }

        if (query == null) {
            query = new Query();
        }

        return (int) mongoOp.count(query, collectionName);
    }


    /**
     * Cerca tutte le entities di una collection ordinate. <br>
     * Gli ordinamenti dei vari filtri vengono concatenati nell'ordine di costruzione <br>
     *
     * @param entityClazz    corrispondente ad una collection sul database mongoDB
     * @param sortPrevalente (facoltativa) indipendentemente dai filtri
     *
     * @return entity
     */
    public List<AEntity> findAll(Class<? extends AEntity> entityClazz, Sort sortPrevalente) {
        return findAll(entityClazz, (List<AFiltro>) null, sortPrevalente);
    }


    /**
     * Cerca tutte le entities di una collection filtrate. <br>
     * Gli ordinamenti dei vari filtri vengono concatenati nell'ordine di costruzione <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param listaFiltri per costruire la query
     *
     * @return entity
     */
    public List<AEntity> findAll(Class<? extends AEntity> entityClazz, List<AFiltro> listaFiltri) {
        return findAll(entityClazz, listaFiltri, (Sort) null);
    }


    /**
     * Cerca tutte le entities di una collection filtrate e ordinate. <br>
     * Gli ordinamenti dei vari filtri vengono concatenati nell' ordine di costruzione <br>
     * Se esiste sortPrevalente, sostituisce i sort dei vari filtri <br>
     *
     * @param entityClazz    corrispondente ad una collection sul database mongoDB
     * @param listaFiltri    per costruire la query
     * @param sortPrevalente (facoltativa) indipendentemente dai filtri
     *
     * @return entity
     */
    public List<AEntity> findAll(Class<? extends AEntity> entityClazz, List<AFiltro> listaFiltri, Sort sortPrevalente) {
        Query query = new Query();
        CriteriaDefinition criteria;
        Sort sort;
        if (entityClazz == null) {
            return null;
        }

        if (listaFiltri != null) {
            if (listaFiltri.size() == 1) {
                criteria = listaFiltri.get(0).getCriteria();
                if (criteria != null) {
                    query.addCriteria(criteria);
                }
                sort = listaFiltri.get(0).getSort();
                if (sort != null) {
                    query.with(sort);
                } else {
                    if (sortPrevalente != null) {
                        query.with(sortPrevalente);
                    }
                }
            } else {
                for (AFiltro filtro : listaFiltri) {
                    criteria = filtro.getCriteria();
                    if (criteria != null) {
                        query.addCriteria(criteria);
                    }
                    sort = filtro.getSort();
                    if (sort != null) {
                        query.with(sort);
                    }
                }
                if (!query.isSorted() && sortPrevalente != null) {
                    query.with(sortPrevalente);
                }
            }
        } else {
            if (sortPrevalente != null) {
                query.with(sortPrevalente);
            }
        }

        return findAll(entityClazz, query);
    }


    /**
     * Cerca tutte le entities di una collection. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return lista di entityBeans
     *
     * @see(https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find/)
     */
    public List<AEntity> find(Class<? extends AEntity> entityClazz) {
        return findAll(entityClazz);
    }


    /**
     * Cerca tutte le entities di una collection. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return lista di entityBeans
     *
     * @see(https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find/)
     */
    public List<AEntity> findAll(Class<? extends AEntity> entityClazz) {
        return findAll(entityClazz, (Query) null, VUOTA);
    }


    /**
     * Cerca tutte le entities di una collection filtrate con una query. <br>
     * Selects documents in a collection or view and returns a list of the selected documents. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param query       Optional. Specifies selection filter using query operators.
     *                    To return all documents in a collection, omit this parameter or pass an empty document ({})
     *
     * @return lista di entityBeans
     *
     * @see(https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find/)
     */
    public List<AEntity> findAll(Class<? extends AEntity> entityClazz, Query query) {
        return findAll(entityClazz, query, VUOTA);
    }


    /**
     * Cerca tutte le entities di una collection filtrate con una property. <br>
     * Selects documents in a collection or view and returns a list of the selected documents. <br>
     *
     * @param entityClazz   corrispondente ad una collection sul database mongoDB
     * @param propertyName  per costruire la query
     * @param propertyValue (serializable) per costruire la query
     *
     * @return lista di entityBeans
     *
     * @see(https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find/)
     */
    public List<AEntity> findAll(Class<? extends AEntity> entityClazz, String propertyName, Serializable propertyValue) {
        if (entityClazz == null) {
            return null;
        }
        if (text.isEmpty(propertyName) || propertyValue == null) {
            return findAll(entityClazz);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where(propertyName).is(propertyValue));

        return findAll(entityClazz, query);
    }


    /**
     * Cerca tutte le entities di una collection filtrate con una query. <br>
     * <p>
     * Selects documents in a collection or view and returns a list of the selected documents. <br>
     * The projection parameter determines which fields are returned in the matching documents. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param query       Optional. Specifies selection filter using query operators.
     *                    To return all documents in a collection, omit this parameter or pass an empty document ({})
     * @param projection  Optional. Specifies the fields to return in the documents that match the query filter.
     *                    To return all fields in the matching documents, omit this parameter.
     *
     * @return lista di entityBeans
     *
     * @see(https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find/)
     */
    public List<AEntity> findAll(Class<? extends AEntity> entityClazz, Query query, String projection) {
        if (entityClazz == null) {
            return null;
        }

        if (query == null) {
            return (List<AEntity>) mongoOp.findAll(entityClazz);
        } else {
            if (text.isEmpty(projection)) {
                return (List<AEntity>) mongoOp.find(query, entityClazz);
            } else {
                return (List<AEntity>) mongoOp.find(query, entityClazz, projection);
            }
        }
    }


    /**
     * Cerca una singola entity di una collection con una determinata chiave. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param keyId       chiave identificativa
     *
     * @return the founded entity
     */
    public AEntity find(Class<? extends AEntity> entityClazz, String keyId) {
        return findById(entityClazz, keyId);
    }


    /**
     * Cerca una singola entity di una collection con una determinata chiave. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param keyId       chiave identificativa
     *
     * @return the founded entity
     */
    public AEntity findById(Class<? extends AEntity> entityClazz, String keyId) {
        AEntity entity = null;
        if (entityClazz == null) {
            return null;
        }

        if (text.isValid(keyId)) {
            entity = mongoOp.findById(keyId, entityClazz);
        }

        return entity;
    }


    /**
     * Retrieves an entity by its keyProperty.
     *
     * @param entityClazz      corrispondente ad una collection sul database mongoDB
     * @param keyPropertyValue must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    public AEntity findByKey(Class<? extends AEntity> entityClazz, String keyPropertyValue) {
        String keyPropertyName = annotation.getKeyPropertyName(entityClazz);
        if (text.isValid(keyPropertyName)) {
            return findOneUnique(entityClazz, keyPropertyName, keyPropertyValue);
        } else {
            return findById(entityClazz, keyPropertyValue);
        }
    }


    /**
     * Cerca una singola entity di una collection con un determinato valore di una property. <br>
     * Costruisce una query semplice, di uguaglianza del valore per la property indicata <br>
     * Per altre query, costruirle direttamente <br>
     * <p>
     * Return a single document from a collection or view. <br>
     * If multiple documents satisfy the query, this method returns the first document <br>
     * according to the query’s sort order or natural order <br>
     *
     * @param entityClazz   corrispondente ad una collection sul database mongoDB
     * @param propertyName  per costruire la query
     * @param propertyValue (serializable) per costruire la query
     *
     * @return the founded entity
     *
     * @see(https://docs.mongodb.com/realm/mongodb/actions/collection.findOne//)
     */
    public AEntity findOneFirst(Class<? extends AEntity> entityClazz, String propertyName, Serializable propertyValue) {
        if (entityClazz == null) {
            return null;
        }
        if (text.isEmpty(propertyName) || propertyValue == null) {
            return null;
        }

        Query query = new Query();
        query.addCriteria(Criteria.where(propertyName).is(propertyValue));
        return findOneFirst(entityClazz, query);
    }


    /**
     * Return the first document in the collection. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return the founded entity
     *
     * @see(https://docs.mongodb.com/realm/mongodb/actions/collection.findOne//)
     */
    public AEntity findOneFirst(Class<? extends AEntity> entityClazz) {
        return findOneFirst(entityClazz, new Query());
    }


    /**
     * Cerca una singola entity di una collection con una query. <br>
     * Return a single document from a collection or view. <br>
     * If multiple documents satisfy the query, this method returns the first document <br>
     * according to the query’s sort order or natural order <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param query       Optional. A standard MongoDB query document that specifies which documents to find.
     *                    Specify an empty query filter ({}) or omit this parameter
     *                    to return the first document in the collection.
     *
     * @return the founded entity
     *
     * @see(https://docs.mongodb.com/realm/mongodb/actions/collection.findOne//)
     */
    public AEntity findOneFirst(Class<? extends AEntity> entityClazz, Query query) {
        if (entityClazz == null || query == null) {
            return null;
        }

        return mongoOp.findOne(query, entityClazz);
    }


    /**
     * Cerca una singola entity di una collection con una query. <br>
     * Restituisce un valore valido SOLO se ne esiste una sola <br>
     *
     * @param entityClazz   corrispondente ad una collection sul database mongoDB
     * @param propertyName  per costruire la query
     * @param propertyValue (serializable) per costruire la query
     *
     * @return the founded entity unique
     *
     * @see(https://docs.mongodb.com/realm/mongodb/actions/collection.findOne//)
     */
    public AEntity findOneUnique(Class<? extends AEntity> entityClazz, String propertyName, Serializable propertyValue) {
        if (entityClazz == null) {
            return null;
        }
        if (text.isEmpty(propertyName) || propertyValue == null) {
            return null;
        }

        Query query = new Query();
        query.addCriteria(Criteria.where(propertyName).is(propertyValue));
        return findOneUnique(entityClazz, query);
    }


    /**
     * Cerca una singola entity di una collection con una query. <br>
     * Restituisce un valore valido SOLO se ne esiste una sola <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param query       A standard MongoDB query document that specifies which documents to find.
     *
     * @return the founded entity unique
     *
     * @see(https://docs.mongodb.com/realm/mongodb/actions/collection.findOne//)
     */
    public AEntity findOneUnique(Class<? extends AEntity> entityClazz, Query query) {
        if (entityClazz == null || query == null) {
            return null;
        }

        if (mongoOp.count(query, entityClazz) != 1) {
            return null;
        }

        return mongoOp.findOne(query, entityClazz);
    }


    /**
     * Cerca una singola entity di una collection con una query. <br>
     * Restituisce un valore valido SOLO se ne esiste una sola <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param query       A standard MongoDB query document that specifies which documents to find.
     *
     * @return the founded entity unique
     *
     * @see(https://docs.mongodb.com/realm/mongodb/actions/collection.findOne//)
     */
    public AEntity findByUniqueKey(Class<? extends AEntity> entityClazz, Query query) {
        if (entityClazz == null || query == null) {
            return null;
        }

        if (mongoOp.count(query, entityClazz) != 1) {
            return null;
        }

        return mongoOp.findOne(query, entityClazz);
    }


    /**
     * Find lista (interna). <br>
     *
     * @param entityClazz   corrispondente ad una collection sul database mongoDB
     * @param propertyName  da controllare
     * @param propertyValue da controllare
     * @param sort          per individuare il primo
     *
     * @return the founded entity
     */
    private List<AEntity> findLista(Class<? extends AEntity> entityClazz, String propertyName, Serializable propertyValue, Sort sort) {
        List<AEntity> lista = null;
        Query query = new Query();
        query.addCriteria(Criteria.where(propertyName).is(propertyValue));

        if (sort != null) {
            query.with(sort);
        }

        return findAll(entityClazz, query);
    }


    /**
     * Find single entity. <br>
     * Cerca sul database (mongo) la versione registrata di una entity in memoria <br>
     *
     * @param entityBean to be found
     *
     * @return the founded entity
     */
    public AEntity find(AEntity entityBean) {
        return entityBean != null ? findById(entityBean.getClass(), entityBean.getId()) : null;
    }


    /**
     * Check the existence of a single entity. <br>
     * Cerca sul database (mongo) la versione registrata di una entity in memoria <br>
     *
     * @param entityBean to be found
     *
     * @return true if exist
     */
    public boolean isEsiste(AEntity entityBean) {
        return find(entityBean) != null;
    }


    /**
     * Check the existence of a single entity. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param keyId       chiave identificativa
     *
     * @return true if exist
     */
    public boolean isEsiste(Class<? extends AEntity> entityClazz, String keyId) {
        return findById(entityClazz, keyId) != null;
    }


    /**
     * Registra una nuova entity. <br>
     * Controlla che sia rispettata l'unicità del campo keyID e di tutte le property definite come uniche <br>
     * <p>
     * Inserts a document or documents into a collection. <br>
     * If the document does not specify an _id field, then MongoDB will add the _id field <br>
     * and assign a unique ObjectId for the document before inserting <br>
     * If the document contains an _id field, the _id value must be unique within the collection <br>
     * to avoid duplicate key error. <br>
     * <p>
     * Per registrare la entity, occorre che oltre al field _id
     * sia rispettata l'unicità degli altri (eventuali) fields unici <br>
     *
     * @param newEntityBean da inserire nella collezione
     *
     * @return la entity appena registrata
     *
     * @see(https://docs.mongodb.com/manual/reference/method/db.collection.insert/)
     */
    public AEntity insert(AEntity newEntityBean) {
        AEntity entityBean = null;

        try {
            entityBean = mongoOp.insert(newEntityBean);
        } catch (Exception unErrore) {
            logger.error(unErrore, this.getClass(), "insert");
        }

        return entityBean;
    }


    /**
     * Recupera dal DB il valore massimo pre-esistente della property <br>
     * Incrementa di uno il risultato <br>
     *
     * @param entityClazz  corrispondente ad una collection sul database mongoDB
     * @param propertyName dell'ordinamento
     */
    public int getNewOrder(Class<? extends AEntity> entityClazz, String propertyName) {
        int ordine = 0;
        AEntity entityBean;
        Object value;
        Sort sort = Sort.by(Sort.Direction.DESC, propertyName);
        Field field = reflection.getField(entityClazz, propertyName);
        Query query = new Query().with(sort).limit(1);

        if (isEmpty(entityClazz)) {
            return 1;
        }

        try {
            entityBean = mongoOp.find(query, entityClazz).get(0);
            value = field.get(entityBean);
            ordine = (Integer) value;
        } catch (Exception unErrore) {
            logger.error(unErrore, this.getClass(), "getNewOrder");
        }

        return ordine + 1;
    }


    /**
     * Saves a given entity. <br>
     * Use the returned instance for further operations <br>
     * as the save operation might have changed the entity instance completely <br>
     * If the document does not contain an _id field, then the save() method calls the insert() method.
     * During the operation, the mongo shell will create an ObjectId and assign it to the _id field.
     * If the document contains an _id field, then the save() method is equivalent to an update
     * with the upsert option set to true and the query predicate on the _id field.
     *
     * @param entityBean to be saved
     *
     * @return the saved entity
     *
     * @see(https://docs.mongodb.com/manual/reference/method/db.collection.save/)
     */
    public AEntity save(AEntity entityBean) {
        try {
            return mongoOp.save(entityBean);
        } catch (Exception unErrore) {
            logger.warn(unErrore, this.getClass(), "save");
            return null;
        }
    }


    /**
     * Delete a single entity. <br>
     *
     * @param entityBean da cancellare
     *
     * @return true se la entity esisteva ed è stata cancellata
     */
    public boolean delete(Class<? extends AEntity> entityClazz, Query query) {
        boolean status = false;
        DeleteResult result = null;

        result = mongoOp.remove(query, entityClazz);
        if (result != null) {
            status = result.getDeletedCount() == 1;
        }

        return status;
    }


    /**
     * Delete a single entity. <br>
     *
     * @param entityBean da cancellare
     *
     * @return true se la entity esisteva ed è stata cancellata
     */
    public boolean delete(AEntity entityBean) {
        boolean status = false;
        DeleteResult result = null;

        if (entityBean != null && entityBean.id != null) {
            try {
                result = mongoOp.remove(entityBean);
                if (result != null) {
                    status = result.getDeletedCount() == 1;
                }
            } catch (Exception unErrore) {
                logger.error(unErrore, this.getClass(), "delete");
            }
        } else {
            logger.warn("Tentativo di cancellare una entity nulla o con id nullo", this.getClass(), "delete");
        }

        return status;
    }


    /**
     * Delete a collection.
     *
     * @param collectionName della collezione
     */
    public boolean drop(String collectionName) {
        this.mongoOp.dropCollection(collectionName);
        return mongo.count(collectionName) == 0;
    }


    /**
     * Delete a collection.
     *
     * @param entityClazz della collezione
     *
     * @return true se la collection è stata cancellata
     */
    public boolean drop(Class<? extends AEntity> entityClazz) {
        return drop(annotation.getCollectionName(entityClazz));
    }

}