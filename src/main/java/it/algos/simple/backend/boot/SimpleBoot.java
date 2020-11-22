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

import java.time.LocalDate;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 01-mag-2020
 * Time: 11:01
 * <p>
 * Running logic after the Spring context has been initialized <br>
 * Executed on container startup, before any browse command <br>
 * <p>
 * Sottoclasse concreta per l' applicazione specifica: <br>
 * - per sovrascrivere alcune variabili in fixApplicationVar() <br>
 * - lanciare gli schedulers in background <br>
 * - costruire e regolare una versione demo <br>
 * - controllare l' esistenza di utenti abilitati all' accesso <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimpleBoot extends FlowBoot {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    public SimpleData simpleData;


    /**
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public SimpleBoot(SimpleData simpleData) {
        super();
        this.setSimpleData(simpleData);
    }


    /**
     * Inizializzazione di alcuni parametri del database mongoDB <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixDBMongo() {
        super.fixDBMongo();
    }


    /**
     * Crea le preferenze standard, se non esistono <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Per un reset ai valori di default, c'è il metodo reset() chiamato da preferenzaService <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected int creaPreferenze() {
        return super.creaPreferenze();
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
        FlowVar.projectVersion = Double.parseDouble(environment.getProperty("algos.vaadflow.version"));
        FlowVar.versionDate = LocalDate.of(2020, 11, 22);
        FlowVar.projectNote = "Sviluppo del modulo base in Vaadin14";
        FlowVar.layoutTitle = "Simple test";
        FlowVar.usaVaadinIcon = true; //@todo Creare una preferenza e sostituirla qui
        FlowVar.usaCronoPackages = true;
        FlowVar.usaGeografiaPackages = true;
    }


    /**
     * Inizializzazione dei dati di alcune collections sul DB mongo <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void initData() {
        super.initData();
        //        simpleData.initData();
    }

    /**
     * Regolazione delle preferenze standard effettuata nella sottoclasse specifica <br>
     * Serve per modificare solo per l'applicazione specifica il valore standard della preferenza <br>
     * Eventuali modifiche delle preferenze specifiche (che peraltro possono essere modificate all'origine) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        super.fixPreferenze();
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

    public void setSimpleData(SimpleData simpleData) {
        this.simpleData = simpleData;
    }

}
