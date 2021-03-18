package it.algos.vaadflow14.ui.service;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

import java.util.*;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: sab, 02-mag-2020
 * Time: 20:16
 */
public class Parametro {

    private String singleParameter;

    private Map<String, String> parametersMap;

    private Map<String, List<String>> multiParametersMap;

    private boolean valido;

    private boolean singoloParametro;

    private boolean mappa;

    private boolean multiMappa;

    private String primoSegmento;


    public Parametro(String singleParameter) {
        this.singleParameter = singleParameter;
        this.setSingoloParametro(true);
        this.setMappa(false);
        this.setMultiMappa(false);
        this.setValido(!singleParameter.equals(VUOTA));
    }


    public Parametro(Map<String, String> parametersMap) {
        this.parametersMap = parametersMap;
        this.setSingoloParametro(false);
        this.setValido(parametersMap != null);
    }


    public Parametro(String singleParameter, String primoSegmento) {
        this.singleParameter = singleParameter;
        this.setSingoloParametro(true);
        this.setMappa(false);
        this.setMultiMappa(false);
        this.setPrimoSegmento(primoSegmento);
        this.setValido(!singleParameter.equals(VUOTA));
    }


    public Parametro(Map<String, String> parametersMap, String primoSegmento) {
        this.parametersMap = parametersMap;
        this.setSingoloParametro(false);
        this.setPrimoSegmento(primoSegmento);
        this.setValido(parametersMap != null);
    }

    public Parametro(Map<String, List<String>> multiParametersMap, String primoSegmento, boolean multiParameters) {
        this.setMultiParametersMap(multiParametersMap);
        this.setPrimoSegmento(primoSegmento);
        this.setSingoloParametro(false);
        this.setMappa(false);
        this.setMultiMappa(true);
        this.setValido(multiParametersMap != null);
    }

    public String getSingleParameter() {
        return singleParameter;
    }


    public Map<String, String> getParametersMap() {
        return parametersMap;
    }


    public Map<String, List<String>> getMultiParametersMap() {
        return multiParametersMap;
    }


    public void setMultiParametersMap(Map<String, List<String>> multiParametersMap) {
        this.multiParametersMap = multiParametersMap;
    }


    public boolean isMappa() {
        return mappa;
    }


    public void setMappa(boolean mappa) {
        this.mappa = mappa;
    }


    public boolean isMultiMappa() {
        return multiMappa;
    }


    public void setMultiMappa(boolean multiMappa) {
        this.multiMappa = multiMappa;
    }


    public String get(String key) {
        return isMappa() ? getParametersMap().get(key) : isMultiMappa()?getMultiParametersMap().get(key).get(0):VUOTA;
    }


    public boolean containsKey(String key) {
        return get(key) != null;
    }


    public String getPrimoSegmento() {
        return primoSegmento;
    }


    public void setPrimoSegmento(String primoSegmento) {
        this.primoSegmento = primoSegmento;
    }


    public boolean isSingoloParametro() {
        return singoloParametro;
    }


    public void setSingoloParametro(boolean singoloParametro) {
        this.singoloParametro = singoloParametro;
    }


    public boolean isValido() {
        return valido;
    }


    public void setValido(boolean valido) {
        this.valido = valido;
    }

}
