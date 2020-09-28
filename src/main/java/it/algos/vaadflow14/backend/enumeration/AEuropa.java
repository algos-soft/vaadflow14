package it.algos.vaadflow14.backend.enumeration;

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

    ;

    private String nome;

    private String isoTag;

    private String paginaWiki;


    AEuropa(String nome, String isoTag) {
        this(nome, isoTag, "ISO_3166-2:" + isoTag);
    }


    AEuropa(String nome, String isoTag, String paginaWiki) {
        this.nome = nome;
        this.isoTag = isoTag;
        this.paginaWiki = paginaWiki;
    }

}
