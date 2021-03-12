package it.algos.vaadflow14.ui.header;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.*;
import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 16-feb-2021
 * Time: 11:30
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AHeaderSpanList extends VerticalLayout implements AIHeader {

    private List<Span> spanList;

    /**
     * Costruttore base con parametro <br>
     * Non usa @Autowired perché l' istanza viene creata con appContext.getBean(AHeaderSpanList.class, spanList) <br>
     */
    public AHeaderSpanList(List<Span> spanList) {
        this.setMargin(false);
        this.setSpacing(false);
        this.setPadding(false);
        this.spanList = spanList;
    }

    @PostConstruct
    private void postConstruct() {
        if (spanList != null && spanList.size() > 0) {
            for (Span span : spanList) {
                this.add(span);
            }
        }
    }

    @Override
    public Component get() {
        return this;
    }

}
