package it.algos.simple.backend.packages;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PiService {

    @Autowired
    private AIMongoService mongo;


    public DataProvider createDataProvider(Class<? extends AEntity> entityClazz) {

        DataProvider<Anno, Void> dataProvider = DataProvider.fromCallbacks(

                // First callback fetches items based on a query
                query -> {
                    return ((MongoService) mongo).findSet(entityClazz, query.getOffset(), query.getLimit()).stream();//@todo da controllare
                },

                // Second callback fetches the total number of items currently in the Grid.
                // The grid can then use it to properly adjust the scrollbars.
                query -> {
                    try {
                        return mongo.count(Anno.class);
                    } catch (AlgosException unErrore) {
                        return 0;
                    }
                }
        );
        return dataProvider;
    }

//    public DataProvider createDataProvider() {
//
//        DataProvider<Anno, Void> dataProvider =
//                DataProvider.fromCallbacks(
//
//                        // First callback fetches items based on a query
//                        query -> {
//
//                            // The index of the first item to load
//                            int offset = query.getOffset();
//
//                            // The number of items to load
//                            int limit = query.getLimit();
//
//                            List<Anno> anni = getAnni(offset, limit);
//
//                            return anni.stream();
//                        },
//
//                        // Second callback fetches the total number of items currently in the Grid.
//                        // The grid can then use it to properly adjust the scrollbars.
//                        query -> countAnni());
//
//        return dataProvider;
//    }



//    private List<Anno> getAnni(int offset, int limit){
//
//        Collection<Document> documents = mongo.mongoOp.getCollection("anno").find().skip(offset).limit(limit).into(new ArrayList());
//
//        List<Anno> anni = new ArrayList();
//        Anno anno;
//        for (Document doc : documents) {
//            anno = new Anno();
//            anno.setId(doc.getString("_id"));
//            anno.setOrdine(doc.getInteger("ordine"));
//            anno.setAnno(doc.getString("anno"));
//            anno.setBisestile(doc.getBoolean("bisestile"));
//            anni.add(anno);
//        }
//
//        return anni;
//
//    }

//    private int countAnni() {
//        Query query = new Query();
//        long count = mongo.mongoOp.count(query, Anno.class);
//        return (int)count;
//    }



}
