package it.algos.vaadflow14.backend.enumeration;

import it.algos.vaadflow14.backend.interfaces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 10:19
 */
public enum AEStatus implements AIPref ,AIEnum{
    cantone("Cantone"),

    capitale("Capitale rumena"),

    collettivita("Collettività d'oltremare"),

    comune("Comune"),

    comunita("Comunità autonoma"),

    dipartimento("Dipartimento"),

    distretto("Distretto"),

    land("Land"),

    periferia("Periferia greca"),

    prefettura("Prefettura"),

    provincia("Provincia"),

    regione("Regione"),

    regioneAutonoma("Regione autonoma"),

    regioneOltremare("Regione d'oltremare"),

    regioneOrdinaria("Regione ordinaria"),

    regioneMetropolitana("Regione metropolitana"),

    regioneSpeciale("Regione speciale"),

    voivodato("Voivodato"),

    governatorato("Governatorato"),

    ;

    private String nome;


    /**
     * Costruttore completo con parametri.
     *
     * @param nome della regione
     */
    AEStatus(String nome) {
        this.nome = nome;
    }


    public static AEStatus get(String nome) {
        AEStatus aeStatuto = AEStatus.regione;

        for (AEStatus statuto : AEStatus.values()) {
            if (statuto.name().toLowerCase().equals(nome.toLowerCase())) {
                aeStatuto = statuto;
            }
        }

        return aeStatuto;
    }


    public static List<AEStatus> getValues() {
        List<AEStatus> lista = new ArrayList<>();

        for (AEStatus aeStatuto : values()) {
            lista.add(aeStatuto);
        }

        return lista;
    }


    public String getNome() {
        return nome;
    }


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

    /**
     * Stringa di valori (text) da usare per memorizzare la preferenza <br>
     * La stringa è composta da tutti i valori separati da virgola <br>
     * Poi, separato da punto e virgola viene il valore selezionato di default <br>
     *
     * @return stringa di valori e valore di default
     */
    @Override
    public String getPref() {
        return null;
    }

    @Override
    public String get() {
        return name();
    }
}
