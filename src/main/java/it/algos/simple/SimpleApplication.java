package it.algos.simple;


import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

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

}
