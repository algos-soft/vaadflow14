package it.algos.vaadflow14.backend.packages.crono.giorno;

import com.querydsl.core.annotations.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.packages.crono.mese.*;
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
 * First time: ven, 14-ago-2020
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
@Document(collection = "giorno")
//Spring data
@TypeAlias("giorno")
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderGiorno")
@EqualsAndHashCode(callSuper = false)
//Algos
@AIScript(sovraScrivibile = false, type = AETypeFile.entity, doc = AEWizDoc.inizioRevisione)
@AIEntity(recordName = "Giorno", keyPropertyName = "titolo", usaKeyIdMinuscolaCaseInsensitive = true, usaBoot = true, usaNew = false)
@AIView(menuName = "giorni", menuIcon = VaadinIcon.CALENDAR, searchProperty = "titolo", sortProperty = "ordine")
@AIList(usaRowIndex = false)
@AIForm(usaSpostamentoTraSchede = false)
public class Giorno extends AREntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordinamento (obbligatorio, unico) <br>
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, caption = "progressivo", typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "#", widthEM = 4)
    public int ordine;


    /**
     * nome completo (obbligatorio, unico) <br>
     */
    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, focus = true)
    @AIColumn(widthEM = 8)
    public String titolo;


    /**
     * mese di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @AIField(type = AETypeField.combo, comboClazz = Mese.class, usaComboBox = true)
    @AIColumn(widthEM = 8)
    public Mese mese;

    /**
     * progressivo da inizio anno (obbligatorio, unico) non bisestile <br>
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, caption = "inizio", typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "start", widthEM = 5)
    public int inizio;

    /**
     * progressivo alla fine dell'anno (obbligatorio, unico) non bisestile <br>
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, caption = "fine", typeNum = AETypeNum.positiviOnly)
    @AIColumn(header = "end", widthEM = 5)
    public int fine;

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getTitolo();
    }

}