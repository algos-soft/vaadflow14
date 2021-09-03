package it.algos.security.ui.springsecurity;

import it.algos.vaadflow14.backend.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.packages.company.*;
import it.algos.vaadflow14.backend.packages.security.utente.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Implements the {@link UserDetailsService}.
 * <p>
 * This implementation searches for {@link Utente} entities by the e-mail address
 * supplied in the login screen.
 */
@Service
@Primary
public class SecurityUserDetailsService implements UserDetailsService {

    public PasswordEncoder passwordEncoder;


    public SecurityUserDetailsService() {
    }// end of Spring constructor


    /**
     * Recovers the {@link Utente} from the database using the e-mail address supplied
     * in the login screen. If the user is found, returns a
     * {@link User}.
     *
     * @param uniqueUserName User's uniqueUserName
     */
//    @Override
    public UserDetails loadUserByUsername(String uniqueUserName) throws UsernameNotFoundException {
        String passwordHash = VUOTA;
        Utente utente;
        Company company;
        Collection<? extends GrantedAuthority> authorities;
        AIMongoService mongo = StaticContextAccessor.getBean(AIMongoService.class);

        uniqueUserName = uniqueUserName.toLowerCase();
        utente = (Utente) ((MongoService) mongo).findByIdOld(Utente.class, uniqueUserName);//@todo da controllare
//        utente = (Utente) mongo.findOneUnique(Utente.class, FlowCost.FIELD_USER, uniqueUserName);

        if (utente != null) {
            passwordHash = passwordEncoder.encode(utente.getPassword());
            authorities = utente.getAuthorities();
            company = utente.company;
//            FlowVar.layoutTitle = company != null ? company.getDescrizione() : projectName;
            return new User(utente.getUsername(), passwordHash, authorities);
        } else {
            throw new UsernameNotFoundException("Non c'è nessun utente di nome: " + uniqueUserName);
        }

    }

}
