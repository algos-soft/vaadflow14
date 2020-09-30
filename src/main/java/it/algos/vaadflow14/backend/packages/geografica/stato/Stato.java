package it.algos.vaadflow14.backend.packages.geografica.stato;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolCol;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import it.algos.vaadflow14.backend.packages.geografica.continente.Continente;
import it.algos.vaadflow14.backend.packages.geografica.regione.Regione;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

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
@AIEntity(recordName = "Stato", keyPropertyName = "stato", usaCompany = false)
@AIView(menuIcon = VaadinIcon.GLOBE, searchProperty = "stato", sortProperty = "ordine")
@AIList(fields = "ordine,bandiera,stato,ue,continente,numerico,alfadue,alfatre,locale")
@AIForm(fields = "ordine,stato,bandiera,regioni,ue,continente,numerico,alfadue,alfatre,locale")
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
    @AIColumn(header = "#", widthEM = 4)
    public int ordine;


    /**
     * nome (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Nome obbligatorio")
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, focus = true, firstCapital = true, widthEM = 24)
    @AIColumn(widthEM = 12)
    public String stato;

    /**
     * bandierina per i popup (facoltativa) <br>
     */
    @AIField(type = AETypeField.image, heightEM = 6)
    @AIColumn(headerIcon = VaadinIcon.GLOBE)
    public String bandiera;


    /**
     * divisione amministrativa di secondo livello (facoltativa) <br>
     */
    @Transient()
    @AIField(type = AETypeField.gridShowOnly, caption = "divisioni amministrative di secondo livello", linkClazz = Regione.class, linkProperty = "stato", properties = "regione,iso,sigla,status")
    public List<Regione> regioni;


    /**
     * continente (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    //    @NotNull
    @DBRef
    @AIField(type = AETypeField.combo, comboClazz = Continente.class, widthEM = 14)
    @AIColumn(widthEM = 8)
    public Continente continente;

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
    @AIColumn(header = "ISO locale", flexGrow = true)
    public String locale;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getStato();
    }

}