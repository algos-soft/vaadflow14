package it.algos.vaadflow14.ui.form;

import com.vaadin.flow.router.Route;
import it.algos.vaadflow14.ui.MainLayout;
import it.algos.vaadflow14.ui.button.ABottomLayout;
import it.algos.vaadflow14.ui.view.AView;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 13-mag-2020
 * Time: 18:45
 */
@Route(value = ROUTE_NAME_GENERIC_FORM, layout = MainLayout.class)
public class AViewForm extends AView {


    //    /**
    //     * Creazione iniziale (business logic) della view DOPO costruttore, init(), postConstruct() e setParameter() <br>
    //     * <p>
    //     * Chiamato da com.vaadin.flow.router.Router tramite l'interfaccia BeforeEnterObserver <br>
    //     * Chiamato DOPO @PostConstruct e DOPO setParameter() <br>
    //     *
    //     * @param beforeEnterEvent con la location, ui, navigationTarget, source, ecc
    //     */
    //    @Override
    //    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    //
    //        this.add(new Label("Pippoz"));
    //        if (entityBean!=null) {
    //            this.add(new Label("E la regione è: "+((Regione)entityBean).getNome()));
    //        }
    //    }


    /**
     *
     */
    protected void fixEntityBean() {
        String keyID;

        keyID = routeParameter.get(KEY_BEAN_ENTITY) != null ? routeParameter.get(KEY_BEAN_ENTITY) : VUOTA;
        if (text.isEmpty(keyID) || keyID.equals(KEY_NULL)) {
            entityBean = entityLogic.newEntity();
        } else {
            entityBean = entityLogic.findById(keyID);
        }
    }


    /**
     * Costruisce un layout per i bottoni di comando in topPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Tipicamente usato SOLO nella List <br>
     * Nell'implementazione standard di default presenta solo il bottone 'New' <br>
     * Recupera dal service specifico i menu/bottoni previsti <br>
     * Costruisce un'istanza dedicata con i bottoni <br>
     * Inserisce l'istanza (grafica) in topPlacehorder della view <br>
     */
    @Override
    protected void fixTopLayout() {
    }


    /**
     * Costruisce il 'corpo' centrale della view <br>
     * <p>
     * Differenziato tra AViewList e AViewForm <br>
     * Costruisce un'istanza dedicata <br>
     * Inserisce l'istanza (grafica) in bodyPlacehorder della view <br>
     */
    @Override
    protected void fixBody() {
        AForm form;

        if (entityClazz == null) {
            logger.error("Manca entityClazz", this.getClass(), "fixBody");
        }

        if (entityLogic == null) {
            logger.error("Manca entityLogic", this.getClass(), "fixBody");
        }

        if (entityBean != null) {
            form = entityLogic.getBodyFormLayout(entityBean);
        } else {
            logger.warn("Manca entityBean", this.getClass(), "fixBody");
            form = entityLogic.getBodyFormLayout(entityLogic.newEntity());
        }

        if (bodyPlacehorder != null && form != null) {
            bodyPlacehorder.add(form);
        }

        this.add(bodyPlacehorder);
    }


    /**
     * Costruisce un layout per i bottoni di comando in footerPlacehorder della view <br>
     * <p>
     * Chiamato da AView.initView() <br>
     * Tipicamente usato SOLO nel Form <br>
     * Nell'implementazione standard di default presenta solo il bottone 'New' <br>
     * Recupera dal service specifico i menu/bottoni previsti <br>
     * Costruisce un'istanza dedicata con i bottoni <br>
     * Inserisce l'istanza (grafica) in bottomPlacehorder della view <br>
     */
    @Override
    protected void fixBottomLayout() {
        ABottomLayout bottomLayout = null;

        if (entityLogic != null) {
            bottomLayout = entityLogic.getBottomLayout(operationForm);
        }

        if (bottomPlacehorder != null && bottomLayout != null) {
            bottomPlacehorder.add(bottomLayout);
        }

        this.add(bottomPlacehorder);
    }

}
