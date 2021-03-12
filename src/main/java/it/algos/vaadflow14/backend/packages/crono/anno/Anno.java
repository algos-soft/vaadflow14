package it.algos.vaadflow14.backend.packages.crono.anno;

import com.querydsl.core.annotations.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.packages.crono.secolo.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 07:20
 * <p>
 * Classe (obbligatoria) di un package <br>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Le properties sono PUBLIC per poter usare la Reflection <br>
 * Unica classe obbligatoria per un package. Le altre servono solo per personalizzare. <br>
 * <p>
 * Annotated with Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, @EqualsAndHashCode <br>
 * Annotated with Algos: @AIScript per controllare la ri-creazione di questo file dal Wizard <br>
 * Annotated with Algos: @AIEntity per informazioni sulle property per il DB <br>
 * Annotated with Algos: @AIView per info su menu, icon, route, search e sort <br>
 * Annotated with Algos: @AIList per info sulle colonne della Grid <br>
 * Annotated with Algos: @AIForm per info sulle properties del Form <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "anno")
@TypeAlias("anno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderAnno")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Anno", keyPropertyName = "anno", usaCompany = false, usaCreazioneModifica = false)
@AIView(menuName = "Anno", menuIcon = VaadinIcon.CALENDAR, searchProperty = "anno", sortProperty = "ordine")
@AIList(fields = "ordine,anno,bisestile,secolo", usaRowIndex = false)
@AIForm(fields = "anno,bisestile,secolo", usaSpostamentoTraSchede = false)
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
     * nome completo (obbligatorio, unico) <br>
     */
    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, focus = true)
    @AIColumn(widthEM = 6)
    public String anno;

    /**
     * flag bisestile (obbligatorio)
     */
    @Indexed()
    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox, caption = "Anno bisestile", widthEM = 6)
    @AIColumn(typeBool = AETypeBoolCol.yesNo, header = "BS")
    public boolean bisestile;

    /**
     * secolo di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @AIField(type = AETypeField.combo, comboClazz = Secolo.class)
    @AIColumn(flexGrow = true)
    public Secolo secolo;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getAnno();
    }

}