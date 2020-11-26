package it.algos.vaadflow14.backend.packages.crono.giorno;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
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
 * Date: ven, 14-ago-2020
 * Time: 15:19
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "giorno")
@TypeAlias("giorno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderGiorno")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIView(menuIcon = VaadinIcon.CALENDAR, sortProperty = "ordine")
@AIEntity(recordName = "Giorno", keyPropertyName = "giorno", usaCompany = false)
@AIList(fields = "ordine,giorno", usaRowIndex = false)
@AIForm(fields = "ordine,giorno,mese")
public class Giorno extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordinamento (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, caption = "progressivo", typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "#", widthEM = 3)
    public int ordine;


    /**
     * nome completo (obbligatorio, unico) <br>
     */
    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, focus = true)
    @AIColumn(flexGrow = true)
    public String giorno;


    /**
     * mese di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @AIField(type = AETypeField.combo, comboClazz = Mese.class)
    @AIColumn(widthEM = 8)
    public Mese mese;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return giorno;
    }

}