package it.algos.simple.pratical.ch07.url;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("This is the title")
@Route("page-title")
public class PageTitleView extends Composite<Component> {

  @Override
  protected Component initContent() {
    return new H1("Hello!");
  }

}
