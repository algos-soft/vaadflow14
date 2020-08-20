package it.algos.security.ui.springsecurity;

import it.algos.vaadflow14.backend.annotation.StaticContextAccessor;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.packages.company.Company;
import it.algos.vaadflow14.backend.packages.security.Utente;
import it.algos.vaadflow14.backend.service.AMongoService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static it.algos.vaadflow14.backend.application.FlowVar.projectName;

/**
 * Implements the {@link UserDetailsService}.
 * <p>
 * This implementation searches for {@link Utente} entities by the e-mail address
 * supplied in the login screen.
 */
@Service
@Primary
public class AUserDetailsService implements UserDetailsService {


    public PasswordEncoder passwordEncoder;


    public AUserDetailsService() {
    }// end of Spring constructor


    /**
     * Recovers the {@link Utente} from the database using the e-mail address supplied
     * in the login screen. If the user is found, returns a
     * {@link User}.
     *
     * @param uniqueUserName User's uniqueUserName
     */
    @Override
    public UserDetails loadUserByUsername(String uniqueUserName) throws UsernameNotFoundException {
        String passwordHash = VUOTA;
        Company company;
        Collection<? extends GrantedAuthority> authorities;
        AMongoService mongo = StaticContextAccessor.getBean(AMongoService.class);
        Utente utente = (Utente) mongo.findOneUnique(Utente.class, FlowCost.FIELD_USER, uniqueUserName);

        if (utente != null) {
            passwordHash = passwordEncoder.encode(utente.getPassword());
            authorities = utente.getAuthorities();
            company = utente.company;
            FlowVar.layoutTitle = company != null ? company.getDescrizione() : projectName;
            return new User(utente.getUsername(), passwordHash, authorities);
        } else {
            throw new UsernameNotFoundException("Non c'è nessun utente di nome: " + uniqueUserName);
        }

    }

}
