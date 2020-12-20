package it.algos.simple.backend.packages;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeData;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.packages.anagrafica.via.Via;
import it.algos.vaadflow14.backend.packages.anagrafica.via.ViaLogic;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 09-set-2020
 * Time: 20:25
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "delta")
@TypeAlias("delta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderDelta")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Delta", keyPropertyName = "code")
@AIView(menuIcon = VaadinIcon.COG)
@AIList(fields = "code,secolo,via,immagine,uno,due,tre", headerAlert = "Esempio di comboBox con e senza valori personalizzati")
@AIForm(fields = "code,secolo,via,immagine,uno,due,tre")
public class Delta extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


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
     * secolo di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @AIField(type = AETypeField.combo, comboClazz = Secolo.class)
    @AIColumn(widthEM = 10)
    public Secolo secolo;

    /**
     * via (facoltativo)
     * riferimento dinamico CON @DBRef
     */
    @DBRef
    @AIField(type = AETypeField.combo, allowCustomValue = true, comboClazz = Via.class, logicClazz = ViaLogic.class)
    @AIColumn(widthEM = 8)
    public Via via;

    @AIField(type = AETypeField.image)
    public String immagine;

    @AIField(type = AETypeField.localDateTime, caption = "data e orario completi")
    @AIColumn(typeData = AETypeData.completaOrario, header = "data e orario completi")
    public LocalDateTime uno;


    @AIField(type = AETypeField.localDate, caption = "AETypeData.dateShort")
    @AIColumn(typeData = AETypeData.standard, header = "standard")
    public LocalDate due;


    @AIField(type = AETypeField.localTime, caption = "Settimana estesa")
    @AIColumn(typeData = AETypeData.orario, header = "orario")
    public LocalTime tre;

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}