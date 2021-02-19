package it.algos.vaadflow14.backend.packages.preferenza;

import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.interfaces.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

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
 * 1) StaticContextAccessor.getBean(APreferenzaService.class); <br>
 * 3) @Autowired public APreferenzaService annotation; <br>
 * <p>
 * La ricerca di una preferenza può essere fatta:
 * 1) tramite la enumeration di AEPreferenza
 * 2) tramite la costante di FlowCost
 * 3) tramite il valore di 'code'
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@AIScript(sovraScrivibile = false)
public class PreferenzaService extends AService {

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore senza parametri <br>
     * Regola la entityClazz (final) associata a questo service <br>
     */
    public PreferenzaService() {
        super(Preferenza.class);
    }

//    /**
//     * Retrieves an entity by its id.
//     *
//     * @param keyID must not be {@literal null}.
//     *
//     * @return the entity with the given id or {@literal null} if none found
//     *
//     * @throws IllegalArgumentException if {@code id} is {@literal null}
//     */
//    public Preferenza findByKey(String keyCode) {
//        return (Preferenza) mongo.findOneUnique(Preferenza.class, "code", keyCode);
//    }
//
//
//    /**
//     * Retrieves an entity by its id.
//     *
//     * @param keyID must not be {@literal null}.
//     *
//     * @return the entity with the given id or {@literal null} if none found
//     *
//     * @throws IllegalArgumentException if {@code id} is {@literal null}
//     */
//    public Preferenza findById(String keyID) {
//        return (Preferenza) mongo.findById(Preferenza.class, keyID);
//    }




    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param aePref: enumeration per la creazione-reset di tutte le entities
     *
     * @return la nuova entity appena creata e salvata
     */
    public Preferenza creaIfNotExist(AIPreferenza aePref) {
        return creaIfNotExist(aePref.getKeyCode(), aePref.getDescrizione(), aePref.getType(), aePref.getDefaultValue(), aePref.isVaadFlow(), aePref.isUsaCompany(), aePref.isNeedRiavvio(),aePref.isVisibileAdmin(),aePref.getNote());
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param code         codice di riferimento (obbligatorio)
     * @param descrizione  (obbligatoria)
     * @param type         (obbligatorio) per convertire in byte[] i valori
     * @param defaultValue (obbligatorio) memorizza tutto in byte[]
     * @param vaadFlow      (obbligatorio) preferenza di vaadflow, di default true
     * @param usaCompany    (obbligatorio) se FlowVar.usaCompany=false, sempre false
     * @param needRiavvio   (obbligatorio) occorre riavviare per renderla efficace, di default false
     * @param visibileAdmin (obbligatorio) visibile agli admin, di default false se FlowVar.usaCompany=true
     *
     * @return la nuova entity appena creata e salvata
     */
    public Preferenza creaIfNotExist(String code, String descrizione, AETypePref type, Object defaultValue, boolean vaadFlow, boolean usaCompany, boolean needRiavvio, boolean visibileAdmin, String note) {
        return (Preferenza) checkAndSave(newEntity(code, descrizione, type, defaultValue, vaadFlow,usaCompany, needRiavvio, visibileAdmin, note));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * <p>
     * @param code          codice di riferimento (obbligatorio)
     * @param descrizione   (obbligatoria)
     * @param type          (obbligatorio) per convertire in byte[] i valori
     * @param defaultValue  (obbligatorio) memorizza tutto in byte[]
     * @param vaadFlow      (obbligatorio) preferenza di vaadflow, di default true
     * @param usaCompany    (obbligatorio) se FlowVar.usaCompany=false, sempre false
     * @param needRiavvio   (obbligatorio) occorre riavviare per renderla efficace, di default false
     * @param visibileAdmin (obbligatorio) visibile agli admin, di default false se FlowVar.usaCompany=true
     * @param note          (facoltativo)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Preferenza newEntity(String code, String descrizione, AETypePref type, Object defaultValue, boolean vaadFlow, boolean usaCompany, boolean needRiavvio, boolean visibileAdmin, String note) {
        Preferenza newEntityBean = Preferenza.builderPreferenza()
                .code(text.isValid(code) ? code : null)
                .descrizione(text.isValid(descrizione) ? descrizione : null)
                .type(type != null ? type : AETypePref.string)
                .value(type != null ? type.objectToBytes(defaultValue) : (byte[]) null)
                .vaadFlow(  vaadFlow)
                .usaCompany(FlowVar.usaCompany ? usaCompany : false)
                .needRiavvio(needRiavvio)
                .visibileAdmin(FlowVar.usaCompany ? visibileAdmin : true)
                .build();

        if (text.isValid(note)) {
            newEntityBean.note = note;
        }

        return (Preferenza) fixKey(newEntityBean);
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
    @Override
    public Preferenza findById(final String keyID) {
        //        return (Preferenza) mongo.findById(Preferenza.class, keyID);
        return (Preferenza) super.findById(keyID);
    }


    /**
     * Retrieves an entity by its keyProperty.
     *
     * @param keyValue must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    public Preferenza findByKey(final String keyValue) {
        //        return (Preferenza) mongo.findOneUnique(Preferenza.class, "code", keyValue);
        return (Preferenza) super.findByKey(keyValue);
    }

    /**
     * Creazione o ricreazione di alcuni dati iniziali standard <br>
     * Invocato in fase di 'startup' e dal bottone Reset di alcune liste <br>
     * <p>
     * 1) deve esistere lo specifico metodo sovrascritto
     * 2) deve essere valida la entityClazz
     * 3) deve esistere la collezione su mongoDB
     * 4) la collezione non deve essere vuota
     * <p>
     * I dati possono essere: <br>
     * 1) recuperati da una Enumeration interna <br>
     * 2) letti da un file CSV esterno <br>
     * 3) letti da Wikipedia <br>
     * 4) creati direttamente <br>
     * DEVE essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @return wrapper col risultato ed eventuale messaggio di errore
     */
    @Override
    public AIResult resetEmptyOnly() {
        AIResult result = super.resetEmptyOnly();
        int numRec = 0;

        if (result.isErrato()) {
            return result;
        }

        //--da sostituire
        String message;
        message = String.format("Nel package %s la classe %s non ha ancora sviluppato il metodo resetEmptyOnly() ", "@PACKAGE@", "@ENTITY@Service");
        return AResult.errato(message);

        // return super.fixPostReset(AETypeReset.enumeration, numRec);
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
        }
        else {
            logger.error("Algos - Preferenze. La preferenza: " + keyCode + " è del tipo sbagliato");
        }

        return status;
    }

}