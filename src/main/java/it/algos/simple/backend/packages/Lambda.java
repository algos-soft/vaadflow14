package it.algos.simple.backend.packages;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginI18n;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 19-set-2020
 * Time: 21:37
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "lambda")
@TypeAlias("lambda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderLambda")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Lambda", keyPropertyName = "uno", usaCompany = false)
@AIView(menuIcon = VaadinIcon.COG, sortProperty = "uno")
@AIList(fields = "uno,due,tre,quattro", headerAlert = "Validator dei textField")
@AIForm(fields = "uno,due,tre,quattro")
public class Lambda extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    @AIField(type = AETypeField.text, required = true,  caption = "Required (uno)")
    @AIColumn()
    public String uno;

    @NotBlank()
    @AIField(type = AETypeField.text, caption = "NotBlank (due)")
    @AIColumn()
    public String due;

    @NotBlank(message = "Testo errore specifico")
    @AIField(type = AETypeField.text, caption = "NotBlank (tre)")
    @AIColumn()
    public String tre;

    @Size(min = 3)
    @AIField(type = AETypeField.text, caption = "Minimo=3 (quattro)")
    @AIColumn()
    public String quattro;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getUno();
    }

}