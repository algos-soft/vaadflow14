package it.algos.simple.pratical.ch06;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

import java.util.*;

@Route("row-selection")
public class RowSelectionView extends Composite<Component> {

  @Override
  protected Component initContent() {
    var increaseQuantity = new Button("Increase quantity");
    increaseQuantity.setEnabled(false);

    var grid = new Grid<Book>();
    grid.addColumn(Book::getTitle).setHeader("Book").setAutoWidth(true).setSortable(true);
    grid.addColumn(book -> book.getPublisher().getName()).setHeader("Publisher").setSortable(true);
    grid.addColumn(Book::getAuthor).setHeader("Author").setSortable(true);
    grid.addColumn(Book::getQuantity).setHeader("Quantity").setSortable(true);
    updateGrid(grid);

    //grid.setSelectionMode(Grid.SelectionMode.MULTI);
    grid.addSelectionListener(event -> {
      boolean enabled = event.getFirstSelectedItem().isPresent();
      increaseQuantity.setEnabled(enabled);
    });

    increaseQuantity.addClickListener(event ->
        grid.asSingleSelect().getOptionalValue().ifPresent(
            book -> {
              BookService.increaseQuantity(book);
              updateGrid(grid);
            }
        )
    );

    return new VerticalLayout(increaseQuantity, grid);
  }

  private void updateGrid(Grid<Book> grid) {
    List<Book> books = BookService.findAll();
    grid.setItems(books);
  }

}
