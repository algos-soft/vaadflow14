package it.algos.simple.ui.views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.*;
import it.algos.simple.backend.packages.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.packages.crono.secolo.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;


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
@Route(value = "deltaView")
public class DeltaView extends VerticalLayout {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ResourceService resourceService;

    @Autowired
    private SecoloService secoloLogic;

    @Autowired
    private MongoService mongo;


    /**
     * Instantiates a new Delta view.
     */
    @PostConstruct
    private void postConstruct() {

        File bandiera = new File("config" + File.separator + "at.png");
        StreamResource resource = null;
        byte[] imageBytes;
        String imageStr = VUOTA;
        Image immagine;

        //--legge dal file
        for (String sigla : Arrays.asList("ad", "ae", "ca", "bb", "mg")) {
            immagine = resourceService.getBandieraFromFile(sigla);
            add(immagine);
        }


        //--registra su mongo
        String valoreCodificato = resourceService.getSrcBandieraPng("fo");
        Delta deltaUno = (Delta) mongo.findByIdOld(Delta.class, "alfa");
        if (deltaUno != null) {
            deltaUno.immagine = valoreCodificato;
            try {
                mongo.saveOld(deltaUno);
            } catch (AMongoException unErrore) {
            }
        }

        add(VaadinIcon.HOSPITAL.create());
        add(new Span("Prima prova di View"));
        add(new Span("Usa @Route ma non usa MainLayout.class"));
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);

        //--legge da mongo
        Delta deltaDue = (Delta) mongo.findOneFirst(Delta.class);
        String imageTxt = deltaDue.immagine;
        Image imageTre= resourceService.getBandieraFromMongo(imageTxt);
        add(imageTre);
    }

}
