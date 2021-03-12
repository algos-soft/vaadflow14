package it.algos.vaadflow14.backend.packages.forza;

import com.querydsl.core.annotations.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;

/**
 * Project: vaadflow14 <br>
 * Created by Algos <br>
 * User: gac <br>
 * Fix date: mar, 9-mar-2021 <br>
 * Fix time: 20:40 <br>
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
@Document(collection = "forza")
@TypeAlias("forza")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderForza")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Forza", keyPropertyName = "code", usaCompany = false)
@AIView(menuName = "Forza", menuIcon = VaadinIcon.ASTERISK, searchProperty = "code", sortProperty = "code")
@AIList(fields = "code,descrizione", usaRowIndex = false)
@AIForm(fields = "code,descrizione", usaSpostamentoTraSchede = false)
public class Forza extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * code di riferimento (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Il code è obbligatorio")
    @Indexed(unique = true)
    @Size(min = 2)
    @AIField(type = AETypeField.text, required = true, focus = true, caption = "code", widthEM = 8)
    @AIColumn(header = "code", widthEM = 8)
    public String code;

    /**
     * descrizione (facoltativo, non unico) <br>
     */
    @Indexed(unique = false)
    @Size(min = 2, max = 50)
    @AIField(type = AETypeField.text, firstCapital = true, caption = "descrizione", widthEM = 24)
    @AIColumn(header = "descrizione", flexGrow = true)
    public String descrizione;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return code;
    }


}// end of entity class