package it.algos.vaadflow14.backend.service;

import it.algos.vaadflow14.backend.wrapper.WrapDueStringhe;
import it.algos.vaadflow14.backend.wrapper.WrapQuattroStringhe;
import it.algos.vaadflow14.backend.wrapper.WrapTreStringhe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URLEncoder;
import java.util.*;

import static it.algos.vaadflow14.backend.application.FlowCost.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: gio, 07-mag-2020
 * Time: 07:49
 * <p>
 * Importazione di pagine  da Wikipedia <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private AWikiService wiki; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AWikiService extends AAbstractService {

    public static final String ENCODE = "UTF-8";

    public static final String PAGINA_ISO_1 = "ISO 3166-1";

    public static final String PAGINA_ISO_2 = "ISO 3166-2:IT";

    public static final String PAGINA_PROVINCE = "Regioni_d'Italia";

    public static final String PAGINA_ISO_1_NUMERICO = "ISO 3166-1 numerico";

    public static final String PAGES = "pages";

    public static final String QUERY = "query";

    public static final String REVISIONS = "revisions";

    public static final String SLOTS = "slots";

    public static final String MAIN = "main";

    public static final String CONTENT = "content";

    private static final String WIKI_URL = "https://it.wikipedia.org/w/api.php?&format=json&formatversion=2&action=query&rvslots=main&prop=info|revisions&rvprop=content|ids|flags|timestamp|user|userid|comment|size&titles=";

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AWebService web;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ATextService text;


//    /**
//     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
//     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
//     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
//     */
//    @Autowired
//    public RegioneLogic regioneLogic;


    /**
     * Estrae una wikitable da una pagina wiki <br>
     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
     *
     * @return lista di valori per ogni riga significativa della wikitable
     */
    public List<List<String>> getStati() {
        List<List<String>> listaTable = new ArrayList<>();
        List<List<String>> listaGrezza = getTable(PAGINA_ISO_1);
        Map<String, String> mappa = getTableStatiNumerico();
        String sep = DOPPIO_PIPE_REGEX;
        String[] parti = null;
        List<String> riga = null;
        String numerico = VUOTA;
        String nome = VUOTA;
        String alfatre = VUOTA;
        String alfadue = VUOTA;
        String locale = VUOTA;

        if (listaGrezza != null && listaGrezza.size() > 1) {
            for (List<String> rigaGrezza : listaGrezza) {
                riga = new ArrayList<>();
                if (rigaGrezza.size() == 3 || rigaGrezza.size() == 4) {
                    parti = rigaGrezza.get(2).split(sep);
                    if (parti.length == 4) {
                        nome = fixNomeStato(mappa.get(parti[0].trim()));
                        numerico = parti[0].trim();
                        alfatre = parti[1].trim();
                        alfadue = parti[2].trim();
                        locale = text.setNoQuadre(parti[3].trim());
                        riga.add(nome);
                        riga.add(numerico);
                        riga.add(alfatre);
                        riga.add(alfadue);
                        riga.add(locale);
                        listaTable.add(riga);
                    }
                }
            }
        }

        return listaTable;
    }


    /**
     * Estrae una wikitable da una pagina wiki <br>
     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
     *
     * @return lista di valori per ogni riga significativa della wikitable
     */
    public Map<String, String> getTableStatiNumerico() {
        Map<String, String> mappa = new HashMap<>();
        List<List<String>> listaGrezza = getTable(PAGINA_ISO_1_NUMERICO, 1, 1);
        List<String> riga;
        String[] partiRiga = null;
        String sep = DOPPIO_PIPE_REGEX;
        String codice;
        String paese;

        if (listaGrezza != null && listaGrezza.size() > 1) {
            for (List<String> rigaGrezza : listaGrezza) {
                partiRiga = rigaGrezza.get(0).split(sep);
                if (partiRiga.length == 2) {
                    codice = partiRiga[0].trim();
                    if (codice.startsWith(PIPE)) {
                        codice = text.levaTesta(codice, PIPE);
                    }
                    paese = text.setNoQuadre(partiRiga[1]).trim();
                    mappa.put(codice, paese);
                }
            }
        }

        return mappa;
    }


    //    /**
    //     * Estrae una wikitable da una pagina wiki <br>
    //     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
    //     *
    //     * @return lista di valori per ogni riga significativa della wikitable
    //     */
    //    public List<List<String>> getRegioni() {
    //        List<List<String>> listaTable = new ArrayList<>();
    //        List<List<String>> listaGrezza = getTable(PAGINA_ISO_2);
    //        List<String> riga;
    //        String iso;
    //        String nome;
    //        String sigla;
    //
    //        if (listaGrezza != null && listaGrezza.size() == 20) {
    //            for (List<String> rigaGrezza : listaGrezza) {
    //                riga = new ArrayList<>();
    //                iso = rigaGrezza.get(0);
    //                nome = rigaGrezza.get(1);
    //                iso = fixCodice(iso);
    //                nome = fixNomeRegione(nome);
    //                sigla = getSiglaDaNome(nome);//@todo Funzionalità ancora da implementare
    //                riga.add(nome);
    //                riga.add(iso);
    //                riga.add(sigla);
    //                listaTable.add(riga);
    //            }
    //        }
    //
    //        return listaTable;
    //    }


    //    //@todo Funzionalità ancora da implementare
    //    public String getSiglaDaNome(String nome) {
    //        String sigla = VUOTA;
    //
    //        if (text.isValid(nome)) {
    //            switch (nome) {
    //                case "Abruzzo":
    //                    sigla = "ABR";
    //                    break;
    //                case "Basilicata":
    //                    sigla = "BAS";
    //                    break;
    //                case "Calabria":
    //                    sigla = "CAL";
    //                    break;
    //                case "Campania":
    //                    sigla = "CAM";
    //                    break;
    //                case "Emilia-Romagna":
    //                    sigla = "EMR";
    //                    break;
    //                case "Friuli-Venezia Giulia":
    //                    sigla = "FVG";
    //                    break;
    //                case "Lazio":
    //                    sigla = "LAZ";
    //                    break;
    //                case "Liguria":
    //                    sigla = "LIG";
    //                    break;
    //                case "Lombardia":
    //                    sigla = "LOM";
    //                    break;
    //                case "Marche":
    //                    sigla = "MAR";
    //                    break;
    //                case "Molise":
    //                    sigla = "MOL";
    //                    break;
    //                case "Piemonte":
    //                    sigla = "PNM";
    //                    break;
    //                case "Puglia":
    //                    sigla = "PUG";
    //                    break;
    //                case "Sardegna":
    //                    sigla = "SAR";
    //                    break;
    //                case "Sicilia":
    //                    sigla = "SIC";
    //                    break;
    //                case "Toscana":
    //                    sigla = "TOS";
    //                    break;
    //                case "Trentino-Alto Adige":
    //                    sigla = "TAA";
    //                    break;
    //                case "Umbria":
    //                    sigla = "UMB";
    //                    break;
    //                case "Valle d'Aosta":
    //                    sigla = "VAO";
    //                    break;
    //                case "Veneto":
    //                    sigla = "VEN";
    //                    break;
    //                default:
    //                    logger.warn("Switch - caso non definito", this.getClass(), "getSiglaDaNome");
    //                    break;
    //            }
    //
    //        }
    //
    //        return sigla;
    //    }
    //
    //
    //    //@todo Funzionalità ancora da implementare
    //    public String getRegioneDaSigla(String sigla) {
    //        String nome = VUOTA;
    //
    //        if (text.isValid(sigla)) {
    //            switch (sigla) {
    //                case "ABR":
    //                    nome = "Abruzzo";
    //                    break;
    //                case "BAS":
    //                    nome = "Basilicata";
    //                    break;
    //                case "CAL":
    //                    nome = "Calabria";
    //                    break;
    //                case "CAM":
    //                    nome = "Campania";
    //                    break;
    //                case "EMR":
    //                    nome = "Emilia-Romagna";
    //                    break;
    //                case "FVG":
    //                    nome = "Friuli-Venezia Giulia";
    //                    break;
    //                case "LAZ":
    //                    nome = "Lazio";
    //                    break;
    //                case "LIG":
    //                    nome = "Liguria";
    //                    break;
    //                case "LOM":
    //                    nome = "Lombardia";
    //                    break;
    //                case "MAR":
    //                    nome = "Marche";
    //                    break;
    //                case "MOL":
    //                    nome = "Molise";
    //                    break;
    //                case "PNM":
    //                    nome = "Piemonte";
    //                    break;
    //                case "PUG":
    //                    nome = "Puglia";
    //                    break;
    //                case "SAR":
    //                    nome = "Sardegna";
    //                    break;
    //                case "SIC":
    //                    nome = "Sicilia";
    //                    break;
    //                case "TOS":
    //                    nome = "Toscana";
    //                    break;
    //                case "TAA":
    //                    nome = "Trentino-Alto Adige";
    //                    break;
    //                case "UMB":
    //                    nome = "Umbria";
    //                    break;
    //                case "VAO":
    //                    nome = "Valle d'Aosta";
    //                    break;
    //                case "VEN":
    //                    nome = "Veneto";
    //                    break;
    //                default:
    //                    logger.warn("Switch - caso non definito", this.getClass(), "getRegioneDaSigla");
    //                    break;
    //            }
    //
    //        }
    //
    //        return nome;
    //    }


//    /**
//     * Estrae una wikitable da una pagina wiki <br>
//     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
//     *
//     * @return lista di valori per ogni riga significativa della wikitable
//     */
//    public List<List<String>> getProvince() {
//        List<List<String>> listaTable = new ArrayList<>();
//        List<List<String>> listaGrezza = getTable(PAGINA_PROVINCE, 1, 1);
//        List<String> listaProvinceDellaRegione;
//        List<String> riga;
//        Regione regione;
//        String siglaRegione;
//        String nomeRegione;
//        String testoRiga;
//
//        //--nella tabella su wiki c'è una riga in più di riepilogo che non interessa
//        if (listaGrezza != null && listaGrezza.size() == 21) {
//            for (List<String> rigaGrezza : listaGrezza.subList(0, 20)) {
//                //                regione = rigaGrezza.get(1);
//                siglaRegione = fixRegione(rigaGrezza.get(0));
//                regione = regioneLogic.findBySigla(siglaRegione);
//                nomeRegione = regione != null ? regione.getNome() : VUOTA;
//                testoRiga = rigaGrezza.get(5);
//                listaProvinceDellaRegione = getListaProvince(testoRiga);
//                for (String provincia : listaProvinceDellaRegione) {
//                    riga = new ArrayList<>();
//                    riga.add(provincia);
//                    riga.add(provincia);//@todo PROVVISORIO
//                    riga.add(nomeRegione);
//                    listaTable.add(riga);
//                }
//            }
//        }
//
//        return listaTable;
//    }


//    private List<String> getListaProvince(String testoRiga) {
//        List<String> lista = new ArrayList<>();
//        String tag = "]]";
//        String[] parti = testoRiga.split(tag);
//        int pos = 0;
//        String provincia = VUOTA;
//
//        for (String testo : parti) {
//            if (testo.contains(PIPE)) {
//                pos = testo.lastIndexOf(PIPE) + 1;
//                provincia = testo.substring(pos, testo.length());
//                if (text.isValid(provincia)) {
//                    lista.add(provincia.trim());
//                }
//            }
//        }
//
//        return lista;
//    }


    /**
     * Estrae una wikitable da una pagina wiki <br>
     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return lista di valori per ogni riga significativa della wikitable
     */
    public List<List<String>> getTable(String wikiTitle) {
        return getTable(wikiTitle, 1, 2);
    }


    /**
     * Estrae una wikitable da una pagina wiki <br>
     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return lista di valori per ogni riga significativa della wikitable
     */
    public List<List<String>> getTable(String wikiTitle, int posTabella) {
        return getTable(wikiTitle, posTabella, 2);
    }


    /**
     * Estrae una wikitable da una pagina wiki <br>
     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
     *
     * @param wikiTitle  della pagina wiki
     * @param posTabella della wikitable nella pagina se ce ne sono più di una
     *
     * @return lista di valori per ogni riga significativa della wikitable
     */
    public List<List<String>> getTable(String wikiTitle, int posTabella, int rigaIniziale) {
        List<List<String>> listaTable = new ArrayList<>();
        List<String> listaRiga;
        String[] righeTable = null;
        String testoRigaSingola;
        String[] partiRiga = null;
        String tagTable = "\\|-";
        String tag = A_CAPO;
        String testoTable = leggeTable(wikiTitle, posTabella);

        if (text.isValid(testoTable)) {
            righeTable = testoTable.split(tagTable);
        }

        if (righeTable != null && righeTable.length > 2) {
            for (int k = rigaIniziale; k < righeTable.length; k++) {
                testoRigaSingola = righeTable[k];
                partiRiga = testoRigaSingola.split(tag);
                if (partiRiga != null && partiRiga.length > 0) {
                    listaRiga = new ArrayList<>();
                    for (String value : partiRiga) {
                        if (text.isValid(value)) {
                            if (value.startsWith(PIPE)) {
                                value = text.levaTesta(value, PIPE);
                            }
                            listaRiga.add(value.trim());
                        }
                    }
                    listaTable.add(listaRiga);
                }
            }
        }

        return listaTable;
    }


    /**
     * Legge una wikitable da una pagina wiki <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return testo della wikitable
     */
    public String leggeTable(String wikiTitle) {
        return leggeTable(wikiTitle, 1);
    }


    /**
     * Legge una wikitable da una pagina wiki <br>
     *
     * @param wikiTitle della pagina wiki
     * @param pos       della wikitable nella pagina se ce ne sono più di una
     *
     * @return testo della wikitable
     */
    public String leggeTable(String wikiTitle, int pos) {
        String testoTable = VUOTA;
        String tag1 = "{| class=\"wikitable";
        String tag2 = "{|class=\"wikitable";
        String tagEnd = "|}\n";
        int posIni = 0;
        int posEnd = 0;
        String testoPagina = legge(wikiTitle);

        if (text.isValid(testoPagina)) {
            if (testoPagina.contains(tag1) || testoPagina.contains(tag2)) {
                if (testoPagina.contains(tag1)) {
                    for (int k = 1; k <= pos; k++) {
                        posIni = testoPagina.indexOf(tag1, posIni + tag1.length());
                    }
                }
                if (testoPagina.contains(tag2)) {
                    for (int k = 1; k <= pos; k++) {
                        posIni = testoPagina.indexOf(tag2, posIni + tag2.length());
                    }
                }
                posEnd = testoPagina.indexOf(tagEnd, posIni) + tagEnd.length();
                testoTable = testoPagina.substring(posIni, posEnd);
            } else {
                logger.warn("La pagina wiki " + wikiTitle + " non contiene nessuna wikitable", this.getClass(), "leggeTable");
            }
        }

        return testoTable.trim();
    }


    /**
     * Legge dal server wiki <br>
     * Testo in linguaggio wiki <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return testo visibile della pagina
     */
    public String legge(String wikiTitle) {
        String testoPagina = VUOTA;
        String webUrl;
        String contenutoCompletoPaginaWebInFormatoJSON;

        try {
            wikiTitle = text.isValid(wikiTitle) ? URLEncoder.encode(wikiTitle, ENCODE) : VUOTA;
        } catch (Exception unErrore) {
            logger.error(unErrore, this.getClass(), "legge");

        }
        webUrl = WIKI_URL + wikiTitle;

        contenutoCompletoPaginaWebInFormatoJSON = web.leggeWeb(webUrl);
        testoPagina = estraeTestoPaginaWiki(contenutoCompletoPaginaWebInFormatoJSON);

        return testoPagina;
    }


    /**
     * Recupera la mappa dei valori dal testo JSON di una singola pagina
     * 21 parametri
     * 10 generali
     * 8 revisions
     * 3 slots/main
     *
     * @param singolaPaginaTextJSON in ingresso
     *
     * @return mappa parametri di una pagina
     */
    public String estraeTestoPaginaWiki(String singolaPaginaTextJSON) {
        String testoPagina = VUOTA;
        JSONArray arrayPagine = this.getArrayPagine(singolaPaginaTextJSON);

        if (arrayPagine != null) {
            testoPagina = this.getContent((JSONObject) arrayPagine.get(0));
        }

        return testoPagina;
    }


    /**
     * Recupera un array di pagine dal testo JSON di una pagina action=query
     *
     * @param contenutoCompletoPaginaWebInFormatoJSON in ingresso
     *
     * @return parametri pages
     */
    public JSONArray getArrayPagine(String contenutoCompletoPaginaWebInFormatoJSON) {
        JSONArray arrayQuery = null;
        JSONObject objectQuery = getObjectQuery(contenutoCompletoPaginaWebInFormatoJSON);

        //--recupera i valori dei parametri pages
        if (objectQuery != null && objectQuery.get(PAGES) != null && objectQuery.get(PAGES) instanceof JSONArray) {
            arrayQuery = (JSONArray) objectQuery.get(PAGES);
        }

        return arrayQuery;
    }


    /**
     * Recupera il contenuto testuale dal testo JSON di una singola pagina
     * 21 parametri
     * 10 generali
     * 8 revisions
     * 3 slots/main
     *
     * @param paginaTextJSON in ingresso
     *
     * @return testo della pagina wiki
     */
    public String getContent(JSONObject paginaTextJSON) {
        String textContent = VUOTA;
        JSONArray arrayRevisions;
        JSONObject objectRevisions = null;
        JSONObject objectSlots;
        JSONObject objectMain = null;

        if (paginaTextJSON == null) {
            return null;
        }

        //--parametri revisions
        if (paginaTextJSON.get(REVISIONS) != null && paginaTextJSON.get(REVISIONS) instanceof JSONArray) {
            arrayRevisions = (JSONArray) paginaTextJSON.get(REVISIONS);
            if (arrayRevisions != null && arrayRevisions.size() > 0 && arrayRevisions.get(0) instanceof JSONObject) {
                objectRevisions = (JSONObject) arrayRevisions.get(0);
            }
        }

        //--parametri slots/main -> content
        if (objectRevisions.get(SLOTS) != null && objectRevisions.get(SLOTS) instanceof JSONObject) {
            objectSlots = (JSONObject) objectRevisions.get(SLOTS);
            if (objectSlots.get(MAIN) != null && objectSlots.get(MAIN) instanceof JSONObject) {
                objectMain = (JSONObject) objectSlots.get(MAIN);
            }
        }

        if (objectMain.get(CONTENT) != null) {
            textContent = (String) objectMain.get(CONTENT);
        }

        return textContent;
    }


    /**
     * Recupera l'oggetto pagina dal testo JSON di una pagina action=query
     *
     * @param contenutoCompletoPaginaWebInFormatoJSON in ingresso
     *
     * @return parametri pages
     */
    public JSONObject getObjectQuery(String contenutoCompletoPaginaWebInFormatoJSON) {
        JSONObject objectQuery = null;
        JSONObject objectAll = (JSONObject) JSONValue.parse(contenutoCompletoPaginaWebInFormatoJSON);

        //--recupera i valori dei parametri pages
        if (objectAll != null && objectAll.get(QUERY) != null && objectAll.get(QUERY) instanceof JSONObject) {
            objectQuery = (JSONObject) objectAll.get(QUERY);
        }

        return objectQuery;
    }


    /**
     * Sorgente completo di una pagina web <br>
     * Testo 'grezzo' <br>
     *
     * @param paginaWiki da leggere
     *
     * @return sorgente
     */
    @Deprecated
    public String getSorgente(String paginaWiki) {
        return web.leggeSorgenteWiki(paginaWiki);
    }


    /**
     * Legge una porzione di testo incolonnato dalla pagina wikipedia <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return testo contenuto nelle colonne
     */
    public String leggeColonne(String wikiTitle) {
        String testoIncolonnato = VUOTA;
        String tagIni = "{{Colonne}}";
        String tagEnd = "{{Colonne fine}}";
        int posIni = 0;
        int posEnd = 0;
        String testoPagina = legge(wikiTitle);

        if (text.isValid(testoPagina)) {
            if (testoPagina.contains(tagIni)) {
                posIni = testoPagina.indexOf(tagIni);
                posEnd = testoPagina.indexOf(tagEnd, posIni);
                testoIncolonnato = testoPagina.substring(posIni, posEnd);
            }
        }

        return testoIncolonnato;
    }


    /**
     * Import da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con due stringhe ognuno
     */
    @Deprecated
    public List<WrapDueStringhe> estraeListaDue(String pagina, String titoli, int posUno, int posDue) {
        List<WrapDueStringhe> listaWrap = null;
        List<List<String>> matriceTable = null;
        String[] titoliTable = text.getMatrice(titoli);
        WrapDueStringhe wrapGrezzo;

        matriceTable = web.getMatriceTableWiki(pagina, titoliTable);
        if (matriceTable != null && matriceTable.size() > 0) {
            listaWrap = new ArrayList<>();
            for (List<String> riga : matriceTable) {
                wrapGrezzo = new WrapDueStringhe(riga.get(posUno - 1), posDue > 0 ? riga.get(posDue - 1) : VUOTA);
                listaWrap.add(wrapGrezzo);
            }
        }
        return listaWrap;
    }


    /**
     * Import da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con tre stringhe ognuno
     */
    @Deprecated
    public List<WrapTreStringhe> estraeListaTre(String pagina, String titoli) {
        List<WrapTreStringhe> listaWrap = null;
        LinkedHashMap<String, LinkedHashMap<String, String>> mappaGenerale = null;
        LinkedHashMap<String, String> mappa;
        String[] titoliTable = text.getMatrice(titoli);
        String tagUno = titoliTable[0];
        String tagDue = titoliTable[1];
        String tagTre = titoliTable[2];
        WrapTreStringhe wrapGrezzo;

        mappaGenerale = web.getMappaTableWiki(pagina, titoliTable);
        if (mappaGenerale != null && mappaGenerale.size() > 0) {
            listaWrap = new ArrayList<>();
            for (String elemento : mappaGenerale.keySet()) {
                mappa = mappaGenerale.get(elemento);
                wrapGrezzo = new WrapTreStringhe(mappa.get(tagUno), mappa.get(tagDue), mappa.get(tagTre));
                listaWrap.add(wrapGrezzo);
            }
        }
        return listaWrap;
    }


    public String fixCodice(String testoGrezzo) {
        String testoValido = VUOTA;
        String tagIni = "<code>";
        String tagEnd = "</code>";

        if (text.isEmpty(testoGrezzo)) {
            return VUOTA;
        }

        if (!testoGrezzo.contains(tagIni) || !testoGrezzo.contains(tagEnd)) {
            return VUOTA;
        }

        testoValido = testoGrezzo.trim();
        testoValido = text.levaTesta(testoValido, tagIni);
        testoValido = text.levaCoda(testoValido, tagEnd);
        testoValido = testoValido.substring(3);

        return testoValido.trim();
    }


    public String fixNome(String testoGrezzo) {
        String testoValido = VUOTA;
        int posIni = 0;
        int posEnd = 0;

        if (text.isEmpty(testoGrezzo)) {
            return VUOTA;
        }

        testoValido = testoGrezzo.trim();
        posEnd = testoValido.lastIndexOf("</a>");
        if (posEnd > 0) {
            testoValido = testoValido.substring(0, posEnd);
        }// end of if cycle
        posIni = testoValido.lastIndexOf(">") + 1;
        testoValido = testoValido.substring(posIni);

        return testoValido;
    }


    public String fixNomeStato(String testoGrezzo) {
        String testoValido = VUOTA;
        String tag = PIPE;
        int pos = 0;

        if (text.isEmpty(testoGrezzo)) {
            return VUOTA;
        }

        testoValido = testoGrezzo.trim();
        if (testoGrezzo.contains(tag)) {
            pos = testoGrezzo.indexOf(tag) + tag.length();
            testoValido = testoGrezzo.substring(pos);
        }

        return testoValido.trim();
    }


    //    public String fixNomeRegione(String testoGrezzo) {
    //        String testoValido = VUOTA;
    //        String tag = "\\|";
    //        String[] parti;
    //
    //        if (text.isEmpty(testoGrezzo)) {
    //            return VUOTA;
    //        }
    //
    //        testoValido = testoGrezzo.trim();
    //        testoValido = text.setNoGraffe(testoValido);
    //        parti = testoValido.split(tag);
    //
    //        if (parti.length == 3) {
    //            testoValido = parti[1];
    //        }
    //
    //        return testoValido.trim();
    //    }


    public String fixRegione(String testoGrezzo) {
        String testoValido = VUOTA;
        String tag = "-";
        int pos = 0;
        String[] parti = null;

        if (text.isEmpty(testoGrezzo)) {
            return VUOTA;
        }

        if (testoGrezzo.contains(tag)) {
            pos = testoGrezzo.indexOf(tag) + tag.length();
            testoValido = testoGrezzo.substring(pos, pos + 3);
        }

        return testoValido.trim();
    }

    //    public String fixRegione(String testoGrezzo) {
    //        String testoValido = VUOTA;
    //        String[] parti = null;
    //
    //        if (text.isEmpty(testoGrezzo)) {
    //            return VUOTA;
    //        }
    //
    //        if (testoGrezzo.contains(PIPE)) {
    //            parti = testoGrezzo.split(PIPE_REGEX);
    //            if (parti != null && parti.length == 2) {
    //                testoValido = parti[1].trim();
    //                testoValido = text.setNoQuadre(testoValido);
    //            }
    //        }
    //
    //        return testoValido.trim();
    //    }


    /**
     * Import delle regioni da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con due stringhe ognuno (sigla, nome)
     */
    @Deprecated
    public List<WrapDueStringhe> regioni() {
        List<WrapDueStringhe> listaWrap = null;
        List<WrapDueStringhe> listaWrapGrezzo = null;
        WrapDueStringhe wrapValido;
        String titoli = "Codice,Regioni";
        String prima;
        String seconda;

        listaWrapGrezzo = estraeListaDue(PAGINA_ISO_2, titoli, 1, 2);
        if (listaWrapGrezzo != null && listaWrapGrezzo.size() > 0) {
            listaWrap = new ArrayList<>();
            for (WrapDueStringhe wrap : listaWrapGrezzo) {
                prima = wrap.getPrima();
                seconda = wrap.getSeconda();
                prima = fixCodice(prima);
                seconda = fixNome(seconda);
                wrapValido = new WrapDueStringhe(prima, seconda);
                listaWrap.add(wrapValido);
            }
        }

        return listaWrap;
    }


    /**
     * Import degli stati da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con tre stringhe ognuno (sigla, nome, regione)
     */
    @Deprecated
    public List<WrapQuattroStringhe> stati() {
        List<WrapQuattroStringhe> listaWrap = null;
        List<WrapQuattroStringhe> listaWrapGrezzo = null;
        WrapQuattroStringhe wrapValido;
        String titoli = "Codice,Regioni";
        String prima;
        String seconda;

        //        listaWrapGrezzo = estraeListaDue(PAGINA, titoli, 1, 2);
        //        if (listaWrapGrezzo != null && listaWrapGrezzo.size() > 0) {
        //            listaWrap = new ArrayList<>();
        //            for (WrapDueStringhe wrap : listaWrapGrezzo) {
        //                prima = wrap.getPrima();
        //                seconda = wrap.getSeconda();
        //                prima = elaboraCodice(prima);
        //                seconda = elaboraNome(seconda);
        //                wrapValido = new WrapDueStringhe(prima, seconda);
        //                listaWrap.add(wrapValido);
        //            }
        //        }

        return listaWrap;
    }

}