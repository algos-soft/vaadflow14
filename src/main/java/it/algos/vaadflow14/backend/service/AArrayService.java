package it.algos.vaadflow14.backend.service;

import com.vaadin.flow.component.grid.Grid;
import it.algos.vaadflow14.backend.functional.APredicate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-set-2019 18.19.24 <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private AArrayService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AArrayService extends AAbstractService {


    /**
     * Controlla la validità di un array (lista). <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (size maggiore di 0) <br>
     * Non ci devono essere elementi con valore null <br>
     * Non sono accettate le stringhe vuote -> "" <br>
     *
     * @param array (List) in ingresso da controllare
     *
     * @return vero se l'array soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isAllValid(final List array) {
        return array != null && array.size() > 0 && array.stream().filter(APredicate.nonValido).count() == 0;
    }


    /**
     * Controlla la validità di una matrice. <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (length maggiore di 0) <br>
     * Non ci devono essere elementi con valore null <br>
     * Non sono accettate le stringhe vuote -> "" <br>
     *
     * @param matrice (String[]) in ingresso da controllare
     *
     * @return vero se la matrice soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isAllValid(final String[] matrice) {
        return matrice != null && matrice.length > 0 && Arrays.asList(matrice).stream().filter(APredicate.nonValido).count() == 0;
    }


    /**
     * Controlla la validità di una mappa. <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (size maggiore di 0) <br>
     * Non ci devono essere chiavi con valore null <br>
     * Non sono accettate le chiavi vuote -> "" <br>
     *
     * @param mappa (Map) in ingresso da controllare
     *
     * @return vero se la mappa soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isAllValid(final Map mappa) {
        return mappa != null && mappa.size() > 0 && mappa.keySet().stream().filter(APredicate.nonValido).count() == 0;
    }


    /**
     * Controlla che l'array sia nullo o vuoto <br>
     * Non deve esistere (null) <br>
     * Se esiste, non deve avere elementi (size = 0) <br>
     * Se ci sono elementi devono avere tutti valore null <br>
     *
     * @param array (List) in ingresso da controllare
     *
     * @return vero se l'array soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isEmpty(final List array) {
        return array == null || array.size() == 0 || array.stream().filter(APredicate.valido).count() == 0;
    }


    /**
     * Controlla che la matrice sia nulla o vuota. <br>
     * Non deve esistere (null) <br>
     * Se esiste, non deve avere elementi (size = 0) <br>
     * Se ci sono elementi devono avere tutti valore null o vuoto -> "" <br>
     *
     * @param matrice (String[]) in ingresso da controllare
     *
     * @return vero se la matrice soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isEmpty(final String[] matrice) {
        return matrice == null || matrice.length == 0 || Arrays.asList(matrice).stream().filter(APredicate.valido).count() == 0;
    }


    /**
     * Controlla che la mappa sia nulla o vuota. <br>
     * Non deve esistere (null) <br>
     * Se esiste, non deve avere elementi (size = 0) <br>
     * Se ci sono elementi devono avere tutti la chiave null o vuota -> "" <br>
     *
     * @param mappa (Map) in ingresso da controllare
     *
     * @return vero se la mappa soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isEmpty(final Map mappa) {
        return mappa == null || mappa.size() == 0 || mappa.keySet().stream().filter(APredicate.valido).count() == 0;
    }


    /**
     * Controlla se una mappa può essere semplificata. <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (size maggiore di 0) <br>
     * La mappa prevede delle liste di valori per ogni key, quindi Map<String, List<String>> <br>
     * Se tutte le liste hanno un singolo valore, si può usare una mappa più semplice Map<String, String> <br>
     */
    public boolean isMappaSemplificabile(Map<String, List<String>> mappaConListe) {
        boolean status = true;

        if (mappaConListe == null) {
            return false;
        }

        if (mappaConListe.size() < 1) {
            return false;
        }

        for (Map.Entry<String, List<String>> entry : mappaConListe.entrySet()) {
            if (entry.getValue().size() > 1) {
                status = false;
            }
        }

        return status;
    }


    /**
     * Semplifica la mappa (semplificabile) <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (size maggiore di 0) <br>
     * Questa prevede delle liste di valori per ogni key, quindi Map<String, List<String>> <br>
     * Spesso basta un valore. <br>
     * Se tutte le keys hanno un solo valore, si usa una mappa più semplice Map<String, String> <br>
     */
    public Map<String, String> semplificaMappa(Map<String, List<String>> mappaConListe) {
        Map<String, String> mappaSemplice = new HashMap<>();

        if (!isMappaSemplificabile(mappaConListe)) {
            return null;
        }

        for (Map.Entry<String, List<String>> entry : mappaConListe.entrySet()) {
            if (entry.getValue().size() == 1) {
                mappaSemplice.put(entry.getKey(), entry.getValue().get(0));
            }
            else {
                //@todo Linea di codice provvisoriamente commentata e DA RIMETTERE
                //                log.error("Qualcosa non ha funzionato");
            }
        }

        return mappaSemplice;
    }


    /**
     * Costruisce una mappa da una singola coppia chiave-valore <br>
     * Entrambe le stringhe devono essere valori validi <br>
     *
     * @param keyMap   di chiave=valore
     * @param valueMap di chiave=valore
     *
     * @return mappa di un solo elemento chiave-valore
     */
    public Map<String, String> getMappa(String keyMap, String valueMap) {
        Map<String, String> mappa = new HashMap<>();

        if (text.isEmpty(keyMap) || text.isEmpty(valueMap)) {
            return null;
        }

        mappa.put(keyMap, valueMap);
        return mappa;
    }


    /**
     * Convert a objArray to ArrayList <br>
     *
     * @param objArray to convert
     *
     * @return the corresponding List
     */
    public List<Object> fromObj(Object[] objArray) {
        List<Object> objList = new ArrayList<Object>();

        for (Object obj : objArray) {
            objList.add(obj);
        }
        //        Collections.addAll(objList, objArray);@//@todo Funzionalità ancora da implementare

        return objList;
    }


    /**
     * Convert a stringArray to List
     *
     * @param strArray to convert
     *
     * @return the corresponding string List
     */
    public List<String> fromStr(String[] strArray) {
        List<String> strList = new ArrayList<String>();

        Collections.addAll(strList, strArray);

        return strList;
    }


    /**
     * Costruisce una lista di un singolo valore <br>
     *
     * @param singoloValore da inserire
     *
     * @return mappa di un solo elemento chiave-valore
     */
    public List<String> getLista(String singoloValore) {
        List<String> lista = new ArrayList<>();

        if (text.isValid(singoloValore)) {//@todo Funzionalità ancora da implementare
            lista.add(singoloValore);
        }

        return lista;
    }


    /**
     * Costruisce una stringa con i singoli valori divisi da un pipe
     *
     * @param array lista di valori
     *
     * @return stringa con i singoli valori divisi da un separatore
     */
    public String toStringaPipe(List array) {
        return toStringa(array, PIPE);
    }


    /**
     * Costruisce una stringa con i singoli valori divisi da una virgola seguita da uno spazio
     *
     * @param array lista di valori
     *
     * @return stringa con i singoli valori divisi da un separatore
     */
    public String toStringaSpazio(List array) {
        return toStringa(array, VIRGOLA_SPAZIO);
    }


    /**
     * Costruisce una stringa con i singoli valori divisi da un separatore virgola
     *
     * @param array lista di valori
     *
     * @return stringa con i singoli valori divisi da un separatore
     */
    public String toStringa(List array) {
        return toStringa(array, VIRGOLA);
    }


    /**
     * Costruisce una stringa con i singoli valori divisi da un separatore
     *
     * @param array lista di valori
     * @param sep   carattere separatore
     *
     * @return stringa con i singoli valori divisi da un separatore
     */
    public String toStringa(List array, String sep) {
        String testo = VUOTA;
        StringBuilder textBuffer = null;

        if (array != null) {
            textBuffer = new StringBuilder();
            for (Object obj : array) {
                textBuffer.append(obj.toString());
                textBuffer.append(sep);
            }
            testo = textBuffer.toString();
            testo = text.levaCoda(testo, sep);
        }

        return testo.trim();
    }

    /**
     * Ordina la lista <br>
     * L'ordinamento funziona SOLO se la lista è omogenea (oggetti della stessa classe) <br>
     *
     * @param listaDisordinata in ingresso
     *
     * @return lista ordinata, null se listaDisordinata è null
     */
    public List sort(List listaDisordinata) {
        List<Object> objList;
        Object[] objArray = listaDisordinata.toArray();

        try {
            Arrays.sort(objArray);
        } catch (Exception unErrore) {
        }
        objList = fromObj(objArray);

        return objList;
    }


    /**
     * Costruisce una matrice di colonne della grid <br>
     *
     * @param grid da esaminare
     *
     * @return matrice
     */
    public Grid.Column[] getColumnArray(Grid grid) {
        List<Grid.Column> lista = grid.getColumns();
        Grid.Column[] matrice = new Grid.Column[lista.size()];

        for (int k = 0; k < lista.size(); k++) {
            matrice[k] = lista.get(k);
        }

        return matrice;
    }


    public List<String> fromString(String testo) {
        List<String> lista = null;
        String tag = VIRGOLA;
        String[] parti = null;

        if (text.isEmpty(testo)) {
            return null;
        }

        if (text.isValid(testo) && testo.contains(tag)) {
            parti = testo.split(tag);
        }

        if (parti != null && parti.length > 0) {
            lista = Arrays.asList(parti);
        }

        if (lista == null) {
            lista = new ArrayList<>(Arrays.asList(testo));
        }

        return lista;
    }

}