package it.algos.vaadflow14.backend.packages.company;

import com.mysema.query.annotations.QueryEntity;
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

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 15-ago-2020
 * Time: 14:52
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "company")
@TypeAlias("company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderCompany")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Company", keyPropertyName = "code")
@AIList(fields = "ordine,code,telefono,email,descrizione")
@AIForm(fields = "ordine,code,telefono,email,descrizione")
public class Company extends AEntity {

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
     * codice di riferimento (obbligatorio)
     */
    @NotBlank()
    @Size(min = 3, max = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, focus = true, caption = "Codice", widthEM = 8)
    @AIColumn(widthEM = 6)
    private String code;


    /**
     * telefono (facoltativo)
     */
    @AIField(type = AETypeField.phone, caption = "Telefono/cellulare")
    @AIColumn()
    private String telefono;

    /**
     * mail (facoltativa)
     */
    @AIField(type = AETypeField.email, caption = "Posta elettronica")
    @AIColumn()
    private String email;


    /**
     * descrizione (facoltativa)
     */
    @NotBlank()
    @AIField(type = AETypeField.text, caption = "Descrizione completa")
    @AIColumn(header = "Descrizione", flexGrow = true)
    private String descrizione;


    /**
     * Eventuali note (facoltativo) <br>
     */
    @AIField(type = AETypeField.textArea)
    @AIColumn()
    public String note;

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}