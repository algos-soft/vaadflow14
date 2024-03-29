package it.algos.vaadflow14.backend.packages.company;

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

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * First time: sab, 15-ago-2020
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
@Document(collection = "company")
//Spring data
@TypeAlias("company")
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderCompany")
@EqualsAndHashCode(callSuper = false)
//Algos
@AIScript(sovraScrivibile = false, doc = AEWizDoc.inizioRevisione)
@AIEntity(recordName = "Company", keyPropertyName = "code", usaNote = true, usaTimeStamp = true)
@AIView(menuName = "Company", menuIcon = VaadinIcon.FACTORY, searchProperty = "code", sortProperty = "code")
@AIList(fields = "code,descrizione,telefono,email", usaRowIndex = true)
@AIForm(fields = "code,descrizione,telefono,email", usaSpostamentoTraSchede = false)
public class Company extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * codice di riferimento (obbligatorio)
     */
    @NotBlank()
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, focus = true, caption = "Codice", widthEM = 6)
    @AIColumn(widthEM = 6)
    public String code;

    /**
     * descrizione (obbligatoria)
     */
    @NotBlank()
    @AIField(type = AETypeField.text, caption = "Descrizione completa")
    @AIColumn(widthEM = 14)
    public String descrizione;

    /**
     * telefono (facoltativo)
     */
    @AIField(type = AETypeField.phone, caption = "Telefono/cellulare")
    @AIColumn()
    public String telefono;

    /**
     * mail (facoltativa)
     */
    @AIField(type = AETypeField.email, caption = "Posta elettronica")
    @AIColumn(flexGrow = true)
    public String email;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}