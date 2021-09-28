package it.algos.simple.pratical.ch04.input;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.listbox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.radiobutton.*;
import com.vaadin.flow.router.*;

import java.util.*;

@Route("select")
public class SelectView extends Composite<Component> {

    private Service service = new Service();

    @Override
    protected Component initContent() {
        List<Department> list = service.getDepartments();
        ComboBox<Department> comboBox = new ComboBox<>("Department");
        comboBox.setItems(list);
        comboBox.setItemLabelGenerator(department -> {
            String text = department.getName() + " department";
            return text;
        });

        RadioButtonGroup<Department> radio = new RadioButtonGroup<>();
        radio.setItems(list);

        ListBox<Department> listBox = new ListBox<>();
        listBox.setItems(list);

        return new VerticalLayout(comboBox, radio, listBox);
    }

}
