package it.algos.vaadflow14.backend.application;

import com.vaadin.flow.component.icon.VaadinIcon;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mar, 28-apr-2020
 * Time: 18:11
 *
 * <p>Classe statica (astratta) per le costanti generali dell'applicazione. <br>
 * Le costanti (static) sono uniche per tutta l'applicazione <br>
 * Il valore delle costanti (final) è immutabile una volta regolato qui <br>
 * Per omogeneità è meglio usare 'static final' (piuttosto che l'equivalente 'final static') <br>
 * Prefisso KEY per la key delle mappe <br>
 * Prefisso TAG per gli identificatori delle View e delle classi <br>
 * Prefisso BOT per i nomi dei bottoni <br>
 * Prefisso BOT_LABEL per i testi dei bottoni <br>
 * Prefisso FLAG e USA per variabili booleane <br>
 * Prefisso SHOW per abilitare la presentazione dei moduli nel menu <br>
 */
public abstract class FlowCost {

    public static final int MAX = 2147483647;

    public static final String NAME_VAADFLOW = "vaadflow14";

    public static final String PATH_RISORSE = "src/main/resources/META-INF/resources/";

    public static final String PATH_WIKI = "https://it.wikipedia.org/wiki/";

    public static final String DEVELOPER_COMPANY = "Algos® ";

    public static final Locale LOCALE = Locale.ITALIAN;

    public static final String TAG_FLOW_DATA = "flowdata";

    public static final String VUOTA = "";

    public static final String VIRGOLA = ",";

    public static final String VIRGOLA_SPAZIO = ", ";

    public static final String SPAZIO = " ";

    public static final String HTLM_SPAZIO = "&nbsp;";

    public static final String REF = "<ref>";

    public static final String DOPPIO_SPAZIO = SPAZIO + SPAZIO;

    public static final String TRATTINO = "-";

    public static final String UNDERSCORE = "_";

    public static final String SLASH = "/";

    public static final String PIPE = "|";

    public static final String ESCLAMATIVO = "!";

    public static final String DOPPIO_ESCLAMATIVO = ESCLAMATIVO + ESCLAMATIVO;

    public static final String SEP = " - ";

    public static final String UGUALE = " = ";

    public static final String FORWARD = " -> ";

    public static final String PUNTO = ".";

    public static final String PUNTO_VIRGOLA = ";";

    public static final String DUE_PUNTI = ":";

    public static final String DUE_PUNTI_SPAZIO = DUE_PUNTI + SPAZIO;

    public static final String TRE_PUNTI = "...";

    public static final String A_CAPO = "\n";

    public static final String TAB = "\t";

    public static final String APICE = "\\\"";

    public static final String A_CAPO_REGEX = "\\\\n";

    public static final String PIPE_REGEX = "\\|";

    public static final String DOPPIO_PIPE_REGEX = PIPE_REGEX + PIPE_REGEX;

    public static final String SUFFIX_ENTITY_LOGIC = "Logic";

    public static final String SUFFIX_ENTITY_SERVICE = "Service";

    public static final String SUFFIX_FORM = "Form";

    public static final String SUFFIX_LIST = "List";

    public static final String SUFFIX_VIEW = "View";

    /**
     * The constant ROUTE_NAME.
     */
    public static final String ROUTE_NAME_LOGIN = "Login";

    public static final String ROUTE_NAME_LOGIN_ERROR = ROUTE_NAME_LOGIN + "?error";

    public static final String ROUTE_NAME_ABOUT = "About";

    public static final String ROUTE_NAME_HOME = "Home";

    public static final String ROUTE_NAME_GENERIC_VIEW = "vista";

    public static final String ROUTE_NAME_GENERIC_LIST = "list";

    public static final String ROUTE_NAME_GENERIC_FORM = "form";

    public static final String TAG_LOGIN = "login";

    public static final String TAG_LOGOUT = "logout";

    public static final String TAG_WIZ = "wizard";

    public static final String TAG_PREFERENZA = "preferenza";

    public static final String KEY_NULL = "null";

    public static final String KEY_ROUTE_TYPE = "type";

    //    public static final String KEY_ROUTE_LIST = "list";
    //
    //    public static final String KEY_ROUTE_FORM = "form";

    public static final String KEY_BEAN_CLASS = "beanClass";

    public static final String KEY_BEAN_ENTITY = "beanID";

    public static final String KEY_FORM_TYPE = "formType";

    public static final String KEY_SERVICE_CLASS = "serviceClass";

    public static final String KEY_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    public static final String KEY_SESSION_LOGIN = "SESSION_LOGIN";

    public static final String KEY_SESSION_MOBILE = "sessionMobile";

    /**
     * The constant PROPERTY_SERIAL.
     */
    public static final String PROPERTY_SERIAL = "serialVersionUID";

    public static final String PROPERTY_CREAZIONE = "creazione";

    public static final String PROPERTY_MODIFICA = "modifica";

    public static final String PROPERTY_NOTE = "note";

    /**
     * The constant DEFAULT_ICON.
     */
    public static final VaadinIcon DEFAULT_ICON = VaadinIcon.ASTERISK;

    public static final String DEFAULT_IMAGE = "frontend/images/medal.ico";

    /**
     * The constant START_DATE.
     */
    public static final LocalDate START_DATE = LocalDate.of(1970, 1, 1);

    /**
     * The constant TAG_EM.
     */
    public static final String TAG_EM = "em";

    public static final String TAG_GENERIC_SERVICE = "EntityService";

    public static final String TAG_GENERIC_LOGIC = "EntityLogic";

    public static final String TAG_GENERIC_VIEW = "view";

    public static final String KEY_BUTTON_DELETE_ALL = "deleteAll";

    public static final String KEY_BUTTON_RESET = "reset";

    public static final String KEY_BUTTON_NEW = "new";

    public static final String KEY_BUTTON_ANNULLA = "annulla";

    public static final String KEY_BUTTON_CONFERMA = "conferma";

    public static final String KEY_BUTTON_REGISTRA = "save";

    public static final String KEY_BUTTON_DELETE = "delete";

    public static final String QUADRA_INI = "[";

    public static final String QUADRE_INI = QUADRA_INI + QUADRA_INI;

    public static final String QUADRA_END = "]";

    public static final String QUADRE_END = QUADRA_END + QUADRA_END;

    public static final String VERO = "true";

    public static final String FALSO = "false";


    /**
     * tag per la singola parentesi tonda di apertura
     */
    public static final String TONDA_INI = "(";

    /**
     * tag per la singola parentesi tonda di chiusura
     */
    public static final String TONDA_END = ")";

    /**
     * tag per la singola graffa di apertura
     */
    public static final String GRAFFA_INI = "{";

    /**
     * tag per le doppie graffe di apertura
     */
    public static final String DOPPIE_GRAFFE_INI = GRAFFA_INI + GRAFFA_INI;

    /**
     * tag per la singola graffa di apertura
     */
    public static final String GRAFFA_END = "}";

    /**
     * tag per le doppie graffe di chiusura
     */
    public static final String DOPPIE_GRAFFE_END = GRAFFA_END + GRAFFA_END;

    /**
     * tag per la singola graffa di apertura
     */
    public static final String GRAFFA_INI_REGEX = "\\{";

    /**
     * tag per le doppie graffe di apertura
     */
    public static final String DOPPIE_GRAFFE_INI_REGEX = GRAFFA_INI_REGEX + GRAFFA_INI_REGEX;

    /**
     * tag per la singola graffa di apertura
     */
    public static final String GRAFFA_END_REGEX = "\\}";

    /**
     * tag per le doppie graffe di chiusura
     */
    public static final String DOPPIE_GRAFFE_END_REGEX = GRAFFA_END_REGEX + GRAFFA_END_REGEX;

    //--chiavi mappa costruzione giorni
    public static final String PRIMO_GIORNO_MESE = "1º";

    public static final String KEY_MAPPA_GIORNI_MESE_NUMERO = "meseNumero";

    public static final String KEY_MAPPA_GIORNI_MESE_TESTO = "meseTesto";

    public static final String KEY_MAPPA_GIORNI_NORMALE = "normale";

    public static final String KEY_MAPPA_GIORNI_BISESTILE = "bisestile";

    public static final String KEY_MAPPA_GIORNI_NOME = "nome";

    public static final String KEY_MAPPA_GIORNI_TITOLO = "titolo";

    public static final String KEY_MAPPA_GIORNI_MESE_MESE = "meseMese";

    //--chiavi mappa eventi
    public static final String KEY_MAPPA_EVENTO_AZIONE = "eventoAzione";

    public static final String TXT_SUFFIX = ".txt";

    public static final String XML_SUFFIX = ".xml";

    public static final String JAVA_SUFFIX = ".java";

    public static final String DIR_APPLICATION = "application/";

    public static final String DIR_PACKAGES = "packages/";

    //--fields tipici
    public static final String FIELD_COMPANY = "company";

    public static final String FIELD_NOTE = "note";

    public static final String FIELD_CODE = "code";

    public static final String FIELD_TYPE = "type";

    public static final String FIELD_ORDINE = "ordine";

    public static final String FIELD_NOME = "nome";

    public static final String FIELD_VALUE = "value";

    public static final String FIELD_INDEX = "rowIndex";

    public static final String BOOL_FIELD = "Valore (booleano)";

    public static final String DATE_PICKER_FIELD = "Valore (giorno)";

    public static final String TIME_PICKER_FIELD = "Valore (orario)";

    public static final String DATE_TIME_PICKER_FIELD = "Valore (data e ora)";

    public static final String ENUM_FIELD_NEW = "Valore (x,y,z;y)";

    public static final String ENUM_FIELD_SHOW = "Valore (enumeration)";

    public static final String MONGO_FIELD_USER = "user";

    public static final String[] esclusiAll = {PROPERTY_SERIAL, PROPERTY_NOTE, PROPERTY_CREAZIONE, PROPERTY_MODIFICA};

    public static final List<String> ESCLUSI_ALL = Arrays.asList(esclusiAll);


    //--Costanti usate per identificare alcune company
    public static final String COMPANY_ALGOS = "algos";

    public static final String COMPANY_DEMO = "demo";

    public static final String COMPANY_TEST = "test";

    public static final String LUMO_PRIMARY_COLOR = "#1676F3";  // non riesco a recuperarlo dal context allora visto che so qual è lo ridefinisco qui

    public static final String PREF_USA_DEBUG = "usaDebug";

    public static final String PREF_USA_SEARCH_CLEAR = "usaSearchClearButton";

    public static final String PREF_USA_BANDIERE_STATI = "usaBandiereStati";

    public static final String PREF_USA_GRID_MAIUSCOLA = "usaGridHeaderMaiuscola";

    public static final String PREF_USA_FORM_MAIUSCOLA = "usaFormFieldMaiuscola";

    public static final String PREF_USA_SEARCH_SENSITIVE = "usaSearchCaseSensitive";

    public static final String PREF_ICONA_EDIT = "iconaEdit";

    public static final String PREF_EMAIL = "email";

    public static final String PREF_MAX_RIGHE_GRID = "maxRigheGrid";

    public static final String PREF_MAX_RADIO = "maxEnumRadio";

    public static final String LINE_HEIGHT = "lineHeight";

    public static final double ICON_WIDTH = 3.0;



}
