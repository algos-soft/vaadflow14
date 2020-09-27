package it.algos.vaadflow14.backend.packages.preferenza;

import it.algos.vaadflow14.backend.enumeration.AETypePref;
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

    enumeration("daCancellare2", "Prova enumeration", AETypePref.enumeration, "Alfa,beta;Alfa"),

    mailTo("email", "Indirizzo email", AETypePref.email, "gac@algos.it", "Email di default a cui spedire i log di posta"),

    maxRigheGrid("maxRigheGrid", "Righe massime della griglia semplice", AETypePref.integer, 20, "Numero di elementi oltre il quale scatta la pagination automatica della Grid (se attiva)"),

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

    //--Valore java da convertire in byte[] a seconda del type
    private Object value;

    //--Link injettato da un metodo static
    private APreferenzaService preferenzaService;

    //    //--chi può vedere la preferenza
    //    private AERole show;
    //
    //    //--usa un prefisso col codice della company
    //    private boolean companySpecifica;

    //--descrizione aggiuntiva
    private String note;


    AEPreferenza(String keyCode, String descrizione, AETypePref type, Object value) {
        this(keyCode, descrizione, type, value, VUOTA);
    }// fine del costruttore


    AEPreferenza(String keyCode, String descrizione, AETypePref type, Object value, String note) {
        this.setKeyCode(keyCode);
        this.setDescrizione(descrizione);
        this.setType(type);
        this.setNote(note);
        //        this.setShow(show);
        //        this.setCompanySpecifica(companySpecifica);
        this.setValue(value);
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


    public Object getValue() {
        return value;
    }


    public void setValue(Object value) {
        this.value = value;
    }


    public boolean is() {
        boolean status = false;
        Preferenza preferenza = null;
        Object javaValue;

        if (type != AETypePref.bool) {
            return false;
        }

        if (preferenzaService == null) {
            return false;
        }

        preferenza = preferenzaService.findByKey(keyCode);
        javaValue = preferenza != null ? type.bytesToObject(preferenza.getValue()) : null;
        status = (javaValue != null && javaValue instanceof Boolean) ? (boolean) javaValue : false;

        return status;
    }


    public String getNote() {
        return note;
    }


    public void setNote(String note) {
        this.note = note;
    }


    @Component
    public static class MeseServiceInjector {

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
