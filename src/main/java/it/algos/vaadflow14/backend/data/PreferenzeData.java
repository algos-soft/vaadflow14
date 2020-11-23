package it.algos.vaadflow14.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEPreferenza;
import it.algos.vaadflow14.backend.packages.preferenza.PreferenzaLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowVar.usaCompany;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 23-nov-2020
 * Time: 21:48
 * Poiché siamo in fase di boot, la sessione non esiste ancora <br>
 * Questo vuol dire che eventuali classi @VaadinSessionScope
 * NON possono essere iniettate automaticamente da Spring <br>
 * Vengono costruite con la BeanFactory <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PreferenzeData {

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
//    @Autowired
    public ApplicationContext appContext;

    protected PreferenzaLogic preferenzaLogic;

    @Autowired
    public PreferenzeData(ApplicationContext appContext) {
        this.preferenzaLogic = appContext.getBean(PreferenzaLogic.class);
    }

    /**
     * Crea le preferenze standard, se non esistono <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Per un reset ai valori di default, c'è il metodo reset() chiamato da preferenzaLogic <br>
     */
    protected void creaPreferenze() {
        List<? extends AEntity> listaCompany = null;
//        IAService serviceCompany;

        if (usaCompany) {
//            serviceCompany = (IAService) StaticContextAccessor.getBean(FlowVar.companyServiceClazz);
//            listaCompany = serviceCompany.findAllAll();
//            for (AEPreferenza eaPref : AEPreferenza.values()) {
//                //--se usa company ed è companySpecifica=true, crea una preferenza per ogni company
//                if (eaPref.isCompanySpecifica()) {
//                    for (AEntity company : listaCompany) {
//                        if (company instanceof Company) {
//                            numPref = preferenzaLogic.creaIfNotExist(eaPref, (Company) company) ? numPref + 1 : numPref;
//                        }
//                    }
//                }
//                else {
//                    numPref = preferenzaLogic.creaIfNotExist(eaPref) ? numPref + 1 : numPref;
//                }
//            }
        }
        else {
            for (AEPreferenza eaPref : AEPreferenza.values()) {
                preferenzaLogic.creaIfNotExist(eaPref);
            }
        }
    }

}
