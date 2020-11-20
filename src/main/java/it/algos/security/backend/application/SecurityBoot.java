package it.algos.security.backend.application;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.boot.FlowBoot;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

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
public class SecurityBoot extends FlowBoot {

//    /**
//     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
//     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
//     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
//     */
//    @Autowired
//    public SimpleData testData;

//    public SecurityBoot() {
//        super(null,null,null);
//    }
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
    protected void initData() {
        super.initData();

//        testData.initData();
    }


    /**
     * Regola alcune variabili generali dell' applicazione al loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione ma il loro valore può essere modificato <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixApplicationVar() {
        super.fixApplicationVar();

        FlowVar.usaCompany = false;
        FlowVar.usaSecurity = true;
        FlowVar.projectName = "Security";
        FlowVar.projectDescrizione = "Programma di prova per testare vaadflow con SpringSecurity ma senza companies";
        FlowVar.projectVersion = 0.73;
        FlowVar.versionDate = LocalDate.of(2020, 8, 21);
        FlowVar.projectNote = "Sviluppo del modulo base in Vaadin14";
        FlowVar.layoutTitle = "Security test";
        FlowVar.usaVaadinIcon = true; //@todo Creare una preferenza e sostituirla qui
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
    }

}
