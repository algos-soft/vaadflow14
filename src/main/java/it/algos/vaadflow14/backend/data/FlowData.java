package it.algos.vaadflow14.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.enumeration.AECrono;
import it.algos.vaadflow14.backend.enumeration.AEGeografia;
import it.algos.vaadflow14.backend.enumeration.AETypeLog;
import it.algos.vaadflow14.backend.service.AArrayService;
import it.algos.vaadflow14.backend.service.AFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 20-ott-2018
 * Time: 08:53
 * <p>
 * Poiché siamo in fase di boot, la sessione non esiste ancora <br>
 * Questo vuol dire che eventuali classi @VaadinSessionScope
 * NON possono essere iniettate automaticamente da Spring <br>
 * Vengono costruite con la BeanFactory <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FlowData extends AData {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext applicationContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AFileService file;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AArrayService array;


    /**
     * Crea le preferenze standard dell'applicazione <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Per un reset ai valori di default, c'è il metodo reset() chiamato da preferenzaLogic <br>
     */
    public void fixPreferenze() {
    }


    /**
     * Check iniziale di alcune collections <br>
     * Crea un elenco specifico di collections che implementano il metodo 'reset' nella classe xxxLogic <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Puo essere sovrascritto, ma SENZA invocare il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     */
    public void fixData() {
        String moduleName = "vaadflow14";
        List<String> listaCanonicalNamesEntity = file.getModuleSubFilesEntity(moduleName);
        List<String> listaEntityEffettivamenteUsate;
        String collezione;

        listaEntityEffettivamenteUsate = checkFiles(listaCanonicalNamesEntity);
        logger.log(AETypeLog.checkData, "Sono state trovate " + listaEntityEffettivamenteUsate.size() + " classi di tipo AEntity da controllare");
        if (array.isValid(listaEntityEffettivamenteUsate)) {
            for (String canonicalName : listaEntityEffettivamenteUsate) {
                collezione = file.estraeClasseFinale(canonicalName).toLowerCase();

                if (checkFile(canonicalName)) {
                    checkSingolaCollection(canonicalName);
                }
                else {
                    logger.log(AETypeLog.checkData, "La collezione " + collezione + " non è prevista in questa applicazione");
                }
            }
        }
    }

    /**
     * Controlla i flags di due enumerations <br>
     * Esclude i files appartenenti se i rispettivi flag sono falsi <br>
     */
    public List<String> checkFiles(List<String> listaCanonicalNameEntity) {
        List<String> listaCanonicalNameEntityEffettivamenteUsati = new ArrayList<>();

        for (String canonicalName : listaCanonicalNameEntity) {
            if (checkFile(canonicalName)) {
                listaCanonicalNameEntityEffettivamenteUsati.add(canonicalName);
            }
        }

        return listaCanonicalNameEntityEffettivamenteUsati;
    }


    /**
     * Controlla i flags di due enumerations <br>
     * Esclude i files appartenenti se i rispettivi flag sono falsi <br>
     */
    public boolean checkFile(String canonicalName) {
        String name = file.estraeClasseFinale(canonicalName).toLowerCase();

        if (!FlowVar.usaCronoPackages && AECrono.getValue().contains(name)) {
            return false;
        }
        if (!FlowVar.usaGeografiaPackages && AEGeografia.getValue().contains(name)) {
            return false;
        }

        return true;
    }

}
