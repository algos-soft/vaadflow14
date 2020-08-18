package it.algos.simple;


import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 27-lug-2020
 * Time: 17:04
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(scanBasePackages = {"it.algos.vaadflow14","it.algos.simple"}, exclude = {SecurityAutoConfiguration.class})
@EnableVaadin({"it.algos.vaadflow14","it.algos.simple"})
@ComponentScan({"it.algos.vaadflow14","it.algos.simple"})
@EntityScan({"it.algos.vaadflow14","it.algos.simple"})
@EnableMongoRepositories({"it.algos.vaadflow14","it.algos.simple"})
public class SimpleApplication extends SpringBootServletInitializer {

    /**
     * The main method makes it possible to run the application as a plain Java
     * application which starts embedded web server via Spring Boot.
     *
     * @param args command line parameters
     */
    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }

}
