package it.algos.vaadflow14.backend.packages.geografica.regione;

import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 10:19
 */
public enum AEStatuto {
    ordinaria("Regione ordinaria"),

    speciale("Regione speciale"),

    germania("Land tedesco"),

    franciaMetropolitana("Regione metropolitana"),

    franciaOltremare("Regione d'oltremare"),

    franciaCollettivita("Collettività d'oltremare"),

    svizzera("Cantone svizzero"),

    austria("Land austriaco"),

    spagna("Comunità autonoma"),

    spagnaRegione("Regione spagnola"),

    portogalloDistretto("Distretto portoghese"),

    portogalloRegione("Regione autonoma"),

    slovenia("Comune sloveno"),

    belgio("Regione belga"),

    olanda("Provincia olandese"),

    croazia("Regione croata"),
    ;

    private String nome;


    /**
     * Costruttore completo con parametri.
     *
     * @param nome della regione
     */
    AEStatuto(String nome) {
        this.nome = nome;
    }


    public static AEStatuto get(String nome) {
        AEStatuto aeStatuto = AEStatuto.ordinaria;

        for (AEStatuto statuto : AEStatuto.values()) {
            if (statuto.getNome().equals(nome)) {
                aeStatuto = statuto;
            }
        }

        return aeStatuto;
    }


    public static List<AEStatuto> getValues() {
        List<AEStatuto> lista = new ArrayList<>();

        for (AEStatuto aeStatuto : values()) {
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

}
