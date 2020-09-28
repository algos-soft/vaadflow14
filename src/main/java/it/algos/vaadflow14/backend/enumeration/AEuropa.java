package it.algos.vaadflow14.backend.enumeration;

import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.backend.service.AMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 28-set-2020
 * Time: 18:35
 */
public enum AEuropa {
    italia("italia", "IT"),

    austria("austria", "AT"),

    belgio("belgio", "BE"),

    bulgaria("bulgaria", "BG"),

    cipro("cipro", "CY"),

    croazia("croazia", "HR"),

    danimarca("danimarca", "DK"),

    estonia("estonia", "EE"),

    finlandia("finlandia", "FI"),

    francia("francia", "FR"),

    germania("germania", "DE"),

    grecia("grecia", "GR"),

    irlanda("irlanda", "IE"),

    lettonia("lettonia", "LV"),

    lituania("lituania", "LT"),

    lussemburgo("lussemburgo", "LU"),

    malta("malta", "MT"),

    olanda("paesibassi", "NL"),

    polonia("polonia", "PL"),

    portogallo("portogallo", "PT"),

    cechia("repubblicaceca", "CZ"),

    romania("romania", "RO"),

    slovacchia("slovacchia", "SK"),

    spagna("spagna", "ES"),

    svezia("svezia", "SE"),

    ungheria("ungheria", "HU"),

    svizzera("svizzera", "CH"),

    albania("albania", "AL"),

    slovenia("slovenia", "SI"),

    ;

    private String nome;

    private String iso;

    private String paginaWiki;

    //--Link injettato da un metodo static
    private AMongoService mongo;


    AEuropa(String nome, String iso) {
        this(nome, iso, "ISO_3166-2:" + iso);
    }


    AEuropa(String nome, String iso, String paginaWiki) {
        this.nome = nome;
        this.iso = iso;
        this.paginaWiki = paginaWiki;
    }


    public void setMongo(AMongoService mongo) {
        this.mongo = mongo;
    }


    public String getNome() {
        return nome;
    }


    public String getIso() {
        return iso;
    }


    public String getIsoTag() {
        return iso + "-";
    }


    public String getPaginaWiki() {
        return paginaWiki;
    }


    public Stato getStato() {
        return (Stato) mongo.findById(Stato.class, nome);
    }


    @Component
    public static class AMongoServiceInjector {

        @Autowired
        private AMongoService mongo;


        @PostConstruct
        public void postConstruct() {
            for (AEuropa aEuropa : AEuropa.values()) {
                aEuropa.setMongo(mongo);
            }
        }

    }

}
