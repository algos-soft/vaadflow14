package it.algos.vaadflow14.backend.packages.geografica.regione;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEStatus;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.backend.packages.geografica.stato.StatoLogic;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 10:24
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "regione")
@TypeAlias("regione")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderRegione")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Regione", keyPropertyName = "divisione", usaCompany = false)
@AIView(menuIcon = VaadinIcon.GLOBE, searchProperty = "divisione", sortProperty = "ordine")
@AIList(fields = "ordine,divisione,stato,iso,sigla,status",title = "divisione")
@AIForm(fields = "ordine,divisione,stato,iso,sigla,status")
public class Regione extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordine di presentazione per il popup (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @AIField(type = AETypeField.integer, typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "#", widthEM = 4)
    public int ordine;

    /**
     * nome (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Nome obbligatorio")
    @Size(min = 2)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, focus = true, firstCapital = true, widthEM = 19)
    @AIColumn(widthEM = 18)
    public String divisione;


    /**
     * stato (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @AIField(type = AETypeField.combo, comboClazz = Stato.class, logicClazz = StatoLogic.class, usaComboMethod = true,methodName = "creaComboStati")
    @AIColumn(widthEM = 8)
    public Stato stato;

    /**
     * codice iso di riferimento (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Il codice ISO numerico è obbligatorio")
    @Indexed(direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, caption = "Codice ISO 3166-2:IT", widthEM = 12)
    @AIColumn(header = "iso", widthEM = 6)
    public String iso;


    /**
     * sigla (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Sigla obbligatoria")
    @Indexed(direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, caption = "sigla breve", widthEM = 12)
    @AIColumn(widthEM = 6)
    public String sigla;


    /**
     * statuto normativo (facoltativo) <br>
     */
    @AIField(type = AETypeField.enumeration, enumClazz = AEStatus.class, widthEM = 19)
    @AIColumn(widthEM = 18,flexGrow = true)
    public AEStatus status;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getDivisione();
    }

}