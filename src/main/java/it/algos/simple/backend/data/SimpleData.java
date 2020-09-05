package it.algos.simple.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.simple.backend.packages.Gamma;
import it.algos.vaadflow14.backend.data.FlowData;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
     * Check iniziale di alcune collections <br>
     * Crea un elenco specifico di collections che implementano il metodo 'reset' nella classe xxxLogic <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Puo essere sovrascritto, ma SENZA invocare il metodo della superclasse <br>
     */
    @Override
    public void initData() {
        Gamma gamma = new Gamma();
        gamma.setCode("Adesso");
        gamma.setUno(LocalDateTime.now());
        gamma.setDue(LocalDate.now());
        gamma.setTre(LocalDateTime.now().toLocalTime());
        mongo.save(gamma);
    }

}
