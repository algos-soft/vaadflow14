package it.algos.vaadflow14.backend.packages.geografica.stato;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
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
import javax.validation.constraints.Size;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 06:45
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "stato")
@TypeAlias("stato")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderStato")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Stato", keyPropertyName = "nome", usaRowIndex = false, usaCompany = false)
@AIView(menuIcon = VaadinIcon.GLOBE, searchProperty = "nome", sortProperty = "ordine")
@AIList(fields = "ordine,nome,ue,numerico,alfadue,alfatre,locale")
@AIForm(fields = "ordine,nome,ue,numerico,alfadue,alfatre,locale")
public class Stato extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordine di presentazione per il popup (obbligatorio, unico) <br>
     */
    //    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @AIField(type = AETypeField.integer, caption = "ordine", typeNum = AETypeNum.positiviOnly, widthEM = 4)
    @AIColumn(header = "#", widthEM = 3)
    public int ordine;


    /**
     * nome (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Nome obbligatorio")
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, focus = true, firstCapital = true, widthEM = 24)
    @AIColumn(widthEM = 12)
    public String nome;

    /**
     * unione europea
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Appartenente all' Unione Europea")
    @AIColumn(typeBool = AETypeBoolCol.checkIcon, header = "UE")
    public boolean ue;


    /**
     * codice iso-numerico di riferimento (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Il codice ISO numerico è obbligatorio")
    @Indexed()
    @AIField(type = AETypeField.text, widthEM = 4)
    @AIColumn(header = "code", widthEM = 6)
    public String numerico;


    /**
     * codice iso-alfa2 di riferimento (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Il codice iso-alfa2 numerico è obbligatorio")
    @Indexed()
    @AIField(type = AETypeField.text, caption = "alfa-due", widthEM = 4)
    @AIColumn(header = "due", widthEM = 5)
    public String alfadue;


    /**
     * codice iso-alfa3 di riferimento (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Il codice iso-alfa3 numerico è obbligatorio")
    @Indexed()
    @AIField(type = AETypeField.text, caption = "alfa-tre", widthEM = 4)
    @AIColumn(header = "tre", widthEM = 5)
    public String alfatre;


    /**
     * codice iso-locale di riferimento (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Il codice iso locale è obbligatorio")
    @Indexed()
    @AIField(type = AETypeField.text, widthEM = 10)
    @AIColumn(header = "ISO locale", widthEM = 10)
    public String locale;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

}