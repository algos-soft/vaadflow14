package it.algos.vaadflow14.backend.packages.preferenza;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.ACEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
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
@SpringComponent
@QueryEntity
@Document(collection = "preferenza")
@TypeAlias("preferenza")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderPreferenza")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Preferenza", keyPropertyName = "code", usaNote = true)
@AIView(menuIcon = VaadinIcon.COG, searchProperty = "code", sortProperty = "code")
@AIList(fields = "code,type,value,usaCompany,usaFlow,descrizione", usaRowIndex = true)
@AIForm(fields = "code,usaCompany,usaFlow,descrizione,type,value")
public class Preferenza extends ACEntity {

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
    @AIField(type = AETypeField.text, widthEM = 13, focus = true, caption = "Codice")
    @AIColumn(widthEM = 14)
    public String code;


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
     * usaCompany (facoltativo) usa un prefisso col codice della company
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.radioSiNo)
    @AIColumn(headerIcon = VaadinIcon.FACTORY)
    public boolean usaCompany;

    /**
     * usaFlow (facoltativo) usa un prefisso col codice della company
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.radioSiNo)
    @AIColumn(headerIcon = VaadinIcon.HOME)
    public boolean usaFlow;

    /**
     * descrizione (obbligatoria)
     */
    @NotBlank()
    @Size(min = 5)
    @AIField(type = AETypeField.text, widthEM = 24)
    @AIColumn(widthEM = 24, flexGrow = true)
    public String descrizione;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }


    public Object getValore() {
        return getType().bytesToObject(value);
    }

}