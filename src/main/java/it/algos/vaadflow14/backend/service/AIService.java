package it.algos.vaadflow14.backend.service;

import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.ui.button.ATopLayout;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import it.algos.vaadflow14.ui.header.AHeader;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 22-dic-2020
 * Time: 10:53
 * Interfaccia di collegamento tra il 'backend' e le 'views' <br>
 * Contiene le API per fornire funzionalità alle Views ed altre classi <br>
 * L'implementazione astratta è in AService <br>
 */
public interface AIService {

    /**
     * Costruisce un (eventuale) layout per avvisi aggiuntivi in alertPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Normalmente ad uso esclusivo del developer <br>
     * Nell' implementazione standard di default NON presenta nessun avviso <br>
     * Recupera dal service specifico gli (eventuali) avvisi <br>
     * Costruisce un' istanza dedicata (secondo il flag usaHeaderWrap) con le liste di avvisi <br>
     * <p>
     * AHeaderWrap:
     * Gli avvisi sono realizzati con label differenziate per colore in base all' utente collegato <br>
     * Se l' applicazione non usa security, il colore è unico (blue) <br>
     * Se AHeaderWrap esiste, inserisce l' istanza (grafica) in alertPlacehorder della view <br>
     * alertPlacehorder viene sempre aggiunto, per poter (eventualmente) essere utilizzato dalle sottoclassi <br>
     * <p>
     * AHeaderList:
     * Gli avvisi sono realizzati con elementi html con possibilità di color e bold <br>
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return componente grafico per il placeHolder
     */
    AHeader getAlertHeaderLayout(final AEVista typeVista);

    /**
     * Costruisce un layout per i bottoni di comando in topPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * 1) Recupera dal service specifico una List<AEButton> di bottoni previsti <br>
     * Se List<AEButton> è vuota, ATopLayout usa i bottoni di default (solo New) <br>
     * 2) Recupera dal service specifico la condizione e la property previste (searchType,searchProperty) <br>
     * 3) Recupera dal service specifico una List<ComboBox> di popup di selezione e filtro <br>
     * Se List<ComboBox> è vuota, ATopLayout non usa popup <br>
     * Costruisce un'istanza dedicata con i bottoni, il campo textEdit di ricerca (eventuale) ed i comboBox (eventuali) <br>
     * Inserisce l'istanza (grafica) in topPlacehorder della view <br>
     *
     * @return componente grafico per il placeHolder
     */
    ATopLayout getTopLayout();



    /**
     * Crea e registra una entityBean solo se non esisteva <br>
     * Deve esistere la keyPropertyName della collezione, in modo da poter creare una nuova entityBean
     * solo col valore di un parametro da usare anche come keyID <br>
     * Controlla che non esista già una entityBean con lo stesso keyID <br>
     * Deve esistere il metodo newEntity(keyPropertyValue) con un solo parametro <br>
     *
     * @param keyPropertyValue obbligatorio
     *
     * @return la nuova entityBean appena creata e salvata
     */
     Object creaIfNotExist(final String keyPropertyValue);

    /**
     * Crea e registra una entityBean solo se non esisteva <br>
     * Controlla che la entityBean sia valida e superi i validators associati <br>
     *
     * @param newEntityBean da registrare
     *
     * @return la nuova entityBean appena creata e salvata
     */
     AEntity checkAndSave(final AEntity newEntityBean);


    /**
     * Check the existence of a single entity <br>
     *
     * @param keyId chiave identificativa
     *
     * @return true if exist
     */
     boolean isEsiste(final String keyId);


    /**
     * Operazioni eseguite PRIMA di save o di insert <br>
     * Regolazioni automatiche di property <br>
     * Controllo della validità delle properties obbligatorie <br>
     * Controllo per la presenza della company se FlowVar.usaCompany=true <br>
     * Controlla se la entityBean registra le date di creazione e modifica <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, Edit)
     *
     * @return the modified entity
     */
     AEntity beforeSave(final AEntity entityBean, final AEOperation operation);

    /**
     * Regola la chiave se esiste il campo keyPropertyName <br>
     * Se la company è nulla, la recupera dal login <br>
     * Se la company è ancora nulla, la entity viene creata comunque
     * ma verrà controllata ancora nel metodo beforeSave() <br>
     *
     * @param newEntityBean to be checked
     *
     * @return the checked entityBean
     */
     AEntity fixKey(final AEntity newEntityBean);


    /**
     * Retrieves an entityBean by its id <br>
     *
     * @param keyID must not be {@literal null}.
     *
     * @return the entityBean with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
     AEntity findById(final String keyID);


    /**
     * Retrieves an entityBean by its keyProperty <br>
     *
     * @param keyPropertyValue must not be {@literal null}.
     *
     * @return the entityBean with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
     AEntity findByKey(final String keyPropertyValue);




    /**
     * Creazione o ricreazione di alcuni dati iniziali standard <br>
     * Invocato in fase di 'startup' e dal bottone Reset di alcune liste <br>
     * <p>
     * 1) deve esistere lo specifico metodo sovrascritto <br>
     * 2) deve essere valida la entityClazz <br>
     * 3) deve esistere la collezione su mongoDB <br>
     * 4) la collezione non deve essere vuota <br>
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
    AIResult resetEmptyOnly();

}

