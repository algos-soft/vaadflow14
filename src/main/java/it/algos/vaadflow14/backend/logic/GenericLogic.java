package it.algos.vaadflow14.backend.logic;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import it.algos.vaadflow14.ui.header.AlertWrap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mar, 05-mag-2020
 * Time: 07:45
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GenericLogic extends ALogic {

    public GenericLogic() {
    }


    public GenericLogic(Class<? extends AEntity> entityClazz) {
        this(entityClazz, AEOperation.edit);
    }

    public GenericLogic(Class<? extends AEntity> entityClazz, AEOperation operationForm) {
        super.entityClazz = entityClazz;
        super.operationForm = operationForm;
    }


    /**
     * Costruisce un wrapper di liste di informazioni per costruire l'istanza di AHeader <br>
     * Informazioni (eventuali) specifiche di ogni modulo <br>
     * Sovrascritto nella sottoclasse <br>
     * Esempio:     return new AlertWrap(new ArrayList(Arrays.asList("uno", "due", "tre")));
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return wrapper per passaggio dati
     */
    @Override
    protected AlertWrap getAlertWrap(AEVista typeVista) {
        List<String> green = new ArrayList<>();
        List<String> blu = new ArrayList<>();
        List<String> red = new ArrayList<>();

        return new AlertWrap(green, blu, red, false);
    }

    /**
     * Costruisce una lista di nomi delle properties della Grid nell'ordine: <br>
     * 1) Cerca nell'annotation @AIList della Entity e usa quella lista (con o senza ID) <br>
     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse) <br>
     * 3) Sovrascrive la lista nella sottoclasse specifica <br>
     * todo ancora da sviluppare
     *
     * @param entityClazz the class of type AEntity
     *
     * @return lista di nomi di properties
     */
    @Override
    public List<String> getGridPropertyNamesList() {
        return annotation.getListaPropertiesGrid(entityClazz);
    }

    //    /**
    //     * Costruisce una lista di nomi delle properties della Grid nell'ordine:
    //     * 1) Cerca nell'annotation @AIList della Entity e usa quella lista (con o senza ID)
    //     * 2) Utilizza tutte le properties della Entity (properties della classe e superclasse)
    //     * 3) Sovrascrive la lista nella sottoclasse specifica
    //     * todo ancora da sviluppare
    //     *
    //     * @return lista di nomi di properties per la Grid
    //     */
    //    public List<String> getGridPropertyNamesList() {
    //        return annotation.getGridPropertiesName(entityClazz);
    //    }

    //    /**
    //     * Gets all.
    //     *
    //     * @return the all
    //     */
    //    public Collection getAll() {
    ////        Collection items = new ArrayList();
    ////        items.add(new Alfa(4, "mario", "adesso"));
    ////        items.add(new Alfa(3, "francesca", "dopo"));
    ////        items.add(new Alfa(17, "aldo", "giovanni"));
    //MongoCollection col;
    //        Collection items = new ArrayList();
    //        items.add(new Role( "mario", "adesso"));
    //        items.add(new Role( "francesca", "dopo"));
    //        items.add(new Role("aldo", "giovanni"));
    //
    //        return items;
    //    }

    /**
     * Creazione o ricreazione di alcuni dati iniziali standard <br>
     * Invocato in fase di 'startup' e dal bottone Reset di alcune liste <br>
     * <p>
     * 1) deve esistere lo specifico metodo sovrascritto
     * 2) deve essere valida la entityClazz
     * 3) deve esistere la collezione su mongoDB
     * 4) la collezione non deve essere vuota
     * <p>
     * I dati possono essere: <br>
     * 1) recuperati da una Enumeration interna <br>
     * 2) letti da un file CSV esterno <br>
     * 3) letti da Wikipedia <br>
     * 4) creati direttamente <br>
     * DEVE essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @return vuota se è stata creata, altrimenti un messaggio di errore
     */
    @Override
    public String resetEmptyOnly() {
        return super.resetEmptyOnly();
    }

}
