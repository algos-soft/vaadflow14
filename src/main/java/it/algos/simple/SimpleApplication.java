package it.algos.simple;


import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 27-lug-2020
 * Time: 17:04
 * The entry point of the Spring Boot application. <br>
 * <p>
 * Spring Boot introduces the @SpringBootApplication annotation. <br>
 * This single annotation is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan. <br>
 * Se l'applicazione NON usa la security, aggiungere exclude = {SecurityAutoConfiguration.class} a @SpringBootApplication <br>
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
        SpringApplication.run(SimpleApplication.class, args);
    }// end of SpringBoot constructor


    @EventListener(ContextRefreshedEvent.class)
    public void ContextRefreshedEventExecute(){
//        System.out.println("");
//        System.out.println("Context Event Listener is getting executed from main class");
//        System.out.println("");
    }


    @EventListener(ApplicationReadyEvent.class)
    public void EventListenerExecute(){
//        System.out.println("");
//        System.out.println("Application Ready Event is successfully Started from main class");
//        System.out.println("");
    }

    @EventListener(ApplicationFailedEvent.class)
    public void EventListenerExecuteFailed(){
//        System.out.println("");
//        System.out.println("Application Event Listener is Failed from main class");
//        System.out.println("");
    }
}
