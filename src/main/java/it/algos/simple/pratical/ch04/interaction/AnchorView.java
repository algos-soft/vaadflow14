package it.algos.simple.pratical.ch04.interaction;

import com.helger.commons.io.stream.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.*;

import java.nio.charset.*;
import java.time.*;

@Route("anchor")
public class AnchorView extends Composite<Component> {

    @Override
    protected Component initContent() {
        Anchor blogLink = new Anchor("https://www.programmingbrain.com",
                "Visit my technical blog");

        Anchor vaadinLink = new Anchor("https://vaadin.com",
                new Button("Visit vaadin.com"));

        Anchor textLink = new Anchor(new StreamResource(
                "text.txt",
                () -> {
                    String content = "Time: " + LocalTime.now();
                    return new StringInputStream(
                            content, Charset.defaultCharset());
                }
        ), "Server-generated text");

        return new VerticalLayout(blogLink, vaadinLink, textLink);
    }
}
