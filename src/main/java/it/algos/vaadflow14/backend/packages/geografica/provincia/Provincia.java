package it.algos.vaadflow14.backend.packages.geografica.provincia;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import it.algos.vaadflow14.backend.enumeration.AETypeProvincia;
import it.algos.vaadflow14.backend.packages.geografica.regione.Regione;
import it.algos.vaadflow14.backend.packages.geografica.regione.RegioneLogic;
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
 * Date: mar, 15-set-2020
 * Time: 11:29
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Le properties sono PUBLIC per poter usare la Reflection <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "provincia")
@TypeAlias("provincia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderProvincia")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Provincia", keyPropertyName = "nome", usaCompany = false)
@AIView(menuIcon = VaadinIcon.GLOBE, searchProperty = "nome", sortProperty = "ordine")
@AIList(fields = "ordine,nome,sigla,regione,iso,status")
@AIForm(fields = "ordine,nome,sigla,regione,iso,status")
public class Provincia extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordinamento (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, caption = "ordine", typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "#", widthEM = 3)
    public int ordine;

    /**
     * nome (obbligatorio, unico) <br>
     */
    @NotBlank()
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, focus = true, firstCapital = true, caption = "provincia")
    @AIColumn()
    public String nome;


    /**
     * sigla (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Sigla obbligatoria")
    @Size(min = 2)
    @Indexed(direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, caption = "sigla breve", widthEM = 12)
    @AIColumn(header = "#", widthEM = 5)
    public String sigla;

    /**
     * regione (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @AIField(type = AETypeField.combo, comboClazz = Regione.class, logicClazz = RegioneLogic.class, usaComboMethod = true, methodName = "creaComboRegioniItaliane")
    @AIColumn(widthEM = 11)
    public Regione regione;


    /**
     * stato (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
//    @NotNull
    @DBRef
    @AIField(type = AETypeField.combo, comboClazz = Stato.class, logicClazz = StatoLogic.class, usaComboMethod = true, methodName = "creaComboStati")
    @AIColumn(widthEM = 8)
    public Stato stato;

    /**
     * codice iso di riferimento (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Il codice ISO numerico è obbligatorio")
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, required = true, caption = "Codice ISO 3166-2:IT", widthEM = 12)
    @AIColumn(header = "iso", widthEM = 5)
    public String iso;


    /**
     * statuto normativo (obbligatorio) <br>
     */
    @NotNull()
    @AIField(type = AETypeField.enumeration, enumClazz = AETypeProvincia.class, widthEM = 16)
    @AIColumn(widthEM = 15)
    public AETypeProvincia status;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

}