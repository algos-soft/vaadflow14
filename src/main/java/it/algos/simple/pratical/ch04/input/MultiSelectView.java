package it.algos.simple.pratical.ch04.input;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.listbox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

import java.util.*;

@Route("multi-select")
public class MultiSelectView extends Composite<Component> {

    private Service service = new Service();

    @Override
    protected Component initContent() {
        CheckboxGroup<Department> checkboxes = new CheckboxGroup<>();
        checkboxes.setItems(service.getDepartments());

        MultiSelectListBox<Department> listBox = new MultiSelectListBox<>();
        listBox.setItems(service.getDepartments());
        Set<Department> departments = listBox.getValue();

        return new VerticalLayout(checkboxes, listBox);
    }

}
