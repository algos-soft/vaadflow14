package it.algos.vaadflow14.backend.packages.geografica.regione;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 03-lug-2020
 * Time: 17:56
 */
public enum AERegione {
    abruzzo("Abruzzo", "IT-65", "ABR", AEStatuto.ordinaria),

    basilicata("Basilicata", "IT-77", "BAS", AEStatuto.ordinaria),

    calabria("Calabria", "IT-78", "CAL", AEStatuto.ordinaria),

    campania("Campania", "IT-72", "CAM", AEStatuto.ordinaria),

    emilia("Emilia-Romagna", "IT-45", "EMR", AEStatuto.ordinaria),

    friuli("Friuli-Venezia Giulia", "IT-36", "FVG", AEStatuto.speciale),

    lazio("Lazio", "IT-62", "LAZ", AEStatuto.ordinaria),

    liguria("Liguria", "IT-42", "LIG", AEStatuto.ordinaria),

    lombardia("Lombardia", "IT-25", "LOM", AEStatuto.ordinaria),

    marche("Marche", "IT-57", "MAR", AEStatuto.ordinaria),

    molise("Molise", "IT-67", "MOL", AEStatuto.ordinaria),

    piemonte("Piemonte", "IT-21", "PNM", AEStatuto.ordinaria),

    puglia("Puglia", "IT-75", "PUG", AEStatuto.ordinaria),

    sardegna("Sardegna", "IT-88", "SAR", AEStatuto.speciale),

    sicilia("Sicilia", "IT-82", "SIC", AEStatuto.speciale),

    toscana("Toscana", "IT-52", "TOS", AEStatuto.ordinaria),

    trentino("Trentino-Alto Adige", "IT-32", "TAA", AEStatuto.speciale),

    umbria("Umbria", "IT-55", "UMB", AEStatuto.ordinaria),

    aosta("Valle d'Aosta", "IT-23", "VAO", AEStatuto.speciale),

    veneto("Veneto", "IT-34", "VEN", AEStatuto.ordinaria),
    ;


    private String nome;

    private String iso;

    private String sigla;

    private AEStatuto statuto;


    /**
     * Costruttore completo con parametri.
     *
     * @param nome    della regione
     * @param iso     codifica ISO 3166-2:IT
     * @param sigla   informale utilizzata
     * @param statuto tipo di statuto della regione
     */
    AERegione(String nome, String iso, String sigla, AEStatuto statuto) {
        this.nome = nome;
        this.iso = iso;
        this.sigla = sigla;
        this.statuto = statuto;
    }


    public int getOrd() {
        return this.ordinal() + 1;
    }


    public String getNome() {
        return nome;
    }


    public String getIso() {
        return iso;
    }


    public String getSigla() {
        return sigla;
    }


    public AEStatuto getStatuto() {
        return statuto;
    }

    public String getStatutoTxt() {
        return statuto.getNome();
    }
}
