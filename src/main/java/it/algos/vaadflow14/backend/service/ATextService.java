package it.algos.vaadflow14.backend.service;

import com.vaadin.flow.component.html.Label;
import it.algos.vaadflow14.backend.annotation.AIColumn;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import javafx.scene.control.ComboBox;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;


/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-set-2019 18.19.24 <br>
 * <p>
 * Classe di servizio <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private ATextService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ATextService extends AAbstractService {


    /**
     * Null-safe, short-circuit evaluation.
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa è vuota o nulla
     */
    public boolean isEmpty(final String stringa) {
        return stringa == null || stringa.trim().isEmpty();
    }


    /**
     * Null-safe, short-circuit evaluation.
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa esiste e non è vuota
     */
    public boolean isValid(final String stringa) {
        return !isEmpty(stringa);
    }


    /**
     * Controlla che sia una stringa e che sia valida.
     *
     * @param obj in ingresso da controllare
     *
     * @return vero se la stringa esiste è non è vuota
     */
    public boolean isValid(final Object obj) {
        return (obj instanceof String) && !isEmpty((String) obj);
    }


    /**
     * Forza il primo carattere della stringa al carattere maiuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso
     *
     * @return testo formattato in uscita
     */
    public String primaMaiuscola(final String testoIn) {
        String testoOut = isValid(testoIn) ? testoIn.trim() : VUOTA;
        String primoCarattere;

        if (isValid(testoOut)) {
            primoCarattere = testoOut.substring(0, 1).toUpperCase();
            testoOut = primoCarattere + testoOut.substring(1);
        }

        return testoOut.trim();
    }


    /**
     * Costruisce un array da una stringa di valori multipli separati da virgole. <br>
     * Se la stringa è nulla, ritorna un nullo <br>
     * Se la stringa è vuota, ritorna un nullo <br>
     * Se manca la virgola, ritorna un array di un solo valore col testo completo <br>
     * Elimina spazi vuoti iniziali e finali di ogni valore <br>
     *
     * @param stringaMultipla in ingresso
     *
     * @return lista di singole stringhe
     */
    public List<String> getArray(String stringaMultipla) {
        List<String> lista = new ArrayList<>();
        String tag = VIRGOLA;
        String[] parti;

        if (isEmpty(stringaMultipla)) {
            return null;
        }

        if (stringaMultipla.contains(tag)) {
            parti = stringaMultipla.split(tag);
            for (String value : parti) {
                lista.add(value.trim());
            }
        } else {
            lista.add(stringaMultipla);
        }

        return lista;
    }


    /**
     * Costruisce un array di interi da una stringa di valori multipli separati da virgole. <br>
     * Se la stringa è nulla, ritorna un nullo <br>
     * Se la stringa è vuota, ritorna una stringa vuota <br>
     * Se manca la virgola, ritorna il valore fornito <br>
     *
     * @param stringaMultipla in ingresso
     *
     * @return lista di singoli interi
     */
    public List<Integer> getArrayInt(String stringaMultipla) {
        List<Integer> lista = new ArrayList<>();
        String tag = VIRGOLA;
        String[] parti;

        if (isEmpty(stringaMultipla)) {
            return null;
        }

        if (stringaMultipla.contains(tag)) {
            parti = stringaMultipla.split(tag);
            for (String value : parti) {
                lista.add(Integer.parseInt(value.trim()));
            }
        } else {
            lista.add(Integer.parseInt(stringaMultipla.trim()));
        }

        return lista;
    }


    /**
     * Elimina dal testo il tagIniziale, se esiste <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagIniziale è vuoto, restituisce il testo <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn     ingresso
     * @param tagIniziale da eliminare
     *
     * @return testo ridotto in uscita
     */
    public String levaTesta(String testoIn, String tagIniziale) {
        String testoOut = testoIn.trim();

        if (this.isValid(testoOut) && this.isValid(tagIniziale)) {
            tagIniziale = tagIniziale.trim();
            if (testoOut.startsWith(tagIniziale)) {
                testoOut = testoOut.substring(tagIniziale.length());
            }
        }

        return testoOut.trim();
    }


    /**
     * Elimina il testo prima di tagIniziale. <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagIniziale è vuoto, restituisce il testo <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn       ingresso
     * @param tagIniziale da dove inizia il testo da tenere
     *
     * @return testo ridotto in uscita
     */
    public String levaTestaDa(final String testoIn, String tagIniziale) {
        String testoOut = testoIn.trim();

        if (this.isValid(testoOut) && this.isValid(tagIniziale)) {
            tagIniziale = tagIniziale.trim();
            if (testoOut.contains(tagIniziale)) {
                testoOut = testoOut.substring(testoOut.indexOf(tagIniziale)+tagIniziale.length());
            }
        }

        return testoOut.trim();
    }

    /**
     * Elimina dal testo il tagFinale, se esiste. <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagFinale è vuoto, restituisce il testo <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn   ingresso
     * @param tagFinale da eliminare
     *
     * @return testo ridotto in uscita
     */
    public String levaCoda(final String testoIn, String tagFinale) {
        String testoOut = testoIn.trim();

        if (this.isValid(testoOut) && this.isValid(tagFinale)) {
            tagFinale = tagFinale.trim();
            if (testoOut.endsWith(tagFinale)) {
                testoOut = testoOut.substring(0, testoOut.length() - tagFinale.length());
            }
        }

        return testoOut.trim();
    }


    /**
     * Elimina il testo da tagFinale in poi. <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagInterrompi è vuoto, restituisce il testo <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn       ingresso
     * @param tagInterrompi da dove inizia il testo da eliminare
     *
     * @return testo ridotto in uscita
     */
    public String levaCodaDa(final String testoIn, String tagInterrompi) {
        String testoOut = testoIn.trim();

        if (this.isValid(testoOut) && this.isValid(tagInterrompi)) {
            tagInterrompi = tagInterrompi.trim();
            if (testoOut.contains(tagInterrompi)) {
                testoOut = testoOut.substring(0, testoOut.lastIndexOf(tagInterrompi));
            }
        }

        return testoOut.trim();
    }


    /**
     * Label colorata
     *
     * @param message    da visualizzare
     * @param labelColor del messaggio
     *
     * @return etichetta visualizzata
     */
    private Label getLabel(String message, String labelColor) {
        return getLabel(message, labelColor, false);
    }


    /**
     * Label colorata
     *
     * @param message    da visualizzare
     * @param labelColor del messaggio
     * @param smallBold  flag per il tipo di style
     *
     * @return etichetta visualizzata
     */
    private Label getLabel(String message, String labelColor, boolean smallBold) {
        Label label = null;

        if (isValid(message)) {
            label = new Label(message);
            label.getElement().getStyle().set("color", labelColor);
        }

        if (smallBold) {
            label.getStyle().set("font-size", "small");
            label.getStyle().set("font-weight", "bold");
        }

        return label;
    }


    /**
     * Label colorata
     *
     * @param message da visualizzare
     *
     * @return etichetta visualizzata
     */
    public Label getLabelHost(String message) {
        return getLabel(message, "black");
    }


    /**
     * Label colorata
     *
     * @param message da visualizzare
     *
     * @return etichetta visualizzata
     */
    public Label getLabelUser(String message) {
        return getLabel(message, "green");
    }


    /**
     * Label colorata
     *
     * @param message da visualizzare
     *
     * @return etichetta visualizzata
     */
    public Label getLabelAdmin(String message) {
        return getLabel(message, "blue");
    }


    /**
     * Label colorata
     *
     * @param message da visualizzare
     *
     * @return etichetta visualizzata
     */
    public Label getLabelDev(String message) {
        return getLabel(message, "red");
    }


    /**
     * Label colorata
     *
     * @param message da visualizzare
     *
     * @return etichetta visualizzata
     */
    public Label getLabelHostSmall(String message) {
        return getLabel(message, "black", true);
    }


    /**
     * Label colorata
     *
     * @param message da visualizzare
     *
     * @return etichetta visualizzata
     */
    public Label getLabelUserSmall(String message) {
        return getLabel(message, "green", true);
    }


    /**
     * Label colorata
     *
     * @param message da visualizzare
     *
     * @return etichetta visualizzata
     */
    public Label getLabelAdminSmall(String message) {
        return getLabel(message, "blue", true);
    }


    /**
     * Label colorata
     *
     * @param message da visualizzare
     *
     * @return etichetta visualizzata
     */
    public Label getLabelDevSmall(String message) {
        return getLabel(message, "red", true);
    }


    /**
     * Estrae una matrice da singole stringhe separate da virgola. <br>
     *
     * @param stringaMultipla di stringhe separate da virgola
     *
     * @return matrice di stringhe
     */
    public String[] getMatrice(String stringaMultipla) {
        String[] matrice = null;
        List<String> lista = getArray(stringaMultipla);

        if (lista != null) {
            matrice = lista.toArray(new String[lista.size()]);
        }

        return matrice;
    }


    /**
     * Estrae una matrice di interi da singole stringhe separate da virgola. <br>
     *
     * @param stringaMultipla di stringhe separate da virgola
     *
     * @return matrice di interi
     */
    public Integer[] getMatriceInt(String stringaMultipla) {
        Integer[] matrice = null;
        List<Integer> lista = getArrayInt(stringaMultipla);

        if (lista != null) {
            matrice = lista.toArray(new Integer[lista.size()]);
        }

        return matrice;
    }


    /**
     * Elimina (eventuali) doppie graffe in testa e coda della stringa.
     * Funziona solo se le graffe sono esattamente in TESTA ed in CODA alla stringa
     * Se arriva una stringa vuota, restituisce una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param stringaIn in ingresso
     *
     * @return stringa con doppie graffe eliminate
     */
    public String setNoGraffe(String stringaIn) {
        String stringaOut = stringaIn;

        if (stringaIn != null && stringaIn.length() > 0) {
            stringaOut = stringaIn.trim();
            stringaOut = levaTesta(stringaOut, GRAFFA_INI);
            stringaOut = levaTesta(stringaOut, GRAFFA_INI);
            stringaOut = levaCoda(stringaOut, GRAFFA_END);
            stringaOut = levaCoda(stringaOut, GRAFFA_END);
        }// fine del blocco if

        return stringaOut.trim();
    }


    /**
     * Elimina (eventuali) parentesi quadre in testa e coda della stringa. <br>
     * Funziona solo se le quadre sono esattamente in TESTA ed in CODA alla stringa <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa con doppie quadre eliminate
     */
    public String setNoQuadre(String stringaIn) {
        String stringaOut = stringaIn.trim();
        int cicli = 3;

        if (this.isValid(stringaOut)) {
            for (int k = 0; k < cicli; k++) {
                stringaOut = this.levaTesta(stringaOut, QUADRA_INI);
                stringaOut = this.levaCoda(stringaOut, QUADRA_END);
            }
        }

        return stringaOut.trim();
    }


    /**
     * Aggiunge parentesi quadre in testa e coda alla stringa. <br>
     * Aggiunge SOLO se gia non esistono <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali <br>
     * Elimina eventuali quadre già presenti, per evitare di metterle doppie <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa con parentesi quadre aggiunte
     */
    public String setQuadre(String stringaIn) {
        String stringaOut = stringaIn;
        int cicli = 3;

        if (this.isValid(stringaOut)) {
            stringaOut = this.setNoQuadre(stringaIn);
            if (this.isValid(stringaOut)) {
                if (!stringaOut.startsWith(QUADRA_INI)) {
                    stringaOut = QUADRA_INI + stringaOut;
                }
                if (!stringaOut.endsWith(QUADRA_END)) {
                    stringaOut = stringaOut + QUADRA_END;
                }
            }
        }

        return stringaOut.trim();
    }


    /**
     * Aggiunge doppie parentesi quadre in testa e coda alla stringa. <br>
     * Aggiunge SOLO se gia non esistono (ne doppie, ne singole) <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali <br>
     * Elimina eventuali quadre già presenti, per evitare di metterle doppie <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa con doppie parentesi quadre aggiunte
     */
    public String setDoppieQuadre(String stringaIn) {
        String stringaOut = stringaIn;

        if (stringaIn != null && stringaIn.length() > 0) {
            stringaOut = this.setNoQuadre(stringaIn);
            if (this.isValid(stringaOut)) {
                if (!stringaOut.startsWith(QUADRA_INI)) {
                    stringaOut = QUADRE_INI + stringaOut;
                }
                if (!stringaOut.endsWith(QUADRA_END)) {
                    stringaOut = stringaOut + QUADRE_END;
                }
            }
        }

        return stringaOut.trim();
    }

    /**
     * Elimina (eventuali) 'apici'' in testa ed in coda alla stringa. <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa senza apici iniziali e finali
     */
    public String setNoApici(String stringaIn) {
        String stringaOut = stringaIn.trim();
        String apice="'";
        int cicli = 4;

        if (this.isValid(stringaOut)) {
            for (int k = 0; k < cicli; k++) {
                stringaOut = this.levaTesta(stringaOut, apice);
                stringaOut = this.levaCoda(stringaOut, apice);
            }
        }

        return stringaOut.trim();
    }

    /**
     * Allunga un testo alla lunghezza desiderata. <br>
     * Se è più corta, aggiunge spazi vuoti <br>
     * Se è più lungo, rimane inalterato <br>
     * La stringa in ingresso viene 'giustificata' a sinistra <br>
     * Vengono eliminati gli spazi vuoti che precedono la stringa <br>
     *
     * @param testoIn ingresso
     *
     * @return testo della 'lunghezza' richiesta
     */

    public String rightPad(final String testoIn, int size) {
        String testoOut = testoIn.trim();
        testoOut = StringUtils.rightPad(testoOut, size);
        return testoOut;
    }

    /**
     * Allunga un testo alla lunghezza desiderata. <br>
     * Se è più corta, antepone spazi vuoti <br>
     * Se è più lungo, rimane inalterato <br>
     * La stringa in ingresso viene 'giustificata' a destra <br>
     * Vengono eliminati gli spazi vuoti che seguono la stringa <br>
     *
     * @param testoIn ingresso
     *
     * @return testo della 'lunghezza' richiesta
     */

    /**
     * Forza un testo alla lunghezza desiderata. <br>
     * Se è più corta, aggiunge spazi vuoti <br>
     * Se è più lungo, lo tronca <br>
     * La stringa in ingresso viene 'giustificata' a sinistra <br>
     * Vengono eliminati gli spazi vuoti che precedono la stringa <br>
     *
     * @param testoIn ingresso
     *
     * @return testo della 'lunghezza' richiesta
     */

    public String fixSize(final String testoIn, int size) {
        String testoOut = testoIn.trim();
        testoOut = rightPad(testoIn, size);

        if (testoOut.length() > size) {
            testoOut = testoOut.substring(0, size);
        }

        return testoOut;
    }


    /**
     * Forza un testo alla lunghezza desiderata ed aggiunge parentesi quadre in testa e coda. <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa con lunghezza prefissata e parentesi quadre aggiunte
     */
    public String fixSizeQuadre(String stringaIn, int size) {
        String stringaOut = stringaIn;
        int cicli = 3;

        if (this.isValid(stringaOut)) {
            stringaOut = this.setNoQuadre(stringaIn);
            stringaOut = rightPad(stringaOut, size);
            stringaOut = fixSize(stringaOut, size);
            if (this.isValid(stringaOut)) {
                if (!stringaOut.startsWith(QUADRA_INI)) {
                    stringaOut = QUADRA_INI + stringaOut;
                }
                if (!stringaOut.endsWith(QUADRA_END)) {
                    stringaOut = stringaOut + QUADRA_END;
                }
            }
        }

        return stringaOut.trim();
    }


    /**
     * Elimina gli spazi della stringa <br>
     *
     * @param stringaIn ingresso
     *
     * @return etichetta visualizzata
     */
    public String levaSpazi(String stringaIn) {
        String stringaOut = stringaIn;
        String tag = SPAZIO;

        if (stringaIn.contains(tag)) {
            stringaOut = stringaIn.replaceAll(tag, VUOTA);
        }

        return stringaOut;
    }

    /**
     * Get the width of the property.
     *
     * @param widthInt larghezza espressa come intero
     *
     * @return larghezza espressa come stringa
     */
    public String getColumnWith(int widthInt) {
        String widthTxt = VUOTA;

        if (widthInt > 0) {
            widthTxt = widthInt + TAG_EM;
        }

        return widthTxt;
    }

}