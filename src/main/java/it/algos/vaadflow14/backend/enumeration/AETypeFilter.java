package it.algos.vaadflow14.backend.enumeration;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import org.springframework.data.mongodb.core.query.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 28-apr-2021
 * Time: 21:06
 */
public enum AETypeFilter {
    uguale("$eq", "Matches values that are equal to a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).is(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return VUOTA;
        }
    },
    maggiore("$gt", "Matches values that are greater than a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).gt(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return VUOTA;
        }
    },
    maggioreUguale("$gte", "Matches values that are greater than or equal to a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).gte(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return VUOTA;
        }
    },
    minore("$lt", "Matches values that are less than a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).lt(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return VUOTA;
        }
    },
    minoreUguale("$lte", "Matches values that are less than or equal to a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).lte(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return VUOTA;
        }
    },
    regex("$regex", "Selects documents where values match a specified regular expression.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).regex(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return VUOTA;
        }
    },
    diverso("$ne", "Matches all values that are not equal to a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).ne(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return VUOTA;
        }
    },
    lista("$in", "Matches any of the values specified in an array.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).in(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return VUOTA;
        }
    },
    contiene("$regex", "Seleziona i documenti che contengono il valore indicato.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).in(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("col field %s che contiene il testo '%s'", fieldName, value);
        }
    },
    inizia("$regex", "Seleziona i documenti che iniziano col valore indicato.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).in(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("col field %s che inizia col testo '%s'", fieldName, value);
        }
    },
    contieneSearch("$regex", "Seleziona i documenti che contengono il valore indicato.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).in(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("col field %s che contiene il testo '%s'", fieldName, value);
        }
    },
    iniziaSearch("$regex", "Seleziona i documenti che iniziano col valore indicato.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).in(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("col field %s che inizia col testo '%s'", fieldName, value);
        }
    },
    link("$eq", "Seleziona i documenti che hanno un link (DBRef) alla collezione indicata.") {
        @Override
        public Criteria getCriteria(final String fieldName, final String value) {
            return Criteria.where(fieldName).is(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("col DBRef field %s linkato alla entity '%s'", fieldName, value);
        }
    },
    ;

    private String tag;

    private String descrizione;


    AETypeFilter(String tag, String descrizione) {
        this.tag = tag;
        this.descrizione = descrizione;
    }

    public Criteria getCriteria(final String fieldName, final String value) {
        return null;
    }

    public String getOperazione(final String fieldName, final String value) {
        return null;
    }
}
