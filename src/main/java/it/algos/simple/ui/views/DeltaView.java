package it.algos.simple.ui.views;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import it.algos.simple.backend.packages.Delta;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import it.algos.vaadflow14.backend.packages.crono.secolo.SecoloLogic;
import it.algos.vaadflow14.backend.service.AMongoService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;


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

    @Autowired
    private SecoloLogic secoloLogic;

    @Autowired
    private AMongoService mongo;


    /**
     * Instantiates a new Delta view.
     */
    @PostConstruct
    private void postConstruct() {

        File bandiera = new File("config" + File.separator + "at.png");
        StreamResource resource = null;
         byte[] imageBytes;
         String imageStr=VUOTA;

         //--legge dal file
        try {
             imageBytes = FileUtils.readFileToByteArray(bandiera);
            resource = new StreamResource("dummyImageName.jpg", () -> new ByteArrayInputStream(imageBytes));
            imageStr=Base64.encodeBase64String( imageBytes);
        } catch (Exception unErrore) {
        }

        Image image = new Image(resource, "dummy image");
        image.setWidth("21px");
        image.setHeight("21px");
        add(image);


        //--registra su mongo
        Delta deltaUno = new Delta();
        deltaUno.code = "alfa";
        deltaUno.id = deltaUno.code;
        deltaUno.secolo = (Secolo) secoloLogic.findById("vsecolo");
        deltaUno.immagine = imageStr;
        mongo.save(deltaUno);

        add(VaadinIcon.HOSPITAL.create());
        add(new Span("Prima prova di View"));
        add(new Span("Usa @Route ma non usa MainLayout.class"));
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);

        //--legge da mongo
        Delta deltaDue=(Delta)mongo.findOneFirst(Delta.class);
        String imageDue= deltaDue.immagine;
        byte[]  bytesDue= Base64.decodeBase64( imageDue);
        StreamResource resourceDue = null;
        try {
            resourceDue = new StreamResource("dummyImageName.jpg", () -> new ByteArrayInputStream(bytesDue));
        } catch (Exception unErrore) {
        }
        Image imageTre = new Image(resourceDue, "dummy image");
        imageTre.setWidth("21px");
        imageTre.setHeight("21px");
        add(imageTre);
    }

}
