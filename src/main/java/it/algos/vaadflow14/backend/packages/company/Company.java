package it.algos.vaadflow14.backend.packages.company;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
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
 * Date: sab, 15-ago-2020
 * Time: 14:52
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Le properties sono PUBLIC per poter usare la Reflection <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "company")
@TypeAlias("company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderCompany")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Company", keyPropertyName = "code", usaNote = true, usaCreazioneModifica = true)
@AIView(menuIcon = VaadinIcon.FACTORY)
@AIList(fields = "code,descrizione,telefono,email", usaRowIndex = true)
@AIForm(fields = "code,descrizione,telefono,email")
public class Company extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * codice di riferimento (obbligatorio)
     */
    @NotBlank()
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, focus = true, caption = "Codice", widthEM = 6)
    @AIColumn(widthEM = 6)
    public String code;

    /**
     * descrizione (obbligatoria)
     */
    @NotBlank()
    @AIField(type = AETypeField.text, caption = "Descrizione completa")
    @AIColumn(widthEM = 14)
    public String descrizione;

    /**
     * telefono (facoltativo)
     */
    @AIField(type = AETypeField.phone, caption = "Telefono/cellulare")
    @AIColumn()
    public String telefono;

    /**
     * mail (facoltativa)
     */
    @AIField(type = AETypeField.email, caption = "Posta elettronica")
    @AIColumn(flexGrow = true)
    public String email;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}