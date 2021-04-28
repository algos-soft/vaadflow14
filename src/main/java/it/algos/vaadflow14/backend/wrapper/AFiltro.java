package it.algos.vaadflow14.backend.wrapper;

import com.mongodb.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;

import java.util.regex.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 19-dic-2019
 * Time: 11:01
 */
public class AFiltro {

    private Criteria criteria;

    private BasicDBObject query;

    private Sort sort;

    private String tag;

    public AFiltro() {
    }

    public AFiltro(Criteria criteria) {
        this.criteria = criteria;
        this.sort = null;
    }


    public AFiltro(Criteria criteria, Sort sort) {
        this.criteria = criteria;
        this.sort = sort;
    }


    public AFiltro(Sort sort) {
        this.sort = sort;
    }

    public static AFiltro start(String searchFieldName, String searchFieldValue) {
        AFiltro filtro = new AFiltro();
        //        Document regexQuery = new Document();
        //        regexQuery.append("$regex", "^" + Pattern.quote(searchFieldValue) + ".*");
        //        BasicDBObject query = new BasicDBObject(searchFieldName, regexQuery);
        //        filtro.tag = "search" + searchFieldName;
        //        filtro.query = query;

        String questionPattern = "^" + Pattern.quote(searchFieldValue) + ".*";
        Criteria criteria = Criteria.where(searchFieldName).regex(questionPattern, "i");
        filtro.criteria = criteria;

        return filtro;
    }

    public static AFiltro contains(String searchFieldName, String searchFieldValue) {
        AFiltro filtro = new AFiltro();

//        Document regexQuery = new Document();
//        regexQuery.append("$regex", ".*" + Pattern.quote(searchFieldValue) + ".*");
//        BasicDBObject query = new BasicDBObject(searchFieldName, regexQuery);
//        filtro.tag = "contains" + searchFieldName;
//        filtro.query = query;

        String questionPattern = ".*" + Pattern.quote(searchFieldValue) + ".*";
        Criteria criteria = Criteria.where(searchFieldName).regex(questionPattern, "i");
        filtro.criteria = criteria;

        return filtro;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public Sort getSort() {
        return sort;
    }

    public BasicDBObject getQuery() {
        return query;
    }

    public String getTag() {
        return tag;
    }

}
