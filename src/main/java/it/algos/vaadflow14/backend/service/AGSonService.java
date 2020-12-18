package it.algos.vaadflow14.backend.service;

import com.google.gson.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEMese;
import org.bson.Document;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 23-ott-2020
 * Time: 17:58
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AGSonService.class); <br>
 * 3) @Autowired public AGSonService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AGSonService extends AAbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Controlla se esistono coppie di graffe interne <br>
     * Serve per i parametri di type @DBRef <br>
     *
     * @param doc 'documento' mongo della collezione
     *
     * @return true se esistono; false se non esistono
     */
    public boolean isDBRef(final Document doc) {
        return countGraffe(fixDoc(doc)) > 0;
    }


    /**
     * Conta quante coppie di graffe interne esistono <br>
     * Serve per i parametri di type @DBRef <br>
     *
     * @param testoIn da elaborare
     *
     * @return numero di coppie interne; 0 se mancano; -1 se coppie non pari
     */
    public int countGraffe(final String testoIn) {
        String testo = testoIn.trim();
        int error = -1;
        int numIni;
        int numEnd;

        if (text.isEmpty(testoIn)) {
            return error;
        }

        testo = text.setNoGraffe(testo);
        numIni = StringUtils.countOccurrencesOf(testo, GRAFFA_INI);
        numEnd = StringUtils.countOccurrencesOf(testo, GRAFFA_END);

        return numIni == numEnd ? numIni : error;
    }


    /**
     * Estrae il contenuto della PRIMA coppia di graffe <br>
     * Comprensivo del nome del parametro che PRECEDE le graffe <br>
     *
     * @param testoIn da elaborare
     *
     * @return contenuto della graffa COMPRESI gli estremi ed il nome del paragrafo
     */
    public String estraeGraffa(final String testoIn) {
        String testo;
        int ini;
        int end;

        if (text.isEmpty(testoIn)) {
            return null;
        }

        testo = testoIn.trim();
        if (countGraffe(testo) <= 0) {
            return null;
        }

        testo = text.setNoGraffe(testo);
        ini = testo.indexOf(GRAFFA_INI);
        end = testo.indexOf(GRAFFA_END, ini) + 1;
        ini = testo.lastIndexOf(VIRGOLA, ini) + 1;
        testo = testo.substring(ini, end);
        testo = text.setNoDoppiApici(testo);

        return testo.trim();
    }


    /**
     * Estrae i contenuti di (eventuali) coppie di graffe interne <br>
     * Se non ci sono graffe interne, restituisce un array di un solo elemento <br>
     * Se ci sono graffe interne, nel primo elemento dell'array il testoIn completo MENO i contenuti interni <br>
     *
     * @param testoIn da elaborare
     *
     * @return lista di sub-contenuti, null se non ci sono graffe interne
     */
    public List<String> estraeGraffe(final String testoIn) {
        List<String> lista;
        String testo;
        String contenuto;

        if (text.isEmpty(testoIn)) {
            return null;
        }

        testo = testoIn.trim();
        if (countGraffe(testo) <= 0) {
            return null;
        }

        lista = new ArrayList<>();
        testo = text.setNoGraffe(testo);
        testo = text.setNoDoppiApici(testo);
        while (countGraffe(testo) > 0) {
            contenuto = estraeGraffa(testo);
            testo = testo.replace("\"" + contenuto + VIRGOLA, VUOTA);

            if (lista.size() == 0) {
                lista.add(testo);
                lista.add(contenuto);
            }
            else {
                lista.remove(0);
                lista.add(0, testo);
                lista.add(contenuto);
            }
        }

        return lista;
    }


    /**
     * Elimina gli underscore del tag _id <br>
     * Elimina gli underscore del tag _class <br>
     *
     * @param testoIn ingresso
     *
     * @return stringa elaborata
     */
    public String fixStringa(final String testoIn) {
        String testoOut = VUOTA;

        if (text.isValid(testoIn)) {
            testoOut = testoIn;
            testoOut = testoOut.replace("_id", "id");
            testoOut = testoOut.replace("_class", "class");
        }

        return testoOut;
    }


    /**
     * Semplice serializzazione dell' oggetto <br>
     * Recupera dal documento la stringa <br>
     * Elimina gli underscore del tag _id <br>
     * Elimina gli underscore del tag _class <br>
     *
     * @param doc 'documento' mongo della collezione
     *
     * @return stringa in formato JSon
     */
    public String fixDoc(final Object doc) {
        String stringaJSON = VUOTA;
        Gson gSon;

        if (doc != null) {
            gSon = new Gson();
            stringaJSON = gSon.toJson(doc);
            stringaJSON = fixStringa(stringaJSON);
        }

        return stringaJSON;
    }


    /**
     * Mappa dell' oggetto <br>
     * Può essere un 'documento' mongo oppure una entity <br>
     *
     * @param doc 'documento' mongo della collezione
     *
     * @return mappa chiave-valore
     */
    @Deprecated
    public LinkedHashMap toMap(Object doc) {
        LinkedHashMap mappa = null;
        String stringaJSON = VUOTA;
        String stringaJSON2 = VUOTA;
        String stringaJSON3 = VUOTA;
        AEntity entity;
        String sepJSON = "\",\"";
        String sepParti = "\":\"";
        List<String> listaContenutiGraffe = null;
        String[] parametri;
        String[] parti;
        String key;
        String value;

        if (doc != null) {
            mappa = new LinkedHashMap<>();
            stringaJSON = fixDoc(doc);
            //            stringaJSON = text.estraeGraffaSingola(stringaJSON);
            stringaJSON = text.setNoGraffe(stringaJSON);
            stringaJSON = text.setNoDoppiApici(stringaJSON);
            listaContenutiGraffe = estraeGraffe(stringaJSON);

            if (array.isAllValid(listaContenutiGraffe)) {
                for (String contenuto : listaContenutiGraffe) {
                    stringaJSON = stringaJSON.replace(contenuto, VUOTA);
                }
            }
            parametri = stringaJSON.split(sepJSON);
            for (String parametro : parametri) {
                parti = parametro.split(sepParti);
                if (parti.length == 2) {
                    key = parti[0];
                    value = parti[1];
                    mappa.put(key, value);
                }
            }
        }

        return mappa;
    }


    /**
     * Costruzione della entity partendo da un documento JSON <br>
     * Recupera dal documento la stringa, sostituendo gli underscore del keyID <br>
     *
     * @param entityClazz della AEntity
     * @param doc         'documento' mongo della collezione
     *
     * @return new entity
     */
    public AEntity crea(Class entityClazz, Document doc) {
        AEntity entityBean = null;

        if (entityClazz == null || doc == null) {
            return null;
        }

        entityBean = creaNoDbRef(entityClazz, doc);
        if (isDBRef(doc)) {
            entityBean = addDbRef(entityClazz, doc, entityBean);
        }

        return entityBean;
    }


    /**
     * Costruzione della entity partendo da una stringa Gson <br>
     * Non ci sono campi linkati con @DBRef <br>
     *
     * @param entityClazz della AEntity
     * @param jsonString  estratta dal document Gson
     *
     * @return new entity
     */
    public AEntity creaNoDbRef(Class entityClazz, String jsonString) {
        AEntity entity;
        Gson gSon;
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {

            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                String[] parti = json.getAsString().split(SPAZIO);
                int mese = AEMese.getNumMese(parti[0]);
                int giorno = Integer.parseInt(text.levaCoda(parti[1], VIRGOLA));
                int anno = Integer.parseInt(text.levaCoda(parti[2], VIRGOLA));
                LocalDate data = LocalDate.of(anno, mese, giorno);
                parti = parti[3].split(DUE_PUNTI);
                LocalTime orario = LocalTime.of(Integer.parseInt(parti[0]), Integer.parseInt(parti[1]), Integer.parseInt(parti[2]));
                LocalDateTime dateTime = LocalDateTime.of(data, orario);
                return dateTime;
            }
        });
        builder.registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {

            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                String[] parti = json.getAsString().split(SPAZIO);
                int mese = AEMese.getNumMese(parti[0]);
                int giorno = Integer.parseInt(text.levaCoda(parti[1], VIRGOLA));
                int anno = Integer.parseInt(text.levaCoda(parti[2], VIRGOLA));
                LocalDate data = LocalDate.of(anno, mese, giorno);
                return data;
            }
        });
        gSon = builder.create();

        entity = (AEntity) gSon.fromJson(jsonString, entityClazz);

        return entity;
    }

    /**
     * Costruzione della entity partendo da un documento Gson <br>
     * Non ci sono campi linkati con @DBRef <br>
     *
     * @param entityClazz della AEntity
     * @param doc         'documento' mongo della collezione
     *
     * @return new entity
     */
    public AEntity creaNoDbRef(Class entityClazz, Document doc) {
        String jsonString = this.fixDoc(doc);
        return creaNoDbRef(entityClazz, jsonString);
    }


    /**
     * Costruzione della entity partendo da una stringa Gson <br>
     * Ci sono campi linkati con @DBRef <br>
     *
     * @param entityClazz della AEntity
     * @param doc         'documento' mongo della collezione
     *
     * @return new entity
     */
    public AEntity addDbRef(Class entityClazz, Document doc, AEntity entityBean) {
//        AEntity entityBean = null;
        Gson gSon = new Gson();
        String jsonString = this.fixDoc(doc);
        List<String> listaContenutiGraffe = estraeGraffe(jsonString);

        //--costruisce la entity SENZA il parametro linkato ad un'altra classe (@DBRef)
        try {
            //            entity = (AEntity) gSon.fromJson(jsonString, clazz);
//            entityBean = creaNoDbRef(entityClazz, doc);

            //--aggiunge il valore dei parametri linkati ad un'altra classe (@DBRef)
            entityBean = estraeAllRef(entityClazz, jsonString, entityBean);

            return entityBean;
        } catch (JsonSyntaxException unErrore) {
            logger.error(unErrore, this.getClass(), "addDbRef");
        }

        return entityBean;
    }

    public AEntity estraeAllRef(Class entityClazz, String jsonString, AEntity entity) {
        List<String> listaContenutiGraffe = estraeGraffe(jsonString);

        if (array.isAllValid(listaContenutiGraffe)) {
            for (String jsonTestoLink : listaContenutiGraffe.subList(1, listaContenutiGraffe.size())) {
                estraeSingoloRef(entityClazz, entity, jsonTestoLink);
            }
        }

        return entity;
    }


    public AEntity estraeSingoloRef(Class entityClazz, AEntity entity, String jsonTestoLink) {
        String[] parti;
        String nomeCollezioneLinkata = VUOTA;
        String riferimentoLinkato = VUOTA;
        String valoreID = VUOTA;

        parti = jsonTestoLink.split(DUE_PUNTI + GRAFFA_INI_REGEX);
        if (parti != null) {
            nomeCollezioneLinkata = parti[0];
            nomeCollezioneLinkata = text.setNoDoppiApici(nomeCollezioneLinkata).trim();
            riferimentoLinkato = parti[1];
        }
        if (text.isValid(riferimentoLinkato)) {
            parti = riferimentoLinkato.split(VIRGOLA);
            if (parti != null) {
                parti = parti[0].split(DUE_PUNTI);

                if (parti != null) {
                    valoreID = parti[1];
                    valoreID = text.setNoDoppiApici(valoreID).trim();
                }
            }
        }

        if (text.isValid(nomeCollezioneLinkata) && text.isValid(valoreID)) {
            entity = creaEntity(entityClazz, entity, nomeCollezioneLinkata, valoreID);
        }

        return entity;
    }


    public AEntity creaEntity(Class entityClazz, AEntity entity, String nomeCollezioneLinkata, String valueID) {
        AEntity entityBean = null;
        Class entityLinkClazz;
        MongoCollection<Document> collection;
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("vaadflow14");
        //        Document doc;
        Field field = reflection.getField(entityClazz, nomeCollezioneLinkata);
        //        Gson gSon = new Gson();
        entityLinkClazz = annotation.getComboClass(field);
        collection = database.getCollection(nomeCollezioneLinkata);
        Document doc = (Document) collection.find().first();
        entityBean = crea(entityLinkClazz, doc);

        //        entityBean = mongo.findById(Secolo.class, valueID);
        try {
            field.set(entity, entityBean);
        } catch (Exception unErrore) {
            logger.error(unErrore, this.getClass(), "nomeDelMetodo");
        }

        return entity;
    }


}