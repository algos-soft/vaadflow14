package it.algos.vaadflow14.backend.data;

import it.algos.vaadflow14.backend.interfaces.AIResult;
import it.algos.vaadflow14.backend.packages.preferenza.PreferenzaLogic;

/**
 * Project vbase
 * Created by Algos
 * User: gac
 * Date: lun, 19-mar-2018
 * Time: 21:10
 */
public interface AIData {

    void fixData();

    void fixPreferenze();

    /**
     * Ricostruisce le preferenze standard dell'applicazione <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * <p>
     *
     * @param isReset true: invocato da xxxLogic.resetEmptyOnly(), con click sul bottone Reset di PreferenzaList
     *                false: invocato da xxxData.fixPreferenze(), in fase di Startup <br>
     *                <br>
     */
    AIResult resetPreferenze(PreferenzaLogic preferenzaLogic, boolean isReset);

}
