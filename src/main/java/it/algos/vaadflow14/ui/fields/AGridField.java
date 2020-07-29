package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.service.AArrayService;
import it.algos.vaadflow14.ui.service.AColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: lun, 27-lug-2020
 * Time: 07:25
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AGridField<T> extends AField<Object> {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AColumnService column;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AArrayService array;


    private Grid innerField;

    private List<String> listaProperties;

    private Class entityClazz;


    public AGridField() {
    }


    public AGridField(final Class<? extends AEntity> entityClazz, List<String> listaProperties) {
        innerField = new Grid();
        this.entityClazz = entityClazz;
        this.listaProperties = listaProperties;
        add(innerField);
    }


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    protected void postConstruct() {
        addColumnsGrid();
    }


    /**
     * Aggiunge in automatico le colonne previste in listaProperties <br>
     */
    protected void addColumnsGrid() {
        if (array.isValid(listaProperties)) {
            for (String propertyName : listaProperties) {
                column.add(innerField, entityClazz, propertyName);
            }
        }
    }


    @Override
    public void setItem(Collection collection) {
        innerField.setItems(collection);
    }


    @Override
    protected Object generateModelValue() {
        return innerField;
    }


    @Override
    protected void setPresentationValue(Object value) {
//        innerField.setItems(value);
    }

}
