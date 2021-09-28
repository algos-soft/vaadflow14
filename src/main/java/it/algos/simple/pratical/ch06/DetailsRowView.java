package it.algos.simple.pratical.ch06;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.progressbar.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.*;

import java.util.*;

@Route("details-row")
public class DetailsRowView extends Composite<Component> {

  @Override
  protected Component initContent() {
    var grid = new Grid<Book>();
    grid.addColumn(Book::getTitle).setHeader("Book").setAutoWidth(true).setSortable(true);
    grid.addColumn(book -> book.getPublisher().getName()).setHeader("Publisher").setSortable(true);
    grid.addColumn(Book::getAuthor).setHeader("Author").setSortable(true);
    grid.addComponentColumn(
        book -> new ProgressBar(0, 50, book.getQuantity())
    ).setHeader("Quantity")
        .setSortable(true)
        .setComparator((b1, b2) -> Integer.compare(
            b1.getQuantity(), b2.getQuantity()));
    grid.setItemDetailsRenderer(
        new ComponentRenderer<>(book -> new VerticalLayout(
            new Text(book.getDescription()),
            new Button("Quantity", VaadinIcon.ARROW_UP.create(), event -> {
              BookService.increaseQuantity(book);
              updateGrid(grid);
              grid.select(book);
            })
        ))
    );

    updateGrid(grid);

    return new VerticalLayout(grid);
  }

  private void updateGrid(Grid<Book> grid) {
    List<Book> books = BookService.findAll();
    grid.setItems(books);
  }

}
