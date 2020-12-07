package it.algos.simple.backend.enumeration;

import it.algos.vaadflow14.backend.enumeration.AETypePref;
import it.algos.vaadflow14.backend.interfaces.AIEnum;
import it.algos.vaadflow14.backend.interfaces.AIPreferenza;
import it.algos.vaadflow14.backend.packages.preferenza.APreferenzaService;
import it.algos.vaadflow14.backend.packages.preferenza.Preferenza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 18-set-2020
 * Time: 15:41
 */
public enum AESimplePreferenza implements AIPreferenza {

    datauno("datauno", "Data senza ora (pref di prova)", AETypePref.localdate, LocalDate.now(), false),

    datadue("datadue", "Data e ora (pref di prova)", AETypePref.localdatetime, LocalDateTime.now(), false),

    timeuno("datatre", "Solo orario (pref di prova)", AETypePref.localtime, LocalTime.now(), false),
    testo("testo", "Testo (pref di prova)", AETypePref.string, "mariolino", false),

    ;

    //--codice di riferimento. Se è usaCompany = true, DEVE contenere anche il code della company come prefisso.
    private String keyCode;

    //--descrizione breve ma comprensibile. Ulteriori (eventuali) informazioni nel campo 'note'
    private String descrizione;

    //--tipologia di dato da memorizzare.
    //--Serve per convertire (nei due sensi) il valore nel formato byte[] usato dal mongoDb
    private AETypePref type;

    //--Valore java iniziale da convertire in byte[] a seconda del type
    private Object defaultValue;

    //--Link injettato da un metodo static
    private APreferenzaService preferenzaService;

    //--usa un prefisso col codice della company
    private boolean companySpecifica;

    //--descrizione aggiuntiva
    private String note;


    AESimplePreferenza(String keyCode, String descrizione, AETypePref type, Object defaultValue, boolean companySpecifica) {
        this(keyCode, descrizione, type, defaultValue, companySpecifica, VUOTA);
    }// fine del costruttore


    AESimplePreferenza(String keyCode, String descrizione, AETypePref type, Object defaultValue, boolean companySpecifica, String note) {
        this.setKeyCode(keyCode);
        this.setDescrizione(descrizione);
        this.setType(type);
        this.setNote(note);
        this.setCompanySpecifica(companySpecifica);
        this.setDefaultValue(defaultValue);
    }// fine del costruttore


    public void setPreferenzaService(APreferenzaService preferenzaService) {
        this.preferenzaService = preferenzaService;
    }


    public String getKeyCode() {
        return keyCode;
    }


    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }


    public String getDescrizione() {
        return descrizione;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public AETypePref getType() {
        return type;
    }


    public void setType(AETypePref type) {
        this.type = type;
    }

    @Override
    public boolean isCompanySpecifica() {
        return companySpecifica;
    }

    public void setCompanySpecifica(boolean companySpecifica) {
        this.companySpecifica = companySpecifica;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }


    public void setDefaultValue(Object defaultValue) {
        if (type == AETypePref.enumeration) {
            if (defaultValue instanceof String) {
                this.defaultValue = defaultValue;
            }
            else {
                this.defaultValue = ((AIEnum) defaultValue).getPref();
            }
        }
        else {
            this.defaultValue = defaultValue;
        }
    }


    public Object getValue() {
        Object javaValue;
        Preferenza preferenza = null;

        if (preferenzaService == null) {
            return null;
        }

        preferenza = preferenzaService.findByKey(keyCode);
        javaValue = preferenza != null ? type.bytesToObject(preferenza.getValue()) : null;

        return javaValue;
    }


    public boolean is() {
        boolean status = false;
        Object javaValue;

        if (type == AETypePref.bool) {
            javaValue = getValue();
            status = javaValue instanceof Boolean && (boolean) javaValue;
        }

        return status;
    }


    public int getInt() {
        int value = 0;
        Object javaValue;

        if (type == AETypePref.integer) {
            javaValue = getValue();
            value = (javaValue instanceof Integer) ? (Integer) javaValue : 0;
        }

        return value;
    }


    public String getNote() {
        return note;
    }


    public void setNote(String note) {
        this.note = note;
    }


    @Component
    public static class APreferenzaServiceInjector {

        @Autowired
        private APreferenzaService preferenzaService;


        @PostConstruct
        public void postConstruct() {
            for (AESimplePreferenza pref : AESimplePreferenza.values()) {
                pref.setPreferenzaService(preferenzaService);
            }
        }

    }


}
