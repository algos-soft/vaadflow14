package it.algos.vaadflow14.wizard.enumeration;

/**
 * Project wikibio
 * Created by Algos
 * User: gac
 * Date: mer, 10-mar-2021
 * Time: 16:07
 */
public enum AETypeWiz {

    nonModificabile("Costanti", false, "Valori statici e sempre immutabili."),
    regolatoSistema("Finali", true,  "Dipende dal programma in uso. Regolato alla partenza di Wizard e poi statiche."),
    necessarioProgetto("Variabili", true, "Dipende dal project selezionato. Regolato all'uscita del dialogo."),
    necessarioPackage("Variabili", true, "Dipende dal package selezionato. Regolato all'uscita del dialogo."),
    necessarioEntrambi("Variabili", true, "Dipende dal project e/o dal package selezionato. Regolato all'uscita del dialogo."),
    ;

    private String tag;

    private boolean serveValore;

    private String descrizione;


    AETypeWiz(String tag, boolean serveValore, String descrizione) {
        this.tag = tag;
        this.serveValore = serveValore;
        this.descrizione = descrizione;
    }

    public String getTag() {
        return tag;
    }

    public boolean isServeValore() {
        return serveValore;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
