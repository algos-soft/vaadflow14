package it.algos.simple.backend.packages;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeBool;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 28-ago-2020
 * Time: 07:50
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "beta")
@TypeAlias("beta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderBeta")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Beta", keyPropertyName = "code")
@AIView(menuIcon = VaadinIcon.COG, sortProperty = "ordine")
@AIList(fields = "ordine,code,uno,due,tre,quattro,cinque,sei")
@AIForm(fields = "ordine,code,uno,due,tre,quattro,cinque,sei")
public class Beta extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * ordinamento (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, caption = "ordine", typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "#")
    public int ordine;

    /**
     * codice di riferimento (obbligatorio, unico)
     */
    @NotBlank()
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, focus = true, caption = "Codice")
    @AIColumn(header = "Code")
    private String code;


    /**
     * booleano
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBool.checkBox, caption = "Checkbox base con spiegazione")
    @AIColumn()
    private String uno;

    /**
     * booleano
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBool.radioTrueFalse, caption = "Label del radio gruppo standard orizzontale")
    @AIColumn()
    private String due;

    /**
     * booleano
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBool.radioSiNo, caption = "Label del radio gruppo standard orizzontale")
    @AIColumn()
    private String tre;

    /**
     * booleano
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBool.radioCustomHoriz, caption = "Label del radio gruppo custom orizzontale",boolEnum = "casa,ufficio")
    @AIColumn()
    private String quattro;

    /**
     * booleano
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBool.radioCustomVert, caption = "Label del radio gruppo custom verticale",boolEnum = "Aperto,Chiuso")
    @AIColumn()
    private String cinque;

    /**
     * booleano
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBool.radioCustomHoriz,boolEnum = "Senza label,Solo valori")
    @AIColumn()
    private String sei;



    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}