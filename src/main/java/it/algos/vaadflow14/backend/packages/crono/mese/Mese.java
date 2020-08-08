package it.algos.vaadflow14.backend.packages.crono.mese;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.binder.Binder;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEFieldType;
import it.algos.vaadflow14.backend.enumeration.AENumType;
import lombok.*;
import org.hibernate.validator.constraints.Range;
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
 * Date: gio, 30-lug-2020
 * Time: 08:27
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "mese")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderMese")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Mese", keyPropertyName = "nome")
@AIView(menuIcon = VaadinIcon.CALENDAR, sortProperty = "ordine")
@AIList(fields = "ordine,nome,giorni,giorniBisestile,sigla")
@AIForm(fields = "ordine,nome,giorni,giorniBisestile,sigla")
public class Mese extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordinamento (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AEFieldType.integer, caption = "progressivo", numType = AENumType.positiviOnly)
    @AIColumn(header = "#")
    public int ordine;

    /**
     * nome completo (obbligatorio, unico) <br>
     */
    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AEFieldType.text, focus = true)
    @AIColumn(flexGrow = true)
    public String nome;

    /**
     * numero di giorni presenti (obbligatorio) <br>
     */
    @Range(min = 28, max = 31)
    @NotNull(message = "I giorni devono essere 28, 30 o 31")
    @AIField(type = AEFieldType.integer, numType = AENumType.rangeControl)
    @AIColumn(headerIcon = VaadinIcon.CALENDAR, headerIconColor = "green")
    public int giorni;


    /**
     * numero di giorni presenti se anno bisestile (obbligatorio) <br>
     */
    @Range(min = 29, max = 31)
    @AIField(type = AEFieldType.integer, caption = "bisestile", numType = AENumType.rangeControl)
    @AIColumn(headerIcon = VaadinIcon.CALENDAR, headerIconColor = "red")
    public int giorniBisestile;


    /**
     * nome abbreviato di tre cifre (obbligatorio, unico) <br>
     */
    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @Size(min = 3, max = 3)
    @AIField(type = AEFieldType.text, required = true, widthEM = 4, placeholder = "xxx")
    @AIColumn()
    public String sigla;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return nome;
    }

}