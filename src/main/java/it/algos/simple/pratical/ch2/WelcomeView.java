package it.algos.simple.pratical.ch2;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
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
        var logo = new Image(
                "https://vaadin.com/images/trademark/PNG/VaadinLogomark_RGB_500x500.png",
                "Vaadin logo"
        );
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

        add(layout);
        setAlignItems(Alignment.CENTER);
    }
}
