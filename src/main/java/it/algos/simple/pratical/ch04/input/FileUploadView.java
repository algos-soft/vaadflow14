package it.algos.simple.pratical.ch04.input;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.upload.*;
import com.vaadin.flow.component.upload.receivers.*;
import com.vaadin.flow.router.*;

import java.io.*;
import java.util.*;

@Route("file-upload")
public class FileUploadView extends Composite<Component> {

    @Override
    protected Component initContent() {
        MemoryBuffer receiver = new MemoryBuffer();
        Upload upload = new Upload(receiver);
        upload.setAcceptedFileTypes("text/plain");
        upload.addSucceededListener(event -> {
            InputStream in = receiver.getInputStream();
            long count = new Scanner(in).findAll("[Aa]").count();
            Notification.show("A x " + count + " times");
        });

        return new VerticalLayout(
                new Text("Upload a text file:"),
                upload
        );
    }

}
