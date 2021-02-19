package it.algos.simple.ui.views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 15-feb-2021
 * Time: 17:52
 */
@Route(value = "labelView")
public class LabelView extends VerticalLayout {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    private ATextService text;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    private AHtmlService html;


    /**
     * Instantiates a new Delta view.
     */
    @PostConstruct
    private void postConstruct() {
        this.add(html.getSpan("Testo semplice nero normale"));
        this.add(html.getSpanVerde("Testo verde normale"));
        this.add(html.getSpanVerde("Testo verde bold", AETypeWeight.bold));
        this.add(html.getSpanVerde("Testo verde small", AETypeSize.small));
        this.add(html.getSpanVerde("Testo verde small bold", AETypeSize.small, AETypeWeight.bold));
        this.add(html.getSpanRosso("Testo rosso small bold", AETypeSize.small, AETypeWeight.bold));
        this.add(html.getSpanBlu("Testo blu normale"));
        this.add(html.getSpan("Testo blu leggero molto grande", AETypeColor.blu, AETypeWeight.w200, AETypeSize.xLarge));
        this.html();
        this.html2();
        this.html3();
    }

    private void labelNormale() {
        Label label;
        String message = VUOTA;

        message = "Label: dimensione normale, peso normale, colore nero";
        label = new Label(message);
        label.getElement().getStyle().set("color", "black");
        this.add(label);

        message = "Label: dimensione normale, peso normale, colore verde";
        label = new Label(message);
        label.getElement().getStyle().set("color", "green");
        this.add(label);

        message = "Label: dimensione normale, peso normale, colore blue";
        label = new Label(message);
        label.getElement().getStyle().set("color", "blue");
        this.add(label);

        message = "Label: dimensione normale, peso normale, colore rosso";
        label = new Label(message);
        label.getElement().getStyle().set("color", "red");
        this.add(label);
    }


    private void labelBold() {
        Label label;
        String message = VUOTA;

        message = "Label: dimensione normale, peso bold, colore nero";
        label = new Label(message);
        label.getElement().getStyle().set("color", "black");
        label.getElement().getStyle().set("font-weight", "bold");
        this.add(label);

        message = "Label: dimensione normale, peso bold, colore verde";
        label = new Label(message);
        label.getElement().getStyle().set("color", "green");
        label.getElement().getStyle().set("font-weight", "bold");
        this.add(label);

        message = "Label: dimensione normale, peso bold, colore blue";
        label = new Label(message);
        label.getElement().getStyle().set("color", "blue");
        label.getElement().getStyle().set("font-weight", "bold");
        this.add(label);

        message = "Label: dimensione normale, peso bold, colore rosso";
        label = new Label(message);
        label.getElement().getStyle().set("color", "red");
        label.getElement().getStyle().set("font-weight", "bold");
        this.add(label);
    }

    private void labelSmall() {
        Label label;
        String message = VUOTA;

        message = "Label: dimensione piccola, peso bold, colore nero";
        label = new Label(message);
        label.getElement().getStyle().set("color", "black");
        label.getElement().getStyle().set("font-weight", "bold");
        label.getElement().getStyle().set("font-size", "small");
        this.add(label);

        message = "Label: dimensione piccola, peso bold, colore verde";
        label = new Label(message);
        label.getElement().getStyle().set("color", "green");
        label.getElement().getStyle().set("font-weight", "bold");
        label.getElement().getStyle().set("font-size", "small");
        this.add(label);

        message = "Label: dimensione piccola, peso bold, colore blue";
        label = new Label(message);
        label.getElement().getStyle().set("color", "blue");
        label.getElement().getStyle().set("font-weight", "bold");
        label.getElement().getStyle().set("font-size", "small");
        this.add(label);

        message = "Label: dimensione piccola, peso bold, colore rosso";
        label = new Label(message);
        label.getElement().getStyle().set("color", "red");
        label.getElement().getStyle().set("font-weight", "bold");
        label.getElement().getStyle().set("font-size", "small");
        this.add(label);
    }

    private void divNormale() {
        Div div;
        String message = VUOTA;

        message = "DIV: dimensione normale, peso normale, colore nero";
        div = new Div();
        div.getElement().getStyle().set("color", "black");
        div.setText(message);
        this.add(div);

        message = "DIV: dimensione normale, peso normale, colore verde";
        div = new Div();
        div.getElement().getStyle().set("color", "green");
        div.setText(message);
        this.add(div);

        message = "DIV: dimensione normale, peso normale, colore blue";
        div = new Div();
        div.getElement().getStyle().set("color", "blue");
        div.setText(message);
        this.add(div);

        message = "DIV: dimensione normale, peso normale, colore rosso";
        div = new Div();
        div.getElement().getStyle().set("color", "red");
        div.setText(message);
        this.add(div);
    }

    private void divBoldSmall() {
        Div div;
        String message = VUOTA;

        message = "DIV: dimensione piccola, peso bold, colore nero";
        div = new Div();
        div.getElement().getStyle().set("color", "black");
        div.getStyle().set("font-size", "small");
        div.getStyle().set("font-weight", "bold");
        div.setText(message);
        this.add(div);

        message = "DIV: dimensione piccola, peso bold, colore verde";
        div = new Div();
        div.getElement().getStyle().set("color", "green");
        div.getStyle().set("font-size", "small");
        div.getStyle().set("font-weight", "bold");
        div.setText(message);
        this.add(div);

        message = "DIV: dimensione piccola, peso bold, colore blue";
        div = new Div();
        div.getElement().getStyle().set("color", "blue");
        div.getStyle().set("font-size", "small");
        div.getStyle().set("font-weight", "bold");
        div.setText(message);
        this.add(div);

        message = "DIV: dimensione piccola, peso bold, colore rosso";
        div = new Div();
        div.getElement().getStyle().set("color", "red");
        div.getStyle().set("font-size", "small");
        div.getStyle().set("font-weight", "bold");
        div.setText(message);
        this.add(div);
    }

    private void spanBoldSmall() {
        Span span;
        String message = VUOTA;

        message = "SPAN: dimensione piccola, peso bold, colore nero";
        span = new Span();
        span.getElement().getStyle().set("color", "black");
        span.getStyle().set("font-size", "small");
        span.getStyle().set("font-weight", "bold");
        span.setText(message);
        this.add(span);

        message = "SPAN: dimensione piccola, peso bold, colore verde";
        span = new Span();
        span.getElement().getStyle().set("color", "green");
        span.getStyle().set("font-size", "small");
        span.getStyle().set("font-weight", "bold");
        span.setText(message);
        this.add(span);

        message = "SPAN: dimensione piccola, peso bold, colore blue";
        span = new Span();
        span.getElement().getStyle().set("color", "blue");
        span.getStyle().set("font-size", "small");
        span.getStyle().set("font-weight", "bold");
        span.setText(message);
        this.add(span);

        message = "SPAN: dimensione piccola, peso bold, colore rosso";
        span = new Span();
        span.getElement().getStyle().set("color", "red");
        span.getStyle().set("font-size", "small");
        span.getStyle().set("font-weight", "bold");
        span.setText(message);
        this.add(span);
    }

    private void html() {
        String message = VUOTA;

        message += "SPAN:";
        message += "<small> dimensione piccola, </small>";
        message += "<span style=\"color:green\"> colore verde, </span>";
        message += "<b> peso bold, <b>";
        this.add(html.getSpanHtml(message));
    }

    private void html2() {
        String message = VUOTA;

        message += "SPAN in riga:";
        message += " dimensione normale, ";
        message += html.verde("testo verde");
        message += " seguito da un ";
        message += html.rosso("testo rosso");
        this.add(html.getSpanHtml(message));
    }

    private void html3() {
        String message = VUOTA;

        message += "SPAN multiplo:";
        message += " dimensione normale, ";
        message += html.rosso("testo rosso");
        message += " all'interno di un testo verde bold";
        this.add(html.getSpanVerde(message,AETypeWeight.bold));

        message += html.verde("Questo funzione: dimensione normale, ");
        message += html.rosso("testo rosso");
        message += html.verde(" all'interno di un testo verde (non bold))");
        this.add(html.getSpanHtml(message));
    }

}
