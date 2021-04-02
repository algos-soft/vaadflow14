package it.algos.vaadflow14.backend.logic;

import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.ui.button.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 27-feb-2021
 * Time: 21:25
 */
public interface AILogic {

    /**
     * Costruisce una lista ordinata di colonne della Grid. <br>
     * Nell' ordine: <br>
     * 1) Cerca nell' annotation @AIList della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica xxxLogicList <br>
     * Può essere sovrascritto senza invocare il metodo della superclasse <br>
     *
     * @return lista dei nomi delle colonne
     */
    List<String> getGridColumns();


    /**
     * Costruisce una lista ordinata di nomi delle properties del Form. <br>
     * La lista viene usata per la costruzione automatica dei campi e l' inserimento nel binder <br>
     * Nell' ordine: <br>
     * 1) Cerca nell' annotation @AIForm della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica di xxxLogic <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Se serve, modifica l' ordine della lista oppure esclude una property che non deve andare nel binder <br>
     * todo ancora da sviluppare
     *
     * @return lista di nomi di properties
     */
    List<String> getFormPropertyNamesList();

    /**
     * Esegue l'azione del bottone, searchText o comboBox. <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param azione selezionata da eseguire
     */
    void performAction(AEAction azione);


    /**
     * Esegue l'azione del bottone, textEdit o comboBox. <br>
     *
     * @param azione     selezionata da eseguire
     * @param entityBean selezionata
     */
    void performAction(AEAction azione, AEntity entityBean);

}
