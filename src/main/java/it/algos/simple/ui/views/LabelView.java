package it.algos.simple.ui.views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;

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
     * Instantiates a new Delta view.
     */
    @PostConstruct
    private void postConstruct() {
        this.labelNormale();
        this.labelBold();
        this.labelSmall();
        this.divNormale();
        this.divBoldSmall();
        this.spanBoldSmall();
        this.html();
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
        Span span;
        String message = VUOTA;

        message = "SPAN:";
        message += "<small> dimensione piccola, </small>";
        message += "<span style=\"color:green\"> colore verde, </span>";
        message += "<b> peso bold, <b>";
        span = new Span();
        span.getElement().setProperty("innerHTML", message);
        //        span.getElement().getStyle().set("color", "black");
        //        span.getStyle().set("font-size", "small");
        //        span.getStyle().set("font-weight", "bold");
        span.setText(message);
        this.add(span);

    }

}
