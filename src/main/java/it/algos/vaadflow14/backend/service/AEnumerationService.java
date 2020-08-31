package it.algos.vaadflow14.backend.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.PUNTO_VIRGOLA;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: Sun, 21-Jul-2019
 * Time: 21:35
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AEnumerationService extends AAbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Private constructor to avoid client applications to use constructor <br>
     * In alcune circostanze SpringBoot non riesce a costruire l'istanza <br>
     * Rimesso 'public' al posto del precedente 'private' <br>
     */
    public AEnumerationService() {
    }// end of constructor


    /**
     * Restituisce le due parti del valore grezzo <br>
     * Se manca il punto e virgola (valore selezionato = nullo), la matrice ha un solo valore <br>
     *
     * @param rawValue dei valori ammessi seguita dal valore selezionato
     *
     * @return matrice delle due parti
     */
    public String[] getParti(String rawValue) {
        String[] parti;
        String prima;
        String seconda;

        if (rawValue.contains(PUNTO_VIRGOLA)) {
            prima = rawValue.substring(0, rawValue.indexOf(PUNTO_VIRGOLA));
            seconda = rawValue.substring(rawValue.indexOf(PUNTO_VIRGOLA) + 1);
            parti = new String[]{prima, seconda};
        } else {
            parti = new String[]{rawValue};
        }

        return parti;
    }


    /**
     * Trasforma il contenuto del mongoDB nel valore selezionato, eliminando la lista dei valori possibili <br>
     * Estrae la parte dopo il punto e virgola (se esiste) <br>
     *
     * @param rawValue dei valori ammessi seguita dal valore selezionato
     *
     * @return valore selezionato
     */
    public String convertToPresentation(String rawValue) {
        String value = VUOTA;
        String[] parti = getParti(rawValue);

        if (parti != null && parti.length == 2) {
            value = parti[1];
        }

        return value;
    }


    /**
     * Restituisce una stringa dei valori ammissibili <br>
     * Estrae la parte prima del punto e virgola (se esiste), altrimenti tutto <br>
     *
     * @param rawValue dei valori ammessi seguita dal valore selezionato
     *
     * @return stringa dei valori ammissibili
     */
    public String getStringaValori(String rawValue) {
        String stringaValori = rawValue;
        String[] parti = getParti(rawValue);

        if (parti != null && parti.length == 2) {
            stringaValori = parti[0];
        }

        return stringaValori;
    }


    /**
     * Restituisce la lista dei valori ammissibili <br>
     *
     * @param rawValue dei valori ammessi seguita dal valore selezionato
     *
     * @return lista dei valori ammissibili
     */
    public List<String> getList(String rawValue) {
        List<String> lista = null;
        String stringaValori = getStringaValori(rawValue);

        if (text.isValid(stringaValori)) {
            lista = array.getList(stringaValori);
        }

        return lista;
    }


    /**
     * Modifica il valore della stringa per il mongoDB <br>
     * Costruisce la stringa da memorizzare <br>
     *
     * @param rawValue         dei valori registrati nel mongoDB (valori ammissibili seguiti dal valore selezionato)
     * @param newSelectedValue da sostituire
     *
     * @return valore selezionato
     */
    public String convertToModel(String rawValue, String newSelectedValue) {
        String newRawValue;
        String stringaValori = getStringaValori(rawValue);

        if (stringaValori.contains(newSelectedValue)) {
            if (text.isValid(stringaValori) && text.isValid(newSelectedValue)) {
                newRawValue = stringaValori + PUNTO_VIRGOLA + newSelectedValue;
            } else {
                newRawValue = stringaValori;
            }
        } else {
            newRawValue = rawValue;
            if (newRawValue.endsWith(PUNTO_VIRGOLA)) {
                newRawValue = text.levaCoda(newRawValue, PUNTO_VIRGOLA);
            }
        }

        return newRawValue;
    }

}
