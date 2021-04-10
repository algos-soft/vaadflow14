package it.algos.vaadflow14.wizard.scripts;

import it.algos.vaadflow14.wizard.enumeration.*;

/**
 * Project wikibio
 * Created by Algos
 * User: gac
 * Date: gio, 11-mar-2021
 * Time: 21:19
 */
public abstract class WizDialogPackage extends WizDialog {

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola i valori regolabili della Enumeration AEWizCost <br>
     * Ci sono diversi VALORE_MANCANTE di cui 7 regolati all'ingresso del dialogo <br>
     */
    protected boolean regolaPackages(final String packName) {
        wizService.regolaPackages(packName);

        wizService.printVuote();
//        AEWizCost.printInfoBase(AEWizCost.getPackages(), "Variabili del package. Dipende dal package selezionato");
        return true;
    }

}
