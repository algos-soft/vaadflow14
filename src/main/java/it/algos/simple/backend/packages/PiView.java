package it.algos.simple.backend.packages;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.io.File;

@Route(value = "gac")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PiView extends Div {

    @Autowired
    ApplicationContext context;


    @PostConstruct
    private void postConstruct() {
        setHeight("100%");

        Component comp = context.getBean(PiView1.class);

        Label label = new Label("Prime");
        add(label);

        this.add(comp);

        //        add(new Label("Dopo"));

    }


}
