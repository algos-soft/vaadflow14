package it.algos.simple.backend.packages;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeData;
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
@AIEntity(recordName = "Gamma", keyPropertyName = "code")
@AIView(menuIcon = VaadinIcon.COG)
@AIList(fields = "code,uno,due,tre,quattro,cinque,sei,sette,otto,nove,dieci,undici,dodici,tredici,quattordici,quindici,sedici,diciassette", usaRowIndex = true, headerAlert = "Esempio di date e tempi, rappresentati in diversi modi")
@AIForm(fields = "code,uno,due,tre,quattro,cinque,sei,sette,otto,nove,dieci,undici,dodici,tredici,quattordici,quindici,sedici,diciassette")
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


    @AIField(type = AETypeField.localDate, caption = "AETypeData.dateShort")
    @AIColumn(typeData = AETypeData.standard, header = "standard")
    public LocalDate uno;

    @AIField(type = AETypeField.localDate, caption = "Data semplice")
    @AIColumn(typeData = AETypeData.dateShort, header = "short")
    public LocalDate due;

    @AIField(type = AETypeField.localDate, caption = "Solo orario")
    @AIColumn(typeData = AETypeData.dateNormal, header = "normal")
    public LocalDate tre;

    @AIField(type = AETypeField.localDate, caption = "Mese short")
    @AIColumn(typeData = AETypeData.dateLong, header = "long")
    public LocalDate quattro;

    @AIField(type = AETypeField.localDate, caption = "Mese normal")
    @AIColumn(typeData = AETypeData.meseShort, header = "short")
    public LocalDate cinque;

    @AIField(type = AETypeField.localDate, caption = "Mese esteso")
    @AIColumn(typeData = AETypeData.meseNormal, header = "normal")
    public LocalDate sei;

    @AIField(type = AETypeField.localDate, caption = "Settimana breve")
    @AIColumn(typeData = AETypeData.meseLong, header = "long")
    public LocalDate sette;

    @AIField(type = AETypeField.localDate, caption = "Settimana estesa")
    @AIColumn(typeData = AETypeData.weekShort, header = "short")
    public LocalDate otto;

    @AIField(type = AETypeField.localDate, caption = "Settimana estesa")
    @AIColumn(typeData = AETypeData.weekShortMese, header = "week")
    public LocalDate nove;

    @AIField(type = AETypeField.localDate, caption = "Settimana estesa")
    @AIColumn(typeData = AETypeData.weekLong, header = "week long")
    public LocalDate dieci;

    @AIField(type = AETypeField.localDate, caption = "Settimana estesa")
    @AIColumn(typeData = AETypeData.meseCorrente, header = "mese corrente")
    public LocalDate undici;

    @AIField(type = AETypeField.localDate, caption = "data completa")
    @AIColumn(typeData = AETypeData.dataCompleta, header = "data completa")
    public LocalDate dodici;

    @AIField(type = AETypeField.localDateTime, caption = "data e orario completi")
    @AIColumn(typeData = AETypeData.completaOrario, header = "data e orario completi")
    public LocalDateTime tredici;

    @AIField(type = AETypeField.localDateTime, caption = "Settimana estesa")
    @AIColumn(typeData = AETypeData.normaleOrario, header = "data e orario")
    public LocalDateTime quattordici;

    @AIField(type = AETypeField.localDateTime, caption = "Settimana estesa")
    @AIColumn(typeData = AETypeData.iso8601, header = "formato iso")
    public LocalDateTime quindici;

    @AIField(type = AETypeField.localTime, caption = "Settimana estesa")
    @AIColumn(typeData = AETypeData.orario, header = "orario")
    public LocalTime sedici;

    @AIField(type = AETypeField.localTime, caption = "Settimana estesa")
    @AIColumn(typeData = AETypeData.orarioLungo, header = "lungo")
    public LocalTime diciassette;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}