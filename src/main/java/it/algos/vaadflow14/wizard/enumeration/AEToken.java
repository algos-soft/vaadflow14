package it.algos.vaadflow14.wizard.enumeration;


import java.util.Map;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: mar, 06-mar-2018
 * Time: 19:46
 */
public enum AEToken {

    nameTargetProject("PROJECT", true),

    pathTargetProject(VUOTA, true),

    projectNameUpper("PROJECTALLUPPER", true),

    moduleNameMinuscolo("MODULELOWER", true),

    moduleNameMaiuscolo("MODULEUPPER", true),

    first("FIRSTCHARPROJECT", true),

    pathVaadFlowWizTxtSources(VUOTA, true),

    packageName("PACKAGE", true),

    projectCost("COST"),

    user("USER"),

    today("TODAY"),

    qualifier("QUALIFIER"),

    tagView("TAG_VIEW"),

    entity("ENTITY"),

    estendeEntity("ESTENDEENTITY"),

    superClassEntity("SUPERCLASSENTITY"),

    parametersFind("PARAMETERSFIND"),

    parameters("PARAMETERS"),

    parametersDoc("PARAMETERSDOC"),

    parametersNewEntity("PARAMETERSNEWENTITY"),

    methodFind("FIND"),

    methodNewOrdine("NEWORDINE"),

    methodIdKeySpecifica("IDKEYSPECIFICA"),

    keyUnica("KEYUNICA"),

    builder("BUILDER"),

    query("QUERY"),

    findAll("FINDALL"),

    properties("PROPERTIES"),

    propertyOrdine("ORDINE"),

    propertyCode("CODE"),

    propertyDescrizione("DESCRIZIONE"),

    toString("TOSTRING"),

    usaCompany("USACOMPANY"),

    usaSecurity("USASECURITY"),


    readCompany("READCOMPANY"),

    grid("GRID"),

    creaGrid("CREAGRID"),

    postConstruct("POSTCONSTRUCT"),

    setParameter("SETPARAMETER"),

    beforeEnter("BEFOREENTER"),

    fixPreferenze("FIXPREFERENZE"),

    fixLayout("FIXLAYOUT"),

    creaAlertLayout("CREAALERTLAYOUT"),

    creaTopLayout("CREATOPLAYOUT"),

    creaPopupFiltro("CREAPOPUPFILTRO"),

    creaFiltri("CREAFILTRI"),

    updateFiltri("UPDATEFILTRI"),

    addListeners("ADDLISTENERS"),

    versionDate("VERSIONDATE"),
    ;

    private static String DELIMITER = "@";

    private String tokenTag;

    private boolean usaValue;

    private String value;


    AEToken(String tokenTag) {
        this(tokenTag, false);
    }


    AEToken(String tokenTag, boolean usaValue) {
        this(tokenTag, usaValue, VUOTA);
    }


    AEToken(String tokenTag, boolean usaValue, String value) {
        this.setTokenTag(tokenTag);
        this.setUsaValue(usaValue);
        this.setValue(value);
    }


    public static void reset() {
        for (AEToken aeToken : AEToken.values()) {
            aeToken.setValue(VUOTA);
        }
    }


    public static String replace(AEToken EAToken, String textReplacing, String value) {
        if (value != null && value.length() > 0) {
            return textReplacing.replaceAll(DELIMITER + EAToken.tokenTag + DELIMITER, value);
        } else {
            return textReplacing;
        }
    }


    public static String replaceAll(String textReplacing, Map<AEToken, String> valueMap) {

        for (AEToken aeToken : values()) {
            if (valueMap.containsKey(aeToken)) {
                textReplacing = replace(aeToken, textReplacing, valueMap.get(aeToken));
                //                textReplacing = textReplacing.replaceAll(DELIMITER + token.tokenTag + DELIMITER, valueMap.get(token));
            }
        }

        return textReplacing;
    }


    public String replace(String textReplacing, String currentTag) {
        return textReplacing.replaceAll(tokenTag, currentTag);
    }


    public String getTokenTag() {
        return tokenTag;
    }


    public void setTokenTag(String tokenTag) {
        this.tokenTag = tokenTag;
    }


    public boolean isUsaValue() {
        return usaValue;
    }


    public void setUsaValue(boolean usaValue) {
        this.usaValue = usaValue;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }

}
