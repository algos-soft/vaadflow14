package it.algos.vaadflow14.backend.logic;


import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.ui.button.ABottomLayout;
import it.algos.vaadflow14.ui.button.AEAction;
import it.algos.vaadflow14.ui.button.ATopLayout;
import it.algos.vaadflow14.ui.enumerastion.AEVista;
import it.algos.vaadflow14.ui.form.AForm;
import it.algos.vaadflow14.ui.header.AHeader;
import it.algos.vaadflow14.ui.list.AGrid;

import java.util.List;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 08-mag-2020
 * Time: 18:45
 * Interfaccia di collegamento tra backend e UI <br>
 * Contiene le API per fornire funzionalità alle Views ed altre classi <br>
 * L'implementazione astratta è ALogic <br>
 */
public interface AILogic {


    /**
     * Costruisce un (eventuale) layout per avvisi aggiuntivi in alertPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Normalmente ad uso esclusivo del developer <br>
     * Nell'implementazione standard di default NON presenta nessun avviso <br>
     * Recupera dal service specifico gli (eventuali) avvisi <br>
     * Costruisce un'istanza dedicata con le liste di avvisi <br>
     * Gli avvisi sono realizzati con label differenziate per colore in base all'utente collegato <br>
     * Se l'applicazione non usa security, il colore è unico <br<
     * Se esiste, inserisce l'istanza (grafica) in alertPlacehorder della view <br>
     * alertPlacehorder viene sempre aggiunto, per poter (eventualmente) essere utilizzato dalle sottoclassi <br>
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return componente grafico per il placeHolder
     */
    AHeader getAlertHeaderLayout(AEVista typeVista);


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
     * Costruisce un layout per la Grid in bodyPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Costruisce un'istanza dedicata <br>
     * Inserisce l'istanza (grafica) in bodyPlacehorder della view <br>
     *
     * @return componente grafico per il placeHolder
     */
    AGrid getBodyGridLayout();


    /**
     * Costruisce un layout per il Form in bodyPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Costruisce un'istanza dedicata <br>
     * Inserisce l'istanza (grafica) in bodyPlacehorder della view <br>
     *
     * @param entityClazz the class of type AEntity
     *
     * @return componente grafico per il placeHolder
     */
    AForm getBodyFormLayout(Class<? extends AEntity> entityClazz);


    /**
     * Costruisce un layout per il Form in bodyPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Costruisce un'istanza dedicata <br>
     * Inserisce l'istanza (grafica) in bodyPlacehorder della view <br>
     *
     * @param entityBean interessata
     *
     * @return componente grafico per il placeHolder
     */
    AForm getBodyFormLayout(AEntity entityBean);


    /**
     * Costruisce un layout per i bottoni di comando in bottomPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Recupera dal service specifico una List<AEButton> di bottoni previsti <br>
     * Se List<AEButton> è vuota, ATopLayout usa i bottoni di default (solo New) <br>
     * Costruisce un'istanza dedicata con i bottoni <br>
     * Inserisce l'istanza (grafica) in bottomPlacehorder della view <br>
     *
     * @return componente grafico per il placeHolder
     */
    ABottomLayout getBottomLayout(AEOperation operationForm);


    /**
     * Costruisce una lista di nomi delle properties del Form nell'ordine:
     * 1) Cerca nell'annotation @AIForm della Entity e usa quella lista (con o senza ID)
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse)
     * 3) Sovrascrive la lista nella sottoclasse specifica di xxxService
     *
     * @return lista di nomi delle properties da usare nel form
     */
    List<String> getListaPropertiesForm();


    /**
     * Costruisce una lista di nomi delle properties del Form, specializzata per una specifica operazione <br>
     * Di default utilizza la lista generale di getListaPropertiesForm() <br>
     * Sovrascritto nella sottoclasse concreta <br>
     *
     * @return lista di nomi delle properties da usare nel form
     */
    List<String> getListaPropertiesFormNew();


    /**
     * Costruisce una lista di nomi delle properties del Form, specializzata per una specifica operazione <br>
     * Di default utilizza la lista generale di getListaPropertiesForm() <br>
     * Sovrascritto nella sottoclasse concreta <br>
     *
     * @return lista di nomi delle properties da usare nel form
     */
    List<String> getListaPropertiesFormEdit();


    /**
     * Costruisce una lista di nomi delle properties del Form, specializzata per una specifica operazione <br>
     * Di default utilizza la lista generale di getListaPropertiesForm() <br>
     * Sovrascritto nella sottoclasse concreta <br>
     *
     * @return lista di nomi delle properties da usare nel form
     */
    List<String> getListaPropertiesFormDelete();


    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     *
     * @param azione selezionata da eseguire
     */
    public void performAction(AEAction azione);


    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     * Invocata solo dalla Grid <br>
     *
     * @param azione           selezionata da eseguire
     * @param searchFieldValue valore corrente del campo editText (solo per List)
     */
    public void performAction(AEAction azione, String searchFieldValue);


    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     * Invocata solo dal Form <br>
     *
     * @param azione     selezionata da eseguire
     * @param entityBean selezionata (solo per Form)
     */
    public void performAction(AEAction azione, AEntity entityBean);


    /**
     * Costruisce una lista di nomi delle properties della Grid nell'ordine: <br>
     * 1) Cerca nell'annotation @AIList della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica <br>
     *
     *
     * @return lista di nomi di properties
     */
    List<String> getGridPropertyNamesList();


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Senza properties per compatibilità con la superclasse <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public AEntity newEntity();


    /**
     * Retrieves an entity by its id.
     *
     * @param keyID must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    public AEntity findById(String keyID);


    //    /**
    //     * Saves a given entity.
    //     * Use the returned instance for further operations as the save operation
    //     * might have changed the entity instance completely.
    //     *
    //     * @param entityBean to be saved
    //     *
    //     * @return the saved entity
    //     */
    //    public AEntity save(AEntity entityBean);

    /**
     * Deletes all entities of the collection.
     */
    public boolean deleteAll();


    /**
     * Creazione di alcuni dati iniziali <br>
     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo in alcuni casi) <br>
     * I dati possono essere presi da una Enumeration o creati direttamente <br>
     * Deve essere sovrascritto - Invocare PRIMA il metodo della superclasse che cancella tutta la Collection <br>
     *
     * @return true se la collection è stata ri-creata
     */
    public boolean reset();

}