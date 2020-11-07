package it.algos.vaadflow14.backend.service;

import it.algos.vaadflow14.backend.enumeration.AECopyDir;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: dom, 28-giu-2020
 * Time: 15:10
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
public class AFileService extends AAbstractService {

    public static final String PARAMETRO_NULLO = "Il parametro in ingresso è nullo";

    public static final String PATH_NULLO = "Il path in ingresso è nullo o vuoto";

    public static final String PATH_NOT_ABSOLUTE = "Il primo carattere del path NON è uno '/' (slash)";

    public static final String NON_ESISTE_FILE = "Il file non esiste";

    public static final String PATH_SENZA_SUFFIX = "Manca il 'suffix' terminale";

    public static final String PATH_FILE_ESISTENTE = "Esiste già il file";

    public static final String NON_E_FILE = "Non è un file";

    public static final String NON_CREATO_FILE = "Il file non è stato creato";

    public static final String NON_COPIATO_FILE = "Il file non è stato copiato";

    public static final String NON_CANCELLATO_FILE = "Il file non è stato cancellato";

    public static final String NON_CANCELLATA_DIRECTORY = "La directory non è stata cancellata";

    public static final String NON_ESISTE_DIRECTORY = "La directory non esiste";

    public static final String NON_CREATA_DIRECTORY = "La directory non è stata creata";

    public static final String NON_E_DIRECTORY = "Non è una directory";

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Controlla l'esistenza di una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param directoryToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteDirectory(File directoryToBeChecked) {
        return isEsisteDirectoryStr(directoryToBeChecked).equals(VUOTA);
    }


    /**
     * Controlla l'esistenza di una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param directoryToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file esiste
     */
    public String isEsisteDirectoryStr(File directoryToBeChecked) {
        if (directoryToBeChecked == null) {
            return PARAMETRO_NULLO;
        }

        if (text.isEmpty(directoryToBeChecked.getName())) {
            return PATH_NULLO;
        }

        if (!directoryToBeChecked.getPath().equals(directoryToBeChecked.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }

        if (directoryToBeChecked.exists()) {
            if (directoryToBeChecked.isDirectory()) {
                return VUOTA;
            } else {
                return NON_E_DIRECTORY;
            }
        } else {
            return NON_ESISTE_DIRECTORY;
        }
    }


    /**
     * Creazioni di una directory 'parent' <br>
     * Se manca il path completo alla creazione di un file, creo la directory 'parent' di quel file <br>
     * Riprovo la creazione del file <br>
     */
    public String creaDirectoryParentAndFile(File unFile) {
        String risposta = NON_CREATO_FILE;
        String parentDirectoryName;
        File parentDirectoryFile = null;
        boolean parentDirectoryCreata = false;

        if (unFile != null) {
            parentDirectoryName = unFile.getParent();
            parentDirectoryFile = new File(parentDirectoryName);
            parentDirectoryCreata = parentDirectoryFile.mkdirs();
        }

        if (parentDirectoryCreata) {
            try { // prova ad eseguire il codice
                unFile.createNewFile();
                risposta = VUOTA;
            } catch (Exception unErrore) { // intercetta l'errore
                System.out.println("Errore nel path per la creazione di un file");
            }
        }

        return risposta;
    }


    /**
     * Controlla l'esistenza di una directory <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathDirectoryToBeChecked path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteDirectory(String absolutePathDirectoryToBeChecked) {
        return isEsisteDirectoryStr(absolutePathDirectoryToBeChecked).equals(VUOTA);
    }


    /**
     * Controlla l'esistenza di una directory <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathDirectoryToBeChecked path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se la directory esiste
     */
    public String isEsisteDirectoryStr(String absolutePathDirectoryToBeChecked) {
        if (text.isEmpty(absolutePathDirectoryToBeChecked)) {
            return PATH_NULLO;
        }

        if (this.isNotSlash(absolutePathDirectoryToBeChecked)) {
            return PATH_NOT_ABSOLUTE;
        }

        return isEsisteDirectoryStr(new File(absolutePathDirectoryToBeChecked));
    }


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteFile(File fileToBeChecked) {
        return isEsisteFileStr(fileToBeChecked).equals(VUOTA);
    }


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param pathDirectoryToBeChecked path completo della directory che DEVE cominciare con '/' SLASH
     * @param fileName                 da controllare
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteFile(String pathDirectoryToBeChecked, String fileName) {
        return isEsisteFile(pathDirectoryToBeChecked + SLASH + fileName);
    }


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeChecked path completo del file che DEVE cominciare con '/' SLASH
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteFile(String absolutePathFileWithSuffixToBeChecked) {
        return isEsisteFileStr(absolutePathFileWithSuffixToBeChecked).equals(VUOTA);
    }


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeChecked path completo del file che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file esiste
     */
    public String isEsisteFileStr(String absolutePathFileWithSuffixToBeChecked) {
        String risposta = VUOTA;

        if (text.isEmpty(absolutePathFileWithSuffixToBeChecked)) {
            return PATH_NULLO;
        }

        if (this.isNotSlash(absolutePathFileWithSuffixToBeChecked)) {
            return PATH_NOT_ABSOLUTE;
        }

        risposta = isEsisteFileStr(new File(absolutePathFileWithSuffixToBeChecked));
        if (!risposta.equals(VUOTA)) {
            if (isEsisteDirectory(new File(absolutePathFileWithSuffixToBeChecked))) {
                return NON_E_FILE;
            }

            if (this.isNotSuffix(absolutePathFileWithSuffixToBeChecked)) {
                return PATH_SENZA_SUFFIX;
            }
        }

        return risposta;
    }


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file esiste
     */
    public String isEsisteFileStr(File fileToBeChecked) {
        if (fileToBeChecked == null) {
            return PARAMETRO_NULLO;
        }

        if (text.isEmpty(fileToBeChecked.getName())) {
            return PATH_NULLO;
        }

        if (!fileToBeChecked.getPath().equals(fileToBeChecked.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }

        if (fileToBeChecked.exists()) {
            if (fileToBeChecked.isFile()) {
                return VUOTA;
            } else {
                return NON_E_FILE;
            }
        } else {
            if (this.isNotSuffix(fileToBeChecked.getAbsolutePath())) {
                return PATH_SENZA_SUFFIX;
            }

            if (!fileToBeChecked.exists()) {
                return NON_ESISTE_FILE;
            }

            return VUOTA;
        }
    }


    /**
     * Crea un nuovo file
     * <p>
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Se manca la directory, viene creata dal System <br>
     *
     * @param absolutePathFileWithSuffixToBeCreated path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return true se il file è stato creato, false se non sono rispettate le condizioni della richiesta
     */
    public boolean creaFile(String absolutePathFileWithSuffixToBeCreated) {
        return creaFileStr(absolutePathFileWithSuffixToBeCreated).equals(VUOTA);
    }


    /**
     * Crea un nuovo file
     * <p>
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Se manca la directory, viene creata dal System <br>
     *
     * @param absolutePathFileWithSuffixToBeCreated path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public String creaFileStr(String absolutePathFileWithSuffixToBeCreated) {

        if (text.isEmpty(absolutePathFileWithSuffixToBeCreated)) {
            return PATH_NULLO;
        }

        return creaFileStr(new File(absolutePathFileWithSuffixToBeCreated));
    }


    /**
     * Crea un nuovo file
     * <p>
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Se manca la directory, viene creata dal System <br>
     *
     * @param fileToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file è stato creato, false se non sono rispettate le condizioni della richiesta
     */
    public boolean creaFile(File fileToBeCreated) {
        return creaFileStr(fileToBeCreated).equals(VUOTA);
    }


    /**
     * Crea un nuovo file
     * <p>
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Se manca la directory, viene creata dal System <br>
     *
     * @param fileToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public String creaFileStr(File fileToBeCreated) {
        if (fileToBeCreated == null) {
            return PARAMETRO_NULLO;
        }

        if (text.isEmpty(fileToBeCreated.getName())) {
            return PATH_NULLO;
        }

        if (!fileToBeCreated.getPath().equals(fileToBeCreated.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }

        if (this.isNotSuffix(fileToBeCreated.getAbsolutePath())) {
            return PATH_SENZA_SUFFIX;
        }

        try {
            fileToBeCreated.createNewFile();
        } catch (Exception unErrore) {
            return creaDirectoryParentAndFile(fileToBeCreated);
        }

        return fileToBeCreated.exists() ? VUOTA : NON_CREATO_FILE;
    }


    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeCreated path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stata creata, false se non sono rispettate le condizioni della richiesta
     */
    public boolean creaDirectory(String absolutePathDirectoryToBeCreated) {
        return creaDirectoryStr(absolutePathDirectoryToBeCreated).equals(VUOTA);
    }


    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeCreated path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public String creaDirectoryStr(String absolutePathDirectoryToBeCreated) {
        if (text.isEmpty(absolutePathDirectoryToBeCreated)) {
            return PATH_NULLO;
        }

        return creaDirectoryStr(new File(absolutePathDirectoryToBeCreated));
    }


    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param directoryToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stata creata, false se non sono rispettate le condizioni della richiesta
     */
    public boolean creaDirectory(File directoryToBeCreated) {
        return creaDirectoryStr(directoryToBeCreated).equals(VUOTA);
    }


    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param directoryToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public String creaDirectoryStr(File directoryToBeCreated) {
        if (directoryToBeCreated == null) {
            return PARAMETRO_NULLO;
        }

        if (text.isEmpty(directoryToBeCreated.getName())) {
            return PATH_NULLO;
        }

        if (!directoryToBeCreated.getPath().equals(directoryToBeCreated.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }

        if (!this.isNotSuffix(directoryToBeCreated.getAbsolutePath())) {
            return NON_E_DIRECTORY;
        }

        try { // prova ad eseguire il codice
            directoryToBeCreated.mkdirs();
        } catch (Exception unErrore) { // intercetta l'errore
            return NON_CREATA_DIRECTORY;
        }

        return directoryToBeCreated.exists() ? VUOTA : NON_CREATA_DIRECTORY;
    }


    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeCanceled path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return true se il file è stato cancellato oppure non esisteva
     */
    public boolean deleteFile(String absolutePathFileWithSuffixToBeCanceled) {
        return deleteFileStr(absolutePathFileWithSuffixToBeCanceled).equals(VUOTA);
    }


    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeCanceled path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return testo di errore, vuoto se il file è stato cancellato
     */
    public String deleteFileStr(String absolutePathFileWithSuffixToBeCanceled) {
        if (text.isEmpty(absolutePathFileWithSuffixToBeCanceled)) {
            return PATH_NULLO;
        }

        return deleteFileStr(new File(absolutePathFileWithSuffixToBeCanceled));
    }


    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file è stato cancellato oppure non esisteva
     */
    public boolean deleteFile(File fileToBeDeleted) {
        return deleteFileStr(fileToBeDeleted).equals(VUOTA);
    }


    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public String deleteFileStr(File fileToBeDeleted) {

        if (fileToBeDeleted == null) {
            return PARAMETRO_NULLO;
        }

        if (text.isEmpty(fileToBeDeleted.getName())) {
            return PATH_NULLO;
        }

        if (!fileToBeDeleted.getPath().equals(fileToBeDeleted.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }

        if (this.isNotSuffix(fileToBeDeleted.getAbsolutePath())) {
            return PATH_SENZA_SUFFIX;
        }

        if (!fileToBeDeleted.exists()) {
            return NON_ESISTE_FILE;
        }

        if (fileToBeDeleted.delete()) {
            return VUOTA;
        } else {
            return NON_CANCELLATO_FILE;
        }

    }


    /**
     * Cancella una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeDeleted path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stato cancellato oppure non esisteva
     */
    public boolean deleteDirectory(String absolutePathDirectoryToBeDeleted) {
        return deleteDirectoryStr(absolutePathDirectoryToBeDeleted).equals(VUOTA);
    }


    /**
     * Cancella una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeDeleted path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se la directory è stata cancellata
     */
    public String deleteDirectoryStr(String absolutePathDirectoryToBeDeleted) {
        if (text.isEmpty(absolutePathDirectoryToBeDeleted)) {
            return PATH_NULLO;
        }

        return deleteDirectoryStr(new File(absolutePathDirectoryToBeDeleted));
    }


    /**
     * Cancella una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param directoryToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stata cancellata oppure non esisteva
     */
    public boolean deleteDirectory(File directoryToBeDeleted) {
        return deleteDirectoryStr(directoryToBeDeleted).equals(VUOTA);
    }


    /**
     * Cancella una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed iniziare con uno 'slash' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param directoryToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se la directory è stata cancellata
     */
    public String deleteDirectoryStr(File directoryToBeDeleted) {
        if (directoryToBeDeleted == null) {
            return PARAMETRO_NULLO;
        }

        if (text.isEmpty(directoryToBeDeleted.getName())) {
            return PATH_NULLO;
        }

        if (!directoryToBeDeleted.getPath().equals(directoryToBeDeleted.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }

        if (!directoryToBeDeleted.exists()) {
            return NON_ESISTE_DIRECTORY;
        }

        if (directoryToBeDeleted.delete()) {
            return VUOTA;
        } else {
            try {
                FileUtils.deleteDirectory(directoryToBeDeleted);
                return VUOTA;
            } catch (Exception unErrore) {
                logger.error(unErrore, this.getClass(), "deleteDirectoryStr");
                return NON_CANCELLATA_DIRECTORY;
            }
        }
    }


    /**
     * Copia un file
     * <p>
     * Se manca il file sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste il file destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo del file sorgente
     * @param destPath nome completo del file destinazione
     *
     * @return true se il file è stato copiato
     */
    @Deprecated
    public boolean copyFile(String srcPath, String destPath) {
        return copyFileStr(srcPath, destPath) == VUOTA;
    }


    /**
     * Copia un file sovrascrivendolo se già esistente
     * <p>
     * Se manca il file sorgente, non fa nulla <br>
     * Se esiste il file destinazione, lo cancella prima di copiarlo <br>
     *
     * @param srcPath  nome completo del file sorgente
     * @param destPath nome completo del file destinazione
     *
     * @return true se il file è stato copiato
     */
    public boolean copyFileDeletingAll(String srcPath, String destPath) {
        if (!isEsisteFile(srcPath)) {
            return false;
        }

        if (isEsisteFile(destPath)) {
            deleteFile(destPath);
        }

        return copyFileStr(srcPath, destPath) == VUOTA;
    }


    /**
     * Copia un file solo se non già esistente
     * <p>
     * Se manca il file sorgente, non fa nulla <br>
     * Se esiste il file destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo del file sorgente
     * @param destPath nome completo del file destinazione
     *
     * @return true se il file è stato copiato
     */
    public boolean copyFileOnlyNotExisting(String srcPath, String destPath) {
        return copyFileStr(srcPath, destPath) == VUOTA;
    }


    /**
     * Copia un file
     * <p>
     * Se manca il file sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste il file destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo del file sorgente
     * @param destPath nome completo del file destinazione
     *
     * @return testo di errore, vuoto se il file è stato copiato
     */
    public String copyFileStr(String srcPath, String destPath) {
        String risposta = VUOTA;
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);

        if (!isEsisteFile(srcPath)) {
            return NON_ESISTE_FILE;
        }

        if (isEsisteFile(destPath)) {
            return PATH_FILE_ESISTENTE;
        }

        try { // prova ad eseguire il codice
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            return NON_COPIATO_FILE;
        }

        return risposta;
    }


    /**
     * Copia una directory <br>
     * <p>
     * Controlla che siano validi i path di riferimento <br>
     * Controlla che esista la directory sorgente da copiare <br>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se non esiste la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione ed è AECopyDir.soloSeNonEsiste, non fa nulla <br>
     * Se esiste la directory di destinazione ed è AECopyDir.deletingAll, la cancella e poi la copia <br>
     * Se esiste la directory di destinazione ed è AECopyDir.addingOnly, la integra aggiungendo file/cartelle <br>
     * Nei messaggi di avviso, accorcia il destPath eliminando i primi 3 livelli (/Users/gac/Documents) <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectory(AECopyDir typeCopy, String srcPath, String destPath) {
        boolean copiata = false;
        String message = VUOTA;
        String tag = VUOTA;
        String path;

        if (text.isEmpty(srcPath) || text.isEmpty(destPath)) {
            tag = text.isEmpty(srcPath) ? "srcPath" : "destPath";
            message = "Manca il " + tag + " della directory da copiare.";
        } else {
//            path=destPath.substring(destPath.i)
            if (isEsisteDirectory(srcPath)) {
                switch (typeCopy) {
                    case soloSeNonEsiste:
                        copiata = copyDirectoryOnlyNotExisting(srcPath, destPath);
                        if (copiata) {
                            message = "La directory: " + destPath + " non esisteva ed è stata copiata.";
                        } else {
                            message = "La directory: " + destPath + " esisteva già e non è stata toccata.";
                        }
                        logger.info(message, this.getClass(), "copyDirectory");
                        message = VUOTA;
                        break;
                    case deletingAll:
                        copiata = copyDirectoryDeletingAll(srcPath, destPath);
                        if (copiata) {
                            message = "La directory: " + destPath + " è stata creata/sostituita.";
                            logger.info(message, this.getClass(), "copyDirectory");
                            message = VUOTA;
                        } else {
                            message = "Non sono riuscito a sostituire " + destPath;
                        }
                        break;
                    case addingOnly:
                        copiata = copyDirectoryAddingOnly(srcPath, destPath);
                        if (!copiata) {
                            message = "Non sono riuscito ad integrare la directory: " + destPath;
                        }
                        break;
                    default:
                        copiata = copyDirectoryAddingOnly(srcPath, destPath);
                        logger.warn("Switch - caso non definito", this.getClass(), "copyDirectory");
                        break;
                }
            } else {
                message = "Manca la directory " + srcPath + " da copiare";
            }
        }

        if (!copiata && text.isValid(message)) {
            logger.error(message, this.getClass(), "copyDirectory");
        }

        return copiata;
    }


    /**
     * Copia una directory
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    @Deprecated
    public boolean copyDirectory(String srcPath, String destPath) {
        return copyDirectoryAddingOnly(srcPath, destPath);
    }


    /**
     * Copia una directory sostituendo integralmente quella eventualmente esistente <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, la cancella prima di ricopiarla <br>
     * Tutte i files e le subdirectories originali vengono cancellata <br>
     *
     * @param srcPath  nome parziale del path sorgente
     * @param destPath nome parziale del path destinazione
     * @param dirName  nome della directory da copiare
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryDeletingAll(String srcPath, String destPath, String dirName) {
        return copyDirectoryDeletingAll(srcPath + dirName, destPath + dirName);
    }


    /**
     * Copia una directory sostituendo integralmente quella eventualmente esistente <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, la cancella prima di ricopiarla <br>
     * Tutte i files e le subdirectories originali vengono cancellata <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryDeletingAll(String srcPath, String destPath) {
        File srcDir = new File(srcPath);
        File destDir = new File(destPath);

        if (!isEsisteDirectory(srcPath)) {
            return false;
        }

        if (isEsisteDirectory(destPath)) {
            try {
                FileUtils.forceDelete(new File(destPath));
            } catch (Exception unErrore) {
            }
        }

        if (isEsisteDirectory(destPath)) {
            return false;
        } else {
            try {
                FileUtils.copyDirectory(srcDir, destDir);
                return true;
            } catch (Exception unErrore) {
            }
        }

        return false;
    }


    /**
     * Copia una directory solo se non esisteva <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryOnlyNotExisting(String srcPath, String destPath) {
        if (!isEsisteDirectory(srcPath)) {
            return false;
        }

        if (isEsisteDirectory(destPath)) {
            return false;
        }

        return copyDirectoryDeletingAll(srcPath, destPath);
    }


    /**
     * Copia una directory aggiungendo files e subdirectories a quelli eventualmente esistenti <br>
     * Lascia inalterate subdirectories e files già esistenti <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory destinazione, aggiunge files e subdirectories <br>
     * Tutti i files e le subdirectories esistenti vengono mantenuti <br>
     * Tutte le aggiunte sono ricorsive nelle subdirectories <br>
     *
     * @param srcPath  nome parziale del path sorgente
     * @param destPath nome parziale del path destinazione
     * @param dirName  nome della directory da copiare
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryAddingOnly(String srcPath, String destPath, String dirName) {
        return copyDirectoryAddingOnly(srcPath + dirName, destPath + dirName);
    }


    /**
     * Copia una directory aggiungendo files e subdirectories a quelli eventualmente esistenti <br>
     * Lascia inalterate subdirectories e files già esistenti <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory destinazione, aggiunge files e subdirectories <br>
     * Tutti i files e le subdirectories esistenti vengono mantenuti <br>
     * Tutte le aggiunte sono ricorsive nelle subdirectories <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    public boolean copyDirectoryAddingOnly(String srcPath, String destPath) {
        boolean copiata = false;
        File srcDir = new File(srcPath);
        File destDir = new File(destPath);

        if (!isEsisteDirectory(srcPath)) {
            return false;
        }

        try {
            FileUtils.copyDirectory(srcDir, destDir);
            copiata = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return copiata;
    }


    /**
     * Scrive un file
     * Se non esiste, non fa nulla
     *
     * @param pathFileToBeWritten nome completo del file
     * @param text                contenuto del file
     */
    public boolean scriveNewFile(String pathFileToBeWritten, String text) {
        return scriveFile(pathFileToBeWritten, text, false);
    }


    /**
     * Scrive un file
     * Se non esiste, lo crea
     *
     * @param pathFileToBeWritten nome completo del file
     * @param text                contenuto del file
     * @param sovrascrive         anche se esiste già
     */
    public boolean scriveFile(String pathFileToBeWritten, String testo, boolean sovrascrive) {
        String message = VUOTA;
        File fileToBeWritten;
        FileWriter fileWriter;

        if (isEsisteFile(pathFileToBeWritten)) {
            if (sovrascrive) {
                sovraScriveFile(pathFileToBeWritten, testo);
                System.out.println("Il file " + pathFileToBeWritten + " esisteva già ed è stato aggiornato");
                return true;
            } else {
                System.out.println("Il file " + pathFileToBeWritten + " esisteva già e non è stato modificato");
                return false;
            }
        } else {
            message = creaFileStr(pathFileToBeWritten);
            if (text.isEmpty(message)) {
                sovraScriveFile(pathFileToBeWritten, testo);
                System.out.println("Il file " + pathFileToBeWritten + " non esisteva ed è stato creato");
                return true;
            } else {
                System.out.println("Il file " + pathFileToBeWritten + " non è stato scritto perché " + message);
                return false;
            }
        }

        //        return status;
    }


    /**
     * Sovrascrive un file
     *
     * @param pathFileToBeWritten nome completo del file
     * @param text                contenuto del file
     */
    public boolean sovraScriveFile(String pathFileToBeWritten, String text) {
        boolean status = false;
        File fileToBeWritten;
        FileWriter fileWriter = null;

        if (isEsisteFile(pathFileToBeWritten)) {
            fileToBeWritten = new File(pathFileToBeWritten);
            try {
                fileWriter = new FileWriter(fileToBeWritten);
                fileWriter.write(text);
                fileWriter.flush();
                status = true;
            } catch (Exception unErrore) {
            } finally {
                try {
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                } catch (Exception unErrore) {
                }
            }
        } else {
            System.out.println("Il file " + pathFileToBeWritten + " non esiste e non è stato creato");
        }

        return status;
    }


    /**
     * Legge un file
     *
     * @param pathFileToBeRead nome completo del file
     */
    public String leggeFile(String pathFileToBeRead) {
        String testo = VUOTA;
        String aCapo = A_CAPO;
        String currentLine;

        //-- non va, perché se arriva it/algos/Alfa.java becca anche il .java
        //        nameFileToBeRead=  nameFileToBeRead.replaceAll("\\.","/");

        try (BufferedReader br = new BufferedReader(new FileReader(pathFileToBeRead))) {
            while ((currentLine = br.readLine()) != null) {
                testo += currentLine;
                testo += "\n";
            }

            testo = text.levaCoda(testo, aCapo);
        } catch (Exception unErrore) {
            logger.error(unErrore, this.getClass(), "leggeFile");
        }

        return testo;
    }


    /**
     * Legge un file CSV <br>
     * Prima lista (prima riga): titoli
     * Liste successive (righe successive): valori
     *
     * @param pathFileToBeRead nome completo del file
     *
     * @return lista di liste di valori, senza titoli
     */
    public List<List<String>> leggeListaCSV(String pathFileToBeRead) {
        return leggeListaCSV(pathFileToBeRead, VIRGOLA, A_CAPO);
    }


    /**
     * Legge un file CSV <br>
     * Prima lista (prima riga): titoli
     * Liste successive (righe successive): valori
     *
     * @param pathFileToBeRead nome completo del file
     * @param sepColonna       normalmente una virgola
     * @param sepRiga          normalmente un \n
     *
     * @return lista di liste di valori, senza titoli
     */
    public List<List<String>> leggeListaCSV(String pathFileToBeRead, String sepColonna, String sepRiga) {
        List<List<String>> lista = new ArrayList<>();
        List<String> riga = null;
        String[] righe;
        String[] colonne;

        String testo = leggeFile(pathFileToBeRead);

        if (text.isValid(testo)) {
            righe = testo.split(sepRiga);
            if (righe != null && righe.length > 0) {
                for (String rigaTxt : righe) {
                    riga = null;
                    colonne = rigaTxt.split(sepColonna);
                    if (colonne != null && colonne.length > 0) {
                        riga = new ArrayList<>();
                        for (String colonna : colonne) {
                            riga.add(colonna);
                        }
                    }
                    if (riga != null) {
                        lista.add(riga);
                    }
                }
            }
        }

        return array.isValid(lista) ? lista.subList(1, lista.size()) : lista;
    }


    /**
     * Legge un file CSV <br>
     * Prima lista (prima riga): titoli
     * Liste successive (righe successive): valori
     *
     * @param pathFileToBeRead nome completo del file
     *
     * @return lista di mappe di valori
     */
    public List<LinkedHashMap<String, String>> leggeMappaCSV(String pathFileToBeRead) {
        return leggeMappaCSV(pathFileToBeRead, VIRGOLA, A_CAPO);
    }


    /**
     * Legge un file CSV <br>
     * Prima lista (prima riga): titoli
     * Liste successive (righe successive): valori
     *
     * @param pathFileToBeRead nome completo del file
     * @param sepColonna       normalmente una virgola
     * @param sepRiga          normalmente un \n
     *
     * @return lista di mappe di valori
     */
    public List<LinkedHashMap<String, String>> leggeMappaCSV(String pathFileToBeRead, String sepColonna, String sepRiga) {
        List<LinkedHashMap<String, String>> lista = new ArrayList<>();
        LinkedHashMap<String, String> mappa = null;
        String[] righe;
        String[] titoli;
        String[] colonne;

        String testo = leggeFile(pathFileToBeRead);
        if (text.isValid(testo)) {
            righe = testo.split(sepRiga);
            titoli = righe[0].split(sepColonna);

            if (righe != null && righe.length > 0) {
                for (int k = 1; k < righe.length; k++) {
                    mappa = null;
                    colonne = righe[k].split(sepColonna);
                    if (colonne != null && colonne.length > 0) {
                        mappa = new LinkedHashMap<>();
                        for (int j = 0; j < colonne.length; j++) {
                            if (j < colonne.length) {
                                mappa.put(titoli[j], colonne[j]);
                            }
                        }
                    }
                    if (mappa != null) {
                        lista.add(mappa);
                    }
                }
            }
        }

        return lista;
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<String> getSubDirectoriesAbsolutePathName(String pathDirectoryToBeScanned) {
        List<String> subDirectoryName = new ArrayList<>();
        List<File> subDirectory = getSubDirectories(pathDirectoryToBeScanned);

        if (subDirectory != null) {
            for (File file : subDirectory) {
                subDirectoryName.add(file.getAbsolutePath());
            }
        }

        return subDirectoryName;
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<String> getSubDirectoriesName(String pathDirectoryToBeScanned) {
        List<String> subDirectoryName = new ArrayList<>();
        List<File> subDirectory = getSubDirectories(pathDirectoryToBeScanned);

        if (subDirectory != null) {
            for (File file : subDirectory) {
                subDirectoryName.add(file.getName());
            }
        }

        return subDirectoryName;
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<String> getSubDirectoriesName(File fileSorgente) {
        List<String> subDirectoryName = new ArrayList<>();
        List<File> subDirectory = getSubDirectories(fileSorgente);

        if (subDirectory != null) {
            for (File file : subDirectory) {
                subDirectoryName.add(file.getName());
            }
        }

        return subDirectoryName;
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<File> getSubDirectories(String pathDirectoryToBeScanned) {
        return getSubDirectories(new File(pathDirectoryToBeScanned));
    }


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param directoryToBeScanned della directory
     *
     * @return lista di sub-directory SENZA files
     */
    public List<File> getSubDirectories(File directoryToBeScanned) {
        List<File> subDirectory = new ArrayList<>();
        File[] allFiles = null;

        if (directoryToBeScanned != null) {
            allFiles = directoryToBeScanned.listFiles();
        }

        if (allFiles != null) {
            subDirectory = new ArrayList<>();
            for (File file : allFiles) {
                if (file.isDirectory()) {
                    subDirectory.add(file);
                }
            }
        }

        return subDirectory;
    }


    /**
     * Estrae le sub-directories da un sotto-livello di una directory <br>
     * La dirInterna non è, ovviamente, al primo livello della directory altrimenti chiamerei getSubDirectories <br>
     *
     * @param pathDirectoryToBeScanned della directory
     * @param dirInterna               da scandagliare
     *
     * @return lista di sub-directory SENZA files
     */
    public List<File> getSubSubDirectories(String pathDirectoryToBeScanned, String dirInterna) {
        return getSubSubDirectories(new File(pathDirectoryToBeScanned), dirInterna);
    }


    /**
     * Estrae le sub-directories da un sotto-livello di una directory <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return lista di sub-directory SENZA files
     */
    public List<File> getSubSubDirectories(File directoryToBeScanned, String dirInterna) {
        String subDir = directoryToBeScanned.getAbsolutePath();

        if (subDir.endsWith(SLASH)) {
            subDir = text.levaCoda(subDir, SLASH);
        }

        if (dirInterna.startsWith(SLASH)) {
            dirInterna = text.levaTesta(dirInterna, SLASH);
        }

        String newPath = subDir + SLASH + dirInterna;
        File subFile = new File(newPath);

        return getSubDirectories(subFile);
    }


    /**
     * Controlla se una sotto-directory esiste <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return true se esiste
     */
    public boolean isEsisteSubDirectory(File directoryToBeScanned, String dirInterna) {
        return isEsisteDirectory(directoryToBeScanned.getAbsolutePath() + SLASH + dirInterna);
    }


    /**
     * Controlla se una sotto-directory è piena <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return true se è piena
     */
    public boolean isPienaSubDirectory(File directoryToBeScanned, String dirInterna) {
        return array.isValid(getSubSubDirectories(directoryToBeScanned, dirInterna));
    }


    /**
     * Controlla se una sotto-directory è vuota <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return true se è vuota
     */
    public boolean isVuotaSubDirectory(File directoryToBeScanned, String dirInterna) {
        return array.isEmpty(getSubSubDirectories(directoryToBeScanned, dirInterna));
    }


    /**
     * Elimina l'ultima directory da un path <br>
     * <p>
     * Esegue solo se il path è valido
     * Elimina spazi vuoti iniziali e finali
     *
     * @param pathIn in ingresso
     *
     * @return path ridotto in uscita
     */
    public String levaDirectoryFinale(final String pathIn) {
        String pathOut = pathIn.trim();

        if (text.isValid(pathOut)) {
            if (pathOut.contains(SLASH)) {
                pathOut = text.levaCoda(pathOut, SLASH);
                pathOut = text.levaCodaDa(pathOut, SLASH) + SLASH;
            }
        }

        return pathOut.trim();
    }


    /**
     * Recupera i progetti da una directory <br>
     * Controlla che la sotto-directory sia di un project e quindi contenga la cartella 'src.main.java' e questa non sia vuota <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<File> getProjects(String pathDirectoryToBeScanned) {
        List<File> listaProjects = null;
        String tag = "/src/main/java";
        List<File> listaDirectory = getSubDirectories(new File(pathDirectoryToBeScanned));

        if (listaDirectory != null) {
            listaProjects = new ArrayList<>();

            for (File file : listaDirectory) {
                if (isEsisteSubDirectory(file, tag)) {
                    listaProjects.add(file);
                }
            }
        }

        return listaProjects;
    }


    /**
     * Recupera i progetti vuoti da una directory <br>
     * Controlla che la sotto-directory sia di un project e quindi contenga la cartella 'src.main.java' <br>
     * Controlla che il progetto sia vuoto; deve essere vuota la cartella 'src.main.java' <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<File> getEmptyProjects(String pathDirectoryToBeScanned) {
        List<File> listaEmptyProjects = null;
        String tag = "/src/main/java";
        List<File> listaProjects = getProjects(pathDirectoryToBeScanned);

        if (listaProjects != null) {
            listaEmptyProjects = new ArrayList<>();
            for (File file : listaProjects) {
                if (isVuotaSubDirectory(file, tag)) {
                    listaEmptyProjects.add(file);
                }
            }
        }

        return listaEmptyProjects;
    }


    /**
     * Sposta un file da una directory ad un'altra <br>
     * Esegue solo se il path sorgente esiste <br>
     * Esegue solo se il path destinazione NON esiste <br>
     * Viene cancellato il file sorgente <br>
     *
     * @param pathFileToBeRead  posizione iniziale del file da spostare
     * @param pathFileToBeWrite posizione iniziale del file da spostare
     *
     * @return testo di errore, vuoto se il file è stato spostato
     */
    public boolean spostaFile(String pathFileToBeRead, String pathFileToBeWrite) {
        return spostaFileStr(pathFileToBeRead, pathFileToBeWrite) == VUOTA;
    }


    /**
     * Sposta un file da una directoy ad un'altra <br>
     * Esegue solo se il path sorgente esiste <br>
     * Esegue solo se il path destinazione NON esiste <br>
     * Viene cancellato il file sorgente <br>
     *
     * @param pathFileToBeRead  posizione iniziale del file da spostare
     * @param pathFileToBeWrite posizione iniziale del file da spostare
     *
     * @return testo di errore, vuoto se il file è stato spostato
     */
    public String spostaFileStr(String pathFileToBeRead, String pathFileToBeWrite) {
        String status = VUOTA;

        if (text.isValid(pathFileToBeRead) && text.isValid(pathFileToBeWrite)) {
            status = copyFileStr(pathFileToBeRead, pathFileToBeWrite);
        } else {
            return PATH_NULLO;
        }

        if (status.equals(VUOTA)) {
            status = deleteFileStr(pathFileToBeRead);
        }

        return status;
    }


    /**
     * Controlla se il primo carattere della stringa passata come parametro è uno 'slash' <br>
     *
     * @param testoIngresso          da elaborare
     * @param primoCarattereExpected da controllare
     *
     * @return true se il primo carattere NON è uno quello previsto
     */
    public boolean isNotCarattere(String testoIngresso, String primoCarattereExpected) {
        boolean status = true;
        String primoCarattereEffettivo;

        if (text.isValid(testoIngresso)) {
            primoCarattereEffettivo = testoIngresso.substring(0, 1);
            if (primoCarattereEffettivo.equals(primoCarattereExpected)) {
                status = false;
            }
        }

        return status;
    }


    /**
     * Controlla se il primo carattere della stringa passata come parametro è uno 'slash' <br>
     *
     * @param testoIngresso da elaborare
     *
     * @return true se NON è uno 'slash'
     */
    public boolean isNotSlash(String testoIngresso) {
        return isNotCarattere(testoIngresso, SLASH);
    }


    /**
     * Controlla la stringa passata come parametro termina con un 'suffix' (3 caratteri terminali dopo un punto) <br>
     *
     * @param testoIngresso da elaborare
     *
     * @return true se MANCA il 'suffix'
     */
    public boolean isNotSuffix(String testoIngresso) {
        boolean status = true;
        String quartultimoCarattere;
        int gap = 4;
        int max;
        String tagPatchProperties = ".properties";
        String tagPatchGitIgnore = ".gitignore";
        String tagPatchJava = ".java";

        if (text.isValid(testoIngresso)) {
            max = testoIngresso.length();
            quartultimoCarattere = testoIngresso.substring(max - gap, max - gap + 1);
            if (quartultimoCarattere.equals(PUNTO)) {
                status = false;
            }
        }

        if (testoIngresso.endsWith(tagPatchProperties)) {
            status = false;
        }

        if (testoIngresso.endsWith(tagPatchGitIgnore)) {
            status = false;
        }

        if (testoIngresso.endsWith(tagPatchJava)) {
            status = false;
        }

        return status;
    }

}