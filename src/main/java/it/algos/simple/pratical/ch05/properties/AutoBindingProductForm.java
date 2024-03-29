package it.algos.simple.pratical.ch05.properties;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.function.SerializableRunnable;

import java.util.Set;

public class AutoBindingProductForm extends Composite<Component> {

  private final SerializableRunnable saveListener;

  @PropertyId("name")
  private TextField name = new TextField("Name");

  @PropertyId("manufacturer")
  private ComboBox<Manufacturer> manufacturer = new ComboBox<>();

  @PropertyId("available")
  private Checkbox available = new Checkbox("Available");

  private TextField phoneNumber = new TextField("Manufacturer phone number");

  private TextField email = new TextField("Manufacturer email");

  public AutoBindingProductForm(Product product, Set<Manufacturer> manufacturers,
                                SerializableRunnable saveListener) {
    this.saveListener = saveListener;

    manufacturer.setItems(manufacturers);
    manufacturer.setItemLabelGenerator(Manufacturer::getName);

    Binder<Product> binder = new Binder<>(Product.class);
    binder.bindInstanceFields(this);

    if (product.getName() == null) {
      phoneNumber.setVisible(false);
      email.setVisible(false);
    } else {
      manufacturer.setEnabled(false);
      binder.bind(phoneNumber, "manufacturer.phoneNumber");
      binder.bind(email,
          p -> p.getManufacturer().getEmail(),
          (p, e) -> p.getManufacturer().setEmail(e));
    }

    binder.setBean(product);
  }

  @Override
  protected Component initContent() {
    return new VerticalLayout(
        new H1("Product"),
        name,
        available,
        manufacturer,
        phoneNumber,
        email,
        new Button("Save", VaadinIcon.CHECK.create(),
            event -> saveListener.run())
    );
  }

}
