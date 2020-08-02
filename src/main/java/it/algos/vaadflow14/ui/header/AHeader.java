package it.algos.vaadflow14.ui.header;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.service.ATextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 11:10
 */
public abstract class AHeader extends VerticalLayout {

    protected AlertWrap alertWrap;

    protected List<String> alertUser;

    protected List<String> alertAdmin;

    protected List<String> alertDev;

    protected List<String> alertDevAll;

    protected List<String> alertParticolare;

    protected List<String> alertHtml;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
     protected ATextService text;


    protected void initView() {
        this.fixProperties();
        this.fixAlertWrap();
        this.fixView();
    }

    /**
     * Regola alcune properties (grafiche e non grafiche) <br>
     * Regola la business logic di questa classe <br>
     */
    protected void fixProperties() {
        this.setMargin(false);
        this.setSpacing(false);
        this.setPadding(false);
    }


    /**
     * Costruisce graficamente la view <br>
     */
    protected void fixView() {
    }

    public void fixAlertWrap() {
        alertUser = new ArrayList<>();
        alertAdmin = new ArrayList<>();
        alertDev = new ArrayList<>();
        alertDevAll = new ArrayList<>();
        alertParticolare = new ArrayList<>();

        if (alertWrap != null) {
            alertUser = alertWrap.getAlertUser();
            alertAdmin = alertWrap.getAlertAdmin();
            alertDev = alertWrap.getAlertDev();
            alertDevAll = alertWrap.getAlertDevAll();
            alertParticolare = alertWrap.getAlertParticolare();
        }
    }

}
