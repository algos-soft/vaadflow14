package it.algos.simple.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.simple.backend.packages.GammaLogic;
import it.algos.simple.backend.packages.LambdaLogic;
import it.algos.vaadflow14.backend.data.FlowData;
import it.algos.vaadflow14.backend.enumeration.AETypeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 01-ago-2020
 * Time: 06:36
 * <p>
 * Check iniziale di alcune collections <br>
 * Crea un elenco specifico di collections che implementano il metodo 'reset' nella classe xxxLogic <br>
 * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
 * L' ordine con cui vengono create le collections è significativo <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
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
     * Crea un elenco specifico di collections che implementano il metodo 'reset' nella classe xxxLogic <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixData() {
        super.fixData();

        String moduleName = "simple";
        List<String> listaCanonicalNameEntity = file.getModuleSubFilesEntity(moduleName);

        if (array.isAllValid(listaCanonicalNameEntity)) {
            for (String canonicalName : listaCanonicalNameEntity) {
                checkSingolaCollection(canonicalName);
            }
        }

        logger.log(AETypeLog.checkData, "Controllati i dati iniziali di simple");

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
     * Crea le preferenze standard dell'applicazione <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Per un reset ai valori di default, c'è il metodo reset() chiamato da preferenzaLogic <br>
     */
    public void fixPreferenze() {
        int stop=87;
    }

}
