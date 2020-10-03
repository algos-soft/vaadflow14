package it.algos.vaadflow14.backend.enumeration;

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
public enum AEPreferenza {
    usaDebug("usaDebug", "Flag generale di debug", AETypePref.bool, false, "Ce ne possono essere di specifici, validi solo se questo è vero"),

    usaSearchClearButton("usaSearchClearButton", "Bottone per pulire il filtro di ricerca", AETypePref.bool, true),

    usaBandiereStati("usaBandiereStati", "Bandierine nel combobox degli stati", AETypePref.bool, true),

    usaGridHeaderMaiuscola("usaGridHeaderMaiuscola", "Prima lettera maiuscola nell' header della Grid", AETypePref.bool, true),

    usaFormFieldMaiuscola("usaFormFieldMaiuscola", "Prima lettera maiuscola nella label di un field", AETypePref.bool, true),

    usaSearchCaseSensitive("usaSearchCaseSensitive", "Search delle query sensibile alle maiuscole", AETypePref.bool, false),

    //    iconaDetail("iconaDetail", "VaadinIcon per aprire il Form", AETypePref.icona, "TRUCK"),

    pippoz("daCancellare", "Prova preferenza testo", AETypePref.string, "Alfa"),

    enumeration2("daCancellare2", "Prova enumeration", AETypePref.enumeration, "Alfa,Beta,Gamma,Delta;Beta"),

    enumeration3("daCancellare3", "Prova enumeration completa", AETypePref.enumeration, AELogLivello.info),

    iconaEdit("iconaEdit", "Icona per il dettaglio della scheda", AETypePref.enumeration, AETypeIconaEdit.edit),

    mailTo("email", "Indirizzo email", AETypePref.email, "gac@algos.it", "Email di default a cui spedire i log di posta"),

    maxRigheGrid("maxRigheGrid", "Righe massime della griglia semplice", AETypePref.integer, 20, "Numero di elementi oltre il quale scatta la pagination automatica della Grid (se attiva)"),

    maxEnumRadio("maxEnumRadio", "Massimo numero di 'radio' nelle Enum' ", AETypePref.integer, 3, "Numero massimo di items nella preferenza di tipo Enum da visualizzare con i radioBottoni; se superiore, usa un ComboBox"),

    datauno("datauno", "Data senza ora", AETypePref.localdate, LocalDate.now()),

    datadue("datadue", "Data e ora", AETypePref.localdatetime, LocalDateTime.now()),

    timeuno("datatre", "Solo orario", AETypePref.localtime, LocalTime.now()),

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

    //    //--chi può vedere la preferenza
    //    private AERole show;
    //
    //    //--usa un prefisso col codice della company
    //    private boolean companySpecifica;

    //--descrizione aggiuntiva
    private String note;


    AEPreferenza(String keyCode, String descrizione, AETypePref type, Object defaultValue) {
        this(keyCode, descrizione, type, defaultValue, VUOTA);
    }// fine del costruttore


    AEPreferenza(String keyCode, String descrizione, AETypePref type, Object defaultValue, String note) {
        this.setKeyCode(keyCode);
        this.setDescrizione(descrizione);
        this.setType(type);
        this.setNote(note);
        //        this.setCompanySpecifica(companySpecifica);
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


    public Object getDefaultValue() {
        return defaultValue;
    }


    public void setDefaultValue(Object defaultValue) {
        if (type == AETypePref.enumeration) {
            if (defaultValue instanceof String) {
                this.defaultValue = defaultValue;
            } else {
                this.defaultValue = ((AIEnum) defaultValue).getPref();
            }
        } else {
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
            for (AEPreferenza pref : AEPreferenza.values()) {
                pref.setPreferenzaService(preferenzaService);
            }
        }
    }


}
