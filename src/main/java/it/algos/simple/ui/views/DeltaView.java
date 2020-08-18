package it.algos.simple.ui.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mar, 28-apr-2020
 * Time: 21:49
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 */
@Route(value = "delta")
public class DeltaView extends VerticalLayout {

    /**
     * Instantiates a new Delta view.
     */
    public DeltaView() {
        add(VaadinIcon.HOSPITAL.create());
        add(new Span("Prima prova di View"));
        add(new Span("Usa @Route ma non usa MainLayout.class"));
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }

}
