package it.algos.simple.backend.data;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.simple.backend.application.SimpleCost.*;
import it.algos.simple.backend.packages.bolla.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.data.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
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

    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
    //     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    public AService aService;

    /**
     * Check iniziale di alcune collections <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Vengono create se mancano e se esiste un metodo resetEmptyOnly() nella classe xxxService specifica <br>
     * Crea un elenco di entities/collections che implementano il metodo resetEmptyOnly() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @since java 8
     */
    @Override
    public void fixData() {
        super.fixData();
        super.fixData("simple");
        this.fixDataPackageBolla();
    }

    /**
     * Check iniziale della collection 'Bolla' che NON ha la classe specifica BollaService <br>
     * Devo quindi 'sostituire' qui il metodo resetEmptyOnly() <br>
     */
    private void fixDataPackageBolla() {
        creaBolla("uno", "prima riga");
        creaBolla("due", "seconda riga");
        creaBolla("tre", "terza riga");
        creaBolla("quattro", "quarta riga");
    }

    private void creaBolla(final String code, final String descrizione) {
        BollaEntity bolla;

        if (mongo.isNotEsiste(BollaEntity.class, code)) {
            bolla = BollaEntity.builderBolla().code(code).descrizione(descrizione).build();
            bolla.setId(code);
            mongo.save(bolla);
        }
    }

}
