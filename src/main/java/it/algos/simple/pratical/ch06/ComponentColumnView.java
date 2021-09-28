package it.algos.simple.pratical.ch06;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.progressbar.*;
import com.vaadin.flow.router.*;

import java.util.*;

@Route("component-column")
public class ComponentColumnView extends Composite<Component> {

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
        .setComparator(Comparator.comparingInt(Book::getQuantity));
    grid.addComponentColumn(
        book -> new Button(VaadinIcon.PLUS.create(), event -> {
          BookService.increaseQuantity(book);
          updateGrid(grid);
        })
    );

    updateGrid(grid);

    return new VerticalLayout(grid);
  }

  private void updateGrid(Grid<Book> grid) {
    List<Book> books = BookService.findAll();
    grid.setItems(books);
  }

}
