package it.algos.vaadflow14.backend.service;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 06-apr-2020
 * Time: 16:43
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WebService extends AbstractService {

    /**
     * Tag aggiunto prima del titoloWiki (leggibile) della pagina per costruire il 'domain' completo
     */
    public static final String TAG_WIKI = "https://it.wikipedia.org/wiki/";

    public static final String UNKNOWN_HOST = "java.net.UnknownHostException: htp";

    //--codifica dei caratteri
    public static final String INPUT = "UTF8";

    public static final String TAG_TABLE_INIZIALE = "<table class=\"wikitable sortable\">";

    public static final String TAG_TABLE_BODY = "<tbody><tr>";

    private static final String TAG_INIZIALE = "http://";

    private static final String TAG_INIZIALE_SECURE = "https://";

    /**
     * Crea la connessione di tipo GET
     */
    public URLConnection getURLConnection(String domain) throws Exception {
        URLConnection urlConn = null;

        if (domain != null && domain.length() > 0) {
            domain = domain.replaceAll(SPAZIO, UNDERSCORE);
            urlConn = new URL(domain).openConnection();
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; it-it) AppleWebKit/418.9 (KHTML, like Gecko) Safari/419.3");
        }

        return urlConn;
    }


    /**
     * Request di tipo GET
     */
    public String getUrlRequest(URLConnection urlConn) throws Exception {
        String risposta;
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader readBuffer;
        StringBuilder textBuffer = new StringBuilder();
        String stringa;

        input = urlConn.getInputStream();
        inputReader = new InputStreamReader(input, INPUT);

        // read the request
        readBuffer = new BufferedReader(inputReader);
        while ((stringa = readBuffer.readLine()) != null) {
            textBuffer.append(stringa);
        }

        //--close all
        readBuffer.close();
        inputReader.close();
        input.close();

        risposta = textBuffer.toString();

        return risposta;
    }

    /**
     * Request di tipo GET. Legge la pagina intera. <br>
     * Accetta SOLO un urlDomain (indirizzo) completo <br>
     * Può essere un urlDomain generico di un sito web e restituisce il testo in formato html <br>
     * Può essere un urlDomain di una pagina wiki in lettura normale (senza API) e restituisce il testo in formato html <br>
     * Può essere un urlDomain che usa le API di Mediawiki e restituisce il testo in formato BSON <br>
     *
     * @param urlDomain completo
     *
     * @return risultato col testo grezzo in formato html oppure BSON
     */
    public String leggeWebTxt(final String urlDomain) {
        return legge(urlDomain).getResponse();
    }

    /**
     * Request di tipo GET. Legge la pagina intera. <br>
     * Accetta SOLO un urlDomain (indirizzo) completo <br>
     * Può essere un urlDomain generico di un sito web e restituisce il testo in formato html <br>
     * Può essere un urlDomain di una pagina wiki in lettura normale (senza API) e restituisce il testo in formato html <br>
     * Può essere un urlDomain che usa le API di Mediawiki e restituisce il testo in formato BSON <br>
     *
     * @param urlDomain completo
     *
     * @return risultato col testo grezzo in formato html oppure BSON
     */
    public AIResult legge(final String urlDomain) {
        AIResult result;
        String codiceSorgente;
        URLConnection urlConn;
        String tag = TAG_INIZIALE;
        String tag2 = TAG_INIZIALE_SECURE;

        try {
            String webUrl = urlDomain.startsWith(tag) || urlDomain.startsWith(tag2) ? urlDomain : tag2 + urlDomain;
            urlConn = getURLConnection(webUrl);
            codiceSorgente = getUrlRequest(urlConn);
            result = AResult.contenuto(codiceSorgente, urlDomain);
        } catch (Exception unErrore) {
            result = AResult.errato(unErrore.toString());
        }
        result.setUrlRequest(urlDomain);

        return result;
    }

    /**
     * Request di tipo GET. Legge il body della pagina. <br>
     * Accetta SOLO un urlDomain (indirizzo) completo <br>
     * Può essere un urlDomain generico di un sito web e restituisce il testo in formato html <br>
     * Può essere un urlDomain di una pagina wiki in lettura normale (senza API) e restituisce il testo in formato html <br>
     * Può essere un urlDomain che usa le API di Mediawiki e restituisce il testo in formato BSON <br>
     *
     * @param urlDomain completo
     *
     * @return risultato col body in formato html oppure BSON
     */
    public String leggeBodyWebTxt(final String urlDomain) {
        return leggeBodyWeb(urlDomain).getResponse();
    }

    /**
     * Request di tipo GET. Legge il body della pagina. <br>
     * Accetta SOLO un urlDomain (indirizzo) completo <br>
     * Può essere un urlDomain generico di un sito web e restituisce il testo in formato html <br>
     * Può essere un urlDomain di una pagina wiki in lettura normale (senza API) e restituisce il testo in formato html <br>
     * Può essere un urlDomain che usa le API di Mediawiki e restituisce il testo in formato BSON <br>
     *
     * @param urlDomain completo
     *
     * @return risultato col body in formato html oppure BSON
     */
    public AIResult leggeBodyWeb(final String urlDomain) {
        AIResult result = legge(urlDomain);
        String bodyText;

        if (result.isValido() && text.isValid(result.getResponse())) {
            bodyText = text.estrae(result.getResponse(), "<body>", "</body>");
            result.setResponse(bodyText);
        }

        return result;
    }

    /**
     * Request di tipo GET <br>
     * Sorgente completo di una pagina wiki <br>
     * Non usa le API di Mediawiki <br>
     * Elabora il wikiTitle per eliminare gli spazi vuoti <br>
     *
     * @param wikiTitleGrezzo da controllare per riempire gli spazi vuoti
     *
     * @return testo sorgente completo della pagina web in formato html
     */
    public String leggeWikiTxt(String wikiTitleGrezzo) {
        return leggeWiki(wikiTitleGrezzo).getResponse();
    }


    /**
     * Request di tipo GET <br>
     * Sorgente completo di una pagina wiki <br>
     * Non usa le API di Mediawiki <br>
     * Elabora il wikiTitle per eliminare gli spazi vuoti <br>
     *
     * @param wikiTitleGrezzo da controllare per riempire gli spazi vuoti
     *
     * @return testo sorgente completo della pagina web in formato html
     */
    public AIResult leggeWiki(final String wikiTitleGrezzo) {
        AIResult result;
        String wikiTitleElaborato = wikiTitleGrezzo.replaceAll(SPAZIO, UNDERSCORE);
        result = legge(TAG_WIKI + wikiTitleElaborato);

        result.setWikiTitle(wikiTitleGrezzo);
        result.setUrlRequest(TAG_WIKI + wikiTitleGrezzo);

        return result;
    }


    /**
     * Costruisce una stringa di testo coi titoli della Table per individuarla nel sorgente della pagina
     *
     * @param titoliTable per individuare una table
     *
     * @return stringa di tag per regex
     */
    public String costruisceTagTitoliTable(String[] titoliTable) {
        String testoTable = VUOTA;
        String tagIniTable = TAG_TABLE_INIZIALE;
        String tagBody = TAG_TABLE_BODY;
        String tagIni = "<th>";
        String tagEnd = "</th>";

        if (titoliTable != null && titoliTable.length > 0) {
            testoTable += tagIniTable;
            testoTable += tagBody;
            for (String titolo : titoliTable) {
                testoTable += tagIni;
                testoTable += titolo;
                testoTable += tagEnd;
            }
        }

        testoTable = text.levaCoda(testoTable, tagEnd);
        return testoTable;
    }


    /**
     * Request di tipo GET
     * Accetta un array di titoli della table
     *
     * @param sorgentePagina
     * @param titoliTable    per individuarla
     *
     * @return testo della table
     */
    public String estraeTableWiki(String sorgentePagina, String[] titoliTable) {
        String testoTable = null;
        String tagTitoli = VUOTA;
        String tagEndHeader = "</tr><tr>";
        String tagEndTable = "</tbody></table>";
        int posIniTable;
        int posEndHeader;
        int posEndTable;

        if (text.isValid(sorgentePagina)) {
            tagTitoli = costruisceTagTitoliTable(titoliTable);
        }// end of if cycle

        posIniTable = sorgentePagina.indexOf(tagTitoli);
        posEndHeader = sorgentePagina.indexOf(tagEndHeader, posIniTable) + tagEndHeader.length();
        posEndTable = sorgentePagina.indexOf(tagEndTable, posEndHeader);

        if (text.isValid(tagTitoli)) {
            testoTable = sorgentePagina.substring(posEndHeader, posEndTable);
        }// end of if cycle

        return testoTable;
    } // fine del metodo


    /**
     * Request di tipo GET
     * Accetta un array di titoli della table
     *
     * @param indirizzoWikiGrezzo della pagina
     * @param titoliTable         per individuarla
     *
     * @return testo della table
     */
    public String leggeTableWiki(String indirizzoWikiGrezzo, String[] titoliTable) {
        String testoTable = VUOTA;
        String sorgentePagina = leggeWikiTxt(indirizzoWikiGrezzo);

        if (text.isValid(sorgentePagina)) {
            testoTable = estraeTableWiki(sorgentePagina, titoliTable);
        }// end of if cycle

        return testoTable;
    } // fine del metodo


    /**
     * Request di tipo GET
     * Accetta un array di titoli della table
     *
     * @param indirizzoWikiGrezzo della pagina
     * @param titoliTable         per individuarla
     *
     * @return lista grezza di righe
     */
    public List<String> getRigheTableWiki(String indirizzoWikiGrezzo, String[] titoliTable) {
        List<String> lista = null;
        String[] righe = null;
        String sep = "</td></tr>";
        String tag = "<tr>";
        String testoTable = leggeTableWiki(indirizzoWikiGrezzo, titoliTable);
        String cella;

        if (testoTable != null && testoTable.length() > 0) {
            righe = testoTable.split(sep);
        }// end of if cycle

        if (righe != null) {
            lista = new ArrayList<>();
            for (String riga : righe) {
                if (text.isValid(riga)) {
                    cella = text.levaTesta(riga, tag);
                    lista.add(cella);
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        return lista;
    } // fine del metodo


    /**
     * Request di tipo GET
     * Accetta un array di titoli della table
     *
     * @param indirizzoWikiGrezzo della pagina
     * @param titoliTable         per individuarla
     *
     * @return lista grezza di righe
     */
    @Deprecated
    public LinkedHashMap<String, LinkedHashMap<String, String>> getMappaTableWiki(String indirizzoWikiGrezzo, String[] titoliTable) {
        LinkedHashMap<String, LinkedHashMap<String, String>> mappaTable = null;
        List<String> lista = getRigheTableWiki(indirizzoWikiGrezzo, titoliTable);
        String[] parti = null;
        String sep = "</td><td>";
        String tag = "<td>";
        String key;
        LinkedHashMap<String, String> mappaRiga;
        String cella;

        if (lista != null && lista.size() > 0) {
            mappaTable = new LinkedHashMap<>();
            for (String riga : lista) {
                parti = riga.split(sep);
                key = text.levaTesta(parti[0], tag);

                mappaRiga = new LinkedHashMap<>();
                if (parti != null && parti.length > 0) {
                    for (int k = 0; k < parti.length; k++) {
                        mappaRiga.put(titoliTable[k], text.levaTesta(parti[k], tag));
                    }// end of for cycle
                }// end of if cycle
                mappaTable.put(key, mappaRiga);
            }// end of for cycle
        }// end of if cycle

        return mappaTable;
    } // fine del metodo


    /**
     * Request di tipo GET
     * Accetta un array di titoli della table
     *
     * @param indirizzoWikiGrezzo della pagina
     * @param titoliTable         per individuarla
     *
     * @return lista grezza di righe
     */
    public List<List<String>> getMatriceTableWiki(String indirizzoWikiGrezzo, String[] titoliTable) {
        List<List<String>> matriceTable = null;
        List<String> lista = getRigheTableWiki(indirizzoWikiGrezzo, titoliTable);
        String[] parti = null;
        String sep = "</td><td>";
        String tag = "<td>";
        List<String> listaRiga;

        if (lista != null && lista.size() > 0) {
            matriceTable = new ArrayList<>();
            for (String riga : lista) {
                parti = riga.split(sep);
                listaRiga = new ArrayList<>();
                if (parti != null && parti.length > 0) {
                    listaRiga.add(text.levaTesta(parti[0], tag));
                    for (int k = 1; k < parti.length; k++) {
                        listaRiga.add(parti[k]);
                    }// end of for cycle
                }// end of if cycle
                matriceTable.add(listaRiga);
            }// end of for cycle
        }// end of if cycle

        return matriceTable;
    } // fine del metodo

}// end of class
