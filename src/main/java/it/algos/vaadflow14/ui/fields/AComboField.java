package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.GeneratedVaadinComboBox;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.packages.anagrafica.via.ViaLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: gio, 11-giu-2020
 * Time: 22:00
 * Simple layer around ComboBox value <br>
 * Banale, ma serve per avere tutti i fields omogenei <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AComboField<T> extends AField<Object> {

    public ComboBox comboBox;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ViaLogic viaLogic;

    private List items;


    /**
     * Costruttore con parametri <br>
     * L' istanza viene costruita con appContext.getBean(AComboField.class, items, isRequired, isAllowCustomValue) <br>
     *
     * @param items      collezione dei valori previsti
     * @param isRequired true, se NON ammette il valore nullo
     */
    public AComboField(List<String> items, boolean isRequired, boolean isAllowCustomValue) {
        comboBox = new ComboBox();
        this.setItems(items);
        comboBox.setClearButtonVisible(!isRequired);

        /**
         * Allow users to enter a value which doesn't exist in the data set, and
         * set it as the value of the ComboBox.
         */
        if (isAllowCustomValue) {
            comboBox.setAllowCustomValue(true);
            this.addCustomListener();
        }

        add(comboBox);
    } // end of SpringBoot constructor


    public void setItems(List items) {
        if (items != null) {
            try {
                this.items = items;
                comboBox.setItems(items);
            } catch (Exception unErrore) {
                System.out.println("Items nulli in AComboField.setItems()");
            }
        }
    }


    @Override
    protected Object generateModelValue() {
        return comboBox.getValue();
    }


    @Override
    protected void setPresentationValue(Object value) {
        comboBox.setValue(value);
    }


    public void addCustomListener() {
        comboBox.addCustomValueSetListener(event -> {
            String newValue = ((GeneratedVaadinComboBox.CustomValueSetEvent) event).getDetail();
            items.add(newValue);
            setItems(items);
            comboBox.setValue(newValue);
        });
    }


    @Override
    public Registration addValueChangeListener(ValueChangeListener valueChangeListener) {
        return comboBox.addValueChangeListener(valueChangeListener);
    }


    @Override
    public void setWidth(String width) {
        comboBox.setWidth(width);
    }


    @Override
    public void setErrorMessage(String errorMessage) {
        comboBox.setErrorMessage(errorMessage);
    }

}
