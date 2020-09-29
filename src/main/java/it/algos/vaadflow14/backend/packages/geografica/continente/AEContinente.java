package it.algos.vaadflow14.backend.packages.geografica.continente;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 29-set-2020
 * Time: 14:34
 */
public enum AEContinente {

    europa("europa", true),

    asia("asia", true),

    africa("africa", true),

    nordamerica("nordamerica", true),

    sudamerica("sudamerica", true),

    oceania("oceania", true),

    antartide("antartide", false),

    ;

    boolean abitato;

    private String nome;


    AEContinente(String nome, boolean abitato) {
        this.nome = nome;
        this.abitato = abitato;
    }

    public int getOrd() {
        return this.ordinal() + 1;
    }

    public String getNome() {
        return nome;
    }


    public boolean isAbitato() {
        return abitato;
    }
}
