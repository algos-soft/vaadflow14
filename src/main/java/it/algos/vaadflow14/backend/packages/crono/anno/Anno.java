package it.algos.vaadflow14.backend.packages.crono.anno;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolCol;
import it.algos.vaadflow14.backend.enumeration.AETypeBoolField;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 07:20
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "anno")
@TypeAlias("anno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderAnno")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Anno", keyPropertyName = "nome", usaRowIndex = false, usaCompany = false)
@AIView(menuIcon = VaadinIcon.CALENDAR)
@AIList(fields = "ordine,secolo,bisestile,nome")
@AIForm(fields = "ordine,secolo,bisestile,nome")
public class Anno extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * ordinamento (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, caption = "progressivo", typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "#")
    public int ordine;

    /**
     * secolo di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @AIField(type = AETypeField.combo, comboClazz = Secolo.class)
    @AIColumn(widthEM = 8)
    public Secolo secolo;


    /**
     * nome completo (obbligatorio, unico) <br>
     */
    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, focus = true, caption = "anno")
    @AIColumn(header = "anno", flexGrow = true)
    public String nome;

    /**
     * flag bisestile (obbligatorio)
     */
    @Indexed()
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Anno bisestile", widthEM = 6)
    @AIColumn(typeBool = AETypeBoolCol.yesNo, header = "BS")
    public boolean bisestile;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

}