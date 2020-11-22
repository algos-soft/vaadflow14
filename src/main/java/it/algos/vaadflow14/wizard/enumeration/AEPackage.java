package it.algos.vaadflow14.wizard.enumeration;

/**
 * Project alfa
 * Created by Algos
 * User: gac
 * Date: sab, 21-nov-2020
 * Time: 14:19
 * <p>
 * Possibili componenti di un package
 */
public enum AEPackage {
    entity("Entity base del package", true),

    logic("Business logic del package", false),

    form("Form specifico del package", false),

    list("List specifico del package", false),

    service("Service specifico del package", false),

    data("Creazione dati specifico del package", false),

    enumeration("Enumerations specifiche del package", false),

    csv("Files csv esterni per i dati del package", false),
    ;

    private String descrizione;

    private boolean attivo;

    /**
     * Costruttore completo <br>
     */
    AEPackage(String descrizione, boolean attivo) {
        this.descrizione = descrizione;
        this.attivo = attivo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public boolean isAttivo() {
        return attivo;
    }
}// end of enumeration class
