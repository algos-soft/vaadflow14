package it.algos.simple.backend.enumeration;

import static it.algos.simple.backend.application.SimpleCost.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.packages.preferenza.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.time.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 18-set-2020
 * Time: 15:41
 * <p>
 * Classi concrete di Enumeration:
 * AEPreferenza (obbligatoria) di VaadFlow14 <br>
 * AESimplePreferenza (facoltativa) specifica del progetto corrente <br>
 */
public enum AESimplePreferenza implements AIPreferenza {

    datauno(PREF_DATA, "Data senza ora (pref di prova)", AETypePref.localdate, DATA, false),
    datadue(PREF_DATA_TIME, "Data e ora (pref di prova)", AETypePref.localdatetime, DATA_TIME, false),
    timeuno(PREF_TIME, "Solo orario (pref di prova)", AETypePref.localtime, TIME, false),
    testo("testo", "Testo (pref di prova)", AETypePref.string, "mariolino", false),

    ;

    //--codice di riferimento. Se è usaCompany=true, DEVE contenere anche il code della company come prefisso.
    private String keyCode;

    //--tipologia di dato da memorizzare.
    //--Serve per convertire (nei due sensi) il valore nel formato byte[] usato dal mongoDb
    private AETypePref type;

    //--Valore java iniziale da convertire in byte[] a seconda del type
    private Object defaultValue;

    //--preferenze singole per ogni company; usa un prefisso col codice della company
    private boolean usaCompany;

    //--preferenze generale del framework e NON specifica di un'applicazione
    private boolean vaadFlow;

    //--preferenze che necessita di un riavvio del programma per avere effetto
    private boolean needRiavvio;

    //--preferenze visibile agli admin se l'applicazione è usaSecurity=true
    private boolean visibileAdmin;

    //--descrizione breve ma comprensibile. Ulteriori (eventuali) informazioni nel campo 'note'
    private String descrizione;

    //--descrizione aggiuntiva eventuale
    private String note;

    //--Link injettato da un metodo static
    private PreferenzaService preferenzaService;

    //--Link injettato da un metodo static
    private ALogService logger;

    //--Link injettato da un metodo static
    private DateService date;

    //--Link injettato da un metodo static
    private EnumerationService enumService;


    AESimplePreferenza(String keyCode, String descrizione, AETypePref type, Object defaultValue, boolean usaCompany) {
        this(keyCode, descrizione, type, defaultValue, usaCompany, false, false, VUOTA);
    }// fine del costruttore

    AESimplePreferenza(String keyCode, String descrizione, AETypePref type, Object defaultValue, boolean usaCompany, String note) {
        this(keyCode, descrizione, type, defaultValue, usaCompany, false, false, note);
    }// fine del costruttore

    AESimplePreferenza(String keyCode, String descrizione, AETypePref type, Object defaultValue, boolean usaCompany, boolean needRiavvio, boolean visibileAdmin, String note) {
        this.keyCode = keyCode;
        this.descrizione = descrizione;
        this.type = type;
        this.setNote(note);
        this.usaCompany = usaCompany;
        this.vaadFlow = false;
        this.needRiavvio = needRiavvio;
        this.visibileAdmin = visibileAdmin;
        this.setDefaultValue(defaultValue);
    }// fine del costruttore


    public void setPreferenzaService(PreferenzaService preferenzaService) {
        this.preferenzaService = preferenzaService;
    }

    public void setLogger(ALogService logger) {
        this.logger = logger;
    }

    public void setDate(DateService date) {
        this.date = date;
    }

    public void setEnumService(EnumerationService enumService) {
        this.enumService = enumService;
    }

    @Override
    public String getKeyCode() {
        return keyCode;
    }

    @Override
    public AETypePref getType() {
        return type;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        if (type == AETypePref.enumeration) {
            if (defaultValue instanceof String) {
                this.defaultValue = defaultValue;
            }
            else {
                this.defaultValue = ((AIPref) defaultValue).getPref();
            }
        }
        else {
            this.defaultValue = defaultValue;
        }
    }

    @Override
    public boolean isVaadFlow() {
        return vaadFlow;
    }

    @Override
    public boolean isUsaCompany() {
        return usaCompany;
    }

    @Override
    public boolean isNeedRiavvio() {
        return needRiavvio;
    }

    @Override
    public boolean isVisibileAdmin() {
        return visibileAdmin;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
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


    public String getStr() {
        String valore = VUOTA;
        Object value = defaultValue;
        String message;

        switch (type) {
            case string:
            case email:
                valore = (String) value;
                break;
            case bool:
                valore = (boolean) getValue() ? VERO : FALSO;
                message = String.format("La preferenza %s è di type boolean. Meglio chiamare is() invece di getStr()", keyCode);
                logger.warn(message);
                break;
            case integer:
                valore += getValue();
                message = String.format("La preferenza %s è di type integer. Meglio chiamare getInt() invece di getStr()", keyCode);
                logger.warn(message);
                break;
            case lungo:
                break;
            case localdate:
                valore = date.get((LocalDate) getValue());
                break;
            case localdatetime:
                valore = date.get((LocalDateTime) getValue());
                break;
            case localtime:
                valore = date.get((LocalTime) getValue());
                break;
            case enumeration:
                valore = ((String) getValue()).substring(((String) getValue()).indexOf(PUNTO_VIRGOLA) + 1);
                break;
            case icona:
                break;
            default:
                message = String.format("La preferenza: AEPreferenza.%s è di tipo %s e non può essere convertita in stringa.", name(), type);
                logger.error(message);
                break;
        }

        return valore;
    }

    @Override
    public boolean is() {
        String message;

        if (type == AETypePref.bool) {
            return getValue() != null ? (boolean) getValue() : false;
        }
        else {
            message = String.format("La preferenza %s è di type %s. Non puoi usare is()", keyCode, type);
            logger.error(message);
            return false;
        }
    }

    @Override
    public int getInt() {
        String message;

        if (type == AETypePref.integer) {
            return getValue() != null ? (int) getValue() : 0;
        }
        else {
            message = String.format("La preferenza %s è di type %s. Non puoi usare getInt()", keyCode, type);
            logger.error(message);
            return 0;
        }
    }

    @Override
    public LocalDateTime getDate() {
        String message;

        if (type == AETypePref.localdatetime) {
            return getValue() != null ? (LocalDateTime) getValue() : null;
        }
        else {
            message = String.format("La preferenza %s è di type %s. Non puoi usare getDate()", keyCode, type);
            logger.error(message);
            return null;
        }
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
        private PreferenzaService preferenzaService;

        @Autowired
        private ALogService logger;

        @Autowired
        private EnumerationService enumService;

        @Autowired
        private DateService date;

        @PostConstruct
        public void postConstruct() {
            for (AESimplePreferenza pref : AESimplePreferenza.values()) {
                pref.setPreferenzaService(preferenzaService);
                pref.setLogger(logger);
                pref.setDate(date);
                pref.setEnumService(enumService);
            }
        }

    }

}
