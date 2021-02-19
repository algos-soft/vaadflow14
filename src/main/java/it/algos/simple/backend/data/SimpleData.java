package it.algos.simple.backend.data;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.simple.backend.application.SimpleCost.*;
import it.algos.simple.backend.enumeration.*;
import it.algos.simple.backend.packages.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.data.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.packages.preferenza.*;
import it.algos.vaadflow14.backend.wrapper.*;
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
 * Viene invocata PRIMA della chiamata del browser, tramite il <br>
 * metodo FlowBoot.onContextRefreshEvent() <br>
 * Crea i dati di alcune collections sul DB mongo <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
 *
 * @since java 8
 */
@SpringComponent
@Qualifier(TAG_SIMPLE_DATA)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AIScript(sovraScrivibile = false)
public class SimpleData extends FlowData {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public GammaLogic gammaLogic;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LambdaLogic lambdaLogic;


    /**
     * Check iniziale di alcune collections <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Vengono create se mancano e se esiste un metodo resetEmptyOnly() nella classe xxxLogic specifica <br>
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
    }



    /**
     * Ricostruisce le preferenze standard dell'applicazione <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * <p>
     *
     * @param isReset true: invocato da xxxLogic.resetEmptyOnly(), con click sul bottone Reset di PreferenzaList
     *                false: invocato da xxxData.fixPreferenze(), in fase di Startup <br>
     *                <br>
     */
    @Override
    public AIResult resetPreferenze(PreferenzaService preferenzaService, boolean isReset) {
        AIResult result = super.resetPreferenze(preferenzaService,isReset);
        int numRec = 0;

        if (result.isErrato()) {
            return result;
        }

        //-- specifiche (facoltative) dell'applicazione in uso prese da una enumeration apposita
        for (AIPreferenza aePref : AESimplePreferenza.values()) {
            numRec = preferenzaService.creaIfNotExist(aePref) != null ? numRec + 1 : numRec;
        }

        if (numRec == 0) {
            result = AResult.valido("Non ci sono nuove preferenze specifiche da aggiungere.");
        }
        else {
            if (numRec == 1) {
                result = AResult.valido("Mancava una preferenza  specifica che è stata aggiunta senza modificare i valori di quelle esistenti");
            }
            else {
                result = AResult.valido("Mancavano " + numRec + " preferenze specifiche che sono state aggiunte senza modificare i valori di quelle esistenti");
            }
        }

        logger.log(isReset ? AETypeLog.reset : AETypeLog.checkData, result.getMessage());
        return result;
    }

}
