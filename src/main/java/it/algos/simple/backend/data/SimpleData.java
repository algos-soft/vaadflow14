package it.algos.simple.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.simple.backend.packages.Gamma;
import it.algos.simple.backend.packages.GammaLogic;
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
     * Check iniziale di alcune collections <br>
     * Crea un elenco specifico di collections che implementano il metodo 'reset' nella classe xxxLogic <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Puo essere sovrascritto, ma SENZA invocare il metodo della superclasse <br>
     */
    @Override
    public void initData() {
        LocalDate adesso=LocalDate.now();
        LocalDateTime adessoDate=LocalDateTime.now();
        LocalTime adessoTime=LocalDateTime.now().toLocalTime();

        Gamma gamma = gammaLogic.crea("Adesso");
        gamma.setUno(adesso);
        gamma.setDue(adesso);
        gamma.setTre(adesso);
        gamma.setQuattro(adesso);
        gamma.setCinque(adesso);
        gamma.setSei(adesso);
        gamma.setSette(adesso);
        gamma.setOtto(adesso);
        gamma.setNove(adesso);
        gamma.setDieci(adesso);
        gamma.setUndici(adesso);
        gamma.setDodici(adesso);
        gamma.setTredici(adessoDate);
        gamma.setQuattordici(adessoDate);
        gamma.setQuindici(adessoDate);
        gamma.setSedici(adessoTime);
        gamma.setDiciassette(adessoTime);
        gammaLogic.save(gamma);

        //        gamma.setCinque(LocalDate.now());
        //        gamma.setSei(LocalDate.now());
        //        gamma.setSette(LocalDate.now());
        //        gamma.setOtto(LocalDate.now());

//        mongo.save(gamma);
    }

}
