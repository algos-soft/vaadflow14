package it.algos.security.ui.springsecurity;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 29-set-2021
 * Time: 20:48
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig  {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //Public views need to be added to the configuration before calling super.configure()
//        http.authorizeRequests().antMatchers("/public-view").permitAll(); // custom matcher
//        http.authorizeRequests().antMatchers("mese").permitAll(); // custom matcher
//
//        // Set default security policy that permits Vaadin internal requests and
//        // denies all other
//        super.configure(http);
//        // use a form based login
//        http.formLogin();
//    }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//        web.ignoring().antMatchers("/images/**");
//    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // Configure users and roles in memory
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}user").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}admin").roles("ADMIN", "USER");
//    }


}


