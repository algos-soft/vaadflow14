package it.algos.vaadflow14.ui.dialog;

import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 29-giu-2021
 * Time: 20:34
 * <p>
 * Dialogo di conferma prima della cancellazione di una entity <br>
 * Due bottoni: Annulla e Delete (Conferma) <br>
 * Riceve la entity come parametro <br>
 * Esegue la funzionalità all'interno della classe, senza ritornare al chiamante <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ADialogDelete<T> extends ADialog {

    //--Titolo standard, eventualmente modificabile nelle sottoclassi
    private static final String TITOLO = "Delete";

    private static final String MESSAGE = "Sei sicuro di voler cancellare la entity";

    private static final String ADDITIONAL_MESSAGE = "L'operazione non è reversibile";

    private T entityBean;


    /**
     * Costruttore base senza parametri <br>
     * Non usato. Serve solo per 'coprire' un piccolo bug di Idea <br>
     * Se manca, manda in rosso il parametro del costruttore usato <br>
     */
    public ADialogDelete(final T entityBean, final Runnable deleteHandler) {
        super(TITOLO);
        this.entityBean = entityBean;
        super.confirmHandler = deleteHandler;
    }// end of Vaadin/Spring constructor


    /**
     * Preferenze usate da questa 'logica' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaCancelButton = true;
        super.usaConfirmButton = true;

        super.title = TITOLO;
        super.message = String.format(MESSAGE + " %s ?", entityBean);
        super.additionalMessage = ADDITIONAL_MESSAGE;

        this.confirmTheme = AETypeTheme.error;
        this.confirmIcon = VaadinIcon.CLOSE_CIRCLE;
        this.confirmText = KEY_BUTTON_DELETE;
    }


    @Override
    public void confermaHandler() {

        //--azione locale
        if (entityBean != null) {
            mongo.delete((AEntity) entityBean);
            logger.info(AETypeLog.delete, String.format("Cancellata la entity %s%s%s", "Farttura", ":", entityBean.toString()));
        }
        else {
            logger.warn(AETypeLog.delete, "La entityBean è nulla");
        }

        //--ritorno al chiamante
        if (confirmHandler != null) {
            confirmHandler.run();
        }

        close();
    }

}
