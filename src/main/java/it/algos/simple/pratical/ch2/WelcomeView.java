package it.algos.simple.pratical.ch2;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.contextmenu.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.menubar.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 28-set-2021
 * Time: 06:59
 */
@Route("welcome")
public class WelcomeView extends VerticalLayout {

    public WelcomeView() {
        var logo = new Image("https://vaadin.com/images/trademark/PNG/VaadinLogomark_RGB_500x500.png", "Vaadin logo");
        logo.setWidth(10, Unit.EM);

        var layout = new VerticalLayout(
                new H1("Welcome to Vaadin!"),
                new Paragraph("Congratulations! Your development environment is up and running!"),
                logo,
                new Paragraph("""
                        Did you know that Vaadin means reindeer in Finnish? If you look at the Vaadin logo, you'll notice it
                        resembles a reindeer. It also represents Java and HTML. Pretty cool, uh?
                        """),
                new Paragraph("""
                        Anyway, continue your journey in Chapter 2 to learn how to debug your applications and the fundamentals
                        of Vaadin. Have fun while learning!
                        """),
                new Button("Thank you", event -> Notification.show("You are welcome!", 1200, Notification.Position.MIDDLE))
        );
        layout.setMaxWidth(38, Unit.EM);
        layout.setAlignItems(Alignment.CENTER);

        add(menuBar(), layout);

        setAlignItems(Alignment.CENTER);
    }

    public MenuBar menuBar() {
        MenuBar menuBar = new MenuBar();

        addCh03(menuBar);
        addCh04(menuBar);
        addCh05(menuBar);
        addCh06(menuBar);
        addCh07(menuBar);
        addCh10(menuBar);

        return menuBar;
    }


    public MenuItem addCh03(MenuBar menuBar) {
        MenuItem menu = menuBar.addItem("Capitolo 3");

        menu.getSubMenu().addItem("Alignment", event -> getUI().ifPresent(ui -> ui.navigate("alignment")));
        menu.getSubMenu().addItem("Composition", event -> getUI().ifPresent(ui -> ui.navigate("composition")));
        menu.getSubMenu().addItem("Direction", event -> getUI().ifPresent(ui -> ui.navigate("flex-direction")));
        menu.getSubMenu().addItem("Shrink", event -> getUI().ifPresent(ui -> ui.navigate("flex-shrink")));
        menu.getSubMenu().addItem("Wrap", event -> getUI().ifPresent(ui -> ui.navigate("flex-wrap")));
        menu.getSubMenu().addItem("Grow", event -> getUI().ifPresent(ui -> ui.navigate("grow")));
        menu.getSubMenu().addItem("HorizontalLayout", event -> getUI().ifPresent(ui -> ui.navigate("horizontal-layout")));
        menu.getSubMenu().addItem("Justify1", event -> getUI().ifPresent(ui -> ui.navigate("justify1")));
        menu.getSubMenu().addItem("Justify2", event -> getUI().ifPresent(ui -> ui.navigate("justify2")));
        menu.getSubMenu().addItem("Padding", event -> getUI().ifPresent(ui -> ui.navigate("padding-margin-spacing")));
        menu.getSubMenu().addItem("Scroller", event -> getUI().ifPresent(ui -> ui.navigate("scroller")));
        menu.getSubMenu().addItem("Size", event -> getUI().ifPresent(ui -> ui.navigate("size")));
        menu.getSubMenu().addItem("VerticalLayout", event -> getUI().ifPresent(ui -> ui.navigate("vertical-layout")));

        return menu;
    }

    public MenuItem addCh04(MenuBar menuBar) {
        MenuItem menu = menuBar.addItem("Capitolo 4");

        addCh04Input(menu.getSubMenu());
        addCh04Interaction(menu.getSubMenu());
        addCh04Visualization(menu.getSubMenu());

        return menu;
    }

    public MenuItem addCh04Input(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Input");

        menu.getSubMenu().addItem("Alignment", event -> getUI().ifPresent(ui -> ui.navigate("alignment")));
        menu.getSubMenu().addItem("Checkbox", event -> getUI().ifPresent(ui -> ui.navigate("checkbox")));
        menu.getSubMenu().addItem("DatePicker", event -> getUI().ifPresent(ui -> ui.navigate("datepicker")));
        menu.getSubMenu().addItem("FileUpload", event -> getUI().ifPresent(ui -> ui.navigate("file-upload")));
        menu.getSubMenu().addItem("MultiSelect", event -> getUI().ifPresent(ui -> ui.navigate("multi-select")));
        menu.getSubMenu().addItem("NumberField", event -> getUI().ifPresent(ui -> ui.navigate("number-field")));
        menu.getSubMenu().addItem("PasswordField", event -> getUI().ifPresent(ui -> ui.navigate("password-field")));
        menu.getSubMenu().addItem("Select", event -> getUI().ifPresent(ui -> ui.navigate("select")));
        menu.getSubMenu().addItem("Service", event -> getUI().ifPresent(ui -> ui.navigate("text-field")));

        return menu;
    }

    public MenuItem addCh04Interaction(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Interaction");

        menu.getSubMenu().addItem("Anchor", event -> getUI().ifPresent(ui -> ui.navigate("anchor")));
        menu.getSubMenu().addItem("Button", event -> getUI().ifPresent(ui -> ui.navigate("button")));
        menu.getSubMenu().addItem("Menu", event -> getUI().ifPresent(ui -> ui.navigate("menu")));

        return menu;
    }

    public MenuItem addCh04Visualization(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Visualization");

        menu.getSubMenu().addItem("Icon", event -> getUI().ifPresent(ui -> ui.navigate("icon")));
        menu.getSubMenu().addItem("Image", event -> getUI().ifPresent(ui -> ui.navigate("image")));
        menu.getSubMenu().addItem("Notification", event -> getUI().ifPresent(ui -> ui.navigate("notification")));
        menu.getSubMenu().addItem("Tabs", event -> getUI().ifPresent(ui -> ui.navigate("tabs")));

        return menu;
    }

    public MenuItem addCh05(MenuBar menuBar) {
        MenuItem menu = menuBar.addItem("Capitolo 5");

        addCh05Binder(menu.getSubMenu());
        addCh05Binding(menu.getSubMenu());
        addCh05Conversion(menu.getSubMenu());
        addCh05Jakarta(menu.getSubMenu());
        addCh05Properties(menu.getSubMenu());
        addCh05Validation(menu.getSubMenu());

        return menu;
    }

    public MenuItem addCh05Binder(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Binder");

        menu.getSubMenu().addItem("ProductManagement", event -> getUI().ifPresent(ui -> ui.navigate("product-management1")));

        return menu;
    }


    public MenuItem addCh05Binding(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Binding");

        menu.getSubMenu().addItem("ProductManagement", event -> getUI().ifPresent(ui -> ui.navigate("product-management2")));

        return menu;
    }

    public MenuItem addCh05Conversion(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Conversion");

        menu.getSubMenu().addItem("ProductManagement", event -> getUI().ifPresent(ui -> ui.navigate("product-management3")));

        return menu;
    }


    public MenuItem addCh05Jakarta(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Jakarta");

        menu.getSubMenu().addItem("ProductManagement", event -> getUI().ifPresent(ui -> ui.navigate("product-management4")));

        return menu;
    }


    public MenuItem addCh05Properties(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Properties");

        menu.getSubMenu().addItem("ProductManagement", event -> getUI().ifPresent(ui -> ui.navigate("product-management5")));

        return menu;
    }
    public MenuItem addCh05Validation(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Validation");

        menu.getSubMenu().addItem("ProductManagement", event -> getUI().ifPresent(ui -> ui.navigate("product-management6")));

        return menu;
    }

    public MenuItem addCh06(MenuBar menuBar) {
        MenuItem menu = menuBar.addItem("Capitolo 6");

        menu.getSubMenu().addItem("AutomaticColumns", event -> getUI().ifPresent(ui -> ui.navigate("automatic-columns")));
        menu.getSubMenu().addItem("ComponentColumn", event -> getUI().ifPresent(ui -> ui.navigate("component-column")));
        menu.getSubMenu().addItem("CsvExport", event -> getUI().ifPresent(ui -> ui.navigate("csv-export")));
        menu.getSubMenu().addItem("DetailsRow", event -> getUI().ifPresent(ui -> ui.navigate("details-row")));
        menu.getSubMenu().addItem("InMemoryData", event -> getUI().ifPresent(ui -> ui.navigate("in-memory-data")));
        menu.getSubMenu().addItem("LazyLoading", event -> getUI().ifPresent(ui -> ui.navigate("lazy-loading")));
        menu.getSubMenu().addItem("ManualColumns", event -> getUI().ifPresent(ui -> ui.navigate("manual-columns")));
        menu.getSubMenu().addItem("RowSelection", event -> getUI().ifPresent(ui -> ui.navigate("row-selection")));
        menu.getSubMenu().addItem("Sorting", event -> getUI().ifPresent(ui -> ui.navigate("sorting")));

        return menu;
    }


    public MenuItem addCh07(MenuBar menuBar) {
        MenuItem menu = menuBar.addItem("Capitolo 7");

        addCh07Navigation(menu.getSubMenu());
        addCh07Routes(menu.getSubMenu());
        addCh07Url(menu.getSubMenu());

        return menu;
    }

    public MenuItem addCh07Navigation(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Navigation");

        menu.getSubMenu().addItem("Data", event -> getUI().ifPresent(ui -> ui.navigate("data")));
        menu.getSubMenu().addItem("Home", event -> getUI().ifPresent(ui -> ui.navigate("test-home")));
        menu.getSubMenu().addItem("NoData", event -> getUI().ifPresent(ui -> ui.navigate("no-data")));

        return menu;
    }

    public MenuItem addCh07Routes(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Routes");

        menu.getSubMenu().addItem("Admin", event -> getUI().ifPresent(ui -> ui.navigate("admin")));
        menu.getSubMenu().addItem("Login", event -> getUI().ifPresent(ui -> ui.navigate("login")));
        menu.getSubMenu().addItem("MainLayout", event -> getUI().ifPresent(ui -> ui.navigate("main-layout")));
        menu.getSubMenu().addItem("User", event -> getUI().ifPresent(ui -> ui.navigate("user")));

        return menu;
    }
    public MenuItem addCh07Url(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Url");

        menu.getSubMenu().addItem("DynamicPageTitle", event -> getUI().ifPresent(ui -> ui.navigate("dynamic-page-title")));
        menu.getSubMenu().addItem("PageTitle", event -> getUI().ifPresent(ui -> ui.navigate("page-title")));
        menu.getSubMenu().addItem("QueryParameter", event -> getUI().ifPresent(ui -> ui.navigate("query-parameter")));
        menu.getSubMenu().addItem("TemplateParameter", event -> getUI().ifPresent(ui -> ui.navigate("template-parameter/:value?")));
        menu.getSubMenu().addItem("TypedParameter", event -> getUI().ifPresent(ui -> ui.navigate("typed-parameter")));

        return menu;
    }


    public MenuItem addCh10(MenuBar menuBar) {
        MenuItem menu = menuBar.addItem("Capitolo 10");

        addCh10Css(menu.getSubMenu());
        addCh10Responsiveness(menu.getSubMenu());
        addCh10Themes(menu.getSubMenu());

        return menu;
    }

    public MenuItem addCh10Css(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Css");

        menu.getSubMenu().addItem("CssClasses", event -> getUI().ifPresent(ui -> ui.navigate("css-classes")));
        menu.getSubMenu().addItem("Lumo", event -> getUI().ifPresent(ui -> ui.navigate("lumo-properties")));

        return menu;
    }

    public MenuItem addCh10Responsiveness(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Responsiveness");

        menu.getSubMenu().addItem("AppLayout", event -> getUI().ifPresent(ui -> ui.navigate("app-layout")));
        menu.getSubMenu().addItem("Css", event -> getUI().ifPresent(ui -> ui.navigate("css")));
        menu.getSubMenu().addItem("FormLayout", event -> getUI().ifPresent(ui -> ui.navigate("form-layout")));

        return menu;
    }

    public MenuItem addCh10Themes(SubMenu menuItem) {
        MenuItem menu = menuItem.addItem("Themes");

        menu.getSubMenu().addItem("Theme", event -> getUI().ifPresent(ui -> ui.navigate("theme")));

        return menu;
    }

}
