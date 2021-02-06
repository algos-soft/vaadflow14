package it.algos.vaadflow14.backend.packages.anagrafica.address;

import com.querydsl.core.annotations.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.packages.anagrafica.via.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 11-set-2020
 * Time: 20:21
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Le properties sono PUBLIC per poter usare la Reflection <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "address")
@TypeAlias("address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderAddress")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Address")
@AIView(menuIcon = VaadinIcon.COG)
@AIList(fields = "via,indirizzo,civico,localita,cap")
@AIForm(fields = "via,indirizzo,civico,localita,cap")
public class Address extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * via (facoltativo)
     * riferimento dinamico CON @DBRef
     */
    @DBRef
    @AIField(type = AETypeField.combo, allowCustomValue = true, comboClazz = Via.class)
    @AIColumn(widthEM = 8)
    public Via via;


    /**
     * indirizzo: via, nome e numero (obbligatoria, non unica)
     */
    @NotBlank(message = "L' indirizzo è obbligatorio")
    @Size(min = 2, max = 50)
    @AIField(type = AETypeField.text, required = true, focus = true, widthEM = 12)
    @AIColumn(widthEM = 16, sortable = false)
    public String indirizzo;

    /**
     * numero civico (facoltativo, non unico - numeri e lettere)
     */
    @Size(max = 8)
    @AIField(type = AETypeField.text, widthEM = 5)
    @AIColumn(widthEM = 6)
    public String civico;


    /**
     * località (obbligatoria, non unica)
     */
    @NotBlank(message = "La località è obbligatoria")
    @Size(min = 2, max = 50)
    @AIField(type = AETypeField.text, required = true, firstCapital = true, widthEM = 24)
    @AIColumn(widthEM = 16)
    public String localita;


    /**
     * codice di avviamento postale (facoltativo, non unica)
     */
    @Size(min = 5, max = 5, message = "Il codice postale deve essere di 5 cifre")
    @AIField(type = AETypeField.cap)
    @AIColumn()
    public String cap;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        String value = VUOTA;
        String spazio = SPAZIO;
        String sep = SEP;

        value += indirizzo != null ? indirizzo : VUOTA;
        value += localita != null ? sep + localita : VUOTA;
        value += cap != null ? spazio + cap : VUOTA;

        return value.trim();
    }

}