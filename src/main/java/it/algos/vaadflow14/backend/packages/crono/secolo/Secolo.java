package it.algos.vaadflow14.backend.packages.crono.secolo;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEFieldType;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 06:48
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "secolo")
@TypeAlias("secolo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderSecolo")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Secolo", keyPropertyName = "nome")
@AIView(menuIcon = VaadinIcon.CALENDAR, sortProperty = "ordine")
@AIList(fields = "ordine,nome,anteCristo,inizio,fine")
@AIForm(fields = "ordine,nome,anteCristo,inizio,fine")
public class Secolo extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordinamento (obbligatorio, unico) <br>
     */
    @NotNull
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AEFieldType.integer, widthEM = 5)
    @AIColumn(header = "#", widthEM = 5)
    public int ordine;


    /**
     * flag di separazione (obbligatorio)
     */
    @Indexed()
    @AIField(type = AEFieldType.yesNo, required = true, caption = "A.C.", widthEM = 6)
    @AIColumn(header = "A.C.", widthEM = 6)
    public boolean anteCristo;


    /**
     * primo anno (obbligatorio, unico) <br>
     */
    @NotNull
    @AIField(type = AEFieldType.integer, widthEM = 3)
    @AIColumn(widthEM = 5)
    public int inizio;

    /**
     * ultimo anno (obbligatorio, unico) <br>
     */
    @NotNull
    @AIField(type = AEFieldType.integer, widthEM = 3)
    @AIColumn(widthEM = 5)
    public int fine;

    /**
     * titolo (obbligatorio, unico) <br>
     */
    @AIField(type = AEFieldType.text, required = true, widthEM = 26)
    @AIColumn(flexGrow = true)
    public String nome;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

}