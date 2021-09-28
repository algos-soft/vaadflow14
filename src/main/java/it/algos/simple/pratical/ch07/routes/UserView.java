package it.algos.simple.pratical.ch07.routes;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;

public class UserView extends Composite<Component> {

  @Override
  protected Component initContent() {
    return new Text("Welcome, user.");
  }

}
