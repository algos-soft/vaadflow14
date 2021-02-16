package it.algos.vaadflow14.backend.service;

import com.vaadin.flow.component.html.*;
import it.algos.vaadflow14.backend.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 16-feb-2021
 * Time: 17:38
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AHtmlService.class); <br>
 * 3) @Autowired public AHtmlService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AHtmlService extends AAbstractService {


    /**
     * Span
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpan(final String message) {
        return getSpan(message, null);
    }

    /**
     * Span
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpan(final String message, final AIType... typeSpan) {
        Span span = new Span();
        String lineHeight = "14pt";//@todo Volendo si può mettere in preferenza

        if (text.isValid(message)) {
            span.setText(message);

            if (typeSpan != null && typeSpan.length > 0) {
                for (AIType type : typeSpan) {
                    span.getElement().getStyle().set(type.getTag(), type.getValue());
                }
            }
            span.getElement().getStyle().set("line-height", lineHeight);
        }

        return span;
    }

    /**
     * Span colorato
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    private Span getSpanBase(final String message, AETypeColor typeColor,final AIType... typeSpan) {
        List<AIType> typeList = new ArrayList(Arrays.asList(typeColor));

        if (typeSpan != null && typeSpan.length > 0) {
            for (AIType type : typeSpan) {
                typeList.add(type);
            }
        }

        return getSpan(message, typeList.toArray(new AIType[typeList.size()]));
    }

    /**
     * Span colorato
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpanVerde(final String message, final AIType... typeSpan) {
        List<AIType> typeList = new ArrayList(Arrays.asList(AETypeColor.verde));

        if (typeSpan != null && typeSpan.length > 0) {
            for (AIType type : typeSpan) {
                typeList.add(type);
            }
        }

        return getSpan(message, typeList.toArray(new AIType[typeList.size()]));
    }


    /**
     * Span colorato
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpanVerde(final String message) {
        return getSpanVerde(message, null);
    }

    /**
     * Span colorato
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpanBlu(final String message, final AIType... typeSpan) {
        List<AIType> typeList = new ArrayList(Arrays.asList(AETypeColor.blu));

        if (typeSpan != null && typeSpan.length > 0) {
            for (AIType type : typeSpan) {
                typeList.add(type);
            }
        }

        return getSpan(message, typeList.toArray(new AIType[typeList.size()]));
    }

    /**
     * Span colorato
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpanBlu(final String message) {
        return getSpanBlu(message, null);
    }

    /**
     * Span colorato
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpanRosso(final String message, final AIType... typeSpan) {
        List<AIType> typeList = new ArrayList(Arrays.asList(AETypeColor.rosso));

        if (typeSpan != null && typeSpan.length > 0) {
            for (AIType type : typeSpan) {
                typeList.add(type);
            }
        }

        return getSpan(message, typeList.toArray(new AIType[typeList.size()]));
    }

    /**
     * Span colorato
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpanRosso(final String message) {
        return getSpanRosso(message, null);
    }

    /**
     * Elimina un tag HTML in testa e coda della stringa. <br>
     * Funziona solo se i tags sono esattamente in TESTA ed in CODA alla stringa <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param stringaIn in ingresso
     * @param tag       html iniziale
     *
     * @return stringa con tags eliminati
     */
    public String setNoHtmlTag(String stringaIn, String tag) {
        String stringaOut = stringaIn;
        String tagIni = "<" + tag + ">";
        String tagEnd = "</" + tag + ">";

        if (text.isValid(stringaIn)) {
            stringaIn = stringaIn.trim();

            if (stringaIn.startsWith(tagIni) && stringaIn.endsWith(tagEnd)) {
                stringaOut = stringaIn;
                stringaOut = text.levaCoda(stringaOut, tagEnd);
                stringaOut = text.levaTesta(stringaOut, tagIni);
            }
        }

        return stringaOut.trim();
    }

}