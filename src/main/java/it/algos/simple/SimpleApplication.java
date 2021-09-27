package it.algos.simple;

import com.vaadin.flow.spring.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.boot.autoconfigure.security.servlet.*;
import org.springframework.boot.context.event.*;
import org.springframework.boot.web.servlet.support.*;
import org.springframework.context.event.*;
import org.vaadin.artur.helpers.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 25-set-2021
 * Time: 14:58
 */
@SpringBootApplication(scanBasePackages = {"it.algos.vaadflow14","it.algos.simple"}, exclude = {SecurityAutoConfiguration.class})
@EnableVaadin({"it.algos.vaadflow14","it.algos.simple"})
@EntityScan({"it.algos.vaadflow14","it.algos.simple"})
public class SimpleApplication extends SpringBootServletInitializer {

    /**
     * The main method makes it possible to run the application as a plain Java
     * application which starts embedded web server via Spring Boot.
     *
     * @param args command line parameters
     */
    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(SimpleApplication.class, args));
    }// end of SpringBoot constructor


    @EventListener(ContextRefreshedEvent.class)
    public void ContextRefreshedEventExecute(){
    }


    @EventListener(ApplicationReadyEvent.class)
    public void EventListenerExecute(){
    }

    @EventListener(ApplicationFailedEvent.class)
    public void EventListenerExecuteFailed(){
    }
}
