package it.algos.simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.simple.backend.application.SimpleCost.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.vers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 08-feb-2022
 * Time: 16:53
 * <p>
 * Log delle versioni, modifiche e patch installate <br>
 * <p>
 * Executed on container startup <br>
 * Setup non-UI logic here <br>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat o altri) <br>
 * Eseguita quindi a ogni avvio/riavvio del server e NON a ogni sessione <br>
 * Classe concreta specifica di ogni progetto <br>
 */
@SpringComponent
@Qualifier(TAG_SIMPLE_VERSION)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AIScript(sovraScrivibile = false)
public class SimpleVers extends FlowVers {

    //    /**
    //     * Performing the initialization in a constructor is not suggested <br>
    //     * as the state of the UI is not properly set up when the constructor is invoked. <br>
    //     * <p>
    //     * La injection viene fatta da SpringBoot solo alla fine del metodo init() del costruttore <br>
    //     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
    //     * <p>
    //     * L'istanza può essere creata con  appContext.getBean(xxxClass.class);  oppure con @Autowired <br>
    //     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
    //     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
    //     */
    //    @PostConstruct
    //    protected void postConstruct() {
    //        this.inizia();
    //    }

    /**
     * This method is called prior to the servlet context being initialized (when the Web application is deployed). <br>
     * You can initialize servlet context related data here. <br>
     * <p>
     * Tutte le aggiunte, modifiche e patch vengono inserite con una versione <br>
     * L'ordine di inserimento è FONDAMENTALE <br>
     */
    @Override
    public void inizia() {
        super.inizia();

        //--prima installazione del progetto specifico Simple
        //--non fa nulla, solo informativo
        super.specifico(AETypeVers.setup, "Setup", "Installazione iniziale del progetto specifico Simple (di prova)");
    }

    //        //--versione 1.68 del 29.1.22
    //        //--aggiunta della preferenza usaInvioMessaggi (una per ogni croce)
    //        //--regola il valore a false per tutte le croci
    //        if (versioneService.isMancaByKeyUnica(PATCH_1)) {
    //            if (addPreferenzaInvioMessaggi()) {
    //                versioneService.creaIfNotExistKey(PATCH_1, "1.68 del 29.1.22", "Aggiunta delle preferenze usaInvioMessaggi (una per ogni croce). Regolato il valore a false per tutte le croci");
    //            }
    //        }

}

