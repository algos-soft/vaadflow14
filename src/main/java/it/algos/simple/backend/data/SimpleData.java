package it.algos.simple.backend.data;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.simple.backend.application.SimpleCost.*;
import it.algos.simple.backend.packages.bolla.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.data.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.exceptions.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 01-ago-2020
 * Time: 06:36
 * <p>
 * Check iniziale per la costruzione di alcune collections e delle preferenze <br>
 * Viene invocata PRIMA della chiamata del browser, da (@EventListener)FlowBoot.onContextRefreshEvent() <br>
 * Crea i dati di alcune collections sul DB mongo <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 *
 * @since java 8
 */
@SpringComponent
@Qualifier(TAG_SIMPLE_DATA)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@AIScript(sovraScrivibile = false)
public class SimpleData extends FlowData {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AMongoService mongo;

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    public EntityService entityService;


    /**
     * Check iniziale. Ad ogni avvio del programma spazzola tutte le collections <br>
     * Ognuna viene ricreata (mantenendo le entities che hanno reset=false) se:
     * - xxx->@AIEntity usaBoot=true,
     * - esiste xxxService.reset(),
     * - la collezione non contiene nessuna entity che abbia la property reset=true
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @since java 8
     */
    @Override
    public void resetData() {
        super.resetData();

        super.resetData("simple");
        this.fixDataPackageBolla();
    }

    /**
     * Check iniziale della collection 'Bolla' che NON ha la classe specifica BollaService <br>
     * Devo quindi 'sostituire' qui il metodo resetEmptyOnly() <br>
     */
    private void fixDataPackageBolla() {
        entityService = appContext.getBean(EntityService.class, Bolla.class);
        AIResult result = entityService.reset();
        int numRec = 0;

        if (result.isErrato()) {
            logger.log(AETypeLog.checkData, result.getMessage());
            return;
        }

        numRec = creaBolla("uno", "prima riga") ? numRec + 1 : numRec;
        numRec = creaBolla("due", "seconda riga") ? numRec + 1 : numRec;
        numRec = creaBolla("tre", "terza riga") ? numRec + 1 : numRec;
        numRec = creaBolla("quattro", "quarta riga") ? numRec + 1 : numRec;

        logger.log(AETypeLog.checkData, result.getMessage());
        return ;
    }


    private boolean creaBolla(final String code, final String descrizione) {
        boolean status = false;
        Bolla bolla;

        if (mongo.isNotEsiste(Bolla.class, code)) {
            bolla = Bolla.builderBolla().code(code).descrizione(descrizione).build();
            bolla.setId(code);
            try {
                mongo.save(bolla);
            } catch (AMongoException unErrore) {
            }
            status = true;
        }

        return status;
    }

}
