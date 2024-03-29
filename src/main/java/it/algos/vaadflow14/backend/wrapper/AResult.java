package it.algos.vaadflow14.backend.wrapper;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 27-nov-2020
 * Time: 14:31
 * <p>
 * Semplice wrapper per veicolare una risposta con diverse property <br>
 */
@Component
public class AResult implements AIResult {

    private boolean valido;

    private String wikiTitle = VUOTA;

    private String urlPreliminary = VUOTA;

    private String urlRequest = VUOTA;

    private String errorCode = VUOTA;

    private String errorMessage = VUOTA;

    private String codeMessage = VUOTA;

    private String validMessage = VUOTA;

    private String preliminaryResponse = VUOTA;

    private String response = VUOTA;

    private String token = VUOTA;

    private String queryType = VUOTA;

    private int value = 0;

    private List lista = null;

    private Map mappa = null;

    protected AResult() {
        this(true, VUOTA);
    }

    protected AResult(final boolean valido, final String message) {
        this(valido, message, 0);
    }

    protected AResult(final boolean valido, final String message, final int value) {
        this.valido = valido;
        if (valido) {
            this.validMessage = message;
        }
        else {
            this.errorMessage = message;
        }
        this.value = value;
    }

    public static AIResult valido() {
        return new AResult();
    }

    public static AIResult valido(final String validMessage) {
        return new AResult(true, validMessage);
    }

    public static AIResult valido(final String validMessage, final int value) {
        return new AResult(true, validMessage, value);
    }

    public static AIResult contenuto(final String text, final String source) {
        AResult result = new AResult();

        if (text != null && text.length() > 0) {
            result.setValido(true);
            result.setResponse(text);
            result.setValidMessage(JSON_SUCCESS);
        }
        else {
            result.setValido(false);
        }

        return result;
    }

    public static AIResult contenuto(final String text) {
        return contenuto(text, VUOTA);
    }

    public static AIResult errato() {
        return new AResult(false, "Non effettuato");
    }

    public static AIResult errato(final int valore) {
        return new AResult(false, VUOTA, valore);
    }

    public static AIResult errato(final String errorMessage) {
        AResult result = new AResult(false, errorMessage);
        result.setErrorCode(errorMessage);
        return result;
    }

    @Override
    public boolean isValido() {
        return valido;
    }

    @Override
    public void setValido(final boolean valido) {
        this.valido = valido;
    }

    @Override
    public boolean isErrato() {
        return !valido;
    }

    @Override
    public String getCodeMessage() {
        return codeMessage;
    }

    @Override
    public void setCodeMessage(String codeMessage) {
        this.codeMessage = codeMessage;
    }

    @Override
    public String getMessage() {
        return isValido() ? getValidMessage() : getErrorMessage();
    }

    @Override
    public void setMessage(final String message) {
        if (isValido()) {
            setValidMessage(message);
        }
        else {
            setErrorMessage(message);
        }
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public AIResult setErrorMessage(final String message) {
        errorMessage = message;
        return this;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getValidMessage() {
        return validMessage;
    }

    @Override
    public void setValidMessage(String validMessage) {
        this.validMessage = validMessage;
    }

    @Override
    public String getWikiTitle() {
        return wikiTitle;
    }

    @Override
    public void setWikiTitle(String wikiTitle) {
        this.wikiTitle = wikiTitle;
    }


    @Override
    public String getResponse() {
        return response;
    }

    @Override
    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public List getLista() {
        return lista;
    }

    @Override
    public void setLista(List lista) {
        this.lista = lista;
    }

    @Override
    public String getUrlPreliminary() {
        return urlPreliminary;
    }

    @Override
    public void setUrlPreliminary(String urlPreliminary) {
        this.urlPreliminary = urlPreliminary;
    }

    @Override
    public String getUrlRequest() {
        return urlRequest;
    }

    @Override
    public void setUrlRequest(String urlRequest) {
        this.urlRequest = urlRequest;
    }

    @Override
    public Map getMappa() {
        return mappa;
    }

    @Override
    public void setMappa(Map mappa) {
        this.mappa = mappa;
    }

    @Override
    public String getPreliminaryResponse() {
        return preliminaryResponse;
    }

    @Override
    public void setPreliminaryResponse(String preliminaryResponse) {
        this.preliminaryResponse = preliminaryResponse;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getQueryType() {
        return queryType;
    }

    @Override
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    @Override
    public void print(final ALogService logger, final AETypeLog typeLog) {
        if (isValido()) {
            logger.log(typeLog, getValidMessage());
        }
        else {
            logger.log(typeLog, getErrorMessage());
        }
    }


}
