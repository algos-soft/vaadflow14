package it.algos.vaadflow14.backend.packages.crono.secolo;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.application.FlowCost;
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

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 06:48
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Le properties sono PUBLIC per poter usare la Reflection <br>
 * Unica classe obbligatoria per un package. Le altre servono solo per personalizzare. <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "secolo")
@TypeAlias("secolo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderSecolo")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Secolo", keyPropertyName = "secolo", usaCompany = false)
@AIView(menuIcon = VaadinIcon.CALENDAR, sortProperty = "ordine")
@AIList(fields = "ordine,secolo,anteCristo,inizio,fine")
@AIForm(fields = "secolo,anteCristo,inizio,fine")
public class Secolo extends AEntity {

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
    @AIColumn()
    public String secolo;


    /**
     * flag di separazione (obbligatorio)
     */
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Ante" + FlowCost.HTLM_SPAZIO + "Cristo", widthEM = 6)
    @AIColumn(typeBool = AETypeBoolCol.yesNo, header = "A.C.")
    public boolean anteCristo;


    /**
     * primo anno (obbligatorio, unico) <br>
     */
    @AIField(type = AETypeField.integer, typeNum = AETypeNum.positiviOnly, caption = "Anno iniziale")
    @AIColumn(widthEM = 6)
    public int inizio;

    /**
     * ultimo anno (obbligatorio, unico) <br>
     */
    @AIField(type = AETypeField.integer, typeNum = AETypeNum.positiviOnly, caption = "Anno finale")
    @AIColumn(flexGrow = true)
    public int fine;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getSecolo();
    }

}