package it.algos.simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.simple.backend.application.SimpleCost.*;
import it.algos.simple.backend.data.*;
import it.algos.simple.backend.enumeration.*;
import it.algos.simple.backend.packages.*;
import it.algos.simple.backend.packages.bolla.*;
import it.algos.simple.backend.packages.fattura.*;
import it.algos.simple.pratical.ch2.*;
import it.algos.simple.ui.enumeration.*;
import it.algos.simple.ui.views.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.boot.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.packages.anagrafica.address.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import it.algos.vaadflow14.backend.packages.crono.anno.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 01-mag-2020
 * Time: 11:01
 * <p>
 * Running logic after the Spring context has been initialized <br>
 * Executed on container startup, before any browse command <br>
 * Any class that use this @EventListener annotation, will be executed
 * before the application is up and its onContextRefreshEvent method will be called
 * The method onApplicationEvent() will be executed nella sottoclasse before
 * the application is up and <br>
 * <p>
 * Sottoclasse concreta dell' applicazione specifica che: <br>
 * 1) regola alcuni parametri standard del database MongoDB <br>
 * 2) regola le variabili generali dell'applicazione <br>
 * 3) crea i dati di alcune collections sul DB mongo <br>
 * 4) crea le preferenze standard e specifiche dell'applicazione <br>
 * 5) aggiunge le @Route (view) standard e specifiche <br>
 * 6) lancia gli schedulers in background <br>
 * 7) costruisce una versione demo <br>
 * 8) controlla l' esistenza di utenti abilitati all' accesso <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@AIScript(sovraScrivibile = false)
public class SimpleBoot extends FlowBoot {


    /**
     * Regola alcuni parametri standard del database MongoDB <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixDBMongo() {
        super.fixDBMongo();
    }


    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixVariabili() {
        super.fixVariabili();

        FlowVar.usaDebug = false;
        FlowVar.usaCompany = false;
        FlowVar.usaSecurity = false;
        FlowVar.dataClazz = SimpleData.class;
        FlowVar.projectNameDirectoryIdea = "vaadflow14";
        FlowVar.projectNameModulo = "simple";
        FlowVar.projectNameUpper = "Simple";
        FlowVar.projectDescrizione = "Programma di prova per testare vaadflow senza security e senza company";
        FlowVar.projectNote = "Sviluppo del modulo base in Vaadin14";
        FlowVar.usaVaadinIcon = true; //@todo Creare una preferenza e sostituirla qui
        FlowVar.projectVersion = environment.getProperty("algos.simple.version") != null ? Double.parseDouble(environment.getProperty("algos.simple.version")) : 0.0;
        FlowVar.preferenzeSpecificheList = Arrays.asList(AESimplePreferenza.values());
        FlowVar.bottoniSpecificiList = Arrays.asList(AESimpleButton.values());

        FlowVar.usaAdminPackages = true;
        FlowVar.usaGestionePackages = true;
        FlowVar.usaGeografiaPackages = true;
        FlowVar.usaCronoPackages = true;
    }


    /**
     * Primo ingresso nel programma nella classe concreta, tramite il <br>
     * metodo FlowBoot.onContextRefreshEvent() della superclasse astratta <br>
     * Crea i dati di alcune collections sul DB mongo <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     * <p>
     * Invoca il metodo fixData() di FlowData oppure della sottoclasse <br>
     */
    protected void fixData() {
        if (FlowVar.dataClazz != null && FlowVar.dataClazz.equals(SimpleData.class)) {
            dataInstance.resetData();
        }
    }


    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * <p>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * Se NON usa la security, le @Route vengono create solo qui <br>
     * Se USA la security, le @Route vengono sovrascritte all' apertura del browser nella classe AUserDetailsService <br>
     * <p>
     * Nella sottoclasse concreta che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in FlowVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixMenuRoutes() {
        FlowVar.menuRouteList.add(FatturaLogicList.class);
        FlowVar.menuRouteList.add(Bolla.class);
        FlowVar.menuRouteList.add(WelcomeView.class);
        FlowVar.menuRouteList.add(ViaLogicList.class);
        //        FlowVar.menuRouteList.add(ProductManagementBinderView.class);
        //        FlowVar.menuRouteList.add(ProductManagementCoversionView.class);
        //        FlowVar.menuRouteList.add(ProductManagementJakartaView.class);
        //        FlowVar.menuRouteList.add(ProductManagementManualView.class);
        //        FlowVar.menuRouteList.add(ProductManagementNestedView.class);

        super.fixMenuRoutes();

        if (AEPreferenza.usaDebug.is()) {
            FlowVar.menuRouteList.add(Address.class);
            FlowVar.menuRouteList.add(PiService.class);
            FlowVar.menuRouteList.add(Gamma.class);
            FlowVar.menuRouteList.add(Delta.class);
            FlowVar.menuRouteList.add(Omega.class);
            FlowVar.menuRouteList.add(Lambda.class);
            FlowVar.menuRouteList.add(PiView.class);
            FlowVar.menuRouteList.add(Anno.class);
            FlowVar.menuRouteList.add(LabelView.class);
            FlowVar.menuRouteList.add(ButtonView.class);
        }
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixSchedules() {
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixDemo() {
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixUsers() {
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(TAG_SIMPLE_DATA)
    public void setDataInstance(SimpleData dataInstance) {
        this.dataInstance = dataInstance;
    }

}