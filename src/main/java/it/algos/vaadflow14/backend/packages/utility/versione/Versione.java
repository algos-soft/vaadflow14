package it.algos.vaadflow14.backend.packages.utility.versione;

import com.querydsl.core.annotations.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;
import java.time.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * First time: mar, 08-dic-2020
 * Last doc revision: mer, 19-mag-2021 alle 18:38 <br>
 * <p>
 * Classe (obbligatoria) di un package <br>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Le properties sono PUBLIC per poter usare la Reflection ed i Test <br>
 * Unica classe obbligatoria per un package. <br>
 * Le altre servono solo se si vuole qualcosa in più dello standard minimo. <br>
 * <p>
 * Annotated with Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, @EqualsAndHashCode <br>
 * Annotated with Algos: @AIScript per controllare il typo di file e la ri-creazione con Wizard <br>
 * Annotated with Algos: @AIEntity per informazioni sulle property per il DB <br>
 * Annotated with Algos: @AIView per info su menu, icon, route, search e sort <br>
 * Annotated with Algos: @AIList per info sulla Grid e sulle colonne <br>
 * Annotated with Algos: @AIForm per info sul Form e sulle properties <br>
 */
//Vaadin spring
@SpringComponent
//querydsl
@QueryEntity
//Spring mongodb
@Document(collection = "versione")
//Spring data
@TypeAlias("versione")
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderVersione")
@EqualsAndHashCode(callSuper = false)
//Algos
@AIScript(sovraScrivibile = false, doc = AEWizDoc.inizioRevisione)
@AIEntity(recordName = "Versione", keyPropertyName = "code", usaCompany = false)
@AIView(menuName = "Versione", menuIcon = VaadinIcon.COG, searchProperty = "code", sortProperty = "code")
@AIList(fields = "code,giorno,descrizione", usaRowIndex = true)
@AIForm(fields = "code,giorno,descrizione", usaSpostamentoTraSchede = false)
public class Versione extends AEntity {

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


    /**
     * giorno (e ora) di rilascio (obbligatorio)
     */
    @AIField(type = AETypeField.localDate, caption = "Data")
    @AIColumn(typeData = AETypeData.normaleOrario, header = "data")
    public LocalDate giorno;


    /**
     * descrizione (obbligatoria)
     */
    @AIField(type = AETypeField.text, caption = "Descrizione completa")
    @AIColumn(header = "Descrizione", flexGrow = true)
    public String descrizione;

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}