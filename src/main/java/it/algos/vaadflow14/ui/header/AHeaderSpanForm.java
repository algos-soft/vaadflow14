package it.algos.vaadflow14.ui.header;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 12-mar-2021
 * Time: 19:56
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AHeaderSpanForm extends VerticalLayout implements AIHeader {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ATextService text;

    private String message;

    /**
     * Costruttore base con parametro <br>
     * Non usa @Autowired perché l' istanza viene creata con appContext.getBean(AHeaderSpanForm.class, spanList) <br>
     */
    public AHeaderSpanForm(final String message) {
        this.setMargin(false);
        this.setSpacing(false);
        this.setPadding(false);
        this.message = message;
    }

    @PostConstruct
    private void postConstruct() {
        Span span = null;

        if (text.isValid(message)) {
            span = new Span(message);
            span.setText(message);
            span.getElement().getStyle().set(AETypeColor.verde.getTag(), AETypeColor.verde.get());
            span.getElement().getStyle().set(AETypeWeight.bold.getTag(), AETypeWeight.bold.get());
            span.getElement().getStyle().set(AETypeSize.smaller.getTag(), AETypeSize.smaller.get());
            this.add(span);
        }
    }

    @Override
    public Component get() {
        return this;
    }

}