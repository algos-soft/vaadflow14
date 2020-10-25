package it.algos.vaadflow14.backend.service;

import com.google.gson.Gson;
import it.algos.vaadflow14.backend.entity.AEntity;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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
     * Semplice serializzazione dell' oggetto <br>
     * Può essere un 'documento' mongo oppure una entity <br>
     *
     * @param doc documento della collezione
     *
     * @return stringa in formato JSon
     */
    public String toStringa(Object doc) {
        String stringaJSON = VUOTA;
        Gson gSon;

        if (doc != null) {
            gSon = new Gson();
            stringaJSON = gSon.toJson(doc);
            stringaJSON = stringaJSON.replace("_id", "id");
        }

        return stringaJSON;
    }


    /**
     * Mappa dell' oggetto <br>
     * Può essere un 'documento' mongo oppure una entity <br>
     *
     * @param doc documento della collezione
     *
     * @return mappa chiave-valore
     */
    public LinkedHashMap toMap(Object doc) {
        LinkedHashMap mappa = null;
        String stringaJSON = VUOTA;
        String stringaJSON2 = VUOTA;
        String stringaJSON3 = VUOTA;
        AEntity entity;
        Gson gSon;
        List<String> listaContenutiGraffe = null;

        if (doc != null) {
            stringaJSON = toStringa(doc);
            gSon = new Gson();
            listaContenutiGraffe = estraeGraffe(stringaJSON);
                        String[] parti = stringaJSON.split(VIRGOLA);
                        stringaJSON2=text.estrae(stringaJSON,GRAFFA_INI,GRAFFA_END);
            //            String[] parti2 = stringaJSON.split(GRAFFA_INI_REGEX);
            //            String[] parti3 = stringaJSON.split(GRAFFA_END_REGEX);
            //            stringaJSON3=stringaJSON.substring(1,stringaJSON.length()-1);
            //            entity = (AEntity) gSon.fromJson(stringaJSON, Anno.class);
            //            entity = (AEntity) gSon.fromJson(stringaJSON3, Anno.class);
            //            entity = (AEntity) gSon.fromJson(stringaJSON2, Anno.class);

            //            JsonElement element=gSon.toJsonTree(doc);
            //            JsonObject obj= element.getAsJsonObject();
            //            stringaJSON2=obj.toString();
            //            JsonElement sec=  obj.get("secolo");
            //            JsonObject obj2= sec.getAsJsonObject();
            //            JsonElement sec2=  obj2.get("id");
            //            String value=sec2.getAsString();
        }

        return mappa;
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
    public List<String> estraeGraffe(String testoIn) {
        List<String> listaContenutiGraffe = null;
        String contenuto = VUOTA;

        if (text.isEmpty(testoIn)) {
            return null;
        }

        testoIn = testoIn.substring(1, testoIn.length() - 1);
        if (!(testoIn.contains(GRAFFA_INI) && testoIn.contains(GRAFFA_END))) {
            return array.getLista(testoIn);
        }

        listaContenutiGraffe = new ArrayList<>();
        contenuto = text.estraeGraffaSingola(testoIn);
        if (text.isValid(contenuto)) {
            contenuto = GRAFFA_INI + contenuto + GRAFFA_END;
            listaContenutiGraffe.add(contenuto);
        }

        return listaContenutiGraffe;
    }

}