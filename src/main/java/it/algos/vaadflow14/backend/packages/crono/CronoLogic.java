package it.algos.vaadflow14.backend.packages.crono;

import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.packages.preferenza.AEPreferenza;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 15-ago-2020
 * Time: 11:36
 */
public abstract class CronoLogic extends ALogic {

    /**
     * Costruttore con parametro <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AFormView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName), operationForm) <br>
     *
     * @param operationForm tipologia di Form in uso
     */
    public CronoLogic(AEOperation operationForm) {
        super(operationForm);
    }


    /**
     * Preferenze standard <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        //--Quattro packages cronologici (secolo, anno, mese, giorno)

        //--Bottoni DeleteAll e Reset presenti solo se FlowVar.usaDebug=true (debug si abilita solo per il developer)
        //--Bottone New presente solo login.isDeveloper()=true
        //--Entity modificabile e cancellabile solo login.isDeveloper()=true, altrimenti AEOperation.showOnly

        super.usaHeaderWrap = true;
        if (FlowVar.usaSecurity) {
            if (vaadinService.isDeveloper()) {
                super.usaBottoneDeleteAll = true;
                super.usaBottoneReset = true;
                super.usaBottoneNew = true;
            } else {
                super.usaBottoneNew = false;
                super.operationForm = AEOperation.showOnly;
            }
            super.usaBottoneExport = vaadinService.isAdminOrDeveloper();
        } else {
            if (AEPreferenza.usaDebug.is()) {
                super.usaBottoneDeleteAll = true;
                super.usaBottoneReset = true;
                super.usaBottoneNew = true;
            } else {
                super.usaBottoneNew = false;
                super.operationForm = AEOperation.showOnly;
            }
            super.usaBottoneExport = true;
        }
    }

}
