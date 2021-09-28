package it.algos.simple.pratical.ch04.visualization;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

@Route("icon")
public class IconView extends Composite<Component> {

  @Override
  protected Component initContent() {
    Icon icon = VaadinIcon.YOUTUBE.create();
    icon.setSize("4em");
    Button button = new Button("Edit", VaadinIcon.EDIT.create());
    return new VerticalLayout(icon, button);
  }

}
