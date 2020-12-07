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

    AIResult resetPreferenze(PreferenzaLogic preferenzaLogic);

}
