package it.algos.vaadflow14.backend.packages.anagrafica.via;

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

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 10-set-2020
 * Time: 10:58
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Al lancio del programma, se la collection è vuota, carica una enumeration di valori standard <br>
 * La collection può poi venire incrementata con valori custom <br>
 */
@QueryEntity
@Document(collection = "via")
@TypeAlias("via")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderVia")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Via", keyPropertyName = "nome")
@AIView(menuIcon = VaadinIcon.COG, sortProperty = "ordine")
@AIList(fields = "ordine,nome")
@AIForm(fields = "ordine,nome")
public class Via extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordine di presentazione per il popup (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @AIField(type = AETypeField.integer, caption = "ordine", typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "#")
    public int ordine;


    /**
     * nome (obbligatorio, unico)
     */
    @NotBlank()
    @Size(min = 3)
    @Indexed(unique = true)
    @AIField(type = AETypeField.text,  focus = true)
    @AIColumn()
    public String nome;



    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

}