package it.algos.vaadflow14.backend.service;

import it.algos.vaadflow14.backend.enumeration.AEMese;
import it.algos.vaadflow14.backend.enumeration.AETime;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mar, 05-mag-2020
 * Time: 10:15
 * <p>
 * Classe di servizio <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private ADateService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ADateService extends AAbstractService {


    /**
     * Convert java.util.Date to java.time.LocalDate <br>
     * Date HA ore, minuti e secondi <br>
     * LocalDate NON ha ore, minuti e secondi <br>
     * Si perdono quindi le ore i minuti ed i secondi di Date <br>
     * Usare alternativamente dateToLocalDateTime <br>
     *
     * @param data da convertire
     *
     * @return data locale (deprecated)
     */
    @Deprecated
    public LocalDate dateToLocalDate(Date data) {
        Instant instant = Instant.ofEpochMilli(data.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }


    /**
     * Convert java.time.LocalDate to java.util.Date
     * LocalDate NON ha ore, minuti e secondi
     * Date HA ore, minuti e secondi
     * La Date ottenuta ha il tempo regolato a mezzanotte
     *
     * @param localDate da convertire
     *
     * @return data (deprecated)
     */
    @Deprecated
    public Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }


    /**
     * Convert java.time.LocalDateTime to java.util.Date
     * LocalDateTime HA ore, minuti e secondi
     * Date HA ore, minuti e secondi
     * Non si perde nulla
     *
     * @param localDateTime da convertire
     *
     * @return data (deprecated)
     */
    @Deprecated
    public Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }


    /**
     * Convert java.time.LocalDate to java.time.LocalDateTime
     * LocalDate NON ha ore, minuti e secondi
     * LocalDateTime HA ore, minuti e secondi
     * La LocalDateTime ottenuta ha il tempo regolato a mezzanotte
     *
     * @param localDate da convertire
     *
     * @return data con ore e minuti alla mezzanotte
     */
    @Deprecated
    public LocalDateTime localDateToLocalDateTime(LocalDate localDate) {
        Date date = localDateToDate(localDate);
        Instant istante = date.toInstant();
        return istante.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    /**
     * Convert java.util.Date to java.time.LocalDateTime
     * Date HA ore, minuti e secondi
     * LocalDateTime HA ore, minuti e secondi
     * Non si perde nulla
     *
     * @param data da convertire
     *
     * @return data e ora locale
     */
    public LocalDateTime dateToLocalDateTime(Date data) {
        Instant instant = Instant.ofEpochMilli(data.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }


    /**
     * Convert java.time.LocalDateTime to java.time.LocalDate
     * LocalDateTime HA ore, minuti e secondi
     * LocalDate NON ha ore, minuti e secondi
     * Si perdono quindi le ore i minuti ed i secondi di Date
     *
     * @param localDateTime da convertire
     *
     * @return data con ore e minuti alla mezzanotte
     */
    public LocalDate localDateTimeToLocalDate(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toLocalDate() : null;
    }


    /**
     * Trasforma la data nel formato standard ISO 8601.
     * <p>
     * 2017-02-16T21:00:00
     * Unsupported field: OffsetSeconds
     * Dovrebbe essere 2017-02-16T21:00:00.000+01:00 per essere completa
     *
     * @param localDate fornita
     *
     * @return testo standard ISO senza OffsetSeconds
     */
    public String getISO(LocalDate localDate) {
        return getISO(localDateToLocalDateTime(localDate));
    }


    /**
     * Trasforma la data e l'0rario nel formato standard ISO 8601.
     * <p>
     * 2017-02-16T21:00:00
     * Unsupported field: OffsetSeconds
     * Dovrebbe essere 2017-02-16T21:00:00.000+01:00 per essere completa
     *
     * @param localDateTime fornito
     *
     * @return testo standard ISO senza OffsetSeconds
     */
    public String getISO(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(AETime.iso8601.getPattern(), LOCALE));
    }


    /**
     * Costruisce una data da una stringa in formato ISO 8601
     *
     * @param isoStringa da leggere
     *
     * @return data costruita
     */
    private Date dateFromISO(String isoStringa) {
        Date data = null;
        DateFormat format = new SimpleDateFormat(AETime.iso8601.getPattern());

        try {
            data = format.parse(isoStringa);
        } catch (Exception unErrore) {
            logger.error(unErrore, this.getClass(), "dateFromISO");

        }

        return data;
    }


    /**
     * Costruisce una localData da una stringa in formato ISO 8601
     * ATTENZIONE: si perdono ore, minuti e secondi (se ci sono)
     *
     * @param isoStringa da leggere
     *
     * @return localData costruita
     */
    public LocalDate localDateFromISO(String isoStringa) {
        return dateToLocalDate(dateFromISO(isoStringa));
    }


    /**
     * Costruisce una localDateTime da una stringa in formato ISO 8601
     *
     * @param isoStringa da leggere
     *
     * @return localDateTime costruita
     */
    public LocalDateTime localDateTimeFromISO(String isoStringa) {
        return dateToLocalDateTime(dateFromISO(isoStringa));
    }


    /**
     * Convert java.util.Date to java.time.LocalDateTime
     * Date HA ore, minuti e secondi
     * LocalDateTime HA ore, minuti e secondi
     * Non si perde nulla
     *
     * @param data da convertire
     *
     * @return data e ora locale
     */
    public LocalDateTime dateToLocalDateTimeUTC(Date data) {
        Instant instant = Instant.ofEpochMilli(data.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }


    /**
     * Convert java.util.LocalDateTime to java.time.LocalTime
     * Estrae la sola parte di Time
     * LocalDateTime HA anni, giorni, ore, minuti e secondi
     * LocalTime NON ha anni e giorni
     * Si perdono quindi gli anni ed i giorni di LocalDateTime
     *
     * @param localDateTime da convertire
     *
     * @return time senza il giorno
     */
    public LocalTime localDateTimeToLocalTime(LocalDateTime localDateTime) {
        return LocalTime.of(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
    }


    /**
     * Restituisce la data corrente nella forma del pattern standard. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @return la data sotto forma di stringa
     */
    public String get() {
        return get(LocalDate.now());
    }


    /**
     * Restituisce la data nella forma del pattern standard. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalDate localDate) {
        return get(localDate, AETime.standard);
    }


    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDate da rappresentare
     * @param pattern   per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalDate localDate, AETime pattern) {
        if (pattern.isSenzaTime()) {
            return get(localDate, pattern.getPattern());
        } else {
            return VUOTA;
        }
    }


    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDateTime da rappresentare
     * @param pattern       per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalDateTime localDateTime, AETime pattern) {
        if (pattern.isSenzaTime()) {
            return VUOTA;
        } else {
            return get(localDateTime, pattern.getPattern());
        }
    }


    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDate da rappresentare
     * @param pattern   per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalDate localDate, String pattern) {
        if (localDate != null) {
            return localDate.format(DateTimeFormatter.ofPattern(pattern, LOCALE));
        } else {
            return VUOTA;
        }
    }


    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDateTime da rappresentare
     * @param pattern       per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalDateTime localDateTime, String pattern) {
        if (localDateTime != null) {
            return localDateTime.format(DateTimeFormatter.ofPattern(pattern, LOCALE));
        } else {
            return VUOTA;
        }
    }


    /**
     * Restituisce la data nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     * Not using leading zeroes in day <br>
     * Two numbers for year <b>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getCorta(LocalDate localDate) {
        return get(localDate, AETime.dateShort);
    }


    /**
     * Restituisce la data nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getNormale(LocalDate localDate) {
        return get(localDate, AETime.dateNormal);
    }

    /**
     * Restituisce la data nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getLunga(LocalDate localDate) {
        return get(localDate, AETime.dateLong);
    }


    /**
     * Restituisce la data nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getCompleta(LocalDate localDate) {
        return get(localDate, AETime.completa);
    }

    /**
     * Restituisce la data e l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getDataOrario(LocalDateTime localDateTime) {
        return get(localDateTime, AETime.normaleOrario);
    }

    /**
     * Restituisce la data e l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getDataOrarioCompleta(LocalDateTime localDateTime) {
        return get(localDateTime, AETime.completaOrario);
    }

    /**
     * Restituisce l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getOrario(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern(AETime.orario.getPattern(), LOCALE));
    }


    /**
     * Restituisce l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getOrario(LocalDateTime localDateTime) {
        return getOrario(localDateTimeToLocalTime(localDateTime));
    }


    /**
     * Restituisce l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getOrarioCompleto(LocalDateTime localDateTime) {
        return localDateTimeToLocalTime(localDateTime).format(DateTimeFormatter.ofPattern(AETime.orarioLungo.getPattern(), LOCALE));
    }


    /**
     * Costruisce tutti i giorni del mese <br>
     * Considera anche l'anno bisestile <br>
     * <p>
     * Restituisce un array di Map <br>
     * Ogni mappa ha: <br>
     * numeroMese <br>
     * nomeMese <br>
     * #progressivoNormale <br>
     * #progressivoBisestile <br>
     * nome  (numero per il primo del mese) <br>
     * titolo (1° per il primo del mese) <br>
     *
     * @param numMese  numero del mese, partendo da 1 per gennaio
     * @param progAnno numero del giorno nell'anno, partendo da 1 per il 1° gennaio
     *
     * @return lista di mappe, una per ogni giorno del mese considerato
     */
    private List<HashMap> getGiorniMese(int numMese, int progAnno) {
        List<HashMap> listaMese = new ArrayList<HashMap>();
        HashMap mappa;
        int giorniDelMese;
        String nomeMese;
        AEMese mese = AEMese.getMese(numMese);
        nomeMese = AEMese.getLong(numMese);
        giorniDelMese = AEMese.getGiorni(numMese, 2016);
        final int taglioBisestile = 60;
        String tag;
        String tagUno;

        for (int k = 1; k <= giorniDelMese; k++) {
            progAnno++;
            tag = k + SPAZIO + nomeMese;
            mappa = new HashMap();

            mappa.put(KEY_MAPPA_GIORNI_MESE_NUMERO, numMese);
            mappa.put(KEY_MAPPA_GIORNI_MESE_TESTO, nomeMese);
            mappa.put(KEY_MAPPA_GIORNI_NOME, tag);
            mappa.put(KEY_MAPPA_GIORNI_BISESTILE, progAnno);
            mappa.put(KEY_MAPPA_GIORNI_NORMALE, progAnno);
            mappa.put(KEY_MAPPA_GIORNI_MESE_MESE, mese);

            if (k == 1) {
                mappa.put(KEY_MAPPA_GIORNI_TITOLO, PRIMO_GIORNO_MESE + SPAZIO + nomeMese);
            } else {
                mappa.put(KEY_MAPPA_GIORNI_TITOLO, tag);
            }
            //--gestione degli anni bisestili
            if (progAnno == taglioBisestile) {
                mappa.put(KEY_MAPPA_GIORNI_NORMALE, 0);
            }
            if (progAnno > taglioBisestile) {
                mappa.put(KEY_MAPPA_GIORNI_NORMALE, progAnno - 1);
            }
            listaMese.add(mappa);
        }
        return listaMese;
    }


    /**
     * Costruisce tutti i giorni dell'anno <br>
     * Considera anche l'anno bisestile <br>
     * <p>
     * Restituisce un array di Map <br>
     * Ogni mappa ha: <br>
     * numeroMese <br>
     * #progressivoNormale <br>
     * #progressivoBisestile <br>
     * nome  (numero per il primo del mese) <br>
     * titolo (1° per il primo del mese) <br>
     */
    public List<HashMap> getAllGiorni() {
        List<HashMap> listaAnno = new ArrayList<HashMap>();
        List<HashMap> listaMese;
        int progAnno = 0;

        for (int k = 1; k <= 12; k++) {
            listaMese = getGiorniMese(k, progAnno);
            for (HashMap mappa : listaMese) {
                listaAnno.add(mappa);
            }
            progAnno += listaMese.size();
        }

        return listaAnno;
    }


    /**
     * Anno bisestile
     *
     * @param anno da validare
     *
     * @return true se l'anno è bisestile
     */
    public boolean bisestile(int anno) {
        boolean bisestile = false;
        int deltaGiuliano = 4;
        int deltaSecolo = 100;
        int deltaGregoriano = 400;
        int inizioGregoriano = 1582;
        boolean bisestileSecolo = false;

        bisestile = math.divisibileEsatto(anno, deltaGiuliano);
        if (anno > inizioGregoriano && bisestile) {
            if (math.divisibileEsatto(anno, deltaSecolo)) {
                if (math.divisibileEsatto(anno, deltaGregoriano)) {
                    bisestile = true;
                } else {
                    bisestile = false;
                }
            }
        }

        return bisestile;
    }

}