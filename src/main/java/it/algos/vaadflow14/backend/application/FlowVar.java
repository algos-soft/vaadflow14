package it.algos.vaadflow14.backend.application;


import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mar, 13-ago-2019
 * Time: 10:50
 *
 * <p>Classe statica (astratta) per le variabili generali dell'applicazione <br>
 * Le variabili (static) sono uniche per tutta l'applicazione <br>
 * Il valore delle variabili è unico per tutta l'applicazione, ma può essere modificato <br>
 * The compiler automatically initializes class fields to their default values before setting <br>
 * them with any initialization values, so there is no need to explicitly set a field to its <br>
 * default value. <br>
 * Further, under the logic that cleaner code is better code, it's considered poor style to do so. <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FlowVar {


    /**
     * Controlla se l'applicazione gira in 'debug mode' oppure no <br>
     * Di default (per sicurezza) uguale a true <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'backend.application' <br>
     */
    public static boolean usaDebug;


    /**
     * Controlla se l'applicazione usa il login oppure no <br>
     * Se si usa il login, occorre la classe SecurityConfiguration <br>
     * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
     * Di default (per sicurezza) uguale a true <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'backend.application' <br>
     */
    public static boolean usaSecurity;

    /**
     * Controlla se l'applicazione è multi-company oppure no <br>
     * Di default (per sicurezza) uguale a true <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'backend.application' <br>
     */
    public static boolean usaCompany;

    /**
     * Nome identificativo dell'applicazione <br>
     * Usato (eventualmente) nella barra di menu in testa pagina <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'backend.application' <br>
     */
    public static String projectName;


    /**
     * Descrizione completa dell'applicazione <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
     */
    public static String projectDescrizione;


    /**
     * Versione dell'applicazione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'backend.application' <br>
     */
    public static double projectVersion;


    /**
     * Data della versione dell'applicazione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'backend.application' <br>
     */
    public static LocalDate versionDate;


    /**
     * Eventuali informazioni aggiuntive da utilizzare nelle informazioni <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'backend.application' <br>
     */
    public static String projectNote;


    /**
     * Flag per usare le icone VaadinIcon <br>
     * In alternativa usa le icone 'lumo' <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'backend.application' <br>
     */
    public static boolean usaVaadinIcon;


    /**
     * Eventuali titolo della pagina <br>
     */
    public static String layoutTitle;


    //    /**
    //     * Service da usare per recuperare dal mongoDB l'utenza loggata tramite 'username' che è unico <br>
    //     * Di default UtenteService oppure eventuale sottoclasse specializzata per applicazioni con accessi particolari <br>
    //     * Eventuale casting a carico del chiamante <br>
    //     * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
    //     */
    //    public static Class loginServiceClazz = UtenteService.class;

    //    /**
    //     * Classe da usare per gestire le informazioni dell'utenza loggata <br>
    //     * Di default ALogin oppure eventuale sottoclasse specializzata per applicazioni con accessi particolari <br>
    //     * Eventuale casting a carico del chiamante <br>
    //     * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
    //     */
    //    public static Class loginClazz = ALogin.class;

    //    /**
    //     * Service da usare per recuperare la lista delle Company (o sottoclassi) <br>
    //     * Di default CompanyService oppure eventuale sottoclasse specializzata per Company particolari <br>
    //     * Eventuale casting a carico del chiamante <br>
    //     * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
    //     */
    //    public static Class companyServiceClazz = CompanyService.class;


    /**
     * Nome da usare per recuperare la lista delle Company (o sottoclassi) <br>
     * Di default 'company' oppure eventuale sottoclasse specializzata per Company particolari <br>
     * Eventuale casting a carico del chiamante <br>
     * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
     */
    public static String companyClazzName;


    /**
     * Path per recuperare dalle risorse un'immagine da inserire nella barra di menu di MainLayout. <br>
     * Ogni applicazione può modificarla <br>
     * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
     */
    public static String pathLogo;


    /**
     * Lista dei moduli di menu da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
     * Regolata dall'applicazione durante l'esecuzione del 'container startup' (non-UI logic) <br>
     * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
     */
    public static List<Class<?>> menuRouteList;

}
