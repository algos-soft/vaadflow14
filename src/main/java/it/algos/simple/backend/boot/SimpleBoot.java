package it.algos.simple.backend.boot;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.simple.backend.data.SimpleData;
import it.algos.simple.backend.packages.*;
import it.algos.simple.ui.views.DeltaView;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.boot.FlowBoot;
import it.algos.vaadflow14.backend.packages.anagrafica.address.Address;
import it.algos.vaadflow14.backend.packages.anagrafica.via.Via;
import it.algos.vaadflow14.wizard.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.time.LocalDate;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 01-mag-2020
 * Time: 11:01
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimpleBoot extends FlowBoot {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    public SimpleData testData;


    @Autowired
    public SimpleBoot(SimpleData testData) {
        super();
        this.testData = testData;
    }


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le (eventuali) istanze @Autowired <br>
     * Questo metodo viene chiamato subito dopo che il framework ha terminato l' init() implicito <br>
     * del costruttore e PRIMA di qualsiasi altro metodo <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l' ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
    }


    /**
     * Riferimento alla sottoclasse specifica di ABoot per utilizzare il metodo sovrascritto resetPreferenze() <br>
     * DEVE essere sovrascritto <br>
     */
    protected void regolaRiferimenti() {
        //--riferimento alla sottoclasse di AData da usare per inizializzare i dati, col metodo loadAllData()
    }


    /**
     * Inizializzazione dei dati di alcune collections sul DB mongo <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void inizializzaData() {
        super.inizializzaData();

        testData.initData();
    }


    /**
     * Regola alcune variabili generali dell' applicazione al loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione, ma il loro valore può essere modificato <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixApplicationVar() {
        super.fixApplicationVar();

        FlowVar.usaSecurity = false;
        FlowVar.usaCompany = false;
        FlowVar.projectName = "Simple";
        FlowVar.projectDescrizione = "Programma di prova per testare vaadflow senza security e senza companies";
//        FlowVar.projectVersion = Double.parseDouble(environment.getProperty("algos.framework.version"));
        FlowVar.versionDate = LocalDate.of(2020, 11, 7);
        FlowVar.projectNote = "Sviluppo del modulo base in Vaadin14";
        FlowVar.layoutTitle = "Simple test";
        FlowVar.usaVaadinIcon = true; //@todo Creare una preferenza e sostituirla qui
        FlowVar.usaCronoPackages = true;
        FlowVar.usaGeografiaPackages = true;
    }


    /**
     * Questa classe viene invocata PRIMA della chiamata del browser
     * Se NON usa la security, le @Route vengono create solo qui
     * Se USA la security, le @Route vengono sovrascritte all' apertura del brose nella classe AUserDetailsService
     * <p>
     * Aggiunge le @Route (view) standard
     * Nella sottoclasse concreta che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in FlowVar
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addMenuRoutes() {
        super.addMenuRoutes();

        FlowVar.menuRouteList.add(Wizard.class);
        FlowVar.menuRouteList.add(Via.class);
        FlowVar.menuRouteList.add(Address.class);
        FlowVar.menuRouteList.add(DeltaView.class);
        FlowVar.menuRouteList.add(Alfa.class);
        FlowVar.menuRouteList.add(Beta.class);
        FlowVar.menuRouteList.add(Gamma.class);
        FlowVar.menuRouteList.add(Delta.class);
        FlowVar.menuRouteList.add(Omega.class);
        FlowVar.menuRouteList.add(Lambda.class);
        FlowVar.menuRouteList.add(PiView.class);
    }

}
