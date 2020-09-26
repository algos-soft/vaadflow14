package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 24-set-2020
 * Time: 20:35
 * <p>
 * Simple layer around TextField <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AImageField extends AField<String> {

    private final Image imageField;

    private HorizontalLayout layout;

    private String width;

    private String height;


    /**
     * Costruttore senza parametri <br>
     * L' istanza viene costruita con appContext.getBean(ATextField.class) <br>
     */
    public AImageField() {
        imageField = new Image();
        //        layout = new HorizontalLayout();
        //        //        selezione = new Button();
        //        //        selezione.setText("Seleziona");
        //        //        selezione.addClickListener(e -> seleziona());
        //        add(layout);
        //        fixUpload();
        //        Upload upload = new Upload();
        //        upload.setDropLabel(new Span());
        //        upload.setUploadButton(new Button("Seleziona..."));
        //        upload.addAttachListener(e -> seleziona());
        //        add(imageField);
        fixUpload();

    } // end of SpringBoot constructor


    protected Upload fixUpload() {
        MemoryBuffer buffer = new MemoryBuffer();
        HorizontalLayout layer = new HorizontalLayout();
        HorizontalLayout output = new HorizontalLayout();
        Upload upload = new Upload(buffer);
        upload.setDropLabel(new Span());
        upload.setUploadButton(new Button("Seleziona..."));
        upload.addSucceededListener(event -> {
            Component component = createComponent(event.getMIMEType(), event.getFileName(), buffer.getInputStream());
            showOutput(event.getFileName(), component, output);
        });
        layer.add(imageField, upload, output);
        add(layer);

        return upload;
    }


    protected void seleziona() {
        Notification.show("Not yet. Coming soon.", 3000, Notification.Position.MIDDLE);
    }


    @Override
    protected String generateModelValue() {
        return VUOTA;
    }


    @Override
    protected void setPresentationValue(String value) {
        byte[] bytesDue = Base64.decodeBase64(value);
        StreamResource resource = null;
        try {
            resource = new StreamResource("dummyImageName.jpg", () -> new ByteArrayInputStream(bytesDue));
        } catch (Exception unErrore) {
        }

        imageField.setSrc(resource);
    }


    @Override
    public void setWidth(String width) {
        this.width = width;
        imageField.setWidth(width);
    }


    @Override
    public void setHeight(String height) {
        this.height = height;
        imageField.setHeight(height);
    }


    private Component createComponent(String mimeType, String fileName, InputStream stream) {
        if (mimeType.startsWith("text")) {
            return createTextComponent(stream);
        } else if (mimeType.startsWith("image")) {
            Image image = new Image();
            try {

                byte[] bytes = IOUtils.toByteArray(stream);
                image.getElement().setAttribute("src", new StreamResource(fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            image.setWidth(reader.getWidth(0) + "px");
                            image.setHeight(reader.getHeight(0) + "px");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            image.setWidth(width);
            image.setHeight(height);
            return image;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'", mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;

    }


    private Component createTextComponent(InputStream stream) {
        String text;
        try {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            text = "exception reading stream";
        }
        return new Text(text);
    }


    private void showOutput(String text, Component content, HasComponents outputContainer) {
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add(p);
        outputContainer.add(content);
    }

}
