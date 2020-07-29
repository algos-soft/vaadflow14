package it.algos.vaadflow14.backend.service;

import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.entity.AILogic;
import it.algos.vaadflow14.backend.entity.GenericLogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static it.algos.vaadflow14.backend.application.FlowCost.SUFFIX_BUSINESS_LOGIC;


/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: sab, 30-mag-2020
 * Time: 22:32
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AClassService extends AAbstractService {


    /**
     * Istanza della sottoclasse (singleton) Service associata al nome delle Entity inviato  <br>
     *
     * @param entityClazz the entity class
     *
     * @return istanza del xxxService associato alla Entity
     */
    public AILogic getLogicFromEntity(Class<? extends AEntity> entityClazz) {
        return getLogicFromEntity(entityClazz, AEOperation.edit);
    }



    /**
     * Istanza della sottoclasse (singleton) Service associata al nome delle Entity inviato  <br>
     *
     * @param entityClazz   the entity class
     * @param operationForm supported by dialog
     *
     * @return istanza di xxxLogic associato alla Entity
     */
    public AILogic getLogicFromEntity(Class<? extends AEntity> entityClazz, AEOperation operationForm) {
        AILogic entityLogic = null;
        String canonicalName;

        if (entityClazz != null) {
            canonicalName = entityClazz.getCanonicalName() + SUFFIX_BUSINESS_LOGIC;
            try {
                entityLogic = (AILogic) appContext.getBean(Class.forName(canonicalName), operationForm);
            } catch (Exception unErrore) {
                try {
                    entityLogic = (AILogic) appContext.getBean(Class.forName(canonicalName));
                } catch (Exception unErrore2) {
                    try {
                        entityLogic = (AILogic) appContext.getBean(GenericLogic.class, entityClazz, operationForm);
                    } catch (Exception unErrore3) {
                        logger.error("Non sono riuscito a creare la entityLogic", this.getClass(), "getServiceFromEntity");
                    }
                }
            }
        } else {
            logger.error("Manca la entityClazz", this.getClass(), "getServiceFromEntity");
            return null;
        }
        return entityLogic;
    }


}
