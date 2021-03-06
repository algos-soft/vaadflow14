package it.algos.vaadflow14.wizard.enumeration;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;

import java.util.*;

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

    entity("Entity base del package", true, true, false, false, SOURCE_ENTITY, SUFFIX_ENTITY),

    logic("Business logic del package", true, true, false, false, SOURCE_LOGIC, SUFFIX_LOGIC),

    service("Service specifico del package", true, true, false, false, SOURCE_SERVICE, SUFFIX_SERVICE),

    menu("Inserimento del package nel menu", true, false, false, false),

    //    list("List", false, "List specifico del package"),
    //
    //    form("Form", false, "Form specifico del package"),
    //
    //    data("Data", false, "Creazione dati specifico del package"),
    //
    //    enumeration("Enum", false, "Enumerations specifiche del package"),
    //
    //    csv("CSV", false, "Files csv esterni per i dati del package"),
    //
    //    company("Entity subclass di Company", false),

    rowIndex("Entity usa rowIndex", false, false, false, false),

    ordine("Entity usa property (int)", false, false, true, true, "PropertyOrdine", VUOTA, "ordine", "ordine"),

    code("Entity usa property (String)", true, false, true, true, "PropertyCode", VUOTA, "code", "code"),

    description("Entity usa property (String)", true, false, true, true, "PropertyDescrizione", VUOTA, "descrizione", "descrizione"),

    valido("Entity usa property (boolean)", false, false, true, true, "PropertyValido", VUOTA, "valido", "valido"),
    ;


    private String descrizione;

    private boolean isAccesoInizialmente;

    private boolean isAcceso;

    private boolean isFile;

    private boolean isProperty;

    private boolean isFieldAssociato;

    private String sourcesName;

    private String suffix;

    private String field;

    private String fieldName;

    //    /**
    //     * Costruttore parziale <br>
    //     */
    //    AEPackage(String descrizione, boolean isAccesoInizialmente) {
    //        this(descrizione, false, false, isAccesoInizialmente, FlowCost.VUOTA, FlowCost.VUOTA, FlowCost.VUOTA, FlowCost.VUOTA);
    //    }
    //
    //    /**
    //     * Costruttore parziale <br>
    //     */
    //    AEPackage(String descrizione, boolean isAccesoInizialmente, String suffix) {
    //        this(descrizione, isAccesoInizialmente, false, false, suffix, FlowCost.VUOTA, FlowCost.VUOTA, FlowCost.VUOTA);
    //    }

    /**
     * Costruttore parziale <br>
     */
    AEPackage(String descrizione, boolean isAccesoInizialmente, boolean isFile, boolean isProperty, boolean isFieldAssociato) {
        this(descrizione, isAccesoInizialmente, isFile, isProperty, isFieldAssociato, VUOTA, VUOTA);
    }

    /**
     * Costruttore parziale <br>
     */
    AEPackage(String descrizione, boolean isAccesoInizialmente, boolean isFile, boolean isProperty, boolean isFieldAssociato, String sourcesName, String suffix) {
        this(descrizione, isAccesoInizialmente, isFile, isProperty, isFieldAssociato, sourcesName, suffix, VUOTA, VUOTA);
    }

    /**
     * Costruttore completo <br>
     */
    AEPackage(String descrizione, boolean isAccesoInizialmente, boolean isFile, boolean isProperty, boolean isFieldAssociato, String sourcesName, String suffix, String field, String fieldName) {
        this.descrizione = descrizione;
        this.isAccesoInizialmente = isAccesoInizialmente;
        this.isAcceso = isAccesoInizialmente;
        this.isFile = isFile;
        this.isProperty = isProperty;
        this.isFieldAssociato = isFieldAssociato;
        this.sourcesName = sourcesName;
        this.suffix = suffix;
        this.field = field;
        this.fieldName = fieldName;
    }

    public static List<AEPackage> getFiles() {
        List<AEPackage> listaPackages = new ArrayList<>();

        for (AEPackage pack : AEPackage.values()) {
            if (pack.isFile()) {
                listaPackages.add(pack);
            }
        }

        return listaPackages;
    }

    /**
     * Visualizzazione di controllo <br>
     */
    public static void printInfo(String posizione) {
        System.out.println("********************");
        System.out.println("AEPackage  - " + posizione);
        System.out.println("********************");
        for (AEPackage pack : AEPackage.values()) {
            System.out.print("AEPackage.");
            System.out.print(pack.name()+ ": ");
            System.out.print("Acceso=");
            System.out.print(pack.is());
            System.out.print(" FieldName=");
            System.out.print(pack.fieldName);
            System.out.println(VUOTA);
        }
        System.out.println(VUOTA);
    }


    public boolean is() {
        return isAcceso;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public boolean isAccesoInizialmente() {
        return isAccesoInizialmente;
    }

    public boolean isAcceso() {
        return isAcceso;
    }

    public void setAcceso(boolean acceso) {
        isAcceso = acceso;
    }

    public boolean isFile() {
        return isFile;
    }

    public boolean isProperty() {
        return isProperty;
    }

    public boolean isFieldAssociato() {
        return isFieldAssociato;
    }

    public String getSourcesName() {
        return sourcesName;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}// end of enumeration class
