package it.algos.vaadflow14.ui.fields;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.GeneratedVaadinComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
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

    private ComboBox comboBox;

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
//        if (isAllowCustomValue) {
//            comboBox.setAllowCustomValue(true);
//            this.addCustomListener();
//        }

        add(comboBox);
    } // end of SpringBoot constructor



    public void setItems(List items) {
        try {
            this.items = items;
            comboBox.setItems(items);
        } catch (Exception unErrore) {
            System.out.println("Items nulli in AComboField.setItems()");
        }
    }


    @Override
    protected Object generateModelValue() {
        Object alfa= comboBox.getValue();
        return comboBox.getValue();
    }


    @Override
    protected void setPresentationValue(Object value) {
        comboBox.setValue(value);
    }


    public void addCustomListener() {
        comboBox.addCustomValueSetListener(event -> {
            Object newValue = ((GeneratedVaadinComboBox.CustomValueSetEvent) event).getDetail();
            comboBox.setValue(newValue);
            items.add(newValue);
            setItems(items);
        });
    }

}
