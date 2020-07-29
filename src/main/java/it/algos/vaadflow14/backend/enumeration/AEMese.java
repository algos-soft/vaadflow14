package it.algos.vaadflow14.backend.enumeration;

import it.algos.vaadflow14.backend.service.ATextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public enum AEMese {

    gennaio("gennaio", 31, 31, "gen"),

    febbraio("febbraio", 28, 29, "feb"),

    marzo("marzo", 31, 31, "mar"),

    aprile("aprile", 30, 30, "apr"),

    maggio("maggio", 31, 31, "mag"),

    giugno("giugno", 30, 30, "giu"),

    luglio("luglio", 31, 31, "lug"),

    agosto("agosto", 31, 31, "ago"),

    settembre("settembre", 30, 30, "set"),

    ottobre("ottobre", 31, 31, "ott"),

    novembre("novembre", 30, 30, "nov"),

    dicembre("dicembre", 31, 31, "dic");


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ATextService text;


    String nome;

    int giorni;

    int giorniBisestili;

    String sigla;


    /**
     * Costruttore interno dell'Enumeration <br>
     */
    AEMese(String nome, int giorni, int giorniBisestili, String sigla) {
        this.nome = nome;
        this.giorni = giorni;
        this.giorniBisestili = giorniBisestili;
        this.sigla = sigla;
    }


    /**
     * Numero di giorni di ogni mese <br>
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     * @param anno            l'anno di riferimento (per sapere se è bisestile)
     *
     * @return Numero di giorni del mese
     */
    public static int getGiorni(int numMeseDellAnno, int anno) {
        int giorniDelMese = 0;
        AEMese mese = getMese(numMeseDellAnno);

        if (mese != null) {
            if (!Year.of(anno).isLeap()) {
                giorniDelMese = mese.giorni;
            } else {
                giorniDelMese = mese.giorniBisestili;
            }
        }

        return giorniDelMese;
    }


    /**
     * Mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     *
     * @return Mese
     */
    public static AEMese getMese(int numMeseDellAnno) {
        AEMese mese = null;

        if (numMeseDellAnno > 0 && numMeseDellAnno < 13) {
            numMeseDellAnno = numMeseDellAnno - 1;
            for (AEMese meseTmp : AEMese.values()) {
                if (meseTmp.ordinal() == numMeseDellAnno) {
                    mese = meseTmp;
                }
            }
        }

        return mese;
    }


    /**
     * Mese
     *
     * @param nomeBreveLungo Nome breve o lungo del mese
     *
     * @return Mese
     */
    public static AEMese getMese(String nomeBreveLungo) {
        AEMese mese = null;
        String nomeBreveLungoMinuscolo;

        if (nomeBreveLungo != null && !nomeBreveLungo.equals("")) {
            nomeBreveLungoMinuscolo = nomeBreveLungo.toLowerCase();
            for (AEMese meseTmp : AEMese.values()) {
                if (meseTmp.sigla.equals(nomeBreveLungoMinuscolo) || meseTmp.nome.equals(nomeBreveLungoMinuscolo)) {
                    mese = meseTmp;
                }
            }
        }

        return mese;
    }


    // l'anno parte da gennaio che è il numero 1
    private static String getMese(int ord, boolean flagBreve) {
        String nome = "";
        AEMese mese = null;

        mese = getMese(ord);
        if (mese != null) {
            if (flagBreve) {
                nome = mese.sigla;
            } else {
                nome = mese.nome;
            }
        }

        return nome;
    }


    /**
     * Numero del mese nell'anno
     *
     * @param nomeBreveLungo L'anno parte da gennaio che è il mese numero 1
     *
     * @return Numero del mese
     */
    public static int getOrder(String nomeBreveLungo) {
        int numMeseDellAnno = 0;
        AEMese mese = getMese(nomeBreveLungo);

        if (mese != null) {
            numMeseDellAnno = mese.ordinal();
            numMeseDellAnno = numMeseDellAnno + 1;
        }

        return numMeseDellAnno;
    }


    /**
     * Elenco di tutti i nomi in forma breve
     *
     * @return Stringa dei nomi brevi separati da virgola
     */
    public static String getAllShortString() {
        String stringa = "";
        String sep = ", ";

        for (AEMese mese : AEMese.values()) {
            stringa += mese.sigla;
            stringa += sep;
        }
        stringa = stringa.substring(0, stringa.length() - sep.length());

        return stringa;
    }


    /**
     * Elenco di tutti i nomi in forma completa
     *
     * @return Stringa dei nomi completi separati da virgola
     */
    public static String getAllLongString() {
        String stringa = "";
        String sep = ", ";

        for (AEMese mese : AEMese.values()) {
            stringa += mese.nome;
            stringa += sep;
        }
        stringa = stringa.substring(0, stringa.length() - sep.length());

        return stringa;
    }


    /**
     * Elenco di tutti i nomi in forma breve
     *
     * @return Array dei nomi brevi
     */
    public static List<String> getAllShortList() {
        List<String> lista = new ArrayList<String>();

        for (AEMese mese : AEMese.values()) {
            lista.add(mese.sigla);
        }

        return lista;
    }


    /**
     * Elenco di tutti i nomi in forma completa
     *
     * @return Array dei nomi completi
     */
    public static List<String> getAllLongList() {
        List<String> lista = new ArrayList<String>();

        for (AEMese mese : AEMese.values()) {
            lista.add(mese.nome);
        }

        return lista;
    }


    /**
     * Nome completo del mese
     *
     * @param numMeseDellAnno L'anno parte da gennaio che è il mese numero 1
     *
     * @return Nome breve del mese
     */
    public static String getLong(int numMeseDellAnno) {
        return getMese(numMeseDellAnno, false);
    }


    @Override
    public String toString() {
        return nome;
    }


    public int getOrd() {
        return this.ordinal() + 1;
    }


    public String getNome() {
        return nome;
    }


    public String getSigla() {
        return sigla;
    }


    public int getGiorni() {
        return giorni;
    }


    public int getGiorniBisestili() {
        return giorniBisestili;
    }


    public void setText(ATextService text) {
        this.text = text;
    }


    @Component
    public static class MeseServiceInjector {

        @Autowired
        private ATextService text;


        @PostConstruct
        public void postConstruct() {
            for (AEMese mese : AEMese.values()) {
                mese.setText(text);
            }
        }

    }
}
