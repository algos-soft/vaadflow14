package it.algos.simple.pratical.ch3;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("vertical-layout")
public class VerticalLayoutView extends VerticalLayout {

  public VerticalLayoutView() {
    add(
        new Paragraph("Paragraph 1"),
        new Paragraph("Paragraph 2"),
        new Button("Button")
    );
  }

}
