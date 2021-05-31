package it.algos.vaadflow14.ui.header;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 04-apr-2021
 * Time: 15:31
 */
public abstract class AHeaderSpan extends VerticalLayout implements AIHeader {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected ATextService text;

    /**
     * Property specifica regolata nella sottoclasse <br>
     */
    protected List<Span> listaSpan;

    /**
     * Property specifica regolata nella sottoclasse <br>
     */
    protected String message;

    public AHeaderSpan() {
    }

    /**
     * Costruttore base senza parametro <br>
     */
    public AHeaderSpan(List<Span> listaSpan) {
        this.setMargin(false);
        this.setSpacing(false);
        this.setPadding(false);
        this.listaSpan = listaSpan;
    }

    /**
     * Performing the initialization in a constructor is not suggested <br>
     * as the state of the UI is not properly set up when the constructor is invoked. <br>
     * <p>
     * La injection viene fatta da SpringBoot solo alla fine del metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        this.initView();
    }

    /**
     * Costruisce la/le componenti grafiche <br>
     * Deve essere sovrascritto, senza  <br>
     * Deve essere sovrascritto, senza invocare il metodo della superclasse <br>
     */
    protected void initView() {
    }

    @Override
    public Component get() {
        return this;
    }

}
