package it.algos.vaadflow14.backend.logic;

import it.algos.vaadflow14.backend.entity.AEntity;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 21-dic-2020
 * Time: 12:06
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Mantiene lo stato di una entityClazz <br>
 * Non mantiene lo stato di una entityBean <br>
 * Di tipo SCOPE_PROTOTYPE ne viene creato uno per ogni
 * package che non implementa la classe specifica xxxService <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GenericService extends AService {


    /**
     * Costruttore con parametro <br>
     * Regola la entityClazz associata a questo service <br>
     */
    public GenericService(Class<? extends AEntity> entityClazz) {
        super(entityClazz);
    }


}
