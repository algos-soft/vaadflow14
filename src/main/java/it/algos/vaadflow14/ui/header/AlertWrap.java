package it.algos.vaadflow14.ui.header;

import java.util.List;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: dom, 03-mag-2020
 * Time: 18:03
 * Wrapper per incapsulare le liste di avvisi e passarle dal Service a AHeader <br>
 */
public class AlertWrap {

    private List<String> alertUser;

    private List<String> alertAdmin;

    private List<String> alertDev;

    private List<String> alertDevAll;

    private List<String> alertParticolare;


    public AlertWrap(List<String> alertUser) {
        this.alertUser = alertUser;
    }


    public AlertWrap(List<String> alertUser, List<String> alertAdmin, List<String> alertDev) {
        this.alertUser = alertUser;
        this.alertAdmin = alertAdmin;
        this.alertDev = alertDev;
    }


    public AlertWrap(List<String> alertUser, List<String> alertAdmin, List<String> alertDev, List<String> alertDevAll, List<String> alertParticolare) {
        this.alertUser = alertUser;
        this.alertAdmin = alertAdmin;
        this.alertDev = alertDev;
        this.alertDevAll = alertDevAll;
        this.alertParticolare = alertParticolare;
    }


    public List<String> getAlertUser() {
        return alertUser;
    }


    public void setAlertUser(List<String> alertUser) {
        this.alertUser = alertUser;
    }


    public List<String> getAlertAdmin() {
        return alertAdmin;
    }


    public void setAlertAdmin(List<String> alertAdmin) {
        this.alertAdmin = alertAdmin;
    }


    public List<String> getAlertDev() {
        return alertDev;
    }


    public void setAlertDev(List<String> alertDev) {
        this.alertDev = alertDev;
    }


    public List<String> getAlertDevAll() {
        return alertDevAll;
    }


    public void setAlertDevAll(List<String> alertDevAll) {
        this.alertDevAll = alertDevAll;
    }


    public List<String> getAlertParticolare() {
        return alertParticolare;
    }


    public void setAlertParticolare(List<String> alertParticolare) {
        this.alertParticolare = alertParticolare;
    }

}
