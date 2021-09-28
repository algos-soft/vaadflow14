package it.algos.simple.pratical.ch04.interaction;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.contextmenu.*;
import com.vaadin.flow.component.menubar.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

@Route("menu")
public class MenuView extends Composite<Component> {

    @Override
    protected Component initContent() {
        MenuBar menuBar = new MenuBar();

        MenuItem file = menuBar.addItem("File");
        file.getSubMenu().addItem("New").setCheckable(true);
        file.getSubMenu().addItem("Open");

        MenuItem edit = menuBar.addItem("Edit");
        edit.getSubMenu().addItem("Copy", event -> Notification.show("Copy selected"));
        MenuItem paste = edit.getSubMenu().addItem("Paste");
        paste.addClickListener(event -> Notification.show("Paste selected"));
        paste.setEnabled(false);

        HorizontalLayout target = new HorizontalLayout(new Text("Right click here"));
        ContextMenu contextMenu = new ContextMenu(target);
        contextMenu.addItem("Copy");
        contextMenu.addItem("Paste");

        return new VerticalLayout(menuBar, target);
    }

}
