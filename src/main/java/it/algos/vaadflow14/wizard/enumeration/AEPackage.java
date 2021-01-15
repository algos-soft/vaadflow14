package it.algos.vaadflow14.wizard.enumeration;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.wizard.scripts.WizCost.LOGIC_SUFFIX;
import static it.algos.vaadflow14.wizard.scripts.WizCost.SERVICE_SUFFIX;

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
    entity(VUOTA, "Entity base del package", true),

    logic(LOGIC_SUFFIX, "Business logic del package", true),

    service(SERVICE_SUFFIX, "Service specifico del package", true),

    list("List", "List specifico del package", false),

    form("Form", "Form specifico del package", false),

    data("Data", "Creazione dati specifico del package", false),

    enumeration("Enum", "Enumerations specifiche del package", false),

    csv("CSV", "Files csv esterni per i dati del package", false),
    ;

    private String tag;

    private String descrizione;

    private boolean acceso;

    private boolean accesoInizialmente;

    /**
     * Costruttore completo <br>
     */
    AEPackage(String tag, String descrizione, boolean accesoInizialmente) {
        this.tag = tag;
        this.descrizione = descrizione;
        this.accesoInizialmente = accesoInizialmente;
        this.acceso = accesoInizialmente;
    }

    /**
     * Visualizzazione di controllo <br>
     */
    public static void printInfo(String posizione) {
        System.out.println("********************");
        System.out.println("AEPackage  - " + posizione);
        System.out.println("********************");
        for (AEPackage pack : AEPackage.values()) {
            System.out.println("AEPackage." + pack.name() + "  = " + pack.is());
        }
        System.out.println("");
    }

    public String getDescrizione() {
        return descrizione;
    }

    public boolean isAccesoInizialmente() {
        return accesoInizialmente;
    }

    public boolean is() {
        return acceso;
    }

    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public String getTag() {
        return tag;
    }
}// end of enumeration class
