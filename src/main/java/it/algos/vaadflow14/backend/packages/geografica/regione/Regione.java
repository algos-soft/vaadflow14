package it.algos.vaadflow14.backend.packages.geografica.regione;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
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
 * Date: sab, 12-set-2020
 * Time: 10:24
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "regione")
@TypeAlias("regione")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderRegione")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Regione", keyPropertyName = "nome", usaRowIndex = true, usaCompany = false)
@AIView(menuIcon = VaadinIcon.GLOBE, searchProperty = "nome", sortProperty = "id")
@AIList(fields = "nome,iso,sigla,statuto")
@AIForm(fields = "nome,iso,sigla,statuto")
public class Regione extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * nome (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Nome obbligatorio")
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, focus = true, firstCapital = true, widthEM = 18)
    @AIColumn(widthEM = 18)
    public String nome;


    /**
     * codice iso di riferimento (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Il codice ISO numerico è obbligatorio")
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, caption = "Codice ISO 3166-2:IT", widthEM = 12)
    @AIColumn(header = "iso", widthEM = 6)
    public String iso;


    /**
     * sigla (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Sigla obbligatoria")
    @Size(min = 2)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, caption = "sigla breve", widthEM = 12)
    @AIColumn(widthEM = 6)
    public String sigla;


    /**
     * statuto normativo (obbligatorio) <br>
     */
    @NotNull()
    @AIField(type = AETypeField.enumeration, enumClazz = AEStatuto.class)
    @AIColumn(widthEM = 10)
    public AEStatuto statuto;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

}