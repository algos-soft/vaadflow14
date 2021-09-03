package it.algos.vaadflow14.backend.service;

import com.mongodb.client.*;
import it.algos.vaadflow14.backend.entity.*;
import org.bson.*;
import org.bson.conversions.*;
import org.springframework.data.mongodb.core.query.*;

import java.io.*;
import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 02-set-2021
 * Time: 06:49
 */
public interface AIMongoService {

    int STANDARD_MONGO_MAX_BYTES = 33554432;

    int EXPECTED_ALGOS_MAX_BYTES = 50151432;

    /**
     * Nome del database. <br>
     *
     * @return nome del database
     */
    String getDatabaseName();

    /**
     * Database. <br>
     *
     * @return database
     */
    MongoDatabase getDataBase();

    /**
     * Tutte le collezioni esistenti. <br>
     *
     * @return collezioni del database
     */
    List<String> getCollezioni();


    /**
     * Collection del database. <br>
     *
     * @param entityClazz della collezione da controllare
     *
     * @return collection if exist
     */
    MongoCollection<Document> getCollection(final Class<? extends AEntity> entityClazz);


    /**
     * Collection del database. <br>
     *
     * @param collectionName The name of the collection or view
     *
     * @return collection if exist
     */
    MongoCollection<Document> getCollection(final String collectionName);


    /**
     * Check the existence of a collection. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection exist
     */
    boolean isExistsCollection(final Class<? extends AEntity> entityClazz);

    /**
     * Check the existence of a collection. <br>
     *
     * @param collectionName corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection exist
     */
    boolean isExistsCollection(final String collectionName);


    /**
     * Check the existence (not empty) of a collection. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection has entities
     */
    boolean isValidCollection(final Class<? extends AEntity> entityClazz);


    /**
     * Check the existence (not empty) of a collection. <br>
     *
     * @param collectionName corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection has entities
     */
    boolean isValidCollection(final String collectionName);


    /**
     * Check if a collection is empty. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection is empty
     */
    boolean isEmptyCollection(final Class<? extends AEntity> entityClazz);

    /**
     * Check if a collection is empty. <br>
     *
     * @param collectionName corrispondente ad una collection sul database mongoDB
     *
     * @return true if the collection is empty
     */
    boolean isEmptyCollection(final String collectionName);

    /**
     * Conteggio di tutte le entities di una collection NON filtrate. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     *
     * @return numero totale di entities
     */
    int count(final Class<? extends AEntity> entityClazz);

    /**
     * Conteggio di tutte le entities di una collection NON filtrate. <br>
     *
     * @param collectionName The name of the collection or view to count
     *
     * @return numero totale di entities
     */
    int count(final String collectionName);


    /**
     * Conteggio di alcune entities selezionate di una collection. <br>
     *
     * @param entityClazz   corrispondente ad una collection sul database mongoDB
     * @param propertyName  per costruire la query
     * @param propertyValue (serializable) per costruire la query
     *
     * @return numero di entities selezionate
     */
    int count(final Class<? extends AEntity> entityClazz, final String propertyName, final Serializable propertyValue);


    /**
     * Conteggio di tutte le entities di una collection filtrate con un filtro. <br>
     * Se il filtro è nullo o vuoto, restituisce il totale dell'intera collection <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param filter      Optional. A filter that selects which documents to count in the collection or view.
     *
     * @return numero di entities eventualmente filtrate
     */
    int count(final Class<? extends AEntity> entityClazz, final Bson filter);


    /**
     * Conteggio di tutte le entities di una collection filtrate con un filtro. <br>
     * Se il filtro è nullo o vuoto, restituisce il totale dell'intera collection <br>
     *
     * @param collectionName The name of the collection or view to count
     * @param filter         Optional. A filter that selects which documents to count in the collection or view.
     *
     * @return numero di entities eventualmente filtrate
     */
    int count(final String collectionName, final Bson filter);


    /**
     * Cerca una singola entity di una collection con una determinata chiave. <br>
     *
     * @param entityClazz corrispondente ad una collection sul database mongoDB
     * @param keyId       chiave identificativa
     *
     * @return the founded entity
     */
    AEntity findById(final Class<? extends AEntity> entityClazz, final String keyId);


    /**
     * Retrieves an entity by its keyProperty.
     *
     * @param keyPropertyValue must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    AEntity findByKey(final Class<? extends AEntity> entityClazz, final Serializable keyPropertyValue);


    /**
     * Retrieves an entity by a keyProperty.
     * Cerca una singola entity di una collection con una query. <br>
     * Restituisce un valore valido SOLO se ne esiste una sola <br>
     *
     * @param entityClazz   corrispondente ad una collection sul database mongoDB
     * @param propertyName  per costruire la query
     * @param propertyValue must not be {@literal null}
     *
     * @return the founded entity unique or {@literal null} if none found
     *
     * @see(https://docs.mongodb.com/realm/mongodb/actions/collection.findOne//)
     */
    AEntity findByKey(final Class<? extends AEntity> entityClazz, final String propertyName, final Serializable propertyValue);


    /**
     * Delete a single entity. <br>
     *
     *
     * @return true se la entity esisteva ed è stata cancellata
     */
    boolean delete(Class<? extends AEntity> entityClazz, Query query);

}// end of interface

