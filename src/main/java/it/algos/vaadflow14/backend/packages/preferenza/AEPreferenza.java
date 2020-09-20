package it.algos.vaadflow14.backend.packages.preferenza;

import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.enumeration.AETypePref;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 18-set-2020
 * Time: 15:41
 */
public enum AEPreferenza {
    usaDebug(FlowCost.USA_DEBUG, "Flag generale di debug", AETypePref.bool, false, "Ce ne possono essere di specifici, validi solo se questo è vero"),

    pippoz("daCancellare", "Prova preferenza testo", AETypePref.string, "Alfa"),

    mailTo("email", "Indirizzo email", AETypePref.email, "gac@algos.it","Email di default a cui spedire i log di posta"),

    ;

    //--codice di riferimento. Se è usaCompany = true, DEVE contenere anche il code della company come prefisso.
    private String code;

    //--descrizione breve ma comprensibile. Ulteriori (eventuali) informazioni nel campo 'note'
    private String descrizione;

    //--tipologia di dato da memorizzare.
    //--Serve per convertire (nei due sensi) il valore nel formato byte[] usato dal mongoDb
    private AETypePref type;

    //--Valore java da convertire in byte[] a seconda del type
    private Object value;

    //    //--chi può vedere la preferenza
    //    private AERole show;
    //
    //    //--usa un prefisso col codice della company
    //    private boolean companySpecifica;

    //--descrizione aggiuntiva
    private String note;


    AEPreferenza(String code, String descrizione, AETypePref type, Object value) {
        this(code, descrizione, type, value, VUOTA);
    }// fine del costruttore


    AEPreferenza(String code, String descrizione, AETypePref type, Object value, String note) {
        this.setCode(code);
        this.setDescrizione(descrizione);
        this.setType(type);
        this.setNote(note);
        //        this.setShow(show);
        //        this.setCompanySpecifica(companySpecifica);
        this.setValue(value);
    }// fine del costruttore


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
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


    public String getNote() {
        return note;
    }


    public void setNote(String note) {
        this.note = note;
    }


}
