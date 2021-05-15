package it.algos.simple.backend.packages.bolla;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import it.algos.vaadflow14.backend.enumeration.*;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;
import static java.awt.image.ImageObserver.WIDTH;

/**
 * Project: simple <br>
 * Created by Algos <br>
 * User: gac <br>
 * Fix date: sab, 15-mag-2021 <br>
 * Fix time: 22:02 <br>
 * <p>
 * Classe (obbligatoria) di un package <br>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Le properties sono PUBLIC per poter usare la Reflection <br>
 * Unica classe obbligatoria per un package. <br>
 * Le altre servono solo se si vuole qualcosa in più dello standard minimo. <br>
 * <p>
 * Annotated with Spring: @SpringComponent (vaadin), @QueryEntity (querydsl), @Document (mongodb), @TypeAlias (data) <br>
 * Annotated with @SpringComponent, @QueryEntity, @Document, @TypeAlias <br>
 * Annotated with Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, @EqualsAndHashCode <br>
 * Annotated with Algos: @AIScript per controllare il typo di file e la ri-creazione con Wizard <br>
 * Annotated with Algos: @AIEntity per informazioni sulle property per il DB <br>
 * Annotated with Algos: @AIView per info su menu, icon, route, search e sort <br>
 * Annotated with Algos: @AIList per info sulla Grid e sulle colonne <br>
 * Annotated with Algos: @AIForm per info sul Form e sulle properties <br>
 */
@SpringComponent
@QueryEntity
@Document(collection = "bolla")
@TypeAlias("bolla")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderBolla")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false, type = AETypeFile.entity)
@AIEntity(recordName = "Bolla", keyPropertyName = "pertino", usaCreazione = true, usaModifica = true, usaCompany = false)
@AIView(menuName = "Bolla", menuIcon = VaadinIcon.ASTERISK, searchProperty = "pertino", sortProperty = "pertino")
@AIList(fields = "code,descrizione", usaRowIndex = false)
@AIForm(fields = "code,descrizione", operationForm = AEOperation.showOnly, usaSpostamentoTraSchede = false)
public class Bolla extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

     

     /**
     * pertino di riferimento (obbligatorio, unico) <br>
     */
    @NotBlank(message = "Il pertino è obbligatorio")
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @Size(min = 2, max = 50)
    @AIField(type = AETypeField.text, required = true, focus = true, caption = "pertino", widthEM = 8)
    @AIColumn(header = "pertino", widthEM = 8)
    public String code;

     /**
     * descrizione (facoltativo, non unico) <br>
     */
    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
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