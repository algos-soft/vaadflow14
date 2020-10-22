package it.algos.simple.backend.data;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Service;

/**
 * Project alfa
 * Created by Algos
 * User: gac
 * Date: mar, 20-ott-2020
 * Time: 18:34
 * This init listener is run once whenever the Vaadin context starts.
 * As such, it is a great place to create dummy data.
 */
@Service
public class Prova implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
//        System.out.println("_________DB initiation has started____________");

        // Initializing tables in the database

//        System.out.println("_________DB initiation has finished____________");
    }

}
