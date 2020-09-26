package it.algos.vaadflow14.backend.packages.preferenza;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.ACEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypePref;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 25-ago-2020
 * Time: 11:23
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "preferenza")
@TypeAlias("preferenza")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderPreferenza")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Preferenza", keyPropertyName = "code", usaNote = true)
@AIView(menuIcon = VaadinIcon.COG, searchProperty = "code", sortProperty = "code")
@AIList(fields = "code,type,value,descrizione", usaRowIndex = true)
@AIForm(fields = "code,descrizione,type,value")
public class Preferenza extends ACEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    //    /**
    //     * ordinamento (obbligatorio, unico) <br>
    //     */
    //    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    //    @AIField(type = AETypeField.integer, caption = "ordine", typeNum = AETypeNum.positiviOnly)
    //    @AIColumn(header = "#")
    //    public int ordine;


    /**
     * codice di riferimento (obbligatorio, unico)
     */
    @NotBlank()
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, widthEM = 13, focus = true, caption = "Codice")
    @AIColumn(widthEM = 14)
    public String code;

    /**
     * descrizione (obbligatoria)
     */
    @NotBlank()
    @Size(min = 5)
    @AIField(type = AETypeField.text, widthEM = 24)
    @AIColumn(widthEM = 24,flexGrow = true)
    public String descrizione;

    /**
     * tipo di dato memorizzato (obbligatorio)
     */
    @NotNull
    @AIField(type = AETypeField.enumeration, enumClazz = AETypePref.class, required = true, focus = true, widthEM = 12)
    @AIColumn(widthEM = 6, sortable = true)
    public AETypePref type;

    /**
     * valore della preferenza (obbligatorio)
     */
    @NotNull
    @AIField(type = AETypeField.preferenza, required = true, caption = "Valore", widthEM = 12)
    @AIColumn(widthEM = 10)
    public byte[] value;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}