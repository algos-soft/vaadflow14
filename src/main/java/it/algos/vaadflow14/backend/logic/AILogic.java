package it.algos.vaadflow14.backend.logic;

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
     * Esegue l'azione di un bottone. <br>
     * <p>
     * Passa a AEntityService.performAction(azione) <br>
     *
     * @param azione selezionata da eseguire
     */
     void performAction(AEAction azione) ;

    }
