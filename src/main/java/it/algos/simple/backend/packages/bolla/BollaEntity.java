package it.algos.simple.backend.packages.bolla;

import com.querydsl.core.annotations.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 05-mar-2021
 * Time: 18:55
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "bolla")
@TypeAlias("bolla")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderBolla")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Bolla", keyPropertyName = "code", usaCompany = false)
@AIView(menuName = "Bolla", menuIcon = VaadinIcon.COG, sortProperty = "ordine")
@AIList(fields = "code,descrizione", usaRowIndex = true)
@AIForm(fields = "code,descrizione")
public class BollaEntity extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    //@TODO
    // Le properties riportate sono INDICATIVE e possono/debbono essere sostituite
    //@TODO


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
     * descrizione (facoltativa)
     */
    @AIField(type = AETypeField.text, caption = "Descrizione completa")
    @AIColumn(header = "Descrizione", flexGrow = true)
    public String descrizione;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}