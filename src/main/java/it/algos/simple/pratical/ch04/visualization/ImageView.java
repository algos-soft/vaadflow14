package it.algos.simple.pratical.ch04.visualization;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.*;

@Route("image")
public class ImageView extends Composite<Component> {

  @Override
  protected Component initContent() {
    Image photo = new Image(
        "https://live.staticflickr.com/65535/50969482201_be1163c6f1_b.jpg",
        "Funny dog"
    );
    photo.setWidth("100%");

    StreamResource source = new StreamResource("logo", () ->
        getClass().getClassLoader().getResourceAsStream("vaadin-logo.png")
    );

    Image logo = new Image(source, "Logo");
    logo.setWidthFull();

    VerticalLayout layout = new VerticalLayout(
        new H2("Vaadin is fun!"),
        logo,
        photo
    );
    layout.setAlignItems(FlexComponent.Alignment.CENTER);
    return layout;
  }

}
