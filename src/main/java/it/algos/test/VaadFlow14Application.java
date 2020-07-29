package it.algos.test;


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
@SpringBootApplication(scanBasePackages = {"it.algos"}, exclude = {SecurityAutoConfiguration.class})
@EnableVaadin({"it.algos"})
@ComponentScan({"it.algos"})
@EntityScan({"it.algos"})
@EnableMongoRepositories({"it.algos"})
public class VaadFlow14Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VaadFlow14Application.class, args);
    }

}
