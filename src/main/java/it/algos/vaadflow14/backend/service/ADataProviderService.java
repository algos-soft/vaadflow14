package it.algos.vaadflow14.backend.service;

import com.vaadin.flow.data.provider.DataProvider;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 04-ott-2020
 * Time: 16:33
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(ADataProviderService.class); <br>
 * 3) @Autowired public ADataProviderService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ADataProviderService extends AAbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    @Autowired
    private AMongoService mongo;


    public DataProvider<AEntity,Void> creaDataProvider(Class entityClazz) {

        DataProvider dataProvider = DataProvider.fromCallbacks(

                // First callback fetches items based on a query
                query -> {
                    // The index of the first item to load
                    int offset = query.getOffset();

                    // The number of items to load
                    int limit = query.getLimit();
//                    limit = 50;//@todo Funzionalità ancora da implementare

                    return mongo.findSet(entityClazz, offset, limit).stream();
                },

                // Second callback fetches the total number of items currently in the Grid.
                // The grid can then use it to properly adjust the scrollbars.
                query -> mongo.count(entityClazz));

        return dataProvider;
    }


    public DataProvider mese() {

        DataProvider<Mese, Void> dataProvider = DataProvider.fromCallbacks(

                // First callback fetches items based on a query
                query -> {

                    // The index of the first item to load
                    int offset = query.getOffset();

                    // The number of items to load
                    int limit = query.getLimit();

                    List<Mese> items = getItemsMese(offset, limit);

                    return items.stream();
                },

                // Second callback fetches the total number of items currently in the Grid.
                // The grid can then use it to properly adjust the scrollbars.
                //                query -> mongo.count(T);
                query -> mongo.count(Mese.class));

        return dataProvider;
    }


    private List<Mese> getItemsMese(int offset, int limit) {

        Collection<Document> documents = mongo.mongoOp.getCollection("mese").find().skip(offset).limit(limit).into(new ArrayList());

        List<Mese> mesi = new ArrayList();
        Mese mese;
        for (Document doc : documents) {
            mese = new Mese();
            mese.setId(doc.getString("_id"));
            mese.setOrdine(doc.getInteger("ordine"));
            mese.setMese(doc.getString("mese"));
            mese.setGiorni(doc.getInteger("giorni"));
            mese.setGiorniBisestile(doc.getInteger("giorniBisestile"));
            mese.setSigla(doc.getString("sigla"));
            mesi.add(mese);
        }

        return mesi;

    }


    public DataProvider anno() {

        DataProvider<Anno, Void> dataProvider = DataProvider.fromCallbacks(

                // First callback fetches items based on a query
                query -> {

                    // The index of the first item to load
                    int offset = query.getOffset();

                    // The number of items to load
                    int limit = query.getLimit();
                    List<Anno> items = getItemsAnno(offset, limit);

                    return items.stream();
                },

                // Second callback fetches the total number of items currently in the Grid.
                // The grid can then use it to properly adjust the scrollbars.
                query -> mongo.count(Anno.class));

        return dataProvider;
    }


    private List<Anno> getItemsAnno(int offset, int limit) {
        long inizio = System.currentTimeMillis();

        Collection<Document> documents = mongo.mongoOp.getCollection("anno").find().skip(offset).limit(limit).into(new ArrayList());

        long intermedio = System.currentTimeMillis();
        List<Anno> anni = new ArrayList();
        Anno anno;
        for (Document doc : documents) {
            anno = new Anno();
            anno.setId(doc.getString("_id"));
            anno.setOrdine(doc.getInteger("ordine"));
            anno.setAnno(doc.getString("anno"));
            anno.setBisestile(doc.getBoolean("bisestile"));
            anni.add(anno);
        }
        long fine = System.currentTimeMillis();

        System.out.println("mongo: " + (intermedio - inizio));
        System.out.println("java: " + (fine - intermedio));
        return anni;

    }

    //    private int countAnni() {
    //        Query query = new Query();
    //        long count = mongo.mongoOp.count(query, Anno.class);
    //        int num = mongo.count( Anno.class);
    //        return mongo.count( Anno.class);;

}