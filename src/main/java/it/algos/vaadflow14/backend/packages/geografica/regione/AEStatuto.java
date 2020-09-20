package it.algos.vaadflow14.backend.packages.geografica.regione;

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
    svizzera("Cantone svizzero"),

    romaniaCapitale("Capitale rumena"),

    franciaCollettivita("Collettività d'oltremare"),

    slovenia("Comune sloveno"),

    spagna("Comunità autonoma"),

    albania("Distretto albanese"),

    bulgaria("Distretto bulgaro"),

    portogalloDistretto("Distretto portoghese"),

    romaniaDistretto("Distretto rumeno"),

    austria("Land austriaco"),

    germania("Land tedesco"),

    greciaPeriferia("Periferia greca"),

    greciaPrefettura("Prefettura greca"),

    olanda("Provincia olandese"),

    ungheria("Provincia ungherese"),

    portogalloRegione("Regione autonoma"),

    belgio("Regione belga"),

    cechia("Regione ceca"),

    croazia("Regione croata"),

    danimarca("Regione danese"),

    franciaOltremare("Regione d'oltremare"),

    ordinaria("Regione ordinaria"),

    franciaMetropolitana("Regione metropolitana"),

    spagnaRegione("Regione spagnola"),

    speciale("Regione speciale"),

    slovacchia("Regione slovacca"),

    polonia("Voivodato polacco"),

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
