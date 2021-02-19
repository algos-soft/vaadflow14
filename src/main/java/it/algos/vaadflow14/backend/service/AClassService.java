package it.algos.vaadflow14.backend.service;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.packages.preferenza.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;


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
     * Istanza della sottoclasse xxxService (singleton) associata alla entity <br>
     *
     * @param entityClazz di riferimento
     *
     * @return istanza di xxxService associata alla Entity
     */
    public AIService getServiceFromEntityClazz(Class<? extends AEntity> entityClazz) {
        return getServiceFromEntityName(entityClazz.getCanonicalName());
    }

    /**
     * Istanza della sottoclasse xxxService (singleton) associata alla entity <br>
     *
     * @param entityClazzCanonicalName the canonical name of entity class
     *
     * @return istanza di xxxService associata alla Entity
     */
    public AIService getServiceFromEntityName(String entityClazzCanonicalName) {
        AIService entityService = null;
        String serviceClazzCanonicalName;
        AEntity entityBean;

        if (text.isValid(entityClazzCanonicalName)) {
            serviceClazzCanonicalName = entityClazzCanonicalName + SUFFIX_ENTITY_SERVICE;
            try {
                entityService = (AIService) appContext.getBean(Class.forName(serviceClazzCanonicalName));
            } catch (Exception unErrore) {
                try {
                    entityBean = (AEntity) appContext.getBean(Class.forName(entityClazzCanonicalName));
                    entityService = appContext.getBean(EntityService.class, entityBean.getClass());
                } catch (Exception unErrore2) {
                    logger.error(unErrore2.getMessage(), this.getClass(), "getServiceFromEntityName");
                }
            }
        }
        return entityService;
    }


    /**
     * Istanza della sottoclasse xxxLogic associata al nome delle Entity inviata  <br>
     *
     * @param entityClazzCanonicalName the canonical name of entity class
     *
     * @return istanza di xxxLogic associata alla Entity
     */
    public AILogic getLogicFromEntityName(String entityClazzCanonicalName) {
        AILogic entityLogic = null;
        String logicClazzCanonicalName;
        AEntity entityBean;

        if (text.isValid(entityClazzCanonicalName)) {
            logicClazzCanonicalName = entityClazzCanonicalName + SUFFIX_ENTITY_LOGIC;
            try {
                entityLogic = (AILogic) appContext.getBean(Class.forName(logicClazzCanonicalName));
            } catch (Exception unErrore) {
                try {
                    entityBean = (AEntity) appContext.getBean(Class.forName(entityClazzCanonicalName));
                    entityLogic = appContext.getBean(EntityLogic.class, entityBean.getClass());
                } catch (Exception unErrore2) {
                    logger.error(unErrore2.getMessage(), this.getClass(), "getLogicFromEntityName");
                }
            }
        }
        return entityLogic;
    }

    /**
     * Istanza della sottoclasse xxxLogic associata alla Entity inviata  <br>
     *
     * @param entityClazz the entity class
     *
     * @return istanza de xxxLogic associata alla Entity
     */
    public AILogic getLogicFromEntityClazz(Class<? extends AEntity> entityClazz) {
        return getLogicFromEntityClazz(entityClazz, null,AEOperation.listNoForm);
    }
    /**
     * Istanza della sottoclasse xxxLogic associata alla Entity inviata  <br>
     *
     * @param entityClazz the entity class
     *
     * @return istanza de xxxLogic associata alla Entity
     */
    public AILogic getLogicFromEntityClazz(Class<? extends AEntity> entityClazz,AIService entityService) {
        return getLogicFromEntityClazz(entityClazz, entityService,AEOperation.listNoForm);
    }


    /**
     * Istanza della sottoclasse xxxLogic associata alla Entity inviata  <br>
     *
     * @param entityClazz   the entity class
     * @param operationForm supported by dialog
     *
     * @return istanza de xxxLogic associata alla Entity
     */
    public AILogic getLogicFromEntityClazz(Class<? extends AEntity> entityClazz, AIService entityService,AEOperation operationForm) {
        AILogic entityLogic = null;
        String canonicalName;

        if (entityClazz != null) {
            canonicalName = entityClazz.getCanonicalName() + SUFFIX_ENTITY_LOGIC;
            try {
                entityLogic = (AILogic) appContext.getBean(Class.forName(canonicalName), entityService,operationForm);
            } catch (Exception unErrore) {
                try {
                    entityLogic = (AILogic) appContext.getBean(Class.forName(canonicalName),entityService);
                } catch (Exception unErrore2) {
                    try {
                        entityLogic = (AILogic) appContext.getBean(EntityLogic.class, entityClazz, operationForm);
                    } catch (Exception unErrore3) {
                        logger.error("Non sono riuscito a creare la entityLogic", this.getClass(), "getLogicFromEntity");
                    }
                }
            }
        }
        else {
            logger.error("Manca la entityClazz", this.getClass(), "getLogicFromEntity");
            return null;
        }
        return entityLogic;
    }

    /**
     * Istanza di PreferenzaLogic associata alla Entity inviata  <br>
     *
     * @return istanza di PreferenzaLogic associata alla Entity
     */
    public PreferenzaService getPreferenzaLogic() {
        return (PreferenzaService) getServiceFromEntityClazz(Preferenza.class);
    }

}
