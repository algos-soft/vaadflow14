package it.algos.test.ui.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.ui.MainLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 29-lug-2020
 * Time: 18:46
 */
@Route(value = "omega", layout = MainLayout.class)
public class OmegaView extends VerticalLayout {

    /**
     * Instantiates a new Omega view.
     */
    public OmegaView() {
        add(VaadinIcon.HOSPITAL.create());
        add(new Span("Seconda prova di View"));
        add(new Span("Usa @Route e usa MainLayout.class"));
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }

}
