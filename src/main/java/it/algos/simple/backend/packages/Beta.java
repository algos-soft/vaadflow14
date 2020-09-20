package it.algos.simple.backend.packages;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolCol;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
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
@AIList(fields = "code,uno,due,tre,quattro,cinque,sei,sette", headerAlert = "Esempio di valori booleani, rappresentati in diversi modi")
@AIForm(fields = "code,uno,due,tre,quattro,cinque,sei,sette")
public class Beta extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * codice di riferimento (obbligatorio, unico)
     */
    @NotBlank()
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, focus = true, caption = "Codice")
    @AIColumn(header = "Code")
    public String code;


    /**
     * booleano (colonna deprecated)
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.radioTrueFalse, caption = "Radio gruppo vero/falso")
    @AIColumn(typeBool = AETypeBoolCol.boolGrezzo, header = "Bool", widthEM = 5)
    public boolean uno;

    /**
     * booleano (colonna semplice)
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Checkbox base con spiegazione")
    @AIColumn(typeBool = AETypeBoolCol.checkBox, header = "Chk")
    public boolean due;

    /**
     * booleano (colonna standard)
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.radioSiNo, caption = "Radio gruppo si/no")
    @AIColumn(typeBool = AETypeBoolCol.checkIcon, header = "True")
    public boolean tre;

    /**
     * booleano (colonna invertita non normale)
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.radioCustomHoriz, caption = "Radio gruppo custom orizzontale", boolEnum = "casa,ufficio")
    @AIColumn(typeBool = AETypeBoolCol.checkIconReverse, header = "Rev")
    public boolean quattro;

    /**
     * booleano (colonna opzionale)
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.radioCustomVert, caption = "Radio gruppo custom verticale", boolEnum = "Aperto,Chiuso")
    @AIColumn(typeBool = AETypeBoolCol.yesNo, header = "SiNo")
    public boolean cinque;

    /**
     * booleano (colonna opzionale)
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.radioCustomHoriz, boolEnum = "Senza label, Solo valori")
    @AIColumn(typeBool = AETypeBoolCol.yesNoBold, header = "Bold")
    public boolean sei;

    /**
     * booleano (colonna custom con larghezza da regolare)
     */
    @AIField(type = AETypeField.booleano, boolEnum = "Custom colonna")
    @AIColumn(typeBool = AETypeBoolCol.customLabel, header = "Custom", boolEnum = "acceso,spento", widthEM = 6)
    public boolean sette;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}