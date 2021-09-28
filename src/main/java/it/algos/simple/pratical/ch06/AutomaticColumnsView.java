package it.algos.simple.pratical.ch06;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

@Route("automatic-columns")
public class AutomaticColumnsView extends Composite<Component> {

  @Override
  protected Component initContent() {
    var grid = new Grid<>(Book.class);
    grid.setColumns("title", "publisher.name", "author", "quantity");

    grid.getColumnByKey("title")
        .setHeader("Book")
        .setFooter("text here")
        .setAutoWidth(true);

    grid.getColumnByKey("publisher.name").setHeader("Publisher");

    return new VerticalLayout(grid);
  }

}
