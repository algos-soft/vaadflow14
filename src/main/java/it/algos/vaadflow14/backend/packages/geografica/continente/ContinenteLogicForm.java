package it.algos.vaadflow14.backend.packages.geografica.continente;

import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.enumeration.AEContinente;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.service.*;
import it.algos.vaadflow14.ui.*;
import it.algos.vaadflow14.ui.fields.AField;
import it.algos.vaadflow14.ui.fields.AGridField;
import it.algos.vaadflow14.ui.form.AForm;
import it.algos.vaadflow14.ui.form.WrapForm;
import it.algos.vaadflow14.wizard.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * First time: sab, 10-ott-2020
 * Last doc revision: 13:25
 * <p>
 */
@Route(value = "continenteForm", layout = MainLayout.class)
//Algos
@AIScript(sovraScrivibile = false, doc = AEWizDoc.inizioRevisione)
public class ContinenteLogicForm extends LogicForm {


    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Costruttore senza parametri <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     */
    public ContinenteLogicForm(@Qualifier("continenteService") AIService entityService) {
        super.entityClazz = Continente.class;
        super.entityService = entityService;
    }// end of Vaadin/@Route constructor

    /**
     * Costruisce una lista (eventuale) di 'span' da mostrare come header della view <br>
     * DEVE essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixSpanForm() {
        super.fixSpanForm();

        addSpanBlu("Nella sub-lista tutti gli stati di questo contenente");
    }

    /**
     * Regolazioni finali di alcuni oggetti <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void regolazioniFinali() {
        super.regolazioniFinali();
        String keyStati = "stati";
        AField field;
        AGridField gridField;

        if (entityBean.id.equals(AEContinente.europa.name())) {
            field = currentForm.fieldsMap.get(keyStati);

            if (field != null && field instanceof AGridField) {
                gridField = (AGridField) field;
                gridField.addColumnsGrid("stato,ue,alfatre");
            }
        }
    }


}