package it.algos.simple.backend.packages.fattura;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.button.*;
import it.algos.vaadflow14.ui.header.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 26-feb-2021
 * Time: 17:27
 */
public abstract class LogicList extends LogicListProperty implements BeforeEnterObserver {


    /**
     * The entityClazz obbligatorio di tipo AEntity, per liste e form <br>
     */
    protected Class<? extends AEntity> entityClazz;

    /**
     * The entityService obbligatorio, singleton di tipo xxxService <br>
     */
    protected AIService entityService;

    /**
     * The entityLogic obbligatorio, prototype di tipo xxxLogic <br>
     */
    protected AILogicOld entityLogic;

    /**
     * The entityBean obbligatorio, istanza di entityClazz per liste e form <br>
     */
    protected AEntity entityBean;


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        initView();
    }


    /**
     * Qui va tutta la logica iniziale della view <br>
     */

    protected void initView() {
        //--Costruisce gli oggetti base (placeholder) di questa view
        super.fixLayout();

        //--Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
        this.fixAlertLayout();

        //--Costruisce un layout (obbligatorio) per i menu ed i bottoni di comando della view <br>
        //--Eventualmente i bottoni potrebbero andare su due righe <br>
        this.fixTopLayout();

        //--Corpo principale della Grid (obbligatorio) <br>
        //        this.fixBodyLayout();

        //--Eventuali bottoni sotto la grid (eventuale) <br>
        //        this.fixBottomLayout();

        //--Eventuali scritte in basso al video (eventuale) <br>
        //        this.fixFooterLayout();

        //--Aggiunge i 5 oggetti base (placeholder) alla view, se sono utilizzati <br>
        super.addToLayout();
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     */
    @Override
    protected void fixAlertLayout() {
        AIHeader headerSpan = appContext.getBean(AHeaderSpan.class, getSpanList());

        if (alertPlaceHolder != null && headerSpan != null) {
            alertPlaceHolder.add(headerSpan.get());
        }
    }

    /**
     * Costruisce una lista (eventuale) di 'span' da mostrare come header della view <br>
     * DEVE essere sovrascritto <br>
     *
     * @return una liste di 'span'
     */
    protected List<Span> getSpanList() {
        return null;
    }


    /**
     * Costruisce un layout (obbligatorio) per i bottoni di comando della view <br>
     */
    @Override
    protected void fixTopLayout() {
        AButtonLayout topLayout = appContext.getBean(ATopLayout.class, getWrapButtonsTop());
//                this.addTopListeners(topLayout);//@todo implementare

        if (topPlaceHolder != null && topLayout != null) {
            topPlaceHolder.add(topLayout);
        }
    }

    /**
     * Costruisce un wrapper (obbligatorio) di dati <br>
     * I dati sono gestiti da questa 'logic' <br>
     * I dati vengono passati alla View che li usa <br>
     * DEVE essere sovrascritto <br>
     *
     * @return wrapper di dati per la view
     */
    public WrapButtons getWrapButtonsTop() {
        return null;
    }


}
