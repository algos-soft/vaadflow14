package it.algos.security;


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
@SpringBootApplication(scanBasePackages = {"it.algos.vaadflow14","it.algos.security"})
@EnableVaadin({"it.algos.vaadflow14","it.algos.security"})
@ComponentScan({"it.algos.vaadflow14","it.algos.security"})
@EntityScan({"it.algos.vaadflow14","it.algos.security"})
@EnableMongoRepositories({"it.algos.vaadflow14","it.algos.security"})
public class SecurityApplication extends SpringBootServletInitializer {

    /**
     * The main method makes it possible to run the application as a plain Java
     * application which starts embedded web server via Spring Boot.
     *
     * @param args command line parameters
     */
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
