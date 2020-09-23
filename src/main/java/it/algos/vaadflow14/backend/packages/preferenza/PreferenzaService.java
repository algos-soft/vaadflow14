package it.algos.vaadflow14.backend.packages.preferenza;

import it.algos.vaadflow14.backend.service.AAbstractService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 23-set-2020
 * Time: 10:44
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(PreferenzaService.class); <br>
 * 3) @Autowired public PreferenzaService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PreferenzaService extends AAbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Retrieves an entity by its id.
     *
     * @param keyID must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    public Preferenza findByKey(String keyCode) {
        return (Preferenza) mongo.findOneUnique(Preferenza.class, "code", keyCode);
    }


    /**
     * Retrieves an entity by its id.
     *
     * @param keyID must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    public Preferenza findById(String keyID) {
        return (Preferenza) mongo.findById(Preferenza.class, keyID);
    }


    public Object getValue(String keyCode) {
        Object value = null;
        Preferenza pref = findByKey(keyCode);

        if (pref != null) {
            value = pref.getType().bytesToObject(pref.value);
        }

        return value;
    }


    public Boolean isBool(String keyCode) {
        boolean status = false;
        Object objValue = getValue(keyCode);

        if (objValue != null && objValue instanceof Boolean) {
            status = (boolean) objValue;
        } else {
            logger.error("Algos - Preferenze. La preferenza: " + keyCode + " è del tipo sbagliato");
        }

        return status;
    }

}