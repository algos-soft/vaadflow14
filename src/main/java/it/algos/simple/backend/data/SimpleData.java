package it.algos.simple.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.simple.backend.packages.*;
import it.algos.vaadflow14.backend.data.FlowData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 01-ago-2020
 * Time: 06:36
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
     * Puo essere sovrascritto, ma SENZA invocare il metodo della superclasse <br>
     */
    @Override
    public void initData() {
        Beta beta = Beta.builderBeta().code("valori booleani").build();
        beta.id = "binario";
        mongo.save(beta);

        LocalDate adesso = LocalDate.now();
        LocalDateTime adessoDate = LocalDateTime.now();
        LocalTime adessoTime = LocalDateTime.now().toLocalTime();
        Gamma gamma = Gamma.builderGamma()

                .code("adesso")

                .uno(adesso)

                .due(adesso)

                .tre(adesso)

                .quattro(adesso)

                .cinque(adesso)

                .sei(adesso)

                .sette(adesso)

                .otto(adesso)

                .nove(adesso)

                .dieci(adesso)

                .undici(adesso)

                .dodici(adesso)

                .tredici(adessoDate)

                .quattordici(adessoDate)

                .quindici(adessoDate)

                .sedici(adessoTime)

                .diciassette(adessoTime)

                .build();

        gamma.id = gamma.code;
        mongo.save(gamma);

        Lambda lambda = lambdaLogic.newEntity("alfa");
        if (lambda != null) {
            lambda.due = "betta";
            lambda.tre = "potta";
            mongo.save(lambda);
        }
    }

}
