package it.algos.vaadflow14.backend.service;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.wrapper.*;
import static it.algos.vaadflow14.wiki.AWikiApiService.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 08-mag-2021
 * Time: 16:37
 * <p>
 * Metodi specializzati per importare ed elaborare dati geografici da Wikipedia <br>
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
public class GeograficService extends AbstractService {


    /**
     * Estrae una wikitable da una pagina wiki <br>
     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
     *
     * @return lista di valori per ogni riga significativa della wikitable
     */
    public List<List<String>> getStati() {
        List<List<String>> listaTable = new ArrayList<>();
        List<List<String>> listaGrezza = null;
        Map<String, String> mappa = getTableStatiNumerico();
        String sep = DOPPIO_PIPE_REGEX;
        String[] parti;
        List<String> riga;
        String numerico;
        String nome;
        String alfatre;
        String alfadue;
        String locale;

        try {
            listaGrezza = wikiApi.getTable(PAGINA_ISO_1);
        } catch (Exception unErrore) {
        }

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
     * Restituisce una lista degli stati <br>
     *
     * @return lista dei nome degli stati
     */
    public List<String> getNomeStati() {
        List<String> lista = new ArrayList<>();
        List<List<String>> listaGrezza = getStati();

        for (List<String> listaRiga : listaGrezza) {
            lista.add(listaRiga.get(0));
        }

        return lista;
    }


    /**
     * Restituisce una lista degli stati <br>
     *
     * @return lista dei nome degli stati
     */
    public List<String> getSiglaStati() {
        List<String> lista = new ArrayList<>();
        List<List<String>> listaGrezza = getStati();

        for (List<String> listaRiga : listaGrezza) {
            lista.add(listaRiga.get(3));
        }

        return lista;
    }

    /**
     * Estrae una wikitable da una pagina wiki <br>
     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
     *
     * @return lista di valori per ogni riga significativa della wikitable
     */
    public Map<String, String> getTableStatiNumerico() {
        Map<String, String> mappa = new HashMap<>();
        List<List<String>> listaGrezza = null;
        String codice;
        String paese;

        try {
            listaGrezza = wikiApi.getTable(PAGINA_ISO_1_NUMERICO, 1);
        } catch (Exception unErrore) {
        }

        if (listaGrezza != null && listaGrezza.size() > 1) {
            for (List<String> riga : listaGrezza) {
                codice = riga.get(0).trim();
                paese = text.setNoQuadre(riga.get(1)).trim();
                mappa.put(codice, paese);
            }
        }

        return mappa;
    }

    /**
     * Estrae il contenuto del template bandierina indicato <br>
     *
     * @param wikiTitle del template wiki
     *
     * @return coppia di valori: sigla e nome
     */
    public WrapTreStringhe getTemplateBandierina(String wikiTitle) {
        WrapTreStringhe wrap = null;
        String tag = "Template:";
        String titolo;
        String testoGrezzo;
        String sigla;

        if (text.isEmpty(wikiTitle)) {
            return null;
        }

        wikiTitle = text.setNoDoppieGraffe(wikiTitle);
        sigla = text.levaTestoPrimaDi(wikiTitle, TRATTINO);
        if (sigla.contains(PIPE)) {
            sigla = text.levaCodaDa(sigla, PIPE);
        }

        if (sigla.length() < 2 && Character.isDigit(sigla.charAt(0))) {
            sigla = "0" + sigla;
        }

        if (wikiTitle.startsWith(tag)) {
            titolo = text.levaTesta(wikiTitle, tag);
        }
        else {
            titolo = wikiTitle;
            wikiTitle = tag + wikiTitle;
        }

        testoGrezzo = wikiApi.legge(wikiTitle);

        if (text.isValid(testoGrezzo)) {
            if (testoGrezzo.startsWith(DOPPIE_GRAFFE_INI)) {
                wrap = estraeBandierinaGraffe(titolo, testoGrezzo, sigla);
            }
            else {
                if (testoGrezzo.contains("{{band div|ITA")) {
                    wrap = estraeBandierinaGraffe(titolo, testoGrezzo, sigla);
                }
                else {
                    wrap = estraeBandierinaQuadre(titolo, testoGrezzo, sigla);
                }
            }
        }

        return wrap;
    }

    /**
     * Restituisce un wrapper di valori: sigla e nome estratti dal template bandierine <br>
     * '{{band div|FRA|Borgogna-Franca Contea}}<noinclude>[[Categoria:Template bandierine regionali francesi|BFC]]</noinclude>' <br>
     *
     * @param testoGrezzo del template wiki
     * @param sigla       della suddivisione richiesta
     *
     * @return wrapper di valori: sigla e nome
     */
    public WrapTreStringhe estraeBandierinaGraffe(String titolo, String testoGrezzo, String sigla) {
        WrapTreStringhe wrap = null;
        String testoGraffa = VUOTA;
        String[] parti = null;
        String nome = VUOTA;

        if (text.isValid(testoGrezzo)) {
            testoGraffa = wikiApi.estraeGraffa(testoGrezzo);
        }

        if (text.isValid(testoGraffa)) {
            parti = testoGraffa.split(PIPE_REGEX);
        }

        if (parti != null && parti.length >= 3) {
            nome = parti[2];
            wrap = new WrapTreStringhe(titolo, sigla, nome);
        }

        return wrap;
    }

    /**
     * Restituisce un wrapper di valori: sigla e nome estratti dal template bandierine <br>
     * '[[File:Blason_Auvergne-Rhône-Alpes.svg|20px]] [[Alvernia-Rodano-Alpi]] <noinclude>[[Categoria:Template bandierine regionali francesi|ARA]]</noinclude>' <br>
     *
     * @param testoGrezzo del template wiki
     * @param sigla       della suddivisione richiesta
     *
     * @return wrapper di valori: sigla e nome
     */
    public WrapTreStringhe estraeBandierinaQuadre(String titolo, String testoGrezzo, String sigla) {
        WrapTreStringhe wrap = null;
        String testoQuadra = VUOTA;
        String tag = "<noinclude";
        String nome;

        if (text.isValid(testoGrezzo)) {
            testoQuadra = text.levaCodaDa(testoGrezzo, tag);
        }

        //--estrae la seconda quadra
        if (text.isValid(testoQuadra)) {
            testoQuadra = text.levaTestoPrimaDi(testoQuadra, DOPPIE_QUADRE_END);
        }

        if (text.isValid(testoQuadra)) {
            nome = text.estrae(testoQuadra, DOPPIE_QUADRE_INI, DOPPIE_QUADRE_END).trim();
            if (nome.contains(PIPE)) {
                nome = text.levaTestoPrimaDi(nome, PIPE);
            }
            wrap = new WrapTreStringhe(titolo, sigla, nome);
        }

        return wrap;
    }


    /**
     * Restituisce una lista di stringhe estratte dai template bandierine <br>
     *
     * @param wikiTitle    della pagina wiki
     * @param posTabella   della wikitable nella pagina se ce ne sono più di una
     * @param rigaIniziale da cui estrarre le righe, scartando la testa della table
     * @param numColonna   da cui estrarre il template-bandierine
     *
     * @return lista di coppia di valori: sigla e nome
     */
    @Deprecated
    public List<WrapTreStringhe> getTemplateList(String wikiTitle, int posTabella, int rigaIniziale, int numColonna) {
        List<String> lista = wikiApi.getColonna(wikiTitle, posTabella, rigaIniziale, numColonna);
        return getTemplateList(lista);
    }

    /**
     * Restituisce una lista di stringhe estratte dai template bandierine <br>
     *
     * @param listaTemplate bandierine
     *
     * @return lista di coppia di valori: sigla e nome
     */
    @Deprecated
    public List<WrapTreStringhe> getTemplateList(List<String> listaTemplate) {
        List<WrapTreStringhe> lista = null;
        WrapTreStringhe wrap;

        if (array.isAllValid(listaTemplate)) {
            lista = new ArrayList<>();
            for (String wikiTitle : listaTemplate) {
                wrap = getTemplateBandierina(wikiTitle);
                if (wrap != null) {
                    lista.add(wrap);
                }
            }
        }

        return lista;
    }

    /**
     * Restituisce una lista di stringhe estratte dai template bandierine <br>
     *
     * @param wikiTitle             della pagina wiki
     * @param posTabella            della wikitable nella pagina se ce ne sono più di una
     * @param rigaIniziale          da cui estrarre le righe, scartando la testa della table
     * @param numColonnaBandierine  da cui estrarre il template-bandierine
     * @param numColonnaTerzoValore da cui estrarre il valore della terza stringa richiesta
     *
     * @return lista di tripletta di valori: sigla e nome e divisione amministrativa superiore
     */
    public List<WrapTreStringhe> getTemplateList(String wikiTitle, int posTabella, int rigaIniziale, int numColonnaBandierine, int numColonnaTerzoValore) {
        List<WrapTreStringhe> listaTre = null;
        WrapTreStringhe wrapBandierina;
        WrapTreStringhe wrapTre;
        List<WrapDueStringhe> listaDue = wikiApi.getDueColonne(wikiTitle, posTabella, rigaIniziale, numColonnaBandierine, numColonnaTerzoValore);

        if (array.isAllValid(listaDue)) {
            listaTre = new ArrayList<>();
            for (WrapDueStringhe wrapDue : listaDue) {
                wrapBandierina = getTemplateBandierina(wrapDue.getPrima());
                if (wrapBandierina != null) {
                    wrapTre = new WrapTreStringhe(wrapBandierina.getPrima(), wrapBandierina.getSeconda(), wrapDue.getSeconda());
                    listaTre.add(wrapTre);
                }
            }
        }

        return listaTre.subList(1, listaTre.size() - 1);
    }


    /**
     * Import delle regioni (tutti gli stati) da una pagina di wikipedia <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return lista di wrapper con due stringhe ognuno (sigla, nome)
     */
    public List<WrapDueStringhe> getRegioni(String wikiTitle) {
        List<WrapDueStringhe> listaWrap = null;
        List<List<String>> listaTable;
        WrapDueStringhe wrap;

        listaTable = wikiApi.getTable(wikiTitle);
        if (listaTable == null || listaTable.size() < 1) {
            listaTable = wikiApi.getTable(wikiTitle, 2);
        }

        if (listaTable != null && listaTable.size() > 0) {
            listaWrap = new ArrayList<>();

            wrap = getWrapTitolo(listaTable.get(0));
            if (wrap != null) {
                listaWrap.add(wrap);
            }

            for (List<String> listaRiga : listaTable.subList(1, listaTable.size())) {
                wrap = getWrapRegione(listaRiga);
                if (wrap != null) {
                    listaWrap.add(wrap);
                }
            }
        }

        return listaWrap;
    }

    /**
     * Import delle province italiane da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con due stringhe ognuno (sigla, nome)
     */
    public List<WrapQuattro> getProvince() {
        List<WrapQuattro> listaWrap = null;
        String wikiTitle = "ISO_3166-2:IT";
        List<List<String>> listaTable;
        WrapTreStringhe wrapTre;

        listaTable = wikiApi.getTable(wikiTitle, 2);
        if (listaTable != null && listaTable.size() > 0) {
            listaWrap = new ArrayList<>();

            for (List<String> listaRiga : listaTable.subList(1, listaTable.size())) {
                wrapTre = getWrapProvincia(listaRiga);
                if (wrapTre != null) {
                    listaWrap.add(new WrapQuattro(wrapTre, true));
                }
            }
        }

        listaTable = wikiApi.getTable(wikiTitle, 3);
        if (listaTable != null && listaTable.size() > 0) {

            for (List<String> listaRiga : listaTable.subList(1, listaTable.size())) {
                wrapTre = getWrapProvincia(listaRiga);
                if (wrapTre != null) {
                    listaWrap.add(new WrapQuattro(wrapTre, false));
                }
            }
        }

        return listaWrap;
    }


    /**
     * Probabilmente il secondo elemento della lista contiene i titoli <br>
     *
     * @param listaRiga valori di una singola riga di titoli
     *
     * @return wrapper di due stringhe (titoloUno, titoloDue)
     */
    public WrapDueStringhe getWrapTitolo(final List<String> listaRiga) {
        WrapDueStringhe wrap = null;
        String titoloUno = VUOTA;
        String titoloDue = VUOTA;

        if (listaRiga != null && listaRiga.size() == 2) {
            titoloUno = listaRiga.get(0).trim();
            titoloDue = listaRiga.get(1).trim();
        }

        if (listaRiga != null && listaRiga.size() == 3) {
            titoloUno = listaRiga.get(1).trim();
            titoloDue = listaRiga.get(2).trim();
        }

        //--Azerbaigian
        if (listaRiga != null && listaRiga.size() == 4) {
            titoloUno = listaRiga.get(0).trim();
            titoloDue = listaRiga.get(1).trim();
        }

        if (text.isValid(titoloUno) && text.isValid(titoloDue)) {
            if (titoloUno.startsWith(ESCLAMATIVO)) {
                titoloUno = text.levaTestoPrimaDi(titoloUno, ESCLAMATIVO);
            }
            titoloUno = titoloUno.trim();

            if (titoloDue.contains(DOPPIE_QUADRE_INI) && titoloDue.contains(DOPPIE_QUADRE_END)) {
                titoloDue = text.estrae(titoloDue, DOPPIE_QUADRE_INI, DOPPIE_QUADRE_END);
            }
            if (titoloDue.contains(PIPE)) {
                titoloDue = text.levaTestoPrimaDi(titoloDue, PIPE);
            }
            if (titoloDue.startsWith(ESCLAMATIVO)) {
                titoloDue = text.levaTestoPrimaDi(titoloDue, ESCLAMATIVO);
            }
            titoloDue = titoloDue.trim();

            wrap = new WrapDueStringhe(titoloUno, titoloDue);
        }

        return wrap;
    }


    /**
     * Estrae una coppia di valori significativi da una lista eterogenea <br>
     * Se la lista ha un solo valore, qualcosa non funziona <br>
     * Se la lista ha più di due valori, occorre selezionare i due significativi <br>
     * Sicuramente uno dei due valori contiene la sigla (deve avere un trattino) <br>
     * Uno dei valori deve essere un nome oppure il link alle bandierine (deve avere le doppie graffe) <br>
     * Dalla eventuale bandierina recupero il nome <br>
     *
     * @param listaRiga valori di una singola regione
     *
     * @return wrapper di due stringhe valid (sigla, nome)
     */
    public WrapDueStringhe getWrapRegione(final List<String> listaRiga) {
        String sigla = VUOTA;
        String nome = VUOTA;
        WrapTreStringhe wrapTre = null;
        WrapDueStringhe wrap = null;

        if (listaRiga.size() < 2) {
            return null;
        }

        if (listaRiga.get(0).contains(TRATTINO)) {
            sigla = listaRiga.get(0);
        }
        else {
            if (listaRiga.get(1).contains(TRATTINO)) {
                sigla = listaRiga.get(1);
            }
            else {
                if (listaRiga.get(0).length() == 1) {
                    sigla = listaRiga.get(0);
                }
            }
        }

        //--finlandia
        if (text.isEmpty(nome) && listaRiga.size() == 4 && !listaRiga.get(1).contains(DOPPIE_QUADRE_INI) && !listaRiga.get(1).contains(DOPPIE_QUADRE_INI) && listaRiga.get(3).contains(DOPPIE_QUADRE_INI) && listaRiga.get(3).contains(DOPPIE_QUADRE_END)) {
            nome = listaRiga.get(3);
        }

        //--azerbaigian
        if (text.isEmpty(nome) && listaRiga.size() == 4 && listaRiga.get(3).contains(DOPPIE_QUADRE_INI) && listaRiga.get(3).contains(DOPPIE_QUADRE_END)) {
            nome = listaRiga.get(1);
        }

        if (text.isEmpty(nome) && listaRiga.get(1).contains(DOPPIE_QUADRE_INI) && listaRiga.get(1).contains(DOPPIE_QUADRE_END)) {
            nome = listaRiga.get(1);
        }
        else {
            if (listaRiga.size() > 2 && listaRiga.get(2).contains(DOPPIE_QUADRE_INI) && listaRiga.get(2).contains(DOPPIE_QUADRE_END)) {
                nome = listaRiga.get(2);
            }
        }

        //--solo per Italia (spero)
        if (text.isEmpty(nome) && listaRiga.get(1).contains(DOPPIE_GRAFFE_INI) && listaRiga.get(1).contains(PIPE) && listaRiga.get(1).contains(DOPPIE_GRAFFE_END)) {
            nome = text.estrae(listaRiga.get(1), DOPPIE_GRAFFE_INI, DOPPIE_GRAFFE_END);
            nome = text.estrae(nome, PIPE, PIPE);
        }

        //--template bandierine per recuperare il nome
        if (text.isEmpty(nome)) {
            if (listaRiga.get(1).contains(DOPPIE_GRAFFE_INI) && listaRiga.get(1).contains(DOPPIE_GRAFFE_END)) {
                wrapTre = getTemplateBandierina(listaRiga.get(1));
                wrap = new WrapDueStringhe(wrapTre.getSeconda(), wrapTre.getTerza());
            }
            else {
                if (listaRiga.size() > 2 && listaRiga.get(2).contains(DOPPIE_GRAFFE_INI) && listaRiga.get(2).contains(DOPPIE_GRAFFE_END)) {
                    wrapTre = getTemplateBandierina(listaRiga.get(2));
                    wrap = new WrapDueStringhe(wrapTre.getSeconda(), wrapTre.getTerza());
                }
            }
        }

        if (text.isEmpty(sigla) && text.isValid(nome)) {
            sigla = listaRiga.get(0);
        }

        //--belize
        if (listaRiga.size() == 2 && text.isEmpty(nome) && sigla.equals(listaRiga.get(1))) {
            nome = listaRiga.get(0);
        }

        sigla = sigla.trim();
        sigla = html.setNoHtmlTag(sigla, "kbd");
        sigla = html.setNoHtmlTag(sigla, "code");
        sigla = text.levaCodaDa(sigla, "<ref");

        if (text.isValid(nome)) {
            nome = nome.trim();
            nome = text.estrae(nome, DOPPIE_QUADRE_INI, DOPPIE_QUADRE_END);
            nome = text.levaTestoPrimaDi(nome, PIPE);
            wrap = new WrapDueStringhe(sigla, nome);
        }
        else {
            if (wrap != null) {
                wrap.setPrima(sigla);
            }
        }

        return wrap;
    }

    /**
     * Estrae una tripletta di valori significativi da una lista eterogenea <br>
     * Se la lista ha un solo valore, qualcosa non funziona <br>
     * Se la lista ha più di due valori, occorre selezionare i due significativi <br>
     * Sicuramente uno dei due valori contiene la sigla (deve avere un trattino) <br>
     * Uno dei valori deve essere un nome oppure il link alle bandierine (deve avere le doppie graffe) <br>
     * Dalla eventuale bandierina recupero il nome <br>
     *
     * @param listaRiga valori di una singola regione
     *
     * @return wrapper di due stringhe valid (sigla, nome)
     */
    public WrapTreStringhe getWrapProvincia(List<String> listaRiga) {
        String sigla;
        String nome = VUOTA;
        String regioneID;
        WrapTreStringhe wrap = null;
        String tagVdA = "valled'aosta";
        String tagA = "Aosta";

        if (listaRiga.size() < 3) {
            return null;
        }

        sigla = listaRiga.get(1).trim();
        regioneID = fixRegione(listaRiga.get(2).trim());

        //--template bandierine per recuperare il nome
        if (sigla.contains(DOPPIE_GRAFFE_INI) && sigla.contains(DOPPIE_GRAFFE_END)) {
            if (regioneID.equals(tagVdA)) {
                sigla = "AO";
                nome = tagA;
            }
            else {
                sigla = text.estraeGraffaDoppia(sigla);
                wrap = getTemplateBandierina(sigla);
                if (wrap != null) {
                    sigla = wrap.getSeconda();
                    nome = wrap.getTerza();
                }
            }
        }

        return new WrapTreStringhe(sigla, nome, regioneID);
    }

    public String fixRegione(String testoIn) {
        String regione = testoIn;

        if (testoIn.contains(DOPPIE_QUADRE_INI) && testoIn.contains(DOPPIE_QUADRE_END)) {
            regione = text.estrae(testoIn, DOPPIE_QUADRE_INI, DOPPIE_QUADRE_END);
            regione = regione.toLowerCase();
            regione = regione.replaceAll(SPAZIO, VUOTA);
        }

        return regione;
    }

    //    /**
    //     * Estrae una tripletta di valori significativi da una lista eterogenea <br>
    //     * Se la lista ha un solo valore, qualcosa non funziona <br>
    //     * Se la lista ha più di due valori, occorre selezionare i due significativi <br>
    //     * Sicuramente uno dei due valori contiene la sigla (deve avere un trattino) <br>
    //     * Uno dei valori deve essere un nome oppure il link alle bandierine (deve avere le doppie graffe) <br>
    //     * Dalla eventuale bandierina recupero il nome <br>
    //     *
    //     * @param listaRiga valori di una singola regione
    //     *
    //     * @return wrapper di due stringhe valid (sigla, nome)
    //     */
    //    public WrapTreStringhe getWrapProvinciaOld(List<String> listaRiga) {
    //        String sigla;
    //        String nome = VUOTA;
    //        String regione;
    //        WrapTreStringhe wrap;
    //        WrapDueStringhe wrapDue;
    //        WrapDueStringhe wrapDueReg;
    //        String tagVdA = "Valle d'Aosta";
    //
    //        if (listaRiga.size() < 3) {
    //            return null;
    //        }
    //
    //        sigla = listaRiga.get(0).trim();
    //        regione = listaRiga.get(2).trim();
    //        regione = text.estrae(regione, DOPPIE_GRAFFE_INI, DOPPIE_GRAFFE_END);
    //        wrapDueReg = getTemplateBandierina(regione);
    //        if (wrapDueReg != null) {
    //            regione = wrapDueReg.getSeconda();
    //        }
    //
    //        //--template bandierine per recuperare il nome
    //        if (sigla.contains(DOPPIE_GRAFFE_INI) && sigla.contains(DOPPIE_GRAFFE_END)) {
    //            if (regione.equals(tagVdA)) {
    //                sigla = "AO";
    //                nome = regione;
    //            }
    //            else {
    //                sigla = text.estraeGraffaDoppia(sigla);
    //                wrapDue = getTemplateBandierina(sigla);
    //                if (wrapDue != null) {
    //                    sigla = wrapDue.getPrima();
    //                    nome = wrapDue.getSeconda();
    //                }
    //            }
    //        }
    //
    //        wrap = new WrapTreStringhe(sigla, nome, regione);
    //        return wrap;
    //    }


    public String fixNomeStato(String testoGrezzo) {
        String testoValido;
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


    /**
     * Import delle regioni da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con due stringhe ognuno (sigla, nome)
     */
    @Deprecated
    public List<WrapDueStringhe> regioni() {
        List<WrapDueStringhe> listaWrap = null;
        List<WrapDueStringhe> listaWrapGrezzo;
        WrapDueStringhe wrapValido;
        String titoli = "Codice,Regioni";
        String prima;
        String seconda;

        listaWrapGrezzo = wikiApi.estraeListaDue(PAGINA_ISO_2, titoli, 1, 2);
        if (listaWrapGrezzo != null && listaWrapGrezzo.size() > 0) {
            listaWrap = new ArrayList<>();
            for (WrapDueStringhe wrap : listaWrapGrezzo) {
                prima = wrap.getPrima();
                seconda = wrap.getSeconda();
                prima = wikiApi.fixCode(prima);
                seconda = fixNome(seconda);
                wrapValido = new WrapDueStringhe(prima, seconda);
                listaWrap.add(wrapValido);
            }
        }

        return listaWrap;
    }


    public String fixNome(String testoGrezzo) {
        String testoValido;
        int posIni;
        int posEnd;

        if (text.isEmpty(testoGrezzo)) {
            return VUOTA;
        }

        testoValido = testoGrezzo.trim();
        posEnd = testoValido.lastIndexOf("</a>");
        if (posEnd > 0) {
            testoValido = testoValido.substring(0, posEnd);
        }
        posIni = testoValido.lastIndexOf(">") + 1;
        testoValido = testoValido.substring(posIni);

        return testoValido;
    }

    //    /**
    //     * Import degli stati da una pagina di wikipedia <br>
    //     *
    //     * @return lista di wrapper con tre stringhe ognuno (sigla, nome, regione)
    //     */
    //    @Deprecated
    //    public List<WrapQuattroStringhe> stati() {
    //        List<WrapQuattroStringhe> listaWrap = null;
    //        List<WrapQuattroStringhe> listaWrapGrezzo = null;
    //        WrapQuattroStringhe wrapValido;
    //        String titoli = "Codice,Regioni";
    //        String prima;
    //        String seconda;
    //
    //        //        listaWrapGrezzo = estraeListaDue(PAGINA, titoli, 1, 2);
    //        //        if (listaWrapGrezzo != null && listaWrapGrezzo.size() > 0) {
    //        //            listaWrap = new ArrayList<>();
    //        //            for (WrapDueStringhe wrap : listaWrapGrezzo) {
    //        //                prima = wrap.getPrima();
    //        //                seconda = wrap.getSeconda();
    //        //                prima = elaboraCodice(prima);
    //        //                seconda = elaboraNome(seconda);
    //        //                wrapValido = new WrapDueStringhe(prima, seconda);
    //        //                listaWrap.add(wrapValido);
    //        //            }
    //        //        }
    //
    //        return listaWrap;
    //    }

}