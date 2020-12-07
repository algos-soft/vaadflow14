package it.algos.simple.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.simple.backend.enumeration.AESimplePreferenza;
import it.algos.simple.backend.packages.GammaLogic;
import it.algos.simple.backend.packages.LambdaLogic;
import it.algos.vaadflow14.backend.annotation.AIScript;
import it.algos.vaadflow14.backend.data.FlowData;
import it.algos.vaadflow14.backend.enumeration.AETypeLog;
import it.algos.vaadflow14.backend.interfaces.AIPreferenza;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.packages.preferenza.PreferenzaLogic;
import it.algos.vaadflow14.backend.wrapper.AResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static it.algos.simple.backend.application.SimpleCost.TAG_SIMPLE_DATA;

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

        String moduleName = "simple";
        List<String> allEntities;
        String message;

        //--spazzola tutta la directory packages
        allEntities = file.getModuleSubFilesEntity(moduleName);

        //--elabora le collections valide
        allEntities.stream()
                .filter(checkEntity)
                .forEach(resetEmptyOnly);
        message = "Controllati i dati iniziali di simple";
        logger.log(AETypeLog.checkData, message);

        //        Beta beta = Beta.builderBeta().code("valori booleani").build();
        //        beta.id = "binario";
        //        mongo.save(beta);
        //
        //        LocalDate adesso = LocalDate.now();
        //        LocalDateTime adessoDate = LocalDateTime.now();
        //        LocalTime adessoTime = LocalDateTime.now().toLocalTime();
        //        Gamma gamma = Gamma.builderGamma()
        //
        //                .code("adesso")
        //
        //                .uno(adesso)
        //
        //                .due(adesso)
        //
        //                .tre(adesso)
        //
        //                .quattro(adesso)
        //
        //                .cinque(adesso)
        //
        //                .sei(adesso)
        //
        //                .sette(adesso)
        //
        //                .otto(adesso)
        //
        //                .nove(adesso)
        //
        //                .dieci(adesso)
        //
        //                .undici(adesso)
        //
        //                .dodici(adesso)
        //
        //                .tredici(adessoDate)
        //
        //                .quattordici(adessoDate)
        //
        //                .quindici(adessoDate)
        //
        //                .sedici(adessoTime)
        //
        //                .diciassette(adessoTime)
        //
        //                .build();
        //
        //        gamma.id = gamma.code;
        //        mongo.save(gamma);
        //
        //        Lambda lambda = lambdaLogic.newEntity("alfa");
        //        if (lambda != null) {
        //            lambda.due = "betta";
        //            lambda.tre = "potta";
        //            mongo.save(lambda);
        //        }
    }


    /**
     * Ricostruisce al valore di default le preferenze standard dell'applicazione <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Il metodo reset() può essere chiamato anche dal bottone di  preferenzaLogic <br>
     *
     * @since java 8
     */
    @Override
    public void fixPreferenze() {
        super.fixPreferenze();
    }

    /**
     * Ricostruisce al valore di default le preferenze standard dell'applicazione <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Il metodo reset() può essere chiamato anche dal bottone di  preferenzaLogic <br>
     * <p>
     * Arriva qui chiamato da fixPreferenze() se esiste la classe PreferenzaLogic <br>
     */
    public AIResult resetPreferenze(PreferenzaLogic preferenzaLogic) {
        AIResult result = super.resetPreferenze(preferenzaLogic);
        int numRec = 0;

        if (result.isErrato()) {
            return result;
        }

        //-- specifiche (facoltative) dell'applicazione in uso prese da una enumeration apposita
        for (AIPreferenza aePref : AESimplePreferenza.values()) {
            numRec = preferenzaLogic.creaIfNotExist(aePref,false) != null ? numRec + 1 : numRec;
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

        logger.log(AETypeLog.checkData, result.getMessage());
        return result;
    }

}
