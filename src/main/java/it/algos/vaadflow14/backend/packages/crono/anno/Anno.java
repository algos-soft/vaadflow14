package it.algos.vaadflow14.backend.packages.crono.anno;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEFieldType;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 02-ago-2020
 * Time: 07:20
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "anno")
@TypeAlias("anno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderAnno")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Anno", keyPropertyName = "nome")
@AIView(menuIcon = VaadinIcon.CALENDAR)
@AIList(fields = "ordine,secolo,nome,bisestile")
@AIForm(fields = "ordine,secolo,nome,bisestile")
public class Anno extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * ordinamento (obbligatorio, unico) <br>
     */
    @NotNull
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AEFieldType.integer, widthEM = 5)
    @AIColumn(header = "#", widthEM = 5)
    public int ordine;

    /**
     * secolo di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @Indexed()
    @DBRef
    @AIField(type = AEFieldType.combo, comboClazz = Secolo.class)
    @AIColumn(widthEM = 8)
    public Secolo secolo;


    /**
     * nome completo (obbligatorio, unico) <br>
     */
    @NotNull
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AEFieldType.text,  widthEM = 10)
    @AIColumn(flexGrow = true)
    public String nome;

    /**
     * flag bisestile (obbligatorio)
     */
    @Indexed()
    @AIField(type = AEFieldType.booleano, caption = "Bisestile", widthEM = 6)
    @AIColumn(header = "BS", widthEM = 6)
    public boolean bisestile;

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getNome();
    }

}