package it.algos.simple.backend.packages;

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
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 02-set-2020
 * Time: 18:17
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "gamma")
@TypeAlias("gamma")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderGamma")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Gamma", keyPropertyName = "code", usaRowIndex = true)
@AIView(menuIcon = VaadinIcon.COG)
@AIList(fields = "code,uno,due,tre,quattro,cinque")
@AIForm(fields = "code,uno,due,tre,quattro,cinque")
public class Gamma extends AEntity {

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
    @AIField(type = AETypeField.text, required = true, focus = true, caption = "Codice")
    @AIColumn(header = "Code")
    public String code;

    @AIField(type = AETypeField.localDate, caption = "Data semplice")
    @AIColumn()
    public LocalDate uno;

    @AIField(type = AETypeField.localDateTime, caption = "Data e orario")
    @AIColumn()
    public LocalDateTime due;

    @AIField(type = AETypeField.localTime, caption = "Solo orario")
    @AIColumn()
    public LocalTime tre;

    @AIField(type = AETypeField.monthdate, caption = "Data col mese")
    @AIColumn()
    public LocalDate quattro;

    @AIField(type = AETypeField.weekdate, caption = "Data con la settimana")
    @AIColumn()
    public LocalTime cinque;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}