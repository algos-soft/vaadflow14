package it.algos.vaadflow14.backend.packages.geografica.continente;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
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
 * Date: mar, 29-set-2020
 * Time: 14:34
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "continente")
@TypeAlias("continente")
@Data()
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderContinente")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Continente", keyPropertyName = "nome", usaCompany = false)
@AIView(menuIcon = VaadinIcon.GLOBE, sortProperty = "ordine")
@AIList(fields = "ordine,nome,abitato", usaRowIndex = false)
@AIForm(fields = "ordine,nome,abitato")
public class Continente extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordine di presentazione per il popup (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @AIField(type = AETypeField.integer, typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "#", widthEM = 2)
    public int ordine;

    /**
     * nome (obbligatorio, unico)
     */
    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, focus = true)
    @AIColumn(widthEM = 14)
    public String nome;


    /**
     * esistono abitanti (facoltativa)
     */
    @AIField(type = AETypeField.booleano, caption = "Continente abitato")
    @AIColumn(header = "Ab.")
    public boolean abitato;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

}