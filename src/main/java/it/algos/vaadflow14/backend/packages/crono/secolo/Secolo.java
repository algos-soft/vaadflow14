package it.algos.vaadflow14.backend.packages.crono.secolo;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolCol;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 06:48
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "secolo")
@TypeAlias("secolo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderSecolo")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Secolo", keyPropertyName = "nome", usaCompany = false)
@AIView(menuIcon = VaadinIcon.CALENDAR, sortProperty = "ordine")
@AIList(fields = "anteCristo,inizio,fine,nome", usaRowIndex = true)
@AIForm(fields = "anteCristo,inizio,fine,nome")
public class Secolo extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * nome completo (obbligatorio, unico) <br>
     */
    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, focus = true, caption = "secolo")
    @AIColumn(header = "secolo", flexGrow = true)
    public String nome;


    /**
     * flag di separazione (obbligatorio)
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Ante" + FlowCost.HTLM_SPAZIO + "Cristo", widthEM = 6)
    //    @AIField(type = AETypeField.booleano, typeBool = AETypeBool.radioTrueFalse, caption = "Ante Cristo", widthEM = 6)
    //    @AIField(type = AETypeField.booleano, typeBool = AETypeBool.radioCustomHoriz, captionRadio = "Prima di Cristo, Dopo Cristo", widthEM = 6)
    @AIColumn(typeBool = AETypeBoolCol.yesNo, header = "A.C.")
    public boolean anteCristo;


    /**
     * primo anno (obbligatorio, unico) <br>
     */
    @AIField(type = AETypeField.integer, typeNum = AETypeNum.positiviOnly, caption = "Anno iniziale")
    @AIColumn(widthEM = 6)
    public int inizio;

    /**
     * ultimo anno (obbligatorio, unico) <br>
     */
    @AIField(type = AETypeField.integer, typeNum = AETypeNum.positiviOnly, caption = "Anno finale")
    @AIColumn(widthEM = 6)
    public int fine;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

}